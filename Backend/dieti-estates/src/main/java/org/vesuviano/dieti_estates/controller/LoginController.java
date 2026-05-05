package org.vesuviano.dieti_estates.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vesuviano.dieti_estates.dao.impl.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/login")
public class LoginController {

    private final ClienteDAOImpl clienteDAO = new ClienteDAOImpl();
    private final AgenteDAOImpl agenteDAO = new AgenteDAOImpl();
    private final GestoreAgenziaDAOImpl gestoreDAO = new GestoreAgenziaDAOImpl();
    private final AmministratoreDAOImpl amministratoreDAO = new AmministratoreDAOImpl();

    @GetMapping("/check-email")
    public ResponseEntity<String> checkEmailInAllRoles(@RequestParam String email) {
        try {
            if (clienteDAO.verifyEmail(email)) return ResponseEntity.ok("cliente");
            if (agenteDAO.verifyEmail(email)) return ResponseEntity.ok("agente");
            if (gestoreDAO.verifyEmail(email)) return ResponseEntity.ok("gestore");
            if (amministratoreDAO.verifyEmail(email)) return ResponseEntity.ok("amministratore");
            
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Errore interno");
        }
    }
    
    @PostMapping("/social-login")
    public ResponseEntity<String> socialLogin(@RequestParam String email, @RequestParam String ruolo) {
        
    	try {
            
        	String sessioneId;
            String cookieName;

            switch (ruolo.toLowerCase()) {
                case "cliente":
                    int idCliente = clienteDAO.getIdByEmail(email);
                    sessioneId = SessioneController.createSessioneCliente(idCliente);
                    cookieName = "uid_cliente";
                    break;

                case "agente":
                    int idAgente = agenteDAO.getIdByEmail(email);
                    sessioneId = SessioneController.createSessioneAgente(idAgente);
                    cookieName = "uid_agente";
                    break;

                case "gestore":
                    int idGestore = gestoreDAO.getIdByEmail(email);
                    sessioneId = SessioneController.createSessioneGestore(idGestore);
                    cookieName = "uid_gestore";
                    break;

                case "amministratore":
                    int idAdmin = amministratoreDAO.getIdByEmail(email);
                    sessioneId = SessioneController.createSessioneAmministratore(idAdmin);
                    cookieName = "uid_amministratore";
                    break;

                default:
                    return ResponseEntity.badRequest().body("Ruolo non valido.");
            }

            // Crea il cookie da restituire
            ResponseCookie cookie = ResponseCookie.from(cookieName, sessioneId)
                    .maxAge(24 * 60 * 60)
                    .httpOnly(false)
                    .secure(false)
                    .sameSite("Lax")
                    .path("/")
                    .build();

            return ResponseEntity.status(201)
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body("Login social riuscito con successo.");

            } catch (Exception e) {
            return ResponseEntity.status(500).body("Errore durante il social login.");
            }
    }
}