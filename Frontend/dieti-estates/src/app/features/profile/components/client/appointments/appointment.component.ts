import { Component, OnInit } from '@angular/core'
import { Appuntamento } from '../../../../models/appuntamento.model';
import { AppuntamentoService } from '../../../../searchpage/services/appuntamento.service';
import { CommonModule } from '@angular/common';
import { AppointmentsComponent } from '../../common/appointment/appointment.component';
import { ModalProfileComponent } from '../../modal-profile/modal-profile.component';

@Component({
  selector: 'appointment-component',
  templateUrl: './appointment.component.html',
  imports: [ CommonModule, AppointmentsComponent, ModalProfileComponent ],
  styleUrls: ['./appointment.component.css'],
})
export class AppointmentComponent implements OnInit {
  appuntamenti: Appuntamento[] = [];
  showAppointment: boolean = false;

  constructor(private appuntamentoService: AppuntamentoService) {}

  ngOnInit(): void {
    this.appuntamentoService.getAppuntamentiCliente().subscribe({
      next: (data) => {
        this.appuntamenti = data;
        console.log('Appuntamenti ricevuti', this.appuntamenti);
      },
      error: (err) => console.error('Errore nel recupero degli immobili', err),
    });
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

