import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {environment} from "../../environments/environment";
import {PageEvent} from "../model/event";

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
}
