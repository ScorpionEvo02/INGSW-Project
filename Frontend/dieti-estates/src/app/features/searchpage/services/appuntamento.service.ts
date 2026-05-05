import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { CookieCliente } from '../../../services/cookie/utente/cliente.service';
import { CookieAgente } from '../../../services/cookie/utente/agente.service';
import { Appuntamento } from '../../models/appuntamento.model';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})

export class AppuntamentoService { 
  private getAppuntamentiClienteUrl = 'http://localhost:8080/prenotazione/cliente';
  private getAppuntamentiAgenteUrl = 'http://localhost:8080/prenotazione/agente';
  private agenteResponseUrl = 'http://localhost:8080/prenotazione';
  private readonly httpClient = inject(HttpClient);

  constructor(private cookieCliente: CookieCliente, private cookieAgente: CookieAgente) {}
  
  getAppuntamentiCliente(): Observable<Appuntamento[]> {
    const headers = new HttpHeaders({ 
      'uid_cliente': this.cookieCliente.getCookie().toString(),
      'Content-Type': 'application/json' 
    });

    return this.httpClient.get<Appuntamento[]>(this.getAppuntamentiClienteUrl, { headers })
    .pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(() => new Error(error.error || 'Errore sconosciuto'));
      })
    );
  }

  getAppuntamentiAgente(): Observable<Appuntamento[]> {
    const headers = new HttpHeaders({ 
      'uid_agente': this.cookieAgente.getCookie().toString(),
      'Content-Type': 'application/json' 
    });

    return this.httpClient.get<Appuntamento[]>(this.getAppuntamentiAgenteUrl, { headers })
    .pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(() => new Error(error.error || 'Errore sconosciuto'));
      })
    );
  }


  acceptAppointment(codPrenotazione: number): Observable<string> {
    const headers = new HttpHeaders({ 
      'uid_agente': this.cookieAgente.getCookie().toString(),
      'Content-Type': 'application/json' 
    });
    const params = new HttpParams().set('codPrenotazione', codPrenotazione.toString());

    return this.httpClient.patch<string>(`${this.agenteResponseUrl}/accetta`, null, { headers, params });
  }

  rejectAppointment(codPrenotazione: number): Observable<string> {
    const headers = new HttpHeaders({ 
      'uid_agente': this.cookieAgente.getCookie().toString(),
      'Content-Type': 'application/json' 
    });
    const params = new HttpParams().set('codPrenotazione', codPrenotazione.toString());

    return this.httpClient.patch<string>(`${this.agenteResponseUrl}/rifiuta`, null, { headers, params });
  }
}