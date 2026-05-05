import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from '../../../../base/header/header.component';
import { ImmaginiImmobileComponent } from '../../components/immagini-immobile/immagini-immobile.component';
import { Immobile } from '../../../models/immobile.model';
import { ImmagineImmobile } from '../../../models/immagineimmobile.model';
import { Agente } from '../../../models/agente.model';
import { ImmagineImmobileService } from '../../services/immagine-immobile.service';
import { AgentePrenotazioneService } from '../../services/agente.service';
import { CronologiaService } from '../../../profile/services/cronologia.service';
import { PrenotazioneImmobileComponent } from '../../components/prenotazione-immobile/prenotazione-immobile.component';
import { DescrizioneImmobileComponent } from '../../components/descrizione-immobile/descrizione-immobile.component';

@Component({
  selector: 'searchpage-component',
  templateUrl: './immobile.component.html',
  styleUrls: ['./immobile.component.css'],
  standalone: true,
  imports: [HeaderComponent, CommonModule, 
    DescrizioneImmobileComponent, PrenotazioneImmobileComponent,
    ImmaginiImmobileComponent ]
})

export class ImmobileComponent implements OnInit {
  immobile!: Immobile;
  immaginiImmobile: ImmagineImmobile[] = [];
  agente!: Agente;
  popUp: boolean = true;

  constructor(private immagineImmobileService : ImmagineImmobileService, 
    private agentePrenotazioneService :AgentePrenotazioneService, 
    private cronologiaService: CronologiaService) { }

  ngOnInit(): void 
  {
    const state = history.state;
    if (state) 
    {
      this.immobile = state.immobile;
    }

    this.recoverImmaginiImmobile(state.immobile.codImmobile);
    this.recoverAgenteByCodAgente(state.immobile.codAgente);
    this.insertImmobileCronologiaUtente(state.immobile.codImmobile);
  }

  recoverImmaginiImmobile(codImmobile: number)
  {
    this.immagineImmobileService.getImmaginiImmobileById(codImmobile)
    .subscribe({
      next: (immaginiImmobile: ImmagineImmobile[]) => {
        this.immaginiImmobile = immaginiImmobile;
        console.log(immaginiImmobile)

      },
      error: (error) => {
        console.error("Errore nel recupero delle foto degli immobili:", error);
      }
    });
  }

  recoverAgenteByCodAgente(codAgente: number)
  {
    this.agentePrenotazioneService.getAgenteById(codAgente)
    .subscribe({
      next: (agente: Agente) => {
        this.agente = agente;
        console.log("Agente:", agente);
      },
      error: (error) => {
        console.error("Errore nel recupero dell'agente:", error);
      }
    });
  }

  insertImmobileCronologiaUtente(codImmobile: number)
  {
    this.cronologiaService.insertImmobileCronologia(codImmobile).subscribe({
      next: (response: Response) => {
        console.log("Response:", response);
      },
      error: (error) => {
        console.error("Errore nell'inserimento della cronologia:", error);
      }
    });;
  
  }
}
