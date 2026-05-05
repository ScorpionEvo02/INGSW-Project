import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { CookieCliente } from './cookie/utente/cliente.service';
import { CookieAgente } from './cookie/utente/agente.service';
import { OffertaImmobile } from '../features/models/offertaimmobile.model';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})

export class OffertaService {
  private insertOffertaClienteUrl = 'http://localhost:8080/offerta/'; 
  private getOfferteClienteUrl = 'http://localhost:8080/offerta/cliente';
  private getOfferteAgenteUrl = 'http://localhost:8080/offerta/agente';
  private agenteResponseUrl = 'http://localhost:8080/offerta';
  private readonly httpClient = inject(HttpClient);

  constructor(private cookieCliente: CookieCliente, private cookieAgente: CookieAgente) {}
  
  getOffersCliente(): Observable<OffertaImmobile[]> {
    const headers = new HttpHeaders({ 
      'uid_cliente': this.cookieCliente.getCookie().toString(),
      'Content-Type': 'application/json' 
    });

    return this.httpClient.get<OffertaImmobile[]>(this.getOfferteClienteUrl, { headers })
    .pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(() => new Error(error.error || 'Errore sconosciuto'));
      })
    );
  }

  getOffersAgente(): Observable<OffertaImmobile[]> {
    const headers = new HttpHeaders({ 
      'uid_agente': this.cookieAgente.getCookie().toString(),
      'Content-Type': 'application/json' 
    });

    return this.httpClient.get<OffertaImmobile[]>(this.getOfferteAgenteUrl, { headers })
    .pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(() => new Error(error.error || 'Errore sconosciuto'));
      })
    );
  }

  insertOfferta(codImmobile: number, offer: number): Observable<any> 
  {
    const headers = new HttpHeaders({
        'uid_cliente': this.cookieCliente.getCookie().toString(),
        'Content-Type': 'application/json' 
    });
    const params = new HttpParams()
      .set('codImmobile', codImmobile)
      .set('offerta', offer);

    return this.httpClient.put(this.insertOffertaClienteUrl, {}, { headers, params, responseType: 'text' });
  }

  acceptOffer(codOfferta: number): Observable<string> {
    const headers = new HttpHeaders({ 
      'uid_agente': this.cookieAgente.getCookie().toString(),
      'Content-Type': 'application/json' 
    });
    const params = new HttpParams().set('codOfferta', codOfferta.toString());

    return this.httpClient.patch<string>(`${this.agenteResponseUrl}/accetta`, null, { headers, params });
  }

  rejectOffer(codOfferta: number): Observable<string> {
    const headers = new HttpHeaders({ 
      'uid_agente': this.cookieAgente.getCookie().toString(),
      'Content-Type': 'application/json' 
    });
    const params = new HttpParams().set('codOfferta', codOfferta.toString());

    return this.httpClient.patch<string>(`${this.agenteResponseUrl}/rifiuta`, null, { headers, params });
  }
}