package com.abm.mainet.property.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.bill.dto.PropertyBillGenerationMap;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.master.dao.IEmployeeDAO;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.dao.IAssessmentMastDao;
import com.abm.mainet.property.dto.NoticeGenSearchDto;
import com.abm.mainet.property.service.PrimaryPropertyService;
import com.abm.mainet.property.service.PropertyBillExceptionService;
import com.abm.mainet.property.service.PropertyBillGenerationService;
import com.abm.mainet.property.service.PropertyMainBillService;
import com.abm.mainet.property.service.PropertyService;

@Component
@Scope("session")
public class PropertyBillGenerationModel extends AbstractFormModel {

	 private static final Logger LOGGER = LoggerFactory.getLogger(PropertyBillGenerationModel.class);
	 
    @Autowired
    private PropertyService propertyService;
   
    @Autowired
    private PropertyBillExceptionService propertyBillExceptionService;
    
    @Autowired
    private PropertyBillGenerationService propertyBillGenerationService;
    
    @Autowired
    IEmployeeDAO empService;
    
    @Autowired
    private SeqGenFunctionUtility seqGenFunctionUtility;
    
    @Autowired
    private IAssessmentMastDao assessmentMastDao;
    
    @Autowired
    private IFinancialYearService iFinancialYearService;
    
    @Autowired
	private PropertyMainBillService propertyMainBillService;
    
    @Autowired
	private PrimaryPropertyService primaryPropertyService;

    private static final long serialVersionUID = 2479397350651234861L;

    NoticeGenSearchDto specialNotGenSearchDto = new NoticeGenSearchDto();

    private List<NoticeGenSearchDto> notGenSearchDtoList = new ArrayList<>();

    private List<LookUp> location = new ArrayList<>(0);
    
    private Date fromDate;
    
    private Date toDate;
    
    private List<String> flatNoList;
    
    private String flatNo;
    
    private String billingMethod;
    
    private String searchGridHide;

    private PropertyBillGenerationMap propertyBillGenerationMap = new PropertyBillGenerationMap();
    
    private List<String> parentPropNameList=new ArrayList<>();
    
    private List<LookUp> parentPropLookupList = new ArrayList<>(0);
    
    @Override
    protected final String findPropertyPathPrefix(
            final String parentCode) {
        switch (parentCode) {

        case "USA":
            return "specialNotGenSearchDto.assdUsagetype";

        case "WZB":
            return "specialNotGenSearchDto.assWard";

        default:
            return null;

        }
    }

    @Override
    public boolean saveForm() {
    	LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " saveForm() method");
    	//Execute only when Aligarh/SUDA
    	List<NoticeGenSearchDto> notGenShowList =new ArrayList<>();
    	long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		if (StringUtils.equals(MainetConstants.FlagY, searchGridHide)) {
			LOGGER.info("Making list for Bill generation for SUDA/Aligarh");
			 if (getSpecialNotGenSearchDto().getSpecNotSearchType().equals("SM")) {
				 if( Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
					 if ((getSpecialNotGenSearchDto().getPropertyNo() == null
			                    || getSpecialNotGenSearchDto().getPropertyNo().isEmpty())
			                    && (getSpecialNotGenSearchDto().getOldPropertyNo() == null
			                            || getSpecialNotGenSearchDto().getOldPropertyNo().isEmpty())
			                    && (getSpecialNotGenSearchDto().getHouseNo() == null
	                            || getSpecialNotGenSearchDto().getHouseNo().isEmpty())) {
			                addValidationError("Please enter valid property number or Old property number or House number");
			            } 
				 }else {
					 if ((getSpecialNotGenSearchDto().getPropertyNo() == null
			                    || getSpecialNotGenSearchDto().getPropertyNo().isEmpty())
			                    && (getSpecialNotGenSearchDto().getOldPropertyNo() == null
			                            || getSpecialNotGenSearchDto().getOldPropertyNo().isEmpty())) {
			                addValidationError("Please enter valid property number or Old property number.");
			            }  
				 }
		            
		        } else if (getSpecialNotGenSearchDto().getSpecNotSearchType().equals("AL")) {
		            if ((getSpecialNotGenSearchDto().getLocId() == null || getSpecialNotGenSearchDto().getLocId() <= 0)
		                    && (getSpecialNotGenSearchDto().getAssWard1() == null
		                            || getSpecialNotGenSearchDto().getAssWard1() <= 0)
		                    && (getSpecialNotGenSearchDto().getAssdUsagetype1() == null
		                            || getSpecialNotGenSearchDto().getAssdUsagetype1() <= 0)
		                    &&(getSpecialNotGenSearchDto().getParshadAssWard1() == null
		                    || getSpecialNotGenSearchDto().getParshadAssWard1() <= 0)) {
		                addValidationError("Please select any mandatory search criteria.");
		            } 
		        }
			 if(hasValidationErrors()) {
				 return false;
			 }
			 
			 if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "SKDCL")) {
				 
				 if(fromDate == null) {
						addValidationError(ApplicationSession.getInstance().getMessage("property.AssessmentList.fromDate"));
					}
					if(toDate == null) {
						addValidationError(ApplicationSession.getInstance().getMessage("property.AssessmentList.toDate"));
					}
					if(fromDate != null && toDate != null) {
						Object[] obj = null;
						obj = iFinancialYearService.getFinacialYearByDate(fromDate);
						int comparision1 = fromDate.compareTo((Date) obj[1]);
						int comparision2 = toDate.compareTo((Date) obj[2]);
						if (comparision1 == -1 || comparision1 == 1 || comparision2 == -1 || comparision2 == 1) {
							addValidationError(ApplicationSession.getInstance().getMessage("property.report.finYear.date"));
						}
					}
			 }
			specialNotGenSearchDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			
			 if(hasValidationErrors()) {
				 return false;
			 }
			if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "SKDCL")) {
				Long currFinId = iFinancialYearService.getFinanceYearId(fromDate);
			  	  FinancialYear currFinYear = iFinancialYearService.getFinincialYearsById(currFinId, UserSession.getCurrent().getOrganisation().getOrgid());
			  	  FinancialYear prevFinYear = Utility.getPreviousFinYearByCurrYear(currFinYear);
			  	  
				 notGenShowList = propertyBillGenerationService.fetchAssDetailBySearchCriteriaForSkdcl(
						 specialNotGenSearchDto,currFinId,prevFinYear.getFaYear(),
	                     UserSession.getCurrent().getOrganisation().getOrgid());
				 
				 if (specialNotGenSearchDto.getSpecNotSearchType().equals("S")) {
					 if(CollectionUtils.isNotEmpty(notGenShowList) && StringUtils.equals(notGenShowList.get(0).getCheckStatus(), MainetConstants.FlagY)) {
		                	addValidationError("Bill has been already generated against selected financial year / No bill exist for previous fin year");
		                	notGenShowList.clear();
		                }
				 }
			}else {
				notGenShowList = propertyBillGenerationService.fetchAssDetailBySearchCriteriaForProduct(specialNotGenSearchDto,
						orgId);
			}
			 
			if(CollectionUtils.isEmpty(notGenShowList)) {
				addValidationError("No record found");
				return false;
			}
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "PSCL")
					&& StringUtils.equals("SM", getSpecialNotGenSearchDto().getSpecNotSearchType())
					&& CollectionUtils.isNotEmpty(notGenShowList)
					&& StringUtils.equals(MainetConstants.FlagN, notGenShowList.get(0).getSplNotDueDatePass())) {
				addValidationError("Special notice due date is not completed");
				return false;
			}
			setNotGenSearchDtoList(notGenShowList);
		 }
		if ("GP".equals(getSpecialNotGenSearchDto().getSpecNotSearchType())) {
			notGenShowList = propertyBillGenerationService.getAllPropWithAuthChangeByPropNo(specialNotGenSearchDto,
					orgId);
			setNotGenSearchDtoList(notGenShowList);
		}
		//Why we have written below line ?
        Optional<NoticeGenSearchDto> data = getNotGenSearchDtoList().stream().filter(dto -> "Y".equals(dto.getGenNotCheck()))
                .findAny();
        if (data == null || !data.isPresent()) {
            addValidationError("Please select atleast one property.");
            return false;
        }
        Organisation orgid = UserSession.getCurrent().getOrganisation();
        final Employee empId = UserSession.getCurrent().getEmployee();
        Long loggedLocId = UserSession.getCurrent().getLoggedLocId();
        int langId = UserSession.getCurrent().getLanguageId();
        //D#150233
        if(Utility.isEnvPrefixAvailable(orgid, MainetConstants.ENV_SKDCL) && isBillExist(getNotGenSearchDtoList())
        		&& StringUtils.equals(MainetConstants.FlagS, specialNotGenSearchDto.getSpecNotSearchType())) {
        	addValidationError("Bill has been already generated against selected financial year");
        	getNotGenSearchDtoList().clear();
        }
        if(hasValidationErrors()) {
			 return false;
		 }
		// #37183 - To keep unique parent bill number across parent properties
		Long bmNumber = null;
		if ("GP".equals(getSpecialNotGenSearchDto().getSpecNotSearchType())) {

			List<String> propertyNoList = new ArrayList<>();
			Long finId = iFinancialYearService.getFinanceYearId(new Date());
			propertyNoList = assessmentMastDao.fetchAssessmentByGroupPropNo(orgid.getOrgid(),
					getSpecialNotGenSearchDto().getParentPropNo(), getSpecialNotGenSearchDto().getParentPropName(),
					MainetConstants.FlagA);
			// To check if bill is already generated in current year for parent properties selected
			List<String> parentBmNoList = assessmentMastDao.fetParentBillNoOfBillGeneratedProps(finId, orgid.getOrgid(),
					propertyNoList);
			if (CollectionUtils.isNotEmpty(parentBmNoList)) {
				String propBmNo = parentBmNoList.get(parentBmNoList.size() - 1);
				bmNumber = (propBmNo != null ? Long.valueOf(propBmNo) : null);
			} else {
				bmNumber = seqGenFunctionUtility.generateSequenceNo(MainetConstants.Property.PROP_DEPT_SHORT_CODE,
						MainetConstants.BILL_TABLE.PropertyTable, MainetConstants.BILL_TABLE.PropBmIdColumn,
						orgid.getOrgid(), MainetConstants.RECEIPT_MASTER.Reset_Type, null);
			}

		}	
		AtomicLong bmNo = new AtomicLong();
		if (bmNumber != null) {
			bmNo.set(bmNumber);
		}
		new Thread(() -> propertyService.propertyBillGeneration(getNotGenSearchDtoList(), orgid.getOrgid(),
				empId.getEmpId(), empId.getEmppiservername(), loggedLocId, langId, bmNo.get())).start();
        
        setSuccessMessage("Bill generation initiated!");
        LOGGER.info("End--> " + this.getClass().getSimpleName() + " saveForm() method");
        return true;
    }
    
    public boolean isBillExist(List<NoticeGenSearchDto> list) {
		int count = 0;
		Organisation org = UserSession.getCurrent().getOrganisation();
		if(CollectionUtils.isNotEmpty(list)) {
			NoticeGenSearchDto searchDto = list.get(0);
			Long billMethod = primaryPropertyService.getBillMethodIdByPropNo(searchDto.getPropertyNo(), org.getOrgid());
			if(StringUtils.equals(CommonMasterUtility.getNonHierarchicalLookUpObject(billMethod, org).getLookUpCode(), MainetConstants.FlagI)) {
				 count = propertyMainBillService.getBillExistByPropNoFlatNoAndYearId(searchDto.getPropertyNo(),
						 org.getOrgid(), Long.valueOf(searchDto.getFinYear()), searchDto.getFlatNo());
			}else {
				count = propertyMainBillService.getBillExistByPropNoAndYearId(searchDto.getPropertyNo(),
						org.getOrgid(), Long.valueOf(searchDto.getFinYear()));
			}
			if(count > 0) {
				return true;
			}
		}
		return false;
	}
    
    public boolean saveFormForMissingPropNos() {
    Organisation org = UserSession.getCurrent().getOrganisation();
    List<Long> userIdList = empService.getEmpId(org.getOrgid(), MainetConstants.MENU._0);
    Long userId = userIdList.get(0);
    List<String> propNoList = propertyBillExceptionService.getAllPendingPropNoForBill(org.getOrgid(), "PD", MainetConstants.FlagA,userId);
    if (CollectionUtils.isEmpty(propNoList)) {
        addValidationError("No property no found for demand generation");
        return false;
    }
    Long empId = UserSession.getCurrent().getEmployee().getEmpId();
    Long loggedLocId = UserSession.getCurrent().getLoggedLocId();
    
    new Thread(() -> propertyService.generateProvisionalBillForReportForMissingPropNos(org, empId, getClientIpAddress(), propNoList,loggedLocId)).start();
    return true;
    }

    public NoticeGenSearchDto getSpecialNotGenSearchDto() {
        return specialNotGenSearchDto;
    }

    public void setSpecialNotGenSearchDto(NoticeGenSearchDto specialNotGenSearchDto) {
        this.specialNotGenSearchDto = specialNotGenSearchDto;
    }

    public List<NoticeGenSearchDto> getNotGenSearchDtoList() {
        return notGenSearchDtoList;
    }

    public void setNotGenSearchDtoList(List<NoticeGenSearchDto> notGenSearchDtoList) {
        this.notGenSearchDtoList = notGenSearchDtoList;
    }

    public List<LookUp> getLocation() {
        return location;
    }

    public void setLocation(List<LookUp> location) {
        this.location = location;
    }

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public List<String> getFlatNoList() {
		return flatNoList;
	}

	public void setFlatNoList(List<String> flatNoList) {
		this.flatNoList = flatNoList;
	}

	public String getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}

	public String getBillingMethod() {
		return billingMethod;
	}

	public void setBillingMethod(String billingMethod) {
		this.billingMethod = billingMethod;
	}

	public String getSearchGridHide() {
		return searchGridHide;
	}

	public void setSearchGridHide(String searchGridHide) {
		this.searchGridHide = searchGridHide;
	}

	public PropertyBillGenerationMap getPropertyBillGenerationMap() {
		return propertyBillGenerationMap;
	}

	public void setPropertyBillGenerationMap(PropertyBillGenerationMap propertyBillGenerationMap) {
		this.propertyBillGenerationMap = propertyBillGenerationMap;
	}	

	public List<String> getParentPropNameList() {
		return parentPropNameList;
	}

	public void setParentPropNameList(List<String> parentPropNameList) {
		this.parentPropNameList = parentPropNameList;
	}

	public List<LookUp> getParentPropLookupList() {
		return parentPropLookupList;
	}

	public void setParentPropLookupList(List<LookUp> parentPropLookupList) {
		this.parentPropLookupList = parentPropLookupList;
	}
		
	
}
