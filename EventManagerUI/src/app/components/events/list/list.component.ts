import {Component, OnInit} from '@angular/core';
import {EventService} from "../../../service/event.service";
import {AsyncPipe, DatePipe} from "@angular/common";
import {Event, PageEvent} from "../../../model/event";
import {MatTableModule} from "@angular/material/table";
import {MatGridListModule} from "@angular/material/grid-list";
import {HeaderComponent} from "../../header/header.component";
import {MatPaginator, PageEvent as PaginatorEvent} from "@angular/material/paginator";
import {MatCardModule} from "@angular/material/card";
import {MatProgressSpinner} from "@angular/material/progress-spinner";
import {MatButton} from "@angular/material/button";
import {RouterLink} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {DeleteEventComponent} from "../delete-event/delete-event.component";

@Component({
  selector: 'app-list',
  standalone: true,
  imports: [
    AsyncPipe,
    MatTableModule,
    DatePipe,
    MatGridListModule,
    HeaderComponent,
    MatPaginator,
    MatCardModule,
    MatProgressSpinner,
    MatButton,
    RouterLink,
    DeleteEventComponent
  ],
  templateUrl: './list.component.html',
  styleUrl: './list.component.css',
  providers: [EventService]
})
export class ListComponent implements OnInit{

  events : PageEvent;
  displayedColumns: string[] = ['name', 'description', 'startDate', 'endDate', 'location', 'maxCapacity', 'actions'];
  pageIndex: number = 0;
  pageSize: number = 10;
  loading: boolean;


  constructor(
    private eventService: EventService,
    private dialog: MatDialog
  ) {}

  ngOnInit() {
    this.loadPage();
  }

  onPaginatorChange(paginatorEvent: PaginatorEvent)  {
    this.pageSize = paginatorEvent.pageSize;
    this.pageIndex = paginatorEvent.pageIndex;
    this.loadPage();
  }

  loadPage(){
    this.loading = true;
    this.eventService.getAllFutureEvents(this.pageSize, this.pageIndex+1)
      .subscribe((pageEvent) => {
        this.events = pageEvent;
        this.loading = false;
      })
  }

  deleteEvent(event: Event): void {
    const dialogRef = this.dialog.open(DeleteEventComponent, {
      data: event
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result) this.eventService.deleteEvent(event.id).subscribe(()=> this.loadPage());
    });
  }

}
