import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {PageEvent, Event} from "../model/event";

@Injectable()
export class EventService {

  constructor(private httpClient: HttpClient) { }

  getAllFutureEvents(pageSize: number, pageNumber: number, location: string): Observable<PageEvent> {
    let params: {pageSize: number, pageNumber: number, location?: string} = {pageSize: pageSize, pageNumber: pageNumber};
    if(location){
      params = {...params, location}
    }
    return this.httpClient.get<PageEvent>(environment.BASE_URL + 'events', {
      params: params
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

  deleteEvent(eventId: number): Observable<null>{
    return this.httpClient.delete<null>(`${environment.BASE_URL}events/${eventId}`, );
  }
}
