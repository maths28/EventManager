package fr.mb.eventmanager.advice;


import fr.mb.eventmanager.constant.ConstraintViolationMessageConstants;
import fr.mb.eventmanager.dto.ErrorResponse;
import fr.mb.eventmanager.dto.event.EventCreateOrUpdateRequest;
import fr.mb.eventmanager.dto.participant.ParticipantCreateRequest;
import fr.mb.eventmanager.exception.EventManagerAppException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Comparator;

@ControllerAdvice
public class EventManagerControllerAdvice {

    @ExceptionHandler(EventManagerAppException.class)
    public ResponseEntity<ErrorResponse> handleAppException(EventManagerAppException exception){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setResumeErrorMessage(exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleConstraintException(MethodArgumentNotValidException exception){
        ErrorResponse errorResponse = new ErrorResponse();

        BindingResult bindingResult = exception.getBindingResult();
        errorResponse.setResumeErrorMessage(
                this.getIntroduceConstraintViolationMessage(
                        bindingResult.getTarget(),
                        bindingResult.getErrorCount() > 1
                )
        );
        errorResponse.setDetailedErrorMessages(
                bindingResult.getAllErrors().stream()
                    .sorted(Comparator.comparing(this::getPath))
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).toList()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private String getPath(ObjectError objectError){
        String path = objectError.getObjectName();
        if(objectError instanceof FieldError fieldError){
            path+="." + fieldError.getField();
        }
        return path;
    }

    private <T> String getIntroduceConstraintViolationMessage(T object, boolean pluralize){
        if(object instanceof EventCreateOrUpdateRequest){
            return pluralize ?
                    ConstraintViolationMessageConstants.INTRODUCE_MANY_EVENT_CONSTRAINT_VIOLATION_MESSAGE :
                    ConstraintViolationMessageConstants.INTRODUCE_EVENT_CONSTRAINT_VIOLATION_MESSAGE;
        }
        if(object instanceof ParticipantCreateRequest){
            return pluralize ?
                    ConstraintViolationMessageConstants.INTRODUCE_MANY_PARTICIPANT_CONSTRAINT_VIOLATION_MESSAGE :
                    ConstraintViolationMessageConstants.INTRODUCE_PARTICIPANT_CONSTRAINT_VIOLATION_MESSAGE;
        }
        return "";
    }
}
