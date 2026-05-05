import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ClienteDataService } from '../../../services/clienteData.service';
import { Cliente } from '../../../../models/cliente.model';
import { ModalProfileComponent } from '../../modal-profile/modal-profile.component';
import { ReactiveFormsModule } from '@angular/forms';
import { CookieCliente } from '../../../../../services/cookie/utente/cliente.service';
import { PopUp } from '../../../../models/pop-up';
import { PopUpComponent } from '../../../../../base/pop-up/pop-upcomponent';

@Component({
  selector: 'personaldata-component',
  templateUrl: './personaldata.component.html',
  styleUrls: ['./personaldata.component.css'],
  standalone: true,
  imports: [CommonModule, ModalProfileComponent,PopUpComponent, ReactiveFormsModule],
})
export class PersonalDataComponent implements OnInit {
  cliente: Cliente | null = null;
  showPersonalData = false;
  clienteForm!: FormGroup;
  passwordTouched = false;
  popUp!: PopUp;
  showPopUp: boolean = false;

  constructor(private clienteService: ClienteDataService, private fb: FormBuilder, private cookieCliente: CookieCliente) {}

  ngOnInit(): void {
    this.clienteForm = this.fb.group({
      nome: ['', [Validators.required, Validators.minLength(2)]],
      cognome: ['', [Validators.required, Validators.minLength(2)]],
      codiceFiscale: ['', [Validators.required, Validators.minLength(16)]],
      dataNascita: ['', [Validators.required]],
      genere: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', []],
      indirizzo: this.fb.group({
        via: ['', [Validators.required]],
        civico: ['', [Validators.required, Validators.min(1), Validators.max(999)]],
        citta: ['', [Validators.required]],
      })
    });

    this.clienteService.getClienteData().subscribe({
      next: (data: Cliente) => {
        if (data) {
          this.cliente = data;
          this.clienteForm.patchValue({
            nome: data.nome,
            cognome: data.cognome,
            codiceFiscale: data.codiceFiscale,
            dataNascita: data.dataNascita,
            genere: data.genere,
            email: data.email,
            password: '',
            indirizzo: {
              via: data.indirizzo?.via,
              civico: data.indirizzo?.civico, 
              citta: data.indirizzo?.citta,
            }
          });
    
          // Disabilita i campi non modificabili
          this.clienteForm.get('nome')?.disable();
          this.clienteForm.get('cognome')?.disable();
          this.clienteForm.get('codiceFiscale')?.disable();
          this.clienteForm.get('dataNascita')?.disable();
          this.clienteForm.get('genere')?.disable();
        }
      },
      error: (err) => {
        console.error('Errore nel recupero dati del cliente.', err);
      },
    });
  }

  onPasswordFocus(): void {
    if (!this.passwordTouched) {
      this.passwordTouched = true;
      this.clienteForm.get('password')?.setValue('');
    }
  }

  openPersonalData(): void {
    this.showPersonalData = true;
  }

  closePersonalData(): void {
    this.showPersonalData = false;
  }

  salvaDati(): void {
    if (this.clienteForm.valid) {

      const idSessione = this.cookieCliente.getCookie();
      const formValues = this.clienteForm.getRawValue();

      if (!formValues.password || formValues.password.trim() === '') {
        formValues.password = null;
      }

      const clienteAggiornato = { 
        ...this.cliente, 
        ...formValues, 
        idSessione: idSessione
      };
      
      this.clienteService.updateClienteData(clienteAggiornato).subscribe({
        next: () => {
          this.popUp = new PopUp({ titolo: 'Successo!', descrizione: 'I tuoi dati sono stati modificati.', isSuccess: true });
          this.showPopUp = true;
          this.closePersonalData();
        },
        error: (err) => {
          this.popUp = new PopUp({ titolo: 'Errore!', descrizione: 'I dati inseriti non sono corretti, riprova.', isSuccess: false });
          this.showPopUp = true;
        },
      });
    }
    else {
      console.warn('Il form non è valido, controlla i campi!');
    }
  }
}


