import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.component.html'
})
export class LoginComponent {
  username = '';
  password = '';

  constructor(private authService: AuthService, private router: Router) {}

  onLogin() {
    this.authService.login(this.username, this.password).subscribe({
      next: res => {
        this.authService.saveAuthData(res.token, res.roles);

        const userRole = res.roles.trim();

        if(userRole === "ROLE_ADMIN"){
          this.router.navigate(['/admin-dashboard']);
        }else{
          this.router.navigate(['/notes']);
        }
      },
      error: () => alert('Invalid credentials')
    });
  }
}
