package com.covid.corona.service;

import java.util.List;
import java.util.Map;
import com.covid.corona.dto.StateDto;
import com.covid.corona.exception.CoronaServiceException;
public interface CoronaService {
	//public String showTotalInfectedByDistrict(String district_name);
	//public TotalDto showTotalInfected();
	public List<StateDto> showTotalInfectedState() throws CoronaServiceException;
	public Map<String, Map<String, String>> zones();
	public String addEmailForUpdates(String email);
	public String deleteEmailForUpdates(String email);
	public List<String> getEmailForUpdates();
	
}
