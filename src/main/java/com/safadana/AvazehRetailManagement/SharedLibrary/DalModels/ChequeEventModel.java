package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import com.safadana.AvazehRetailManagement.SharedLibrary.Enums.ChequeEventTypes;

import lombok.Data;

@Data
public class ChequeEventModel {
    private int ChequeId;
    private String EventDate;
    private ChequeEventTypes EventType;
    private String EventText;

    public String getEventTypeString() {
        return EventType.toString();
    }

    public void setEventTypeString(String eventTypeString) {
        try {
            this.EventType = ChequeEventTypes.valueOf(eventTypeString);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public int getEventTypeValue() {
        return this.EventType.getValue();
    }

    public void setEventTypeValue(int eventTypeValue) {
        if (eventTypeValue >= 0 && eventTypeValue < ChequeEventTypes.values().length) {
            this.EventType = ChequeEventTypes.values()[eventTypeValue];
        } else {
            System.err.println("Invalid eventTypeValue: " + eventTypeValue);
        }
    }
}
