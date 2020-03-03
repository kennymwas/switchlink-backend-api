package com.ke.switchlinkafrica.transactionprocessing.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account_transaction")
public class AccountTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private BigInteger id;

    @Column(name = "TRANSACTION_TYPE")
    private String transactionType;

    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "AMOUNT")
    private Float amount;

    @Column(name = "TRANSFER_ACCOUNT_NUMBER")
    private String transferAccountNumber;

    @Column(name = "CREATION_DATE",insertable = false,updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column(name = "USER_ID")
    private Long userID;

    @JoinColumn(name = "USER_ID",insertable = false,updatable = false,referencedColumnName = "ID")
    @OneToOne
    private UserData userIDs;

    public AccountTransaction(String transactionType, String accountNumber, String description,Long userID) {
        this.transactionType = transactionType;
        this.accountNumber = accountNumber;
        this.description = description;
        this.userID = userID;
    }

    public AccountTransaction(String transactionType, String accountNumber, String description,Float amount,Long userID) {
        this.transactionType = transactionType;
        this.accountNumber = accountNumber;
        this.description = description;
        this.amount = amount;
        this.userID = userID;
    }

    public AccountTransaction(String transactionType, String accountNumber, String description, Float amount, String transferAccountNumber,Long userID) {
        this.transactionType = transactionType;
        this.accountNumber = accountNumber;
        this.description = description;
        this.amount = amount;
        this.transferAccountNumber = transferAccountNumber;
        this.userID = userID;
    }
}
