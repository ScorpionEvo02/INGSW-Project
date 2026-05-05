package org.vesuviano.dieti_estates.dao;

import java.util.ArrayList;

import org.vesuviano.dieti_estates.dto.AgenteImmobiliareDTO;

public interface AgenteDAO 
{
		public AgenteImmobiliareDTO getAllAgent(int codAgente) throws Exception;
		public ArrayList<AgenteImmobiliareDTO> getAllAgenti(int codGestore) throws Exception;
		public int loginAgent(String email, String password) throws Exception;
        public boolean verifyAgent(int codAgente) throws Exception;
        public boolean verifyEmail(String email) throws Exception;
        public int getIdByEmail(String email) throws Exception;
		
		public void insertAgent(AgenteImmobiliareDTO agente) throws Exception;
		public void updateAgent(AgenteImmobiliareDTO agente) throws Exception;
		public void deleteAgent(int codAgente) throws Exception;
		
}
