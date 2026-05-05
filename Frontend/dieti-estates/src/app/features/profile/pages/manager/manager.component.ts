import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AgencyDataManagerComponent } from '../../components/manager/agencyDataManager/agencyDataManager.component';
import { PersonalDataManagerComponent } from '../../components/manager/personalDataManager/personalDataManager.component';

import { AppointmentManagerComponent } from '../../components/manager/appointmentManager/appointmentManager.component';
import { CommunicationsComponent } from '../../components/manager/communications/communications.component';

import { manageAgentComponent } from '../../components/manager/manageAgent/manageAgent.component';
import { InsertAgentComponent } from '../../components/manager/insertAgent/insertAgent.component';


@Component({
  selector: 'manager-component',
  templateUrl: './manager.component.html',
  imports: [CommonModule, PersonalDataManagerComponent, InsertAgentComponent, manageAgentComponent],
  styleUrls: ['./manager.component.css'],
})

export class GestoreComponent implements OnInit
{
  @Input() uid_gestore!: String;

  ngOnInit(): void 
  {
  
  }
}
