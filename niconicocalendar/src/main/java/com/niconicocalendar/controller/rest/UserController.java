package com.niconicocalendar.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.niconicocalendar.User;
import com.niconicocalendar.model.UserManager;

@RestController
@RequestMapping("rest/user")
public class UserController {
	@Autowired
	UserManager userManager;

	// ユーザー全件取得
	@RequestMapping(method = RequestMethod.GET)
	List<User> getAll() {
		List<User> users = userManager.getAllUsers();
		return users;
	}

	// ユーザー1件取得
	@RequestMapping(value = "{userId}", method = RequestMethod.GET)
	User getOne(@PathVariable Integer userId) {
		User user = userManager.getOneUser(userId);
		return user;
	}

	// ユーザー新規作成
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	void register(@PathVariable String userName) {
		userManager.registerUser(userName);
	}

	// ユーザー1件更新
	@RequestMapping(value = "{userId}", method = RequestMethod.POST)
	void update(@PathVariable Integer userId) {
		userManager.updateUser(userId);
	}

	// ユーザー1件削除
	@RequestMapping(value = "{userId}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void delete(@PathVariable Integer userId) {
		userManager.deleteUser(userId);
	}
}
