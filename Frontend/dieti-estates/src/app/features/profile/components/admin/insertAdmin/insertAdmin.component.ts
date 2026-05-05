
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModalProfileComponent } from '../../modal-profile/modal-profile.component';
import { PopUpComponent } from '../../../../../base/pop-up/pop-upcomponent';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Amministratore } from '../../../../models/amministratore.model';
import { PopUp } from '../../../../models/pop-up';
import { AmministrtatoreDataService } from '../../../services/AmministratoreData.service';
import { CookieAmministratore } from '../../../../../services/cookie/utente/amministratore.service';

@Component({
  selector: 'insertAdmin-component',
  templateUrl: './insertAdmin.component.html',
  imports: [CommonModule, ModalProfileComponent, PopUpComponent, ReactiveFormsModule],
  styleUrls: ['./insertAdmin.component.css'],
})

export class InsertAdminComponent implements OnInit {
 
  amministratore: Amministratore | null = null;
  showInsertAdmin = false;
  amministratoreForm!: FormGroup;
  popUpData: PopUp = new PopUp({
    titolo: "Riprova.",
    descrizione: "Errore nell'inserimento dell'agente.",
    isSuccess: false
  });
    
  constructor(private fb: FormBuilder, private amministratoreDataService: AmministrtatoreDataService, private cookieAmministratore: CookieAmministratore ) {}

  ngOnInit(): void {

    this.amministratoreForm = this.fb.group({
      nome: ['', [Validators.required, Validators.minLength(2)]],
      cognome: ['', [Validators.required,  Validators.minLength(2)]],
      dataNascita: ['', [Validators.required]], 
      indirizzo: this.fb.group({
        citta: ['', [Validators.required]], 
        via: ['', [Validators.required]],   
        num_civico: ['', [Validators.required, Validators.min(1)]],
      }),
      codiceFiscale: ['', [Validators.required, Validators.minLength(16)]],
      partitaIva: ['', [Validators.required, Validators.minLength(13)]],
      genere: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password_c: ['', [Validators.required, Validators.minLength(6)]],
    }); 
    
  }

  inserisciAmministratore(): void {

    if (this.amministratoreForm.valid) {

      const idSessione = this.cookieAmministratore.getCookie().toString();
      const formValue = this.amministratoreForm.value;

      const amministratore : Amministratore = {
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
        partitaIva: formValue.partitaIva,
        genere: formValue.genere,
        email: formValue.email,
        password: formValue.password_c,
        codAmministratoreInsert: 0,
        codAgenzia: 0,
        idSessione: idSessione
      };

      console.log(' JSON che sarà inviato al backend:', JSON.stringify(amministratore, null, 2));

      this.amministratoreDataService.insertAmministratoreData(amministratore).subscribe({
        next: () => {
          console.log('Agente inserito con successo!', amministratore);
          this.amministratoreForm.reset();
          this.closeInsertAdmin();
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

  openInsertAdmin(): void {
    this.showInsertAdmin = true;
  }

  closeInsertAdmin(): void {
    this.showInsertAdmin = false;
  }

}

