export class PopUp {
    titolo: string;
    descrizione: string;
    isSuccess: boolean;
  
    constructor(data: any = {}) {
      this.titolo = data.titolo;
      this.descrizione = data.descrizione;
      this.isSuccess = data.isSuccess;
    }
}