package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;


@RestController
//@PreAuthorize("isAuthenticated()")
public class TenmoController {

    private final UserDao userDao;
    private final TransferDao transferDao;
    private final AccountDao accountDao;

    public TenmoController(UserDao userDao, TransferDao transferDao, AccountDao accountDao){
        this.userDao = userDao;
        this.transferDao = transferDao;
        this.accountDao = accountDao;
    }

    //find all users
    @RequestMapping (path = "/user", method = RequestMethod.GET) // "value" is used in authentication controller and not "path"
        public List<User> listOfUsers(){
            return userDao.findAll();
        }

    //getting individual balance
    @RequestMapping(path = "/account/{id}", method = RequestMethod.GET)
        public BigDecimal accountBalance(@PathVariable("id") Long accountId) { // I dont like how I used the reference type Long, might change it
        return accountDao.getBalance(accountId);
    }

    //getting transfer history
    @RequestMapping(path ="/transfer", method = RequestMethod.GET)
    public List<Transfer> getTransferHistory(Principal user){
        if (user == null){
            throw new RuntimeException();
        }
        String userName = user.getName();
        int userId = userDao.findIdByUsername(userName);
        Long accountId = accountDao.getAccountIdFromUserId(userId);
        return transferDao.getTransferHistory(accountId);
    }
    //I feel like there could be some ambiguity with accountId or maybe I'm just confused like usual lol

    // POST new transfer
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/transfer", method = RequestMethod.POST)
    public String sendNewTransfer(BigDecimal amount, Principal user, String recipientUser){
        if (user == null){
            throw new RuntimeException("goobye");
        }
        String userName = user.getName();
        int userId = userDao.findIdByUsername(userName);
        Long accountFrom = accountDao.getAccountIdFromUserId(userId);
        if (recipientUser == null){
            throw new RuntimeException("hello");
        }
        int recipientId = userDao.findIdByUsername(recipientUser);
        Long accountTo = accountDao.getAccountIdFromUserId(recipientId);
        int transferId = transferDao.send(amount, accountFrom, accountTo);
        return "Success! Your transfer id is: " + transferId;
    }

    // GET individual transfer details
    @RequestMapping(path = "/transfer/{id}", method = RequestMethod.GET)
    public Transfer SingleTransferDetails(Principal user, @PathVariable("id") Long transferId){
        // need to write code to use the JWT
        return transferDao.getSingleTransferDetails(transferId);
    }
}
