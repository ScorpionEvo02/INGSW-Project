import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { Cliente } from '../../models/cliente.model';
import { CookieCliente } from '../../../services/cookie/utente/cliente.service';

@Injectable({
  providedIn: 'root'
})
export class ClienteDataService {
  
  private getClienteUrl = 'http://localhost:8080/cliente/';
  private updateClienteUrl = 'http://localhost:8080/cliente/update';
  private readonly httpClient = inject(HttpClient);
  
  constructor(private cookieCliente: CookieCliente) {}

  getClienteData(): Observable<Cliente> {
    const headers = new HttpHeaders({ 
      'uid_cliente': this.cookieCliente.getCookie().toString(),
      'Content-Type': 'application/json' 
    });
    return this.httpClient.get<Cliente>(this.getClienteUrl, { headers })
    .pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(() => new Error(error.error || 'Errore sconosciuto'));
      })
    );
  }

  updateClienteData(cliente: Cliente): Observable<string>{
    const headers = new HttpHeaders({ 
      'uid_cliente': this.cookieCliente.getCookie().toString(),
      'Content-Type': 'application/json' 
    });

    return this.httpClient.put(this.updateClienteUrl, cliente, {
      headers,
      responseType: 'text' 
    }).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(() => new Error(error.error || 'Errore sconosciuto'));
      })
    );
  }  
}

  


