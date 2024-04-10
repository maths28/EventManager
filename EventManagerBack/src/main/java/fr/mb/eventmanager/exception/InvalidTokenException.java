package fr.mb.eventmanager.exception;

public class InvalidTokenException extends EventManagerAppException{

    public InvalidTokenException() {
        super("Invalid Token");
    }
}
