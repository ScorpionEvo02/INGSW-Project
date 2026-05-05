import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from '../../services/login.service';
import { LoginRequest } from '../../../models/login-request.model';
import { CheckCookie } from '../../../../services/cookie/utente/check-cookie.service';
import { PopUp } from '../../../models/pop-up';
import { PopUpComponent } from '../../../../base/pop-up/pop-upcomponent';
import { SocialLoginComponent } from '../../components/social-login/social-login.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  standalone: true,
  imports: [CommonModule, FormsModule, PopUpComponent, SocialLoginComponent ],
})
export class LoginComponent implements OnInit {
  email: string = '';
  password: string = '';
  clicked: boolean = false;
  popUp!: PopUp;
  showPopUp: boolean = false;

  constructor(private loginService: LoginService, private router: Router, private checkCookie: CheckCookie) {}

  ngOnInit(): void {
    if (this.checkCookie.checkCookie()) {
      this.router.navigate(['/']);
    }
  }

  login(): void {
    this.clicked = true;

    if (!this.email || !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(this.email) || !this.password) {
      this.popUp = new PopUp({ titolo: 'Errore!', descrizione: 'Inserisci email e password valide.', isSuccess: false });
      this.showPopUp = true;
      this.clicked = false;
      return;
    }

    const loginRequest = new LoginRequest({ email: this.email, password: this.password });

    this.loginService.loginWithFallback(loginRequest).subscribe({
      next: (response) => {
        this.popUp = new PopUp({ titolo: 'Approvato!', descrizione: 'Login riuscito.', isSuccess: true });
        this.showPopUp = true;
        
        setTimeout(() => {
          this.showPopUp = false;
          this.router.navigate(['/']);
        }, 2000); 
      },
      error: (error) => {
        this.popUp = new PopUp({ titolo: 'Riprova!', descrizione: 'Login non riuscito, verifica le credenziali.', isSuccess: false });
        this.showPopUp = true;
        console.error('Errore durante il login:', error);
      }
    });
    this.clicked = false;
  }
  
}