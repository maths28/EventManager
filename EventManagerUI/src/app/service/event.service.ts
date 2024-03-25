import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {PageEvent, Event} from "../model/event";

@Injectable()
export class EventService {

  constructor(private httpClient: HttpClient) { }

  getAllFutureEvents(pageSize: number, pageNumber: number): Observable<PageEvent> {
    return this.httpClient.get<PageEvent>(environment.BASE_URL + 'events', {
      params: {
        pageSize: pageSize,
        pageNumber: pageNumber
      }
    });
  }

  getEvent(eventId: number): Observable<Event> {
    return this.httpClient.get<Event>(`${environment.BASE_URL}events/${eventId}`);
  }

  updateEvent(eventId: number, event: Event): Observable<Event>{
    return this.httpClient.put<Event>(`${environment.BASE_URL}events/${eventId}`, event);
  }

  createEvent(event: Event): Observable<Event>{
    return this.httpClient.post<Event>(`${environment.BASE_URL}events`, event);
  }
}
