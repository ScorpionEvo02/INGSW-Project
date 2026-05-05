import { Immobile } from '../../models/immobile.model';
import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { CookieCliente } from '../../../services/cookie/utente/cliente.service';

@Injectable({
  providedIn: 'root'
})

export class CronologiaService {
  private insertImmobileCronologiaUrl = 'http://localhost:8080/cronologia/';
  private getCronologiaClienteUrl = 'http://localhost:8080/cronologia/';
  private readonly httpClient = inject(HttpClient);

  constructor(private cookieCliente: CookieCliente) {}

  insertImmobileCronologia(codImmobile: number): Observable<any> {
    const params = new HttpParams().set('codImmobile', codImmobile);
    const headers = new HttpHeaders({ 
        'uid_cliente': this.cookieCliente.getCookie().toString(),
        'Content-Type': 'application/json' 
    });

    return this.httpClient.put(this.insertImmobileCronologiaUrl, {}, {
      params, 
      headers,
      responseType: 'text' 
    }).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(() => new Error(error.error || 'Errore sconosciuto'));
      })
    );
  }

  getCronologiaCliente(): Observable<Immobile[]> 
  {
    const headers = new HttpHeaders({ 
      'uid_cliente': this.cookieCliente.getCookie().toString(),
      'Content-Type': 'application/json' 
    });
    return this.httpClient.get<Immobile[]>(this.getCronologiaClienteUrl, { headers })
    .pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(() => new Error(error.error || 'Errore sconosciuto'));
      })
    );
  }
}
