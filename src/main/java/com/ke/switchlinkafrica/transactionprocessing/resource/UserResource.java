package com.ke.switchlinkafrica.transactionprocessing.resource;

import com.ke.switchlinkafrica.transactionprocessing.entities.AccountTransaction;
import com.ke.switchlinkafrica.transactionprocessing.entities.UserAccount;
import com.ke.switchlinkafrica.transactionprocessing.entities.UserData;
import com.ke.switchlinkafrica.transactionprocessing.repositories.UserAccountRepository;
import com.ke.switchlinkafrica.transactionprocessing.services.AccountTransactionService;
import com.ke.switchlinkafrica.transactionprocessing.services.UserService;
import ke.axle.chassis.utils.AppConstants;
import ke.axle.chassis.wrappers.ResponseWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.List;
import java.util.Objects;

@RestController
public class UserResource {

    private final UserService userService;
    private final UserAccountRepository accountRepository;
    private final AccountTransactionService accountTransactionService;

    private static final String DEPOSIT = "Deposit";
    private static final String TRANSFER = "Transfer";
    private static final String WITHDRAW = "Withdraw";

    public UserResource(UserService userService,UserAccountRepository accountRepository,AccountTransactionService accountTransactionService) {

        this.userService = userService;
        this.accountRepository = accountRepository;
        this.accountTransactionService = accountTransactionService;
    }

    @RequestMapping(value = "/user",method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<ResponseWrapper> createUser(@RequestBody UserData userData) {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        userData.setPassword("{bcrypt}$2a$04$I9Q2sDc4QGGg5WNTLmsz0.fvGv3OjoZyj81PrSFyGOqMphqfS2qKu");
        userData.setRole(userData.getRole());
        userData.setAccountExpired(0);
        userData.setAccountLocked(0);
        userData.setCredentialsExpired(0);
        userData.setEnabled(1);
        UserData user = userService.save(userData);
        //save account details
        UserAccount userAccount = accountRepository.findByAccountNumber(userData.getAccountNumber());
        if(Objects.nonNull(userAccount)){
            responseWrapper.setMessage("Account Already Tied To A Customer");
            responseWrapper.setCode(HttpStatus.CONFLICT.value());
            return ResponseEntity.ok(responseWrapper);
        }
        UserAccount userAcc = new UserAccount();
        userAcc.setAmount(userData.getAmount());
        userAcc.setAccountNumber(userData.getAccountNumber());
        userAcc.setIntrash(AppConstants.NO);
        userAcc.setAction(AppConstants.ACTIVITY_CREATE);
        userAcc.setUserID(user.getId());
        accountRepository.save(userAcc);

        //save transaction
        accountTransactionService.saveTransaction(new AccountTransaction(DEPOSIT,userData.getAccountNumber(),"Depositing Money",user.getId()));


        responseWrapper.setData(user);
        responseWrapper.setCode(201);
        responseWrapper.setMessage("User Created Successfully");
        return ResponseEntity.ok(responseWrapper);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseWrapper> delete(@PathVariable(value = "id") Long id){
        ResponseWrapper responseWrapper = new ResponseWrapper();
        userService.delete(id);
        responseWrapper.setMessage("User Deleted Successfully");
        responseWrapper.setCode(200);
        return ResponseEntity.ok(responseWrapper);
    }

    @RequestMapping(value="/user", method = RequestMethod.GET)
    public ResponseEntity<ResponseWrapper> getAllUsers(Pageable pg){
        ResponseWrapper wrapper =new ResponseWrapper();
        wrapper.setData(userService.findAll(pg));
        wrapper.setMessage("Request was successful");
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/me")
    public ResponseEntity<ResponseWrapper> user(Principal principal) {
        ResponseWrapper wrapper = new ResponseWrapper();
        wrapper.setMessage("Logged in user ");
        wrapper.setData(userService.getUser(principal.getName(),1));
        return ResponseEntity.ok(wrapper);
    }
}
