/**
 * 
 */
package com.abm.mainet.socialsecurity.ui.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IFileUploadService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.abm.mainet.socialsecurity.service.RenewalFormService;
import com.abm.mainet.socialsecurity.ui.dto.RenewalFormDto;
import com.abm.mainet.socialsecurityl.ui.validator.RenewalFormValidator;

/**
 * @author priti.singh
 *
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class RenewalFormModel extends AbstractFormModel {

	private static final Logger LOGGER = Logger.getLogger(RenewalFormModel.class);

	private static final long serialVersionUID = 1387449222271618470L;

	private RenewalFormDto renewalFormDto = new RenewalFormDto();

	private List<Object[]> serviceList = new ArrayList<>();

	private List<DocumentDetailsVO> uploadFileList = new ArrayList<>();
	private PortalService serviceMaster = new PortalService();

	@Autowired
	private IFileUploadService fileUpload;

	@Autowired
	private RenewalFormService renewalFormService;

	@Autowired
	private ISMSAndEmailService ismsAndEmailService;

	private Long deptId;

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public List<Object[]> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<Object[]> serviceList) {
		this.serviceList = serviceList;
	}

	public List<DocumentDetailsVO> getUploadFileList() {
		return uploadFileList;
	}

	public void setUploadFileList(List<DocumentDetailsVO> uploadFileList) {
		this.uploadFileList = uploadFileList;
	}

	public IFileUploadService getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(IFileUploadService fileUpload) {
		this.fileUpload = fileUpload;
	}

	public RenewalFormDto getRenewalFormDto() {
		return renewalFormDto;
	}

	public void setRenewalFormDto(RenewalFormDto renewalFormDto) {
		this.renewalFormDto = renewalFormDto;
	}

	public RenewalFormService getRenewalFormService() {
		return renewalFormService;
	}

	public void setRenewalFormService(RenewalFormService renewalFormService) {
		this.renewalFormService = renewalFormService;
	}

	public PortalService getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(PortalService serviceMaster) {
		this.serviceMaster = serviceMaster;
	}

	public ISMSAndEmailService getIsmsAndEmailService() {
		return ismsAndEmailService;
	}

	public void setIsmsAndEmailService(ISMSAndEmailService ismsAndEmailService) {
		this.ismsAndEmailService = ismsAndEmailService;
	}

	@Override
	public boolean saveForm() {
		RenewalFormDto statusDto = this.getRenewalFormDto();
		try {
			statusDto = renewalFormService.saveRenewalDetails(getRenewalFormDto());

			setSuccessMessage(getAppSession().getMessage("social.success.msg") + statusDto.getMasterAppId());

			
			// sms and email (renewal form submit)

			final SMSAndEmailDTO smsDto = new SMSAndEmailDTO();

			smsDto.setMobnumber(getRenewalFormDto().getMobNum().toString());
			smsDto.setAppNo(statusDto.getMasterAppId().toString());
			/* smsDto.setAppNo(getRenewalFormDto().getMasterAppId().toString()); */
			
			Long sm = ApplicationContextProvider.getApplicationContext().getBean(IPortalServiceMasterService.class)
					.getServiceId(MainetConstants.SocialSecurity.RENEWAL_OF_LIFE_CERTIFICATE_SERVICE_CODE,
							UserSession.getCurrent().getOrganisation().getOrgid());
			PortalService portalService = ApplicationContextProvider.getApplicationContext()
					.getBean(IPortalServiceMasterService.class).getPortalService(sm);

			setServiceMaster(portalService);
			smsDto.setServName(portalService.getServiceName());
			String url = "RenewalForm.html";
			smsDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());

			int langId = UserSession.getCurrent().getLanguageId();
			ismsAndEmailService.sendEmailSMS("CFC", url, MainetConstants.SocialSecurity.GENERAL_MSG, smsDto,
					UserSession.getCurrent().getOrganisation(), langId);
			 

		} catch (FrameworkException exp) {
			statusDto.setStatusFlag(false);
			throw new FrameworkException(exp);

		}
		return true;
	}

	public boolean validateInputs() {
        validateBean(this, RenewalFormValidator.class);
        

        if (hasValidationErrors()) {
            return false;
        }
        return true;
    }
	
	
	public List<DocumentDetailsVO> getFileUploadList(final List<DocumentDetailsVO> checkList) {

		final Map<Long, String> listString = new HashMap<>();
		final Map<Long, String> fileName = new HashMap<>();

		try {
			final List<DocumentDetailsVO> docs = checkList;
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {

				final List<File> list = new ArrayList<>(entry.getValue());
				for (final File file : list) {
					try {
						final Base64 base64 = new Base64();

						final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));
						fileName.put(entry.getKey(), file.getName());
						listString.put(entry.getKey(), bytestring);

					} catch (final IOException e) {
					
						throw new FrameworkException(e);
					}
				}
			}
			long count = 0;
			if (docs != null) {
				if (!docs.isEmpty() && !listString.isEmpty()) {
					for (final DocumentDetailsVO d : docs) {
						// final long count = d.getDocumentSerialNo() - 1;
						if (listString.containsKey(count) && fileName.containsKey(count)) {
							d.setDocumentByteCode(listString.get(count));
							d.setDocumentName(fileName.get(count));
							count++;
						}

					}

				}
			}
			return docs;
		} catch (final Exception e) {
			throw new FrameworkException("FileUploading Exception occur in getFileUploadList", e);
		}
	}

}
