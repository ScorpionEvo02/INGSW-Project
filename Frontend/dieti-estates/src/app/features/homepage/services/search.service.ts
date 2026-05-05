import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { Immobile } from '../../models/immobile.model';
import { environment } from '../../../../environments';

@Injectable({
  providedIn: 'root'
})

export class SearchService {
  private readonly geocodeApiKey = environment.googleMapsApiKey;
  private readonly httpClient = inject(HttpClient);

  geocodeAddress(address: string): Observable<{ lat: number; lng: number }> 
  {
    const encodedAddress = encodeURIComponent(address);
    const url = `https://maps.googleapis.com/maps/api/geocode/json?address=${encodedAddress}&key=${this.geocodeApiKey}`;

    return this.httpClient.get<any>(url).pipe(
      map(response => {
        if (response.status === 'OK' && response.results.length > 0) 
        {
          const location = response.results[0].geometry.location;
          return { lat: location.lat, lng: location.lng };
        } 
        else 
        {
          throw new Error(`Geocoding error: ${response.status}`);
        }
      }),
      catchError(err => 
        {
        return throwError(() => new Error('Errore nella geocodifica:', err));
        })
    );
  }
}
