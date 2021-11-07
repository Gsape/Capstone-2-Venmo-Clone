package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Scanner;

public class TransferService {

    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    //there is not what we know as a "console service" because the app
    // is handling the cli somehow so I'm not sure what to do
    private AuthenticatedUser currentUser;
    private String authToken = null;

    public TransferService(String url) {
        baseUrl = url;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    //get all users
    public User[] listUsers() {
        User[] users = null;
        try {
            ResponseEntity<User[]> response = restTemplate.exchange(baseUrl + "user", HttpMethod.GET,
                    makeAuthEntity(), User[].class);
            users = response.getBody();
        } catch (RestClientResponseException e) {
            System.out.println(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException ex) {
            System.out.println(ex.getMessage());
        }
        return users;
    }

    public BigDecimal balance() {
        System.out.println();
        BigDecimal balance = null;
        try {
            ResponseEntity<BigDecimal> response = restTemplate.exchange(baseUrl + "account", HttpMethod.GET,
                    makeAuthEntity(), BigDecimal.class);
            balance = response.getBody();
        }catch (RestClientResponseException e) {
            System.out.println(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException ex) {
            System.out.println(ex.getMessage());
        }
        return balance;
    }

    private User[] viewTransferHistory() {
        User[] users = null;
        try {
            ResponseEntity<User[]> response = restTemplate.exchange(baseUrl + "user", HttpMethod.GET,
                    makeAuthEntity(), User[].class);
            users = response.getBody();
        } catch (RestClientResponseException e) {
            System.out.println(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException ex) {
            System.out.println(ex.getMessage());
        }
        return users;

    }
    public void sendBucks() {
        Transfer transfer = new Transfer();
        System.out.println("Enter ID of user you are sending to (0 to cancel): ");
        Scanner scanner = new Scanner(System.in);
        transfer.setAccountTo(Long.parseLong(scanner.nextLine()));

        if(transfer.getAccountTo() != 0){
            System.out.println("Amount to send: ");
            try {
                transfer.setAmount(new BigDecimal(Double.parseDouble(scanner.nextLine())));
            } catch (NumberFormatException e) {
                System.out.println("ERROR");
            }
            String response = restTemplate.exchange(baseUrl + "transfer", HttpMethod.POST, makeTransferEntity(transfer), String.class).getBody();
            System.out.println(response);
        }
    }

    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
        return entity;
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }
}
