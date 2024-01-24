package com.abm.mainet.property.ui.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.Common_Constant;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.property.dao.IAssessmentMastDao;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.BillPaymentDetailDto;
import com.abm.mainet.property.dto.PropertyBillPaymentDto;
import com.abm.mainet.property.dto.ProperySearchDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.service.AssesmentMastService;
import com.abm.mainet.property.service.IProvisionalAssesmentMstService;
import com.abm.mainet.property.service.PrimaryPropertyService;
import com.abm.mainet.property.service.PropertyBillPaymentService;
import com.abm.mainet.property.service.PropertyPenltyService;
import com.abm.mainet.property.service.PropertyServiceImpl;
import com.abm.mainet.property.service.ViewPropertyDetailsService;
import com.abm.mainet.property.ui.model.PropertyBillPaymentModel;

@Controller
@RequestMapping("/PropertyBillPayment.html")
public class PropertyBillPaymentController extends AbstractFormController<PropertyBillPaymentModel> {
	
    private static final Logger LOGGER = Logger.getLogger(PropertyServiceImpl.class);

    @Autowired
    private PropertyBillPaymentService propertyBillPaymentService;

    @Autowired
    private ViewPropertyDetailsService viewPropertyDetailsService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private AssesmentMastService assesmentMastService;
    
    @Autowired
    private PrimaryPropertyService primaryPropertyService;
    
    @Autowired
    private IReceiptEntryService receiptEntryService;
    
    @Autowired
    private IAssessmentMastDao assessmentMastDao;
    
    @Autowired
    private IFinancialYearService iFinancialYearService;
    
	@Autowired
	private PropertyPenltyService propertyPenltyService;
	
    @Autowired
    private TbTaxMasService tbTaxMasService;
    
    @Resource
    private IProvisionalAssesmentMstService provisionalAssesmentMstService;
    
    @Autowired
    private IWorkflowRequestService workflowReqService;
    
    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest request) {
        sessionCleanup(request);
        getModel().bind(request);
        PropertyBillPaymentModel model = this.getModel();
        model.setCommonHelpDocs("PropertyBillPayment.html");
        Organisation org = UserSession.getCurrent().getOrganisation();
        if(Utility.isEnvPrefixAvailable(org, "BMT")) {
        	model.getOfflineDTO().setOnlineOfflineCheck(MainetConstants.FlagP);
		}
		try {
			List<LookUp> parentPropLookupList = CommonMasterUtility.getLevelData(PrefixConstants.GPI,
					MainetConstants.NUMBER_ONE, org);
			this.getModel().setParentPropLookupList(parentPropLookupList);
		} catch (Exception e) {
			LOGGER.error("Prefix GPI is not configured in prefix master ");
		}
        return new ModelAndView("PropertyBillPaymentSearch", MainetConstants.FORM_NAME, model);

    }

    @RequestMapping(params = "getBillPaymentDetail", method = RequestMethod.POST)
    public ModelAndView getBillPaymentDetail(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        PropertyBillPaymentModel model = this.getModel();
        PropertyBillPaymentDto dto = model.getPropBillPaymentDto();
        Organisation org = UserSession.getCurrent().getOrganisation();
        List<String> checkActiveFlagList = null;
        String billingMethod = null;
        LookUp billMethod  = null;
    	try {
    		billMethod = CommonMasterUtility.getValueFromPrefixLookUp("BMT", "ENV", org);
    	}catch (Exception e) {
		}
    	getModel().setParentGrpFlag(MainetConstants.FlagN);
    	ModelAndView mv = new ModelAndView("propertyBillPaymentSearchValidn", MainetConstants.FORM_NAME, this.getModel());
        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        if (StringUtils.isNotBlank(dto.getAssNo())) {
        	if (billMethod != null && StringUtils.isNotBlank(billMethod.getOtherField())
    				&& StringUtils.equals(billMethod.getOtherField(), MainetConstants.FlagY)) {
        		Long billingMethodId = primaryPropertyService.getBillMethodIdByPropNo(dto.getAssNo(), org.getOrgid());
        		
        		if(billingMethodId == null) {
        			 getModel().addValidationError("Invalid Property Number");                    
                     return mv;
        		}
            	LookUp billingMethodLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(billingMethodId, org);
            	
               if(billingMethodLookUp != null) {
            	   billingMethod = billingMethodLookUp.getLookUpCode();
            	   getModel().setBillingMethod(billingMethod);
            	   if(StringUtils.equals(billingMethodLookUp.getLookUpCode(), MainetConstants.FlagI) && StringUtils.isBlank(dto.getFlatNo())) {
            		   getModel().addValidationError("Please enter flat no");                       
                       return mv;
            	   }
                  //D#145343
            	   if(dto.getFlatNo()!=null) {
            		   String occpierName=   primaryPropertyService.getPropertyDetailsByPropNoFlatNoAndOrgId(dto.getAssNo(), dto.getFlatNo(), org.getOrgid()) ;
            		   if(occpierName!=null) {
            			  model.setOccupierName(occpierName); 
            		   }
            	   }
               }
    		}
        	checkActiveFlagList = assesmentMastService.checkActiveFlag(dto.getAssNo(),
                    UserSession.getCurrent().getOrganisation().getOrgid());
        }
        if (CollectionUtils.isNotEmpty(checkActiveFlagList)) {
            String checkActiveFlag = checkActiveFlagList.get(checkActiveFlagList.size() - 1);
            if (StringUtils.equals(checkActiveFlag, MainetConstants.STATUS.INACTIVE)) {
                getModel().addValidationError("This property is Inactive");                
                return mv;
            }
        }
        if ("M".equals(model.getReceiptType()) && model.getManualReeiptDate() == null) {
            getModel().addValidationError("Enter valid manual receipt date.");           
            return mv;
        }
        
        if ("M".equals(model.getReceiptType()) && model.getManualReeiptDate() != null) {
        	if(Utility.compareDate(new Date(), model.getManualReeiptDate())) {
        		getModel().addValidationError("Manual receipt date cannot be greater than current date");
        		return mv;
        	}
        }
        
        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL) && Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "BPA")) {
        	List<ProvisionalAssesmentMstDto> provAssesMastList = new ArrayList<>();
			if (!StringUtils.isEmpty(dto.getAssNo())) {
				provAssesMastList = provisionalAssesmentMstService.getPropDetailByPropNoOnly(dto.getAssNo());
			} else {
				String propNo = provisionalAssesmentMstService.getPropNoByOldPropNo(dto.getAssOldpropno(), UserSession.getCurrent().getOrganisation().getOrgid());
				if(StringUtils.isNotBlank(propNo)) {
					provAssesMastList = provisionalAssesmentMstService.getPropDetailByPropNoOnly(propNo);
				}
			}
			if(CollectionUtils.isNotEmpty(provAssesMastList)) {
				ProvisionalAssesmentMstDto provAssesMstDto = provAssesMastList.get(provAssesMastList.size() - 1);
				if(provAssesMstDto!=null && provAssesMstDto.getApmApplicationId() != null) {
					WorkflowRequest workflowRequest = workflowReqService
							.getWorkflowRequestByAppIdOrRefId(provAssesMstDto.getApmApplicationId(), null, UserSession.getCurrent().getOrganisation().getOrgid());
					if (workflowRequest != null && MainetConstants.WorkFlow.Status.PENDING.equalsIgnoreCase(workflowRequest.getStatus())) {
						if(StringUtils.isNotBlank(dto.getAssNo())) {
							getModel().addValidationError("Change In Assessment against property number " + dto.getAssNo()
							+ " is already in progress ");
						}else {
							getModel().addValidationError("Change In Assessment against property number " + dto.getAssOldpropno()
							+ " is already in progress ");
						}
						return mv;
					}
				}
			}
        	
		}
        
        BillPaymentDetailDto billPayDto =null;
		if (StringUtils.isNotBlank(dto.getParentPropNo())) {
			getModel().setParentGrpFlag(MainetConstants.FlagY);
			this.getModel().getPropBillPaymentDto().setSpecNotSearchType("GP");
			List<String> propertyNoList = assessmentMastDao.fetchAssessmentByGroupPropNo(org.getOrgid(),
					dto.getParentPropNo(), null, MainetConstants.FlagA);
			if (CollectionUtils.isNotEmpty(propertyNoList)) {
				// To check whether at least one bill is present for all properties present in parent property
				List<Object[]> propNoWithoutBills = assessmentMastDao.fetchPropNoWhoseBillNotPresent(org.getOrgid(),
						null);
				if (CollectionUtils.isNotEmpty(propNoWithoutBills)) {
					List<String> propNos = new ArrayList<>();
					List<String> parentPropNos = new ArrayList<>(propertyNoList);
					propNoWithoutBills.forEach(prop -> {
						propNos.add(prop[0].toString());
					});
					parentPropNos.retainAll(propNos);
					if (CollectionUtils.isNotEmpty(parentPropNos)) {
						getModel().addValidationError(
								ApplicationSession.getInstance().getMessage("property.groupPropertyValidn"));
						return mv;
					}
				}
				// To check out parent properties whose bill is not generated in current year
				Long finId = iFinancialYearService.getFinanceYearId(new Date());
				List<Object[]> result = assessmentMastDao.getAllPropBillGeneByPropNoList(finId, org.getOrgid(),
						propertyNoList);
				if (CollectionUtils.isNotEmpty(result)) {
					getModel().addValidationError(
							ApplicationSession.getInstance().getMessage("property.groupPropertyValidn"));
					return mv;
				} else if (CollectionUtils.isNotEmpty(propertyNoList)) {					
					billPayDto = propertyBillPaymentService.getBillPaymentDetailForGrp(dto, propertyNoList,
							model.getManualReeiptDate(), UserSession.getCurrent().getEmployee().getEmpId(),
							UserSession.getCurrent().getOrganisation());
					
				}
			} else {
				getModel().addValidationError(ApplicationSession.getInstance().getMessage("property.vaidParentPropNo"));
				return mv;
			}
		} else {
			List<ProvisionalAssesmentMstDto> assList = assesmentMastService.getPropDetailFromMainAssByPropNoOrOldPropNo(
					UserSession.getCurrent().getOrganisation().getOrgid(), dto.getAssNo(), dto.getAssOldpropno());
			if (CollectionUtils.isNotEmpty(assList)) {
				ProvisionalAssesmentMstDto assesMast = assList.get(assList.size() - 1);
				if (assesMast != null && StringUtils.equals(assesMast.getIsGroup(), MainetConstants.FlagY)) {
					getModel().addValidationError(
							ApplicationSession.getInstance().getMessage("property.propBillPayGrpValidn"));					
					return mv;
				}
			}
			billPayDto = propertyBillPaymentService.getBillPaymentDetail(dto.getAssOldpropno(), dto.getAssNo(),
                UserSession.getCurrent().getOrganisation().getOrgid(), UserSession.getCurrent().getEmployee().getEmpId(),
                model.getManualReeiptDate(),billingMethod,dto.getFlatNo());
		}
		// Defect#114092 - After submitting the form sequence of Tax Description is
		// getting changed on generated Receipt
		if (billPayDto != null) {
			if(billPayDto.getBillDisList() != null && !billPayDto.getBillDisList().isEmpty()) {
				billPayDto.getBillDisList().sort(Comparator.comparing(BillDisplayDto::getDisplaySeq));
			}
			if(StringUtils.equals(MainetConstants.FlagI, billingMethod)) {				
				billPayDto.getAssmtDto().getProvisionalAssesmentDetailDtoList().forEach(detail ->{
					if("Illegal".equalsIgnoreCase(detail.getLegal()) || MainetConstants.FlagN.equals(detail.getLegal())) {
						model.setIllegal(MainetConstants.FlagY);
					}
				});
			}
			model.setBillPayDto(billPayDto);
	        model.setOwnerDtlDto(billPayDto.getOwnerDtlDto());
	        model.setHalfPaymentRebate(billPayDto.getHalfPaymentRebate());
	        BigDecimal receivedAmount = receiptEntryService.getReceiptAmountPaidByPropNoOrFlatNo(dto.getAssNo(), dto.getFlatNo(), UserSession.getCurrent().getOrganisation(), billPayDto.getDeptId());
	        if(receivedAmount != null) {
	        	 model.setReceivedAmount(receivedAmount.doubleValue());
	        }else {
	        	model.setReceivedAmount(0.0);
	        }
	        if(billPayDto.getTotalRebate().doubleValue() > 0) {
	        	model.setRebateApplFlag(MainetConstants.FlagY);
	        }else {
	        	model.setRebateApplFlag(MainetConstants.FlagN);
	        }
			if (billPayDto.getErrorMsg() != null) {
				getModel().addValidationError(billPayDto.getErrorMsg());
				return mv;
			}
		}

        if (billPayDto == null) {
        	if(model.getManualReeiptDate() != null) {
        		getModel().addValidationError(
                        "Enter valid Property No or Old Property No/ Manual receipt date cannot be less than bill date and last manual receipt date");
        	}else {
        		getModel().addValidationError(
                        "Enter valid Property No or Old Property No");
        	}          
            return mv;
        }
        if (billPayDto.getRedirectCheck() != null && billPayDto.getRedirectCheck().equals(MainetConstants.Y_FLAG)) {           
            return mv;
        }

        BigDecimal amount = new BigDecimal(MainetConstants.Property.BIG_DEC_ZERO);
        if (billPayDto != null && CollectionUtils.isNotEmpty(billPayDto.getBillDisList())) {
            for (BillDisplayDto bdDto : billPayDto.getBillDisList()) {
                if (bdDto.getTaxCategoryId() != null) {
                    final String taxCode = CommonMasterUtility
                            .getHierarchicalLookUp(bdDto.getTaxCategoryId(), billPayDto.getOrgId()).getLookUpCode();
                    if (StringUtils.isNotBlank(taxCode) && taxCode.equals(PrefixConstants.TAX_CATEGORY.REBATE)) {
                        amount = amount.add(bdDto.getCurrentYearTaxAmt());
                    }
                }
            }
        }
        model.setTotalRebate(Double.valueOf(Math.round(Double.valueOf(amount.toString()))));
        model.setDeptCode(departmentService.getDeptCode(billPayDto.getDeptId()));
        if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)){
			model.setSkdclEnv(MainetConstants.FlagY);
		}
        return new ModelAndView("PropertyBillPayment", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "backToMainPage", method = RequestMethod.POST)
    public ModelAndView back(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        this.sessionCleanup(httpServletRequest);
        getModel().setCommonHelpDocs("PropertyBillPayment.html");
        getModel().bind(httpServletRequest);
        PropertyBillPaymentModel model = this.getModel();
		try {
			List<LookUp> parentPropLookupList = CommonMasterUtility.getLevelData(PrefixConstants.GPI,
					MainetConstants.NUMBER_ONE, UserSession.getCurrent().getOrganisation());
			model.setParentPropLookupList(parentPropLookupList);
		} catch (Exception e) {
			LOGGER.error("Prefix GPI is not configured in prefix master ");
		}
        return new ModelAndView("BackToPropertyBillPayment", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "propertyDetailSearch", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView propertyDeatilSearch(final HttpServletRequest request) {
        getModel().bind(request);
   	    PropertyBillPaymentModel beforeCleanUpmodel = this.getModel();
   	    String receiptType = beforeCleanUpmodel.getReceiptType();
        this.sessionCleanup(request);
        PropertyBillPaymentModel model = this.getModel();
        model.setReceiptType(receiptType);
        return new ModelAndView("PropertyBillPaymentDetailSearch", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "searchData", method = RequestMethod.POST)
    public @ResponseBody List<ProperySearchDto> search(HttpServletRequest request) {
    	getModel().bind(request);
    	PropertyBillPaymentModel beforeCleanUpmodel = this.getModel();
    	String receiptType = beforeCleanUpmodel.getReceiptType();
       // this.sessionCleanup(request);
        PropertyBillPaymentModel model = this.getModel();
        model.setReceiptType(receiptType);
        List<ProperySearchDto> result = null;
        // model.setSearchDtoResult(new ArrayList<>(0));
        ProperySearchDto dto = model.getSearchDto();
        model.getSearchDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.PROPERTY,
                Common_Constant.ACTIVE_FLAG);
        model.getSearchDto().setDeptId(deptId);

        if ((dto.getOwnerName() == null || dto.getOwnerName().isEmpty())
                && (dto.getAssWard1() == null || dto.getAssWard1() <= 0)) {
            model.addValidationError("In order to search it is mandatory to enter any one of the below search detail");
        }
        if ((dto.getOwnerName() != null && !dto.getOwnerName().isEmpty()) && dto.getOwnerName().length() < 3) {
            model.addValidationError("Please enter more than three character in owner name feild");
        }
        if (!model.hasValidationErrors()) {
            result = viewPropertyDetailsService.searchPropertyDetailsForAll(model.getSearchDto(), null, null, null,null);
        }
        return result;
    }
    
    @ResponseBody
    @RequestMapping(params = "getBillingMethod", method = { RequestMethod.POST })
    public List<String> getBillingMethodAndFlatList(@RequestParam("propNo") String propNo, HttpServletRequest request) {
    	this.getModel().bind(request);
    	PropertyBillPaymentModel model = this.getModel();
    	List<String> flatNoList = null;
    	String billingMethod = null;
    	Long billingMethodId = primaryPropertyService.getBillMethodIdByPropNo(propNo, UserSession.getCurrent().getOrganisation().getOrgid());
    	LookUp billingMethodLookUp  = null;
    	try {
    		 billingMethodLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(billingMethodId, UserSession.getCurrent().getOrganisation());
    	}catch (Exception e) {
			// TODO: handle exception
		}
    	if(billingMethodLookUp != null) {
    		billingMethod = billingMethodLookUp.getLookUpCode();
    	}
    	this.getModel().setBillingMethod(billingMethod);
    	if(StringUtils.isNotBlank(billingMethod) && StringUtils.equals(billingMethod, MainetConstants.FlagI)) {
    		flatNoList = new ArrayList<String>();
    		flatNoList = primaryPropertyService.getFlatNoIdByPropNo(propNo, UserSession.getCurrent().getOrganisation().getOrgid());
    	}
    	model.setFlatNoList(flatNoList);
    	return flatNoList;
    }

    @ResponseBody
    @RequestMapping(params = "saveBillPayment", method = RequestMethod.POST)
    public Map<String, Object> saveBillPayment(HttpServletRequest httpServletRequest) {
	getModel().bind(httpServletRequest);
	Map<String, Object> object = new LinkedHashMap<String, Object>();
	if (this.getModel().saveForm()) {
	    object.put("successFlag", "Y");
	} else {
	    object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
	}

	return object;

    }

	@RequestMapping(params = "fetchBillPaymentDetail", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView fetchBillPaymentDetail(HttpServletRequest httpServletRequest,
			@RequestParam("propNo") String propNo, @RequestParam("flatNo") String flatNo,
			HttpServletResponse httpServletResponse) {

		getModel().bind(httpServletRequest);
		PropertyBillPaymentModel model = this.getModel();
		PropertyBillPaymentDto dto = new PropertyBillPaymentDto();
		dto.setAssNo(propNo);
		dto.setFlatNo(flatNo);
		model.setPropBillPaymentDto(dto);
		getBillPaymentDetail(httpServletRequest, httpServletResponse);
		return new ModelAndView("PropertyBillPaymentBase", MainetConstants.FORM_NAME, model);

	}
}
