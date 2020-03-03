package com.ke.switchlinkafrica.transactionprocessing.resource;


import com.ke.switchlinkafrica.transactionprocessing.entities.AccountTransaction;
import com.ke.switchlinkafrica.transactionprocessing.entities.UserAccount;
import com.ke.switchlinkafrica.transactionprocessing.repositories.UserAccountRepository;
import com.ke.switchlinkafrica.transactionprocessing.services.AccountTransactionService;
import com.ke.switchlinkafrica.transactionprocessing.wrapper.AccountWrapper;
import ke.axle.chassis.wrappers.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;


@RestController
public class UserAccountController{

    private final UserAccountRepository userAccountRepository;
    private final AccountTransactionService accountTransactionService;

    private static final String DEPOSIT = "Deposit";
    private static final String TRANSFER = "Transfer";
    private static final String WITHDRAW = "Withdraw";

    public UserAccountController(UserAccountRepository userAccountRepository,AccountTransactionService accountTransactionService) {
        this.userAccountRepository = userAccountRepository;
        this.accountTransactionService = accountTransactionService;
    }

    /**
     *
     * @param accountWrapper
     * @return balance
     */
    @PostMapping(value = "/check_balance")
    public ResponseEntity<ResponseWrapper> checkBalance(@RequestBody AccountWrapper accountWrapper) {
        ResponseWrapper wrapper =new ResponseWrapper();
        UserAccount userAccount = userAccountRepository.findByAccountNumber(accountWrapper.getAccountNumber());
        if(Objects.isNull(userAccount)){
            wrapper.setMessage("Account With that account number does not exist");
            return new ResponseEntity<>(wrapper, HttpStatus.OK);
        }

        wrapper.setData(userAccount.getAmount());
        wrapper.setMessage("Request was successful");
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    /**
     * Depositing money into an atm
     * @param accountWrapper
     * @return
     */
    @PostMapping(value = "/deposit")
    public ResponseEntity<ResponseWrapper> deposit(@RequestBody AccountWrapper accountWrapper) {
        ResponseWrapper wrapper =new ResponseWrapper();
        UserAccount userAccount = userAccountRepository.findByAccountNumber(accountWrapper.getAccountNumber());
        if(Objects.isNull(userAccount)){
            wrapper.setMessage("Account With that account number does not exist");
            return new ResponseEntity<>(wrapper, HttpStatus.OK);
        }
        Float amount = userAccount.getAmount() + accountWrapper.getAmount();
        userAccount.setAmount(amount);

        accountTransactionService.saveTransaction(new AccountTransaction(DEPOSIT,accountWrapper.getAccountNumber(),"Depositing Money",accountWrapper.getAmount(),userAccount.getUserID()));
        wrapper.setData(userAccountRepository.save(userAccount));
        wrapper.setMessage("Money Deposited Successfully");
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    /**
     * Transfer money to other people
     * @param accountWrapper
     * @return
     */
    @PostMapping(value = "/transfer")
    public ResponseEntity<ResponseWrapper> transfer(@RequestBody AccountWrapper accountWrapper) {
        ResponseWrapper wrapper =new ResponseWrapper();
        UserAccount userAccount = userAccountRepository.findByAccountNumber(accountWrapper.getAccountNumber());
        if(Objects.isNull(userAccount)){
            wrapper.setMessage("Account With that account number does not exist");
            return new ResponseEntity<>(wrapper, HttpStatus.OK);
        }

        if (userAccount.getAmount() < accountWrapper.getAmount()) {
            wrapper.setMessage("Amount in your account is less than the amount you want to transfer ");
            return new ResponseEntity<>(wrapper, HttpStatus.OK);
        }

        //crediting the other user account
        UserAccount userAccount2 = userAccountRepository.findByAccountNumber(accountWrapper.getTransfer_accountNumber());
        if(Objects.isNull(userAccount2)){
            wrapper.setMessage("Account To Transfer With that account number does not exist");
            return new ResponseEntity<>(wrapper, HttpStatus.OK);
        }
        Float amount = userAccount2.getAmount() + accountWrapper.getAmount();
        userAccount2.setAmount(amount);
        userAccountRepository.save(userAccount2);

        //debit the user transferring money
        float amount1 = userAccount.getAmount() - accountWrapper.getAmount();
        userAccount.setAmount(amount1);
        userAccountRepository.save(userAccount);

        accountTransactionService.saveTransaction(new AccountTransaction(TRANSFER,accountWrapper.getAccountNumber(),"Transferring  Money",
                accountWrapper.getAmount(),accountWrapper.getTransfer_accountNumber(),userAccount.getUserID()));
        wrapper.setData("Amount transfered successfully");

        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    /**
     * Withdraw from an atm
     * @param accountWrapper
     * @return
     */
    @PostMapping(value = "/withdraw")
    public ResponseEntity<ResponseWrapper> withdraw(@RequestBody AccountWrapper accountWrapper) {
        ResponseWrapper wrapper =new ResponseWrapper();
        UserAccount userAccount = userAccountRepository.findByAccountNumber(accountWrapper.getAccountNumber());
        if(Objects.isNull(userAccount)){
            wrapper.setMessage("Account With that account number does not exist");
            return new ResponseEntity<>(wrapper, HttpStatus.OK);
        }

        if (userAccount.getAmount() < accountWrapper.getAmount()) {
            wrapper.setMessage("Insufficent funds in your account ");
            return new ResponseEntity<>(wrapper, HttpStatus.OK);
        }

        Float amount = userAccount.getAmount() - Math.abs(accountWrapper.getAmount());
        userAccount.setAmount(amount);

        accountTransactionService.saveTransaction(new AccountTransaction(WITHDRAW,accountWrapper.getAccountNumber(),"Withdrawing  Money",accountWrapper.getAmount(),userAccount.getUserID()));
        wrapper.setMessage("Withdraw amount successfully:" + accountWrapper.getAmount());
        wrapper.setData(userAccountRepository.save(userAccount));

        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }
}
