package org.vesuviano.dieti_estates.dto;

import java.sql.Timestamp;

public class PrenotazioneDTO {
	
	int codPrenotazione;
	String statoPrenotazione; 
	String luogo;
	String citta;
	Timestamp orarioPrenotazione; 
	int codCliente;
	int codImmobile;
	int codAgente;
	
	public int getCodPrenotazione() {
		return codPrenotazione;
	}
	public void setCodPrenotazione(int codPrenotazione) {
		this.codPrenotazione = codPrenotazione;
	}
	
	public String getStatoPrenotazione() {
		return statoPrenotazione;
	}
	public void setStatoPrenotazione(String statPrenotazione) {
		this.statoPrenotazione = statPrenotazione;
	}
	
	public String getLuogo() {
		return luogo;
	}
	public void setLuogo(String luog) {
		this.luogo = luog;
	}
	
	public String getCitta() {
		return citta;
	}
	public void setCitta(String cit) {
		this.citta = cit;
	}
	
	public Timestamp getOrarioPrenotazione() {
		return orarioPrenotazione;
	}
	public void setOrarioPrenotazione(Timestamp orarioPrenotazione) {
		this.orarioPrenotazione = orarioPrenotazione;
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
	
	public int getCodAgente() {
		return codAgente;
	}
	public void setCodAgente(int codAgente) {
		this.codAgente = codAgente;
	}
	
}

