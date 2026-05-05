import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from '../../../../base/header/header.component';
import { FilterBarComponent } from '../../components/filter-bar/filter-bar.component';
import { Coordinata, CoordinataUtil } from '../../../models/coordinata.model';
import { ImmobileService } from '../../services/immobile.service';
import { Immobile } from '../../../models/immobile.model';
import { ListImmobiliComponent } from '../../components/list-immobili/list-immobili.component';
import { PopUpComponent } from '../../../../base/pop-up/pop-upcomponent';
import { PopUp } from '../../../models/pop-up';
import { MapComponent } from '../../components/map/map.component';

@Component({
  selector: 'searchpage-component',
  templateUrl: './searchpage.component.html',
  styleUrls: ['./searchpage.component.css'],
  standalone: true,
  imports: [CommonModule, HeaderComponent, FilterBarComponent, 
    MapComponent, ListImmobiliComponent, PopUpComponent ]
})

export class SearchpageComponent implements OnInit  {
  searchType: "Vendita" | "Affitto" | "Vendute" = "Vendita";
  raggio: number = 10;
  coordinate: Coordinata = ({ latitudine: 0, longitudine: 0 });
  immobili: Immobile[] = [];
  filteredImmobili: Immobile[] = [];
  filter?: any;
  popUpData: PopUp = new PopUp({titolo: "Riprova.", descrizione: "Errore nel recupero degli immobili.", isSuccess: false});
  popUp: boolean = false;

  constructor(private immobileService : ImmobileService) { }

  ngOnInit(): void 
  {
    const state = history.state;
    if (state) 
    {
      this.searchType = state.searchType;
      this.coordinate = state.coordinate;
    }
    this.recoverImmobiliByCoordinate();
  }

  recoverImmobiliByCoordinate(): void 
  {
    this.immobileService.getImmobileByCoordinate(this.coordinate, this.searchType, this.raggio)
      .subscribe({
        next: (immobili: Immobile[]) => {
          this.immobili = immobili;
          this.applyFilters(this.filter);
          console.log("immobili:",immobili)
        },
        error: (error) => {
          this.popUp = true;
          console.error("Errore nel recupero degli immobili:", error);
        }
      });
  }  

  onFilterChanged(filters: {
    minPrezzo: number | null,
    maxPrezzo: number | null,
    minCamereLetto: number | null,
    maxCamereLetto: number | null,
    minBagni: number | null,
    maxBagni: number | null,
    classeEnergetica: string | null,
    raggio: number | 10,
    minSuperficie: number | null,
    maxSuperficie: number | null,
  }): void {
    this.filter = filters;
    if(this.raggio !== filters.raggio)
    {
      this.raggio = filters.raggio;
      this.recoverImmobiliByCoordinate();
    }
    this.applyFilters(filters);
  }
  
  applyFilters(filters?: {
    minPrezzo?: number | null,
    maxPrezzo?: number | null,
    minCamereLetto: number | null,
    maxCamereLetto: number | null,
    minBagni: number | null,
    maxBagni: number | null,
    classeEnergetica?: string | null,
    minSuperficie: number | null,
    maxSuperficie: number | null,
  }): void {
    this.filteredImmobili = this.immobili.filter(immobile => {
      const matchesPrezzo =
        (filters?.minPrezzo == null || immobile.costo >= filters.minPrezzo) &&
        (filters?.maxPrezzo == null || immobile.costo <= filters.maxPrezzo);
  
      const matchesCamere =
      (filters?.minCamereLetto == null || immobile.numeroCamereLetto >= filters.minCamereLetto) &&
      (filters?.maxCamereLetto == null || immobile.numeroCamereLetto <= filters.maxCamereLetto);
  
      const matchesBagni =
      (filters?.minBagni == null || immobile.numeroStanze >= filters.minBagni) &&
      (filters?.maxBagni == null || immobile.numeroStanze <= filters.maxBagni);
  
      const matchesClasse =
        (!filters?.classeEnergetica || immobile.classeEnergetica === filters.classeEnergetica);
      
      const matchesMetratura =
        (filters?.minSuperficie == null || immobile.metratura >= filters.minSuperficie) &&
        (filters?.maxSuperficie == null || immobile.metratura <= filters.maxSuperficie);

      return matchesPrezzo && matchesCamere && matchesBagni && matchesClasse && matchesMetratura;
    });
  }  

  onCoordChanged(filters: {
    selectedCoordinata: Coordinata
  }): void {
    const newCoord = filters.selectedCoordinata;
    if (newCoord.latitudine === 0 && newCoord.longitudine === 0) 
    {
      return;
    }
    else if(CoordinataUtil.getDistanceFromLatLonInKm(newCoord.latitudine, newCoord.longitudine, this.coordinate.latitudine, this.coordinate.longitudine) > 4)
    {
      this.coordinate = newCoord;
      this.recoverImmobiliByCoordinate();
      console.log("Distanza:", CoordinataUtil.getDistanceFromLatLonInKm(newCoord.latitudine, newCoord.longitudine, this.coordinate.latitudine, this.coordinate.longitudine));
    }
  }
}
