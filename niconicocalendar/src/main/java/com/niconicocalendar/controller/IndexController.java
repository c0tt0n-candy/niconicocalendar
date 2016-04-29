package com.niconicocalendar.controller;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
		
		List<User> user = jdbcTemplate.query("select * from user_tbl",
				(rs, rowNum) -> new User(rs.getInt("userId"), rs.getString("username")));

		model.addAttribute("user", user);
		
		
		return "niconico";
	}
}