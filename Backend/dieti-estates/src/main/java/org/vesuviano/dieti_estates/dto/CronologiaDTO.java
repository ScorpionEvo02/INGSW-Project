package org.vesuviano.dieti_estates.dto;

import java.sql.Date;

public class CronologiaDTO {
	
	int codCliente;
	int codImmobile;
	Date data;
	
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
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
}
