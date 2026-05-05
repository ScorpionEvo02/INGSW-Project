import { Utente } from './utente.model';

export class Gestore extends Utente {
  codGestore?: number;
  codAmministratore: number;
  codAgenzia: number;

  constructor(data: any) {
    super(data);
    this.codGestore = data.codGestore;
    this.codAmministratore = data.codAmministratore;
    this.codAgenzia = data.codAgenzia;
  }
}
