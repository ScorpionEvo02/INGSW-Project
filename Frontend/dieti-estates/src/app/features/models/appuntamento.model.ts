import { Immobile } from "./immobile.model";
import { Agente } from "./agente.model";
import { Prenotazione } from "./prenotazione.model";

export class Appuntamento {
  prenotazione: Prenotazione;
  immobile: Immobile;
  agente: Agente;

  constructor(data: any) {
    this.prenotazione = data.prenotazione;
    this.immobile = data.immobile;
    this.agente = data.agente;
  }
}
