import {Component, OnInit} from '@angular/core';
import {ErrorService} from "../../service/error.service";
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
import {RouterLink} from "@angular/router";
import {ErrorResponse} from "../../model/errorResponse";

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
    MatCardTitle,
    RouterLink
  ],
  templateUrl: './error.component.html',
  styleUrl: './error.component.css'
})
export class ErrorComponent implements OnInit{

  errorResponse: ErrorResponse;

  constructor(
    private errorService: ErrorService
  ) {}

  ngOnInit() {
    this.errorResponse = this.errorService.errorResponse;
  }
}
