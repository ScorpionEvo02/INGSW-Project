import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { SearchService } from '../../../../homepage/services/search.service';
import { Immobile } from '../../../../models/immobile.model';
import { ModalProfileComponent } from '../../modal-profile/modal-profile.component';
import { ImmobileService } from '../../../services/Immobile.service';
import { PopUp } from '../../../../models/pop-up';
import { PopUpComponent } from '../../../../../base/pop-up/pop-upcomponent';

@Component({
  selector: 'manageproperty-component',
  standalone: true,
  templateUrl: './manageproperty.component.html',
  styleUrls: ['./manageproperty.component.css'],
  imports: [ CommonModule, ReactiveFormsModule, 
    ModalProfileComponent, PopUpComponent ],
})
export class ManagePropertyComponent implements OnInit {
  showPersonalData = false;
  sezioneCorrente = 1;
  propertyForm!: FormGroup;
  immagine!: string;

  popUp!: PopUp;
  showPopUp: boolean = false;

  constructor(private fb: FormBuilder, private immobileService: ImmobileService,
    private searchService: SearchService ) {}

  ngOnInit(): void {
    this.initForm();
  }

  private initForm(): void {
    this.propertyForm = this.fb.group({
      tipo: ['', Validators.required],
      descrizione: ['', Validators.required],
      costo: ['', [Validators.required, Validators.min(0)]],
      indirizzo: this.fb.group({
        citta: ['', [Validators.required]], // ← Senza accento
        via: ['', [Validators.required]],   // ← Minuscolo per uniformità
        num_civico: ['', [Validators.required, Validators.min(1)]],
        comune: ['', [Validators.required]],
      }),
      num_stanze: [1, [Validators.required, Validators.min(1), Validators.max(50)]],
      portineria: ['', Validators.required],
      cod_agente: [''],
      stato: ['Vendita', Validators.required],
      metratura: [10, [Validators.required, Validators.min(0)]],
      piano: [1, [Validators.required, Validators.min(0)]],
      classe_energetica: ['G', Validators.required]
    });
    
  }

  openPersonalData(): void {
    this.showPersonalData = true;
  }

  closePersonalData(): void {
    this.showPersonalData = false;
  }

  cambiaSezione(sezione: number): void {
    this.sezioneCorrente = sezione;
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

  salvaProprieta(): void {
    if (this.propertyForm.valid) {
      const formValue = this.propertyForm.value;
      const indirizzo = formValue.indirizzo;
      const fullAddress = `${indirizzo.via} ${indirizzo.num_civico}, ${indirizzo.citta}, ${indirizzo.comune}`;
  
      this.searchService.geocodeAddress(fullAddress).subscribe({
        next: (coordinate) => {
          console.log('Coordinate ottenute:', coordinate);
  
          this.immobileService.getNearPlaces(coordinate.lat, coordinate.lng).subscribe({
            next: (poiData) => {
              console.log('Punti di interesse vicini:', poiData);
  
              let etichetta = 'Zona residenziale'; 
  
              const poiTypes = poiData.features.map((poi: any) => poi.properties.categories).flat();
  
              if (poiTypes.includes('education.school')) {
                etichetta = 'Nei pressi di una scuola';
              }
              if (poiTypes.includes('education.university')) {
                etichetta = 'Nei pressi di un\'università';
              }
              if (poiTypes.includes('leisure.park')) {
                etichetta = 'Nei pressi di un parco';
              }
  
              const immobile: Immobile = {
                codImmobile: 0,
                numeroCamereLetto: 3,
                codAgente: 0,
                informazioniAggiuntive: JSON.stringify({}),
                tipo: formValue.tipo,
                descrizione: formValue.descrizione,
                costo: formValue.costo,
                indirizzo: {
                  citta: formValue.indirizzo.citta,
                  via: formValue.indirizzo.via,
                  civico: formValue.indirizzo.num_civico,
                  cap: 0,
                  comune: formValue.indirizzo.comune
                },
                ascensore: false,
                numeroStanze: formValue.num_stanze,
                metratura: formValue.metratura,
                piano: formValue.piano,
                portineria: formValue.portineria,
                stato: formValue.stato,
                classeEnergetica: formValue.classe_energetica,
                immagine: this.immagine.toString(),
                coordinata: {
                  latitudine: coordinate.lat,
                  longitudine: coordinate.lng
                },
                etichetta: etichetta, 
              };
  
              console.log('Immobile pronto per l\'invio:', immobile);
  
              this.immobileService.aggiungiImmobile(immobile).subscribe({
                next: () => {
                  console.log('Proprietà inserita con successo!', immobile);
                  this.propertyForm.reset();
                  this.closePersonalData();
                  this.popUp = new PopUp({ titolo: "Successo!", descrizione: "Inserimento riuscito.", isSuccess: true });
                  this.showPopUp = true;
                },
                error: (err) => {
                  this.popUp = new PopUp({ titolo: "Errore!", descrizione: "Errore nell'inserimento.", isSuccess: false });
                  this.showPopUp = true;
                }
              });
            },
            error: (err) => {
              console.error('Errore nel recupero dei POI da Geoapify:', err);
              this.popUp = new PopUp({ titolo: "Errore!", descrizione: "Errore nel recupero dei POI.", isSuccess: false });
              this.showPopUp = true;
            }
          });
        },
        error: (err) => {
          this.popUp = new PopUp({ titolo: "Errore!", descrizione: "Errore nella geocodifica coordinate.", isSuccess: false });
          this.showPopUp = true;
          console.error('Errore nel recupero delle coordinate:', err);
        }
      });
    } else {
      this.popUp = new PopUp({ titolo: "Errore!", descrizione: "Form non valido.", isSuccess: false });
      this.showPopUp = true;
      console.warn('Il form non è valido, controlla i campi!');
    }
  }

}


