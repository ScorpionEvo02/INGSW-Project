export class ImmagineImmobile {
  codImmagine: number;
  codImmobile: number;
  immagine: string;
  

  constructor(data: any) {
      this.codImmagine = data.codImmagine;
      this.codImmobile = data.codImmobile;
      this.immagine = data.immagine;     
  }
}