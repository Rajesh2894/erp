package com.abm.mainet.property.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.PropertyInputDto;
import com.abm.mainet.property.dto.LandTypeApiDetailRequestDto;
import com.abm.mainet.property.dto.PropertyTransferMasterDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class MutationServiceImpl implements MutationService{

	@Override
	public ProvisionalAssesmentMstDto fetchDetailForMutataion(String propNo, String oldPropNo, Long orgId) {
		ProvisionalAssesmentMstDto dto= new ProvisionalAssesmentMstDto();
		dto.setOrgId(orgId);
		dto.setAssNo(propNo);
		dto.setAssOldpropno(null);
		if (propNo == null || propNo.isEmpty()) {
			dto.setAssNo(null);
			dto.setAssOldpropno(oldPropNo);
		}
	
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(dto,
				ServiceEndpoints.PROPERTY_URL.PROPERTY_MUTATION_FETCH_DETAIL+dto.getAssNo()+"/"+dto.getAssOldpropno()+"/"+dto.getOrgId());
		if (responseVo != null) {
		final String d = new JSONObject(responseVo).toString();

        try {
            return new ObjectMapper().readValue(d,
            		ProvisionalAssesmentMstDto.class);
        } catch (IOException e) {
            // TODO Auto-generated catch block
        	throw new FrameworkException(e);
        }
		}
        return null;
    }
	@Override
	public ProvisionalAssesmentMstDto fetchMutationDetails(String propNo, String oldPropNo, String flatNo, Long orgId) {
		PropertyInputDto dto=new PropertyInputDto();
		dto.setPropertyNo(propNo);
		dto.setOldPropertyNo(oldPropNo);
		dto.setFlatNo(flatNo);
		dto.setOrgId(orgId);
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(dto,
				ServiceEndpoints.PROPERTY_URL.PROPERTY_MUTATION_FETCH_DETAIL_WITH_FLAT);
		if (responseVo != null) {
			final String d = new JSONObject(responseVo).toString();

			try {
				return new ObjectMapper().readValue(d,
						ProvisionalAssesmentMstDto.class);
			} catch (JsonParseException e) {
	            // TODO Auto-generated catch block
            	throw new FrameworkException(e);
	        } catch (JsonMappingException e) {
	            // TODO Auto-generated catch block
	        	throw new FrameworkException(e);
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	        	throw new FrameworkException(e);
	        }
		}
        return null;
	}
	@Override
	public List<DocumentDetailsVO> fetchCheckList(PropertyTransferMasterDto tranDto) {
		 List<DocumentDetailsVO> dataModel = new ArrayList<>();
		 final ArrayList<LinkedHashMap<Long, Object>> responseVo = (ArrayList<LinkedHashMap<Long, Object>>) JersyCall.callRestTemplateClient(tranDto,
				 ServiceEndpoints.PROPERTY_URL.PROPERTY_MUTATION_FETCH_CHECKLIST);
     
	        try {
	        	for(LinkedHashMap<Long, Object> obj:responseVo) {
	        		final String d = new JSONObject(obj).toString();
	        		DocumentDetailsVO l= new ObjectMapper().readValue(d,
	        				DocumentDetailsVO.class);
	        		dataModel.add(l);
	        	}
            } catch (JsonParseException e) {
	            // TODO Auto-generated catch block
            	throw new FrameworkException(e);
	        } catch (JsonMappingException e) {
	            // TODO Auto-generated catch block
	        	throw new FrameworkException(e);
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	        	throw new FrameworkException(e);
	        }
	        return dataModel;

	}

	@Override
	public PropertyTransferMasterDto fetchChargesForMuatation(PropertyTransferMasterDto tranDto) {
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(tranDto,
				ServiceEndpoints.PROPERTY_URL.PROPERTY_MUTATION_FETCH_CHARGES);
        final String d = new JSONObject(responseVo).toString();

        try {
            return new ObjectMapper().readValue(d,
            		PropertyTransferMasterDto.class);
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
        	throw new FrameworkException(e);
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
        	throw new FrameworkException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
	}

	@Override
	public PropertyTransferMasterDto saveMutation(PropertyTransferMasterDto propTransferDto) {
		  final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(propTransferDto,
				  ApplicationSession.getInstance().getMessage(
	                		ServiceEndpoints.PROPERTY_URL.PROPERTY_MUTATION_SAVE));
	       final String d = new JSONObject(responseVo).toString();

	        try {
	            return new ObjectMapper().readValue(d,
	            		PropertyTransferMasterDto.class);
	        } catch (JsonParseException e) {
	            // TODO Auto-generated catch block
	        	throw new FrameworkException(e);
	        } catch (JsonMappingException e) {
	            // TODO Auto-generated catch block
	        	throw new FrameworkException(e);
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return null;
		
	}

	@Override
	public PropertyTransferMasterDto callWorkFlowForFreeService(PropertyTransferMasterDto propTransferDto) {
		
		  final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(propTransferDto,
	                ServiceEndpoints.PROPERTY_URL.PROPERTY_MUTATION_CALL_WORKFLOW_FOR_FREESERVICE);
	     final String d = new JSONObject(responseVo).toString();
	     try {
	            return new ObjectMapper().readValue(d,
	            		PropertyTransferMasterDto.class);
	        } catch (JsonParseException e) {
	            // TODO Auto-generated catch block
	        	throw new FrameworkException(e);
	        } catch (JsonMappingException e) {
	            // TODO Auto-generated catch block
	        	throw new FrameworkException(e);
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }   
	     return null;
	}
	
	@Override
	public List<LookUp> getLocationList(Long orgId, Long deptId) {
		List<LookUp> lookup= new ArrayList<>();
        final ArrayList<LinkedHashMap<Long, Object>> responseVo = (ArrayList<LinkedHashMap<Long, Object>> ) JersyCall.callRestTemplateClient(null,
        		ServiceEndpoints.PROPERTY_URL.PROPERTY_LOCATION_LIST+orgId+"/"+deptId);
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
        	throw new FrameworkException(e);
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
        	throw new FrameworkException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		return null;
	}
	
	@Override
	public String getPropertyBillingMethod(String propNo, Long orgId) {
		final LinkedHashMap<String, String> responseVo = (LinkedHashMap<String, String>) JersyCall.callRestTemplateClient(orgId,	
				ServiceEndpoints.PROPERTY_URL.GET_BILLING_METHOD+propNo+"/orgId/"+orgId);
		final String billingMethod = responseVo.get("billingMethod");
		
		return billingMethod;
	}
	
	@Override
	public List<String> getPropertyFlatList(String propNo, String orgId) {
		final List<String> responseVo =  (List<String>) JersyCall.callRestTemplateClient(orgId,	
				ServiceEndpoints.PROPERTY_URL.GET_FLAT_LIST+propNo+"/orgId/"+orgId);
		return responseVo;
	}
}