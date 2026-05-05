import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { GoogleMapsModule } from '@angular/google-maps';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { provideHttpClient } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { AppComponent } from './app.component'; 

@NgModule({

  imports: [
    AppComponent, 
    BrowserModule,
    CommonModule,
    FormsModule,
    GoogleMapsModule,
  ],
  providers: [provideHttpClient(), CookieService,]
})
export class AppModule { }
