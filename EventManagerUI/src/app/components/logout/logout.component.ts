import {Component, OnInit} from '@angular/core';
import {LoginService} from "../../service/login.service";

@Component({
  selector: 'app-logout',
  standalone: true,
  template: '',
  imports: []
})
export class LogoutComponent implements OnInit{
  constructor(private loginService: LoginService) {
  }

  ngOnInit() {
    this.loginService.logout();
  }

}
