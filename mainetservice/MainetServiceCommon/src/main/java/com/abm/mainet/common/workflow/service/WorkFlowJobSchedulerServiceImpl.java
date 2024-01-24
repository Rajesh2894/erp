package com.abm.mainet.common.workflow.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.abm.mainet.cfc.challan.dao.IChallanDAO;
import com.abm.mainet.cfc.loi.repository.PaymentTransactionMasRepository;
import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;
import com.abm.mainet.cfc.objection.service.IObjectionDetailsService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.payment.entity.PaymentTransactionMas;
import com.abm.mainet.common.master.repository.EmployeeJpaRepository;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.TCPChallannRecocileService;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
@Service
public class WorkFlowJobSchedulerServiceImpl implements IWorkFlowJobSchedulerService {

    private static final Logger LOGGER = Logger.getLogger(WorkFlowJobSchedulerServiceImpl.class);

    @Autowired
    private IWorkflowTyepResolverService iWorkflowTyepResolverService;

    @Autowired
    private IOrganisationService iOrganisationService;

    @Autowired
    private IObjectionDetailsService iObjectionDetailsService;

    @Autowired
    private PaymentTransactionMasRepository paymentTransactionMasRepository;
    @Autowired
    private IChallanDAO iChallanDAO;
    @Autowired 
    private EmployeeJpaRepository employeeJpaRepository;
	@Autowired
    private IWorkflowRequestService iWorkflowRequestService;
    @Autowired
    private ServiceMasterService serviceMasterService;
    @Override
    public void updateWorkflowObjectionTask(QuartzSchedulerMaster runtimeBean, List<Object> parameterList) {

        LOGGER.info("Schedular for work flow objection task Started");

        Map<String, List<Long>> workflowDetails = new LinkedHashMap<String, List<Long>>();

        List<Long> applicationId = new ArrayList<>();

        List<ObjectionDetailsDto> list = iObjectionDetailsService.getAllInactiveObjectionList();

        if (list != null && !list.isEmpty()) {
            list.forEach(objection -> {

                applicationId.add(objection.getApmApplicationId());
            });

            workflowDetails.put("applicationId", applicationId);

            String url = ServiceEndpoints.WorkflowExecutionURLS.WORKFLOW_AUTO_OBJECTION_TASK;

            updateAllNewlyGenertaedTask(workflowDetails, url);

        }

        LOGGER.info("Schedular for work flow objection time completed ");

    }

    @Override
    public void updateWorkflowAutoEscalationTask(QuartzSchedulerMaster runtimeBean, List<Object> parameterList) {

        LOGGER.info("Schedular for work flow Auto Escalation Task Started");

        Map<String, List<Long>> workflowDetails = new LinkedHashMap<String, List<Long>>();
        LOGGER.info("Organisation Id from updateWorkflowAutoEscalationTask method >>"+runtimeBean.getOrgId().getOrgid());
        final Organisation organisation = iOrganisationService.getOrganisationById(runtimeBean.getOrgId().getOrgid());

        LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp("AE", "WFM", organisation);
        LOGGER.info("Lookup code from updateWorkflowAutoEscalationTask method >>"+lookup.getLookUpId());
        List<WorkflowMas> list = iWorkflowTyepResolverService.resolveAutoEscalationWorkFlow(lookup.getLookUpId());

        if (list != null && !list.isEmpty()) {

            List<Long> ids = list.stream().map(WorkflowMas::getWfId).collect(Collectors.toList());

            workflowDetails.put("workFlowId", ids);

            String url = ServiceEndpoints.WorkflowExecutionURLS.WORKFLOW_AUTO_ESCALATION_TASK;

            updateAllNewlyGenertaedTask(workflowDetails, url);
        }

        LOGGER.info("Schedular for work flow Auto Escalation Task end ");

    }

    private void updateAllNewlyGenertaedTask(Map<String, List<Long>> requestParam, String url) {
        ResponseEntity<?> responseEntity = null;
        DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
        dd.setParsePath(true);
        try {
            responseEntity = RestClient.callRestTemplateClient(requestParam, url);
            HttpStatus statusCode = responseEntity.getStatusCode();
            if (statusCode == HttpStatus.OK) {
                LOGGER.info("Schedular for work flow run successfully ");
            }
        } catch (Exception ex) {
            throw new FrameworkException(
                    "Exception occured while calling Schedular :" + requestParam, ex);
        }
    }
    @Override
    public void runOfflinechallanReconclitionJob(QuartzSchedulerMaster runtimeBean, List<Object> parameterList) {

        LOGGER.info("Schedular for work flow Auto initialization after ");

        Map<String, List<Long>> workflowDetails = new LinkedHashMap<String, List<Long>>();
        LOGGER.info("Organisation Id from updateWorkflowAutoEscalationTask method >>"+runtimeBean.getOrgId().getOrgid());
        final Organisation organisation = iOrganisationService.getOrganisationById(runtimeBean.getOrgId().getOrgid());
        WardZoneBlockDTO dwzDto=new WardZoneBlockDTO();
        String flag=MainetConstants.BLANK;
        if(ApplicationSession.getInstance() != null)
          flag= ApplicationSession.getInstance().getMessage("transaction.amount.nl");
       List<PaymentTransactionMas> payDtoList= paymentTransactionMasRepository.getAlloffLineChallanDataForReconcile(MainetConstants.FlagM, runtimeBean.getOrgId().getOrgid());
      if(CollectionUtils.isNotEmpty(payDtoList)) {
    	  for(PaymentTransactionMas rdto:payDtoList){
    		  LOGGER.info("Schedular for work flow Auto initialization for Reference No " +rdto.getReferenceId()); 
    		  Map<String,String> resMap=TCPChallannRecocileService.callSoapServiceForGrnDetails(rdto.getRecvMihpayid());
    		  LOGGER.info("Response from Egrass "+resMap); 
    		if(( StringUtils.equals(flag,"Y"))||(resMap!=null && StringUtils.equalsIgnoreCase(resMap.get("Status"), MainetConstants.PAYMENT_STATUS.SUCCESS))) {
    			CommonChallanDTO offline=new CommonChallanDTO();
    			offline.setOrgId(runtimeBean.getOrgId().getOrgid());
      		  setOfflineData(offline,rdto);
    		initiateWorkFlowAndUpdateStatus(offline,dwzDto,rdto);
    		}
    		  
    	  }
      }

        LOGGER.info("Schedular for work flow Auto initialization Task end ");

    }


   
	private void initiateWorkFlowAndUpdateStatus(CommonChallanDTO offline,WardZoneBlockDTO dwzDto,PaymentTransactionMas rdto) {
		  try {
				iWorkflowRequestService.initiateAndUpdateWorkFlowProcess(offline, dwzDto);
				  rdto.setRecvStatus("RECONCILE");
				  rdto.setUpdatedDate(new Date());
				  paymentTransactionMasRepository.save(rdto);
			} catch (Exception e) {
				LOGGER.error("Error at the time of initialize task and update recieived status "+e);
			}
		
	}

	private void setOfflineData(CommonChallanDTO offline,PaymentTransactionMas rdto) {
		if (rdto.getReferenceId() != null) {
			offline.setApplNo(Long.valueOf(rdto.getReferenceId()));
		}
		offline.setReferenceNo(rdto.getReferenceId());
		if (StringUtils.equals(rdto.getRecvMode(), MainetConstants.FlagY)) {
			offline.setDocumentUploaded(true);
		} else {
			offline.setDocumentUploaded(false);rdto.getSmServiceId();
		}
		offline.setServiceId(rdto.getSmServiceId());
		offline.setUserId(rdto.getUserId());
		offline.setDeptId(serviceMasterService.getServiceMaster(rdto.getSmServiceId(), rdto.getOrgId()).getTbDepartment().getDpDeptid());
		offline.setApplicantName(rdto.getSendFirstname());
		offline.setMobileNumber(rdto.getSendPhone());
		
		
	}

}
