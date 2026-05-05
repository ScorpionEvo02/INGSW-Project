package org.vesuviano.dieti_estates.dao;

import org.vesuviano.dieti_estates.dto.ClienteDTO;

public interface ClienteDAO {
	
	public ClienteDTO getAllCliente(int codCliente) throws Exception;
	public void registerClient(ClienteDTO cliente) throws Exception;
	public void updateClient(ClienteDTO cliente) throws Exception;
	public int loginClient(String email, String password) throws Exception;
	public boolean verifyEmail(String email) throws Exception;
    public int getIdByEmail(String email) throws Exception;
	
}
