import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Participant} from "../model/participant";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {User} from "../model/user";
import {Event} from "../model/event";

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




}
