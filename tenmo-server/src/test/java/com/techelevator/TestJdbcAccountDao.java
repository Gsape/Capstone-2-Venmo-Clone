package com.techelevator;


import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;


import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class TestJdbcAccountDao {
    @Autowired
    private DataSource dataSource;
    private JdbcAccountDao sut;
    private JdbcTemplate jdbcTemplate;
    private Account testAccount1 = new Account((long)2001,(long)1001, new BigDecimal(1000));
    private Account testAccount2 = new Account((long)2002,(long)1002, new BigDecimal(1000));


    @Before
    public void setup(){
        jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcAccountDao(jdbcTemplate);
//        testAccount1 = new Account((long)2001,(long)1001, new BigDecimal(1000));
//        testAccount2 = new Account((long)2002,(long)1002, new BigDecimal(1000));
    }

    @Test
    public void testReturnAllAccounts(){

        List<Account> accounts = sut.findAll();

        Assert.assertEquals(2, accounts.size());

        assertAccountsMatch(testAccount1, accounts.get(0));
        assertAccountsMatch(testAccount2, accounts.get(1));
    }

    @Test
    public void correctAccountInfoReturned() {
        Account testAccountInfo = sut.getAccountInfo(2001);
        long testId = testAccountInfo.getAccountId();

    }



    public void assertAccountsMatch(Account expected, Account actual){
        Assert.assertEquals(expected.getAccountId(), actual.getAccountId());
        Assert.assertEquals(expected.getUserId(), actual.getUserId());
        Assert.assertEquals(expected.getBalance(), actual.getBalance());
    }

    @After
    public void rollback() throws SQLException {
        dataSource.getConnection().rollback();
    }
}
