package com.covid.corona.web;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.covid.corona.dto.StateDto;
import com.covid.corona.dto.TotalDto;
import com.covid.corona.exception.CoronaServiceException;
import com.covid.corona.service.CoronaService;
import com.covid.corona.service.impl.CoronaServiceImpl;

@Controller
public class CoronaController {

	@Autowired
	CoronaServiceImpl coronaService;
	@Value("${spring.application.name}")
	String appName;

	@GetMapping(value = "/")
	// @RequestMapping(method=RequestMethod.GET,value="/player/create")
	public String showHome(Model model) {
		System.out.println("controller");
		model.addAttribute("appName", appName);
		model.addAttribute("totalDto", coronaService.getTotalDtoTemporary());
		System.out.println("aftercontroller");
		System.out.println(coronaService.getTotalDtoTemporary().getConfirmed());
		return "home";
	}

	@GetMapping(value = "/total/corona/state")
	// @RequestMapping(method=RequestMethod.GET,value="/player/create")
	public ResponseEntity<List<StateDto>> showTotalInfectedState() throws CoronaServiceException {
		System.out.println("inside_coroastatecontroller");
		return new ResponseEntity<List<StateDto>>(coronaService.showTotalInfectedState(), HttpStatus.OK);
	}

	@GetMapping("/analytics")
	public String analytics() {
		return "analytics";
	}

	@GetMapping("/worldanalytics")
	public String worldanalytics() {
		return "worldanalytics";
	}

	@GetMapping("/zones")
	public ResponseEntity<Map<String, Map<String, String>>> zones() {
		return new ResponseEntity<Map<String, Map<String, String>>>(coronaService.zones(), HttpStatus.OK);
	}

	@PostMapping("/getUpdates/{email}")
	public ResponseEntity<String> addEmailForUpdates(@PathVariable String email) {
		return new ResponseEntity<String>(coronaService.addEmailForUpdates(email), HttpStatus.OK);
	}

	@PostMapping("/unsubscribe/{email}")
	public ResponseEntity<String> deleteEmailForUpdates(@PathVariable String email) {
		System.out.println("delete called");
		return new ResponseEntity<String>(coronaService.deleteEmailForUpdates(email), HttpStatus.OK);
	}

	@GetMapping("/getsuscriedemails")
	public ResponseEntity<List<String>> getEmailForUpdates() {
		return new ResponseEntity<List<String>>(coronaService.getEmailForUpdates(), HttpStatus.OK);
	}
}
