import {Component, Input, OnInit} from '@angular/core';
import {Event} from "../../../model/event";
import {EventService} from "../../../service/event.service";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {Observable} from "rxjs";
import {AsyncPipe, DatePipe} from "@angular/common";
import {
  MatCard,
  MatCardActions,
  MatCardContent,
  MatCardHeader,
  MatCardSubtitle,
  MatCardTitle
} from "@angular/material/card";
import {MatButton} from "@angular/material/button";
import {DeleteEventComponent} from "../delete-event/delete-event.component";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-details-event',
  standalone: true,
  imports: [
    AsyncPipe,
    MatCard,
    MatCardHeader,
    MatCardContent,
    MatCardActions,
    MatCardSubtitle,
    MatCardTitle,
    DatePipe,
    MatButton,
    RouterLink
  ],
  providers: [EventService],
  templateUrl: './details-event.component.html',
  styleUrl: './details-event.component.css'
})
export class DetailsEventComponent implements OnInit{

  event$: Observable<Event>;
  @Input() id: number;

  constructor(
    private eventService: EventService,
    private router: Router,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.event$ = this.eventService.getEvent(this.id);
  }

  deleteEvent(event: Event): void {
    const dialogRef = this.dialog.open(DeleteEventComponent, {
      data: event
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result) this.eventService.deleteEvent(event.id).subscribe(()=> this.router.navigateByUrl("/events"));
    });
  }

}
