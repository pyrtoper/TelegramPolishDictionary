package com.pyrtoper.dictionary.config;

import com.pyrtoper.dictionary.bot.DictionaryBot;
import com.pyrtoper.dictionary.bot.TelegramConfig;
import com.pyrtoper.dictionary.handlers.CallbackQueryHandler;
import com.pyrtoper.dictionary.handlers.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;
import java.util.Set;

@Configuration
public class SpringConfig {

    @Autowired
    private TelegramConfig telegramConfig;

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url(telegramConfig.getWebhookPath()).build();
    }

    @Bean
    public DictionaryBot springWebhookBot(SetWebhook setWebhook,
                                          MessageHandler messageHandler,
                                          CallbackQueryHandler callbackQueryHandler) {
        setWebhook.setAllowedUpdates(List.of("message", "callback_query"));
        DictionaryBot bot = new DictionaryBot(setWebhook, messageHandler, callbackQueryHandler);

        bot.setBotPath(telegramConfig.getWebhookPath());
        bot.setBotUsername(telegramConfig.getBotUsername());
        bot.setBotToken(telegramConfig.getBotToken());

        return bot;
    }
}
