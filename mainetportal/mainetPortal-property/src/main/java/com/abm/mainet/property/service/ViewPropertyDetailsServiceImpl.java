package com.abm.mainet.property.service;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.ws.ResponseWrapper;

import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.GridSearchDTO;
import com.abm.mainet.common.dto.PagingDTO;
import com.abm.mainet.common.dto.TbBillMas;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.property.dto.ProperySearchDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.SelfAssessmentFinancialDTO;
import com.abm.mainet.property.dto.ViewPropertyDetailRequestDto;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

@Service
public class ViewPropertyDetailsServiceImpl implements IViewPropertyDetailsService{

	@Override
	public List<ProperySearchDto> searchPropertyDetails(ProperySearchDto searchDto) {
		List<ProperySearchDto> dto = new ArrayList<>();
		ViewPropertyDetailRequestDto viewRequestDto=new ViewPropertyDetailRequestDto();
		viewRequestDto.setPropSearchDto(searchDto);
		/*viewRequestDto.setPagingDTO(pagingDTO);
		viewRequestDto.setGridSearchDTO(gridSearchDTO);*/
		 final ArrayList<LinkedHashMap<Long, Object>> responseVo = (ArrayList<LinkedHashMap<Long, Object>>) JersyCall.callRestTemplateClient(viewRequestDto,
				 ApplicationSession.getInstance().getMessage("SEARCH_PROPERTY_DETAILS"));
  
	        try {
	        	for(LinkedHashMap<Long, Object> obj:responseVo) {
	        		final String d = new JSONObject(obj).toString();
	        		ProperySearchDto propSearchDto= new ObjectMapper().readValue(d,
	        				ProperySearchDto.class);
	        		dto.add(propSearchDto);
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
		
		return dto;
	}
	
	@Override
	public int getTotalSearchCount(ProperySearchDto searchDto, PagingDTO pagingDTO,
			GridSearchDTO gridSearchDTO) {
		ViewPropertyDetailRequestDto viewRequestDto=new ViewPropertyDetailRequestDto();
		viewRequestDto.setPropSearchDto(searchDto);
		viewRequestDto.setPagingDTO(pagingDTO);
		viewRequestDto.setGridSearchDTO(gridSearchDTO);
		final int count = (int) JersyCall.callRestTemplateClient(viewRequestDto,
				 ApplicationSession.getInstance().getMessage("GET_TOTAL_SEARCH_COUNT"));
      
        return count;

	
	
	}	
	
	@Override
	public ProvisionalAssesmentMstDto fetchPropertyByPropNo(ProperySearchDto request) {
		
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(request,
				ApplicationSession.getInstance().getMessage("VIEW_ASSESSMENT_MAS"));
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
            e.printStackTrace();
        }
        return null;
	}

	@Override
	public List<LookUp> getCollectionDetails(ProperySearchDto searchDto) {
		
		List<LookUp> lookup = new ArrayList<>();
		 final ArrayList<LinkedHashMap<Long, Object>> responseVo = (ArrayList<LinkedHashMap<Long, Object>>) JersyCall.callRestTemplateClient(searchDto,
				 ApplicationSession.getInstance().getMessage("VIEW_PAYMENT_HISTORY"));
 
	        try {
	        	for(LinkedHashMap<Long, Object> obj:responseVo) {
	        		final String d = new JSONObject(obj).toString();
	        		LookUp l= new ObjectMapper().readValue(d,
	        				LookUp.class);
	        		lookup.add(l);
	        	}
           } 
	        catch (JsonParseException e)
	        {
	        	throw new FrameworkException(e);
	        } catch (JsonMappingException e)
	        {
	        	throw new FrameworkException(e);
	        } catch (IOException e) 
	        {
	        	throw new FrameworkException(e);
	        }
		
		return lookup;
	}

	@Override
	public List<TbBillMas> getViewData(ProperySearchDto dto) {
		List<TbBillMas> bill = new ArrayList<>();
		Organisation org = new Organisation();
	    org.setOrgid(dto.getOrgId());
		
		 final ArrayList<LinkedHashMap<Long, Object>> responseVo = (ArrayList<LinkedHashMap<Long, Object>>) JersyCall.callRestTemplateClient(dto,
				 ApplicationSession.getInstance().getMessage("VIEW_BILL_HISTORY"));
 
	        try {
	        	for(LinkedHashMap<Long, Object> obj:responseVo) {
	        		final String d = new JSONObject(obj).toString();
	        		TbBillMas b = new ObjectMapper().readValue(d,
	        				TbBillMas.class);
	        		bill.add(b);
	        	}
        } catch (JsonParseException e) {
        	throw new FrameworkException(e);
	        } catch (JsonMappingException e) {
	        	throw new FrameworkException(e);
	        } catch (IOException e) {
	        	throw new FrameworkException(e);
	        }
		
		return bill;
	}
	
	@Override
	public Map<Long, List<DocumentDetailsVO>> fetchApplicaionDocuments(ProperySearchDto searchDto) {
		
		return null;
	}
	

	@Override
	public ProperySearchDto getAndGenearteJasperForBill(ProperySearchDto propDto) {
		
		ProperySearchDto Dto =new ProperySearchDto();
		    
		final LinkedHashMap<Long, Object>  responseVo= (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(propDto,
				ApplicationSession.getInstance().getMessage("GENERTE_JASPER_FOR_BILL"));
        final String d = new JSONObject(responseVo).toString();

        try {
            return new ObjectMapper().readValue(d,
            		ProperySearchDto.class);
        } catch (JsonParseException e) {
        	 throw new FrameworkException(e);
        } catch (JsonMappingException e) {
        	 throw new FrameworkException(e);
        } catch (IOException e) {
        	e.printStackTrace();
        }
        return null;
	}
	
	
	
	@Override
	public ProperySearchDto getAndGenearteJasperForReceipt(ProperySearchDto propDto) {
		
		ProperySearchDto Dto =new ProperySearchDto();
		    
		final LinkedHashMap<Long, Object>  responseVo= (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(propDto,
				ApplicationSession.getInstance().getMessage("GENERTE_JASPER_FOR_RECEIPT"));
        final String d = new JSONObject(responseVo).toString();

        try {
            return new ObjectMapper().readValue(d,
            		ProperySearchDto.class);
        } catch (JsonParseException e) 
        {
        	 throw new FrameworkException(e);
        }
        catch (JsonMappingException e)
        {
        	 throw new FrameworkException(e);
        } catch (IOException e)
        {
        	e.printStackTrace();
        }
        return null;
	}

	@Override
	public ChallanReceiptPrintDTO getRevenueReceiptDetails(Long recptId, Long receiptNo, String assNo) {
		ProperySearchDto requestDto =new ProperySearchDto();
		requestDto.setRecptId(recptId);
		requestDto.setRecptNo(receiptNo);
		requestDto.setProertyNo(assNo);
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(requestDto,
				ApplicationSession.getInstance().getMessage("DOWNLOAD_REVENUE_RECEIPT"));
        final String d = new JSONObject(responseVo).toString();
        try {
            return new ObjectMapper().readValue(d,
            		ChallanReceiptPrintDTO.class);
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
	
	
	
	
	
	
	

}
