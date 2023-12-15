package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import java.util.List;

import com.safadana.AvazehRetailManagement.SharedLibrary.Enums.ChequeEventTypes;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "cheques")
@Data
public class ChequeModel {
    @Id
    private int id;

    private String drawer;
    private String orderer;
    private long payAmount;
    private String about;
    private String issueDate;
    private String dueDate;
    private String bankName;
    private String serial;
    private String identifier; // Sayyaad Code
    private String descriptions;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "chequeId")
    private List<ChequeEventModel> events;
    private String payAmountInPersian;

    public ChequeEventModel lastEvent() {
        return (events == null || events.size() == 0) ? null : events.get(events.size() - 1);
    }

    public String lastEventString() {
        return events == null || events.size() == 0 ? ChequeEventTypes.None.toString() : lastEvent().getEventTypeString();
    }

    public String lastEventText() {
        return lastEvent() == null ? "" : lastEvent().getEventText();
    }
}
