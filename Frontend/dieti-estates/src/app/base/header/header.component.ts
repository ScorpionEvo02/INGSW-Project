import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CheckCookie } from '../../services/cookie/utente/check-cookie.service';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'header-component',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.css', '../../../styles.css'],
    imports: [CommonModule]
})

export class HeaderComponent implements OnInit{
    logout: boolean = false;
    dropdownOpen: boolean = false;

    constructor(private checkCookie: CheckCookie, private router: Router) {}
    
    ngOnInit(): void 
    {
        if(this.checkCookie.checkCookie())
        {
          this.logout = true;
        }
    }

    async logoutUser(): Promise<void>
    {
        await this.checkCookie.deleteCookie().then(() => {
            this.logout = false;
            this.router.navigate(['/']);
        }).catch(error => {
            console.error("Errore nel logout:", error);
        });
    }
    
    toggleDropdown(): void {
        this.dropdownOpen = !this.dropdownOpen;
    }
      
}