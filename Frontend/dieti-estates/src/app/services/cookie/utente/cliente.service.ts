import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { CookieUtente } from './utente.service';

@Injectable({
  providedIn: 'root'
})

export class CookieCliente implements CookieUtente {

    constructor(private cookieService: CookieService) {};

    getCookie(): String 
    {
        try 
        {
            return this.cookieService.get("uid_cliente");
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
            this.cookieService.set("uid_cliente", sessioneUtente, giorniVitaCookie);
            return true;
        } 
        catch (error) 
        {
            return false;
        }
    }

    deleteCookie(): void 
    {
        this.cookieService.delete("uid_cliente", "/");
    }

    checkCookie(): boolean
    {
        return this.cookieService.check("uid_cliente");
    }
}