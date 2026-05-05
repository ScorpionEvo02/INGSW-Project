package org.vesuviano.dieti_estates.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.vesuviano.dieti_estates.connection.ConnectionPostgreSQLDB;
import org.vesuviano.dieti_estates.dao.ImmagineImmobileDAO;
import org.vesuviano.dieti_estates.dto.ImmagineImmobileDTO;

public class ImmagineImmobileDAOImpl implements ImmagineImmobileDAO{
	
	@Override
	public ArrayList<ImmagineImmobileDTO> getImmaginiImmobile(int cod_immobile) throws Exception 
	{
		String query = "SELECT cod_immagine, cod_immobile, immagine FROM immagine_immobile WHERE cod_immobile = ?;";
        ArrayList<ImmagineImmobileDTO> immaginiImmobile = new ArrayList<>();
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try 
        {
        	ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
            ps.setInt(1, cod_immobile);
        	
            try
            {
            	rs = ps.executeQuery();
            	
                while (rs.next()) 
                {       	
                	ImmagineImmobileDTO immagineImmobile = new ImmagineImmobileDTO();
                	System.out.println(rs.getInt("cod_immagine")+" "+ rs.getInt("cod_immobile"));
                	immagineImmobile.setCodImmagine(rs.getInt("cod_immagine"));
                	immagineImmobile.setCodImmobile(rs.getInt("cod_immobile"));
                	
                	byte[] immagineBytes = rs.getBytes("immagine");
                    if (immagineBytes != null)
						immagineImmobile.setImmagine(immagineBytes);
					else
						immagineImmobile = null;
                    
                    immaginiImmobile.add(immagineImmobile);
                    
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
        return immaginiImmobile;
	}

	@Override
	public void insertImmagineImmobile(ImmagineImmobileDTO immagineImmobile) throws Exception 
	{
		String query = "INSERT INTO immagine_immobile (cod_immobile, immagine) VALUES (?, decode(?, 'base64'));";
        
		PreparedStatement ps = null;
		
        try 
        {
        	ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
        	
        	ps.setInt(1, immagineImmobile.getCodImmobile());
        	ps.setString(2, immagineImmobile.getImmagine());
        	
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
	public void removeImmagineImmobile(int codImmagineImmobile) throws Exception 
	{
		String query = "DELETE FROM immagine_immobile WHERE cod_immagine = ?;";
        
		PreparedStatement ps = null;
		
        try 
        {
        	ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
        	ps.setInt(1, codImmagineImmobile);
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

}
