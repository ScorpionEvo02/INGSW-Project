package org.vesuviano.dieti_estates.dao;

import java.util.ArrayList;

import org.vesuviano.dieti_estates.dto.PrenotazioneDTO;

public interface PrenotazioneDAO
{
		public ArrayList<PrenotazioneDTO> getAllPrenotazioneAgente(int codAgente) throws Exception;
		public ArrayList<PrenotazioneDTO> getAllPrenotazioneCliente(int codCliente) throws Exception;
		
		public int acceptPrenotazioneByAgente(int codAgente, int codPrenotazione) throws Exception;
		public int refusePrenotazioneByAgente(int codAgente, int codPrenotazione) throws Exception;
		
		public void insertPrenotazioneByCliente(PrenotazioneDTO prenotazione) throws Exception;
		public void removePrenotazioneByCliente(int codCliente, int codPrenotazione) throws Exception;
		
}
