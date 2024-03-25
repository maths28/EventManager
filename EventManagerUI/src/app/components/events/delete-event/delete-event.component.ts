import {Component, Inject} from '@angular/core';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogTitle
} from "@angular/material/dialog";
import {MatButton} from "@angular/material/button";
import {Event} from "../../../model/event";

@Component({
  selector: 'app-delete-event',
  standalone: true,
  imports: [
    MatDialogContent,
    MatDialogActions,
    MatDialogClose,
    MatButton,
    MatDialogTitle,
  ],
  templateUrl: './delete-event.component.html',
  styleUrl: './delete-event.component.css'
})
export class DeleteEventComponent {

  constructor(@Inject(MAT_DIALOG_DATA) public data: Event) {
  }

}
