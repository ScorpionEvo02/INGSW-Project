import { Utente } from './utente.model';

export class Agente extends Utente {
  codAgente?: number;
  codGestore: number;
  codAgenzia: number;
  
  constructor(data: any) {
    super(data);
    this.codAgente = data.codAgente;
    this.codGestore = data.codGestore;
    this.codAgenzia = data.codAgenzia;
  }
}
