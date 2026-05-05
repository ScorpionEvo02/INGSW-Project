import { Coordinata } from "./coordinata.model";
import { Indirizzo } from "./indirizzo.model";

export class Immobile {
  codImmobile: number;
  tipo: string;
  descrizione: string;
  costo: number;
  indirizzo: Indirizzo;
  metratura: number;
  piano: number;
  numeroStanze: number;
  numeroCamereLetto: number;
  ascensore: boolean;
  portineria: string;
  classeEnergetica: string;
  informazioniAggiuntive: any;
  codAgente: number;
  immagine: string | null;
  coordinata: Coordinata;
  stato: string;
  etichetta: string;

  constructor(data: any) {
      this.codImmobile = data.codImmobile;
      this.tipo = data.tipo;
      this.descrizione = data.descrizione;
      this.costo = data.costo;
      this.indirizzo = data.indirizzo;
      this.piano = data.piano;
      this.metratura = data.metratura;
      this.numeroStanze = data.numeroStanze;
      this.numeroCamereLetto = data.numeroCamereLetto;
      this.ascensore = data.ascensore;
      this.portineria = data.portineria;
      this.classeEnergetica = data.classeEnergetica;
      this.etichetta = data.etichetta;

      try {
          this.informazioniAggiuntive = JSON.parse(data.informazioniAggiuntive);
      } catch (error) {
          this.informazioniAggiuntive = data.informazioniAggiuntive ?? {};
      }

      this.codAgente = data.codAgente;
      this.immagine = data.immagine;
      this.coordinata = data.coordinata;
      this.stato = data.stato;
  }
}