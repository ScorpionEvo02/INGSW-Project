import { Component, EventEmitter, Output  } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'filter-bar-component',
  templateUrl: './filter-bar.component.html',
  styleUrls: ['./filter-bar.component.css'],
  imports: [ CommonModule, FormsModule ]
})

export class FilterBarComponent {
  numbers: number[] = Array.from(Array(50).keys());
  mLeft: string = "20px";

  minPrezzo: number | null = null;
  maxPrezzo: number | null = null;
  minCamereLetto: number | null = null;
  maxCamereLetto: number | null = null;
  minBagni: number | null = null;
  maxBagni: number | null = null;
  classeEnergetica: string | null = null;
  raggio: number = 10;
  minSuperficie: number | null = null;
  maxSuperficie: number | null = null;

  showPrezzo: boolean = false;
  showCamereLetto: boolean = false;
  showBagni: boolean = false;
  showClasseEnergetica: boolean = false;
  showRaggio: boolean = false;
  showSuperficie: boolean = false;

  @Output() filterChanged = new EventEmitter<{
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
  }>();
  
  private emitFilters(): void 
  {
    this.filterChanged.emit({
      minPrezzo: this.minPrezzo,
      maxPrezzo: this.maxPrezzo,
      minCamereLetto: this.minCamereLetto,
      maxCamereLetto: this.maxCamereLetto,
      minBagni: this.minBagni,
      maxBagni: this.maxBagni,
      classeEnergetica: this.classeEnergetica,
      raggio: this.raggio,
      minSuperficie: this.minSuperficie,
      maxSuperficie: this.maxSuperficie
    });
  }

  openFilter(window: "prezzo" | "camere" | "bagni" | "classe_energetica" | "raggio" | "metratura"): void 
  {
    this.open(window);
  }

  applyFilter(): void
  {
    this.open("close");

    if (this.minPrezzo !== null && this.maxPrezzo !== null && this.minPrezzo > this.maxPrezzo) 
    {
      [this.minPrezzo, this.maxPrezzo] = [this.maxPrezzo, this.minPrezzo];
    }
  
    if (this.minCamereLetto !== null && this.maxCamereLetto !== null && this.minCamereLetto > this.maxCamereLetto) 
    {
      [this.minCamereLetto, this.maxCamereLetto] = [this.maxCamereLetto, this.minCamereLetto];
    }

    if (this.minBagni !== null && this.maxBagni !== null && this.minBagni > this.maxBagni) 
    {
      [this.minBagni, this.maxBagni] = [this.maxBagni, this.minBagni];
    }
  
    if (this.minSuperficie !== null && this.maxSuperficie !== null && this.minSuperficie > this.maxSuperficie) 
    {
      [this.minSuperficie, this.maxSuperficie] = [this.maxSuperficie, this.minSuperficie];
    }

    this.emitFilters();
  }

  open(opened: string)
  {
    switch(opened)
    {
      case "prezzo": 
        this.showPrezzo = this.toggleOpen(this.showPrezzo); this.showCamereLetto = false; this.showBagni = false; this.showClasseEnergetica = false; this.showRaggio = false; this.showSuperficie = false; 
        this.mLeft = this.checkWindowWidth() ? "0px" : "20px";
        break;  
      case "camere": 
        this.showPrezzo = false; this.showCamereLetto = this.toggleOpen(this.showCamereLetto); this.showBagni = false; this.showClasseEnergetica = false; this.showRaggio = false; this.showSuperficie = false; 
        this.mLeft = this.checkWindowWidth() ? "0px" : "145px"; 
        break;  
      case "bagni": 
      this.showPrezzo = false; this.showCamereLetto = false; this.showBagni = this.toggleOpen(this.showBagni); this.showClasseEnergetica = false; this.showRaggio = false; this.showSuperficie = false; 
      this.mLeft = this.checkWindowWidth() ? "0px" : "145px"; 
      break;
      case "metratura": 
        this.showPrezzo = false; this.showCamereLetto = false; this.showBagni = false; this.showClasseEnergetica = false; this.showRaggio = false; this.showSuperficie = this.toggleOpen(this.showSuperficie); 
        this.mLeft = this.checkWindowWidth() ? "0px" : "305px"; 
        break;  
      case "classe_energetica": 
        this.showPrezzo = false; this.showCamereLetto = false; this.showBagni = false; this.showClasseEnergetica = this.toggleOpen(this.showClasseEnergetica); this.showRaggio = false; this.showSuperficie = false; 
        this.mLeft = this.checkWindowWidth() ? "0px" : "440px"; 
        console.log("mleft", this.mLeft);
        break;  
      case "raggio": 
        this.showPrezzo = false; this.showCamereLetto = false; this.showBagni = false; this.showClasseEnergetica = false; this.showRaggio = this.toggleOpen(this.showRaggio); this.showSuperficie = false; 
        this.mLeft = this.checkWindowWidth() ? "0px" : "615px"; 
        break;    
      case "close": 
        this.showPrezzo = false; this.showCamereLetto = false; this.showBagni = false; this.showClasseEnergetica = false; this.showRaggio = false; this.showSuperficie = false; 
        break;
    }
  }

  checkWindowWidth(): boolean
  {
    console.log("Valore:", window.innerWidth < 768)
    return window.innerWidth < 768;
  }

  toggleOpen(state: boolean): boolean
  {
    return !state;
  }
}
