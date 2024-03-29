import {Component, Inject, OnInit} from '@angular/core';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent, MatDialogRef,
  MatDialogTitle
} from "@angular/material/dialog";
import {ActionForEventDialogData, ActionForEventDialogTemplateData} from "./ActionForEventDialogData";
import {MatButton} from "@angular/material/button";
import {ActionForEventType} from "../../enum/ActionForEventType";
import {Observable} from "rxjs";
import {EventService} from "../../../service/event.service";
import {ParticipantService} from "../../../service/participant.service";

@Component({
  selector: 'app-action-for-event-dialog',
  standalone: true,
  imports: [
    MatButton,
    MatDialogActions,
    MatDialogContent,
    MatDialogTitle,
    MatDialogClose
  ],
  templateUrl: './action-for-event-dialog.component.html',
  styleUrl: './action-for-event-dialog.component.css'
})
export class ActionForEventDialogComponent implements OnInit{

  title: string;
  question: string;
  constructor(
    private dialogRef: MatDialogRef<ActionForEventDialogComponent>,
    @Inject(MAT_DIALOG_DATA) private data: ActionForEventDialogData,
    private eventService: EventService,
    private participantService: ParticipantService
  ) {}

  ngOnInit(): void {
    let templateData : ActionForEventDialogTemplateData =
      ActionForEventDialogTemplateData.getConstantsActionForEventDialog(this.data.actionForEventType);
    this.title = templateData.titleDialog;
    this.question = templateData.questionDialog.replaceAll("[eventName]", this.data.event.name);
  }

  onClose(response: boolean)
  {
    this.dialogRef.close();
    if(response){
      let actionCall: Observable<any>;
      switch (this.data.actionForEventType){
        case ActionForEventType.DELETE_EVENT:
          actionCall = this.eventService.deleteEvent(this.data.event.id);
          break;
        case ActionForEventType.REGISTER_TO_EVENT:
          actionCall = this.participantService.registerParticipantToEvent(this.data.user!, this.data.event);
          break;
        case ActionForEventType.UNREGISTER_TO_EVENT:
          actionCall = this.participantService.unregisterParticipantToEvent(this.data.user!, this.data.event);
          break;
      }
      actionCall.subscribe(()=> {
        (this.data.callback && this.data.callback()) || (this.eventService.applyNeedToUpdateList());
      });
    }
  }
}
