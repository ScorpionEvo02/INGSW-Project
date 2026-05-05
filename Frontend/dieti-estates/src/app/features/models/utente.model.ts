import { Indirizzo } from "./indirizzo.model";

export abstract class Utente {
  nome: string;
  cognome: string;
  indirizzo: Indirizzo;
  dataNascita: Date;
  codiceFiscale: string;
  email: string;
  password: string;
  genere: string;
  idSessione: string;

  constructor(data: any) {
    this.nome = data.nome;
    this.cognome = data.cognome;
    this.indirizzo = data.indirizzo;
    this.dataNascita = data.dataNascita ? new Date(data.dataNascita) : new Date();
    this.codiceFiscale = data.codiceFiscale;
    this.email = data.email;
    this.password = data.password;
    this.genere = data.genere;
    this.idSessione = data.idSessione;
  }
}
