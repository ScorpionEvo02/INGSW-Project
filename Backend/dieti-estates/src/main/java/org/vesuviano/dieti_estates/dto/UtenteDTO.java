package org.vesuviano.dieti_estates.dto;

import java.sql.Date;

public abstract class UtenteDTO{
	
	String nome;
	String cognome;
	IndirizzoDTO indirizzo;
	Date dataNascita;
	String codiceFiscale;
	String email;
	String password;
	String genere;
	String idSessione;
	
	public UtenteDTO(String nome, String cognome, IndirizzoDTO indirizzo, Date dataNascita, String codiceFiscale, String email, String password, String genere, String idSessione) {
	
		this.nome = nome;
		this.cognome = cognome;
		this.indirizzo = indirizzo;
		this.dataNascita = dataNascita;
		this.codiceFiscale = codiceFiscale;
		this.email = email;
		this.password = password;
		this.genere = genere;
		this.idSessione = idSessione;
		
	}
	
	public UtenteDTO() {
		
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	public IndirizzoDTO getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(IndirizzoDTO indirizzo) {
		this.indirizzo = indirizzo;
	}
	public Date getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGenere() {
		return genere;
	}
	public void setGenere(String genere) {
		this.genere = genere;
	}
	
	public String getIdSessione() {
		return idSessione;
	}
	public void setIdSessione(String idSessione) {
		this.idSessione = idSessione;
	}
	
}
