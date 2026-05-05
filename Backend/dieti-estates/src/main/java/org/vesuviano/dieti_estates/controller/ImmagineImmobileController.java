package org.vesuviano.dieti_estates.controller;

import java.util.ArrayList;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vesuviano.dieti_estates.dao.ImmagineImmobileDAO;
import org.vesuviano.dieti_estates.dao.impl.ImmagineImmobileDAOImpl;
import org.vesuviano.dieti_estates.dto.ImmagineImmobileDTO;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/immagine_immobile")
public class ImmagineImmobileController {

	private ImmagineImmobileDAO immagineImmobileDAO = new ImmagineImmobileDAOImpl();
	
	@GetMapping("/{id}")
	public ResponseEntity<ArrayList<ImmagineImmobileDTO>> getImmaginiImmobile(@PathVariable int id) 
	{
        try
        {
        	ArrayList<ImmagineImmobileDTO> immaginiImmobile = immagineImmobileDAO.getImmaginiImmobile(id);
            return ResponseEntity.ok(immaginiImmobile);
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        	return ResponseEntity.status(500).body(null);
        }
    }
	
	@PutMapping("/{codImmobile}")
    public ResponseEntity<String> insertImmagine(@RequestHeader("uid_agente")String sessioneId, @PathVariable int codImmobile, @RequestBody String immagine) 
    {
        try
        {
        	int codAgente = SessioneController.getAgenteId(sessioneId);
        	
        	if(codAgente == -1)
        		throw new Exception();
        	System.out.println(codImmobile+" "+immagine);
        	ImmagineImmobileDTO immagineImmobile = new ImmagineImmobileDTO();
        	immagineImmobile.setCodImmobile(codImmobile);
        	immagineImmobile.setImmagine(immagine);
        	
        	try
        	{
        		immagineImmobileDAO.insertImmagineImmobile(immagineImmobile);
                return ResponseEntity.status(201).contentType(MediaType.TEXT_PLAIN).body("Inserimento immagine immobile riuscita.");
        		
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
            	return ResponseEntity.status(500).contentType(MediaType.TEXT_PLAIN).body("Errore nell'inserimento: problema server.");
        	}
        }
        catch(Exception e)
        {
        
        	return ResponseEntity.status(404).contentType(MediaType.TEXT_PLAIN).body("Errore nell'inserimento: agente non trovato.");
        }
    }
	
	@DeleteMapping("/{id}")
    public ResponseEntity<String> deleteImmobile(@RequestHeader("uid_agente")String sessioneId, @PathVariable int id) 
    {
    	try
        {
        	int codAgente = SessioneController.getAgenteId(sessioneId);
        	
        	if(codAgente == -1)
        		throw new Exception();
        	
        	try
        	{
        		immagineImmobileDAO.removeImmagineImmobile(id);
                return ResponseEntity.status(201).contentType(MediaType.TEXT_PLAIN).body("Eliminazione immagine immobile riuscita.");
        		
        	}
        	catch(Exception e)
        	{
            	return ResponseEntity.status(500).contentType(MediaType.TEXT_PLAIN).body("Errore nella cancellazione: problema server.");
        	}
        }
        catch(Exception e)
        {
        	return ResponseEntity.status(404).contentType(MediaType.TEXT_PLAIN).body("Errore nella cancellazione: agente non trovato.");
        }
    }
	
}
