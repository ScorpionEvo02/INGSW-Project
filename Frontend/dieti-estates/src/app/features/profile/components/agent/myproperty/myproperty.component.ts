import { Component, OnInit } from '@angular/core';
import { CronologiaService } from '../../../services/cronologia.service'
import { ImmobileService } from '../../../services/Immobile.service';
import { Immobile } from '../../../../models/immobile.model';
import { CommonModule } from '@angular/common';
import { ModalProfileComponent } from '../../modal-profile/modal-profile.component';
import { ManagePropertyInternComponent } from '../../common/manage-property/manage-property.component';
import { PopUp } from '../../../../models/pop-up';
import { PopUpComponent } from '../../../../../base/pop-up/pop-upcomponent';

@Component({
  selector: 'myproperty-component',
  templateUrl: './myproperty.component.html',
  imports: [ CommonModule, ModalProfileComponent,
    ManagePropertyInternComponent, PopUpComponent ],
  styleUrls: ['./myproperty.component.css'],
})
export class MyProperty implements OnInit {
  immobili: Immobile[] = [];
  showProperty: boolean = false;
  popUpData: PopUp = new PopUp({titolo: ".", descrizione: ".", isSuccess: false});
  popUp: boolean = false;

  constructor(private immobileService: ImmobileService) {}

  ngOnInit(): void 
  {
    this.getImmobiliAgente();
  }

  updateImmobile(immobile: Immobile)
  {
    this.immobileService.updateImmobileData(immobile).subscribe({
      next: (response) => {
        this.popUpData = new PopUp({ titolo: "Successo!", descrizione: "Aggiornamento riuscito.", isSuccess: true });
        console.log('Immobile aggiornato con successo!', response);
        this.getImmobiliAgente();
      },
      error: (error) => {
        this.popUpData = new PopUp({ titolo: "Riprova!", descrizione: "Aggiornamento fallito.", isSuccess: false });
        console.error('Errore durante l\'aggiornamento dell\'immobile:', error);
      }
    });
    this.popUp = true;
  }

  getImmobiliAgente(): void
  {
    this.immobileService.getImmobileByAgente().subscribe({
      next: (data) => {
        this.immobili = data;
        console.log('Immobili ricevuti:', this.immobili);
      },
      error: (err) => console.error('Errore nel recupero degli immobili', err),
    });
  }

  mostraProperty(): void 
  {
    this.showProperty = true;
  }

  chiudiProperty(): void 
  {
    this.showProperty = false;
  }
}

