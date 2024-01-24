package com.abm.mainet.water.ui.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.scrutiny.dto.ScrutinyLableValueDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.domain.TbWaterReconnection;
import com.abm.mainet.water.domain.TbWtCsmrRoadTypes;
import com.abm.mainet.water.dto.RoadTypeDTO;
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
public class RoadDiggingConnectionFormModel extends AbstractFormModel {

	private static final long serialVersionUID = -8776011749650658114L;

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

	private List<RoadTypeDTO> tempList = new ArrayList<>();

	private String serviceShortCode;

	private Long applicationId;
	private List<RoadTypeDTO> reconnRoadDiggingDBList = new ArrayList<>();

	private TbWaterReconnection waterReconnection = new TbWaterReconnection();

	private Long csIdn;
	private String applicationDate;

	public void setConnectionDetailsInfo(final Long appId, final long serviceId, final long orgId) {
		setCfcAddressEntity(cfcService.getApplicantsDetails(appId));
		setCfcEntity(cfcService.getCFCApplicationByApplicationId(appId, orgId));
		final ServiceMaster serviceMaster = serviceMasterService.getServiceMaster(serviceId, orgId);
		setServiceName(serviceMaster.getSmServiceName());
		final TbCsmrInfoDTO master = waterCommonService.getApplicantInformationByAppId(appId, orgId,
				serviceMaster.getSmShortdesc());
		if (master != null) {
			setCsmrInfo(master);
			setApplicationDate(
					new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(getCfcEntity().getApmApplicationDate()));
			final TbCsmrInfoDTO csmrInfo = getCsmrInfo();
			for (final RoadTypeDTO dto : csmrInfo.getRoadList()) {
				if ((dto.getIsDeleted() != null) && !dto.getIsDeleted().equals(MainetConstants.MENU.Y)) {
					getTempList().add(dto);
				}
			}
			csmrInfo.setRoadList(getTempList());
			if (csmrInfo.getCsMname() != null) {
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
		}
	}

	public void setReconnectionRoadDiggingInfo(final Long appId, final Long serviceId, final Long orgId) {

		setCfcAddressEntity(cfcService.getApplicantsDetails(appId));
		setCfcEntity(cfcService.getCFCApplicationByApplicationId(appId, orgId));
		setApplicationDate(
				new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(getCfcEntity().getApmApplicationDate()));
		final ServiceMaster serviceMaster = serviceMasterService.getServiceMaster(serviceId, orgId);
		setServiceName(serviceMaster.getSmServiceName());
		waterReconnection = waterReconnectionService.getReconnectionDetails(applicationId, orgId);
		final TbKCsmrInfoMH connectionDetails = waterReconnectionService
				.getWaterConnectionDetailsById(waterReconnection.getCsIdn(), orgId);
		final TbCsmrInfoDTO infoDTO = getCsmrInfo();
		setCsIdn(connectionDetails.getCsIdn());
		if (connectionDetails.getCodDwzid1() != null) {
			infoDTO.setCodDwzid1(connectionDetails.getCodDwzid1());
		}
		if (connectionDetails.getCodDwzid2() != null) {
			infoDTO.setCodDwzid2(connectionDetails.getCodDwzid2());
		}
		if (connectionDetails.getCodDwzid3() != null) {
			infoDTO.setCodDwzid3(connectionDetails.getCodDwzid3());
		}
		if (connectionDetails.getCodDwzid4() != null) {
			infoDTO.setCodDwzid4(connectionDetails.getCodDwzid4());
		}
		if (connectionDetails.getCodDwzid5() != null) {
			infoDTO.setCodDwzid5(connectionDetails.getCodDwzid5());
		}
		infoDTO.setApplicationNo(getCfcAddressEntity().getApmApplicationId());
		if (getCfcEntity().getApmMname() != null) {
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

		final List<RoadTypeDTO> roadDiggingDBList = waterService.getReconnectionRoadDiggingListByAppId(appId, orgId);
		setReconnRoadDiggingDBList(roadDiggingDBList);
		for (final RoadTypeDTO dto : roadDiggingDBList) {
			if ((dto.getIsDeleted() != null) && !dto.getIsDeleted().equals(MainetConstants.MENU.Y)) {
				getTempList().add(dto);
			}
		}

		infoDTO.setRoadList(getTempList());

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
		final int langId = UserSession.getCurrent().getLanguageId();
		final Employee emp = UserSession.getCurrent().getEmployee();
		List<TbWtCsmrRoadTypes> existingRoadList = null;
		TbKCsmrInfoMH master = null;
		List<TbWtCsmrRoadTypes> updatedRoadList = null;
		if (MainetConstants.WaterServiceShortCode.WATER_RECONN.equals(getServiceShortCode())) {

			final List<TbWtCsmrRoadTypes> roadTypesEntityFrmDB = waterMapper
					.mapRoadTypeDTOToTbWtCsmrRoadTypesEntity(getReconnRoadDiggingDBList());
			existingRoadList = roadTypesEntityFrmDB;
			final List<RoadTypeDTO> roadTypeDTOs = csmrInfo.getRoadList();
			updatedRoadList = waterMapper.mapRoadTypeDTOToTbWtCsmrRoadTypesEntity(roadTypeDTOs);
			if ((updatedRoadList != null) && !updatedRoadList.isEmpty()) {

				for (final TbWtCsmrRoadTypes road : updatedRoadList) {
					master = new TbKCsmrInfoMH();
					master.setCsIdn(getCsIdn());
					road.setCrtCsIdn(master);
					road.setLangId(langId);
					road.setUserId(emp);
					road.setCrtGranted(MainetConstants.MENU.Y);
					road.setCrtLatest(MainetConstants.MENU.Y);
					road.setIsDeleted(MainetConstants.MENU.N);
					road.setLmodDate(new Date());
					road.setOrgId(UserSession.getCurrent().getOrganisation());
					road.setApmApplicationId(applicationId);
					road.setSmServiceId(serviceId);
				}
			}

			for (final TbWtCsmrRoadTypes roadType : existingRoadList) {

				if (!updatedRoadList.contains(roadType)) {
					roadType.setIsDeleted(MainetConstants.MENU.Y);
					roadType.setUpdatedBy(emp);
					roadType.setUpdatedDate(new Date());
					updatedRoadList.add(roadType);
				}
			}

		} else {
			master = waterMapper.mapTbKCsmrInfoDTOToTbKCsmrInfoEntity(csmrInfo);
			existingRoadList = waterService.getRoadListById(master);
			updatedRoadList = master.getRoadList();

			if ((master.getRoadList() != null) && !master.getRoadList().isEmpty()) {

				for (final TbWtCsmrRoadTypes road : master.getRoadList()) {
					road.setCrtCsIdn(master);
					road.setLangId(langId);
					road.setUserId(emp);
					road.setCrtGranted(MainetConstants.MENU.Y);
					road.setCrtLatest(MainetConstants.MENU.Y);
					road.setIsDeleted(MainetConstants.MENU.N);
					road.setLmodDate(new Date());
					road.setOrgId(UserSession.getCurrent().getOrganisation());
					road.setApmApplicationId(applicationId);
					road.setSmServiceId(serviceId);
				}
			}

			for (final TbWtCsmrRoadTypes roadType : existingRoadList) {

				if (!updatedRoadList.contains(roadType)) {
					roadType.setIsDeleted(MainetConstants.MENU.Y);
					roadType.setUpdatedBy(emp);
					roadType.setUpdatedDate(new Date());
					updatedRoadList.add(roadType);
				}
			}
		}

		if (MainetConstants.WaterServiceShortCode.WATER_RECONN.equals(getServiceShortCode())) {

			waterService.saveReconnectionRoadDiggingDetails(updatedRoadList, getLableValueDTO());

		} else {
			master.setRoadList(updatedRoadList);
			master.setApplicationNo(applicationId);
			setMasterUpdateFields(master);
			waterCommonService.updateCsmrInfo(master, getLableValueDTO());
		}

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

	public String getHasError() {
		return hasError;
	}

	public void setHasError(final String hasError) {
		this.hasError = hasError;
	}

	public boolean validateInputs() {
		final TbCsmrInfoDTO dto = getCsmrInfo();

		for (final RoadTypeDTO road : dto.getRoadList()) {
			if ((road.getCrtRoadTypes() != null) && (road.getCrtRoadTypes() == 0L)) {
				addValidationError(getAppSession().getMessage("road.type"));
			}
			if (road.getCrtRoadUnits() == null) {
				addValidationError(getAppSession().getMessage("road.length"));
			}
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
	 * @return the tempList
	 */
	public List<RoadTypeDTO> getTempList() {
		return tempList;
	}

	/**
	 * @param tempList
	 *            the tempList to set
	 */
	public void setTempList(final List<RoadTypeDTO> tempList) {
		this.tempList = tempList;
	}

	/**
	 * @return the serviceShortCode
	 */
	public String getServiceShortCode() {
		return serviceShortCode;
	}

	/**
	 * @param serviceShortCode
	 *            the serviceShortCode to set
	 */
	public void setServiceShortCode(final String serviceShortCode) {
		this.serviceShortCode = serviceShortCode;
	}

	/**
	 * @return the applicationId
	 */
	public Long getApplicationId() {
		return applicationId;
	}

	/**
	 * @param applicationId
	 *            the applicationId to set
	 */
	public void setApplicationId(final Long applicationId) {
		this.applicationId = applicationId;
	}

	/**
	 * @return the reconnRoadDiggingDBList
	 */
	public List<RoadTypeDTO> getReconnRoadDiggingDBList() {
		return reconnRoadDiggingDBList;
	}

	/**
	 * @param reconnRoadDiggingDBList
	 *            the reconnRoadDiggingDBList to set
	 */
	public void setReconnRoadDiggingDBList(final List<RoadTypeDTO> reconnRoadDiggingDBList) {
		this.reconnRoadDiggingDBList = reconnRoadDiggingDBList;
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

	/**
	 * @return the csIdn
	 */
	public Long getCsIdn() {
		return csIdn;
	}

	/**
	 * @param csIdn
	 *            the csIdn to set
	 */
	public void setCsIdn(final Long csIdn) {
		this.csIdn = csIdn;
	}

	public String getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}

}
