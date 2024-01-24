package com.abm.mainet.water.service;

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

public class WaterDashboardSchedular  implements WaterDashboardSchedularService{
	
	@Autowired
    private IOrganisationService iOrganisationService;
	
	private static final Logger LOGGER = Logger.getLogger(WaterDashboardSchedular.class);
	
	@Transactional
	  public void waterDashboardSchedular(QuartzSchedulerMaster runtimeBean, List<Object> parameterList) {
	  LOGGER.info("Started call for WaterDashboard calculation job schedularr");
	  //ProperySearchDto searchDto = new ProperySearchDto(); 
	  Map<String, List<Long>> dashboardDetails = new LinkedHashMap<String, List<Long>>();
      LOGGER.info("Organisation Id from waterDashboard method >>"+runtimeBean.getOrgId().getOrgid());
   //   final Organisation organisation = iOrganisationService.getOrganisationById(runtimeBean.getOrgId().getOrgid());

	  //searchDto.setOrgId(orgId);
      LOGGER.info("Call for WaterDashboard Task FETCH_WATER_DASHBOARD_DATA Started ");
	  String url = ServiceEndpoints.FETCH_WATER_DASHBOARD_DATA;
	//  LOGGER.info("Call for WaterDashboard Task FETCH_WATER_DASHBOARD_DATA Ended");
	  updateAllWaterDashboardTask(dashboardDetails, url);
	  LOGGER.info("Call for WaterDashboard Task FETCH_WATER_DASHBOARD_DATA Ended");


LOGGER.info("Schedular for WaterDashboard Task end ");

}

	private void updateAllWaterDashboardTask(Map<String, List<Long>> requestParam, String url) {
		ResponseEntity<?> responseEntity = null;
	    DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
	    dd.setParsePath(true);
	    Object requestParam1 = null;
	    try {
	        responseEntity = RestClient.callRestTemplateClient(requestParam1, url);
	        HttpStatus statusCode = responseEntity.getStatusCode();
	        if (statusCode == HttpStatus.OK) {
	            LOGGER.info("Schedular for water dashboard run successfully ");
	        }
	    } catch (Exception ex) {
	        throw new FrameworkException(
	                "Exception occured while calling Schedular :" + requestParam1, ex);
	    }
	}
	
}
