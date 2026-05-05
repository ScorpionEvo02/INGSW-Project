package org.vesuviano.dieti_estates.dto;

import java.sql.Date;

public class NotificaDTO {
	int cod_notifica;
	String tipo;
	Date data;
	int cod_offerta;
	int cod_prenotazione;
	
	public int getCod_notifica() {
		return cod_notifica;
	}
	public void setCod_notifica(int cod_notifica) {
		this.cod_notifica = cod_notifica;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public int getCod_offerta() {
		return cod_offerta;
	}
	public void setCod_offerta(int cod_offerta) {
		this.cod_offerta = cod_offerta;
	}
	public int getCod_prenotazione() {
		return cod_prenotazione;
	}
	public void setCod_prenotazione(int cod_prenotazione) {
		this.cod_prenotazione = cod_prenotazione;
	}
	
}
