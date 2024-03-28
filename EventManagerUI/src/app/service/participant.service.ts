import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Participant} from "../model/participant";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {User} from "../model/user";
import {Event, PageEvent} from "../model/event";

@Injectable({
  providedIn: 'root'
})
export class ParticipantService {
  constructor(private httpClient: HttpClient) { }

  registerParticipant(participant: Participant): Observable<Participant>{
    return this.httpClient.post<Participant>(`${environment.BASE_URL}participants`, participant);
  }

  registerParticipantToEvent(participant: User, event: Event): Observable<Event[]>{
    return this.httpClient.post<Event[]>(`${environment.BASE_URL}participants/${participant.userId}/events/${event.id}`, null);
  }

  unregisterParticipantToEvent(participant: User, event: Event): Observable<null>{
    return this.httpClient.delete<null>(`${environment.BASE_URL}participants/${participant.userId}/events/${event.id}`);
  }

  getEvents(participant: User, pageSize: number, pageNumber: number): Observable<PageEvent>{
    return this.httpClient.get<PageEvent>(`${environment.BASE_URL}participants/${participant.userId}/events`, {
      params: {
        pageSize: pageSize,
        pageNumber: pageNumber
      }
    });
  }




}
