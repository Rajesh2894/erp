package com.abm.mainet.cfc.loi.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.loi.dto.TbLoiDet;
import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.cfc.scrutiny.dto.ScrutinyLableValueDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.dto.CommonSequenceConfigDto;
import com.abm.mainet.common.dto.SequenceConfigMasterDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.dto.TbCfcApplicationMst;
import com.abm.mainet.common.master.service.TbCfcApplicationMstService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.UtilityService;

@Component
@Scope("session")
public class LoiManualEntryModel  extends AbstractFormModel {
	

	private static final long serialVersionUID = -6479543900720438472L;
	
	@Resource
	private SeqGenFunctionUtility seqGenFunctionUtility;
	
	@Autowired
	private TbCfcApplicationMstService tbCfcservice;
	
	@Autowired
	private TbLoiMasService iTbLoiMasService;
	
	private TbLoiMas entity = new TbLoiMas();

	private String applicantName;

	private String serviceName;

	private Date applicationDate;
	
	private String mobNo;

	private String email;
	
	private BigDecimal total;

	private Long appId;

	private Long serviceId;

	private Long taskId;
	
	private Long deptId;
	
	private List<TbLoiDet> loiDets;
	
	private ScrutinyLableValueDTO labelValueDTO = new ScrutinyLableValueDTO();
	
	public boolean saveLoiData() {
		boolean status;
		final UserSession session = UserSession.getCurrent();
		TbCfcApplicationMst applicationMst = new TbCfcApplicationMst();
		final List<TbLoiDet> loiDetails = new ArrayList<>(0);
		final ApplicationSession appSession = ApplicationSession.getInstance();
		Long orgId = session.getOrganisation().getOrgid();
		
		if(total != null) {
			Long appNo = setAppIdforAppDetails(orgId, deptId);
			applicationMst.setApmApplicationId(appNo);
			applicationMst.setApmApplicationDate(applicationDate);
			applicationMst.setSmServiceId(serviceId);
			setAppFullNAme(applicationMst);
	        applicationMst.setOrgid(orgId);
	        applicationMst.setUserId(orgId);
	        applicationMst.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
	        applicationMst.setLmoddate(new Date());
	        applicationMst.setLgIpMac(session.getEmployee().getLgIpMac());
	        
	        tbCfcservice.create(applicationMst);
	        
	        
	        final Long payType = CommonMasterUtility
					.getValueFromPrefixLookUp(MainetConstants.FlagC, PrefixConstants.LookUpPrefix.LPT, session.getOrganisation()).getLookUpId();
	        loiDets.forEach(loiDet 	-> {
				loiDet.setLgIpMac(session.getEmployee().getLgIpMac());
				loiDet.setOrgid(session.getOrganisation().getOrgid());
				loiDet.setUserId(session.getEmployee().getEmpId());
				loiDet.setLmoddate(new Date());
				loiDet.setLoiCharge(MainetConstants.Common_Constant.YES);
				loiDet.setLoiPaytype(payType);
				loiDetails.add(loiDet);
	        });
	        getEntity().setLoiApplicationId(appNo);
	        getEntity().setLoiRefId(appNo);
	        getEntity().setLoiServiceId(serviceId);
	        getEntity().setLgIpMac(session.getEmployee().getLgIpMac());
			getEntity().setOrgid(session.getOrganisation().getOrgid());
			getEntity().setUserId(session.getEmployee().getEmpId());
			getEntity().setLoiDate(new Date());
			getEntity().setLmoddate(new Date());
			getEntity().setLoiPaid(MainetConstants.Common_Constant.NO);
			getEntity().setLoiStatus(MainetConstants.FlagA);
			final Calendar calendar = Calendar.getInstance();
			getEntity().setLoiYear(calendar.get(Calendar.YEAR));
			getEntity().setLoiAmount(total);
		}
		status = iTbLoiMasService.saveLoiDetails(entity, loiDetails, getLabelValueDTO());
		if (status) {
			setSuccessMessage(appSession.getMessage("loiGen.msg.loiNo") + entity.getLoiNo()
			+ MainetConstants.WHITE_SPACE + appSession.getMessage("loiGen.msg.genSuccess"));
		}
		return status;
		
	}

	private void setAppFullNAme(TbCfcApplicationMst applicationMst) {
		String[] names = applicantName.split(" ");

        applicationMst.setApmFname(names[0]);
        if (names.length > 1) {
        	applicationMst.setApmLname(names[names.length - 1]);
        }
        String middleName = "";
        if (names.length > 2) {
            for (int i = 1; i < names.length - 1; i++) {
                middleName += names[i] + " ";
            }
            applicationMst.setApmMname(middleName.trim());
        }
	}

	private Long setAppIdforAppDetails(Long orgId, Long dpDeptId) {
		Long appNo = null;
		final Date sysDate = UtilityService.getSQLDate(new Date());
		final String date = sysDate.toString();
		final String[] dateParts = date.split("-");
		final String year = dateParts[0];
		final String month = dateParts[1];
		final String day = dateParts[2];
		final String subString = year.substring(2);
		final String YYMMDDDate = subString.concat(month).concat(day);
		Long number = 0l;
		SequenceConfigMasterDTO configMasterDTO = null;
		configMasterDTO = seqGenFunctionUtility.loadSequenceData(orgId, deptId,
				MainetConstants.CommonMasterUi.TB_CFC_APP_MST,
				MainetConstants.SCRUTINY_COMMON_PARAM.APM_APPLICATION_ID);
		if (configMasterDTO.getSeqConfigId() != null) {
			CommonSequenceConfigDto commonSequenceConfigDto = new CommonSequenceConfigDto();
			String sequenceNo = seqGenFunctionUtility.generateNewSequenceNo(configMasterDTO, commonSequenceConfigDto);
			if (sequenceNo == null) {
				throw new FrameworkException("sequence not generated for " );
			} else {
				appNo = Long.valueOf(sequenceNo);
				if (appNo <= 0) {
					throw new FrameworkException("sequence not generated for ");
				}
			}
		} else {
			String orgIdString = orgId.toString();
			number = seqGenFunctionUtility.generateSequenceNo(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER,
					MainetConstants.CommonMasterUi.TB_CFC_APP_MST,
					MainetConstants.SCRUTINY_COMMON_PARAM.APM_APPLICATION_ID, orgId,
					MainetConstants.CommonMasterUi.D, null);
			final String paddingAppNo = String.format(MainetConstants.LOI.LOI_NO_FORMAT,
					Long.parseLong(number.toString()));
			String appNumber = orgIdString.concat(YYMMDDDate).concat(paddingAppNo);
			appNo = Long.parseLong(appNumber);
		}
		return appNo;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public TbLoiMas getEntity() {
		return entity;
	}

	public void setEntity(TbLoiMas entity) {
		this.entity = entity;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Date getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
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

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public List<TbLoiDet> getLoiDets() {
		return loiDets;
	}

	public void setLoiDets(List<TbLoiDet> loiDets) {
		this.loiDets = loiDets;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public ScrutinyLableValueDTO getLabelValueDTO() {
		return labelValueDTO;
	}

	public void setLabelValueDTO(ScrutinyLableValueDTO labelValueDTO) {
		this.labelValueDTO = labelValueDTO;
	}

	
}
