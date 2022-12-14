package com.pyrtoper.dictionary.keyboard;

import java.util.Collections;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class InlineKeyboardMaker {

    //Similar words keyboard

    public InlineKeyboardMarkup getInlineMessageMarkup(List<String> wordNameList, String initialWord) {
        // get inline keyboard where buttons - similar to initial words + word not found button
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowListOfButtons = new ArrayList<>();
        wordNameList.stream()
            .map(this::getButton)
            .forEach(rowListOfButtons::add);
        rowListOfButtons.add(getNotFoundButton(initialWord));
        inlineKeyboardMarkup.setKeyboard(rowListOfButtons);
        return inlineKeyboardMarkup;
    }


    private List<InlineKeyboardButton> getButton(String wordName) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(wordName);
        button.setCallbackData(wordName);
        return Collections.singletonList(button);
    }

    private List<InlineKeyboardButton> getNotFoundButton(String initialWord) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        String buttonData = "Мое слово - " + initialWord;
        button.setText(buttonData);
        button.setCallbackData(buttonData);
        return Collections.singletonList(button);
    }
}
