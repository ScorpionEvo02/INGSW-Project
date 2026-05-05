package org.vesuviano.dieti_estates.service;

import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class PrenotazioneEmailService extends EmailService {

    public PrenotazioneEmailService(JavaMailSender mailSender) 
    {
        super(mailSender);
    }

    public void sendPrenotazioneAgente(String to) 
    {
        String subject = "Nuova prenotazione";
        String body = "Gentile agente,\n\nC'è una nuova prenotazione. Accedi per visualizzarla";
        sendEmail(to, subject, body);
    }

    public void sendConfirmPrenotazione(String to, String bookingDetails) 
    {
        String subject = "Conferma prenotazione";
        String body = "Gentile cliente,\n\nLa tua prenotazione è confermata:\n" 
                        + bookingDetails + "\n\nGrazie!";
        sendEmail(to, subject, body);
    }
}

