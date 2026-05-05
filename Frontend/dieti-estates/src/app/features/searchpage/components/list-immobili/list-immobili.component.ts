import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Immobile } from '../../../models/immobile.model';
import { Router } from '@angular/router';
import { PreferitiService } from '../../../profile/services/preferiti.service';

@Component({
    selector: 'list-immobili-component',
    templateUrl: './list-immobili.component.html',
    styleUrls: ['./list-immobili.component.css'],
    imports: [ CommonModule ]
})

export class ListImmobiliComponent implements OnInit{
  @Input() immobili!: Immobile[];
  @Input() searchpage: boolean = true;

  constructor(private router: Router, private preferitiService: PreferitiService) {}
  
  ngOnInit(): void 
  {
    const state = history.state;
    if (state  && this.searchpage) 
    {
      this.immobili = state.immobili;
    }
  }

  goImmobilePage(immobile: Immobile) 
  {
    if (!immobile) 
    { 
      console.log("Nessun immobile selezionato.");
      return;
    }
    this.router.navigate(['/immobile'], { state: { immobile: immobile } });
  }

  insertPreferitoUtente(codImmobile: number)
  {
    if (codImmobile == 0) 
    { 
      console.log("Nessun immobile selezionato.");
      return;
    }
    
    this.preferitiService.insertPreferitoUtente(codImmobile).subscribe({
      next: (response: string) => {
        console.log("Risposta dal backend:", response);
      },
      error: (error) => {
        console.error("Errore nell'inserimento del preferito:", error);
      }
    });
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