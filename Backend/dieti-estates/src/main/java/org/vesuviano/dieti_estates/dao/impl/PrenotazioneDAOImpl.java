
package org.vesuviano.dieti_estates.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.vesuviano.dieti_estates.connection.ConnectionPostgreSQLDB;
import org.vesuviano.dieti_estates.dao.PrenotazioneDAO;
import org.vesuviano.dieti_estates.dto.PrenotazioneDTO;

public class PrenotazioneDAOImpl implements PrenotazioneDAO
{
	
	@Override
	public ArrayList<PrenotazioneDTO> getAllPrenotazioneAgente(int codAgente) throws Exception
	{
		String getPrenotazioneQuery = "SELECT * FROM prenotazione WHERE cod_agente = ?;";
		ArrayList<PrenotazioneDTO> prenotazioni = new ArrayList<>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
	
		try 
		{
			ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(getPrenotazioneQuery);
			ps.setInt(1, codAgente);
		
			try 
			{
				rs = ps.executeQuery();
			
				while(rs.next())
				{
					prenotazioni.add(mapResultSetToPrenotazione(rs));
				}
			}
			catch(SQLException e) 
			{
				throw e;
			}
		 }
	     catch(SQLException e) 
		 {      
	    	 throw e;
		 }
		 finally 
		 {
			ConnectionPostgreSQLDB.closeStatement(ps);
			ConnectionPostgreSQLDB.closeResultSet(rs);
		 }
		 
		 return prenotazioni;
	
	}
	
	@Override
	public ArrayList<PrenotazioneDTO> getAllPrenotazioneCliente(int codCliente) throws Exception
	{
		String getPrenotazioneQuery = "SELECT * FROM prenotazione WHERE cod_cliente = ?;";
		ArrayList<PrenotazioneDTO> prenotazioni = new ArrayList<>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
	
		try 
		{
			ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(getPrenotazioneQuery);
			ps.setInt(1, codCliente);
		
			try 
			{
				rs = ps.executeQuery();
			
				while(rs.next())
				{
					prenotazioni.add(mapResultSetToPrenotazione(rs));
				}
			}
			catch(SQLException e) 
			{
				throw e;
			}
		 }
	     catch(SQLException e) 
		 {      
	    	 throw e;
		 }
		 finally 
		 {
			ConnectionPostgreSQLDB.closeStatement(ps);
			ConnectionPostgreSQLDB.closeResultSet(rs);
		 }
		 
		 return prenotazioni;
		
	}
	
	@Override
	public int acceptPrenotazioneByAgente(int codAgente, int codPrenotazione) throws Exception
	{
	    int rowsUpdated = 0;
	    String updateQuery = "UPDATE Prenotazione SET stato_prenotazione = 'Accettata' WHERE cod_agente = ? AND cod_prenotazione = ?";
		
	    PreparedStatement ps = null;

	    try 
	    {
	       
	        ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(updateQuery);
	        ps.setInt(1, codAgente);
	        ps.setInt(2, codPrenotazione);

	        
	        rowsUpdated = ps.executeUpdate();

	    } 
	    catch (SQLException e) 
	    {
	    	throw e;
	    } 
	    finally 
	    {
	      
	        ConnectionPostgreSQLDB.closeStatement(ps);
	    }

	    return rowsUpdated;
	}

	
	@Override
	public int refusePrenotazioneByAgente(int codAgente, int codPrenotazione) throws Exception
	{
	    String updateQuery = "UPDATE Prenotazione SET stato_prenotazione = 'Rifiutata' WHERE cod_agente = ? AND cod_prenotazione = ?";
	    int rowsUpdated = 0;
		
	    PreparedStatement ps = null;

	    try 
	    {
	       
	        ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(updateQuery);
	        ps.setInt(1, codAgente);
	        ps.setInt(2, codPrenotazione);
  
	        rowsUpdated = ps.executeUpdate();

	    } 
	    catch (SQLException e) 
	    {
	    	throw e;
	    } 
	    finally 
	    {
	      
	        ConnectionPostgreSQLDB.closeStatement(ps);
	    }
	   
	    return rowsUpdated;
	}
	
	@Override
	public void insertPrenotazioneByCliente(PrenotazioneDTO prenotazione) throws Exception
	{
	    String insertQuery = "INSERT INTO prenotazione (  momento_prenotazione, cod_cliente, cod_immobile, cod_agente) "
	                        + "VALUES (?, ?, ?, ?)";
	    System.out.println(prenotazione.getCodAgente()+"/"+prenotazione.getOrarioPrenotazione());
	    PreparedStatement ps = null;
	
	    try 
	    {
	
	        ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(insertQuery);

	        ps.setTimestamp(1, prenotazione.getOrarioPrenotazione());
	        ps.setInt(2, prenotazione.getCodCliente());
	        ps.setInt(3, prenotazione.getCodImmobile());
	        ps.setInt(4, prenotazione.getCodAgente());

	        ps.executeUpdate();
	        
	    } 
	    catch (SQLException e) 
	    {
	    	throw e;
	    } 
	    finally 
	    {
	        ConnectionPostgreSQLDB.closeStatement(ps);
	    }
	}

	
	@Override
	public void removePrenotazioneByCliente(int codCliente, int codPrenotazione) throws Exception
	{
	    String deleteQuery = "DELETE FROM Prenotazioni WHERE cod_cliente = ? AND cod_prenotazione = ?";
		PreparedStatement ps = null;

	    try 
	    {
	        ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(deleteQuery);

	        ps.setInt(1, codCliente);
	        ps.setInt(2, codPrenotazione);

	        ps.executeUpdate();
	        
	        
	    } 
	    catch (SQLException e) 
	    {
	    	throw e; 
	    } 
	    finally 
	    {
	        ConnectionPostgreSQLDB.closeStatement(ps);
	    }
	}

	
	private PrenotazioneDTO mapResultSetToPrenotazione(ResultSet rs) throws SQLException 
    {
		PrenotazioneDTO prenotazione = new PrenotazioneDTO();
		prenotazione.setCodPrenotazione(rs.getInt("cod_prenotazione"));
		prenotazione.setStatoPrenotazione(rs.getString("stato_prenotazione"));
		prenotazione.setLuogo(rs.getString("luogo_incontro"));
		prenotazione.setOrarioPrenotazione(rs.getTimestamp("momento_prenotazione"));
        
        prenotazione.setCodCliente(rs.getInt("cod_cliente"));
        prenotazione.setCodImmobile(rs.getInt("cod_immobile"));
        prenotazione.setCodAgente(rs.getInt("cod_agente"));
        
        
        return prenotazione;
    }
	
}
