import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Immobile } from '../../../models/immobile.model';
import { PreferitiService } from '../../../profile/services/preferiti.service';

@Component({
    selector: 'descrizione-immobile-component',
    templateUrl: './descrizione-immobile.component.html',
    styleUrls: ['./descrizione-immobile.component.css'],
    imports: [ CommonModule ]
})

export class DescrizioneImmobileComponent{
  @Input() immobile!: Immobile;

  constructor(private preferitiService : PreferitiService) { }
 
  addPreferito(): void
  {
    this.preferitiService.insertPreferitoUtente(this.immobile.codImmobile);
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