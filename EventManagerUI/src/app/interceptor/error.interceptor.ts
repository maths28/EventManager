import {HttpErrorResponse, HttpInterceptorFn} from '@angular/common/http';
import {catchError, filter, throwError} from "rxjs";
import {inject} from "@angular/core";
import {ErrorService} from "../service/error.service";
import {EventType, NavigationEnd, Router} from "@angular/router";

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const errorService: ErrorService = inject(ErrorService);
  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      console.error('An error occurred:', error);
      errorService.handleError(error);
      return throwError(() => new Error(error.message));
    })
  );
};
