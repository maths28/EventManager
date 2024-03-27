import {Component, Inject} from '@angular/core';
import {MatButton} from "@angular/material/button";
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogTitle
} from "@angular/material/dialog";
import {Event} from "../../../model/event";

@Component({
  selector: 'app-register-to-event',
  standalone: true,
  imports: [
    MatButton,
    MatDialogActions,
    MatDialogContent,
    MatDialogTitle,
    MatDialogClose
  ],
  templateUrl: './register-to-event.component.html',
  styleUrl: './register-to-event.component.css'
})
export class RegisterToEventComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: Event) {
  }

}
