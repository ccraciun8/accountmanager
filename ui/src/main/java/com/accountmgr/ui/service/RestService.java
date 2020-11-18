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
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RestService {
	
	private static final String kAccountMgrServiceUserName = "app-accountmanager";
	private static final String kAccountMgrServicePort = "8081";
	
    private final RestTemplate restTemplate;

    private String getAccountMgrUrl() {
    	return "http://" + kAccountMgrServiceUserName + ":" + kAccountMgrServicePort + "/accounts";
    }
    
    public RestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }
    
    public List<Account> getAccounts() {
    	ObjectMapper mapper = new ObjectMapper();
    	String url = getAccountMgrUrl() + "?username=" + getUsername();
        String jsonStr = this.restTemplate.getForObject(url, String.class);
        
        List<Account> accounts = new ArrayList<Account>();
        try {
        	accounts = mapper.readValue(jsonStr, new TypeReference<List<Account>>(){});
        }
        catch(Exception ex) {
        	System.out.println("Caught error while parsing Account list: " + ex.getMessage());
        }
        

        return accounts;
    }

    public void addAccount(Account account) throws Exception{
    	ObjectMapper mapper = new ObjectMapper();
    	HashMap<String, Object> map = new HashMap<String,Object>();
    	map.put("balance", account.getBalance());
    	map.put("customerName", getUsername());
    	try {
			mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			throw new Exception("Failed to add a new account. Error: " + e.getMessage());
		}
    	
    	try{
    		this.restTemplate.postForObject(getAccountMgrUrl(), map, HashMap.class, new HashMap<String, Object>());
    	}
    	catch(RestClientException ex) {
    		throw new Exception(ex.getMessage());
    	}
    }
    
    private String getUsername() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();	
    }
    
}