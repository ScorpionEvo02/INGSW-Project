package org.vesuviano.dieti_estates.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.vesuviano.dieti_estates.connection.ConnectionPostgreSQLDB;
import org.vesuviano.dieti_estates.dto.GestoreAgenziaDTO;

public class GestoreAgenziaDAOImpl {
	
	private Statement statement;
	private PreparedStatement ps;
	private ResultSet rs;
	// private Controller control;
	
	public GestoreAgenziaDAOImpl(/*Controller control*/) {
		
	}  	
	
	 public int InserimentoGestore(GestoreAgenziaDTO gestore){
			
			int inserireGestore = 0;
			
			String query = "insert into gestore (CodGestore, Nome, Cognome, CodiceFiscale, Data_Nascita, Genere, Indirizzo, pIva,  Email, Password, CodGestore, CodAgenzia, IdSessione)"
				       + "values ('"+gestore.getCodGestore()+"','"+gestore.getNome()+"','"+gestore.getCognome()+"','"+gestore.getCodiceFiscale()+"','"+gestore.getDataNascita()+"','"+gestore.getGenere()+"','"+gestore.getIndirizzo()+"','"+gestore.getpIva()+"','"+gestore.getEmail()+"','"+gestore.getPassword()+"','"+gestore.getCodAmministratore()+"','"+gestore.getCodAgenzia()+"','"+gestore.getIdSessione()+"' );";
			try 
			{
				inserireGestore = statement.executeUpdate(query);
			    return inserireGestore;
			} catch (SQLException e ) 
			{
				e.printStackTrace();
				return inserireGestore;
			}
		}
	 
	 public boolean verificaGestore(int codGestore)
		{
		 
		    String query = "select * from gestore where cod_gestore like '"+codGestore+"' ;";
		 
			try {
				rs = statement.executeQuery(query);
				return rs.next();
				
			}catch(SQLException e)
			{
				e.printStackTrace();
				return false;
			}
		}
	 
	 public void ModificaGestore(GestoreAgenziaDTO gestore) {
		 
		    String query = "update gestore set cod_gestore = ? , nome = ?, cognome = ?, codicefiscale = ?, data_nascita = ?, genere = ?, indirizzo = ?, pIva = ?, email = ?, password = ?, cod_amministratore = ?, cod_agenzia = ?, id_sessione = ? where cod_gestore = ?;";
			
			try 
			{

				   ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
				
					ps.setInt(1, gestore.getCodGestore());
					ps.setString(2, gestore.getNome());
					ps.setString(3, gestore.getCognome());
					ps.setString(4, gestore.getCodiceFiscale());
					//ps.setDate(5, gestore.getDataNascita());     //da vedere
					ps.setInt(6, gestore.getGenere());
					ps.setObject(7, gestore.getIndirizzo());       //da vedere
					ps.setInt(8, gestore.getpIva());
					ps.setString(9, gestore.getEmail());
					ps.setString(10, gestore.getPassword());
					ps.setInt(11, gestore.getCodAmministratore());
					ps.setInt(12, gestore.getCodAgenzia());
					ps.setString(13, gestore.getIdSessione());

					ps.executeUpdate();

			}catch (SQLException e) {

			  e.printStackTrace();

			}

		}
	 
	  public int EliminaGestore(int codGestore)
		{
		    int eliminaGestore = 0;
		    
		    String query = "delete from gestore where cod_gestore = '"+codGestore+"' ;";
		    		
			try {
				eliminaGestore = statement.executeUpdate(query);
				return eliminaGestore;
				
			}catch(SQLException e)
			{
				e.printStackTrace();
				return eliminaGestore;
			}
		}
	  
	  public boolean verificaIngresso(String email , String password)
		{
		  
		   String query = "select * from gestore where email='"+email+"' and password='"+password+"' ;";
		  
			try {
				rs = statement.executeQuery(query);
				return rs.next();			
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
	
}
