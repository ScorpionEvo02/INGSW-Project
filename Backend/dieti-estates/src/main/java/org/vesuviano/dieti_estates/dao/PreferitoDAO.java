package org.vesuviano.dieti_estates.dao;

import java.util.ArrayList;

import org.vesuviano.dieti_estates.dto.ImmobileDTO;

public interface PreferitoDAO 
{ 
	public ArrayList<ImmobileDTO> getAllPreferitoCliente(int codCliente) throws Exception;
	
	public void insertPreferito(int codCliente, int id_immobile) throws Exception;
	public void removePreferito(int codCliente, int id_immobile) throws Exception;

}
