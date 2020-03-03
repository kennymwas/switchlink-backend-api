package com.ke.switchlinkafrica.transactionprocessing.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ACCOUNT_EXPIRED")
    private int accountExpired;

    @Column(name = "ACCOUNT_LOCKED")
    private int accountLocked;

    @Column(name = "CREDENTIALS_EXPIRED")
    private int credentialsExpired;

    @Column(name = "ENABLED")
    private int enabled;

    @Column(name = "ROLE")
    private String role;


    @Transient
    private String accountNumber;

    @Transient
    private Float amount;


}
