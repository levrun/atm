package ru.simplewebapp.model;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "OPERATIONS")
public class Operation {

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "opt_type")
    private Type operationCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    public Operation() {
    }

    public Operation(Type operationCode, Account account, LocalDateTime dateTime, Integer amount) {
        this.operationCode = operationCode;
        this.account = account;
        this.dateTime = dateTime;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + id +
                ", operationCode=" + operationCode +
                ", account=" + account +
                ", dateTime=" + dateTime +
                ", amount=" + amount +
                '}';
    }
}
