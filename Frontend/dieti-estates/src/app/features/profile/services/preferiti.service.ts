import { Immobile } from '../../models/immobile.model';
import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { CookieCliente } from '../../../services/cookie/utente/cliente.service';

@Injectable({
  providedIn: 'root'
})

export class PreferitiService {
  private getPreferitiClienteUrl = 'http://localhost:8080/preferito/'; 
  private insertImmobilePreferitoUrl = 'http://localhost:8080/preferito/'; 
  private readonly httpClient = inject(HttpClient);

  constructor(private cookieCliente: CookieCliente) {}

  getPreferitiCliente(): Observable<Immobile[]> 
  {
    const headers = new HttpHeaders({ 
      'uid_cliente': this.cookieCliente.getCookie().toString(),
      'Content-Type': 'application/json' 
    });
    return this.httpClient.get<Immobile[]>(this.getPreferitiClienteUrl, { headers })
      .pipe(
        catchError((error: HttpErrorResponse) => {
        return throwError(() => new Error(error.error || 'Errore sconosciuto'));
      })
    );
  }

  insertPreferitoUtente(codImmobile: number): Observable<string> 
  {
    const headers = new HttpHeaders({
      'uid_cliente': this.cookieCliente.getCookie().toString()
    });
    
    const params = new HttpParams().set('codImmobile', codImmobile);
    console.log("Cod Immobile preferito:", codImmobile, "Uid_cliente:", this.cookieCliente.getCookie().toString());
    
    return this.httpClient.put<string>(this.insertImmobilePreferitoUrl, null, { headers, params });
  }
}
