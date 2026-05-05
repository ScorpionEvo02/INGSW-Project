package org.vesuviano.dieti_estates.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.vesuviano.dieti_estates.connection.ConnectionPostgreSQLDB;
import org.vesuviano.dieti_estates.dto.OffertaDTO;

public class OffertaDAOImpl {

	private Statement statement;
	private PreparedStatement ps;
	private ResultSet rs;
	// private Controller control;
	
	public OffertaDAOImpl(/*Controller control*/) {
		
		
	}  	
	
	 public int InserimentoOfferta(OffertaDTO offerta){
			
			int inserireOfferta = 0;
			
			String query = "insert into offerta (CodOfferta, StatoOfferta, Ammontare, CodCliente, CodImmobile, CodAgente)"
				         + "values ('"+offerta.getCodOfferta()+"','"+offerta.getStatoOfferta()+"','"+offerta.getAmmontare()+"','"+offerta.getCodCliente()+"','"+offerta.getCodImmobile()+"','"+offerta.getCodAgente()+"' );";
			
			try 
			{
				inserireOfferta = statement.executeUpdate(query);
			    return inserireOfferta;
			} catch (SQLException e ) 
			{
				System.err.println("Errore durante l'inserimento dell'offerta: " + e.getMessage());
				return inserireOfferta;
			}
		}
	 
	 public boolean AccettazioneOfferta(int codOfferta, int codAgente) {
		 
	        try
	        {
	    
	        	ps = ConnectionPostgreSQLDB.getConnection().prepareStatement("update Offerta set StatoOfferta = ?, CodAgente = ? WHERE CodOfferta = ? AND StatoOfferta = ?;");
	        	
	        	ps.setString(1, "Accettata");
	        	ps.setInt(2, codAgente);
	        	ps.setInt(3, codOfferta);
	        	ps.setString(4, "Pendente");
	           
	            int offerteAccettate = ps.executeUpdate();
	            
	            return offerteAccettate > 0;
	            
	        }catch (SQLException e) {
	        	System.err.println("Errore durante l'accettazione dell'offerta: " + e.getMessage());
	            return false;
	        }
	 }
	 
	 public boolean RifiutoOfferta(int codOfferta, int codAgente) {
		 
		    String query = "update Offerta set StatoOfferta = ?, CodAgente = ? WHERE CodOfferta = ? AND StatoOfferta = ?;";
		 
	        try 
	        {
	    
	        	ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
	        	
	        	ps.setString(1, "Rifiutata");
	        	ps.setInt(2, codAgente);
	        	ps.setInt(3, codOfferta);
	        	ps.setString(4, "Pendente");
	           
	            int offerteRifiutate = ps.executeUpdate();
	            
	            return offerteRifiutate > 0;
	            
	        }catch (SQLException e) {
	        	System.err.println("Errore durante il rifiuto dell'offerta: " + e.getMessage());
	            return false;
	        }
	 }
	 
	 public void modificaOffertaDaAgente(int codOfferta, int codAgente) {
		 
		 String query = "update Offerta set StatoOfferta = ?, CodAgente = ? WHERE CodOfferta = ? AND StatoOfferta = ?;";
		 
		 try
		 {
			    
			    ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
			    
	        	ps.setString(1, "Rifiutata");
	        	ps.setInt(2, codAgente);
	        	ps.setInt(3, codOfferta);
	        	ps.setString(4, "Pendente");
	           
	            ps.executeUpdate();
	            
	         
	            
	        }catch (SQLException e) {
	        	System.err.println("Errore durante la modifica dell'offerta: " + e.getMessage());
	        }
	 }
	 
	 public void modificaOffertaDaGestore(OffertaDTO offerta, int codOfferta, int codGestore) {
		 
		 String query = "update Offerta set StatoOfferta = ?, Ammontare = ?, CodCliente = ? WHERE CodOfferta = ? AND CodGestore = ?;";
		 
		 try
		 {
			    
			    ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
			  
	        	ps.setString(1, offerta.getStatoOfferta());
	        	ps.setDouble(2, offerta.getAmmontare());
	        	ps.setInt(3, offerta.getCodCliente());
	        	ps.setInt(4, codOfferta);
	        	ps.setInt(5, codGestore);
	           
	            ps.executeUpdate();
	            
	        }catch (SQLException e) {
	        	System.err.println("Errore durante la modifica dell'offerta: " + e.getMessage());
	        }
	 }
	 
	 public void modificaOffertaDaAgente(OffertaDTO offerta, int codOfferta, int codAgente) {
		 
		 String query = "update Offerta set StatoOfferta = ?, Ammontare = ?, CodCliente = ? WHERE CodOfferta = ? AND CodAgente = ?;";
		 
		 try
		 {
			    
			    ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
			     
	        	ps.setString(1, offerta.getStatoOfferta());
	        	ps.setDouble(2, offerta.getAmmontare());
	        	ps.setInt(3, offerta.getCodCliente());
	        	ps.setInt(4, codOfferta);
	        	ps.setInt(5, codAgente);
	           
	            ps.executeUpdate();
	            
	        }catch (SQLException e) {
	        	System.err.println("Errore durante la modifica dell'offerta: " + e.getMessage());
	        }
	 }
	
}
