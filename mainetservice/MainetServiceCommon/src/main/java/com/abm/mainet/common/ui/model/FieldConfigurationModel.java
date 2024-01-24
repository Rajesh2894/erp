package com.abm.mainet.common.ui.model;

import java.util.Date;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.dto.TbResourceDetDto;
import com.abm.mainet.common.dto.TbResourceMasDto;
import com.abm.mainet.common.service.IResourceService;

import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author sadik.shaikh
 *
 */
@Component
@Scope("session")
public class FieldConfigurationModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4164137663253487386L;

	@Autowired
	private IResourceService resourceService;

	private String saveMode;

	private String flag;

	private String formMode;

	private List<TbResourceMasDto> masDtos;

	private TbResourceMasDto resourceMasDto;

	private List<TbResourceDetDto> detDtos;

	private TbResourceDetDto resourceDetDto;

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public List<TbResourceDetDto> getDetDtos() {
		return detDtos;
	}

	public void setDetDtos(List<TbResourceDetDto> detDtos) {
		this.detDtos = detDtos;
	}

	public TbResourceMasDto getResourceMasDto() {
		return resourceMasDto;
	}

	public void setResourceMasDto(TbResourceMasDto resourceMasDto) {
		this.resourceMasDto = resourceMasDto;
	}

	public TbResourceDetDto getResourceDetDto() {
		return resourceDetDto;
	}

	public void setResourceDetDto(TbResourceDetDto resourceDetDto) {
		this.resourceDetDto = resourceDetDto;
	}

	public List<TbResourceMasDto> getMasDtos() {
		return masDtos;
	}

	public void setMasDtos(List<TbResourceMasDto> masDtos) {
		this.masDtos = masDtos;
	}

	public String getFormMode() {
		return formMode;
	}

	public void setFormMode(String formMode) {
		this.formMode = formMode;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	@Override
	public boolean saveForm() {

		Long resId = this.getResourceMasDto().getResId();

		if (resId == null) {
			resourceMasDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
			resourceMasDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			resourceMasDto.setCreatedDate(new Date());
			resourceMasDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());

			for (TbResourceDetDto configDetDTO : resourceMasDto.getFieldDetails()) {
				if (configDetDTO.getFieldId() == null) {
					configDetDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
					configDetDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
					configDetDTO.setLmodDate(new Date());
					configDetDTO.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());

				}
			}

			resourceService.saveTbResourceMas(resourceMasDto);
			setSuccessMessage(ApplicationSession.getInstance().getMessage("Field Configuration updated sucessfully"));
		} else {
			resourceMasDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			resourceMasDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			resourceMasDto.setUpdatedDate(new Date());
			resourceMasDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());

			for (TbResourceDetDto configDetDTO : resourceMasDto.getFieldDetails()) {

				if (configDetDTO.getFieldId() != null) {
					configDetDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
					configDetDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
					configDetDTO.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
					configDetDTO.setUpdatedDate(new Date());
					configDetDTO.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
				}

			}
			resourceService.saveTbResourceMas(resourceMasDto);
			setSuccessMessage(ApplicationSession.getInstance().getMessage("Field Configuration updated sucessfully"));
		}
		this.getAppSession().updateResourceCache(resourceMasDto.getPageId());

		return true;

	}

}
