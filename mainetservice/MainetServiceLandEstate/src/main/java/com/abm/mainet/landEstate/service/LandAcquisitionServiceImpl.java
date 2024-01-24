package com.abm.mainet.landEstate.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebMethod;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.dto.VendorBillApprovalDTO;
import com.abm.mainet.common.integration.acccount.dto.VendorBillExpDetailDTO;
import com.abm.mainet.common.integration.asset.dto.AssetDetailsDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.landEstate.dao.ILandAcquisitionDao;
import com.abm.mainet.landEstate.domain.LandAcquisition;
import com.abm.mainet.landEstate.dto.LandAcquisitionDto;
import com.abm.mainet.landEstate.repository.LandAcquisitionRepository;

@Service
public class LandAcquisitionServiceImpl implements ILandAcquisitionService {

    private static Logger log = Logger.getLogger(LandAcquisitionServiceImpl.class);

    @Autowired
    LandAcquisitionRepository acqRepository;

    @Resource
    private IFileUploadService fileUploadService;

    @Autowired
    ILandAcquisitionDao iLandAcquisitionDao;

    @Autowired
    private CommonService commonService;

    @Autowired
    ServiceMasterService serviceMasterService;

    @Autowired
    ILandAcquisitionDao acquisitionDao;

    @Resource
    private IWorkflowExecutionService workflowExecutionService;

    @Transactional
    public LandAcquisitionDto saveLandAcquisition(LandAcquisitionDto acquisitionDto, ServiceMaster serviceMaster,
            List<DocumentDetailsVO> checkList, RequestDTO requestDTO) {

        Long appicationId = null;
        appicationId = ApplicationContextProvider.getApplicationContext().getBean(ApplicationService.class)
                .createApplication(requestDTO);
        LandAcquisition acquisition = new LandAcquisition();
        acquisitionDto.setApmApplicationId(appicationId);
        BeanUtils.copyProperties(acquisitionDto, acquisition);

        acqRepository.save(acquisition);

        /*
         * requestDTO.setIdfId(MainetConstants.LandEstate.LandAcquisition.SERVICE_SHOT_CODE + MainetConstants.WINDOWS_SLASH +
         * acquisition.getLnaqId());
         */

        if ((checkList != null) && !checkList.isEmpty()) {
            requestDTO.setApplicationId(appicationId);
            fileUploadService.doFileUpload(checkList, requestDTO);
            // fileUploadService.doMasterFileUpload(checkList, requestDTO);
        }
        // code for workflow process

        // check charge applicable at application level
        Long chargeApplicableAt = CommonMasterUtility
                .getValueFromPrefixLookUp(MainetConstants.NewWaterServiceConstants.APL,
                        MainetConstants.NewWaterServiceConstants.CAA, UserSession.getCurrent().getOrganisation())
                .getLookUpId();

        LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(
                chargeApplicableAt, UserSession.getCurrent().getOrganisation());
        if (serviceMaster.getSmFeesSchedule().equals(1l)
                && ((serviceMaster.getSmAppliChargeFlag().equals(MainetConstants.Common_Constant.YES))
                        && lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.ChargeApplicableAt.APPLICATION))
                || ((serviceMaster.getSmScrutinyChargeFlag().equals(MainetConstants.Common_Constant.YES))
                        && lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.ChargeApplicableAt.SCRUTINY))) {
            // code write for Taxes based on rate if required

            // till time not initiate workflow here because charges not know
            // and if service master change like charges at application time than write code here

        } else {
            // Application Time is Free service so call below method
            ApplicationMetadata applicationData = new ApplicationMetadata();
            applicationData.setApplicationId(acquisitionDto.getApmApplicationId());
            applicationData.setIsCheckListApplicable(true);
            applicationData.setOrgId(requestDTO.getOrgId());
            if (serviceMaster.getSmFeesSchedule().longValue() == 0) {
                applicationData.setIsLoiApplicable(false);
            } else {
                applicationData.setIsLoiApplicable(true);
            }
            ApplicantDetailDTO applicantDto = new ApplicantDetailDTO();
            applicantDto.setServiceId(requestDTO.getServiceId());
            applicantDto.setOrgId(requestDTO.getOrgId());
            applicantDto.setDepartmentId(requestDTO.getDeptId());
            applicantDto.setUserId(requestDTO.getUserId());
            // pass ApplicationMetadata OBJ and applicant dto
            commonService.initiateWorkflowfreeService(applicationData, applicantDto);
        }
        return acquisitionDto;

    }

    @Override
    public LandAcquisitionDto getAcquisitionChargesFromBrmsRule(LandAcquisitionDto acqDto) {
        Organisation organisation = new Organisation();
        organisation.setOrgid(acqDto.getOrgId());
        Date todayDate = new Date();
        final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
                MainetConstants.ChargeApplicableAt.APPLICATION,
                PrefixConstants.LookUpPrefix.CAA, organisation);

        ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                .getServiceMasterByShortCode(
                        MainetConstants.LandEstate.LandAcquisition.SERVICE_SHOT_CODE,
                        acqDto.getOrgId());

        final List<TbTaxMasEntity> taxesMaster = ApplicationContextProvider.getApplicationContext().getBean(TbTaxMasService.class)
                .fetchAllApplicableServiceCharge(sm.getSmServiceId(),
                        organisation.getOrgid(),
                        chargeApplicableAt.getLookUpId());

        /*
         * List<MLNewTradeLicense> masterList = new ArrayList<>(); if (masDto.getTrdId() != null) { final LookUp
         * chargeApplicableAtScrutiny = CommonMasterUtility.getValueFromPrefixLookUp(
         * PrefixConstants.NewWaterServiceConstants.SCRUTINY, PrefixConstants.NewWaterServiceConstants.CAA, organisation);
         * ServiceMaster smm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
         * .getServiceMasterByShortCode( MainetConstants.TradeLicense.SERVICE_SHORT_CODE, masDto.getOrgid()); final
         * List<TbTaxMasEntity> taxesMasterr = ApplicationContextProvider.getApplicationContext() .getBean(TbTaxMasService.class)
         * .fetchAllApplicableServiceCharge(smm.getSmServiceId(), organisation.getOrgid(),
         * chargeApplicableAtScrutiny.getLookUpId()); List<TradeLicenseItemDetailDTO> tradeLicenseItemDetailDTO =
         * masDto.getTradeLicenseItemDetailDTO(); List<LookUp> lookupListLevel1 = new ArrayList<LookUp>(); List<LookUp>
         * lookupListLevel2 = new ArrayList<LookUp>(); List<LookUp> lookupListLevel3 = new ArrayList<LookUp>(); List<LookUp>
         * lookupListLevel4 = new ArrayList<LookUp>(); List<LookUp> lookupListLevel5 = new ArrayList<LookUp>(); try {
         * lookupListLevel1 = CommonMasterUtility.getNextLevelData(MainetConstants.TradeLicense.ITC, 1, masDto.getOrgid());
         * lookupListLevel2 = CommonMasterUtility.getNextLevelData(MainetConstants.TradeLicense.ITC, 2, masDto.getOrgid());
         * lookupListLevel3 = CommonMasterUtility.getNextLevelData(MainetConstants.TradeLicense.ITC, 3, masDto.getOrgid());
         * lookupListLevel4 = CommonMasterUtility.getNextLevelData(MainetConstants.TradeLicense.ITC, 4, masDto.getOrgid());
         * lookupListLevel5 = CommonMasterUtility.getNextLevelData(MainetConstants.TradeLicense.ITC, 5, masDto.getOrgid()); }
         * catch (Exception e) { LOGGER.info("prefix level not found"); } //
         * tradeLicenseItemDetailDTO.parallelStream().forEach(dto -> { for (TradeLicenseItemDetailDTO dto :
         * tradeLicenseItemDetailDTO) { MLNewTradeLicense license = new MLNewTradeLicense(); license.setOrgId(dto.getOrgid());
         * license.setServiceCode(MainetConstants.TradeLicense.SERVICE_CODE);
         * license.setTaxType(MainetConstants.TradeLicense.TAX_TYPE); license.setTaxCode(taxesMasterr.get(0).getTaxCode()); // for
         * getting scrutiny level tax code(category and // subcategory)
         * license.setTaxCategory(CommonMasterUtility.getHierarchicalLookUp(taxesMasterr.get(0).getTaxCategory1(),
         * organisation).getDescLangFirst());
         * license.setTaxSubCategory(CommonMasterUtility.getHierarchicalLookUp(taxesMasterr.get(0).getTaxCategory2(),
         * organisation).getDescLangFirst()); license.setRateStartDate(todayDate.getTime());
         * license.setDeptCode(MainetConstants.TradeLicense.MARKET_LICENSE);
         * license.setApplicableAt(chargeApplicableAtScrutiny.getDescLangFirst()); if (dto.getTriCod1() != null &&
         * dto.getTriCod1() != 0) { List<LookUp> level1 = lookupListLevel1.parallelStream() .filter(clList -> clList != null &&
         * clList.getLookUpId() == dto.getTriCod1().longValue()) .collect(Collectors.toList()); if (level1 != null &&
         * !level1.isEmpty()) { license.setItemCategory1(level1.get(0).getDescLangFirst());
         * dto.setItemCategory1(level1.get(0).getDescLangFirst()); } } else {
         * dto.setItemCategory1(MainetConstants.TradeLicense.NOT_APPLICABLE); } if (dto.getTriCod2() != null && dto.getTriCod2()
         * != 0) { List<LookUp> level2 = lookupListLevel2.parallelStream() .filter(clList -> clList != null &&
         * clList.getLookUpId() == dto.getTriCod2().longValue()) .collect(Collectors.toList()); if (level2 != null &&
         * !level2.isEmpty()) { license.setItemCategory2(level2.get(0).getDescLangFirst());
         * dto.setItemCategory2(level2.get(0).getDescLangFirst()); } } else {
         * dto.setItemCategory2(MainetConstants.TradeLicense.NOT_APPLICABLE); } if (dto.getTriCod3() != null && dto.getTriCod3()
         * != 0) { List<LookUp> level3 = lookupListLevel3.parallelStream() .filter(clList -> clList != null &&
         * clList.getLookUpId() == dto.getTriCod3().longValue()) .collect(Collectors.toList()); if (level3 != null &&
         * !level3.isEmpty()) { license.setItemCategory3(level3.get(0).getDescLangFirst());
         * dto.setItemCategory2(level3.get(0).getDescLangFirst()); } } else {
         * dto.setItemCategory3(MainetConstants.TradeLicense.NOT_APPLICABLE); } if (dto.getTriCod4() != null && dto.getTriCod4()
         * != 0) { List<LookUp> level4 = lookupListLevel4.parallelStream() .filter(clList -> clList != null &&
         * clList.getLookUpId() == dto.getTriCod4().longValue()) .collect(Collectors.toList()); if (level4 != null &&
         * !level4.isEmpty()) { license.setItemCategory4(level4.get(0).getDescLangFirst());
         * dto.setItemCategory4(level4.get(0).getDescLangFirst()); } } else {
         * dto.setItemCategory4(MainetConstants.TradeLicense.NOT_APPLICABLE); } if (dto.getTriCod5() != null && dto.getTriCod5()
         * != 0) { List<LookUp> level5 = lookupListLevel5.parallelStream() .filter(clList -> clList != null &&
         * clList.getLookUpId() == dto.getTriCod5().longValue()) .collect(Collectors.toList()); if (level5 != null &&
         * !level5.isEmpty()) { license.setItemCategory5(level5.get(0).getDescLangFirst());
         * dto.setItemCategory5(level5.get(0).getDescLangFirst()); } } else {
         * dto.setItemCategory5(MainetConstants.TradeLicense.NOT_APPLICABLE); } masterList.add(license); // }); } } else { for
         * (TbTaxMasEntity taxes : taxesMaster) { MLNewTradeLicense license = new MLNewTradeLicense();
         * license.setOrgId(masDto.getOrgid()); license.setServiceCode(MainetConstants.TradeLicense.SERVICE_CODE);
         * license.setTaxType(MainetConstants.TradeLicense.TAX_TYPE); license.setTaxCode(taxes.getTaxCode()); //
         * license.setTaxCategory(taxes.getTaxCategory1().toString()); //
         * license.setTaxSubCategory(taxes.getTaxCategory2().toString()); settingTaxCategories(license, taxes, organisation);
         * license.setRateStartDate(todayDate.getTime()); license.setApplicableAt(chargeApplicableAt.getDescLangFirst());
         * license.setDeptCode(MainetConstants.TradeLicense.MARKET_LICENSE); masterList.add(license); } }
         * LOGGER.info("brms ML getTradeLicenceChargesFromBrmsRule execution start.."); WSResponseDTO responseDTO = new
         * WSResponseDTO(); WSRequestDTO wsRequestDTO = new WSRequestDTO(); List<MLNewTradeLicense> master = new ArrayList<>();
         * wsRequestDTO.setDataModel(masterList); try { LOGGER.info("brms ML request DTO  is :" + wsRequestDTO.toString());
         * responseDTO = RestClient.callBRMS(wsRequestDTO, ServiceEndpoints.ML_NEW_TRADE_LICENSE); if
         * (responseDTO.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) { master =
         * setTradeLicenceChargesDTO(responseDTO); } else { throw new FrameworkException(responseDTO.getErrorMessage()); } } catch
         * (Exception ex) { throw new FrameworkException("unable to process request for Trade Licence Fee", ex); }
         * LOGGER.info("brms ML getTradeLicenceChargesFromBrmsRule execution End."); setChargeToItemsDtoList(master, masDto); for
         * (TbTaxMasEntity tbTaxMas : taxesMaster) { masDto.getFeeIds().put(tbTaxMas.getTaxId(),
         * masDto.getTotalApplicationFee().doubleValue()); } return masDto;
         */
        return null;
    }

    @Override
    public LandAcquisitionDto getLandAcqDataByApplicationId(Long applicationId, Long orgId) {

        LandAcquisitionDto landAcquisitionDto = new LandAcquisitionDto();
        // get record using JPA repository
        LandAcquisition laqEntity = acqRepository.getLAQDataByRefNoAndOrgId(applicationId, orgId);
        BeanUtils.copyProperties(laqEntity, landAcquisitionDto);
        return landAcquisitionDto;
    }

    // for duplicate
    @Override
    public int checkDuplicateLand(String lnServno, String lnArea, Long locId, String payTo) {
        // get record using JPA repository
        int laqData = acqRepository.getLandAcquisitiondata(lnServno, lnArea, locId, payTo);
        return laqData;
    }

    @Override
    public List<String> fetchProposalNoList() {
        return acqRepository.fetchProposalNoList();
    }

    @Override
    public List<LandAcquisitionDto> fetchSearchData(String proposalNo, String payTo, String acqStatus, Long locId,
            Long orgid) {
        List<LandAcquisitionDto> landAcquisitionDtos = new ArrayList<LandAcquisitionDto>();
        List<LandAcquisition> acquisitionList = iLandAcquisitionDao.searchLandAcquisitionData(proposalNo, payTo, acqStatus,
                locId, orgid);
        acquisitionList.forEach(acquisition -> {
            LandAcquisitionDto dto = new LandAcquisitionDto();
            BeanUtils.copyProperties(acquisition, dto);
            // Acquisition Status
            if (acquisition.getAcqStatus().equalsIgnoreCase(MainetConstants.LandEstate.LandAcquisition.ACQUIRED_STATUS)) {
                dto.setAcqStatus(MainetConstants.LandEstate.LandAcquisition.ACQUIRED);
            } else {
                dto.setAcqStatus(MainetConstants.LandEstate.LandAcquisition.TRANSIT);
            }
            dto.setAcqDateDesc(Utility.dateToString(acquisition.getAcqDate()));
            landAcquisitionDtos.add(dto);
        });
        return landAcquisitionDtos;
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public Map<Long, Double> getLoiCharges(final Long applicationId, final Long serviceId, final Long orgId)
            throws CloneNotSupportedException {
        Map<Long, Double> chargeMap = new HashMap<>();
        LandAcquisitionDto dto = getLandAcqDataByApplicationId(applicationId, orgId);
        Organisation organisation = new Organisation();
        organisation.setOrgid(orgId);
        double amount = 0;
        if (dto.getAcqCost() != null) {
            amount = dto.getAcqCost().doubleValue();
        }
        // get Tax id

        final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
                MainetConstants.ChargeApplicableAt.SCRUTINY,
                PrefixConstants.LookUp.CHARGE_MASTER_CAA, organisation);

        ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                .getServiceMasterByShortCode(
                        MainetConstants.LandEstate.LandAcquisition.SERVICE_SHOT_CODE,
                        orgId);

        final List<TbTaxMasEntity> taxesMaster = ApplicationContextProvider.getApplicationContext().getBean(TbTaxMasService.class)
                .fetchAllApplicableServiceCharge(sm.getSmServiceId(),
                        organisation.getOrgid(),
                        chargeApplicableAt.getLookUpId());

        for (TbTaxMasEntity tbTaxMas : taxesMaster) {
            chargeMap.put(tbTaxMas.getTaxId(), amount);
        }
        return chargeMap;

        /*
         * chargeMap.put(taxesMaster.get(0).getTaxId(), amount); return chargeMap;
         */
    }

    @Transactional
    public void updateLandValuationData(Long apmApplicationId, BigDecimal acqCost, Long vendorId, String billNo, Long assetId,
            String transferStatus, Date acqDate, Long orgId) {
        // update cost and vendorId against applicationId
        acquisitionDao.updateLandValuationData(apmApplicationId, acqCost, vendorId, billNo, assetId, transferStatus, acqDate,
                orgId);

    }

    @Override
    @Transactional
    public Long pushAssetDetails(AssetDetailsDTO astDet) {
        Long astId = null;
        ResponseEntity<?> responseEntity = null;
        try {
            responseEntity = RestClient.callRestTemplateClient(astDet, ServiceEndpoints.WMS_ASSET_DETAILS);
            HttpStatus statusCode = responseEntity.getStatusCode();
            if (statusCode == HttpStatus.OK) {
                astId = Long.valueOf(responseEntity.getBody().toString());
            }
        } catch (Exception ex) {
            log.error("Exception occured while pushAssetDetails() : " + ex);
            return astId;
        }
        return astId;
    }

    @Transactional
    public void updateLandProposalAcqStatusById(Long apmApplicationId, Employee employee,
            Long serviceId, Long orgId, Long taskId) {
        // HERE A -> Acquired

        acqRepository.updateLandProposalAcqStatus(MainetConstants.LandEstate.LandAcquisition.ACQUIRED_STATUS, employee.getEmpId(),
                employee.getEmppiservername(), apmApplicationId);

        String processName = serviceMasterService.getProcessName(serviceId, orgId);
        if (processName != null) {
            WorkflowProcessParameter workflowdto = new WorkflowProcessParameter();
            WorkflowTaskAction workflowAction = new WorkflowTaskAction();
            workflowAction.setTaskId(taskId);
            workflowAction.setApplicationId(apmApplicationId);
            workflowAction.setDateOfAction(new Date());
            workflowAction.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
            workflowAction.setOrgId(orgId);
            workflowAction.setEmpId(employee.getEmpId());
            workflowAction.setModifiedBy(employee.getEmpId());
            workflowAction.setEmpType(employee.getEmplType());
            workflowAction.setEmpName(employee.getEmpname());
            workflowAction.setCreatedBy(employee.getEmpId());
            workflowAction.setCreatedDate(new Date());
            workflowdto.setProcessName(processName);
            workflowdto.setWorkflowTaskAction(workflowAction);
            try {
                workflowExecutionService.updateWorkflow(workflowdto);
            } catch (final Exception e) {
                throw new FrameworkException("Exception in work order generation for jbpm workflow : " + e.getMessage(),
                        e);
            }
        }

    }

    @Override
    public LandAcquisitionDto getLandAcqProposalByAppId(Long apmApplicationId) {
        LandAcquisitionDto landAcquisitionDto = new LandAcquisitionDto();
        BeanUtils.copyProperties(acqRepository.getLAQProposal(apmApplicationId), landAcquisitionDto);
        return landAcquisitionDto;
    }

    // TODO Auto-generated method stub
    @Transactional
    public void updateLoiPayableData(LandAcquisitionDto acquisitionDto, String workFlowDecision, Long taskId, Employee employee,
            Organisation org, int langId, Long orgId) {
        ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                .getServiceMasterByShortCode(MainetConstants.LandEstate.LandAcquisition.SERVICE_SHOT_CODE, orgId);

        LookUp lookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_LIVE_VALUE,
                MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE, langId, org);
        if (lookup != null) {
            // account integration code
            String accountCode = lookup.getLookUpCode();
            if (lookup.getDefaultVal().equals(MainetConstants.FlagY) && accountCode.equals(MainetConstants.FlagL)) {
                List<TbLoiMas> loiData = ApplicationContextProvider.getApplicationContext().getBean(TbLoiMasService.class)
                        .getloiByApplicationId(acquisitionDto.getApmApplicationId(), sm.getSmServiceId(), orgId);
                if (loiData.isEmpty()) {
                    log.info("First LOI No generate - loiData is empty FOR " + acquisitionDto.getApmApplicationId());
                }
                accountIntegrate(loiData.get(0).getLoiNo(), acquisitionDto, orgId, employee.getEmpId(), sm.getSmServiceId(),
                        employee, org);
            } /*
               * else { return jsonResult(JsonViewObject.failureResult("account module not live")); }
               */
        }
        // update the status in TB_EST_AQUISN
        /*
         * updateLandProposalAcqStatusById(employee.getEmpId(), employee.getEmppiservername(),
         * acquisitionDto.getApmApplicationId());
         */

        WorkflowTaskAction taskAction = new WorkflowTaskAction();
        taskAction.setOrgId(orgId);
        taskAction.setEmpId(employee.getEmpId());
        taskAction.setEmpType(employee.getEmplType());
        taskAction.setDateOfAction(new Date());
        taskAction.setCreatedDate(new Date());
        taskAction.setCreatedBy(employee.getEmpId());
        taskAction.setEmpName(employee.getEmplname());
        taskAction.setEmpEmail(employee.getEmpemail());
        taskAction.setApplicationId(acquisitionDto.getApmApplicationId());
        taskAction.setDecision(workFlowDecision);
        taskAction.setTaskId(taskId);
        taskAction.setPaymentMode(MainetConstants.PAYMENT.ONLINE);
        WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
        workflowProcessParameter.setProcessName(MainetConstants.LandEstate.PROCESS_SCRUITNY);
        workflowProcessParameter.setWorkflowTaskAction(taskAction);
        try {
            ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class)
                    .updateWorkflow(workflowProcessParameter);
        } catch (Exception exception) {
            throw new FrameworkException("Exception  Occured when Update Workflow methods", exception);
        }

    }

    public void accountIntegrate(String loiNo, LandAcquisitionDto acquisitionDto, Long orgId, Long empId, Long serviceId,
            Employee employee, Organisation org) {
        VendorBillApprovalDTO billDTO = new VendorBillApprovalDTO();
        List<VendorBillExpDetailDTO> billExpDetListDto = new ArrayList<>();
        VendorBillExpDetailDTO billExpDetailDTO = new VendorBillExpDetailDTO();

        billDTO.setBillEntryDate(Utility.dateToString(new Date()));
        billDTO.setBillTypeId(
                CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(MainetConstants.LandEstate.BILL_TYPE_MISCELLANEOUS,
                        MainetConstants.ABT, orgId));
        // get LOI no for set in NARRATION field
        billDTO.setOrgId(orgId);
        billDTO.setNarration(loiNo + "-" + acquisitionDto.getAcqPurpose());
        billDTO.setCreatedBy(empId);
        billDTO.setCreatedDate(Utility.dateToString(new Date()));
        billDTO.setLgIpMacAddress(employee.getEmppiservername());
        billDTO.setVendorId(acquisitionDto.getVendorId());
        billDTO.setInvoiceAmount(acquisitionDto.getAcqCost());
        billDTO.setFieldId(acquisitionDto.getLocId());
        billDTO.setDepartmentId(ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
                .getDepartment(MainetConstants.LandEstate.LandEstateCode,
                        MainetConstants.CommonConstants.ACTIVE)
                .getDpDeptid());

        final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
                MainetConstants.ChargeApplicableAt.SCRUTINY,
                PrefixConstants.LookUp.CHARGE_MASTER_CAA, org);

        // ask to sir below code is correct or not
        final List<TbTaxMasEntity> taxesMasters = ApplicationContextProvider.getApplicationContext()
                .getBean(TbTaxMasService.class)
                .fetchAllApplicableServiceCharge(serviceId, orgId, chargeApplicableAt.getLookUpId());
        Long sacHeadId = null;
        for (TbTaxMasEntity tax : taxesMasters) {
            Long taxId = tax.getTaxId();
            sacHeadId = ApplicationContextProvider.getApplicationContext().getBean(TbTaxMasService.class)
                    .fetchSacHeadIdForReceiptDet(orgId, taxId, MainetConstants.FlagA); // here A means Active
            if (sacHeadId != null) {
                break;
            }
        }
        billExpDetailDTO.setBudgetCodeId(sacHeadId);
        billExpDetailDTO.setAmount(acquisitionDto.getAcqCost());
        billExpDetailDTO.setSanctionedAmount(acquisitionDto.getAcqCost());
        billExpDetListDto.add(billExpDetailDTO);
        billDTO.setExpDetListDto(billExpDetListDto);
        try {
            ResponseEntity<?> responseEntity = RestClient.callRestTemplateClient(billDTO, ServiceEndpoints.SALARY_POSTING);
            if (responseEntity != null && responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                // update billNo in TB_EST_AQUISN
                String billNo = responseEntity.getBody().toString();
                // D#132554
                String finalBillNo = billNo.substring(billNo.indexOf(":") + 1);
                log.info("LN_BILLNO ISSUE " + finalBillNo);
                updateLandValuationData(acquisitionDto.getApmApplicationId(), null,
                        null, finalBillNo, null, null, null, orgId);
            }
        } catch (Exception exception) {
            throw new FrameworkException("error occured while bill posting to account module ", exception);
        }
    }

}
