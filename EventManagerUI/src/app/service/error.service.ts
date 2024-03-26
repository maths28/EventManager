import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {Router} from "@angular/router";
import {HttpErrorResponse} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ErrorService {

  private errorSource: BehaviorSubject<HttpErrorResponse | null> = new BehaviorSubject<HttpErrorResponse | null>(null);
  error$: Observable<HttpErrorResponse|null> = this.errorSource.asObservable();

  constructor(private router: Router) { }

  handleError(error: HttpErrorResponse){
    this.errorSource.next(error);
    this.router.navigateByUrl("/error");
  }
}
