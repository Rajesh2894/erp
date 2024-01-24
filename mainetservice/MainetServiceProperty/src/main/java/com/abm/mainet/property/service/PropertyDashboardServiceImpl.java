package com.abm.mainet.property.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.dto.AccountReceiptDTO;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.master.dto.TbOrganisation;
import com.abm.mainet.common.master.repository.TbOrganisationJpaRepository;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.service.OrganisationService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.repository.AssesmentMstRepository;
import com.abm.mainet.property.repository.PropertyPenltyRepository;
import com.abm.mainet.property.rest.dto.PropertyDashboardAssessedPropertiesDTO;
import com.abm.mainet.property.rest.dto.PropertyDashboardBucketDTO;
import com.abm.mainet.property.rest.dto.PropertyDashboardCessDTO;
import com.abm.mainet.property.rest.dto.PropertyDashboardInterestDTO;
import com.abm.mainet.property.rest.dto.PropertyDashboardMetricsDTO;
import com.abm.mainet.property.rest.dto.PropertyDashboardPenaltyDTO;
import com.abm.mainet.property.rest.dto.PropertyDashboardPropertiesRegisteredDTO;
import com.abm.mainet.property.rest.dto.PropertyDashboardPropertyTaxDTO;
import com.abm.mainet.property.rest.dto.PropertyDashboardRebateDTO;
import com.abm.mainet.property.rest.dto.PropertyDashboardRequestDTO;
import com.abm.mainet.property.rest.dto.PropertyDashboardResponseDTO;
import com.abm.mainet.property.rest.dto.PropertyDashboardTodaysCollectionDTO;
import com.abm.mainet.property.rest.dto.PropertyDashboardTodaysMovedApplDTO;
import com.abm.mainet.property.rest.dto.PropertyDashboardTransactionsDTO;

import io.swagger.annotations.Api;

/**
 * @author Mithila.Jondhale
 * @since 03-July-2023
 */

@Service
@WebService(endpointInterface = "com.abm.mainet.property.service.PropertyDashboardService")
@Api(value = "/propertyDashboard")
@Path("/propertyDashboard")

public class PropertyDashboardServiceImpl implements PropertyDashboardService{

	@Autowired
	private IOrganisationService iOrganisationService;

	@Autowired
	private TbOrganisationService organisationService;

	@Resource
	private TbOrganisationJpaRepository tbOrganisationJpaRepository;

	@Autowired
	private PropertyPenltyRepository propertyPenltyRepository;

	@Autowired
	private ICFCApplicationMasterService cfcApplicationMasService;
		
	@Autowired
	private TbDepartmentService departmentService;
	 
	@Autowired
	private IReceiptEntryService iReceiptEntryService;

	@Autowired
    private AssesmentMstRepository assesmentMstRepository;

	@Autowired
    private AssesmentMastService assesmentMastService;
	
	@Resource
	private TbTaxMasService tbTaxMasService;
	
	private static final Logger LOGGER = Logger.getLogger(PropertyDashboardServiceImpl.class);
	@POST
	@Path("/fetchPropertyData")
	@Transactional(readOnly = true)
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public List<PropertyDashboardResponseDTO> fetchPropertyData(PropertyDashboardRequestDTO requestDTO) {
		List<PropertyDashboardResponseDTO> propertyResponseDtoList = new ArrayList<>();
	    Date date1=requestDTO.getDate();
	    String dateSet = new SimpleDateFormat(MainetConstants.DATE_FORMATS).format(date1);
	    LOGGER.info("Property API DATE:"+date1+"String format"+dateSet);
		List<TbOrganisation> orgList = organisationService.findAll();
		 orgList = orgList.stream().filter(or -> !or.getOrgid().equals(Long.valueOf(MainetConstants.NUMBER_ONE))).collect(Collectors.toList());
		for (final TbOrganisation orgData : orgList) {
			if(orgData.getOrgid()!=null && !orgData.getoNlsOrgname().isEmpty() && !orgData.getoNlsOrgname().contains("test")) {
		
				LOGGER.info("Begin -> " + this.getClass().getSimpleName() + " fetchPropertyData() method");
				
				PropertyDashboardResponseDTO propertyResponse = new PropertyDashboardResponseDTO();
				final String orgName = tbOrganisationJpaRepository.findOrgNameById(orgData.getOrgid());
				final Organisation org = new Organisation();
				org.setOrgid(orgData.getOrgid());
				LOGGER.info("Property API OrgId:"+org.getOrgid()+" OrgName:"+org.getONlsOrgname());
				 LookUp stateLookup = null;
				String state = null;
				if(org.getOrgid()!=0l && orgData.getOrgCpdIdState()!=null) {
				stateLookup= CommonMasterUtility.getNonHierarchicalLookUpObject(orgData.getOrgCpdIdState(), org);
				state=(stateLookup.getDescLangFirst().toLowerCase()).substring(0, 2);
				LOGGER.info("Property API State Name ---------------->" + state);
				}
				if(orgData.getOrgid()!=0l) {
					Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.PROPERTY);
					Long assesments = cfcApplicationMasService.getTransctionsData(orgData.getOrgid(), deptId, date1);	
					Long avgDaysForApplicationApproval = 0l;
					Long stipulatedDays = 0l;
					Long todaysApprovedApplications = cfcApplicationMasService.getTodaysApprovedApplications(orgData.getOrgid(), deptId, dateSet);
					Long todaysApprovedApplicationsWithinSLA=0l;
					Long todaysClosedApplications=cfcApplicationMasService.getCountClosedApplications(orgData.getOrgid(), deptId, dateSet);
					Long todaysTotalApplications=cfcApplicationMasService.getTransctionsData(orgData.getOrgid(), deptId, date1);
					Long noOfPropertyPaidToday=iReceiptEntryService.countnoOfPropertyPaidToday(orgData.getOrgid(), deptId, dateSet);
					
					
					  List<PropertyDashboardTodaysMovedApplDTO> todaysMovedApplDTOs =null;
					  todaysMovedApplDTOs=getPropertyDashboardTodaysMovedApplDTOs(orgData.getOrgid().toString(), deptId, dateSet);
					  
					  List<PropertyDashboardPropertiesRegisteredDTO> propertiesRegisteredDTOs =null;
					  propertiesRegisteredDTOs=getPropertyDashboardPropertiesRegisteredDTOs(orgData.getOrgid().toString(), deptId, dateSet);
					  
					  List<PropertyDashboardAssessedPropertiesDTO>assessedPropertiesDTOs=null;
					  assessedPropertiesDTOs=getPropertyDashboardAssessedPropertiesDTO(orgData.getOrgid().toString(), deptId, dateSet);
					  
					  List<PropertyDashboardTransactionsDTO>transactionsDTOs=null;
					  transactionsDTOs=getPropertyDashboardTransactionsDTO(orgData.getOrgid().toString(), deptId, dateSet);
					  
					  List<PropertyDashboardTodaysCollectionDTO> todyCollecDtoList =null;
					  todyCollecDtoList =getPropertyDashboardTodyCollecDtoList(orgData.getOrgid().toString(), deptId, dateSet);
					  
					  List<PropertyDashboardPropertyTaxDTO> propertyTaxDTOs=null;
					  propertyTaxDTOs=getPropertyDashboardPropertyTaxDTO(orgData.getOrgid().toString(), deptId, dateSet);
					  
					  final List<LookUp> taxCodeList = tbTaxMasService.getAllTaxesBasedOnDept(orgData.getOrgid(), deptId);
					  String taxDescP= "";
					  String taxDescC= "";
					  String taxDescI= "";
					  String taxDescR = "";
					  if(!taxCodeList.isEmpty()) {
						  for(LookUp tax:taxCodeList) {
							  if(tax.getDescLangFirst().contains(MainetConstants.propertyDashboard.PENALTY)) {
								   taxDescP = tax.getDescLangFirst();
							  }else if(tax.getDescLangFirst().contains(MainetConstants.propertyDashboard.CESS)) {
								  taxDescC=tax.getDescLangFirst();
							  }else if(tax.getDescLangFirst().contains(MainetConstants.propertyDashboard.INTEREST)) {
								  taxDescI=tax.getDescLangFirst();
							  }else if(tax.getDescLangFirst().contains(MainetConstants.propertyDashboard.REBATE)) {
								  taxDescR=tax.getDescLangFirst();
							  }
						
						  }
					  }
					  List<PropertyDashboardCessDTO> cessDTOs=null;
					  cessDTOs=getPropertyDashboardCessDTO(orgData.getOrgid().toString(), deptId, taxDescC, dateSet);
					  
					  List<PropertyDashboardRebateDTO> rebateDTOs=null;
					  rebateDTOs=getPropertyDashboardRebateDTO(orgData.getOrgid().toString(), deptId, taxDescR, dateSet);
					  
					  List<PropertyDashboardInterestDTO>interestDTOs=null;
					  interestDTOs=getPropertyDashboardInterestDTO(orgData.getOrgid().toString(), deptId, taxDescI, dateSet);
					 					
					List<PropertyDashboardPenaltyDTO>penaltyDTOs=null;
					penaltyDTOs=getPropertyDashboardPenaltyDTO(orgData.getOrgid().toString(), deptId, taxDescP, dateSet);
					
					PropertyDashboardMetricsDTO metricsDto = new PropertyDashboardMetricsDTO();
					PropertyDashboardMetricsDTO metricsDtoList;

                      metricsDto.setAssessments(assesments!=null?assesments:0);
					  metricsDto.setAvgDaysForApplicationApproval(avgDaysForApplicationApproval!=null?avgDaysForApplicationApproval:0);
					  metricsDto.setStipulatedDays(stipulatedDays!=null?stipulatedDays:0);
					  metricsDto.setTodaysApprovedApplications(todaysApprovedApplications!=null?todaysApprovedApplications:0);
					  metricsDto.setTodaysApprovedApplicationsWithinSLA(
					  todaysApprovedApplicationsWithinSLA!=null?todaysApprovedApplicationsWithinSLA:0);
					  metricsDto.setTodaysClosedApplications(todaysClosedApplications!=null?todaysClosedApplications:0);
					  metricsDto.setTodaysTotalApplications(todaysTotalApplications!=null?todaysTotalApplications:0);
					  metricsDto.setNoOfPropertiesPaidToday(noOfPropertyPaidToday!=null?noOfPropertyPaidToday:0);
					 
					
					  metricsDto.setTodaysMovedApplications(todaysMovedApplDTOs);
					  metricsDto.setPropertiesRegistered(propertiesRegisteredDTOs);
					  metricsDto.setAssessedProperties(assessedPropertiesDTOs);
					  metricsDto.setTransactions(transactionsDTOs);
					  metricsDto.setTodaysCollection(todyCollecDtoList);
					  metricsDto.setPropertyTax(propertyTaxDTOs);
					  metricsDto.setCess(cessDTOs); 
					  metricsDto.setRebate(rebateDTOs);
					  metricsDto.setInterest(interestDTOs);
                      metricsDto.setPenalty(penaltyDTOs);
							
					propertyResponse.setMetrics(metricsDto);
					propertyResponse.setUlb(StringUtils.deleteWhitespace(state+"."+orgData.getoNlsOrgname().toLowerCase()));	
				    propertyResponse.setDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT_UPLOAD).format(date1));
					propertyResponse.setModule("PT");
					propertyResponse.setRegion("CityA");
					propertyResponse.setState(stateLookup!=null?stateLookup.getDescLangFirst():"Chhattisgarh");
					propertyResponse.setWard("Ward 1");
					propertyResponseDtoList.add(propertyResponse);
					LOGGER.info("Property API Data added for ulb ---------------->"+StringUtils.deleteWhitespace(state+"."+orgData.getoNlsOrgname().toLowerCase())+" " + propertyResponse);
				
			}

			}
		}
		LOGGER.info("Property API Data for all ulb is added to list for Date:"+dateSet);
		return propertyResponseDtoList;
	}
	

	public List<PropertyDashboardTodaysMovedApplDTO> getPropertyDashboardTodaysMovedApplDTOs(String orgId, Long deptId,String dateSet) {
		List<Object[]> PropTodaysMovedAppl=cfcApplicationMasService.getPropTodaysMovedAppl(Long.valueOf(orgId), deptId, dateSet);
		 List<PropertyDashboardBucketDTO> bucketList = new ArrayList<>();
	
		 PropertyDashboardBucketDTO bucketDto =null;
     if(PropTodaysMovedAppl!=null) {
     	for(Object[] pUType : PropTodaysMovedAppl) {
	        bucketDto = new PropertyDashboardBucketDTO(); 
			bucketDto.setName(String.valueOf(pUType[0]));
		    bucketDto.setValue(((BigInteger) pUType[1]).longValue());
		    bucketList.add(bucketDto);
				 
	        }
     }
     PropertyDashboardTodaysMovedApplDTO pTodaysMovedApplDto = new PropertyDashboardTodaysMovedApplDTO();
     pTodaysMovedApplDto.setGroupBy(MainetConstants.propertyDashboard.APPLICATION_STATUS);
     pTodaysMovedApplDto.setBuckets(bucketList);
     
     List<PropertyDashboardTodaysMovedApplDTO> pTodaysMovedApplDtoList = new ArrayList<>();
     pTodaysMovedApplDtoList.add(pTodaysMovedApplDto);
     LOGGER.info("Response of getPropertyDashboardTodaysMovedApplDTOs ends");
	return pTodaysMovedApplDtoList;
	}
	
	public List<PropertyDashboardPropertiesRegisteredDTO> getPropertyDashboardPropertiesRegisteredDTOs(String orgId, Long deptId,String dateSet) {
		List<Object[]> PropertiesRegisteredList=assesmentMastService.getPropertiesRegisteredList(Long.valueOf(orgId));
		 List<PropertyDashboardBucketDTO> bucketList = new ArrayList<>();
	
		 PropertyDashboardBucketDTO bucketDto =null;
   if(PropertiesRegisteredList!=null) {
   	for(Object[] pUType : PropertiesRegisteredList) {
	        bucketDto = new PropertyDashboardBucketDTO(); 
			bucketDto.setName(String.valueOf(pUType[0]));
		    bucketDto.setValue(((BigInteger) pUType[1]).longValue());
		    bucketList.add(bucketDto);
				 
	        }
   }
   PropertyDashboardPropertiesRegisteredDTO propertiesRegisteredDto= new PropertyDashboardPropertiesRegisteredDTO();
   propertiesRegisteredDto.setGroupBy(MainetConstants.propertyDashboard.FINANCIAL_YEAR);
   propertiesRegisteredDto.setBuckets(bucketList);
   
   List<PropertyDashboardPropertiesRegisteredDTO> propertiesRegisteredDtoList = new ArrayList<>();
   propertiesRegisteredDtoList.add(propertiesRegisteredDto);
   LOGGER.info("Response of getPropertyDashboardPropertiesRegisteredDTOs ends");
	return propertiesRegisteredDtoList;
	}

	
	public List<PropertyDashboardAssessedPropertiesDTO> getPropertyDashboardAssessedPropertiesDTO(String orgId, Long deptId, String dateSet) {
		List<Object[]> AssessedPropertiesList=assesmentMastService.getAssessedProperties(Long.valueOf(orgId), dateSet);
		 List<PropertyDashboardBucketDTO> bucketList = new ArrayList<>();
	
		 PropertyDashboardBucketDTO bucketDto =null;
    if(AssessedPropertiesList!=null) {
    	for(Object[] pUType : AssessedPropertiesList) {
	        bucketDto = new PropertyDashboardBucketDTO(); 
	        if(pUType[0].toString().contains(",") && pUType[0]!=null) {
	        	bucketDto.setName((pUType[0].toString()).substring(0, (pUType[0].toString()).indexOf(',')));
	        }else {		        	
	        	bucketDto.setName(String.valueOf(pUType[0]));
	        }
		    bucketDto.setValue(((BigInteger) pUType[1]).longValue());
		    bucketList.add(bucketDto);
				 
	        }
    }
    PropertyDashboardAssessedPropertiesDTO assessedPropertiesDto= new PropertyDashboardAssessedPropertiesDTO();
    assessedPropertiesDto.setGroupBy(MainetConstants.propertyDashboard.USAGE_TYPE);
    assessedPropertiesDto.setBuckets(bucketList);
    
    List<PropertyDashboardAssessedPropertiesDTO> assessedPropertiesDtoList = new ArrayList<>();
    assessedPropertiesDtoList.add(assessedPropertiesDto);
    LOGGER.info("Response of getPropertyDashboardAssessedPropertiesDTO ends");
	return assessedPropertiesDtoList;
	}

	public List<PropertyDashboardTransactionsDTO> getPropertyDashboardTransactionsDTO(String orgId, Long deptId, String dateSet) {
		List<Object[]> TransactionsPropList=assesmentMastService.getTransactionsProp(Long.valueOf(orgId), deptId, dateSet);
		 List<PropertyDashboardBucketDTO> bucketList = new ArrayList<>();
	
	   PropertyDashboardBucketDTO bucketDto =null;
	   if(TransactionsPropList!=null) {
	   	for(Object[] pUType : TransactionsPropList) {
		        bucketDto = new PropertyDashboardBucketDTO(); 
		        if(pUType[0].toString().contains(",") && pUType[0]!=null) {
		        	bucketDto.setName((pUType[0].toString()).substring(0, (pUType[0].toString()).indexOf(',')));
		        }else {		        	
		        	bucketDto.setName(String.valueOf(pUType[0]));
		        }
			    bucketDto.setValue(((BigInteger) pUType[2]).longValue());
			    bucketList.add(bucketDto);
					 
		        }
	   }
	   PropertyDashboardTransactionsDTO transactionsPropDto= new PropertyDashboardTransactionsDTO();
	   transactionsPropDto.setGroupBy(MainetConstants.propertyDashboard.USAGE_TYPE);
	   transactionsPropDto.setBuckets(bucketList);
	   
	   List<PropertyDashboardTransactionsDTO> transactionsPropDtoList = new ArrayList<>();
	   transactionsPropDtoList.add(transactionsPropDto);
	    LOGGER.info("Response of getPropertyDashboardTransactionsDTO ends");
	return transactionsPropDtoList;
	}

	public List<PropertyDashboardTodaysCollectionDTO> getPropertyDashboardTodyCollecDtoList(String orgId, Long deptId, String dateSet) {
		Organisation org = new Organisation();
		org.setOrgid(Long.valueOf(orgId));
		 List<PropertyDashboardBucketDTO> bucketList = new ArrayList<>();
		 List<PropertyDashboardBucketDTO> bucketList_1 = new ArrayList<>();
		 List<PropertyDashboardBucketDTO> bucketList_2 = new ArrayList<>();
		 List<PropertyDashboardBucketDTO> bucketList_fnl= new ArrayList<>();
		 
		 String tarrifCate = null;
			List<Object[]> usageTypeList=assesmentMastService.getTransactionsProp(Long.valueOf(orgId), deptId, dateSet);
	       PropertyDashboardBucketDTO bucketDto =null;
	       if(usageTypeList!=null) {
	       	for(Object[] uType : usageTypeList) {
		         bucketDto = new PropertyDashboardBucketDTO(); 
		         if(uType[0].toString().contains(",") && uType[0]!=null) {
			        	bucketDto.setName((uType[0].toString()).substring(0, (uType[0].toString()).indexOf(',')));
			        }else {		        	
			        	bucketDto.setName((String)uType[0]);	
			        }
		        	bucketDto.setValue(((BigDecimal)uType[1]).longValue()); 
					bucketList.add(bucketDto); 
		        }
	       }
	       List<Object[]> payType =assesmentMastService.getPaymentModeWiseColln(Long.valueOf(orgId), deptId, dateSet);
	       if(payType!=null) {
	       for(Object[] paymode:payType) {
	    	   bucketDto = new PropertyDashboardBucketDTO(); 
	    	
			  bucketDto.setName(paymode[0]!=null?paymode[0].toString():"");
		      bucketDto.setValue(((BigDecimal)paymode[1]).longValue());
			  bucketList_1.add(bucketDto); 
			  }
	       }
           PropertyDashboardTodaysCollectionDTO todyCollectDtoList1 = new PropertyDashboardTodaysCollectionDTO();
	       
	       todyCollectDtoList1.setGroupBy(MainetConstants.propertyDashboard.USAGE_TYPE);
	       todyCollectDtoList1.setBuckets(bucketList);
	       
	       PropertyDashboardTodaysCollectionDTO todyCollectDtoList2 = new PropertyDashboardTodaysCollectionDTO();
	       
	       todyCollectDtoList2.setGroupBy(MainetConstants.propertyDashboard.PAY_MODE);
	       todyCollectDtoList2.setBuckets(bucketList_1);
	       
	      List<PropertyDashboardTodaysCollectionDTO> todayList = new ArrayList<PropertyDashboardTodaysCollectionDTO>();
	       
	      todayList.add(todyCollectDtoList1);
	      todayList.add(todyCollectDtoList2);
	   LOGGER.info("Response of getPropertyDashboardTodyCollecDtoList ends");
		return todayList;
	
	}
	
	public List<PropertyDashboardPropertyTaxDTO> getPropertyDashboardPropertyTaxDTO(String orgId, Long deptId, String dateSet) {
		List<Object[]> TransactionsPropList=assesmentMastService.getTransactionsProp(Long.valueOf(orgId), deptId, dateSet);
		 List<PropertyDashboardBucketDTO> bucketList = new ArrayList<>();
	
	   PropertyDashboardBucketDTO bucketDto =null;
	   if(TransactionsPropList!=null) {
	   	for(Object[] pUType : TransactionsPropList) {
		        bucketDto = new PropertyDashboardBucketDTO(); 
		        if(pUType[0].toString().contains(",") && pUType[0]!=null) {
		        	bucketDto.setName((pUType[0].toString()).substring(0, (pUType[0].toString()).indexOf(',')));
		        }else {		        	
		        	bucketDto.setName(String.valueOf(pUType[0]));
		        }
			    bucketDto.setValue(((BigDecimal) pUType[1]).longValue());
			    bucketList.add(bucketDto);
					 
		        }
	   }
	   PropertyDashboardPropertyTaxDTO transactionsPropDto= new PropertyDashboardPropertyTaxDTO();
	   transactionsPropDto.setGroupBy(MainetConstants.propertyDashboard.USAGE_TYPE);
	   transactionsPropDto.setBuckets(bucketList);
	   
	   List<PropertyDashboardPropertyTaxDTO> transactionsPropDtoList = new ArrayList<>();
	   transactionsPropDtoList.add(transactionsPropDto);
	   LOGGER.info("Response of getPropertyDashboardPropertyTaxDTO ends");
	return transactionsPropDtoList;

	}


	
	public List<PropertyDashboardPenaltyDTO> getPropertyDashboardPenaltyDTO(String orgId, Long deptId, String taxDescP, String dateSet) {
		List<Object[]> TaxWiseAmountCollectnProp=iReceiptEntryService.getTaxWiseAmountCollectnProp(Long.valueOf(orgId), deptId, taxDescP, dateSet);
		 List<PropertyDashboardBucketDTO> bucketList = new ArrayList<>();
	
	   PropertyDashboardBucketDTO bucketDto =null;
	   if(TaxWiseAmountCollectnProp!=null) {
	   	for(Object[] pUType : TaxWiseAmountCollectnProp) {
		        bucketDto = new PropertyDashboardBucketDTO(); 
		        if(pUType[0].toString().contains(",") && pUType[0]!=null) {
		        	bucketDto.setName((pUType[0].toString()).substring(0, (pUType[0].toString()).indexOf(',')));
		        }else {		        	
		        	bucketDto.setName(String.valueOf(pUType[0]));
		        }
			    bucketDto.setValue(((BigDecimal) pUType[2]).longValue());
			    bucketList.add(bucketDto);
					 
		        }
	   }
	   PropertyDashboardPenaltyDTO penaltyPropDto= new PropertyDashboardPenaltyDTO();
	   penaltyPropDto.setGroupBy(MainetConstants.propertyDashboard.USAGE_TYPE);
	   penaltyPropDto.setBuckets(bucketList);
	   
	   List<PropertyDashboardPenaltyDTO> penaltyPropDtoList = new ArrayList<>();
	   penaltyPropDtoList.add(penaltyPropDto);
	   LOGGER.info("Response of getPropertyDashboardPenaltyDTO ends");
	return penaltyPropDtoList;
	}

	public List<PropertyDashboardInterestDTO> getPropertyDashboardInterestDTO(String orgId, Long deptId,
			String taxDescI, String dateSet) {
		List<Object[]> TaxWiseAmountCollectnProp=iReceiptEntryService.getTaxWiseAmountCollectnProp(Long.valueOf(orgId), deptId, taxDescI, dateSet);
		 List<PropertyDashboardBucketDTO> bucketList = new ArrayList<>();
	
	   PropertyDashboardBucketDTO bucketDto =null;
	   if(TaxWiseAmountCollectnProp!=null) {
	   	for(Object[] pUType : TaxWiseAmountCollectnProp) {
		        bucketDto = new PropertyDashboardBucketDTO(); 
		        if(pUType[0].toString().contains(",") && pUType[0]!=null) {
		        	bucketDto.setName((pUType[0].toString()).substring(0, (pUType[0].toString()).indexOf(',')));
		        }else {		        	
		        	bucketDto.setName(String.valueOf(pUType[0]));
		        }
			    bucketDto.setValue(((BigDecimal) pUType[2]).longValue());
			    bucketList.add(bucketDto);
					 
		        }
	   }
	   PropertyDashboardInterestDTO interestPropDto= new PropertyDashboardInterestDTO();
	   interestPropDto.setGroupBy(MainetConstants.propertyDashboard.USAGE_TYPE);
	   interestPropDto.setBuckets(bucketList);
	   
	   List<PropertyDashboardInterestDTO> interestPropDtoList = new ArrayList<>();
	   interestPropDtoList.add(interestPropDto);
	   LOGGER.info("Response of getPropertyDashboardInterestDTO ends");
	return interestPropDtoList;
	}




	public List<PropertyDashboardRebateDTO> getPropertyDashboardRebateDTO(String orgId, Long deptId,
			String taxDescR, String dateSet) {
		List<Object[]> TaxWiseAmountCollectnProp=iReceiptEntryService.getTaxWiseAmountCollectnProp(Long.valueOf(orgId), deptId, taxDescR,  dateSet);
		 List<PropertyDashboardBucketDTO> bucketList = new ArrayList<>();
	
	   PropertyDashboardBucketDTO bucketDto =null;
	   if(TaxWiseAmountCollectnProp!=null) {
	   	for(Object[] pUType : TaxWiseAmountCollectnProp) {
		        bucketDto = new PropertyDashboardBucketDTO(); 
		        if(pUType[0].toString().contains(",") && pUType[0]!=null) {
		        	bucketDto.setName((pUType[0].toString()).substring(0, (pUType[0].toString()).indexOf(',')));
		        }else {		        	
		        	bucketDto.setName(String.valueOf(pUType[0]));
		        }
			    bucketDto.setValue(((BigDecimal) pUType[2]).longValue());
			    bucketList.add(bucketDto);
					 
		        }
	   }
	   PropertyDashboardRebateDTO rebatePropDto= new PropertyDashboardRebateDTO();
	   rebatePropDto.setGroupBy(MainetConstants.propertyDashboard.USAGE_TYPE);
	   rebatePropDto.setBuckets(bucketList);
	   
	   List<PropertyDashboardRebateDTO> rebatePropDtoList = new ArrayList<>();
	   rebatePropDtoList.add(rebatePropDto);
	   LOGGER.info("Response of getPropertyDashboardRebateDTO ends");
	return rebatePropDtoList;
	}




	public List<PropertyDashboardCessDTO> getPropertyDashboardCessDTO(String orgId, Long deptId, String taxDescC, String dateSet) {
		List<Object[]> TaxWiseAmountCollectnProp=iReceiptEntryService.getTaxWiseAmountCollectnProp(Long.valueOf(orgId), deptId, taxDescC, dateSet);
		 List<PropertyDashboardBucketDTO> bucketList = new ArrayList<>();
	
	   PropertyDashboardBucketDTO bucketDto =null;
	   if(TaxWiseAmountCollectnProp!=null) {
	   	for(Object[] pUType : TaxWiseAmountCollectnProp) {
		        bucketDto = new PropertyDashboardBucketDTO(); 
		        if(pUType[0].toString().contains(",") && pUType[0]!=null) {
		        	bucketDto.setName((pUType[0].toString()).substring(0, (pUType[0].toString()).indexOf(',')));
		        }else {		        	
		        	bucketDto.setName(String.valueOf(pUType[0]));
		        }
			    bucketDto.setValue(((BigDecimal) pUType[2]).longValue());
			    bucketList.add(bucketDto);
					 
		        }
	   }
	   PropertyDashboardCessDTO cessPropDto= new PropertyDashboardCessDTO();
	   cessPropDto.setGroupBy(MainetConstants.propertyDashboard.USAGE_TYPE);
	   cessPropDto.setBuckets(bucketList);
	   
	   List<PropertyDashboardCessDTO> cessPropDtoList = new ArrayList<>();
	   cessPropDtoList.add(cessPropDto);

	return cessPropDtoList;
	}


}