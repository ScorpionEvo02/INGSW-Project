package org.vesuviano.dieti_estates.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.vesuviano.dieti_estates.connection.ConnectionPostgreSQLDB;
import org.vesuviano.dieti_estates.dao.AmministratoreDAO;
import org.vesuviano.dieti_estates.dto.AmministratoreDTO;
import org.vesuviano.dieti_estates.dto.IndirizzoDTO;

public class AmministratoreDAOImpl implements AmministratoreDAO
{

		@Override
		public ArrayList<AmministratoreDTO> getAllAdmin(int CodAmministratore) throws Exception
		{
			String getAmministratoreQuery = "SELECT * FROM amministratore WHERE cod_amministratore = ?;";
			ArrayList<AmministratoreDTO> amministratori = new ArrayList<>();
		
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			try 
			{
				ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(getAmministratoreQuery);
				ps.setInt(1, CodAmministratore);
			
				try 
				{
					rs = ps.executeQuery();
				
					while(rs.next())
					{
						amministratori.add(mapResultSetToAmministratore(rs));
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
			 return amministratori;
		
		}
		
		@Override
		public AmministratoreDTO getAllAmministratore(int codAmministratore) throws Exception
		{
			String getAmministratoreQuery = "SELECT * FROM amministratore WHERE cod_amministratore = ?;";
			AmministratoreDTO amministratore = new AmministratoreDTO();
			
			PreparedStatement ps = null;
			ResultSet rs = null;

			try 
			{
				ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(getAmministratoreQuery);
				ps.setInt(1, codAmministratore);

				try 
				{
					rs = ps.executeQuery();
					if(rs.next())
					{
						amministratore = mapResultSetToAmministratore(rs);
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
		 	
			return amministratore;
		}
		
		private AmministratoreDTO mapResultSetToAmministratore(ResultSet rs) throws SQLException 
	    {
			AmministratoreDTO amministratore = new AmministratoreDTO();
			amministratore.setCodAmministratore(rs.getInt("cod_amministratore"));
			amministratore.setNome(rs.getString("nome"));
			amministratore.setCognome(rs.getString("cognome"));
			amministratore.setCodiceFiscale(rs.getString("codice_fiscale"));
			amministratore.setDataNascita(rs.getDate("data_nascita"));
			amministratore.setGenere(rs.getString("genere"));
			
        	IndirizzoDTO indirizzo = new IndirizzoDTO();
        	indirizzo.setCitta(rs.getString("città"));
        	indirizzo.setVia(rs.getString("via"));
        	indirizzo.setCivico(rs.getInt("num_civico"));
        	amministratore.setIndirizzo(indirizzo);
        	
	        amministratore.setPartitaIva(rs.getString("partita_iva"));
	        amministratore.setEmail(rs.getString("email"));
	        amministratore.setCodAmministratoreInsert(rs.getInt("cod_amministratore_insert"));
	        amministratore.setCodAgenzia(rs.getInt("cod_agenzia"));
	        
	        return amministratore;
	    }
		
		@Override
		public void insertAdmin(AmministratoreDTO admin) throws Exception
		{
					
			String query = "INSERT INTO amministratore (nome, cognome, codice_fiscale, data_nascita, genere, città, via, num_civico, partita_iva, email, password_c, cod_amministratore_insert, cod_agenzia) "
						 + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, crypt(?, gen_salt('md5')), ?, ?);";
		
			PreparedStatement ps = null;
		
			try 
			{
				ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
			
				ps.setString(1, admin.getNome());
				ps.setString(2, admin.getCognome());
				ps.setString(3, admin.getCodiceFiscale());
				ps.setDate(4, admin.getDataNascita());
				ps.setString(5, admin.getGenere());
				ps.setString(6, admin.getIndirizzo().getCitta());
				ps.setString(7, admin.getIndirizzo().getVia());
				ps.setInt(8, admin.getIndirizzo().getCivico());
				ps.setString(9,admin.getPartitaIva());
				ps.setString(10, admin.getEmail());
				ps.setString(11, admin.getPassword());
				ps.setInt(12, admin.getCodAmministratoreInsert());
				ps.setInt(13, admin.getCodAgenzia());
		    
				ps.executeUpdate();
			} 
			catch (SQLException e ) 
			{
				throw e;
			}
	 	}

	 
		@Override
	 	public boolean verifyAdmin(int codAmministratore) throws Exception
	 	{
		 
		    String query = "SELECT * FROM amministratore WHERE cod_amministratore = ?;";
		 
		    PreparedStatement ps = null;
		    ResultSet rs = null;
		    
			try 
			{
				ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
				ps.setInt(1, codAmministratore);
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
				    
			String query = "SELECT 1 FROM amministratore WHERE email = ?";
				
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
	     public int getIdByEmail(String email) throws Exception {
		    
			String query = "SELECT cod_amministratore FROM amministratore WHERE email = ?";
			
			PreparedStatement ps = null;
			ResultSet rs = null;
			
		    try 
		    {
		    	
		    	ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
		        ps.setString(1, email);
		        rs = ps.executeQuery();
		        
		        if (rs.next()) {
		           return rs.getInt("cod_amminstratore");
		        }
		        else 
		        {
		           throw new Exception("Amministratore non trovato");
		        }
		    }
		    finally 
			{
				ConnectionPostgreSQLDB.closeStatement(ps);
			}
		 }
	 
		 public void updateAdmin(AmministratoreDTO amministratore) throws Exception 
		 {
			 	
		 	PreparedStatement ps = null;

		    try 
		    {
		        String query = "UPDATE amministratore SET nome = ?, cognome = ?, codice_fiscale = ?, data_nascita = ?, genere = ?, città = ?, via = ?, num_civico = ?, partita_iva = ?, email = ?, cod_amministratore_insert = ?, cod_agenzia = ?";
		        boolean passwordNuova = amministratore.getPassword() != null && !amministratore.getPassword().trim().isEmpty();

		        if (passwordNuova) {
		            query += ", password_c = crypt(?, gen_salt('md5'))";
		        }

		        query += " WHERE cod_amministratore = ?;";

		        ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);

		        ps.setString(1, amministratore.getNome());
				ps.setString(2, amministratore.getCognome());
				ps.setString(3, amministratore.getCodiceFiscale());
				ps.setDate(4, amministratore.getDataNascita());
				ps.setString(5, amministratore.getGenere());
				ps.setString(6, amministratore.getIndirizzo().getCitta());
				ps.setString(7, amministratore.getIndirizzo().getVia());
				ps.setInt(8, amministratore.getIndirizzo().getCivico());
				ps.setString(9, amministratore.getPartitaIva());
				ps.setString(10, amministratore.getEmail());
				ps.setInt(11, amministratore.getCodAmministratoreInsert());
				ps.setInt(12, amministratore.getCodAgenzia());

		        int paramIndex;
		        if (passwordNuova) {
		            ps.setString(13, amministratore.getPassword());
		            paramIndex = 14;
		        } else {
		            paramIndex = 13;
		        }

		        ps.setInt(paramIndex, amministratore.getCodAmministratore());
		        
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
	 
		 @Override
	     public void deleteAdmin(int codAmministratore) throws Exception
		 {
		    
	    	String query = "DELETE FROM amministatore WHERE cod_amministratore = ?;";
		    
		    PreparedStatement ps = null;
		    
			try 
			{
				ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
				ps.setInt(1, codAmministratore);
				ps.executeUpdate();
			}
			catch(SQLException e)
			{
				throw e;
			}
		 }
	  
		 @Override
	     public int loginAdmin(String email, String password) throws Exception
		 {
	    	
	    	String query = "SELECT * FROM amministratore WHERE email = ? AND password_c = crypt(?, password_c);";
	    	
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
		            return rs.getInt("cod_amministratore");
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
		public void changePassword(int codAmministratore, String password) throws Exception
		{
			String query = "UPDATE amministratore SET password_c = crypt(?, gen_salt('md5')) WHERE cod_amministratore = ?";
			
			PreparedStatement ps = null;
			
			try
			{
				
				ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
				ps.setString(1, password);
				ps.setInt(2, codAmministratore);
	            ps.executeUpdate();
		    }
			catch(SQLException e)
			{
				throw e;
			}
		}
		
}
