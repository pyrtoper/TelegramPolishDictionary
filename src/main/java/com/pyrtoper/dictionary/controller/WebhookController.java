package com.pyrtoper.dictionary.controller;

import com.pyrtoper.dictionary.bot.DictionaryBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class WebhookController {

    private final DictionaryBot dictionaryBot;

    @Autowired
    public WebhookController(DictionaryBot dictionaryBot) {
        this.dictionaryBot = dictionaryBot;
    }

    @PostMapping("/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return dictionaryBot.onWebhookUpdateReceived(update);
    }
}
