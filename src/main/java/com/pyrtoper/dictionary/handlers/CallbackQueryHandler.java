package com.pyrtoper.dictionary.handlers;

import com.pyrtoper.dictionary.constant.WorkState;
import com.pyrtoper.dictionary.exception.WordIsMissingException;
import com.pyrtoper.dictionary.repository.WorkStateRepository;
import com.pyrtoper.dictionary.service.MissingWordService;
import com.pyrtoper.dictionary.service.WorkStateService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CallbackQueryHandler {
    private final MissingWordService missingWordService;
    private final Map<WorkState, WorkStateService> workStateServiceMap;
    private final WorkStateRepository workStateRepository;
    private final Logger logger = LoggerFactory.getLogger(MessageHandler.class.getName());
    private static final Pattern missingWordPattern = Pattern.compile("Мое слово - (.*)");

    @Autowired
    public CallbackQueryHandler(MissingWordService missingWordService,
            WorkStateRepository workStateRepository,
            List<WorkStateService> workStateServiceList) {
        this.missingWordService = missingWordService;
        this.workStateRepository = workStateRepository;
        this.workStateServiceMap = workStateServiceList.stream()
            .collect(Collectors.toMap(WorkStateService::getType,
                (workStateService -> workStateService)));
    }

    public BotApiMethod<?> processCallbackQuery(CallbackQuery callbackQuery) {
        Matcher missingWordMatcher = missingWordPattern.matcher(callbackQuery.getData());
        if (missingWordMatcher.find()) {
            missingWordService.saveMissingWord(missingWordMatcher.group(1));
            return getWordIsMissingMessage(callbackQuery);
        } else {
            WorkStateService workStateService = workStateServiceMap.get(
                workStateRepository.findById(callbackQuery.getMessage().getChatId()).orElseThrow()
                    .getWorkState());
            try {
                return new SendMessage(callbackQuery.getMessage().getChatId().toString(),
                    workStateService.translate(callbackQuery.getData()));
            } catch (WordIsMissingException e) {
                logger.debug(e.toString());
                return getWrongWorkStateMessage(callbackQuery);
            } catch (Exception e) {
                logger.error(e.toString());
                return new SendMessage(callbackQuery.getMessage().getChatId().toString(),
                    "Неизвестная ошибка, мой создатель оповещен об этом!");
            }
        }
    }

    private SendMessage getWrongWorkStateMessage(CallbackQuery callbackQuery) {
        return new SendMessage(callbackQuery.getMessage().getChatId().toString(),
                "Слово не найдено. Проверьте, пожалуйста, верно ли поставлен режим работы бота");
    }

    private SendMessage getWordIsMissingMessage(CallbackQuery callbackQuery) {
       return new SendMessage(callbackQuery.getMessage().getChatId().toString(),
                "Слово не найдено. "
                    + "Сожалею, что так произошло, мой создатель уже оповещен об этом недоразумении!");
    }
}
