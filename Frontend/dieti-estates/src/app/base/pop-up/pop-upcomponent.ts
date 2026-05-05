import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PopUp } from '../../features/models/pop-up';

@Component({
  selector: 'pop-up-component',
  standalone: true,
  templateUrl: './pop-up.component.html',
  styleUrls: ['./pop-up.component.css'],
  imports: [CommonModule]
})
export class PopUpComponent {
  @Input() popUp!: PopUp;
  @Input() isOpen: boolean = false;

  @Output() close = new EventEmitter<void>();

  closeModal(): void 
  {
    this.close.emit();
  }
}
