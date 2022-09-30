package com.pyrtoper.dictionary.keyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class InlineKeyboardMaker {

    //Similar words keyboard

    public InlineKeyboardMarkup getInlineMessageMarkup(List<String> wordNameList, String initialWord) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowListOfButtons = new ArrayList<>();
        for (String wordName: wordNameList) {
            rowListOfButtons.add(getButton(wordName));
        }
        rowListOfButtons.add(getNotFoundButton(initialWord));
        inlineKeyboardMarkup.setKeyboard(rowListOfButtons);
        return inlineKeyboardMarkup;
    }


    private List<InlineKeyboardButton> getButton(String wordName) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(wordName);
        button.setCallbackData(wordName);

        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(button);
        return keyboardButtonsRow;
    }

    private List<InlineKeyboardButton> getNotFoundButton(String initialWord) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        String buttonData = "Мое слово - " + initialWord;
        button.setText(buttonData);
        button.setCallbackData(buttonData);

        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(button);
        return keyboardButtonsRow;
    }
}
