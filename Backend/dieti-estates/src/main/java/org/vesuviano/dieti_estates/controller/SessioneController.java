package org.vesuviano.dieti_estates.controller;

import java.util.UUID;

import org.vesuviano.dieti_estates.dao.SessioneDAO;
import org.vesuviano.dieti_estates.dao.impl.SessioneAgenteDAOImpl;
import org.vesuviano.dieti_estates.dao.impl.SessioneAmministratoreDAOImpl;
import org.vesuviano.dieti_estates.dao.impl.SessioneClienteDAOImpl;
import org.vesuviano.dieti_estates.dao.impl.SessioneGestoreDAOImpl;

public class SessioneController {
	
	private static SessioneDAO sessioneAgenteDAO = new SessioneAgenteDAOImpl();
	private static SessioneDAO sessioneAmministratoreDAO = new SessioneAmministratoreDAOImpl();
	private static SessioneDAO sessioneClienteDAO = new SessioneClienteDAOImpl();
	private static SessioneDAO sessioneGestoreDAO = new SessioneGestoreDAOImpl();
	
	private SessioneController() {
		
	}
	
	public static int getAgenteId(String sessioneId) throws Exception 
	{
		try 
		{
			int agenteId = sessioneAgenteDAO.getCodice(sessioneId);
			
			if(agenteId == 0)
				throw new Error();
				
			return agenteId;
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	public static String createSessioneAgente(int codAgente) throws Exception 
	{
		try 
		{
			String sessioneId = getRandomKey();
			sessioneAgenteDAO.insertSessione(codAgente, sessioneId);
			return sessioneId;
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	
	public static int getAmministratoreId(String sessioneId) throws Exception 
	{
		try 
		{
			int amministratoreId = sessioneAmministratoreDAO.getCodice(sessioneId);
			
			if(amministratoreId == 0)
				throw new Error();
				
			return amministratoreId;
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	public static String createSessioneAmministratore(int codAmministratore) throws Exception 
	{
		try 
		{
			String sessioneId = getRandomKey();
			sessioneAmministratoreDAO.insertSessione(codAmministratore, sessioneId);
			return sessioneId;
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	
	public static int getClienteId(String sessioneId) throws Exception {
		try 
		{
			int clienteId = sessioneClienteDAO.getCodice(sessioneId);
			
			if(clienteId == 0)
				throw new Error();
				
			return clienteId;
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	public static String createSessioneCliente(int codCliente) throws Exception 
	{
		try 
		{
			String sessioneId = getRandomKey();
			sessioneClienteDAO.insertSessione(codCliente, sessioneId);
			return sessioneId;
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	
	public static int getGestoreId(String sessioneId) throws Exception 
	{
		try 
		{
			int gestoreId = sessioneGestoreDAO.getCodice(sessioneId);
			
			if(gestoreId == 0)
				throw new Error();
				
			return gestoreId;
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	public static String createSessioneGestore(int codGestore) throws Exception 
	{
		try 
		{
			String sessioneId = getRandomKey();
			sessioneGestoreDAO.insertSessione(codGestore, sessioneId);
			return sessioneId;
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	private static String getRandomKey() 
	{
		return UUID.randomUUID().toString();
	}
}
