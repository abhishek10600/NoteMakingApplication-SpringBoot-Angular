import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AdminService } from '../../services/admin.service';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-dashboard.component.html',
})
export class AdminDashboardComponent implements OnInit {
  users: any[] = [];
  notes: any[] = [];
  nonAdminUsers: any[] = [];
  activeTab: 'users' | 'notes' = 'users'; 

  constructor(
    private adminService: AdminService,
    private router: Router
  ) {}

  ngOnInit() {
    this.loadUsers();
    this.loadNotes();
  }

//   get nonAdminUsers() {
//     return this.users.filter(u => u.roles !== 'ROLE_ADMIN');
//   }

  loadUsers() {
    this.adminService.getAllUsers().subscribe({
      next: (res) => {
        this.users = res;
        this.nonAdminUsers = this.users.filter(
          (u) => u.roles !== 'ROLE_ADMIN'
        ); // ✅ filter here
      },
      error: (err) => console.error('Error loading users:', err),
    });
  }

  loadNotes() {
    this.adminService.getAllNotes().subscribe({
      next: (res) => (this.notes = res),
      error: (err) => console.error('Error loading notes:', err),
    });
  }

  deleteUser(id: number) {
    if (confirm('Are you sure you want to delete this user?')) {
      this.adminService.deleteUser(id).subscribe({
        next: () => {
          alert('User deleted successfully!');
          this.loadUsers();
        },
        error: (err) => alert('Error deleting user: ' + err.message),
      });
    }
  }

  deleteNote(id: number) {
    if (confirm('Are you sure you want to delete this note?')) {
      this.adminService.deleteNote(id).subscribe({
        next: (res) => {
          alert(res);
          this.loadNotes();
        },
        error: (err) => alert('Error deleting note: ' + err.message),
      });
    }
  }

  logout() {
    localStorage.removeItem('access_token');
    localStorage.removeItem('refresh_token');
    alert('Logged out successfully!');
    this.router.navigate(['/login']);
  }
}
