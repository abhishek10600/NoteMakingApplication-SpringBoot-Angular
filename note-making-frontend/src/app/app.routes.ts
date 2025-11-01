import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { NotesComponent } from './pages/notes/notes.component';
import { AuthGuard } from './guards/auth.guard';
import { RegisterComponent } from './pages/register/register.component';
import { AdminDashboardComponent } from './pages/admin-dashboard/admin-dashboard.component';

export const routes: Routes = [
    {path: '', redirectTo: '/login', pathMatch: 'full'},
    {path: 'login', component: LoginComponent},
    {path: 'register', component: RegisterComponent},
    {path: 'notes', component: NotesComponent, canActivate: [AuthGuard]},
    {path: 'admin-dashboard', component: AdminDashboardComponent },
    {path: '**', redirectTo: '/login'},
];
