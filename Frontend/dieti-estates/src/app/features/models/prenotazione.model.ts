export class Prenotazione {
    codPrenotazione: number;
    statoPrenotazione: string;
    luogo: string;
    citta: string;
    orarioPrenotazione: string; 
    codCliente: number;
    codImmobile: number;
    codAgente: number;


    constructor(data: any) {
        this.codPrenotazione = data.codPrenotazione;
        this.statoPrenotazione = data.statoPrenotazione;
        this.luogo = data.luogo;
        this.citta = data.citta;
        this.orarioPrenotazione = data.orarioPrenotazione;
        this.codCliente = data.codCliente;
        this.codImmobile = data.codImmobile;
        this.codAgente = data.codAgente;
    }
}