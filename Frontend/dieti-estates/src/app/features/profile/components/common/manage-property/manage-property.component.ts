import { Component, Input, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Immobile } from '../../../../models/immobile.model';
import { ImmagineImmobileService } from '../../../../searchpage/services/immagine-immobile.service';
import { PopUp } from '../../../../models/pop-up';
import { PopUpComponent } from '../../../../../base/pop-up/pop-upcomponent';
import { ImmagineImmobile } from '../../../../models/immagineimmobile.model';

@Component({
  selector: 'manage-property-intern-component',
  templateUrl: './manage-property.component.html',
  imports: [ CommonModule, FormsModule, PopUpComponent ],
  styleUrls: ['./manage-property.component.css'],
})
export class ManagePropertyInternComponent {
  @Input() immobili: Immobile[] = [];
  @Output() edit = new EventEmitter<Immobile>();
  classiEnergetiche: string[] = ['A4', 'A3', 'A2', 'A1', 'B', 'C', 'D', 'E', 'F', 'G'];
  showPopUp = false;
  popUpData: PopUp = new PopUp({
    titolo: "Riprova.",
    descrizione: "Errore nell'inserimento dell'immagine.",
    isSuccess: false
  });

  editingImmobile: Immobile | null = null;
  immaginiImmobile!: ImmagineImmobile[];
  codImmobile!: number;
  immagine!: string;

  constructor (private immagineImmobileService: ImmagineImmobileService) {}

  openEditModal(immobile: Immobile): void {
    this.editingImmobile = { ...immobile }; 
  }
  
  openAddImage(codImmobile: number): void
  {
    this.codImmobile = codImmobile;
    this.recoverImmaginiImmobile(codImmobile);
  }


  annullaModifica(): void {
    this.editingImmobile = null;
    this.immaginiImmobile = [];
    this.codImmobile = 0;
    this.immagine = "";
  }

  salvaModifiche(): void {
    if (this.editingImmobile) {
      this.edit.emit( this.editingImmobile );
    }
  }

  markAsSold(immobile: Immobile): void {
    const immobileAggiornato = { ...immobile, stato: 'Venduto' };
    this.edit.emit( immobileAggiornato );
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

  onClosePopUp(): void {
    this.showPopUp = false;
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

  salvaImmagine(): void
  {
    this.immagineImmobileService.insertImmagineImmobile(this.codImmobile, this.immagine).subscribe({
      next: () => {
        this.popUpData = new PopUp({ titolo: "Successo!", descrizione: "Inserimento riuscito.", isSuccess: true });
      },
      error: (err) => {
        this.popUpData = new PopUp({ titolo: "Errore!", descrizione: "Errore nell'inserimento.", isSuccess: false });
      }
    });
    this.recoverImmaginiImmobile(this.codImmobile);

    this.showPopUp = true;
  }

    caricaFoto(event: any): void {
      const file = event.target.files[0]; 
      if (file) {
        const reader = new FileReader();
        
        reader.onload = () => {
          const base64String = (reader.result as string).split(',')[1]; 
          this.immagine = base64String; 
        };
        
        reader.readAsDataURL(file); 
      }
    }
}

