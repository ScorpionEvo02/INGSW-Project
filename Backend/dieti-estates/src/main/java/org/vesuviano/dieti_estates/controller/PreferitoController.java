package org.vesuviano.dieti_estates.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vesuviano.dieti_estates.dao.impl.PreferitoDAOImpl;
import org.vesuviano.dieti_estates.dao.PreferitoDAO;
import org.vesuviano.dieti_estates.dto.ImmobileDTO;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/preferito")
public class PreferitoController 
{

    private PreferitoDAO preferitoDAO = new PreferitoDAOImpl();

    @GetMapping("/")
    public ResponseEntity<ArrayList<ImmobileDTO>> getAllPreferitiCliente(@RequestHeader("uid_cliente") String sessioneId)
    {
        try 
        {
            int codCliente = SessioneController.getClienteId(sessioneId);
            ArrayList<ImmobileDTO> preferiti = preferitoDAO.getAllPreferitoCliente(codCliente);
            return ResponseEntity.ok(preferiti);
        } 
        catch (Exception e) 
        {
            return ResponseEntity.status(401).body(new ArrayList<ImmobileDTO>());
        }
    }

    @PutMapping("/")
    public ResponseEntity<String> insertPreferito(@RequestHeader("uid_cliente") String sessioneId,
                                                  @RequestParam int codImmobile) 
    {
        try
        {
            int codCliente = SessioneController.getClienteId(sessioneId);
            preferitoDAO.insertPreferito(codCliente, codImmobile);
            return ResponseEntity.ok("Preferito aggiunto con successo.");
        } 
        catch (Exception e) 
        {
            return ResponseEntity.status(500).contentType(MediaType.TEXT_PLAIN).body("Errore durante l'aggiunta del preferito.");
        }
    }

    @DeleteMapping("/")
    public ResponseEntity<String> removePreferito(@RequestHeader("uid_cliente") String sessioneId,
                                                  @RequestParam int codImmobile) 
    {
        try 
        {
            int codCliente = SessioneController.getClienteId(sessioneId);
            preferitoDAO.removePreferito(codCliente, codImmobile);
            return ResponseEntity.ok("Preferito rimosso con successo.");
        } 
        catch (Exception e) 
        {
            return ResponseEntity.status(500).body("Errore durante la rimozione del preferito.");
        }
    }
}
