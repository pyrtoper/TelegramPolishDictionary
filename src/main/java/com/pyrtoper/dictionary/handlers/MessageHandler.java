package com.pyrtoper.dictionary.handlers;

import com.pyrtoper.dictionary.bot.DictionaryBot;
import com.pyrtoper.dictionary.bot.TelegramConfig;
import com.pyrtoper.dictionary.constant.WorkState;
import com.pyrtoper.dictionary.entity.Translation;
import com.pyrtoper.dictionary.entity.Word;
import com.pyrtoper.dictionary.exception.WordIsMissingException;
import com.pyrtoper.dictionary.keyboard.InlineKeyboardMaker;
import com.pyrtoper.dictionary.keyboard.PolishKeyboardMaker;
import com.pyrtoper.dictionary.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Component
public class MessageHandler {
    @Autowired
    private WordService wordService;
    @Autowired
    private InlineKeyboardMaker inlineKeyboardMaker;
    @Autowired
    private PolishKeyboardMaker polishKeyboardMaker;
    @Autowired
    private TelegramConfig telegramConfig;

    public BotApiMethod<?> answerMessage(Message message) {
        if (message.getText().equals("/start")) {
            return sendStartMessage(message.getChatId().toString());
        } else if (message.getText().equals("Польский -> Русский")) {
//            DictionaryBot.setPolishToRussianWorkState(true);
            telegramConfig.setWorkState(WorkState.POLISH_TO_RUSSIAN);
            return new SendMessage(message.getChatId().toString(), "Текущий режим: Польский -> Русский!");
        } else if (message.getText().equals("Русский -> Польский")) {
//            DictionaryBot.setPolishToRussianWorkState(false);
            telegramConfig.setWorkState(WorkState.RUSSIAN_TO_POLISH);
            return new SendMessage(message.getChatId().toString(), "Текущий режим: Русский -> Польский!");
        }
        else {
            if (telegramConfig.getWorkState() == WorkState.POLISH_TO_RUSSIAN) {
                return processPolishWord(message);
            } else {
                return processRussianWord(message);
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
        sendMessage.setReplyMarkup(polishKeyboardMaker.getPolishKeyboard());
        sendMessage.enableMarkdown(false);
        return sendMessage;
    }

    private SendMessage processPolishWord(Message message) {
        try {
            Word word = wordService.getWordByName(message.getText());
            return new SendMessage(message.getChatId().toString(), word.toString());
        } catch (EmptyResultDataAccessException e) {
            List<String> similarWords = wordService.getSimilarWords(message.getText());
            SendMessage inlineSimilarityMessage = new SendMessage(message.getChatId().toString(), "Возможно, Вы имели ввиду:\n");
            inlineSimilarityMessage.setReplyMarkup(inlineKeyboardMaker.getInlineMessageMarkup(similarWords, message.getText()));
            return inlineSimilarityMessage;
        }
    }

    private SendMessage processRussianWord(Message message) {
        try {
            Translation translation = wordService.getTranslationByName(message.getText());
            return new SendMessage(message.getChatId().toString(), translation.toString());
        } catch (EmptyResultDataAccessException e) {
            List<String> similarTranslations = wordService.getSimilarTranslations(message.getText());
            SendMessage inlineSimilarityMessage = new SendMessage(message.getChatId().toString(), "Возможно, Вы имели ввиду:\n");
            inlineSimilarityMessage.setReplyMarkup(inlineKeyboardMaker.getInlineMessageMarkup(similarTranslations, message.getText()));
            return inlineSimilarityMessage;
        }
    }

}
