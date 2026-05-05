import { Routes } from '@angular/router';
import { HomepageComponent } from './features/homepage/pages/homepage/homepage.component';
import { SearchpageComponent } from './features/searchpage/pages/searchpage/searchpage.component';
import { ProfileComponent } from './features/profile/pages/profile/profile.component';
import { ImmobileComponent } from './features/searchpage/pages/immobile/immobile.component';
import { LoginComponent } from './features/login/pages/login/login.component';
import { RegisterComponent } from './features/register/pages/register/register.component';

export const routes: Routes = [
  { path: '', component: HomepageComponent },  
  { path: 'searchpage', component: SearchpageComponent },  
  { path: 'profile', component: ProfileComponent},
  { path: 'immobile', component: ImmobileComponent},
  { path: 'login', component : LoginComponent},
  { path: 'register', component : RegisterComponent}
];

/*
  { path: '', redirectTo: 'homepage', pathMatch: 'full' }, 
*/