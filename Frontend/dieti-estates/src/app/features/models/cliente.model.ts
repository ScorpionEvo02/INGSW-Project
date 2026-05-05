import { Utente } from './utente.model';

export class Cliente extends Utente {
  codCliente: number;

  constructor(data: any) {
    super(data);
    this.codCliente = data.codCliente;
  }
}
