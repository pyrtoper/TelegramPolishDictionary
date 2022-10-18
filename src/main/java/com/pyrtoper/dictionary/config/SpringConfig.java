package com.pyrtoper.dictionary.config;

import com.pyrtoper.dictionary.bot.DictionaryBot;
import com.pyrtoper.dictionary.handlers.CallbackQueryHandler;
import com.pyrtoper.dictionary.handlers.MessageHandler;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;


@Configuration
@EnableAspectJAutoProxy
public class SpringConfig {

    @Bean
    public SetWebhook setWebhookInstance(@Value("${telegram.webhook-path}") String webhookPath) {
        SetWebhook webhook = SetWebhook.builder().url(webhookPath).build();
        webhook.setAllowedUpdates(List.of("message", "callback_query"));
        return webhook;
    }

    @Bean
    @Autowired
    public DictionaryBot springWebhookBot(SetWebhook setWebhook,
                                          MessageHandler messageHandler,
                                          CallbackQueryHandler callbackQueryHandler,
        @Value("${telegram.bot.username}") String botUsername,
        @Value("${telegram.bot.token}") String botToken,
        @Value("${telegram.webhook-path}") String webhookPath

    ) {
        return new DictionaryBot(setWebhook, messageHandler, callbackQueryHandler,
            botUsername, botToken, webhookPath);
    }

}
