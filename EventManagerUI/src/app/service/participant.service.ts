import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Participant} from "../model/participant";
import {environment} from "../../environments/environment";
import {map, Observable} from "rxjs";
import {User} from "../model/user";
import {Event, PageEvent} from "../model/event";
import {AbstractControl, ValidationErrors} from "@angular/forms";

@Injectable({
  providedIn: 'root'
})
export class ParticipantService {
  constructor(private httpClient: HttpClient) { }

  findByEmail(email: string): Observable<Participant|undefined> {
    return this.httpClient.get<Participant|undefined>(`${environment.BASE_URL}participants/search`, {
      params: {
        email: email
      }
    })
  }

  existsByEmailValidator(control: AbstractControl): Observable<ValidationErrors|null>{
      return this.findByEmail(control.value).pipe(
        map((participant: Participant|undefined) => participant && participant.id ? {uniqueEmail: true} : null)
      );
  }

  registerParticipant(participant: Participant): Observable<Participant>{
    return this.httpClient.post<Participant>(`${environment.BASE_URL}participants`, participant);
  }

  registerParticipantToEvent(participant: User, event: Event): Observable<Event[]>{
    return this.httpClient.post<Event[]>(`${environment.BASE_URL}participants/${participant.id}/events/${event.id}`, null);
  }

  unregisterParticipantToEvent(participant: User, event: Event): Observable<null>{
    return this.httpClient.delete<null>(`${environment.BASE_URL}participants/${participant.id}/events/${event.id}`);
  }

  getEvents(participant: User, pageSize: number, pageNumber: number): Observable<PageEvent>{
    return this.httpClient.get<PageEvent>(`${environment.BASE_URL}participants/${participant.id}/events`, {
      params: {
        pageSize: pageSize,
        pageNumber: pageNumber
      }
    });
  }




}
