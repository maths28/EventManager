import {Injectable} from '@angular/core';
import {Router} from "@angular/router";
import {HttpErrorResponse} from "@angular/common/http";
import {ErrorResponse} from "../model/errorResponse";

@Injectable({
  providedIn: 'root'
})
export class ErrorService {

  public errorResponse: ErrorResponse;

  constructor(private router: Router) { }

  handleError(rawErrorResponse: HttpErrorResponse|string){
    if(rawErrorResponse instanceof HttpErrorResponse){
      const rawErrorContent = rawErrorResponse.error;
      this.errorResponse = new ErrorResponse(rawErrorContent.resumeErrorMessage, rawErrorContent.detailedErrorMessages)
    } else {
      this.errorResponse = new ErrorResponse(rawErrorResponse);
    }
    this.router.navigateByUrl("/error");
  }
}
