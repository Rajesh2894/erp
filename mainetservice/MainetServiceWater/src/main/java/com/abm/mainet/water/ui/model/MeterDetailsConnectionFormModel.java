package com.abm.mainet.water.ui.model;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.scrutiny.dto.ScrutinyLableValueDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.domain.TbWaterReconnection;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.service.NewWaterConnectionService;
import com.abm.mainet.water.service.WaterCommonService;
import com.abm.mainet.water.service.WaterReconnectionService;
import com.abm.mainet.water.service.WaterServiceMapper;

/**
 * @author deepika.pimpale
 *
 */
@Component
@Scope("session")
public class MeterDetailsConnectionFormModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1667532624510310460L;

	private TbCsmrInfoDTO csmrInfo = new TbCsmrInfoDTO();

	private TbCfcApplicationMstEntity cfcEntity = new TbCfcApplicationMstEntity();

	private CFCApplicationAddressEntity cfcAddressEntity = new CFCApplicationAddressEntity();

	private String applicantFullName;

	private String serviceName;

	@Autowired
	private NewWaterConnectionService waterService;

	@Autowired
	private ICFCApplicationMasterService cfcService;

	@Autowired
	private WaterServiceMapper waterMapper;

	@Autowired
	private WaterReconnectionService waterReconnectionService;

	@Autowired
	private WaterCommonService waterCommonService;

	@Resource
	private ServiceMasterService serviceMasterService;

	private ScrutinyLableValueDTO lableValueDTO = new ScrutinyLableValueDTO();

	private Long serviceId;

	private String hasError = MainetConstants.BLANK;

	private TbWaterReconnection waterReconnection = new TbWaterReconnection();

	public void setConnectionDetailsInfo(final Long appId, final long serviceId, final long orgId) {
		setCfcAddressEntity(cfcService.getApplicantsDetails(appId));
		setCfcEntity(cfcService.getCFCApplicationByApplicationId(appId, orgId));
		final ServiceMaster serviceMaster = serviceMasterService.getServiceMaster(serviceId, orgId);
		setServiceName(serviceMaster.getSmServiceName());
		setCsmrInfo(waterCommonService.getApplicantInformationByAppId(appId, orgId, serviceMaster.getSmShortdesc()));
		final TbCsmrInfoDTO csmrInfo = getCsmrInfo();
		if (csmrInfo.getCsMname() != null) {
			String userName = (csmrInfo.getCsName() == null ? MainetConstants.BLANK
					: csmrInfo.getCsName() + MainetConstants.WHITE_SPACE);
			userName += csmrInfo.getCsMname() == null ? MainetConstants.BLANK
					: csmrInfo.getCsMname() + MainetConstants.WHITE_SPACE;
			userName += csmrInfo.getCsLname() == null ? MainetConstants.BLANK : csmrInfo.getCsLname();
			setApplicantFullName(userName);
		} else {
			String userName = (csmrInfo.getCsName() == null ? MainetConstants.BLANK
					: csmrInfo.getCsName() + MainetConstants.WHITE_SPACE);
			userName += csmrInfo.getCsMname() == null ? MainetConstants.BLANK
					: csmrInfo.getCsMname() + MainetConstants.WHITE_SPACE;
			userName += csmrInfo.getCsLname() == null ? MainetConstants.BLANK : csmrInfo.getCsLname();
			setApplicantFullName(userName);
		}
	}

	public void setReconnectionMeterDetails(final Long appId, final long serviceId, final long orgId) {

		setCfcAddressEntity(cfcService.getApplicantsDetails(appId));
		setCfcEntity(cfcService.getCFCApplicationByApplicationId(appId, orgId));
		final ServiceMaster serviceMaster = serviceMasterService.getServiceMaster(serviceId, orgId);
		setServiceName(serviceMaster.getSmServiceName());
		waterReconnection = waterReconnectionService.getReconnectionDetails(appId, orgId);
		final TbKCsmrInfoMH connectionDetails = waterReconnectionService
				.getWaterConnectionDetailsById(waterReconnection.getCsIdn(), orgId);

		final TbCsmrInfoDTO infoDTO = getCsmrInfo();
		infoDTO.setCsIdn(connectionDetails.getCsIdn());
		infoDTO.setApplicationNo(getApmApplicationId());
		infoDTO.setCsApldate(getCfcEntity().getApmApplicationDate());
		infoDTO.setCodDwzid1(getCfcAddressEntity().getApaWardNo());
		infoDTO.setCodDwzid2(getCfcAddressEntity().getApaZoneNo());
		if (null != getCfcAddressEntity().getApaBlockno()) {
			infoDTO.setCodDwzid3(Long.parseLong(getCfcAddressEntity().getApaBlockno()));
		}
		if (null != getCfcEntity().getApmMname()) {
			String userName = (getCfcEntity().getApmFname() == null ? MainetConstants.BLANK
					: getCfcEntity().getApmFname() + MainetConstants.WHITE_SPACE);
			userName += getCfcEntity().getApmMname() == null ? MainetConstants.BLANK
					: getCfcEntity().getApmMname() + MainetConstants.WHITE_SPACE;
			userName += getCfcEntity().getApmLname() == null ? MainetConstants.BLANK : getCfcEntity().getApmLname();
			setApplicantFullName(userName);
		} else {
			String userName = (getCfcEntity().getApmFname() == null ? MainetConstants.BLANK
					: getCfcEntity().getApmFname() + MainetConstants.WHITE_SPACE);
			userName += getCfcEntity().getApmMname() == null ? MainetConstants.BLANK
					: getCfcEntity().getApmMname() + MainetConstants.WHITE_SPACE;
			userName += getCfcEntity().getApmLname() == null ? MainetConstants.BLANK : getCfcEntity().getApmLname();
			setApplicantFullName(userName);
		}
		setCsmrInfo(infoDTO);

	}

	@Override
	protected final String findPropertyPathPrefix(final String parentCode) {
		switch (parentCode) {

		case PrefixConstants.NewWaterServiceConstants.WWZ:
			return "csmrInfo.codDwzid";

		default:
			return null;
		}
	}

	@Override
	public boolean saveForm() {
		final TbCsmrInfoDTO csmrInfo = getCsmrInfo();
		final TbKCsmrInfoMH master = waterMapper.mapTbKCsmrInfoDTOToTbKCsmrInfoEntity(csmrInfo);
		master.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		master.setUpdatedDate(new Date());
		master.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getLgIpMacUpd());
		master.setLmodDate(new Date());
		waterCommonService.updateCsmrInfo(master, getLableValueDTO());
		return true;
	}

	public boolean validateInputs() {
		final TbCsmrInfoDTO master = getCsmrInfo();
		if ((master.getCsMeteredccn() != null) && (master.getCsMeteredccn() == 0L)) {
			addValidationError(getAppSession().getMessage("meter.status"));
		}
		if (hasValidationErrors()) {
			setHasError(MainetConstants.MENU.Y);
			return false;
		} else {
			setHasError(MainetConstants.MENU.N);
		}
		return true;
	}

	/**
	 * @return the hasError
	 */
	public String getHasError() {
		return hasError;
	}

	/**
	 * @param hasError
	 *            the hasError to set
	 */
	public void setHasError(final String hasError) {
		this.hasError = hasError;
	}

	public TbCsmrInfoDTO getCsmrInfo() {
		return csmrInfo;
	}

	public void setCsmrInfo(final TbCsmrInfoDTO csmrInfo) {
		this.csmrInfo = csmrInfo;
	}

	public TbCfcApplicationMstEntity getCfcEntity() {
		return cfcEntity;
	}

	public void setCfcEntity(final TbCfcApplicationMstEntity cfcEntity) {
		this.cfcEntity = cfcEntity;
	}

	public CFCApplicationAddressEntity getCfcAddressEntity() {
		return cfcAddressEntity;
	}

	public void setCfcAddressEntity(final CFCApplicationAddressEntity cfcAddressEntity) {
		this.cfcAddressEntity = cfcAddressEntity;
	}

	public String getApplicantFullName() {
		return applicantFullName;
	}

	public void setApplicantFullName(final String applicantFullName) {
		this.applicantFullName = applicantFullName;
	}

	@Override
	public String getServiceName() {
		return serviceName;
	}

	@Override
	public void setServiceName(final String serviceName) {
		this.serviceName = serviceName;
	}

	public ScrutinyLableValueDTO getLableValueDTO() {
		return lableValueDTO;
	}

	public void setLableValueDTO(final ScrutinyLableValueDTO lableValueDTO) {
		this.lableValueDTO = lableValueDTO;
	}

	@Override
	public Long getServiceId() {
		return serviceId;
	}

	@Override
	public void setServiceId(final Long serviceId) {
		this.serviceId = serviceId;
	}

	/**
	 * @return the waterReconnection
	 */
	public TbWaterReconnection getWaterReconnection() {
		return waterReconnection;
	}

	/**
	 * @param waterReconnection
	 *            the waterReconnection to set
	 */
	public void setWaterReconnection(final TbWaterReconnection waterReconnection) {
		this.waterReconnection = waterReconnection;
	}

}
