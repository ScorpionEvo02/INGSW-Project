
import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ManageAdminComponent } from '../../components/admin/manageAdmin/manageAdmin.component';
import { PersonalDataAdminComponent } from '../../components/admin/personalDataAdmin/personalDataAdmin.component';

import { InsertManagerComponent } from '../../components/admin/insertManager/insertManager.component';
import { InsertAdminComponent } from '../../components/admin/insertAdmin/insertAdmin.component';

@Component({
  selector: 'admin-component',
  templateUrl: './admin.component.html',
  imports: [CommonModule, InsertManagerComponent, PersonalDataAdminComponent, ManageAdminComponent, InsertAdminComponent ],
  styleUrls: ['./admin.component.css'],
})

export class AdminComponent implements OnInit
{
  @Input() uid_cliente!: String;

  ngOnInit(): void 
  {
  
  }
}
