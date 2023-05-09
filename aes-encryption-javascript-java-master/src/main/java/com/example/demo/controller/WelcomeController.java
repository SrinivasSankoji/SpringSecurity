package com.example.demo.controller;

import com.example.demo.model.Credentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class WelcomeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(WelcomeController.class);
	
	private String decryptedPassword="";

	@RequestMapping(value={"/login"},method = RequestMethod.GET)
    public String loginPage(HttpServletRequest request){
		LOGGER.info("Received request for login page with id - " + request.getSession().getId());
		String randomKey = UUID.randomUUID().toString();
		//String uniqueKey = randomKey.substring(randomKey.length()-17, randomKey.length() -1);
        String uniqueKey = "1234567891234567";
	    request.getSession().setAttribute("key", uniqueKey);
	    return "index";
    }

    @RequestMapping(value={"/login"},method = RequestMethod.POST)
    public @ResponseBody ResponseEntity  login(@RequestBody Credentials credentials) {
    	String decryptedPassword =  new String(java.util.Base64.getDecoder().decode(credentials.getPassword()));
        AesUtil aesUtil = new AesUtil(128, 1000);
        System.out.println("Decrypted Password :" +decryptedPassword);
        Map map = new HashMap<>();
        if (decryptedPassword != null && decryptedPassword.split("::").length == 3) {
            LOGGER.info("Password decrypted successfully for username - " + credentials.getUserName());
            String password = aesUtil.decrypt(decryptedPassword.split("::")[1], decryptedPassword.split("::")[0], "1234567891234567", decryptedPassword.split("::")[2]);
            map.put("password", password);
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
