package com.pyrtoper.dictionary.handlers;

import com.pyrtoper.dictionary.constant.PolishLetter;
import com.pyrtoper.dictionary.entity.Word;
import com.pyrtoper.dictionary.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Arrays;

@Component
public class CallbackQueryHandler {

    @Autowired
    private WordService wordService;
    public BotApiMethod<?> processCallbackQuery(CallbackQuery callbackQuery) {
        Word word = wordService.getWordByName(callbackQuery.getData());
        return new SendMessage(callbackQuery.getMessage().getChatId().toString(), "Значения:\n" + word.getTranslatedMeanings());
    }


}
