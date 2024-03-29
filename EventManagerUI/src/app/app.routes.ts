import {Routes} from '@angular/router';
import {LoginComponent} from "./components/login/login.component";
import {LogoutComponent} from "./components/logout/logout.component";
import {authGuard} from "./guard/auth.guard";
import {EditEventComponent} from "./components/events/edit-event/edit-event.component";
import {AddEventComponent} from "./components/events/add-event/add-event.component";
import {DetailsEventComponent} from "./components/events/details-event/details-event.component";
import {ErrorComponent} from "./components/error/error.component";
import {DashboardComponent} from "./components/dashboard/dashboard.component";
import {RegisterComponent} from "./components/register/register.component";
import {alreadyLoggedGuard} from "./guard/already-logged.guard";

export const routes: Routes = [
  {path: '', component: DashboardComponent, pathMatch: "full", canActivate: [authGuard]},
  {path: 'events/new', component: AddEventComponent, canActivate: [authGuard]},
  {path: 'events/:id/edit', component: EditEventComponent, canActivate: [authGuard]},
  {path: 'events/:id', component: DetailsEventComponent, canActivate: [authGuard]},
  {path: 'login', component: LoginComponent, canActivate: [alreadyLoggedGuard]},
  {path: 'register', component: RegisterComponent, canActivate: [alreadyLoggedGuard]},
  {path: 'logout', component: LogoutComponent},
  {path: 'error', component: ErrorComponent},
  {path: '**', redirectTo: ''}
];
