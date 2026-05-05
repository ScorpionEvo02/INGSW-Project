import { Component, Input, OnInit, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Coordinata } from '../../../models/coordinata.model';
import { MeteoService, WeatherData } from '../../services/meteo.service';

@Component({
  selector: 'calendar-component',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css'],
  imports: [ CommonModule, FormsModule ]
})
export class CalendarComponent implements OnInit {
  @Input() coordinate!: Coordinata;
  @Output() calendarEvent = new EventEmitter<{ timestamp: string }>();
  completed: boolean = true;

  selectableDays: Date[] = [];
  selectedDate: Date | null = null;
  selectedTime: string = '';
  weatherData: { [key: string]: any } = {};
  weatherIcons: { [key: string]: string } = {
    '0': '☀️',
    '1': '🌤️', '2': '🌤️', '3': '🌥️',
    '45': '🌫️', '48': '🌫️',
    '51': '🌦️', '53': '🌦️', '55': '🌦️',
    '56': '🌧️', '57': '🌧️',
    '61': '🌧️', '63': '🌧️', '65': '🌧️',
    '66': '❄️', '67': '❄️',
    '71': '❄️', '73': '❄️', '75': '❄️',
    '77': '❄️',
    '80': '🌦️', '81': '🌦️', '82': '🌧️',
    '85': '❄️', '86': '❄️',
    '95': '⛈️',
    '96': '⛈️', '99': '⛈️'
  };

  constructor(private meteoService: MeteoService) {}

  ngOnInit(): void 
  {
    this.getMeteo();
    this.generateSelectableDays();
  }

  generateSelectableDays() 
  {
    const today = new Date();
    const tomorrow = new Date(today);
    tomorrow.setDate(today.getDate() + 1); 

    for (let i = 0; i < 15; i++) 
    {
      const date = new Date(tomorrow);
      date.setDate(tomorrow.getDate() + i);
      this.selectableDays.push(date);
    }
  }

  getMeteo() 
  {
    const today = new Date();
    const start_date = today.toISOString().split('T')[0]; 
    const end_date = new Date(today);
    end_date.setDate(today.getDate() + 14); 
    const formatted_end_date = end_date.toISOString().split('T')[0]; 

    this.meteoService.getWeather(this.coordinate.latitudine, this.coordinate.longitudine, start_date, formatted_end_date).subscribe(data => {
      const daily = data.daily; 
      
      this.selectableDays.forEach((day, index) => {
        const dateKey = day.toISOString().split('T')[0]; 
        const dayName = day.toLocaleDateString('it-IT', { weekday: 'long' }); 
        
        if (daily) 
        {
          this.weatherData[dateKey] = {
            weatherCode: daily.weather_code[index], 
            dayName: dayName 
          };
        }
      });
    });
  }

  isToday(date: Date): boolean 
  {
    const today = new Date();
    return date.toDateString() === today.toDateString();
  }

  selectDate(date: Date) 
  {
    this.selectedDate = date;
  }

  submit() 
  {
    if (this.selectedDate && this.selectedTime) 
    {
      const timestamp = new Date(`${this.selectedDate.toISOString().split('T')[0]}T${this.selectedTime}`).toISOString();
      this.calendarEvent.emit({ timestamp });
      this.completed = true;
      return;
    }
    this.completed = false;
  }
}
