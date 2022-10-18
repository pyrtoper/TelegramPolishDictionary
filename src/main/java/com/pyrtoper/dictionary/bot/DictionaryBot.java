package com.pyrtoper.dictionary.bot;

import com.pyrtoper.dictionary.handlers.CallbackQueryHandler;
import com.pyrtoper.dictionary.handlers.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

public class DictionaryBot extends SpringWebhookBot {
    private final String botUsername;
    private final String botToken;
    private final String botPath;
    private final MessageHandler messageHandler;
    private final CallbackQueryHandler callbackQueryHandler;

    private static final Logger logger = LoggerFactory.getLogger(DictionaryBot.class.getName());

    public DictionaryBot(SetWebhook setWebhook,
                         MessageHandler messageHandler,
                         CallbackQueryHandler callbackQueryHandler,
                         String botUsername,
                         String botToken,
                         String botPath) {
        super(setWebhook);
        this.messageHandler = messageHandler;
        this.callbackQueryHandler = callbackQueryHandler;
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.botPath = botPath;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        try {
            return handleUpdate(update);
        } catch (Exception e) {
            logger.error("Error in DictionaryBot class: " + e.getMessage());
        }
        logger.debug(DictionaryBot.class.getName() + " onWebhookUpdateReceived() "
            + "didn't do anything");
        return null;
    }

    private BotApiMethod<?> handleUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            return callbackQueryHandler.processCallbackQuery(callbackQuery);
        } else if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message != null) {
                return messageHandler.answerMessage(message);
            }
        }
        logger.debug(DictionaryBot.class.getName() + " handleUpdate() didn't do anything");
        return null;
    }

    @Override
    public String getBotPath() {
        return botPath;
    }

}
