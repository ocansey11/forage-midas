package com.jpmc.midascore.entity;
import jakarta.persistence.*;

@Entity
public class TransactionRecord {

    @Id
    @GeneratedValue()
    private long id;

    @ManyToOne
    private UserRecord recipient;

    @ManyToOne
    private UserRecord sender;

    private float amount;


    protected TransactionRecord() {
    }

    public TransactionRecord(UserRecord sender, UserRecord recipient, float amount) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
    }

    @Override

    public String toString() {
        return "Transaction from " + sender.getName() + ", to " + recipient.getName() + ", amount=" + amount ;
    }

}
