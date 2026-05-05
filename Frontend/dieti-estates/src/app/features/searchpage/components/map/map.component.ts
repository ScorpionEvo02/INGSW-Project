import { Component, Input, OnChanges, SimpleChanges, ViewChild, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Immobile } from '../../../models/immobile.model';
import { Coordinata } from '../../../models/coordinata.model';
import { Router } from '@angular/router';
import { GoogleMapsModule, MapInfoWindow, MapAdvancedMarker } from '@angular/google-maps';

@Component({
  selector: 'map-component',
  templateUrl: './map.component.html',
  standalone: true,
  styleUrls: ['./map.component.css'],
  imports: [GoogleMapsModule, CommonModule]
})

export class MapComponent implements OnChanges {

  selectedImmobile?: Immobile ;
  @Input() immobili: Immobile[] = [];
  @Input() center: Coordinata = { latitudine: 40.8517746, longitudine: 14.2681244 };
  mapCenter: google.maps.LatLngLiteral = { lat: this.center.latitudine, lng: this.center.longitudine };
  zoom: number = 12;
  selectedCoordinata!: Coordinata;

  constructor(private router: Router) {}

  mapOptions: google.maps.MapOptions = {
    mapId: 'a82b8460db7ef13c',
    disableDefaultUI: true,
    zoomControl: true,
  };

  @ViewChild(MapInfoWindow) infoWindow!: MapInfoWindow;

  ngOnChanges(changes: SimpleChanges): void 
  {
    if (changes['center'] && this.center) {
      this.mapCenter = { lat: this.center.latitudine, lng: this.center.longitudine };
    }
  }

  onMapClick(event: google.maps.MapMouseEvent): void {
    if (event.latLng) {
      const lat = event.latLng.lat();
      const lng = event.latLng.lng();
      this.selectedCoordinata = { latitudine: lat, longitudine: lng };
      console.log('Coordinate cliccate:', this.selectedCoordinata);
      this.emitFilters();
    } else {
      console.log('Clic non valido.');
    }
  }  

  @Output() onCoordChanged = new EventEmitter<{ selectedCoordinata: Coordinata }>();

  private emitFilters(): void {
    this.onCoordChanged.emit({
      selectedCoordinata: this.selectedCoordinata
    });
  }

  openInfoWindow(marker: MapAdvancedMarker, immobile: Immobile): void 
  {
    this.selectedImmobile = immobile;
    this.infoWindow.open(marker);
  }

  goImmobilePage(codImmobile: number | undefined | null) {
    if (!codImmobile) { 
      console.log("Nessun immobile selezionato.");
      return;
    }
    this.router.navigate(['/immobile'], { state: { immobile: this.selectedImmobile } });
  }
}
