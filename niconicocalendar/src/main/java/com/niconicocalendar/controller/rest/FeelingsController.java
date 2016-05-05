package com.niconicocalendar.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.niconicocalendar.Feelings;
import com.niconicocalendar.model.FeelingsManager;

@RestController
@RequestMapping("rest/feelings")
public class FeelingsController {
	@Autowired
	FeelingsManager feelingsManager;
	
	// FeelingsList取得
	@RequestMapping(value = "list", method = RequestMethod.GET)
	List<Feelings> getList() {
		List<Feelings> feelingsList = feelingsManager.getList();
		return feelingsList;
	}
	
	// Feelings全件取得
	@RequestMapping(method = RequestMethod.GET)
	List<Feelings> getAll() {
		List<Feelings> feelings = feelingsManager.getAllFeelings();
		return feelings;
	}
	
	// Feelings1件取得
	@RequestMapping(value = "{feelingsId}", method = RequestMethod.GET)
	Feelings getOne(@PathVariable Integer feelingsId) {
		Feelings feelings = feelingsManager.getOneFeelings(feelingsId);
		return feelings;
	}
	
	// Feelings新規作成
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	void register(@RequestBody Feelings feelings){
		feelingsManager.registerFeelings(feelings);
	}
	
	// Feelings1件削除	
	@RequestMapping(value = "{feelingsId}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void delete(@PathVariable Integer feelingsId) {
		feelingsManager.deleteFeelings(feelingsId);
	}
	
}