package org.vesuviano.dieti_estates.dao;

import java.util.ArrayList;

import org.vesuviano.dieti_estates.dto.OffertaDTO;

public interface OffertaDAO {
	
	public ArrayList<OffertaDTO> getAllOfferAgent(int codAgente) throws Exception;
	 public ArrayList<OffertaDTO> getAllOfferClient(int codCliente) throws Exception;

	public void insertOffer(OffertaDTO offerta) throws Exception;
	public int acceptOffer(int codOfferta) throws Exception;
	public void modifyOffer(OffertaDTO offerta) throws Exception;
	public int rejectOffer(int codOfferta) throws Exception;
	public void deleteOffer(int codOfferta) throws Exception;
	
}
