package com.abm.mainet.asset.rest.ui.controller;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.asset.rest.dto.AssetInformationExternalDTO;
import com.abm.mainet.asset.rest.dto.AssetPostingInformationExternalDTO;
import com.abm.mainet.asset.rest.dto.AssetSpecificationExternalDTO;
import com.abm.mainet.asset.service.IAssetInformationService;
import com.abm.mainet.common.integration.asset.dto.AssetDetailsDTO;
import com.abm.mainet.common.integration.asset.dto.AssetInformationDTO;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.utility.CommonMasterUtility;

@RestController
@RequestMapping("/asset")
public class AssetsDetailController {

 private static final Logger LOGGER = LoggerFactory.getLogger(AssetsDetailController.class);

@Autowired
private IAssetInformationService iAssetInformationService;	


@Autowired
private TbFinancialyearService financialyearService;

	
	
@RequestMapping(value = "/getAssetDetail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
public  ResponseEntity<?> getAssetsDetail(@RequestParam("assetCode") String assetCode,@RequestParam("orgId") Long orgId) {
	 ResponseEntity<?> responseEntity = null;
	 AssetInformationExternalDTO assInfoExDto=null;
	 AssetPostingInformationExternalDTO assetsPosting=null;
	 AssetSpecificationExternalDTO assetSpecific=null;
	 AssetDetailsDTO dto=new AssetDetailsDTO();
	 AssetInformationDTO assDto=new AssetInformationDTO();
	 dto.setOrgId(orgId);
	 assDto.setAstCode(assetCode);
	 dto.setAssetInformationDTO(assDto);
	try {
		assInfoExDto=new AssetInformationExternalDTO();
		assetsPosting=new AssetPostingInformationExternalDTO();
		assetSpecific=new AssetSpecificationExternalDTO();
	   AssetDetailsDTO detail = iAssetInformationService.getDetailsByOrgIdAndAssetCode(dto);
	   if(detail!=null) {
		  BeanUtils.copyProperties(detail.getAssetInformationDTO(), assInfoExDto);
		  BeanUtils.copyProperties(detail.getAssetInformationDTO().getAssetSpecificationDTO(), assetSpecific);
		  BeanUtils.copyProperties(detail.getAssetInformationDTO().getAssetPostingInfoDTO(), assetsPosting);
		  if(detail.getAssetInformationDTO().getAssetPostingInfoDTO().getAcquisitionYear()!=null) {
		   TbFinancialyear financialyear = financialyearService.findYearById(detail.getAssetInformationDTO().getAssetPostingInfoDTO().getAcquisitionYear(), orgId);
				  if(financialyear!=null) {
					  String fianacial = financialyear.getFaFromDate().toString().substring(0, 4);
					  assetsPosting.setAcquisitionYear(fianacial + "-" + financialyearService
								.findYearById(detail.getAssetInformationDTO().getAssetPostingInfoDTO().getAcquisitionYear(), orgId).getFaToDate().toString().substring(2, 4));
				  }
		  }
		  assInfoExDto.setAssetSpecificationDTO(assetSpecific);
		  assInfoExDto.setAssetPostingInfoDTO(assetsPosting);
		  assInfoExDto.setAssetClass1(CommonMasterUtility.findLookUpDesc("ASC", orgId, detail.getAssetInformationDTO().getAssetClass1()));
		  assInfoExDto.setAssetClass2(CommonMasterUtility.findLookUpDesc("ACL", orgId, detail.getAssetInformationDTO().getAssetClass2()));
		  assInfoExDto.setAcquisitionMethod(CommonMasterUtility.findLookUpDesc("AQM", orgId, detail.getAssetInformationDTO().getAcquisitionMethod()));
		  assInfoExDto.setAssetStatus(CommonMasterUtility.findLookUpDesc("AST", orgId, detail.getAssetInformationDTO().getAssetStatus()));
		  responseEntity=ResponseEntity.status(HttpStatus.OK).body(assInfoExDto);
		  
		  LOGGER.info("Connection Detail in json formate:"+new ObjectMapper().writeValueAsString(detail.getAssetInformationDTO()));	
	  }else {
		  responseEntity=ResponseEntity.status(HttpStatus.OK).body("No Data found againt given asset code");
		  LOGGER.info("No Data Available Against connection No: "+assetCode);
	  }
	
	}catch(Exception ex) {
		  responseEntity=ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
		  LOGGER.error("Error While Fetching connection detail: " + ex.getMessage(), ex);	
		
	}
	return responseEntity;
  }	
}
