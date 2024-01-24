package com.abm.mainet.firemanagement.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.firemanagement.Constants.Constants;
import com.abm.mainet.firemanagement.dto.FireCallRegisterDTO;
import com.abm.mainet.firemanagement.service.IFireCallRegisterService;
import com.abm.mainet.firemanagement.ui.validator.FireCallRegisterValidator;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
//import com.abm.mainet.vehicle.management.domain.FmVehicleMaster;
import com.abm.mainet.vehicle.management.dto.GenVehicleMasterDTO;
//import com.abm.mainet.vehicle.management.repository.GenVehicleMasterRepository;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class FireCallRegisterModel extends AbstractFormModel {

	private static final long serialVersionUID = 3108541764227266478L;

	@Autowired
	private IFireCallRegisterService fireCallRegisterService;

	@Autowired
	private IEmployeeService iEmployeeService;

	private FireCallRegisterDTO entity = new FireCallRegisterDTO();

	@Autowired
	private DepartmentService departmentService;
	//@Autowired
	//private GenVehicleMasterRepository vehicleMasterRepository;
	private String saveMode;

	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	private List<AttachDocs> attachDocsList = new ArrayList<>();
	
	@Autowired
    private IAttachDocsService iAttachDocsService;

	/**
	 * @return the entity
	 */
	public FireCallRegisterDTO getEntity() {
		return entity;
	}

	/**
	 * @param entity
	 *            the entity to set
	 */
	public void setEntity(FireCallRegisterDTO entity) {
		this.entity = entity;
	}

	/**
	 * @return the saveMode
	 */
	public String getSaveMode() {
		return saveMode;
	}

	/**
	 * @param saveMode
	 *            the saveMode to set
	 */
	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	/**
	 * @return the attachments
	 */
	public List<DocumentDetailsVO> getAttachments() {
		return attachments;
	}

	/**
	 * @param attachments
	 *            the attachments to set
	 */
	public void setAttachments(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
	}

	/**
	 * @return the attachDocsList
	 */
	public List<AttachDocs> getAttachDocsList() {
		return attachDocsList;
	}

	/**
	 * @param attachDocsList
	 *            the attachDocsList to set
	 */
	public void setAttachDocsList(List<AttachDocs> attachDocsList) {
		this.attachDocsList = attachDocsList;
	}

	@Override
	public boolean saveForm() {
		validateBean(entity, FireCallRegisterValidator.class);
		if (this.hasValidationErrors()) {
			return false;
		}
	
	//entity.setVehicleInTime((entity.getVehicleInTime().equals("")) ?null : entity.getVehicleInTime());
	entity.setVehicleOutTime((entity.getVehicleOutTime().equals("")) ?null :entity.getVehicleOutTime());

		String lgIpMac = UserSession.getCurrent().getEmployee().getEmppiservername();
		long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

		if (entity.getCmplntId() != null) {
			entity.setOrgid(orgId);
			entity.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			entity.setUpdatedDate(new Date());
			entity.setLgIpMacUpd(lgIpMac);

			if (entity.getStatusFlag().equals(Constants.DRAFT_STATUS)) {
				entity.setComplaintStatus(Constants.DRAFT_STATUS);
			} else {
				entity.setComplaintStatus(entity.getStatusFlag());
			}
			prepareContractDocumentsData(entity);
			fireCallRegisterService.save(entity);
			if (entity.getStatusFlag().equals(Constants.DRAFT_STATUS)) {
				setSuccessMessage(getAppSession().getMessage("FireCallRegisterDTO.form.saveasdraft" + "",
						new Object[] { entity.getCmplntNo() }));
			} else {
				setSuccessMessage(getAppSession().getMessage("FireCallRegisterDTO.form.save" + "",
						new Object[] { entity.getCmplntNo() }));
			}

		} else {

			ServiceMaster service = ApplicationContextProvider.getApplicationContext()
					.getBean(ServiceMasterService.class)
					.getServiceMasterByShortCode(Constants.FIRE_SERVICE_CODE, orgId);

			FinancialYear financiaYear = ApplicationContextProvider.getApplicationContext()
					.getBean(TbFinancialyearService.class).getFinanciaYearByDate(new Date());
			String deptCode = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
					.getDeptCode(service.getTbDepartment().getDpDeptid());
			String finacialYear = Utility.getFinancialYear(financiaYear.getFaFromDate(), financiaYear.getFaToDate());
			final Long sequence = ApplicationContextProvider.getApplicationContext()
					.getBean(SeqGenFunctionUtility.class).generateSequenceNo(Constants.FIRE_DRPT_CODE,
							Constants.FIRE_CALL_REG_TABLE, Constants.FIRE_CALL_REG_TABLE_COLUMN, orgId,
							MainetConstants.FlagC, financiaYear.getFaYear());
			String complainNo = deptCode + MainetConstants.WINDOWS_SLASH + finacialYear + MainetConstants.WINDOWS_SLASH
					+ String.format(MainetConstants.WorksManagement.FOUR_PERCENTILE, sequence);

			entity.setCmplntNo(complainNo);
			entity.setCreatedBy(UserSession.getCurrent().getEmployee().getUserId());
			entity.setCreatedDate(new Date());

			if (entity.getStatusFlag().equals(Constants.DRAFT_STATUS)) {
				entity.setComplaintStatus(entity.getStatusFlag());
			} else {
				entity.setComplaintStatus(entity.getStatusFlag());
			}
			entity.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
			entity.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());

			prepareContractDocumentsData(entity);
			fireCallRegisterService.save(entity);
			if (entity.getStatusFlag().equals(Constants.DRAFT_STATUS)) {
				setSuccessMessage(getAppSession().getMessage("FireCallRegisterDTO.form.saveasdraft" + "",
						new Object[] { entity.getCmplntNo() }));
			} else {
				setSuccessMessage(getAppSession().getMessage("FireCallRegisterDTO.form.save" + "",
						new Object[] { entity.getCmplntNo() }));
			}

		}

		// Code For SMS-EMAIL Start
		if (entity.getStatusFlag().equals(Constants.OPEN_STATUS)) {
			String[] parts = getEntity().getCpdFireStation().split(",");
			String fireStation = "";
			for (int i = 0; i < parts.length; i++) {
				String call = CommonMasterUtility.getCPDDescription(Long.parseLong(parts[i]), MainetConstants.BLANK);
				fireStation = fireStation + " " + call;
			}
			
			
			int langId = UserSession.getCurrent().getLanguageId();
			Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
			Long depId = departmentService.getDepartmentIdByDeptCode(Constants.FM);
			Organisation organisation = UserSession.getCurrent().getOrganisation();
			GenVehicleMasterDTO vehicle=null;
			//List<Long> employeIds = fireCallRegisterService.getEmpId(depId, orgid);
			StringBuffer  builder=new StringBuffer ();
			builder.append(entity.getCallerMobileNo());
			/*
			 * String mobileList = iEmployeeService.getEmpDetailListByEmpIdList(employeIds,
			 * orgid).stream() .map(Employee::getEmpmobno).collect(Collectors.joining(","));
			 */
			Employee empData=iEmployeeService.findEmployeeByIdAndOrgId(Long.valueOf(getEntity().getDutyOfficer()),orgid);
			String mobileList=empData.getEmpmobno();
			builder.append(MainetConstants.operator.COMMA+mobileList);
			String[] ids=builder.toString().split(MainetConstants.operator.COMMA);
			
			if (entity.getAssignVehicle() != null) {
				// FmVehicleMaster vehicle =
				// vehicleMasterRepository.findOne(entity.getAssignVehicle());
				vehicle = fireCallRegisterService.getVehicleByVehicleMasterId(entity.getAssignVehicle(), orgId);
			}
			
			for (String id : ids) {
				
				SMSAndEmailDTO dto = new SMSAndEmailDTO();
				
				if (vehicle != null)
					dto.setAppName(vehicle.getVeNo());
				else {
					dto.setAppName(MainetConstants.CommonConstants.NA);
				}

				dto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
				dto.setAppNo(getEntity().getCmplntNo());
				dto.setAppDate(Utility.dateToString(getEntity().getDate()));
				dto.setCc(getEntity().getTime());
				dto.setType(fireStation);
				dto.setCaseID(getEntity().getIncidentLocation());
				// dto.setAppName(vehicle.getVeNo());
				dto.setUserId(getUserSession().getEmployee().getEmpId());
				dto.setMobnumber(id);
				if(id.equals(mobileList) && StringUtils.isNotBlank(empData.getEmpemail()))
				dto.setEmail(empData.getEmpemail());
				ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
						Constants.FM, "FireCallRegister.html", PrefixConstants.SMS_EMAIL_ALERT_TYPE.GENERAL_MSG, dto,
						organisation, langId);
			} 
		}
		// Code For SMS-EMAIL End

		return true;
	}

	public void prepareContractDocumentsData(FireCallRegisterDTO entity) {
		RequestDTO requestDTO = new RequestDTO();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		requestDTO.setOrgId(orgId);
		requestDTO.setStatus(MainetConstants.FlagA);

		requestDTO.setDepartmentName(Constants.FIRE_DRPT_CODE);

		requestDTO.setIdfId(Constants.FIRE_CALL_REG_TABLE + MainetConstants.WINDOWS_SLASH + entity.getCmplntNo());
		requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());

		setCommonFileAttachment(ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
		//if (getCommonFileAttachment().size() > Constants.FIRE_ZERO) 
		//	ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).doMasterFileUpload(getCommonFileAttachment(), requestDTO);
		
		List<AttachDocs> ListAtt = iAttachDocsService.findByCode(requestDTO.getOrgId(), requestDTO.getIdfId());
		if(ListAtt.isEmpty()) {
			ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).doMasterFileUpload(getCommonFileAttachment(), requestDTO);
		}
		else {
			for(AttachDocs attDocs: ListAtt) {
				if(requestDTO.getIdfId().equals(attDocs.getIdfId())) {
					//if (entity.getSaveMode().equals("E")){
					if(!getCommonFileAttachment().isEmpty()) {
						getCommonFileAttachment().get(0).setAttachmentId(attDocs.getAttId());
						ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).doMasterFileUpload(getCommonFileAttachment(), requestDTO);
					//}
					}
				}
			}
		}
		
		
		
	}

}
