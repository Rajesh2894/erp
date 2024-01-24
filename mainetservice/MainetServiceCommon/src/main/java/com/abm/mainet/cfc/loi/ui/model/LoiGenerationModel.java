/**
 *
 */
package com.abm.mainet.cfc.loi.ui.model;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.loi.domain.TbLoiDetEntity;
import com.abm.mainet.cfc.loi.dto.TbLoiDet;
import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.service.TbLoiDetService;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.cfc.scrutiny.dto.ScrutinyLableValueDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.domain.TbWorkOrderDetailEntity;
import com.abm.mainet.common.domain.TbWorkOrderEntity;
import com.abm.mainet.common.dto.TbApprejMas;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbServicesMst;
import com.abm.mainet.common.master.dto.TbTaxDetMas;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.repository.TbWorkOrderDetailJpaRepository;
import com.abm.mainet.common.repository.TbWorkOrderJpaRepository;
import com.abm.mainet.common.service.ICFCApplicationAddressService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.service.TbApprejMasService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.LoiDetail;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

/**
 * @author Rahul.Yadav
 *
 */
@Component
@Scope("session")
public class LoiGenerationModel extends AbstractFormModel {
	private static final long serialVersionUID = -6479543900720438472L;

	@Autowired
	private ICFCApplicationMasterService iCFCApplicationMasterService;

	@Autowired
	private TbServicesMstService iTbServicesMstService;

	@Autowired
	private TbLoiDetService iTbLoiDetService;

	@Autowired
	private TbLoiMasService iTbLoiMasService;

	@Autowired
	private TbDepartmentService iTbDepartmentService;

	@Autowired
	ISMSAndEmailService ismsAndEmailService;

	@Autowired
	private ICFCApplicationMasterService applicationMstService;

	@Autowired
	private ICFCApplicationAddressService iCFCApplicationAddressService;

	@Autowired
	private IWorkflowActionService iWorkflowActionService;
	
	@Resource
    private TbWorkOrderJpaRepository tbWorkOrderJpaRepository;
	
	@Resource
    private TbWorkOrderDetailJpaRepository tbWorkOrderDetailJpaRepository;
	
	@Autowired
    private SeqGenFunctionUtility seqGenFunctionUtility;
	
	@Resource
    private ICFCApplicationMasterService icfcApplicationMasterService;
	
	@Resource
	private ServiceMasterService serviceMaster;
	 
	@Resource
	private TbApprejMasService tbApprejMasService;

	private TbLoiMas entity = new TbLoiMas();

	private String applicantName;

	private String serviceName;

	private Date applicationDate;

	private Map<Long, Double> loiCharges;

	private Map<String, Double> chargeDesc = new LinkedHashMap<>(0);

	private Double total = new Double(0d);

	private Long appId;

	private Long labelId;

	private Long serviceId;

	private Long taskId;

	private String chargesDefined;

	private ScrutinyLableValueDTO labelValueDTO = new ScrutinyLableValueDTO();

	private List<TbApprejMas> remarkList;

	private String serviceShortName;

	private String mobNo;

	private String email;

	private Map<String, Double> waterDues;

	private Map<String, Double> propDues;

	private String wndServiceFlag;

	private String departmentCode;
	
	private boolean showPropDues;
	
	private String reamrkValidFlag;
	
	private String refNo = "";
	
	private List<String> newRemarks;
	
	private Long currentLevel;

	public String getWndServiceFlag() {
		return wndServiceFlag;
	}

	public void setWndServiceFlag(String wndServiceFlag) {
		this.wndServiceFlag = wndServiceFlag;
	}

	public Map<String, Double> getWaterDues() {
		return waterDues;
	}

	public void setWaterDues(Map<String, Double> waterDues) {
		this.waterDues = waterDues;
	}

	public Map<String, Double> getPropDues() {
		return propDues;
	}

	public void setPropDues(Map<String, Double> propDues) {
		this.propDues = propDues;
	}

	/**
	 * @param applicationId2
	 * @param serviceId
	 * @throws LinkageError
	 * @throws ClassNotFoundException
	 */
	public void setApplicationDetails(final Long applicationId2, final Long serviceId)
			throws ClassNotFoundException, LinkageError {
		final UserSession session = UserSession.getCurrent();

		final List<TbLoiMas> loimas = iTbLoiMasService.getloiByApplicationId(applicationId2, serviceId,
				session.getOrganisation().getOrgid());

		if ((loimas != null) && !loimas.isEmpty()) {
			getEntity().setLoiRecordFound(MainetConstants.Common_Constant.YES);
			setEntity(loimas.get(0));
		} else {
			getEntity().setLoiRecordFound(MainetConstants.Common_Constant.NO);
		}
		final TbCfcApplicationMstEntity applicationMaster = iCFCApplicationMasterService
				.getCFCApplicationByApplicationId(applicationId2, session.getOrganisation().getOrgid());
		if (applicationMaster != null) {
			String userName = (applicationMaster.getApmFname() == null ? MainetConstants.BLANK
					: applicationMaster.getApmFname() + MainetConstants.WHITE_SPACE);
			userName += applicationMaster.getApmMname() == null ? MainetConstants.BLANK
					: applicationMaster.getApmMname() + MainetConstants.WHITE_SPACE;
			userName += applicationMaster.getApmLname() == null ? MainetConstants.BLANK
					: applicationMaster.getApmLname();
			setApplicantName(userName);
			setApplicationDate(applicationMaster.getApmApplicationDate());
		}
		final TbServicesMst serviceMst = iTbServicesMstService.findById(serviceId);
		if (serviceMst != null) {
			if (session.getLanguageId() == MainetConstants.ENGLISH) {
				setServiceName(serviceMst.getSmServiceName());
			} else {
				setServiceName(serviceMst.getSmServiceNameMar());
			}
		}
		// code added for set email and mobile no for Defect#121682
		final CFCApplicationAddressEntity address = iCFCApplicationAddressService
				.getApplicationAddressByAppId(applicationId2, session.getOrganisation().getOrgid());
		if (address != null) {
			this.setMobNo(address.getApaMobilno());
			this.setEmail(address.getApaEmail());
		}
		getEntity().setLoiApplicationId(applicationId2);
		getEntity().setLoiServiceId(serviceId);
		getEntity().setServiceShortCode(serviceMst.getSmShortdesc());
		getEntity().setLoiDateStr(Utility.dateToString(getEntity().getLoiDate()));

		final TbDepartment department = iTbDepartmentService.findById(serviceMst.getCdmDeptId());
		getEntity().setDeptShortCode(department.getDpDeptcode());

		setChargesForLOI(applicationId2, serviceId, serviceMst.getSmShortdesc(), session.getOrganisation().getOrgid());

	}

	/**
	 * @param applicationId2
	 * @param serviceId
	 * @param serviceShortDesc
	 * @param orgId
	 * @throws LinkageError
	 * @throws ClassNotFoundException
	 */
	private void setChargesForLOI(final Long applicationId2, final Long serviceId, final String serviceShortDesc,
			final long orgId) throws ClassNotFoundException, LinkageError {
		Map<Long, Double> charges = new HashMap<Long, Double>();
		if (StringUtils.equals(getEntity().getLoiRecordFound(), MainetConstants.Common_Constant.NO)) {
			charges = iTbLoiDetService.getModuleWiseCharges(applicationId2, serviceId, serviceShortDesc, orgId);
		} else {
			List<TbLoiDetEntity> loiDetList = iTbLoiDetService.findLoiDetailsByLoiIdAndOrgId(getEntity().getLoiId(),
					getEntity().getOrgid());
			for (TbLoiDetEntity tbLoiDetEntity : loiDetList) {
				charges.put(tbLoiDetEntity.getLoiChrgid(), tbLoiDetEntity.getLoiAmount().doubleValue());
			}
		}

		setLoiCharges(charges);

		if (charges != null && !charges.isEmpty()) {
			final List<Long> ids = new ArrayList<>(0);
			for (final Entry<Long, Double> entry : charges.entrySet()) {
				ids.add(entry.getKey());
			}
			final Map<Long, String> chargedesc = iTbLoiMasService.getChargeDescByChgId(ids,
					UserSession.getCurrent().getOrganisation().getOrgid(), getEntity().getLoiRecordFound());
			if ((chargedesc != null) && !chargedesc.isEmpty()) {
				for (final Entry<Long, Double> entry : charges.entrySet()) {
					for (final Entry<Long, String> entrydesc : chargedesc.entrySet()) {
						if (entry.getKey().equals(entrydesc.getKey())) {
							chargeDesc.put(entrydesc.getValue(), Double.valueOf(Math.round(entry.getValue())));
							if (entry.getValue() != null) {
								setTotal(Double.valueOf(Math.round(getTotal() + entry.getValue())));
							}
						}
					}
				}
			}
		}

	}

	public boolean saveLoiData() {
		boolean status;
		final UserSession session = UserSession.getCurrent();
		final List<TbLoiDet> loiDetails = new ArrayList<>(0);
		final ApplicationSession appSession = ApplicationSession.getInstance();
		boolean loiPayFlag = false;
		TbLoiDet loiDet = null;
		double totalEditableLoi = 0;
		if (loiCharges != null) {
			final Long payType = CommonMasterUtility
					.getValueFromPrefixLookUp(MainetConstants.FlagC, PrefixConstants.LookUpPrefix.LPT).getLookUpId();
			Long feeId = null;
			Double ammount = null;
			for (final Entry<Long, Double> entry : loiCharges.entrySet()) {
				feeId = entry.getKey();
				ammount = entry.getValue();
				loiDet = new TbLoiDet();

				TbTaxMas taxMaster = ApplicationContextProvider.getApplicationContext().getBean(TbTaxMasService.class)
						.findById(feeId, session.getOrganisation().getOrgid());
				String loiEditableFlag = MainetConstants.FlagN;
				if (CollectionUtils.isNotEmpty(taxMaster.getTaxDetMasList())) {
					for (TbTaxDetMas tbTaxMasEntity : taxMaster.getTaxDetMasList()) {
						LookUp EditableLoiLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(
								tbTaxMasEntity.getTdDependFact(), session.getOrganisation());
						if (EditableLoiLookUp != null && StringUtils.equals(EditableLoiLookUp.getLookUpCode(),
								PrefixConstants.LookUpPrefix.ELC)) {
							loiEditableFlag = MainetConstants.FlagY;
							break;
						}
					}
				}
				if (!StringUtils.equals(loiEditableFlag, MainetConstants.FlagY)) {
					if (ammount != null && ammount >= 0) {
						loiPayFlag = true;
						loiDet.setLoiAmount(BigDecimal.valueOf(ammount));
					}
				} else {
					for (final Entry<String, Double> chargeEntry : chargeDesc.entrySet()) {
						String[] splitTaxDescAndCode = chargeEntry.getKey().split("-");
						if (StringUtils.equals(splitTaxDescAndCode[1], MainetConstants.FlagY)) {
							loiPayFlag = true;
							loiDet.setLoiAmount(BigDecimal.valueOf(chargeEntry.getValue()));
							totalEditableLoi = totalEditableLoi + chargeEntry.getValue();
						}
					}
				}
				loiDet.setLoiChrgid(feeId);
				loiDet.setLgIpMac(Utility.getMacAddress());
				loiDet.setOrgid(session.getOrganisation().getOrgid());
				loiDet.setUserId(session.getEmployee().getEmpId());
				loiDet.setLmoddate(new Date());
				loiDet.setLoiCharge(MainetConstants.Common_Constant.YES);
				loiDet.setLoiPaytype(payType);
				loiDetails.add(loiDet);
			}
		}

		getEntity().setLgIpMac(Utility.getMacAddress());
		getEntity().setOrgid(session.getOrganisation().getOrgid());
		getEntity().setUserId(session.getEmployee().getEmpId());
		getEntity().setLoiDate(new Date());
		getEntity().setLmoddate(new Date());
		getEntity().setLoiPaid(MainetConstants.Common_Constant.NO);
		getEntity().setLoiStatus(MainetConstants.FlagA);
		final Calendar calendar = Calendar.getInstance();
		getEntity().setLoiYear(calendar.get(Calendar.YEAR));
		getEntity().setLoiRefId(entity.getLoiApplicationId());
		getEntity().setLoiAmount(BigDecimal.valueOf(getTotal() + totalEditableLoi));
		// US#106604
		if (CollectionUtils.isNotEmpty(remarkList)) {
			List<String> remList = new ArrayList<>();
			for (TbApprejMas rem : remarkList) {
				if (rem != null) {
					remList.add(rem.getArtRemarks());
				}
			}
			// Defect #132407 for restrict to set remark list as loi remark in the case of
			// License module.
			
			  if (!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TCP) &&StringUtils.isNotEmpty(this.getDepartmentCode()) &&
			  !this.getDepartmentCode().equals(MainetConstants.TradeLicense.MARKET_LICENSE)
			  ) {
			  
			  getEntity().setLoiRemark(StringUtils.join(remList, ",")); }
			} // END
		status = iTbLoiMasService.saveLoiDetails(entity, loiDetails, getLabelValueDTO());
		if (status) {
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TCP)) {
				saveRemark();
			}
				
			if (!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TCP) && taskId != null) {
				WorkflowTaskAction workFlowActionDto = new WorkflowTaskAction();
				workFlowActionDto.setApplicationId(this.getLabelValueDTO().getApplicationId());
				workFlowActionDto.setTaskId(taskId);
				workFlowActionDto.setIsLoiGenerated(loiPayFlag);
				iWorkflowActionService.updateWorkFlow(workFlowActionDto, UserSession.getCurrent().getEmployee(),
						UserSession.getCurrent().getOrganisation().getOrgid(), serviceId);
			}
			setSuccessMessage(appSession.getMessage("loiGen.msg.loiNo") + entity.getLoiNo()
					+ MainetConstants.WHITE_SPACE + appSession.getMessage("loiGen.msg.genSuccess"));

			sendSmsAndEmail(getEntity());

		}

		return status;
	}

	public boolean saveRemark() {
		String WPCcode = "WO";
		String FinancialYear = null;
		String workorderNo;
		final Long EmpId = UserSession.getCurrent().getEmployee().getEmpId();
		final Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		final Long LanguageId = Long.valueOf(UserSession.getCurrent().getLanguageId());
		final Long WoServiceId = serviceId;
		final Long WoApplicationId = this.getLabelValueDTO().getApplicationId();
		final TbCfcApplicationMstEntity tbCfcApplicationMstEntity = icfcApplicationMasterService
				.getCFCApplicationByApplicationId(WoApplicationId,
						UserSession.getCurrent().getOrganisation().getOrgid());
		final Date ApplicationDate = tbCfcApplicationMstEntity.getApmApplicationDate();
		ServiceMaster service = serviceMaster.getServiceMaster(serviceId,
				UserSession.getCurrent().getOrganisation().getOrgid());
		final Long WoDeptId = service.getTbDepartment().getDpDeptid();
		final String MacAddress = UserSession.getCurrent().getEmployee().getEmppiservername();
		try {
			FinancialYear = Utility.getFinancialYearFromDate(new Date());
		} catch (final Exception e) {
			e.printStackTrace();
		}
		TbWorkOrderEntity tbWorkOrderEntity = new TbWorkOrderEntity();
		final TbDepartment dept = iTbDepartmentService.findById(WoDeptId);
		TbWorkOrderDetailEntity tbWorkOrderDetailEntity = null;
		TbApprejMas tbApprejMas = null;
		if (null != entity.getLoiRecordFound() && entity.getLoiRecordFound().equals("N")) {
			final Long woId = seqGenFunctionUtility.generateSequenceNo("COM", "TB_WORK_ORDER", "WO_ID", orgid, null, null);
			tbWorkOrderEntity.setWoServiceId(WoServiceId);
			tbWorkOrderEntity.setWoDeptId(WoDeptId);
			tbWorkOrderEntity.setWoApplicationDate(ApplicationDate);
			tbWorkOrderEntity.setWoApplicationId(WoApplicationId);
			tbWorkOrderEntity.setWoServiceId(WoServiceId);
			tbWorkOrderEntity.setLmoddate(new Date());
			tbWorkOrderEntity.setUpdatedDate(new Date());
			tbWorkOrderEntity.setWoOrderDate(new Date());
			tbWorkOrderEntity.setUserId(EmpId);
			tbWorkOrderEntity.setOrgid(orgid);
			tbWorkOrderEntity.setLangId(LanguageId);
			tbWorkOrderEntity.setLgIpMac(MacAddress);
			tbWorkOrderEntity.setWoId(woId);
			tbWorkOrderEntity.setWoDeptId(WoDeptId);
			workorderNo = WPCcode + MainetConstants.operator.FORWARD_SLACE + dept.getDpDeptcode().toUpperCase()
					+ MainetConstants.operator.FORWARD_SLACE + FinancialYear + MainetConstants.operator.FORWARD_SLACE
					+ String.format("%04d", woId);
			tbWorkOrderEntity.setWoOrderNo(workorderNo);
			tbWorkOrderJpaRepository.save(tbWorkOrderEntity);
		} else {
			tbWorkOrderEntity = tbWorkOrderJpaRepository.findByApplicationId(WoApplicationId, orgid);
		}

		for (final TbApprejMas WorkOrderRemark : remarkList) {
			if (null != WorkOrderRemark) {
				tbWorkOrderDetailEntity = new TbWorkOrderDetailEntity();
				if (WorkOrderRemark.getArtId() != null) {

					tbWorkOrderDetailEntity = tbWorkOrderDetailJpaRepository.findByRemarkId(WoApplicationId,WorkOrderRemark.getArtId(),
							orgid);
					if (tbWorkOrderDetailEntity != null) {
						if (WorkOrderRemark.getIsSelected() != null) {
							tbWorkOrderDetailEntity.setWdOthRemark("Y");
						} else {
							tbWorkOrderDetailEntity.setWdOthRemark("N");
						}
						tbWorkOrderDetailEntity = tbWorkOrderDetailJpaRepository.save(tbWorkOrderDetailEntity);
					} else if(WorkOrderRemark.getIsSelected() != null){
						tbWorkOrderDetailEntity = new TbWorkOrderDetailEntity();
						tbWorkOrderDetailEntity.setWdApplicationId(WoApplicationId);
						tbWorkOrderDetailEntity.setWdServiceId(WoServiceId);
						tbWorkOrderDetailEntity.setTbWorkOrder(tbWorkOrderEntity);
						tbWorkOrderDetailEntity.setLmoddate(new Date());
						tbWorkOrderDetailEntity.setUpdatedDate(new Date());
						tbWorkOrderDetailEntity.setUserId(EmpId);
						tbWorkOrderDetailEntity.setOrgid(orgid);
						tbWorkOrderDetailEntity.setLangId(LanguageId);
						tbWorkOrderDetailEntity.setLgIpMac(MacAddress);
						tbWorkOrderDetailEntity.setWdRemarkId(WorkOrderRemark.getArtId());
						tbWorkOrderDetailEntity.setWdOthRemark("Y");
						final Long woDId = seqGenFunctionUtility.generateSequenceNo("COM", "TB_WORK_ORDER_DETAIL",
								"WD_ID", orgid, null, null);
						tbWorkOrderDetailEntity.setWdId(woDId);
						tbWorkOrderDetailEntity = tbWorkOrderDetailJpaRepository.save(tbWorkOrderDetailEntity);
					}

					
				}
			}
		}
		if (CollectionUtils.isNotEmpty(newRemarks)) {
			for (String remark : newRemarks) {
				tbWorkOrderDetailEntity = new TbWorkOrderDetailEntity();
				tbApprejMas = new TbApprejMas();
				if (StringUtils.isNotBlank(remark)) {
					tbApprejMas.setStatusflag(MainetConstants.Y_FLAG);
					tbApprejMas.setLgIpMac(MacAddress);
					tbApprejMas.setUserId(EmpId);
					tbApprejMas.setOrgid(orgid);
					tbApprejMas.setLangId(LanguageId);
					tbApprejMas.setArtRemarks(remark);
					tbApprejMas.setArtRemarksreg(remark);
					tbApprejMas.setDeptId(WoDeptId);
					tbApprejMas.setArtServiceId(WoServiceId);
					final Long artId = seqGenFunctionUtility.generateSequenceNo("COM", "TB_APPREJ_MAS", "ART_TYPE",
							tbApprejMas.getOrgid(), null, null);
					LookUp artTypeLookUp = CommonMasterUtility.getValueFromPrefixLookUp(
							PrefixConstants.ADVERTISEMENT_AND_HOARDING_PREFIX.APP,
							PrefixConstants.ADVERTISEMENT_AND_HOARDING_PREFIX.REM,
							UserSession.getCurrent().getOrganisation());
					tbApprejMas.setArtId(artId);
					tbApprejMas.setArtType(artTypeLookUp.getLookUpId());
					final TbApprejMas tbApprejMasCreated = tbApprejMasService.create(tbApprejMas);

					tbWorkOrderDetailEntity.setWdApplicationId(WoApplicationId);
					tbWorkOrderDetailEntity.setWdServiceId(WoServiceId);
					tbWorkOrderDetailEntity.setTbWorkOrder(tbWorkOrderEntity);
					tbWorkOrderDetailEntity.setLmoddate(new Date());
					tbWorkOrderDetailEntity.setUpdatedDate(new Date());
					tbWorkOrderDetailEntity.setUserId(EmpId);
					tbWorkOrderDetailEntity.setOrgid(orgid);
					tbWorkOrderDetailEntity.setLangId(LanguageId);
					tbWorkOrderDetailEntity.setLgIpMac(MacAddress);
					tbWorkOrderDetailEntity.setWdRemarkId(tbApprejMasCreated.getArtId());
					tbWorkOrderDetailEntity.setWdOthRemark("Y");
					tbWorkOrderDetailEntity.setWdRemark(remark);
					final Long woDId = seqGenFunctionUtility.generateSequenceNo("COM", "TB_WORK_ORDER_DETAIL", "WD_ID",
							orgid, null, null);
					tbWorkOrderDetailEntity.setWdId(woDId);
					tbWorkOrderDetailEntity = tbWorkOrderDetailJpaRepository.save(tbWorkOrderDetailEntity);

				}
			}
		}
		return true;

	}

	// T#90050 method for LOI other than scrutiny time
	public TbLoiMas saveLOIAppData(Map<Long, Double> loiCharges, Long serviceId, List<TbLoiDet> loiDetList,
			Boolean approvalLetterGenerationApplicable, WorkflowTaskAction wfActionDto) {
		TbLoiMas loiMasDto = new TbLoiMas();
		final UserSession session = UserSession.getCurrent();
		final Long paymentType = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.FlagC,
				PrefixConstants.LookUpPrefix.LPT, session.getOrganisation()).getLookUpId();

		loiMasDto.setOrgid(session.getOrganisation().getOrgid());
		loiMasDto.setUserId(session.getEmployee().getEmpId());
		loiMasDto.setLgIpMac(session.getEmployee().getLgIpMac());
		loiMasDto.setLmoddate(new Date());
		loiMasDto.setLoiPaid(MainetConstants.Common_Constant.NO);
		loiMasDto.setLoiStatus(MainetConstants.FlagA);
		loiMasDto.setLoiDate(new Date());
		loiMasDto.setLoiYear(Calendar.getInstance().get(Calendar.YEAR));
		loiMasDto.setLoiServiceId(serviceId);
		loiMasDto.setLoiRefId(wfActionDto.getApplicationId());
		loiMasDto.setLoiApplicationId(wfActionDto.getApplicationId());
		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL) && StringUtils.isNotBlank(wfActionDto.getComments()))
		loiMasDto.setLoiV3(wfActionDto.getComments());

		Long taxId = null;
		Double taxAmount = new Double(0);
		Double totalAmount = new Double(0);
		for (final Entry<Long, Double> loiCharge : loiCharges.entrySet()) {
			TbLoiDet loiDetails = new TbLoiDet();
			taxId = loiCharge.getKey();
			taxAmount = loiCharge.getValue();
			loiDetails.setLoiAmount(BigDecimal.valueOf(taxAmount));
			loiDetails.setOrgid(session.getOrganisation().getOrgid());
			loiDetails.setLoiChrgid(taxId);
			loiDetails.setLgIpMac(session.getEmployee().getLgIpMac());
			loiDetails.setUserId(session.getEmployee().getEmpId());
			loiDetails.setLoiCharge(MainetConstants.Common_Constant.YES);
			loiDetails.setLoiPaytype(paymentType);
			loiDetails.setLmoddate(new Date());
			loiDetList.add(loiDetails);
			totalAmount = totalAmount + taxAmount;
		}

		// setLoiDetail(loiDetailList);
		loiMasDto.setLoiAmount(BigDecimal.valueOf(totalAmount));
		if (totalAmount != 0) {
			iTbLoiMasService.saveLoiDetails(loiMasDto, loiDetList, null);
			LoiDetail loiDetail = new LoiDetail();
			loiDetail.setLoiNumber(loiMasDto.getLoiNo());
			loiDetail.setLoiPaymentApplicable(true);
			loiDetail.setIsComplianceApplicable(false);
			loiDetail.setIsApprovalLetterGenerationApplicable(approvalLetterGenerationApplicable);
			wfActionDto.setLoiDetails(new ArrayList<>());
			wfActionDto.getLoiDetails().add(loiDetail);
			return loiMasDto;
		}
		return null;
	}

	/**
	 * this method is used for sending SMS and Email to user at LOI Amount
	 * generation time.
	 * 
	 * @param entity
	 */
	private void sendSmsAndEmail(TbLoiMas entity) {
		TbCfcApplicationMstEntity applicant = applicationMstService
				.getCFCApplicationByApplicationId(entity.getLoiApplicationId(), entity.getOrgid());
		CFCApplicationAddressEntity appAddress = iCFCApplicationAddressService
				.getApplicationAddressByAppId(entity.getLoiApplicationId(), entity.getOrgid());
		if (applicant != null) {
			final SMSAndEmailDTO dto = new SMSAndEmailDTO();
			String userName = applicant.getApmFname() == null ? MainetConstants.BLANK
					: applicant.getApmFname() + MainetConstants.WHITE_SPACE;
			userName += applicant.getApmMname() == null ? MainetConstants.BLANK
					: applicant.getApmMname() + MainetConstants.WHITE_SPACE;
			userName += applicant.getApmLname() == null ? MainetConstants.BLANK : applicant.getApmLname();

			dto.setAppName(userName);
			dto.setAppNo(entity.getLoiApplicationId().toString());
			dto.setLoiAmt(entity.getLoiAmount().toString());
			dto.setEmail(appAddress.getApaEmail());
			dto.setMobnumber(appAddress.getApaMobilno());
			dto.setLoiNo(entity.getLoiNo());
			// 126164
			serviceName = iTbServicesMstService.getServiceNameByServiceId(entity.getLoiServiceId());
			if (StringUtils.isNotBlank(serviceName))
				dto.setServName(serviceName);
			if (applicant.getTbOrganisation() != null)
				dto.setOrganizationName(applicant.getTbOrganisation().getONlsOrgname());
			// to sent smsAndEmail in both language English and regional
			int langId = UserSession.getCurrent().getLanguageId();
			// Added Changes As per told by Rajesh Sir For Sms and Email
			dto.setUserId(entity.getUserId());
			ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER,
					MainetConstants.SMS_EMAIL_URL.LOI_GENERATION, PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL, dto,
					applicant.getTbOrganisation(), langId);

		}
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(final String applicantName) {
		this.applicantName = applicantName;
	}

	@Override
	public String getServiceName() {
		return serviceName;
	}

	@Override
	public void setServiceName(final String serviceName) {
		this.serviceName = serviceName;
	}

	public Date getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(final Date applicationDate) {
		this.applicationDate = applicationDate;
	}

	public Map<Long, Double> getLoiCharges() {
		return loiCharges;
	}

	public void setLoiCharges(final Map<Long, Double> loiCharges) {
		this.loiCharges = loiCharges;
	}

	public TbLoiMas getEntity() {
		return entity;
	}

	public void setEntity(final TbLoiMas entity) {
		this.entity = entity;
	}

	public Map<String, Double> getChargeDesc() {
		return chargeDesc;
	}

	public void setChargeDesc(final Map<String, Double> chargeDesc) {
		this.chargeDesc = chargeDesc;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(final Double total) {
		this.total = total;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(final Long appId) {
		this.appId = appId;
	}

	public Long getLabelId() {
		return labelId;
	}

	public void setLabelId(final Long labelId) {
		this.labelId = labelId;
	}

	@Override
	public Long getServiceId() {
		return serviceId;
	}

	@Override
	public void setServiceId(final Long serviceId) {
		this.serviceId = serviceId;
	}

	public ScrutinyLableValueDTO getLabelValueDTO() {
		return labelValueDTO;
	}

	public void setLabelValueDTO(final ScrutinyLableValueDTO labelValueDTO) {
		this.labelValueDTO = labelValueDTO;
	}

	public String getChargesDefined() {
		return chargesDefined;
	}

	public void setChargesDefined(final String chargesDefined) {
		this.chargesDefined = chargesDefined;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public List<TbApprejMas> getRemarkList() {
		return remarkList;
	}

	public void setRemarkList(List<TbApprejMas> remarkList) {
		this.remarkList = remarkList;
	}

	public String getServiceShortName() {
		return serviceShortName;
	}

	public String getMobNo() {
		return mobNo;
	}

	public void setMobNo(String mobNo) {
		this.mobNo = mobNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setServiceShortName(String serviceShortName) {
		this.serviceShortName = serviceShortName;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public boolean isShowPropDues() {
		return showPropDues;
	}

	public void setShowPropDues(boolean showPropDues) {
		this.showPropDues = showPropDues;
	}

	public String getReamrkValidFlag() {
		return reamrkValidFlag;
	}

	public void setReamrkValidFlag(String reamrkValidFlag) {
		this.reamrkValidFlag = reamrkValidFlag;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public List<String> getNewRemarks() {
		return newRemarks;
	}

	public void setNewRemarks(List<String> newRemarks) {
		this.newRemarks = newRemarks;
	}

	public Long getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(Long currentLevel) {
		this.currentLevel = currentLevel;
	}

	

}
