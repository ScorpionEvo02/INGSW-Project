package org.vesuviano.dieti_estates.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.vesuviano.dieti_estates.connection.ConnectionPostgreSQLDB;
import org.vesuviano.dieti_estates.dto.AgenteImmobiliareDTO;

public class AgenteDAOImpl {
	
	private Statement statement;
	private PreparedStatement ps;
	private ResultSet rs;
	// private Controller control;
	
	public AgenteDAOImpl(/*Controller control*/) {

		
	}  	
	
	 public int InserimentoAgente(AgenteImmobiliareDTO agente){
			
			int inserireAgente = 0;
			
			String query = "insert into agente (CodAgente, Nome, Cognome, CodiceFiscale, Data_Nascita, Genere, Email, Password, CodGestore, CodAgenzia, IdSessione)"
		                 + "values ('"+agente.getCodAgente()+"','"+agente.getNome()+"','"+agente.getCognome()+"','"+agente.getCodiceFiscale()+"','"+agente.getDataNascita()+"','"+agente.getGenere()+"','"+agente.getIndirizzo()+"','"+agente.getEmail()+"','"+agente.getPassword()+"','"+agente.getCodGestore()+"','"+agente.getCodAgenzia()+"','"+agente.getIdSessione()+"' );";
			try 
			{
				inserireAgente = statement.executeUpdate(query);
			    return inserireAgente;
			} catch (SQLException e ) 
			{
				System.err.println("Errore durante l'inserimento dell'agente: " + e.getMessage());
				return inserireAgente;
			}
		}
	 
	 public boolean verificaAgente(int codAgente)
		{
		 
		 String query = "select * from agente where cod_agente like '"+codAgente+"' ;";
		 
			try {
				rs = statement.executeQuery(query);
				return rs.next();
				
			}catch(SQLException e)
			{
				System.err.println("Errore: " + e.getMessage());
				return false;
			}
		}
	 
	 public void ModificaAgente(AgenteImmobiliareDTO agente) {
		 
		 String query = "update agente set cod_agente = ? , nome = ?, cognome = ?, codicefiscale = ?, data_nascita = ?, genere = ?, indirizzo = ?, email = ?, password = ?, cod_gestore = ?, cod_agenzia = ?, id_sessione = ? where cod_agente = ?;";
			
			try
			{

				ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
		
					ps.setInt(1, agente.getCodAgente());
					ps.setString(2, agente.getNome());
					ps.setString(3, agente.getCognome());
					ps.setString(4, agente.getCodiceFiscale());
					//ps.setDate(5, agente.getDataNascita());     //da vedere
					ps.setInt(6, agente.getGenere());
					//ps.setObject(7, agente.getIndirizzo());       //da vedere
					ps.setString(8, agente.getEmail());           
					ps.setString(9, agente.getPassword());
					ps.setInt(10, agente.getCodGestore());
					ps.setInt(11, agente.getCodAgenzia());
					ps.setString(12, agente.getIdSessione());

					ps.executeUpdate();

			}catch (SQLException e) {

			  e.printStackTrace();

			}

		}
	 
	  public int EliminaAgente(int codAgente)
		{
		    int eliminaAgente = 0;
			try {
				eliminaAgente = statement.executeUpdate("delete from agente where cod_agente = '"+codAgente+"' ;");
				return eliminaAgente;
				
			}catch(SQLException e)
			{
				System.err.println("Errore durante l'eliminazione dell'agente: " + e.getMessage());
				return eliminaAgente;
			}
		}
	  
	  public boolean verificaIngresso(String email, String password)
		{
			try {
				rs = statement.executeQuery("select * from agente where email='"+email+"' and password='"+password+"' ;");
				return rs.next();			
			} catch (SQLException e) {
				System.err.println("Errore durante il login dell'agente: " + e.getMessage());
				return false;
			}
		}

}
