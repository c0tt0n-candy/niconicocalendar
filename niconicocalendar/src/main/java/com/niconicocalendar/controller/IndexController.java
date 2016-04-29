package com.niconicocalendar.controller;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
		
		// 履歴を取得
		List<Feelings> feelings = jdbcTemplate.query("select userId, day, feelingId from feelings_history_tbl where year=? and month=?",
				(rs, rowNum) -> new Feelings(rs.getInt("userId"), rs.getInt("day"), rs.getInt("feelingId")), nowYear, nowMonth);
		model.addAttribute("feelingHistory", feelings);
		
		return "niconico";
	}
}