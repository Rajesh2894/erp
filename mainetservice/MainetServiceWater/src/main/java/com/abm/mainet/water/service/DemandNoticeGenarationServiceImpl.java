package com.abm.mainet.water.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.dao.DemandNoticeGenarationRepository;
import com.abm.mainet.water.datamodel.WaterRateMaster;
import com.abm.mainet.water.domain.DemandNotice;
import com.abm.mainet.water.dto.DemandNoticeRequestDTO;
import com.abm.mainet.water.dto.DemandNoticeResponseDTO;

@Service
public class DemandNoticeGenarationServiceImpl implements DemandNoticeGenarationService {

    @Resource
    private DemandNoticeGenarationRepository demandNoticeGenarationRepository;

    @Resource
    private SeqGenFunctionUtility commonFunctionUtility;

    @Resource
    private TbTaxMasService taxMasterService;

    @Autowired
    private TbDepartmentService departmentService;

    @Autowired
    private WaterChecklistAndChargeService chargeService;

    @Autowired
    private BRMSCommonService brmsCommonService;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DemandNoticeGenarationServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
    public List<DemandNoticeResponseDTO> searchAllDefaulter(final DemandNoticeRequestDTO request) {
        Organisation lookupOrg = new Organisation();
        lookupOrg.setOrgid((request.getOrgid() == 0 ? UserSession.getCurrent().getOrganisation().getOrgid() : request.getOrgid()));
	
        request.setFinalNoticeType(getDemandType(MainetConstants.DemandNotice.FINAL_DEMAND_NOTICE, lookupOrg).getLookUpId());
        final long demandId = getDemandType(MainetConstants.DemandNotice.DEMAND_NOTICE, lookupOrg).getLookUpId();
        return demandNoticeGenarationRepository.searchAllDefaulter(request, demandId);

    }

    @Override
    public List<DemandNoticeResponseDTO> searchAllDemand(final DemandNoticeRequestDTO request) {

        return demandNoticeGenarationRepository.searchAllDemand(request);
    }

    @Override
    @Transactional
    public boolean generateDemandNotice(final List<DemandNoticeResponseDTO> demands, final long userId, final long orgId,
            final long langId) {

        final Map<Long, DemandNotice> demandList = demandNoticeGenarationRepository.findAllPreviousDemand(orgId);
        final long dpDeptId = getWaterDeptId();
        for (final DemandNoticeResponseDTO dto : demands) {

            if (dto.isSelected()) {
                if (demandList.containsKey(dto.getConnectionId())) {

                    updateDemandtoFinalDemand(userId, demandList, dto, dpDeptId);

                } else {

                    createNewDemand(userId, orgId, langId, dto, demandList, dpDeptId);

                }

            }
        }
        if (demandList.isEmpty()) {
            return false;
        }

        demandNoticeGenarationRepository.generateDemandNotice(demandList);
        return true;
    }
 
    private Object[] getDemandTaxCode(final long orgId, final String demandShortCode, final long dpDeptId) {
        final LookUp demandTaxLookup = getDemandLookup(demandShortCode);
        final long taxCategory = demandTaxLookup.getLookUpParentId();
        final long taxSubCategory = demandTaxLookup.getLookUpId();
        final Object[] result = taxMasterService.getTaxCodeByTaxCatagory(orgId, dpDeptId , taxCategory, taxSubCategory);
        LOGGER.info("getDemandTaxCode() 1 result: "+ result);
        return (result!=null && result.length>0? (Object[]) result[0] : result);
    }
  
    private Long getWaterDeptId() {
        final Long dpDeptId = departmentService.searchDeptData(null, MainetConstants.WATER_DEPARTMENT_CODE).get(0)
                .getDpDeptid();
        return dpDeptId;
    }
    
    private LookUp getDemandLookup(final String shortCode) {
        final List<LookUp> demandLookup = CommonMasterUtility.getSecondLevelData(PrefixConstants.WATERMODULEPREFIX.TAC,
                MainetConstants.INDEX.TWO);
        for (final LookUp lookUp : demandLookup) {

            if (lookUp.getLookUpCode().equalsIgnoreCase(shortCode)) {
                return lookUp;
            }

        }
        throw new FrameworkException("Demand Tax subcatagory of code not define in TAC prefix: " + shortCode);
    }

    private void updateDemandtoFinalDemand(final long userId, final Map<Long, DemandNotice> demandList,
            final DemandNoticeResponseDTO dto, final long dpDeptId) {
	
	
        
        final DemandNotice demand = demandList.get(dto.getConnectionId());
        
        Organisation lookupOrg = new Organisation();
	lookupOrg.setOrgid(demand.getOrgid());
	
        final LookUp finalFemandNotice = getDemandType(MainetConstants.DemandNotice.FINAL_DEMAND_NOTICE, lookupOrg);
        
        // check if final notice already generated...
        if (isFinalDemandGenerated(finalFemandNotice.getLookUpId(), demand)) {
            final Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, Integer.parseInt(finalFemandNotice.getOtherField()));
            demand.setNbNotduedt(cal.getTime());
            demand.setCpdNottype(finalFemandNotice.getLookUpId());
            demand.setUpdatedBy(userId);
            demand.setUpdatedDate(new Date());
            demand.setLgIpMacUpd(Utility.getMacAddress());
            demand.setNbNoticeno(generateNoticeNumber());
            final Object[] demandTaxCode = getDemandTaxCode(demand.getOrgid(),
                    MainetConstants.DemandNotice.FINAL_DEMAND_NOTICE, dpDeptId);
            LOGGER.info("updateDemandtoFinalDemand() 2 demandTaxCode: "+ demandTaxCode);
            demand.setTaxCode(demandTaxCode!=null && demandTaxCode.length>0? demandTaxCode[0].toString(): null);
            demand.setTaxAmount(getTaxAmount(demand.getOrgid(), dpDeptId, demandTaxCode, dto.getBillAmount(),
                    MainetConstants.DemandNotice.FINAL_DEMAND_NOTICE));
        }
    }

    private boolean isFinalDemandGenerated(final long finalFemandNotice, final DemandNotice demand) {
        final Long demandType = demand.getCpdNottype();
        return !demandType.equals(finalFemandNotice);
    }

    private void createNewDemand(final long userId, final long orgId, final long langId,
            final DemandNoticeResponseDTO dto, final Map<Long, DemandNotice> demandList, final long dpDeptId) {
        Organisation lookupOrg = new Organisation();
	lookupOrg.setOrgid(orgId);
	
        DemandNotice notice;
        final LookUp demandNotice = getDemandType(MainetConstants.DemandNotice.DEMAND_NOTICE, lookupOrg);
        notice = new DemandNotice();
        final Calendar cal = Calendar.getInstance();
        LOGGER.info("createNewDemand() 3");
        cal.add(Calendar.DATE, (!demandNotice.getOtherField().isEmpty()? Integer.parseInt(demandNotice.getOtherField()): 0));
        notice.setNbNotduedt(cal.getTime());
        notice.setCsIdn(dto.getConnectionId());
        notice.setBmIdno(dto.getBillId());
        notice.setCpdNottype(demandNotice.getLookUpId());
        notice.setNbNoticedt(new Date());
        notice.setUserId(userId);
        notice.setOrgid(orgId);
        // notice.setLangId(langId);
        notice.setLgIpMac(Utility.getMacAddress());
        notice.setLmoddate(new Date());
        notice.setIsDeleted(MainetConstants.IsDeleted.NOT_DELETE);
        notice.setNbNoticedt(new Date());
        /*
         * Utility.getCurrentFinancialYear() ; UserSession.getCurrent().getFinancialPeriodShortForm();
         */
        notice.setNbNoticeno(generateNoticeNumber());
        final Object[] demandTaxCode = getDemandTaxCode(orgId, MainetConstants.DemandNotice.DEMAND_NOTICE, dpDeptId);
        LOGGER.info("createNewDemand() 4");
        notice.setTaxCode(demandTaxCode!=null && demandTaxCode.length>0 ? demandTaxCode[0].toString() : null);
        notice.setTaxAmount(
                getTaxAmount(orgId, dpDeptId, demandTaxCode, dto.getBillAmount(), MainetConstants.DemandNotice.DEMAND_NOTICE));
        demandList.put(dto.getConnectionId(), notice);
    }

    private double getTaxAmount(final long orgId, final long dpDeptId, final Object[] demandTaxCode, final Double dueAmount,
            final String noticeType) {
        WSResponseDTO response = null;
        WaterRateMaster rateMaster = null;
        double baseRate = 0;

        Organisation lookupOrg = new Organisation();
	lookupOrg.setOrgid(orgId);
	
        final WSRequestDTO initReqdto = new WSRequestDTO();
        initReqdto.setModelName(MainetConstants.NewWaterServiceConstants.CHECKLIST_WATERRATEMASTER_MODEL);
        response = brmsCommonService.initializeModel(initReqdto);
        // response = chargeService.initializeModel();
        final WSRequestDTO dto = new WSRequestDTO();
        dto.setModelName(MainetConstants.NewWaterServiceConstants.CHECKLIST_WATERRATEMASTER_MODEL);
        if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
            final List<Object> waterRateMasterList = RestClient.castResponse(response, WaterRateMaster.class, 1);
            rateMaster = (WaterRateMaster) waterRateMasterList.get(0);
            rateMaster.setFinancialYear(Utility.getCurrentFinancialYear());
            rateMaster.setDeptCode(MainetConstants.WATER_DEPARTMENT_CODE);
            rateMaster.setOrgId(orgId);
            final LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(Long.valueOf(demandTaxCode[1].toString()), lookupOrg);
            rateMaster.setTaxType(lookUp.getDescLangFirst());
            rateMaster.setTaxCode(demandTaxCode[0].toString());
            final LookUp demandTaxLookup = getDemandLookup(noticeType);
            rateMaster.setTaxCategory(
                    CommonMasterUtility.getHierarchicalLookUp(demandTaxLookup.getLookUpParentId(), orgId).getDescLangFirst());
            rateMaster.setTaxSubCategory(
                    CommonMasterUtility.getHierarchicalLookUp(demandTaxLookup.getLookUpId(), orgId).getDescLangFirst());
            rateMaster.setRateStartDate(new Date().getTime());
            final WSRequestDTO requestRateMaster = new WSRequestDTO();
            requestRateMaster.setDataModel(rateMaster);
            response = RestClient.callBRMS(requestRateMaster, ServiceEndpoints.BRMSMappingURL.WATER_RATE_URL);
            if ((response != null) && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
                Object dataModel = null;
                LinkedHashMap<Long, Object> responseMap = null;
                final List<Object> dataModelList = new ArrayList<>();
                responseMap = (LinkedHashMap<Long, Object>) response.getResponseObj();
                final String jsonString = new JSONObject(responseMap).toString();
                try {
                    dataModel = new ObjectMapper().readValue(jsonString, WaterRateMaster.class);
                } catch (final IOException e) {
                    throw new FrameworkException("BRMS response can not be cast", e);
                }
                dataModelList.add(dataModel);
                rateMaster = (WaterRateMaster) dataModelList.get(0);

                switch (lookUp.getLookUpCode()) {
                case PrefixConstants.TAX_TYPE.FLAT:// flat
                    baseRate = rateMaster.getFlatRate();
                    break;
                case PrefixConstants.TAX_TYPE.SLAB:// slab
                    if ((rateMaster.getConsumption() >= rateMaster.getSlab1())
                            && (rateMaster.getConsumption() < rateMaster.getSlab2())) {
                        baseRate = rateMaster.getSlabRate2();
                    }
                    if ((rateMaster.getConsumption() >= rateMaster.getSlab2())
                            && (rateMaster.getConsumption() < rateMaster.getSlab3())) {
                        baseRate = rateMaster.getSlabRate3();
                    }
                    if ((rateMaster.getConsumption() >= rateMaster.getSlab3())
                            && (rateMaster.getConsumption() < rateMaster.getSlab4())) {
                        baseRate = rateMaster.getSlabRate4();
                    }
                    break;
                case PrefixConstants.TAX_TYPE.PERCENTAGE:
                    baseRate += dueAmount * (rateMaster.getPercentageRate() / 100);
                    break;
                }

            }
        }
        return baseRate;
    }

    private Long generateNoticeNumber() {
        return commonFunctionUtility.generateSequenceNo(MainetConstants.DemandNotice.MODULE,
                MainetConstants.DemandNotice.TABLE, MainetConstants.DemandNotice.COLUMN,
                UserSession.getCurrent().getOrganisation().getOrgid(),
                MainetConstants.DemandNotice.RESET, null);
    }

    @Override
    public LookUp getDemandType(final String demandNoticeType, final Organisation organisation) {
        final LookUp demandLookup = CommonMasterUtility.getValueFromPrefixLookUp(demandNoticeType,
                MainetConstants.DemandNotice.DEMAND_TYPE, organisation);
        return demandLookup;
    }

    @Override
    @Transactional
    public void updateNoticeDueDate(final Date dueDate, final Date distDate, final long orgid, final long dnId) {
        demandNoticeGenarationRepository.updateNoticeDueDate(dueDate, distDate, orgid, dnId);

    }
    
	@Override
    @Transactional(readOnly = true)
    public List<DemandNoticeResponseDTO> searchAllDefaulterForAscl(final DemandNoticeRequestDTO request, final Long csIdn) {
        Organisation lookupOrg = new Organisation();
        lookupOrg.setOrgid((request.getOrgid() == 0 ? UserSession.getCurrent().getOrganisation().getOrgid() : request.getOrgid()));
	
        request.setFinalNoticeType(getDemandType(MainetConstants.DemandNotice.FINAL_DEMAND_NOTICE, lookupOrg).getLookUpId());
        final long demandId = getDemandType(MainetConstants.DemandNotice.DEMAND_NOTICE, lookupOrg).getLookUpId();
        return demandNoticeGenarationRepository.searchAllDefaulterForAscl(request, demandId, csIdn);
    }


}
