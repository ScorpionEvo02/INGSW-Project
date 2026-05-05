import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'italy-component',
  templateUrl: './italy.component.html',
  styleUrls: ['./italy.component.css'],
})
export class ItalyComponent {

  constructor( private router: Router ) {}

  onRegionClick(event: MouseEvent) {
    const target = event.target as SVGElement;
    if (target && target.id) {
      console.log('Regione selezionata:', target.id);
    }
  }

  onRegionSelected(lat: number, lng: number):void 
  {
    const coordinate = ({ latitudine: lat, longitudine: lng });    
    this.router.navigate(['/searchpage'], { state: { searchType: "Vendita", coordinate: coordinate } });
  }
}

