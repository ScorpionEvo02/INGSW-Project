export interface CookieUtente {
    
    getCookie() : String;
    setCookie(sessioneUtente : string) : boolean;
    deleteCookie() : void;
    
    checkCookie() : boolean;

}