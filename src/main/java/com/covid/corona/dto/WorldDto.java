package com.covid.corona.dto;

import lombok.Data;

@Data
public class WorldDto {
	private String country;
	private String confirmed;
	private String active;
	private String recovered;
	private String deceased;
}
