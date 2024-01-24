package com.abm.mainet.securitymanagement.ui.model;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.securitymanagement.dto.ShiftMasterDTO;
import com.abm.mainet.securitymanagement.service.IShiftMasterService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class ShiftMasterModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	IShiftMasterService shiftService;

	private String saveMode;
	ShiftMasterDTO shiftMasterDTO = new ShiftMasterDTO();

	@Override
	public boolean saveForm() {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

		Employee employee = UserSession.getCurrent().getEmployee();
		if (getSaveMode().equals(MainetConstants.Legal.SaveMode.EDIT)) {
			shiftMasterDTO.setUpdatedBy(employee.getEmpId());
			shiftMasterDTO.setUpdatedDate(new Date());
			shiftMasterDTO.setLgIpMacUpd(employee.getEmppiservername());
			shiftService.saveOrUpdate(shiftMasterDTO);
			if (shiftMasterDTO.getCount() == 0) {
				setSuccessMessage(ApplicationSession.getInstance().getMessage("ShiftMasterDTO.shiftDataSave"));
			} 
			else if(shiftMasterDTO.getCrossDayFlag()!=null && shiftMasterDTO.getCrossDayFlag().equals("Y") ){
				setSuccessMessage(ApplicationSession.getInstance().getMessage("ShiftMasterDTO.shiftExistsCross"));
			}
			else if(shiftMasterDTO.getGeneralDayFlag()!=null && shiftMasterDTO.getGeneralDayFlag().equals("Y")) {
				setSuccessMessage(ApplicationSession.getInstance().getMessage("ShiftMasterDTO.shiftExistsGen"));
			}
			else {
				//RM-39313
				LookUp lokkup = null;
				if (shiftMasterDTO.getShiftId() != null) {
					lokkup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(shiftMasterDTO.getShiftId(), orgId, "SHT");
				}
				String ShiftdescEng=lokkup.getDescLangFirst();
				String ShiftdescReg=lokkup.getDescLangSecond();
				int LangId = UserSession.getCurrent().getLanguageId();
				if((LangId==1) && !(ShiftdescEng.isEmpty()) || ShiftdescEng!=null) {
					addValidationError(getAppSession().getMessage("ShiftMasterDTO.shiftExists"));
					return false;
					//setSuccessMessage(ShiftdescEng+" "+ApplicationSession.getInstance().getMessage("ShiftMasterDTO.shiftExists"));
				}else {
					addValidationError(getAppSession().getMessage("ShiftMasterDTO.shiftExists"));
					return false;
					//setSuccessMessage(ShiftdescReg+" "+ApplicationSession.getInstance().getMessage("ShiftMasterDTO.shiftExists"));
				}
				//RM-39313
			}
		} else {
			shiftMasterDTO.setOrgid(orgId);
			shiftMasterDTO.setCreatedBy(employee.getEmpId());
			shiftMasterDTO.setCreatedDate(new Date());
			shiftMasterDTO.setLgIpMac(employee.getEmppiservername());
			//RM-38980
			shiftMasterDTO.setStatus("A");
			//RM-38980
			shiftService.saveOrUpdate(shiftMasterDTO);
			if (shiftMasterDTO.getCount() == 0) {
				setSuccessMessage(ApplicationSession.getInstance().getMessage("ShiftMasterDTO.shiftDataSave"));
			} 
			else if(shiftMasterDTO.getCrossDayFlag()!=null && shiftMasterDTO.getCrossDayFlag().equals("Y")){
				setSuccessMessage(ApplicationSession.getInstance().getMessage("ShiftMasterDTO.shiftExistsCross"));
			}
			else if(shiftMasterDTO.getGeneralDayFlag()!=null && shiftMasterDTO.getGeneralDayFlag().equals("Y")) {
				setSuccessMessage(ApplicationSession.getInstance().getMessage("ShiftMasterDTO.shiftExistsGen"));
			}
			else {
				//RM-39313
				LookUp lokkup = null;
				if (shiftMasterDTO.getShiftId() != null) {
					lokkup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(shiftMasterDTO.getShiftId(), orgId, "SHT");
				}
				String ShiftdescEng=lokkup.getDescLangFirst();
				String ShiftdescReg=lokkup.getDescLangSecond();
				int LangId = UserSession.getCurrent().getLanguageId();
				if((LangId==1) && !(ShiftdescEng.isEmpty()) || ShiftdescEng!=null) {
					addValidationError(getAppSession().getMessage("ShiftMasterDTO.shiftExists"));
					return false;
					//setSuccessMessage(ShiftdescEng+" "+ApplicationSession.getInstance().getMessage("ShiftMasterDTO.shiftExists"));
				}else {
					addValidationError(getAppSession().getMessage("ShiftMasterDTO.shiftExists"));
					return false;
					//setSuccessMessage(ShiftdescReg+" "+ApplicationSession.getInstance().getMessage("ShiftMasterDTO.shiftExists"));
				}
				//RM-39313
			}
		}
		return true;
	}

	public ShiftMasterDTO getShiftMasterDTO() {
		return shiftMasterDTO;
	}

	public void setShiftMasterDTO(ShiftMasterDTO shiftMasterDTO) {
		this.shiftMasterDTO = shiftMasterDTO;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
