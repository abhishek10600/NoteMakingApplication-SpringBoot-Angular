import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Note } from '../../models/note.model';

@Component({
  selector: 'app-note-form-modal',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './note-form-modal.component.html'
})
export class NoteFormModalComponent {
  @Input() note: Note = { noteTitle: '', noteDescription: '' };
  @Input() isEdit = false;
  @Output() close = new EventEmitter<void>();
  @Output() submit = new EventEmitter<Note>();

  onClose() { this.close.emit(); }
  onSubmit() { this.submit.emit(this.note); }
}
