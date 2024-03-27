import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Participant} from "../model/participant";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ParticipantService {
  constructor(private httpClient: HttpClient) { }

  registerParticipant(participant: Participant): Observable<Participant>{
    return this.httpClient.post<Participant>(`${environment.BASE_URL}participants`, participant);
  }




}
