import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Immobile } from '../../models/immobile.model';
import { Coordinata } from '../../models/coordinata.model';

@Injectable({
  providedIn: 'root'
})

export class ImmobileService {
  private getImmobileByCittaUrl = 'http://localhost:8080/immobile/città/Napoli'; 
  private getImmobileByCoordinateUrl = 'http://localhost:8080/immobile/vendita';
  private readonly httpClient = inject(HttpClient);

  getImmobileByCitta(): Observable<Immobile[]> 
  {
    return this.httpClient.get<Immobile[]>(this.getImmobileByCittaUrl);
  }

  getImmobileByCoordinate(coordinate: Coordinata, tipo: "Vendita" | "Affitto" | "Vendute", raggio: number): Observable<Immobile[]> 
  {
    const params = new HttpParams().set('tipo', tipo).set('raggio', raggio);
    return this.httpClient.post<Immobile[]>(this.getImmobileByCoordinateUrl, coordinate, {params});
  }
}
