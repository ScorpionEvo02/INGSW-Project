package org.vesuviano.dieti_estates.controller;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import java.time.format.DateTimeFormatter;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import org.vesuviano.dieti_estates.dao.impl.PrenotazioneDAOImpl;
import org.vesuviano.dieti_estates.controller.email.PrenotazioneEmailController;
import org.vesuviano.dieti_estates.dao.PrenotazioneDAO;
import org.vesuviano.dieti_estates.dto.PrenotazioneDTO;

import org.vesuviano.dieti_estates.dao.ImmobileDAO;
import org.vesuviano.dieti_estates.dao.impl.ImmobileDAOImpl;

import org.vesuviano.dieti_estates.dao.AgenteDAO;
import org.vesuviano.dieti_estates.dao.impl.AgenteDAOImpl;

import org.vesuviano.dieti_estates.dto.AppuntamentoDTO;
import org.vesuviano.dieti_estates.dto.AgenteImmobiliareDTO;
import org.vesuviano.dieti_estates.dto.ImmobileDTO;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/prenotazione")
public class PrenotazioneController 
{
    private PrenotazioneDAO prenotazioneDAO = new PrenotazioneDAOImpl();
    private ImmobileDAO immobileDAO = new ImmobileDAOImpl();
    private AgenteDAO agenteDAO = new AgenteDAOImpl();
    
    private PrenotazioneEmailController prenotazioneEmailController;

    @Autowired
    public PrenotazioneController(JavaMailSender mailSender) 
    {
        this.prenotazioneEmailController = new PrenotazioneEmailController(mailSender);
    }
    
    @GetMapping("/agente")
    public ResponseEntity<ArrayList<AppuntamentoDTO>> getAllPrenotazioneAgente(@RequestHeader("uid_agente") String sessioneId)
    {
    	try 
    	{
    	  int agenteId = SessioneController.getAgenteId(sessioneId);
    	  
    	  if (agenteId != 0) 
    	  {
              ArrayList<PrenotazioneDTO> prenotazioni = prenotazioneDAO.getAllPrenotazioneAgente(agenteId);
              ArrayList<AppuntamentoDTO> appuntamenti = new ArrayList<>();

              for (PrenotazioneDTO prenotazione : prenotazioni) 
              {
                  ImmobileDTO immobile = immobileDAO.getImmobileById(prenotazione.getCodImmobile());
                  AgenteImmobiliareDTO agente = agenteDAO.getAllAgent(prenotazione.getCodAgente());
                  
                  if (immobile != null) 
                  {
                	  AppuntamentoDTO appuntamento = new AppuntamentoDTO();
                	  appuntamento.setAgente(agente);
                	  appuntamento.setImmobile(immobile);
                	  appuntamento.setPrenotazione(prenotazione);
                      
                      appuntamenti.add(appuntamento);
                  }
              }

              return ResponseEntity.ok(appuntamenti);
          } 
    	  else 
    	  {
              return ResponseEntity.status(401).body(new ArrayList<AppuntamentoDTO>());
          }
    	  
    	}
    	catch(Exception e)
    	{
            return ResponseEntity.status(500).body(new ArrayList<AppuntamentoDTO>());
    	}
    }
    
    @GetMapping("/cliente")
    public ResponseEntity<ArrayList<AppuntamentoDTO>> getAllPrenotazioneCliente(@RequestHeader("uid_cliente") String sessioneId)
    {
    	try 
    	{
    	  int clienteId = SessioneController.getClienteId(sessioneId);
    	  
    	  if (clienteId != 0) 
    	  {
              ArrayList<PrenotazioneDTO> prenotazioni = prenotazioneDAO.getAllPrenotazioneCliente(clienteId);
              ArrayList<AppuntamentoDTO> appuntamenti = new ArrayList<>();

              for (PrenotazioneDTO prenotazione : prenotazioni) 
              {
                  ImmobileDTO immobile = immobileDAO.getImmobileById(prenotazione.getCodImmobile());
                  AgenteImmobiliareDTO agente = agenteDAO.getAllAgent(prenotazione.getCodAgente());
                  
                  if (immobile != null) 
                  {
                	  AppuntamentoDTO appuntamento = new AppuntamentoDTO();
                	  appuntamento.setAgente(agente);
                	  appuntamento.setImmobile(immobile);
                	  appuntamento.setPrenotazione(prenotazione);
                      
                      appuntamenti.add(appuntamento);
                  }
              }

              return ResponseEntity.ok(appuntamenti);
          } 
    	  else 
    	  {
              return ResponseEntity.status(401).body(new ArrayList<AppuntamentoDTO>());
          }
    	  
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
            return ResponseEntity.status(500).body(new ArrayList<AppuntamentoDTO>());
    	}
    }
    
    @PatchMapping("/accetta")
    public ResponseEntity<String> acceptPrenotazione(@RequestHeader("uid_agente") String sessioneId, 
    		@RequestParam int codPrenotazione) 
    {
        try 
        {
            int agenteId = SessioneController.getAgenteId(sessioneId);
            
            int update = prenotazioneDAO.acceptPrenotazioneByAgente(agenteId, codPrenotazione);
            return update > 0 ? ResponseEntity.ok("Prenotazione accettata.") : ResponseEntity.status(400).body("Errore: prenotazione non trovata.");
        } 
        catch (Exception e) 
        {
        	e.printStackTrace();
            return ResponseEntity.status(500).body("Errore durante l'accettazione della prenotazione.");
        }
    }
    
    @PatchMapping("/rifiuta")
    public ResponseEntity<String> refusePrenotazione(@RequestHeader("uid_agente") String sessioneId, 
    		@RequestParam int codPrenotazione) 
    {
        try 
        {
            int agenteId = SessioneController.getAgenteId(sessioneId);
            
            int update = prenotazioneDAO.refusePrenotazioneByAgente(agenteId, codPrenotazione);
            return update > 0 ? ResponseEntity.ok("Prenotazione rifiutata.") : ResponseEntity.status(400).body("Errore: prenotazione non trovata.");
        } 
        catch (Exception e) 
        {
            return ResponseEntity.status(500).body("Errore durante il rifiuto della prenotazione.");
        }
    }
    
    @PutMapping("/")
    public ResponseEntity<String> insertPrenotazione(@RequestHeader("uid_cliente") String sessioneId, @RequestParam int codImmobile,
            @RequestParam String dataPrenotazione, @RequestParam int codAgente) {
        try 
        {
            int clienteid = SessioneController.getClienteId(sessioneId);

            try 
            {
                PrenotazioneDTO prenotazione = new PrenotazioneDTO();
                prenotazione.setCodCliente(clienteid);
                prenotazione.setCodImmobile(codImmobile);

                OffsetDateTime offsetDateTime = OffsetDateTime.parse(dataPrenotazione, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
                Timestamp timestamp = Timestamp.valueOf(offsetDateTime.toLocalDateTime());
                prenotazione.setOrarioPrenotazione(timestamp);

                prenotazione.setCodAgente(codAgente);

                prenotazioneDAO.insertPrenotazioneByCliente(prenotazione);
                prenotazioneEmailController.sendConfirmPrenotazione(codAgente);
                return ResponseEntity.ok("Prenotazione inserita con successo.");
            } 
            catch (Exception e) 
            {
                return ResponseEntity.status(500).body("Errore durante l'inserimento della prenotazione: " + e.getMessage());
            }
        } 
        catch (Exception e) 
        {
            return ResponseEntity.status(401).body("Errore durante l'inserimento della prenotazione: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/")
    public ResponseEntity<String> removePrenotazione(@RequestHeader("uid_agente") String sessioneId,
                                                     @RequestParam int codPrenotazione) {
        try {
            int codAgente = SessioneController.getAgenteId(sessioneId);
            
            prenotazioneDAO.removePrenotazioneByCliente(codAgente, codPrenotazione);
            return ResponseEntity.ok("Prenotazione rimossa con successo.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Errore durante la rimozione della prenotazione.");
        }
    }

}
