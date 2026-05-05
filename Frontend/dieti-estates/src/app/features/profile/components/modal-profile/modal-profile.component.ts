import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'modal-profile',
  templateUrl: './modal-profile.component.html',
  styleUrls: ['./modal-profile.component.css'],
  imports: [CommonModule]
})
export class ModalProfileComponent {
  @Input() isOpen: boolean = false;
  @Input() popUpName!: string;
  @Output() close = new EventEmitter<void>();

  closeModal(): void 
  {
    this.close.emit();
  }
}
