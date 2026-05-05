import { Component, OnInit, ViewChild, ElementRef  } from '@angular/core';
import { Router } from '@angular/router';
import { CronologiaService } from '../../../profile/services/cronologia.service';
import { Immobile } from '../../../models/immobile.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'history-component',
  templateUrl: './history.component.html',
  imports: [CommonModule],
  styleUrls: ['./history.component.css'],
})

export class HistoryComponent implements OnInit{
  immobili!: Immobile[];
  @ViewChild('scrollContainer', { static: false }) scrollContainer!: ElementRef;

  constructor(private cronologiaService : CronologiaService, private router: Router) { }

  ngOnInit(): void {
    this.cronologiaService.getCronologiaCliente().subscribe({
      next: (data) => {
        this.immobili = data;
        console.log('Immobili ricevuti', this.immobili);
      },
      error: (err) => console.error('Errore nel recupero degli immobili', err)
    });
  }

  scroll(direction: 'left' | 'right') {
    const container = this.scrollContainer.nativeElement;
    const scrollAmount = container.clientWidth * 0.9;
    container.scrollBy({ left: direction === 'left' ? -scrollAmount : scrollAmount, behavior: 'smooth' });
  }

  goImmobilePage(immobile: Immobile) {
    console.log("Dido2")
    if (!immobile) { 
      console.log("Nessun immobile selezionato.");
      return;
    }
    this.router.navigate(['/immobile'], { state: { immobile: immobile } });
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
