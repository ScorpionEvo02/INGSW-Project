package org.vesuviano.dieti_estates.dto;

public class OffertaImmobile {
	
	OffertaDTO offerta;
	ImmobileDTO immobile;
	
	public OffertaImmobile() { }

	public OffertaDTO getOfferta() {
		return offerta;
	}

	public void setOfferta(OffertaDTO offerta) {
		this.offerta = offerta;
	}

	public ImmobileDTO getImmobile() {
		return immobile;
	}

	public void setImmobile(ImmobileDTO immobile) {
		this.immobile = immobile;
	}
	
	
	
}
