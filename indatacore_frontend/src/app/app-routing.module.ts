
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { NotLoggedInGuard } from './guards/not-logged-in.guard';
import { AuthenticatedOnlyGuard } from './guards/authenticated-only.guard';
import { RegisterComponent } from './pages/register/register.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';

const routes: Routes = [
    { path: '', pathMatch: 'full', redirectTo: 'dashboard' },
    { path: 'login', component: LoginComponent, canActivate: [NotLoggedInGuard]},
    { path: 'register', component: RegisterComponent, canActivate: [NotLoggedInGuard]},
    { path: 'dashboard', component: DashboardComponent, canActivate: [AuthenticatedOnlyGuard]},


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
