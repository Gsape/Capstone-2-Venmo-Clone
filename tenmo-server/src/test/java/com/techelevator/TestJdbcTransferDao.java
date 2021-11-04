package com.techelevator;

import com.techelevator.tenmo.dao.JdbcTransferDao;
import org.junit.After;
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


    @Before
    public void setup(){
        jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcTransferDao(jdbcTemplate);
    }

    @Test
    public void testDepositHappyPath(){
        // arrange


        // act
        // assert
    }

    @After
    public void rollback() throws SQLException {
        dataSource.getConnection().rollback();
    }
}
