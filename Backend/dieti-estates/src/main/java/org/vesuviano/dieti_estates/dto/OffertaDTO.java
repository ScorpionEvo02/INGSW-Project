package org.vesuviano.dieti_estates.dto;

public class OffertaDTO {

	int codOfferta;
	double ammontare;
	String statoOfferta;
	int codCliente;
	int codImmobile;
	
	public int getCodOfferta() {
		return codOfferta;
	}
	public void setCodOfferta(int codOfferta) {
		this.codOfferta = codOfferta;
	}
	
	public double getAmmontare() {
		return ammontare;
	}
	public void setAmmontare(double ammontare) {
		this.ammontare = ammontare;
	}
	
	public String getStatoOfferta() {
		return statoOfferta;
	}
	public void setStatoOfferta(String statoOfferta) {
		this.statoOfferta = statoOfferta;
	}

	public int getCodCliente() {
		return codCliente;
	}
	public void setCodCliente(int codCliente) {
		this.codCliente = codCliente;
	}
	
	public int getCodImmobile() {
		return codImmobile;
	}
	public void setCodImmobile(int codImmobile) {
		this.codImmobile = codImmobile;
	}
	
}

