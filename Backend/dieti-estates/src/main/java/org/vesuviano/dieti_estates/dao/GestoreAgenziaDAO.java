package org.vesuviano.dieti_estates.dao;

import java.util.ArrayList;

import org.vesuviano.dieti_estates.dto.GestoreAgenziaDTO;

public interface GestoreAgenziaDAO {
	
	public ArrayList<GestoreAgenziaDTO> getAllManager(int codAdmin) throws Exception;
	public GestoreAgenziaDTO getAllGestore(int gestoreId) throws Exception;
	public int loginManager(String email, String password) throws Exception;
	public boolean verifyManager(int codGestore) throws Exception;
	public boolean verifyEmail(String email) throws Exception;
    public int getIdByEmail(String email) throws Exception;
	
	public void insertManager(GestoreAgenziaDTO gestore) throws Exception;
	public void updateManager(GestoreAgenziaDTO gestore) throws Exception;
	public void deleteManager(int codGestore) throws Exception; 
	public void changePassword(int codGestore,String Password) throws Exception;

}

