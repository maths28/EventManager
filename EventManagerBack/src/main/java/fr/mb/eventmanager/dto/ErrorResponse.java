package fr.mb.eventmanager.dto;

import lombok.Data;

import java.util.List;
@Data
public class ErrorResponse {
    private String resumeErrorMessage;
    private List<String> detailedErrorMessages;
}

