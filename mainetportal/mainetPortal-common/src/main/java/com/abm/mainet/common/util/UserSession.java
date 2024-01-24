package com.abm.mainet.common.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.SEOKeyWordMaster;
import com.abm.mainet.common.dto.MenuManager;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.service.ISEOMetaDataMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.dms.client.FileNetApplicationClient;

/**
 * @author Pranit.Mhatre
 * @since 02 September, 2013
 */

@Component
@Scope(value = "session")
public class UserSession implements Serializable {

    private static final long serialVersionUID = 289609296378313644L;
    private Organisation organisation;
    private int languageId;
    private Employee employee = new Employee();		// User
    private String mobileNoToValidate;
    private String userIp;
    private String serviceType;
    private boolean readMoreReq = false;
    private Long emplType;
    private String errorDescription;
    private String quickPayFlag;
    private String contextName;

    private String currentClassName;

    private String orgLogoPath;

    private Long currentAppid;

    private List<String> slidingImgLookUps = new ArrayList<>();
    private List<String> logoImagesList = new ArrayList<>();

    private List<String> userAccessList = new ArrayList<>();

    private Map<String, String> mapList = new HashMap<>(0);

    private List<String> filePathForPDF = new ArrayList<>(); // Used for set Path for Show PDF in Browser

    private Map<Long, String> bankList = new HashMap<>();// bank list for offline payment

    private String browserType;// used for knowing browserType

    private List<LookUp> paymentMode = new ArrayList<>();

    private Map<Long, String> onlineBankList = new HashMap<>();
    private String quickLinkEng, quickLinkReg = null;

    private String redirecturl;
    private String keywords;
    private String description;
	private String userAgent;
	private String uniqueKeyId;
    /**
     * @return the redirecturl
     */
    public String getRedirecturl() {
        return redirecturl;
    }

    /**
     * @param redirecturl the redirecturl to set
     */
    public void setRedirecturl(final String redirecturl) {
        this.redirecturl = redirecturl;
    }

    public List<String> getUserAccessList() {
        return userAccessList;
    }

    public void setUserAccessList(final List<String> userAccessList) {
        this.userAccessList = userAccessList;
    }

    public static UserSession getCurrent() {
        return ApplicationContextProvider.getApplicationContext().getBean(UserSession.class);
    }

    public static MenuManager getCurrentMenuManager() {
        return ApplicationContextProvider.getApplicationContext().getBean(MenuManager.class);
    }

    /**
     * @return the organisation
     */
    public Organisation getOrganisation() {
        return organisation;
    }

    /**
     * @param organisation the organisation to set
     */
    public void setOrganisation(final Organisation organisation) {
        this.organisation = organisation;
    }

    /**
     * @return the languageId
     */
    public int getLanguageId() {
        return languageId;
    }

    /**
     * @param languageId the languageId to set
     */
    public void setLanguageId(final int languageId) {
        this.languageId = languageId;
    }

    /**
     * @return the employee
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * @param employee the employee to set
     */
    public void setEmployee(final Employee employee) {
        this.employee = employee;
    }

    /**
     * @return the mobileNoToValidate
     */
    public String getMobileNoToValidate() {
        return mobileNoToValidate;
    }

    /**
     * @param mobileNoToValidate the mobileNoToValidate to set
     */
    public void setMobileNoToValidate(final String mobileNoToValidate) {
        this.mobileNoToValidate = mobileNoToValidate;
    }

    /**
     * @return the userIp
     */
    public String getUserIp() {
        return userIp;
    }

    /**
     * @param userIp the userIp to set
     */
    public void setUserIp(final String userIp) {
        this.userIp = userIp;
    }

    public String getServiceType() {
        return serviceType;
    }

    public List<String> getSlidingImgLookUps() {
        return slidingImgLookUps;
    }

    public void setSlidingImgLookUps(final List<String> slidingImgLookUps) {
        this.slidingImgLookUps = slidingImgLookUps;
    }

    /**
     * @return the readMoreReq
     */
    public boolean isReadMoreReq() {
        return readMoreReq;
    }

    /**
     * @param readMoreReq the readMoreReq to set
     */
    public void setReadMoreReq(final boolean readMoreReq) {
        this.readMoreReq = readMoreReq;
    }

    /**
     * @return the currentAppid
     */
    public Long getCurrentAppid() {
        return currentAppid;
    }

    /**
     * @param currentAppid the currentAppid to set
     */
    public void setCurrentAppid(final Long currentAppid) {
        this.currentAppid = currentAppid;
    }

    public Long getEmplType() {
        return emplType;
    }

    public void setEmplType(final Long emplType) {
        this.emplType = emplType;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(final String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getCurrentClassName() {
        return currentClassName;
    }

    public void setCurrentClassName(final String currentClassName) {
        this.currentClassName = currentClassName;
    }

    public List<String> getLogoImagesList() {
        return logoImagesList;
    }

    public void setLogoImagesList(final List<String> logoImagesList) {
        this.logoImagesList = logoImagesList;
    }

    public String getBrowserType() {
        return browserType;
    }

    public void setBrowserType(final String browserType) {
        this.browserType = browserType;
    }

    public List<String> getFilePathForPDF() {
        return filePathForPDF;
    }

    public void setFilePathForPDF(final List<String> filePathForPDF) {
        this.filePathForPDF = filePathForPDF;
    }

    public Map<Long, String> getBankList() {
        return bankList;
    }

    public void setBankList(final Map<Long, String> bankList) {
        this.bankList = bankList;
    }

    public List<LookUp> getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(final List<LookUp> paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Map<Long, String> getOnlineBankList() {
        return onlineBankList;
    }

    public void setOnlineBankList(final Map<Long, String> onlineBankList) {
        this.onlineBankList = onlineBankList;
    }

    public Map<String, String> getMapList() {
        return mapList;
    }

    public void setMapList(final Map<String, String> mapList) {
        this.mapList = mapList;
    }

    /**
     * @return the orgLogoPath
     */
    public String getOrgLogoPath() {

        final Organisation organisation = getOrganisation();
        if (organisation.getOLogo() != null) {
            final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR
                    + "SHOW_DOCS";
            orgLogoPath = Utility.downloadedFileUrl(organisation.getOLogo(), outputPath, FileNetApplicationClient.getInstance());
        } else {
            orgLogoPath = null;
        }
        return orgLogoPath;

    }

    /**
     * @param orgLogoPath the orgLogoPath to set
     */
    public void setOrgLogoPath(String orgLogoPath) {
        this.orgLogoPath = orgLogoPath;
    }

    public LookUp getULBName() {
        final Organisation organisation = getOrganisation();
        final LookUp lookUp = new LookUp();

        if (organisation != null) {
            lookUp.setDescLangFirst(organisation.getONlsOrgname());
            if ((organisation.getONlsOrgnameMar() == null) || organisation.getONlsOrgnameMar().equals(MainetConstants.BLANK)) {
                lookUp.setDescLangSecond(organisation.getONlsOrgname());
            } else {
                lookUp.setDescLangSecond(organisation.getONlsOrgnameMar());
            }

        }

        return lookUp;
    }

    public Map<String, String> getSocialMediaMap() {

        List<String> urlsList = null;
        final String socialMediaKeywords = ApplicationSession.getInstance().getMessage("social.media.supported");
        final Map<String, String> sMediaMap = new LinkedHashMap<>();
        String url = MainetConstants.BLANK;
        if ((null != socialMediaKeywords) && !socialMediaKeywords.equals(MainetConstants.BLANK)) {
            urlsList = new ArrayList<>(Arrays.asList(socialMediaKeywords.split(MainetConstants.operator.COMA)));
        }
        if ((null != urlsList) && (null != urlsList)) {
            for (final String linkURL : urlsList) {
                url = ApplicationSession.getInstance()
                        .getMessage("social.media." + linkURL + MainetConstants.operator.DOT
                                + UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.BLANK);

                if (!url.isEmpty()) {
                    sMediaMap.put(linkURL, url);
                }

            }
        }
        return sMediaMap;
    }

    public Map<String, Set<Organisation>> getOrganisationsList() {
        IOrganisationService iOrganisationService = ApplicationContextProvider.getApplicationContext()
                .getBean(IOrganisationService.class);
        return iOrganisationService.findAllActiveOrganizationByULBOrgId("A", getLanguageId() == 0 ? 1 : getLanguageId());
    }

    public String getContextName() {
        return contextName;
    }

    public void setContextName(final String contextName) {
        this.contextName = contextName;
    }

    public String getQuickLinkEng() {
        return quickLinkEng;
    }

    public void setQuickLinkEng(final String quickLinkEng) {
        this.quickLinkEng = quickLinkEng;
    }

    public String getQuickLinkReg() {
        return quickLinkReg;
    }

    public void setQuickLinkReg(final String quickLinkReg) {
        this.quickLinkReg = quickLinkReg;
    }

    /**
     * @return the quickPayFlag
     */
    public String getQuickPayFlag() {
        return quickPayFlag;
    }

    /**
     * @param quickPayFlag the quickPayFlag to set
     */
    public void setQuickPayFlag(final String quickPayFlag) {
        this.quickPayFlag = quickPayFlag;
    }

    public SEOKeyWordMaster getSEOMasterData() {

        ISEOMetaDataMasterService metaDataMasterService = ApplicationContextProvider.getApplicationContext()
                .getBean(ISEOMetaDataMasterService.class);
        Optional<SEOKeyWordMaster> master = metaDataMasterService
                .getSEOMaster(UserSession.getCurrent().getOrganisation().getOrgid());

        if (master.isPresent()) {
            return master.get();
        }
        return null;

    }

    public Date getLastUpdated() {
        IPortalServiceMasterService iPortalServiceMasterService = ApplicationContextProvider.getApplicationContext()
                .getBean(IPortalServiceMasterService.class);
        return iPortalServiceMasterService.getLastUpdated();

    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getUniqueKeyId() {
		return uniqueKeyId;
	}

	public void setUniqueKeyId(String uniqueKeyId) {
		this.uniqueKeyId = uniqueKeyId;
	}

    
}
