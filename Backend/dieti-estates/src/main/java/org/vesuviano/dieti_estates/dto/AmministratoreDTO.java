
package org.vesuviano.dieti_estates.dto;

import java.sql.Date;

public class AmministratoreDTO extends UtenteDTO {

	int codAgenzia;
	int codAmministratore;
	int codAmministratoreInsert;
	String partitaIva;

	public AmministratoreDTO(int codAmministratore, String nome, String cognome, String codiceFiscale, Date dataNascita,
          String genere, IndirizzoDTO indirizzo, String partitaIva, String email, String password, int codAmministratoreInsert, String idSessione) {
	super(nome, cognome, indirizzo, dataNascita, codiceFiscale, email, password, genere, idSessione);
		// TODO Auto-generated constructor stub
	}
	
	public AmministratoreDTO() {
		
	}
	
	public int getCodAgenzia() {
		return codAgenzia;
	}

	public void setCodAgenzia(int codAgenzia) {
		this.codAgenzia = codAgenzia;
	}

	public int getCodAmministratore() {
		return codAmministratore;
	}
	public void setCodAmministratore(int codAmministratore) {
		this.codAmministratore = codAmministratore;
	}
	
	public int getCodAmministratoreInsert() {
		return codAmministratoreInsert;
	}
	public void setCodAmministratoreInsert(int codAmministratoreInsert) {
		this.codAmministratoreInsert = codAmministratoreInsert;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}
    
}
