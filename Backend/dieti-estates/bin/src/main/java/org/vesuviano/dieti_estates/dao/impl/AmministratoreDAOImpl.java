package org.vesuviano.dieti_estates.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.vesuviano.dieti_estates.connection.ConnectionPostgreSQLDB;
import org.vesuviano.dieti_estates.dto.AmministratoreDTO;

public class AmministratoreDAOImpl {

	private Statement statement;
	private PreparedStatement ps;
	private ResultSet rs;
	//private Controller control;
	
	public AmministratoreDAOImpl(/*Controller control*/) {
		
		
	}  	
	
	public int InserimentoAdmin(AmministratoreDTO admin){
			
			int inserireAdmin = 0;
			
			String query = "insert into amministratore (CodAmministratore, Nome, Cognome, CodiceFiscale, Data_Nascita, Genere, Indirizzo, pIva, Email, Password, CodAmministratoreInsert, IdSessione)"
				         + "values ('"+admin.getCodAmministratore()+"','"+admin.getNome()+"','"+admin.getCognome()+"','"+admin.getCodiceFiscale()+"','"+admin.getDataNascita()+"','"+admin.getGenere()+"','"+admin.getIndirizzo()+"','"+admin.getpIva()+"','"+admin.getEmail()+"','"+admin.getPassword()+"','"+admin.getCodAmministratoreInsert()+"','"+admin.getIdSessione()+"' );";
			try 
			{
				inserireAdmin = statement.executeUpdate(query);
			    return inserireAdmin;
			} catch (SQLException e ) 
			{
				e.printStackTrace();
				return inserireAdmin;
			}
	}
	 
	public boolean verificaAdmin(int codAmministratore)
		{
		
		 String query = "select * from amministratore where cod_amministratore like '"+codAmministratore+"' ;";
		
			try {
				rs = statement.executeQuery(query);
				return rs.next();
				
			}catch(SQLException e)
			{
				e.printStackTrace();
				return false;
			}
	}
	 
	public void ModificaAdmin(AmministratoreDTO admin) {
		
		String query = "update admin set cod_amministratore = ? , nome = ?, cognome = ?, codicefiscale = ?, data_nascita = ?, genere = ?, indirizzo = ?, pIva = ?, email = ?, password = ?, cod_amministratore_insert = ?, id_sessione = ? where cod_amministratore = ?;";
			
			try 
			{

				PreparedStatement ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
				
					ps.setInt(1, admin.getCodAmministratore());
					ps.setString(2, admin.getNome());
					ps.setString(3, admin.getCognome());
					ps.setString(4, admin.getCodiceFiscale());
					//ps.setDate(5, admin.getDataNascita());     //da vedere
					ps.setInt(6, admin.getGenere());
					ps.setObject(7, admin.getIndirizzo());       //da vedere
					ps.setInt(8, admin.getpIva());
					ps.setString(9, admin.getEmail());
					ps.setString(10, admin.getPassword());
					ps.setInt(11, admin.getCodAmministratoreInsert());
					ps.setString(12, admin.getIdSessione());

					ps.executeUpdate();

			}catch (SQLException e) {

			  e.printStackTrace();

			}

	}
	 
	public int EliminaAdmin(int codAmministratore)
		{
		    int eliminaAmministratore = 0;
		    
		    String query = "delete from amministratore where cod_amministratore = '"+codAmministratore+"' ;";
		    
			try {
				eliminaAmministratore = statement.executeUpdate(query);
				return eliminaAmministratore;
				
			}catch(SQLException e)
			{
				e.printStackTrace();
				return eliminaAmministratore;
			}
	}
	  
	public boolean verificaIngresso(String email , String password)
		{
		
		   String query = "select * from amministratore where email='"+email+"' and password='"+password+"' ;";
		
			try {
				rs = statement.executeQuery(query);
			    return rs.next();			
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
	}
	
}
