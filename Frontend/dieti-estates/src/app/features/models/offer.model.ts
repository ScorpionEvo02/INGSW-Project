export class Offerta {
    codOfferta: number;
    ammontare: number;
    statoOfferta: string;
    codCliente: number;
    codImmobile: number;
  
    constructor(data?: any) {
      this.codOfferta = data.codOfferta;
      this.ammontare = data?.ammontare ?? 0;
      this.statoOfferta = data?.statoOfferta ?? '';
      this.codCliente = data.codCliente;
      this.codImmobile = data.codImmobile;
    }
  }
  