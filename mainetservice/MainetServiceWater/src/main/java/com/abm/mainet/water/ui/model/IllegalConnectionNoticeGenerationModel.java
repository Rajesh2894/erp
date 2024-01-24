/**
 * 
 */
package com.abm.mainet.water.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.water.domain.PlumberMaster;
import com.abm.mainet.water.dto.NewWaterConnectionReqDTO;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.WaterDataEntrySearchDTO;
import com.abm.mainet.water.service.NewWaterConnectionService;
import com.abm.mainet.water.ui.validator.IllegalConnectionNoticeGenerationValidator;

/**
 * @author Saiprasad.Vengurlekar
 *
 */

@Component
@Scope("session")
public class IllegalConnectionNoticeGenerationModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8308006467803350004L;

	@Autowired
	private NewWaterConnectionService newWaterConnectionService;

	private TbCsmrInfoDTO csmrInfo = new TbCsmrInfoDTO();
	private List<TbCsmrInfoDTO> noticeList = new ArrayList<>();
	WaterDataEntrySearchDTO searchDTO = new WaterDataEntrySearchDTO();
	private NewWaterConnectionReqDTO reqDTO = new NewWaterConnectionReqDTO();
	private String saveMode;
	public List<PlumberMaster> plumberList = new ArrayList<>();
	public List<PlumberMaster> ulbPlumberList = new ArrayList<>();
	private String propOutStanding;

	@Override
	public boolean saveForm() {
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long empId = UserSession.getCurrent().getEmployee().getEmpId();
		Date todayDate = new Date();
		TbCsmrInfoDTO infoDto = getCsmrInfo();

		if (infoDto.getUserId() == null) {
			infoDto.setCsIllegalNoticeDate(todayDate);
			String noticeNo = ApplicationContextProvider.getApplicationContext()
					.getBean(NewWaterConnectionService.class).generateIllegalConnectionNoticeNumber(orgId);
			if (noticeNo != null)
				infoDto.setCsIllegalNoticeNo(noticeNo);
			else {
				addValidationError("Error in Generating Illegal Notice Number");
				return false;
			}
			infoDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			infoDto.setUserId(empId);
			infoDto.setLmodDate(todayDate);
			infoDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());

		} else {
			infoDto.setUpdatedBy(empId);
			infoDto.setUpdatedDate(todayDate);
			infoDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
		}

		validateBean(this, IllegalConnectionNoticeGenerationValidator.class);
		if (hasValidationErrors()) {
			return false;
		}

		if (saveMode.equals(MainetConstants.MODE_CREATE)) {
			ApplicationContextProvider.getApplicationContext().getBean(NewWaterConnectionService.class)
					.saveIllegalConnectionNoticeGeneration(infoDto);
			setSuccessMessage(ApplicationSession.getInstance().getMessage("water.saved.msg"));
		} else {
			ApplicationContextProvider.getApplicationContext().getBean(NewWaterConnectionService.class)
					.saveIllegalConnectionNoticeGeneration(infoDto);
			setSuccessMessage(ApplicationSession.getInstance().getMessage("water.saved.msg"));
		}

		return true;
	}

	public TbCsmrInfoDTO getPropertyDetailsByPropertyNumber(NewWaterConnectionReqDTO requestDTO) {
		return newWaterConnectionService.getPropertyDetailsByPropertyNumber(requestDTO);
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public TbCsmrInfoDTO getCsmrInfo() {
		return csmrInfo;
	}

	public void setCsmrInfo(TbCsmrInfoDTO csmrInfo) {
		this.csmrInfo = csmrInfo;
	}

	public List<PlumberMaster> getPlumberList() {
		return plumberList;
	}

	public void setPlumberList(List<PlumberMaster> plumberList) {
		this.plumberList = plumberList;
	}

	public List<PlumberMaster> getUlbPlumberList() {
		return ulbPlumberList;
	}

	public void setUlbPlumberList(List<PlumberMaster> ulbPlumberList) {
		this.ulbPlumberList = ulbPlumberList;
	}

	public String getPropOutStanding() {
		return propOutStanding;
	}

	public void setPropOutStanding(String propOutStanding) {
		this.propOutStanding = propOutStanding;
	}

	public NewWaterConnectionReqDTO getReqDTO() {
		return reqDTO;
	}

	public void setReqDTO(NewWaterConnectionReqDTO reqDTO) {
		this.reqDTO = reqDTO;
	}

	public List<TbCsmrInfoDTO> getNoticeList() {
		return noticeList;
	}

	public void setNoticeList(List<TbCsmrInfoDTO> noticeList) {
		this.noticeList = noticeList;
	}

	public WaterDataEntrySearchDTO getSearchDTO() {
		return searchDTO;
	}

	public void setSearchDTO(WaterDataEntrySearchDTO searchDTO) {
		this.searchDTO = searchDTO;
	}

}
