package com.abm.mainet.property.service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import com.abm.mainet.bill.service.BillMasterCommonService;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.dto.ChallanReportDTO;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.TbReceiptDuplicateEntity;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.CommonChallanPayModeDTO;
import com.abm.mainet.common.dto.CommonSequenceConfigDto;
import com.abm.mainet.common.dto.GridSearchDTO;
import com.abm.mainet.common.dto.PagingDTO;
import com.abm.mainet.common.dto.SequenceConfigMasterDTO;
import com.abm.mainet.common.dto.TbReceiptDuplicateDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.TbBillDet;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.integration.property.dto.PropertyDetailDto;
import com.abm.mainet.common.integration.property.dto.PropertyInputDto;
import com.abm.mainet.common.integration.report.utility.ReportUtility;
import com.abm.mainet.common.master.dto.LocOperationWZMappingDto;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.repository.DuplicateReceiptRepository;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.dao.IAssessmentMastDao;
import com.abm.mainet.property.dao.IProvisionalAssessmentMstDao;
import com.abm.mainet.property.domain.AssesmentOwnerDtlEntity;
import com.abm.mainet.property.domain.MainBillMasEntity;
import com.abm.mainet.property.domain.PropertyDetEntity;
import com.abm.mainet.property.domain.PropertyMastEntity;
import com.abm.mainet.property.domain.ProvisionalBillMasEntity;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.PrintBillMaster;
import com.abm.mainet.property.dto.PropertyTransferMasterDto;
import com.abm.mainet.property.dto.ProperySearchDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentDetailDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentFactorDtlDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentOwnerDtlDto;
import com.abm.mainet.property.dto.ReceiptPrintDetailDto;
import com.abm.mainet.property.dto.ReceiptPrintDto;
import com.abm.mainet.property.dto.ViewPropertyDetailRequestDto;
import com.abm.mainet.property.repository.PropertyMainBillRepository;
import com.abm.mainet.property.repository.PropertyTransferRepository;
import com.abm.mainet.property.repository.ProvisionalBillRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.AtomicDouble;

import io.swagger.annotations.Api;

@WebService(endpointInterface = "com.abm.mainet.property.service.ViewPropertyDetailsService")
@Api(value = "/viewPropertyDetail")
@Path("/viewPropertyDetail")
@Produces("application/json")
@Service
public class ViewPropertyDetailsServiceImpl implements ViewPropertyDetailsService {

    private static final Logger LOGGER = Logger.getLogger(ViewPropertyDetailsServiceImpl.class);

    @Autowired
    private IProvisionalAssessmentMstDao iProvisionalAssessmentMstDao;

    @Autowired
    private PropertyMainBillRepository propertyMainBillRepository;

    @Autowired
    private ProvisionalBillRepository propertyProvisionalBillRepository;

    @Autowired
    private IReceiptEntryService iReceiptEntryService;

    @Autowired
    private TbTaxMasService tbTaxMasService;

    @Autowired
    private IProvisionalAssesmentMstService iProvisionalAssesmentMstService;

    @Autowired
    private IChecklistVerificationService iChecklistVerificationService;

    @Autowired
    private ServiceMasterService serviceMasterService;

    @Autowired
    private IAssessmentMastDao iAssessmentMastDao;

    @Autowired
    private AssesmentMastService assesmentMastService;

    @Autowired
    private IProvisionalBillService iProvisionalBillService;

    @Autowired
    private PropertyMainBillService propertyMainBillService;

    @Autowired
    private PropertyBillGenerationService propertyBillGenerationService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private IOrganisationService iOrganisationService;

    @Autowired
    private IFinancialYearService iFinancialYearService;

    @Autowired
    private PrimaryPropertyService primaryPropertyService;

    @Autowired
    private ILocationMasService iLocationMasService;

    @Autowired
    private PropertyBRMSService propertyBRMSService;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private IFinancialYearService iFinancialYear;

    @Autowired
    private DuplicateReceiptRepository repository;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private IAttachDocsService attachDocsService;
    
    @Autowired
    private PropertyTransferService propertyTransferService;

    @Autowired
    private IEmployeeService iEmployeeService;

    @Resource
    private SelfAssessmentService selfAssessmentService;
    
	@Resource
	private SeqGenFunctionUtility seqGenFunctionUtility;
	
	@Autowired
    private BillMasterCommonService billMasterCommonService;
	
	@Autowired
	private PropertyTransferRepository propertyTransferRepository;

    @Override
    @POST
    @Path("/searchData")
    @Transactional(readOnly = true)
    public List<ProperySearchDto> searchPropertyDetailsByRequest(ViewPropertyDetailRequestDto viewPropDet) {
        return searchPropertyDetails(viewPropDet.getPropSearchDto(), viewPropDet.getPagingDTO(),
                viewPropDet.getGridSearchDTO(), null, null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProperySearchDto> searchPropertyDetails(ProperySearchDto searchDto, PagingDTO pagingDTO,
            GridSearchDTO gridSearchDTO, Long serviceId, Long locationId) {

        List<ProperySearchDto> resultList = new ArrayList<>();
        List<Object[]> entity = iProvisionalAssessmentMstDao.searchPropetyForViewForAll(searchDto, pagingDTO, gridSearchDTO,
                serviceId);

        TbLocationMas locationMas = null;
        if (locationId != null) {
            locationMas = ApplicationContextProvider.getApplicationContext().getBean(ILocationMasService.class)
                    .findById(locationId);
        }
        Organisation org = new Organisation();
        org.setOrgid(searchDto.getOrgId());
        LookUp operationalWardMapping = null;
        try {
            operationalWardMapping = CommonMasterUtility.getValueFromPrefixLookUp("LOM", "ENV", org);
        } catch (Exception exception) {
            LOGGER.error("No Prefix found for LOM(ENV)");
        }
        /*
         * HashSet<Object> filterdublicateproperty=new HashSet<>(); entity.removeIf(e ->
         * !filterdublicateproperty.add(Arrays.asList(e.length)));
         */
        if (entity != null && !entity.isEmpty()) {
            Long propertyDeptId = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
                    .getDepartmentIdByDeptCode("AS");

            for (Object[] assMst : entity) {
                Optional<ProperySearchDto> checkPropExists = null;
                if (CollectionUtils.isNotEmpty(resultList)) {
                    checkPropExists = resultList.stream().filter(result -> result.getProertyNo().equals(assMst[0])).findFirst();
                }

                if (((CollectionUtils.isEmpty(resultList)) || (CollectionUtils.isNotEmpty(resultList)
                        && checkPropExists != null && !checkPropExists.isPresent()))
                        && (((locationMas == null) || (operationalWardMapping == null
                                || StringUtils.isBlank(operationalWardMapping.getLookUpDesc())
                                || StringUtils.equals(operationalWardMapping.getOtherField(), "N")))
                                || (locationMas != null
                                        && checkWardZoneMapping(assMst, locationMas, propertyDeptId, searchDto.getOrgId())))) {
                    ProperySearchDto mst = new ProperySearchDto();
                    mst.setProertyNo(assMst[0].toString());

                    if (assMst[1] != null) {
                        mst.setOldPid(assMst[1].toString());
                    }
                    if (assMst[2] != null) {
                        mst.setOwnerName(assMst[2].toString());
                    }
                    if (assMst[3] != null) {
                        mst.setMobileno(maskNumber(assMst[3].toString(), "********##"));
                    }
                    mst.setRowId(assMst[0].toString());
                    mst.setDeptShortName(MainetConstants.Property.PROP_DEPT_SHORT_CODE);

                    if (assMst[10] != null) {
                        mst.setOutstandingAmt(assMst[10].toString());
                    }
                    if (assMst[11] != null) {
                        mst.setReceiptAmt(assMst[11].toString());
                    }
                    if (assMst[12] != null) {
                        mst.setReceiptDate(
                                new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(assMst[12]));
                    }
                    if (assMst[16] != null) {
                        mst.setGuardianName(assMst[16].toString());
                    }
                    if (assMst[17] != null) {
                        mst.setHouseNo(assMst[17].toString());
                    }
                    resultList.add(mst);
                }
            }
        }

        return resultList;

    }

    private String maskNumber(String number, String mask) {

        int index = 0;
        StringBuilder masked = new StringBuilder();
        for (int i = 0; i < mask.length(); i++) {
            char c = mask.charAt(i);
            if (c == '#') {
                if (number.length() > index) {
                    masked.append(number.charAt(index));
                    index++;
                }
            } else {
                if (number.length() > index) {
                    masked.append(c);
                }
                index++;
            }
        }
        return masked.toString();
    }

    @Override
    @POST
    @Path("/viewBillHistory")
    @Transactional(readOnly = true)
    public List<TbBillMas> getViewData(ProperySearchDto properySearchDto) {
        Organisation org = new Organisation();
        org.setOrgid(properySearchDto.getOrgId());
        List<TbBillMas> billMas = new ArrayList<>(0);
        Map<Long, String> tax = new HashMap<>(0);
        Map<Long, Long> taxDisplaySeq = new HashMap<>(0);
        
        final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
                MainetConstants.Property.propPref.BILL, MainetConstants.Property.propPref.CAA, org);
        final LookUp chargeApplicableAtBillRec = CommonMasterUtility.getValueFromPrefixLookUp(
                MainetConstants.Property.propPref.BILL_RECEIPT, MainetConstants.Property.propPref.CAA, org);
        final LookUp chargeApplicableAtDemand = CommonMasterUtility.getValueFromPrefixLookUp(
                "DN", MainetConstants.Property.propPref.CAA, org);
        List<TbTaxMas> taxes = tbTaxMasService.findAllTaxesForBillGeneration(properySearchDto.getOrgId(),
                properySearchDto.getDeptId(), chargeApplicableAt.getLookUpId(), null);
        List<TbTaxMas> taxesBillRece = tbTaxMasService.findAllTaxesForBillGeneration(properySearchDto.getOrgId(),
                properySearchDto.getDeptId(), chargeApplicableAtBillRec.getLookUpId(), null);
        List<TbTaxMas> taxesBillDemand = tbTaxMasService.findAllTaxesForBillGeneration(properySearchDto.getOrgId(),
                properySearchDto.getDeptId(), chargeApplicableAtDemand.getLookUpId(), null);
        List<TbTaxMas> notActiveTaxes = tbTaxMasService.findAllNotActiveTaxesForBillGeneration(properySearchDto.getOrgId(),
                properySearchDto.getDeptId(), chargeApplicableAt.getLookUpId(), null);
        
        if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)) {
        	final LookUp chequeDishonor = CommonMasterUtility.getValueFromPrefixLookUp(
            		MainetConstants.Property.CHEQUE_DISHONR_CHARGES, MainetConstants.Property.propPref.CAA, org);
            List<TbTaxMas> chequeDishonorTaxes = tbTaxMasService.findAllTaxesForBillGeneration(properySearchDto.getOrgId(),
                    properySearchDto.getDeptId(), chequeDishonor.getLookUpId(), null);
            if (chequeDishonorTaxes != null && !chequeDishonorTaxes.isEmpty()) {
            	chequeDishonorTaxes.forEach(t -> {
                    tax.put(t.getTaxId(), t.getTaxDesc());
                    taxDisplaySeq.put(t.getTaxId(), t.getTaxDisplaySeq());
                });
            }
        }
        if (taxes != null && !taxes.isEmpty()) {
            taxes.forEach(t -> {
                tax.put(t.getTaxId(), t.getTaxDesc());
                taxDisplaySeq.put(t.getTaxId(), t.getTaxDisplaySeq());
            });
        }
        if(CollectionUtils.isNotEmpty(taxesBillDemand)) {
        	taxesBillDemand.forEach(t -> {
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
        List<MainBillMasEntity> bills = new LinkedList<>();
        if (StringUtils.isNotBlank(properySearchDto.getFlatNo())) {
            bills = propertyMainBillRepository.fetchBillSForViewPropertyByPropAndFlatNo(properySearchDto.getProertyNo(),
                    properySearchDto.getFlatNo(), properySearchDto.getOrgId());
        } else {
            bills = propertyMainBillRepository.fetchBillSForViewProperty(properySearchDto.getProertyNo());
        }
		List<MainBillMasEntity> newBills = new LinkedList<>();
		if (StringUtils.equals(properySearchDto.getBillMethodChangeFlag(), MainetConstants.FlagY)) {
			newBills = propertyMainBillRepository.fetchBillSForViewProperty(properySearchDto.getProertyNo());
			newBills = newBills.stream().filter(billl -> billl.getFlatNo() == null).collect(Collectors.toList());
			if (CollectionUtils.isNotEmpty(newBills)) {
				bills.addAll(newBills);
			}
		}
	
		if (CollectionUtils.isNotEmpty(bills)) {
            bills.forEach(bill -> {
            	AtomicDouble totalOutstanding = new AtomicDouble(0);
                TbBillMas b = new TbBillMas();
                List<TbBillDet> tbBillDet = new ArrayList<>(0);
                BeanUtils.copyProperties(bill, b);
                b.setBmTotalAmount(0);
                b.setBmRemarks(Utility.dateToString(b.getBmDuedate()));
                if (b.getBmBilldt() != null) {
                    b.setBmBilldtString(Utility.dateToString(b.getBmBilldt()));
                }
                if (b.getBillDistrDate() != null) {
                    b.setBillDistrDateString(Utility.dateToString(b.getBillDistrDate()));
                }
                if (b.getIntTo() != null) {
                    b.setCurrentBillFlag(Utility.dateToString(b.getIntTo()));
                }
                
                b.setBmCcnOwner(new SimpleDateFormat("MMM").format(b.getBmFromdt()) + " "
                        + Utility.getYearByDate(b.getBmFromdt()) + "-"
                        + new SimpleDateFormat("MMM").format(b.getBmTodt()) + " "
                        + Utility.getYearByDate(b.getBmTodt()));
                bill.getBillDetEntityList().forEach(billdet -> {
                    TbBillDet det = new TbBillDet();
                    BeanUtils.copyProperties(billdet, det);
                    totalOutstanding.addAndGet(det.getBdCurBalTaxamt() + det.getBdPrvBalArramt());
                    det.setTaxDesc(tax.get(det.getTaxId()));
                    det.setDisplaySeq(taxDisplaySeq.get(det.getTaxId()));
                    b.setBmTotalAmount(b.getBmTotalAmount() + billdet.getBdCurTaxamt());
					det.setBdBalAmtToTransfer(
							billdet.getBdCurBalTaxamt() != null ? BigDecimal.valueOf(billdet.getBdCurBalTaxamt())
									: null);// US - 130541 (Amount to be adjusted)                  
					tbBillDet.add(det);
                });
                b.setBmTotalOutstanding(totalOutstanding.doubleValue());
                b.setTbWtBillDet(tbBillDet);
                billMas.add(b);
            });
        }
        List<ProvisionalBillMasEntity> proBill = null;
        if(!Utility.isEnvPrefixAvailable(org, "PSCL")) {
        	if (StringUtils.isNotBlank(properySearchDto.getFlatNo())) {
                proBill = propertyProvisionalBillRepository.fetchBillSForViewPropertyByPropAndFlatNo(properySearchDto.getProertyNo(),
                        properySearchDto.getFlatNo(), properySearchDto.getOrgId());
            } else {
                proBill = propertyProvisionalBillRepository.fetchBillSForViewProperty(properySearchDto.getProertyNo());
            }
        }

        if (proBill != null && !proBill.isEmpty()) {
            proBill.forEach(bill -> {
                TbBillMas b = new TbBillMas();
                BeanUtils.copyProperties(bill, b);
                b.setBmTotalAmount(0);
                b.setBmRemarks(Utility.dateToString(b.getBmDuedate()));
                if (b.getBmBilldt() != null) {
                    b.setBmBilldtString(Utility.dateToString(b.getBmBilldt()));
                }
                b.setBmCcnOwner(new SimpleDateFormat("MMM").format(b.getBmFromdt()) + " "
                        + Utility.getYearByDate(b.getBmFromdt()) + "-"
                        + new SimpleDateFormat("MMM").format(b.getBmTodt()) + " "
                        + Utility.getYearByDate(b.getBmTodt()));
                List<TbBillDet> tbBillDet = new ArrayList<>(0);
                bill.getProvisionalBillDetEntityList().forEach(billdet -> {
                    TbBillDet det = new TbBillDet();
                    BeanUtils.copyProperties(billdet, det);
                    det.setTaxDesc(tax.get(det.getTaxId()));
                    b.setBmTotalAmount(b.getBmTotalAmount() + billdet.getBdCurTaxamt());
					det.setBdBalAmtToTransfer(
							billdet.getBdCurBalTaxamt() != null ? BigDecimal.valueOf(billdet.getBdCurBalTaxamt())
									: null);// US - 130541 (Amount to be adjusted)
                    tbBillDet.add(det);
                });
                b.setTbWtBillDet(tbBillDet);
                billMas.add(b);
            });
        }
        return billMas;
    }

    @Override
    @POST
    @Path("/viewPaymentHistory")
    @Transactional(readOnly = true)
    public List<LookUp> getCollectionDetails(ProperySearchDto searchDto) {
        List<LookUp> dtoList = new ArrayList<>();
		List<TbServiceReceiptMasEntity> entity = new ArrayList<>();
        Organisation org = new Organisation();
        org.setOrgid(searchDto.getOrgId());
		if (StringUtils.isNotBlank(searchDto.getFlatNo())) {
			entity = iReceiptEntryService.getCollectionDetailsWithFlatNo(searchDto.getProertyNo(),
					searchDto.getFlatNo(), searchDto.getDeptId(), searchDto.getOrgId());
		} else {
			entity = iReceiptEntryService.getCollectionDetails(searchDto.getProertyNo(), searchDto.getDeptId(),
					searchDto.getOrgId());
		}		
		if (StringUtils.equals(searchDto.getBillMethodChangeFlag(), MainetConstants.FlagY)) {
			List<TbServiceReceiptMasEntity> oldReceipts = iReceiptEntryService
					.getCollectionDetails(searchDto.getProertyNo(), searchDto.getDeptId(), searchDto.getOrgId());
			oldReceipts = oldReceipts.stream().filter(receipt -> receipt.getFlatNo() == null)
					.collect(Collectors.toList());
			if (CollectionUtils.isNotEmpty(oldReceipts)) {
				entity.addAll(oldReceipts);
			}
		}
        List<String> serviceCodeList = Arrays.asList("SAS", "CIA", "NCA", "DES", "NPR", "PBP", "BMC");
        if (entity != null && !entity.isEmpty()) {
            entity.forEach(receipt -> {
                String serviceShortCode = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                        .fetchServiceShortCode(receipt.getSmServiceId(), searchDto.getOrgId());
                String payModeCode = CommonMasterUtility
                        .getNonHierarchicalLookUpObject(receipt.getReceiptModeDetail().get(0).getCpdFeemode(), org)
                        .getLookUpCode();

                LookUp data = new LookUp();
                if (!"RB".equals(receipt.getReceiptTypeFlag()) && serviceCodeList.contains(serviceShortCode)
                        && !StringUtils.equals(MainetConstants.FlagT, payModeCode)) {
                    data.setLookUpParentId(receipt.getRmRcptno());
                    data.setLookUpId(receipt.getRmRcptid());
                    data.setLookUpCode(Utility.dateToString(receipt.getRmDate()));
                    data.setLookUpType(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(receipt.getReceiptModeDetail().get(0).getCpdFeemode(), org)
                            .getDescLangFirst());
                    data.setOtherField(receipt.getRmAmount().toString());
                    data.setDescLangFirst(receipt.getRmReceivedfrom());
                    dtoList.add(data);
                }
            });
        }
        return dtoList;
    }

    @Override
    @Consumes("application/json")
    @POST
    @Path("/viewAssessmentMas")
    @Transactional(readOnly = true)
    public ProvisionalAssesmentMstDto fetchPropertyByPropNo(ProperySearchDto searchDto) {
        ProvisionalAssesmentMstDto assMstDto = null;
        if (StringUtils.isNotBlank(searchDto.getFlatNo())) {
            assMstDto = assesmentMastService.fetchLatestAssessmentByPropNoAndFlatNo(searchDto.getOrgId(),
                    searchDto.getProertyNo(), searchDto.getFlatNo());
        } else {
            assMstDto = assesmentMastService.fetchLatestAssessmentByPropNo(searchDto.getOrgId(),
                    searchDto.getProertyNo());
        }
        // if (assMstDto == null) {
        // assMstDto = iProvisionalAssesmentMstService
        // .fetchPropertyByPropNo(searchDto.getProertyNo(), searchDto.getOrgId());
        // }
        if (assMstDto != null) {
            String mobNo = "";
            String panNo = "";
            if (assMstDto.getAssCorrAddress() == null) {
                assMstDto.setProAsscheck(MainetConstants.FlagY);
            } else {
                assMstDto.setProAsscheck(MainetConstants.FlagN);
            }
            if (assMstDto.getAssLpYear() == null) {
                assMstDto.setProAssBillPayment(MainetConstants.Property.NOT_APP);
            } else {
                assMstDto.setProAssBillPayment(MainetConstants.Property.MANUAL);
            }
            // 127491
            Organisation org = iOrganisationService.getOrganisationById(searchDto.getOrgId());
            if (!Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL) && !Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL)
            		&& !Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_ASCL)) {
                for (int i = 0; i < assMstDto.getProvisionalAssesmentOwnerDtlDtoList().size(); i++) {
                    mobNo = maskNumber(assMstDto.getProvisionalAssesmentOwnerDtlDtoList().get(i).getAssoMobileno(), "********##");
                    assMstDto.getProvisionalAssesmentOwnerDtlDtoList().get(i).setAssoMobileno(mobNo);
                    if (assMstDto.getProvisionalAssesmentOwnerDtlDtoList().get(i).getAssoPanno() != null
                            && !assMstDto.getProvisionalAssesmentOwnerDtlDtoList().get(i).getAssoPanno().isEmpty()) {
                        panNo = maskNumber(assMstDto.getProvisionalAssesmentOwnerDtlDtoList().get(i).getAssoPanno(),
                                "********##");
                        assMstDto.getProvisionalAssesmentOwnerDtlDtoList().get(i).setAssoPanno(panNo);
                    }
                }
            }
            assMstDto.setLocationName(
                    iLocationMasService.getLocationNameById(assMstDto.getLocId(), searchDto.getOrgId()));
        }
        return assMstDto;
    }

    @Override
    @POST
    @Path("/viewAssessmentMasWithDesc")
    @Transactional(readOnly = true)
    public ProvisionalAssesmentMstDto viewAssessmentMasWithDesc(ProperySearchDto searchDto) throws Exception {
        ProvisionalAssesmentMstDto assMstDto = iProvisionalAssesmentMstService
                .fetchPropertyByPropNo(searchDto.getProertyNo(), searchDto.getOrgId());
        if (assMstDto == null) {
            assMstDto = assesmentMastService.fetchLatestAssessmentByPropNo(searchDto.getOrgId(),
                    searchDto.getProertyNo());
        }
        if (assMstDto.getAssCorrAddress() == null) {
            assMstDto.setProAsscheck(MainetConstants.FlagY);
        } else {
            assMstDto.setProAsscheck(MainetConstants.FlagN);
        }
        if (assMstDto.getAssLpYear() == null) {
            assMstDto.setProAssBillPayment(MainetConstants.Property.NOT_APP);
        } else {
            assMstDto.setProAssBillPayment(MainetConstants.Property.MANUAL);
        }
        assMstDto.setLocationName(iLocationMasService.getLocationNameById(assMstDto.getLocId(), searchDto.getOrgId()));
        Organisation org = new Organisation();
        org.setOrgid(searchDto.getOrgId());
        setDropDownValues(org, assMstDto);
        return assMstDto;
    }

    @Override
    @POST
    @Path("/viewUploadedDocs")
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public Map<Long, List<DocumentDetailsVO>> fetchApplicaionDocuments(ProperySearchDto searchDto) {
        Map<Long, List<DocumentDetailsVO>> document = new LinkedHashMap<>();
        List<Long> newAppIdList = new ArrayList<>();
        List<Long> applicaionids = null;
        if (StringUtils.isNotBlank(searchDto.getFlatNo())) {
            applicaionids = assesmentMastService.fetchApplicationAgainstPropertyWithFlatNo(searchDto.getOrgId(),
                    searchDto.getProertyNo(), searchDto.getFlatNo());
        } else {
            applicaionids = assesmentMastService.fetchApplicationAgainstProperty(searchDto.getOrgId(),
                    searchDto.getProertyNo());
        }
        
        if (applicaionids == null || applicaionids.isEmpty()) {
        	if (StringUtils.isNotBlank(searchDto.getFlatNo())) {
                applicaionids = iProvisionalAssesmentMstService
                        .fetchApplicationAgainstPropertyWithFlatNo(searchDto.getOrgId(), searchDto.getProertyNo(),
                                searchDto.getFlatNo());
            } else {
                applicaionids = iProvisionalAssesmentMstService
                        .fetchApplicationAgainstProperty(searchDto.getOrgId(), searchDto.getProertyNo());
            }
        }
		List<Long> mutApplicaionids = null;
		if (StringUtils.isNotBlank(searchDto.getFlatNo())) {
			mutApplicaionids = propertyTransferService.getAllApplicationIdsByPropNoNFlat(searchDto.getProertyNo(),
					searchDto.getFlatNo(), searchDto.getOrgId());
		} else {
			mutApplicaionids = propertyTransferService.getAllApplicationIdsByPropNo(searchDto.getProertyNo(),
					searchDto.getOrgId());
		}
		if (CollectionUtils.isNotEmpty(mutApplicaionids)) {
			applicaionids.addAll(mutApplicaionids);
		}

        if (applicaionids != null && !applicaionids.isEmpty()) {
            applicaionids.forEach(appId -> {
                if (appId != null) {
                    newAppIdList.add(appId);
                }
            });
        }
        if (!newAppIdList.isEmpty()) {
            List<CFCAttachment> att = iChecklistVerificationService.getDocumentUploadedForApplications(newAppIdList,
                    searchDto.getOrgId());
            if (att != null && !att.isEmpty()) {
                att.forEach(doc -> {
                    List<DocumentDetailsVO> docs = document.get(doc.getApplicationId());
                    if (docs == null || docs.isEmpty()) {
                        DocumentDetailsVO d = new DocumentDetailsVO();
                        docs = new ArrayList<>(0);
                        d.setDocumentName(doc.getAttFname());
                        d.setUploadedDocumentPath(doc.getAttPath());
                        d.setDocumentType(doc.getDmsDocId());
                        d.setDescriptionType(serviceMasterService.getServiceNameByServiceId(doc.getServiceId()));
                        docs.add(d);

                    } else {
                        DocumentDetailsVO d = new DocumentDetailsVO();
                        // docs = new ArrayList<>(0);
                        d.setDocumentName(doc.getAttFname());
                        d.setUploadedDocumentPath(doc.getAttPath());
                        d.setDocumentType(doc.getDmsDocId());
                        docs.add(d);
                    }
                    document.put(doc.getApplicationId(), docs);
                });

            }
        }
        return document;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentDetailsVO> fetchDESUploadedDocs(Long orgId, String propNo) {
        List<DocumentDetailsVO> document = new ArrayList<>();
        List<AttachDocs> att = attachDocsService.findByCode(orgId, propNo);
        if (att != null && !att.isEmpty()) {
            att.forEach(doc -> {
                DocumentDetailsVO d = new DocumentDetailsVO();
                d.setDocumentName(doc.getAttFname());// doc name
                d.setUploadedDocumentPath(doc.getAttPath());
                d.setDocumentType(doc.getDmsDocId());
                d.setDoc_DESC_ENGL(doc.getDmsDocName());// doc description
                document.add(d);
            });
        }
        return document;
    }

    @Override
    @POST
    @Path("/viewUploadedDocuments")
    @Transactional(readOnly = true)
    public List<DocumentDetailsVO> fetchAppDocuments(ProperySearchDto searchDto) {
        List<DocumentDetailsVO> document = new ArrayList<>();
        List<CFCAttachment> att = iChecklistVerificationService.getDocumentUploaded(searchDto.getApplicationId(),
                searchDto.getOrgId());
        if (att != null && !att.isEmpty()) {
            att.forEach(doc -> {
                DocumentDetailsVO d = new DocumentDetailsVO();
                d.setDocumentName(doc.getAttFname());
                d.setUploadedDocumentPath(doc.getAttPath());
                d.setDocumentType(doc.getDmsDocId());
                document.add(d);
            });
        }
        return document;
    }

    @Override
    @Transactional(readOnly = true)
    public int getTotalSearchCount(ProperySearchDto properySearchDto, PagingDTO pagingDTO, GridSearchDTO gridSearchDTO,
            Long serviceId) {
        return iProvisionalAssessmentMstDao.getTotalSearchCount(properySearchDto, pagingDTO, gridSearchDTO, serviceId);
    }

    @Override
    @POST
    @Path("/getTotalSearchCount")
    @Transactional(readOnly = true)
    @Consumes("application/json")
    public int getTotalSearchCountByRequest(ViewPropertyDetailRequestDto viewPropDet) {

        return iProvisionalAssessmentMstDao.getTotalSearchCount(viewPropDet.getPropSearchDto(),
                viewPropDet.getPagingDTO(), viewPropDet.getGridSearchDTO(), null);
    }

    @Override
    @POST
    @Path("/getAndGenearteJasperForBill")
    @Transactional(readOnly = true)
    public ProperySearchDto getAndGenearteJasperForBill(ProperySearchDto propDto) {
        Organisation org = null;
        if (propDto.getOrg() == null) {
            org = iOrganisationService.getOrganisationById(propDto.getOrgId());
        } else {
            org = propDto.getOrg();
        }
        TbBillMas billMas = iProvisionalBillService.fetchBillFromBmIdNo(propDto.getBmIdNo(), propDto.getOrgId());
        if (billMas == null) {
            billMas = propertyMainBillService.fetchBillFromBmIdNo(propDto.getBmIdNo(), propDto.getOrgId());
        }

        PrintBillMaster printBillMas = propertyBillGenerationService.getPrintBillMasterBillGeanertion(org,
                propDto.getLangId(), billMas, FileNetApplicationClient.getInstance());
        List<PrintBillMaster> dtolist = new ArrayList<>();
        dtolist.add(printBillMas);
        final Map<String, Object> map = new HashMap<>();
        final String subReportSource = Filepaths.getfilepath() + MainetConstants.JASPER_REPORT_NAME
                + MainetConstants.FILE_PATH_SEPARATOR;
        map.put("SUBREPORT_DIR", subReportSource);
        String jrxmlName = "PropertyTaxBillPrintingEnglish.jrxml";
        final String jrxmlFileLocation = Filepaths.getfilepath() + "jasperReport" + MainetConstants.FILE_PATH_SEPARATOR
                + jrxmlName;
        final String fileName = ReportUtility.generateReportFromCollectionUtility(null, null, jrxmlFileLocation, map,
                dtolist, propDto.getEmpId());
        if (!fileName.equals(MainetConstants.SERVER_ERROR)) {
            propDto.setFilePath(fileName);
        }
        final File file = new File(Filepaths.getfilepath() + fileName);
        byte[] billFile = null;
        try {
            billFile = FileUtils.readFileToByteArray(file);
        } catch (Exception e) {
            throw new FrameworkException(e);
        }
        propDto.setBillFile(billFile);
        return propDto;
    }

    @Override
    @POST
    @Path("/getAndGenearteJasperForReceipt")
    @Transactional(readOnly = true)
    public ProperySearchDto getAndGenearteJasperForReceipt(ProperySearchDto propDto) {

        Organisation org = null;
        if (propDto.getOrg() == null) {
            org = iOrganisationService.getOrganisationById(propDto.getOrgId());
        } else {
            org = propDto.getOrg();
        }
        TbServiceReceiptMasEntity recipt = iReceiptEntryService.findByRmRcptidAndOrgId(propDto.getRecptId(), org.getOrgid());

        final String deptName = departmentService.getDepartment(MainetConstants.Property.PROP_DEPT_SHORT_CODE, "A")
                .getDpDeptdesc();
        ReceiptPrintDto receiptPrintDto = getDataForReceiptPrinting(org, deptName, recipt,
                FileNetApplicationClient.getInstance(), propDto.getLangId());
        List<ReceiptPrintDto> dtolist = new ArrayList<>();
        dtolist.add(receiptPrintDto);
        final Map<String, Object> map = new HashMap<>();
        final String subReportSource = Filepaths.getfilepath() + MainetConstants.JASPER_REPORT_NAME
                + MainetConstants.FILE_PATH_SEPARATOR;
        map.put("SUBREPORT_DIR", subReportSource);
        String jrxmlName = "property_Tax_Receipt.jrxml";
        final String jrxmlFileLocation = Filepaths.getfilepath() + "jasperReport" + MainetConstants.FILE_PATH_SEPARATOR
                + jrxmlName;
        final String fileName = ReportUtility.generateReportFromCollectionUtility(null, null, jrxmlFileLocation, map,
                dtolist, propDto.getEmpId());
        if (!fileName.equals(MainetConstants.SERVER_ERROR)) {
            propDto.setFilePath(fileName);
        }
        final File file = new File(Filepaths.getfilepath() + fileName);
        byte[] billFile = null;
        try {
            billFile = FileUtils.readFileToByteArray(file);
        } catch (Exception e) {
            throw new FrameworkException(e);
        }
        propDto.setBillFile(billFile);
        return propDto;
    }

    private ReceiptPrintDto getDataForReceiptPrinting(Organisation organisation, final String deptName,
            TbServiceReceiptMasEntity recipt, FileNetApplicationClient fileNetApp, int langId) {
        SimpleDateFormat sm = new SimpleDateFormat(MainetConstants.DATE_FRMAT);
        AtomicDouble totAmt = new AtomicDouble(0);
        AtomicDouble totDemAmt = new AtomicDouble(0);
        ProvisionalAssesmentMstDto assessment = iProvisionalAssesmentMstService
                .fetchPropertyByPropNo(recipt.getAdditionalRefNo(), organisation.getOrgid());
        if (assessment == null) {
            assessment = assesmentMastService.fetchLatestAssessmentByPropNo(organisation.getOrgid(),
                    recipt.getAdditionalRefNo());
        }

        ReceiptPrintDto receiptPrintDto = new ReceiptPrintDto();
        receiptPrintDto.setPropertyNoL(ApplicationSession.getInstance().getMessage("propertydetails.PropertyNo."));
        receiptPrintDto.setPropertyNoV(assessment.getAssNo());
        receiptPrintDto.setUlbNameL(organisation.getONlsOrgname());
        receiptPrintDto.setReceipt(ApplicationSession.getInstance().getMessage("prop.rec.print.recipt"));
        final Object[] finData = iFinancialYearService.getFinacialYearByDate(new Date());
        if ((finData != null) && (finData.length > 0)) {
            String year = Utility.getFinancialYear((Date) finData[1], (Date) finData[2]);
            receiptPrintDto.setFinancialYearV(year);
        }
        String lookupCode = CommonMasterUtility.getNonHierarchicalLookUpObject(organisation.getOrgCpdId(), organisation)
                .getLookUpCode();
        if (lookupCode.equals(PrefixConstants.OrgnisationType.CORPORATION)) {
            receiptPrintDto.setActL(ApplicationSession.getInstance().getMessage("prop.bill.orgType.Corporation"));
        } else if (lookupCode.equals(PrefixConstants.OrgnisationType.COUNCIL)) {
            receiptPrintDto.setActL(ApplicationSession.getInstance().getMessage("prop.bill.orgType.muncipal"));
        } else {
            receiptPrintDto.setActL(ApplicationSession.getInstance().getMessage("prop.bill.orgType.panchayat"));
        }
        receiptPrintDto.setReceiptNoL(ApplicationSession.getInstance().getMessage("property.receiptno"));
        receiptPrintDto.setReceiptNoV(Long.toString(recipt.getRmRcptno()));
        receiptPrintDto.setReceiptDateL(ApplicationSession.getInstance().getMessage("property.receiptdate"));
        receiptPrintDto.setReceiptDateV(sm.format(recipt.getCreatedDate()));
        // receiptPrintDto.setReceiptNoV(recipt.getCreatedDate().toString());
        receiptPrintDto.setFinancialYearL(ApplicationSession.getInstance().getMessage("bill.finYear"));
        receiptPrintDto.setSubjectL(ApplicationSession.getInstance().getMessage("prop.rec.print.subj.key"));
        receiptPrintDto.setSubjectV(ApplicationSession.getInstance().getMessage("prop.rec.print.subj.value"));
        // receiptPrintDto.setFinancialYearV(financialYearV);
        receiptPrintDto.setDepartmentL(ApplicationSession.getInstance().getMessage("prop.rec.print.subj.value"));
        receiptPrintDto.setDepartmentV(deptName);
        final List<LookUp> lookupList = CommonMasterUtility.getListLookup(MainetConstants.Property.propPref.WZB,
                organisation);
        if (assessment.getAssWard1() != null) {
            if (langId == 0) {
                receiptPrintDto.setWard1L(lookupList.get(0).getDescLangFirst());// need to change label from prefix for
                                                                                // hierarchical
            } else {
                receiptPrintDto.setWard1L(lookupList.get(0).getDescLangSecond());
            }

            receiptPrintDto.setWard1V(CommonMasterUtility.getHierarchicalLookUp(assessment.getAssWard1(), organisation)
                    .getDescLangFirst());
        }
        if (assessment.getAssWard2() != null) {
            if (langId == 0) {
                receiptPrintDto.setWard2L(lookupList.get(1).getDescLangFirst());// need to change label from prefix for
                                                                                // hierarchical
            } else {
                receiptPrintDto.setWard2L(lookupList.get(1).getDescLangSecond());
            }
            receiptPrintDto.setWard2V(CommonMasterUtility.getHierarchicalLookUp(assessment.getAssWard2(), organisation)
                    .getDescLangFirst());
        }
        if (assessment.getAssWard3() != null) {
            if (langId == 0) {
                receiptPrintDto.setWard3L(lookupList.get(2).getDescLangFirst());// need to change label from prefix for
                                                                                // hierarchical
            } else {
                receiptPrintDto.setWard3L(lookupList.get(2).getDescLangSecond());
            }
            receiptPrintDto.setWard3V(CommonMasterUtility.getHierarchicalLookUp(assessment.getAssWard3(), organisation)
                    .getDescLangFirst());
        }
        if (assessment.getAssWard4() != null) {
            if (langId == 0) {
                receiptPrintDto.setWard4L(lookupList.get(3).getDescLangFirst());// need to change label from prefix for
                                                                                // hierarchical
            } else {
                receiptPrintDto.setWard4L(lookupList.get(3).getDescLangSecond());
            }
            receiptPrintDto.setWard4V(CommonMasterUtility.getHierarchicalLookUp(assessment.getAssWard4(), organisation)
                    .getDescLangFirst());
        }
        if (assessment.getAssWard5() != null) {
            if (langId == 0) {
                receiptPrintDto.setWard5L(lookupList.get(4).getDescLangFirst());// need to change label from prefix for
                                                                                // hierarchical
            } else {
                receiptPrintDto.setWard5L(lookupList.get(4).getDescLangSecond());
            }
            receiptPrintDto.setWard5V(CommonMasterUtility.getHierarchicalLookUp(assessment.getAssWard5(), organisation)
                    .getDescLangFirst());
        }
        receiptPrintDto.setModeL(ApplicationSession.getInstance().getMessage("prop.rec.print.mode"));
        receiptPrintDto.setModeV(CommonMasterUtility
                .getNonHierarchicalLookUpObject(recipt.getReceiptModeDetail().get(0).getCpdFeemode(), organisation)
                .getDescLangFirst());
        ProvisionalAssesmentOwnerDtlDto owner = assessment.getProvisionalAssesmentOwnerDtlDtoList().get(0);
        StringBuilder name = new StringBuilder(owner.getAssoOwnerName());
        if (owner.getRelationId() != null) {
            name.append(" " + CommonMasterUtility.getNonHierarchicalLookUpObject(owner.getRelationId(), organisation)
                    .getDescLangFirst());
            name.append(" " + owner.getAssoGuardianName());
        }
        receiptPrintDto.setReceivedFromL(ApplicationSession.getInstance().getMessage("ownerdetail.Ownername"));
        receiptPrintDto.setReceivedFromV(name.toString());
        receiptPrintDto.setAddressL(ApplicationSession.getInstance().getMessage("prop.rec.print.addr"));
        receiptPrintDto.setAddressV(assessment.getAssAddress());
        receiptPrintDto.setAppNoL(ApplicationSession.getInstance().getMessage("prop.rec.print.app.no"));
        if (recipt.getApmApplicationId() != null) {
            receiptPrintDto.setAppNoV(recipt.getApmApplicationId().toString());
        }
        receiptPrintDto.setAppDateL(ApplicationSession.getInstance().getMessage("prop.rec.print.app.date"));
        receiptPrintDto.setAppDateV(sm.format(assessment.getCreatedDate()));
        receiptPrintDto.setPaymentModeL(ApplicationSession.getInstance().getMessage("prop.rec.print.pay.mode"));
        receiptPrintDto.setPaymentModeV(CommonMasterUtility
                .getNonHierarchicalLookUpObject(recipt.getReceiptModeDetail().get(0).getCpdFeemode(), organisation)
                .getDescLangFirst());
        receiptPrintDto.setChequeNoL(ApplicationSession.getInstance().getMessage("prop.rec.print.cheq.no"));
        if (recipt.getReceiptModeDetail().get(0).getRdChequeddno() != null) {
            receiptPrintDto.setChequeNoV(recipt.getReceiptModeDetail().get(0).getRdChequeddno().toString());
        }
        receiptPrintDto.setChequeDateL(ApplicationSession.getInstance().getMessage("prop.rec.print.cheq.date"));
        if (recipt.getReceiptModeDetail().get(0).getRdChequedddate() != null) {
            receiptPrintDto.setChequeDateV(recipt.getReceiptModeDetail().get(0).getRdChequedddate().toString());
        }
        receiptPrintDto.setAmountL(ApplicationSession.getInstance().getMessage("property.DefaulterListReport.amount"));
        receiptPrintDto.setAmountV(recipt.getRmAmount().toString());
        List<ReceiptPrintDetailDto> reciptDetList = new ArrayList<>();
        Map<Long, ReceiptPrintDetailDto> taxMap = new HashMap<>();
        recipt.getReceiptFeeDetail().forEach(det -> {
            ReceiptPrintDetailDto printDetails = taxMap.get(det.getTaxId());
            if (printDetails == null) {
                printDetails = new ReceiptPrintDetailDto();
                printDetails.setDetailsV(
                        tbTaxMasService.findTaxByTaxIdAndOrgId(det.getTaxId(), organisation.getOrgid()).getTaxDesc());
                printDetails.setPaymentAmtV(MainetConstants.ZERO);
                printDetails.setReceivedAmtV(MainetConstants.ZERO);
                reciptDetList.add(printDetails);
                taxMap.put(det.getTaxId(), printDetails);
            }
            if (det.getRmDemand() != null) {
                Double demand = 0d;
                demand = Double.valueOf(printDetails.getPaymentAmtV()) + det.getRmDemand().doubleValue();
                printDetails.setPaymentAmtV(demand.toString());
                totDemAmt.addAndGet(det.getRmDemand().doubleValue());
            }
            if (det.getRfFeeamount() != null) {
                Double feeAmount = Double.valueOf(printDetails.getReceivedAmtV()) + det.getRfFeeamount().doubleValue();
                totAmt.addAndGet(det.getRfFeeamount().doubleValue());
                printDetails.setReceivedAmtV(feeAmount.toString());
            }
        });
        receiptPrintDto.setAmtInWordsV(Utility.convertNumberToWord(recipt.getRmAmount().doubleValue()));

        receiptPrintDto.setDetailList(reciptDetList);
        receiptPrintDto.setDetailsL(ApplicationSession.getInstance().getMessage("prop.rec.print.details"));
        receiptPrintDto.setPaymentAmtL(ApplicationSession.getInstance().getMessage("prop.rec.print.payAmout"));
        receiptPrintDto.setTotalPayAmtV(totDemAmt.toString());
        receiptPrintDto.setTotalReceivedAmtV(totAmt.toString());
        receiptPrintDto.setReceivedAmtL(ApplicationSession.getInstance().getMessage("prop.rec.print.recAmount"));
        receiptPrintDto.setAmtInWordsL(ApplicationSession.getInstance().getMessage("prop.rec.print.amtInWords"));
        receiptPrintDto.setTotalAmtL(ApplicationSession.getInstance().getMessage("prop.rec.print.totAmount"));
        receiptPrintDto.setNoteV(ApplicationSession.getInstance().getMessage("prop.rec.print.noteV"));
        receiptPrintDto.setNoteL(ApplicationSession.getInstance().getMessage("prop.rec.print.noteL"));
        receiptPrintDto.setFinalNoteL(MainetConstants.SUDHA_URL);
        receiptPrintDto.setBankNameL(ApplicationSession.getInstance().getMessage("prop.rec.print.bank"));
        receiptPrintDto.setLoiNoL(ApplicationSession.getInstance().getMessage("prop.rec.print.Loi"));
        final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
                + MainetConstants.FILE_PATH_SEPARATOR + "SHOW_DOCS";
        String dirPath = ApplicationSession.getInstance().getMessage("tempfilepath");
        String filePath = Utility.downloadedFileUrl(organisation.getoLogo(), outputPath, fileNetApp);
        if (filePath != null && !filePath.isEmpty()) {
            receiptPrintDto.setLogo(dirPath + filePath);
        }
        return receiptPrintDto;
    }

    @POST
    @Path("/checkPropertyExitOrNot")
    @Transactional(readOnly = true)
    @Consumes("application/json")
    @Override
    public boolean checkPropertyExitOrNot(ProperySearchDto dto) {
        List<Object[]> entity = iProvisionalAssessmentMstDao.searchPropetyForView(dto, null, null,
                null);
        if (entity != null && !entity.isEmpty()) {
            return true;
        }
        return false;
    }

    @POST
    @Path("/getPropertyDetails")
    @Transactional(readOnly = true)
    @Consumes("application/json")
    @Override
    public PropertyDetailDto getPropertyDetails(PropertyInputDto dto) {
        LOGGER.info("REST call to getPropertyDetails");
        PropertyDetailDto detailDto = new PropertyDetailDto();
        PropertyMastEntity popMas = null;

        // StringBuilder inputCheckSum = new StringBuilder();
        // inputCheckSum.append(dto.getFirstName());
        // inputCheckSum.append(MainetConstants.operator.ORR);
        // inputCheckSum.append(dto.getLastName());
        // String outputCheckSum =
        // ApplicationContextProvider.getApplicationContext().getBean(ICommonEncryptionAndDecryption.class)
        // .commonCheckSum(inputCheckSum.toString());
        // if (StringUtils.equals(dto.getCheckSum(), outputCheckSum)) {
        try {
            popMas = primaryPropertyService.getPropertyDetailsByPropNo(dto.getPropertyNo(),
                    dto.getOrgId());
            if (popMas != null) {
                // return new ResponseEntity<PropertyDetailDto>(HttpStatus.NOT_FOUND);

                Organisation org = new Organisation();
                org.setOrgid(dto.getOrgId());
                // detailDto = new PropertyDetailDto();
                detailDto.setPropNo(popMas.getAssNo());
                detailDto.setOrgId(dto.getOrgId());
                detailDto.setAddress(popMas.getAssAddress());
                detailDto.setCorrAddress(popMas.getAssCorrAddress());
                detailDto.setPinCode(popMas.getAssPincode());
                detailDto.setOldPropNo(popMas.getAssOldpropno());
                detailDto.setTppPlotNo(popMas.getTppPlotNo());
                detailDto.setUniquePropertyId(popMas.getUniquePropertyId());
                detailDto.setLocation(iLocationMasService.getLocationNameById(popMas.getLocId(), dto.getOrgId()));
                detailDto.setPropAddressReg(popMas.getAssAddressReg());
                List<AssesmentOwnerDtlEntity> ownerList = assesmentMastService
                        .getOwnerDetailsByPropertyNo(dto.getOrgId(), dto.getPropertyNo());
                if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL)) {
                	     ownerList = assesmentMastService
                              .getOwnerDetailsByPropertyNo(dto.getOrgId(), String.valueOf(popMas.getPmPropid()));
                }
                AssesmentOwnerDtlEntity owner = null;
                if(CollectionUtils.isNotEmpty(ownerList)){
                	 owner = ownerList.get(0);
                	 detailDto.setPrimaryOwnerName(owner.getAssoOwnerName());
                     detailDto.setPrimaryOwnerMobNo(owner.getAssoMobileno());
                     detailDto.setGardianOwnerName(owner.getAssoGuardianName());
                     detailDto.setOwnerEmail(owner.geteMail());
                     detailDto.setOwnerRelationId(owner.getRelationId());
                     detailDto.setOwnerNameReg(owner.getAssoOwnerNameReg());
                }
                              
                StringBuilder jointOwnerName = new StringBuilder();
                List<String> jointOwnerNameReg = new ArrayList<>();
                ownerList.forEach(owners -> {
                    if (StringUtils.isEmpty(jointOwnerName.toString())) {
                        jointOwnerName.append(owners.getAssoOwnerName());
                    } else {
                        jointOwnerName.append(MainetConstants.BLANK_WITH_SPACE + owners.getAssoOwnerName());
                    }

                    if (owners.getAssoOwnerNameReg() != null) {
                        jointOwnerNameReg.add(owners.getAssoOwnerNameReg());
                    }
                });
                detailDto.setJointOwnerName(jointOwnerName.toString());
                detailDto.setJointOwnerNameReg(CollectionUtils.isNotEmpty(jointOwnerNameReg)
                        ? String.join(MainetConstants.BLANK_WITH_SPACE, jointOwnerNameReg)
                        : null);
                detailDto.setStatus(MainetConstants.SUCCESS_MSG);
                detailDto.setResponseMsg(
                        ApplicationSession.getInstance().getMessage("property.succMsg.validPropNo"));
                detailDto.setHttpstatus(HttpStatus.OK);
                setWardZoneCode(detailDto, popMas, org);
                
                
                
                

           	 Organisation organisation = new Organisation();
           	 organisation.setOrgid(dto.getOrgId());
                
                StringBuilder ownerFullName = new StringBuilder();
                int ownerSize = 1;
                for (AssesmentOwnerDtlEntity ownerDto : ownerList) {
                    if (ownerDto.getAssoOType() != null && ownerDto.getAssoOType().equals(MainetConstants.Property.PRIMARY_OWN)) {

                        if (StringUtils.isEmpty(ownerFullName.toString())) {
                            ownerFullName.append(ownerDto.getAssoOwnerName());
                            ownerFullName.append(MainetConstants.WHITE_SPACE);
                            if (ownerDto.getRelationId() != null && ownerDto.getRelationId() > 0) {
                                LookUp reltaionLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(ownerDto.getRelationId(),
                                        org);
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
                        if (ownerSize < ownerList.size()) {
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
                        if (ownerSize < ownerList.size()) {
                            ownerFullName.append("," + " ");
                        }
                    }
                }
                
                detailDto.setFullOwnerName(ownerFullName.toString());
           
                
                
                
                
                if (popMas.getAssLandType() != null)
                    detailDto.setAssLandTypeDesc(
                            CommonMasterUtility.getNonHierarchicalLookUpObject(popMas.getAssLandType(), org).getLookUpDesc());
                detailDto.setTppSurveyNumber(popMas.getTppSurveyNumber());
                detailDto.setTppKhataNo(popMas.getTppKhataNo());
                detailDto.setTppPlotNo(popMas.getTppPlotNo());
                if (popMas.getPropLvlRoadType() != null)
                    detailDto.setProAssdRoadfactorDesc(
                            CommonMasterUtility.getNonHierarchicalLookUpObject(popMas.getPropLvlRoadType(), org).getLookUpDesc());
                detailDto.setAreaName(popMas.getAreaName());
                detailDto.setLandTypeId(popMas.getAssLandType());
                detailDto.setRoadTypeId(popMas.getPropLvlRoadType());
                detailDto.setAssPlotArea(popMas.getAssPlotArea());
                if(dto.getApplicationId() != null && dto.getApplicationId() > 0) {
                	Long transferType = propertyTransferRepository.getTransferTypeByApplicationId(dto.getApplicationId(), org.getOrgid());
                	if(transferType !=null) {
                		LookUp transferTypeLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(transferType, org);
                		if(transferTypeLookUp != null) {
                			detailDto.setTransferTypeDesc(transferTypeLookUp.getLookUpDesc());
                		}
                	}
                }

                // To check assessment is done for current financial year or not
                Long financeYearId = iFinancialYear.getFinanceYearId(new Date());
                boolean assessment = selfAssessmentService.CheckForAssesmentFiledForCurrYear(org.getOrgid(),
                        popMas.getAssNo(), null, MainetConstants.FlagA, financeYearId);
                if (assessment) {
                    detailDto.setAssessmentCheckFlag(MainetConstants.Y_FLAG);
                } else {
                    detailDto.setAssessmentCheckFlag(MainetConstants.N_FLAG);
                }
                // end

                Employee emp = iEmployeeService.findEmployeeByIdAndOrgId(popMas.getTaxCollEmp(), dto.getOrgId());
                if (emp != null) {
                    detailDto.setpTaxCollEmpId(emp.getEmpId());
                    detailDto.setpTaxCollEmpName(emp.getEmpname() + " " + emp.getEmplname());
                    detailDto.setpTaxCollEmpMobNo(emp.getEmpmobno());
                    detailDto.setpTaxCollEmpEmailId(emp.getEmpemail());
                }
                List<String> usageList = new ArrayList<>();
                List<String> propertyTypeList = new ArrayList<>();
                double arv=0.0;
                for (PropertyDetEntity det : popMas.getProDetList()) {
                    LookUp usageLookup = CommonMasterUtility.getHierarchicalLookUp(det.getAssdUsagetype1(),
                            dto.getOrgId());
                    String code = usageLookup.getDescLangFirst();
                    String usageRegDesc = usageLookup.getDescLangSecond();
                    detailDto.setUasge(code);
                    detailDto.setUsageTypeReg(usageRegDesc);
                    if (!usageList.isEmpty() && !usageList.contains(code)) {
                        detailDto.setUasge(MainetConstants.Property.MIX_USAGE);
                        detailDto.setUsageTypeReg(ApplicationSession.getInstance().getMessage("property.usageTypeMix"));
                    }
                    usageList.add(code);

                    LookUp propertyTypeLookup = null;
                    if(det.getAssdNatureOfproperty1()!= null)
                    propertyTypeLookup = CommonMasterUtility
                            .getHierarchicalLookUp(det.getAssdNatureOfproperty1(), dto.getOrgId());
                    if(propertyTypeLookup!= null){
                    String propertyType = propertyTypeLookup.getDescLangFirst();
                    String propertyTypeReg = propertyTypeLookup.getDescLangSecond();                              
                    detailDto.setPropertyType(propertyType);
                    detailDto.setPropertyTypeReg(propertyTypeReg);
                    if (!propertyTypeList.isEmpty() && !propertyTypeList.contains(propertyType)) {
                        detailDto.setPropertyType(MainetConstants.Property.MIX_USAGE);
                        detailDto.setPropertyTypeReg(
                                ApplicationSession.getInstance().getMessage("property.propertyTypeMix"));
                    }
                    propertyTypeList.add(propertyType);
                    }
                    if(det.getAssdRv()!=null) {
                    arv+= det.getAssdRv();
                    }
                }
                detailDto.setTotalArv(arv);
                // D#72824
                List<PropertyDetEntity> detEntityList = popMas.getProDetList();
                if (CollectionUtils.isNotEmpty(detEntityList)) {
                    PropertyDetEntity detEntity = detEntityList.get(0);
                    if (detEntity != null && detEntity.getAssdOccupancyType() > 0) {
                        detailDto.setOccupancyType(CommonMasterUtility
                                .getNonHierarchicalLookUpObject(detEntity.getAssdOccupancyType(), org).getDescLangFirst());
                    }
                }// END
                if(owner!= null){
                	if (owner.getGenderId() != null) {
                        detailDto.setGender(
                                CommonMasterUtility.getNonHierarchicalLookUpObject(owner.getGenderId(), org).getLookUpCode());
                    }
                }
                
                Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Property.PROP_DEPT_SHORT_CODE);
                List<TbBillMas> billMasList = null;

                int count = iAssessmentMastDao.getCountWhetherMaxBmIdExistInMainBill(popMas.getAssNo(), dto.getOrgId());
                if (count > 0) {
                    // Form Main Bill table
                    billMasList = propertyMainBillService.fetchNotPaidBillForAssessment(popMas.getAssNo(), dto.getOrgId());
                } else {
                    // From Provisional Bill Table
                    billMasList = iProvisionalBillService.fetchNotPaidBillForProAssessment(popMas.getAssNo(),
                            dto.getOrgId());
                }
                if (billMasList != null && !billMasList.isEmpty()) {
                    TbBillMas billMas = billMasList.get(billMasList.size() - 1);// Fetching latest Bill

                    if (billMas.getBmPaidFlag().equals(MainetConstants.PAY_STATUS.NOT_PAID)) {
                        // propertyBRMSService.fetchInterstRate(billMasList, org, deptId);// calculating interest rate through
                        // BRMS
                        // billMasterCommonService.calculateInterest(billMasList, org, deptId, MainetConstants.Y_FLAG, null);
                        Long finYearId = iFinancialYear.getFinanceYearId(new Date());
                        BillDisplayDto surCharge = propertyService.calculateSurcharge(org, deptId, billMasList,
                                popMas.getAssNo(), MainetConstants.Property.SURCHARGE, finYearId, null);
                        List<BillDisplayDto> rebateDto = propertyBRMSService.fetchEarlyPayRebateRate(billMasList, org, deptId,
                                null, null);
                        double totalAmt = propertyService.getTotalPaybaleAmount(billMasList, rebateDto, surCharge);
                        detailDto.setTotalOutsatandingAmt(totalAmt);

                    }
                }

                // } else {
                // LOGGER.error(("Checksum mismatch") + ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Checksum mismatch"));
                // // throw new FrameworkException("Checksum mismatch");
                // }
            } else {
                detailDto.setStatus(MainetConstants.FAILURE_MSG);
                detailDto.setHttpstatus(HttpStatus.NOT_FOUND);
                detailDto.setResponseMsg(ApplicationSession.getInstance().getMessage("property.validMsg.validPropNo"));
            }
        } catch (final Exception exception) {
            LOGGER.error("Exception occur in getPropertydetails", exception);
            detailDto = new PropertyDetailDto();
            detailDto.setStatus(MainetConstants.FAILURE_MSG);
            detailDto.setResponseMsg(MainetConstants.FAILURE_MSG);
            detailDto.setErrorMsg(exception.getMessage());
        }
        return detailDto;

    }

    private void setWardZoneCode(PropertyDetailDto detailDto, PropertyMastEntity popMas, Organisation org) {
        if (popMas.getAssWard1() != null) {
            detailDto.setWard1(CommonMasterUtility.getHierarchicalLookUp(popMas.getAssWard1(), org).getLookUpCode());
            detailDto.setWd1(popMas.getAssWard1());
        }
        if (popMas.getAssWard2() != null) {
            detailDto.setWard2(CommonMasterUtility.getHierarchicalLookUp(popMas.getAssWard2(), org).getLookUpCode());
            detailDto.setWd2(popMas.getAssWard2());
        }
        if (popMas.getAssWard3() != null) {
            detailDto.setWard3(CommonMasterUtility.getHierarchicalLookUp(popMas.getAssWard3(), org).getLookUpCode());
            detailDto.setWd3(popMas.getAssWard3());
        }
        if (popMas.getAssWard4() != null) {
            detailDto.setWard4(CommonMasterUtility.getHierarchicalLookUp(popMas.getAssWard4(), org).getLookUpCode());
            detailDto.setWd4(popMas.getAssWard4());
        }
        if (popMas.getAssWard5() != null) {
            detailDto.setWard5(CommonMasterUtility.getHierarchicalLookUp(popMas.getAssWard5(), org).getLookUpCode());
            detailDto.setWd5(popMas.getAssWard5());
        }
    }

    // to get text description of the selected value of a dropdownList
    public void setDropDownValues(Organisation org, ProvisionalAssesmentMstDto proAssdto) throws Exception {
        proAssdto.setProAssOwnerTypeName(CommonMasterUtility
                .getNonHierarchicalLookUpObject(proAssdto.getAssOwnerType(), org).getDescLangFirst());

        proAssdto.setProAssdRoadfactorDesc(CommonMasterUtility
                .getNonHierarchicalLookUpObject(proAssdto.getPropLvlRoadType(), org).getDescLangFirst());

        String ownerTypeCode = CommonMasterUtility.getNonHierarchicalLookUpObject(proAssdto.getAssOwnerType(), org)
                .getLookUpCode();
        if (MainetConstants.Property.SO.equals(ownerTypeCode) || MainetConstants.Property.JO.equals(ownerTypeCode)) {
            for (ProvisionalAssesmentOwnerDtlDto dto : proAssdto.getProvisionalAssesmentOwnerDtlDtoList()) {
                dto.setProAssGenderId(
                        CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getGenderId(), org).getDescLangFirst());
                dto.setProAssRelationId(CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getRelationId(), org)
                        .getDescLangFirst());
            }
        } else {
            ProvisionalAssesmentOwnerDtlDto ownerDto = new ProvisionalAssesmentOwnerDtlDto();
            ownerDto.setGenderId(null);
            ownerDto.setRelationId(null);
            ownerDto.setAssoAddharno(null);
        }

        proAssdto.setProAssdRoadfactorDesc(CommonMasterUtility
                .getNonHierarchicalLookUpObject(proAssdto.getPropLvlRoadType(), org).getDescLangFirst());

        if (proAssdto.getAssLandType() != null) {
            proAssdto.setAssLandTypeDesc(CommonMasterUtility
                    .getNonHierarchicalLookUpObject(proAssdto.getAssLandType(), org).getDescLangFirst());

        }

        proAssdto.setAssWardDesc1(
                CommonMasterUtility.getHierarchicalLookUp(proAssdto.getAssWard1(), org).getDescLangFirst());

        if (proAssdto.getAssWard2() != null) {

            proAssdto.setAssWardDesc2(
                    CommonMasterUtility.getHierarchicalLookUp(proAssdto.getAssWard2(), org).getDescLangFirst());
        }

        if (proAssdto.getAssWard3() != null) {

            proAssdto.setAssWardDesc3(
                    CommonMasterUtility.getHierarchicalLookUp(proAssdto.getAssWard3(), org).getDescLangFirst());
        }

        if (proAssdto.getAssWard4() != null) {

            proAssdto.setAssWardDesc4(
                    CommonMasterUtility.getHierarchicalLookUp(proAssdto.getAssWard4(), org).getDescLangFirst());
        }

        if (proAssdto.getAssWard5() != null) {

            proAssdto.setAssWardDesc5(
                    CommonMasterUtility.getHierarchicalLookUp(proAssdto.getAssWard5(), org).getDescLangFirst());
        }

        List<FinancialYear> financialYearList = iFinancialYear.getFinanceYearListFromGivenDate(org.getOrgid(),
                proAssdto.getFaYearId(), new Date());
        Map<Long, String> financialYearMap = new LinkedHashMap<>();

        if (!financialYearList.isEmpty()) {
            String financialYear = null;
            for (final FinancialYear finYearTemp : financialYearList) {
                financialYear = Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate());
                financialYearMap.put(finYearTemp.getFaYear(), financialYear);
            }
        }

        for (ProvisionalAssesmentDetailDto detaildto : proAssdto.getProvisionalAssesmentDetailDtoList()) {
            if (detaildto.getAssdBuildupArea() != null) {
                for (Map.Entry<Long, String> entry : financialYearMap.entrySet()) {
                    if (entry.getKey().toString().equals(detaildto.getFaYearId().toString())) {
                        detaildto.setProFaYearIdDesc(entry.getValue());
                    }
                    SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.DATE_FRMAT);
                    detaildto.setProAssdConstructionDate(formatter.format(detaildto.getAssdYearConstruction()));
                    detaildto.setProAssdUsagetypeDesc(CommonMasterUtility
                            .getHierarchicalLookUp(detaildto.getAssdUsagetype1(), org).getDescLangFirst());

                    if (detaildto.getAssdUsagetype2() != null) {
                        detaildto.setProAssdUsagetypeDesc2(CommonMasterUtility
                                .getHierarchicalLookUp(detaildto.getAssdUsagetype2(), org).getDescLangFirst());
                    }
                    if (detaildto.getAssdUsagetype3() != null) {
                        detaildto.setProAssdUsagetypeDesc3(CommonMasterUtility
                                .getHierarchicalLookUp(detaildto.getAssdUsagetype3(), org).getDescLangFirst());
                    }
                    if (detaildto.getAssdUsagetype4() != null) {
                        detaildto.setProAssdUsagetypeDesc4(CommonMasterUtility
                                .getHierarchicalLookUp(detaildto.getAssdUsagetype4(), org).getDescLangFirst());
                    }
                    if (detaildto.getAssdUsagetype5() != null) {
                        detaildto.setProAssdUsagetypeDesc5(CommonMasterUtility
                                .getHierarchicalLookUp(detaildto.getAssdUsagetype5(), org).getDescLangFirst());
                    }

                    detaildto.setAssdNatureOfpropertyDesc1(CommonMasterUtility
                            .getHierarchicalLookUp(detaildto.getAssdNatureOfproperty1(), org).getDescLangFirst());

                    if (detaildto.getAssdNatureOfproperty2() != null) {
                        detaildto.setAssdNatureOfpropertyDesc2(CommonMasterUtility
                                .getHierarchicalLookUp(detaildto.getAssdNatureOfproperty2(), org).getDescLangFirst());
                    }
                    if (detaildto.getAssdNatureOfproperty3() != null) {
                        detaildto.setAssdNatureOfpropertyDesc3(CommonMasterUtility
                                .getHierarchicalLookUp(detaildto.getAssdNatureOfproperty3(), org).getDescLangFirst());
                    }
                    if (detaildto.getAssdNatureOfproperty4() != null) {
                        detaildto.setAssdNatureOfpropertyDesc4(CommonMasterUtility
                                .getHierarchicalLookUp(detaildto.getAssdNatureOfproperty4(), org).getDescLangFirst());
                    }
                    if (detaildto.getAssdNatureOfproperty5() != null) {
                        detaildto.setAssdNatureOfpropertyDesc5(CommonMasterUtility
                                .getHierarchicalLookUp(detaildto.getAssdNatureOfproperty5(), org).getDescLangFirst());
                    }
                    detaildto.setProFloorNo(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(detaildto.getAssdFloorNo(), org).getDescLangFirst());
                    detaildto.setProAssdConstruTypeDesc(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(detaildto.getAssdConstruType(), org).getDescLangFirst());

                    detaildto.setProAssdOccupancyTypeDesc(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(detaildto.getAssdOccupancyType(), org).getDescLangFirst());

                    for (ProvisionalAssesmentFactorDtlDto provisionalAssesmentFactorDtlDto : detaildto
                            .getProvisionalAssesmentFactorDtlDtoList()) {
                        if (provisionalAssesmentFactorDtlDto.getAssfFactorValueId() != null) {
                            provisionalAssesmentFactorDtlDto.setProAssfFactorValueDesc(CommonMasterUtility
                                    .getNonHierarchicalLookUpObject(
                                            provisionalAssesmentFactorDtlDto.getAssfFactorValueId(), org)
                                    .getDescLangFirst());
                            provisionalAssesmentFactorDtlDto
                                    .setProAssfFactorIdDesc(CommonMasterUtility
                                            .getNonHierarchicalLookUpObject(
                                                    provisionalAssesmentFactorDtlDto.getAssfFactorId(), org)
                                            .getDescLangFirst());
                        }
                    }
                }

            }
        }

        setAddressDetails(proAssdto);
        formatDate(proAssdto);

    }

    private void setAddressDetails(ProvisionalAssesmentMstDto proAssdto) {
        if (proAssdto.getProAsscheck().equalsIgnoreCase(MainetConstants.Y_FLAG)) {
            proAssdto.setAssCorrAddress(proAssdto.getAssAddress());
            proAssdto.setAssCorrPincode(proAssdto.getAssPincode());
            proAssdto.setAssCorrEmail(proAssdto.getAssEmail());
        }
    }

    // Commented not required for Sudha

    /*
     * private void setBillPayment() { if (getProvisionalAssesmentMstDto().getProAssBillPayment().equalsIgnoreCase(
     * MainetConstants.Property.NOT_APP)) { getProvisionalAssesmentMstDto().setProAssBillPaymentDesc(MainetConstants.
     * Property.NOT_APP_DESC); // blank model set data getProvisionalAssesmentMstDto().setAssLpReceiptNo(null);
     * getProvisionalAssesmentMstDto().setAssLpReceiptAmt(null); getProvisionalAssesmentMstDto().setAssLpReceiptDate(null);
     * getProvisionalAssesmentMstDto().setAssLpYear(null); getProvisionalAssesmentMstDto().setBillAmount(null);
     * getProvisionalAssesmentMstDto().setOutstandingAmount(null);
     * getProvisionalAssesmentMstDto().setProAssLpReceiptDateFormat(null);
     * getProvisionalAssesmentMstDto().setProAssLpYearDesc(null); } else if
     * (getProvisionalAssesmentMstDto().getProAssBillPayment().equalsIgnoreCase( MainetConstants.Property.MANUAL)) {
     * getProvisionalAssesmentMstDto().setProAssBillPaymentDesc(MainetConstants. Property.MANUAL_DESC); } }
     */

    private void formatDate(ProvisionalAssesmentMstDto proAssdto) {
        SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.DATE_FRMAT);
        // Commented not required for Sudha
        /*
         * if (getProvisionalAssesmentMstDto().getProAssBillPayment().equalsIgnoreCase( MainetConstants.Property.MANUAL)) {
         * this.getProvisionalAssesmentMstDto() .setProAssLpReceiptDateFormat(formatter.format(getProvisionalAssesmentMstDto(
         * ).getAssLpReceiptDate())); }
         */
        proAssdto.setProAssAcqDateFormat(formatter.format(proAssdto.getAssAcqDate()));
        // Commented not required for Sudha
        /*
         * for (ProvisionalAssesmentDetailDto dto : this.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList()) {
         * dto.setProAssdConstructionDate(formatter.format(dto. getProAssdYearConstruction())); }
         */
    }

    @Override
    @POST
    @Path("/searchPropertyData")
    @Transactional(readOnly = true)
    public List<ProperySearchDto> searchPropertyDetailsByApiRequest(ViewPropertyDetailRequestDto viewPropDet) {
        return searchPropertyDetailsFromApiRequest(viewPropDet.getPropSearchDto(), viewPropDet.getPagingDTO(),
                viewPropDet.getGridSearchDTO(), null);
    }

    @Transactional(readOnly = true)
    public List<ProperySearchDto> searchPropertyDetailsFromApiRequest(ProperySearchDto searchDto, PagingDTO pagingDTO,
            GridSearchDTO gridSearchDTO, Long serviceId) {
        List<ProperySearchDto> resultList = new ArrayList<>();
        if ((searchDto.getKhasraNo() != null && !searchDto.getKhasraNo().isEmpty())
                || (searchDto.getVsrNo() != null && !searchDto.getVsrNo().isEmpty())) {
            List<Object[]> entity = iProvisionalAssessmentMstDao.searchPropetyDetailsForApi(searchDto, pagingDTO,
                    gridSearchDTO, serviceId);
            if (entity != null && !entity.isEmpty()) {
                for (Object[] assMst : entity) {
                    ProperySearchDto mst = new ProperySearchDto();
                    mst.setProertyNo(assMst[0].toString());
                    if (assMst[1] != null) {
                        mst.setOldPid(assMst[1].toString());
                    }
                    if (assMst[2] != null) {
                        mst.setOwnerName(assMst[2].toString());
                    }
                    if (assMst[3] != null) {
                        mst.setMobileno(maskNumber(assMst[3].toString(), "********##"));

                    }
                    mst.setRowId(assMst[0].toString());
                    mst.setDeptShortName(MainetConstants.Property.PROP_DEPT_SHORT_CODE);
                    if (assMst[4] != null) {
                        mst.setKhasraNo(assMst[4].toString());
                    }
                    if (assMst[5] != null) {
                        mst.setVillageNo(assMst[5].toString());
                    }
                    if (assMst[10] != null) {
                        mst.setOutstandingAmt(assMst[10].toString());
                    }
                    if (assMst[11] != null) {
                        mst.setReceiptAmt(assMst[11].toString());
                    }
                    if (assMst[12] != null) {
                        mst.setReceiptDate(
                                new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(assMst[12]));
                    }
                    mst.setWaterOutstanding(getWaterOutstandingByPropNo(assMst[0].toString()));
                    resultList.add(mst);
                }
            }
        }

        return resultList;

    }

    @Override
    @POST
    @Path("/getRevenueReceiptDetails")
    @Transactional(readOnly = true)
    public ChallanReceiptPrintDTO getRevenueReceiptDetails(ProperySearchDto requestDto) {
        ChallanReceiptPrintDTO dataDTO = null;
        try {
            TbReceiptDuplicateEntity entity = repository.findByDupRcptByRcptIdAndrcptNoAndRefNo(requestDto.getRecptId(),
                    requestDto.getRecptNo(), requestDto.getProertyNo());
            if (entity != null) {
                TbReceiptDuplicateDTO rcptPrintDto = new TbReceiptDuplicateDTO();
                BeanUtils.copyProperties(entity, rcptPrintDto);
                dataDTO = new ObjectMapper().readValue(rcptPrintDto.getDupReceiptData(),
                        ChallanReceiptPrintDTO.class);
            }
        } catch (IOException e) {
            throw new FrameworkException("Problem while getting respose in duplicate receipt", e);
        }
        return dataDTO;

    }
    
    private double getWaterOutstandingByPropNo(String propNo) {
        double oustandingAmount = 0;
        try {
            Class<?> clazz = null;
            Object dynamicServiceInstance = null;
            String serviceClassName = null;
            serviceClassName = messageSource.getMessage("WNC", new Object[] {},
                    StringUtils.EMPTY, Locale.ENGLISH);
            clazz = ClassUtils.forName(serviceClassName, ApplicationContextProvider.getApplicationContext()
                    .getClassLoader());
            dynamicServiceInstance = ApplicationContextProvider.getApplicationContext().getAutowireCapableBeanFactory()
                    .autowire(clazz, 4, false);
            final Method method = ReflectionUtils.findMethod(clazz,
                    ApplicationSession.getInstance().getMessage("water.getOutstanding.by.propNo"),
                    new Class[] { String.class });
            oustandingAmount = (double) ReflectionUtils.invokeMethod(method, dynamicServiceInstance,
                    new Object[] { propNo });
        } catch (Exception exception) {
            throw new FrameworkException(
                    "Problem occured while calling getTotalOutstandingOfConnNosAssocWithPropNo  method through refelection utils",
                    exception);
        }

        return oustandingAmount;
    }

    private boolean checkWardZoneMapping(Object[] assMst, TbLocationMas locationMas, Long propertyDeptId, Long orgId) {
        AtomicBoolean mappingFlag = new AtomicBoolean(false);
        if (locationMas != null && StringUtils.equalsIgnoreCase(locationMas.getLocNameEng(), "Head Office")) {
            return true;
        }
        if (locationMas != null && CollectionUtils.isNotEmpty(locationMas.getLocOperationWZMappingDto())) {
            List<LocOperationWZMappingDto> propertyLocationOperWard = locationMas.getLocOperationWZMappingDto().stream()
                    .filter(operationalLocation -> operationalLocation.getDpDeptId().equals(propertyDeptId))
                    .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(propertyLocationOperWard)) {
                for (LocOperationWZMappingDto operationalWardZone : propertyLocationOperWard) {
                    LookUp hierarchicalLookUp = CommonMasterUtility
                            .getHierarchicalLookUp(operationalWardZone.getCodIdOperLevel1(), orgId);
                    if ((assMst[6] != null && ((hierarchicalLookUp.getLookUpCode().equals("ALL")) || (Long
                            .parseLong(String.valueOf(assMst[6])) == (operationalWardZone.getCodIdOperLevel1()))))
                            && ((assMst[7] == null) || (assMst[7] != null
                                    && ((operationalWardZone.getCodIdOperLevel2() == (-1)) || (Long.parseLong(
                                            String.valueOf(assMst[7])) == (operationalWardZone.getCodIdOperLevel2())))))
                            && ((assMst[13] == null) || (assMst[13] != null
                                    && ((operationalWardZone.getCodIdOperLevel3() == (-1)) || (Long.parseLong(String
                                            .valueOf(assMst[13])) == (operationalWardZone.getCodIdOperLevel3())))))
                            && ((assMst[14] == null) || (assMst[14] != null
                                    && ((operationalWardZone.getCodIdOperLevel4() == (-1)) || (Long.parseLong(String
                                            .valueOf(assMst[14])) == (operationalWardZone.getCodIdOperLevel4())))))
                            && ((assMst[15] == null) || (assMst[15] != null
                                    && ((operationalWardZone.getCodIdOperLevel5() == (-1)) || (Long.parseLong(String
                                            .valueOf(assMst[15])) == (operationalWardZone.getCodIdOperLevel5())))))) {
                        mappingFlag.set(true);
                    }
                }
            }
        }
        return mappingFlag.get();
    }

    @Override
    @POST
    @Path("/getPropDetails")
    public CommonChallanDTO getPropDetails(CommonChallanDTO dto) { // #102456 By Arun
        StringBuilder jointOwnerName = new StringBuilder();
        List<ProvisionalAssesmentMstDto> provDtoList = assesmentMastService.getPropDetailFromAssByPropNo(dto.getOrgId(),
                dto.getPropNoConnNoEstateNoV());
        if (CollectionUtils.isNotEmpty(provDtoList)) {
            ProvisionalAssesmentMstDto assMst = provDtoList.get(provDtoList.size() - 1);
            dto.setReferenceNo(assMst.getAssOldpropno());
            dto.setPlotNo(assMst.getTppPlotNo());
            assMst.getProvisionalAssesmentOwnerDtlDtoList().forEach(data -> {
                if (StringUtils.isEmpty(jointOwnerName.toString())) {
                    jointOwnerName
                            .append(data.getAssoOwnerName() + MainetConstants.WHITE_SPACE + data.getAssoGuardianName());
                } else {
                    jointOwnerName.append(MainetConstants.BLANK_WITH_SPACE + data.getAssoOwnerName()
                            + MainetConstants.WHITE_SPACE + data.getAssoGuardianName());
                }
            });
            dto.setApplicantFullName(jointOwnerName.toString());
        }
        return dto;
    }

    @Override
    @POST
    @Path("/getPropDetailsByAppNo")
    public CommonChallanDTO getPropDetailsByAppNo(CommonChallanDTO dto) {
        PropertyTransferMasterDto tranferDto = propertyTransferService.getPropTransferMstByAppId(dto.getOrgId(),
                dto.getApplNo());
        StringBuilder transferOwnerFullName = new StringBuilder();
        tranferDto.getPropTransferOwnerList().forEach(owner -> {
            if (StringUtils.isEmpty(transferOwnerFullName.toString())) {
                transferOwnerFullName.append(owner.getOwnerName() + MainetConstants.WHITE_SPACE);
            } else {
                transferOwnerFullName.append(
                        MainetConstants.operator.AMPERSAND + MainetConstants.WHITE_SPACE + owner.getOwnerName());
            }
        });
        dto.setTransferOwnerFullName(transferOwnerFullName.toString());
        SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.DATE_FRMAT);
        if (tranferDto.getActualTransferDate() != null)
            dto.setTransferDate(formatter.format(tranferDto.getActualTransferDate()));
        if (tranferDto.getCreatedDate() != null)
            dto.setTransferInitiatedDate(formatter.format(tranferDto.getCreatedDate()));
        if (tranferDto.getRegNo() != null)
            dto.setRegNo(tranferDto.getRegNo());
        String propNo = propertyTransferService.getPropertyNoByAppId(dto.getOrgId(), dto.getApplNo(), dto.getServiceId());
        List<ProvisionalAssesmentMstDto> provDtoList = assesmentMastService.getPropDetailFromAssByPropNo(dto.getOrgId(),
                propNo);
        if (CollectionUtils.isNotEmpty(provDtoList)) {
            ProvisionalAssesmentMstDto assMst = provDtoList.get(provDtoList.size() - 1);
            dto.setPropNoConnNoEstateNoL(assMst.getAssNo());
            if (StringUtils.isNotEmpty(assMst.getAssAddress())) {
                dto.setTransferAddress(assMst.getAssAddress());
            }
        }
        if (StringUtils.isNotEmpty(tranferDto.getFlatNo())) {
            dto.setFlatNo(tranferDto.getFlatNo());
        }
		// Generate certificate no
		try {
			SequenceConfigMasterDTO configMasterDTO = null;
			Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Property.PROP_DEPT_SHORT_CODE,
					PrefixConstants.STATUS_ACTIVE_PREFIX);
			configMasterDTO = seqGenFunctionUtility.loadSequenceData(dto.getOrgId(), deptId,
					MainetConstants.Property.TB_AS_TRANSFER_MST, MainetConstants.Property.CERTIFICATE_NO);
			String certificateNo = seqGenFunctionUtility.generateNewSequenceNo(configMasterDTO,
					new CommonSequenceConfigDto());
			certificateNo=certificateNo.replaceFirst("^0+(?!$)", "");
			if (certificateNo.length() == 1) {
				certificateNo = MainetConstants.NUMBERS.ZERO + certificateNo;
			}
			dto.setCertificateNo(certificateNo);
		} catch (Exception e) {
			LOGGER.error("Sequence number is not configured for generating certificate number :" + e.getMessage());
		}
		//
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProperySearchDto> searchPropertyDetailsForAll(ProperySearchDto searchDto, PagingDTO pagingDTO,
            GridSearchDTO gridSearchDTO, Long serviceId, Long locationId) {

        List<ProperySearchDto> resultList = new ArrayList<>();
        List<Object[]> entity = iProvisionalAssessmentMstDao.searchPropetyForViewForAll(searchDto, pagingDTO,
                gridSearchDTO, serviceId);

        /*
         * TbLocationMas locationMas = null; if (locationId != null) { locationMas =
         * ApplicationContextProvider.getApplicationContext().getBean(ILocationMasService.class) .findById(locationId); } LookUp
         * operationalWardMapping = null;
         */
        Organisation org = new Organisation();
        org.setOrgid(searchDto.getOrgId());

        /*
         * try { operationalWardMapping = CommonMasterUtility.getValueFromPrefixLookUp("LOM", "ENV", org); } catch (Exception
         * exception) { LOGGER.error("No Prefix found for LOM(ENV)"); }
         */

        if (entity != null && !entity.isEmpty()) {

            for (Object[] assMst : entity) {
            	
            	Optional<ProperySearchDto> checkPropExists = null;
                if (CollectionUtils.isNotEmpty(resultList)) {
                    checkPropExists = resultList.stream().filter(result -> result.getProertyNo().equals(assMst[0])).findFirst();
                }

            	if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)
            			|| ((CollectionUtils.isEmpty(resultList)) || (CollectionUtils.isNotEmpty(resultList)
                        && checkPropExists != null && !checkPropExists.isPresent()))) {
            		
                    ProperySearchDto mst = new ProperySearchDto();
                    LookUp lookup = null;
                    mst.setProertyNo(assMst[0].toString());

                    if (assMst[1] != null) {
                        mst.setOldPid(assMst[1].toString());
                    }
                    if (assMst[2] != null) {
                        mst.setOwnerName(assMst[2].toString());
                    }
                    if (assMst[3] != null) {
                        mst.setMobileno(assMst[3].toString());
                    }
                    if (assMst[4] != null) {
                        mst.setFlatNo(assMst[4].toString());
                    }
                    if (assMst[5] != null) {
                        lookup = CommonMasterUtility.getNonHierarchicalLookUpObject(Long.valueOf(assMst[5].toString()), org);
                    }
                    if (assMst[6] != null) {
                        mst.setGuardianName(assMst[6].toString());
                    }
                    if (assMst[7] != null) {
                        mst.seteMail(assMst[7].toString());
                    }
                    if (assMst[8] != null) {
                        mst.setOldPid(assMst[8].toString());
                    }
                    if (assMst[9] != null) {
                        mst.setHouseNo(assMst[9].toString());
                    }
                    
                    
                    if (assMst[10] != null) {
                        mst.setAssWard1(Long.valueOf(assMst[10].toString()));
                    }
                    if (assMst[11] != null) {
                    	mst.setAssWard2(Long.valueOf(assMst[11].toString()));
                    }
                    if (assMst[12] != null) {
                    	mst.setAssWard3(Long.valueOf(assMst[12].toString()));
                    }
                    if (assMst[13] != null) {
                    	mst.setAssWard4(Long.valueOf(assMst[13].toString()));
                    }
                    if (assMst[14] != null) {
                    	mst.setAssWard5(Long.valueOf(assMst[14].toString()));
                    }
                    if (assMst[16] != null) {
                    	mst.setAddress(assMst[16].toString());
                    }
                    if(assMst[17] != null) {
                       	mst.setOccupierName(assMst[17].toString());
                    }
                    if ((lookup != null && MainetConstants.FlagI.equals(lookup.getLookUpCode()) && (assMst[0] != null && assMst[4] !=null))) {
                        mst.setRowId(assMst[0].toString() + MainetConstants.operator.UNDER_SCORE + assMst[4].toString());
                    } else if (assMst[0] != null ){
                        mst.setRowId(assMst[0].toString());
                    }
                    mst.setDeptShortName(MainetConstants.Property.PROP_DEPT_SHORT_CODE);
                    resultList.add(mst);
            	}
            }
            /*
             * Long propertyDeptId = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
             * .getDepartmentIdByDeptCode("AS"); for (Object[] assMst : entity) { Optional<ProperySearchDto> checkPropExists =
             * null; LookUp lookup =null; if(assMst[5]!=null) { lookup =
             * CommonMasterUtility.getNonHierarchicalLookUpObject(Long.valueOf(assMst[5].toString()), org); } if
             * (CollectionUtils.isNotEmpty(resultList)) { if(lookup!=null && MainetConstants.FlagI.equals(lookup.getLookUpCode()))
             * { checkPropExists = resultList.stream().filter(result -> (result.getProertyNo().equals(assMst[0]) &&
             * result.getFlatNo().equals(assMst[3]))) .findFirst(); }else { checkPropExists = resultList.stream().filter(result ->
             * result.getProertyNo().equals(assMst[0])) .findFirst(); } } if (((CollectionUtils.isEmpty(resultList)) ||
             * (CollectionUtils.isNotEmpty(resultList) && checkPropExists != null && !checkPropExists.isPresent())) &&
             * (((locationMas == null) || (operationalWardMapping == null ||
             * StringUtils.isBlank(operationalWardMapping.getLookUpDesc()) ||
             * StringUtils.equals(operationalWardMapping.getOtherField(), "N"))) || (locationMas != null &&
             * checkWardZoneMapping(assMst, locationMas, propertyDeptId, searchDto.getOrgId())))) { ProperySearchDto mst = new
             * ProperySearchDto(); mst.setProertyNo(assMst[0].toString()); if (assMst[1] != null) {
             * mst.setOldPid(assMst[1].toString()); } if (assMst[2] != null) { mst.setOwnerName(assMst[2].toString()); } if
             * (assMst[3] != null) { mst.setFlatNo(assMst[3].toString()); } if (assMst[4] != null) {
             * mst.setMobileno(assMst[4].toString()); } if (assMst[5] != null) { mst.setAddress(assMst[5].toString()); }
             * if(lookup!=null && MainetConstants.FlagI.equals(lookup.getLookUpCode())) {
             * mst.setRowId(assMst[0].toString()+MainetConstants.operator.UNDER_SCORE+assMst[3].toString()); }else {
             * mst.setRowId(assMst[0].toString()); } mst.setDeptShortName(MainetConstants.Property.PROP_DEPT_SHORT_CODE);
             * resultList.add(mst); } }
             */
        }

        return resultList;

    }

    @Override
    @Transactional(readOnly = true)
    public int getTotalSearchCountForAll(ProperySearchDto properySearchDto, PagingDTO pagingDTO,
            GridSearchDTO gridSearchDTO, Long serviceId) {
        return iProvisionalAssessmentMstDao.getTotalSearchCountForAll(properySearchDto, pagingDTO, gridSearchDTO,
                serviceId);
    }

    @POST
    @Path("/getPropertyDetailsByPropNoNFlatNo")
    @Transactional(readOnly = true)
    @Consumes("application/json")
    @Override
    public PropertyDetailDto getPropertyDetailsByPropNoNFlatNo(PropertyInputDto dto) {
        LOGGER.info("REST call to getPropertyDetailsByPropNoNFlatNo");
        PropertyDetailDto detailDto = new PropertyDetailDto();
        Organisation org = new Organisation();
        org.setOrgid(dto.getOrgId());

        try {
            LookUp billMethod = null;
            LookUp billingMethodLookUp = null;
            ProvisionalAssesmentMstDto mastDto = null;
            try {
                billMethod = CommonMasterUtility.getValueFromPrefixLookUp("BMT", "ENV", org);
            } catch (Exception e) {
            }

            if (billMethod != null && StringUtils.isNotBlank(billMethod.getOtherField())
                    && StringUtils.equals(billMethod.getOtherField(), MainetConstants.FlagY)) {
                Long billingMethodId = primaryPropertyService.getBillMethodIdByPropNo(dto.getPropertyNo(),
                        dto.getOrgId());
                billingMethodLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(billingMethodId, org);
            }
            if (billingMethodLookUp != null
                    && StringUtils.equals(billingMethodLookUp.getLookUpCode(), MainetConstants.FlagI)) {
                mastDto = assesmentMastService.fetchLatestAssessmentByPropNoAndFlatNo(dto.getOrgId(),
                        dto.getPropertyNo(), dto.getFlatNo());
            } else {
                mastDto = assesmentMastService.fetchLatestAssessmentByPropNo(dto.getOrgId(), dto.getPropertyNo());
            }

            if (mastDto != null) {
                org.setOrgid(dto.getOrgId());
                detailDto.setPropNo(dto.getPropertyNo());
                detailDto.setOrgId(dto.getOrgId());
                detailDto.setAddress(mastDto.getAssAddress());
                detailDto.setCorrAddress(mastDto.getAssCorrAddress());
                detailDto.setPinCode(mastDto.getAssPincode());
                detailDto.setFlatNo(dto.getFlatNo());
                detailDto.setPropAddressReg(mastDto.getAssAddressReg());
                detailDto.setLocation(iLocationMasService.getLocationNameById(mastDto.getLocId(), dto.getOrgId()));
                detailDto.setServiceShortCode(serviceMasterService.fetchServiceShortCode(mastDto.getSmServiceId(), dto.getOrgId()));
                if (billingMethodLookUp != null
                        && StringUtils.equals(billingMethodLookUp.getLookUpCode(), MainetConstants.FlagI)) {
                    if (CollectionUtils.isNotEmpty(mastDto.getProvisionalAssesmentDetailDtoList())) {
                        ProvisionalAssesmentDetailDto provDetailDto = mastDto.getProvisionalAssesmentDetailDtoList().get(0);
                        detailDto.setPrimaryOwnerName(provDetailDto.getOccupierName());
                        detailDto.setPrimaryOwnerMobNo(provDetailDto.getOccupierMobNo());
                        detailDto.setOwnerEmail(provDetailDto.getOccupierEmail());
                        detailDto.setOwnerNameReg(provDetailDto.getOccupierNameReg());
                        detailDto.setJointOwnerName(provDetailDto.getOccupierName());
                        detailDto.setJointOwnerNameReg(provDetailDto.getOccupierNameReg());
                    }
                } else {
                    List<AssesmentOwnerDtlEntity> ownerList = assesmentMastService
                            .getOwnerDetailsByPropertyNo(dto.getOrgId(), dto.getPropertyNo());
                    if (CollectionUtils.isNotEmpty(ownerList)) {
                    AssesmentOwnerDtlEntity owner = ownerList.get(0);
                    detailDto.setPrimaryOwnerName(owner.getAssoOwnerName());
                    detailDto.setPrimaryOwnerMobNo(owner.getAssoMobileno());
                    detailDto.setGardianOwnerName(owner.getAssoGuardianName());
                    detailDto.setOwnerEmail(owner.geteMail());
                    detailDto.setOwnerRelationId(owner.getRelationId());
                    detailDto.setOwnerNameReg(owner.getAssoOwnerNameReg());
                    StringBuilder jointOwnerName = new StringBuilder();
                    List<String> jointOwnerNameReg = new ArrayList<>();
                    ownerList.forEach(owners -> {
                        if (StringUtils.isEmpty(jointOwnerName.toString())) {
                            jointOwnerName.append(owners.getAssoOwnerName());
                        } else {
                            jointOwnerName.append(MainetConstants.BLANK_WITH_SPACE + owners.getAssoOwnerName());
                        }

                        if (owners.getAssoOwnerNameReg() != null) {
                            jointOwnerNameReg.add(owners.getAssoOwnerNameReg());
                        }
                    });
                    detailDto.setJointOwnerName(jointOwnerName.toString());
                    detailDto.setJointOwnerNameReg(CollectionUtils.isNotEmpty(jointOwnerNameReg)
                            ? String.join(MainetConstants.BLANK_WITH_SPACE, jointOwnerNameReg)
                            : null);
                    if (owner.getGenderId() != null) {
                        detailDto.setGender(
                                CommonMasterUtility.getNonHierarchicalLookUpObject(owner.getGenderId(), org).getLookUpCode());
                    }
                }
                }
                detailDto.setStatus(MainetConstants.SUCCESS_MSG);
                detailDto.setResponseMsg(ApplicationSession.getInstance().getMessage("property.succMsg.validPropNo"));
                detailDto.setHttpstatus(HttpStatus.OK);
                setWardZoneCode(detailDto, mastDto, org);
                if (mastDto.getAssLandType() != null)
                    detailDto.setAssLandTypeDesc(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(mastDto.getAssLandType(), org).getLookUpDesc());
                detailDto.setTppSurveyNumber(mastDto.getTppSurveyNumber());
                detailDto.setTppKhataNo(mastDto.getTppKhataNo());
                detailDto.setTppPlotNo(mastDto.getTppPlotNo());
                if (mastDto.getPropLvlRoadType() != null) {
                    detailDto.setProAssdRoadfactorDesc(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(mastDto.getPropLvlRoadType(), org).getLookUpDesc());
                }
                detailDto.setAreaName(mastDto.getAreaName());
                detailDto.setLandTypeId(mastDto.getAssLandType());
                detailDto.setRoadTypeId(mastDto.getPropLvlRoadType());
                detailDto.setAssPlotArea(mastDto.getAssPlotArea());

                // To check assessment is done for current financial year or not
                Long financeYearId = iFinancialYear.getFinanceYearId(new Date());
                boolean assessment = selfAssessmentService.CheckForAssesmentFiledForCurrYear(org.getOrgid(),
                        mastDto.getAssNo(), null, MainetConstants.FlagA, financeYearId);
                if (assessment) {
                    detailDto.setAssessmentCheckFlag(MainetConstants.Y_FLAG);
                }
                // end
                Employee emp = iEmployeeService.findEmployeeByIdAndOrgId(mastDto.getTaxCollEmp(), dto.getOrgId());
                if (emp != null) {
                    detailDto.setpTaxCollEmpId(emp.getEmpId());
                    detailDto.setpTaxCollEmpName(emp.getEmpname() + " " + emp.getEmplname());
                    detailDto.setpTaxCollEmpMobNo(emp.getEmpmobno());
                    detailDto.setpTaxCollEmpEmailId(emp.getEmpemail());
                }
                List<String> usageList = new ArrayList<>();
                List<String> propertyTypeList = new ArrayList<>();
                for (ProvisionalAssesmentDetailDto det : mastDto.getProvisionalAssesmentDetailDtoList()) {
                    LookUp usageLookup = CommonMasterUtility.getHierarchicalLookUp(det.getAssdUsagetype1(), dto.getOrgId());
                    String code = usageLookup.getDescLangFirst();
                    String usageRegDesc = usageLookup.getDescLangSecond();
                    detailDto.setUasge(code);
                    detailDto.setUsageTypeReg(usageRegDesc);
                    if (!usageList.isEmpty() && !usageList.contains(code)) {
                        detailDto.setUasge(MainetConstants.Property.MIX_USAGE);
                        detailDto.setUsageTypeReg(ApplicationSession.getInstance().getMessage("property.usageTypeMix"));
                    }
                    usageList.add(code);

                    LookUp propertyTypeLookup = CommonMasterUtility
                            .getHierarchicalLookUp(det.getAssdNatureOfproperty1(), dto.getOrgId());
                    String propertyType = propertyTypeLookup.getDescLangFirst();
                    String propertyTypeReg = propertyTypeLookup.getDescLangSecond();
                    detailDto.setPropertyType(propertyType);
                    detailDto.setPropertyTypeReg(propertyTypeReg);
                    if (!propertyTypeList.isEmpty() && !propertyTypeList.contains(propertyType)) {
                        detailDto.setPropertyType(MainetConstants.Property.MIX_USAGE);
                        detailDto.setPropertyTypeReg(ApplicationSession.getInstance().getMessage("property.propertyTypeMix"));
                    }
                    propertyTypeList.add(propertyType);
                }
                List<ProvisionalAssesmentDetailDto> detDtoList = mastDto.getProvisionalAssesmentDetailDtoList();
                if (CollectionUtils.isNotEmpty(detDtoList)) {
                    ProvisionalAssesmentDetailDto detDto = detDtoList.get(0);
                    if (detDto != null && detDto.getAssdOccupancyType() > 0) {
                        detailDto.setOccupancyType(CommonMasterUtility
                                .getNonHierarchicalLookUpObject(detDto.getAssdOccupancyType(), org).getDescLangFirst());
                    }
                }
                Long deptId = departmentService
                        .getDepartmentIdByDeptCode(MainetConstants.Property.PROP_DEPT_SHORT_CODE);
                List<TbBillMas> billMasList = null;
                int count = iAssessmentMastDao.getCountWhetherMaxBmIdExistInMainBill(dto.getPropertyNo(),
                        dto.getOrgId());
                if (count > 0) {
                    // Form Main Bill table
                    if (billingMethodLookUp != null
                            && StringUtils.equals(billingMethodLookUp.getLookUpCode(), MainetConstants.FlagI)) {
                        billMasList = propertyMainBillService.fetchNotPaidBillForAssessmentByFlatNo(dto.getPropertyNo(),
                                dto.getOrgId(), dto.getFlatNo());
                    } else {
                        billMasList = propertyMainBillService.fetchNotPaidBillForAssessment(dto.getPropertyNo(),
                                dto.getOrgId());
                    }

                } else {
                    if (billingMethodLookUp != null
                            && StringUtils.equals(billingMethodLookUp.getLookUpCode(), MainetConstants.FlagI)) {
                        billMasList = iProvisionalBillService.fetchNotPaidBillForProAssessmentWithFlatNo(
                                dto.getPropertyNo(), dto.getFlatNo(), dto.getOrgId());
                    } else {
                        // From Provisional Bill Table
                        billMasList = iProvisionalBillService.fetchNotPaidBillForProAssessment(dto.getPropertyNo(),
                                dto.getOrgId());
                    }
                }
                if (billMasList != null && !billMasList.isEmpty()) {
                    TbBillMas billMas = billMasList.get(billMasList.size() - 1);// Fetching latest Bill

                    if (billMas.getBmPaidFlag().equals(MainetConstants.PAY_STATUS.NOT_PAID)) {
                        Long finYearId = iFinancialYear.getFinanceYearId(new Date());
                        BillDisplayDto surCharge = propertyService.calculateSurcharge(org, deptId, billMasList,
                                dto.getPropertyNo(), MainetConstants.Property.SURCHARGE, finYearId, null);
                        List<BillDisplayDto> rebateDto = propertyBRMSService.fetchEarlyPayRebateRate(billMasList, org,
                                deptId, null, null);
                        
                        double totalPenalty = 0;
                        LookUp penalIntLookUp = null;
                        try {
                            penalIntLookUp = CommonMasterUtility.getValueFromPrefixLookUp("PIT", "ENV", org);
                        } catch (Exception e) {
                            LOGGER.error("No Prefix found for ENV(PIT)");
                        }
                        if (penalIntLookUp != null && StringUtils.isNotBlank(penalIntLookUp.getOtherField())
                                && StringUtils.equals(penalIntLookUp.getOtherField(), MainetConstants.FlagY)) {
                            ApplicationContextProvider.getApplicationContext().getBean(PropertyBRMSService.class)
                                    .fetchInterstRate(billMasList, org, deptId);
                            totalPenalty = billMasterCommonService.calculatePenaltyInterest(billMasList, org, deptId, "Y",
                                    null, "N", null, null);
                        }

                        detailDto.setPenaltyAmount(totalPenalty);
                        
                        double totalAmt = propertyService.getTotalPaybaleAmount(billMasList, rebateDto, surCharge);
                        
                        detailDto.setTotalOutsatandingAmt(Math.round(totalAmt + totalPenalty));
                        detailDto.setTotalAmount(Math.round(totalAmt + totalPenalty));
                    }
                }
            } else {
                detailDto.setStatus(MainetConstants.FAILURE_MSG);
                detailDto.setHttpstatus(HttpStatus.NOT_FOUND);
                detailDto.setResponseMsg(ApplicationSession.getInstance().getMessage("property.validMsg.validPropNo"));
            }
        } catch (final Exception exception) {
            LOGGER.error("Exception occur in getPropertyDetailsByPropNoNFlatNo", exception);
            detailDto.setStatus(MainetConstants.FAILURE_MSG);
            detailDto.setResponseMsg(MainetConstants.FAILURE_MSG);
            detailDto.setErrorMsg(exception.getMessage());
        }
        return detailDto;

    }

    private void setWardZoneCode(PropertyDetailDto detailDto, ProvisionalAssesmentMstDto popMas, Organisation org) {
        if (popMas.getAssWard1() != null) {
            detailDto.setWard1(CommonMasterUtility.getHierarchicalLookUp(popMas.getAssWard1(), org).getLookUpCode());
            detailDto.setWd1(popMas.getAssWard1());
        }
        if (popMas.getAssWard2() != null) {
            detailDto.setWard2(CommonMasterUtility.getHierarchicalLookUp(popMas.getAssWard2(), org).getLookUpCode());
            detailDto.setWd2(popMas.getAssWard2());
        }
        if (popMas.getAssWard3() != null) {
            detailDto.setWard3(CommonMasterUtility.getHierarchicalLookUp(popMas.getAssWard3(), org).getLookUpCode());
            detailDto.setWd3(popMas.getAssWard3());
        }
        if (popMas.getAssWard4() != null) {
            detailDto.setWard4(CommonMasterUtility.getHierarchicalLookUp(popMas.getAssWard4(), org).getLookUpCode());
            detailDto.setWd4(popMas.getAssWard4());
        }
        if (popMas.getAssWard5() != null) {
            detailDto.setWard5(CommonMasterUtility.getHierarchicalLookUp(popMas.getAssWard5(), org).getLookUpCode());
            detailDto.setWd5(popMas.getAssWard5());
        }
    }

    @POST
    @Path("/checkWhetherPropertyIsActive")
    @Consumes("application/json")
    @Transactional(readOnly = true)
    @Override
    public Boolean checkWhetherPropertyIsActive(ProvisionalAssesmentMstDto dto) {
        List<String> checkActiveFlagList = null;
        String checkActiveFlag = null;
        if (StringUtils.isNotBlank(dto.getAssNo())) {
            checkActiveFlagList = assesmentMastService.checkActiveFlag(dto.getAssNo(), dto.getOrgId());
        } else {
            checkActiveFlagList = assesmentMastService.checkActiveFlagByOldPropNo(dto.getAssOldpropno(),
                    dto.getOrgId());
        }
        if (CollectionUtils.isNotEmpty(checkActiveFlagList)) {
            checkActiveFlag = checkActiveFlagList.get(checkActiveFlagList.size() - 1);
        }
        if (StringUtils.equals(checkActiveFlag, MainetConstants.FlagA)) {
            return true;
        }
        return false;
    }

    // Defect#130681 searching should be combination of owner name, guardian name and ward number apart from property number
    @POST
    @Path("/searchPropertyDetailsForMobile")
    @Transactional(readOnly = true)
    @Consumes("application/json")
    @Override
    public List<ProperySearchDto> searchPropertyDetailsForMobile(ProperySearchDto searchDto) {
        List<ProperySearchDto> searchDtoList = new ArrayList<>();
        searchDtoList = searchPropertyDetailsForAll(searchDto, null, null, null, null);
        if (CollectionUtils.isNotEmpty(searchDtoList)) {
            return searchDtoList;
        } else {
            ProperySearchDto dto = new ProperySearchDto();
            dto.setStatus(MainetConstants.WebServiceStatus.FAIL);
            dto.setErrorMsg("No records found");
            searchDtoList.add(dto);
            return searchDtoList;
        }

    }

    @SuppressWarnings("unused")
	@Override
    @POST
    @Path("/getPaymentHistory")
    @Transactional(readOnly = true)
	public List<ChallanReceiptPrintDTO> getPaymentHistory(ProperySearchDto searchDto) {
    	
    	LOGGER.info("Begin -->  " + this.getClass().getSimpleName() + " getPaymentHistory method");
    	List<ChallanReceiptPrintDTO> dtoList = new ArrayList<>();
		List<TbServiceReceiptMasEntity> entity = new ArrayList<>();
        Organisation org = new Organisation();
        org.setOrgid(searchDto.getOrgId());
        
        PropertyMastEntity propMas = null;
        
        if (StringUtils.isNotBlank(searchDto.getFlatNo())) {
        	propMas = primaryPropertyService.getPropertyDetailsByPropNoNFlatNo(searchDto.getProertyNo(), searchDto.getFlatNo(), searchDto.getOrgId());
        }else {
        	propMas = primaryPropertyService.getPropertyDetailsByPropNo(searchDto.getProertyNo(), searchDto.getOrgId());
        }
        
		if (StringUtils.isNotBlank(searchDto.getFlatNo())) {
			entity = iReceiptEntryService.getCollectionDetailsWithFlatNo(searchDto.getProertyNo(),
					searchDto.getFlatNo(), searchDto.getDeptId(), searchDto.getOrgId());
		} else {
			entity = iReceiptEntryService.getCollectionDetails(searchDto.getProertyNo(), searchDto.getDeptId(),
					searchDto.getOrgId());
		}		
		if(entity.size() > 3) {
			entity = entity.subList(entity.size() - 3, entity.size());
		}
        List<String> serviceCodeList = Arrays.asList("SAS", "CIA", "NCA", "DES", "NPR", "PBP", "BMC");
        if (CollectionUtils.isNotEmpty(entity) && propMas != null) {
            entity.forEach(receipt -> {
                String serviceShortCode = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                        .fetchServiceShortCode(receipt.getSmServiceId(), searchDto.getOrgId());
                String payModeCode = CommonMasterUtility
                        .getNonHierarchicalLookUpObject(receipt.getReceiptModeDetail().get(0).getCpdFeemode(), org)
                        .getLookUpCode();

                ChallanReceiptPrintDTO data = new ChallanReceiptPrintDTO();
                if (!"RB".equals(receipt.getReceiptTypeFlag()) && serviceCodeList.contains(serviceShortCode)
                        && !StringUtils.equals(MainetConstants.FlagT, payModeCode)) {
                    
                    try {
                        TbReceiptDuplicateEntity duplicateEntity = repository.findByDupRcptByRcptIdAndrcptNoAndRefNo(receipt.getRmRcptid(),
                        		receipt.getRmRcptno(), searchDto.getProertyNo());
                        if (duplicateEntity != null) {
                            TbReceiptDuplicateDTO rcptPrintDto = new TbReceiptDuplicateDTO();
                            BeanUtils.copyProperties(duplicateEntity, rcptPrintDto);
                            data = new ObjectMapper().readValue(rcptPrintDto.getDupReceiptData(),
                                    ChallanReceiptPrintDTO.class);
                        }
                    } catch (Exception e) {
                    	LOGGER.error("Problem while getting respose in duplicate receipt", e);
                    }
                    //D#154659
                    Boolean displaySeq = true;
            		for (ChallanReportDTO dto : data.getPaymentList()) {
            			if (dto.getDisplaySeq() == null) {
            				displaySeq = false;
            				break;
            			}
            		}
            		if (displaySeq) {
            			data.getPaymentList().sort(Comparator.comparing(ChallanReportDTO::getDisplaySeq));
            		}
                    dtoList.add(data);
                }
            });
        }else {
        	//D#153751  D#154578
        	List<CommonChallanPayModeDTO> multiPayModeList = new ArrayList<>(0);
        	CommonChallanPayModeDTO dto = new CommonChallanPayModeDTO();
        	if(propMas == null) {
        		dto.setErrorMesg("Invalid");
        	}else {
        		dto.setErrorMesg("NOT_FOUND");
        	}
        	multiPayModeList.add(dto);
        	ChallanReceiptPrintDTO printDTO = new ChallanReceiptPrintDTO();
        	printDTO.setMultiPayModeList(multiPayModeList);
        	dtoList.add(printDTO);
        }
        LOGGER.info("End -->  " + this.getClass().getSimpleName() + " getPaymentHistory method");
        return dtoList;
	}
	
    @SuppressWarnings("unused")
	@Override
    @POST
    @Path("/getBillHistory")
    @Transactional(readOnly = true)
	public List<ChallanReceiptPrintDTO> getBillHistory(ProperySearchDto searchDto) {
    	LOGGER.info("Begin -->  " + this.getClass().getSimpleName() + " getBillHistory method");
    	Organisation org = new Organisation();
        org.setOrgid(searchDto.getOrgId());
        List<ChallanReceiptPrintDTO> billMas = new ArrayList<>(0);
    	List<MainBillMasEntity> bills = new LinkedList<>();
    	
    	PropertyMastEntity propMas = null;
        
        if (StringUtils.isNotBlank(searchDto.getFlatNo())) {
        	propMas = primaryPropertyService.getPropertyDetailsByPropNoNFlatNo(searchDto.getProertyNo(), searchDto.getFlatNo(), searchDto.getOrgId());
        }else {
        	propMas = primaryPropertyService.getPropertyDetailsByPropNo(searchDto.getProertyNo(), searchDto.getOrgId());
        }
    	
    	Map<Long, String> tax = new HashMap<>(0);
        Map<Long, Long> taxDisplaySeq = new HashMap<>(0);
        
        
    	final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
                MainetConstants.Property.propPref.BILL, MainetConstants.Property.propPref.CAA, org);
        final LookUp chargeApplicableAtBillRec = CommonMasterUtility.getValueFromPrefixLookUp(
                MainetConstants.Property.propPref.BILL_RECEIPT, MainetConstants.Property.propPref.CAA, org);
        final LookUp chargeApplicableAtDemand = CommonMasterUtility.getValueFromPrefixLookUp(
                "DN", MainetConstants.Property.propPref.CAA, org);
        List<TbTaxMas> taxes = tbTaxMasService.findAllTaxesForBillGeneration(searchDto.getOrgId(),
        		searchDto.getDeptId(), chargeApplicableAt.getLookUpId(), null);
        List<TbTaxMas> taxesBillRece = tbTaxMasService.findAllTaxesForBillGeneration(searchDto.getOrgId(),
        		searchDto.getDeptId(), chargeApplicableAtBillRec.getLookUpId(), null);
        List<TbTaxMas> taxesBillDemand = tbTaxMasService.findAllTaxesForBillGeneration(searchDto.getOrgId(),
        		searchDto.getDeptId(), chargeApplicableAtDemand.getLookUpId(), null);
        List<TbTaxMas> notActiveTaxes = tbTaxMasService.findAllNotActiveTaxesForBillGeneration(searchDto.getOrgId(),
        		searchDto.getDeptId(), chargeApplicableAt.getLookUpId(), null);
        
        if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)) {
        	final LookUp chequeDishonor = CommonMasterUtility.getValueFromPrefixLookUp(
            		MainetConstants.Property.CHEQUE_DISHONR_CHARGES, MainetConstants.Property.propPref.CAA, org);
            List<TbTaxMas> chequeDishonorTaxes = tbTaxMasService.findAllTaxesForBillGeneration(searchDto.getOrgId(),
            		searchDto.getDeptId(), chequeDishonor.getLookUpId(), null);
            if (chequeDishonorTaxes != null && !chequeDishonorTaxes.isEmpty()) {
            	chequeDishonorTaxes.forEach(t -> {
                    tax.put(t.getTaxId(), t.getTaxDesc());
                    taxDisplaySeq.put(t.getTaxId(), t.getTaxDisplaySeq());
                });
            }
        }
        
        if (taxes != null && !taxes.isEmpty()) {
            taxes.forEach(t -> {
                tax.put(t.getTaxId(), t.getTaxDesc());
                taxDisplaySeq.put(t.getTaxId(), t.getTaxDisplaySeq());
            });
        }
        if(CollectionUtils.isNotEmpty(taxesBillDemand)) {
        	taxesBillDemand.forEach(t -> {
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
        if (StringUtils.isNotBlank(searchDto.getFlatNo())) {
            bills = propertyMainBillRepository.fetchBillSForViewPropertyByPropAndFlatNo(searchDto.getProertyNo(),
           		 searchDto.getFlatNo(), searchDto.getOrgId());
        } else {
            bills = propertyMainBillRepository.fetchBillSForViewProperty(searchDto.getProertyNo());
        }
        
        if (CollectionUtils.isNotEmpty(bills) && propMas != null) {
        	if(bills.size() > 3) {
        		 bills = bills.subList(bills.size() - 3, bills.size());
        	 }
             bills.forEach(bill -> {
                 TbBillMas billDto = new TbBillMas();
                 List<ChallanReportDTO> tbBillDet = new ArrayList<>(0);
                 ChallanReceiptPrintDTO data = new ChallanReceiptPrintDTO();
                 AtomicDouble totalPayableCurrent = new AtomicDouble(0);
                 AtomicDouble totalPayableArrear = new AtomicDouble(0);
                 AtomicDouble totalPayable = new AtomicDouble(0);
                 BeanUtils.copyProperties(bill, billDto);
                 data.setReceiptNo(billDto.getBmNo());//bill No.
                 data.setDate(Utility.dateToString(billDto.getBmBilldt()));//bill date
                 data.setFrom_finYear(Utility.dateToString(billDto.getBmFromdt()));
                 data.setTo_finYear(Utility.dateToString(billDto.getBmTodt()));
                 data.setAmount(billDto.getBmTotalOutstanding());
                 bill.getBillDetEntityList().forEach(billdet -> {
                     TbBillDet det = new TbBillDet();
                     ChallanReportDTO  dto= new ChallanReportDTO();
                     BeanUtils.copyProperties(billdet, det);
                     dto.setDetails(tax.get(det.getTaxId()));
                     dto.setDisplaySeq(taxDisplaySeq.get(det.getTaxId()));
                     dto.setAmountPayableCurrent(billdet.getBdCurTaxamt());
                     dto.setAmountPayableArrear(billdet.getBdPrvArramt());
                     dto.setAmountPayable(billdet.getBdCurTaxamt() + billdet.getBdPrvArramt());
                     totalPayableCurrent.addAndGet(billdet.getBdCurTaxamt());
                     totalPayableArrear.addAndGet(billdet.getBdPrvArramt());
                     totalPayable.addAndGet(billdet.getBdCurTaxamt() + billdet.getBdPrvArramt());
 					 tbBillDet.add(dto);
                 });
                 data.setPaymentList(tbBillDet);
                 data.setTotalPayableCurrent(totalPayableCurrent.doubleValue());
                 data.setTotalPayableArrear(totalPayableArrear.doubleValue());
                 data.setTotalAmountPayable(totalPayable.doubleValue());
                 
               //D#154659
                 Boolean displaySeq = true;
         		for (ChallanReportDTO dto : data.getPaymentList()) {
         			if (dto.getDisplaySeq() == null) {
         				displaySeq = false;
         				break;
         			}
         		}
         		if (displaySeq) {
         			data.getPaymentList().sort(Comparator.comparing(ChallanReportDTO::getDisplaySeq));
         		}
                 billMas.add(data);
             });
         }
        else {
        	//D#153751  D#154578
        	List<CommonChallanPayModeDTO> multiPayModeList = new ArrayList<>(0);
        	CommonChallanPayModeDTO dto = new CommonChallanPayModeDTO();
        	if(propMas == null) {
        		dto.setErrorMesg("Invalid");
        	}else {
        		dto.setErrorMesg("NOT_FOUND");
        	}
        	multiPayModeList.add(dto);
        	ChallanReceiptPrintDTO printDTO = new ChallanReceiptPrintDTO();
        	printDTO.setMultiPayModeList(multiPayModeList);
        	billMas.add(printDTO);
        }
        LOGGER.info("End -->  " + this.getClass().getSimpleName() + " getBillHistory method");
		return billMas;
	}
    public String getGurdianName(Long orgId,String PropNo)
	{
		List<AssesmentOwnerDtlEntity> ownerList = assesmentMastService.getOwnerDetailsByPropertyNo(orgId,PropNo);
		if (CollectionUtils.isNotEmpty(ownerList)) {
			return ownerList.get(0).getAssoGuardianName();
		}
		return null;

	}
}
