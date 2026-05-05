package org.vesuviano.dieti_estates.dto;

import java.util.Base64;

public class ImmagineImmobileDTO {
    private int codImmagine;
    private int codImmobile;
    private String immagine;

    public int getCodImmagine() {
        return codImmagine;
    }

    public void setCodImmagine(int codImmagine) {
        this.codImmagine = codImmagine;
    }

    public int getCodImmobile() {
        return codImmobile;
    }

    public void setCodImmobile(int codImmobile) {
        this.codImmobile = codImmobile;
    }

    public String getImmagine() 
    { 
    	return immagine; 
    }
    public void setImmagine(byte[] immagineBytes) 
    { 
        this.immagine = (immagineBytes != null) ? Base64.getEncoder().encodeToString(immagineBytes) : null;
    }
    public void setImmagine(String immagine) 
    { 
        this.immagine = immagine;
    }
}

