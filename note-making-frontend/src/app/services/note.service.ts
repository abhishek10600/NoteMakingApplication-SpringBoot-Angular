import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Note } from "../models/note.model";

@Injectable({ providedIn: 'root'})
export class NoteService {

    private baseUrl = 'http://localhost:8080/api/note';

    constructor(private http: HttpClient){}

    getAllNotes(): Observable<Note[]>{
        return this.http.get<Note[]>(this.baseUrl);
    }

    createNote(note: Note): Observable<Note>{
        return this.http.post<Note>(this.baseUrl, note);
    }

    updateNote(id: number, note: Note): Observable<Note>{
        return this.http.put<Note>(`${this.baseUrl}/${id}`, note);
    }

    updateNoteStatus(id: number): Observable<Note>{
        return this.http.put<Note>(`${this.baseUrl}/${id}/update-status`, {});
    }

    deleteNote(id: number): Observable<any>{
        return this.http.delete(`${this.baseUrl}/${id}`);
    }

    searchNotes(keyword: string): Observable<Note[]> {
        return this.http.get<Note[]>(`${this.baseUrl}/search?keyword=${keyword}`);
    }

}