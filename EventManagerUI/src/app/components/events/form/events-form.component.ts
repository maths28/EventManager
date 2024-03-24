import {Component, OnInit, ViewChild} from '@angular/core';
import {MatButton} from "@angular/material/button";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {FormBuilder, FormGroup, FormsModule, NgForm, ReactiveFormsModule, Validators} from "@angular/forms";
import {Event} from "../../../model/event"
import {EventService} from "../../../service/event.service";
import {ActivatedRoute} from "@angular/router";
import {MatDatepickerModule} from "@angular/material/datepicker";

@Component({
  selector: 'app-events-form',
  standalone: true,
    imports: [
        MatButton,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule,
        MatDatepickerModule
    ],
  providers: [
    EventService,
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
      maxCapacity: ['', Validators.required]
    });

    this.eventService.getEvent(Number(this.activeRouter.snapshot.paramMap.get('id') || ''))
      .subscribe((event)=> {
        this.event = event;
        this.form.patchValue(this.event);
      });

  }



}
