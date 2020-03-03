package com.ke.switchlinkafrica.transactionprocessing.repositories;

import com.ke.switchlinkafrica.transactionprocessing.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, BigInteger> {
    UserAccount findByAccountNumber(String accountNumber);
}
