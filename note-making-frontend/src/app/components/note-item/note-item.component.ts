import { Component, Input, Output, EventEmitter } from "@angular/core";
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Note } from "../../models/note.model";
import { LucideAngularModule } from 'lucide-angular';

@Component({
    selector: 'app-note-item',
    standalone: true,
    imports: [CommonModule, FormsModule, LucideAngularModule],
    templateUrl: './note-item.component.html'
})

export class NoteItemComponent{

    @Input() note!: Note;
    @Output() delete = new EventEmitter<number>();
    @Output() edit = new EventEmitter<Note>();
    @Output() toggle = new EventEmitter<number>();

    onDelete(){
        this.delete.emit(this.note.id);
    }

    onEdit(){
        this.edit.emit(this.note);
    }

    toggleStatus(){
        this.toggle.emit(this.note.id);
    }
}