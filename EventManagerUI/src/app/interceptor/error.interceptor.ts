import {HttpErrorResponse, HttpInterceptorFn} from '@angular/common/http';
import {catchError, throwError} from "rxjs";
import {inject} from "@angular/core";
import {ErrorService} from "../service/error.service";
import {Router} from "@angular/router";
import {LoginService} from "../service/login.service";

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const errorService: ErrorService = inject(ErrorService);
  const router : Router = inject(Router);
  const loginService: LoginService = inject(LoginService);
  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      console.error('An error occurred:', error);
      if(error.status != 401 || !['/login', '/'].includes(router.url)){
        errorService.handleError(error);
      }
      if(error.status == 401 && router.url == '/'){
        loginService.logout();
      }
      return throwError(() => error);
    })
  );
};
