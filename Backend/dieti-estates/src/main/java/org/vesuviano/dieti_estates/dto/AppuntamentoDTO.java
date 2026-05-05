package org.vesuviano.dieti_estates.dto;

public class AppuntamentoDTO {
	PrenotazioneDTO prenotazione;
	ImmobileDTO immobile;
	AgenteImmobiliareDTO agente;
	
	public AppuntamentoDTO() {
		//Empty constructor
	}
	
	public PrenotazioneDTO getPrenotazione() {
		return prenotazione;
	}
	public void setPrenotazione(PrenotazioneDTO prenotazione) {
		this.prenotazione = prenotazione;
	}
	public ImmobileDTO getImmobile() {
		return immobile;
	}
	public void setImmobile(ImmobileDTO immobile) {
		this.immobile = immobile;
	}
	public AgenteImmobiliareDTO getAgente() {
		return agente;
	}
	public void setAgente(AgenteImmobiliareDTO agente) {
		this.agente = agente;
	}
	
	
}
