package com.abm.mainet.common.utility;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;

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
    private Employee employee = new Employee();
    private String mobileNoToValidate;
    private String userIp;
    private String finYearId = null;
    private Date finEndDate = null;
    private Date finStartDate = null;
    private String serviceType;
    private boolean readMoreReq = false;
    private Long emplType;
    private String errorDescription;
    // private String isPIO;
    private String quickPayFlag;
    private String contextName;

    private String currentClassName;

    private Long currentAppid;

    private String loggedInMessage;

    // private List<String> slidingImgLookUps = new ArrayList<>();

    private List<String> logoImagesList = new ArrayList<>();

    private Map<String, String> scrutinyCommonParamMap = new HashMap<>();

    private List<String> userAccessList = new ArrayList<>();

    private Map<String, String> mapList = new HashMap<>(0);

    private List<String> filePathForPDF = new ArrayList<>(); // Used for set Path for Show PDF in Browser

    private Map<Long, String> bankList = new HashMap<>();// bank list for offline payment

    // private long typeID; // used for hospital master,can be used for other

    // private String subTypeCode; // used for the BND Authorization ,can be used for others

    private String browserType;// used for knowing browserType

    private List<LookUp> paymentMode = new ArrayList<>();

    // private Map<Long, String> onlineBankList = new HashMap<>();
    private String quickLinkEng, quickLinkReg = null;

    private Long loggedLocId;

    private String orgLogoPath;

    private String loggedInEmpPhoto;

    Map<Long, String> applicationTaskMap = new HashMap<>();

    private String moduleDeptCode;// T#92467
    
    private Map<String, Object> objectMap = new HashMap<>(0);

    /*
     * public void setAppletURL(final String appletURL) { ApplicationSession.getInstance(); }
     */
	//119534  Fields added for Scheduling for TRX check at session level
    private String countersts;
    private String validEmpFlag;
    private Date scheduleDate;
    private String roleCode;
    private String checkSudaUatEnv;
    private String checkThaneProdEnv;
    private String uniqueKeyId;
    private String loggedFromOtherAppl;
   

	public Date getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public String getValidEmpFlag() {
		return validEmpFlag;
	}

	public void setValidEmpFlag(String validEmpFlag) {
		this.validEmpFlag = validEmpFlag;
	}

	public String getCountersts() {
		return countersts;
	}

	public void setCountersts(String countersts) {
		this.countersts = countersts;
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

    public String getFinYearId() {

        if (finYearId == null) {

            setCurrentFinancialYear();

        }
        return finYearId;
    }

    public void setFinYearId(final String finYearId) {
        this.finYearId = finYearId;
    }

    public String getLoggedInMessage() {
        return loggedInMessage;
    }

    public void setLoggedInMessage(final String loggedInMessage) {
        this.loggedInMessage = loggedInMessage;
    }

    /**
     * @return the finEndDate
     */
    public Date getFinEndDate() {

        if (finEndDate == null) {

            setCurrentFinancialYear();

        }

        return finEndDate;
    }

    /**
     * @param finEndDate the finEndDate to set
     */
    public void setFinEndDate(final Date finEndDate) {
        this.finEndDate = finEndDate;
    }

    /**
     * @return the finStartDate
     */
    public Date getFinStartDate() {

        if (finStartDate == null) {
            setCurrentFinancialYear();

        }
        return finStartDate;
    }

    /**
     * @param finStartDate the finStartDate to set
     */
    public void setFinStartDate(final Date finStartDate) {
        this.finStartDate = finStartDate;
    }

    public String getFinancialPeriodShortForm() {

        final Calendar startCal = Calendar.getInstance();
        startCal.setTime(getFinStartDate());

        final Calendar endCal = Calendar.getInstance();
        endCal.setTime(getFinEndDate());

        return startCal.get(Calendar.YEAR) + "-" + String.valueOf(endCal.get(Calendar.YEAR)).substring(2);
    }

    public String getCurrentDate() {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(MainetConstants.DATE_FORMAT);

        return dateFormat.format(new Date());
    }

    public String getServiceType() {
        return serviceType;
    }

    private void setCurrentFinancialYear() {
        Connection conn = null;
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;

        try {
            conn = new ApplicationDatasourceLoader().getConnection();

            preStatement = conn.prepareStatement(
                    "select f.fa_yearid,f.fa_fromdate,f.fa_todate  from TB_FINANCIALYEAR f where ? between f.fa_fromdate and f.fa_todate");
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            final String dt = sdf.format(new Date());
            final Date date = sdf.parse(dt);
            preStatement.setDate(1, new java.sql.Date(date.getTime()));
            preStatement.execute();
            resultSet = preStatement.getResultSet();

            while (resultSet.next()) {
                finYearId = String.valueOf(resultSet.getLong(MainetConstants.FA_YEARID));
                finStartDate = resultSet.getDate(MainetConstants.FA_FROMDATE);
                finEndDate = resultSet.getDate(MainetConstants.FA_TODATE);
            }

        } catch (final Exception e) {
            e.printStackTrace();
        } finally {

            if (conn != null) {
                try {
                    conn.close();
                } catch (final SQLException e) {
                }
            }

        }
    }

    /*
     * public List<String> getSlidingImgLookUps() { return slidingImgLookUps; } public void setSlidingImgLookUps(final
     * List<String> slidingImgLookUps) { this.slidingImgLookUps = slidingImgLookUps; }
     */
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

    public Map<String, String> getScrutinyCommonParamMap() {
        return scrutinyCommonParamMap;
    }

    public void setScrutinyCommonParamMap(final Map<String, String> scrutinyCommonParamMap) {
        this.scrutinyCommonParamMap = scrutinyCommonParamMap;
    }

    /**
     * @return the typeID
     */
    /*
     * public long getTypeID() { return typeID; }
     */

    /**
     * @param typeID the typeID to set
     */
    /*
     * public void setTypeID(final long typeID) { this.typeID = typeID; } public String getSubTypeCode() { return subTypeCode; }
     * public void setSubTypeCode(final String subTypeCode) { this.subTypeCode = subTypeCode; }
     */
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

    /*
     * public Map<Long, String> getOnlineBankList() { return onlineBankList; } public void setOnlineBankList(final Map<Long,
     * String> onlineBankList) { this.onlineBankList = onlineBankList; }
     */

    /*
     * public String getIsPIO() { return isPIO; } public void setIsPIO(final String isPIO) { this.isPIO = isPIO; }
     */

    public Map<String, String> getMapList() {
        return mapList;
    }

    public void setMapList(final Map<String, String> mapList) {
        this.mapList = mapList;
    }

    public LookUp getULBName() {
        final Organisation organisation = getOrganisation();
        final LookUp lookUp = new LookUp();

        if (organisation != null) {
            lookUp.setDescLangFirst(organisation.getONlsOrgname());
            if ((organisation.getONlsOrgnameMar() == null) || organisation.getONlsOrgnameMar().equals("")) {
                lookUp.setDescLangSecond(organisation.getONlsOrgname());
            } else {
                lookUp.setDescLangSecond(organisation.getONlsOrgnameMar());
            }

        }

        return lookUp;
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

    /**
     * @return the applicationTaskMap
     */
    public Map<Long, String> getApplicationTaskMap() {
        return applicationTaskMap;
    }

    /**
     * @param applicationTaskMap the applicationTaskMap to set
     */
    public void setApplicationTaskMap(
            final Map<Long, String> applicationTaskMap) {
        this.applicationTaskMap = applicationTaskMap;
    }

    public String getModuleDeptCode() {
        return moduleDeptCode;
    }

    public void setModuleDeptCode(String moduleDeptCode) {
        this.moduleDeptCode = moduleDeptCode;
    }

    public Long getLoggedLocId() {
        return loggedLocId;
    }

    public void setLoggedLocId(final Long loggedLocId) {
        this.loggedLocId = loggedLocId;
    }

    public String getOrgLogoPath() {
        final Organisation organisation = getOrganisation();
        if (organisation.getoLogo() != null) {
            final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR
                    + "SHOW_DOCS";
            orgLogoPath = Utility.downloadedFileUrl(organisation.getoLogo(), outputPath, FileNetApplicationClient.getInstance());
        } else {
            orgLogoPath = null;
        }
        return orgLogoPath;
    }

    public void setOrgLogoPath(final String orgLogoPath) {
        this.orgLogoPath = orgLogoPath;
    }

    public String getLoggedInEmpPhoto() {
        final Employee employee = getEmployee();
        if (employee.getEmpphotopath() != null) {
            final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR
                    + "SHOW_DOCS";
            loggedInEmpPhoto = Utility.downloadedFileUrl(employee.getEmpphotopath(), outputPath,
                    FileNetApplicationClient.getInstance());
        } else {
            loggedInEmpPhoto = null;
        }
        return loggedInEmpPhoto;
    }

    public void setLoggedInEmpPhoto(String loggedInEmpPhoto) {
        this.loggedInEmpPhoto = loggedInEmpPhoto;
    }

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public Map<String, Object> getObjectMap() {
		return objectMap;
	}

	public void setObjectMap(Map<String, Object> objectMap) {
		this.objectMap = objectMap;
	}

	public String getCheckSudaUatEnv() {
		return checkSudaUatEnv;
	}

	public void setCheckSudaUatEnv(String checkSudaUatEnv) {
		this.checkSudaUatEnv = checkSudaUatEnv;
	}

	public String getCheckThaneProdEnv() {
		return checkThaneProdEnv;
	}

	public void setCheckThaneProdEnv(String checkThaneProdEnv) {
		this.checkThaneProdEnv = checkThaneProdEnv;
	}

	public String getUniqueKeyId() {
		return uniqueKeyId;
	}

	public void setUniqueKeyId(String uniqueKeyId) {
		this.uniqueKeyId = uniqueKeyId;
	}

	public String getLoggedFromOtherAppl() {
		return loggedFromOtherAppl;
	}

	public void setLoggedFromOtherAppl(String loggedFromOtherAppl) {
		this.loggedFromOtherAppl = loggedFromOtherAppl;
	}

	
}