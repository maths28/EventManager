import {Injectable} from '@angular/core';
import {Router} from "@angular/router";
import {HttpErrorResponse} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ErrorService {

  public errorMessage: string;

  constructor(private router: Router) { }

  handleError(error: HttpErrorResponse|string){
    if(error instanceof HttpErrorResponse){
      this.errorMessage = error.error;
    } else {
      this.errorMessage = error;
    }
    this.router.navigateByUrl("/error");
  }
}
