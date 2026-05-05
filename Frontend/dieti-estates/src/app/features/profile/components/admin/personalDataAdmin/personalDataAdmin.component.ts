import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ModalProfileComponent } from '../../modal-profile/modal-profile.component';
import { Amministratore } from '../../../../models/amministratore.model';
import { CookieAmministratore } from '../../../../../services/cookie/utente/amministratore.service';
import { AmministrtatoreDataService } from '../../../services/AmministratoreData.service';
import { PopUp } from '../../../../models/pop-up';
import { PopUpComponent } from '../../../../../base/pop-up/pop-upcomponent';

@Component({
  selector: 'personalDataAdmin-component',
  templateUrl: './personalDataAdmin.component.html',
  imports: [CommonModule, ModalProfileComponent, PopUpComponent, ReactiveFormsModule],
  styleUrls: ['./personalDataAdmin.component.css'],
})
export class PersonalDataAdminComponent implements OnInit {

  amministratore: Amministratore | null = null;
  showPersonalData = false;
  adminForm!: FormGroup;
  passwordTouched = false;
  popUp!: PopUp;
  showPopUp: boolean = false;

  constructor(private amministratoreService: AmministrtatoreDataService, private fb: FormBuilder, private cookieamministratore: CookieAmministratore) {}

  ngOnInit(): void {
    this.adminForm = this.fb.group({
      nome: ['', [Validators.required, Validators.minLength(2)]],
      cognome: ['', [Validators.required, Validators.minLength(2)]],
      codiceFiscale: ['', [Validators.required, Validators.minLength(16)]],
      dataNascita: ['', [Validators.required]],
      genere: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', []],
      partitaIva: ['', [Validators.required, Validators.minLength(11)]],
      codAmministratore: [''],
      codAmministratoreInsert: [''],
      codAgenzia: [''],
      indirizzo: this.fb.group({
        via: ['', [Validators.required]],
        citta: ['', [Validators.required]],
        civico: ['', [Validators.required, Validators.min(0)]]
      })
    });

    this.amministratoreService.getAmministratoreData().subscribe({
      next: (data: Amministratore) => {
        if (data) {
          this.amministratore = data;
          this.adminForm.patchValue({
            nome: data.nome,
            cognome: data.cognome,
            codiceFiscale: data.codiceFiscale,
            dataNascita: data.dataNascita,
            genere: data.genere,
            email: data.email,
            password: '',
            partitaIva: data.partitaIva,
            codAmministratore: data.codAmministratore,
            codAmministratoreInsert: data.codAmministratoreInsert,
            codAgenzia: data.codAgenzia,
            indirizzo: {
              via: data.indirizzo?.via,
              citta: data.indirizzo?.citta,
              civico: data.indirizzo?.civico
            }
          });

          // Disabilita i campi non modificabili
          this.adminForm.get('nome')?.disable();
          this.adminForm.get('cognome')?.disable();
          this.adminForm.get('codiceFiscale')?.disable();
          this.adminForm.get('dataNascita')?.disable();
          this.adminForm.get('genere')?.disable();

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
      this.adminForm.get('password')?.setValue('');
    }
  }

  openPersonalData(): void {
    this.showPersonalData = true;
  }

  closePersonalData(): void {
    this.showPersonalData = false;
  }

  salvaDati(): void {
    if (this.adminForm.valid) {

      const idSessione = this.cookieamministratore.getCookie();
      const formValues = this.adminForm.getRawValue();

      if (!formValues.password || formValues.password.trim() === '') {
        formValues.password = null;
      }

      if (formValues.codAmministratoreInsert === 0) {
        formValues.codAmministratoreInsert = null;
      }
      
      const adminAggiornato = { 
        ...this.amministratore, 
        ...formValues,
        idSessione: idSessione
      };

      this.amministratoreService.updateAmministratoreData(adminAggiornato).subscribe({
        next: () => {
          this.popUp = new PopUp({ titolo: 'Successo!', descrizione: 'I tuoi dati sono stati modificati.', isSuccess: true });
          this.showPopUp = true;
          this.closePersonalData();
        },
        error: (err: any) => {
          this.popUp = new PopUp({ titolo: 'Errore!', descrizione: 'I dati inseriti non sono corretti, riprova.', isSuccess: false });
          this.showPopUp = true;
        },
      });
    } else {
      console.warn('Il form non è valido, controlla i campi!');
    }
  }
}

