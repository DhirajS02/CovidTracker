package com.covid.corona.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.covid.corona.dto.StateDto;
import com.covid.corona.dto.WorldDto;
import com.covid.corona.service.CoronaWorldService;
import com.covid.corona.service.impl.CoronaWorldServiceImpl;
@RequestMapping("/getWorldValues")
@Controller
public class CoronaWorldController {
	@Autowired
	CoronaWorldServiceImpl coronaWorldService;
	@GetMapping(value="/corona")
	public String showHome(Model model) {
		System.out.println("controller");
	//	model.addAttribute("appName", appName);
		model.addAttribute("countrycases",coronaWorldService.getListWorldDtotemporary());
		model.addAttribute("totalCases",coronaWorldService.getTotalWorldDtoTemporary());
        return "worlddata";
	}
	
}
