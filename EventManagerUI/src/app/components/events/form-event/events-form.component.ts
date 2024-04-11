import {Component, Input, OnInit} from '@angular/core';
import {MatButton} from "@angular/material/button";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {Event} from "../../../model/event"
import {EventService} from "../../../service/event.service";
import {Router} from "@angular/router";
import {MatMomentDatetimeModule} from "@mat-datetimepicker/moment"
import {MatDatetimepickerModule,MAT_DATETIME_FORMATS} from "@mat-datetimepicker/core";
import {MatDatepickerModule} from "@angular/material/datepicker";
import moment, {Moment} from "moment";
import {endDateEventFormValidator, startDateEventFormValidator} from "../../../validators/event-form-validator";
import {environment} from "../../../../environments/environment";

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
    EventService,
    {
      provide: MAT_DATETIME_FORMATS,
      useValue: {
        parse: {
          datetimeInput: ['YYYY-MM-DD HH:mm', 'DD/MM/YYYY HH:mm'],
        },
        display: {
          dateInput: 'L',
          monthInput: 'MMMM',
          datetimeInput: 'DD/MM/YYYY HH:mm',
          timeInput: 'LT',
          monthYearLabel: 'MMM YYYY',
          dateA11yLabel: 'LL',
          monthYearA11yLabel: 'MMMM YYYY',
          popupHeaderDateLabel: 'ddd DD MMM',
        }
      }
    }
  ],
  templateUrl: './events-form.component.html'
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
      startDate: ['', [Validators.required, startDateEventFormValidator]],
      endDate: ['', [Validators.required, endDateEventFormValidator]],
      location: ['', Validators.required],
      maxCapacity: [1, [Validators.required, Validators.min(Math.max(1, this.event.totalParticipants))]]
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
          .subscribe(()=> {
            this.router.navigate(['/events', this.event.id])
          });
      }else {
        this.eventService.createEvent(editedEvent)
          .subscribe( (newEvent: Event) => {
            this.router.navigate(['/events', newEvent.id]);
          });
      }
    }

  }

  getMinEndDate(): Moment{
    let rawValue = this.form.value.startDate;
    if(!rawValue || rawValue === ""){
      return moment();
    }
    let startDate: Moment = moment.isMoment(rawValue) ?
      rawValue : moment(rawValue, environment.DATE_TIME_FORMAT);

    return startDate.clone().add(5, 'minute');

  }

  protected readonly moment = moment;
}
