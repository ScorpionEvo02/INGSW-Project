import { Component, Input  } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Immobile } from '../../../models/immobile.model';
import { Agente } from '../../../models/agente.model';
import { CalendarComponent } from '../calendar/calendar.component';
import { OfferComponent } from '../offer/offer.component';
import { PrenotazioneService } from '../../../../services/prenotazione.service';
import { OffertaService } from '../../../../services/offerta.service';
import { PopUp } from '../../../models/pop-up';
import { PopUpComponent } from '../../../../base/pop-up/pop-upcomponent';

@Component({
    selector: 'prenotazione-immobile-component',
    templateUrl: './prenotazione-immobile.component.html',
    styleUrls: ['./prenotazione-immobile.component.css'],
    imports: [ CommonModule, CalendarComponent, PopUpComponent, OfferComponent ]
})

export class PrenotazioneImmobileComponent{
  @Input() immobile!: Immobile;
  @Input() agente!: Agente;
  showPrenotazione: boolean = false;
  popUp!: PopUp;
  showPopUp: boolean = false;
  chosenPopUp!: 'appuntamento' | 'offerta';

  constructor(private prenotazioneService: PrenotazioneService, 
    private offertaService: OffertaService,  ) {}

  openPrenotazione(choose: 'appuntamento' | 'offerta'): void
  {
    this.chosenPopUp = choose;
    this.showPrenotazione = true;
  }

  closePrenotazione(): void
  {
    this.showPrenotazione = false;
  }

  onPrenotazioneReceived(event: { timestamp: string }) {
    console.log('Routine di prenotazione avviata con:', event);
    this.insertPrenotazioneCliente(event.timestamp);
  }
  
  insertPrenotazioneCliente(dataPrenotazione: string): void {
    this.prenotazioneService.insertPrenotazione(this.immobile.codImmobile, dataPrenotazione, this.immobile.codAgente)
      .subscribe({
        next: (response) => { 
          console.log("Prenotazione inserita con successo:", response);
          this.popUp = new PopUp({ titolo: "Successo!", descrizione: "Prenotazione completata.", isSuccess: true });
        },
        error: (error) => {
          if (error.status === 400) {
            this.popUp = new PopUp({ titolo: "Errore!", descrizione: "Richiesta non valida.", isSuccess: false });
          } else if (error.status === 401) {
            this.popUp = new PopUp({ titolo: "Non autorizzato!", descrizione: "Sessione scaduta o non valida.", isSuccess: false });
          } else if (error.status === 500) {
            this.popUp = new PopUp({ titolo: "Errore server!", descrizione: "Problema interno del server.", isSuccess: false });
          } else {
            this.popUp = new PopUp({ titolo: "Errore!", descrizione: "Qualcosa è andato storto.", isSuccess: false });
          }
        }
      });
    this.showPopUp = true;
  }
  

  onOfferReceived(event: { offer: number }) {
    console.log('Routine di offerta avviata con:', event.offer);
    this.insertOffertaReceived(event.offer);
  }

  insertOffertaReceived(offer: number)
  {
    if(offer == 0)
    {
      this.popUp = new PopUp({ titolo: "Errore!", descrizione: "Inserisci un prezzo maggiore.", isSuccess: false });
    }
    else
    {
      this.offertaService.insertOfferta(this.immobile.codImmobile, offer)
      .subscribe({
        next: (response) => { 
          console.log("Offerta inserita con successo:", response);
          this.popUp = new PopUp({ titolo: "Successo!", descrizione: "Offerta inviata.", isSuccess: true });
        },
        error: (error) => {
          console.log("Status", error.status)
          if (error.status === 400) 
          {
            this.popUp = new PopUp({ titolo: "Errore!", descrizione: "Richiesta non valida.", isSuccess: false });
          }
          else if (error.status === 401) 
          {
            this.popUp = new PopUp({ titolo: "Non autorizzato!", descrizione: "Sessione scaduta o non valida.", isSuccess: false });
          }
          else if (error.status === 500) 
          {
            this.popUp = new PopUp({ titolo: "Errore server!", descrizione: "Problema interno del server.", isSuccess: false });
          } 
          else 
          {
            this.popUp = new PopUp({ titolo: "Errore!", descrizione: "Qualcosa è andato storto.", isSuccess: false });
          }
        }
      });
    }
    this.showPopUp = true;
  }
}