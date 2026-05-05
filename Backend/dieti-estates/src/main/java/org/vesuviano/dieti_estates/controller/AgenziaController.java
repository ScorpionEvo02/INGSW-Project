package org.vesuviano.dieti_estates.controller;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.vesuviano.dieti_estates.dao.impl.AgenziaDAOImpl;
import org.vesuviano.dieti_estates.dao.AgenziaDAO;
import org.vesuviano.dieti_estates.dto.AgenziaDTO;


@RestController
@RequestMapping("/agenzia")
public class AgenziaController {

	private AgenziaDAO agenziaDAO = new AgenziaDAOImpl();
	
	@GetMapping("/")
	public ResponseEntity<ArrayList<AgenziaDTO>> getAllAgenzie() 
	{
        try
        {
        	ArrayList<AgenziaDTO> agenzie = agenziaDAO.getAllAgenzie();
            return ResponseEntity.ok(agenzie);
        }
        catch(Exception e)
        {
        	return ResponseEntity.status(500).body(null);
        }
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<AgenziaDTO> getAgenziaById(@PathVariable int id) 
	{
        try
        {
        	AgenziaDTO agenzia = agenziaDAO.getAgenziaById(id);
            return ResponseEntity.ok(agenzia);
        }
        catch(Exception e)
        {
        	return ResponseEntity.status(404).body(null);
        }
    }
	
	@PutMapping("/")
    public ResponseEntity<String> insertAgenzia(@RequestHeader("uid_amministratore")String sessioneId, @RequestBody AgenziaDTO agenzia) 
    {
        try
        {
        	int codAgente = SessioneController.getAmministratoreId(sessioneId);
        	
        	if(codAgente == -1)
        		throw new Exception();
        	
        	try
        	{
        		agenziaDAO.insertAgenzia(agenzia);
                return ResponseEntity.status(201).body("Inserimento agenzia riuscita.");
        		
        	}
        	catch(Exception e)
        	{
            	return ResponseEntity.status(500).body("Errore nell'inserimento: problema server.");
        	}
        }
        catch(Exception e)
        {
        	return ResponseEntity.status(404).body("Errore nell'inserimento: agente non trovato.");
        }
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateImmobile(@RequestHeader("uid_amministratore")String sessioneId, @PathVariable int id, @RequestBody AgenziaDTO agenzia) 
    {
    	try
        {
        	int codAgente = SessioneController.getAmministratoreId(sessioneId);
        	
        	if(codAgente == -1)
        		throw new Exception();
        	
        	try
        	{
        		agenziaDAO.updateAgenzia(agenzia);
                return ResponseEntity.status(201).body("Eliminazione agenzia riuscita.");
        		
        	}
        	catch(Exception e)
        	{
            	return ResponseEntity.status(500).body("Errore nella cancellazione: problema server.");
        	}
        }
        catch(Exception e)
        {
        	return ResponseEntity.status(404).body("Errore nella cancellazione: agente non trovato.");
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteImmobile(@RequestHeader("uid_amministratore")String sessioneId, @PathVariable int id) 
    {
    	try
        {
        	int codAgente = SessioneController.getAmministratoreId(sessioneId);
        	
        	if(codAgente == -1)
        		throw new Exception();
        	
        	try
        	{
                agenziaDAO.removeAgenzia(id);
                return ResponseEntity.status(201).body("Eliminazione agenzia riuscita.");
        		
        	}
        	catch(Exception e)
        	{
            	return ResponseEntity.status(500).body("Errore nella cancellazione: problema server.");
        	}
        }
        catch(Exception e)
        {
        	return ResponseEntity.status(404).body("Errore nella cancellazione: agente non trovato.");
        }
    }
}
