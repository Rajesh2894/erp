package com.abm.mainet.property.ui.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.BillReceiptPostingDTO;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.OrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.property.domain.BillingScheduleDetailEntity;
import com.abm.mainet.property.dto.AmalgamationDto;
import com.abm.mainet.property.dto.ArrayOfDiversionPlotDetails;
import com.abm.mainet.property.dto.ArrayOfKhasraDetails;
import com.abm.mainet.property.dto.ArrayOfPlotDetails;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.BillPresentAndCalculationDto;
import com.abm.mainet.property.dto.ExcelSheetDto;
import com.abm.mainet.property.dto.LandTypeApiDetailRequestDto;
import com.abm.mainet.property.dto.PropertyRoomDetailsDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentDetailDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentFactorDtlDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.WriteExcelData;
import com.abm.mainet.property.service.AmalgamationService;
import com.abm.mainet.property.service.AsExecssAmtService;
import com.abm.mainet.property.service.AssesmentMastService;
import com.abm.mainet.property.service.BillingScheduleService;
import com.abm.mainet.property.service.DataEntrySuiteService;
import com.abm.mainet.property.service.IProvisionalAssesmentMstService;
import com.abm.mainet.property.service.PropertyBRMSService;
import com.abm.mainet.property.service.PropertyMainBillService;
import com.abm.mainet.property.service.PropertyService;
import com.abm.mainet.property.service.SelfAssessmentService;
import com.abm.mainet.property.ui.model.AmalgamationModel;
import com.abm.mainet.property.ui.validator.RuleErrorValidator;
import com.abm.mainet.property.ui.validator.SelfAssessmentValidator;
import com.google.common.util.concurrent.AtomicDouble;

@Controller
@RequestMapping({ "/AmalgamationForm.html", "/AmalgamationAuthorization.html" })
public class AmalgamationFormController extends AbstractFormController<AmalgamationModel> {

    @Resource
    private ILocationMasService iLocationMasService;

    @Autowired
    private IFinancialYearService iFinancialYear;

    @Autowired
    private AssesmentMastService assesmentMastService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private PropertyService propertyService;

    @Resource
    private SelfAssessmentService selfAssessmentService;

    @Autowired
    private PropertyBRMSService propertyBRMSService;

    @Autowired
    private IFileUploadService fileUpload;

    @Autowired
    private BillingScheduleService billingScheduleService;

    @Autowired
    private IChecklistVerificationService iChecklistVerificationService;

    @Resource
    private IProvisionalAssesmentMstService provisionalAssesmentMstService;

    @Autowired
    private IWorkFlowTypeService iWorkFlowTypeService;

    @Autowired
    private ServiceMasterService serviceMaster;

    @Autowired
    private DataEntrySuiteService dataEntrySuiteService;

    @Autowired
    private PropertyMainBillService mainBillService;

    @Autowired
    private AmalgamationService amalgamationService;
    
    @Autowired
    private IOrganisationService orgService;
    
    @Autowired
    private PropertyMainBillService propertyMainBillService;
    
    @Autowired
    private AsExecssAmtService asExecssAmtService;
    
    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(HttpServletRequest request) {
        this.sessionCleanup(request);
        fileUpload.sessionCleanUpForFileUpload();
        this.getModel().setActionURL("AmalgamationForm.html");
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        ServiceMaster service = serviceMaster.getServiceByShortName(MainetConstants.Property.Amalgamation, orgId);
        getModel().setOrgId(orgId);
        getModel().setDeptId(service.getTbDepartment().getDpDeptid());
        getModel().setIntgrtionWithBP(CommonMasterUtility
                .getDefaultValueByOrg(MainetConstants.Property.IBP, UserSession.getCurrent().getOrganisation()).getLookUpCode());
        getModel().setOrgId(orgId);
        getModel().setDeptId(service.getTbDepartment().getDpDeptid());

        getModel().setCommonHelpDocs("AmalgamationForm.html");
        return defaultResult();
    }

    @RequestMapping(method = { RequestMethod.POST }, params = "searchProperty")
    public ModelAndView serachProperty(HttpServletRequest request) {
        AmalgamationModel model = this.getModel();
        model.bind(request);
        model.setAssType(MainetConstants.Property.Amalgamation);
        model.setSuccessMessage(MainetConstants.Y_FLAG);
        setCommonFields(model);
        model.setProvisionalAssesmentMstDto(null);
        model.setProvisionalAssesmentMstDtoList(null);
        AmalgamationDto proAssMas = model.getAmalgamationDto();
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Long currrentFinYearId = iFinancialYear.getFinanceYearId(new Date());
        List<String> propNoList = new ArrayList<>();
        List<String> oldPropNoList = new ArrayList<>();
        List<String> parentPropNoList = new ArrayList<>();
        List<TbBillMas> paidFlagList = null;
        if (proAssMas.getAmalgmatedPropNo() != null && !proAssMas.getAmalgmatedPropNo().isEmpty()) {
            String[] propNo = proAssMas.getAmalgmatedPropNo().split(MainetConstants.operator.COMMA);
            for (String s : propNo) {
                propNoList.add(s.trim());
            }
        }
        if (proAssMas.getAmalgmatedOldPropNo() != null && !proAssMas.getAmalgmatedOldPropNo().isEmpty()) {
            String[] propNo = proAssMas.getAmalgmatedOldPropNo().split(MainetConstants.operator.COMMA);
            for (String s : propNo) {
                oldPropNoList.add(s.trim());
            }
        }
        if (propNoList.contains(proAssMas.getParentPropNo())) {
            model.addValidationError(
                    getApplicationSession().getMessage("parent.aml.val"));
        }
        if (oldPropNoList.contains(proAssMas.getParentOldPropNo())) {
            model.addValidationError(
                    getApplicationSession().getMessage("oldparent.aml.val"));
        }
		if (model.hasValidationErrors()) {
			model.setSuccessMessage(MainetConstants.N_FLAG);
			return defaultMyResult();
		}
        
        final List<ProvisionalAssesmentMstDto> assMstList = assesmentMastService.fetchAssessmentMasterForAmalgamation(orgId,
                proAssMas.getParentPropNo(),
                propNoList, proAssMas.getParentOldPropNo(), oldPropNoList);
        List<String> propNoListSize = new ArrayList<>();
        List<String> oldPropNoListSize = new ArrayList<>();
        propNoListSize.addAll(propNoList);
        oldPropNoListSize.addAll(oldPropNoList);
        
		if (CollectionUtils.isEmpty(assMstList)
				|| (CollectionUtils.isNotEmpty(propNoListSize) && assMstList.size() != propNoListSize.size())
				|| (CollectionUtils.isNotEmpty(oldPropNoList) && assMstList.size() != oldPropNoListSize.size())) {
			if(CollectionUtils.isNotEmpty(oldPropNoList)) {
				if(CollectionUtils.isEmpty(assMstList)) {
					model.addValidationError(getApplicationSession().getMessage("Please enter valid old property no :" +" " + oldPropNoList.toString()));
				}else {
					List<String> validatePropList = new ArrayList<String>();
					oldPropNoList.forEach(oldProp ->{
						boolean present = assMstList.stream().map(ProvisionalAssesmentMstDto::getAssOldpropno).filter(oldProp::equals).findFirst().isPresent();
						if(!present) {
							validatePropList.add(oldProp);
						}
					});
					model.addValidationError(getApplicationSession().getMessage("Please enter valid old property no :" +" "  + validatePropList.toString()));
				}
			}else {
				if(CollectionUtils.isEmpty(assMstList)) {
					model.addValidationError(getApplicationSession().getMessage("Please enter valid property no :" +" "  + propNoList.toString()));
				}else {
					List<String> validatePropList = new ArrayList<String>();
					propNoList.forEach(prop ->{
						boolean present = assMstList.stream().map(ProvisionalAssesmentMstDto::getAssOldpropno).filter(prop::equals).findFirst().isPresent();
						if(!present) {
							validatePropList.add(prop);
						}
					});
					model.addValidationError(getApplicationSession().getMessage("prop.enter.validation") +" "  + validatePropList.toString());
				}
			}
			
		} else {
			ProvisionalAssesmentMstDto parentProperty = assMstList.get(0);
			ProvisionalAssesmentMstDto childProperty = assMstList.get(assMstList.size() -1);
			if ((!parentProperty.getAssWard1().equals(childProperty.getAssWard1()))
					&& ((parentProperty.getAssWard2() == null) || (!parentProperty.getAssWard2().equals(childProperty.getAssWard2())))
					&& ((parentProperty.getAssWard3() == null) || (!parentProperty.getAssWard3().equals(childProperty.getAssWard3())))) {
				model.addValidationError("Amalgamation not allowed for two different zone and ward Properties");
			}
			StringBuilder propNosList = new StringBuilder();
			assMstList.forEach(assDto ->{
				
				if(!checkCurrentYearBillPaymentDone(currrentFinYearId, assDto.getAssNo())) {
					if (proAssMas.getParentPropNo() != null && !proAssMas.getParentPropNo().isEmpty()) {
						if(StringUtils.isBlank(propNosList.toString())) {
							propNosList.append(assDto.getAssNo());
						}else {
							propNosList.append(" ");
							propNosList.append(",");
							propNosList.append(" ");
							propNosList.append(assDto.getAssNo());
						}
					}
					if (proAssMas.getParentOldPropNo() != null && !proAssMas.getParentOldPropNo().isEmpty()) {
						if(StringUtils.isBlank(propNosList.toString())) {
							propNosList.append(assDto.getAssOldpropno());
						}else {
							propNosList.append(" ");
							propNosList.append(",");
							propNosList.append(" ");
							propNosList.append(assDto.getAssOldpropno());
						}
					}
					
					
				}
			});
			if(StringUtils.isNotBlank(propNosList.toString())) {
				model.addValidationError("Please generate current bill for "+ propNosList.toString());
			}
        List<String> checkActiveFlagList = assesmentMastService.checkActiveFlag(proAssMas.getAmalgmatedPropNo(), orgId);
        if (CollectionUtils.isNotEmpty(checkActiveFlagList)) {
            String checkActiveFlag = checkActiveFlagList.get(checkActiveFlagList.size() - 1);
            if (StringUtils.equals(checkActiveFlag, MainetConstants.STATUS.INACTIVE)) {
                model.addValidationError(getApplicationSession().getMessage("property.PropertyNumberAmalgamated.is.inactive"));
            }
        }
        List<String> checkParentActiveFlagList = assesmentMastService.checkActiveFlag(proAssMas.getParentPropNo(), orgId);
        if (CollectionUtils.isNotEmpty(checkParentActiveFlagList)) {
            String checkParentActiveFlag = checkParentActiveFlagList.get(checkParentActiveFlagList.size() - 1);
            if (StringUtils.equals(checkParentActiveFlag, MainetConstants.STATUS.INACTIVE)) {
                model.addValidationError(getApplicationSession().getMessage("property.ParentProperty.is.inactive"));
            }
        }        
        
        Organisation org = orgService.getOrganisationById(orgId);
        Long currFinYearId = iFinancialYear.getFinanceYearId(new Date());
        if(CollectionUtils.isEmpty(propNoList)) {
        	assMstList.forEach(dto ->{
        		propNoList.add(dto.getAssNo());
        	});
        }
        paidFlagList = amalgamationService.fetchNotPaidBillsByPropNo(propNoList,
                orgId);
        if (CollectionUtils.isNotEmpty(paidFlagList)) {
            Set<String> uniqueProp = new HashSet<String>();
            for (TbBillMas billMas : paidFlagList) {
                String propNo = billMas.getPropNo();
                uniqueProp.add(propNo);
            }
            for (String propNo : uniqueProp) {
                List<TbBillMas> billMasList = mainBillService.fetchAllBillByPropNo(propNo, orgId);
                boolean paidFlag = false;
                if(CollectionUtils.isNotEmpty(billMasList)) {
                	TbBillMas billMaster = billMasList.get(billMasList.size() - 1);
                    if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL)) {
                    	AtomicDouble pendingAmount = new AtomicDouble(0);
                    	billMaster.getTbWtBillDet().forEach(billDet ->{
                        	pendingAmount.addAndGet(billDet.getBdCurBalTaxamt() + billDet.getBdPrvArramt());
                        });
                        BillDisplayDto advanceAmnt = asExecssAmtService.getBillDisplayDtoWithAdvanceAmt(propNo, orgId, null);
                        if(advanceAmnt != null && advanceAmnt.getCurrentYearTaxAmt() != null && advanceAmnt.getCurrentYearTaxAmt().doubleValue() >= pendingAmount.doubleValue()) {
                        	paidFlag = true;
                        }
                    }
                }
                if (CollectionUtils.isNotEmpty(billMasList)  && !paidFlag) {
                    TbBillMas billMas = billMasList.get(billMasList.size() - 1);
                    
                    boolean paidFlagAmal = false;
                        if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL)) {
                        	AtomicDouble pendingAmount = new AtomicDouble(0);
                        	billMas.getTbWtBillDet().forEach(billDet ->{
                            	pendingAmount.addAndGet(billDet.getBdCurBalTaxamt() + billDet.getBdPrvArramt());
                            });
                            BillDisplayDto advanceAmnt = asExecssAmtService.getBillDisplayDtoWithAdvanceAmt(billMas.getPropNo(), orgId, null);
                            if(advanceAmnt != null && advanceAmnt.getCurrentYearTaxAmt() != null && advanceAmnt.getCurrentYearTaxAmt().doubleValue() >= pendingAmount.doubleValue()) {
                            	paidFlagAmal = true;
                            }
                        }
                    
                    if (CollectionUtils.isEmpty(oldPropNoList) && !StringUtils.equals(propNo, proAssMas.getParentPropNo()) && StringUtils.equals(billMas.getBmPaidFlag(), MainetConstants.N_FLAG) && !paidFlagAmal) {
                        model.addValidationError(getApplicationSession()
                                .getMessage("Dues pending for property number to be amalgamated " + billMas.getPropNo()));
                    }else if(CollectionUtils.isNotEmpty(oldPropNoList)) {
                    	List<ProvisionalAssesmentMstDto> amalgamatedOldPropNo = assMstList.stream().filter(assDto -> assDto.getAssOldpropno().equals(proAssMas.getAmalgmatedOldPropNo())).collect(Collectors.toList());
                    	
                    	if(CollectionUtils.isNotEmpty(amalgamatedOldPropNo) && amalgamatedOldPropNo.get(0).getAssNo().equals(propNo) && StringUtils.equals(billMas.getBmPaidFlag(), MainetConstants.N_FLAG) && !paidFlagAmal) {
                    		model.addValidationError(getApplicationSession()
                                    .getMessage("Dues pending for old property number to be amalgamated " + proAssMas.getAmalgmatedOldPropNo()));
                    	}
                    }
                    //To check bill is present for current year or not
					else {
						if (Utility.isEnvPrefixAvailable(org, PrefixConstants.ENV.ABD)) {
							if (!currFinYearId.equals(billMas.getBmYear())) {
								model.addValidationError(getApplicationSession().getMessage(
										ApplicationSession.getInstance().getMessage("property.validBillAmalgmated" + "",
												new Object[] { billMas.getPropNo() })));
							}
						}
					}
                }
            }

        }
               
        if (proAssMas.getParentPropNo() != null && !proAssMas.getParentPropNo().isEmpty()) {
            String[] propNo = proAssMas.getParentPropNo().split(MainetConstants.operator.COMMA);
            for (String s : propNo) {
                parentPropNoList.add(s.trim());
            }
        }
        if(CollectionUtils.isEmpty(parentPropNoList) && StringUtils.isNotBlank(proAssMas.getParentOldPropNo())) {
        	String assNo = assMstList.stream().filter(dto -> dto.getAssOldpropno().equals(proAssMas.getParentOldPropNo())).collect(Collectors.toList()).get(0).getAssNo();
        	parentPropNoList.add(assNo);
        }
        paidFlagList = amalgamationService.fetchNotPaidBillsByPropNo(parentPropNoList,
                orgId);
        if (CollectionUtils.isNotEmpty(paidFlagList)) {
            TbBillMas billMas = paidFlagList.get(paidFlagList.size() - 1);
            
            boolean paidFlag = false;
            if(CollectionUtils.isNotEmpty(paidFlagList)) {
            	TbBillMas billMaster = paidFlagList.get(paidFlagList.size() - 1);
                if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL)) {
                	AtomicDouble pendingAmount = new AtomicDouble(0);
                	billMaster.getTbWtBillDet().forEach(billDet ->{
                    	pendingAmount.addAndGet(billDet.getBdCurBalTaxamt() + billDet.getBdPrvArramt());
                    });
                    BillDisplayDto advanceAmnt = asExecssAmtService.getBillDisplayDtoWithAdvanceAmt(billMas.getPropNo(), orgId, null);
                    if(advanceAmnt != null && advanceAmnt.getCurrentYearTaxAmt() != null && advanceAmnt.getCurrentYearTaxAmt().doubleValue() >= pendingAmount.doubleValue()) {
                    	paidFlag = true;
                    }
                }
            }
            
            if (StringUtils.equals(billMas.getBmPaidFlag(), MainetConstants.N_FLAG)  && !paidFlag) {
            	if(StringUtils.isNotBlank(proAssMas.getParentOldPropNo())) {
            		model.addValidationError(getApplicationSession()
                            .getMessage("Dues pending for parent old property no. " + proAssMas.getParentOldPropNo()));
            	}else {
            		model.addValidationError(getApplicationSession()
                            .getMessage("Dues pending for parent property no. " + billMas.getPropNo()));
            	}
            }
			else {
				// To check bill is present for current year or not
				if (Utility.isEnvPrefixAvailable(org, PrefixConstants.ENV.ABD)) {
					if (!currFinYearId.equals(billMas.getBmYear())) {
						model.addValidationError(
								getApplicationSession().getMessage(ApplicationSession.getInstance().getMessage(
										"property.validBillAmalgmated" + "", new Object[] { billMas.getPropNo() })));
					}
				}
			}
        }              
        }
        if (!model.hasValidationErrors()) {                   
            List<ProvisionalAssesmentMstDto> childList = new ArrayList<>(0);
                assMstList.forEach(propMst -> {
                    propMst.setLocationName(iLocationMasService.getLocationNameById(propMst.getLocId(), orgId));
                    if (proAssMas.getParentPropNo() != null && !proAssMas.getParentPropNo().isEmpty()) {
                        if (proAssMas.getParentPropNo().equals(propMst.getAssNo())) {
                            model.setProvisionalAssesmentMstDto(propMst);
                            model.setAmalParentPropDto(propMst);

                        } else {
                            childList.add(propMst);
                        }
                    }
                    if (proAssMas.getParentOldPropNo() != null && !proAssMas.getParentOldPropNo().isEmpty()) {
                        if (proAssMas.getParentOldPropNo().equals(propMst.getAssOldpropno())) {
                            model.setProvisionalAssesmentMstDto(propMst);
                            model.setAmalParentPropDto(propMst);
                        } else {
                            childList.add(propMst);
                        }
                    }
                });
                model.setProvisionalAssesmentMstDtoList(childList);

                model.getProvisionalAssesmentMstDtoList().forEach(provAssMast -> {
                    final List<ProvisionalAssesmentDetailDto> provAssDetDtoListC = new ArrayList<>();
                    final int parentsize = model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().size();
                    AtomicInteger increnment = new AtomicInteger(parentsize);
                    provAssMast.getProvisionalAssesmentDetailDtoList().forEach(provDetEnt -> {
                        ProvisionalAssesmentDetailDto provDetDto = new ProvisionalAssesmentDetailDto();
                        final List<ProvisionalAssesmentFactorDtlDto> provAssFactDtoList = new ArrayList<>();
                        final List<PropertyRoomDetailsDto> propertyRoomDetailsDtoList = new ArrayList<>();
                        BeanUtils.copyProperties(provDetEnt, provDetDto);
                        provDetDto.setPropNo(provAssMast.getAssNo());
                        provDetDto.setAssdUnitNo(Long.valueOf(increnment.incrementAndGet()));
                        provDetDto.setTbAsAssesmentMast(provAssMast);
                        provDetEnt.getProvisionalAssesmentFactorDtlDtoList().forEach(proAssFactEnt -> {
                            ProvisionalAssesmentFactorDtlDto proAssFactDto = new ProvisionalAssesmentFactorDtlDto();
                            BeanUtils.copyProperties(proAssFactEnt, proAssFactDto);
                            provAssFactDtoList.add(proAssFactDto);
                        });
                        provDetEnt.getRoomDetailsDtoList().forEach(room ->{
                        	PropertyRoomDetailsDto propertyRoomDetailsDto=new PropertyRoomDetailsDto();
                        	BeanUtils.copyProperties(room, propertyRoomDetailsDto);
                        	propertyRoomDetailsDtoList.add(propertyRoomDetailsDto);
                        });
                        provDetDto.setProvisionalAssesmentFactorDtlDtoList(provAssFactDtoList);
                        provDetDto.setRoomDetailsDtoList(propertyRoomDetailsDtoList);
                        provAssDetDtoListC.add(provDetDto);
                    });
                    model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().addAll(provAssDetDtoListC);
                });
                Long finYearId = iFinancialYear.getFinanceYearId(new Date());
                List<FinancialYear> financialYearList = iFinancialYear
                        .getFinanceYearListFromGivenDate(UserSession.getCurrent().getOrganisation().getOrgid(),
                                finYearId, new Date());
                if (!financialYearList.isEmpty()) {
                    List<Long> finYearListDAta = new ArrayList<>();
                    for (FinancialYear financialYearEach : financialYearList) {
                        Long finYear = iFinancialYear.getFinanceYearId(financialYearEach.getFaFromDate());
                        finYearListDAta.add(finYear);
                    }
                    this.getModel().setFinYearList(finYearListDAta);
                }
                if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)
                		|| Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL)
                		|| Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
                	model.getProvisionalAssesmentMstDto().setFaYearId(finYearId);
                    List<ProvisionalAssesmentDetailDto> detailList = new ArrayList<>();
                    for (ProvisionalAssesmentDetailDto detailDto : model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList()) {
                        detailDto.setFaYearId(finYearId);
                        detailList.add(detailDto);
                    }
                    model.getProvisionalAssesmentMstDto().setProvisionalAssesmentDetailDtoList(detailList);
                }
        }
        if (model.hasValidationErrors()) {
			model.setSuccessMessage(MainetConstants.N_FLAG);
        }
        return defaultMyResult();
    }

    @RequestMapping(params = "showDetails", method = RequestMethod.POST)
    public ModelAndView defaultLoad(@RequestParam("appNo") final long applicationId, @RequestParam("taskId") final long serviceId,
            @RequestParam("actualTaskId") final long taskId, final HttpServletRequest httpServletRequest) throws Exception {
        sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        this.getModel().setActionURL("AmalgamationAuthorization.html");
        getModel().setLastChecker(iWorkFlowTypeService.isLastTaskInCheckerTaskList(taskId));
        getModel().getWorkflowActionDto().setApplicationId(applicationId);
        getModel().getWorkflowActionDto().setTaskId(taskId);
        AmalgamationModel model = this.getModel();
        setCommonFields(model);
        model.setAssType(MainetConstants.Property.ASESS_AUT);// to identify call of model from Authorization
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        getModel().setDocumentList(iChecklistVerificationService.getDocumentUploaded(applicationId, orgId));
        final List<ProvisionalAssesmentMstDto> provAssDtoList = provisionalAssesmentMstService
                .getAssesmentMstDtoListByAppId(applicationId, orgId);
        ProvisionalAssesmentMstDto amalgamatedDto = provAssDtoList.get(provAssDtoList.size() - 1);
        model.setProvisionalAssesmentMstDto(amalgamatedDto);
        model.setAmalParentPropDto(amalgamatedDto);
        List<LookUp> locList = getModel().getLocation();
        Organisation org = UserSession.getCurrent().getOrganisation();
        Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Property.PROP_DEPT_SHORT_CODE);
        List<LocationMasEntity> location = iLocationMasService.findWZMappedLocationByOrgIdAndDeptId(org.getOrgid(),
                deptId);
        if (location != null && !location.isEmpty()) {
            location.forEach(loc -> {
                LookUp lookUp = new LookUp();
                lookUp.setLookUpId(loc.getLocId());
                lookUp.setDescLangFirst(loc.getLocNameEng());
                lookUp.setDescLangSecond(loc.getLocNameReg());
                locList.add(lookUp);
            });
        }
        final List<FinancialYear> finYearList = iFinancialYear.getAllFinincialYear();
        String financialYear = null;
        for (final FinancialYear finYearTemp : finYearList) {
            financialYear = Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate());
            getModel().getFinancialYearMap().put(finYearTemp.getFaYear(), financialYear);
        }
        List<ProvisionalAssesmentFactorDtlDto> factorMap = factorMappingForView(model.getProvisionalAssesmentMstDto());
        model.setProvAsseFactDtlDto(factorMap);
        model.setOwnershipPrefix(CommonMasterUtility
                .getNonHierarchicalLookUpObject(model.getProvisionalAssesmentMstDto().getAssOwnerType(), org).getLookUpCode());
        if (model.getProvisionalAssesmentMstDto().getAssLandType() != null) {
            LookUp landTypePrefix = CommonMasterUtility.getNonHierarchicalLookUpObject(
                    model.getProvisionalAssesmentMstDto().getAssLandType(), UserSession.getCurrent().getOrganisation());
            getModel().setLandTypePrefix(landTypePrefix.getLookUpCode());
        }
        List<FinancialYear> financialYearList = iFinancialYear
                .getFinanceYearListFromGivenDate(UserSession.getCurrent().getOrganisation().getOrgid(),
                        model.getProvisionalAssesmentMstDto().getFaYearId(), new Date());
        if (!financialYearList.isEmpty()) {
            List<Long> finYearListDAta = new ArrayList<>();
            for (FinancialYear financialYearEach : financialYearList) {
                Long finYear = iFinancialYear.getFinanceYearId(financialYearEach.getFaFromDate());
                finYearListDAta.add(finYear);
            }
            this.getModel().setFinYearList(finYearListDAta);
        }
        if (getModel().getLandTypePrefix() != null) {
            setlandTypeDetails(getModel());
        }
        this.getModel().setDropDownValues();
        
        //check whether charges are applicable or not
		ServiceMaster serviceMas = serviceMaster.getServiceByShortName(MainetConstants.Property.Amalgamation,
				model.getOrgId());
		if (serviceMas != null && serviceMas.getSmFeesSchedule() != null && serviceMas.getSmFeesSchedule() != 0) {
			getCharges(model);
		}
        getModel().setServiceId(serviceId);

        String serviceShortCode = serviceMaster.fetchServiceShortCode(serviceId, orgId);
        getModel().setServiceShortCode(serviceShortCode);
        model.setIntgrtionWithBP(CommonMasterUtility
                .getDefaultValueByOrg(MainetConstants.Property.IBP, UserSession.getCurrent().getOrganisation()).getLookUpCode());
        getModel().checkListDecentralized(applicationId, serviceId, orgId);
        return new ModelAndView("AmalgamationView", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "getCheckList", method = RequestMethod.POST)
    public ModelAndView getCheckList(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        AmalgamationModel model = this.getModel();
        ProvisionalAssesmentMstDto dto = model.getProvisionalAssesmentMstDto();
        List<ProvisionalAssesmentFactorDtlDto> factDto = model.getProvAsseFactDtlDto();
        dto.setOrgId(model.getOrgId());
        dto.setSmServiceId(model.getServiceId());
        if (!"A".equals(model.getAssType())) {
            List<DocumentDetailsVO> docs = selfAssessmentService.fetchCheckList(dto, factDto);
            model.setCheckList(docs);
            if (docs.isEmpty()) {
                this.getModel().setDropDownValues();
                
                //check whether charges are applicable or not
                ServiceMaster serviceMas = serviceMaster.getServiceByShortName(MainetConstants.Property.Amalgamation, model.getOrgId());
				if (serviceMas != null && serviceMas.getSmFeesSchedule()!=null && serviceMas.getSmFeesSchedule()!=0) {										
					getCharges(model);
				}
                // D#18545 Error MSG for Rule found or not
                model.validateBean(model.getProvisionalAssesmentMstDto(), RuleErrorValidator.class);
                if (model.hasValidationErrors()) {
                    return defaultMyResult();
                }
                return new ModelAndView("AmalgamationView", MainetConstants.FORM_NAME, model);
            }
        }
        /*
         * Long max = 0l; List<ProvisionalAssesmentDetailDto> detailDto = model.getProvisionalAssesmentMstDto()
         * .getProvisionalAssesmentDetailDtoList(); if (detailDto != null && !detailDto.isEmpty()) { for
         * (ProvisionalAssesmentDetailDto det : detailDto) { if (det.getAssdUnitNo() > max) { max = det.getAssdUnitNo(); } } }
         * model.setMaxUnit(max);
         */
        model.setAssType(MainetConstants.Property.Amalgamation);
        return new ModelAndView("AmalgamationUpdateDetail", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "editAmalgamationForm", method = RequestMethod.POST)
    public ModelAndView editForm(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws Exception {
        getModel().bind(httpServletRequest);
        AmalgamationModel model = this.getModel();
        fileUpload.sessionCleanUpForFileUpload();
        getModel().setFinancialYearMap(new HashMap<Long, String>());
        getModel().setLocation(new ArrayList<>());
        List<LookUp> locList = getModel().getLocation();
        Organisation org = UserSession.getCurrent().getOrganisation();
        Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Property.PROP_DEPT_SHORT_CODE);
        List<LocationMasEntity> location = iLocationMasService.findWZMappedLocationByOrgIdAndDeptId(org.getOrgid(),
                deptId);
        if (location != null && !location.isEmpty()) {
            location.forEach(loc -> {
                LookUp lookUp = new LookUp();
                lookUp.setLookUpId(loc.getLocId());
                lookUp.setDescLangFirst(loc.getLocNameEng());
                lookUp.setDescLangSecond(loc.getLocNameReg());
                locList.add(lookUp);
            });
        }
        final List<FinancialYear> finYearList = iFinancialYear.getAllFinincialYear();
        String financialYear = null;
        for (final FinancialYear finYearTemp : finYearList) {
            financialYear = Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate());
            getModel().getFinancialYearMap().put(finYearTemp.getFaYear(), financialYear);
        }
        List<ProvisionalAssesmentFactorDtlDto> factorMap = factorMappingForView(model.getProvisionalAssesmentMstDto());
        model.setProvAsseFactDtlDto(factorMap);
        model.setOwnershipPrefix(CommonMasterUtility
                .getNonHierarchicalLookUpObject(model.getProvisionalAssesmentMstDto().getAssOwnerType(), org).getLookUpCode());
        if (model.getProvisionalAssesmentMstDto().getAssLandType() != null) {
            LookUp landTypePrefix = CommonMasterUtility.getNonHierarchicalLookUpObject(
                    model.getProvisionalAssesmentMstDto().getAssLandType(), UserSession.getCurrent().getOrganisation());
            getModel().setLandTypePrefix(landTypePrefix.getLookUpCode());
        }
        if (getModel().getLandTypePrefix() != null) {
            setlandTypeDetails(getModel());
        }
        this.getModel().setDropDownValues();
        // add parent property plot area + amalgamated property plot area [START]
        ProvisionalAssesmentMstDto assesmentMstDto = this.getModel().getProvisionalAssesmentMstDto();
        AmalgamationDto amalgamationDto = this.getModel().getAmalgamationDto();
        Double plotArea = assesmentMstDto.getAssPlotArea();
        List<String> result = Arrays.asList(amalgamationDto.getAmalgmatedPropNo().split("\\s*,\\s*"));
        List<Double> amalgmatedPlotArea = assesmentMastService
                .getPlotAreaByPropNo(org.getOrgid(), result);
        Double sum = amalgmatedPlotArea.stream().collect(Collectors.summingDouble(Double::doubleValue));
        this.getModel().getProvisionalAssesmentMstDto().setAssPlotArea(plotArea + sum);
        this.getModel().setAmalgamationDto(model.getAmalgamationDto());
        // [END]
        model.getProvisionalAssesmentMstDto().setAssPlotArea(Utility.round(model.getProvisionalAssesmentMstDto().getAssPlotArea(), 2));
        model.setAssType("");
        return new ModelAndView("AmalgamationFormEdit", MainetConstants.FORM_NAME, model);
    }

    private void setlandTypeDetails(AmalgamationModel model) {
        LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
        dto.setLandType(model.getLandTypePrefix());
        dto.setDistrictId(model.getProvisionalAssesmentMstDto().getAssDistrict());
        dto.setTehsilId(model.getProvisionalAssesmentMstDto().getAssTahasil());
        dto.setVillageId(model.getProvisionalAssesmentMstDto().getTppVillageMauja());
        dto.setMohallaId(model.getProvisionalAssesmentMstDto().getMohalla());
        dto.setStreetNo(model.getProvisionalAssesmentMstDto().getAssStreetNo());
        dto.setKhasraNo(model.getProvisionalAssesmentMstDto().getTppPlotNoCs());
        dto.setPlotNo(model.getProvisionalAssesmentMstDto().getTppPlotNo());
        model.setDistrict(dataEntrySuiteService.findDistrictByLandType(dto));
        model.setTehsil(dataEntrySuiteService.getTehsilListByDistrict(dto));
        model.setVillage(dataEntrySuiteService.getVillageListByTehsil(dto));
        if (model.getLandTypePrefix().equals(MainetConstants.Property.LandType.KPK)) {
            model.setKhasraNo(dataEntrySuiteService.getKhasraNoList(dto));
        }
        if (model.getLandTypePrefix().equals(MainetConstants.Property.LandType.NZL)
                || model.getLandTypePrefix().equals(MainetConstants.Property.LandType.DIV)) {
            getModel().setMohalla(dataEntrySuiteService.getMohallaListByVillageId(dto));
            getModel().setBlockStreet(dataEntrySuiteService.getStreetListByMohallaId(dto));
        }
        if (model.getLandTypePrefix().equals(MainetConstants.Property.LandType.NZL)) {
            getModel().setPlotNo(dataEntrySuiteService.getNajoolPlotList(dto));
        }
        if (model.getLandTypePrefix().equals(MainetConstants.Property.LandType.DIV)) {
            getModel().setPlotNo(dataEntrySuiteService.getDiversionPlotList(dto));

        }

    }

    @RequestMapping(params = "getOwnershipTypeInfoDiv", method = RequestMethod.POST)
    public ModelAndView getOwnershipTypeDiv(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestParam(value = "ownershipType") String ownershipType) {

        this.getModel().bind(httpServletRequest);
        AmalgamationModel model = this.getModel();

        model.setOwnershipPrefix(ownershipType);
        LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(ownershipType,
                MainetConstants.Property.propPref.OWT, UserSession.getCurrent().getOrganisation());

        model.setOwnershipTypeValue(lookup.getDescLangFirst());
        model.getProvisionalAssesmentMstDto().setAssOwnerType(lookup.getLookUpId());
		
        return new ModelAndView("SelfAssessmentOwnershipDetailForm", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "backToMainPage", method = RequestMethod.POST)
    public ModelAndView back(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        this.sessionCleanup(httpServletRequest);
        getModel().bind(httpServletRequest);
        AmalgamationModel model = this.getModel();

        return new ModelAndView("AmalgamationMainForm", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "saveProceed", method = RequestMethod.POST)
    public ModelAndView saveProceed(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        AmalgamationModel model = this.getModel();
        List<DocumentDetailsVO> docs = model.getCheckList();
        docs = fileUpload.prepareFileUpload(docs);
        ProvisionalAssesmentMstDto assMstDto = model.getProvisionalAssesmentMstDto();
        assMstDto.setDocs(docs);
        model.setDocumentList(new ArrayList<>());
        List<CFCAttachment> documentList = selfAssessmentService.preparePreviewOfFileUpload(model.getDocumentList(),
                model.getCheckList());
        model.setDocumentList(documentList);
        model.validateBean(assMstDto, SelfAssessmentValidator.class);
        if (model.hasValidationErrors()) {
            return new ModelAndView("AmalgamationUpdateDetail", MainetConstants.FORM_NAME, model)
                    .addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        }
        this.getModel().setDropDownValues();
        //check whether charges are applicable or not
		ServiceMaster serviceMas = serviceMaster.getServiceByShortName(MainetConstants.Property.Amalgamation,
				model.getOrgId());
		if (serviceMas != null && serviceMas.getSmFeesSchedule() != null && serviceMas.getSmFeesSchedule() != 0) {
			getCharges(model);
		}
        // D#18545 Error MSG for Rule found or not
        model.validateBean(model.getProvisionalAssesmentMstDto(), RuleErrorValidator.class);
        if (model.hasValidationErrors()) {
            return defaultMyResult();
        }
        return new ModelAndView("AmalgamationView", MainetConstants.FORM_NAME, model);
    }

    private void getCharges(AmalgamationModel model) {
        ProvisionalAssesmentMstDto parentDto = model.getProvisionalAssesmentMstDto();
        // propertyService.setWardZoneDetailByLocId(parentDto, model.getDeptId(), parentDto.getLocId());
        parentDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        parentDto.setSmServiceId(model.getServiceId());
        Map<Long, BillDisplayDto> taxWiseRebate = new TreeMap<>();// map<TxaId,BillDisplayDto>
        Map<Date, List<BillPresentAndCalculationDto>> schWiseChargeMap = propertyBRMSService.fetchCharges(
                model.getProvisionalAssesmentMstDto(), model.getDeptId(),
                taxWiseRebate, model.getFinYearList(), null, model.getAssType(), null,null);
        List<TbBillMas> billMasList = propertyService.generateNewBill(schWiseChargeMap,
                UserSession.getCurrent().getOrganisation(), model.getDeptId(), null,null);
        List<BillReceiptPostingDTO> reabteRecDtoList = selfAssessmentService.knowkOffDemandLevelRebateAndExemption(billMasList,
                schWiseChargeMap,
                UserSession.getCurrent().getOrganisation(), taxWiseRebate);
        model.setReabteRecDtoList(reabteRecDtoList);
        propertyService.interestCalculationWithoutBRMSCall(UserSession.getCurrent().getOrganisation(), model.getDeptId(),
                billMasList,
                MainetConstants.Property.INT_RECAL_NO);
        propertyService.taxCarryForward(billMasList, model.getOrgId());
        List<BillDisplayDto> earlyPayRebate = propertyBRMSService.fetchEarlyPayRebateRate(billMasList,
                UserSession.getCurrent().getOrganisation(), model.getDeptId(), null, null);
        Map<String, List<BillDisplayDto>> presentMap = selfAssessmentService.getDisplayMapOfTaxes(billMasList,
                UserSession.getCurrent().getOrganisation(), model.getDeptId(), earlyPayRebate, taxWiseRebate, null);
        model.getProvisionalAssesmentMstDto()
                .setBillTotalAmt(propertyService.getTotalActualAmount(billMasList, earlyPayRebate, taxWiseRebate, null));
        model.setBillMasList(billMasList);
        model.setDisplayMap(presentMap);

    }

    @RequestMapping(params = "editAmalgamationUpdateDetails", method = RequestMethod.POST)
    public ModelAndView editUpdateDetailsForm(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        AmalgamationModel model = this.getModel();
        model.setAuthEditFlag("Y");
//        model.setAssType(MainetConstants.Property.Amalgamation);
        return new ModelAndView("AmalgamationUpdateDetail", MainetConstants.FORM_NAME, model);
    }

    private List<ProvisionalAssesmentFactorDtlDto> factorMappingForView(final ProvisionalAssesmentMstDto assMst) {
        List<ProvisionalAssesmentFactorDtlDto> factorMap = new ArrayList<>();
        assMst.getProvisionalAssesmentDetailDtoList().forEach(propDet -> {
            propDet.getProvisionalAssesmentFactorDtlDtoList().forEach(fact -> {
                fact.setPropNo(propDet.getPropNo());
                fact.setUnitNoFact(propDet.getAssdUnitNo().toString());
                fact.setProAssfFactorIdDesc(CommonMasterUtility
                        .getNonHierarchicalLookUpObject(fact.getAssfFactorId(), UserSession.getCurrent().getOrganisation())
                        .getDescLangFirst());
                fact.setProAssfFactorValueDesc(CommonMasterUtility
                        .getNonHierarchicalLookUpObject(fact.getAssfFactorValueId(),
                                UserSession.getCurrent().getOrganisation())
                        .getDescLangFirst());
                fact.setFactorValueCode(CommonMasterUtility
                        .getNonHierarchicalLookUpObject(fact.getAssfFactorId(), UserSession.getCurrent().getOrganisation())
                        .getLookUpCode());
                factorMap.add(fact);
                assMst.getProAssfactor().add(MainetConstants.FlagY);
            });

        });
        return factorMap;
    }

    private void setCommonFields(AmalgamationModel model) {
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        ServiceMaster service = serviceMaster.getServiceByShortName(MainetConstants.Property.Amalgamation, orgId);
        model.setOrgId(orgId);
        model.setDeptId(service.getTbDepartment().getDpDeptid());
        model.setServiceId(service.getSmServiceId());
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(params = "downloadSheet", method = RequestMethod.GET)
    public void exportRateExcelData(final HttpServletResponse response, final HttpServletRequest request) {
        ProvisionalAssesmentMstDto dto = getModel().getProvisionalAssesmentMstDto();
        List<ExcelSheetDto> dtoList = new LinkedList<>();
        if (this.getModel().getBillMasList() != null && !this.getModel().getBillMasList().isEmpty()) {
            TbBillMas currBill = this.getModel().getBillMasList().get(this.getModel().getBillMasList().size() - 1);
            ExcelSheetDto excelHeadDto = new ExcelSheetDto();
            excelHeadDto.setTaxName(getApplicationSession().getMessage("propertyTax.TaxName"));
            excelHeadDto.setSrNo(getApplicationSession().getMessage("propertyTax.SrNo"));
            List<String> taxAmountList = new LinkedList<>();
            AtomicInteger count = new AtomicInteger(1);
            List<LookUp> billSchList = selfAssessmentService.getAllBillschedulByOrgid(UserSession.getCurrent().getOrganisation());
            this.getModel().getBillMasList().forEach(mas -> {
                BillingScheduleDetailEntity billSchDet = billingScheduleService
                        .getSchedulebySchFromDate(UserSession.getCurrent().getOrganisation().getOrgid(), mas.getBmFromdt());
                billSchList.parallelStream()
                        .filter(lookup -> billSchDet.getSchDetId().equals(lookup.getLookUpId()))// enter in loop if tax cat is
                        .forEach(lookup -> {
                            taxAmountList.add(lookup.getLookUpCode());
                        });
            });
            excelHeadDto.setTaxAmount(taxAmountList);// Setting Schedule name as header
            dtoList.add(excelHeadDto);// Excel Sheet Header Row as Header is also Dynamic
            // Taking all taxes from current Bill as tax carry forward
            currBill.getTbWtBillDet().forEach(taxDet -> {// for each Tax row
                ExcelSheetDto excelDto = new ExcelSheetDto();
                List<String> taxAmount = new LinkedList<>();
                excelDto.setTaxName(taxDet.getTaxDesc());// tax name
                this.getModel().getBillMasList().forEach(mas -> {
                    boolean hasTax = mas.getTbWtBillDet().stream()
                            .anyMatch(billDetail -> billDetail.getTaxId().equals(taxDet.getTaxId()));
                    mas.getTbWtBillDet().parallelStream()
                            .filter(det -> taxDet.getTaxId().equals(det.getTaxId()))
                            .forEach(det -> {
                                taxAmount.add(Double.toString(det.getBdCurTaxamt()));
                            });
                    if (!hasTax) {
                        taxAmount.add("0.0");
                    }
                });
                excelDto.setSrNo(count.toString());
                count.incrementAndGet();
                excelDto.setTaxAmount(taxAmount);
                dtoList.add(excelDto);
            });

            ExcelSheetDto excelTotalDto = new ExcelSheetDto();
            excelTotalDto.setTaxName("Total");
            List<String> totAmount = new LinkedList<>();

            this.getModel().getBillMasList().forEach(mas -> {
                totAmount.add(Double.toString(mas.getBmToatlInt() + mas.getBmTotalAmount()));
            });
            excelTotalDto.setTaxAmount(totAmount);
            dtoList.add(excelTotalDto);// Last row of Excel Sheet
        }
        try {
            WriteExcelData data = new WriteExcelData("Tax_Wise_Details_Sheet" + ".xlsx",
                    request, response);
            data.getExpotedExcelSheet(dtoList, ExcelSheetDto.class, dto.getBillTotalAmt());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(params = "getLandTypeDetails", method = RequestMethod.POST)
    public ModelAndView getLandTypeDetailsDiv(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestParam(value = "landType") String landType) {
        this.getModel().bind(httpServletRequest);
        AmalgamationModel model = this.getModel();
        model.setLandTypePrefix(landType);
        LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
        dto.setLandType(landType);
        List<LookUp> districtList = dataEntrySuiteService.findDistrictByLandType(dto);
        getModel().setDistrict(districtList);
        return new ModelAndView("PropertyLandDetails", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "getTehsilListByDistrict", method = RequestMethod.POST)
    public @ResponseBody List<LookUp> getObjectionServiceByDept(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse, @RequestParam("districtId") String districtId,
            @RequestParam(value = "landType") String landType) {
        this.getModel().getTehsil().clear();
        LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
        dto.setLandType(landType);
        dto.setDistrictId(districtId);
        List<LookUp> tehsilList = dataEntrySuiteService.getTehsilListByDistrict(dto);
        getModel().setTehsil(tehsilList);
        return tehsilList;
    }

    @RequestMapping(params = "getVillageListByTehsil", method = RequestMethod.POST)
    public @ResponseBody List<LookUp> getVillageListByTehsil(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse, @RequestParam("tehsilId") String tehsilId,
            @RequestParam("districtId") String districtId, @RequestParam(value = "landType") String landType) {
        this.getModel().getVillage().clear();
        LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
        dto.setLandType(landType);
        dto.setDistrictId(districtId);
        dto.setTehsilId(tehsilId);
        List<LookUp> villageList = dataEntrySuiteService.getVillageListByTehsil(dto);
        getModel().setVillage(villageList);
        return villageList;
    }

    @RequestMapping(params = "getMohallaListByVillageId", method = RequestMethod.POST)
    public @ResponseBody List<LookUp> getMohallaListByVillageId(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse, @RequestParam("villageId") String villageId,
            @RequestParam("tehsilId") String tehsilId, @RequestParam("districtId") String districtId,
            @RequestParam(value = "landType") String landType) {
        this.getModel().getMohalla().clear();
        LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
        dto.setLandType(landType);
        dto.setDistrictId(districtId);
        dto.setTehsilId(tehsilId);
        dto.setVillageId(villageId);
        List<LookUp> mohallaList = dataEntrySuiteService.getMohallaListByVillageId(dto);
        getModel().setMohalla(mohallaList);
        return mohallaList;
    }

    @RequestMapping(params = "getStreetListByMohallaId", method = RequestMethod.POST)
    public @ResponseBody List<LookUp> getStreetListByMohallaId(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse, @RequestParam("mohallaId") String mohallaId,
            @RequestParam("villageId") String villageId, @RequestParam("tehsilId") String tehsilId,
            @RequestParam("districtId") String districtId, @RequestParam(value = "landType") String landType) {
        this.getModel().getBlockStreet().clear();
        LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
        dto.setLandType(landType);
        dto.setDistrictId(districtId);
        dto.setTehsilId(tehsilId);
        dto.setVillageId(villageId);
        dto.setMohallaId(mohallaId);
        List<LookUp> streetList = dataEntrySuiteService.getStreetListByMohallaId(dto);
        getModel().setBlockStreet(streetList);
        return streetList;
    }

    @RequestMapping(params = "getKhasaraDetails", method = RequestMethod.POST)
    public ModelAndView getKhasaraDetails(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse, @RequestParam("landType") String landType,
            @RequestParam("khasara") String khasara, @RequestParam("districtId") String districtId,
            @RequestParam("TehsilId") String TehsilId, @RequestParam("villageId") String villageId) {
        LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
        dto.setLandType(landType);
        dto.setDistrictId(districtId);
        dto.setTehsilId(TehsilId);
        dto.setVillageId(villageId);
        dto.setKhasraNo(khasara);
        ArrayOfKhasraDetails response = dataEntrySuiteService.getKhasraDetails(dto);
        AmalgamationModel model = this.getModel();
        model.setArrayOfKhasraDetails(response);

        return new ModelAndView("showPropertyKhasaraDetails", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "getNajoolAndDiversionDetails", method = RequestMethod.POST)
    public ModelAndView getNajoolAndDiversionDetails(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse, @RequestParam("landType") String landType,
            @RequestParam("plotNo") String plotNo, @RequestParam("districtId") String districtId,
            @RequestParam("TehsilId") String TehsilId, @RequestParam("villageId") String villageId,
            @RequestParam("mohallaId") String mohallaId, @RequestParam("streetNo") String streetNo) {
        AmalgamationModel model = this.getModel();
        LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
        dto.setLandType(landType);
        dto.setDistrictId(districtId);
        dto.setTehsilId(TehsilId);
        dto.setVillageId(villageId);
        dto.setMohallaId(mohallaId);
        dto.setStreetNo(streetNo);
        dto.setPlotNo(plotNo);
        dto.setLandTypePrefix(landType);
        if (model.getLandTypePrefix().equals(MainetConstants.Property.LandType.NZL)) {
            ArrayOfPlotDetails response = dataEntrySuiteService.getNajoolDetails(dto);
            model.setArrayOfPlotDetails(response);
        } else {
            ArrayOfDiversionPlotDetails response = dataEntrySuiteService.getDiversionDetails(dto);
            model.setArrayOfDiversionPlotDetails(response);
        }

        return new ModelAndView("showPropertyKhasaraDetails", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "getLandTypeApiDetails", method = RequestMethod.POST)
    public ModelAndView getLandTypeApiDetails(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse) {
        AmalgamationModel model = this.getModel();
        LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
        dto.setLandType(model.getLandTypePrefix());
        dto.setDistrictId(model.getProvisionalAssesmentMstDto().getAssDistrict());
        dto.setTehsilId(model.getProvisionalAssesmentMstDto().getAssTahasil());
        dto.setVillageId(model.getProvisionalAssesmentMstDto().getTppVillageMauja());
        dto.setMohallaId(model.getProvisionalAssesmentMstDto().getMohalla());
        dto.setStreetNo(model.getProvisionalAssesmentMstDto().getAssStreetNo());
        dto.setKhasraNo(model.getProvisionalAssesmentMstDto().getTppPlotNoCs());
        dto.setPlotNo(model.getProvisionalAssesmentMstDto().getTppPlotNo());
        if (model.getLandTypePrefix().equals(MainetConstants.Property.LandType.KPK)) {
            ArrayOfKhasraDetails response = dataEntrySuiteService.getKhasraDetails(dto);
            model.setArrayOfKhasraDetails(response);
        } else if (model.getLandTypePrefix().equals(MainetConstants.Property.LandType.NZL)) {
            ArrayOfPlotDetails response = dataEntrySuiteService.getNajoolDetails(dto);
            model.setArrayOfPlotDetails(response);
        } else if (model.getLandTypePrefix().equals(MainetConstants.Property.LandType.DIV)) {
            ArrayOfDiversionPlotDetails response = dataEntrySuiteService.getDiversionDetails(dto);
            model.setArrayOfDiversionPlotDetails(response);
        }

        return new ModelAndView("showPropertyKhasaraDetails", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "getKhasraNoList", method = { RequestMethod.POST, RequestMethod.GET })
    public @ResponseBody List<LookUp> getKhasraNoList(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse, @RequestParam("villageId") String villageId,
            @RequestParam("tehsilId") String tehsilId, @RequestParam("districtId") String districtId,
            @RequestParam(value = "landType") String landType, @RequestParam("khasara") String khasara) {
        ProvisionalAssesmentMstDto assDto = getModel().getProvisionalAssesmentMstDto();
        LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
        dto.setLandType(landType);
        dto.setDistrictId(districtId);
        dto.setTehsilId(tehsilId);
        dto.setVillageId(villageId);
        dto.setKhasraNo(khasara);
        List<LookUp> khasraNoList = dataEntrySuiteService.getKhasraNoList(dto);
        assDto.setAssVsrNo(dto.getVillageCode());
        getModel().setKhasraNo(khasraNoList);
        return khasraNoList;
    }

    @RequestMapping(params = "getNajoolPlotList", method = { RequestMethod.POST, RequestMethod.GET })
    public @ResponseBody List<LookUp> getNajoolPlotList(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse, @RequestParam("landType") String landType,
            @RequestParam("districtId") String districtId,
            @RequestParam("TehsilId") String TehsilId, @RequestParam("villageId") String villageId,
            @RequestParam("mohallaId") String mohallaId, @RequestParam("streetNo") String streetNo,
            @RequestParam("plotNo") String plotNo) {
        LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
        dto.setLandType(landType);
        dto.setDistrictId(districtId);
        dto.setTehsilId(TehsilId);
        dto.setVillageId(villageId);
        dto.setMohallaId(mohallaId);
        dto.setStreetNo(streetNo);
        dto.setPlotNo(plotNo);
        List<LookUp> najoolPlotList = dataEntrySuiteService.getNajoolPlotList(dto);
        getModel().setPlotNo(najoolPlotList);
        return najoolPlotList;
    }

    @RequestMapping(params = "getDiversionPlotList", method = { RequestMethod.POST, RequestMethod.GET })
    public @ResponseBody List<LookUp> getDiversionPlotList(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse, @RequestParam("landType") String landType,
            @RequestParam("districtId") String districtId,
            @RequestParam("TehsilId") String TehsilId, @RequestParam("villageId") String villageId,
            @RequestParam("mohallaId") String mohallaId, @RequestParam("streetNo") String streetNo,
            @RequestParam("plotNo") String plotNo) {
        LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
        dto.setLandType(landType);
        dto.setDistrictId(districtId);
        dto.setTehsilId(TehsilId);
        dto.setVillageId(villageId);
        dto.setMohallaId(mohallaId);
        dto.setStreetNo(streetNo);
        dto.setPlotNo(plotNo);
        List<LookUp> diversionPlotList = dataEntrySuiteService.getDiversionPlotList(dto);
        getModel().setPlotNo(diversionPlotList);
        return diversionPlotList;
    }

    @RequestMapping(params = "deleteLandEntry", method = RequestMethod.POST)
    public @ResponseBody void deleteLandEntry(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        this.getModel().bind(httpServletRequest);
        AmalgamationModel model = this.getModel();
        model.getProvisionalAssesmentMstDto().setAssLandType(null);
        model.getProvisionalAssesmentMstDto().setAssLandTypeDesc(null);
        model.setLandTypePrefix(null);
        model.getProvisionalAssesmentMstDto().setAssDistrict(null);
        model.getProvisionalAssesmentMstDto().setAssDistrictDesc(null);
        model.getProvisionalAssesmentMstDto().setAssTahasil(null);
        model.getProvisionalAssesmentMstDto().setAssTahasilDesc(null);
        model.getProvisionalAssesmentMstDto().setTppVillageMauja(null);
        model.getProvisionalAssesmentMstDto().setTppVillageMaujaDesc(null);
        model.getProvisionalAssesmentMstDto().setMohalla(null);
        model.getProvisionalAssesmentMstDto().setMohallaDesc(null);
        model.getProvisionalAssesmentMstDto().setAssStreetNo(null);
        model.getProvisionalAssesmentMstDto().setAssStreetNoDesc(null);
        model.getProvisionalAssesmentMstDto().setTppPlotNo(null);
        model.getProvisionalAssesmentMstDto().setTppPlotNoCs(null);
        model.setArrayOfKhasraDetails(null);
        model.setArrayOfDiversionPlotDetails(null);
        model.setArrayOfPlotDetails(null);
    }
    
    private boolean checkCurrentYearBillPaymentDone(Long finId, String propNo) {
    	AtomicBoolean payStatus = new AtomicBoolean(true);
    	if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "BRV")) {
    		List<TbBillMas> currentYearBillMas = propertyMainBillService.fetchAllBillByPropNoAndCurrentFinId(propNo, UserSession.getCurrent().getOrganisation().getOrgid(), finId);
    		if(CollectionUtils.isEmpty(currentYearBillMas)) {
    			payStatus.getAndSet(false);
    		}
    	}
    	
    	return payStatus.get();
    	
    }

}
