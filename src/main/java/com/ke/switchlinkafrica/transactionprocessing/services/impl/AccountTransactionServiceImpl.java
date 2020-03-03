package com.ke.switchlinkafrica.transactionprocessing.services.impl;

import com.ke.switchlinkafrica.transactionprocessing.entities.AccountTransaction;
import com.ke.switchlinkafrica.transactionprocessing.repositories.AccountTransactionRepository;
import com.ke.switchlinkafrica.transactionprocessing.services.AccountTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AccountTransactionServiceImpl implements AccountTransactionService {

    @Autowired
    private AccountTransactionRepository transactionRepository;

    /**
     * save transaction
     * @param accountTransaction
     */
    @Override
    public void saveTransaction(AccountTransaction accountTransaction) {
        transactionRepository.save(accountTransaction);
    }

    /**
     * get all transactions
     * @param pg
     * @return
     */
    @Override
    public Page<AccountTransaction> getAll(Pageable pg) {
        return transactionRepository.findAll(pg);
    }

    /**
     * get all customer transactions filter by account number
     * @param accountNumber
     * @param pg
     * @return
     */
    @Override
    public Page<AccountTransaction> getCustomerTrns(String accountNumber,Pageable pg) {
        return transactionRepository.findAllByAccountNumber(accountNumber, pg);
    }

    /**
     * get all customer transactions filter by userId
     * @param userId
     * @param pg
     * @return
     */
    @Override
    public Page<AccountTransaction> getCustomerTrnsByUserId(Long userId, Pageable pg) {
        return transactionRepository.findAllByUserID(userId, pg);
    }
}
