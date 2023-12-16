package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import java.time.LocalDateTime;

import com.safadana.AvazehRetailManagement.SharedLibrary.Enums.ChequeEventTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "chequeevents")
@Data
public class ChequeEventModel {
    @Id
    private int id;

    @Column(nullable = false)
    private int chequeId;

    @Column(nullable = false)
    private LocalDateTime eventDate;

    @Column(nullable = false)
    private ChequeEventTypes eventType;
    
    @Column(length = 50)
    private String eventText;

    public String getEventTypeString() {
        return eventType.toString();
    }

    public void setEventTypeString(String eventTypeString) {
        try {
            this.eventType = ChequeEventTypes.valueOf(eventTypeString);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public int getEventTypeValue() {
        return this.eventType.getValue();
    }

    public void setEventTypeValue(int eventTypeValue) {
        if (eventTypeValue >= 0 && eventTypeValue < ChequeEventTypes.values().length) {
            this.eventType = ChequeEventTypes.values()[eventTypeValue];
        } else {
            System.err.println("Invalid eventTypeValue: " + eventTypeValue);
        }
    }
}
