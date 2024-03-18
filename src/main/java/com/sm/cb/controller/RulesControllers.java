package com.sm.cb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sm.cb.controller.service.RuleService;

@RestController
public class RulesControllers {
	
	private final RuleService ruleService;
	@Autowired
    public RulesControllers(RuleService ruleService) {
        this.ruleService = ruleService;
    }
	
	@PostMapping
	public String validate(@RequestBody String data) {
		
		String resp=ruleService.validateData(data);
		
		return resp;
		
	}

}
