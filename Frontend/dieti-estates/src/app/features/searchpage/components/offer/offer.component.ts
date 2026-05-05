import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Immobile } from '../../../models/immobile.model';

@Component({
  selector: 'offer-component',
  templateUrl: './offer.component.html',
  styleUrls: ['./offer.component.css'],
  imports: [ CommonModule, FormsModule ]
})
export class OfferComponent {
  
  @Input() immobile!: Immobile;

  @Output() offerEvent = new EventEmitter<{ offer: number }>();
  @Output() closeEvent = new EventEmitter<void>();

  completed: boolean = true;
  amount!: number;

  onSubmit() {
    this.offerEvent.emit({ offer: this.amount });
  }

  close() {
    this.closeEvent.emit(); 
  }

  formatCosto(costo: number): string 
  {
    if (isNaN(costo)) 
    {
      return '';
    }

    const costoString = costo.toString();
    let formattedCosto = '';
    let count = 0;

    for (let i = costoString.length - 1; i >= 0; i--) 
    {
      formattedCosto = costoString[i] + formattedCosto;
      count++;
      if (count % 3 === 0 && i !== 0) 
      {
        formattedCosto = '.' + formattedCosto;
      }
    }

    return formattedCosto;
  }

}
