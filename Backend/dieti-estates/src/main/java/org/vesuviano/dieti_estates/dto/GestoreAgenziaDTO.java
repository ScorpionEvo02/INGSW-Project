package org.vesuviano.dieti_estates.dto;

import java.sql.Date;

public class GestoreAgenziaDTO extends UtenteDTO {

	int codGestore;
	int codAmministratore;
	int codAgenzia;

    public GestoreAgenziaDTO() {
    	
    }

	public GestoreAgenziaDTO(String nome, String cognome, IndirizzoDTO indirizzo, Date dataNascita,
			String codiceFiscale, String email, String password, String genere, String idSessione, int codGestore,
			int codAmministratore, int codAgenzia) {
		super(nome, cognome, indirizzo, dataNascita, codiceFiscale, email, password, genere, idSessione);
		this.codGestore = codGestore;
		this.codAmministratore = codAmministratore;
		this.codAgenzia = codAgenzia;
	}

    
	public int getCodGestore() {
		return codGestore;
	}
	public void setCodGestore(int codGestore) {
		this.codGestore = codGestore;
	}
	
	public int getCodAmministratore() {
		return codAmministratore;
	}
	public void setCodAmministratore(int codAmministratore) {
		this.codAmministratore = codAmministratore;
	}

	public int getCodAgenzia() {
		return codAgenzia;
	}
	public void setCodAgenzia(int codAgenzia) {
		this.codAgenzia = codAgenzia;
	}
    
}
