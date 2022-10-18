package com.pyrtoper.dictionary.keyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class WorkStateKeyboardMaker {
    public ReplyKeyboardMarkup getPolishKeyboard() {
        KeyboardRow row = new KeyboardRow();
        row.add(new KeyboardButton("Польский -> Русский"));
        row.add(new KeyboardButton("Русский -> Польский"));
        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        return replyKeyboardMarkup;
    }
}
