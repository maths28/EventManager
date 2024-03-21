package fr.mb.eventmanager.exception;

public class UserNotRegisteredForEventException extends EventManagerAppException{

    public UserNotRegisteredForEventException(int id) {
        super("L'utilisateur n'est pas inscrit à l'évènement d'id " + id);
    }
}
