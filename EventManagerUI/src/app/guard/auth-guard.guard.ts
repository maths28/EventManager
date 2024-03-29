import {CanActivateFn, Router} from '@angular/router';
import {LoginService} from "../service/login.service";
import {inject} from "@angular/core";
import {ErrorService} from "../service/error.service";

export const authGuardGuard: CanActivateFn = (route, state) => {
  const loginService: LoginService = inject(LoginService);
  const router : Router = inject(Router);
  const errorService: ErrorService = inject(ErrorService);
  if(!loginService.isLogged()) {
    router.navigate(["/login"]);
    return false;
  } else if (state.url.startsWith("/events") && loginService.getUser()?.role != "ORGA"){
    errorService.handleError("Accès reservé à l'organisateur !");
  }
  return true;
};
