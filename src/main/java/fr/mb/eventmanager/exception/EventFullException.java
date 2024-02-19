package fr.mb.eventmanager.exception;

public class EventFullException extends EventManagerAppException{

    public EventFullException(int id) {
        super("L'évènement avec l'id " + id + " est déjà complet");
    }
}
