import {Injectable} from '@angular/core';
import {User} from "../model/user";
import {environment} from "../../environments/environment";
import {Router} from "@angular/router";
import {Participant} from "../model/participant";
import {firstValueFrom} from "rxjs";
import {ParticipantService} from "./participant.service";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private userLogged: User | undefined = undefined;

  constructor(private router: Router, private participantService: ParticipantService) { }

  isLogged(): boolean {
    if(!this.userLogged && sessionStorage.getItem('user')){
      let user: User = JSON.parse(sessionStorage.getItem('user') || '') as User;
      this.userLogged = new User(user.id, user.role);
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

  private async processLogin(username: string, password: string): Promise<void> {
    this.loginOrga(username, password);
    if(!this.userLogged){
      await this.loginParticipant(username, password);
    }
  }

  private loginOrga(username: string, password: string): void{
    if(username === environment.ORGA_USER.username && password === environment.ORGA_USER.password) {
      this.userLogged = new User(0, 'ORGA');
    }
  }

  private async loginParticipant(username: string, password: string): Promise<void>{
    if(username === password ) {
      const participant: Participant|undefined = await firstValueFrom(this.participantService.findByEmail(username));

      if(participant){
        this.userLogged = new User(participant.id, 'PARTICIPANT');
      }
    }
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
