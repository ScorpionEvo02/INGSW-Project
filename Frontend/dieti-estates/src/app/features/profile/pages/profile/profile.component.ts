import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { HeaderComponent } from '../../../../base/header/header.component';
import { CookieCliente } from '../../../../services/cookie/utente/cliente.service';
import { CookieAgente } from '../../../../services/cookie/utente/agente.service';
import { CookieGestore } from '../../../../services/cookie/utente/gestore.service';
import { CookieAmministratore } from '../../../../services/cookie/utente/amministratore.service';
import { ClienteComponent } from '../client/client.component';
import { AgentComponent } from '../agent/agent.component';
import { GestoreComponent} from '../manager/manager.component';
import { AdminComponent } from '../admin/admin.component';

@Component({
  selector: 'profile-component',
  templateUrl: './profile.component.html',
  imports: [ HeaderComponent, CommonModule, ClienteComponent, AgentComponent, GestoreComponent, AdminComponent],
})

export class ProfileComponent implements OnInit
{
  utente : String = "";

  tipo : "cliente" | "amministratore" | "gestore" | "agente" | "" = "";
  constructor(  private cookieAgente: CookieAgente, private cookieCliente: CookieCliente,
              private cookieGestore: CookieGestore,  private cookieAmministratore: CookieAmministratore, 
              private router: Router
  ) {};


  ngOnInit(): void 
  {
    this.utente = this.cookieAgente.getCookie();
    if(this.utente != "")
    {
      this.tipo = "agente";
    }
    else
    {
      this.utente = this.cookieCliente.getCookie();
      if(this.utente != "")
        {
          this.tipo = "cliente";
        }
        else
        {
          this.utente = this.cookieGestore.getCookie();
          if(this.utente != "")
            {
              this.tipo = "gestore";
            }
            else
            {
              this.utente = this.cookieAmministratore.getCookie();
              if(this.utente != "")
                {
                  this.tipo = "amministratore";
                }
              else
                {
                  this.router.navigate(['/homepage'], { });
                }
            }
        }
    }
      
  }
}
