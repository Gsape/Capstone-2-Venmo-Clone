package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
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

    public Transfer[] viewTransferHistory() {
        Transfer[] history = null;
        try {
            ResponseEntity<Transfer[]> response = restTemplate.exchange(baseUrl + "transfer", HttpMethod.GET,
                    makeAuthEntity(), Transfer[].class);
            history = response.getBody();
        } catch (RestClientResponseException e) {
            System.out.println(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException ex) {
            System.out.println(ex.getMessage());
        }
        return history;
    }
    public void sendBucks(AuthenticatedUser currentUser) {
        TransferDTO sendThis = new TransferDTO();
        System.out.println("Enter a username that you would like to send to: ");
        Scanner scanner = new Scanner(System.in);
        sendThis.setToUser(scanner.nextLine());
        sendThis.setFromUser(currentUser.getUser().getUsername());
        if(sendThis.getToUser() != null){
            System.out.println("Amount of TEbucks: ");
            try {
                BigDecimal amount = new BigDecimal(Double.parseDouble(scanner.nextLine()));
                if (amount == null || amount.equals(0.00) || amount.compareTo(BigDecimal.ZERO)<0) {
                    System.out.println("ERROR");
                }
                sendThis.setAmount(amount);
            } catch (NumberFormatException e) {
                System.out.println("ERROR");
            }
            String response = restTemplate.exchange(baseUrl + "transfer", HttpMethod.POST, makeTransferEntity(sendThis), String.class).getBody();
            System.out.println(response);
        }
    }

    private HttpEntity<TransferDTO> makeTransferEntity(TransferDTO transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        HttpEntity<TransferDTO> entity = new HttpEntity<>(transfer, headers);
        return entity;
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }
}
