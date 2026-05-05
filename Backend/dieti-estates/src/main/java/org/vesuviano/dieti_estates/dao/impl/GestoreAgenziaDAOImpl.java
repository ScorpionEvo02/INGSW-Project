package org.vesuviano.dieti_estates.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.vesuviano.dieti_estates.connection.ConnectionPostgreSQLDB;
import org.vesuviano.dieti_estates.dao.GestoreAgenziaDAO;
import org.vesuviano.dieti_estates.dto.GestoreAgenziaDTO;
import org.vesuviano.dieti_estates.dto.IndirizzoDTO;

public class GestoreAgenziaDAOImpl implements GestoreAgenziaDAO{		
	
	    @Override
		public ArrayList<GestoreAgenziaDTO> getAllManager(int codAdmin) throws Exception
		{
	    	
			String getGestoreQuery = "SELECT * FROM gestore WHERE cod_amministratore = ?;";
			ArrayList<GestoreAgenziaDTO> gestori = new ArrayList<>();
		
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			try 
			{ 
		    	ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(getGestoreQuery);
				ps.setInt(1, codAdmin);
			
				try 
				{
					rs = ps.executeQuery();
				    
			    	while(rs.next())
					{
						gestori.add(mapResultSetToGestore(rs));
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
			return gestori;
		
		}
	    
	    @Override
		public GestoreAgenziaDTO getAllGestore(int codGestore) throws Exception
		{
	    	System.out.println("sono in getAllgestore.");
			String getGestoreQuery = "SELECT * FROM gestore WHERE cod_gestore = ?;";
			GestoreAgenziaDTO gestore = new GestoreAgenziaDTO();
			
			PreparedStatement ps = null;
			ResultSet rs = null;

			try 
			{
				System.out.println("sono nel try getAllgestore.");
				ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(getGestoreQuery);
				ps.setInt(1, codGestore);

				try 
				{
					rs = ps.executeQuery();
			
					if(rs.next())
					{
						gestore = mapResultSetToGestore(rs);
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
		 	
			return gestore;
		}
		
		private GestoreAgenziaDTO mapResultSetToGestore(ResultSet rs) throws SQLException 
	    {
			
			GestoreAgenziaDTO gestore = new GestoreAgenziaDTO();
			
	        gestore.setCodGestore(rs.getInt("cod_gestore"));
	        gestore.setNome(rs.getString("nome"));
	        gestore.setCognome(rs.getString("cognome"));
	        gestore.setCodiceFiscale(rs.getString("codice_fiscale"));
	        gestore.setDataNascita(rs.getDate("data_nascita"));
	        gestore.setGenere(rs.getString("genere"));
	        IndirizzoDTO indirizzo = new IndirizzoDTO();
	        
	        indirizzo.setCitta(rs.getString("città"));
	        indirizzo.setVia(rs.getString("via"));
	        indirizzo.setCivico(rs.getInt("num_civico"));
	        gestore.setIndirizzo(indirizzo);
	        
	        gestore.setEmail(rs.getString("email"));
	        gestore.setCodAmministratore(rs.getInt("cod_amministratore"));
	        gestore.setCodAgenzia(rs.getInt("cod_agenzia"));
	        
	        return gestore;
	    }
		
		@Override
		public void insertManager(GestoreAgenziaDTO manager) throws Exception
	 	{
			
			String query = "INSERT INTO gestore (nome, cognome, codice_fiscale, data_nascita, genere, città, via, num_civico, email, password_c, cod_amministratore, cod_agenzia)"
			         	 + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, crypt(?, gen_salt('md5')), ?, ?);";
		
			PreparedStatement ps = null;
			
			try 
			{
				ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
			
				ps.setString(1, manager.getNome());
				ps.setString(2, manager.getCognome());
				ps.setString(3, manager.getCodiceFiscale());
				ps.setDate(4, manager.getDataNascita());
				ps.setString(5, manager.getGenere());
				ps.setString(6, manager.getIndirizzo().getCitta());
				ps.setString(7, manager.getIndirizzo().getVia());
				ps.setInt(8, manager.getIndirizzo().getCivico());
				ps.setString(9, manager.getEmail());
				ps.setString(10, manager.getPassword());
				ps.setInt(11, manager.getCodAmministratore());
				ps.setInt(12, manager.getCodAgenzia());
		    
				ps.executeUpdate();
			} 
			catch (SQLException e ) 
			{
				throw e;
			}
	 	}
		
		public void updateManager(GestoreAgenziaDTO gestore) throws Exception 
		{
			 	
		 	PreparedStatement ps = null;

		    try 
		    {
		        String query = "UPDATE gestore SET nome = ?, cognome = ?, codice_fiscale = ?, data_nascita = ?, genere = ?, città = ?, via = ?, num_civico = ?, email = ?, cod_amministratore = ?, cod_agenzia = ?";
		        boolean passwordNuova = gestore.getPassword() != null && !gestore.getPassword().trim().isEmpty();

		        if (passwordNuova) {
		            query += ", password_c = crypt(?, gen_salt('md5'))";
		        }

		        query += " WHERE cod_gestore = ?;";

		        ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
		 		
		 		ps.setString(1, gestore.getNome());
		 		ps.setString(2, gestore.getCognome());
		 		ps.setString(3, gestore.getCodiceFiscale());
		 		ps.setDate(4, gestore.getDataNascita());
		 		ps.setString(5, gestore.getGenere());
		 		ps.setString(6, gestore.getIndirizzo().getCitta());
		 		ps.setString(7, gestore.getIndirizzo().getVia());
		 		ps.setInt(8, gestore.getIndirizzo().getCivico());
		 		ps.setString(9, gestore.getEmail());
		 		ps.setInt(10, gestore.getCodAmministratore());
		 		ps.setInt(11, gestore.getCodAgenzia());
		 		
		        int paramIndex = 12;

		        if (passwordNuova) {
		            ps.setString(paramIndex++, gestore.getPassword());
		        }

		        ps.setInt(paramIndex, gestore.getCodGestore());

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
	 	public boolean verifyManager(int codGestore) throws Exception
	 	{
		 
		    String query = "SELECT * FROM gestore WHERE cod_gestore = ?;";
		 
		    PreparedStatement ps = null;
		    ResultSet rs = null;
		    
			try 
			{
				ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
				ps.setInt(1, codGestore);
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
			    
			String query = "SELECT 1 FROM gestore WHERE email = ?";
			
			PreparedStatement ps = null;
			ResultSet rs = null;
			
		    try
		    {
		    	
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
		    
			String query = "SELECT cod_gestore FROM gestore WHERE email = ?";
			
			PreparedStatement ps = null;
			ResultSet rs = null;
			
		    try 
		    {
		    	
		    	ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
		        ps.setString(1, email);
		        rs = ps.executeQuery();
		        
		        if (rs.next()) {
		           return rs.getInt("cod_gestore");
		        }
		        else {
		           throw new Exception("Gestore non trovato");
		        }
		    }
		    finally 
			{
				ConnectionPostgreSQLDB.closeStatement(ps);
			}
		 }
	 
		 @Override
	     public void deleteManager(int codGestore) throws Exception
		 {
		    
	    	String query = "DELETE FROM gestore WHERE cod_gestore = ?;";
		    
		    PreparedStatement ps = null;
		    
			try 
			{
				ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
				ps.setInt(1, codGestore);
				ps.executeUpdate();
			}
			catch(SQLException e)
			{
				throw e;
			}
		 }
	  
		 @Override
	     public int loginManager(String email, String password) throws Exception
		 {
	    	
	    	String query = "SELECT * FROM gestore WHERE email = ? AND password_c = crypt(?, password_c);";
	    	
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
		            return rs.getInt("cod_gestore");
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
		 
		 @Override
		public void changePassword(int codGestore, String password) throws Exception
		{
			String query = "UPDATE gestore SET password_c = crypt(?, gen_salt('md5')) WHERE cod_amministratore = ?";
				
			PreparedStatement ps = null;
				
			try
			{
					
				ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
				ps.setString(1, password);
				ps.setInt(2, codGestore);
		        ps.executeUpdate();
			}
			catch(SQLException e)
			{
				throw e;
			}
		}

}
