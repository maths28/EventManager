import {Injectable} from '@angular/core';
import {User} from "../model/user";
import {environment} from "../../environments/environment";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private userLogged: User | undefined = undefined;

  constructor(private router: Router) { }

  isLogged(): boolean {
    if(!this.userLogged && sessionStorage.getItem('user')){
      this.userLogged = JSON.parse(sessionStorage.getItem('user') || '') as User;
    }
    return this.userLogged != undefined;
  }

  login(username: string, password: string) : boolean{
    if(username === environment.ORGA_USER.username && password === environment.ORGA_USER.password){
      this.userLogged = new User(0, 'ORGA');
      sessionStorage.setItem('user', JSON.stringify(this.userLogged));
      this.router.navigate( ['/events']);
    } else {
      this.userLogged = undefined;
      sessionStorage.removeItem('user');
    }
    return this.userLogged != undefined;
  }

  logout() : void{
    this.userLogged = undefined;
    sessionStorage.removeItem('user');
    this.router.navigate( ['/']);
  }

  getUser(): User | undefined{
    return this.userLogged;
  }
}
