package org.vesuviano.dieti_estates.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.vesuviano.dieti_estates.connection.ConnectionPostgreSQLDB;
import org.vesuviano.dieti_estates.dao.SessioneDAO;

public abstract class SessioneDAOImpl implements SessioneDAO{
	
    protected int getCodice(String query, String sessioneId) throws Exception {
        
    	try
        {
        	PreparedStatement ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
            ps.setString(1, sessioneId);
            
            try
            {
            	ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            catch(SQLException e)
            {
            	return 0;
            }
        }
        catch (SQLException e)
        {
        	throw e;
        }
        return 0;
    }

    protected void insertSessione(String query, int codUtente, String sessioneId) throws Exception {
        
    	try
        {
        	PreparedStatement ps = ConnectionPostgreSQLDB.getConnection().prepareStatement(query);
            ps.setInt(1, codUtente);
            ps.setString(2, sessioneId);
            ps.executeUpdate();
        }
        catch(SQLException e)
        {
        	throw e;
        }
    }
}
