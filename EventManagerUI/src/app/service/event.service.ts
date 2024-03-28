import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, Subject} from "rxjs";
import {environment} from "../../environments/environment";
import {PageEvent, Event} from "../model/event";
import {PageParticipant} from "../model/participant";

@Injectable({
  providedIn: 'root'
})
export class EventService {

  private updatedInscriptions: Subject<boolean> = new Subject<boolean>();
  updatedInscriptions$: Observable<boolean>;

  constructor(private httpClient: HttpClient) { }

  initNotifyInscriptions(): void {
    if(!this.updatedInscriptions$){
      this.updatedInscriptions$ = this.updatedInscriptions.asObservable();
    }
  }

  applyNotifyInscriptions(): void{
    this.updatedInscriptions.next(true);
  }

  getAllFutureEvents(
    pageSize: number,
    pageNumber: number,
    location: string,
    excludeParticipantId?: number
  ): Observable<PageEvent> {
    let params: {pageSize: number, pageNumber: number, location?: string, excludeParticipantId?: number} =
      {pageSize: pageSize, pageNumber: pageNumber};
    if(location){
      params = {...params, location}
    }
    if(excludeParticipantId){
      params = {...params, excludeParticipantId};
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
    return this.httpClient.delete<null>(`${environment.BASE_URL}events/${eventId}`);
  }

  getParticipants(eventId: number, pageSize: number, pageNumber: number): Observable<PageParticipant> {
    return this.httpClient.get<PageParticipant>(`${environment.BASE_URL}events/${eventId}/participants`,
      {
        params: { pageSize: pageSize, pageNumber: pageNumber}
      });
  }
}
