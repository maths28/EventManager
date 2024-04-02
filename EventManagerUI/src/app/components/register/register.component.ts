import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatButton} from "@angular/material/button";
import {MatError, MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {ParticipantService} from "../../service/participant.service";
import {Observable} from "rxjs";
import {Participant} from "../../model/participant";
import {AsyncPipe} from "@angular/common";
import {RouterLink} from "@angular/router";
import {MatCard, MatCardContent, MatCardHeader, MatCardTitle} from "@angular/material/card";

@Component({
  selector: 'app-register',
  standalone: true,
    imports: [
        MatButton,
        MatError,
        MatFormField,
        MatInput,
        MatLabel,
        ReactiveFormsModule,
        AsyncPipe,
        RouterLink,
        MatCard,
        MatCardContent,
        MatCardHeader,
        MatCardTitle
    ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent implements OnInit{
  form: FormGroup;
  submitted: boolean;
  participant$: Observable<Participant>;

  constructor(
    private formBuilder: FormBuilder,
    private participantService: ParticipantService
  ) {}

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      email: ['', Validators.required, Validators.email],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required]
    });
  }

  onSubmit(): void {
    this.form.disable();
    this.submitted = true;
    let participant : Participant = this.form.value as Participant;
    this.participant$ = this.participantService.registerParticipant(participant);
  }
}
