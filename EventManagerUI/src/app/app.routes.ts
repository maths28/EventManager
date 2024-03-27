import {Routes} from '@angular/router';
import {ParticipantListComponent} from "./participants/list/participant-list.component";
import {LoginComponent} from "./components/login/login.component";
import {LogoutComponent} from "./components/logout/logout.component";
import {authGuardGuard} from "./guard/auth-guard.guard";
import {EditEventComponent} from "./components/events/edit-event/edit-event.component";
import {AddEventComponent} from "./components/events/add-event/add-event.component";
import {DetailsEventComponent} from "./components/events/details-event/details-event.component";
import {ErrorComponent} from "./components/error/error.component";
import {DashboardComponent} from "./components/dashboard/dashboard.component";
import {RegisterComponent} from "./components/register/register.component";

export const routes: Routes = [
  {path: '', component: DashboardComponent, pathMatch: "full", canActivate: [authGuardGuard]},
  {path: 'events/new', component: AddEventComponent, canActivate: [authGuardGuard]},
  {path: 'events/:id/edit', component: EditEventComponent, canActivate: [authGuardGuard]},
  {path: 'events/:id', component: DetailsEventComponent, canActivate: [authGuardGuard]},
  {path: 'participants', component: ParticipantListComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'logout', component: LogoutComponent},
  {path: 'error', component: ErrorComponent},
  {path: '**', redirectTo: ''}
];
