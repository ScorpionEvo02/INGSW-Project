package org.vesuviano.dieti_estates.dto;

import java.sql.ResultSet;
import java.util.Base64;

public class ImmobileDTO {

    private int codImmobile;
    private String tipo;
    private String descrizione;
    private int costo;
    private IndirizzoDTO indirizzo;
    private int metratura;
    private int piano;
    private int numeroStanze;
    private int numeroCamereLetto;
    private boolean ascensore;
    private String portineria;
    private String classeEnergetica;
    private String informazioniAggiuntive;
    private int codAgente;
    private String immagine;
    private CoordinataDTO coordinata;
    private String stato;
    private String etichetta;

    public ImmobileDTO() 
    {
        // Empty constructor
    }

    public ImmobileDTO(ResultSet rs) throws Exception 
    {
        mapResultSetToImmobile(rs);
    }

    public ImmobileDTO(int codImmobile, String tipo, String descrizione, int costo, IndirizzoDTO indirizzo,
                       int metratura, int numeroStanze, String portineria, String informazioniAggiuntive) 
    {
        this.codImmobile = codImmobile;
        this.tipo = tipo;
        this.descrizione = descrizione;
        this.costo = costo;
        this.indirizzo = indirizzo;
        this.metratura = metratura;
        this.numeroStanze = numeroStanze;
        this.portineria = portineria;
        this.informazioniAggiuntive = informazioniAggiuntive;
    }

    public int getCodImmobile() 
    { 
    	return codImmobile; 
    }
    public void setCodImmobile(int codImmobile) 
    { 
    	this.codImmobile = codImmobile; 
    }
    public String getTipo() 
    { 
    	return tipo; 
    }
    public void setTipo(String tipo) 
    { 
    	this.tipo = tipo; 
    }
    public String getDescrizione() 
    { 
    	return descrizione; 
    }
    public void setDescrizione(String descrizione) 
    { 
    	this.descrizione = descrizione; 
    }
    public int getCosto() 
    { 
    	return costo; 
    }
    public void setCosto(int costo) 
    { 
    	this.costo = costo; 
    }
    public IndirizzoDTO getIndirizzo() 
    { 
    	return indirizzo; 
    }
    public void setIndirizzo(IndirizzoDTO indirizzo) 
    { 
    	this.indirizzo = indirizzo; 
    }
    public int getMetratura() 
    { 
    	return metratura; 
    }
    public void setMetratura(int metratura) 
    { 
    	this.metratura = metratura; 
    }
    public int getPiano() 
    { 
    	return piano; 
    }
    public void setPiano(int piano) 
    { 
    	this.piano = piano; 
    }
    public int getNumeroStanze() 
    { 
    	return numeroStanze; 
    }
    public void setNumeroStanze(int numeroStanze) 
    { 
    	this.numeroStanze = numeroStanze; 
    }
    public int getNumeroCamereLetto() 
    { 
    	return numeroCamereLetto; 
    }
    public void setNumeroCamereLetto(int numeroCamereLetto) 
    { 
    	this.numeroCamereLetto = numeroCamereLetto;
    }
    public boolean isAscensore() 
    { 
    	return ascensore; 
    }
    public void setAscensore(boolean ascensore) 
    { 
    	this.ascensore = ascensore; 
    }
    public String isPortineria() 
    { 
    	return portineria; 
    }
    public void setPortineria(String portineria) 
    { 
    	this.portineria = portineria; 
    }
    public String getClasseEnergetica() 
    {
    	return classeEnergetica; 
    }
    public void setClasseEnergetica(String classeEnergetica) 
    { 
    	this.classeEnergetica = classeEnergetica; 
    }
    public String getInformazioniAggiuntive() 
    { 
    	return informazioniAggiuntive; 
    }
    public void setInformazioniAggiuntive(String informazioniAggiuntive) 
    { 
    	this.informazioniAggiuntive = informazioniAggiuntive; 
    }
    public int getCodAgente() 
    { 
    	return codAgente; 
    }
    public void setCodAgente(int codAgente) 
    { 
    	this.codAgente = codAgente; 
    }
    public String getImmagine() 
    { 
    	return immagine; 
    }
    public void setImmagine(byte[] immagineBytes) 
    { 
        this.immagine = (immagineBytes != null) ? Base64.getEncoder().encodeToString(immagineBytes) : null;
    }
    public CoordinataDTO getCoordinata() 
    { 
    	return coordinata; 
    }
    public void setCoordinata(CoordinataDTO coordinata) 
    { 
    	this.coordinata = coordinata; 
    }
    public String getStato() 
    { 
    	return stato; 
    }
    public void setStato(String stato) 
    { 
    	this.stato = stato; 
    }
    public String getEtichetta() 
    { 
    	return etichetta; 
    }
    public void setEtichetta(String etichetta) 
    { 
    	this.etichetta = etichetta; 
    }
    
    private void mapResultSetToImmobile(ResultSet rs) throws Exception 
    {
        this.codImmobile = rs.getInt("cod_immobile");
        this.tipo = rs.getString("tipo");
        this.descrizione = rs.getString("descrizione");
        this.costo = rs.getInt("costo");
        this.metratura = rs.getInt("metratura");
        this.piano = rs.getInt("piano");
        this.numeroStanze = rs.getInt("num_stanze");
        this.numeroCamereLetto = rs.getInt("num_camere_letto");
        this.ascensore = rs.getBoolean("ascensore");
        this.portineria = rs.getString("portineria");
        this.classeEnergetica = rs.getString("classe_energetica");
        this.informazioniAggiuntive = rs.getString("altre_colonne");
        this.codAgente = rs.getInt("cod_agente");
        this.etichetta = rs.getString("etichetta");
        this.stato = rs.getString("stato");

        this.indirizzo = new IndirizzoDTO();
        this.indirizzo.setCitta(rs.getString("città"));
        this.indirizzo.setComune(rs.getString("comune"));
        this.indirizzo.setVia(rs.getString("via"));
        this.indirizzo.setCivico(rs.getInt("num_civico"));

        this.coordinata = new CoordinataDTO();
        this.coordinata.setLatitudine(rs.getDouble("latitudine"));
        this.coordinata.setLongitudine(rs.getDouble("longitudine"));
        
        byte[] immagineBytes = rs.getBytes("immagine");
        this.immagine = (immagineBytes != null) ? Base64.getEncoder().encodeToString(immagineBytes) : null;
    }
}


