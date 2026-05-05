package org.vesuviano.dieti_estates.controller;

import java.util.ArrayList;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.vesuviano.dieti_estates.dao.GestoreAgenziaDAO;
import org.vesuviano.dieti_estates.dao.impl.AmministratoreDAOImpl;
import org.vesuviano.dieti_estates.dao.impl.GestoreAgenziaDAOImpl;
import org.vesuviano.dieti_estates.dto.AmministratoreDTO;
import org.vesuviano.dieti_estates.dto.GestoreAgenziaDTO;
import org.vesuviano.dieti_estates.dto.LoginRequestDTO;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/gestore")
public class GestoreAgenziaController 
{

	private GestoreAgenziaDAO gestoreDAO = new GestoreAgenziaDAOImpl();
	
	@GetMapping("/gestore")
    public ResponseEntity<?> getAllManager(@RequestHeader("uid_gestore") String sessioneId)
    {
		try 
    	{
    	  int managerId = SessioneController.getGestoreId(sessioneId);
       	  if(managerId != 0)
    	  {  
    	      ArrayList<GestoreAgenziaDTO> gestori = gestoreDAO.getAllManager(managerId);
              return ResponseEntity.ok(gestori);
    	  }
    	  else 
          {
              return ResponseEntity.status(401).body("Errore durante la ricerca del gestore: gestore non trovato");
          }
    	  
    	}
    	catch(Exception e)
    	{
            return ResponseEntity.status(500).body("Errore durante il caricamento dei risultati");
    	}
    }
	
	@GetMapping("/receive")
    public ResponseEntity<?> getAllGestore(@RequestHeader("uid_gestore") String sessioneId)
    {
		try 
    	{
    	  int gestoreId = SessioneController.getGestoreId(sessioneId);
    	  
    	  if(gestoreId != 0)
    	  {  
    		  GestoreAgenziaDTO gestori = gestoreDAO.getAllGestore(gestoreId);
              return ResponseEntity.ok(gestori);
    	  }
    	  else 
          {
              return ResponseEntity.status(401).body("Errore durante la ricerca del gestore: agente non trovato");
          }
    	  
    	}
    	catch(Exception e)
    	{
            return ResponseEntity.status(500).body("Errore durante il caricamento dei risultati");
    	}
    }


    @PostMapping("/insert")
	public ResponseEntity<String> insertManager(@RequestHeader("uid_amministratore") String sessionId, @RequestBody GestoreAgenziaDTO gestore) 
    {
	    
    	try 
    	{
	    	int codAmministratore = SessioneController.getAmministratoreId(sessionId);
	        
	        if (codAmministratore == 0) return ResponseEntity.status(401).body("Utente non autorizzato.");

	        AmministratoreDAOImpl amministratoreDAO = new AmministratoreDAOImpl();
	        AmministratoreDTO amministratore = amministratoreDAO.getAllAmministratore(codAmministratore);
	        
	        gestore.setCodAmministratore(codAmministratore);
	        gestore.setCodAgenzia(amministratore.getCodAgenzia());

	        GestoreAgenziaDAOImpl gestoreDAO = new GestoreAgenziaDAOImpl();
	        gestoreDAO.insertManager(gestore);

	        return ResponseEntity.ok("Agente inserito con successo.");
	        
	    } catch (Exception e) {
	    	
	        return ResponseEntity.status(500).body("Errore durante l'inserimento dell'agente.");
	    }
	 }
	 
	 @PutMapping("/update")
	 public ResponseEntity<String> updateManager(@RequestHeader("uid_gestore") String sessioneId, @RequestBody GestoreAgenziaDTO gestore) 
	 {
	     
	     try 
	     {
	     	
	         int codGestore = SessioneController.getGestoreId(sessioneId);
	         gestore.setCodGestore(codGestore);
	         gestoreDAO.updateManager(gestore);
	         return ResponseEntity.ok("Gestore modificato con successo.");
	         
	     } 
	     catch (Exception e) 
	     {
	         return ResponseEntity.status(500).body(" Errore durante la modifica dei dati: problema server.");
	     }

	 }
	 
	 @DeleteMapping("/delete")
	 public ResponseEntity<String> deleteManager(@RequestHeader("uid_gestore") String sessioneId, @RequestParam int codGestore) 
	 {
	     try 
	     {
	 		 gestoreDAO.deleteManager(codGestore);
	         return ResponseEntity.ok("Gestore rimosso con successo.");
	     } 
	     catch(Exception e) 
	     {
	         return ResponseEntity.status(500).body("Errore durante la rimozione del gestore.");
	     }
	 }
	
	 @GetMapping("/verify")
	 public ResponseEntity<Boolean> verifyManager(@RequestParam("codGestore") int codGestore) 
	 {
	     try 
	     {
	         boolean exists = gestoreDAO.verifyManager(codGestore);
	         return ResponseEntity.ok(exists);
	     } 
	     catch (Exception e) 
	     {
	         return ResponseEntity.status(500).body(false);
	     }
	 }
	
	 @PostMapping("/login")
	 public ResponseEntity<String> loginManager(@RequestBody LoginRequestDTO request) 
	 {
		 try 
	     {
			 int gestoreId = gestoreDAO.loginManager(request.getEmail(), request.getPassword());
	            
			 String sessioneId = SessioneController.createSessioneGestore(gestoreId);
	            	
			 if(sessioneId != null)
			 {
            	 ResponseCookie cookie = ResponseCookie.from("uid_gestore", sessioneId)
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
    	        return ResponseEntity.status(500).body("Errore durante login del gestore: problema del server.");
	         }
	      }
	 }
	 
	 @PatchMapping("/password") 
	 public ResponseEntity<String> changePassword(@RequestHeader("uid_gestore")String sessioneId, @RequestParam int codGestore, @RequestParam String newPassword) 
	 {
	       
	        if (newPassword == null || newPassword.isEmpty()) 
	        {
	            return ResponseEntity.badRequest().body("La password non può essere vuota.");
	        }
	        
	        try 
	        {
				gestoreDAO.changePassword(codGestore, newPassword);
			} 
	        catch (Exception e) 
	        {
				return ResponseEntity.status(500).body("Errore durante il cambio della password: problema con il server.");
			}
	        
	        return ResponseEntity.ok("Password aggiornata con successo.");
	 }
	 
}
