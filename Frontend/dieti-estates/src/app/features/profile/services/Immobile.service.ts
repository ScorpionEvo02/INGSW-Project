import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { CookieAgente } from '../../../services/cookie/utente/agente.service';
import { Immobile } from '../../models/immobile.model';

@Injectable({
  providedIn: 'root'
})
export class ImmobileService {
  private patchImmobileUrl = 'http://localhost:8080/immobile/';
  private getImmobileAgenteUrl  = 'http://localhost:8080/immobile/agente';
  private apiKey = '8a46acbf7cec4524b9a26b26df597ccc'; 
  private placesUrl = 'https://api.geoapify.com/v2/places';
  private readonly httpClient = inject(HttpClient);
  
  constructor(private cookieAgente: CookieAgente) {}

  updateImmobileData(immobile: Immobile): Observable<string> {
    const headers = new HttpHeaders({
      'uid_agente': this.cookieAgente.getCookie().toString(),
      'Content-Type': 'application/json'
    });

    return this.httpClient.patch(this.patchImmobileUrl, immobile, { 
      headers,
      responseType: 'text'
    }).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(() => new Error(error.error || 'Errore sconosciuto durante l\'aggiornamento'));
      })
    );
  }
  
  aggiungiImmobile(immobile: Immobile): Observable<string> {
    const headers = new HttpHeaders({ 
      'uid_agente': this.cookieAgente.getCookie().toString(),
      'Content-Type': 'application/json' 
    });
  
    return this.httpClient.post<string>('http://localhost:8080/immobile/receive', immobile, { 
      headers, 
      responseType: 'text' as 'json'
    }).pipe(
      catchError((error: HttpErrorResponse) => {
        console.error('Errore durante l\'inserimento:', error);
        return throwError(() => new Error(error.error || 'Errore sconosciuto'));
      })
    );
  }
  

  getNearPlaces(lat: number, lon: number): Observable<any> 
  {
    const categories = 'education.school,education.university,leisure.park'; 
    const radius = 1000; 
    
    const url = `${this.placesUrl}?categories=${categories}&filter=circle:${lon},${lat},${radius}&bias=proximity:${lon},${lat}&limit=5&apiKey=${this.apiKey}`;
    
    return this.httpClient.get<any>(url);
  }

  getImmobileByAgente(): Observable<Immobile[]>
  {
    const headers = new HttpHeaders({
      'uid_agente': this.cookieAgente.getCookie().toString(),
      'Content-Type': 'application/json' 
    });
    return this.httpClient.get<Immobile[]>(this.getImmobileAgenteUrl, { headers });
  }
}

  


