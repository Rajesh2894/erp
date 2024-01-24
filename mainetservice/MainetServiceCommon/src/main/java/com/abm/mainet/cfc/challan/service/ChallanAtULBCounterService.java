package com.abm.mainet.cfc.challan.service;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import com.abm.mainet.bill.service.BillMasterCommonService;
import com.abm.mainet.bill.service.BillPaymentService;
import com.abm.mainet.cfc.challan.dao.IChallanAtULBCounterDAO;
import com.abm.mainet.cfc.challan.dao.IChallanDAO;
import com.abm.mainet.cfc.challan.domain.ChallanDetails;
import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanDetailsDTO;
import com.abm.mainet.cfc.challan.dto.ChallanRegenerateDTO;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.dao.ICFCApplicationMasterDAO;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.BillReceiptPostingDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.ICFCApplicationAddressService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Service
public class ChallanAtULBCounterService implements IChallanAtULBCounterService {

	@Autowired
	private IChallanAtULBCounterDAO iChallanAtULBCounterDAO;

	@Autowired
	private ICFCApplicationMasterDAO iCFCApplicationMasterDAO;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private IChallanDAO challanDAO;

	@Autowired
	private ISMSAndEmailService ismsAndEmailService;

	@Autowired
	private TbLoiMasService iTbLoiMasService;

	@Autowired
	private IChallanService iChallanService;

	@Autowired
	private ICFCApplicationAddressService iCFCApplicationAddressService;

	@Resource
	private IWorkflowTyepResolverService iWorkflowTyepResolverService;

	@Resource
	private BillMasterCommonService billMasterCommonService;

	@Autowired
	private IReceiptEntryService iReceiptEntryService;

	@Resource
	private DepartmentService departmentService;

	@Autowired
	private IWorkflowRequestService workflowRequestService;

	@Override
	public ChallanDetailsDTO getChallanDetails(final Long challanNo, final Long applicationNo,final String payStaus, final long orgId,
			final long langId) {

		final Object[] challanRawDetais = iChallanAtULBCounterDAO.queryChallanDetails(challanNo, applicationNo,payStaus, orgId);
		ChallanDetailsDTO dto = null;
		if (null != challanRawDetais) {
			dto = new ChallanDetailsDTO();
			dto.setChallanId(Long.valueOf(challanRawDetais[0].toString()));
			dto.setSmServiceId(Long.valueOf(challanRawDetais[1].toString()));
			dto.setChallanNo(Long.valueOf(challanRawDetais[2].toString()));
			dto.setOrgId(Long.valueOf(challanRawDetais[3].toString()));
			if (challanRawDetais[4] != null) {
				dto.setApplicationId(Long.valueOf(challanRawDetais[4].toString()));
			}
			dto.setChallanDate((Date) challanRawDetais[5]);
			dto.setChallanAmount(Double.valueOf(challanRawDetais[6].toString()));
			dto.setServiceName(String.valueOf(challanRawDetais[7].toString()));
			if (langId == 2) {
				if (null != challanRawDetais[8]) {
					dto.setServiceName(String.valueOf(challanRawDetais[8].toString()));
				}
			}
			if (challanRawDetais[9] != null) {
				final StringBuilder str = new StringBuilder(challanRawDetais[9].toString());
				dto.setServiceURL(str.subSequence(0, str.indexOf(MainetConstants.operator.COMMA)).toString().trim());
			}
			if (null != challanRawDetais[10]) {
				dto.setServiceShortDesc(String.valueOf(challanRawDetais[10].toString()));
			}
			dto.setUserId(Long.valueOf(challanRawDetais[11].toString()));
			dto.setMasterServiceId(Long.valueOf(challanRawDetais[12].toString()));
			dto.setDeptId(Long.valueOf(challanRawDetais[13].toString()));
			dto.setChallanExpierdDate(((Date) challanRawDetais[14]));
			dto.setChallanServiceType(challanRawDetais[15].toString());
			if (null != challanRawDetais[16]) {
				dto.setLoiNo(challanRawDetais[16].toString());
			}
			if (null != challanRawDetais[17]) {
				dto.setUniqueKey(challanRawDetais[17].toString());
			}
			if (null != challanRawDetais[18]) {
				dto.setPaymentCategory(challanRawDetais[18].toString());
			}

			if (dto.getApplicationId() > 0) {
				final TbCfcApplicationMstEntity master = iCFCApplicationMasterDAO
						.getCFCApplicationByApplicationId(dto.getApplicationId(), orgId);
				if (master != null) {
					String userName = (master.getApmFname() == null ? MainetConstants.BLANK
							: master.getApmFname() + MainetConstants.WHITE_SPACE);
					userName += master.getApmMname() == null ? MainetConstants.BLANK
							: master.getApmMname() + MainetConstants.WHITE_SPACE;
					userName += master.getApmLname() == null ? MainetConstants.BLANK : master.getApmLname();
					dto.setApplicantName(userName);
				}
			} else if (dto.getUniqueKey() != null && !dto.getUniqueKey().isEmpty()) {
				String userName = getApplicantUserNameModuleWise(dto.getUniqueKey(), dto.getOrgId(), dto.getDeptId());
				dto.setApplicantName(userName);
			}
		}
		return dto;
	}

	private String getApplicantUserNameModuleWise(String uniqueKey, long orgId, long deptId) {
		String result = null;
		try {
			BillPaymentService dynamicServiceInstance = null;
			String serviceClassName = null;
			final String dept = departmentService.getDeptCode(deptId);
			serviceClassName = messageSource.getMessage(
					ApplicationSession.getInstance().getMessage("bill.lbl.Bills") + dept, new Object[] {},
					StringUtils.EMPTY, Locale.ENGLISH);
			dynamicServiceInstance = ApplicationContextProvider.getApplicationContext().getBean(serviceClassName,
					BillPaymentService.class);
			result = dynamicServiceInstance.getApplicantUserNameModuleWise(orgId, uniqueKey);
		} catch (LinkageError | Exception e) {
			throw new FrameworkException("Exception in UpdateBill for advance payment for department Id :" + deptId, e);
		}
		return result;

	}

	@Override
	@Transactional
	public TbServiceReceiptMasEntity updateChallanDetails(final ChallanDetailsDTO challanDetails,
			final CommonChallanDTO offlineMaster, final Organisation orgnisation, Long taskId, Long empType, Long empId,
			String empName) {
		List<BillReceiptPostingDTO> result = null;
		offlineMaster.setDeptId(challanDetails.getDeptId());
		offlineMaster.setOrgId(orgnisation.getOrgid());
		offlineMaster.setUniquePrimaryId(challanDetails.getUniqueKey());
		offlineMaster.setUserId(empId);
		offlineMaster.setServiceId(challanDetails.getSmServiceId());
		offlineMaster.setApplNo(challanDetails.getApplicationId());
		offlineMaster.setLoiNo(challanDetails.getLoiNo());
		offlineMaster.setChallanServiceType(challanDetails.getChallanServiceType());
		offlineMaster.setApplicantName(challanDetails.getApplicantName());
		TbServiceReceiptMasEntity serviceReceiptMaster = null;
		Map<Long, Double> feeId = null;
		Map<Long, Long> billDetailId = null;
		iChallanAtULBCounterDAO.updateChallanDetails(challanDetails);
		final List<ChallanDetails> challanfeeMode = challanDAO
				.getChallanDetails(Long.toString(challanDetails.getChallanNo()), orgnisation.getOrgid());
		if ((MainetConstants.CHALLAN_RECEIPT_TYPE.REVENUE_BASED.equals(challanDetails.getChallanServiceType())
				|| MainetConstants.CHALLAN_RECEIPT_TYPE.MIXED.equals(challanDetails.getChallanServiceType()))
				&& challanDetails.getUniqueKey() != null) {
			result = iChallanService.updateBillMasterData(challanDetails.getUniqueKey(), orgnisation.getOrgid(),
					challanDetails.getDeptId(), challanDetails.getChallanAmount(), challanDetails.getUserId(),
					offlineMaster.getLgIpMac(),offlineMaster.getManualReeiptDate(),null,null);
			if (result != null && !result.isEmpty()) {
				iChallanService.getDataRequiredforRevenueReceipt(offlineMaster, orgnisation);
				LookUp billReceipt = null;
				final Long advanceTaxId = billMasterCommonService.getAdvanceTaxId(offlineMaster.getOrgId(), null,
						offlineMaster.getDeptId());
				double advanceTaxPayment = 0;
				if (advanceTaxId != null) {
					advanceTaxPayment = result.stream().filter(bill -> bill.getTaxId().equals(advanceTaxId))
							.collect(Collectors.toList()).stream().mapToDouble(i -> i.getTaxAmount()).sum();
				}

				if (advanceTaxPayment > 0d && advanceTaxPayment == challanDetails.getChallanAmount()) {
					billReceipt = CommonMasterUtility.getValueFromPrefixLookUp("A", "RV", orgnisation);
				} else {
					billReceipt = CommonMasterUtility.getValueFromPrefixLookUp("R", "RV", orgnisation);
				}
				offlineMaster.setPaymentCategory(billReceipt.getLookUpCode());
				serviceReceiptMaster = iReceiptEntryService.insertInReceiptMaster(offlineMaster, result);
				final Long receiptId = serviceReceiptMaster.getRmRcptid();
				if (advanceTaxPayment > 0d) {
					iChallanService.saveAdvancePayment(offlineMaster.getOrgId(), offlineMaster.getUniquePrimaryId(),
							receiptId, offlineMaster.getDeptId(), challanDetails.getUserId(), advanceTaxPayment);
				}
			}
		}
		if (MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED.equals(challanDetails.getChallanServiceType())) {
			feeId = new HashMap<>(0);
			billDetailId = new HashMap<>(0);
			if (challanfeeMode != null) {
				for (final ChallanDetails details : challanfeeMode) {
					feeId.put(details.getTaxId(), details.getRfFeeamount());
					if (details.getBilldetId() != null) {
						billDetailId.put(details.getTaxId(), details.getBilldetId());
					}
				}
			}
			offlineMaster.setFeeIds(feeId);
			offlineMaster.setBillDetIds(billDetailId);
			final LookUp serviceReceipt = CommonMasterUtility.getValueFromPrefixLookUp("M", "RV", orgnisation);
			offlineMaster.setPaymentCategory(serviceReceipt.getLookUpCode());
			serviceReceiptMaster = iReceiptEntryService.insertInReceiptMaster(offlineMaster, result);
		}
		if (MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED.equals(challanDetails.getChallanServiceType())
				|| MainetConstants.CHALLAN_RECEIPT_TYPE.MIXED.equals(challanDetails.getChallanServiceType())) {
			iCFCApplicationMasterDAO.updateCFCApplicationMasterPaymentStatus(challanDetails.getApplicationId(),
					MainetConstants.PAY_STATUS.PAID, orgnisation.getOrgid());
			if ((challanDetails.getLoiNo() != null) && !challanDetails.getLoiNo().isEmpty()) {
				iTbLoiMasService.updateLoiPaidByLoiNo(challanDetails.getLoiNo(), challanDetails.getOrgId());
			}
			workflowRequestService.updateWorkFlow(taskId, offlineMaster, empType, empId, empName);
		}
		final CFCApplicationAddressEntity address = iCFCApplicationAddressService
				.getApplicationAddressByAppId(challanDetails.getApplicationId(), orgnisation.getOrgid());
		final TbCfcApplicationMstEntity applicationMaster = iCFCApplicationMasterDAO
				.getApplicationMaster(challanDetails.getApplicationId(), orgnisation);
		if (address != null) {
			challanDetails.setEmailId(address.getApaEmail());
			challanDetails.setMobileNo(address.getApaMobilno());

			String useraddress = address.getApaFloor() == null

					? MainetConstants.CommonConstants.BLANK
					: address.getApaFloor() + MainetConstants.BLANK_WITH_SPACE;
			useraddress += address.getApaBldgnm() == null ? MainetConstants.CommonConstants.BLANK
					: address.getApaBldgnm() + MainetConstants.BLANK_WITH_SPACE;
			useraddress += address.getApaAreanm() == null ? MainetConstants.CommonConstants.BLANK
					: address.getApaAreanm() + MainetConstants.BLANK_WITH_SPACE;

			useraddress += address.getApaHsgCmplxnm() == null

					? MainetConstants.CommonConstants.BLANK
					: address.getApaHsgCmplxnm() + MainetConstants.BLANK_WITH_SPACE;
			useraddress += address.getApaRoadnm() == null ? MainetConstants.CommonConstants.BLANK
					: address.getApaRoadnm() + MainetConstants.BLANK_WITH_SPACE;
			useraddress += address.getApaPincode() == null ? MainetConstants.CommonConstants.BLANK
					: address

							.getApaPincode() + " .";
			useraddress = useraddress.substring(0, useraddress.length() - 1);
			
			challanDetails.setAddress(useraddress);
		
		}
    	offlineMaster.setApplicantAddress(address.getApaAreanm());
		if (applicationMaster != null) {
			String userName = (applicationMaster.getApmFname() == null ? MainetConstants.CommonConstants.BLANK
					: applicationMaster.getApmFname() + MainetConstants.WHITE_SPACE);
			userName += applicationMaster.getApmMname() == null ? MainetConstants.CommonConstants.BLANK
					: applicationMaster.getApmMname() + MainetConstants.WHITE_SPACE;
			userName += applicationMaster.getApmLname() == null ? MainetConstants.CommonConstants.BLANK
					: applicationMaster.getApmLname();
			challanDetails.setApplicantName(userName);
		}

		sendMail(challanDetails, orgnisation);
		return serviceReceiptMaster;
	}

	private void sendMail(final ChallanDetailsDTO challanDetails, final Organisation orgnisation) {

		final SMSAndEmailDTO dto = new SMSAndEmailDTO();
		final double amount = challanDetails.getChallanAmount();

		dto.setChallanAmt(amount > 0 ? String.valueOf(amount) : MainetConstants.BLANK);
		dto.setV_muncipality_name(orgnisation.getONlsOrgname());
		dto.setEmail(challanDetails.getEmailId());
		dto.setMobnumber(challanDetails.getMobileNo());
		if (challanDetails.getServiceName() != null) {
			dto.setServName(challanDetails.getServiceName());
		} else {
			dto.setServName(ApplicationSession.getInstance().getMessage("challan.message.appliedService"));
		}

		final long applicationId = challanDetails.getApplicationId();
		dto.setAppNo(String.valueOf(applicationId > 0 ? String.valueOf(applicationId) : MainetConstants.BLANK));
		dto.setOrganizationName(orgnisation.getONlsOrgname());
		int langId = Utility.getDefaultLanguageId(orgnisation);
		// Added Changes As per told by Rajesh Sir For Sms and Email
		dto.setUserId(challanDetails.getUserId());
       //126164 payment url and sms type changed to send smsAndEmail bcoz ChallanUpdate.html this url is not present in event of sms configuration
		ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER, "ChallanVerification.html",
				PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL, dto, orgnisation, langId);
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(final MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainetservice.web.cfc.challan.service.IChallanAtULBCounterService#
	 * inactiveOldChallan(java.lang.Long, java.lang.Long, long)
	 */
	private boolean inactiveOldChallan(final Long applicationNo, final Long challanNo, final long orgid) {
		iChallanAtULBCounterDAO.inactiveOldChallan(applicationNo, challanNo, orgid);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainetservice.web.cfc.challan.service.IChallanAtULBCounterService#
	 * regenerateChallanAndInactiveOld(com.abm.
	 * mainetservice.web.cfc.challan.dto.ChallanDetailsDTO)
	 */
	@Override
	@Transactional
	public CommonChallanDTO regenerateChallanAndInactiveOld(final ChallanDetailsDTO challanDetails,
			final ChallanRegenerateDTO dto) {
		inactiveOldChallan(challanDetails.getApplicationId(), challanDetails.getChallanNo(), challanDetails.getOrgId());
		return regenerateChallan(challanDetails, dto);
	}

	/**
	 * @param challanDetails
	 * @param dto
	 */
	private CommonChallanDTO regenerateChallan(final ChallanDetailsDTO challanDetails, final ChallanRegenerateDTO dto) {
		final CommonChallanDTO requestDto = new CommonChallanDTO();
		requestDto.setApplNo(challanDetails.getApplicationId());
		requestDto.setEmailId(challanDetails.getEmailId());
		requestDto.setApplicantName(challanDetails.getApplicantName());
		requestDto.setMobileNumber(challanDetails.getMobileNo());
		requestDto.setChallanServiceType(challanDetails.getChallanServiceType());
		requestDto.setServiceId(challanDetails.getSmServiceId());
		requestDto.setDeptId(challanDetails.getDeptId());
		requestDto.setLoiNo(challanDetails.getLoiNo());
		requestDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		requestDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		requestDto.setLangId(UserSession.getCurrent().getLanguageId());
		requestDto.setLgIpMac(Utility.getMacAddress());
		requestDto.setFaYearId(UserSession.getCurrent().getFinYearId());
		requestDto.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
		requestDto.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
		requestDto.setUniquePrimaryId(challanDetails.getUniqueKey());
		requestDto.setPaymentCategory(challanDetails.getPaymentCategory());
		requestDto.setChallanServiceType(challanDetails.getChallanServiceType());
		final Long paymentMode = CommonMasterUtility.getValueFromPrefixLookUp(
				PrefixConstants.PRIFIX_CODE.PAY_AT_ULB_COUNTER, PrefixConstants.PRIFIX.ONLINE_OFFLINE).getLookUpId();
		requestDto.setOflPaymentMode(paymentMode);
		if (dto != null) {
			if (dto.getFeeIds() != null) {
				requestDto.setFeeIds(dto.getFeeIds());
			}
			if (dto.getBillDetIds() != null) {
				requestDto.setBillDetIds(dto.getBillDetIds());
			}
			if (dto.getAmountToPay() != null) {
				requestDto.setAmountToPay(dto.getAmountToPay().toString());
			}
		}
		final ChallanMaster challan = iChallanService.InvokeGenerateChallan(requestDto);
		requestDto.setChallanValidDate(challan.getChallanValiDate());
		requestDto.setChallanNo(challan.getChallanNo());
		return requestDto;
	}

	@Override
	@Transactional
	public ChallanRegenerateDTO calculateChallanAmount(final ChallanDetailsDTO challanDetail)
			throws ClassNotFoundException, LinkageError {
		Class<?> clazz = null;
		Object runtimeService = null;
		ChallanRegenerateDTO challanDto = null;

		final String smShortDesc = challanDetail.getServiceShortDesc();
		final String serviceName = messageSource.getMessage(smShortDesc, new Object[] {}, StringUtils.EMPTY,
				Locale.ENGLISH);
		if (!StringUtils.isEmpty(serviceName)) {
			clazz = ClassUtils.forName(serviceName,
					ApplicationContextProvider.getApplicationContext().getClassLoader());
			runtimeService = ApplicationContextProvider.getApplicationContext().getAutowireCapableBeanFactory()
					.autowire(clazz, 4, false);
			final Method method = ReflectionUtils.findMethod(clazz,
					ApplicationSession.getInstance().getMessage("challan.message.recalculateChallanAmount"),
					new Class[] { ChallanDetailsDTO.class });
			challanDto = (ChallanRegenerateDTO) ReflectionUtils.invokeMethod(method, runtimeService,
					new Object[] { challanDetail });
		}
		return challanDto;
	}

}
