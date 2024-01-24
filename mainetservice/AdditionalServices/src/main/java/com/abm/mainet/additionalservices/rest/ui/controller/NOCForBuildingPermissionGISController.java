package com.abm.mainet.additionalservices.rest.ui.controller;

import java.util.List;
import org.apache.commons.collections.CollectionUtils;
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
import com.abm.mainet.additionalservices.dto.NOCForBuildingPermissionDTO;
import com.abm.mainet.additionalservices.rest.dto.NOCForBuildingPermissionExtDTO;
import com.abm.mainet.additionalservices.service.NOCForBuildingPermissionService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.utility.CommonMasterUtility;
/**
 * 
 * @author vishwanath.s
 *
 */
@RestController
@RequestMapping("/BuildingPermission")
public class NOCForBuildingPermissionGISController {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(NOCForBuildingPermissionGISController.class);
	
	@Autowired
	private NOCForBuildingPermissionService nOCForBuildingPermissionService;
	
	@Autowired
	private ILocationMasService iLocationMasService;
	
	
	@RequestMapping(value="/GetNocBuildingPermissionDetail",method= {RequestMethod.GET},produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> getNOCForBuilddingPermisionDetail(@RequestParam("applinId")String applicationId,@RequestParam("orgId")Long orgId) {
		 ResponseEntity<?> responseEntity = null;
		 List<NOCForBuildingPermissionDTO> Nocdetail =null;
		 NOCForBuildingPermissionExtDTO  extDto=null;
    	try {
    		Nocdetail = nOCForBuildingPermissionService.getAppliDetail(Long.valueOf(applicationId), null,null, orgId);
        	 if(CollectionUtils.isNotEmpty(Nocdetail)) {
        		 extDto=new NOCForBuildingPermissionExtDTO();
        		 BeanUtils.copyProperties(Nocdetail.get(0), extDto);
        		 extDto.setTitleId(CommonMasterUtility.findLookUpDesc(PrefixConstants.LookUp.TITLE, orgId, Nocdetail.get(0).getTitleId()));
        		 extDto.setSex(CommonMasterUtility.findLookUpDesc(MainetConstants.GENDER, orgId, Long.valueOf(Nocdetail.get(0).getSex())));
        		 extDto.setApplicationType(CommonMasterUtility.findLookUpDesc(PrefixConstants.LookUp.APT, orgId, Long.valueOf(Nocdetail.get(0).getApplicationType())));
        		 extDto.setLocation(iLocationMasService.getLocationNameById(Nocdetail.get(0).getLocation(), orgId));
        		 responseEntity= ResponseEntity.status(HttpStatus.OK).body(extDto);
                 LOGGER.info("Assets Detail in json formate:"+new ObjectMapper().writeValueAsString(Nocdetail.get(0)));		

        	 }else {
        		 responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND)
                         .body("No Data Available Against Application  id :"+applicationId);
         		LOGGER.info("No Data Available Against Application  id : "+applicationId);
        	 }
        	 
    	}catch (Exception ex) {
    		responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Server Error");
    		LOGGER.error("Error While Fetching Noc buiding permission  detail: " + ex.getMessage(), ex);	
		}
		return responseEntity;
		
	}
	
	
}
