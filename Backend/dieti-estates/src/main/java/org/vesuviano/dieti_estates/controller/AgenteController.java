package org.vesuviano.dieti_estates.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vesuviano.dieti_estates.dao.AgenteDAO;
import org.vesuviano.dieti_estates.dao.impl.AgenteDAOImpl;
import org.vesuviano.dieti_estates.dao.impl.GestoreAgenziaDAOImpl;
import org.vesuviano.dieti_estates.dto.AgenteImmobiliareDTO;
import org.vesuviano.dieti_estates.dto.GestoreAgenziaDTO;
import org.vesuviano.dieti_estates.dto.LoginRequestDTO;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/agente")
public class AgenteController 
{
	
	 private AgenteDAO agenteDAO = new AgenteDAOImpl();
	
	 @GetMapping("/receive")
     public ResponseEntity<?> getAllAgent(@RequestHeader("uid_agente") String sessioneId)
     {
		try 
    	{
    	  int agenteId = SessioneController.getAgenteId(sessioneId);
    	  if(agenteId != 0)
    	  {  
    		  AgenteImmobiliareDTO agenti = agenteDAO.getAllAgent(agenteId);
              return ResponseEntity.ok(agenti);
    	  }
    	  else 
          {
              return ResponseEntity.status(401).body("Errore durante la ricerca dell'agente: agente non trovato");
          }
    	  
    	}
    	catch(Exception e)
    	{
            return ResponseEntity.status(500).body("Errore durante il caricamento dei risultati");
    	}
     }
	
	 @GetMapping("/")
     public ResponseEntity<AgenteImmobiliareDTO> getAgenteById(@RequestParam int codAgente)
     {
    	try 
    	{
    	  if(codAgente != 0)
    	  {  
    	      AgenteImmobiliareDTO agente = agenteDAO.getAllAgent(codAgente);
    	      return ResponseEntity.ok(agente);
    	  }
    	  else 
          {
              throw new Error();
          }
    	  
    	}
    	catch(Exception e)
    	{
            return ResponseEntity.status(500).body(new AgenteImmobiliareDTO());
    	}
     }
	 
	 @PostMapping("/insert")
	 public ResponseEntity<String> insertAgent(@RequestHeader("uid_gestore") String sessionId, @RequestBody AgenteImmobiliareDTO agente) {
		 System.out.println("METODO CHIAMATO. uid_gestore = " + sessionId);
		 
		 try {
	        int codGestore = SessioneController.getGestoreId(sessionId);
	        
	        System.out.println("ID gestore ottenuto: " + codGestore);
	        
	        if (codGestore == 0) return ResponseEntity.status(401).body("Utente non autorizzato.");

	        GestoreAgenziaDAOImpl gestoreDAO = new GestoreAgenziaDAOImpl();
	        GestoreAgenziaDTO gestore = gestoreDAO.getAllGestore(codGestore);
	        System.out.println("Agenzia del gestore: " + gestore.getCodAgenzia());
	        
	        agente.setCodGestore(codGestore);
	        agente.setCodAgenzia(gestore.getCodAgenzia());
	        System.out.println("Agente ricevuto e aggiornato: " + agente);
	        
	        System.out.println("COD GESTORE: " + codGestore);
	        System.out.println("COD AGENZIA da gestore: " + gestore.getCodAgenzia());
	        System.out.println("Agente ricevuto e aggiornato: " + agente);

	        AgenteDAOImpl agenteDAO = new AgenteDAOImpl();
	        agenteDAO.insertAgent(agente);

	        return ResponseEntity.ok("Agente inserito con successo.");
	        
	    } catch (Exception e) {
	    	
	        return ResponseEntity.status(500).body("Errore durante l'inserimento dell'agente.");
	    }
	 }
	 
	 @PutMapping("/update")
	 public ResponseEntity<String> updateAgent(@RequestHeader("uid_agente") String sessioneId, @RequestBody AgenteImmobiliareDTO agente) 
	 {
	     
	     try 
	     {
	     	
	         int codAgente = SessioneController.getAgenteId(sessioneId);
	         agente.setCodAgente(codAgente);
	         agenteDAO.updateAgent(agente);
	         return ResponseEntity.ok(" Cliente modificato con successo.");
	         
	     } 
	     catch (Exception e) 
	     {
	         return ResponseEntity.status(500).contentType(MediaType.TEXT_PLAIN).body(" Errore durante la modifica dei dati: problema server.");
	     }

	 }
	 
	 @DeleteMapping("/delete")
	 public ResponseEntity<String> deleteAgent(@RequestHeader("uid_gestore") String sessioneId, @RequestParam int codAgente) 
	 {
	     try 
	     { 
	         agenteDAO.deleteAgent(codAgente);
	         return ResponseEntity.ok("Gestore rimosso con successo.");
	     } 
	     catch(Exception e) 
	     {
	         return ResponseEntity.status(500).body("Errore durante la rimozione del gestore.");
	     }
	 }
	
	 @GetMapping("/verify")
	 public ResponseEntity<Boolean> verifyAgent(@RequestParam("codAgente") int codAgente) 
	 {
	     try 
	     {
	         boolean exists = agenteDAO.verifyAgent(codAgente);
	         return ResponseEntity.ok(exists);
	     } 
	     catch (Exception e) 
	     {
	         return ResponseEntity.status(500).body(false);
	     }
	 }
	
	 @PostMapping("/login")
	 public ResponseEntity<String> loginAgent(@RequestBody LoginRequestDTO request) 
	 {
		 try 
	     {
            int agenteId = agenteDAO.loginAgent(request.getEmail(), request.getPassword());
	            
            String sessioneId = SessioneController.createSessioneAgente(agenteId);
	            	
            	if(sessioneId != null)
            	{
            		 ResponseCookie cookie = ResponseCookie.from("uid_agente", sessioneId)
            				 .maxAge(24 * 60 * 60) // 24 ore
                             .httpOnly(false)
                             .secure(false)         
                             .sameSite("Lax")
                             .path("/")
                             .build();
            		 return ResponseEntity.status(201).header(HttpHeaders.SET_COOKIE, cookie.toString()).body("Login riuscito.");
            	}
            	else
            	{
            		 return ResponseEntity.status(200).body("Login già attivo: sessione esistente.");
            	}
	      }  
    	  catch (Exception e) 
		  {
    	       if ("Credenziali non valide".equals(e.getMessage())) 
    	       {
    	            return ResponseEntity.status(400).body("Credenziali non valide.");
	           } 
    	       else 
    	       {
    	            return ResponseEntity.status(500).body("Errore durante login dell'agente: problema del server.");
	           }
	      }
	 }


	 @GetMapping("/received")
	 public ResponseEntity<?> getAllAgenti(@RequestHeader("uid_gestore") String sessioneId) 
	 {
		 
		 try 
		 {
			 int codGestore = SessioneController.getGestoreId(sessioneId);
			 
			 if (codGestore != 0) 
			 {
				 ArrayList<AgenteImmobiliareDTO> agenti = agenteDAO.getAllAgenti(codGestore);
				 return ResponseEntity.ok(agenti);
			 } 
			 else 
			 {
				 return ResponseEntity.status(401).body("Errore: gestore non trovato");
			 }
		 } 
		 catch (Exception e) 
		 {
			 return ResponseEntity.status(500).body("Errore durante il caricamento degli agenti");
		 }
	 }



}
