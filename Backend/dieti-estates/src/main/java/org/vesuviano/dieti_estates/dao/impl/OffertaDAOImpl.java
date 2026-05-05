package org.vesuviano.dieti_estates.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.vesuviano.dieti_estates.connection.ConnectionPostgreSQLDB;
import org.vesuviano.dieti_estates.dao.OffertaDAO;
import org.vesuviano.dieti_estates.dto.OffertaDTO;

public class OffertaDAOImpl implements OffertaDAO{
	
	 @Override
	 public ArrayList<OffertaDTO> getAllOfferAgent(int codAgente) throws Exception
	 {
		String getOffertaQuery = "SELECT * FROM offerta AS o JOIN immobile AS i ON o.cod_immobile = i.cod_immobile WHERE i.cod_agente = ?;";
		ArrayList<OffertaDTO> offerte = new ArrayList<>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
	
		try 
		{
			ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(getOffertaQuery);
			ps.setInt(1, codAgente);
		
			try 
			{
				rs = ps.executeQuery();
			
				while(rs.next())
				{
					offerte.add(mapResultSetToOfferta(rs));
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
		 
		 return offerte;
	
	 }
	
	 @Override
	 public ArrayList<OffertaDTO> getAllOfferClient(int codCliente) throws Exception
	 {
		String getOffertaQuery = "SELECT * FROM offerta WHERE cod_cliente = ?;";
		ArrayList<OffertaDTO> offerte = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;
	
		try 
		{
			ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(getOffertaQuery);
			ps.setInt(1, codCliente);
		
			try 
			{
				rs = ps.executeQuery();
			
				while(rs.next())
				{
					offerte.add(mapResultSetToOfferta(rs));
				}
			}
			catch(SQLException e) 
			{
				e.printStackTrace();
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
		 
		 return offerte;
	
	 }
	 
	 private OffertaDTO mapResultSetToOfferta(ResultSet rs) throws SQLException 
	 {
		    OffertaDTO offerta = new OffertaDTO();
			offerta.setCodOfferta(rs.getInt("cod_offerta"));
			offerta.setStatoOfferta(rs.getString("stato_offerta"));
			offerta.setAmmontare(rs.getInt("ammontare"));
			
			offerta.setCodCliente(rs.getInt("cod_cliente"));
	        offerta.setCodImmobile(rs.getInt("cod_immobile"));
	        
	        return offerta;
	 }
	
	 @Override
	 public void insertOffer(OffertaDTO offerta) throws Exception
	 {
			
			String query = "INSERT INTO offerta (ammontare, cod_cliente, cod_immobile) VALUES (?, ?, ?);";
			
			PreparedStatement ps = null; 
			
			try 
			{
				ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
				
		        ps.setDouble(1, offerta.getAmmontare());
		        ps.setInt(2, offerta.getCodCliente());
		        ps.setInt(3, offerta.getCodImmobile());
		        
		        ps.executeUpdate();  
			} 
			catch (SQLException e ) 
			{
				throw e;
			}
			finally 
			{
			    ConnectionPostgreSQLDB.closeStatement(ps);
			}
	 }
	 
	 @Override
	 public int acceptOffer(int codOfferta) throws Exception
	 {
		 
		 	int rowsUpdated = 0;
		    PreparedStatement ps = null;
		 
	        try
	        {
	        	ps = ConnectionPostgreSQLDB.getConnection().prepareStatement("UPDATE Offerta SET stato_offerta = ? WHERE cod_offerta = ?;");
	        	
	        	ps.setString(1, "Accettata");
	        	ps.setInt(2, codOfferta);
	           
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
	 public int rejectOffer(int codOfferta) throws Exception
	 {
		 
		 	int rowsUpdated = 0;
		    String query = "UPDATE offerta SET stato_offerta = ? WHERE cod_offerta = ?;";
		 
		    PreparedStatement ps = null;
		    
	        try 
	        {
	    
	        	ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
	        	
	        	ps.setString(1, "Rifiutata");
	        	ps.setInt(2, codOfferta);
	           
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
	 public void modifyOffer(OffertaDTO offerta) throws Exception
	 {
		 
		 String query = "UPDATE offerta SET stato_offerta = ?, ammontare = ?, cod_cliente = ? WHERE cod_offerta = ?;";
		 
		 PreparedStatement ps = null;
		 
		 try
		 {
			    
			  ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
			     
	          ps.setString(1, offerta.getStatoOfferta());
	          ps.setDouble(2, offerta.getAmmontare());
	          ps.setInt(3, offerta.getCodCliente());
	          ps.setInt(4, offerta.getCodOfferta());
	           
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
	 public void deleteOffer(int codOfferta) throws Exception
     {
		    String query = "DELETE FROM offerta WHERE cod_offerta = ?;";
			
		    PreparedStatement ps = null;

		    try 
		    {
		        ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);

		        ps.setInt(1, codOfferta);

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
	
}
