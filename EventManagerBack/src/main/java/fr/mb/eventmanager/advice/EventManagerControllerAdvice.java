package fr.mb.eventmanager.advice;


import fr.mb.eventmanager.exception.EventManagerAppException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EventManagerControllerAdvice {

    @ExceptionHandler(EventManagerAppException.class)
    public ResponseEntity<String> handleAppException(EventManagerAppException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
