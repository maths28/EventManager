import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatButton} from "@angular/material/button";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {combineLatest, Observable} from "rxjs";
import {Participant} from "../../model/participant";
import {AsyncPipe} from "@angular/common";
import {RouterLink} from "@angular/router";
import {MatCard, MatCardContent, MatCardHeader, MatCardTitle} from "@angular/material/card";
import {ErrorStateMatcher, MatOption} from "@angular/material/core";
import {EmailErrorStateMatcher} from "./emailErrorStateMatcher";
import {MatSelect} from "@angular/material/select";
import {User} from "../../model/user";
import {UserService} from "../../service/user.service";
import {MatIcon} from "@angular/material/icon";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    MatButton,
    MatFormFieldModule,
    MatInput,
    ReactiveFormsModule,
    AsyncPipe,
    RouterLink,
    MatCard,
    MatCardContent,
    MatCardHeader,
    MatCardTitle,
    MatSelect,
    MatOption,
    MatIcon
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent implements OnInit{
  form: FormGroup;
  submitted: boolean;
  user$: Observable<User>;
  matcher: ErrorStateMatcher = new EmailErrorStateMatcher();

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      role: ['', Validators.required],
      email: ['', [Validators.required, Validators.email], this.userService.existsByEmailValidator.bind(this.userService)],
      password: ['', Validators.required]
    });

    this.form.get('role')?.valueChanges.subscribe((newValue)=> this.onRoleChange(newValue));
  }

  onRoleChange(newRole: string){
    if(newRole === 'PARTICIPANT'){
      this.form.addControl('firstName', new FormControl('', Validators.required));
      this.form.addControl('lastName', new FormControl('', Validators.required))
    } else {
      this.form.removeControl('firstName');
      this.form.removeControl('lastName');
    }
  }

  onSubmit(): void {
    if(this.form.valid){
      this.form.disable({emitEvent: false});
      this.submitted = true;
      let user : User;
      if(this.form.value.role == 'ORGA'){
        user = this.form.value as User;
      } else {
        user = this.form.value as Participant;
      }
      this.user$ = this.userService.registerUser(user);
    }
  }

  asParticipant(user: User) : Participant{
    return user as Participant;
  }

}
