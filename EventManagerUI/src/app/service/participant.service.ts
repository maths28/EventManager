import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {User} from "../model/user";
import {Event, PageEvent} from "../model/event";

@Injectable({
  providedIn: 'root'
})
export class ParticipantService {
  constructor(private httpClient: HttpClient) { }

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
