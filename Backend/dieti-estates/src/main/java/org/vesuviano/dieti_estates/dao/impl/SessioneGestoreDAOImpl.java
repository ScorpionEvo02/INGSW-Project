package org.vesuviano.dieti_estates.dao.impl;


public class SessioneGestoreDAOImpl extends SessioneDAOImpl{
	
	private static final String queryRecoverGestore = "SELECT cod_gestore FROM sessione_gestore WHERE id_sessione = crypt(?, id_sessione);";
	private static final String insertSessioneGestore = "INSERT INTO sessione_gestore (cod_gestore, id_sessione) VALUES (?, crypt(?, gen_salt('md5')));";
	
	public int getCodice(String sessioneId) throws Exception 
	{ 
		return getCodice(queryRecoverGestore, sessioneId); 
	} 
	
	public void insertSessione(int codCliente, String sessioneId) throws Exception 
	{ 
		insertSessione(insertSessioneGestore, codCliente, sessioneId); 
	}
}
