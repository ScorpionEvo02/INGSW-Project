package org.vesuviano.dieti_estates.dao.impl;


public class SessioneClienteDAOImpl extends SessioneDAOImpl {
	
	private static final String recoverCodiceCliente = "SELECT cod_cliente FROM sessione_cliente WHERE id_sessione = crypt(?, id_sessione);";
	private static final String insertSessioneCliente = "INSERT INTO sessione_cliente (cod_cliente, id_sessione) VALUES (?, crypt(?, gen_salt('md5')));";
	
	public int getCodice(String sessioneId) throws Exception 
	{ 
		return getCodice(recoverCodiceCliente, sessioneId); 
	} 
	
	public void insertSessione(int codCliente, String sessioneId) throws Exception 
	{ 
		insertSessione(insertSessioneCliente, codCliente, sessioneId); 
	}
}
