package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
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
        public BigDecimal accountBalance(@PathVariable Long accountId) { // I dont like how I used the reference type Long, might change it
        return accountDao.getBalance(accountId);
    }

    //getting transfer history
    @RequestMapping(path ="/transfer", method = RequestMethod.GET)
    public List<Transfer> transferHistory(@PathVariable Long accountId){
        return transferDao.getTransferHistory(accountId);
    }
    //I feel like there could be some ambiguity with accountId or maybe I'm just confused like usual lol
}