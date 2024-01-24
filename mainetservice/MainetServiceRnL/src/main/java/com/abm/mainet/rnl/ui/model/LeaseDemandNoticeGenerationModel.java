/**
 * 
 */
package com.abm.mainet.rnl.ui.model;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.objection.dto.NoticeMasterDto;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.rnl.dto.LeaseDemandNoticeGenerationDTO;
import com.abm.mainet.rnl.service.LeaseDemandNoticeGenerationService;
import com.aspose.slides.Collections.ArrayList;

/**
 * @author divya.marshettiwar
 *
 */
@Component
@Scope("session")
public class LeaseDemandNoticeGenerationModel extends AbstractFormModel{
	
	private static final long serialVersionUID = 1977595869988606954L;
	
	private NoticeMasterDto noticeDto = new NoticeMasterDto();
	
	private List<NoticeMasterDto> noticeDtoList = new ArrayList();
	
	private LeaseDemandNoticeGenerationDTO dto = new LeaseDemandNoticeGenerationDTO();
	private List<LeaseDemandNoticeGenerationDTO> demandDtoList = new ArrayList();
	
	@Resource
    private LeaseDemandNoticeGenerationService leaseDemandNoticeGenerationService;
	
	@Autowired
	private ServiceMasterService seviceMasterService;
	
	@Autowired
    private TbDepartmentService iTbDepartmentService;
	
	private ServiceMaster serviceMaster = new ServiceMaster();
	private List<TbLocationMas> locationList = null;
		
	@Override 
	protected void initializeModel() {
		initializeLookupFields(PrefixConstants.RnLPrefix.RNG);
	}
	
	@Override
	public boolean saveForm() {
		boolean status = false;
		final UserSession session = UserSession.getCurrent();

		final Department dept = iTbDepartmentService.findDepartmentByCode(MainetConstants.DEPT_SHORT_NAME.RNL);
		Long dpDeptId = dept.getDpDeptid();

		dto.setOrgId(session.getOrganisation().getOrgid());
		dto.setLgIpMacUpd(getClientIpAddress());
		dto.setCreatedDate(new Date());
		dto.setCreatedBy(session.getEmployee().getEmpId());
		dto.setUpdatedDate(new Date());
		dto.setUpdatedBy(session.getEmployee().getEmpId());
		dto.setLgIpMac(getClientIpAddress());
		dto.setLangId((long) session.getLanguageId());
		dto.setDeptId(dpDeptId);

		// validation
		/*
		 * if (dto.getNoticeTypeDesc().equals("M")) { if (dto.getNotTyp().equals(0L)) {
		 * addValidationError(getAppSession().getMessage("rnl.select")); return false; }
		 * } else { if (dto.getRefNo().equals("")) {
		 * addValidationError(getAppSession().getMessage("rnl.select.propertyNumber"));
		 * return false; } }
		 */

		// to generate notice number
		if (dto.getNoticeTypeDesc().equals("S")) {
			if ((dto.getNotTyp() == 0) && dto.getRefNo().equals("")) {
				addValidationError(getAppSession().getMessage("rnl.select.propertyNumberAndNoticeType"));
				return false;
			}else if(dto.getRefNo().equals("")) {
				addValidationError(getAppSession().getMessage("rnl.select.propertyNumber"));
				return false;
			}else if(dto.getNotTyp() == 0){
				addValidationError(getAppSession().getMessage("rnl.select.noticeType"));
				return false;
			}else {
				List<LeaseDemandNoticeGenerationDTO> dtoList = leaseDemandNoticeGenerationService
						.findPropertyNo(dto.getOrgId(), dto.getRefNo());
				if (dtoList.isEmpty()) {
					addValidationError(getAppSession().getMessage("rnl.noDues"));
					return false;
				} else {
					
					LookUp noticeTypeByLookup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(dto.getNotTyp(),
							dto.getOrgId(), "RNG");
					
					LeaseDemandNoticeGenerationDTO resultDto = new LeaseDemandNoticeGenerationDTO();
					
					LeaseDemandNoticeGenerationDTO propDto = leaseDemandNoticeGenerationService.findFirstReminderNotice(dto.getNotTyp(),
							dto.getRefNo(), dto.getOrgId());
					
					if (noticeTypeByLookup.getLookUpCode().equals("RRN1")) {						
						if(propDto.getRefNo() != null) {
							addValidationError(getAppSession().getMessage("rnl.firstrrn") + " " + dto.getRefNo()
							+ " " + ApplicationSession.getInstance().getMessage("rnl.alreadyGenerated"));
							return false;
						}else {												
							resultDto = leaseDemandNoticeGenerationService.saveNoticeForm(dto, UserSession.getCurrent().getOrganisation());
							setSuccessMessage(
									ApplicationSession.getInstance().getMessage("rnl.demandNoticeNumber") + " " + resultDto.getNotNo()
											+ " " + ApplicationSession.getInstance().getMessage("rnl.savedSuccessfully"));
							status = true;
						}
					}else {
						if(propDto.getRefNo() != null) {
							addValidationError(getAppSession().getMessage("rnl.secondrrn") + " " + dto.getRefNo()
							+ " " + ApplicationSession.getInstance().getMessage("rnl.alreadyGenerated"));
							return false;
						}else {	
						resultDto = leaseDemandNoticeGenerationService.saveNoticeForm(dto, UserSession.getCurrent().getOrganisation());
						setSuccessMessage(
								ApplicationSession.getInstance().getMessage("rnl.demandNoticeNumber") + " " + resultDto.getNotNo()
										+ " " + ApplicationSession.getInstance().getMessage("rnl.savedSuccessfully"));
						status = true;
						}
					}
					
					
				}
			}
		} else {

			LookUp noticeTypeByLookup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(dto.getNotTyp(),
					dto.getOrgId(), "RNG");

			if (noticeTypeByLookup.getLookUpCode().equals("RRN2")) {
				Long rrn1ByLookup = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("RRN1", "RNG", dto.getOrgId());

				List<LeaseDemandNoticeGenerationDTO> demandDtoList = leaseDemandNoticeGenerationService
						.findSecondReminderNotice(rrn1ByLookup, dto.getOrgId());
				
				setSuccessMessage(ApplicationSession.getInstance().getMessage("rnl.secondrrn") + " "
						+ ApplicationSession.getInstance().getMessage("rnl.generatedSuccessfully"));
				
				leaseDemandNoticeGenerationService.saveNoticeFormList(demandDtoList, dto.getNotTyp(), UserSession.getCurrent().getOrganisation());
			
			} else if(noticeTypeByLookup.getLookUpCode().equals("RRN1")) {

				List<LeaseDemandNoticeGenerationDTO> dtoListByLocation = leaseDemandNoticeGenerationService
						.findByLocationId(dto.getOrgId(), dto.getLocationId());
				
				List<LeaseDemandNoticeGenerationDTO> alreadyGenList = leaseDemandNoticeGenerationService
						.getfirstReminderNoticeByNoticeType(dto.getNotTyp(), dto.getOrgId());
				
				List<LeaseDemandNoticeGenerationDTO> rrn1dtoList = new ArrayList();
				dtoListByLocation.forEach(notGen -> {
					List<LeaseDemandNoticeGenerationDTO> existList = alreadyGenList.stream().filter( a->
						a.getRefNo().equals(notGen.getRefNo())).collect(Collectors.toList());
					if(existList.isEmpty()) {
						notGen.setOrgId(dto.getOrgId());
						notGen.setCreatedDate(dto.getCreatedDate());
						notGen.setLangId(dto.getLangId());
						notGen.setDeptId(dto.getDpDeptid());
						notGen.setUpdatedBy(dto.getUpdatedBy());
						notGen.setUpdatedDate(dto.getUpdatedDate());
						notGen.setLgIpMac(dto.getLgIpMac());
						notGen.setLgIpMacUpd(dto.getLgIpMacUpd());
						rrn1dtoList.add(notGen);
					}
				});
				
				if(rrn1dtoList.isEmpty()) {
					addValidationError(ApplicationSession.getInstance().getMessage("rnl.alreadyGenerated"));
					return false;
				}

				setSuccessMessage(ApplicationSession.getInstance().getMessage("rnl.firstrrn") + " "
						+ ApplicationSession.getInstance().getMessage("rnl.generatedSuccessfully"));

				leaseDemandNoticeGenerationService.saveNoticeFormList(rrn1dtoList, dto.getNotTyp(), UserSession.getCurrent().getOrganisation());
			}
			status = true;
		}
		return status;
	}
	
	public NoticeMasterDto getNoticeDto() {
		return noticeDto;
	}

	public void setNoticeDto(NoticeMasterDto noticeDto) {
		this.noticeDto = noticeDto;
	}

	public List<NoticeMasterDto> getNoticeDtoList() {
		return noticeDtoList;
	}

	public void setNoticeDtoList(List<NoticeMasterDto> noticeDtoList) {
		this.noticeDtoList = noticeDtoList;
	}

	public LeaseDemandNoticeGenerationDTO getDto() {
		return dto;
	}

	public void setDto(LeaseDemandNoticeGenerationDTO dto) {
		this.dto = dto;
	}

	public LeaseDemandNoticeGenerationService getLeaseDemandNoticeGenerationService() {
		return leaseDemandNoticeGenerationService;
	}

	public void setLeaseDemandNoticeGenerationService(
			LeaseDemandNoticeGenerationService leaseDemandNoticeGenerationService) {
		this.leaseDemandNoticeGenerationService = leaseDemandNoticeGenerationService;
	}

	public ServiceMasterService getSeviceMasterService() {
		return seviceMasterService;
	}

	public void setSeviceMasterService(ServiceMasterService seviceMasterService) {
		this.seviceMasterService = seviceMasterService;
	}

	public ServiceMaster getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(ServiceMaster serviceMaster) {
		this.serviceMaster = serviceMaster;
	}

	public List<TbLocationMas> getLocationList() {
		return locationList;
	}

	public void setLocationList(List<TbLocationMas> locationList) {
		this.locationList = locationList;
	}

	public List<LeaseDemandNoticeGenerationDTO> getDemandDtoList() {
		return demandDtoList;
	}

	public void setDemandDtoList(List<LeaseDemandNoticeGenerationDTO> demandDtoList) {
		this.demandDtoList = demandDtoList;
	}

	public TbDepartmentService getiTbDepartmentService() {
		return iTbDepartmentService;
	}

	public void setiTbDepartmentService(TbDepartmentService iTbDepartmentService) {
		this.iTbDepartmentService = iTbDepartmentService;
	}
	
	
}
