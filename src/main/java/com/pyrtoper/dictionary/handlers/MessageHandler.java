package com.pyrtoper.dictionary.handlers;

import com.pyrtoper.dictionary.constant.WorkState;
import com.pyrtoper.dictionary.entity.WorkStates;
import com.pyrtoper.dictionary.exception.WordIsMissingException;
import com.pyrtoper.dictionary.keyboard.InlineKeyboardMaker;
import com.pyrtoper.dictionary.keyboard.WorkStateKeyboardMaker;
import com.pyrtoper.dictionary.repository.WorkStateRepository;
import com.pyrtoper.dictionary.service.WorkStateService;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Component
public class MessageHandler {
    private final InlineKeyboardMaker inlineKeyboardMaker;
    private final WorkStateKeyboardMaker workStateKeyboardMaker;
    private final WorkStateRepository workStateRepository;
    private final Map<WorkState, WorkStateService> workStateServiceMap;
    private final Logger logger = LoggerFactory.getLogger(MessageHandler.class.getName());
    @Autowired
    public MessageHandler(InlineKeyboardMaker inlineKeyboardMaker,
        WorkStateKeyboardMaker workStateKeyboardMaker,
        WorkStateRepository workStateRepository,
        List<WorkStateService> workStateServiceList
        ) {
        this.inlineKeyboardMaker = inlineKeyboardMaker;
        this.workStateKeyboardMaker = workStateKeyboardMaker;
        this.workStateRepository = workStateRepository;
        this.workStateServiceMap = workStateServiceList.stream()
            .collect(Collectors.toMap(WorkStateService::getType,
                (workStateService -> workStateService)));
    }

    public BotApiMethod<?> answerMessage(Message message) {
        if (message.getText().equals("/start")) {
            workStateRepository.save(new WorkStates(message.getChatId()));
            return sendStartMessage(message.getChatId( ).toString());
        } else if (message.getText().equals("Польский -> Русский")) {
            workStateRepository.save(new WorkStates(message.getChatId(), WorkState.POLISH_TO_RUSSIAN));
            return new SendMessage(message.getChatId().toString(), "Текущий режим: Польский -> Русский!");
        } else if (message.getText().equals("Русский -> Польский")) {
            workStateRepository.save(new WorkStates(message.getChatId(), WorkState.RUSSIAN_TO_POLISH));
            return new SendMessage(message.getChatId().toString(), "Текущий режим: Русский -> Польский!");
        } else {
            WorkStateService workStateService = workStateServiceMap.get(
                workStateRepository.findById(message.getChatId()).orElseThrow()
                    .getWorkState()
            );
            try {
                return new SendMessage(message.getChatId().toString(),
                    workStateService.translate(message.getText()));
            } catch (WordIsMissingException e) {
                logger.debug(e.toString());
                List<String> similarWords = workStateService.getSimilarWords(message.getText());
                SendMessage inlineSimilarityMessage = new SendMessage(
                    message.getChatId().toString(), "Возможно, Вы имели ввиду:\n");
                inlineSimilarityMessage.setReplyMarkup(
                    inlineKeyboardMaker.getInlineMessageMarkup(similarWords, message.getText()));
                return inlineSimilarityMessage;
            } catch (IllegalArgumentException e) {
                logger.error(e.toString());
                return new SendMessage(message.getChatId().toString(), "Неизвестная ошибка, "
                    + "мой создатель оповещен об этом!");
            }
        }
    }

    private SendMessage sendStartMessage(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Что умеет бот:\n\n" +
                " \uD83D\uDCCC В режиме Польский -> Русский:\n" +
                "    ✅ Показывать различные значения введенного слова, переведенные на русский язык;\n" +
                "    ✅ Показывать склонения существительных;\n" +
                "    ✅ Показывать возможные переводы слова на русский язык\n\n" +
                " \uD83D\uDCCC В режиме Русский -> Польский:\n" +
                "    ✅ Показывать возможные переводы введенного слова на польский язык\n\n" +
                "❗ Также поддерживает функцию: Возможно, Вы имели в виду... Поэтому не бойтесь опечаток и разных форм слова," +
                " бот постарается найти наиболее подходящее к введенному Вами слову!\n\n" +
                "❗ По умолчанию установлен первый режим");
        sendMessage.setReplyMarkup(workStateKeyboardMaker.getWorkStateKeyboard());
        sendMessage.enableMarkdown(true);
        return sendMessage;
    }
}
