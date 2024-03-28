import {Component, Input, OnInit} from '@angular/core';
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
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {FormsModule} from "@angular/forms";
import {Observable, tap} from "rxjs";
import {LoginService} from "../../../service/login.service";
import {RegisterToEventComponent} from "../../participant/register-to-event/register-to-event.component";
import {ParticipantService} from "../../../service/participant.service";
import {EventListType} from "../../enum/EventListType";

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
    DeleteEventComponent,
    MatFormField,
    MatInput,
    MatLabel,
    FormsModule
  ],
  templateUrl: './list.component.html',
  styleUrl: './list.component.css'
})
export class ListComponent implements OnInit{

  events$ : Observable<PageEvent>;
  private commonDisplayedColumns: string[] = ['name', 'description', 'startDate', 'endDate', 'location', 'maxCapacity'];
  displayedColumns: string[];
  pageIndex: number = 0;
  pageSize: number = 5;
  location: string;
  totalElements: number;
  role: string;
  @Input() typeList: EventListType;


  constructor(
    private eventService: EventService,
    private dialog: MatDialog,
    private loginService: LoginService,
    private participantService: ParticipantService
  ) {}

  ngOnInit() {
    this.role = this.loginService.getUser()!.role;
    if(this.role === "ORGA"){
      this.displayedColumns = [...this.commonDisplayedColumns, 'actions'];
    } else if (this.role === "PARTICIPANT") {
      this.displayedColumns = [...this.commonDisplayedColumns, 'partActions'];
      this.eventService.initNotifyInscriptions();
      this.eventService.updatedInscriptions$.subscribe(()=>this.loadPage());
    }
    this.loadPage();
  }

  onPaginatorChange(paginatorEvent: PaginatorEvent)  {
    this.pageSize = paginatorEvent.pageSize;
    this.pageIndex = paginatorEvent.pageIndex;
    this.loadPage();
  }

  loadPage(){
    let events$: Observable<PageEvent>;
    if(this.typeList === EventListType.ALL_EVENTS) {
      const excludeParticipantId = this.role === "ORGA" ? undefined : this.loginService.getUser()!.userId
      events$ = this.eventService.getAllFutureEvents(this.pageSize, this.pageIndex, this.location, excludeParticipantId);
    } else {
      events$ = this.participantService.getEvents(this.loginService.getUser()!, this.pageSize, this.pageIndex)
    }

    this.events$ = events$.pipe(tap((eventPage)=> this.totalElements = eventPage.totalElements));
  }

  deleteEvent(event: Event): void {
    const dialogRef = this.dialog.open(DeleteEventComponent, {
      data: event
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result) this.eventService.deleteEvent(event.id).subscribe(()=> this.loadPage());
    });
  }

  registerToEvent(event: Event): void {
    const dialogRef = this.dialog.open(RegisterToEventComponent, {
      data: event
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result) this.participantService.registerParticipantToEvent(this.loginService.getUser()!, event)
        .subscribe(()=> this.eventService.applyNotifyInscriptions());
    });
  }

  protected readonly EventListType = EventListType;
}
