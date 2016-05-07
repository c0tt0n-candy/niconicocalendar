package com.niconicocalendar.controller;

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
import com.niconicocalendar.model.getCalendar;

@Controller
public class IndexController {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	UserManager userManager;
	@Autowired
	FeelingsManager feelingsManager;

	@ModelAttribute
	void setModels(Model model) {
		List<User> users = userManager.getAllUsers();
		List<Feelings> feelingsList = feelingsManager.getList();

		model.addAttribute("users", users);
		model.addAttribute("feelingsList", feelingsList);
	}

	@RequestMapping(value = "/")
	public String index(Model model) {
		int dispYear = getCalendar.getDispYear();
		int dispMonth = getCalendar.getDispMonth();
		int lastDay = getCalendar.getLastDay(dispYear, dispMonth);
		List<Feelings> feelingsHistory = feelingsManager.getAllThisPeriodFeelings(dispYear, dispMonth);

		model.addAttribute("dispYear", dispYear);
		model.addAttribute("dispMonth", dispMonth);
		model.addAttribute("lastDay", lastDay);
		model.addAttribute("feelingsHistory", feelingsHistory);

		return "niconico";
	}

	@RequestMapping(value = "/register")
	public String register(@ModelAttribute("user") User user, Model model, @RequestParam("year") int year, @RequestParam("month") int month) {
		int lastDay = getCalendar.getLastDay(year, month);
		List<Feelings> feelingsHistory = feelingsManager.getAllThisPeriodFeelings(year, month);

		model.addAttribute("lastDay", lastDay);
		model.addAttribute("feelingsHistory", feelingsHistory);

		return "regist";
	}

	@RequestMapping(value = "/register/user")
	public String registerUser(@Validated User user, BindingResult result, Model model, @RequestParam("year") int year, @RequestParam("month") int month) {
		if(result.hasErrors()) {
			return register(user, model, year, month);
		}
		userManager.registerUser(user);

		int lastDay = getCalendar.getLastDay(year, month);
		List<Feelings> feelingsHistory = feelingsManager.getAllThisPeriodFeelings(year, month);

		model.addAttribute("dispYear", year);
		model.addAttribute("dispMonth", month);
		model.addAttribute("lastDay", lastDay);
		model.addAttribute("feelingsHistory", feelingsHistory);

		return "redirect:/";
	}

	@RequestMapping(value = "/edit/user")
	public String editUser(@ModelAttribute("user") User user, Model model, @RequestParam("userId") int userId, @RequestParam("year") int year, @RequestParam("month") int month) {
		int lastDay = getCalendar.getLastDay(year, month);
		List<Feelings> feelingsHistory = feelingsManager.getAllThisPeriodFeelings(year, month);

		model.addAttribute("lastDay", lastDay);
		model.addAttribute("feelingsHistory", feelingsHistory);

		User theUser = userManager.getOneUser(userId);
		BeanUtils.copyProperties(theUser, user);

		return "edit";
	}

	@RequestMapping(value = "/rename/user")
	public String renameUser(@Validated User user, BindingResult result, Model model, @RequestParam("userId") int userId, @RequestParam("year") int year, @RequestParam("month") int month) {
		if(result.hasErrors()) {
			return editUser(user, model, userId, year, month);
		}
		userManager.updateUser(user);

		int lastDay = getCalendar.getLastDay(year, month);
		List<Feelings> feelingsHistory = feelingsManager.getAllThisPeriodFeelings(year, month);

		model.addAttribute("dispYear", year);
		model.addAttribute("dispMonth", month);
		model.addAttribute("lastDay", lastDay);
		model.addAttribute("feelingsHistory", feelingsHistory);

		return "redirect:/";
	}

	@RequestMapping(value = "/delete/user")
	public String deleteUser(Model model, @RequestParam("userId") int userId, @RequestParam("year") int year, @RequestParam("month") int month) {
		userManager.deleteUser(userId);

		int lastDay = getCalendar.getLastDay(year, month);
		List<Feelings> feelingsHistory = feelingsManager.getAllThisPeriodFeelings(year, month);

		model.addAttribute("dispYear", year);
		model.addAttribute("dispMonth", month);
		model.addAttribute("lastDay", lastDay);
		model.addAttribute("feelingsHistory", feelingsHistory);

		return "redirect:/";
	}

	@RequestMapping(value = "/select")
	public String selectFeelings(@ModelAttribute("feelings") Feelings feelings, Model model, @RequestParam("userId") int userId, @RequestParam("year") int year, @RequestParam("month") int month, @RequestParam("day") int day) {
		int lastDay = getCalendar.getLastDay(year, month);
		List<Feelings> feelingsHistory = feelingsManager.getAllThisPeriodFeelings(year, month);

		model.addAttribute("lastDay", lastDay);
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
	public String registerFeelings(Feelings feelings, Model model, @RequestParam("year") int year, @RequestParam("month") int month) {
		feelingsManager.registerFeelings(feelings);

		int lastDay = getCalendar.getLastDay(year, month);
		List<Feelings> feelingsHistory = feelingsManager.getAllThisPeriodFeelings(year, month);

		model.addAttribute("dispYear", year);
		model.addAttribute("dispMonth", month);
		model.addAttribute("lastDay", lastDay);
		model.addAttribute("feelingsHistory", feelingsHistory);

		return "redirect:/";
	}

	@RequestMapping(value = "/delete/feelings")
	public String deleteFeelings(Model model, @RequestParam("feelingsId") int feelingsId, @RequestParam("year") int year, @RequestParam("month") int month) {
		int lastDay = getCalendar.getLastDay(year, month);
		List<Feelings> feelingsHistory = feelingsManager.getAllThisPeriodFeelings(year, month);

		model.addAttribute("dispYear", year);
		model.addAttribute("dispMonth", month);
		model.addAttribute("lastDay", lastDay);
		model.addAttribute("feelingsHistory", feelingsHistory);
		feelingsManager.deleteFeelings(feelingsId);

		return "redirect:/";
	}

	@RequestMapping(value = "/previous")
	public String getPrevious(Model model, @RequestParam("year") int year, @RequestParam("month") int month) {
		if(month == 0){
			year -= 1;
			month = 12;
		}
		int lastDay = getCalendar.getLastDay(year, month);

		model.addAttribute("dispYear", year);
		model.addAttribute("dispMonth", month);
		model.addAttribute("lastDay", lastDay);

		return "redirect:/";
	}

	@RequestMapping(value = "/next")
	public String getNext(Model model, @RequestParam("year") int year, @RequestParam("month") int month) {
		if(month == 13){
			year += 1;
			month = 1;
		}
		int lastDay = getCalendar.getLastDay(year, month);

		model.addAttribute("dispYear", year);
		model.addAttribute("dispMonth", month);
		model.addAttribute("lastDay", lastDay);

		return "redirect:/";
	}
}