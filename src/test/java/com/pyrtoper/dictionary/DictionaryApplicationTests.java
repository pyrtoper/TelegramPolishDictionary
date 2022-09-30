package com.pyrtoper.dictionary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.pyrtoper.dictionary.config.TelegramConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SpringBootTest(classes = DictionaryApplication.class)
class DictionaryApplicationTests {
	@Autowired
	private TelegramConfig telegramConfig;

	@Test
	void connectingWithEmptyBody() throws Exception {
		//need to start DictionaryApplication.main() before that test, not sure why
		DictionaryApplication.main(new String[] {});
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(telegramConfig.getWebhookPath()))
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString("{}"))
				.build();

		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());
		assertEquals(response.statusCode(), 200);
	}



}
