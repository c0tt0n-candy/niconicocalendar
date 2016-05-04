package com.niconicocalendar.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.niconicocalendar.User;

@Component
public class UserManager {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<User> getAllUsers() {
		List<User> userList = jdbcTemplate.query("select * from user_tbl",
				(rs, rowNum) -> new User(rs.getInt("userId"), rs.getString("userName")));
		return userList;
	}

	public User getOneUser(Integer userId) {
		User user = jdbcTemplate.queryForObject("select * from user_tbl where userId=?", User.class, userId);
		return user;
	}

	public void registerUser(String userName) {
		int count = jdbcTemplate.queryForObject("select count(*) from user_tbl", Integer.class);
		if (count > 0) {
			int maxUserId = jdbcTemplate.queryForObject("select max(userId) from user_tbl", Integer.class);
			jdbcTemplate.update("insert into user_tbl(userId, userName) VALUES (?,?)",
					new Object[] { maxUserId + 1, userName });
		} else {
			jdbcTemplate.update("insert into user_tbl(userId, userName) VALUES (?,?)", new Object[] { 1, userName });
		}

	}

	public void updateUser(Integer userId) {
		String userName = jdbcTemplate.queryForObject("select userName from user_tbl where userId=?", String.class,
				userId);
		if (userName != "") {
			jdbcTemplate.update("update user_tbl set userName=? where userId=?", userName, userId);
		}
	}

	public void deleteUser(Integer userId) {
		jdbcTemplate.update("delete from user_tbl where userId=?", userId);
		int count = jdbcTemplate.queryForObject("select count(*) from feelings_history_tbl where userId=?",
				Integer.class, userId);
		if (count > 0) {
			jdbcTemplate.update("delete from feelings_history_tbl where userId=?", userId);
		}
	}

}
