package com.covid.corona.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.covid.corona.dto.TotalDto;
import com.covid.corona.dto.WorldDto;
import com.covid.corona.exception.CoronaServiceException;
import com.covid.corona.exception.CoronaWorldServiceException;
import com.covid.corona.exception.DataUnreachableException;
import com.covid.corona.service.CoronaWorldService;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class CoronaWorldServiceImpl implements CoronaWorldService {
	@Autowired
	RestTemplate restTemplate;
	//HashMap<String,HashMap<String,String>> coronaDetailsTotalWorld;
	JSONObject coronaDetailsTotalWorld;
	@Getter
	TotalDto totalWorldDtoTemporary;
	@Getter
	List<WorldDto> listWorldDtotemporary;
    @Scheduled(fixedRate = 100000)
	@PostConstruct
	public void showTotalInfected() throws DataUnreachableException, CoronaWorldServiceException {
		TotalDto totalDto=new TotalDto();
        List<WorldDto> listWorldDto=new ArrayList<WorldDto>();
		int totaldeceased=0; 
		int totalrecovered=0;
		int totalconfirmed=0;
		int totalactive=0;
		
		 HttpHeaders headers = new HttpHeaders();
	     headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	     HttpEntity <String> entity = new HttpEntity<String>(headers);
	     try
	     {
	     coronaDetailsTotalWorld= restTemplate.exchange("https://covid2019-api.herokuapp.com/current", HttpMethod.GET, entity, JSONObject.class).getBody();
	     }
	     catch(RestClientException e0 )
			{
				log.error("Worldcovid data could not be found");
				throw new DataUnreachableException("Worldcovid data could not be found");
			}
	     try
	     {
	     Set<String> objcoronaDetailsTotalWorld=(Set<String>)((HashMap<String,HashMap<String,Integer>>)coronaDetailsTotalWorld).keySet();
         System.out.println("size is"+objcoronaDetailsTotalWorld.size());
	    for(String obj:objcoronaDetailsTotalWorld)
         {   
	    	if(obj.equalsIgnoreCase("ts")||obj.equalsIgnoreCase("dt"))
         {
        	 continue;
         } 
	    	System.out.println(obj);
	    	
        	  WorldDto worldDto=new WorldDto();
        	 // System.out.println("key is obj"+obj+"class is"+coronaDetailsTotalWorld.get(obj).getClass().toString());
        	  int confirmed=((HashMap<String,Integer>)coronaDetailsTotalWorld.get(obj)).get("confirmed");
        	  int recovered=((HashMap<String,Integer>)coronaDetailsTotalWorld.get(obj)).get("recovered");
        	  int deceased=((HashMap<String,Integer>)coronaDetailsTotalWorld.get(obj)).get("deaths");
        	  if(obj.equalsIgnoreCase("US"))
  	    	{        	  System.out.println("coutry is "+obj+" cofirmed is"+confirmed+" recovered is"+recovered+" deceased is"+deceased);

  	    		
  	    	}
              String active=String.valueOf(confirmed-deceased-recovered);
        	  worldDto.setCountry(obj);
        	  worldDto.setConfirmed(String.valueOf(confirmed));
        	  worldDto.setActive(active);
        	  worldDto.setRecovered(String.valueOf(recovered));
        	  worldDto.setDeceased(String.valueOf(deceased));
        	  listWorldDto.add(worldDto);
        	  //System.out.println("coutry is "+obj+" cofirmed is"+confirmed+" recovered is"+recovered+" deceased is"+deceased);
        	  totaldeceased=totaldeceased+deceased;
        	  totalrecovered=totalrecovered+recovered;
        	  totalconfirmed=totalconfirmed+confirmed;
        	  totalactive=totalactive+Integer.parseInt(active);
        	 // System.out.println("total cofirmed is"+totalconfirmed+" totalrecovered is"+totalrecovered+" totaldeceased is"+totaldeceased);
         }
	     }
	     catch(Exception e)
	        {
			   log.error("Error Fetching data for covid aroud the world");
	     	   throw new CoronaWorldServiceException("Error Fetching data for covid aroud the world");
	        }
	    System.out.println("after for"); 
	     totalDto.setActive(String.valueOf(totalactive));
	     totalDto.setConfirmed(String.valueOf(totalconfirmed));
	     totalDto.setDeceased(String.valueOf(totaldeceased));
	     totalDto.setRecovered(String.valueOf(totalrecovered));
	     List<WorldDto> sortedlistWorldDto=listWorldDto.stream().sorted((i1,i2)->-(Integer.valueOf(i1.getConfirmed()).compareTo(Integer.valueOf(i2.getConfirmed())))).collect(Collectors.toList());
	     sortedlistWorldDto.stream().forEach(s->System.out.println(s.getCountry()+s.getConfirmed()));
	     listWorldDtotemporary=sortedlistWorldDto;
	     totalWorldDtoTemporary=totalDto;
	     //listWorldDtotemporary.stream().forEach(s->System.out.println("country is"+s.getCountry()+" deceaased is"+s.getDeceased()+" active is"+s.getActive()+" recovered is"+s.getRecovered()));
	     
	}
	
}
