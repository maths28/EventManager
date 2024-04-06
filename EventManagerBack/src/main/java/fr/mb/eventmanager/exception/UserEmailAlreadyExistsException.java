package fr.mb.eventmanager.exception;

public class UserEmailAlreadyExistsException extends EventManagerAppException{

    public UserEmailAlreadyExistsException(String email) {
        super("Le mail " + email + " est déjà utilisé");
    }
}
