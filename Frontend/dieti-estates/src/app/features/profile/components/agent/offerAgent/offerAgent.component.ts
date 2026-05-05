import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OffertaImmobile } from '../../../../models/offertaimmobile.model';
import { OffertaService } from '../../../../../services/offerta.service';
import { OffersComponent } from '../../common/offers/offers.component';
import { ModalProfileComponent } from '../../modal-profile/modal-profile.component';
import { PopUpComponent } from '../../../../../base/pop-up/pop-upcomponent';
import { PopUp } from '../../../../models/pop-up';

@Component({
  selector: 'offerAgent-component',
  templateUrl: './offerAgent.component.html',
  imports: [ CommonModule, OffersComponent, ModalProfileComponent, PopUpComponent ],
  styleUrls: ['./offerAgent.component.css'],
})
export class OfferAgentComponent implements OnInit {
  offerteImmobile: OffertaImmobile[] = [];
  showOffer = false;
  popUpData: PopUp = new PopUp({titolo: "Riprova.", descrizione: "Errore nel recupero delle offerte.", isSuccess: false});
  popUp: boolean = false;

  constructor(private offertaService: OffertaService) {}
  
  ngOnInit(): void {
    this.getOfferteAgente();
  }
  
  getOfferteAgente(): void
  {
    this.offertaService.getOffersAgente().subscribe({
      next: (data) => {
        this.offerteImmobile = data;
        console.log('Offerte ricevute', this.offerteImmobile);
      },
      error: (err) => console.error('Errore nel recupero delle offerte', err),
    });
  }

  manageOffertaSignal(event: { 
    codOfferta: number, 
    accettata: boolean 
  }) 
  {
    console.log(`Offerta ${event.codOfferta} ${event.accettata ? 'accettata' : 'rifiutata'}`);
    if(!event.codOfferta)
    {
      this.popUpData = new PopUp({ titolo: "Riprova!", descrizione: "Codice offerta non valido.", isSuccess: false });
      return;
    }
    else if(event.accettata)
    {
      this.offertaService.acceptOffer(event.codOfferta).subscribe({
        next: (data) => {
          this.popUpData = new PopUp({ titolo: "Successo!", descrizione: "Offerta accettata.", isSuccess: true });
        },
        error: (err) => { 
          console.error('Errore nel recupero delle offerte', err)
          this.popUpData = new PopUp({ titolo: "Riprova!", descrizione: "Errore server.", isSuccess: false });
        },
      });
    }
    else
    {
      this.offertaService.rejectOffer(event.codOfferta).subscribe({
        next: (data) => {
          this.popUpData = new PopUp({ titolo: "Successo!", descrizione: "Offerta rifiutata.", isSuccess: true });
        },
        error: (err) => { 
          console.error('Errore nel recupero delle offerte', err)
          this.popUpData = new PopUp({ titolo: "Riprova!", descrizione: "Errore server.", isSuccess: false });
        },
      });
    }
    this.getOfferteAgente();
  }

  openOffer(): void 
  {
    this.showOffer = true;
  }

  closeOffer(): void 
  {
    this.showOffer = false;
  }
}

