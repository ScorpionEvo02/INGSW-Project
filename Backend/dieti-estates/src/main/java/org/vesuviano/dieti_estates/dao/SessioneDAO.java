package org.vesuviano.dieti_estates.dao;

public interface SessioneDAO {

	public int getCodice(String sessioneId) throws Exception;
	public void insertSessione(int codAgente, String sessioneId) throws Exception;
	
}