package com.abm.mainet.sfac.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.repository.EmployeeJpaRepository;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.sfac.domain.CBBOMasterEntity;
import com.abm.mainet.sfac.domain.CircularNotificationMasterEntity;
import com.abm.mainet.sfac.domain.FPOMasterEntity;
import com.abm.mainet.sfac.domain.IAMasterEntity;
import com.abm.mainet.sfac.dto.CircularNotificationMasterDto;
import com.abm.mainet.sfac.dto.CircularNotiicationDetDto;
import com.abm.mainet.sfac.repository.CBBOMasterRepository;
import com.abm.mainet.sfac.repository.CircularNotificationMasterRepository;
import com.abm.mainet.sfac.repository.FPOMasterRepository;
import com.abm.mainet.sfac.repository.IAMasterRepository;
import com.abm.mainet.smsemail.dto.EMail;
import com.abm.mainet.smsemail.service.IEmailService;
@Service
public class CircularNotificationBackgroundService implements Runnable {

	RequestDTO requestDTO = new RequestDTO();

	List<DocumentDetailsVO> attach = new ArrayList<>();

	private String filenetPath;

	CircularNotificationMasterDto dto = new CircularNotificationMasterDto();

	IEmailService emailService;

	CBBOMasterRepository cbboMasterRepository  ;

	FPOMasterRepository fpoMasterRepository;

	IAMasterRepository iaMasterrepository;

	EmployeeJpaRepository employeeJpaRepo;

	IOrganisationService orgService;

	CircularNotificationMasterRepository circularNotificationMasterRepository;



	private String masterDirStructure(final Long orgId, final String idfId, final String departmentName) {
		final StringBuilder builder = new StringBuilder();

		builder.append(orgId).append(File.separator).append(departmentName).append(File.separator)
		.append(Utility.dateToString(new Date(), MainetConstants.DATE_FORMAT_UPLOAD)).append(File.separator)
		.append(idfId);

		return builder.toString();
	}

	@Transactional
	public void sendEmails(CircularNotiicationDetDto dto, CircularNotificationMasterDto mastDto, List<File> files) {

		List<CBBOMasterEntity> cbboMasterEntities = new ArrayList<>();
		List<FPOMasterEntity> fpoMasterEntities = new ArrayList<>();
		List<IAMasterEntity> iaMasterEntities = new ArrayList<>();

		if(dto.getSdb1()!=null && dto.getSdb1()!=0 && dto.getSdb2()!=null && dto.getSdb2()!=0) {
			fpoMasterEntities = fpoMasterRepository.findByOrgIdAndSdb1AndSdb2(dto.getOrgType(),dto.getSdb1(), dto.getSdb2());
		}
		else if(dto.getSdb1()!=null && dto.getSdb1()!=0) {
			cbboMasterEntities = cbboMasterRepository.findByOrgIdAndSdb1(dto.getOrgType(), dto.getSdb1());
			iaMasterEntities = iaMasterrepository.findByOrgId(dto.getOrgType());
			fpoMasterEntities = fpoMasterRepository.findByOrgIdAndSdb1(dto.getOrgType(), dto.getSdb1());
		}
		else if(dto.getSdb2()!=null && dto.getSdb2()!=0) {
			fpoMasterEntities = fpoMasterRepository.findByOrgIdAndSdb2(dto.getOrgType(), dto.getSdb2());
		}

		else {
			try {
				cbboMasterEntities = cbboMasterRepository.findByOrgId(dto.getOrgType());
			}catch (Exception e) {
				// TODO: handle exception
			}
			try {
				fpoMasterEntities = fpoMasterRepository.findByOrgId(dto.getOrgType());
			}catch (Exception e) {
				// TODO: handle exception
			}
			try {
				iaMasterEntities = iaMasterrepository.findByOrgId(dto.getOrgType());
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		if(cbboMasterEntities.size()>0) {
			for(CBBOMasterEntity cbboMasterEntity : cbboMasterEntities) {
				Organisation org = orgService.getOrganisationById(cbboMasterEntity.getOrgId());
				Employee employee = null;
				try {
					employee =	employeeJpaRepo.findByMasIdAndOrganisationAndEmpemailNotNull(cbboMasterEntity.getCbboId(),org);
				}catch (Exception e) {
					// TODO: handle exception
				}
				if(employee!=null && employee.getEmpemail()!=null) {
					String[] scripts = new String [] {employee.getEmpemail()};
					EMail email = new EMail();

					email.setTo(scripts);
					email.setSubject(mastDto.getCircularTitle());
					email.setMessage(doHtmlMergedData(mastDto.getEmailBody(), null, org.getOrgShortNm(), org.getOrgid(), true));


					email.setFiles(files);
					emailService.sendHTMLEmail(email, dto.getOrgId());
				}
			}
		}
		if(fpoMasterEntities.size()>0) {
			for(FPOMasterEntity fpoMasterEntity : fpoMasterEntities) {
				Organisation org = orgService.getOrganisationById(fpoMasterEntity.getOrgId());
				Employee employee = null;
				try {
					employee =	employeeJpaRepo.findByMasIdAndOrganisationAndEmpemailNotNull(fpoMasterEntity.getFpoId(),org);
				}catch (Exception e) {
					// TODO: handle exception
				}
				if(employee!=null && employee.getEmpemail()!=null) {
					String[] scripts = new String [] {employee.getEmpemail()};
					EMail email = new EMail();

					email.setTo(scripts);
					email.setSubject(mastDto.getCircularTitle());
					email.setMessage(doHtmlMergedData(mastDto.getEmailBody(), null, org.getOrgShortNm(), org.getOrgid(), true));



					email.setFiles(files);
					emailService.sendHTMLEmail(email, dto.getOrgId());
				}
			}
		}
		if(iaMasterEntities.size()>0) {
			for(IAMasterEntity iaMasterEntity : iaMasterEntities) {
				Organisation org = orgService.getOrganisationById(iaMasterEntity.getOrgId());
				Employee employee = null;
				try {
					employee =	employeeJpaRepo.findByMasIdAndOrganisationAndEmpemailNotNull(iaMasterEntity.getIAId(),org);
				}catch (Exception e) {
					// TODO: handle exception
				}
				if(employee!=null && employee.getEmpemail()!=null && !employee.getEmpemail().isEmpty()) {
					String[] scripts = new String [] {employee.getEmpemail()};
					EMail email = new EMail();

					email.setTo(scripts);
					email.setSubject(mastDto.getCircularTitle());
					email.setMessage(doHtmlMergedData(mastDto.getEmailBody(), null, org.getOrgShortNm(), org.getOrgid(), true));

					email.setFiles(files);
					emailService.sendHTMLEmail(email, dto.getOrgId());
				}

			}
		}
		try {
			CircularNotificationMasterEntity circularNotificationMasterEntity = circularNotificationMasterRepository.findOne(mastDto.getCnId());
			if(circularNotificationMasterEntity!=null) {
				circularNotificationMasterEntity.setStatus(MainetConstants.COMMON_STATUS.SUCCESS);
				circularNotificationMasterRepository.saveAndFlush(circularNotificationMasterEntity);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

	}


	@Override
	public void run() {

		List<File> files = new ArrayList<>();


		if(attach.size()>0) {
			final String masterDirPath = masterDirStructure(requestDTO.getOrgId(), requestDTO.getIdfId(),
					requestDTO.getDepartmentName());
			final String completeDirpath = filenetPath + MainetConstants.FILE_PATH_SEPARATOR + masterDirPath;
			final String completPath = completeDirpath + MainetConstants.FILE_PATH_SEPARATOR + attach.get(0).getDocumentName();
			File file = new File(completPath);
			files.add(file);
		}
		dto.getCircularNotiicationDetDtos().forEach(dtos -> {
			sendEmails(dtos, dto, files);

		});

	}

	public String getFilenetPath() {
		return filenetPath;
	}

	public void setFilenetPath(String filenetPath) {
		this.filenetPath = filenetPath;
	}

	public CircularNotificationMasterDto getDto() {
		return dto;
	}

	public void setDto(CircularNotificationMasterDto dto) {
		this.dto = dto;
	}

	public RequestDTO getRequestDTO() {
		return requestDTO;
	}

	public void setRequestDTO(RequestDTO requestDTO) {
		this.requestDTO = requestDTO;
	}

	public List<DocumentDetailsVO> getAttach() {
		return attach;
	}

	public void setAttach(List<DocumentDetailsVO> attach) {
		this.attach = attach;
	}

	public IEmailService getEmailService() {
		return emailService;
	}

	public void setEmailService(IEmailService emailService) {
		this.emailService = emailService;
	}

	public CBBOMasterRepository getCbboMasterRepository() {
		return cbboMasterRepository;
	}

	public void setCbboMasterRepository(CBBOMasterRepository cbboMasterRepository) {
		this.cbboMasterRepository = cbboMasterRepository;
	}

	public FPOMasterRepository getFpoMasterRepository() {
		return fpoMasterRepository;
	}

	public void setFpoMasterRepository(FPOMasterRepository fpoMasterRepository) {
		this.fpoMasterRepository = fpoMasterRepository;
	}

	public IAMasterRepository getIaMasterrepository() {
		return iaMasterrepository;
	}

	public void setIaMasterrepository(IAMasterRepository iaMasterrepository) {
		this.iaMasterrepository = iaMasterrepository;
	}

	public EmployeeJpaRepository getEmployeeJpaRepo() {
		return employeeJpaRepo;
	}

	public void setEmployeeJpaRepo(EmployeeJpaRepository employeeJpaRepo) {
		this.employeeJpaRepo = employeeJpaRepo;
	}

	public IOrganisationService getOrgService() {
		return orgService;
	}

	public void setOrgService(IOrganisationService orgService) {
		this.orgService = orgService;
	}

	public CircularNotificationMasterRepository getCircularNotificationMasterRepository() {
		return circularNotificationMasterRepository;
	}

	public void setCircularNotificationMasterRepository(
			CircularNotificationMasterRepository circularNotificationMasterRepository) {
		this.circularNotificationMasterRepository = circularNotificationMasterRepository;
	}
	
	private String doHtmlMergedData(final String template, final String userName, final String orgName,
			final long orgId, Boolean customMsg) {
		final ApplicationSession session = ApplicationSession.getInstance();
		final StringBuilder builder = new StringBuilder();

		builder.append(
				"<!doctype html><html><head><meta charset=\"utf-8\" accept-charset=\"UTF-8\"><title>EMAIL NOTIFICATION</title></head><body>");
		builder.append(
				"<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: collapse;background-color: #fff;\"><tr> <td align=\"center\" valign=\"top\">");
		builder.append(
				"<tr><td valign=\"top\"></td></tr></table><table align=\"center\" width=\"600\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: collapse;\"><tr>");

		builder.append(
				"<td width=\"50\" valign=\"top\" style=\"background-color: #f0f0f0;\"><table align=\"left\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: collapse;border: none;mso-table-lspace: 0pt;mso-table-rspace: 0pt;\"><tr>");

		builder.append("<td style=\"border:5px solid #F0F0F0;\">&nbsp;</td></tr> </table>");

		builder.append(
				"</td> <td width=\"35\" style=\"background-color: #f0f0f0;\"></td><td width=\"365\" valign=\"top\" style=\"background-color: #f0f0f0;\"><table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: collapse;\"><tr><td valign=\"top\">");
		
			builder.append(
					"</td></tr><tr> <td align=\"center\" mc:edit=\"title1\" style=\"font-weight: 400;color: #20394d;font-family: \'Raleway\',Tahoma,Arial;font-size: 16px;line-height: 20px;text-transform: uppercase;padding-right: 30px;\"> <singleline label=\"title1\">"
							+ session.getMessage("smsEmail.welcome") + "</singleline> </td> </tr>");
		
		builder.append(
				"<tr><td height=\"20\"></td></tr> <tr><td align=\"left\" mc:edit=\"title2\" style=\"font-weight:400;color: #20394d;font-family: \'Raleway\',Tahoma,Arial;font-size: 14px;line-height: 24px;padding-right: 30px;\"><singleline label=\"title2\">");
		// code added by ISRAT at 30/07/2019 because in my case Dear STMT. not want
		if (!customMsg) {
			if (userName != null) {
				// #129079 to get content in both eng and reg language
				if (session.getLangId() == MainetConstants.DEFAULT_LANGUAGE_ID)
					builder.append(session.getMessage("smsEmail.dear", 
							"smsEmail.dear",new Locale(MainetConstants.REG_ENG.ENGLISH))).append(" ").append(userName)
					.append(",</singleline> </td> </tr>");
				else
					builder.append(session.getMessage("smsEmail.dear", 
							"smsEmail.dear",new Locale(MainetConstants.REG_ENG.REGIONAL))).append(" ").append(userName)
					.append(",</singleline> </td> </tr>");

			} else {
				if (session.getLangId() == MainetConstants.DEFAULT_LANGUAGE_ID)
					builder.append(session.getMessage("smsEmail.dearApplicant", 
							"smsEmail.dearApplicant",new Locale(MainetConstants.REG_ENG.ENGLISH)))
					.append(",</singleline> </td> </tr>");
				else
					builder.append(session.getMessage("smsEmail.dearApplicant", 
							"smsEmail.dearApplicant",new Locale(MainetConstants.REG_ENG.REGIONAL)))
					.append(",</singleline> </td> </tr>");
			}
		}

		builder.append(
				"<tr> <td align=\"left\" valign=\"top\" style=\"font-family: \'Raleway\',Tahoma,Arial;font-size: 13px;line-height: 18px;font-weight: 400;color: #5f5f5f;-webkit-font-smoothing: antialiased;padding-right: 30px;\" mc:edit=\"content\"><multiline label=\"content\"><p style=\"font-family: \'Raleway\',Tahoma,Arial;font-size: 13px;line-height: 18px;font-weight: 400;color: #5f5f5f;-webkit-font-smoothing: antialiased;margin: 0 0 10px !important;\">");
		builder.append(template);
		builder.append("</p></multiline> <br>&nbsp;</td></tr>");

		builder.append(
				"<tr><td align=\"left\" valign=\"top\" style=\"font-family: \'Raleway\',Tahoma,Arial;font-size: 13px;line-height: 18px;font-weight: 400;color: #5f5f5f;-webkit-font-smoothing: antialiased;padding-right: 30px;\" mc:edit=\"content\">"
						+ session.getMessage("rti.mail.notemsg") + "<br>&nbsp;</td></tr>");

		builder.append(
				"<tr><td height=\"10\"></td></tr><tr> <td valign=\"top\" style=\"padding-right: 30px;\"> &nbsp; </td> </tr><tr><td height=\"10\"></td></tr></table>");
		builder.append(" </td></tr> <tr><td height=\"50\"></td></tr> </table></td> </tr> </table></body></html>");
		return builder.toString();
	}




}
