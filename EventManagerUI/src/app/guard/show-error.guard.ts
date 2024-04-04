import {CanActivateFn, Router} from '@angular/router';
import {ErrorService} from "../service/error.service";
import {inject} from "@angular/core";

export const showErrorGuard: CanActivateFn = (route, state) => {
  const errorService: ErrorService = inject(ErrorService);
  const router : Router = inject(Router);
  if(!errorService.errorResponse){
      router.navigateByUrl("/");
      return false;
  }
  return true;
};
