import { Component, AfterViewInit, ViewChild, ElementRef  } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { SearchService } from '../../services/search.service';
import { PopUp } from '../../../models/pop-up';
import { PopUpComponent } from '../../../../base/pop-up/pop-upcomponent';

declare var google: any;

@Component({
  selector: 'search-component',
  templateUrl: './search.component.html',
  imports: [CommonModule, FormsModule, PopUpComponent],
  styleUrls: ['./search.component.css'],
})

export class SearchComponent implements AfterViewInit {
  
  searchType: "Vendita" | "Affitto" | "Vendute" = "Vendita";
  query: string = "";
  filteredSuggestions: Array<{ description: string; place_id: string }> = [];
  autocompleteService: any;
  popUp!: PopUp;
  showPopUp: boolean = false;

  @ViewChild('autocompleteInput') autocompleteInput!: ElementRef<HTMLInputElement>;

  constructor(private searchService: SearchService, private router: Router) {}

  ngAfterViewInit(): void 
  {
    if (google && google.maps && google.maps.places) 
    {
      this.autocompleteService = new google.maps.places.AutocompleteService();
    }
  }

  setSearchType(valore: "Vendita" | "Affitto" | "Vendute") 
  {
    this.searchType = valore;
    console.log("Tipo ricerca:", valore);
  }

  updateSuggestions() 
  {
    if (!this.query || this.query.length < 3) 
    {
      this.filteredSuggestions = [];
      return;
    }
    
    if (this.autocompleteService) 
    {
      this.autocompleteService.getPlacePredictions({ input: this.query }, 
          (predictions: any, status: any) => {
        if (status === google.maps.places.PlacesServiceStatus.OK && predictions) 
        {
          this.filteredSuggestions = predictions;
        } 
        else 
        {
          this.filteredSuggestions = [];
        }
      });
    }
  }

  selectSuggestion(suggestion: any): void 
  {
    this.query = suggestion.description;
    this.filteredSuggestions = [];
  }

  startSearch() {
    if(this.query.length < 4)
    {
      this.popUp = new PopUp({ titolo: 'Errore!', descrizione: 'Inserire una città per effettuare la ricerca.', isSuccess: false });
      this.showPopUp = true;
      return;
    }  

    this.searchService.geocodeAddress(this.query).subscribe({
      next: coords => {
        const coordinate = ({ latitudine: coords.lat, longitudine: coords.lng });    
        this.router.navigate(['/searchpage'], { state: { searchType: this.searchType, coordinate: coordinate } });
      },
      error: err => {
        console.error("Errore durante la geocodifica:", err);
        return;
      }
    }); 
  }

}
