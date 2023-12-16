package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import java.time.LocalDate;
import java.util.List;

import com.safadana.AvazehRetailManagement.SharedLibrary.Enums.ChequeEventTypes;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Table(name = "cheques")
@Data
public class ChequeModel {
    @Id
    private int id;

    @Column(length = 50, nullable = false)
    private String drawer;

    @Column(length = 50, nullable = false)
    private String orderer;

    @Column(nullable = false)
    private long payAmount;

    @Column(length = 100)
    private String about;

    @Column(nullable = false)
    private LocalDate issueDate;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(length = 50, nullable = false)
    private String bankName;

    @Column(length = 25)
    private String serialNumber;

    @Column(length = 20)
    private String identifier; // Sayyaad Code

    @Column(columnDefinition="TEXT")
    private String descriptions;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "chequeId")
    private List<ChequeEventModel> events;

    @Transient
    private String payAmountInPersian;

    public ChequeEventModel lastEvent() {
        return (events == null || events.size() == 0) ? null : events.get(events.size() - 1);
    }

    public String lastEventString() {
        return events == null || events.size() == 0 ? ChequeEventTypes.None.toString()
                : lastEvent().getEventTypeString();
    }

    public String lastEventText() {
        return lastEvent() == null ? "" : lastEvent().getEventText();
    }
}
