import { Component, OnInit } from '@angular/core';
import { Appuntamento } from '../../../../models/appuntamento.model';
import { AppuntamentoService } from '../../../../searchpage/services/appuntamento.service';
import { CommonModule } from '@angular/common';
import { AppointmentsComponent } from '../../common/appointment/appointment.component';
import { ModalProfileComponent } from '../../modal-profile/modal-profile.component';
import { PopUpComponent } from '../../../../../base/pop-up/pop-upcomponent';
import { PopUp } from '../../../../models/pop-up';

@Component({
  selector: 'appointmentAgent-component',
  templateUrl: './appointmentAgent.component.html',
  imports: [ CommonModule, AppointmentsComponent, ModalProfileComponent, PopUpComponent ],
  styleUrls: ['./appointmentAgent.component.css'],
})
export class AppointmentAgentComponent implements OnInit {
  appuntamenti: Appuntamento[] = [];
  showAppointment: boolean = false;
  popUpData: PopUp = new PopUp({titolo: "Riprova.", descrizione: "Errore nel recupero degli appuntamenti.", isSuccess: false});
  popUp: boolean = false;

  constructor(private appuntamentoService: AppuntamentoService) {}

  ngOnInit(): void {
    this.getAppuntamentiAgenti();
  }

  getAppuntamentiAgenti(): void
  {
    this.appuntamentoService.getAppuntamentiAgente().subscribe({
      next: (data) => {
        this.appuntamenti = data;
        console.log('Appuntamenti ricevuti', this.appuntamenti);
      },
      error: (err) => console.error('Errore nel recupero degli immobili', err),
    });
  }

  manageOffertaSignal(event: { 
      codPrenotazione: number, 
      accettata: boolean 
    }) 
    {
      if(!event.codPrenotazione)
      {
        this.popUpData = new PopUp({ titolo: "Riprova!", descrizione: "Codice offerta non valido.", isSuccess: false });
        return;
      }
      else if(event.accettata)
      {
        this.appuntamentoService.acceptAppointment(event.codPrenotazione).subscribe({
          next: (data) => {
            this.popUpData = new PopUp({ titolo: "Successo!", descrizione: "Prenotazione accettata.", isSuccess: true });
          },
          error: (err) => { 
            console.error('Errore:', err)
            this.popUpData = new PopUp({ titolo: "Riprova!", descrizione: "Errore server.", isSuccess: false });
          },
        });
      }
      else
      {
        this.appuntamentoService.rejectAppointment(event.codPrenotazione).subscribe({
          next: (data) => {
            this.popUpData = new PopUp({ titolo: "Successo!", descrizione: "Prenotazione rifiutata.", isSuccess: true });
          },
          error: (err) => { 
            console.error('Errore:', err)
            this.popUpData = new PopUp({ titolo: "Riprova!", descrizione: "Errore server.", isSuccess: false });
          },
        });
      }
      this.popUp = true;
      this.getAppuntamentiAgenti();
    }

  mostraAppointment(): void
  {
    this.showAppointment = true;
  }
  
  closeAppointment(): void 
  {
    this.showAppointment = false;
  }
}

