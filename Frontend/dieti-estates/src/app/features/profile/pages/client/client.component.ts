import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PropertyDisplayedComponent } from '../../components/client/propertydisplayed/propertydisplayed.component';
import { PersonalDataComponent } from '../../components/client/personaldata/personaldata.component';
import { OfferComponent } from '../../components/client/offer/offer.component';
import { AppointmentComponent } from '../../components/client/appointments/appointment.component';
import { FavoritesComponent } from '../../components/client/favorites/favorites.component';


@Component({
  selector: 'cliente-component',
  templateUrl: './cliente.component.html',
  imports: [ CommonModule, FavoritesComponent,  PropertyDisplayedComponent, PersonalDataComponent, OfferComponent, AppointmentComponent],
  styleUrls: ['./cliente.component.css'],
})

export class ClienteComponent implements OnInit
{
  @Input() uid_cliente!: String;

  ngOnInit(): void 
  {
  
  }
}
