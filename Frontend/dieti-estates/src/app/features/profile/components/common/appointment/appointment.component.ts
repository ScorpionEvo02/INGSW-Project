import { Component, Input, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Appuntamento } from '../../../../models/appuntamento.model';

@Component({
  selector: 'appointments-component',
  templateUrl: './appointments.component.html',
  imports: [CommonModule],
  styleUrls: [ '../offers/offers.component.css' ],
})
export class AppointmentsComponent {
  @Input() appuntamenti: Appuntamento[] = [];
  @Input() isAdmin: boolean = false;
  @Output() appointmentSignal = new EventEmitter<{ codPrenotazione: number, accettata: boolean }>();

  getStatusClass(stato: string): string 
  {
    switch (stato) 
    {
      case 'In attesa': return 'status-pending';
      case 'Accettata': return 'status-accepted';
      case 'Rifiutata': return 'status-rejected';
      default: return '';
    }
  }

  sendOfferResponse(codPrenotazione: number, accettata: boolean) 
  {
    this.appointmentSignal.emit({ codPrenotazione, accettata });
  }

  addToCalendar(appuntamento: Appuntamento): void 
  {
    const title = 'Appuntamento Dieti Estates';
    const startDate = this.formatDateForCalendar(appuntamento.prenotazione.orarioPrenotazione);
    const details = `Appuntamento per l'immobile in via ${appuntamento.immobile.indirizzo.via}, ${appuntamento.immobile.indirizzo.civico} ${appuntamento.immobile.indirizzo.citta} con l'agente ${appuntamento.agente.nome} ${appuntamento.agente.cognome}`;
    const location = appuntamento.immobile.indirizzo.via+','+appuntamento.immobile.indirizzo.civico+' '+appuntamento.immobile.indirizzo.citta;

    const calendarUrl = `https://www.google.com/calendar/render?action=TEMPLATE&text=${encodeURIComponent(title)}&dates=${startDate}&details=${encodeURIComponent(details)}&location=${encodeURIComponent(location)}`;

    window.open(calendarUrl, '_blank');
  }

  formatDateForCalendar(dateString: string): string 
  {
      const date = new Date(dateString);
      const year = date.getUTCFullYear();
      const month = this.pad(date.getUTCMonth() + 1);
      const day = this.pad(date.getUTCDate());
      const hours = this.pad(date.getUTCHours());
      const minutes = this.pad(date.getUTCMinutes());
      const seconds = this.pad(date.getUTCSeconds());
      return `${year}${month}${day}T${hours}${minutes}${seconds}Z`;
  }

  pad(num: number): string 
  {
      return num < 10 ? '0' + num : num.toString();
  }
}

