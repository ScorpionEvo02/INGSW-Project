package org.vesuviano.dieti_estates.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.vesuviano.dieti_estates.connection.ConnectionPostgreSQLDB;
import org.vesuviano.dieti_estates.dao.NotificaDAO;
import org.vesuviano.dieti_estates.dto.NotificaDTO;

public class NotificaDAOImpl implements NotificaDAO{

	@Override
	public ArrayList<NotificaDTO> getAllNotificheAgente(int codAgente) throws Exception
	{
		String query = "SELECT n.cod_notifica, n.tipo, n.momento_notifica, n.cod_offerta, n.cod_prenotazione FROM notifica AS n JOIN prenotazione AS p ON n.cod_prenotazione = p.cod_prenotazione WHERE p.cod_agente = ?;";
        ArrayList<NotificaDTO> notifiche = new ArrayList<>();
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try 
        {
        	ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
        	ps.setInt(1, codAgente);
            
            try
            {
            	rs = ps.executeQuery();
            	
                while (rs.next()) 
                {       	
                	notifiche.add(mapResultToNotifica(rs));
                }
            }
            catch(Exception e) 
            {
            	throw e;
            }
        } 
        catch (Exception e) 
        {
        	throw e;
        }
        finally 
		{
			ConnectionPostgreSQLDB.closeStatement(ps);
			ConnectionPostgreSQLDB.closeResultSet(rs);
		}
        return notifiche;
	}

	@Override
	public ArrayList<NotificaDTO> getAllNotificheCliente(int codCliente) throws Exception
	{
		String query = "SELECT n.cod_notifica, n.tipo, n.momento_notifica, n.cod_offerta, n.cod_prenotazione FROM notifica AS n JOIN prenotazione AS p ON n.cod_prenotazione = p.cod_prenotazione WHERE p.cod_cliente = ?;";
        ArrayList<NotificaDTO> notifiche = new ArrayList<>();
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try 
        {
        	ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
        	ps.setInt(1, codCliente);
            
            try
            {
            	rs = ps.executeQuery();
            	
                while (rs.next()) 
                {       	
                	notifiche.add(mapResultToNotifica(rs));
                }
            }
            catch(Exception e) 
            {
            	throw e;
            }
        } 
        catch (Exception e) 
        {
        	throw e;
        }
        finally 
		{
			ConnectionPostgreSQLDB.closeStatement(ps);
			ConnectionPostgreSQLDB.closeResultSet(rs);
		}
        return notifiche;
	}
	
	@Override
	public void insertNotifica(NotificaDTO notifica) throws Exception 
	{
		String query = "INSERT INTO notifica (tipo, momento_notifica, cod_offerta, cod_prenotazione) VALUES (?, ?, ?, ?);";
        
		PreparedStatement ps = null; 
        
		try 
        {
			ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
        	
			ps.setString(1, notifica.getTipo());
			ps.setDate(2, notifica.getData());
			ps.setInt(3, notifica.getCod_offerta());
			ps.setInt(4, notifica.getCod_prenotazione());
        	
			ps.executeUpdate();  
        } 
        catch (Exception e) 
        {
        	throw e;
        }
        finally 
		{
			ConnectionPostgreSQLDB.closeStatement(ps);
		}
		
	}

	@Override
	public void removeNotifica(int codNotifica) throws Exception 
	{
		String query = "DELETE FROM notifica WHERE cod_notifica = ?;";
       
		PreparedStatement ps = null;
       
		try 
        {
			ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
			ps.setInt(1, codNotifica);
			ps.executeUpdate();
        } 
        catch (Exception e) 
        {
        	throw e;
        }
        finally 
		{
			ConnectionPostgreSQLDB.closeStatement(ps);
		}
		
	}
	
	private NotificaDTO mapResultToNotifica(ResultSet rs) throws Exception
    {
		NotificaDTO notifica = new NotificaDTO();
    	
    	notifica.setCod_notifica(rs.getInt("cod_notifica"));
    	notifica.setCod_offerta(rs.getInt("cod_offerta"));
    	notifica.setCod_prenotazione(rs.getInt("cod_prenotazione"));
    	notifica.setData(rs.getDate("momento_notifica"));
    	notifica.setTipo(rs.getString("tipo"));
    	
        return notifica;
    }

}
