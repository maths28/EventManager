import {Component, OnInit} from '@angular/core';
import {ListEventComponent} from "../events/list-event/list-event.component";
import {EventListType} from "../enum/EventListType";
import {LoginService} from "../../service/login.service";

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    ListEventComponent
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit{
  protected readonly EventListType = EventListType;
  role: string;
  constructor(private loginService: LoginService) {
  }

  ngOnInit(): void {
    this.role = this.loginService.getUser()!.role;
  }

}
