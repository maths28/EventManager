import {Component} from '@angular/core';
import {ErrorService} from "../../service/error.service";
import {Observable} from "rxjs";
import {AsyncPipe, DatePipe} from "@angular/common";
import {MatButton} from "@angular/material/button";
import {
  MatCard,
  MatCardActions,
  MatCardContent,
  MatCardHeader,
  MatCardSubtitle,
  MatCardTitle
} from "@angular/material/card";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-error',
  standalone: true,
  imports: [
    AsyncPipe,
    DatePipe,
    MatButton,
    MatCard,
    MatCardActions,
    MatCardContent,
    MatCardHeader,
    MatCardSubtitle,
    MatCardTitle
  ],
  templateUrl: './error.component.html',
  styleUrl: './error.component.css'
})
export class ErrorComponent {

  errorMessage$: Observable<HttpErrorResponse|null>;

  constructor(
    private errorService: ErrorService
  ) {}

  ngOnInit() {
    this.errorMessage$ = this.errorService.error$;
  }

  onBack(){
    window.history.go(-2);
  }
}
