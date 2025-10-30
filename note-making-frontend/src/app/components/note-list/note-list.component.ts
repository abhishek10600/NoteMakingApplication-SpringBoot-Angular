import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Note } from '../../models/note.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
// import { NoteItemComponent } from '../note-item/note-item.component';
import { NoteItemComponent } from '../note-item/note-item.component';


@Component({
  selector: 'app-note-list',
  standalone: true,
  imports: [CommonModule, FormsModule, NoteItemComponent],
  templateUrl: './note-list.component.html'
})
export class NoteListComponent {
  @Input() notes: Note[] = [];
  @Output() delete = new EventEmitter<number>();
  @Output() edit = new EventEmitter<Note>();
  @Output() toggle = new EventEmitter<number>();

  

  deleteNote(id: number) { this.delete.emit(id); }
  editNote(note: Note) { this.edit.emit(note); }
  toggleStatus(id: number) { this.toggle.emit(id); }
}