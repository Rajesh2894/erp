package com.abm.mainet.property.service;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;

/**
 * @author Mithila.Jondhale
 * @since 28-June-2023
 */

public class PropertyDashboardSchedular  implements PropertyDashboardSchedularService{
	
	@Autowired
    private IOrganisationService iOrganisationService;
	
	private static final Logger LOGGER = Logger.getLogger(PropertyDashboardSchedular.class);
	
	@Transactional
	  public void propertyDashboardSchedular(QuartzSchedulerMaster runtimeBean, List<Object> parameterList) {
	  LOGGER.info("Started call for PropertyDashboard calculation job schedularr");
	  //ProperySearchDto searchDto = new ProperySearchDto(); 
	  Map<String, List<Long>> dashboardDetails = new LinkedHashMap<String, List<Long>>();
      LOGGER.info("Organisation Id from PropertyDashboard method >>"+runtimeBean.getOrgId().getOrgid());
   //   final Organisation organisation = iOrganisationService.getOrganisationById(runtimeBean.getOrgId().getOrgid());

	  //searchDto.setOrgId(orgId);
      LOGGER.info("Call for PropertyDashboard Task FETCH_PROPERTY_DASHBOARD_DATA Started ");
	  String url = ServiceEndpoints.FETCH_PROPERTY_DASHBOARD_DATA;
	  updateAllPropertyDashboardTask(dashboardDetails, url);
	  LOGGER.info("Call for PropertyDashboard Task FETCH_PROPERTY_DASHBOARD_DATA Ended");


LOGGER.info("Schedular for PropertyDashboard Task end ");

}

	private void updateAllPropertyDashboardTask(Map<String, List<Long>> requestParam, String url) {
		ResponseEntity<?> responseEntity = null;
	    DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
	    dd.setParsePath(true);
	    Object requestParam1 = null;
	    try {
	        responseEntity = RestClient.callRestTemplateClient(requestParam1, url);
	        HttpStatus statusCode = responseEntity.getStatusCode();
	        if (statusCode == HttpStatus.OK) {
	            LOGGER.info("Schedular for property dashboard run successfully ");
	        }
	    } catch (Exception ex) {
	        throw new FrameworkException(
	                "Exception occured while calling Schedular :" + requestParam1, ex);
	    }
	}

	
}
