import {Routes} from '@angular/router';
import {ListComponent} from "./components/events/list/list.component";
import {ParticipantListComponent} from "./participants/list/participant-list.component";
import {LoginComponent} from "./components/login/login.component";
import {LogoutComponent} from "./components/logout/logout.component";
import {authGuardGuard} from "./guard/auth-guard.guard";
import {HomeComponent} from "./components/home/home.component";
import {EditEventComponent} from "./components/events/edit-event/edit-event.component";
import {AddEventComponent} from "./components/events/add-event/add-event.component";
import {DetailsEventComponent} from "./components/events/details-event/details-event.component";

export const routes: Routes = [
  {path: '', component: HomeComponent, pathMatch: "full"},
  {path: 'events/new', component: AddEventComponent, canActivate: [authGuardGuard]},
  {path: 'events/:id/edit', component: EditEventComponent, canActivate: [authGuardGuard]},
  {path: 'events/:id', component: DetailsEventComponent, canActivate: [authGuardGuard]},
  {path: 'events', component: ListComponent, canActivate: [authGuardGuard]},
  {path: 'participants', component: ParticipantListComponent},
  {path: 'login', component: LoginComponent},
  {path: 'logout', component: LogoutComponent},
  {path: '**', redirectTo: ''}
];
