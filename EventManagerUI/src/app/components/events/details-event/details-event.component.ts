import {Component, Input, OnInit} from '@angular/core';
import {Event} from "../../../model/event";
import {EventService} from "../../../service/event.service";
import {Observable, tap} from "rxjs";
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
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell, MatHeaderCellDef,
  MatHeaderRow,
  MatHeaderRowDef,
  MatRow,
  MatRowDef,
  MatTable
} from "@angular/material/table";
import {MatPaginator, PageEvent as PaginatorEvent} from "@angular/material/paginator";
import {MatProgressSpinner} from "@angular/material/progress-spinner";
import {PageParticipant} from "../../../model/participant";
import {Router, RouterLink} from "@angular/router";

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
    RouterLink,
    MatCell,
    MatCellDef,
    MatColumnDef,
    MatHeaderCell,
    MatHeaderRow,
    MatHeaderRowDef,
    MatPaginator,
    MatProgressSpinner,
    MatRow,
    MatRowDef,
    MatTable,
    MatHeaderCellDef
  ],
  providers: [EventService],
  templateUrl: './details-event.component.html',
  styleUrl: './details-event.component.css'
})
export class DetailsEventComponent implements OnInit{

  event$: Observable<Event>;
  @Input() id: number;
  displayedColumns: string[] = ['firstName', 'lastName', 'email'];
  participants$: Observable<PageParticipant>;
  pageIndex: number = 0;
  pageSize: number = 10;
  totalElements: number;

  constructor(
    private eventService: EventService,
    private router: Router,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.event$ = this.eventService.getEvent(this.id);
    this.loadParticipants();
  }

  loadParticipants() {
    this.participants$ = this.eventService.getParticipants(this.id, this.pageSize, this.pageIndex)
      .pipe(tap((participantPage) => this.totalElements = participantPage.totalElements));
  }

  deleteEvent(event: Event): void {
    const dialogRef = this.dialog.open(DeleteEventComponent, {
      data: event
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result) this.eventService.deleteEvent(event.id).subscribe(()=> this.router.navigateByUrl("/"));
    });
  }

  onPaginatorChange(paginatorEvent: PaginatorEvent)  {
    this.pageSize = paginatorEvent.pageSize;
    this.pageIndex = paginatorEvent.pageIndex;
    this.loadParticipants();
  }

}
