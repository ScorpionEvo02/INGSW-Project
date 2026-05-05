
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModalProfileComponent } from '../../modal-profile/modal-profile.component';
import { Agente } from '../../../../models/agente.model';
import { PopUp } from '../../../../models/pop-up';
import { PopUpComponent } from '../../../../../base/pop-up/pop-upcomponent';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AgenteDataService } from '../../../services/agenteData.service';
import { CookieGestore } from '../../../../../services/cookie/utente/gestore.service';

@Component({
  selector: 'insertAgent-component',
  templateUrl: './insertAgent.component.html',
  standalone: true,
  imports: [ CommonModule, ModalProfileComponent, PopUpComponent, ReactiveFormsModule ],
  styleUrls: ['./insertAgent.component.css'],
})

export class InsertAgentComponent implements OnInit{

  agente: Agente | null = null;
  showInsertAgente = false;
  agenteForm!: FormGroup;
  popUpData: PopUp = new PopUp({
    titolo: "Riprova.",
    descrizione: "Errore nell'inserimento dell'agente.",
    isSuccess: false
  });
   
  constructor(private fb: FormBuilder, private agenteDataService: AgenteDataService, private cookieGestore: CookieGestore ) {}

  ngOnInit(): void {

    this.agenteForm = this.fb.group({
      nome: ['', [Validators.required, Validators.minLength(2)]],
      cognome: ['', [Validators.required,  Validators.minLength(2)]],
      dataNascita: ['', [Validators.required]], 
      indirizzo: this.fb.group({
        citta: ['', [Validators.required]], 
        via: ['', [Validators.required]],   
        num_civico: ['', [Validators.required, Validators.min(1)]],
      }),
      codiceFiscale: ['', [Validators.required, Validators.minLength(16)]],
      genere: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
    }); 
    
  }

  inserisciAgente(): void {

    if (this.agenteForm.valid) {

      const idSessione = this.cookieGestore.getCookie().toString();
      const formValue = this.agenteForm.value;

      const agente : Agente = {
        nome: formValue.nome,
        cognome: formValue.cognome,
        dataNascita: formValue.dataNascita,
        indirizzo: {
          citta: formValue.indirizzo.citta,
          via: formValue.indirizzo.via,
          civico: formValue.indirizzo.num_civico,
          cap: 0,
          comune: ''
        }, 
        codiceFiscale: formValue.codiceFiscale,
        genere: formValue.genere,
        email: formValue.email,
        password: formValue.password,
        codGestore: 0,
        codAgenzia: 0,
        idSessione: idSessione
      };

      console.log("Agente inviato al backend:", agente);

      this.agenteDataService.insertAgenteData(agente).subscribe({
        next: () => {
          console.log('Agente inserito con successo!', agente);
          this.agenteForm.reset();
          this.closeInsertAgente();
          this.popUpData = new PopUp({ titolo: "Successo!", descrizione: "Inserimento riuscito.", isSuccess: true });
        },
        error: (err: any) => {
          this.popUpData = new PopUp({ titolo: "Errore!", descrizione: "Errore nell'inserimento.", isSuccess: false });
        }
      });
    } else {
      this.popUpData = new PopUp({ titolo: "Errore!", descrizione: "Form non valido.", isSuccess: false });
      console.warn('Il form non è valido, controlla i campi!');
    }
  }

  openInsertAgente(): void {
    this.showInsertAgente = true;
  }

  closeInsertAgente(): void {
    this.showInsertAgente = false;
  }
  
}