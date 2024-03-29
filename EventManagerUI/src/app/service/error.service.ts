import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {Router} from "@angular/router";
import {HttpErrorResponse} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ErrorService {

  public errorMessage: string;

  constructor(private router: Router) { }

  handleError(error: HttpErrorResponse){
    this.errorMessage = error.error;
    this.router.navigateByUrl("/error");
  }
}
