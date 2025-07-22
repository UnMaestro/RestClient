package com.odfin.core;

import com.odfin.domain.Message;
import com.odfin.domain.User;
import com.odfin.gui.ClientFrame;
import com.odfin.gui.LogInFrame;
import com.odfin.network.rest.RestNetworkClient;

import java.util.Arrays;
import java.util.List;

public class ClientApp {
	public static void main(String[] args) throws Exception {
		String baseURL = "http://localhost:8080";
		LogInFrame login = new LogInFrame();
		String loginData[] = login.showLogInDialog();
		User user = new User(loginData[0]);
		List<User> userList = Arrays.asList(
				new User(user.getUsername()),
				new User("Tolga"),
				new User("Enes"),
				new User("Leon")
		);

		ClientFrame.launch(baseURL, user.getUsername(), userList);
	}
}