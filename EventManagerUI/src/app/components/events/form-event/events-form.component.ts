import {Component, Input, OnInit} from '@angular/core';
import {MatButton} from "@angular/material/button";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {Event} from "../../../model/event"
import {EventService} from "../../../service/event.service";
import {Router} from "@angular/router";
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
    MatDatetimepickerModule,
  ],
  providers: [
    EventService
  ],
  templateUrl: './events-form.component.html',
  styleUrl: './events-form.component.css'
})
export class EventsFormComponent implements OnInit {

  form: FormGroup;
  @Input() event: Event;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private eventService: EventService){
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

    this.form.patchValue(this.event);

  }

  onSubmit(): void {
    if(this.form.valid){
      this.form.disable();
      let editedEvent: Event = this.form.value;
      if(moment.isMoment(editedEvent.startDate)){
        editedEvent.startDate = editedEvent.startDate.format("YYYY-MM-DD HH:mm");
      }
      if(moment.isMoment(editedEvent.endDate)){
        editedEvent.endDate = editedEvent.endDate.format("YYYY-MM-DD HH:mm");
      }

      if(this.event.id){
        this.eventService.updateEvent(this.event.id, editedEvent)
          .subscribe(()=> this.router.navigate(['/events', this.event.id]));
      }else {
        this.eventService.createEvent(editedEvent)
          .subscribe((newEvent: Event)=> this.router.navigate(['/events', newEvent.id]));
      }
    }

  }



}
