import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; 
import { Cliente } from '../../../models/cliente.model';
import { Indirizzo } from '../../../models/indirizzo.model';
import { RegisterService } from '../../services/register.service';
import { CheckCookie } from '../../../../services/cookie/utente/check-cookie.service';
import { Router } from '@angular/router';
import { PopUp } from '../../../models/pop-up';
import { PopUpComponent } from '../../../../base/pop-up/pop-upcomponent';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  imports: [ FormsModule, PopUpComponent ] 
})

export class RegisterComponent implements OnInit{
  nome!: string;
  cognome!: string;
  dataNascita!: string;
  citta!: string;
  via!: string;
  civico!: number;
  codiceFiscale!: string;
  genere!: string;
  email!: string;
  password!: string;

  invalidFields: string[] = [];
  clicked: boolean = false;
  popUp!: PopUp;
  showPopUp: boolean = false;

  constructor(private registerService: RegisterService,  private router: Router, 
              private checkCookie: CheckCookie) {}
  
  ngOnInit(): void 
  {
    if(this.checkCookie.checkCookie())
    {
      this.router.navigate(['/'], { });
    }
  }
      
  onSubmit() {
    this.clicked = true;
    this.invalidFields = [];

    if (!this.nome || /\d/.test(this.nome)) this.invalidFields.push('nome');
    if (!this.cognome || /\d/.test(this.cognome)) this.invalidFields.push('cognome');
    if (!this.dataNascita) this.invalidFields.push('dataNascita');
    if (!this.citta || /\d/.test(this.citta)) this.invalidFields.push('citta');
    if (!this.via) this.invalidFields.push('via');
    if (!this.civico) this.invalidFields.push('numeroCivico');
    if (!this.codiceFiscale || this.codiceFiscale.length != 16) this.invalidFields.push('codiceFiscale');
    if (!this.genere) this.invalidFields.push('genere');
    if (!this.email || !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(this.email)) this.invalidFields.push('email');
    if (!this.password) this.invalidFields.push('password');

    this.highlightFields();

    if (this.invalidFields.length > 0) 
    {
      this.popUp = new PopUp({ titolo: 'Riprova!', descrizione: 'Compila correttamente tutti i campi.', isSuccess: false });
    } 
    else 
    {
      const indirizzo: Indirizzo = {
        citta: this.citta,
        via: this.via,
        civico: this.civico,
        cap: 0,
        comune: ""
      };
  
      const cliente = new Cliente({
        nome: this.nome,
        cognome: this.cognome,
        dataNascita: this.dataNascita,
        codiceFiscale: this.codiceFiscale,
        email: this.email,
        password: this.password,
        indirizzo: indirizzo,
        genere: this.genere,
      });

      this.registerService.registerNewCliente(cliente).subscribe({
        next: (response) => {
          console.log("Response:",response);
          this.popUp = new PopUp({ titolo: 'Approvato!', descrizione: 'Registrazione riuscita.', isSuccess: true });
          this.router.navigate(['/homepage'], { });
        },
        error: (error) => {
          console.error('Errore durante la registrazione:', error);
          this.popUp = new PopUp({ titolo: 'Riprova!', descrizione: 'Registrata non riuscita. Email o CF già presenti.', isSuccess: false });
        }
      });

    }
    this.showPopUp = true;
    this.clicked = false;
  }

  highlightFields() 
  {
    const inputs = document.querySelectorAll('input');
    inputs.forEach((input) => {
      if (this.invalidFields.includes(input.name)) 
      {
        input.classList.add('invalid');
        input.classList.remove('valid');
      } 
      else 
      {
        input.classList.remove('invalid');
        input.classList.add('valid');
      }
    });
  }
}


