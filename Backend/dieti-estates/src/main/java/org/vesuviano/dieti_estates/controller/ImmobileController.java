package org.vesuviano.dieti_estates.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vesuviano.dieti_estates.dao.ImmobileDAO;
import org.vesuviano.dieti_estates.dao.impl.ImmobileDAOImpl;
import org.vesuviano.dieti_estates.dto.CoordinataDTO;
import org.vesuviano.dieti_estates.dto.ImmobileDTO;

import java.util.ArrayList;

@RestController
@RequestMapping("/immobile")
public class ImmobileController {

    private ImmobileDAO immobileDAO = new ImmobileDAOImpl();
    
    @PostMapping("/vendita")
    public ResponseEntity<ArrayList<ImmobileDTO>> getImmobiliByCoordinate(@RequestBody CoordinataDTO coordinate, @RequestParam String tipo, @RequestParam int raggio) 
    {
        try
        {
        	if(raggio > 50)
        		raggio = 50;
        	ArrayList<ImmobileDTO> immobili = immobileDAO.getImmobiliByCoordinate(coordinate, tipo, raggio);
            return ResponseEntity.ok(immobili);
        }
        catch(Exception e)
        {
        	return ResponseEntity.status(410).body(new ArrayList<>());
        }
    	
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImmobileDTO> getImmobileById(@PathVariable int id) 
    {
        try
        {
        	ImmobileDTO immobile = immobileDAO.getImmobileById(id);
            return ResponseEntity.ok(immobile);
        }
        catch(Exception e)
        {
        	return ResponseEntity.status(404).body(new ImmobileDTO());
        }
    	
    }

    @GetMapping("/città/{città}")
    public ResponseEntity<ArrayList<ImmobileDTO>> getImmobiliByCittà(@PathVariable String città) 
    {
    	try
    	{
            ArrayList<ImmobileDTO> immobili = immobileDAO.getImmobileByCittà(città);
            return ResponseEntity.ok(immobili);
    	}
    	catch (Exception e)
    	{
    		return ResponseEntity.status(404).body(new ArrayList<>());
    	}
    }

    @PostMapping("/receive")
    public ResponseEntity<String> insertImmobile(@RequestHeader("uid_agente")String sessioneId, @RequestBody ImmobileDTO immobile) 
    {
        try
        {
        	int codAgente = SessioneController.getAgenteId(sessioneId);
        	System.out.println(""+immobile.getCoordinata().getLatitudine()+" "+immobile.getCoordinata().getLongitudine());
        	if(codAgente == -1)
        		throw new Exception();
        	
        	try
        	{
        		immobile.setCodAgente(codAgente);
            	immobileDAO.insertImmobile(immobile);
                return ResponseEntity.status(201).contentType(MediaType.TEXT_PLAIN).body("Inserimento immobile riuscita.");
        		
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
    
    @GetMapping("/agente")
    public ResponseEntity<ArrayList<ImmobileDTO>> getImmobiliAgente(@RequestHeader("uid_agente")String sessioneId) 
    {
        try
        {
        	int codAgente = SessioneController.getAgenteId(sessioneId);
        	if(codAgente == -1)
        		throw new Exception();
        	
        	try
        	{
            	ArrayList<ImmobileDTO> immobili = immobileDAO.getImmobiliAgente(codAgente);
                return ResponseEntity.ok(immobili);
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        		return ResponseEntity.status(500).body(new ArrayList<>());
        	}
        }
        catch(Exception e)
        {
        	return ResponseEntity.status(404).body(new ArrayList<>());
        }
    }
    
    @PatchMapping("/")
    public ResponseEntity<String> updateImmobile(@RequestHeader("uid_agente")String sessioneId, @RequestBody ImmobileDTO immobile) 
    {
    	try
        {
        	int codAgente = SessioneController.getAgenteId(sessioneId);
        	
        	if(codAgente == -1)
        		throw new Exception();
        	
        	try
        	{
        		immobile.setCodAgente(codAgente);
                immobileDAO.updateImmobile(immobile);
                return ResponseEntity.status(201).body("Eliminazione immobile riuscita.");
        		
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        		return ResponseEntity.status(500).contentType(MediaType.TEXT_PLAIN).body("Errore nella cancellazione: problema server.");
        	}
        }
        catch(Exception e)
        {
        	return ResponseEntity.status(404).contentType(MediaType.TEXT_PLAIN).body("Errore nella cancellazione: agente non trovato.");
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
                immobileDAO.removeImmobile(id);
                return ResponseEntity.status(201).contentType(MediaType.TEXT_PLAIN).body("Eliminazione immobile riuscita.");
        		
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
