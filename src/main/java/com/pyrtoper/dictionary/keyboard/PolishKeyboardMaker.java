package com.pyrtoper.dictionary.keyboard;

import com.pyrtoper.dictionary.constant.PolishLetter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class PolishKeyboardMaker {
    public ReplyKeyboardMarkup getPolishKeyboard() {
        KeyboardRow row = new KeyboardRow();
//        KeyboardRow row2 = new KeyboardRow();
//        for (PolishLetter letter: PolishLetter.values()) {
////            InlineKeyboardButton button1 = new InlineKeyboardButton();
////            InlineKeyboardButton button2 = new InlineKeyboardButton();
////            button1.setCallbackData(letter.getLetter());
////            button1.setText(letter.getLetter());
////            button2.setCallbackData(letter.getLetter().toUpperCase());
////            button2.setText(letter.getLetter().toUpperCase());
////            row1.add(button1);
//
//            row1.add(new KeyboardButton(letter.getLetter()));
//            row2.add(new KeyboardButton(letter.getLetter().toUpperCase()));
//        }
        row.add(new KeyboardButton("Польский -> Русский"));
        row.add(new KeyboardButton("Русский -> Польский"));
        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row);
//        keyboard.add(row2);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        return replyKeyboardMarkup;
    }
}
