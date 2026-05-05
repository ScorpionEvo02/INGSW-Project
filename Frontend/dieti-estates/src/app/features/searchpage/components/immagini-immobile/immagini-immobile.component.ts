import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ImmagineImmobile } from '../../../models/immagineimmobile.model';

@Component({
  selector: 'immagini-immobile-component',
  templateUrl: './immagini-immobile.component.html',
  styleUrls: ['./immagini-immobile.component.css'],
  standalone: true,
  imports: [CommonModule]
})
export class ImmaginiImmobileComponent implements OnInit, OnDestroy {
  @Input() immaginiImmobile: ImmagineImmobile[] = [];

  currentIndex: number = 0;
  intervalId: any;

  ngOnInit(): void 
  {
    if (this.immaginiImmobile.length > 0) {
      this.startSlideshow();
    }
  }

  ngOnDestroy(): void 
  {
    if (this.intervalId) 
    {
      clearInterval(this.intervalId);
    }
  }

  startSlideshow(): void 
  {
    this.intervalId = setInterval(() => {
      this.next();
    }, 6000);
  }

  next(): void 
  {
    this.currentIndex = (this.currentIndex + 1) % this.immaginiImmobile.length;
  }

  prev(): void 
  {
    this.currentIndex = (this.currentIndex - 1 + this.immaginiImmobile.length) % this.immaginiImmobile.length;
  }

  getPreviousIndex(): number 
  {
    return (this.currentIndex - 1 + this.immaginiImmobile.length) % this.immaginiImmobile.length;
  }

  getNextIndex(): number 
  {
    return (this.currentIndex + 1) % this.immaginiImmobile.length;
  }
}
