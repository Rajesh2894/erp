package com.abm.mainet.property.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.dto.DuplicateBillDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.repository.TbTaxMasJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.repository.ReceiptRepository;
import com.abm.mainet.common.service.IDuplicateBillService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.property.dao.IAssessmentMastDao;
import com.abm.mainet.property.dao.IProvisionalAssessmentMstDao;
import com.abm.mainet.property.domain.AssesmentMastEntity;
import com.abm.mainet.property.domain.AssesmentOwnerDtlEntity;
import com.abm.mainet.property.domain.MainBillMasEntity;
import com.abm.mainet.property.dto.NoticeGenSearchDto;
import com.abm.mainet.property.dto.PrintBillDetails;
import com.abm.mainet.property.dto.PrintBillMaster;
import com.abm.mainet.property.dto.PropertyPenltyDto;
import com.abm.mainet.property.dto.ProperySearchDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentOwnerDtlDto;
import com.abm.mainet.property.repository.AssesmentMstRepository;
import com.abm.mainet.property.repository.PropertyMainBillRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.AtomicDouble;

@Service
public class PropertyBillGenerationServiceImpl implements PropertyBillGenerationService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertyBillGenerationServiceImpl.class);

    @Autowired
    private AssesmentMstRepository assesmentMstRepository;


    @Autowired
    private ILocationMasService iLocationMasService;

    @Autowired
    private IAssessmentMastDao iAssessmentMastDao;

    @Autowired
    private IFinancialYearService iFinancialYearService;

    @Autowired
    private PropertyMainBillRepository propertyMainBillRepository;

    @Autowired
    private TbTaxMasService tbTaxMasService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private AsExecssAmtService asExecssAmtService;

    @Autowired
    private PropertyMainBillService propertyMainBillService;

    @Autowired
    private IProvisionalAssesmentMstService iProvisionalAssesmentMstService;

    @Autowired
    private AssesmentMastService assesmentMastService;

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private IOrganisationService iOrganisationService;
    
    @Autowired
    private IProvisionalAssessmentMstDao provisionalAssessmentMstDao;  
    
    @Autowired
    private IAssessmentMastDao assessmentMastDao;
    
    @Autowired
    private IOrganisationService organisationService;
    
    @Autowired
    private TbTaxMasJpaRepository tbTaxMasJpaRepository;
    
    @Autowired
    private ReceiptRepository receiptRepository;

    @Transactional
    @Override
    public List<NoticeGenSearchDto> getAllPropWithAuthChangeByPropNo(NoticeGenSearchDto specNotSearchDto, Long orgId) {
        Long finId = iFinancialYearService.getFinanceYearId(new Date());
        List<String> propNos = new ArrayList<>();
        List<String> propNoList = new ArrayList<>();
        List<NoticeGenSearchDto> notGenShowList = new ArrayList<>();
        List<AssesmentMastEntity> assMasList = null;     
        List<Object[]> result = iAssessmentMastDao.getAllPropBillGeneByPropNo(finId, orgId);
		List<Object[]> propNoWithoutBills = iAssessmentMastDao.fetchPropNoWhoseBillNotPresent(orgId, specNotSearchDto);
		if (result != null && !result.isEmpty() || (CollectionUtils.isNotEmpty(propNoWithoutBills))) {
            result.forEach(prop -> {
                propNos.add(prop[2].toString());
            });
			propNoWithoutBills.forEach(prop -> {
				propNos.add(prop[0].toString());
			});
            List<String> propList = new ArrayList<>(propNos);
           
            if (specNotSearchDto.getPropertyNo() != null && !specNotSearchDto.getPropertyNo().isEmpty()) {
                String[] propNo = specNotSearchDto.getPropertyNo().split(MainetConstants.operator.COMMA);
                for (String s : propNo) {
                    propNoList.add(s);
                }
                propList.retainAll(propNoList);

            } else if (specNotSearchDto.getOldPropertyNo() != null && !specNotSearchDto.getOldPropertyNo().isEmpty()) {
                List<String> oldNoList = new ArrayList<>();
                String[] propNo = specNotSearchDto.getOldPropertyNo().split(MainetConstants.operator.COMMA);
                for (String s : propNo) {
                    oldNoList.add(s);
                }
                List<String> propNoOld = assesmentMstRepository.getAllPropBillGeneByOldPropNo(orgId, oldNoList);
                propList.retainAll(propNoOld);
            }
			else if (StringUtils.isNotBlank(specNotSearchDto.getParentPropNo())
					|| StringUtils.isNotBlank(specNotSearchDto.getParentPropName())) {
				List<String> propertyNoList = new ArrayList<>();				
				propertyNoList = assessmentMastDao.fetchAssessmentByGroupPropNo(orgId,
						specNotSearchDto.getParentPropNo(), specNotSearchDto.getParentPropName(),
						MainetConstants.FlagA);
				propList.retainAll(propertyNoList);
			}

            Map<Long,String> locNameMap=new HashMap<>();
            if (propList != null && !propList.isEmpty()) {
                assMasList = assesmentMstRepository.fetchAllAssessmentForBillGneeration(finId, propList);
				List<TbLocationMas> locList = iLocationMasService
						.findAllLocationsForOrg(organisationService.getOrganisationById(orgId));				
				locList.forEach(loc ->{
					locNameMap.put(loc.getLocId(), loc.getLocNameEng());
				});               
            }
            if (CollectionUtils.isNotEmpty(assMasList)) {
            	for(AssesmentMastEntity assMas:assMasList) {               
                    NoticeGenSearchDto notGen = new NoticeGenSearchDto();
                    notGen.setPropertyNo(assMas.getAssNo());
                    notGen.setApplicationId(assMas.getApmApplicationId());
                    notGen.setAssId(assMas.getProAssId());
                    notGen.setLocationName(locNameMap.get(assMas.getLocId()));
                    List<AssesmentOwnerDtlEntity> ownerList = assMas.getAssesmentOwnerDetailEntityList();
                    for (AssesmentOwnerDtlEntity owner : ownerList) {
                        if (owner.getAssoOType() != null && owner.getAssoOType().equals(MainetConstants.Property.PRIMARY_OWN)) {
                            notGen.setOwnerName(owner.getAssoOwnerName());
                        }
                    }
                    notGen.setGenNotCheck(MainetConstants.Y_FLAG);
					if (StringUtils.isNotBlank(assMas.getIsGroup())) {
						notGen.setIsGroup(assMas.getIsGroup());
					}
					notGen.setParentPropNo(assMas.getParentPropNo());
                    notGenShowList.add(notGen);
                }
            }
        }
        return notGenShowList;
    }

    @Override
    @Transactional
    public List<NoticeGenSearchDto> fetchAssDetailBySearchCriteria(NoticeGenSearchDto specNotSearchDto, long orgId) {
    	LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " fetchAssDetailBySearchCriteria() method"+orgId);
        List<NoticeGenSearchDto> notGenShowList = new ArrayList<>();
        Long finId = iFinancialYearService.getFinanceYearId(new Date());
        List<String> propNos = new ArrayList<>();
            List<Object[]> propNoObjList = getPropNoListBySearchCriteria(specNotSearchDto,null,propNos);
            Map<String, String> oldPropList = new HashMap<String, String>();
            propNoObjList.forEach(propObj ->{
            	propNos.add(propObj[0].toString());
            	if(propObj[1] != null) {
            		oldPropList.put(propObj[0].toString(), propObj[1].toString());
            	}else {
            		oldPropList.put(propObj[0].toString(), null);
            	}
            });
                       
		if (CollectionUtils.isNotEmpty(propNos)) {
			LOGGER.info("Property list is created size is : " + propNos.size());
			List<Object[]> result = iAssessmentMastDao.getAllPropBillGeneByPropNoList(finId, orgId, propNos);
			result.forEach(obj -> {
				Optional<NoticeGenSearchDto> checkPropExists = null;
				
				if (CollectionUtils.isNotEmpty(notGenShowList)) {
						checkPropExists = notGenShowList.stream()
								.filter(result1 -> result1.getPropertyNo().equals(obj[2])).findFirst();
				}

				if(checkPropExists != null && !checkPropExists.isPresent()) {
					String oldPropNo = oldPropList.get(obj[2].toString());
					NoticeGenSearchDto notGen = new NoticeGenSearchDto();
					notGen.setPropertyNo(obj[2].toString());
					notGen.setOldPropertyNo(oldPropNo);
					notGen.setGenNotCheck(MainetConstants.Y_FLAG);
					notGenShowList.add(notGen);
				}
				
			});
		}
		
		List<Object[]> propNoWithoutBills = iAssessmentMastDao.fetchPropNoWhoseBillNotPresent(orgId, specNotSearchDto);
		propNoWithoutBills.forEach(billMissingprop -> {
			NoticeGenSearchDto notGen = new NoticeGenSearchDto();
			notGen.setPropertyNo(billMissingprop[0].toString());
			if(billMissingprop[1] != null) {
				notGen.setOldPropertyNo(billMissingprop[1].toString());
			}
			notGen.setGenNotCheck(MainetConstants.Y_FLAG);
			notGenShowList.add(notGen);
		});
        LOGGER.info("End--> " + this.getClass().getSimpleName() + " fetchAssDetailBySearchCriteria() method & orgId : "+orgId+ "notGenShowList size : "+notGenShowList.size());
        return notGenShowList;
    }

    @Override
    @Transactional
    public List<PrintBillMaster> getBillPrintingData(List<NoticeGenSearchDto> notGenSearchDtoList, Organisation organisation,
            int langId) {
        List<PrintBillMaster> printMas = new ArrayList<>(0);
        List<String> propId = new ArrayList<>();
        List<Long> billId = new ArrayList<>();
        notGenSearchDtoList.stream()
                .filter(dto -> MainetConstants.NewWaterServiceConstants.YES.equals(dto.getGenNotCheck()))
                .forEach(dto -> {
                    propId.add(dto.getPropertyNo());
                });
        List<Object[]> bill = propertyMainBillRepository.fetchCurrentBillByPropNo(propId);
        if (bill != null && !bill.isEmpty()) {
            bill.forEach(value -> {
                billId.add(Long.valueOf(value[1].toString()));
            });
            List<TbBillMas> mainBill = propertyMainBillService.fetchBillFromBmIdNos(billId);
            if (mainBill != null && !mainBill.isEmpty()) {
                mainBill.forEach(mainbill -> {
                    PrintBillMaster printDto = getPrintBillMasterBillGeanertion(organisation, langId, mainbill, null);
                    printMas.add(printDto);
                });
            }
        }
        return printMas;
    }
    @Override
    @Transactional
    public PrintBillMaster getPrintBillMasterBillGeanertion(Organisation organisation, final int langId,
            TbBillMas mainbill, FileNetApplicationClient fileNetApp) {
        ProvisionalAssesmentMstDto assessment = iProvisionalAssesmentMstService
                .fetchPropertyByPropNo(mainbill.getPropNo(), organisation.getOrgid());
        if (assessment == null) {
        	if(StringUtils.isNotBlank(mainbill.getFlatNo())) {
        		assessment = assesmentMastService.fetchLatestAssessmentByPropNoAndFlatNo(organisation.getOrgid(), mainbill.getPropNo(),mainbill.getFlatNo());
        	}else {
        		assessment = assesmentMastService.fetchLatestAssessmentByPropNo(organisation.getOrgid(), mainbill.getPropNo());
        	}
        }
        Map<Long, String> tax = new HashMap<>(0);
        Map<Long, Long> taxDisplaySeq = new HashMap<>(0);
        Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Property.PROP_DEPT_SHORT_CODE, "A");
        final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
                MainetConstants.Property.propPref.BILL, MainetConstants.Property.propPref.CAA, organisation);
        final LookUp chargeApplicableAtBillRec = CommonMasterUtility.getValueFromPrefixLookUp(
                MainetConstants.Property.propPref.BILL_RECEIPT, MainetConstants.Property.propPref.CAA, organisation);
        List<TbTaxMas> taxes = tbTaxMasService.findAllTaxesForBillGeneration(organisation.getOrgid(),
        		deptId, chargeApplicableAt.getLookUpId(), null);
        List<TbTaxMas> taxesBillRece = tbTaxMasService.findAllTaxesForBillGeneration(organisation.getOrgid(),
        		deptId, chargeApplicableAtBillRec.getLookUpId(), null);
        List<TbTaxMas> notActiveTaxes = tbTaxMasService.findAllNotActiveTaxesForBillGeneration(organisation.getOrgid(),
        		deptId, chargeApplicableAt.getLookUpId(), null);
        if (taxes != null && !taxes.isEmpty()) {
            taxes.forEach(t -> {
                tax.put(t.getTaxId(), t.getTaxDesc());
                taxDisplaySeq.put(t.getTaxId(), t.getTaxDisplaySeq());
            });
        }
        if ((notActiveTaxes != null && !notActiveTaxes.isEmpty())) {
            notActiveTaxes.forEach(t -> {
                tax.put(t.getTaxId(), t.getTaxDesc());
                taxDisplaySeq.put(t.getTaxId(), t.getTaxDisplaySeq());
            });
        }

        if (taxesBillRece != null && !taxesBillRece.isEmpty()) {
            taxesBillRece.forEach(t -> {
                tax.put(t.getTaxId(), t.getTaxDesc());
                taxDisplaySeq.put(t.getTaxId(), t.getTaxDisplaySeq());
            });
        }
        
        final String deptName = departmentService.getDepartment(MainetConstants.Property.PROP_DEPT_SHORT_CODE, "A")
                .getDpDeptdesc();
        Long orgId = organisation.getOrgid();
        SimpleDateFormat sm = new SimpleDateFormat(MainetConstants.DATE_FRMAT);
        PrintBillMaster printDto = new PrintBillMaster();
        printDto.setDeptNameL(deptName);
        printDto.setOrgName(organisation.getONlsOrgname());
        printDto.setHeadingL(organisation.getONlsOrgname());
        if(Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_SKDCL)) {
        	 try {
				printDto.setYearL(Utility.getFinancialYearFromDate(mainbill.getBmFromdt()));
				//US#151175
				String temp[] = Utility.getFinancialYearFromDate(mainbill.getBmFromdt()).split(MainetConstants.HYPHEN);
				printDto.setFirstHalfFromDate("01.04." + temp[0]);
				printDto.setFirstHalfToDate("30.09." + temp[0]);
				printDto.setSecondHalfFromDate("01.10." + temp[0]);
				printDto.setSecondHalfToDate("31.03." + temp[1]);
				String tem[] = Utility.getFinancialYearFromDate(new Date()).split(MainetConstants.HYPHEN);
				printDto.setFirstHalfDueDate("31.08." + tem[0]);
				printDto.setSecondHalfDueDate("31.12." + tem[0]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }else {
        	 printDto.setYearL("Year From " + UtilityService.convertDateToDDMMYYYY(mainbill.getBmFromdt()) + " To "
                     + UtilityService.convertDateToDDMMYYYY(mainbill.getBmTodt()));
        }
       
		printDto.setFromDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(mainbill.getBmFromdt()));
		printDto.setToDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(mainbill.getBmTodt()));
        printDto.setBillNoV(mainbill.getBmNo());
        printDto.setPropNoV(mainbill.getPropNo());
        printDto.setBillDateV(sm.format(mainbill.getBmBilldt()));
        printDto.setOldPropNoV(assessment.getAssOldpropno());
        LookUp billMethodLookUp = null;
        try {
        	billMethodLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(assessment.getBillMethod(), organisation);
        }catch(Exception e) {
        	LOGGER.info("No prefix found for billing method");
        }
        if(billMethodLookUp != null) {
        	printDto.setBillingMethod(billMethodLookUp.getLookUpCode());
        }
        if(StringUtils.isNotBlank(assessment.getFlatNo())) {
        	printDto.setFlatNo(assessment.getFlatNo());
        }else {
        	printDto.setFlatNo(MainetConstants.BLANK);
        }
        printDto.setClusterNo(assessment.getClusterNo());
        printDto.setBuildingNo(assessment.getBuildingNo());
        String flatNo = assessment.getFlatNo();
        final List<LookUp> lookupList = CommonMasterUtility.getListLookup(MainetConstants.Property.propPref.WZB, organisation);

        if (assessment.getAssWard1() != null) {
            if (langId == 0) {
                printDto.setWard1L(lookupList.get(0).getDescLangFirst());// need to change label from prefix for hierarchical
            } else {
                printDto.setWard1L(lookupList.get(0).getDescLangSecond());
            }
            printDto.setWard1V(CommonMasterUtility.getHierarchicalLookUp(assessment.getAssWard1(), organisation)
                    .getDescLangFirst());
        }
        if (assessment.getAssWard2() != null) {
            if (langId == 0) {
                printDto.setWard2L(lookupList.get(1).getDescLangFirst());// need to change label from prefix for hierarchical
            } else {
                printDto.setWard2L(lookupList.get(1).getDescLangSecond());
            }
            printDto.setWard2V(CommonMasterUtility.getHierarchicalLookUp(assessment.getAssWard2(), organisation)
                    .getDescLangFirst());
        }
        if (assessment.getAssWard3() != null) {

            if (langId == 0) {
                printDto.setWard3L(lookupList.get(2).getDescLangFirst());// need to change label from prefix for hierarchical
            } else {
                printDto.setWard3L(lookupList.get(2).getDescLangSecond());
            }
            printDto.setWard3V(CommonMasterUtility.getHierarchicalLookUp(assessment.getAssWard3(), organisation)
                    .getDescLangFirst());
        }
        if (assessment.getAssWard4() != null) {
            if (langId == 0) {
                printDto.setWard4L(lookupList.get(3).getDescLangFirst());// need to change label from prefix for hierarchical
            } else {
                printDto.setWard4L(lookupList.get(3).getDescLangSecond());
            }
            printDto.setWard4V(CommonMasterUtility.getHierarchicalLookUp(assessment.getAssWard4(), organisation)
                    .getDescLangFirst());
        }
        if (assessment.getAssWard5() != null) {
            if (langId == 0) {
                printDto.setWard5L(lookupList.get(4).getDescLangFirst());// need to change label from prefix for hierarchical
            } else {
                printDto.setWard5L(lookupList.get(4).getDescLangSecond());
            }
            printDto.setWard5V(CommonMasterUtility.getHierarchicalLookUp(assessment.getAssWard5(), organisation)
                    .getDescLangFirst());
        }
        printDto.setAnnualRentAmtL(ApplicationSession.getInstance().getMessage("prop.bill.print.alv"));
        printDto.setIllegalFlag(MainetConstants.FlagY);
        AtomicDouble totalAlv = new AtomicDouble(0);
        AtomicDouble totalRv = new AtomicDouble(0);
        AtomicDouble totalcv = new AtomicDouble(0);
        AtomicDouble buildUpArea = new AtomicDouble(0);
        assessment.getProvisionalAssesmentDetailDtoList().forEach(detail -> {
        	if(Utility.isEnvPrefixAvailable(organisation, "SKDCL") && detail.getAssdUsagetype1() != null) {
        		if(StringUtils.isNotBlank(detail.getLegal()) && StringUtils.equals("Legal", detail.getLegal())) {
        			printDto.setIllegalFlag(MainetConstants.FlagN);
        		}
        		LookUp lookUp = CommonMasterUtility.getHierarchicalLookUp(detail.getAssdUsagetype1(), organisation);
        		if(lookUp != null) {
        			if(lookUp.getLookUpCode().equals("MRES")) {
        				printDto.setUsageTypeMixed(lookUp.getDescLangFirst());
        				if(detail.getAssdRv() != null) {
        					printDto.setMixedRv(printDto.getMixedRv() + detail.getAssdRv());
        				}
        			}else if(lookUp.getLookUpCode().equals("NRES")) {
        				printDto.setUsageTypeNonResidential(lookUp.getDescLangFirst());
        				if(detail.getAssdRv() != null) {
        					printDto.setNonResidentialRv(printDto.getNonResidentialRv() + detail.getAssdRv());
        				}
        			}else if(lookUp.getLookUpCode().equals("NA")) {
        				printDto.setUsageTypeNotStated(lookUp.getDescLangFirst());
        				if(detail.getAssdRv() != null) {
        					printDto.setNotStatedRv(printDto.getNotStatedRv() + detail.getAssdRv());
        				}
        			}else if(lookUp.getLookUpCode().equals("RES")) {
        				printDto.setUsageTypeResidential(lookUp.getDescLangFirst());
        				if(detail.getAssdRv() != null) {
        					printDto.setResidentialRv(printDto.getResidentialRv() + detail.getAssdRv());
        				}
        			}
        		}
        		
        	}
        	if (detail.getAssdBuildupArea() != null) {
        		buildUpArea.getAndAdd(detail.getAssdBuildupArea());
            }
        	if(StringUtils.isNotBlank(flatNo)) {
        		printDto.setOccupierName(detail.getOccupierName());
        	}
        	
            if (detail.getAssdAlv() != null) {
                totalAlv.getAndAdd(detail.getAssdAlv());
            }
            if (detail.getAssdRv() != null) {
                totalRv.getAndAdd(detail.getAssdRv());
            }
            if (detail.getAssdCv() != null)
                totalcv.addAndGet(detail.getAssdCv());
            if(detail.getAssdNatureOfproperty1() !=null) {
        		printDto.setNatureOfProperty1(CommonMasterUtility
                        .getHierarchicalLookUp(detail.getAssdNatureOfproperty1(), organisation).getDescLangFirst());
        	}
            if(detail.getAssdNatureOfproperty2() !=null) {
        		printDto.setNatureOfProperty2(CommonMasterUtility
                        .getHierarchicalLookUp(detail.getAssdNatureOfproperty2(), organisation).getDescLangFirst());
        	}
            if(detail.getAssdNatureOfproperty3() !=null) {
        		printDto.setNatureOfProperty3(CommonMasterUtility
                        .getHierarchicalLookUp(detail.getAssdNatureOfproperty3(), organisation).getDescLangFirst());
        	}
            if(detail.getAssdNatureOfproperty4() !=null) {
        		printDto.setNatureOfProperty4(CommonMasterUtility
                        .getHierarchicalLookUp(detail.getAssdNatureOfproperty4(), organisation).getDescLangFirst());
        	}
            if(detail.getAssdNatureOfproperty5() !=null) {
        		printDto.setNatureOfProperty5(CommonMasterUtility
                        .getHierarchicalLookUp(detail.getAssdNatureOfproperty5(), organisation).getDescLangFirst());
        	}
        });
        printDto.setTotAlvV(totalAlv.get());
        printDto.setTotRvV(totalRv.get());
        printDto.setCvV(totalcv.get());
        printDto.setBuildUpArea(buildUpArea.doubleValue());
        printDto.setAnnualRentAmtV(totalAlv.toString());
        ProvisionalAssesmentOwnerDtlDto owner = assessment.getProvisionalAssesmentOwnerDtlDtoList().get(0);
        StringBuilder name = new StringBuilder(owner.getAssoOwnerName());
        if (owner.getRelationId() != null) {
            name.append(" " + CommonMasterUtility.getNonHierarchicalLookUpObject(owner.getRelationId(), organisation)
                    .getDescLangFirst());
            name.append(" " + owner.getAssoGuardianName());
        }
        printDto.setNameL(ApplicationSession.getInstance().getMessage("ownerdetail.Ownername"));
        printDto.setNameV(name.toString());
        printDto.setAddressL(ApplicationSession.getInstance().getMessage("property.Address"));
        String locName = iLocationMasService.getLocationNameById(assessment.getLocId(), organisation.getOrgid());
        StringBuilder address = new StringBuilder();
        address.append(assessment.getAssAddress());
        if(StringUtils.isNotBlank(locName)) {
        	address.append(locName);
        }
        if(assessment.getAssPincode() != null && assessment.getAssPincode() >0) {
        	address.append(address);
        }
        printDto.setAddressV(address.toString());
        AtomicDouble totalArrear = new AtomicDouble(0);
        AtomicDouble totalCurrent = new AtomicDouble(0);
        AtomicDouble firstHalTotalCurrent = new AtomicDouble(0);
        AtomicDouble secondHalfTotalCurrent = new AtomicDouble(0);
        mainbill.getTbWtBillDet().forEach(billdet -> {
            PrintBillDetails printDetails = new PrintBillDetails();
            printDetails
                    .setTaxNameV(tbTaxMasService.findTaxByTaxIdAndOrgId(billdet.getTaxId(), organisation.getOrgid())
                            .getTaxDesc());
            if(Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_SKDCL)) {
            	totalArrear.addAndGet(billdet.getBdPrvBalArramt());
                printDetails.setArrearsAmountV(billdet.getBdPrvBalArramt());
            }else {
            	totalArrear.addAndGet(billdet.getBdPrvArramt());
                printDetails.setArrearsAmountV(billdet.getBdPrvArramt());
            }
            printDetails.setCurrentAmountV(billdet.getBdCurTaxamt());
            printDetails.setDisplaySeq(taxDisplaySeq.get(billdet.getTaxId()));
            if(billdet.getPercentageRate() != null) {
            	printDetails.setPercentageRate(billdet.getPercentageRate());
            }
            totalCurrent.addAndGet(billdet.getBdCurTaxamt());
            //US#151175
            if(Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_SKDCL)) {
            	printDetails.setFirstHalfCurrentAmountV(billdet.getBdCurTaxamt()/2);
            	printDetails.setSecondHalfCurrentAmountV(billdet.getBdCurTaxamt()/2);
            	firstHalTotalCurrent.addAndGet(billdet.getBdCurTaxamt()/2);
            	secondHalfTotalCurrent.addAndGet(billdet.getBdCurTaxamt()/2);
            	printDetails.setTotalAmountV(billdet.getBdCurTaxamt() + billdet.getBdPrvBalArramt());
            }else {
            	printDetails.setTotalAmountV(billdet.getBdCurTaxamt() + billdet.getBdPrvArramt());
            }
            printDto.getPrintBillDetails().add(printDetails);
        });
        PropertyPenltyDto lastSurcharge = ApplicationContextProvider.getApplicationContext()
				.getBean(PropertyPenltyService.class).getLastClaculatedSurcharge(mainbill.getPropNo(), organisation.getOrgid());
        if(lastSurcharge != null) {
        	 PrintBillDetails printDetails = new PrintBillDetails();
        	 LookUp taxSubCatLookup = CommonMasterUtility.getHieLookupByLookupCode(MainetConstants.Property.SURCHARGE,
                     PrefixConstants.LookUpPrefix.TAC, 2, organisation.getOrgid());
             final LookUp taxAppAtBill = CommonMasterUtility.getValueFromPrefixLookUp(
                     MainetConstants.Property.propPref.RCPT, MainetConstants.Property.propPref.CAA,
                     organisation);
             List<TbTaxMasEntity> taxList = tbTaxMasService.findAllTaxesByChargeAppAtAndTaxSubCat(organisation.getOrgid(), deptId,
                     taxAppAtBill.getLookUpId(), taxSubCatLookup.getLookUpId());
             printDetails
                     .setTaxNameV(taxList.get(0).getTaxDesc());
             printDetails.setCurrentAmountV(lastSurcharge.getActualAmount());
             if(taxList.get(0).getTaxId() != null) {
            	 printDetails.setDisplaySeq(taxDisplaySeq.get(taxList.get(0).getTaxId()));
             }
             totalCurrent.addAndGet(lastSurcharge.getActualAmount());
             printDetails.setTotalAmountV(lastSurcharge.getActualAmount());
             printDto.getPrintBillDetails().add(printDetails);
        }
		List<TbServiceReceiptMasEntity> rebateList = receiptRepository
				.getRebateByAdditionalRefNo(organisation.getOrgid(), mainbill.getPropNo());
		BigDecimal demandPaidAmount = receiptRepository.getPaidAmountByAdditionalRefNoDemandAmount(organisation.getOrgid(), mainbill.getPropNo(),mainbill.getBmIdno(),mainbill.getBmBilldt());
		if(demandPaidAmount != null) {
			printDto.setReceivedAmount(Utility.round(Math.round(demandPaidAmount.doubleValue()), 2));
		}
		printDto.setTotalAmount(Utility.round(((totalCurrent.doubleValue() + totalArrear.doubleValue()) - printDto.getReceivedAmount()), 2));
		printDto.setTotalAmountInWords(Utility.convertNumberToWord(printDto.getTotalAmount()));
		
		AtomicDouble rebateAmount = new AtomicDouble(0);
		if(Utility.isEnvPrefixAvailable(organisation, "SKDCL") && CollectionUtils.isNotEmpty(rebateList)) {
			AtomicDouble rebateOnRain = new AtomicDouble(0);
			AtomicDouble rebateOnSendriya = new AtomicDouble(0);
			AtomicDouble rebateOnSolar = new AtomicDouble(0);
			AtomicDouble rebateSainik = new AtomicDouble(0);
			
			List<String> rebateOnRainList = Arrays.asList("ERWBT","ERRT","ERKT","ERGT","ERCBT","ERCT");
			List<String> rebateOnSendriyaList = Arrays.asList("ESNWBT","ESNRT","ESNKT","ESSGT","ESNCT","ESNCBT");
			List<String> rebateOnSolarList = Arrays.asList("ESWBT","ESRT","ESKT","ESGT","ESCT","ESCBT");
			List<String> rebateOnSainik = Arrays.asList("EFRWBT","EFRRT","EFRKT","EFRGT","EFRCT","EFRCBT");
			
			rebateList.forEach(rebateDto ->{
				rebateDto.getReceiptFeeDetail().forEach(feeDetailDto ->{
					
					Long taxDescId = tbTaxMasJpaRepository.getTaxDescIdByTaxId(feeDetailDto.getTaxId());
					String lookUpCode = CommonMasterUtility.getNonHierarchicalLookUpObject(taxDescId, organisation)
							.getLookUpCode();
					if(rebateOnRainList.contains(lookUpCode)) {
						rebateOnRain.getAndAdd(feeDetailDto.getRfFeeamount().doubleValue());
					}else if(rebateOnSendriyaList.contains(lookUpCode)) {
						rebateOnSendriya.getAndAdd(feeDetailDto.getRfFeeamount().doubleValue());
					}else if(rebateOnSolarList.contains(lookUpCode)) {
						rebateOnSolar.addAndGet(feeDetailDto.getRfFeeamount().doubleValue());
					}else if(rebateOnSainik.contains(lookUpCode)) {
						rebateSainik.addAndGet(feeDetailDto.getRfFeeamount().doubleValue());
					}
					
				});
			});
			if (rebateOnRain.doubleValue() > 0) {
				PrintBillDetails printDetails = new PrintBillDetails();
				printDetails.setTaxNameV("Rain water Harvesting Rebate");
				printDetails.setCurrentAmountV(Math.round(rebateOnRain.doubleValue()));
				printDetails.setTotalAmountV(Math.round(rebateOnRain.doubleValue()));
				printDto.getPrintBillDetails().add(printDetails);
			}
			if (rebateOnSendriya.doubleValue() > 0) {
				PrintBillDetails printDetails = new PrintBillDetails();
				printDetails.setTaxNameV("Sendriya Khat Prakalp Rebate");
				printDetails.setCurrentAmountV(Math.round(rebateOnSendriya.doubleValue()));
				printDetails.setTotalAmountV(Math.round(rebateOnSendriya.doubleValue()));
				printDto.getPrintBillDetails().add(printDetails);
			}
			if (rebateOnSolar.doubleValue() > 0) {
				PrintBillDetails printDetails = new PrintBillDetails();
				printDetails.setTaxNameV("Solar Water Heater Rebate");
				printDetails.setCurrentAmountV(Math.round(rebateOnSolar.doubleValue()));
				printDetails.setTotalAmountV(Math.round(rebateOnSolar.doubleValue()));
				printDto.getPrintBillDetails().add(printDetails);
			}
			if (rebateSainik.doubleValue() > 0) {
				PrintBillDetails printDetails = new PrintBillDetails();
				printDetails.setTaxNameV("Freedom Fighter Exemption");
				printDetails.setCurrentAmountV(rebateSainik.doubleValue());
				printDto.getPrintBillDetails().add(printDetails);
			}
			
			
		}
		else if(CollectionUtils.isNotEmpty(rebateList)) {
			rebateList.forEach(rebate ->{
				rebateAmount.addAndGet(rebate.getRmAmount().doubleValue());
			});
			 PrintBillDetails printDetails = new PrintBillDetails();
			 printDetails
             .setTaxNameV(tbTaxMasService.findTaxByTaxIdAndOrgId(rebateList.get(0).getReceiptFeeDetail().get(0).getTaxId(), organisation.getOrgid())
                     .getTaxDesc());
         printDetails.setCurrentAmountV(rebateAmount.doubleValue());
         printDto.getPrintBillDetails().add(printDetails);
		}
        printDto.setTotArrBill(Utility.round(totalArrear.doubleValue(), 2));
        printDto.setTotCurrBill(Utility.round(totalCurrent.doubleValue(), 2));
        printDto.setFirstHalfTotCurrBill(Utility.round(firstHalTotalCurrent.doubleValue(), 2));
        printDto.setSecondHalfTotCurrBill(Utility.round(secondHalfTotalCurrent.doubleValue(), 2));
        printDto.setTotTatAmt(Utility.round(Math.round(totalArrear.doubleValue() + totalCurrent.doubleValue()), 2));
        printDto.setAdjAmtCurrentV(0d);
        printDto.setAjdAmtArrearsV(0d);
        printDto.setAdjTotalAmtV(0d);
        printDto.setExcessAdjTotalV(0d);

        printDto.setBalExcessAdjTotalV(
                asExecssAmtService.getAdvanceAmount(mainbill.getPropNo(), organisation.getOrgid()));

        printDto.setTotPayableV(printDto.getTotTatAmt());
        printDto.setAmtToWordsV(Utility.convertNumberToWord(Math.round(printDto.getTotTatAmt())));
        if(mainbill.getBmDuedate()!=null)
        printDto.setBillDueDateV(sm.format(mainbill.getBmDuedate()));

        // printDto.setFooterName(ApplicationSession.getInstance().getMessage("notice.footer"));
        // printDto.setFooterSing(footerSing);
        /*
         * printDto.setBottomNotePath(ApplicationSession.getInstance().getMessage("notice.bottomNote") + "\n" +
         * ApplicationSession.getInstance().getMessage("prop.bill.tip.second", new Object[] { MainetConstants.SUDHA_URL,
         * MainetConstants.Property.BILL_DUE_DATE }));
         */
        printDto.setBottomNotePath(ApplicationSession.getInstance().getMessage("prop." + orgId + ".bottomNote"));
        printDto.setOrgType(CommonMasterUtility
                .getNonHierarchicalLookUpObject(organisation.getOrgCpdId(), organisation)
                .getLookUpCode());
        if (printDto.getOrgType().equals(PrefixConstants.OrgnisationType.CORPORATION)) {
            printDto.setSectionL(ApplicationSession.getInstance().getMessage("prop.bill.orgType.Corporation"));
        } else if (printDto.getOrgType().equals(PrefixConstants.OrgnisationType.COUNCIL)) {
            printDto.setSectionL(ApplicationSession.getInstance().getMessage("prop.bill.orgType.muncipal"));
        } else {
            printDto.setSectionL(ApplicationSession.getInstance().getMessage("prop.bill.orgType.panchayat"));
        }
        printDto.setBillDateL(ApplicationSession.getInstance().getMessage("property.billdate"));
        printDto.setPaymentDateL(ApplicationSession.getInstance().getMessage("prop.bill.print.due"));
        if(mainbill.getBmDuedate()!=null)
        printDto.setPaymentDateV(sm.format(mainbill.getBmDuedate()));
        printDto.setDiscountL(ApplicationSession.getInstance().getMessage("prop.bill.print.discount.lable"));
        printDto.setDiscountV(ApplicationSession.getInstance().getMessage("prop." + orgId + ".rebate"));
        printDto.setPropNoL(ApplicationSession.getInstance().getMessage("property.PropertyNo"));
        printDto.setBillNoL(ApplicationSession.getInstance().getMessage("prop.bill.print.No"));

        printDto.setWarningL(
                ApplicationSession.getInstance().getMessage("prop.bill.warning", new Object[] { mainbill.getBmIntValue() }));

        final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR
                + "SHOW_DOCS";
        String dirPath = ApplicationSession.getInstance().getMessage("tempfilepath");
        String filePath = Utility.downloadedFileUrl(organisation.getoLogo(), outputPath, fileNetApp);
        if (filePath != null && !filePath.isEmpty()) {
            printDto.setLeftLogo(dirPath + filePath);
        }
        String empLoginName = ApplicationSession.getInstance().getMessage("prop." + orgId + ".emp.login.name");
        if (empLoginName != null && !empLoginName.isEmpty()) {
            Employee emp = employeeService.getEmployeeByLoginName(empLoginName, organisation, MainetConstants.IsDeleted.ZERO);
            if (emp != null) {
                String signFilePath = Utility.downloadedFileUrl(emp.getScansignature(), outputPath, fileNetApp);
                if (signFilePath != null && !signFilePath.isEmpty()) {
                    printDto.setSignPath(dirPath + signFilePath);
                }
                if (langId == 1) {
                    printDto.setEmployeeName(emp.getDesignation().getDsgnameReg());
                } else {
                    printDto.setEmployeeName(emp.getDesignation().getDsgname());
                }
            }
        }
        printDto.setArrearAmtL(ApplicationSession.getInstance().getMessage("taxdetails.arrears"));
        printDto.setTaxnameL(ApplicationSession.getInstance().getMessage("propertyTax.TaxName"));
        printDto.setCurrentAmtL(ApplicationSession.getInstance().getMessage("propertyTax.CurrentYear"));
        printDto.setTotAmtL(ApplicationSession.getInstance().getMessage("taxdetails.total"));
        printDto.setMobileL(ApplicationSession.getInstance().getMessage("ownersdetail.mobileno"));
        printDto.setTotalPayAmtL(ApplicationSession.getInstance().getMessage("taxdetails.total"));
        printDto.setSignatureMsgL(ApplicationSession.getInstance().getMessage("prop.bill.dig.sign"));
        printDto.setMobileV(owner.getAssoMobileno());
        printDto.setSignatureMsgL(ApplicationSession.getInstance().getMessage("prop.rec.print.recsign"));
		printDto.getPrintBillDetails().sort(
				Comparator.comparing(PrintBillDetails::getDisplaySeq, Comparator.nullsLast(Comparator.naturalOrder())));
        return printDto;
    }

    @Override
    @Transactional
    public PrintBillMaster getPrintBillMasterForDuplicateBill(Organisation organisation, final int langId,
            MainBillMasEntity mainbill, ProvisionalAssesmentMstDto assessment, FileNetApplicationClient fileNetApp) {
        final String deptName = departmentService.getDepartment(MainetConstants.Property.PROP_DEPT_SHORT_CODE, "A")
                .getDpDeptdesc();
        Long orgId = organisation.getOrgid();
        SimpleDateFormat sm = new SimpleDateFormat(MainetConstants.DATE_FRMAT);
        PrintBillMaster printDto = new PrintBillMaster();
        printDto.setDeptNameL(deptName);
        printDto.setOrgName(organisation.getONlsOrgname());
        printDto.setHeadingL(organisation.getONlsOrgname());
        printDto.setYearL("Year From " + UtilityService.convertDateToDDMMYYYY(mainbill.getBmFromdt()) + " To "
                + UtilityService.convertDateToDDMMYYYY(mainbill.getBmTodt()));
        printDto.setBillNoV(mainbill.getBmNo());
        printDto.setPropNoV(mainbill.getPropNo());
        printDto.setBillDateV(sm.format(mainbill.getBmBilldt()));
        printDto.setOldPropNoV(assessment.getAssOldpropno());
        final List<LookUp> lookupList = CommonMasterUtility.getListLookup(MainetConstants.Property.propPref.WZB, organisation);

        if (assessment.getAssWard1() != null) {
            if (langId == 0) {
                printDto.setWard1L(lookupList.get(0).getDescLangFirst());// need to change label from prefix for hierarchical
            } else {
                printDto.setWard1L(lookupList.get(0).getDescLangSecond());
            }
            printDto.setWard1V(CommonMasterUtility.getHierarchicalLookUp(assessment.getAssWard1(), organisation)
                    .getDescLangFirst());
        }
        if (assessment.getAssWard2() != null) {
            if (langId == 0) {
                printDto.setWard2L(lookupList.get(1).getDescLangFirst());// need to change label from prefix for hierarchical
            } else {
                printDto.setWard2L(lookupList.get(1).getDescLangSecond());
            }
            printDto.setWard2V(CommonMasterUtility.getHierarchicalLookUp(assessment.getAssWard2(), organisation)
                    .getDescLangFirst());
        }
        if (assessment.getAssWard3() != null) {

            if (langId == 0) {
                printDto.setWard3L(lookupList.get(2).getDescLangFirst());// need to change label from prefix for hierarchical
            } else {
                printDto.setWard3L(lookupList.get(2).getDescLangSecond());
            }
            printDto.setWard3V(CommonMasterUtility.getHierarchicalLookUp(assessment.getAssWard3(), organisation)
                    .getDescLangFirst());
        }
        if (assessment.getAssWard4() != null) {
            if (langId == 0) {
                printDto.setWard4L(lookupList.get(3).getDescLangFirst());// need to change label from prefix for hierarchical
            } else {
                printDto.setWard4L(lookupList.get(3).getDescLangSecond());
            }
            printDto.setWard4V(CommonMasterUtility.getHierarchicalLookUp(assessment.getAssWard4(), organisation)
                    .getDescLangFirst());
        }
        if (assessment.getAssWard5() != null) {
            if (langId == 0) {
                printDto.setWard5L(lookupList.get(4).getDescLangFirst());// need to change label from prefix for hierarchical
            } else {
                printDto.setWard5L(lookupList.get(4).getDescLangSecond());
            }
            printDto.setWard5V(CommonMasterUtility.getHierarchicalLookUp(assessment.getAssWard5(), organisation)
                    .getDescLangFirst());
        }
        printDto.setAnnualRentAmtL(ApplicationSession.getInstance().getMessage("prop.bill.print.alv"));
        AtomicDouble totalAlv = new AtomicDouble(0);
        AtomicDouble totalRv = new AtomicDouble(0);
        AtomicDouble totalcv = new AtomicDouble(0);
        assessment.getProvisionalAssesmentDetailDtoList().forEach(detail -> {
            if (detail.getAssdAlv() != null) {
                totalAlv.getAndAdd(detail.getAssdAlv());
            }
            if (detail.getAssdRv() != null) {
                totalRv.getAndAdd(detail.getAssdRv());
            }
            if (detail.getAssdCv() != null)
                totalcv.addAndGet(detail.getAssdCv());
        });
        printDto.setTotAlvV(totalAlv.get());
        printDto.setTotRvV(totalRv.get());
        printDto.setCvV(totalcv.get());
        printDto.setAnnualRentAmtV(totalAlv.toString());
        ProvisionalAssesmentOwnerDtlDto owner = assessment.getProvisionalAssesmentOwnerDtlDtoList().get(0);
        StringBuilder name = new StringBuilder(owner.getAssoOwnerName());
        if (owner.getRelationId() != null) {
            name.append(" " + CommonMasterUtility.getNonHierarchicalLookUpObject(owner.getRelationId(), organisation)
                    .getDescLangFirst());
            name.append(" " + owner.getAssoGuardianName());
        }
        
        
        
        
        
        
        
        
        
        StringBuilder ownerFullName = new StringBuilder();
        int ownerSize = 1;
        for (ProvisionalAssesmentOwnerDtlDto ownerDto : assessment.getProvisionalAssesmentOwnerDtlDtoList()) {
            if (ownerDto.getAssoOType() != null && ownerDto.getAssoOType().equals(MainetConstants.Property.PRIMARY_OWN)) {

                if (StringUtils.isEmpty(ownerFullName.toString())) {
                    ownerFullName.append(ownerDto.getAssoOwnerName());
                    ownerFullName.append(MainetConstants.WHITE_SPACE);
                    if (ownerDto.getRelationId() != null && ownerDto.getRelationId() > 0) {
                        LookUp reltaionLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(ownerDto.getRelationId(),
                                organisation);
                        ownerFullName.append(reltaionLookUp.getDescLangFirst());
                    } else {
                        ownerFullName.append("Contact person - ");
                    }
                    if (StringUtils.isNotBlank(ownerDto.getAssoGuardianName())) {
                        ownerFullName.append(MainetConstants.WHITE_SPACE);
                        ownerFullName.append(ownerDto.getAssoGuardianName());
                    }
                } else {
                    ownerFullName.append(ownerDto.getAssoOwnerName());
                    ownerFullName.append(MainetConstants.WHITE_SPACE);
                    if (ownerDto.getRelationId() != null && ownerDto.getRelationId() > 0) {
                        LookUp reltaionLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(ownerDto.getRelationId(),
                                organisation);
                        ownerFullName.append(reltaionLookUp.getDescLangFirst());
                    } else {
                        ownerFullName.append("Contact person - ");
                    }
                    ownerFullName.append(MainetConstants.WHITE_SPACE);
                    ownerFullName.append(ownerDto.getAssoGuardianName());
                }
                if (ownerSize < assessment.getProvisionalAssesmentOwnerDtlDtoList().size()) {
                    ownerFullName.append("," + " ");
                }
                ownerSize = ownerSize + 1;
            } else {
                ownerFullName.append(ownerDto.getAssoOwnerName());
                ownerFullName.append(MainetConstants.WHITE_SPACE);
                if (ownerDto.getRelationId() != null && ownerDto.getRelationId() > 0) {
                    LookUp reltaionLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(ownerDto.getRelationId(),
                            organisation);
                    ownerFullName.append(reltaionLookUp.getDescLangFirst());
                } else {
                    ownerFullName.append("Contact person - ");
                }
                ownerFullName.append(MainetConstants.WHITE_SPACE);
                ownerFullName.append(ownerDto.getAssoGuardianName());
                if (ownerSize < assessment.getProvisionalAssesmentOwnerDtlDtoList().size()) {
                    ownerFullName.append("," + " ");
                }
            }
        }
        
        
        
        
        printDto.setNameL(ApplicationSession.getInstance().getMessage("ownerdetail.Ownername"));
        printDto.setNameV(ownerFullName.toString());
        printDto.setAddressL(ApplicationSession.getInstance().getMessage("property.Address"));
        // String locName = iLocationMasService.getLocationNameById(assessment.getLocId(), organisation.getOrgid());
        printDto.setAddressV(
                assessment.getAssAddress() + ", " + assessment.getLocationName() + ", " + assessment.getAssPincode());
        AtomicDouble totalArrear = new AtomicDouble(0);
        AtomicDouble totalCurrent = new AtomicDouble(0);
        mainbill.getBillDetEntityList().forEach(billdet -> {
            PrintBillDetails printDetails = new PrintBillDetails();
            printDetails
                    .setTaxNameV(tbTaxMasService.findTaxByTaxIdAndOrgId(billdet.getTaxId(), organisation.getOrgid())
                            .getTaxDesc());
            totalArrear.addAndGet(billdet.getBdPrvArramt());
            printDetails.setArrearsAmountV(billdet.getBdPrvArramt());
            printDetails.setCurrentAmountV(billdet.getBdCurTaxamt());
            totalCurrent.addAndGet(billdet.getBdCurTaxamt());
            printDetails.setTotalAmountV(billdet.getBdCurTaxamt() + billdet.getBdPrvArramt());
            printDto.getPrintBillDetails().add(printDetails);
        });
        printDto.setTotArrBill(totalArrear.doubleValue());
        printDto.setTotCurrBill(totalCurrent.doubleValue());
        printDto.setTotTatAmt(Math.round(totalArrear.doubleValue() + totalCurrent.doubleValue()));
        printDto.setAdjAmtCurrentV(0d);
        printDto.setAjdAmtArrearsV(0d);
        printDto.setAdjTotalAmtV(0d);
        printDto.setExcessAdjTotalV(0d);

        printDto.setBalExcessAdjTotalV(
                asExecssAmtService.getAdvanceAmount(mainbill.getPropNo(), organisation.getOrgid()));

        printDto.setTotPayableV(printDto.getTotTatAmt());
        printDto.setAmtToWordsV(Utility.convertNumberToWord(printDto.getTotTatAmt()));
        printDto.setBillDueDateV(sm.format(mainbill.getBmDuedate()));

        // printDto.setFooterName(ApplicationSession.getInstance().getMessage("notice.footer"));
        // printDto.setFooterSing(footerSing);
        /*
         * printDto.setBottomNotePath(ApplicationSession.getInstance().getMessage("notice.bottomNote") + "\n" +
         * ApplicationSession.getInstance().getMessage("prop.bill.tip.second", new Object[] { MainetConstants.SUDHA_URL,
         * MainetConstants.Property.BILL_DUE_DATE }));
         */
        printDto.setBottomNotePath(ApplicationSession.getInstance().getMessage("prop." + orgId + ".bottomNote"));
        printDto.setOrgType(CommonMasterUtility
                .getNonHierarchicalLookUpObject(organisation.getOrgCpdId(), organisation)
                .getLookUpCode());
        if (printDto.getOrgType().equals(PrefixConstants.OrgnisationType.CORPORATION)) {
            printDto.setSectionL(ApplicationSession.getInstance().getMessage("prop.bill.orgType.Corporation"));
        } else if (printDto.getOrgType().equals(PrefixConstants.OrgnisationType.COUNCIL)) {
            printDto.setSectionL(ApplicationSession.getInstance().getMessage("prop.bill.orgType.muncipal"));
        } else {
            printDto.setSectionL(ApplicationSession.getInstance().getMessage("prop.bill.orgType.panchayat"));
        }
        printDto.setBillDateL(ApplicationSession.getInstance().getMessage("property.billdate"));
        printDto.setPaymentDateL(ApplicationSession.getInstance().getMessage("prop.bill.print.due"));
        printDto.setPaymentDateV(sm.format(mainbill.getBmDuedate()));
        printDto.setDiscountL(ApplicationSession.getInstance().getMessage("prop.bill.print.discount.lable"));
        printDto.setDiscountV(ApplicationSession.getInstance().getMessage("prop." + orgId + ".rebate"));
        printDto.setPropNoL(ApplicationSession.getInstance().getMessage("property.PropertyNo"));
        printDto.setBillNoL(ApplicationSession.getInstance().getMessage("prop.bill.print.No"));

        printDto.setWarningL(
                ApplicationSession.getInstance().getMessage("prop.bill.warning", new Object[] { mainbill.getBmIntValue() }));

        /*
         * final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR +
         * "SHOW_DOCS"; String dirPath = ApplicationSession.getInstance().getMessage("tempfilepath"); String filePath =
         * Utility.downloadedFileUrl(organisation.getoLogo(), outputPath, fileNetApp); if (filePath != null &&
         * !filePath.isEmpty()) { printDto.setLeftLogo(dirPath + filePath); }
         */
        String empLoginName = ApplicationSession.getInstance().getMessage("prop." + orgId + ".emp.login.name");
        if (empLoginName != null && !empLoginName.isEmpty()) {
            Employee emp = employeeService.getEmployeeByLoginName(empLoginName, organisation, MainetConstants.IsDeleted.ZERO);
            if (emp != null) {
                /*
                 * String signFilePath = Utility.downloadedFileUrl(emp.getScansignature(), outputPath, fileNetApp); if
                 * (signFilePath != null && !signFilePath.isEmpty()) { printDto.setSignPath(dirPath + signFilePath); }
                 */
                if (langId == 1) {
                    printDto.setEmployeeName(emp.getDesignation().getDsgnameReg());
                } else {
                    printDto.setEmployeeName(emp.getDesignation().getDsgname());
                }
            }
        }
        printDto.setArrearAmtL(ApplicationSession.getInstance().getMessage("taxdetails.arrears"));
        printDto.setTaxnameL(ApplicationSession.getInstance().getMessage("propertyTax.TaxName"));
        printDto.setCurrentAmtL(ApplicationSession.getInstance().getMessage("propertyTax.CurrentYear"));
        printDto.setTotAmtL(ApplicationSession.getInstance().getMessage("taxdetails.total"));
        printDto.setMobileL(ApplicationSession.getInstance().getMessage("ownersdetail.mobileno"));
        printDto.setTotalPayAmtL(ApplicationSession.getInstance().getMessage("taxdetails.total"));
        printDto.setSignatureMsgL(ApplicationSession.getInstance().getMessage("prop.bill.dig.sign"));
        printDto.setMobileV(owner.getAssoMobileno());
        printDto.setSignatureMsgL(ApplicationSession.getInstance().getMessage("prop.rec.print.recsign"));
        return printDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<NoticeGenSearchDto> getAllPropForBillPrint(NoticeGenSearchDto specNotSearchDto, long orgid) {
        List<NoticeGenSearchDto> notGenShowList = new ArrayList<>();
        List<Object[]> result = new ArrayList<>();
        List<String> propId = new ArrayList<>();
        List<String> propNoList = new ArrayList<>();
        List<String> oldNoList = new ArrayList<>();
        if (specNotSearchDto.getPropertyNo() != null && !specNotSearchDto.getPropertyNo().isEmpty()) {
            String[] propNo = specNotSearchDto.getPropertyNo().split(MainetConstants.operator.COMMA);
            for (String s : propNo) {
                propNoList.add(s);
            }
            result = iAssessmentMastDao.getAllPropBillForPrint(specNotSearchDto, orgid, propNoList);
        } else if (specNotSearchDto.getOldPropertyNo() != null && !specNotSearchDto.getOldPropertyNo().isEmpty()) {
            String[] propNo = specNotSearchDto.getOldPropertyNo().split(MainetConstants.operator.COMMA);
            for (String s : propNo) {
                oldNoList.add(s);
            }
            result = iAssessmentMastDao.getAllPropBillForPrintByOldPropNo(specNotSearchDto, orgid, oldNoList);
        }
        if (result != null && !result.isEmpty()) {
            result.forEach(value -> {
                propId.add(value[0].toString());
            });
            List<AssesmentMastEntity> assMasList = assesmentMstRepository.fetchAssessmentMstByPropNo(orgid, propId);
            if (assMasList != null) {
                assMasList.forEach(assMas -> {
                    NoticeGenSearchDto notGen = new NoticeGenSearchDto();
                    notGen.setPropertyNo(assMas.getAssNo());
                    notGen.setApplicationId(assMas.getApmApplicationId());
                    notGen.setAssId(assMas.getProAssId());
                    notGen.setOldPropertyNo(assMas.getAssOldpropno());
                    notGen.setLocationName(iLocationMasService.getLocationNameById(assMas.getLocId(), orgid));
                    List<AssesmentOwnerDtlEntity> ownerList = assMas.getAssesmentOwnerDetailEntityList();
                    for (AssesmentOwnerDtlEntity owner : ownerList) {
                        if (owner.getAssoOType() != null && owner.getAssoOType().equals(MainetConstants.Property.PRIMARY_OWN)) {
                            notGen.setOwnerName(owner.getAssoOwnerName());
                        }
                    }
                    notGen.setGenNotCheck(MainetConstants.N_FLAG);
                    if (StringUtils.isNotBlank(assMas.getIsGroup())) {
						notGen.setIsGroup(assMas.getIsGroup());
					}
					if (assMas.getSplNotDueDate() != null && new Date().after(assMas.getSplNotDueDate())) {
						notGen.setSplNotDueDatePass(MainetConstants.FlagY);
					} else if (assMas.getSplNotDueDate() == null) {
						notGen.setSplNotDueDatePass(MainetConstants.FlagY);
					}
                    notGenShowList.add(notGen);

                });
            }
        }
        return notGenShowList;
    }

    @Override
    public void saveDuplicateBill(final List<MainBillMasEntity> billList, ProvisionalAssesmentMstDto proAssDto, int langId,
            Long orgId) {
        Organisation org = iOrganisationService.getOrganisationById(orgId);
        List<DuplicateBillDTO> dupList = new ArrayList<>();
        for (MainBillMasEntity entity : billList) {
            DuplicateBillDTO dto = new DuplicateBillDTO();
            dto.setBmId(entity.getBmIdno());
            dto.setBillDate(entity.getBmBilldt());
            dto.setBillFromDate(entity.getBmFromdt());
            dto.setBillToDate(entity.getBmTodt());
            dto.setBmYear(entity.getBmYear());
            dto.setCreatedBy(entity.getUserId());
            dto.setCreatedDate(entity.getLmoddate());
            dto.setDeptCode(MainetConstants.Property.PROP_DEPT_SHORT_CODE);
            dto.setLgIpMac(entity.getLgIpMac());
            // dto.setLgIpMacUpd(entity.getLgIpMacUpd());
            dto.setOrgId(entity.getOrgid());
            dto.setReferenceId(entity.getPropNo());
            // dto.setUpdatedBy(entity.getUpdatedBy());
            // dto.setUpdatedDate(entity.getUpdatedDate());
            PrintBillMaster printBillMas = getPrintBillMasterForDuplicateBill(org, langId, entity, proAssDto,
                    FileNetApplicationClient.getInstance());
            try {
                dto.setDupBillData(new ObjectMapper().writeValueAsString(printBillMas));
                dupList.add(dto);

            } catch (IOException e) {
                throw new FrameworkException("Error While Casting Object in String for saveDuplicateBill() " + e);
            }

        }
        ApplicationContextProvider.getApplicationContext().getBean(IDuplicateBillService.class).save(dupList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NoticeGenSearchDto> fetchBillPrintDetailBySearchCriteria(NoticeGenSearchDto specialNotGenSearchDto, long orgid) {
        List<NoticeGenSearchDto> notGenShowList = new ArrayList<>();
        List<String> propId = new ArrayList<>();
        List<Object[]> result = iAssessmentMastDao.fetchBillPrintDetailBySearchCriteria(specialNotGenSearchDto, orgid);
        if (result != null && !result.isEmpty()) {
            result.forEach(value -> {
                propId.add(value[0].toString());
            });
            List<AssesmentMastEntity> assMasList = assesmentMstRepository.fetchAssessmentMstByPropNo(orgid, propId);
            if (CollectionUtils.isNotEmpty(assMasList)) {
				Map<Long, String> locNameMap = new HashMap<>();
				List<TbLocationMas> locList = iLocationMasService
						.findAllLocationsForOrg(organisationService.getOrganisationById(orgid));				
				locList.forEach(loc ->{
					locNameMap.put(loc.getLocId(), loc.getLocNameEng());
				});
				
                assMasList.forEach(assMas -> {
                    NoticeGenSearchDto notGen = new NoticeGenSearchDto();
                    notGen.setPropertyNo(assMas.getAssNo());
                    notGen.setApplicationId(assMas.getApmApplicationId());
                    notGen.setAssId(assMas.getProAssId());
                    notGen.setOldPropertyNo(assMas.getAssOldpropno());
                    notGen.setLocationName(locNameMap.get(assMas.getLocId()));
                    List<AssesmentOwnerDtlEntity> ownerList = assMas.getAssesmentOwnerDetailEntityList();
                    for (AssesmentOwnerDtlEntity owner : ownerList) {
                        if (owner.getAssoOType() != null && owner.getAssoOType().equals(MainetConstants.Property.PRIMARY_OWN)) {
                            notGen.setOwnerName(owner.getAssoOwnerName());
                        }
                    }
					if (assMas.getSplNotDueDate() != null && new Date().after(assMas.getSplNotDueDate())) {
						notGen.setSplNotDueDatePass(MainetConstants.FlagY);
					} else if (assMas.getSplNotDueDate() == null) {
						notGen.setSplNotDueDatePass(MainetConstants.FlagY);
					}
                    notGen.setGenNotCheck(MainetConstants.N_FLAG);
                    notGenShowList.add(notGen);

                });
            }
        }
        return notGenShowList;
    }
    
    
    @Override
    @Transactional
    public List<NoticeGenSearchDto> fetchAssDetailBySearchCriteriaForSkdcl(NoticeGenSearchDto specNotSearchDto,Long currFinYearId, Long prevFinYearId, long orgId) {
        List<NoticeGenSearchDto> notGenShowList = new ArrayList<>();
        Organisation org = iOrganisationService.getOrganisationById(orgId);
        List<Object[]> propNoObjList = getPropNoListBySearchCriteria(specNotSearchDto,MainetConstants.FlagA,null);
        List<String> propNoList = new ArrayList<String>();
            if (CollectionUtils.isNotEmpty(propNoObjList)) {
            	propNoObjList.forEach(value -> {
            		if(value[15] != null) {
            			propNoList.add(value[15].toString());
            		}else {
            			StringBuilder logicalNo = new StringBuilder();
            			logicalNo.append(value[0].toString());
            			if(value[4] != null) {
            				logicalNo.append("_");
            				logicalNo.append(value[4].toString());
            			}
            			propNoList.add(logicalNo.toString());
        				value[15] = logicalNo.toString();
            		}
            		
                 });
            	Map<String, Long> lastYearBillByPropNo = getLastYearBillByPropNo(propNoList, orgId);
            	propNoObjList.forEach(propObj -> {
            		 Optional<NoticeGenSearchDto> checkPropExists = null;
            	LookUp lookup =null;
            	if(propObj[5]!=null) {
           		 	lookup = CommonMasterUtility.getNonHierarchicalLookUpObject(Long.valueOf(propObj[5].toString()), org);
                }
				if (CollectionUtils.isNotEmpty(notGenShowList)) {
					
					if (propObj[4] != null && lookup!=null  && MainetConstants.FlagI.equals(lookup.getLookUpCode())) {
						checkPropExists = notGenShowList.stream()
								.filter(result -> result.getPropertyNo().equals(propObj[0])
										&& result.getFlatNo() != null
										&& result.getFlatNo().equals(propObj[4].toString()))
								.findFirst();
					} else {
						checkPropExists = notGenShowList.stream()
								.filter(result -> result.getPropertyNo().equals(propObj[0])).findFirst();
					}
				}
                     
				if (((CollectionUtils.isEmpty(notGenShowList)) || (CollectionUtils.isNotEmpty(notGenShowList)
						&& checkPropExists != null && !checkPropExists.isPresent()))) {
					Long lastYear = lastYearBillByPropNo.get(propObj[15].toString());
					if ((lastYear == null) || (lastYear != null && lastYear.equals(prevFinYearId) && !lastYear.equals(currFinYearId))) {
						NoticeGenSearchDto notGen = new NoticeGenSearchDto();
						notGen.setPropertyNo(propObj[0].toString());
						notGen.setOwnerName(propObj[2].toString());
						notGen.setFinYear(String.valueOf(currFinYearId));
						if(propObj[1] != null) {
							notGen.setOldPropertyNo(propObj[1].toString());
						}
						if (propObj[4] != null && lookup!=null  && MainetConstants.FlagI.equals(lookup.getLookUpCode())) {
							notGen.setFlatNo(propObj[4].toString());
						}
						notGen.setGenNotCheck(MainetConstants.Y_FLAG);
						notGenShowList.add(notGen);
					}else {
						if(StringUtils.equals(specNotSearchDto.getSpecNotSearchType(), MainetConstants.FlagS)) {
							NoticeGenSearchDto notGen = new NoticeGenSearchDto();
							notGen.setCheckStatus(MainetConstants.FlagY);
							notGen.setPropertyNo(propObj[0].toString());
							notGenShowList.add(notGen);
						}
					}
				}
                });
            
        
}
        return notGenShowList;
    }
    
    @Override
    @Transactional
    public List<Object[]> getPropNoListBySearchCriteria(NoticeGenSearchDto noticeSearchDto,String flag,List<String> propNoList){
    	LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " getPropNoListBySearchCriteria() method & flag is : "+flag);
    	ProperySearchDto searchDto = new ProperySearchDto();
    	searchDto.setOrgId(noticeSearchDto.getOrgId());
    	searchDto.setAssWard1(noticeSearchDto.getAssWard1());
    	searchDto.setAssWard2(noticeSearchDto.getAssWard2());
    	searchDto.setAssWard3(noticeSearchDto.getAssWard3());
    	searchDto.setAssWard4(noticeSearchDto.getAssWard4());
    	searchDto.setAssWard5(noticeSearchDto.getAssWard5());
    	searchDto.setProertyNo(noticeSearchDto.getPropertyNo());
    	searchDto.setFlatNo(noticeSearchDto.getFlatNo());
    	searchDto.setOldPid(noticeSearchDto.getOldPropertyNo());
    	searchDto.setAssParshadWard1(noticeSearchDto.getParshadAssWard1());
    	searchDto.setAssParshadWard2(noticeSearchDto.getParshadAssWard2());
    	searchDto.setAssParshadWard3(noticeSearchDto.getParshadAssWard3());
    	searchDto.setAssParshadWard4(noticeSearchDto.getParshadAssWard4());
    	searchDto.setAssParshadWard5(noticeSearchDto.getParshadAssWard5());
    	searchDto.setHouseNo(noticeSearchDto.getHouseNo());
    	
    	searchDto.setStatusFlag(flag);
    	List<Object[]> propObjList = null;
    	if(StringUtils.equals(MainetConstants.FlagY, flag)) {
    		propObjList = provisionalAssessmentMstDao.searchPropetyForViewForBillGeneration(searchDto, null, null, null,propNoList);
    	}else {
    		propObjList = provisionalAssessmentMstDao.searchPropetyForViewForAll(searchDto, null, null, null);
    	}
    	
    	LOGGER.info("End--> " + this.getClass().getSimpleName() + " getPropNoListBySearchCriteria() method");
    	return propObjList;
    }

    private Map<String, Long> getLastYearBillByPropNo(List<String> propNoList, Long orgId){
    	Map<String, Long> lastBillMap = new HashMap<String, Long>();
    	List<Object[]> lastYearBill = iAssessmentMastDao.getAllMaxBmYearByPropNo(propNoList, orgId);
    	lastYearBill.forEach(lastBill ->{
    		lastBillMap.put(lastBill[0].toString(), Long.valueOf(lastBill[1].toString()));
    	});
    	return lastBillMap;
    }
    
    @Override
    @Transactional
	public List<NoticeGenSearchDto> fetchAssDetailBySearchCriteriaForProduct(NoticeGenSearchDto specNotSearchDto, long orgId) {
		List<NoticeGenSearchDto> notGenShowList = new ArrayList<>();
		Long currFinId = iFinancialYearService.getFinanceYearId(new Date());
		Organisation org = new Organisation();
		org.setOrgid(orgId);
		List<Object[]> propNoObjList = getPropNoListBySearchCriteria(specNotSearchDto, MainetConstants.FlagA, null);
		List<String> propNoList = new ArrayList<String>();
		if (CollectionUtils.isNotEmpty(propNoObjList)) {
			propNoObjList.forEach(value -> {
				propNoList.add(value[0].toString());
			});

			Map<String, Long> lastYearBillByPropNo = getLastYearBillByPropNoForWhole(propNoList, orgId);

			propNoObjList.forEach(propObj -> {
				Optional<NoticeGenSearchDto> checkPropExists = null;
				if (CollectionUtils.isNotEmpty(notGenShowList)) {
						checkPropExists = notGenShowList.stream()
								.filter(result -> result.getPropertyNo().equals(propObj[0])).findFirst();
				}

				if (((CollectionUtils.isEmpty(notGenShowList)) || (CollectionUtils.isNotEmpty(notGenShowList)
						&& checkPropExists != null && !checkPropExists.isPresent()))) {
					Long lastYear = 0L;

					lastYear = lastYearBillByPropNo.get(propObj[0].toString());

					if ((lastYear == null) || (lastYear != null && !lastYear.equals(currFinId))) {
						NoticeGenSearchDto notGen = new NoticeGenSearchDto();
						notGen.setPropertyNo(propObj[0].toString());
						notGen.setOwnerName(propObj[2].toString());
						if (propObj[1] != null) {
							notGen.setOldPropertyNo(propObj[1].toString());
						}
						notGen.setGenNotCheck(MainetConstants.Y_FLAG);
						notGen.setAssWard1(specNotSearchDto.getAssWard1());
						notGen.setAssWard2(specNotSearchDto.getAssWard2());
						notGen.setAssWard3(specNotSearchDto.getAssWard3());
						notGen.setAssWard4(specNotSearchDto.getAssWard4());
						notGen.setAssWard5(specNotSearchDto.getAssWard5());
						if (propObj[18] != null && Utility.isEnvPrefixAvailable(org, "PSCL") && StringUtils.equals("SM", specNotSearchDto.getSpecNotSearchType())) {
							Date specNotDueDate = (Date) propObj[18];
							if (specNotDueDate != null && (Utility.comapreDates(specNotDueDate, new Date()) || !new Date().after(specNotDueDate))) {
								notGen.setSplNotDueDatePass(MainetConstants.FlagN);
							}
						}
						notGenShowList.add(notGen);
					}
				}
			});

		}
		return notGenShowList;
	}
    
    private Map<String, Long> getLastYearBillByPropNoForWhole(List<String> propNoList, Long orgId){
    	Map<String, Long> lastBillMap = new HashMap<String, Long>();
    	if(CollectionUtils.isNotEmpty(propNoList)) {
    		List<Object[]> lastYearBill = iAssessmentMastDao.getAllMaxBmYearByPropNoForWhole(propNoList, orgId);
        	lastYearBill.forEach(lastBill ->{
        		lastBillMap.put(lastBill[0].toString(), Long.valueOf(lastBill[1].toString()));
        	});
    	}
    	return lastBillMap;
    }
}
