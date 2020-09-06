package com.covid.corona.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StateDto {
	private String state;
	private String confirmed;
	private String active;
	private String recovered;
	private String deceased;
}
