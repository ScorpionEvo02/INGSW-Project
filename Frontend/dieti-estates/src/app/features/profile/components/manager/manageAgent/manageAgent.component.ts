/*import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Agente } from '../../../../models/agente.model';
import { AgenteDataService } from '../../../services/agenteData.service';
import { ModalProfileComponent } from "../../modal-profile/modal-profile.component";

@Component({
  selector: 'manageAgent-component',
  templateUrl: './manageAgent.component.html',
  imports: [CommonModule, ModalProfileComponent],
  styleUrls: ['./manageAgent.component.css'],
})
export class ManageAgentComponent implements OnInit {
  showPersonalData: boolean = false; 
  agenti: Agente[] = []; 
  filteredAgenti: Agente[] = []; 

  constructor(private agenteService: AgenteDataService) {}

  ngOnInit(): void {
    this.loadAgenti();
  }

  // Metodo per caricare tutti gli agenti dal backend
  loadAgenti(): void {
    this.agenteService.getAllAgenti().subscribe({
      next: (data) => {
        this.agenti = data;
        this.filteredAgenti = data; // Copia iniziale per il filtro
      },
      error: (err) => {
        console.error('Errore nel recupero degli agenti:', err);
      }
    });
  }

  // Metodo per filtrare gli agenti mentre si digita nella barra di ricerca
  filterAgenti(event: Event): void {
    const query = (event.target as HTMLInputElement).value.toLowerCase();
    this.filteredAgenti = this.agenti.filter(agente =>
      agente.nome.toLowerCase().includes(query) || 
      agente.cognome.toLowerCase().includes(query) ||
      agente.email.toLowerCase().includes(query)
    );
  }

  // Metodo per aprire il modale con la lista degli agenti
  openPersonalData(): void {
    this.showPersonalData = true;
  }
  
  // Metodo per chiudere il modale
  closePersonalData(): void {
    this.showPersonalData = false;
  }

  // Metodo placeholder per visualizzare i dettagli di un agente (da espandere in futuro)
  viewAgentDetails(agente: Agente): void {
    console.log("Dettagli dell'agente:", agente);
    // Qui puoi implementare la logica per aprire un nuovo modale con le info dettagliate
  }
}*/

 import { Component, Input, OnInit } from '@angular/core'; 
import { CommonModule } from '@angular/common';
import { ModalProfileComponent } from '../../modal-profile/modal-profile.component';
import { AgenteDataService } from '../../../services/agenteData.service';
import { Agente } from '../../../../models/agente.model';
import { ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'manageAgent-component',
  templateUrl: './manageAgent.component.html',
  styleUrls: ['./manageAgent.component.css'],
  imports: [CommonModule, ModalProfileComponent, ReactiveFormsModule],
})
export class manageAgentComponent implements OnInit {
  @Input() agents: Agente[] = [];
  showAgents = false;

  constructor(private agenteService: AgenteDataService) {}

  ngOnInit(): void {
    this.agenteService.getAllAgenti().subscribe({
      next: (data: Agente[]) => {
        this.agents = data;
      },
      error: (err: any) => {
        console.error('Errore nel recupero degli agenti.', err);
      },
    });
  }

  openAgents(): void {
    this.showAgents = true;
  }

  closeAgents(): void {
    this.showAgents = false;
  }

    confermaEliminazioneAgente(agente: Agente): void {
      const conferma = confirm(`Sei sicuro di voler eliminare il gestore ${agente.nome} ${agente.cognome}?`);
      
      if (conferma) {
        this.eliminaAgente(agente); 
      } else {
        console.log("Eliminazione annullata dall'utente.");
      }
    }
    
    eliminaAgente(agente: Agente): void {
      this.agenteService.deleteAgente(agente).subscribe({
        next: () => {
          console.log("Gestore eliminato con successo.");
          // Rimuove il gestore dalla lista in base al codGestore
          this.agents = this.agents.filter(g => g.codAgente !== agente.codAgente);
        },
        error: (err: any) => {
          console.error("Errore durante l'eliminazione del gestore:", err);
        }
      });
    }
}
  
