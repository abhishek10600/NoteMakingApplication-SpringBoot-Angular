import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AdminService {
  private apiUrl = 'http://localhost:8080/api/admin';

  constructor(private http: HttpClient, private authService: AuthService) {}

  private getHeaders() {
    const token = this.authService.getToken();
    return {
      headers: new HttpHeaders({
        Authorization: `Bearer ${token}`,
      }),
    };
  }

  getAllUsers(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/users`, this.getHeaders());
  }


  getAllNotes(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/notes`, this.getHeaders());
  }


  deleteUser(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/users/${id}`, {
        ...this.getHeaders(),
        responseType: 'text' as 'json'
    });
  }

  deleteNote(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/notes/${id}`, {
        ...this.getHeaders(),
        responseType: 'text' as 'json'
    });
  }
}
