import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css'],
  standalone: true,
  imports: [CommonModule,
            FormsModule
  ]
})

export class LoginFormComponent {
  email: string = '';
  password: string = '';
  
  login() {
    console.log('Login attempted with:', this.email, this.password);
  }
}