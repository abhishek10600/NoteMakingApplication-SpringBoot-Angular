import { Component, OnInit } from '@angular/core';
import { NoteService } from '../../services/note.service';
import { Note } from '../../models/note.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NoteListComponent } from '../../components/note-list/note-list.component';
import { NoteFormModalComponent } from '../../components/note-form-modal/note-form-modal.component';
import { HeaderComponent } from '../../components/header/header.component';
import { debounceTime, distinctUntilChanged, Subject } from 'rxjs';

@Component({
  selector: 'app-notes',
  standalone: true,
  imports: [CommonModule, NoteListComponent, NoteFormModalComponent, HeaderComponent, FormsModule],
  templateUrl: './notes.component.html'
})
export class NotesComponent implements OnInit {
  notes: Note[] = [];
  showModal = false;
  isEdit = false;
  selectedNote: Note = { noteTitle: '', noteDescription: '' };
  searchKeyword: string = "";
  private searchSubject = new Subject<string>();

  constructor(private noteService: NoteService) {}

  ngOnInit() {
    this.fetchNotes();

    this.searchSubject.pipe(
    debounceTime(400),
    distinctUntilChanged()
  ).subscribe(keyword => {
    if(keyword.trim()){
      this.noteService.searchNotes(keyword).subscribe((data)=>this.notes = data);
    }else{
      this.fetchNotes();
    }
  });
  }

  fetchNotes() {
    this.noteService.getAllNotes().subscribe((data) => (this.notes = data));
  }

  onSearch(){
    const keyword = this.searchKeyword.trim();

    if(keyword){
      this.noteService.searchNotes(keyword).subscribe((data)=>(this.notes = data));
    }else{
      this.fetchNotes();
    }
  }

  onSearchInput(keyword: string){
    this.searchSubject.next(keyword);
  }

  openCreateModal() {
    this.isEdit = false;
    this.selectedNote = { noteTitle: '', noteDescription: '' };
    this.showModal = true;
  }

  openEditModal(note: Note) {
    this.isEdit = true;
    this.selectedNote = { ...note };
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
  }

  submitNote(note: Note) {
    if (this.isEdit && note.id) {
      this.noteService.updateNote(note.id, note).subscribe(() => this.fetchNotes());
    } else {
      this.noteService.createNote(note).subscribe(() => this.fetchNotes());
    }
    this.closeModal();
  }

  deleteNote(id: number) {
    this.noteService.deleteNote(id).subscribe(() => this.fetchNotes());
  }

  toggleNoteStatus(id: number) {
    this.noteService.updateNoteStatus(id).subscribe(() => this.fetchNotes());
  }
}
