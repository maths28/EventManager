package fr.mb.eventmanager.controller.dto;


import java.time.LocalDateTime;

public record EventResource(int id, String name, String description, LocalDateTime startDate, LocalDateTime endDate,
                            String location, int maxCapacity) {

}
