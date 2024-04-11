import {Component, OnInit} from '@angular/core';
import {MatToolbar} from "@angular/material/toolbar";
import {MatButton} from "@angular/material/button";
import {EventType, Router, RouterLink, RouterOutlet} from "@angular/router";
import {LoginService} from "../../service/login.service";

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [
    MatToolbar,
    MatButton,
    RouterLink,
    RouterOutlet
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit{

  isLogged: boolean;
  constructor(private loginService: LoginService, private router: Router) {
  }

  ngOnInit() {
    this.router.events.subscribe((value)=> {
        if(value.type == EventType.NavigationEnd) {
          this.isLogged = this.loginService.isLogged();
        }
    });
  }


}
