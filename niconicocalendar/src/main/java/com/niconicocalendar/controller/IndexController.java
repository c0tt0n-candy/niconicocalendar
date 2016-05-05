package com.niconicocalendar.controller;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.niconicocalendar.Feelings;
import com.niconicocalendar.User;
import com.niconicocalendar.model.FeelingsManager;
import com.niconicocalendar.model.UserManager;

@Controller
public class IndexController {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	UserManager userManager;
	@Autowired
	FeelingsManager feelingsManager;
	
	@RequestMapping(value = "/")
	public String index(Model model) {

		// カレンダー取得
		Calendar calendar = Calendar.getInstance();
		int nowYear = calendar.get(Calendar.YEAR);
		int nowMonth = calendar.get(Calendar.MONTH) + 1;
		calendar.set(nowYear, nowMonth - 1, 1);
		int lastDay = calendar.getActualMaximum(Calendar.DATE);
		model.addAttribute("dispYear", nowYear);
		model.addAttribute("dispMonth", nowMonth);
		model.addAttribute("lastDay", lastDay);
		
		List<User> users = userManager.getAllUsers();
		List<Feelings> feelingsList = feelingsManager.getList();
		List<Feelings> feelingsHistory = feelingsManager.getAllFeelings();
		model.addAttribute("users", users);
		model.addAttribute("feelingsList", feelingsList);
		model.addAttribute("feelingsHistory", feelingsHistory);

		return "niconico";
	}
	
	@RequestMapping(value = "/register")
	public String register(@ModelAttribute("user") User user, Model model, @RequestParam("year") int year, @RequestParam("month") int month) {

		// カレンダー取得
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		int lastDay = calendar.getActualMaximum(Calendar.DATE);
		model.addAttribute("dispYear", year);
		model.addAttribute("dispMonth", month);
		model.addAttribute("lastDay", lastDay);

		List<User> users = userManager.getAllUsers();
		List<Feelings> feelingsList = feelingsManager.getList();
		List<Feelings> feelingsHistory = feelingsManager.getAllFeelings();
		model.addAttribute("users", users);
		model.addAttribute("feelingsList", feelingsList);
		model.addAttribute("feelingsHistory", feelingsHistory);

		return "regist";
	}
	
	@RequestMapping(value = "/register/user")
	public String registerUser(@Validated User user, BindingResult result, Model model, @RequestParam("year") int year, @RequestParam("month") int month) {
		if(result.hasErrors()) {
			return register(user, model, year, month);
		}
		userManager.registerUser(user);

		// カレンダー取得
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		int lastDay = calendar.getActualMaximum(Calendar.DATE);
		model.addAttribute("dispYear", year);
		model.addAttribute("dispMonth", month);
		model.addAttribute("lastDay", lastDay);

		List<User> users = userManager.getAllUsers();
		List<Feelings> feelingsList = feelingsManager.getList();
		List<Feelings> feelingsHistory = feelingsManager.getAllFeelings();
		model.addAttribute("users", users);
		model.addAttribute("feelingsList", feelingsList);
		model.addAttribute("feelingsHistory", feelingsHistory);

		return "redirect:/";
	}
	
	@RequestMapping(value = "/edit/user")
	public String editUser(@ModelAttribute("user") User user, Model model, @RequestParam("userId") int userId, @RequestParam("year") int year, @RequestParam("month") int month) {

		// カレンダー取得
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		int lastDay = calendar.getActualMaximum(Calendar.DATE);
		model.addAttribute("dispYear", year);
		model.addAttribute("dispMonth", month);
		model.addAttribute("lastDay", lastDay);

		User theUser = userManager.getOneUser(userId);
		BeanUtils.copyProperties(theUser, user);
		List<User> users = userManager.getAllUsers();
		List<Feelings> feelingsList = feelingsManager.getList();
		List<Feelings> feelingsHistory = feelingsManager.getAllFeelings();
		model.addAttribute("users", users);
		model.addAttribute("feelingsList", feelingsList);
		model.addAttribute("feelingsHistory", feelingsHistory);

		return "edit";
	}

	@RequestMapping(value = "/rename/user")
	public String renameUser(@Validated User user, BindingResult result, Model model, @RequestParam("userId") int userId, @RequestParam("year") int year, @RequestParam("month") int month) {
		if(result.hasErrors()) {
			return editUser(user, model, userId, year, month);
		}
		userManager.updateUser(user);
		
		// カレンダー取得
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		int lastDay = calendar.getActualMaximum(Calendar.DATE);
		model.addAttribute("dispYear", year);
		model.addAttribute("dispMonth", month);
		model.addAttribute("lastDay", lastDay);

		List<User> users = userManager.getAllUsers();
		List<Feelings> feelingsList = feelingsManager.getList();
		List<Feelings> feelingsHistory = feelingsManager.getAllFeelings();
		model.addAttribute("users", users);
		model.addAttribute("feelingsList", feelingsList);
		model.addAttribute("feelingsHistory", feelingsHistory);

		return "redirect:/";
	}

	@RequestMapping(value = "/delete/user")
	public String deleteUser(Model model, @RequestParam("userId") int userId, @RequestParam("year") int year, @RequestParam("month") int month) {

		userManager.deleteUser(userId);

		// カレンダー取得
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		int lastDay = calendar.getActualMaximum(Calendar.DATE);
		model.addAttribute("dispYear", year);
		model.addAttribute("dispMonth", month);
		model.addAttribute("lastDay", lastDay);

		List<User> users = userManager.getAllUsers();
		List<Feelings> feelingsList = feelingsManager.getList();
		List<Feelings> feelingsHistory = feelingsManager.getAllFeelings();
		model.addAttribute("users", users);
		model.addAttribute("feelingsList", feelingsList);
		model.addAttribute("feelingsHistory", feelingsHistory);

		return "redirect:/";
	}
	
	@RequestMapping(value = "/select")
	public String selectFeelings(@ModelAttribute("feelings") Feelings feelings, Model model, @RequestParam("userId") int userId, @RequestParam("year") int year, @RequestParam("month") int month, @RequestParam("day") int day) {

		// カレンダー取得
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		int lastDay = calendar.getActualMaximum(Calendar.DATE);
		model.addAttribute("dispYear", year);
		model.addAttribute("dispMonth", month);
		model.addAttribute("lastDay", lastDay);

		List<User> users = userManager.getAllUsers();
		List<Feelings> feelingsList = feelingsManager.getList();
		List<Feelings> feelingsHistory = feelingsManager.getAllFeelings();
		model.addAttribute("users", users);
		model.addAttribute("feelingsList", feelingsList);
		model.addAttribute("feelingsHistory", feelingsHistory);

		Feelings selectedFeelings = feelingsManager.findFeelings(userId, year, month, day);
		int selectedFeelingsId = 0;
		if(selectedFeelings != null) {
			selectedFeelingsId = selectedFeelings.getFeelingsId();
		}
		model.addAttribute("selectedFeelingsId",selectedFeelingsId);

		return "select";
	}
	
	@RequestMapping(value = "/register/feelings")
	public String registerFeelings(Feelings feelings, Model model, @RequestParam("userId") int userId, @RequestParam("year") int year, @RequestParam("month") int month, @RequestParam("day") int day) {

		// カレンダー取得
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		int lastDay = calendar.getActualMaximum(Calendar.DATE);
		model.addAttribute("dispYear", year);
		model.addAttribute("dispMonth", month);
		model.addAttribute("lastDay", lastDay);

		feelingsManager.registerFeelings(feelings);		

		List<User> users = userManager.getAllUsers();
		List<Feelings> feelingsList = feelingsManager.getList();
		List<Feelings> feelingsHistory = feelingsManager.getAllFeelings();
		model.addAttribute("users", users);
		model.addAttribute("feelingsList", feelingsList);
		model.addAttribute("feelingsHistory", feelingsHistory);

		return "redirect:/";
	}

	@RequestMapping(value = "/delete/feelings")
	public String deleteFeelings(Model model, @RequestParam("feelingsId") int feelingsId, @RequestParam("year") int year, @RequestParam("month") int month) {

		// カレンダー取得
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		int lastDay = calendar.getActualMaximum(Calendar.DATE);
		model.addAttribute("dispYear", year);
		model.addAttribute("dispMonth", month);
		model.addAttribute("lastDay", lastDay);
		
		feelingsManager.deleteFeelings(feelingsId);
		
		List<User> users = userManager.getAllUsers();
		List<Feelings> feelingsList = feelingsManager.getList();
		List<Feelings> feelingsHistory = feelingsManager.getAllFeelings();
		model.addAttribute("users", users);
		model.addAttribute("feelingsList", feelingsList);
		model.addAttribute("feelingsHistory", feelingsHistory);

		return "redirect:/";
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

		List<User> users = userManager.getAllUsers();
		List<Feelings> feelingsList = feelingsManager.getList();
		List<Feelings> feelingsHistory = feelingsManager.getAllFeelings();
		model.addAttribute("users", users);
		model.addAttribute("feelingsList", feelingsList);
		model.addAttribute("feelingsHistory", feelingsHistory);

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

		List<User> users = userManager.getAllUsers();
		List<Feelings> feelingsList = feelingsManager.getList();
		List<Feelings> feelingsHistory = feelingsManager.getAllFeelings();
		model.addAttribute("users", users);
		model.addAttribute("feelingsList", feelingsList);
		model.addAttribute("feelingsHistory", feelingsHistory);

		return "niconico";
	}
}