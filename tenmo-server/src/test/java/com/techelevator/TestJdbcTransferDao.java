package com.techelevator;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Account;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import javax.sql.DataSource;

import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.sql.SQLException;

//@RunWith(SpringRunner.class)
public class TestJdbcTransferDao {
    @Autowired
    private DataSource dataSource;
    private JdbcTransferDao sut;
    private JdbcTemplate jdbcTemplate;
    private JdbcUserDao userDao;
    private JdbcAccountDao accountDao;
    long test1ID;
    long test2ID;

    @Before
    public void setup(){
        jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcTransferDao(jdbcTemplate);
        userDao.create("test1", "happy");
        userDao.create("test2", "sad");
        test1ID = userDao.findIdByUsername("test1");
        test2ID = userDao.findIdByUsername("test2");
    }

    @Test
    public void testDepositHappyPath(){
        // arrange
            // BigDecimal object instantiated to what the method should return
            BigDecimal expected = BigDecimal.valueOf(1010);
        // act
            // deposit method
            BigDecimal actual = sut.deposit(test1ID, BigDecimal.valueOf(10.00));
        // assert
            // comparing BigDec to result of the method
        Assert.assertEquals(expected, actual);
    }

    @After
    public void rollback() throws SQLException {
        dataSource.getConnection().rollback();
    }
}
