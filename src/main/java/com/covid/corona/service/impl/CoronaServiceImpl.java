package com.covid.corona.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.covid.corona.dto.EmailMsg;
import com.covid.corona.dto.StateDto;
import com.covid.corona.dto.TotalDto;
import com.covid.corona.exception.CoronaServiceException;
import com.covid.corona.exception.DataUnreachableException;
import com.covid.corona.exception.JmsSenderException;
import com.covid.corona.service.CoronaService;
import com.covid.corona.util.JmsSender;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CoronaServiceImpl implements CoronaService {
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	JmsSender jmsSender;
	TotalDto totalDtoTemporary = new TotalDto();
	TotalDto totalDto;
	List<String> emailForUpdates=new ArrayList<String>();
	JSONObject coronaDetailsTotalBefore26ThApril;
	JSONObject recoveredDeceasedBefore26ThApril;
	JSONObject coronaDetailsTotalAfter26ThApril;
	JSONObject coronaDetailsTotalAfter9thMay;
	JSONObject coronaDetailsTotalAfter23rdMay;
	JSONObject coronaDetailsTotalAfter4thJun;
	JSONObject coronaDetailsTotalAfter19thJun;
	JSONObject coronaDetailsTotalAfter30thJun;
	JSONObject coronaDetailsTotalAfter7thJuly;
	JSONObject coronaDetailsTotalAfter17thJuly;
	JSONObject coronaDetailsTotalAfter22ndJuly;
	JSONObject coronaDetailsTotalAfter6thAug;
	JSONObject coronaDetailsTotalAfter13thJuly;
	JSONObject coronaDetailsTotalAfter21stAug;
	JSONObject coronaDetailsTotalAfter21stAugTemporary;




	int deceasedBefore26ThApril = 0;
	int recoveredBefore26ThApril = 0;
	int confirmedBefore26ThApril = 0;
	int hospitalizedBefore26ThApril = 0;
	int deceasedAfter26ThApril = 0;
	int recoveredAfter26ThApril = 0;
	int confirmedAfter26ThApril = 0;
	int hospitalizedAfter26ThApril = 0;
	/*int deceasedAfter30thJun = 0;
	int recoveredAfter30thJun = 0;
	int confirmedAfter30thJun = 0;
	int hospitalizedAfter30thJun = 0;*/
	int deceasedAfter21stAug = 0;
	int recoveredAfter21stAug= 0;
	int confirmedAfter21stAug = 0;
	int hospitalizedAfter21stAug = 0;


	@Value("${com.covid.corona.coronaservice.raw_data}")
	private String raw_data;
	@Value("${com.covid.corona.coronaservice.deaths_recoveries}")
	private String deaths_recoveries;
	@Value("${com.covid.corona.coronaservice.raw_data3}")
	private String raw_data3;
	@Value("${com.covid.corona.coronaservice.raw_data4}")
	private String raw_data4;
	@Value("${com.covid.corona.coronaservice.raw_data5}")
	private String raw_data5;
	@Value("${com.covid.corona.coronaservice.raw_data6}")
	private String raw_data6;
	@Value("${com.covid.corona.coronaservice.raw_data7}")
	private String raw_data7;
	@Value("${com.covid.corona.coronaservice.raw_data8}")
	private String raw_data8;
	@Value("${com.covid.corona.coronaservice.raw_data9}")
	private String raw_data9;
	@Value("${com.covid.corona.coronaservice.raw_data10}")
	private String raw_data10;
	@Value("${com.covid.corona.coronaservice.raw_data11}")
	private String raw_data11;
	@Value("${com.covid.corona.coronaservice.raw_data12}")
	private String raw_data12;
	@Value("${com.covid.corona.coronaservice.raw_data13}")
	private String raw_data13;
	@Value("${com.covid.corona.coronaservice.raw_data14}")
	private String raw_data14;

	

	@PostConstruct
	private void loadData() throws DataUnreachableException, CoronaServiceException{
		totalDto = new TotalDto();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		try
		{
		coronaDetailsTotalBefore26ThApril = restTemplate.exchange(raw_data, HttpMethod.GET, entity, JSONObject.class)
				.getBody();
		recoveredDeceasedBefore26ThApril = restTemplate
				.exchange(deaths_recoveries, HttpMethod.GET, entity, JSONObject.class).getBody();
		coronaDetailsTotalAfter26ThApril = restTemplate.exchange(raw_data3, HttpMethod.GET, entity, JSONObject.class)
				.getBody();
		coronaDetailsTotalAfter9thMay = restTemplate.exchange(raw_data4, HttpMethod.GET, entity, JSONObject.class)
				.getBody();
		coronaDetailsTotalAfter23rdMay = restTemplate.exchange(raw_data5, HttpMethod.GET, entity, JSONObject.class)
				.getBody();
		coronaDetailsTotalAfter4thJun = restTemplate.exchange(raw_data6, HttpMethod.GET, entity, JSONObject.class)
				.getBody();
		coronaDetailsTotalAfter19thJun = restTemplate.exchange(raw_data7, HttpMethod.GET, entity, JSONObject.class)
				.getBody();
		coronaDetailsTotalAfter30thJun = restTemplate.exchange(raw_data8, HttpMethod.GET, entity, JSONObject.class)
				.getBody();
		coronaDetailsTotalAfter7thJuly=restTemplate.exchange(raw_data9, HttpMethod.GET, entity, JSONObject.class)
				.getBody();
		coronaDetailsTotalAfter13thJuly=restTemplate.exchange(raw_data10, HttpMethod.GET, entity, JSONObject.class)
		        .getBody();
		coronaDetailsTotalAfter17thJuly=restTemplate.exchange(raw_data11, HttpMethod.GET, entity, JSONObject.class)
				.getBody();
		coronaDetailsTotalAfter22ndJuly=restTemplate.exchange(raw_data12, HttpMethod.GET, entity, JSONObject.class)
				.getBody();
		coronaDetailsTotalAfter6thAug=restTemplate.exchange(raw_data13, HttpMethod.GET, entity, JSONObject.class)
				.getBody();
		coronaDetailsTotalAfter21stAug=restTemplate.exchange(raw_data14, HttpMethod.GET, entity, JSONObject.class)
				.getBody();
		}
		catch(RestClientException e0 )
		{
			log.error("Data can not be found for covidIndia");
			throw new DataUnreachableException("Data can not be found for covidIndia");
		}
		
		
       try
       {
		List objCoronaDetailsTotal = (List) coronaDetailsTotalBefore26ThApril.get("raw_data");
		List objRecoveredDeceased = (List) recoveredDeceasedBefore26ThApril.get("deaths_recoveries");
		List objCoronaDetailsTotalAfter26ThApril = (List) coronaDetailsTotalAfter26ThApril.get("raw_data");
		List objcoronaDetailsTotalAfter9thMay = (List) coronaDetailsTotalAfter9thMay.get("raw_data");
		List objcoronaDetailsTotalAfter23rdMay = (List) coronaDetailsTotalAfter23rdMay.get("raw_data");
		List objcoronaDetailsTotalAfter4thJun = (List) coronaDetailsTotalAfter4thJun.get("raw_data");
		List objcoronaDetailsTotalAfter19thJun = (List) coronaDetailsTotalAfter19thJun.get("raw_data");
		List objcoronaDetailsTotalAfter30thJun = (List) coronaDetailsTotalAfter30thJun.get("raw_data");
		List objcoronaDetailsTotalAfter7thJuly = (List) coronaDetailsTotalAfter7thJuly.get("raw_data");
		List objcoronaDetailsTotalAfter13thJuly = (List) coronaDetailsTotalAfter13thJuly.get("raw_data");
		List objcoronaDetailsTotalAfter17thJuly = (List) coronaDetailsTotalAfter17thJuly.get("raw_data");
		List objcoronaDetailsTotalAfter22ndJuly = (List) coronaDetailsTotalAfter22ndJuly.get("raw_data");
		List objcoronaDetailsTotalAfter6thAug = (List) coronaDetailsTotalAfter6thAug.get("raw_data");
		List objcoronaDetailsTotalAfter21stAug = (List) coronaDetailsTotalAfter21stAug.get("raw_data");


		
		
		List objAll = new ArrayList<List>();
		objAll.add(objCoronaDetailsTotalAfter26ThApril);
		objAll.add(objcoronaDetailsTotalAfter9thMay);
		objAll.add(objcoronaDetailsTotalAfter23rdMay);
		objAll.add(objcoronaDetailsTotalAfter4thJun);
		objAll.add(objcoronaDetailsTotalAfter19thJun);
		objAll.add(objcoronaDetailsTotalAfter30thJun);
		objAll.add(objcoronaDetailsTotalAfter7thJuly);
		objAll.add(objcoronaDetailsTotalAfter13thJuly);
		objAll.add(objcoronaDetailsTotalAfter17thJuly);
		objAll.add(objcoronaDetailsTotalAfter22ndJuly);
		objAll.add(objcoronaDetailsTotalAfter6thAug);




		for (Object obj1 : objRecoveredDeceased) {
			HashMap hs = (HashMap) obj1;
			String deceasedRecovered = (String) hs.get("patientstatus");
			if (deceasedRecovered.equalsIgnoreCase("deceased")) {
				deceasedBefore26ThApril++;
			} else if (deceasedRecovered.equalsIgnoreCase("recovered")) {
				recoveredBefore26ThApril++;
			}

		}
		confirmedBefore26ThApril = objCoronaDetailsTotal.size();
		for (Object all : objAll) {

			for (Object obj1 : (List) all) {
				HashMap hs = (HashMap) obj1;
				int deceased = 0;
				int recovered = 0;
				int hospitalised = 0;
				String deceasedHospitalizedRecovered = (String) hs.get("currentstatus");
				if (deceasedHospitalizedRecovered.equalsIgnoreCase("deceased")) {
					String numcases = (String) hs.get("numcases");
					if (!(numcases == null || numcases.isEmpty())) {
						deceased = Integer.parseInt((String) hs.get("numcases"));
						deceasedAfter26ThApril = deceasedAfter26ThApril + deceased;
					}
				}

				else if (deceasedHospitalizedRecovered.equalsIgnoreCase("recovered")) {
					String numcases = (String) hs.get("numcases");
					if (!(numcases == null || numcases.isEmpty())) {
						recovered = Integer.parseInt((String) hs.get("numcases"));
						recoveredAfter26ThApril = recoveredAfter26ThApril + recovered;
					}
				} else if (deceasedHospitalizedRecovered.equalsIgnoreCase("hospitalized")) {
					String numcases = (String) hs.get("numcases");
					if (!(numcases == null || numcases.isEmpty())) {
						hospitalised = Integer.parseInt((String) hs.get("numcases"));
						hospitalizedAfter26ThApril = hospitalizedAfter26ThApril + hospitalised;
					}
				}
			}
			
		}
		for (Object obj1 : objcoronaDetailsTotalAfter21stAug) {
			HashMap hs = (HashMap) obj1;
			int deceased = 0;
			int recovered = 0;
			int hospitalised = 0;
			String deceasedHospitalizedRecovered = (String) hs.get("currentstatus");
			// System.out.println("befor if");
			if (deceasedHospitalizedRecovered.equalsIgnoreCase("deceased")) {
				String numcases = (String) hs.get("numcases");
				if (!(numcases == null || numcases.isEmpty())) {
					deceased = Integer.parseInt((String) hs.get("numcases"));
					deceasedAfter21stAug = deceasedAfter21stAug + deceased;
					
				}
			}

			else if (deceasedHospitalizedRecovered.equalsIgnoreCase("recovered")) {
				String numcases = (String) hs.get("numcases");
				if (!(numcases == null || numcases.isEmpty())) {
					recovered = Integer.parseInt((String) hs.get("numcases"));
					recoveredAfter21stAug = recoveredAfter21stAug + recovered;
				}
			} else if (deceasedHospitalizedRecovered.equalsIgnoreCase("hospitalized")) {
				String numcases = (String) hs.get("numcases");
				if (!(numcases == null || numcases.isEmpty())) {
					hospitalised = Integer.parseInt((String) hs.get("numcases"));
					hospitalizedAfter21stAug = hospitalizedAfter21stAug+ hospitalised;
				}
			}
		}
		
       }
       catch(Exception e)
       {
    	   throw new CoronaServiceException("Error Fetching data for covid in India");
       }

		totalDto.setActive(String.valueOf(confirmedBefore26ThApril + hospitalizedAfter26ThApril
				+ hospitalizedAfter21stAug - deceasedBefore26ThApril - recoveredBefore26ThApril - recoveredAfter26ThApril
				- deceasedAfter26ThApril - recoveredAfter21stAug - deceasedAfter21stAug));
		totalDto.setConfirmed(
				String.valueOf(confirmedBefore26ThApril + hospitalizedAfter26ThApril + hospitalizedAfter21stAug));
		totalDto.setDeceased(String.valueOf(deceasedAfter26ThApril + deceasedBefore26ThApril + deceasedAfter21stAug));
		totalDto.setRecovered(
				String.valueOf(recoveredAfter26ThApril + recoveredBefore26ThApril + recoveredAfter21stAug));
		totalDtoTemporary = totalDto;
		
		coronaDetailsTotalAfter21stAugTemporary = coronaDetailsTotalAfter21stAug;

	}
	

	@Scheduled(fixedRate = 100000)
	private void loadRawDataAfter21stAug() throws CoronaServiceException {
		int deceasedAfter21stAugScheduled = 0;
		int recoveredAfter21stAugScheduled = 0;
		int hospitalizedAfter21stAugScheduled = 0;
		int emptyStatus = 0;
		try
		{
		List objcoronaDetailsTotalAfter21stAug = (List) coronaDetailsTotalAfter21stAug.get("raw_data");
		for (Object obj1 : objcoronaDetailsTotalAfter21stAug) {

			HashMap hs = (HashMap) obj1;
			int deceased = 0;
			int recovered = 0;
			int hospitalised = 0;
			String deceasedHospitalizedRecovered = (String) hs.get("currentstatus");
			if (deceasedHospitalizedRecovered.equalsIgnoreCase("deceased")) {
				String numcases = (String) hs.get("numcases");
				if (!(numcases == null || numcases.isEmpty())) {
					deceased = Integer.parseInt((String) hs.get("numcases"));
					deceasedAfter21stAugScheduled = deceasedAfter21stAugScheduled + deceased;
				}
			} else if (deceasedHospitalizedRecovered.equalsIgnoreCase("recovered")) {
				String numcases = (String) hs.get("numcases");
				if (!(numcases == null || numcases.isEmpty())) {
					recovered = Integer.parseInt((String) hs.get("numcases"));
					recoveredAfter21stAugScheduled = recoveredAfter21stAugScheduled + recovered;
				}
			} else if (deceasedHospitalizedRecovered.equalsIgnoreCase("hospitalized")) {
				String numcases = (String) hs.get("numcases");
				if (!(numcases == null || numcases.isEmpty())) {
					hospitalised = Integer.parseInt((String) hs.get("numcases"));
					hospitalizedAfter21stAugScheduled = hospitalizedAfter21stAugScheduled + hospitalised;
				}
			}

		}
		
		}
		 catch(Exception e)
	       {
	    	   throw new CoronaServiceException("Error Fetching data for covid in India");
	       }
		if ((confirmedBefore26ThApril + hospitalizedAfter26ThApril + hospitalizedAfter21stAug - deceasedBefore26ThApril
				- recoveredBefore26ThApril - recoveredAfter26ThApril - deceasedAfter26ThApril - deceasedAfter21stAug
				- recoveredAfter21stAug) < (confirmedBefore26ThApril + hospitalizedAfter26ThApril
						+ hospitalizedAfter21stAugScheduled - deceasedBefore26ThApril - recoveredBefore26ThApril
						- recoveredAfter21stAugScheduled - deceasedAfter21stAugScheduled)) {
			
		}

			totalDto.setActive(String.valueOf((confirmedBefore26ThApril + hospitalizedAfter26ThApril
					+ hospitalizedAfter21stAug- deceasedBefore26ThApril - recoveredBefore26ThApril
					- recoveredAfter26ThApril - deceasedAfter26ThApril - deceasedAfter21stAug - recoveredAfter21stAug)
					+ (confirmedBefore26ThApril + hospitalizedAfter26ThApril + hospitalizedAfter21stAug
							- deceasedBefore26ThApril - recoveredBefore26ThApril - recoveredAfter26ThApril
							- deceasedAfter26ThApril - deceasedAfter21stAug- recoveredAfter21stAug)
					- (confirmedBefore26ThApril + hospitalizedAfter26ThApril + hospitalizedAfter21stAugScheduled
							- deceasedBefore26ThApril - recoveredBefore26ThApril - recoveredAfter21stAugScheduled
							- deceasedAfter21stAugScheduled - recoveredAfter26ThApril - deceasedAfter26ThApril)));
		
		if (hospitalizedAfter21stAug < hospitalizedAfter21stAugScheduled) {
			totalDto.setConfirmed(String
					.valueOf(confirmedBefore26ThApril + hospitalizedAfter26ThApril + hospitalizedAfter21stAugScheduled));

		}
		// totalDto.setConfirmed(String.valueOf(Integer.parseInt(totalDtoTemporary.getConfirmed())+hospitalizedAfter26ThApril));
		if (deceasedAfter21stAug< deceasedAfter21stAugScheduled) {
			totalDto.setDeceased(
					(String.valueOf(deceasedBefore26ThApril + deceasedAfter26ThApril + deceasedAfter21stAugScheduled)));

		}
		// totalDto.setDeceased(String.valueOf(Integer.parseInt(totalDtoTemporary.getDeceased())+deceasedAfter26ThApril));
		if (recoveredAfter21stAug < recoveredAfter21stAugScheduled) {
			totalDto.setRecovered((String
					.valueOf(recoveredBefore26ThApril + recoveredAfter26ThApril + recoveredAfter21stAugScheduled)));

		}
		// totalDto.setRecovered(String.valueOf(Integer.parseInt(totalDtoTemporary.getDeceased())+recoveredAfter26ThApril));
		totalDtoTemporary = totalDto;
		hospitalizedAfter21stAug = hospitalizedAfter21stAugScheduled;
		deceasedAfter21stAug= deceasedAfter21stAugScheduled;
		recoveredAfter21stAug= recoveredAfter21stAugScheduled;
		coronaDetailsTotalAfter21stAugTemporary = coronaDetailsTotalAfter21stAug;
	}

	public TotalDto getTotalDtoTemporary() {
		return totalDtoTemporary;
	}

	public void setTotalDtoTemporary(TotalDto totalDtoTemporary) {
		this.totalDtoTemporary = totalDtoTemporary;
	}

	public TotalDto getTotalDto() {
		return totalDto;
	}

	public void setTotalDto(TotalDto totalDto) {
		this.totalDto = totalDto;
	}


	public List<StateDto> showTotalInfectedState() throws CoronaServiceException{
		System.out.println("inside showtotalstate");
		Map<String, Integer> showTotalInfectedByState = new HashMap<String, Integer>();
		Map<String, Integer> showTotalRecoveredByState = new HashMap<String, Integer>();
		Map<String, Integer> showTotalDeceasedByState = new HashMap<String, Integer>();
        try
        {
		List obj = (List) coronaDetailsTotalBefore26ThApril.get("raw_data");
		List objRecoveredDeceased = (List) recoveredDeceasedBefore26ThApril.get("deaths_recoveries");
		List objCoronaDetailsTotalAfter26ThApril = (List) coronaDetailsTotalAfter26ThApril.get("raw_data");
		List objcoronaDetailsTotalAfter9thMay = (List) coronaDetailsTotalAfter9thMay.get("raw_data");
		List objcoronaDetailsTotalAfter23rdMay = (List) coronaDetailsTotalAfter23rdMay.get("raw_data");
		List objcoronaDetailsTotalAfter4thJun = (List) coronaDetailsTotalAfter4thJun.get("raw_data");
		List objcoronaDetailsTotalAfter19thJun = (List) coronaDetailsTotalAfter19thJun.get("raw_data");
		List objcoronaDetailsTotalAfter30thJun = (List) coronaDetailsTotalAfter30thJun.get("raw_data");
		List objcoronaDetailsTotalAfter7thJuly = (List) coronaDetailsTotalAfter7thJuly.get("raw_data");
		List objcoronaDetailsTotalAfter13thJuly = (List) coronaDetailsTotalAfter13thJuly.get("raw_data");
		List objcoronaDetailsTotalAfter17thJuly = (List) coronaDetailsTotalAfter17thJuly.get("raw_data");
		List objcoronaDetailsTotalAfter22ndJuly = (List) coronaDetailsTotalAfter22ndJuly.get("raw_data");
		List objcoronaDetailsTotalAfter6thAug = (List) coronaDetailsTotalAfter6thAug.get("raw_data");
		List objcoronaDetailsTotalAfter21stAug = (List) coronaDetailsTotalAfter21stAug.get("raw_data");


		List objAll = new ArrayList<List>();
		objAll.add(objCoronaDetailsTotalAfter26ThApril);
		objAll.add(objcoronaDetailsTotalAfter9thMay);
		objAll.add(objcoronaDetailsTotalAfter23rdMay);
		objAll.add(objcoronaDetailsTotalAfter4thJun);
		objAll.add(objcoronaDetailsTotalAfter19thJun);
		objAll.add(objcoronaDetailsTotalAfter30thJun);
		objAll.add(objcoronaDetailsTotalAfter7thJuly);
		objAll.add(objcoronaDetailsTotalAfter13thJuly);
		objAll.add(objcoronaDetailsTotalAfter17thJuly);
		objAll.add(objcoronaDetailsTotalAfter22ndJuly);
		objAll.add(objcoronaDetailsTotalAfter6thAug);
		objAll.add(objcoronaDetailsTotalAfter21stAug);




		// System.out.println(objRecoveredDeceased);
		for (Object obj1 : obj) {
			int count = 0;
			HashMap hs = (HashMap) obj1;
			String detectedstate = (String) hs.get("detectedstate");
			// System.out.println("detected state is "+detectedstate);
			if (!showTotalInfectedByState.containsKey(detectedstate)) {
				showTotalInfectedByState.put(detectedstate, 1);

			} else {
				showTotalInfectedByState.put(detectedstate, showTotalInfectedByState.get(detectedstate) + 1);

			}

		}
		System.out.println("after confirrmed" + showTotalInfectedByState);
		for (Object obj1 : objRecoveredDeceased) {
			HashMap hs = (HashMap) obj1;
			String state = (String) hs.get("state");
			String deceasedRecovered = (String) hs.get("patientstatus");
			if (deceasedRecovered.equalsIgnoreCase("recovered")) {
				if (!showTotalRecoveredByState.containsKey(state) && deceasedRecovered.equalsIgnoreCase("recovered")) {
					showTotalRecoveredByState.put(state, 1);

				} else {
					showTotalRecoveredByState.put(state, showTotalRecoveredByState.get(state) + 1);

				}
			} else {
				if (!showTotalDeceasedByState.containsKey(state)) {
					showTotalDeceasedByState.put(state, 1);

				} else {
					showTotalDeceasedByState.put(state, showTotalDeceasedByState.get(state) + 1);

				}
			}

		}
		
		for (Object all : objAll) {
			for (Object obj1 : (List) all) {

				HashMap hs = (HashMap) obj1;
				int deceased = 0;
				int recovered = 0;
				int hospitalised = 0;
				String deceasedHospitalizedRecovered = (String) hs.get("currentstatus");
				String state = (String) hs.get("detectedstate");

				if (deceasedHospitalizedRecovered.equalsIgnoreCase("deceased")) {
					String numcases = (String) hs.get("numcases");
					if (!(numcases == null || numcases.isEmpty())) {
						deceased = Integer.parseInt((String) hs.get("numcases"));
						if (!showTotalDeceasedByState.containsKey(state)) {
							showTotalDeceasedByState.put(state, 1);

						} else {
							showTotalDeceasedByState.put(state, showTotalDeceasedByState.get(state) + deceased);

						}
					}
				} else if (deceasedHospitalizedRecovered.equalsIgnoreCase("recovered")) {
					String numcases = (String) hs.get("numcases");
					if (!(numcases == null || numcases.isEmpty())) {
						recovered = Integer.parseInt((String) hs.get("numcases"));
						if (!showTotalRecoveredByState.containsKey(state)
								&& deceasedHospitalizedRecovered.equalsIgnoreCase("recovered")) {
							showTotalRecoveredByState.put(state, 1);

						} else {
							showTotalRecoveredByState.put(state, showTotalRecoveredByState.get(state) + recovered);

						}
					}
				} else if (deceasedHospitalizedRecovered.equalsIgnoreCase("hospitalized")) {
					String numcases = (String) hs.get("numcases");
					if (!(numcases == null || numcases.isEmpty())) {
						hospitalised = Integer.parseInt((String) hs.get("numcases"));
						if (!showTotalInfectedByState.containsKey(state)) {
							showTotalInfectedByState.put(state, 1);

						} else {
							showTotalInfectedByState.put(state, showTotalInfectedByState.get(state) + hospitalised);

						}
					}
				}
			}
			
		}
        }
        catch(Exception e)
        {
           log.error("Error Fetching data for covid around Indian states");
     	   throw new CoronaServiceException("Error Fetching data for covid around Indian states");
        }

		
		List listStateDto = new ArrayList<StateDto>();
		for (String key : showTotalInfectedByState.keySet()) {
			int recovered = 0;
			int deceased = 0;
			int active = 0;
			StateDto stateDto = new StateDto();
			stateDto.setConfirmed(showTotalInfectedByState.get(key).toString());
			if (showTotalRecoveredByState.containsKey(key)) {
				recovered = showTotalRecoveredByState.get(key);

				stateDto.setRecovered(Integer.valueOf(recovered).toString());
			} else {
				stateDto.setRecovered(Integer.valueOf(recovered).toString());

			}
			if (showTotalDeceasedByState.containsKey(key)) {
				deceased = showTotalDeceasedByState.get(key);
				stateDto.setDeceased(Integer.valueOf(deceased).toString());
			} else {
				stateDto.setDeceased(Integer.valueOf(deceased).toString());

			}
			active = showTotalInfectedByState.get(key) - recovered - deceased;
			stateDto.setActive(Integer.valueOf(active).toString());
			stateDto.setState(key);
			listStateDto.add(stateDto);

		}

		return listStateDto;
	}

	public Map<String, Map<String, String>> zones() {
		HashMap<String, Map<String, String>> zones = new HashMap<String, Map<String, String>>();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		JSONObject coronaDetailszones = restTemplate
				.exchange("https://api.covid19india.org/zones.json", HttpMethod.GET, entity, JSONObject.class)
				.getBody();
		List objcoronaDetailszones = (List) coronaDetailszones.get("zones");
		for (Object obj1 : objcoronaDetailszones) {
			HashMap hs = (HashMap) obj1;
			String state = (String) hs.get("state");
			String district = (String) hs.get("district");
			String zone = (String) hs.get("zone");
			HashMap<String, String> districtZone = new HashMap<String, String>();
			if (!zones.containsKey(state)) {
				districtZone.put(district, zone);
				zones.put(state, districtZone);

			} else {
				zones.get(state).put(district, zone);

			}

		}
		return zones;
	}


	@Override
	public String addEmailForUpdates(String email) {
		emailForUpdates.add(email);
		return "success";
	}
	@Scheduled(cron = "* * 6 * * *")
	private void sendEmailForUpdateEmail() {
		EmailMsg emailMsg = new EmailMsg();
		emailMsg.setListOfEmails(emailForUpdates);//totalDtoTemporary
		emailMsg.setTotalDto(totalDtoTemporary);
		try {
			jmsSender.sendToQueue(emailMsg);
		} catch (JmsSenderException jmse) {
			log.error("Error occured during call to jms sender", jmse);
		}
	}

	@Override
	public String deleteEmailForUpdates(String email) {
		emailForUpdates.remove(email);
		return "success";
	}

	@Override
	public List<String> getEmailForUpdates() {
		// TODO Auto-generated method stub
		return emailForUpdates;
	}

}
