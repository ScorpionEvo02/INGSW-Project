package org.vesuviano.dieti_estates.dao;

import java.util.ArrayList;

import org.vesuviano.dieti_estates.dto.AmministratoreDTO;

public interface AmministratoreDAO {
	
	public ArrayList<AmministratoreDTO> getAllAdmin(int codAmministratore) throws Exception;
	public AmministratoreDTO getAllAmministratore(int amministratoreId) throws Exception;
	public int loginAdmin(String email, String password) throws Exception;
	public boolean verifyAdmin(int codAmministratore) throws Exception;
	public boolean verifyEmail(String email) throws Exception;
    public int getIdByEmail(String email) throws Exception;
	
	public void insertAdmin(AmministratoreDTO admin) throws Exception;
	public void updateAdmin(AmministratoreDTO amministratore) throws Exception;
	public void deleteAdmin(int codAmministratore) throws Exception; 
	public void changePassword(int codAmministratore, String password) throws Exception; 
	
}