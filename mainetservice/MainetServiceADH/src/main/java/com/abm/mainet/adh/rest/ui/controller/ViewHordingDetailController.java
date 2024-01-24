package com.abm.mainet.adh.rest.ui.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.adh.dto.HoardingMasterDto;
import com.abm.mainet.adh.rest.dto.ViewHoardingMasterDto;
import com.abm.mainet.adh.service.IHoardingMasterService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.utility.CommonMasterUtility;

/**
 * 
 * @author vishwanath.s
 *
 */
@RestController
@RequestMapping("/HoardingMaster")
public class ViewHordingDetailController {

	   private static final Logger LOGGER = Logger.getLogger(ViewHordingDetailController.class);
	
	@Autowired
	private IHoardingMasterService iHoardingMasterService;	
	
	@Autowired
	private ILocationMasService iLocationMasService;
	
	
    @RequestMapping(value = "/viewHordingDetail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> viewHordingDetail(@RequestParam("hoardingNumber") String hoardingNumber,@RequestParam("orgId") Long orgId){
    	HoardingMasterDto hordingDto=null;
    	ResponseEntity<?> response=null;
    	ViewHoardingMasterDto viewHordingDetail=null;
    	
    	try{
    		LOGGER.info("Enter into viewHordingDetail");
    	 hordingDto = iHoardingMasterService.getHoardingDetailsByOrgIdAndHoardingNo(orgId, hoardingNumber);
    	 
    	 if(hordingDto!=null) {
    		 viewHordingDetail=new ViewHoardingMasterDto();
    		 BeanUtils.copyProperties(hordingDto, viewHordingDetail);
    		 if(hordingDto.getHoardingZone1()!=null)
    		 viewHordingDetail.setHoardingZone1(CommonMasterUtility.getHierarchicalLookUp(hordingDto.getHoardingZone1(), orgId).getDescLangFirst());
    		 if(hordingDto.getHoardingZone2()!=null)
    		 viewHordingDetail.setHoardingZone2(CommonMasterUtility.getHierarchicalLookUp(hordingDto.getHoardingZone2(), orgId).getDescLangFirst());
    		 if(hordingDto.getHoardingZone3()!=null)
    		 viewHordingDetail.setHoardingZone3(CommonMasterUtility.getHierarchicalLookUp(hordingDto.getHoardingZone3(), orgId).getDescLangFirst());
    		 if(hordingDto.getHoardingZone4()!=null)
    		 viewHordingDetail.setHoardingZone4(CommonMasterUtility.getHierarchicalLookUp(hordingDto.getHoardingZone4(), orgId).getDescLangFirst());
    		 if(hordingDto.getHoardingZone5()!=null)
    		 viewHordingDetail.setHoardingZone5(CommonMasterUtility.getHierarchicalLookUp(hordingDto.getHoardingZone5(), orgId).getDescLangFirst());
    		 viewHordingDetail.setHoardingStatus(CommonMasterUtility.findLookUpDesc("HRS", orgId,hordingDto.getHoardingStatus()));
    		 viewHordingDetail.setHoardingPropertyOwnership(CommonMasterUtility.findLookUpDesc("ONT", orgId,hordingDto.getHoardingPropTypeId()));
    		 viewHordingDetail.setDisplayDirection(CommonMasterUtility.findLookUpDesc("DSP", orgId,hordingDto.getDisplayTypeId()));
    		 if(hordingDto.getHoardingTypeId1()!=null)
    		 viewHordingDetail.setHoardingTypeId1(CommonMasterUtility.getHierarchicalLookUp(hordingDto.getHoardingTypeId1(), orgId).getDescLangFirst());
    		 if(hordingDto.getHoardingTypeId2()!=null)
    		 viewHordingDetail.setHoardingTypeId2(CommonMasterUtility.getHierarchicalLookUp(hordingDto.getHoardingTypeId2(), orgId).getDescLangFirst());
    		 if(hordingDto.getHoardingTypeId3()!=null)
    		 viewHordingDetail.setHoardingTypeId3(CommonMasterUtility.getHierarchicalLookUp(hordingDto.getHoardingTypeId3(), orgId).getDescLangFirst());
    		 if(hordingDto.getHoardingTypeId4()!=null)
    		 viewHordingDetail.setHoardingTypeId4(CommonMasterUtility.getHierarchicalLookUp(hordingDto.getHoardingTypeId4(), orgId).getDescLangFirst());
    		 if(hordingDto.getHoardingTypeId5()!=null)
    		 viewHordingDetail.setHoardingTypeId5(CommonMasterUtility.getHierarchicalLookUp(hordingDto.getHoardingTypeId5(), orgId).getDescLangFirst());
    		  if(hordingDto.getHoardingFlag().equals("A"))
    			  viewHordingDetail.setHoardingFlag("Authorized");
    		  else
    			  viewHordingDetail.setHoardingFlag("Unauthorized");
    		  viewHordingDetail.setLocation(iLocationMasService.findbyLocationId(hordingDto.getLocationId()).getLocNameEng());
    		  response=ResponseEntity.status(HttpStatus.OK).body(viewHordingDetail);
    		  
    	 }else {
    		 response = ResponseEntity.status(HttpStatus.NOT_FOUND)
                     .body("No Data Available Against hoardingNumber No "+hoardingNumber);
    		 LOGGER.info("No Data Available Against hoardingNumber No "+hoardingNumber);
    	 }
		}catch(Exception ex) {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                     .body("Internal Server Error");
             LOGGER.error("Error While Fetching hording detail: " + ex.getMessage(), ex);
		}
    	
    	LOGGER.info("Exit from viewHordingDetail");
    	return response;
	}
}
