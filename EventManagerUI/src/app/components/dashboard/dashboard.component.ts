import { Component } from '@angular/core';
import {ListComponent} from "../events/list/list.component";

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    ListComponent
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {

}
