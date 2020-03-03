package com.ke.switchlinkafrica.transactionprocessing.repositories;


import com.ke.switchlinkafrica.transactionprocessing.entities.AccountTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, BigInteger> {

    Page<AccountTransaction> findAll(Pageable pg);

    Page<AccountTransaction> findAllByAccountNumber(String accountNumber,Pageable pg);

    Page<AccountTransaction> findAllByUserID(Long userId,Pageable pg);
}
