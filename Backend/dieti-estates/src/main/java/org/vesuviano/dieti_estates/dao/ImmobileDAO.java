package org.vesuviano.dieti_estates.dao;

import org.vesuviano.dieti_estates.dto.CoordinataDTO;
import org.vesuviano.dieti_estates.dto.ImmobileDTO;

import java.util.ArrayList;

public interface ImmobileDAO {
	
	public ArrayList<ImmobileDTO> getImmobiliByCoordinate(CoordinataDTO coordinate, String tipo, int raggio) throws Exception;
	public ArrayList<ImmobileDTO> getImmobileByCittà(String città) throws Exception;
	public ImmobileDTO getImmobileById(int cod_immobile) throws Exception;
	public ArrayList<ImmobileDTO> getImmobiliAgente(int codAgente) throws Exception; 
	public ArrayList<ImmobileDTO> getImmobileByVariousParameters(String parametro1, String parametro2) throws Exception; 

	public void insertImmobile(ImmobileDTO immobile) throws Exception;
	public void insertImmobile2(ImmobileDTO immobile) throws Exception;
	public void updateImmobile(ImmobileDTO immobile) throws Exception;
	public void removeImmobile(int immobile) throws Exception;

}
