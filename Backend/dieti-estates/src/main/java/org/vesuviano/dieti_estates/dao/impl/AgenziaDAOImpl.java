package org.vesuviano.dieti_estates.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.vesuviano.dieti_estates.connection.ConnectionPostgreSQLDB;
import org.vesuviano.dieti_estates.dao.AgenziaDAO;
import org.vesuviano.dieti_estates.dto.AgenziaDTO;
import org.vesuviano.dieti_estates.dto.IndirizzoDTO;

public class AgenziaDAOImpl implements AgenziaDAO
{
	
	@Override
	public ArrayList<AgenziaDTO> getAllAgenzie() throws Exception 
	{
		String query = "SELECT * FROM agenzia_immobiliare;";
        ArrayList<AgenziaDTO> agenzie = new ArrayList<>();
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try 
        {
        	ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
            
            try
            {
            	rs = ps.executeQuery();
            	
                while (rs.next()) 
                {       	
                	agenzie.add(mapResultToAgenzia(rs));
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
        return agenzie;
	}

	@Override
	public AgenziaDTO getAgenziaById(int codAgenzia) throws Exception 
	{
		String query = "SELECT * FROM agenzia_immobiliare WHERE cod_agenzia = ?;";
        
		PreparedStatement ps = null;
        ResultSet rs = null;
		
        try 
        {
        	ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
        	ps.setInt(1, codAgenzia);
        	
            try
            {
            	rs = ps.executeQuery();
            	if (rs.next()) 
                {
                    return mapResultToAgenzia(rs);
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
        return null;
	}
	
	@Override
	public void insertAgenzia(AgenziaDTO agenzia) throws Exception 
	{
		String query = "INSERT INTO agenzia_immobiliare (nome_agenzia, descrizione, città, via, num_civico, p_iva, email, logo) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        
		PreparedStatement ps = null;
		
		try 
        {
			ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
			ps = mapAgenziaToStatement(ps, agenzia, false);
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
	public void updateAgenzia(AgenziaDTO agenzia) throws Exception 
	{
		String query = "UPDATE agenzia_immobiliare SET nome_agenzia = ?, descrizione = ?, città = ?, via = ?, num_civico = ?, p_iva = ?, email = ?, logo = ? WHERE cod_agenzia = ?;";
      
		PreparedStatement ps = null;
		
        try 
        {
        	ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
        	ps = mapAgenziaToStatement(ps, agenzia, true);
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
	public void removeAgenzia(int codAgenzia) throws Exception 
	{
		String query = "DELETE FROM agenzia_immobiliare WHERE cod_agenzia = ?;";
       
		PreparedStatement ps = null;
		
        try 
        {
        	ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
        	ps.setInt(1, codAgenzia);
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
	
	private AgenziaDTO mapResultToAgenzia(ResultSet rs) throws Exception
    {
		AgenziaDTO agenzia = new AgenziaDTO();
    	
    	agenzia.setCodAgenzia(rs.getInt("cod_agenzia"));
    	agenzia.setNome(rs.getString("nome_agenzia"));
    	agenzia.setDescrizione(rs.getString("descrizione"));
    	
    	IndirizzoDTO indirizzo = new IndirizzoDTO();
    	indirizzo.setCitta(rs.getString("città"));
    	indirizzo.setVia(rs.getString("via"));
    	indirizzo.setCivico(rs.getInt("num_civico"));
    	indirizzo.setCAP(rs.getInt("cap"));
    	agenzia.setIndirizzo(indirizzo);
    	
    	agenzia.setpIva(rs.getString("p_iva"));
    	agenzia.setEmail(rs.getString("email"));
    	agenzia.setLogo(rs.getString("logo"));
        
        return agenzia;
    }
	

	private PreparedStatement mapAgenziaToStatement(PreparedStatement ps, AgenziaDTO agenzia, boolean includeCodAgenzia) throws Exception
	{
	    ps.setString(1, agenzia.getNome());
	    ps.setString(2, agenzia.getDescrizione());
	    ps.setString(3, agenzia.getIndirizzo().getCitta());
	    ps.setString(4, agenzia.getIndirizzo().getVia());
	    ps.setInt(5, agenzia.getIndirizzo().getCivico());
	    ps.setInt(6, agenzia.getIndirizzo().getCAP());
	    ps.setString(7, agenzia.getpIva());
	    ps.setString(8, agenzia.getEmail());
	    ps.setString(9, agenzia.getLogo());
	    
	    if (includeCodAgenzia) {
	        ps.setInt(10, agenzia.getCodAgenzia());
	    }
	    
	    return ps;
	}

}
