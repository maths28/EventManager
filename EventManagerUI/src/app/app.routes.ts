import { Routes } from '@angular/router';
import {ListComponent} from "./components/events/list/list.component";
import {ParticipantListComponent} from "./participants/list/participant-list.component";
import {LoginComponent} from "./components/login/login.component";
import {LogoutComponent} from "./components/logout/logout.component";
import {authGuardGuard} from "./guard/auth-guard.guard";
import {HomeComponent} from "./components/home/home.component";

export const routes: Routes = [
  {path: '', component: HomeComponent, pathMatch: "full"},
  {path: 'events', component: ListComponent, canActivate: [authGuardGuard]},
  {path: 'participants', component: ParticipantListComponent},
  {path: 'login', component: LoginComponent},
  {path: 'logout', component: LogoutComponent},
  {path: '**', redirectTo: ''}
];
