package com.ke.switchlinkafrica.transactionprocessing.wrapper;

import lombok.Data;

@Data
public class AccountWrapper {

    private String accountNumber;
    private String transfer_accountNumber;
    private Float amount;
}
