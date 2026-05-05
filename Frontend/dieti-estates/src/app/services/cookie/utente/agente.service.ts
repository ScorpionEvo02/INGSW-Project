import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { CookieUtente } from './utente.service';

@Injectable({
  providedIn: 'root'
})

export class CookieAgente implements CookieUtente {

    constructor(private cookieService: CookieService) {};

    getCookie(): String {
        try 
        {
            return this.cookieService.get("uid_agente");
        } 
        catch (error) 
        {
            return "";
        }
    }

    setCookie(sessioneUtente: string): boolean {
        try 
        {
            const giorniVitaCookie = 1;
            this.cookieService.set("uid_agente", sessioneUtente, giorniVitaCookie);
            return true;
        } 
        catch (error) 
        {
            return false;
        }
    }

    deleteCookie(): void 
    {
        this.cookieService.delete("uid_agente", "/");
    }

    checkCookie(): boolean
    {
        return this.cookieService.check("uid_agente");
    }
}