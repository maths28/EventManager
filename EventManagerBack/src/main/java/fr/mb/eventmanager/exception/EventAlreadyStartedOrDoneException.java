package fr.mb.eventmanager.exception;

public class EventAlreadyStartedOrDoneException extends EventManagerAppException{

    public EventAlreadyStartedOrDoneException(int eventId) {
        super("L'évènement " + eventId + " a déjà commencé ou est fini");
    }
}
