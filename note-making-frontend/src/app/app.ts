import { Component, signal } from '@angular/core';
import { NotesComponent } from './pages/notes/notes.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [NotesComponent],
  templateUrl: './app.html',
  styleUrls: ['./app.css']
})

export class App {
  protected readonly title = signal('note-making-frontend');
}