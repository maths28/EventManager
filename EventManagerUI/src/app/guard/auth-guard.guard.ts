import {CanActivateFn, Router} from '@angular/router';
import {LoginService} from "../service/login.service";
import {inject} from "@angular/core";

export const authGuardGuard: CanActivateFn = (route, state) => {
  const loginService: LoginService = inject(LoginService);
  const router : Router = inject(Router);
  if(!loginService.isLogged()) {
    router.navigate(["/login"]);
    return false;
  }
  return true;
};
