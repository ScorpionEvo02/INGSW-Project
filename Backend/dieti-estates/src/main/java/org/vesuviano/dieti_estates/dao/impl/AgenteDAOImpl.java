package org.vesuviano.dieti_estates.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.vesuviano.dieti_estates.connection.ConnectionPostgreSQLDB;
import org.vesuviano.dieti_estates.dao.AgenteDAO;
import org.vesuviano.dieti_estates.dto.AgenteImmobiliareDTO;
import org.vesuviano.dieti_estates.dto.IndirizzoDTO;

public class AgenteDAOImpl implements AgenteDAO
{
	 
	    @Override
		public AgenteImmobiliareDTO getAllAgent(int codAgente) throws Exception
		{
	    	
			String getAgenteQuery = "SELECT * FROM agente WHERE cod_agente = ?;";
			AgenteImmobiliareDTO agente = new AgenteImmobiliareDTO();
			
			PreparedStatement ps = null;
			ResultSet rs = null;

			try 
			{
				ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(getAgenteQuery);
				ps.setInt(1, codAgente);

				try 
				{
					rs = ps.executeQuery();
			
					if(rs.next())
					{
						agente = mapResultSetToAgente(rs);
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
		 	
			return agente;
		}
	    
	    private AgenteImmobiliareDTO mapResultSetToAgente(ResultSet rs) throws SQLException 
    	{
			AgenteImmobiliareDTO agente = new AgenteImmobiliareDTO();
			agente.setCodAgente(rs.getInt("cod_agente"));
			agente.setNome(rs.getString("nome"));
			agente.setCognome(rs.getString("cognome"));
			agente.setCodiceFiscale(rs.getString("codice_fiscale"));
			agente.setDataNascita(rs.getDate("data_nascita"));
			agente.setGenere(rs.getString("genere"));
        
        	IndirizzoDTO indirizzo = new IndirizzoDTO();
        	indirizzo.setCitta(rs.getString("città"));
        	indirizzo.setVia(rs.getString("via"));
        	indirizzo.setCivico(rs.getInt("num_civico"));
        	agente.setIndirizzo(indirizzo);

        	agente.setEmail(rs.getString("email"));
        	agente.setCodGestore(rs.getInt("cod_gestore"));
        	agente.setCodAgenzia(rs.getInt("cod_agenzia"));

        	return agente;
     	}
		
		@Override
		public ArrayList<AgenteImmobiliareDTO> getAllAgenti(int codGestore) throws Exception 
		{
			String getAgentiQuery = "SELECT * FROM agente WHERE cod_gestore = ?;";
		    ArrayList<AgenteImmobiliareDTO> agenti = new ArrayList<>();

		    PreparedStatement ps = null;
		    ResultSet rs = null;

		    try 
		    {
		        ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(getAgentiQuery);
		        ps.setInt(1, codGestore);
		        rs = ps.executeQuery();

		        while (rs.next()) 
		        {
		            AgenteImmobiliareDTO agente = mapResultSetToAgente(rs);
		            agenti.add(agente);
		        }
		    } 
		    catch (SQLException e) 
		    {
		        throw new Exception("Errore durante il recupero degli agenti", e);
		    } 
		    finally 
		    {
		        ConnectionPostgreSQLDB.closeStatement(ps);
		        ConnectionPostgreSQLDB.closeResultSet(rs);
		    }

		    return agenti;
		}

	    
		@Override
		public void insertAgent(AgenteImmobiliareDTO agent) throws Exception
		{
					
			String query = "INSERT INTO agente (nome, cognome, codice_fiscale, data_nascita, genere, città, via, num_civico, email, password_c, cod_gestore, cod_agenzia) "
			         	 + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, crypt(?, gen_salt('md5')), ?, ?);";
		
			PreparedStatement ps = null;
		
			try 
			{
				ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
			
				ps.setString(1, agent.getNome());
				ps.setString(2, agent.getCognome());
				ps.setString(3, agent.getCodiceFiscale());
				ps.setDate(4, agent.getDataNascita());
				ps.setString(5, agent.getGenere());
				ps.setString(6, agent.getIndirizzo().getCitta());
				ps.setString(7, agent.getIndirizzo().getVia());
				ps.setInt(8, agent.getIndirizzo().getCivico());
				ps.setString(9, agent.getEmail());
				ps.setString(10, agent.getPassword());
				ps.setInt(11, agent.getCodGestore());
				ps.setInt(12, agent.getCodAgenzia());
		    
				ps.executeUpdate();
			} 
			catch (SQLException e ) 
			{
				throw e;
			}
	 	}
		
		@Override
	 	public boolean verifyAgent(int codAgente) throws Exception
	 	{
		 
		    String query = "SELECT * FROM agente WHERE cod_agente = ?;";
		 
		    PreparedStatement ps = null;
		    ResultSet rs = null;
		    
			try 
			{
				ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
				ps.setInt(1, codAgente);
				rs = ps.executeQuery();
				
				if(rs != null)
				{
					return true;
				}
				
			}
			catch(SQLException e)
			{
				return false;
			}
			return false;
	 	 }
		
		 @Override
		 public boolean verifyEmail(String email) throws Exception 
		 {
		    
			String query = "SELECT 1 FROM agente WHERE email = ?";
			
			PreparedStatement ps = null;
			ResultSet rs = null;
			
		    try{
		    	
		    	ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
		    	ps.setString(1, email);
		        rs = ps.executeQuery();
		        
		        return rs.next();
		        
		       } 
		       catch (SQLException e) 
		       {
		        throw e;
		       }
		 }
		
		 @Override
	     public int getIdByEmail(String email) throws Exception 
		 {
		     
			String query = "SELECT cod_agente FROM agente WHERE email = ?";
			
			PreparedStatement ps = null;
			ResultSet rs = null;
			
		    try {
		    	
		    	ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
		        ps.setString(1, email);
		        rs = ps.executeQuery();
		        
		        if (rs.next()) {
		           return rs.getInt("cod_agente");
		        }
		        else {
		           throw new Exception("Agente non trovato");
		        }
		    }
		    finally 
			{
				ConnectionPostgreSQLDB.closeStatement(ps);
			}
		 }	 
		 
		 public void updateAgent(AgenteImmobiliareDTO agente) throws Exception 
		 {
			 	
		 	PreparedStatement ps = null;

		    try 
		    {
		        String query = "UPDATE agente SET nome = ?, cognome = ?, codice_fiscale = ?, data_nascita = ?, genere = ?, città = ?, via = ?, num_civico = ?, email = ?, cod_gestore = ?, cod_agenzia = ?";
		        boolean passwordNuova = agente.getPassword() != null && !agente.getPassword().trim().isEmpty();

		        if (passwordNuova) {
		            query += ", password_c = crypt(?, gen_salt('md5'))";
		        }

		        query += " WHERE cod_agente = ?;";

		        ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);

		        ps.setString(1, agente.getNome());
		        ps.setString(2, agente.getCognome());
		        ps.setString(3, agente.getCodiceFiscale());
		        ps.setDate(4, agente.getDataNascita());
		        ps.setString(5, agente.getGenere());
		        ps.setString(6, agente.getIndirizzo().getCitta());
		        ps.setString(7, agente.getIndirizzo().getVia());
		        ps.setInt(8, agente.getIndirizzo().getCivico());
		        ps.setString(9, agente.getEmail());
		        ps.setInt(10, agente.getCodGestore());
		        ps.setInt(11, agente.getCodAgenzia());

		        int paramIndex = 12;

		        if (passwordNuova) {
		            ps.setString(paramIndex++, agente.getPassword());
		        }

		        ps.setInt(paramIndex, agente.getCodAgente());

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
	     public void deleteAgent(int codAgente) throws Exception
		 {
		    
	    	String query = "DELETE FROM agente WHERE cod_agente = ?;";
		    
		    PreparedStatement ps = null;
		    
			try 
			{
				ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
				ps.setInt(1, codAgente);
				ps.executeUpdate();
			}
			catch(SQLException e)
			{
				throw e;
			}
		 }
	  
		 @Override
	     public int loginAgent(String email, String password) throws Exception
		 {
	    	
	    	String query = "SELECT * FROM agente WHERE email = ? AND password_c = crypt(?, password_c);";
	    	
	    	PreparedStatement ps = null;
			ResultSet rs = null;
	    	
			try 
			{
				ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
				ps.setString(1, email);
				ps.setString(2, password);
				rs = ps.executeQuery();
				
				if (rs.next()) 
				{
		            return rs.getInt("cod_agente");
		        } 
				else 
		        {
		            throw new Exception("Credenziali non valide");
		        }
			} 
			catch(SQLException e) 
			{
				throw e;
			}
			
		 }

}