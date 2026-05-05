package org.vesuviano.dieti_estates.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.vesuviano.dieti_estates.connection.ConnectionPostgreSQLDB;
import org.vesuviano.dieti_estates.dao.PreferitoDAO;
import org.vesuviano.dieti_estates.dto.ImmobileDTO;

public class PreferitoDAOImpl implements PreferitoDAO
{
	
	@Override
	public ArrayList<ImmobileDTO> getAllPreferitoCliente(int codCliente) throws Exception
	{
		String getPrenotazioneQuery = "SELECT i.cod_immobile as cod_immobile, i.tipo as tipo, i.descrizione as descrizione, i.comune as comune, i.costo as costo, i.metratura as metratura, i.città as città, i.via as via, i.num_civico as num_civico, i.piano as piano, i.ascensore as ascensore, i.classe_energetica as classe_energetica, i.num_stanze as num_stanze, i.num_camere_letto as num_camere_letto, i.portineria as portineria, i.cod_agente as cod_agente, i.immagine as immagine, i.altre_colonne as altre_colonne, i.etichetta as etichetta, i.stato as stato, i.coordinate[0] AS longitudine, i.coordinate[1] AS latitudine FROM preferito AS p JOIN immobile AS i ON p.cod_immobile = i.cod_immobile WHERE p.cod_cliente = ? GROUP BY i.cod_immobile ORDER BY MAX(p.data_aggiunzione) DESC;";
		ArrayList<ImmobileDTO> preferiti = new ArrayList<>();
		
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
					preferiti.add(new ImmobileDTO(rs));
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
		 
		 return preferiti;
	
	}
	
	@Override
	public void insertPreferito(int codCliente, int codImmobile) throws Exception
	{
	    String insertQuery = "INSERT INTO preferito (cod_cliente, cod_immobile) VALUES (?, ?)";
		
	    PreparedStatement ps = null;
	
	    try {

	        ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(insertQuery);

	        ps.setInt(1, codCliente);
	        ps.setInt(2, codImmobile);

	        
	        int rowsAffected = ps.executeUpdate();
	        
	        if (rowsAffected > 0) 
	        {
	            System.out.println("Preferito aggiunto con successo!");
	        }
	        else 
	        {
	            throw new Error();
	        }
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
	public void removePreferito(int codCliente, int codImmobile) throws Exception
	{
	    String deleteQuery = "DELETE FROM preferito WHERE cod_cliente = ? AND cod_immobile = ?";
		
	    PreparedStatement ps = null;
	
	    try 
	    { 
	        ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(deleteQuery);

	        ps.setInt(1, codCliente);
	        ps.setInt(2, codImmobile);

	        int rowsAffected = ps.executeUpdate();
	        
	        if (rowsAffected > 0) 
	        {
	            System.out.println("Preferito rimosso con successo!");
	        } 
	        else 
	        {
	            System.out.println("Nessun preferito trovato per i parametri forniti.");
	        }
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

