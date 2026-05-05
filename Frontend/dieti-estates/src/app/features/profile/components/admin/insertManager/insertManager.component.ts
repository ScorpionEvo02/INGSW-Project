
import { Component, OnInit } from '@angular/core';
import { GestoreDataService } from '../../../services/gestoreData.service';
import { PopUpComponent } from '../../../../../base/pop-up/pop-upcomponent';
import { PopUp } from '../../../../models/pop-up';
import { Gestore } from '../../../../models/gestore.model';
import { ModalProfileComponent } from '../../modal-profile/modal-profile.component';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CookieAmministratore } from '../../../../../services/cookie/utente/amministratore.service';

@Component({
  selector: 'insertManager-component',
  templateUrl: './insertManager.component.html',
  imports: [ CommonModule, ModalProfileComponent, PopUpComponent, ReactiveFormsModule ],
  styleUrls: ['./insertManager.component.css'],
})

export class InsertManagerComponent implements OnInit {

  gestore: Gestore | null = null;
  showInsertGestore = false;
  gestoreForm!: FormGroup;
  popUpData: PopUp = new PopUp({
    titolo: "Riprova.",
    descrizione: "Errore nell'inserimento del gestore.",
    isSuccess: false
  });
    
  constructor(private fb: FormBuilder, private gestoreDataService: GestoreDataService, private cookieAmministratore: CookieAmministratore ) {}

  ngOnInit(): void {

    this.gestoreForm = this.fb.group({
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
      password_c: ['', [Validators.required, Validators.minLength(6)]],
    }); 
    
  }

  inserisciGestore(): void {

    if (this.gestoreForm.valid) {

      const idSessione = this.cookieAmministratore.getCookie().toString();
      const formValue = this.gestoreForm.value;

      const gestore : Gestore = {
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
        password: formValue.password_c,
        codAmministratore: 0,
        codAgenzia: 0,
        idSessione: idSessione
      };
      
      console.log(' JSON che sarà inviato al backend:', JSON.stringify(gestore, null, 2));

      this.gestoreDataService.insertGestoreData(gestore).subscribe({
        next: () => {
          console.log('Gestore inserito con successo!', gestore);
          this.gestoreForm.reset();
          this.closeInsertGestore();
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

  openInsertGestore(): void {
    this.showInsertGestore = true;
  }

  closeInsertGestore(): void {
    this.showInsertGestore = false;
  }
}



