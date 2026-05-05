package org.vesuviano.dieti_estates.dao.impl;


public class SessioneAmministratoreDAOImpl extends SessioneDAOImpl {
	
	private static final String recoverCodiceAmministratore = "SELECT cod_amministratore FROM sessione_amministratore WHERE id_sessione = crypt(?, id_sessione);"; 
	private static final String insertSessioneAmministratore = "INSERT INTO sessione_amministratore (cod_amministratore, id_sessione) VALUES (?, crypt(?, gen_salt('md5')));"; 
	
	public int getCodice(String sessioneId) throws Exception 
	{ 
		return getCodice(recoverCodiceAmministratore, sessioneId); 
	} 
	
	public void insertSessione(int codAmministratore, String sessioneId) throws Exception 
	{ 
		insertSessione(insertSessioneAmministratore, codAmministratore, sessioneId); 
	}
}