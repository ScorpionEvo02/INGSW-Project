import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Gestore } from '../../../../models/gestore.model';
import { CookieGestore } from '../../../../../services/cookie/utente/gestore.service';
import { GestoreDataService } from '../../../services/gestoreData.service';
import { ModalProfileComponent } from "../../modal-profile/modal-profile.component";
import { CommonModule } from '@angular/common';
import { PopUpComponent } from '../../../../../base/pop-up/pop-upcomponent';
import { PopUp } from '../../../../models/pop-up';

@Component({
  selector: 'personalDataManager-component',
  templateUrl: './personalDataManager.component.html',
  styleUrls: ['./personalDataManager.component.css'],
  imports: [CommonModule, ModalProfileComponent, PopUpComponent, ReactiveFormsModule],
})
export class PersonalDataManagerComponent implements OnInit {
  gestore: Gestore | null = null;
  showPersonalData = false;
  gestoreForm!: FormGroup;
  passwordTouched = false;
  popUp!: PopUp;
  showPopUp: boolean = false;

  constructor(private gestoreService: GestoreDataService, private fb: FormBuilder, private cookiegestore: CookieGestore) {}

  ngOnInit(): void {
    this.gestoreForm = this.fb.group({
      nome: ['', [Validators.required, Validators.minLength(2)]],
      cognome: ['', [Validators.required, Validators.minLength(2)]],
      codiceFiscale: ['', [Validators.required, Validators.minLength(16)]],
      dataNascita: ['', [Validators.required]],
      genere: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', []],
      codGestore: [''],
      codAgenzia: [''],
      codAmministratore: [''],
      indirizzo: this.fb.group({
        via: ['', [Validators.required]],
        citta: ['', [Validators.required]],
        civico: ['', [Validators.required, Validators.min(1)]]
      })
    });

    this.gestoreService.getGestoreData().subscribe({
      next: (data: Gestore) => {
        console.log(' Dati ricevuti:', data);
        if (data) {
          this.gestore = data;
          this.gestoreForm.patchValue({
            nome: data.nome,
            cognome: data.cognome,
            codiceFiscale: data.codiceFiscale,
            dataNascita: data.dataNascita,
            genere: data.genere,
            email: data.email,
            password: '',
            codGestore: data.codGestore,
            codAgenzia: data.codAgenzia,
            codAmministratore: data.codAmministratore,
            indirizzo: {
              via: data.indirizzo?.via,
              citta: data.indirizzo?.citta,
              civico: data.indirizzo?.civico
            }
          });

          // Disabilita i campi non modificabili
          this.gestoreForm.get('nome')?.disable();
          this.gestoreForm.get('cognome')?.disable();
          this.gestoreForm.get('codiceFiscale')?.disable();
          this.gestoreForm.get('dataNascita')?.disable();
          this.gestoreForm.get('genere')?.disable();
        }
      },
      error: (err: any) => {
        console.error('Errore nel recupero dati del gestore.', err);
      },
    });
  }

  onPasswordFocus(): void {
    if (!this.passwordTouched) {
      this.passwordTouched = true;
      this.gestoreForm.get('password')?.setValue('');
    }
  }

  openPersonalData(): void {
    this.showPersonalData = true;
  }

  closePersonalData(): void {
    this.showPersonalData = false;
  }

  salvaDati(): void {
    if (this.gestoreForm.valid) {

      const idSessione = this.cookiegestore.getCookie();
      const formValues = this.gestoreForm.getRawValue();
      
      if (!formValues.password || formValues.password.trim() === '') {
        formValues.password = null;
      }

      const gestoreAggiornato = { 
        ...this.gestore, 
        ...formValues,  
        idSessione: idSessione
      };

      this.gestoreService.updateGestoreData(gestoreAggiornato).subscribe({
        next: () => {
          this.popUp = new PopUp({ titolo: 'Successo!', descrizione: 'I tuoi dati sono stati modificati.', isSuccess: true });
          this.showPopUp = true;
          this.closePersonalData();
        },
        error: (err: any) => {
          this.popUp = new PopUp({ titolo: 'Errore!', descrizione: 'I dati inseriti non sono corretti, riprova.', isSuccess: false });
          this.showPopUp = true;
          console.error('Errore nell’aggiornamento dei dati.', err);
        },
      });
    } else {
      console.warn('Il form non è valido, controlla i campi!');
    }
  }

}
