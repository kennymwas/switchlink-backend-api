package com.ke.switchlinkafrica.transactionprocessing.services;

import com.ke.switchlinkafrica.transactionprocessing.entities.AccountTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountTransactionService {

    void saveTransaction(AccountTransaction accountTransaction);

    public Page<AccountTransaction> getAll(Pageable pg);

    public Page<AccountTransaction> getCustomerTrns(String accountNumber,Pageable pg);

    public Page<AccountTransaction> getCustomerTrnsByUserId(Long userId,Pageable pg);

}
