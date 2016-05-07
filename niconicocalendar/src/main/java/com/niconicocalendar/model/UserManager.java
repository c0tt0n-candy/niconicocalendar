package com.niconicocalendar.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.niconicocalendar.User;

@Component
public class UserManager {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<User> getAllUsers() {
		List<User> users= jdbcTemplate.query("select * from user_tbl", new UserRowMapper());
		return users;
	}

	public User getOneUser(Integer userId) {
		User user = jdbcTemplate.queryForObject("select * from user_tbl where userId=?", new UserRowMapper(), userId);
		return user;
	}

	public void registerUser(User user) {
		int count = jdbcTemplate.queryForObject("select count(*) from user_tbl", Integer.class);
		if (count > 0) {
			int maxUserId = jdbcTemplate.queryForObject("select max(userId) from user_tbl", Integer.class);
			jdbcTemplate.update("insert into user_tbl(userId, userName) VALUES (?,?)",
					new Object[] { maxUserId + 1, user.getUserName() });
		} else {
			jdbcTemplate.update("insert into user_tbl(userId, userName) VALUES (?,?)", new Object[] { 1, user.getUserName() });
		}
	}

	public void updateUser(User user) {
		if (user.getUserName() != "") {
			jdbcTemplate.update("update user_tbl set userName=? where userId=?", user.getUserName(), user.getUserId());
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

	private class UserRowMapper implements RowMapper<User>{
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			int userId = rs.getInt("userId");
			String userName = rs.getString("userName");
			return new User(userId, userName);
		}
	}
}
