package org.vesuviano.dieti_estates.dto;

public class IndirizzoDTO {
	
	String via;
	int civico;
	String citta;
	String comune;
	int cap;
	
	public IndirizzoDTO(String via, int civico, String citta, int cap) {
		super();
		
		this.via = via;
		this.civico = civico;
		this.citta = citta;
		this.cap = cap;
	}
	
	public IndirizzoDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getVia() {
		return via;
	}
	
	public void setVia(String via) {
		this.via = via;
	}

	public int getCivico() {
		return civico;
	}
	
	public void setCivico(int i) {
		this.civico = i;
	}
	
	public String getCitta() {
		return citta;
	}
	
	public void setCitta(String citta) {
		this.citta = citta;
	}
	
	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public int getCAP() {
		return cap;
	}
	
	public void setCAP(int cap) {
		this.cap = cap;
	}

}

