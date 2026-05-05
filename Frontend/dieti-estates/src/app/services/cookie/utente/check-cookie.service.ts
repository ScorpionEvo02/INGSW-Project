import { Injectable } from '@angular/core';
import { CookieAgente } from './agente.service';
import { CookieAmministratore } from './amministratore.service';
import { CookieCliente } from './cliente.service';
import { CookieGestore } from './gestore.service';

@Injectable({
  providedIn: 'root'
})

export class CheckCookie  {

    constructor(private cookieAgente: CookieAgente, private cookieAmministratore: CookieAmministratore,
        private cookieCliente: CookieCliente, private cookieGestore: CookieGestore) {};

    checkCookie(): boolean
    {
        return this.cookieAgente.checkCookie() || this.cookieAmministratore.checkCookie() ||
         this.cookieCliente.checkCookie() || this.cookieGestore.checkCookie();
    }
    
    async deleteCookie(): Promise<void> {
        return Promise.all([
            this.cookieCliente.deleteCookie(),
            this.cookieAgente.deleteCookie(),
            this.cookieAmministratore.deleteCookie(),
            this.cookieGestore.deleteCookie()
        ]).then(() => {
        }).catch(error => {
            throw error;
        });
    }
    
}