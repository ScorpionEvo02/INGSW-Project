import { Immobile } from "./immobile.model";
import { Offerta } from "./offer.model";

export class OffertaImmobile {
  offerta: Offerta;
  immobile: Immobile;

  constructor(data: any) {
    this.offerta = new Offerta(data.offerta);
    this.immobile = new Immobile(data.immobile);
  }
}
