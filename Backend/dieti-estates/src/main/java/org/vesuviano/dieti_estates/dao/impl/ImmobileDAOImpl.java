package org.vesuviano.dieti_estates.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.vesuviano.dieti_estates.connection.ConnectionPostgreSQLDB;

import java.util.ArrayList;

import org.vesuviano.dieti_estates.dao.ImmobileDAO;
import org.vesuviano.dieti_estates.dto.CoordinataDTO;
import org.vesuviano.dieti_estates.dto.ImmobileDTO;

public class ImmobileDAOImpl implements ImmobileDAO {
	

	@Override
	public ArrayList<ImmobileDTO> getImmobiliByCoordinate(CoordinataDTO coordinate, String tipo, int raggio) throws Exception {
		String query = "SELECT cod_immobile, tipo, descrizione, costo, comune, città, via, num_civico, \r\n"
				+ "       coordinate[0] AS longitudine, coordinate[1] AS latitudine, metratura, piano, ascensore, classe_energetica,\r\n"
				+ "       num_stanze, num_camere_letto, portineria, cod_agente, immagine, altre_colonne, etichetta, stato\r\n"
				+ "FROM immobile\r\n"
				+ "WHERE (\r\n"
				+ "    6371 * 2 * \r\n"
				+ "    ASIN(\r\n"
				+ "        SQRT(\r\n"
				+ "            POWER(SIN(RADIANS((coordinate[1] - ?) / 2)), 2) +\r\n"
				+ "            COS(RADIANS(45.46422)) * COS(RADIANS(coordinate[1])) *\r\n"
				+ "            POWER(SIN(RADIANS((coordinate[0] - ?) / 2)), 2)\r\n"
				+ "        )\r\n"
				+ "    )\r\n"
				+ ") <= ? AND stato = ?";
		
        ArrayList<ImmobileDTO> immobili = new ArrayList<>();
       
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try 
        {
        	ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
        	ps.setDouble(1, coordinate.getLatitudine());
            ps.setDouble(2, coordinate.getLongitudine());
            ps.setInt(3, raggio);
        	ps.setString(4, tipo);
        	
        	try
            {
            	rs = ps.executeQuery();
            	
                while (rs.next()) 
                {
                    immobili.add(new ImmobileDTO(rs));
                }
            }
            catch(Exception e) 
            {
            	throw e;
            }
        } 
        catch (Exception e) 
        {
        	e.printStackTrace();
        	throw e;
        }
        finally 
		{
			ConnectionPostgreSQLDB.closeStatement(ps);
			ConnectionPostgreSQLDB.closeResultSet(rs);
		}
        return immobili;
	}
	
    @Override
    public ArrayList<ImmobileDTO> getImmobileByCittà(String città) throws Exception
    {
        String query = "SELECT cod_immobile, tipo, descrizione, costo, comune, città, comune, via, num_civico, piano, ascensore, classe_energetica, coordinate[0] AS longitudine, coordinate[1] AS latitudine, metratura,num_stanze, num_camere_letto,portineria, cod_agente, immagine, altre_colonne, etichetta, stato FROM immobile WHERE città = ?;";
        ArrayList<ImmobileDTO> immobili = new ArrayList<>();
       
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try 
        {
        	ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
        	ps.setString(1, città);
            
            try
            {
            	rs = ps.executeQuery();
            	
                while (rs.next()) 
                {
                    immobili.add(new ImmobileDTO(rs));
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
        return immobili;
    }

    @Override
    public ImmobileDTO getImmobileById(int codImmobile) throws Exception
    {
        String query = "SELECT cod_immobile, tipo, descrizione, costo, piano, ascensore, classe_energetica, metratura, comune, città, comune,via, num_civico, coordinate[0] AS longitudine, coordinate[1] AS latitudine, num_stanze, num_camere_letto, portineria, cod_agente, immagine, altre_colonne, etichetta, stato FROM immobile WHERE cod_immobile = ?;";
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try 
        {
        	ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
        	ps.setInt(1, codImmobile);
            
            try
            {
            	rs = ps.executeQuery();
                if (rs.next()) 
                {
                    return new ImmobileDTO(rs);
                }
            }
            catch (Exception e) 
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
    public void insertImmobile(ImmobileDTO immobile) throws Exception
    {

        String query = "INSERT INTO immobile \r\n"
        		+ "(tipo, descrizione, costo, città, via, num_civico, num_stanze, portineria, cod_agente,  immagine, coordinate, comune, stato, num_camere_letto, metratura, piano, ascensore, classe_energetica, etichetta) \r\n"
        		+ "VALUES \r\n"
        		+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, decode(?, 'base64')::bytea, point(?,?), ?, ?, ?, ?, ?, ?, ?, ?);";
        
        PreparedStatement ps = null;
        System.out.println("sono prima del try dido2");
        try 
        {
        	ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
        	ps = mapImmobileToStatement2(ps, immobile);
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
	public void updateImmobile(ImmobileDTO immobile) throws Exception 
    {
    	String query = "UPDATE immobile SET tipo = ?, descrizione = ?, costo = ?, città = ?, via = ?, num_civico = ?, num_stanze = ?, portineria = ?, immagine = decode(?, 'base64'), coordinate = point(?, ?), comune = ?, stato = ?, num_camere_letto = ?, metratura = ?,  piano = ?, ascensore = ?, classe_energetica = ?, etichetta = ? WHERE cod_immobile = ? AND cod_agente = ?;";
       
    	PreparedStatement ps = null;
    	
        try 
        {
        	ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
        	ps = mapImmobileToStatement(ps, immobile, true);
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
    public void removeImmobile(int codImmobile) throws Exception
    {
        String query = "DELETE FROM immobile WHERE cod_immobile = ?;";
      
        PreparedStatement ps = null;
        
        try 
        {
        	ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
        	
        	ps.setInt(1, codImmobile);
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
	public ArrayList<ImmobileDTO> getImmobiliAgente(int codAgente) throws Exception
	{
    	String query = "SELECT cod_immobile, tipo, descrizione, costo, comune, città, comune, via, num_civico, piano, ascensore, classe_energetica, coordinate[0] AS longitudine, coordinate[1] AS latitudine, metratura,num_stanze, num_camere_letto,portineria, cod_agente, immagine, altre_colonne, etichetta, stato FROM immobile WHERE cod_agente = ?;";
        ArrayList<ImmobileDTO> immobili = new ArrayList<>();
       
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
                    immobili.add(new ImmobileDTO(rs));
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
        return immobili;
	}
    
	@Override
	public ArrayList<ImmobileDTO> getImmobileByVariousParameters(String parametro1, String parametro2) throws Exception
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	private PreparedStatement mapImmobileToStatement(PreparedStatement ps, ImmobileDTO immobile, boolean includeCodImmobileAndCodAgente) throws Exception
	{
		ps.setString(1, immobile.getTipo());
		ps.setString(2, immobile.getDescrizione());
		ps.setInt(3, immobile.getCosto());
		ps.setString(4, immobile.getIndirizzo().getCitta());
		ps.setString(5, immobile.getIndirizzo().getVia());
		ps.setInt(6, immobile.getIndirizzo().getCivico());
		ps.setInt(7, immobile.getNumeroStanze());
		ps.setString(8, immobile.isPortineria());
		ps.setString(9, immobile.getImmagine());
		System.out.println(immobile.getImmagine());
		ps.setDouble(10, immobile.getCoordinata().getLongitudine());
		ps.setDouble(11, immobile.getCoordinata().getLatitudine());
		ps.setString(12, immobile.getIndirizzo().getComune());
		ps.setString(13, immobile.getStato());
		ps.setInt(14, immobile.getNumeroCamereLetto());
		ps.setInt(15, immobile.getMetratura());
		ps.setInt(16, immobile.getPiano());
		ps.setBoolean(17, immobile.isAscensore());
		ps.setString(18, immobile.getClasseEnergetica());
		ps.setString(19, immobile.getEtichetta());
		
	    if (includeCodImmobileAndCodAgente) {
	    	ps.setInt(20, immobile.getCodImmobile());
	    	ps.setInt(21, immobile.getCodAgente());
	    }

	    return ps;
	}
	
	@Override
    public void insertImmobile2(ImmobileDTO immobile) throws Exception
    {
        String query = "INSERT INTO immobile (tipo, descrizione, costo, città, via, num_civico, num_stanze, portineria, cod_agente, comune, stato, metratura, piano, classe_energetica) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        
        PreparedStatement ps = null;
        try 
        {
        	ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
        	ps = mapImmobileToStatement2(ps, immobile);
        	ps.executeUpdate();   
        } 
        catch (Exception e) 
        {
        	e.printStackTrace();
        	throw e;
        }
        finally 
		{
			ConnectionPostgreSQLDB.closeStatement(ps);
		}
    }
	
	private PreparedStatement mapImmobileToStatement2(PreparedStatement ps, ImmobileDTO immobile) throws Exception
	{
		
	    ps.setString(1, immobile.getTipo());
	    ps.setString(2, immobile.getDescrizione());
	    ps.setInt(3, immobile.getCosto());
	    ps.setString(4, immobile.getIndirizzo().getCitta());
	    ps.setString(5, immobile.getIndirizzo().getVia());
	    ps.setInt(6, immobile.getIndirizzo().getCivico());
	    ps.setInt(7, immobile.getNumeroStanze());
	    ps.setString(8, immobile.isPortineria());
	    ps.setInt(9, immobile.getCodAgente());
		ps.setString(10, immobile.getImmagine());
		ps.setDouble(11, immobile.getCoordinata().getLongitudine());
		ps.setDouble(12, immobile.getCoordinata().getLatitudine());
		ps.setString(13, immobile.getIndirizzo().getComune());
		ps.setString(14, immobile.getStato());
		ps.setInt(15, immobile.getNumeroCamereLetto());
		ps.setInt(16, immobile.getMetratura());
		ps.setInt(17, immobile.getPiano());
		ps.setBoolean(18, immobile.isAscensore());
		ps.setString(19, immobile.getClasseEnergetica());
		System.out.println(immobile.getClasseEnergetica()+" "+ immobile.getEtichetta());
		ps.setString(20, immobile.getEtichetta());


		return ps;
}


}

