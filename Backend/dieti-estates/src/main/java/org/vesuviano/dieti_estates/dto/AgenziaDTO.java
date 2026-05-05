package org.vesuviano.dieti_estates.dto;

public class AgenziaDTO {

	int codAgenzia;
	String nome;
	String descrizione;
	IndirizzoDTO indirizzo;
	String pIva;
	String email;
	int telefono;
	String logo;
	

	public AgenziaDTO(int codAgenzia, String nome, String descrizione, IndirizzoDTO indirizzo,
			String pIva, String email,int telefono) {
		
		super();
		this.codAgenzia = codAgenzia;
		this.nome = nome;
		this.descrizione = descrizione;
		this.indirizzo = indirizzo;
		this.pIva = pIva;
		this.email = email;
		this.telefono = telefono;
		
	}
  
	public AgenziaDTO() {
		
	}

	public int getCodAgenzia() {
		return codAgenzia;
	}
	public void setCodAgenzia(int codAgenzia) {
		this.codAgenzia = codAgenzia;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public IndirizzoDTO getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(IndirizzoDTO indirizzo) {
		this.indirizzo = indirizzo;
	}
	
	public String getpIva() {
		return pIva;
	}
	public void setpIva(String pIva) {
		this.pIva = pIva;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getTelefono() {
		return telefono;
	}
	
	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	
}
