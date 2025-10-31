import { Component, EventEmitter, Output } from "@angular/core";
import { Router } from "@angular/router";
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-header',
     standalone: true,
    imports: [CommonModule],
    templateUrl: './header.component.html'
})

export class HeaderComponent{

    @Output() openCreateModal = new EventEmitter<void>();

    constructor(private router: Router) {}

    onCreateNewNote(){
        this.openCreateModal.emit();
    }

    isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }

  logout(): void {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }

}