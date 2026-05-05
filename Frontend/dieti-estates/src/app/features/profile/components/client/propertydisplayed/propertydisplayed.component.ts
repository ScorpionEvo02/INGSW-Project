import { Component, OnInit } from '@angular/core';
import { CronologiaService } from '../../../services/cronologia.service';
import { Immobile } from '../../../../models/immobile.model';
import { ListImmobiliComponent } from '../../../../searchpage/components/list-immobili/list-immobili.component';
import { ModalProfileComponent } from '../../modal-profile/modal-profile.component';

@Component({
  selector: 'property-displayed-component',
  templateUrl: './propertydisplayed.component.html',
  styleUrls: ['./propertydisplayed.component.css'],
  imports: [ListImmobiliComponent, ModalProfileComponent]
})
export class PropertyDisplayedComponent implements OnInit {
  immobili: Immobile[] = [];
  showCronologia: boolean = false;

  constructor(private cronologiaService: CronologiaService) {}

  ngOnInit(): void 
  {
    this.cronologiaService.getCronologiaCliente().subscribe({
      next: (data) => this.immobili = data,
      error: (err) => console.error('Errore nel recupero della cronologia:', err)
    });
  }

  openCronologia(): void 
  {
    this.showCronologia = true;
  }

  closeCronologia(): void 
  {
    this.showCronologia = false;
  }
}
