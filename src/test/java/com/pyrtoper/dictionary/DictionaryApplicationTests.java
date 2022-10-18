package com.pyrtoper.dictionary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.pyrtoper.dictionary.bot.DictionaryBot;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DictionaryApplication.class)
class DictionaryApplicationTests {
	@Autowired
	private DictionaryBot dictionaryBot;

	@Test
	void connectingWithEmptyBody() throws Exception {
		HttpURLConnection connection = (HttpURLConnection) new URL(dictionaryBot.getBotPath()).openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("Accept", "application/json");
		connection.setDoOutput(true);
		try (OutputStream os = connection.getOutputStream()) {
			byte[] input = "{}".getBytes();
			os.write(input);
		}
		assertEquals(connection.getResponseCode(), 200);
	}



}
