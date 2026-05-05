package org.vesuviano.dieti_estates.dao;

import java.util.ArrayList;

import org.vesuviano.dieti_estates.dto.NotificaDTO;

public interface NotificaDAO {
	
	public ArrayList<NotificaDTO> getAllNotificheAgente(int codAgente) throws Exception;
	public ArrayList<NotificaDTO> getAllNotificheCliente(int codCliente) throws Exception;
	
	public void insertNotifica(NotificaDTO notifica) throws Exception;
	public void removeNotifica(int codNotifica) throws Exception; 
	
}
