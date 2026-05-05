import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { Agente } from '../../models/agente.model';
import { CookieGestore } from '../../../services/cookie/utente/gestore.service';
import { Gestore } from '../../models/gestore.model';
import { CookieAmministratore } from '../../../services/cookie/utente/amministratore.service';

@Injectable({
  providedIn: 'root'
})
export class GestoreDataService {
  private getGestoreUrl = 'http://localhost:8080/gestore/receive';
  private updateGestoreUrl = 'http://localhost:8080/gestore/update';
  private insertAgenteUrl= 'http://localhost:8080/agente/';
  private insertGestoreUrl= 'http://localhost:8080/gestore/';
  private readonly httpClient = inject(HttpClient);
  
  constructor(private cookiegestore: CookieGestore, private cookieadmin: CookieAmministratore) {}

  getGestoreData(): Observable<Gestore> {

    const headers = new HttpHeaders({ 
      'uid_gestore': this.cookiegestore.getCookie().toString(),
      'Content-Type': 'application/json' 
    });
    console.log("uid_gestore: ", this.cookiegestore.getCookie());
    return this.httpClient.get<Gestore>(this.getGestoreUrl, { headers })
    .pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(() => new Error(error.error || 'Errore sconosciuto'));
      })
    );
  }

  getAllGestori(): Observable<Gestore[]> {
    const headers = new HttpHeaders({ 
      'uid_amministratore': this.cookieadmin.getCookie().toString(),
      'Content-Type': 'application/json' 
    });
  
    return this.httpClient.get<Gestore[]>('http://localhost:8080/gestore/received', { headers })
      .pipe(
        catchError((error: HttpErrorResponse) => {
          return throwError(() => new Error(error.error || 'Errore sconosciuto'));
        })
      );
  }

  updateGestoreData(gestore: Gestore): Observable<string>{
    const headers = new HttpHeaders({ 
      'uid_gestore': this.cookiegestore.getCookie().toString(),
      'Content-Type': 'application/json' 
    });

    return this.httpClient.put(this.updateGestoreUrl, gestore, {
      headers,
      responseType: 'text' 
    }).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(() => new Error(error.error || 'Errore sconosciuto'));
      })
    );
  }  

  insertAgente(agente: Agente): Observable<string>{
    const headers = new HttpHeaders({ 
      'uid_gestore': this.cookiegestore.getCookie().toString(),
      'Content-Type': 'application/json' 
    });

    return this.httpClient.put(this.insertAgenteUrl, agente, {
      headers,
      responseType: 'text' 
    }).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(() => new Error(error.error || 'Errore sconosciuto'));
      })
    );
  } 

  insertGestore(gestore: Gestore): Observable<string>{
    const headers = new HttpHeaders({ 
      'uid_amministratore': this.cookieadmin.getCookie().toString(),
      'Content-Type': 'application/json' 
    });

    return this.httpClient.put(this.insertGestoreUrl, gestore, {
      headers,
      responseType: 'text' 
    }).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(() => new Error(error.error || 'Errore sconosciuto'));
      })
    );
  } 

  deleteGestore(gestore: Gestore): Observable<string> {
    
    const headers = new HttpHeaders({ 
      'uid_amministratore': this.cookieadmin.getCookie().toString()
    });
  
    const url = `http://localhost:8080/gestore/delete?codGestore=${gestore.codGestore}`;
  
    return this.httpClient.delete(url, {
      headers,
      responseType: 'text'
    }).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(() => new Error(error.error || 'Errore durante l\'eliminazione del gestore'));
      })
    );
  }
  
  
  insertGestoreData(gestore: Gestore): Observable<string>{
    const headers = new HttpHeaders({ 
      'uid_amministratore': this.cookieadmin.getCookie().toString(),
      'Content-Type': 'application/json' 
    });

    return this.httpClient.post<string>('http://localhost:8080/gestore/insert', gestore,  {
      headers,
      responseType: 'text' as 'json'
    }).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(() => new Error(error.error || 'Errore sconosciuto'));
      })
    );
  } 
  
}
