import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { LoginRequest } from '../../models/login-request.model';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private loginUrls = [
    'http://localhost:8080/cliente/login',
    'http://localhost:8080/agente/login',
    'http://localhost:8080/gestore/login',
    'http://localhost:8080/amministratore/login'
  ];

  private readonly httpClient = inject(HttpClient);

  loginWithFallback(loginRequest: LoginRequest): Observable<any> {
    return new Observable(observer => {
      let index = 0;
  
      const tryNext = () => {
        if (index >= this.loginUrls.length) {
          observer.error(new Error('Nessun endpoint ha accettato il login.'));
          return;
        }
  
        const url = this.loginUrls[index++];
        this.httpClient.post(url, loginRequest, {
          responseType: 'text',
          withCredentials: true
        }).subscribe({
          next: response => {
            observer.next(response);
            observer.complete();
          },
          error: (error: HttpErrorResponse) => {
            tryNext();
          }
        });
      };
  
      tryNext();
    });
  }

  private tryLogin(index: number, loginRequest: LoginRequest): Observable<any> {
    if (index >= this.loginUrls.length) {
      return throwError(() => new Error('Nessun endpoint ha accettato il login.'));
    }

    const url = this.loginUrls[index];
    return this.httpClient.post(url, loginRequest, { 
      responseType: 'text',
      withCredentials: true
    }).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 400) {
          console.error('Errore: credenziali non valide.');
        }else if (error.status === 500) {
          console.error('Errore: problema server.');
        } else {
          console.error('Errore generico:', error.message);
        }
        return this.tryLogin(index + 1, loginRequest); 
      })
    );
  }
}
