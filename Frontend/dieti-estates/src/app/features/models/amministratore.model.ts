import { Utente } from './utente.model';

export class Amministratore extends Utente {
  codAmministratore?: number;
  codAmministratoreInsert: number;
  codAgenzia: number;
  partitaIva: string;

  constructor(data: any) {
    super(data);
    this.codAmministratore = data.codAmministratore;
    this.codAmministratoreInsert = data.codAmministratoreInsert;
    this.partitaIva = data.pIva;
    this.codAgenzia = data.codAgenzia;
  }
}
