package com.niconicocalendar.controller;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.niconicocalendar.Feelings;
import com.niconicocalendar.User;

@Controller
public class IndexController {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@RequestMapping(value = "/")
	public String index(Model model) {
		
		// カレンダー取得
		Calendar calendar = Calendar.getInstance();
		int nowYear = calendar.get(Calendar.YEAR);
		int nowMonth = calendar.get(Calendar.MONTH) + 1;
		int nowDay = calendar.get(Calendar.DATE);

		model.addAttribute("dispYear", nowYear);
		model.addAttribute("dispMonth", nowMonth);

		calendar.set(nowYear, nowMonth - 1, 1);
		int lastDay = calendar.getActualMaximum(Calendar.DATE);
		model.addAttribute("lastDay", lastDay);
		
		// ユーザーを取得
		List<User> user = jdbcTemplate.query("select * from user_tbl",
				(rs, rowNum) -> new User(rs.getInt("userId"), rs.getString("username")));
		model.addAttribute("user", user);
		
		// Feelingsを取得
		List<Feelings> feelings = jdbcTemplate.query("select * from feelings_tbl",
				(rs, rowNum) -> new Feelings(rs.getInt("feelingId"), rs.getString("feeling")));
		model.addAttribute("feelings", feelings);
		
		// 履歴を取得
		List<Feelings> feelingHistory = jdbcTemplate.query("select userId, day, feelingId from feelings_history_tbl where year=? and month=?",
				(rs, rowNum) -> new Feelings(rs.getInt("userId"), rs.getInt("day"), rs.getInt("feelingId")), nowYear, nowMonth);
		model.addAttribute("feelingHistory", feelingHistory);
		
		return "niconico";
	}
	
	
	@RequestMapping(value = "/regist")
	public String registerUser(Model model, @RequestParam("name") String name) {
		
		// ユーザー追加
		int count = jdbcTemplate.queryForObject("select count(*) from user_tbl", Integer.class);
		if (count > 0) {
			int maxUserId = jdbcTemplate.queryForObject("select max(userId) from user_tbl", Integer.class);
			jdbcTemplate.update("insert into user_tbl(userId, username) VALUES (?,?)", maxUserId + 1, name);
		} else {
			jdbcTemplate.update("insert into user_tbl(userId, username) VALUES (?,?)", 1, name);
		}
			
		// カレンダー取得
		Calendar calendar = Calendar.getInstance();
		int nowYear = calendar.get(Calendar.YEAR);
		int nowMonth = calendar.get(Calendar.MONTH) + 1;
		int nowDay = calendar.get(Calendar.DATE);

		model.addAttribute("dispYear", nowYear);
		model.addAttribute("dispMonth", nowMonth);

		calendar.set(nowYear, nowMonth - 1, 1);
		int lastDay = calendar.getActualMaximum(Calendar.DATE);
		model.addAttribute("lastDay", lastDay);
		
		// ユーザーを取得
		List<User> user = jdbcTemplate.query("select * from user_tbl",
				(rs, rowNum) -> new User(rs.getInt("userId"), rs.getString("username")));
		model.addAttribute("user", user);

		// Feelingsを取得
		List<Feelings> feelings = jdbcTemplate.query("select * from feelings_tbl",
				(rs, rowNum) -> new Feelings(rs.getInt("feelingId"), rs.getString("feeling")));
		model.addAttribute("feelings", feelings);
		
		// 履歴を取得
		List<Feelings> feelingHistory = jdbcTemplate.query("select userId, day, feelingId from feelings_history_tbl where year=? and month=?",
				(rs, rowNum) -> new Feelings(rs.getInt("userId"), rs.getInt("day"), rs.getInt("feelingId")), nowYear, nowMonth);
		model.addAttribute("feelingHistory", feelingHistory);
		
		return "niconico";
	}
	
	@RequestMapping(value = "/delete")
	public String deleteUser(Model model, @RequestParam("userId") int userId) {
		
		// ユーザー削除
		jdbcTemplate.update("delete from user_tbl where userId=?", userId);
		int count = jdbcTemplate.queryForObject("select count(*) from feelings_history_tbl where userId=?",
				Integer.class, userId);
		if (count>0){
			jdbcTemplate.update("delete from feelings_history_tbl where userId=?", userId);
		}
		
		// カレンダー取得
		Calendar calendar = Calendar.getInstance();
		int nowYear = calendar.get(Calendar.YEAR);
		int nowMonth = calendar.get(Calendar.MONTH) + 1;
		int nowDay = calendar.get(Calendar.DATE);

		model.addAttribute("dispYear", nowYear);
		model.addAttribute("dispMonth", nowMonth);

		calendar.set(nowYear, nowMonth - 1, 1);
		int lastDay = calendar.getActualMaximum(Calendar.DATE);
		model.addAttribute("lastDay", lastDay);
		
		// ユーザーを取得
		List<User> user = jdbcTemplate.query("select * from user_tbl",
				(rs, rowNum) -> new User(rs.getInt("userId"), rs.getString("username")));
		model.addAttribute("user", user);

		// Feelingsを取得
		List<Feelings> feelings = jdbcTemplate.query("select * from feelings_tbl",
				(rs, rowNum) -> new Feelings(rs.getInt("feelingId"), rs.getString("feeling")));
		model.addAttribute("feelings", feelings);
		
		// 履歴を取得
		List<Feelings> feelingHistory = jdbcTemplate.query("select userId, day, feelingId from feelings_history_tbl where year=? and month=?",
				(rs, rowNum) -> new Feelings(rs.getInt("userId"), rs.getInt("day"), rs.getInt("feelingId")), nowYear, nowMonth);
		model.addAttribute("feelingHistory", feelingHistory);
		
		return "niconico";
	}
	
	@RequestMapping(value = "/feel")
	public String registFeelings(Model model, @RequestParam("userId") int userId, @RequestParam("feelingId") int feelingId) {
		
		// カレンダー取得
		Calendar calendar = Calendar.getInstance();
		int nowYear = calendar.get(Calendar.YEAR);
		int nowMonth = calendar.get(Calendar.MONTH) + 1;
		int nowDay = calendar.get(Calendar.DATE);

		model.addAttribute("dispYear", nowYear);
		model.addAttribute("dispMonth", nowMonth);

		calendar.set(nowYear, nowMonth - 1, 1);
		int lastDay = calendar.getActualMaximum(Calendar.DATE);
		model.addAttribute("lastDay", lastDay);
		
		// ニコニコ登録
		int count = jdbcTemplate.queryForObject("select count(*) from feelings_history_tbl where userId=? and year=? and month=? and day=?",
				Integer.class, userId, nowYear, nowMonth, nowDay);
		if (count == 0) {
			jdbcTemplate.update("insert into feelings_history_tbl(userId, year, month, day, feelingId) VALUES (?,?,?,?,?)",
					userId, nowYear, nowMonth, nowDay, feelingId);
		} else {
			jdbcTemplate.update("update feelings_history_tbl set feelingId=? where userId=? and year=? and month=? and day=? ",
					feelingId, userId, nowYear, nowMonth, nowDay);
		}
		
		// ユーザーを取得
		List<User> user = jdbcTemplate.query("select * from user_tbl",
				(rs, rowNum) -> new User(rs.getInt("userId"), rs.getString("username")));
		model.addAttribute("user", user);
		
		// Feelingsを取得
		List<Feelings> feelings = jdbcTemplate.query("select * from feelings_tbl",
				(rs, rowNum) -> new Feelings(rs.getInt("feelingId"), rs.getString("feeling")));
		model.addAttribute("feelings", feelings);
		
		// 履歴を取得
		List<Feelings> feelingHistory = jdbcTemplate.query("select userId, day, feelingId from feelings_history_tbl where year=? and month=?",
				(rs, rowNum) -> new Feelings(rs.getInt("userId"), rs.getInt("day"), rs.getInt("feelingId")), nowYear, nowMonth);
		model.addAttribute("feelingHistory", feelingHistory);
		
		return "niconico";
	}
	
	@RequestMapping(value = "/select")
	public String selectFeeling(Model model, @RequestParam("userId") int userId, @RequestParam("year") int year, @RequestParam("month") int month, @RequestParam("day") int day) {
		
		// カレンダー取得
		Calendar calendar = Calendar.getInstance();
		int nowYear = calendar.get(Calendar.YEAR);
		int nowMonth = calendar.get(Calendar.MONTH) + 1;
		int nowDay = calendar.get(Calendar.DATE);

		model.addAttribute("dispYear", nowYear);
		model.addAttribute("dispMonth", nowMonth);

		calendar.set(nowYear, nowMonth - 1, 1);
		int lastDay = calendar.getActualMaximum(Calendar.DATE);
		model.addAttribute("lastDay", lastDay);
		
		// ユーザーを取得
		List<User> user = jdbcTemplate.query("select * from user_tbl",
				(rs, rowNum) -> new User(rs.getInt("userId"), rs.getString("username")));
		model.addAttribute("user", user);
		
		// Feelingsを取得
		List<Feelings> feelings = jdbcTemplate.query("select * from feelings_tbl",
				(rs, rowNum) -> new Feelings(rs.getInt("feelingId"), rs.getString("feeling")));
		model.addAttribute("feelings", feelings);
		
		// 履歴を取得
		List<Feelings> feelingHistory = jdbcTemplate.query("select userId, day, feelingId from feelings_history_tbl where year=? and month=?",
				(rs, rowNum) -> new Feelings(rs.getInt("userId"), rs.getInt("day"), rs.getInt("feelingId")), nowYear, nowMonth);
		model.addAttribute("feelingHistory", feelingHistory);
		
		model.addAttribute("userId",userId);
		model.addAttribute("year",year);
		model.addAttribute("month",month);
		model.addAttribute("day",day);
		
		return "selectFeeling";
	}
	
}