import { Component, Input, EventEmitter, Output } from '@angular/core';
import { OffertaImmobile } from '../../../../models/offertaimmobile.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'offers-component',
  templateUrl: './offers.component.html',
  imports: [CommonModule],
  styleUrls: ['./offers.component.css'],
})
export class OffersComponent {
  @Input() offerteImmobili: OffertaImmobile[] = [];
  @Input() isAdmin: boolean = false;
  @Output() offerSignal = new EventEmitter<{ codOfferta: number, accettata: boolean }>();

  getStatusClass(stato: string): string 
  {
    switch (stato) 
    {
      case 'Pendente': return 'status-pending';
      case 'Accettata': return 'status-accepted';
      case 'Rifiutata': return 'status-rejected';
      default: return '';
    }
  }

  sendOfferResponse(codOfferta: number, accettata: boolean) 
  {
    this.offerSignal.emit({ codOfferta, accettata });
  }
}

