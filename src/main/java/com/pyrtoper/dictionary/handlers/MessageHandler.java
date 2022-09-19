package com.pyrtoper.dictionary.handlers;

import com.pyrtoper.dictionary.constant.PolishLetter;
import com.pyrtoper.dictionary.entity.Word;
import com.pyrtoper.dictionary.keyboard.InlineKeyboardMaker;
import com.pyrtoper.dictionary.keyboard.PolishKeyboardMaker;
import com.pyrtoper.dictionary.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.persistence.NoResultException;
import java.util.Arrays;
import java.util.List;

@Component
public class MessageHandler {

    @Autowired
    private WordService wordService;
    @Autowired
    private InlineKeyboardMaker inlineKeyboardMaker;
    @Autowired
    private PolishKeyboardMaker polishKeyboardMaker;
    public BotApiMethod<?> answerMessage(Message message) {
        String inputText = message.getText();
        if (inputText.equals("/start")) {
            return sendStartMessage(message.getChatId().toString());
        } else if (Arrays.stream(PolishLetter.values()).anyMatch((letter) -> letter.getLetter().equals(inputText))) {
            return new EditMessageText(inputText);
        } else {
            try {
                Word word = wordService.getWordByName(message.getText());
//                return new SendMessage(message.getChatId().toString(), "Значения:\n" + word.getTranslatedMeanings());
                return new SendMessage(message.getChatId().toString(), word.toString());
            } catch (EmptyResultDataAccessException e) {
                List<String> similarWords = wordService.getSimilarWords(message.getText());
                SendMessage inlineSimilarityMessage = new SendMessage(message.getChatId().toString(), "Возможно, Вы имели ввиду:\n");
                inlineSimilarityMessage.setReplyMarkup(inlineKeyboardMaker.getInlineMessageMarkup(similarWords));
                return inlineSimilarityMessage;
            }
        }



//        return new SendMessage(message.getChatId().toString(), message.getText());
    }

    private SendMessage sendStartMessage(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Можете воспользоваться клавиатурой:");
        sendMessage.setReplyMarkup(polishKeyboardMaker.getPolishKeyboard());
        sendMessage.enableMarkdown(false);
        return sendMessage;
    }

}
