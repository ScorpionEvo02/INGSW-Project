package org.vesuviano.dieti_estates.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ConnectionPostgreSQLDB {
    private static final String URL = System.getenv("DB_URL");
    private static final String USER = System.getenv("DB_USER");
    private static final String PASSWORD = System.getenv("DB_PASSWORD");
    
    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connessione al database riuscita!");
            } catch (SQLException e) {
                System.err.println("Errore durante la connessione al database.");
                throw e; 
            }
        }
        return connection;
    }
    
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connessione chiusa.");
            } catch (SQLException e) {
                System.err.println("Errore durante la chiusura della connessione: " + e.getMessage());
            }
        }
    }

    public static void closeStatement(Statement st) {
    	if(st != null) {
    		try {
    			st.close();
    		}catch(SQLException e) {
    			
    		}
    	}
    }
    
    public static void closeResultSet(ResultSet rs) {
    	if(rs != null) {
    		try {
    			rs.close();
    		}catch(SQLException e) {
    			
    		}
    	}
    }
}
