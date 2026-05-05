import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { CookieUtente } from './utente.service';

@Injectable({
  providedIn: 'root'
})

export class CookieGestore implements CookieUtente {

    constructor(private cookieService: CookieService) {};

    getCookie(): String {
        try 
        {
            return this.cookieService.get("uid_gestore");
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
            this.cookieService.set("uid_gestore", sessioneUtente, giorniVitaCookie);
            return true;
        } 
        catch (error) 
        {
            return false;
        }
    }

    deleteCookie(): void {
        this.cookieService.delete("uid_gestore", "/");
    }

    checkCookie(): boolean
    {
        return this.cookieService.check("uid_gestore");
    }
}