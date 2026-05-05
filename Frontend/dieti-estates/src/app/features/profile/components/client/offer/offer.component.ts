import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OffertaService } from '../../../../../services/offerta.service';
import { OffertaImmobile } from '../../../../models/offertaimmobile.model';
import { OffersComponent } from '../../common/offers/offers.component';
import { ModalProfileComponent } from '../../modal-profile/modal-profile.component';

@Component({
  selector: 'offer-component',
  templateUrl: './offer.component.html',
  imports: [ CommonModule, OffersComponent, ModalProfileComponent ],
  styleUrls: ['./offer.component.css'],
})
export class OfferComponent implements OnInit {
  offerteImmobile: OffertaImmobile[] = [];
  showOffer = false;

  constructor(private offertaService: OffertaService) {}

  ngOnInit(): void {
    this.offertaService.getOffersCliente().subscribe({
      next: (data) => {
        this.offerteImmobile = data;
        console.log('Offerte ricevute', this.offerteImmobile);
      },
      error: (err) => console.error('Errore nel recupero delle offerte', err),
    });
  }

  openOffer(): void 
  {
    this.showOffer = true;
  }

  closeOffer(): void 
  {
    this.showOffer = false;
  }
}

