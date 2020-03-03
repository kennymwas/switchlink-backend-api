package com.ke.switchlinkafrica.transactionprocessing.resource;


import com.ke.switchlinkafrica.transactionprocessing.services.AccountTransactionService;
import ke.axle.chassis.wrappers.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class AccountTransactionController {

    @Autowired
     private AccountTransactionService accountTransactionService;

    /**
     * @return all transactions
     */
    @GetMapping(value = "/all")
    public ResponseEntity<ResponseWrapper> allTransactions(Pageable pg) {
        ResponseWrapper wrapper =new ResponseWrapper();
        wrapper.setData(accountTransactionService.getAll(pg));
        wrapper.setMessage("Request was successful");
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    /**
     * @return all transactions for specific user using the account number
     */
    @GetMapping(value = "/{accNumber}/all")
    public ResponseEntity<ResponseWrapper> getCustomerTransactions(@PathVariable("accNumber") String accNumber, Pageable pg) {
        ResponseWrapper wrapper =new ResponseWrapper();
        wrapper.setData(accountTransactionService.getCustomerTrns(accNumber,pg));
        wrapper.setMessage("Request was successful");
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    /**
     * @return all transactions for specific user using user id
     */
    @GetMapping(value = "/all/{userId}")
    public ResponseEntity<ResponseWrapper> getCustTransactions(@PathVariable("userId") Long userId, Pageable pg) {
        ResponseWrapper wrapper =new ResponseWrapper();
        wrapper.setData(accountTransactionService.getCustomerTrnsByUserId(userId,pg));
        wrapper.setMessage("Request was successful");
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }
}
