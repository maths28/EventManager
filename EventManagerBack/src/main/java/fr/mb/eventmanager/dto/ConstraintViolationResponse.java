package fr.mb.eventmanager.dto;

import lombok.Data;

import java.util.List;
@Data
public class ConstraintViolationResponse {
    private String introduceMessage;
    private List<String> constraintViolationMessages;
}
