import { Component, OnInit } from '@angular/core';
import {  PreferitiService } from '../../../services/preferiti.service';
import { Immobile } from '../../../../models/immobile.model';
import { CommonModule } from '@angular/common';
import { ModalProfileComponent } from '../../modal-profile/modal-profile.component';
import { ListImmobiliComponent } from '../../../../searchpage/components/list-immobili/list-immobili.component';

@Component({
  selector: 'favorites-component',
  templateUrl: './favorites.component.html',
  imports: [ CommonModule, ModalProfileComponent, ListImmobiliComponent ],
  styleUrls: ['./favorites.component.css',],
})
export class FavoritesComponent implements OnInit {
  immobili: Immobile[] = [];
  showPreferiti = false;

  constructor(private preferitiService: PreferitiService) {}

  ngOnInit(): void 
  {
    this.preferitiService.getPreferitiCliente().subscribe({
      next: (data) => {
        this.immobili = data;
      },
      error: (err) => {
        console.error('Errore nel recupero dei preferiti.', err)
      },
    });
  }

  openPreferiti(): void 
  {
    this.showPreferiti = true;
  }

  closePreferiti(): void 
  {
    this.showPreferiti = false;
  }
}

