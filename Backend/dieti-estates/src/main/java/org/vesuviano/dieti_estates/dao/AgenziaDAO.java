package org.vesuviano.dieti_estates.dao;

import java.util.ArrayList;

import org.vesuviano.dieti_estates.dto.AgenziaDTO;

public interface AgenziaDAO {

	public ArrayList<AgenziaDTO> getAllAgenzie() throws Exception;
	public AgenziaDTO getAgenziaById(int codAgenzia) throws Exception;
	
	public void insertAgenzia(AgenziaDTO agenzia) throws Exception;
	public void removeAgenzia(int codAgenzia) throws Exception; 
	public void updateAgenzia(AgenziaDTO agenzia) throws Exception;
	
}
