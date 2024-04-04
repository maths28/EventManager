package fr.mb.eventmanager.exception;

public class ParticipantEmailAlreadyExistsException extends EventManagerAppException{

    public ParticipantEmailAlreadyExistsException(String email) {
        super("Le mail " + email + " est déjà utilisé");
    }
}
