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
@Table(name = "user_account")
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private BigInteger id;

    @Column(name = "USER_ID")
    private Long userID;

    @JoinColumn(name = "USER_ID",insertable = false,updatable = false,referencedColumnName = "ID")
    @OneToOne
    private UserData userIDs;

    @Column(name = "AMOUNT")
    private Float amount;

    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;

    @Column(name = "INTRASH")
    private String intrash;

    @Column(name = "ACTION")
    private String action;

    @Column(name = "CREATION_DATE",insertable = false,updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
}
