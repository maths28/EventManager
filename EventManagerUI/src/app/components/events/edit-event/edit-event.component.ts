import {Component, OnInit} from '@angular/core';
import {EventService} from "../../../service/event.service";
import {ActivatedRoute} from "@angular/router";
import {Event} from "../../../model/event";
import {EventsFormComponent} from "../form/events-form.component";
import {Observable} from "rxjs";
import {AsyncPipe} from "@angular/common";

@Component({
  selector: 'app-edit-event',
  standalone: true,
  imports: [
    EventsFormComponent,
    AsyncPipe
  ],
  providers: [
    EventService
  ],
  templateUrl: './edit-event.component.html',
  styleUrl: './edit-event.component.css'
})
export class EditEventComponent implements OnInit{

  event$: Observable<Event>;

  constructor(private eventService: EventService, private activeRouter: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.event$ = this.eventService.getEvent(Number(this.activeRouter.snapshot.paramMap.get('id') || ''));
  }





}
