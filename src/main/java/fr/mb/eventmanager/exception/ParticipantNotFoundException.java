package fr.mb.eventmanager.exception;

public class ParticipantNotFoundException extends EventManagerAppException{

    public ParticipantNotFoundException(int id) {
        super("Le participant avec l'id " + id + " n'existe pas");
    }
}
