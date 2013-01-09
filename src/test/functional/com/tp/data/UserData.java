package com.tp.data;

import com.tp.entity.account.User;
import com.tp.utils.RandomData;

public class UserData {
	public static User randomNewUser() {
		User user = new User();
		user.setLoginName(RandomData.randomName("user"));
		user.setName(RandomData.randomName("User"));
		user.setPlainPassword(RandomData.randomName("password"));

		return user;
	}
}
