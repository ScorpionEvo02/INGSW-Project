import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface WeatherData {
    daily: {
      weather_code: number[];
    };
}

@Injectable({
  providedIn: 'root'
})
export class MeteoService {
  private apiUrl = 'https://api.open-meteo.com/v1/forecast';

  constructor(private http: HttpClient) {}

  getWeather(lat: number, lon: number, start_date: string, end_date: string): Observable<WeatherData> 
  {
    return this.http.get<WeatherData>(`${this.apiUrl}?latitude=${lat}&longitude=${lon}&daily=weather_code&timezone=Europe%2FLondon&start_date=${start_date}&end_date=${end_date}`);
  }
}
