package fr.mb.eventmanager.exception;

public abstract class EventManagerAppException extends Exception{

    protected EventManagerAppException(String message) {
        super(message);
    }
}
