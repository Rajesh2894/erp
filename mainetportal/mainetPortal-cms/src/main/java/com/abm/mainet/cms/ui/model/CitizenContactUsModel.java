package com.abm.mainet.cms.ui.model;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.EIPContactUs;
import com.abm.mainet.cms.domain.EIPContactUsHistory;
import com.abm.mainet.cms.domain.EipUserContactUs;
import com.abm.mainet.cms.service.IEIPContactUsService;
import com.abm.mainet.cms.ui.validator.CitizenContactUsValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.dto.TemplateLookUp;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Component
@Scope(value = "session")
public class CitizenContactUsModel extends AbstractEntryFormModel<EIPContactUs> implements Serializable {

    private static final long serialVersionUID = -8131310246907338937L;

    private EipUserContactUs eipUserContactUs;
    private List<EIPContactUsHistory> contactUsList = new ArrayList<>();
    private List<EIPContactUs> contactUsByFlag = new ArrayList<>();
    private List<EIPContactUsHistory> contactUsListByFlag = new ArrayList<>();
    private List<String> contactOptionList = new ArrayList<>();
    
    private String captchaSessionLoginValue;
   

	@Autowired
    private IEIPContactUsService iEIPContactUsService;

    @Autowired
    private ISMSAndEmailService ismsAndEmailService;

    public List<EIPContactUsHistory> getContactUsorg() {
        contactUsList = iEIPContactUsService.getContactUsorg(UserSession.getCurrent().getOrganisation());
        return contactUsList;
    }

    public List<EIPContactUsHistory> getContactUsListBy() {

    	contactUsListByFlag = iEIPContactUsService.getContactUslistBy(UserSession.getCurrent().getOrganisation());
		
        return contactUsListByFlag;
    }

    public List<String> getContactUsOptionList() {

    	for (EIPContactUsHistory eIPContactUsHistory : this.contactUsListByFlag) {
    		contactOptionList.add(eIPContactUsHistory.getDesignationEn());
		}
    	 
    	
        return contactOptionList;
    }
    
    

    @Override
    public String getActiveClass() {
        return "contactUs";
    }

    @Override
    public boolean saveForm() {

        validateBean(this, CitizenContactUsValidator.class);
        
        if (hasValidationErrors()) {
            return false;
        }

        else {
            final List<File> files = new ArrayList<>(0);

            if ((FileUploadUtility.getCurrent().getFileMap() != null) && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
                for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                    final List<File> list = new ArrayList<>(entry.getValue());
                    for (final File file : list) {
                        eipUserContactUs.setAttPath(getDirectry() + File.separator + file.getName());
                        break;
                    }
                    files.addAll(list);
                }
            }
            boolean flag2 = true;
            iEIPContactUsService.saveEIPuserContactus(getEipUserContactUs());

            for (final EIPContactUsHistory eipContactUs : contactUsListByFlag) {

                if (UserSession.getCurrent().getLanguageId() == 1) {
                    final String msg1 = ApplicationSession.getInstance().getMessage("eip.admin.contactUs.mailmsg1")
                            + MainetConstants.WHITE_SPACE
                            + (UserSession.getCurrent().getOrganisation().getONlsOrgname()) + MainetConstants.WHITE_SPACE
                            + ApplicationSession.getInstance().getMessage("eip.admin.contactUs.mailmsg3")
                            + MainetConstants.WHITE_SPACE
                            + (UserSession.getCurrent().getOrganisation().getONlsOrgname()) + MainetConstants.WHITE_SPACE
                            + ApplicationSession.getInstance().getMessage("eip.admin.contactUs.mailmsg4");
                    sendSMSandEMail(UserSession.getCurrent().getEmployee(), msg1, "N");
                }
                if (UserSession.getCurrent().getLanguageId() == 2) {
                    final String msg1 = (UserSession.getCurrent().getOrganisation().getONlsOrgnameMar())
                            + MainetConstants.WHITE_SPACE
                            + ApplicationSession.getInstance().getMessage("eip.admin.contactUs.mailmsg1")
                            + MainetConstants.WHITE_SPACE
                            + ApplicationSession.getInstance().getMessage("eip.admin.contactUs.mailmsg3")
                                    .concat(UserSession.getCurrent().getOrganisation().getONlsOrgnameMar())
                            + MainetConstants.WHITE_SPACE
                            + ApplicationSession.getInstance().getMessage("eip.admin.contactUs.mailmsg4");
                    sendSMSandEMail(UserSession.getCurrent().getEmployee(), msg1, "N");

                    if (eipContactUs.getTelephoneNo1En() != null) {
                    }
                    if (flag2) {
                        flag2 = false;
                    }
                }

                try {
                    getFileNetClient().uploadFileList(files, getDirectry());
                } catch (final Exception e) {
                    throw new FrameworkException(e);
                }
            }

            return true;
        }

    }

    private void sendSMSandEMail(final Employee registeredEmployee, final String msg, final String templateType) {
        final TemplateLookUp lookup = new TemplateLookUp();

        ApplicationSession.getInstance().getMessage("eip.admin.contactUs.mailmsg1");
        UserSession.getCurrent().getOrganisation().getONlsOrgname();
        ApplicationSession.getInstance().getMessage("eip.admin.contactUs.mailmsg3");
        UserSession.getCurrent().getOrganisation().getONlsOrgname();
        ApplicationSession.getInstance().getMessage("eip.admin.contactUs.mailmsg4");
        ApplicationSession.getInstance().getMessage("eip.admin.contactUs.mailmsg2");
        ApplicationSession.getInstance().getMessage("eip.admin.contactUs.mailmsg5");
        eipUserContactUs.getEmailId();
        ApplicationSession.getInstance().getMessage("eip.citizen.contactUs.name");
        eipUserContactUs.getFirstName();
        eipUserContactUs.getLastName();
        ApplicationSession.getInstance().getMessage("feedback.Email");
        eipUserContactUs.getEmailId();
        ApplicationSession.getInstance().getMessage("eip.admin.contactUs.desc");
        eipUserContactUs.getDescQuery();
        ApplicationSession.getInstance().getMessage("eip.admin.contactUs.mailmsg7");

        final SMSAndEmailDTO dto = new SMSAndEmailDTO();
        dto.setMobnumber(getEipUserContactUs().getPhoneNo());
        dto.setEmail(getEipUserContactUs().getEmailId());
        dto.setAppName(getEipUserContactUs().getFirstName());

        if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
            dto.setV_muncipality_name(UserSession.getCurrent().getOrganisation().getONlsOrgname());
        } else {
            dto.setV_muncipality_name(UserSession.getCurrent().getOrganisation().getONlsOrgnameMar());
        }

        lookup.setSmsBody(msg);
        lookup.setMailBody(msg);
        lookup.setAlertType("B");
        ismsAndEmailService.sendEmailSMS(lookup, dto, UserSession.getCurrent().getOrganisation());
    }

    private String getDirectry() {
    	SimpleDateFormat formatter=new SimpleDateFormat(MainetConstants.FORMAT_DDMMMYY);
        String date=formatter.format(new Date());
        final String directoryTree = UserSession.getCurrent().getOrganisation().getOrgid() + MainetConstants.FILE_PATH_SEPARATOR + date
                + MainetConstants.FILE_PATH_SEPARATOR + "EIPCONTACTUS" + MainetConstants.FILE_PATH_SEPARATOR
                + Utility.getTimestamp();
        return directoryTree;
    }

    public EipUserContactUs getEipUserContactUs() {
        return eipUserContactUs;
    }

    public void setEipUserContactUs(final EipUserContactUs eipUserContactUs) {
        this.eipUserContactUs = eipUserContactUs;
    }

    public List<EIPContactUsHistory> getContactUsList() {

        return contactUsList;
    }

    public void setContactUsList(final List<EIPContactUsHistory> contactUsList) {
        this.contactUsList = contactUsList;
    }

   
    public List<EIPContactUs> getContactUsByFlag() {
		return contactUsByFlag;
	}

	public void setContactUsByFlag(List<EIPContactUs> contactUsByFlag) {
		this.contactUsByFlag = contactUsByFlag;
	}

	public void getcheckLoginStatus() {
        final Employee emp = UserSession.getCurrent().getEmployee();
        if (emp != null && emp.getLoggedIn() != null && emp.getLoggedIn().equals(MainetConstants.AUTH)) {
            final EipUserContactUs eipUserContactUs = new EipUserContactUs();
            eipUserContactUs.setFirstName(emp.getEmpname());
            eipUserContactUs.setLastName(emp.getEmpLName());
            eipUserContactUs.setPhoneNo(emp.getEmpmobno());
            eipUserContactUs.setEmailId(emp.getEmpemail());
            setEipUserContactUs(eipUserContactUs);
        }

    }

	public List<EIPContactUsHistory> getContactUsListByFlag() {
		return contactUsListByFlag;
	}

	public void setContactUsListByFlag(List<EIPContactUsHistory> contactUsListByFlag) {
		this.contactUsListByFlag = contactUsListByFlag;
	} 
	
	public List<String> getContactOptionList() {
		return contactOptionList;
}

	public void setContactOptionList(List<String> contactOptionList) {
		this.contactOptionList = contactOptionList;
	} 
	
	 public String getCaptchaSessionLoginValue() {
			return captchaSessionLoginValue;
		}

		public void setCaptchaSessionLoginValue(String captchaSessionLoginValue) {
			this.captchaSessionLoginValue = captchaSessionLoginValue;
		}
}
