import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MyProperty } from '../../components/agent/myproperty/myproperty.component';
import { AgencyDataAgentComponent } from '../../components/agent/agencyDataAgent/agencyDataAgent.component';
import { AppointmentAgentComponent } from '../../components/agent/appointmentAgent/appointmentAgent.component';
import { OfferAgentComponent } from '../../components/agent/offerAgent/offerAgent.component';
import { PersonalDataAgentComponent } from "../../components/agent/personalDataAgent/personalDataAgent.component";
import { ManagePropertyComponent } from '../../components/agent/manageproperty/manageproperty.component';



@Component({
  selector: 'agent-component',
  templateUrl: './agent.component.html',
  imports: [CommonModule, ManagePropertyComponent, MyProperty, AppointmentAgentComponent, OfferAgentComponent, PersonalDataAgentComponent],
  styleUrls: ['./agent.component.css'],
})

export class AgentComponent implements OnInit
{
  @Input() uid_agent!: String;

  ngOnInit(): void 
  {
  
  }
}
