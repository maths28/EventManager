import {ActionForEventType} from "../../enum/ActionForEventType";
import {Event} from "../../../model/event";
import {User} from "../../../model/user";

export class ActionForEventDialogData {
  actionForEventType: ActionForEventType;
  event: Event;
  user?: User;
  callback?: () => void;

  constructor(actionForEventType: ActionForEventType, event: Event);
  constructor(actionForEventType: ActionForEventType, event: Event, user: User);
  constructor(actionForEventType: ActionForEventType, event: Event, callback: () => void);

  constructor(...args: any[]) {
    this.actionForEventType = args[0];
    this.event = args[1];
    this.user = args.length > 2 && args[2] instanceof User ? args[2] : undefined ;
    this.callback = args.length > 2 && typeof args[2] == "function" ? args[2] : undefined;
  }
}

export class ActionForEventDialogTemplateData {

  private static TITLE_DIALOG_DELETE_EVENT = "Suppression d'un évènement";
  private static TITLE_DIALOG_REGISTER_TO_EVENT = "Inscription à un évènement";
  private static TITLE_DIALOG_UNREGISTER_T0_EVENT = "Désinscription à un évènement";

  private static QUESTION_DIALOG_DELETE_EVENT = "Voulez-vous vraiment supprimer l'évènement <b>[eventName]</b> ?";
  private static QUESTION_DIALOG_REGISTER_TO_EVENT
    = "Voulez-vous vraiment vous inscrire à l'évènement <b>[eventName]</b> ?";
  private static QUESTION_DIALOG_UNREGISTER_TO_EVENT
    = "Voulez-vous vraiment vous désinscrire à l'évènement <b>[eventName]</b> ?"

  titleDialog: string;
  questionDialog: string;

  static getConstantsActionForEventDialog(actionType: ActionForEventType){
    let instance = new ActionForEventDialogTemplateData();
    switch (actionType){
      case ActionForEventType.DELETE_EVENT:
        instance.titleDialog = ActionForEventDialogTemplateData.TITLE_DIALOG_DELETE_EVENT;
        instance.questionDialog = ActionForEventDialogTemplateData.QUESTION_DIALOG_DELETE_EVENT;
        break;
      case ActionForEventType.REGISTER_TO_EVENT:
        instance.titleDialog = ActionForEventDialogTemplateData.TITLE_DIALOG_REGISTER_TO_EVENT;
        instance.questionDialog = ActionForEventDialogTemplateData.QUESTION_DIALOG_REGISTER_TO_EVENT;
        break;
      case ActionForEventType.UNREGISTER_TO_EVENT:
        instance.titleDialog = ActionForEventDialogTemplateData.TITLE_DIALOG_UNREGISTER_T0_EVENT;
        instance.questionDialog = ActionForEventDialogTemplateData.QUESTION_DIALOG_UNREGISTER_TO_EVENT;
        break;
    }
    return instance;
  }

}
