package com.pyrtoper.dictionary.bot;

import com.pyrtoper.dictionary.handlers.CallbackQueryHandler;
import com.pyrtoper.dictionary.handlers.MessageHandler;
import com.pyrtoper.dictionary.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;


@PropertySource("classpath:application.properties")
public class DictionaryBot extends SpringWebhookBot {
    private String botUsername;
    private String botToken;

    private String botPath;
    public static boolean PolishToRussian = true;

    @Autowired
    private MessageHandler messageHandler;
    @Autowired
    private CallbackQueryHandler callbackQueryHandler;


//    public DictionaryBot(@Value("${telegram.bot.username}") String botUsername,
//                         @Value("${telegram.bot.token}") String botToken) {
//        this.botUsername = botUsername;
//        this.botToken = botToken;
//        System.out.println(botToken);
//        System.out.println(botUsername);
//    }
    public DictionaryBot(SetWebhook setWebhook,
                         MessageHandler messageHandler,
                         CallbackQueryHandler callbackQueryHandler) {
        super(setWebhook);
        this.messageHandler = messageHandler;
        this.callbackQueryHandler = callbackQueryHandler;
    }


//    public DictionaryBot(String botUsername,
//                         String botToken) {
//        this.botUsername = botUsername;
//        this.botToken = botToken;
//    }


    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

//    @Override
//    public void onUpdateReceived(Update update) {
//        if (update.hasMessage() && update.getMessage().hasText()) {
//            SendMessage message = new SendMessage();
//            message.setChatId(update.getMessage().getChatId().toString());
//            Word word = wordService
//                    .getWordByName(update.getMessage().getText());
//            message.setText(word.getName() + "\n" +
//                    word.getTranslatedMeanings());
////            message.setText(update.getMessage().getText());
//
//            try {
//                execute(message);
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
//        }
//    }

//    public void registerBot() {
//        try {
//            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
//            botsApi.registerBot(this);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }


    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        try {
            return handleUpdate(update);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        return null;
    }

    public void setBotUsername(String botUsername) {
        this.botUsername = botUsername;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    @Override
    public String getBotPath() {
        return botPath;
    }

    public void setBotPath(String botPath) {
        this.botPath = botPath;
    }

    public boolean isPolishToRussian() {
        return PolishToRussian;
    }

    public void setPolishToRussian(boolean polishToRussian) {
        PolishToRussian = polishToRussian;
    }

    public static void setPolishToRussianWorkState(boolean desiredState) {
        DictionaryBot.PolishToRussian = desiredState;
    }
}
