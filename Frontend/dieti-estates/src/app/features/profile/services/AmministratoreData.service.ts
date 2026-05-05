import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { CookieAmministratore } from '../../../services/cookie/utente/amministratore.service';
import { Amministratore } from '../../models/amministratore.model';

@Injectable({
  providedIn: 'root'
})
export class AmministrtatoreDataService {
  private getAmministratoreUrl = 'http://localhost:8080/amministratore/receive';
  private updateAmministratoreUrl = 'http://localhost:8080/amministratore/update';
  private readonly httpClient = inject(HttpClient);
  
  constructor(private cookieamministratore: CookieAmministratore) {}

  getAmministratoreData(): Observable<Amministratore> {
    const headers = new HttpHeaders({ 
      'uid_amministratore': this.cookieamministratore.getCookie().toString(),
      'Content-Type': 'application/json' 
    });
    console.log("UID amministratore inviato:", this.cookieamministratore.getCookie());
   
    return this.httpClient.get<Amministratore>(this.getAmministratoreUrl, { headers })
    .pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(() => new Error(error.error || 'Errore sconosciuto'));
      })
    );
  }
  updateAmministratoreData(amministratore: Amministratore): Observable<string>{
    const headers = new HttpHeaders({ 
      'uid_amministratore': this.cookieamministratore.getCookie().toString(),
      'Content-Type': 'application/json' 
    });

    console.log("UID amministratore inviato:", this.cookieamministratore.getCookie());

    return this.httpClient.put(this.updateAmministratoreUrl, amministratore, {
      headers,
      responseType: 'text' 
    }).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(() => new Error(error.error || 'Errore sconosciuto'));
      })
    );
  }  

  insertAmministratoreData(amministratore: Amministratore): Observable<string> {
    const headers = new HttpHeaders({ 
      'uid_amministratore': this.cookieamministratore.getCookie().toString(),
      'Content-Type': 'application/json' 
    });
  
    return this.httpClient.post<string>('http://localhost:8080/amministratore/insert', amministratore, { 
      headers, 
      responseType: 'text' as 'json'
    }).pipe(
      catchError((error: HttpErrorResponse) => {
        console.error('Errore durante l\'inserimento:', error);
        return throwError(() => new Error(error.error || 'Errore sconosciuto'));
      })
    );
  }
}

  


