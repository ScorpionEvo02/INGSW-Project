import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Gestore } from '../../../../models/gestore.model';
import { GestoreDataService } from '../../../services/gestoreData.service';
import { ModalProfileComponent } from '../../modal-profile/modal-profile.component';
import { ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'manageAdmin-component',
  templateUrl: './manageAdmin.component.html',
  styleUrls: ['./manageAdmin.component.css'],
  imports: [CommonModule, ModalProfileComponent, ReactiveFormsModule],
})
export class ManageAdminComponent implements OnInit {
  @Input() gestori: Gestore[] = [];
  showGestori = false;

  constructor(private gestoreService: GestoreDataService) {}

  ngOnInit(): void {
    this.gestoreService.getAllGestori().subscribe({
      next: (data: Gestore[]) => {
        this.gestori = data;
      },
      error: (err: any) => {
        console.error('Errore nel recupero degli agenti.', err);
      },
    });
  }

  openGestori(): void {
    this.showGestori = true;
  }

  closeGestori(): void {
    this.showGestori = false;
  }

  confermaEliminazioneGestore(gestore: Gestore): void {
    const conferma = confirm(`Sei sicuro di voler eliminare il gestore ${gestore.nome} ${gestore.cognome}?`);
    
    if (conferma) {
      this.eliminaGestore(gestore); // Supponiamo che ogni gestore abbia un campo "id"
    } else {
      console.log("Eliminazione annullata dall'utente.");
    }
  }
  
  eliminaGestore(gestore: Gestore): void {
    this.gestoreService.deleteGestore(gestore).subscribe({
      next: () => {
        console.log("Gestore eliminato con successo.");
        // Rimuove il gestore dalla lista in base al codGestore
        this.gestori = this.gestori.filter(g => g.codGestore !== gestore.codGestore);
      },
      error: (err: any) => {
        console.error("Errore durante l'eliminazione del gestore:", err);
      }
    });
  }
  
}

