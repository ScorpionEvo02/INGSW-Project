import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Agente } from '../../../../models/agente.model';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AgenteDataService } from '../../../services/agenteData.service';
import { CookieAgente } from '../../../../../services/cookie/utente/agente.service';
import { ModalProfileComponent } from '../../modal-profile/modal-profile.component';
import { PopUp } from '../../../../models/pop-up';
import { PopUpComponent } from '../../../../../base/pop-up/pop-upcomponent';

@Component({
  selector: 'personalDataAgent-component',
  templateUrl: './personalDataAgent.component.html',
  imports: [CommonModule,  ModalProfileComponent, PopUpComponent, ReactiveFormsModule],
  styleUrls: ['./personalDataAgent.component.css'],
})
export class PersonalDataAgentComponent implements OnInit {
  
  agente: Agente | null = null;
  showPersonalData = false;
  agenteForm!: FormGroup;
  passwordTouched = false;
  popUp!: PopUp;
  showPopUp: boolean = false;

  constructor(private agenteService: AgenteDataService, private fb: FormBuilder, private cookieAgente: CookieAgente) {}

  ngOnInit(): void {
    this.agenteForm = this.fb.group({
      nome: ['', [Validators.required, Validators.minLength(2)]],
      cognome: ['', [Validators.required, Validators.minLength(2)]],
      codiceFiscale: ['', [Validators.required, Validators.minLength(16)]],
      dataNascita: ['', [Validators.required]],
      genere: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', []],
      codGestore: [''],
      codAgenzia: [''],
      indirizzo: this.fb.group({
        via: ['', [Validators.required]],
        citta: ['', [Validators.required]],
        civico: ['', [Validators.required, Validators.min(0), Validators.max(999)]]
      })
    });
    
    this.agenteService.getAgenteData().subscribe({
      next: (data: Agente) => {
        if (data) {
          this.agente = data;
          this.agenteForm.patchValue({
            nome: data.nome,
            cognome: data.cognome,
            codiceFiscale: data.codiceFiscale,
            dataNascita: data.dataNascita,
            genere: data.genere,
            email: data.email,
            password: '',
            codGestore: data.codGestore,
            codAgenzia: data.codAgenzia,
            indirizzo: {
              via: data.indirizzo?.via,
              citta: data.indirizzo?.citta,
              civico: data.indirizzo?.civico, 
            }
          });
    
          // Disabilita i campi non modificabili
          this.agenteForm.get('nome')?.disable();
          this.agenteForm.get('cognome')?.disable();
          this.agenteForm.get('codiceFiscale')?.disable();
          this.agenteForm.get('dataNascita')?.disable();
          this.agenteForm.get('genere')?.disable();
        }
      },
      error: (err) => {
        console.error('Errore nel recupero dati del agente.', err);
      },
    });
  }

  onPasswordFocus(): void {
    if (!this.passwordTouched) {
      this.passwordTouched = true;
      this.agenteForm.get('password')?.setValue('');
    }
  }

  openPersonalData(): void {
    this.showPersonalData = true;
  }

  closePersonalData(): void {
    this.showPersonalData = false;
  }

  salvaDati(): void {
    
    if (this.agenteForm.valid) {

      const idSessione = this.cookieAgente.getCookie();
      const formValues = this.agenteForm.getRawValue();
      
      if (!formValues.password || formValues.password.trim() === '') {
        formValues.password = null;
      }

      const agenteAggiornato = { 
        ...this.agente, 
        ...formValues, 
        idSessione: idSessione
      };

      this.agenteService.updateAgenteData(agenteAggiornato).subscribe({
        next: () => {
          this.closePersonalData();
          this.popUp = new PopUp({ titolo: 'Successo!', descrizione: 'I tuoi dati sono stati modificati.', isSuccess: true });
          this.showPopUp = true;
        },
        error: (err) => {
          this.popUp = new PopUp({ titolo: 'Errore!', descrizione: 'I dati inseriti non sono corretti, riprova.', isSuccess: false });
          this.showPopUp = true;
          console.error('Errore nell’aggiornamento dei dati.', err);
        },
      });
    }
    else {
      console.warn('Il form non è valido, controlla i campi!');
    }
  }

}
