package com.abm.mainet.workManagement.rest.ui.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.abm.mainet.workManagement.rest.dto.ViewWorkDefinitionDto;
import com.abm.mainet.workManagement.service.WorkDefinitionService;

@RestController
@RequestMapping("/viewWorksDefination")
public class ViewWorksDefinationController {
	
	
    private static final Logger LOGGER = LoggerFactory.getLogger(ViewWorksDefinationController.class);

	@Autowired
	WorkDefinitionService workDefinitionService;
	
    @RequestMapping(value = "/viewWorksDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getViewWorksDefination(@RequestParam("worksCode") String worksCode,@RequestParam("orgId") Long orgId) {
    	 ResponseEntity<?> responseEntity = null;
    	 ViewWorkDefinitionDto workDefDto=null;
    	try {
        	 workDefDto = workDefinitionService.findAllWorkDefinitionByWorkCodeNo(worksCode, orgId);
        	 if(workDefDto!=null) {
        		 responseEntity= ResponseEntity.status(HttpStatus.OK).body(workDefDto);
         		
                 LOGGER.info("Connection Detail in json formate:"+new ObjectMapper().writeValueAsString(workDefDto));		

        	 }else {
        		 responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND)
                         .body("No Data Available Against connection No :"+worksCode);
         		LOGGER.info("No Data Available Against connection No: "+worksCode);
        	 }
        	 

    	}catch (Exception ex) {
    		responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Server Error");
    		LOGGER.error("Error While Fetching connection detail: " + ex.getMessage(), ex);	
		}
		return responseEntity;
		
		
	}
	
}
