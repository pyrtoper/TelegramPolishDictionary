package com.pyrtoper.dictionary.handlers;

import com.pyrtoper.dictionary.bot.DictionaryBot;
import com.pyrtoper.dictionary.bot.TelegramConfig;
import com.pyrtoper.dictionary.constant.WorkState;
import com.pyrtoper.dictionary.entity.MissingWord;
import com.pyrtoper.dictionary.entity.Translation;
import com.pyrtoper.dictionary.entity.Word;
import com.pyrtoper.dictionary.exception.WordIsMissingException;
import com.pyrtoper.dictionary.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CallbackQueryHandler {
    @Autowired
    private WordService wordService;
    @Autowired
    private TelegramConfig telegramConfig;
    public BotApiMethod<?> processCallbackQuery(CallbackQuery callbackQuery) {
        Pattern missingWordPattern = Pattern.compile("Мое слово - (.*)");
        Matcher missingWordMatcher = missingWordPattern.matcher(callbackQuery.getData());
        if (missingWordMatcher.find()) {
            callbackQuery.setData(missingWordMatcher.group(1));
//        if (callbackQuery.getData().contains("Моего слова нет в списке")) {
            return getWordIsMissingMessage(callbackQuery);
        } else {
            if (telegramConfig.getWorkState() == WorkState.POLISH_TO_RUSSIAN) {
                return processPolishWord(callbackQuery);
            } else {
                return processRussianWord(callbackQuery);
            }
        }
    }

    private SendMessage processPolishWord(CallbackQuery callbackQuery) {
        try {
            Word word = wordService.getWordByName(callbackQuery.getData());
            return new SendMessage(callbackQuery.getMessage().getChatId().toString(),
                    word.toString());
        } catch (EmptyResultDataAccessException e) {
            return getWrongWorkStateMessage(callbackQuery);
        }
    }

    private SendMessage processRussianWord(CallbackQuery callbackQuery) {
        try {
            Translation translation = wordService.getTranslationByName(callbackQuery.getData());
            return new SendMessage(callbackQuery.getMessage().getChatId().toString(),
                    translation.toString());
        } catch (EmptyResultDataAccessException e) {
            return getWrongWorkStateMessage(callbackQuery);
        }
    }

    private SendMessage getWrongWorkStateMessage(CallbackQuery callbackQuery) {
        return new SendMessage(callbackQuery.getMessage().getChatId().toString(),
                "Слово не найдено. Проверьте, пожалуйста, верно ли поставлен режим работы бота");
    }

    private SendMessage getWordIsMissingMessage(CallbackQuery callbackQuery) {
        MissingWord missingWord = new MissingWord();
        missingWord.setWordName(callbackQuery.getData());
        missingWord.setLocalDateTime(LocalDateTime.now());
        try {
            wordService.getMissingWordByName(missingWord.getWordName());
        } catch (WordIsMissingException e) {
            wordService.saveMissingWord(missingWord);
        }
        return new SendMessage(callbackQuery.getMessage().getChatId().toString(),
                "Сожалею, что так произошло, мой создатель уже оповещен об этом недоразумении!");
    }

}
