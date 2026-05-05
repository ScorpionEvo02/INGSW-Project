package org.vesuviano.dieti_estates.dao.impl;


public class SessioneAgenteDAOImpl extends SessioneDAOImpl {
	
	String recoverCodiceAgente = "SELECT cod_agente FROM sessione_agente WHERE id_sessione = crypt(?, id_sessione);";
	String insertSessioneAgente = "INSERT INTO sessione_agente (cod_agente, id_sessione) VALUES (?, crypt(?, gen_salt('md5')));";
	
	public int getCodice(String sessioneId) throws Exception 
	{ 
		return getCodice(recoverCodiceAgente, sessioneId); 
	} 
	
	public void insertSessione(int codAgente, String sessioneId) throws Exception 
	{ 
		insertSessione(insertSessioneAgente, codAgente, sessioneId); 
	}
}
