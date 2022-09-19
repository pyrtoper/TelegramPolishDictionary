package com.pyrtoper.dictionary.keyboard;

import com.pyrtoper.dictionary.entity.Word;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class InlineKeyboardMaker {


    public InlineKeyboardMarkup getInlineMessageMarkup(List<String> wordNameList) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowListOfButtons = new ArrayList<>();
        for (String wordName: wordNameList) {
            rowListOfButtons.add(getButton(wordName));
        }
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
}
