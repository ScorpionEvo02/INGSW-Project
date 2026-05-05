package org.vesuviano.dieti_estates.dao;

import java.util.ArrayList;

import org.vesuviano.dieti_estates.dto.ImmagineImmobileDTO;

public interface ImmagineImmobileDAO {
	
	public ArrayList<ImmagineImmobileDTO> getImmaginiImmobile(int cod_immobile) throws Exception;
	
	public void insertImmagineImmobile(ImmagineImmobileDTO immagineImmobile) throws Exception;
	public void removeImmagineImmobile(int codImmagineImmobile) throws Exception; 

}
