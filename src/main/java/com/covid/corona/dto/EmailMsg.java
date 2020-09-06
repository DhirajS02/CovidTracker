package com.covid.corona.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
@Data
public class EmailMsg implements Serializable{
 /**
	 * 
	 */
	private static final long serialVersionUID = -5011414001393992395L;
private List<String> listOfEmails;
 private TotalDto totalDto;
}
