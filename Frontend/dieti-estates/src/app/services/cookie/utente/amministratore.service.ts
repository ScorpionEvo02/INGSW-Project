import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { CookieUtente } from './utente.service';

@Injectable({
  providedIn: 'root'
})

export class CookieAmministratore implements CookieUtente {

    constructor(private cookieService: CookieService) {};

    getCookie(): String 
    {
        try 
        {
            return this.cookieService.get("uid_amministratore");
        } 
        catch (error) 
        {
            return "";
        }
    }

    setCookie(sessioneUtente: string): boolean 
    {
        try 
        {
            const giorniVitaCookie = 1;
            this.cookieService.set("uid_amministratore", sessioneUtente, giorniVitaCookie);
            return true;
        } 
        catch (error) 
        {
            return false;
        }
    }

    deleteCookie(): void 
    {
        this.cookieService.delete("uid_amministratore", "/");
    }

    checkCookie(): boolean
    {
        return this.cookieService.check("uid_amministratore");
    }
}