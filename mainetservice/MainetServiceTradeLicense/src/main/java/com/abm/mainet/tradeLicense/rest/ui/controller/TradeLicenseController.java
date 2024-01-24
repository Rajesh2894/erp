package com.abm.mainet.tradeLicense.rest.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.tradeLicense.dto.TradeLicenseItemDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeLicenseItemDetailDTOS;
import com.abm.mainet.tradeLicense.dto.TradeLicenseOwnerDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeLicenseOwnerDetailDTOS;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTOS;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;
/**
 * 
 * @author vishwanath.s
 *
 */
@RestController
@RequestMapping("/TradeLicense")
public class TradeLicenseController {
	
    private static final Logger LOGGER = Logger.getLogger(TradeLicenseController.class);

	@Resource
	private ITradeLicenseApplicationService iTradeLicenseApplicationService;
	
	
    @RequestMapping(value = "/viewLicenseDeatils", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getLicenseDetailsByLicenseNo(@RequestParam("trdLicno") String trdLicno,@RequestParam("orgId") Long orgId) {
    	 ResponseEntity<?> responseEntity = null;
    	 TradeMasterDetailDTO LicesnseDetails=null;
    	 TradeMasterDetailDTOS LicesnseDetail=new TradeMasterDetailDTOS();
    	 TradeLicenseOwnerDetailDTOS ownerDetail=null;
    	 TradeLicenseItemDetailDTOS itemDetails=null;
    	 List<TradeLicenseOwnerDetailDTOS> ownerList=new ArrayList<TradeLicenseOwnerDetailDTOS>();
    	 List<TradeLicenseItemDetailDTOS> itemList=new ArrayList<TradeLicenseItemDetailDTOS>();
  
    	
    	 
    	try {
    	    LicesnseDetails = iTradeLicenseApplicationService.getLicenseDetailsByLicenseNo(trdLicno, orgId);
    	 if (LicesnseDetails!=null) {
    		 
    		
    		 
    		 
    		 //LicesnseDetail.setTrdLictype(LicesnseDetails.getTrdLictype());
    		 LicesnseDetail.setTrdLictypeDesc(CommonMasterUtility.findLookUpDesc("LIT", orgId,LicesnseDetails.getTrdLictype()));
    		 if(LicesnseDetails.getTrdWard1()!=null)
    		 LicesnseDetail.setTrdWard1(CommonMasterUtility.getHierarchicalLookUp(LicesnseDetails.getTrdWard1(), orgId).getDescLangFirst());
    		 if(LicesnseDetails.getTrdWard2()!=null)
    		 LicesnseDetail.setTrdWard2(CommonMasterUtility.getHierarchicalLookUp(LicesnseDetails.getTrdWard2(), orgId).getDescLangFirst());
    		 if(LicesnseDetails.getTrdWard3()!=null)
    		 LicesnseDetail.setTrdWard3(CommonMasterUtility.getHierarchicalLookUp(LicesnseDetails.getTrdWard3(), orgId).getDescLangFirst());
    		 if(LicesnseDetails.getTrdWard4()!=null)
    		 LicesnseDetail.setTrdWard4(CommonMasterUtility.getHierarchicalLookUp(LicesnseDetails.getTrdWard4(), orgId).getDescLangFirst());
    		 if(LicesnseDetails.getTrdWard5()!=null)
    		 LicesnseDetail.setTrdWard5(CommonMasterUtility.getHierarchicalLookUp(LicesnseDetails.getTrdWard5(), orgId).getDescLangFirst());
    		 LicesnseDetail.setTrdOldlicno(LicesnseDetails.getTrdOldlicno());
    		 LicesnseDetail.setTrdLicfromDate(LicesnseDetails.getTrdLicfromDate());
    		 LicesnseDetail.setTrdLictoDate(LicesnseDetails.getTrdLictoDate());
    		 LicesnseDetail.setPmPropNo(LicesnseDetails.getPmPropNo());
    		 LicesnseDetail.setUsage(LicesnseDetails.getUsage());
    		 LicesnseDetail.setTrdOwnerNm(LicesnseDetails.getTrdOwnerNm());
    		 LicesnseDetail.setTotalOutsatandingAmt(LicesnseDetails.getTotalOutsatandingAmt());
    		 LicesnseDetail.setTrdFlatNo(LicesnseDetails.getTrdFlatNo());
    		 LicesnseDetail.setTrdBusnm(LicesnseDetails.getTrdBusnm());
    		 LicesnseDetail.setTrdBusadd(LicesnseDetails.getTrdBusadd());
    		 LicesnseDetail.setTrdStatus(CommonMasterUtility.findLookUpDesc("LIS", orgId,LicesnseDetails.getTrdStatus()));
    		 LicesnseDetail.setTrdLicno(LicesnseDetails.getTrdLicno());
    		 LicesnseDetail.setGstNo(LicesnseDetails.getGstNo());
    		 LicesnseDetail.setTrdLicisdate(LicesnseDetails.getTrdLicisdate());
    		 LicesnseDetail.setAgreementFromDate(LicesnseDetails.getAgreementFromDate());
    		 LicesnseDetail.setAgreementToDate(LicesnseDetails.getAgreementToDate());
    		 for(TradeLicenseOwnerDetailDTO DTO:LicesnseDetails.getTradeLicenseOwnerdetailDTO()) {
    			 ownerDetail=new TradeLicenseOwnerDetailDTOS();
    			 ownerDetail.setTroName(DTO.getTroName());
    			 ownerDetail.setTroAddress(DTO.getTroAddress());
    			 ownerDetail.setTroAdhno(DTO.getTroAdhno());
    			 ownerDetail.setTroEmailid(DTO.getTroEmailid());
    			 ownerDetail.setTroMobileno(DTO.getTroMobileno());
    			 ownerDetail.setTroPr(DTO.getTroPr());
    			 ownerDetail.setTroEducation(DTO.getTroEducation());
    			 ownerDetail.setTroCast(DTO.getTroCast());
    			 ownerDetail.setTroAge(DTO.getTroAge());
    			 ownerDetail.setTroTitle(CommonMasterUtility.findLookUpDesc("TTL", orgId,DTO.getTroTitle()));
    			 ownerDetail.setTroMname(DTO.getTroMname());
    			 ownerDetail.setTroGen(DTO.getTroGen());
    			 ownerList.add(ownerDetail);
    		 }
    		 
    				 
    		 
    		 for(TradeLicenseItemDetailDTO DTO:LicesnseDetails.getTradeLicenseItemDetailDTO()) {
    			 itemDetails=new TradeLicenseItemDetailDTOS();
    			 
    			 if(DTO.getTriCod1()!=null)
    			 itemDetails.setItemCategory1(CommonMasterUtility.getHierarchicalLookUp(DTO.getTriCod1(), orgId).getDescLangFirst());
    			 if(DTO.getTriCod2()!=null)
    			 itemDetails.setItemCategory2(CommonMasterUtility.getHierarchicalLookUp(DTO.getTriCod2(), orgId).getDescLangFirst());
    			 if(DTO.getTriCod3()!=null)
    			 itemDetails.setItemCategory3(CommonMasterUtility.getHierarchicalLookUp(DTO.getTriCod3(), orgId).getDescLangFirst());
    			 if(DTO.getTriCod4()!=null)
    			 itemDetails.setItemCategory4(CommonMasterUtility.getHierarchicalLookUp(DTO.getTriCod4(), orgId).getDescLangFirst());
    			 if(DTO.getTriCod5()!=null)
    			 itemDetails.setItemCategory5(CommonMasterUtility.getHierarchicalLookUp(DTO.getTriCod5(), orgId).getDescLangFirst());
    		    itemDetails.setTrdQuantity(DTO.getTrdQuantity());
    			 itemDetails.setTrdUnit(DTO.getTrdUnit());
    			 itemList.add(itemDetails);
    		 }
    		 
    		 LicesnseDetail.setTradeLicenseItemDetailDTO(itemList);
    		 LicesnseDetail.setTradeLicenseOwnerdetailDTO(ownerList);
    		 responseEntity = ResponseEntity.status(HttpStatus.OK).body(LicesnseDetail);
            
          } else {
        	 responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND)
                     .body("No Data Available Against License No");
          }
    }
    catch(Exception ex) {
    		 responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                     .body("Internal Server Error");
             LOGGER.error("Error While Fetching License detail: " + ex.getMessage(), ex);
    	 }
		return responseEntity;
	    }
	}


