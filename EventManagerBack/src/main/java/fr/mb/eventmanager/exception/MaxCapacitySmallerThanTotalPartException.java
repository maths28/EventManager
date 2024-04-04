package fr.mb.eventmanager.exception;

public class MaxCapacitySmallerThanTotalPartException extends EventManagerAppException{
    public MaxCapacitySmallerThanTotalPartException(Integer totalParticipants) {
        super(totalParticipants + " participants sont déjà inscrits.");
    }
}
