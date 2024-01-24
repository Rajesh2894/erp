/**
 * 
 */
package com.abm.mainet.water.ui.model;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.scrutiny.dto.ScrutinyLableValueDTO;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.service.NewWaterConnectionService;
import com.abm.mainet.water.service.WaterCommonService;
import com.abm.mainet.water.service.WaterServiceMapper;

/**
 * @author Saiprasad.Vengurekar
 *
 */
@Component
@Scope("session")
public class NewWaterConnectionExecutionFormModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1140393398137734807L;
	private TbCsmrInfoDTO csmrInfo = new TbCsmrInfoDTO();

	private TbCfcApplicationMstEntity cfcEntity = new TbCfcApplicationMstEntity();

	private CFCApplicationAddressEntity cfcAddressEntity = new CFCApplicationAddressEntity();

	private String applicantFullName;

	private String serviceName;

	@Autowired
	private ICFCApplicationMasterService cfcService;

	@Autowired
	private WaterServiceMapper waterMapper;

	@Autowired
	private WaterCommonService waterCommonService;

	@Resource
	private ServiceMasterService serviceMasterService;

	@Resource
	private NewWaterConnectionService newWaterConnectionService;

	private ScrutinyLableValueDTO lableValueDTO = new ScrutinyLableValueDTO();

	private Long serviceId;

	private Long applicationId;
	private String levelValue;
	private Long levelId;
	private Long level;

	private Date applicationDate;
	private String applicanttName;
	private String approveBy;
	private Date approveDate;

	@Override
	public boolean saveForm() {
		final TbCsmrInfoDTO csmrInfo = waterCommonService.getApplicantInformationById(getApplicationId(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		final TbKCsmrInfoMH master = waterMapper.mapTbKCsmrInfoDTOToTbKCsmrInfoEntity(csmrInfo);
		if (csmrInfo!=null && csmrInfo.getCsCcn() == null) {
			String ccnNo = newWaterConnectionService.generateWaterConnectionNumber(
					getLableValueDTO().getApplicationId(), getServiceId(),
					UserSession.getCurrent().getOrganisation().getOrgid(),master);
			csmrInfo.setCsCcn(ccnNo);
		}
		
		setMasterUpdateFields(master);
		setCsmrInfo(csmrInfo);
		waterCommonService.updateCsmrInfo(master, getLableValueDTO());
		if (csmrInfo!=null && csmrInfo.getCsCcn() == null)
		setSuccessMessage(getAppSession().getMessage("New Water Connection No. Execution Done Successfully",
                new Object[] { csmrInfo.getCsCcn() }));
		return true;
	}

	/**
	 * @param master
	 * 
	 */
	private void setMasterUpdateFields(final TbKCsmrInfoMH master) {
		master.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		master.setUpdatedDate(new Date());
		master.setLmodDate(new Date());
		master.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getLgIpMac());

	}

	public TbCsmrInfoDTO getCsmrInfo() {
		return csmrInfo;
	}

	public void setCsmrInfo(TbCsmrInfoDTO csmrInfo) {
		this.csmrInfo = csmrInfo;
	}

	public TbCfcApplicationMstEntity getCfcEntity() {
		return cfcEntity;
	}

	public void setCfcEntity(TbCfcApplicationMstEntity cfcEntity) {
		this.cfcEntity = cfcEntity;
	}

	public CFCApplicationAddressEntity getCfcAddressEntity() {
		return cfcAddressEntity;
	}

	public void setCfcAddressEntity(CFCApplicationAddressEntity cfcAddressEntity) {
		this.cfcAddressEntity = cfcAddressEntity;
	}

	public String getApplicantFullName() {
		return applicantFullName;
	}

	public void setApplicantFullName(String applicantFullName) {
		this.applicantFullName = applicantFullName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public ICFCApplicationMasterService getCfcService() {
		return cfcService;
	}

	public void setCfcService(ICFCApplicationMasterService cfcService) {
		this.cfcService = cfcService;
	}

	public ScrutinyLableValueDTO getLableValueDTO() {
		return lableValueDTO;
	}

	public void setLableValueDTO(ScrutinyLableValueDTO lableValueDTO) {
		this.lableValueDTO = lableValueDTO;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public String getLevelValue() {
		return levelValue;
	}

	public void setLevelValue(String levelValue) {
		this.levelValue = levelValue;
	}

	public Long getLevelId() {
		return levelId;
	}

	public void setLevelId(Long levelId) {
		this.levelId = levelId;
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public Date getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}

	public String getApplicanttName() {
		return applicanttName;
	}

	public void setApplicanttName(String applicanttName) {
		this.applicanttName = applicanttName;
	}

	public String getApproveBy() {
		return approveBy;
	}

	public void setApproveBy(String approveBy) {
		this.approveBy = approveBy;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

}
