import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Agente } from '../../models/agente.model';

@Injectable({
  providedIn: 'root'
})

export class AgentePrenotazioneService {
  private getAgente = 'http://localhost:8080/agente/'; 
  private readonly httpClient = inject(HttpClient);

  getAgenteById(codAgente: number): Observable<Agente> {
    const params = new HttpParams().set('codAgente', codAgente);
    console.log("CodAgente Dietro:", codAgente);
    return this.httpClient.get<Agente>(this.getAgente, {params});
  }
}
