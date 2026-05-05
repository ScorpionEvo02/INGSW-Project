import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {Auth, browserSessionPersistence, GoogleAuthProvider, FacebookAuthProvider, GithubAuthProvider, signInWithPopup, signOut, user, User} from '@angular/fire/auth';
import { UserCredential,setPersistence } from 'firebase/auth';
import { from, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  user$: Observable<User | null>;

  constructor(private firebaseAuth: Auth, private http: HttpClient) {
    this.setSessionStoragePersistence();
    this.user$ = user(this.firebaseAuth);
  }

  private setSessionStoragePersistence(): void {
    setPersistence(this.firebaseAuth, browserSessionPersistence);
  }

  logout(): Observable<void> {
    const promise = signOut(this.firebaseAuth).then(() => {
      sessionStorage.clear();
    });
    return from(promise);
  }

  private checkEmail(email: string): Promise<string> {
    return this.http.get(`http://localhost:8080/login/check-email?email=${email}`, {
      responseType: 'text',
      withCredentials: true
    }).toPromise().then(ruolo => {
      if (!ruolo) throw { code: 'utente-non-trovato-nel-db' };
      return ruolo;
    }).catch(() => {
      throw { code: 'utente-non-trovato-nel-db' };
    });
  }

  private createSocialSession(email: string, ruolo: string): Promise<void> {
    return this.http.post(`http://localhost:8080/login/social-login?email=${email}&ruolo=${ruolo}`, {}, {
      responseType: 'text',
      withCredentials: true
    }).toPromise().then(() => {
      // Sessione creata correttamente
    }).catch(() => {
      throw { code: 'creazione-cookie-fallita' };
    });
  }

  loginWithGoogle(): Observable<void> {
    const provider = new GoogleAuthProvider();
    const promise = signInWithPopup(this.firebaseAuth, provider).then(async (result: UserCredential) => {
      const email = result.user.email;
      if (!email) throw { code: 'no-email' };

      const ruolo = await this.checkEmail(email);
      await this.createSocialSession(email, ruolo);
    });
    return from(promise);
  }

  loginWithFacebook(): Observable<void> {
    const provider = new FacebookAuthProvider();
    const promise = signInWithPopup(this.firebaseAuth, provider).then(async (result: UserCredential) => {
      const email = result.user.email;
      if (!email) throw { code: 'no-email' };

      const ruolo = await this.checkEmail(email);
      await this.createSocialSession(email, ruolo);
    });
    return from(promise);
  }

  loginWithGitHub(): Observable<void> {
    const provider = new GithubAuthProvider();
    const promise = signInWithPopup(this.firebaseAuth, provider).then(async (result: UserCredential) => {
      const email = result.user.email;
      if (!email) throw { code: 'no-email' };

      const ruolo = await this.checkEmail(email);
      await this.createSocialSession(email, ruolo);
    });
    return from(promise);
  }

}