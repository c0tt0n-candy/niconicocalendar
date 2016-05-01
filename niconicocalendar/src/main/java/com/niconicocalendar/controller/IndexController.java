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
	
	@RequestMapping(value = "/edit/user")
	public String editUser(Model model, @RequestParam("year") int year, @RequestParam("month") int month) {
			
		// カレンダー取得
		model.addAttribute("dispYear", year);
		model.addAttribute("dispMonth", month);

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
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
				(rs, rowNum) -> new Feelings(rs.getInt("userId"), rs.getInt("day"), rs.getInt("feelingId")), year, month);
		model.addAttribute("feelingHistory", feelingHistory);
		
		return "regist";
	}
	
	@RequestMapping(value = "/register/user")
	public String registerUser(Model model, @RequestParam("name") String name, @RequestParam("year") int year, @RequestParam("month") int month) {
		
		// ユーザー追加
		int count = jdbcTemplate.queryForObject("select count(*) from user_tbl", Integer.class);
		if (count > 0) {
			int maxUserId = jdbcTemplate.queryForObject("select max(userId) from user_tbl", Integer.class);
			jdbcTemplate.update("insert into user_tbl(userId, username) VALUES (?,?)", maxUserId + 1, name);
		} else {
			jdbcTemplate.update("insert into user_tbl(userId, username) VALUES (?,?)", 1, name);
		}
			
		// カレンダー取得
		model.addAttribute("dispYear", year);
		model.addAttribute("dispMonth", month);

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
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
				(rs, rowNum) -> new Feelings(rs.getInt("userId"), rs.getInt("day"), rs.getInt("feelingId")), year, month);
		model.addAttribute("feelingHistory", feelingHistory);
		
		return "niconico";
	}
	
	@RequestMapping(value = "/delete/user")
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
	
	@RequestMapping(value = "/register/feeling")
	public String registFeeling(Model model, @RequestParam("userId") int userId, @RequestParam("year") int year, @RequestParam("month") int month, @RequestParam("day") int day, @RequestParam("feelingId") int feelingId) {
		
		// カレンダー取得
		model.addAttribute("dispYear", year);
		model.addAttribute("dispMonth", month);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		int lastDay = calendar.getActualMaximum(Calendar.DATE);
		model.addAttribute("lastDay", lastDay);
		
		// ニコニコ登録
		int count = jdbcTemplate.queryForObject("select count(*) from feelings_history_tbl where userId=? and year=? and month=? and day=?",
				Integer.class, userId, year, month, day);
		if (count == 0) {
			jdbcTemplate.update("insert into feelings_history_tbl(userId, year, month, day, feelingId) VALUES (?,?,?,?,?)",
					userId, year, month, day, feelingId);
		} else {
			jdbcTemplate.update("update feelings_history_tbl set feelingId=? where userId=? and year=? and month=? and day=? ",
					feelingId, userId, year, month, day);
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
				(rs, rowNum) -> new Feelings(rs.getInt("userId"), rs.getInt("day"), rs.getInt("feelingId")), year, month);
		model.addAttribute("feelingHistory", feelingHistory);
		
		return "niconico";
	}
	
	@RequestMapping(value = "/delete/feeling")
	public String deleteFeeling(Model model, @RequestParam("userId") int userId, @RequestParam("year") int year, @RequestParam("month") int month, @RequestParam("day") int day) {
		
		// カレンダー取得
		model.addAttribute("dispYear", year);
		model.addAttribute("dispMonth", month);

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		int lastDay = calendar.getActualMaximum(Calendar.DATE);
		model.addAttribute("lastDay", lastDay);
		
		// ニコニコ削除
		jdbcTemplate.update("delete from feelings_history_tbl where userId=? and year=? and month=? and day=?",
					userId, year, month, day);
		
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
				(rs, rowNum) -> new Feelings(rs.getInt("userId"), rs.getInt("day"), rs.getInt("feelingId")), year, month);
		model.addAttribute("feelingHistory", feelingHistory);
		
		return "niconico";
	}
	
	@RequestMapping(value = "/select")
	public String selectFeeling(Model model, @RequestParam("userId") int userId, @RequestParam("year") int year, @RequestParam("month") int month, @RequestParam("day") int day) {
		
		// カレンダー取得
		model.addAttribute("dispYear", year);
		model.addAttribute("dispMonth", month);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
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
				(rs, rowNum) -> new Feelings(rs.getInt("userId"), rs.getInt("day"), rs.getInt("feelingId")), year, month);
		model.addAttribute("feelingHistory", feelingHistory);
		
		// 選択日の履歴があれば取得
		int count = jdbcTemplate.queryForObject("select count(*) from feelings_history_tbl where userId=? and year=? and month=? and day=?",
				Integer.class, userId, year, month, day);
		int selectedFeelingId = -1;
		if (count != 0) {
			selectedFeelingId = jdbcTemplate.queryForObject("select feelingId from feelings_history_tbl where userId=? and year=? and month=? and day=?",
				Integer.class, userId, year, month, day);
		}
		
		model.addAttribute("userId",userId);
		model.addAttribute("day",day);
		model.addAttribute("selectedFeelingId",selectedFeelingId);
		
		return "select";
	}
	
	@RequestMapping(value = "/previous")
	public String getPrevious(Model model, @RequestParam("previous") String previous) {
		String[] prev = previous.split("/");
		int prevYear = Integer.parseInt(prev[0]);
		int prevMonth = Integer.parseInt(prev[1]);
		if(prevMonth == 0){
			prevYear -= 1;
			prevMonth = 12;
		}
		model.addAttribute("dispYear",prevYear);
		model.addAttribute("dispMonth",prevMonth);
		
		// カレンダー取得
		Calendar calendar = Calendar.getInstance();
		calendar.set(prevYear, prevMonth - 1, 1);
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
				(rs, rowNum) -> new Feelings(rs.getInt("userId"), rs.getInt("day"), rs.getInt("feelingId")), prevYear, prevMonth);
		model.addAttribute("feelingHistory", feelingHistory);
		
		return "niconico";
	}
	
	@RequestMapping(value = "/next")
	public String getNext(Model model, @RequestParam("next") String next) {
		String[] Next = next.split("/");
		
		int nextYear = Integer.parseInt(Next[0]);
		int nextMonth = Integer.parseInt(Next[1]);
		if(nextMonth == 13){
			nextYear += 1;
			nextMonth = 1;
		}
		model.addAttribute("dispYear",nextYear);
		model.addAttribute("dispMonth",nextMonth);
		
		// カレンダー取得
		Calendar calendar = Calendar.getInstance();
		calendar.set(nextYear, nextMonth - 1, 1);
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
				(rs, rowNum) -> new Feelings(rs.getInt("userId"), rs.getInt("day"), rs.getInt("feelingId")), nextYear, nextMonth);
		model.addAttribute("feelingHistory", feelingHistory);
		
		return "niconico";
	}
}