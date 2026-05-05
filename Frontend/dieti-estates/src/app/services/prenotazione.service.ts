import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CookieCliente } from './cookie/utente/cliente.service';

@Injectable({
  providedIn: 'root'
})

export class PrenotazioneService {
  private insertPrenotazioneCliente = 'http://localhost:8080/prenotazione/'; 
  private readonly httpClient = inject(HttpClient);

  constructor(private cookieCliente: CookieCliente) {}

  insertPrenotazione(codImmobile: number, dataPrenotazione: string, codAgente: number): Observable<any> {
    const headers = new HttpHeaders({
        'uid_cliente': this.cookieCliente.getCookie().toString(),
        'Content-Type': 'application/json' 
    });
    const params = new HttpParams()
      .set('codImmobile', codImmobile)
      .set('dataPrenotazione', dataPrenotazione)
      .set('codAgente', codAgente);

    return this.httpClient.put(this.insertPrenotazioneCliente, {}, { headers, params, responseType: 'text' });
  }
}