package org.vesuviano.dieti_estates.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.vesuviano.dieti_estates.connection.ConnectionPostgreSQLDB;
import org.vesuviano.dieti_estates.dao.ClienteDAO;
import org.vesuviano.dieti_estates.dto.ClienteDTO;
import org.vesuviano.dieti_estates.dto.IndirizzoDTO;

public class ClienteDAOImpl implements ClienteDAO
{
	
	@Override
	public ClienteDTO getAllCliente(int codCliente) throws Exception
	{
		String getClienteQuery = "SELECT * FROM cliente WHERE cod_cliente = ?;";
		ClienteDTO cliente = new ClienteDTO();
	
		PreparedStatement ps = null;
		ResultSet rs = null;

		try 
		{
			ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(getClienteQuery);
			ps.setInt(1, codCliente);

			try 
			{
				rs = ps.executeQuery();
	
				if(rs.next())
				{
					cliente = mapResultSetToCliente(rs);
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
 	
		return cliente;
	}

	@Override
	public void registerClient(ClienteDTO cliente) throws Exception
	{
		
		String registerClienteQuery = "INSERT INTO cliente (nome, cognome, codice_fiscale, data_nascita, genere, città, via, num_civico, email, password_c) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, crypt(?, gen_salt('md5')));";
		PreparedStatement ps = null;
	
		try 
		{
			ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(registerClienteQuery);
			ps.setString(1, cliente.getNome());
			ps.setString(2, cliente.getCognome());
			ps.setString(3, cliente.getCodiceFiscale());
			ps.setDate(4, cliente.getDataNascita());
			ps.setString(5, cliente.getGenere());
			ps.setString(6, cliente.getIndirizzo().getCitta());
			ps.setString(7, cliente.getIndirizzo().getVia());
			ps.setInt(8, cliente.getIndirizzo().getCivico());
			ps.setString(9, cliente.getEmail());
			ps.setString(10, cliente.getPassword());

			ps.executeUpdate();
			
		}
		catch(Exception e) 
		{
			e.printStackTrace();
			throw e;
		}
  }
    
	@Override
	public int loginClient(String email, String password) throws Exception 
	{
		String loginClienteEmailQuery = "SELECT cod_cliente FROM cliente WHERE email = ? AND password_c = crypt(?, password_c);";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
	
		try 
		{
			ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(loginClienteEmailQuery);
			ps.setString(1, email);
			ps.setString(2, password);
			rs = ps.executeQuery();
			
			if (rs.next()) 
			{
	            return rs.getInt("cod_cliente");
	        } 
			else 
	        {
	            throw new Exception("Credenziali non valide");
	        }
			
		}
		catch(Exception e) 
		{
			throw e;
		}

	}
	
	@Override
	public boolean verifyEmail(String email) throws Exception 
	{
	    
		String query = "SELECT 1 FROM cliente WHERE email = ?";
		
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
	    
		String query = "SELECT cod_cliente FROM cliente WHERE email = ?";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
	    try {
	    	
	    	ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
	        ps.setString(1, email);
	        rs = ps.executeQuery();
	        
	        if (rs.next()) {
	           return rs.getInt("cod_cliente");
	        }
	        else {
	           throw new Exception("Cliente non trovato");
	        }
	    }
	    finally 
		{
			ConnectionPostgreSQLDB.closeStatement(ps);
		}
	 }
	
	public void updateClient(ClienteDTO cliente) throws Exception 
	{
		 	
	 	PreparedStatement ps = null;

	    try 
	    {
	        String query = "UPDATE cliente SET nome = ?, cognome = ?, codice_fiscale = ?, data_nascita = ?, genere = ?, città = ?, via = ?, num_civico = ?, email = ?";
	        boolean passwordNuova = cliente.getPassword() != null && !cliente.getPassword().trim().isEmpty();

	        if (passwordNuova) {
	            query += ", password_c = crypt(?, gen_salt('md5'))";
	        }

	        query += " WHERE cod_cliente = ?;";

	        ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
			
			ps.setString(1, cliente.getNome());
			ps.setString(2, cliente.getCognome());
			ps.setString(3, cliente.getCodiceFiscale());
			ps.setDate(4, cliente.getDataNascita());
			ps.setString(5, cliente.getGenere());
			ps.setString(6, cliente.getIndirizzo().getCitta());
			ps.setString(7, cliente.getIndirizzo().getVia());
			ps.setInt(8, cliente.getIndirizzo().getCivico());
			ps.setString(9, cliente.getEmail());

	        int paramIndex = 10;

	        if (passwordNuova) {
	            ps.setString(paramIndex++, cliente.getPassword());
	        }

	        ps.setInt(paramIndex, cliente.getCodCliente());

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

	private ClienteDTO mapResultSetToCliente(ResultSet rs) throws SQLException 
	{
		ClienteDTO cliente = new ClienteDTO();
		cliente.setCodCliente(rs.getInt("cod_cliente"));
		cliente.setNome(rs.getString("nome"));
		cliente.setCognome(rs.getString("cognome"));
		cliente.setCodiceFiscale(rs.getString("codice_fiscale"));
		cliente.setDataNascita(rs.getDate("data_nascita"));
		cliente.setGenere(rs.getString("genere"));

		IndirizzoDTO indirizzo = new IndirizzoDTO();
		indirizzo.setCitta(rs.getString("città"));
		indirizzo.setVia(rs.getString("via"));
		indirizzo.setCivico(rs.getInt("num_civico"));
		cliente.setIndirizzo(indirizzo);
	
		cliente.setEmail(rs.getString("email"));

		return cliente;
	}
	
}