import { Component } from '@angular/core';
import { AuthService } from '../../../../services/autentication/auth.service';
import { PopUpComponent } from '../../../../base/pop-up/pop-upcomponent';
import { Router } from '@angular/router';
@Component({
  selector: 'app-social-login',
  templateUrl: './social-login.component.html',
  styleUrls: ['./social-login.component.css'],
  imports: [ PopUpComponent ],
})
export class SocialLoginComponent {

  errorMessage: string | null = null;
  isLoggingIn = false;
  showPopUp = false;
  popUp = {
    isSuccess: false,
    titolo: '',
    descrizione: ''
  };
  
  constructor(private authService: AuthService,  private router: Router) {}

  loginWith(provider: 'Google' | 'Facebook' | 'GitHub'): void 
  {
    
    if (this.isLoggingIn) return;

    this.isLoggingIn = true;

    const loginMap = {
      Google: this.authService.loginWithGoogle,
      Facebook: this.authService.loginWithFacebook,
      GitHub: this.authService.loginWithGitHub,
    };

    const loginHandler = loginMap[provider];

    if (loginHandler) {
      loginHandler.call(this.authService).subscribe({
        next: () => {
          this.isLoggingIn = false;
          this.popUp = {
            isSuccess: true, titolo: 'Approvato!', descrizione: `Login Riuscito.`
          };
          this.showPopUp = true;
          setTimeout(() => {
            this.router.navigate(['/']);
          }, 1500);
        },
        error: async (err) => {
          console.error(`Errore login ${provider}`, err);
          this.isLoggingIn = false;

          if (err.code === 'auth/account-exists-with-different-credential') {
            const email = err.customData?.email;

            this.popUp = {
              isSuccess: false, titolo: 'Riprova!', descrizione: `L'indirizzo email ${email} è già registrato con un altro provider!`
            };
            this.showPopUp = true;
          }
          else if (err.code === 'utente-non-trovato-nel-db') {
            this.popUp = {
              isSuccess: false,
              titolo: 'Utente non trovato!',
              descrizione: `Nessun utente con questa email è presente nel nostro sistema. Procedi con la registrazione.`
            };
            this.showPopUp = true;
          }
          else {
            this.popUp = {
              isSuccess: false, titolo: 'Errore!', descrizione: 'Si è verificato un errore in fase di accesso. Riprova più tardi.'
            };
            this.showPopUp = true;
          }
        }
      });
    }
  }

  onClosePopUp(): void {
    this.showPopUp = false;
  
    // Reindirizza alla registrazione solo se l'errore è "utente non trovato"
    if (this.popUp.titolo.includes('Utente non trovato!')) {
      this.router.navigate(['/register']);
    }
  }

}
