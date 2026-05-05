import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { HttpErrorResponse } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';
import { ImmagineImmobile } from '../../models/immagineimmobile.model';
import { CookieAgente } from '../../../services/cookie/utente/agente.service';

@Injectable({
  providedIn: 'root'
})

export class ImmagineImmobileService {
  private getImmaginiImmobileUrl = 'http://localhost:8080/immagine_immobile/'; 
  private readonly httpClient = inject(HttpClient);

  constructor(private cookieAgente: CookieAgente) {}

  getImmaginiImmobileById(id: number): Observable<ImmagineImmobile[]> 
  {
    const getImmaginiImmobileByIdUrl = `${this.getImmaginiImmobileUrl}${id}`;
    return this.httpClient.get<ImmagineImmobile[]>(getImmaginiImmobileByIdUrl);
  }

  insertImmagineImmobile(codImmobile: number, immagine: string)
  {
    const headers = new HttpHeaders({ 
      'uid_agente': this.cookieAgente.getCookie().toString(),
      'Content-Type': 'application/json'
    });

    return this.httpClient.put(
      `${this.getImmaginiImmobileUrl}${codImmobile}`, 
      immagine, // questo è il body vero e proprio
      {
        headers,
        responseType: 'text'
      }
    ).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(() => new Error(error.error || 'Errore sconosciuto'));
      })
    );
  }

}
