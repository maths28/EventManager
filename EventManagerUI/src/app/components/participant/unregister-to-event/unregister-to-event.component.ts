import {Component, Inject} from '@angular/core';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogTitle
} from "@angular/material/dialog";
import {Event} from "../../../model/event";
import {MatButton} from "@angular/material/button";

@Component({
  selector: 'app-unregister-to-event',
  standalone: true,
  imports: [
    MatButton,
    MatDialogActions,
    MatDialogContent,
    MatDialogTitle,
    MatDialogClose
  ],
  templateUrl: './unregister-to-event.component.html',
  styleUrl: './unregister-to-event.component.css'
})
export class UnregisterToEventComponent {

  constructor(@Inject(MAT_DIALOG_DATA) public data: Event) {
  }

}
