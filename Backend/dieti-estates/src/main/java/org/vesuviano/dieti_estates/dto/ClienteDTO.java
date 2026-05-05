package org.vesuviano.dieti_estates.dto;

import java.sql.Date;

public class ClienteDTO extends UtenteDTO {
    
	int codCliente;

	public ClienteDTO() 
	{
		
	}
	
	public ClienteDTO(int codCliente, String nome, String cognome, String codiceFiscale, Date dataNascita,
		     String genere, IndirizzoDTO indirizzo, String email, String password, String idSessione){
		super(nome, cognome, indirizzo, dataNascita, codiceFiscale, email, password, genere, idSessione);
		// TODO Auto-generated constructor stub

	}
	
	public int getCodCliente() {
		return codCliente;
	}
	public void setCodCliente(int codCliente) {
		this.codCliente = codCliente;
	}
	
}

