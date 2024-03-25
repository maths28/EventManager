import {Component} from '@angular/core';
import {Event} from "../../../model/event";
import {EventsFormComponent} from "../form/events-form.component";

@Component({
  selector: 'app-add-event',
  standalone: true,
  imports: [
    EventsFormComponent
  ],
  templateUrl: './add-event.component.html',
  styleUrl: './add-event.component.css',
})
export class AddEventComponent {

    event: Event = new Event();

}
