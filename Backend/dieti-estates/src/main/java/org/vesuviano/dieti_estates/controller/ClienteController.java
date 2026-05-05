package org.vesuviano.dieti_estates.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vesuviano.dieti_estates.dao.impl.ClienteDAOImpl;
import org.vesuviano.dieti_estates.dao.ClienteDAO;
import org.vesuviano.dieti_estates.dto.ClienteDTO;
import org.vesuviano.dieti_estates.dto.LoginRequestDTO;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/cliente")
public class ClienteController {

    private ClienteDAO clienteDAO = new ClienteDAOImpl();

    @PutMapping("/")
    public ResponseEntity<String> registerCliente(@RequestBody ClienteDTO cliente) 
    {
        try 
        {
        	clienteDAO.registerClient(cliente);
            return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body("Cliente registrato con successo.");
        } 
        catch (Exception e) 
        {
            return ResponseEntity.status(500).contentType(MediaType.TEXT_PLAIN).body("Errore durante la registrazione del cliente.");
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> loginWithEmail(@RequestBody LoginRequestDTO request) {
        
    	try 
    	{
            int clienteId = clienteDAO.loginClient(request.getEmail(), request.getPassword());

            String sessioneId = SessioneController.createSessioneCliente(clienteId);

            if (sessioneId != null) 
            {
                ResponseCookie cookie = ResponseCookie.from("uid_cliente", sessioneId)
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
                return ResponseEntity.status(500).body("Errore durante login del cliente: problema del server.");
            }
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCliente(@RequestHeader("uid_cliente") String sessioneId, @RequestBody ClienteDTO cliente) 
    {
        
        try 
        {
            int codCliente = SessioneController.getClienteId(sessioneId);
            cliente.setCodCliente(codCliente);
            clienteDAO.updateClient(cliente);
            return ResponseEntity.ok(" Cliente modificato con successo.");
            
        } 
        catch (Exception e) 
        {
            return ResponseEntity.status(500).contentType(MediaType.TEXT_PLAIN).body(" Errore durante la modifica dei dati: problema server.");
        }
    }


    @GetMapping("/")
    public ResponseEntity<?> getCliente(@RequestHeader("uid_cliente") String sessioneId)
    {
    	try 
    	{
    		int clienteId = SessioneController.getClienteId(sessioneId);
	  
    		if(clienteId != 0)
    		{  
    			ClienteDTO agenti = clienteDAO.getAllCliente(clienteId);
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

}


/*
 * .maxAge(24 * 60 * 60) // 24 ore
                             .httpOnly(true)
                             .secure(true)         Solo https
                             .sameSite("None")
                             .path("/")
                             .build();*/
