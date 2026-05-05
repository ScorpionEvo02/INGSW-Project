import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Cliente } from '../../models/cliente.model';

@Injectable({
  providedIn: 'root'
})

export class RegisterService {
  private registerClienteUrl = 'http://localhost:8080/cliente/'; 
  private readonly httpClient = inject(HttpClient);

  registerNewCliente(cliente: Cliente): Observable<any> {
    return this.httpClient.put(this.registerClienteUrl, cliente, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
      responseType: 'text' 
    }).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(() => new Error(error.error || 'Errore sconosciuto'));
      })
    );
  }
}
