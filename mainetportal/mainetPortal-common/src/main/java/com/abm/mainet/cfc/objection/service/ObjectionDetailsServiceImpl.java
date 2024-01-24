package com.abm.mainet.cfc.objection.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.integration.ws.JersyCall;//import com.abm.mainet.property.dto.AssessmentChargeCalDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service
public class ObjectionDetailsServiceImpl implements IObjectionDetailService{
	   //private final Logger LOGGER = Logger.getLogger(EstateBookingController.class);
	   
	    public Set<LookUp> getDepartmentList(@RequestBody ObjectionDetailsDto objDto) {
	    	Set<LookUp> lookup= new HashSet<>();
	        final ArrayList<LinkedHashMap<Long, Object>> responseVo = (ArrayList<LinkedHashMap<Long, Object>> ) JersyCall.callRestTemplateClient(objDto,
	                ApplicationSession.getInstance().getMessage(
	                        "DEPARTMENT_LIST"));
	        try {
	        	for(LinkedHashMap<Long, Object> obj:responseVo) {
	        		final String d = new JSONObject(obj).toString();
	        		LookUp l= new ObjectMapper().readValue(d,
	        				 LookUp.class);
	        		lookup.add(l);
	        	}
	            return lookup;
	        } catch (JsonParseException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (JsonMappingException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return null;
	    }
	    
	    
	    public Set<LookUp> getLocationByDepartment(@RequestBody ObjectionDetailsDto objDto) {
	    	Set<LookUp> lookup= new HashSet<>();
	        final ArrayList<LinkedHashMap<Long, Object>> responseVo = (ArrayList<LinkedHashMap<Long, Object>> ) JersyCall.callRestTemplateClient(objDto,
	                ApplicationSession.getInstance().getMessage(
	                        "LOCATION_LIST"));
	        try {
	        	for(LinkedHashMap<Long, Object> obj:responseVo) {
	        		final String d = new JSONObject(obj).toString();
	        		LookUp l= new ObjectMapper().readValue(d,
	        				 LookUp.class);
	        		lookup.add(l);
	        	}
	            return lookup;
	        } catch (JsonParseException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (JsonMappingException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return null;
	    }


		@Override
		public Set<LookUp> findALlActiveServiceByDeptId(@RequestBody ObjectionDetailsDto objDto, Long orgId) {
			Set<LookUp> lookup= new HashSet<>();
	        final ArrayList<LinkedHashMap<Long, Object>> responseVo = (ArrayList<LinkedHashMap<Long, Object>> ) JersyCall.callRestTemplateClient(objDto,
	                ApplicationSession.getInstance().getMessage(
	                        "SERVICE_BY_DEPT_ID"));
	        try {
	        	for(LinkedHashMap<Long, Object> obj:responseVo) {
	        		final String d = new JSONObject(obj).toString();
	        		LookUp l= new ObjectMapper().readValue(d,
	        				 LookUp.class);
	        		lookup.add(l);
	        	}
	            return lookup;
	        } catch (JsonParseException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (JsonMappingException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
			return null;
		}
	    
		
	    public ObjectionDetailsDto saveObjectionAndCallWorkFlow(ObjectionDetailsDto objectionDetailsDto) {
	        final LinkedHashMap<Long, Object> responseVo =(LinkedHashMap<Long, Object>)JersyCall.callRestTemplateClient(objectionDetailsDto,
	                ApplicationSession.getInstance().getMessage(
	                        "SAVE_OBJECTION"));

	        final String d = new JSONObject(responseVo).toString();
	        try {
	        	/*for(LinkedHashMap<Long, Object> obj:responseVo) {
	        		final String d = new JSONObject(obj).toString();
	        		LookUp l= new ObjectMapper().readValue(d,
	        				 LookUp.class);
	        		lookup.add(l);
	        	}*/
	        	 return new ObjectMapper().readValue(d,
	        			 ObjectionDetailsDto.class);
	        } catch (JsonParseException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (JsonMappingException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
			return null;
	    
	    }


		@Override
		@SuppressWarnings("unchecked")
		public CommonChallanDTO getCharges(ObjectionDetailsDto objDto) {
			
			final LinkedHashMap<Long, Object> responseVo =(LinkedHashMap<Long, Object>)JersyCall.callRestTemplateClient(objDto,
	                ApplicationSession.getInstance().getMessage(
	                        "OBJECTION_CHARGES"));

	        final String d = new JSONObject(responseVo).toString();
	        try {
	        	/*for(LinkedHashMap<Long, Object> obj:responseVo) {
	        		final String d = new JSONObject(obj).toString();
	        		LookUp l= new ObjectMapper().readValue(d,
	        				 LookUp.class);
	        		lookup.add(l);
	        	}*/
	        	 return new ObjectMapper().readValue(d,
	        			 CommonChallanDTO.class);
	        } catch (JsonParseException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (JsonMappingException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
			return null;
		}


		@Override
		public ObjectionDetailsDto fetchRtiAppDetailByRefNo(ObjectionDetailsDto dto) {
	        final LinkedHashMap<Long, Object> responseVo =(LinkedHashMap<Long, Object>)JersyCall.callRestTemplateClient(dto,
	                ApplicationSession.getInstance().getMessage(
	                        ServiceEndpoints.FETCH_APPLICANT_DETAILS));

	        final String d = new JSONObject(responseVo).toString();
	        try {
	        	/*for(LinkedHashMap<Long, Object> obj:responseVo) {
	        		final String d = new JSONObject(obj).toString();
	        		LookUp l= new ObjectMapper().readValue(d,
	        				 LookUp.class);
	        		lookup.add(l);
	        	}*/
	        	 return new ObjectMapper().readValue(d,
	        			 ObjectionDetailsDto.class);
	        } catch (JsonParseException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (JsonMappingException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
			return null;
	    
	    }
	    
}
