package com.abm.mainet.common.rest.ui.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.common.utility.UserSession;


@RestController
@RequestMapping("/testrestcontroller")
public class TestRestController {

    
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String checkApplicationContext() throws Exception {
	String result = String.valueOf(UserSession.getCurrent().getLanguageId());
	System.out.println(result);
	return result;
        
    }
    
    
}
