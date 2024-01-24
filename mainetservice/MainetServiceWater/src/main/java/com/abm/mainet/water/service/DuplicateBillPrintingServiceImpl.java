/**
 * 
 */
package com.abm.mainet.water.service;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.repository.TbCsmrInfoRepository;
import com.abm.mainet.water.ui.model.DuplicateBillReceiptModel;

/**
 * @author akshata.bhat
 *
 */
@Service
public class DuplicateBillPrintingServiceImpl implements DuplicateBillPrintingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DuplicateBillPrintingServiceImpl.class);

	@Autowired
	private IChallanService challanService;
	
	@Autowired
	private ServiceMasterService serviceMasterService;

	@Autowired
	private TbCsmrInfoRepository csmrInfoRepository;
	
	@Override
	public void saveData(DuplicateBillReceiptModel duplicatePaymentReceiptModel, CommonChallanDTO offlineDTO) {
		final CommonChallanDTO offline = duplicatePaymentReceiptModel.getOfflineDTO();
		ServiceMaster serviceMaster = serviceMasterService.getServiceMasterByShortCode(MainetConstants.ServiceShortCode.DUPLICATE_BILL_PRINT,
				UserSession.getCurrent().getOrganisation().getOrgid());
		if (serviceMaster.getSmFeesSchedule()!=null && serviceMaster.getSmFeesSchedule().longValue() != 0l
				&& MainetConstants.FlagY.equals(serviceMaster.getSmAppliChargeFlag())) {
			setChallanDToandSaveChallanData(offline, duplicatePaymentReceiptModel, serviceMaster);
		}
	}

	private void setChallanDToandSaveChallanData(CommonChallanDTO offline, DuplicateBillReceiptModel model, ServiceMaster serviceMaster) {

		offline.setReferenceNo(model.getConnectionNo());
		offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		offline.setLangId(UserSession.getCurrent().getLanguageId());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setFaYearId(UserSession.getCurrent().getFinYearId());
		offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
		offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
		offline.setServiceId(serviceMaster.getSmServiceId());

		TbKCsmrInfoMH csmrInfo = csmrInfoRepository.getCsmrInfoByCsCcnAndOrgId(model.getConnectionNo(), model.getOrgId());
		
		offline.setReferenceNo(csmrInfo.getCsCcn());
		String firstName = csmrInfo.getCsName() != null && !csmrInfo.getCsName().isEmpty() ? csmrInfo.getCsName(): "";
		String middleName = csmrInfo.getCsMname() != null && !csmrInfo.getCsMname().isEmpty() ? csmrInfo.getCsMname(): "";
		String lastName = csmrInfo.getCsLname() != null && !csmrInfo.getCsLname().isEmpty() ? csmrInfo.getCsLname(): "";
		offline.setApplicantName(String.join(" ", Arrays.asList(firstName, middleName, lastName)));
		offline.setApplicantAddress(csmrInfo.getCsAdd());
		offline.setEmailId(csmrInfo.getCsEmail());
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		WardZoneBlockDTO setDivWardZone = setDivWardZone(csmrInfo);
		offline.setDwzDTO(setDivWardZone);
		
		if (model.getChargesInfo() != null) {
			for (ChargeDetailDTO dto : model.getChargesInfo()) {
				offline.getFeeIds().put(dto.getChargeCode(), dto.getChargeAmount());
				offline.setAmountToPay(String.valueOf(dto.getChargeAmount()));
			}
		}	
		offline.setLgIpMac(UserSession.getCurrent().getEmployee().getLgIpMac());
		offline.setLoggedLocId(UserSession.getCurrent().getLoggedLocId());
		offline.setDeptId(serviceMaster.getTbDepartment().getDpDeptid());
		offline.setOfflinePaymentText(CommonMasterUtility
				.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
				.getLookUpCode());
		offline.setLoiNo(csmrInfo.getCsCcn());
		if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.MENU.N)) {
			final ChallanMaster responseChallan = challanService.InvokeGenerateChallan(offline);
			offline.setChallanNo(responseChallan.getChallanNo());
			offline.setChallanValidDate(responseChallan.getChallanValiDate());
		} else if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {
			final ChallanReceiptPrintDTO printDto = challanService.savePayAtUlbCounter(offline,
				null);

			model.setReceiptDTO(printDto);
			model.setSuccessMessage(model.getAppSession().getMessage("adh.receipt"));
		}
		model.setOfflineDTO(offline);
	
	}

	private WardZoneBlockDTO setDivWardZone(TbKCsmrInfoMH master) {
		
		WardZoneBlockDTO wardZoneDTO = new WardZoneBlockDTO();
		try {
            if (master != null) {
                if (master.getCodDwzid1() != null) {
                    wardZoneDTO.setAreaDivision1(master.getCodDwzid1());
                }
                if (master.getCodDwzid2() != null) {
                    wardZoneDTO.setAreaDivision2(master.getCodDwzid2());
                }
                if (master.getCodDwzid3() != null) {
                    wardZoneDTO.setAreaDivision3(master.getCodDwzid3());
                }
                if (master.getCodDwzid4() != null) {
                    wardZoneDTO.setAreaDivision4(master.getCodDwzid4());
                }
                if (master.getCodDwzid5() != null) {
                    wardZoneDTO.setAreaDivision5(master.getCodDwzid5());
                }
                if (master.getTrmGroup1() != null) {
                    wardZoneDTO.setTariffCategory(master.getTrmGroup1());
                }
            }
        } catch (Exception e) {
            LOGGER.error("Exception occurs in setDivWardZone for connection no. "+ master.getCsCcn() + " " + e.getMessage());
            throw new FrameworkException("Exception occours in setDivWardZone() method" + e);
        }	
		
		return wardZoneDTO;
	}
}
