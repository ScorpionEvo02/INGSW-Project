package org.vesuviano.dieti_estates.dao;

import java.util.ArrayList;

import org.vesuviano.dieti_estates.dto.ImmobileDTO;

public interface CronologiaDAO {
	
	public ArrayList<ImmobileDTO> getAllCronologiaCliente(int codCliente) throws Exception;
	public void insertCronologiaCliente(int codCliente, int immobile) throws Exception;
	
}
