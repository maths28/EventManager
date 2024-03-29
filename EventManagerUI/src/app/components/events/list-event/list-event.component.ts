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
import {MatButtonModule, MatIconButton} from "@angular/material/button";
import {RouterLink} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput, MatInputModule} from "@angular/material/input";
import {FormsModule} from "@angular/forms";
import {debounceTime, distinctUntilChanged, Observable, Subject, tap} from "rxjs";
import {LoginService} from "../../../service/login.service";
import {ParticipantService} from "../../../service/participant.service";
import {EventListType} from "../../../enum/EventListType";
import {ActionForEventDialogData} from "../../dialog/action-for-event-dialog/ActionForEventDialogData";
import {ActionForEventType} from "../../../enum/ActionForEventType";
import {ActionForEventDialogComponent} from "../../dialog/action-for-event-dialog/action-for-event-dialog.component";
import {MatExpansionPanel, MatExpansionPanelHeader, MatExpansionPanelTitle} from "@angular/material/expansion";
import {MatIcon} from "@angular/material/icon";
import {MatToolbar} from "@angular/material/toolbar";

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
    MatButtonModule,
    RouterLink,
    MatFormField,
    MatInputModule,
    MatLabel,
    FormsModule,
    MatExpansionPanelTitle,
    MatExpansionPanelHeader,
    MatExpansionPanel,
    MatIconButton,
    MatIcon,
    MatToolbar
  ],
  templateUrl: './list-event.component.html',
  styleUrl: './list-event.component.css'
})
export class ListEventComponent implements OnInit{

  events$ : Observable<PageEvent>;
  private commonDisplayedColumns: string[] = ['name', 'description', 'startDate', 'endDate', 'location', 'maxCapacity'];
  displayedColumns: string[];
  pageIndex: number = 0;
  pageSize: number = 5;
  location: string = "";
  private locationSearchTerms: Subject<string> = new Subject<string>();
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
    }
    this.eventService.initNeedToUpdateEventList();
    this.eventService.needToUpdateEventList$.subscribe(()=>this.loadPage());
    if(this.typeList == EventListType.ALL_EVENTS){
      this.locationSearchTerms.pipe(
        // {...."ab"..."abz"."ab"...."abc"......}
        debounceTime(300),
        // {......"ab"...."ab"...."abc"......}
        distinctUntilChanged(),
      ).subscribe((location) => {
        this.location = location;
        this.loadPage();
      });
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

  searchByLocation(location: string){
    this.locationSearchTerms.next(location);
  }

  clearSearchByLocation(input: HTMLInputElement): void {
    input.value = "";
    this.searchByLocation("");
  }

  deleteEvent(event: Event): void {
    let dialogData : ActionForEventDialogData =
      new ActionForEventDialogData(ActionForEventType.DELETE_EVENT, event);
    this.dialog.open(ActionForEventDialogComponent, {
      data: dialogData
    });
  }

  registerToEvent(event: Event): void {
    let dialogData : ActionForEventDialogData =
      new ActionForEventDialogData(ActionForEventType.REGISTER_TO_EVENT, event, this.loginService.getUser()!);
    this.dialog.open(ActionForEventDialogComponent, {
      data: dialogData
    });
  }

  unregisterToEvent(event: Event): void {
    let dialogData : ActionForEventDialogData =
      new ActionForEventDialogData(ActionForEventType.UNREGISTER_TO_EVENT, event, this.loginService.getUser()!);
    this.dialog.open(ActionForEventDialogComponent, {
      data: dialogData
    });
  }

  protected readonly EventListType = EventListType;
}
