package org.vesuviano.dieti_estates.controller.email;

import org.vesuviano.dieti_estates.dao.AgenteDAO;
import org.vesuviano.dieti_estates.dao.impl.AgenteDAOImpl;
import org.vesuviano.dieti_estates.dto.AgenteImmobiliareDTO;
import org.vesuviano.dieti_estates.service.PrenotazioneEmailService;
import org.springframework.mail.javamail.JavaMailSender;

public class PrenotazioneEmailController {

    private final AgenteDAO agenteDAO;
    private final PrenotazioneEmailService prenotazioneEmailService;

   
    public PrenotazioneEmailController(JavaMailSender mailSender) 
    {
        this.agenteDAO = new AgenteDAOImpl();
        this.prenotazioneEmailService = new PrenotazioneEmailService(mailSender);
    }

    public void sendConfirmPrenotazione(int codAgente) {
        try 
        {
            AgenteImmobiliareDTO agente = agenteDAO.getAllAgent(codAgente);
            prenotazioneEmailService.sendPrenotazioneAgente(agente.getEmail());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}

