import { Component, OnInit } from '@angular/core';
import { CronologiaService } from '../../../services/cronologia.service';
import { Immobile } from '../../../../models/immobile.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'agencyDataAgent-component',
  templateUrl: './agencyDataAgent.component.html',
  imports: [CommonModule],
  styleUrls: ['./agencyDataAgent.component.css'],
})
export class AgencyDataAgentComponent implements OnInit {
  immobili: Immobile[] = [];

  constructor(private cronologiaService: CronologiaService) {}

  ngOnInit(): void {
    this.cronologiaService.getCronologiaCliente().subscribe({
      next: (data) => {
        this.immobili = data;
        console.log('Immobili ricevuti', this.immobili);
      },
      error: (err) => console.error('Errore nel recupero degli immobili', err),
    });
  }

  mostraPreferiti(): void {
    console.log('Proprietà visualizzate:', this.immobili);
  }
}

