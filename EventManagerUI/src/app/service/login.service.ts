import {Injectable} from '@angular/core';
import {User} from "../model/user";
import {environment} from "../../environments/environment";
import {Router} from "@angular/router";
import {firstValueFrom} from "rxjs";
import {Participant} from "../model/participant";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private userLogged: User | undefined = undefined;

  constructor(
    private router: Router,
    private httpClient: HttpClient
  ) { }

  isLogged(): boolean {
    if(!this.userLogged && sessionStorage.getItem('user')){
      let user: User = JSON.parse(sessionStorage.getItem('user') || '') as User;
      if(user.role == 'ORGA'){
        this.userLogged = new User();
      } else {
        this.userLogged = new Participant();
      }
      Object.assign(this.userLogged, user);
    }
    return this.userLogged != undefined;
  }

  async login(username: string, password: string) : Promise<boolean>{
    await this.processLogin(username, password);
    if(this.userLogged){
      sessionStorage.setItem('user', JSON.stringify(this.userLogged));
      this.router.navigate( ['/']);
    } else {
      this.userLogged = undefined;
      sessionStorage.removeItem('user');
    }
    return this.userLogged != undefined;
  }

  private async processLogin(username: string, password: string): Promise<void>{
    const user: User|undefined = await firstValueFrom(
      this.httpClient.get<User>(`${environment.BASE_URL}login`,{
        headers: {
          Authorization: 'Basic ' + btoa(`${username}:${password}`)
        }
      })
    ).catch((err) => undefined);

    if(user){
      if(user.role == 'ORGA'){
        this.userLogged = new User();
      } else {
        this.userLogged = new Participant();
      }
      Object.assign(this.userLogged, user);
    }
  }

  logout() : void{
    this.httpClient.post<null>(`${environment.BASE_URL}logout`, null)
      .subscribe((response)=> {
        this.userLogged = undefined;
        sessionStorage.removeItem('user');
        this.router.navigate( ['/login']);
      })

  }

  getUser(): User | undefined{
    return this.userLogged;
  }
}
