package org.vesuviano.dieti_estates.controller;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.vesuviano.dieti_estates.dao.impl.OffertaDAOImpl;
import org.vesuviano.dieti_estates.dao.OffertaDAO;
import org.vesuviano.dieti_estates.dao.impl.ImmobileDAOImpl;
import org.vesuviano.dieti_estates.dao.ImmobileDAO;

import org.vesuviano.dieti_estates.dto.OffertaDTO;
import org.vesuviano.dieti_estates.dto.ImmobileDTO;
import org.vesuviano.dieti_estates.dto.OffertaImmobile;

@RestController
@RequestMapping("/offerta")

public class OffertaController 
{
    private OffertaDAO offertaDAO = new OffertaDAOImpl();
    private ImmobileDAO immobileDAO = new ImmobileDAOImpl();
    
    @GetMapping("/agente")
    public ResponseEntity<ArrayList<OffertaImmobile>> getAllOfferAgent(@RequestHeader("uid_agente") String sessioneId) {
        try 
        {
            int agenteId = SessioneController.getAgenteId(sessioneId);

            if (agenteId != 0) 
            {
                ArrayList<OffertaDTO> offerte = offertaDAO.getAllOfferAgent(agenteId);
                ArrayList<OffertaImmobile> offerteImmobili = new ArrayList<>();

                for (OffertaDTO offerta : offerte) 
                {
                    ImmobileDTO immobile = immobileDAO.getImmobileById(offerta.getCodImmobile());
                    
                    if (immobile != null) 
                    {
                        OffertaImmobile offertaImmobile = new OffertaImmobile();
                        offertaImmobile.setOfferta(offerta);
                        offertaImmobile.setImmobile(immobile);
                        offerteImmobili.add(offertaImmobile);
                    }
                }

                return ResponseEntity.ok(offerteImmobili);
            } 
            else 
            {
                return ResponseEntity.status(401).body(new ArrayList<OffertaImmobile>());
            }
        } 
        catch (Exception e) 
        {
        	e.printStackTrace();
            return ResponseEntity.status(500).body(new ArrayList<OffertaImmobile>());
        }
    }

    @GetMapping("/cliente")
    public ResponseEntity<ArrayList<OffertaImmobile>> getAllOfferClient(@RequestHeader("uid_cliente") String sessioneId) {
        try 
        {
            int clienteId = SessioneController.getClienteId(sessioneId);
            
            if (clienteId != 0) 
            {
                ArrayList<OffertaDTO> offerte = offertaDAO.getAllOfferClient(clienteId);
                ArrayList<OffertaImmobile> offerteImmobili = new ArrayList<OffertaImmobile>();
                
                for (OffertaDTO offerta : offerte) 
                {
                    ImmobileDTO immobile = immobileDAO.getImmobileById(offerta.getCodImmobile());
                    
                    if (immobile != null) 
                    {
                        OffertaImmobile offertaImmobile = new OffertaImmobile();
                        offertaImmobile.setOfferta(offerta);
                        offertaImmobile.setImmobile(immobile);
                        offerteImmobili.add(offertaImmobile);
                    }
                }

                return ResponseEntity.ok(offerteImmobili);
            } 
            else 
            {
                return ResponseEntity.status(401).body(new ArrayList<OffertaImmobile>());
            }
        } 
        catch (Exception e) 
        {
            return ResponseEntity.status(500).body(new ArrayList<OffertaImmobile>());
        }
    }
    
    @PutMapping("/")
    public ResponseEntity<String> insertOffer(@RequestHeader("uid_cliente") String sessioneId, @RequestParam int codImmobile, @RequestParam int offerta)
    {
    	try 
    	{
    		
    		if (sessioneId == null ) {
                return ResponseEntity.status(400).body("Errore:Sessione non valida");
            }
    		else if (codImmobile > 9000) {
                return ResponseEntity.status(400).body("Errore: Immobile inesistente");
            }
    		else if (offerta < 1) {
                return ResponseEntity.status(400).body("Errore: Offerta non valida");
            }
    		else if (offerta > 10000000) {
                return ResponseEntity.status(400).body("Errore:Offerta troppo alta");
            }
    		
               
            int clienteid = SessioneController.getClienteId(sessioneId);
    
			OffertaDTO offertaDTO = new OffertaDTO();
    		offertaDTO.setAmmontare(offerta);
    		offertaDTO.setCodCliente(clienteid);
    		offertaDTO.setCodImmobile(codImmobile);
        		
    		offertaDAO.insertOffer(offertaDTO);
    		
    		return ResponseEntity.status(200).body("Offerta inserita con successo.");
    		
    	}
    	catch (Exception e) 
    	{
            return ResponseEntity.status(500).body("Errore durante l'inserimento dell'offerta.");
        }
    }
    
    @PatchMapping("/")
    public ResponseEntity<String> modifyOffer(@RequestHeader("uid_cliente")String sessioneId, @RequestBody OffertaDTO offer) 
    {
    	try
    	{
    		int codCliente = SessioneController.getClienteId(sessioneId);
    		
    		try 
            {
    			offer.setCodCliente(codCliente);
                offertaDAO.modifyOffer(offer);
                return ResponseEntity.ok("Offerta modificato con successo.");
            } 
            catch (Exception e) 
            {
                return ResponseEntity.status(500).body("Errore durante la modifica dell'offerta: problema server.");
            }
    	}
        catch(Exception e)
    	{
        	return ResponseEntity.status(401).body("Errore durante la modifica dei dati: non autorizzato.");
    	}
    }
    
    @PatchMapping("/accetta")
    public ResponseEntity<String> acceptOffer(@RequestHeader("uid_agente") String sessioneId, @RequestParam int codOfferta) 
    {
        try 
        {
            int agenteId = SessioneController.getAgenteId(sessioneId);
            
            if(agenteId != 0)
            {
            	int update = offertaDAO.acceptOffer(codOfferta);
                return update > 0 ? ResponseEntity.ok("Offerta accettata.") : ResponseEntity.status(400).body("Errore: offerta non trovata.");	
            }
            else
            {
            	return ResponseEntity.status(401).body("Utente non autorizzato.");
            }
        } 
        catch (Exception e) 
        {
        	e.printStackTrace();
            return ResponseEntity.status(500).body("Errore durante l'accettazione dell'offerta.");
        }
    }
    
    @PatchMapping("/rifiuta")
    public ResponseEntity<String> rejectOffer(@RequestHeader("uid_agente") String sessioneId, @RequestParam int codOfferta) 
    {
        try 
        {
            int agenteId = SessioneController.getAgenteId(sessioneId);
            
            if(agenteId != 0)
            {
            	int update = offertaDAO.rejectOffer(codOfferta);
                return update > 0 ? ResponseEntity.ok("Offerta rifiutata.") : ResponseEntity.status(400).body("Errore: offerta non trovata.");    
            }
            else
            {
            	return ResponseEntity.status(401).body("Utente non autorizzato.");
            }
        } 
        catch (Exception e) 
        {
            return ResponseEntity.status(500).body("Errore durante il rifiuto dell'offerta.");
        }
    }
    
    @DeleteMapping("/")
    public ResponseEntity<String> deleteOffer(@RequestHeader("uid_agente") String sessioneId, @RequestParam int codOfferta) 
    {
        try 
        {
            SessioneController.getAgenteId(sessioneId);
            
            offertaDAO.deleteOffer(codOfferta);
            return ResponseEntity.ok("Offerta rimossa con successo.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Errore durante la rimozione dell'offerta.");
        }
    }
    
}
