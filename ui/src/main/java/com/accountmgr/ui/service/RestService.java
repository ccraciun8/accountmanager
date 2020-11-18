package com.accountmgr.ui.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.accountmgr.ui.model.Account;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RestService {
	// the constants below define the container name and port of the account mgr backend service
	private static final String kAccountMgrServiceUserName = "app-accountmanager";
	private static final String kAccountMgrServicePort = "8081";
	
    private final RestTemplate restTemplate;

    private String getAccountMgrUrl() {
    	return "http://" + kAccountMgrServiceUserName + ":" + kAccountMgrServicePort + "/accounts";
    }
    
    public RestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }
    
    // Retrieve all accounts that belong to the current user.
    public List<Account> getAccounts() {
    	ObjectMapper mapper = new ObjectMapper();
    	// The username will be passed as a Query Parameter
    	String url = getAccountMgrUrl() + "?username=" + getUsername();
        String jsonStr = this.restTemplate.getForObject(url, String.class);
        
        List<Account> accounts = new ArrayList<Account>();
        try {
        	// Deserialize the GET response to a list of Account.
        	accounts = mapper.readValue(jsonStr, new TypeReference<List<Account>>(){});
        }
        catch(Exception ex) {
        	System.out.println("Caught error while parsing Account list: " + ex.getMessage());
        }
        

        return accounts;
    }

    // Add a new account in the list. 
    public void addAccount(Account account) throws Exception{
    	
    	// The account mgr service requires the balance and customerName as part of the request body
    	ObjectMapper mapper = new ObjectMapper();
    	HashMap<String, Object> map = new HashMap<String,Object>();
    	map.put("balance", account.getBalance());
    	map.put("customerName", getUsername());
    	try {
			mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			throw new Exception("Failed to add a new account. Error: " + e.getMessage());
		}
    	
    	// Add the account using the 'postForObject' method. 
    	// Any error that is received from the POST request will be shown in the UI
    	try{
    		this.restTemplate.postForObject(getAccountMgrUrl(), map, HashMap.class, new HashMap<String, Object>());
    	}
    	catch(RestClientException ex) {
    		throw new Exception(ex.getMessage());
    	}
    }
    
    // Get the username from the Security Context
    private String getUsername() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();	
    }
    
}