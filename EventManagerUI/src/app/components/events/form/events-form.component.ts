import {Component, OnInit} from '@angular/core';
import {MatButton} from "@angular/material/button";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {Event} from "../../../model/event"
import {EventService} from "../../../service/event.service";
import {ActivatedRoute} from "@angular/router";
import {MatMomentDatetimeModule} from "@mat-datetimepicker/moment"
import {MatDatetimepickerModule} from "@mat-datetimepicker/core";
import {MatDatepickerModule} from "@angular/material/datepicker";
import moment from "moment";

@Component({
  selector: 'app-events-form',
  standalone: true,
  imports: [
    MatButton,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
    MatDatepickerModule,
    MatMomentDatetimeModule,
    MatDatetimepickerModule
  ],
  providers: [
    EventService
  ],
  templateUrl: './events-form.component.html',
  styleUrl: './events-form.component.css'
})
export class EventsFormComponent implements OnInit {

  form: FormGroup;
  event: Event;

  constructor(private formBuilder: FormBuilder, private eventService: EventService, private activeRouter: ActivatedRoute){
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
      location: ['', Validators.required],
      maxCapacity: [0, Validators.required]
    });

    this.eventService.getEvent(Number(this.activeRouter.snapshot.paramMap.get('id') || ''))
      .subscribe((event: Event)=> {
        this.event = event;
        this.form.patchValue(this.event);
      });

  }

  onSubmit(): void {
    let editedEvent: Event = this.form.value;
    if(moment.isMoment(editedEvent.startDate)){
      editedEvent.startDate = editedEvent.startDate.format("YYYY-MM-DD HH:mm");
    }
    if(moment.isMoment(editedEvent.endDate)){
      editedEvent.endDate = editedEvent.endDate.format("YYYY-MM-DD HH:mm");
    }
  }



}
