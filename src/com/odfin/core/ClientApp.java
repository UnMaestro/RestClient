package com.odfin.core;

import com.odfin.domain.Message;
import com.odfin.domain.User;
import com.odfin.gui.ClientFrame;
import com.odfin.network.rest.RestNetworkClient;

import java.util.Arrays;
import java.util.List;

public class ClientApp {
	public static void main(String[] args) throws Exception {
		String baseURL = "http://localhost:8080";
		String username = "user";

		List<User> userList = Arrays.asList(
				new User(username),
				new User("Tolga"),
				new User("Enes"),
				new User("Leon")
		);

		ClientFrame.launch(baseURL, username, userList);
	}
}