package com.pyrtoper.dictionary;

import com.pyrtoper.dictionary.bot.DictionaryBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class DictionaryApplication {

	@Autowired
	ApplicationContext applicationContext;

	public static void main(String[] args) {
		SpringApplication.run(DictionaryApplication.class, args);

//		try {
//			TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
//			botsApi.registerBot(new DictionaryBot("Polish_Russian_bot", "5506359833:AAEhoRQ6SH9mea04fzH1u8SZHOHeLFmXJIM"));
//		} catch (TelegramApiException e) {
//			e.printStackTrace();
//		}
	}

//	@Override
//	public void run(String... args) throws Exception {
//		DictionaryBot bot = applicationContext.getBean(DictionaryBot.class);
//		try {
//			TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
//			botsApi.registerBot(bot);
//		} catch (TelegramApiException e) {
//			e.printStackTrace();
//		}
//	}
}
