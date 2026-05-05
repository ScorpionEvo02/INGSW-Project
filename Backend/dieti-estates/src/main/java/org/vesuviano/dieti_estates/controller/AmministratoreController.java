package org.vesuviano.dieti_estates.controller;

import java.util.ArrayList;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
import org.vesuviano.dieti_estates.dao.AmministratoreDAO;
import org.vesuviano.dieti_estates.dao.impl.AmministratoreDAOImpl;
import org.vesuviano.dieti_estates.dto.AmministratoreDTO;
import org.vesuviano.dieti_estates.dto.LoginRequestDTO;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/amministratore")
public class AmministratoreController 
{

    private AmministratoreDAO adminDAO = new AmministratoreDAOImpl();
	
	@GetMapping("/false")
    public ResponseEntity<?> getAllAgent(@RequestHeader("uid_amministratore") String sessioneId)
    {
    	try 
    	{
    	  int adminId = SessioneController.getAmministratoreId(sessioneId);
    	  
    	  if(adminId != 0)
    	  {  
    	      ArrayList<AmministratoreDTO> amministratori = adminDAO.getAllAdmin(adminId);
              return ResponseEntity.ok(amministratori);
    	  }
    	  else 
          {
              return ResponseEntity.status(401).body("Errore durante la ricerca dell'amministratore: amministratore non trovato");
          }
    	  
    	}
    	catch(Exception e)
    	{
            return ResponseEntity.status(500).body("Errore durante il caricamento dei risultati");
    	}
    }
	
	@GetMapping("/receive")
    public ResponseEntity<?> getAllAmministratore(@RequestHeader("uid_amministratore") String sessioneId)
    {
		try 
    	{
    	  int amministratoreId = SessioneController.getAmministratoreId(sessioneId);
    	  if(amministratoreId != 0)
    	  {  
    		  AmministratoreDTO amministratore = adminDAO.getAllAmministratore(amministratoreId);
              return ResponseEntity.ok(amministratore);
    	  }
    	  else 
          {
              return ResponseEntity.status(401).body("Errore durante la ricerca dell'amministratore: amministratore non trovato");
          }
    	  
    	}
    	catch(Exception e)
    	{
            return ResponseEntity.status(500).body("Errore durante il caricamento dei risultati");
    	}
    }

	@PostMapping("/insert")
	public ResponseEntity<String> insertAdmin(@RequestHeader("uid_amministratore") String sessionId, @RequestBody AmministratoreDTO admin) 
	{
	    try {
	        int codAmministratore = SessioneController.getAmministratoreId(sessionId);
	        
	        if (codAmministratore == 0) return ResponseEntity.status(401).body("Utente non autorizzato.");

	        AmministratoreDAOImpl amministratoreDAO = new AmministratoreDAOImpl();
	        AmministratoreDTO amministratore = amministratoreDAO.getAllAmministratore(codAmministratore);
	        
	        admin.setCodAmministratoreInsert(codAmministratore);
	        admin.setCodAgenzia(amministratore.getCodAgenzia());

	        AmministratoreDAOImpl adminDAO = new AmministratoreDAOImpl();
	        adminDAO.insertAdmin(admin);

	        return ResponseEntity.ok("Amministratore inserito con successo.");
	        
	    } catch (Exception e) {
	    	
	        return ResponseEntity.status(500).body("Errore durante l'inserimento dell'amministratore");
	    }
	}
	 
	 @PutMapping("/update")
	 public ResponseEntity<String> updateAdmin(@RequestHeader("uid_amministratore") String sessioneId, @RequestBody AmministratoreDTO amministratore) 
	 {
	     
	     try 
	     {
	    	 int codAmministratore = SessioneController.getAmministratoreId(sessioneId);
	         amministratore.setCodAmministratore(codAmministratore);
	         amministratore.setCodAmministratoreInsert(codAmministratore);
	         adminDAO.updateAdmin(amministratore);
	         return ResponseEntity.ok(" Amministratore modificato con successo.");
	         
	     } 
	     catch (Exception e) 
	     {
	         return ResponseEntity.status(500).contentType(MediaType.TEXT_PLAIN).body(" Errore durante la modifica dei dati: problema del server.");
	     }

	 }
	 
	 @DeleteMapping("/")
	 public ResponseEntity<String> deleteAdmin(@RequestHeader("uid_admin") String sessioneId, @RequestParam int codAmministratore) 
	 {
	     try 
	     {
	         adminDAO.deleteAdmin(codAmministratore);
	         return ResponseEntity.ok("Amministratore rimosso con successo.");
	     } 
	     catch(Exception e) 
	     {
	         return ResponseEntity.status(500).body("Errore durante la rimozione dell'amministratore.");
	     }
	 }
	
	 @GetMapping("/verify")
	 public ResponseEntity<Boolean> verifyAdmin(@RequestParam("codAmministratore") int codAmministratore) 
	 {
	     try 
	     {
	         boolean exists = adminDAO.verifyAdmin(codAmministratore);
	         return ResponseEntity.ok(exists);
	     } 
	     catch (Exception e) 
	     {
	         return ResponseEntity.status(500).body(false);
	     }
	 }
	
	 @PostMapping("/login")
	 public ResponseEntity<String> loginAdmin(@RequestBody LoginRequestDTO request) 
	 {
	     try 
	     {
	         int amministratoreId = adminDAO.loginAdmin(request.getEmail(), request.getPassword());

	         String sessioneId = SessioneController.createSessioneAmministratore(amministratoreId);

	         if (sessioneId != null) 
	         {
	             ResponseCookie cookie = ResponseCookie.from("uid_amministratore", sessioneId)
	                     .maxAge(24 * 60 * 60)
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
	             return ResponseEntity.status(500).body("Errore durante login dell'amministratore: problema del server.");
	         }
	     }
	 }
	 
	 @PatchMapping("/password") 
	 public ResponseEntity<String> changePassword(@RequestHeader("uid_amministratore")String sessioneId, @RequestParam int codAmministratore, @RequestParam String newPassword) 
	 {
	       
	        if (newPassword == null || newPassword.isEmpty() || newPassword.length() < 6) 
	        {
	            return ResponseEntity.status(400).body("Errore durante il cambio della password: password troppo corta.");
	        }
	        else if(codAmministratore < 1) 
	        {
	        	return ResponseEntity.status(400).body("Errore durante il cambio della password: amministratore non trovato."); 
	        }
	        else if(sessioneId == null)
	        {
	        	return ResponseEntity.status(400).body("Errore durante il cambio della password: sessione non valida.");
	        }
	        
	        try 
	        {
				adminDAO.changePassword(codAmministratore, newPassword);
			} 
	        catch (Exception e) 
	        {
				return ResponseEntity.status(500).body("Errore il cambio della password: problema del server.");
			}
	        
	        return ResponseEntity.ok("Password aggiornata con successo.");
	 }

}
