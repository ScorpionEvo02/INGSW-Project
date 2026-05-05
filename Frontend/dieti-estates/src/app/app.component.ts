import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { HeaderComponent } from './base/header/header.component';

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  imports: [RouterModule] 
})
export class AppComponent {
  title = 'dieti-estates';
}

