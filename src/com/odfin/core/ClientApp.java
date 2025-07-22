package com.odfin.core;

import com.odfin.domain.Message;
import com.odfin.domain.User;
import com.odfin.network.rest.RestNetworkClient;

import java.util.List;

public class ClientApp {
	public static void main(String[] args) throws Exception {
		RestNetworkClient client = new RestNetworkClient("http://localhost:8080");

		client.sendMessage(new Message("Pas", "Test"));

		List<Message> history = client.getMessages();
		history.forEach(m -> System.out.println(m.getAuthor() + ": " + m.getText() + " " + m.getCreated()));
	}
}