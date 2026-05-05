package org.vesuviano.dieti_estates.dto;

import java.sql.Date;

public class AgenteImmobiliareDTO extends UtenteDTO {

	int codAgente; 
	int codGestore;
	int codAgenzia;
	
	public AgenteImmobiliareDTO(int codAgente, String nome, String cognome, String codiceFiscale, Date dataNascita,
		     String genere, IndirizzoDTO indirizzo, String email, String password,int codGestore, int codAgenzia, String idSessione){
		super(nome, cognome, indirizzo, dataNascita, codiceFiscale, email, password, genere, idSessione);
		// TODO Auto-generated constructor stub
	}
	
	public AgenteImmobiliareDTO() {
		
	}
	
	public int getCodAgente() {
		return codAgente;
	}
	public void setCodAgente(int codAgente) {
		this.codAgente = codAgente;
	}
	
	public int getCodGestore() {
		return codGestore;
	}
	public void setCodGestore(int codGestore) {
		this.codGestore = codGestore;
	}

	public int getCodAgenzia() {
		return codAgenzia;
	}
	public void setCodAgenzia(int codAgenzia) {
		this.codAgenzia = codAgenzia;
	}
	
	
}
