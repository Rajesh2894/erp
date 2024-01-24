
package com.abm.mainet.cfc.challan.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.bill.service.AdjustmentEntryService;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.ui.model.CitizenOnlinePendingPayment;
import com.abm.mainet.cfc.loi.domain.TbLoiMasEntity;
import com.abm.mainet.cfc.loi.repository.PaymentTransactionMasRepository;
import com.abm.mainet.cfc.loi.repository.TbLoiMasJpaRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.asset.dto.AssetDetailsDTO;
import com.abm.mainet.common.integration.payment.dao.PaymentDAO;
import com.abm.mainet.common.integration.payment.entity.PGBankParameter;
import com.abm.mainet.common.integration.payment.entity.PaymentTransactionMas;
import com.abm.mainet.common.integration.payment.service.AtomPayment;
import com.abm.mainet.common.master.repository.EmployeeJpaRepository;
import com.abm.mainet.common.service.CitizenDashBoardService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CallBackResponseObj;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.HDFCIntegrationClass;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author cherupelli.srikanth
 *
 */
@Service
public class CitizenOnlinePendingPaymentSchedulerImpl implements CitizenOnlinePendingPaymentScheduler{


	private static final Logger log = LoggerFactory.getLogger(CitizenOnlinePendingPaymentSchedulerImpl.class);

	@Autowired
	private CitizenDashBoardService citizenDashBoardService;

	@Autowired
	private IChallanService iChallanService;

	@Autowired
	private ServiceMasterService serviceMasterService;

	@Autowired
	private EmployeeJpaRepository employeeJpaRepository;

	@Resource
	private TbLoiMasJpaRepository tbLoiMasJpaRepository;

	@Autowired
	private CommonService commonService;
	@Resource
	private PaymentDAO paymentDAO;
	@Autowired
	private PaymentTransactionMasRepository paymentTransactionMasRepository;
	@Autowired
	private AdjustmentEntryService adjustmentEntryService;
	
	

	@SuppressWarnings("static-access")
	@Override
	@Transactional 
	public void runOnlinePendingPaymentDate(CitizenOnlinePendingPayment model) {
		Employee emp = UserSession.getCurrent().getEmployee();
		int succsesCount = 0;
		int failCount = 0;
		Map<String, String> responseMap = new HashMap<String, String>();
		Map<Long, String> map = commonService.getAllPgBank(model.getOrgId());
		for (Map.Entry<Long, String> entry : map.entrySet()) {
			if (entry.getValue() != null && entry.getValue().equals(MainetConstants.PG_SHORTNAME.TECH_PROCESS)) {
				Optional<PGBankParameter> opt = commonService
						.getMerchantMasterParamByPgId(entry.getKey(), model.getOrgId()).stream()
						.filter(r -> r.getParName() != null
								&& r.getParName().equalsIgnoreCase(MainetConstants.BankParam.PRODUCTION))
						.findAny();
				
			}
		}
		List<PaymentTransactionMas> dto = citizenDashBoardService.getCitizenPayPendingDataByDateAndStatus(
				model.getChallanDTO().getValidDate(), model.getServiceId(), model.getOfflineDTO().getFlatNo(),
				model.getOrgId(), model.getOfflineDTO().getReferenceNo());

		if (CollectionUtils.isNotEmpty(dto)) {
			log.info("Pending Transaction  is started total size is : " + dto.size());
			
			for (PaymentTransactionMas rDTO : dto) {
				log.info("Pending Transaction  is started for ref no : " + rDTO.getReferenceId() + "  and Service Id "
						+ rDTO.getSmServiceId());
				CallBackResponseObj responseObj =null;
				
				if(MainetConstants.PG_SHORTNAME.CCA.equalsIgnoreCase(rDTO.getPgSource())) {
					StringBuilder input = new StringBuilder();
					input.append("{'order_no':'");
					input.append(rDTO.getTranCmId());
					input.append("'}");
					String pXmlData= input.toString();
					String pAccessCode=ApplicationSession.getInstance().getMessage("pAccessCode");
					String aesKey=ApplicationSession.getInstance().getMessage("aesKey");
					String pCommand=ApplicationSession.getInstance().getMessage("pCommand");
					String pRequestType=ApplicationSession.getInstance().getMessage("pRequestType");
					String pResponseType=ApplicationSession.getInstance().getMessage("pResponseType");
					String pVersion=ApplicationSession.getInstance().getMessage("pVersion");
					HDFCIntegrationClass hdfcInte = new HDFCIntegrationClass();
					responseObj = hdfcInte.callCCavenueApi(pXmlData, pAccessCode, pCommand, aesKey, pRequestType, pResponseType, pVersion);
				 }else if(MainetConstants.PG_SHORTNAME.ATOMPAY.equalsIgnoreCase(rDTO.getPgSource())) {
					 AtomPayment atomPayment =new AtomPayment();
					 String mId=ApplicationSession.getInstance().getMessage("atom.payment.MerchantId");
					 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                      //String dateString = ;
                      //System.out.println(formattedDate);
					responseObj = atomPayment.getResponseForPaymentStatus(mId, rDTO.getTranCmId(), rDTO.getSendAmount(), format.format(rDTO.getReferenceDate()));
					 
				 }
				
				if ((MainetConstants.Common_Constant.NUMBER.ZERO.equals(responseObj.getStatus()) && MainetConstants.PG_SHORTNAME.CCA.equalsIgnoreCase(rDTO.getPgSource())) || (MainetConstants.PG_REQUEST_PROPERTY.STATUSCODE.equals(responseObj.getStatus()) && MainetConstants.PG_SHORTNAME.ATOMPAY.equalsIgnoreCase(rDTO.getPgSource()))) {
					CommonChallanDTO offline = new CommonChallanDTO();

					offline = setOfflineDto(offline, rDTO);
					try {
						ChallanReceiptPrintDTO challandDto = iChallanService.updateDataAfterPayment(offline);
						if (challandDto != null) {
							rDTO.setRecvStatus(MainetConstants.Req_Status.SUCCESS);
							paymentTransactionMasRepository.save(rDTO);
							log.info("Pending Transaction done for  : " + rDTO.getReferenceId());
							succsesCount++;
						}
					} catch (Exception e) {
						log.error("Error occur at the time of updating Reciept data and generate workflow");
					}
				} else {
					rDTO.setRecvStatus("pending");
					paymentTransactionMasRepository.save(rDTO);
				}

				// });
			}
			model.setSuccessMessage("Succsessfully upadate  citizen pending payment count " + succsesCount);

		} else {
			model.addValidationError("No record Found for selected criteria");
		}
	}

	@SuppressWarnings("deprecation")
	private CommonChallanDTO setOfflineDto(CommonChallanDTO offline, PaymentTransactionMas rDTO) {
		offline.setOrgId(rDTO.getOrgId());
		offline.setPropNoConnNoEstateNoV(rDTO.getReferenceId());
		offline.setUniquePrimaryId(rDTO.getReferenceId());
		if (!rDTO.getReferenceId().isEmpty() && StringUtils.isNumeric(rDTO.getReferenceId()))
			offline.setApplNo(Long.valueOf(rDTO.getReferenceId()));
		if (rDTO.getSmServiceId() != null) {
			ServiceMaster sm = serviceMasterService.getServiceMaster(rDTO.getSmServiceId(), rDTO.getOrgId());
			if (sm != null && sm.getTbDepartment() != null) {
				if (sm.getTbDepartment().getDpDeptcode().equals(MainetConstants.DEPT_SHORT_NAME.WATER)) {
					/*Long csidn = null;
					if (csidn != null)*/
					Long csIdn = null;
					try {
						csIdn = commonService.getcsIdnByConnectionNo(rDTO.getReferenceId(), rDTO.getOrgId());
					} catch (ClassNotFoundException | LinkageError e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					offline.setUniquePrimaryId(csIdn.toString());
				} else if (sm.getTbDepartment().getDpDeptcode().equals(MainetConstants.DEPT_SHORT_NAME.PROPERTY)) {
					offline = getPropertyPaymentDetails(offline);
				}
			}
			offline.setDeptId(sm.getTbDepartment().getDpDeptid());
			offline.setServiceId(rDTO.getSmServiceId());
		}
		offline.setApplicantFullName(rDTO.getSendFirstname());

			
		offline.setOnlineOfflineCheck(MainetConstants.FlagY);

		if (rDTO.getSendAmount() != null) {
			offline.setAmountToPay(rDTO.getSendAmount().toString());
			offline.setAmountToShow(rDTO.getSendAmount().doubleValue());
		}
		offline.setOfflinePaymentText(MainetConstants.PAYMENT_MODES.PCB_MODE);
		Long lookUpId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("W", MainetConstants.PAY_PREFIX.PREFIX_VALUE,
				rDTO.getOrgId());
		offline.setPayModeIn(lookUpId);
		// offline.setPgRefId(rDTO.getPgType());

		offline.setFlatNo(rDTO.getFlatNo());
		offline.setChallanServiceType(rDTO.getChallanServiceType());
		offline.setUserId(rDTO.getUserId());
		// offline.setEmpType();
		offline.setLgIpMac(rDTO.getLgIpMac());
		offline.setApplicantName(rDTO.getSendFirstname());
		if (StringUtils.isNotEmpty(rDTO.getFeeIds()) && rDTO.getFeeIds().length() > 2)
			offline.setFeeIds(getFeeId(rDTO.getFeeIds(), rDTO.getSendAmount()));
		offline.setMobileNumber(rDTO.getSendPhone());
		if (null != rDTO.getReferenceDate()) {
		offline.setReferenceDate(rDTO.getReferenceDate());
		offline.setManualReeiptDate(rDTO.getReferenceDate());
		}
		TbLoiMasEntity entity=tbLoiMasJpaRepository.findloiByApplicationIdAndOrgId(offline.getApplNo(),  rDTO.getOrgId());
		if(entity!=null) {
			offline.setLoiNo(entity.getLoiNo());
		}
		return offline;

	}

	private Map<Long, Double> getFeeId(String feeIds, BigDecimal amt) {

		String feeIds1 = feeIds.replace("{", "").replace("}", "");
		String[] arr = feeIds1.split(",");
		Map<Long, Double> map = new HashMap();
		if (arr != null && arr.length > 0) {
			for (String s : arr) {
				String[] s1 = s.split("=");

				map.put(Long.valueOf(s1[0].toString().trim()), Double.valueOf(s1[1].toString().trim()));
			}
		} else if (amt != null) {
			map.put(1L, amt.doubleValue());
		}
		return map;
	}
	
	public CommonChallanDTO getPropertyPaymentDetails(CommonChallanDTO data) {
		CommonChallanDTO detaildto = new CommonChallanDTO();
		try {
			ResponseEntity<?> responseEntity = RestClient.callRestTemplateClient(data,
					ServiceEndpoints.Property.GET_PROPERTY_DETAILS_BY_PROPERTY_NO_AND_ORGID);
			if ((responseEntity != null) && (responseEntity.getStatusCode() == HttpStatus.OK)) {
				ObjectMapper mapper = new ObjectMapper();
				detaildto = mapper.convertValue(responseEntity.getBody(), CommonChallanDTO.class);
			}
		} catch (Exception ex) {
			throw new FrameworkException("Exception occured while calling get asset details :" + data, ex);
		}
		return detaildto;

	}
	
}
