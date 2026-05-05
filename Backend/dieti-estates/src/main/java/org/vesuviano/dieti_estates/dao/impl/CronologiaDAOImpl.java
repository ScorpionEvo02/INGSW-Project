package org.vesuviano.dieti_estates.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.vesuviano.dieti_estates.connection.ConnectionPostgreSQLDB;
import org.vesuviano.dieti_estates.dao.CronologiaDAO;
import org.vesuviano.dieti_estates.dto.ImmobileDTO;

public class CronologiaDAOImpl implements CronologiaDAO
{ 
	
	public ArrayList<ImmobileDTO> getAllCronologiaCliente(int codCliente) throws Exception
	{
		String getCronologiaQuery = "SELECT i.cod_immobile, i.tipo, i.descrizione, i.costo, i.comune, i.metratura, i.città, i.via, i.num_civico, i.piano, i.ascensore, i.classe_energetica, i.num_stanze, i.num_camere_letto, i.portineria, i.cod_agente, i.immagine, i.altre_colonne, i.etichetta, i.stato, i.coordinate[0] AS longitudine, i.coordinate[1] AS latitudine FROM visualizzare AS v JOIN immobile AS i ON v.cod_immobile = i.cod_immobile WHERE v.cod_cliente = ? GROUP BY i.cod_immobile ORDER BY MAX(v.data_aggiunzione) DESC LIMIT 10;";
	
		ArrayList<ImmobileDTO> immobiliCronologia = new ArrayList<>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try 
		{
			ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(getCronologiaQuery);
			ps.setInt(1, codCliente);
			rs = ps.executeQuery();
			
			while(rs.next()) 
			{   
				immobiliCronologia.add(new ImmobileDTO(rs));
			}
		}
		catch(Exception e) 
		{
			throw e;
		}
		finally 
		{
			ConnectionPostgreSQLDB.closeStatement(ps);
			ConnectionPostgreSQLDB.closeResultSet(rs);
		}
		return immobiliCronologia;
	}
	
	public void insertCronologiaCliente(int codCliente, int immobile) throws Exception 
	{

		String insertCronologia = "INSERT INTO visualizzare (cod_cliente, cod_immobile) VALUES (?, ?);";
		
		PreparedStatement ps = null;
		
		try 
		{
			ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(insertCronologia);
			ps.setInt(1, codCliente);
			ps.setInt(2, immobile);
			ps.executeUpdate();
		}
		catch(Exception e) 
		{
			throw e;
		}
		finally 
		{
			ConnectionPostgreSQLDB.closeStatement(ps);
		}
	}
}
