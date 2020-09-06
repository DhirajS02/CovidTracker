package com.covid.corona.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalDto implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 805174245304755434L;
private String confirmed;
private String active;
private String recovered;
private String deceased;
@Override
public String toString()
{
	return("Confirmed cases="+this.confirmed+" Active Cases="+this.active+" Recovered Cases="+this.recovered+" Deceased Cases="+this.deceased);
}
}
