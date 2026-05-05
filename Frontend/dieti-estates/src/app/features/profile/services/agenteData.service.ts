import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { CookieAgente } from '../../../services/cookie/utente/agente.service';
import { Agente } from '../../models/agente.model';
import { CookieGestore } from '../../../services/cookie/utente/gestore.service';

@Injectable({
  providedIn: 'root'
})
export class AgenteDataService {
  private getAgenteUrl = 'http://localhost:8080/agente/receive';
  private updateAgenteUrl = 'http://localhost:8080/agente/update';
  private readonly httpClient = inject(HttpClient);
  
  constructor(private cookieAgente: CookieAgente, private cookieGestore: CookieGestore) {}

  getAgenteData(): Observable<Agente> {
    const headers = new HttpHeaders({ 
      'uid_agente': this.cookieAgente.getCookie().toString(),
      'Content-Type': 'application/json' 
    });
    
    return this.httpClient.get<Agente>(this.getAgenteUrl, { headers })
    .pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(() => new Error(error.error || 'Errore sconosciuto'));
      })
    );
  }

  getAllAgenti(): Observable<Agente[]> {
    const headers = new HttpHeaders({ 
      'uid_gestore': this.cookieGestore.getCookie().toString(),
      'Content-Type': 'application/json' 
    });
  
    return this.httpClient.get<Agente[]>('http://localhost:8080/agente/received', { headers })
      .pipe(
        catchError((error: HttpErrorResponse) => {
          return throwError(() => new Error(error.error || 'Errore sconosciuto'));
        })
      );
  }

  updateAgenteData(agente: Agente): Observable<string>{
    const headers = new HttpHeaders({ 
      'uid_agente': this.cookieAgente.getCookie().toString(),
      'Content-Type': 'application/json' 
    });

    return this.httpClient.put(this.updateAgenteUrl, agente, {
      headers,
      responseType: 'text' 
    }).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(() => new Error(error.error || 'Errore sconosciuto'));
      })
    );
  }  

  deleteAgente(agente: Agente): Observable<string> {
    const headers = new HttpHeaders({ 
      'uid_gestore': this.cookieGestore.getCookie().toString()
    });
  
    const url = `http://localhost:8080/agente/delete?codAgente=${agente.codAgente}`;
  
    return this.httpClient.delete(url, {
      headers,
      responseType: 'text'
    }).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(() => new Error(error.error || 'Errore durante l\'eliminazione del gestore'));
      })
    );
  }

  insertAgenteData(agente: Agente): Observable<string> {
    const headers = new HttpHeaders({ 
      'uid_gestore': this.cookieGestore.getCookie().toString(),
      'Content-Type': 'application/json' 
    });
  
    return this.httpClient.post<string>('http://localhost:8080/agente/insert', agente, { 
      headers, 
      responseType: 'text' as 'json'
    }).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(() => new Error(error.error || 'Errore sconosciuto'));
      })
    );
  }
}

  


