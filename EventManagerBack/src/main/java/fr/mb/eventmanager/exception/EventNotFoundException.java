package fr.mb.eventmanager.exception;

public class EventNotFoundException extends EventManagerAppException{

    public EventNotFoundException(int id) {
        super("L'évènement avec l'id " + id + " n'existe pas");
    }
}
