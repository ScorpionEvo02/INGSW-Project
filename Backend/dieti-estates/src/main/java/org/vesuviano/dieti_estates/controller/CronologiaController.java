package org.vesuviano.dieti_estates.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vesuviano.dieti_estates.dao.CronologiaDAO;
import org.vesuviano.dieti_estates.dao.impl.CronologiaDAOImpl;
import org.vesuviano.dieti_estates.dto.ImmobileDTO;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/cronologia")
public class CronologiaController {

    private CronologiaDAO cronologiaDAO = new CronologiaDAOImpl();

    @GetMapping("/")
    public ResponseEntity<ArrayList<ImmobileDTO>> getCronologiaCliente(@RequestHeader("uid_cliente") String sessioneId) 
    {
        try {
        	int codCliente = SessioneController.getClienteId(sessioneId);
            try
            {
            	ArrayList<ImmobileDTO> cronologia = cronologiaDAO.getAllCronologiaCliente(codCliente);
                return ResponseEntity.ok(cronologia);
            }
            catch (Exception e)
            {
            	return ResponseEntity.status(401).body(new ArrayList<>());
            }     	
        } 
        catch (Exception e) 
        {
            return ResponseEntity.status(500).body(new ArrayList<>());
        }
    }

    @PutMapping("/")
    public ResponseEntity<String> insertCronologiaCliente(@RequestHeader("uid_cliente")String sessioneId, @RequestParam int codImmobile) 
    {
        try 
        {
        	int codCliente = SessioneController.getClienteId(sessioneId);
	    	try 
	        {
	            cronologiaDAO.insertCronologiaCliente(codCliente, codImmobile);
	            return ResponseEntity.ok("Cronologia inserita con successo.");
	        } 
	        catch (Exception e) 
	        {
	            return ResponseEntity.status(500).contentType(MediaType.TEXT_PLAIN).body("Errore durante l'inserimento della cronologia: problema server.");
	        }
        }
        catch (Exception e)
        {
        	return ResponseEntity.status(401).contentType(MediaType.TEXT_PLAIN).body("Errore durante l'inserimento della cronologia: utente non trovato.");
        }
    }
}
