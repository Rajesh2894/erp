package com.abm.mainet.cms.ui.model;


import java.io.File;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.abm.mainet.cms.dao.ISubLinkMasterDAO;
import com.abm.mainet.cms.domain.EIPAboutUsHistory;
import com.abm.mainet.cms.domain.EIPAnnouncementHistory;
import com.abm.mainet.cms.domain.EIPContactUsHistory;
import com.abm.mainet.cms.domain.Feedback;
import com.abm.mainet.cms.domain.FrequentlyAskedQuestions;
import com.abm.mainet.cms.domain.ProfileMaster;
import com.abm.mainet.cms.domain.ProfileMasterHistory;
import com.abm.mainet.cms.domain.PublicNoticesHistory;
import com.abm.mainet.cms.domain.SubLinkFieldDetails;
import com.abm.mainet.cms.domain.SubLinkFieldDetailsHistory;
import com.abm.mainet.cms.domain.SubLinkFieldMapping;
import com.abm.mainet.cms.domain.SubLinkMaster;
import com.abm.mainet.cms.dto.MDDAAPIReqDto;
import com.abm.mainet.cms.dto.MDDAApiResponseDto;
import com.abm.mainet.cms.dto.NewsLetterSubscriptionDTO;
import com.abm.mainet.cms.dto.TraficUpdateDTO;
import com.abm.mainet.cms.service.IAdminPublicNoticesService;
import com.abm.mainet.cms.service.IEIPAboutUsService;
import com.abm.mainet.cms.service.IEIPContactUsService;
import com.abm.mainet.cms.service.IEipAnnouncementService;
import com.abm.mainet.cms.service.IProfileMasterService;
import com.abm.mainet.cms.service.ISectionService;
import com.abm.mainet.cms.service.IThemeMasterService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.PortalDepartmentDTO;
import com.abm.mainet.common.dto.PortalPhotoDTO;
import com.abm.mainet.common.dto.PortalVideoDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IEntitlementService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.service.ISEOMetaDataMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.ui.model.AbstractModel;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.FileStorageCache;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.MappingJackson2HttpMessageConverter;
import com.abm.mainet.common.util.StringUtility;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.dms.service.DmsService;
import com.abm.mainet.integration.ws.JersyCall;

@Component
@Scope(value = "session")
public class CitizenHomeModel extends AbstractFormModel implements Serializable {

    private static final long serialVersionUID = -8089318502962092944L;
    private static final Logger LOG = Logger.getLogger(CitizenHomeModel.class);
    final String physicalPath = ApplicationSession.getInstance().getMessage("upload.physicalPath");
    
    
    
            

    private String uploadedFile = MainetConstants.BLANK;

    @Autowired
    private IOrganisationService iOrganisationService;

    @Autowired
    private IEmployeeService iEmployeeService;

    @Autowired
    private IEipAnnouncementService iEipAnnouncementService;

    @Autowired
    private ISectionService iSectionService;

    @Autowired
    private DmsService dmsService;

    @Autowired
    private IEIPContactUsService contactUsService;

    private int preTrnId;
    private int postTrnId;

    private List<FrequentlyAskedQuestions> frequentlyAskedQuestionList = Collections.emptyList();
    private List<EIPAnnouncementHistory> eipAnnouncement = new ArrayList<>(0);
    private List<PortalService> serviceList;
    private EIPAboutUsHistory citizenAboutUsHistroy;
    private String aboutUsDescFirstPara;
    private String aboutUsDescSecondPara;
    public List<PortalDepartmentDTO> AllDeptAndService;
    private List<Organisation> organisationsList = new ArrayList<>();
    private Employee currentEmpForEditProfile;

    private List<EIPContactUsHistory> organisationContactList;
    
    
    private List<MDDAApiResponseDto> mDDAApiResponseDto=new ArrayList<>();  

    
    private String empNameForEditProfile;
    List<String> smfaction = new ArrayList<>();
    private boolean isHighlightedAnnouncement;
    private boolean isHighlighted;
    private boolean isUsefull;
    private boolean isScheme;
    private String serviceURL;
    private String service;
    private String serviceName;
    private String serviceCode;
   
    // added for BRMS checklist
    private List<DocumentDetailsVO> checkList = new ArrayList<>();

    private Long OrgId;
    
    private String hiddEmailId;
    private String hiddMobNo;

    @Autowired
    private IEIPAboutUsService iEIPAboutUsService;

    @Autowired
    private IPortalServiceMasterService iPortalServiceMasterService;

    @Autowired
    private IEntitlementService iEntitlementService;

    @Autowired
    private IAdminPublicNoticesService iAdminPublicNoticesService;

    @Autowired
    private IThemeMasterService themeMasterService;

    @Autowired
    ISEOMetaDataMasterService metaDataMasterService;

    @Autowired
    private ISubLinkMasterDAO subLinkMasterDAO;
    
    
    @Autowired
    private IProfileMasterService iProfileMasterService;

   
    private List<PublicNoticesHistory> publicNotices = new ArrayList<>(0);

    private List<ProfileMaster> commissinorProfile = new ArrayList<>(0);
    
    private List<ProfileMaster> mayorProfile = new ArrayList<>(0);
    
    private String mayorOrCommisnrFlag;
  

	

    private Map<String, String> galleryMap = new HashMap<>();
    
    private Boolean isDMS;
    
    private Map<String,Integer> map=new LinkedHashMap<>();
    
    private Map<String,Integer>  mddastatus=new LinkedHashMap<>();
    
    private Map<String,Integer> totalMDDA=new LinkedHashMap<>();
    
 private NewsLetterSubscriptionDTO subscription = new NewsLetterSubscriptionDTO();
    
    private String subscriberEmail;

	//changes for adding feedBack
    private Feedback feedback = new Feedback();

   

	private String captchaSessionLoginValue;
	
	private String empGenderCode;
	
	private List<TraficUpdateDTO> traficUpdateAPIList;
	  
	
	@Override
    public void initializeModel() {

        super.initializeModel();
        setOrganisationsList(iOrganisationService.findAllActiveOrganization(MainetConstants.STATUS.ACTIVE));
        
    }

    public void getAboutUs() {
        setCitizenAboutUsHistroy(
                iEIPAboutUsService.getGuestAboutUs(getUserSession().getOrganisation(), MainetConstants.IsDeleted.NOT_DELETE));

        if (getCitizenAboutUsHistroy() != null) {
            if (getUserSession().getLanguageId() != 1L) {
                if (getCitizenAboutUsHistroy().getDescriptionReg1() != null) {
                    setAboutUsDescFirstPara(getCitizenAboutUsHistroy().getDescriptionReg());
                    setAboutUsDescSecondPara(getCitizenAboutUsHistroy().getDescriptionReg1());
                } else {
                    setAboutUsDescFirstPara(getCitizenAboutUsHistroy().getDescriptionReg());
                    setAboutUsDescSecondPara(MainetConstants.BLANK);
                }

            } else {
                if (getCitizenAboutUsHistroy().getDescriptionEn1() != null) {
                    setAboutUsDescFirstPara(getCitizenAboutUsHistroy().getDescriptionEn());
                    setAboutUsDescSecondPara(getCitizenAboutUsHistroy().getDescriptionEn1());
                } else {
                    setAboutUsDescFirstPara(getCitizenAboutUsHistroy().getDescriptionEn());
                    setAboutUsDescSecondPara(MainetConstants.BLANK);
                }
            }
        } else {
            setAboutUsDescFirstPara(MainetConstants.BLANK);
            setAboutUsDescSecondPara(MainetConstants.BLANK);
        }
    }

    public String getAboutUsDescFirstPara() {
        return aboutUsDescFirstPara;
    }

    public void setAboutUsDescFirstPara(String aboutUsDescFirstPara) {
        this.aboutUsDescFirstPara = aboutUsDescFirstPara;
    }

    public void setAboutUsDescSecondPara(String aboutUsDescSecondPara) {
        this.aboutUsDescSecondPara = aboutUsDescSecondPara;
    }

    public List<FrequentlyAskedQuestions> getFrequentlyAskedQuestionList() {
        return frequentlyAskedQuestionList;
    }

    public void setFrequentlyAskedQuestionList(
            final List<FrequentlyAskedQuestions> frequentlyAskedQuestionList) {
        this.frequentlyAskedQuestionList = frequentlyAskedQuestionList;
    }

    public Organisation getOrganisationById(final long orgId) {
        final Organisation organisation = iOrganisationService.getOrganisationById(orgId);
        return organisation;
    }

    public int getPreTrnId() {
        return preTrnId;
    }

    public void setPreTrnId(final int preTrnId) {
        this.preTrnId = preTrnId;
    }

    public int getPostTrnId() {
        return postTrnId;
    }

    public void setPostTrnId(final int postTrnId) {
        this.postTrnId = postTrnId;
    }

    /**
     * To check valid citizen.
     * @param citizen
     * @return true/false
     */
    public boolean isCitizenEmployee(final Employee citizen) {
        final List<LookUp> lookUpList = super.getLookUpList(MainetConstants.NEC.PARENT);

        if ((citizen.getEmplType() != null) && (citizen.getEmplType() != 0l)) {
            for (final LookUp lookUp : lookUpList) {
                if ((lookUp.getLookUpId() == citizen.getEmplType())
                        && lookUp.getLookUpCode().equals(MainetConstants.NEC.CITIZEN)) {
                    ((EIPMenuManager) UserSession.getCurrentMenuManager()).setCitizenType(true);
                    return true;
                }
            }
            return false;
        } else {
            ((EIPMenuManager) UserSession.getCurrentMenuManager()).setAgencyType(false);
            return false;
        }
    }

    /**
     * From citizen login only NEC type employee can able to login. Method will check login request is given by an NEC type
     * employee.
     * @param emplTypeId
     * @return true/false
     * @since Add on 14 Feb 2014 as per instruction given.
     */
    public boolean checkNECType(final long emplTypeId) {
        final List<LookUp> lookUpList = super.getLookUpList(MainetConstants.NEC.PARENT);

        for (final LookUp lookUp : lookUpList) {
            if (lookUp.getLookUpId() == emplTypeId) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return the outputPath
     */
    public String getOutputPath() {
        return MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR
                + UserSession.getCurrent().getOrganisation().getOrgid() + MainetConstants.FILE_PATH_SEPARATOR
                + MainetConstants.DirectoryTree.SLIDER_IMAGE;
    }

    /**
     * @param outputPath the outputPath to set
     */
    public void setOutputPath(final String outputPath) {
    }

    @Override
    public String getActiveClass() {
        return "home";
    }

    public EIPAboutUsHistory getCitizenAboutUsHistroy() {
        return citizenAboutUsHistroy;
    }

    public void setCitizenAboutUsHistroy(EIPAboutUsHistory citizenAboutUsHistroy) {
        this.citizenAboutUsHistroy = citizenAboutUsHistroy;
    }

    /**
     * @return the aboutUsDescSecondPara
     */
    public String getAboutUsDescSecondPara() {
        return aboutUsDescSecondPara;
    }

    public List<EIPAnnouncementHistory> getEipAnnouncement() {
        return eipAnnouncement;
    }

    public void setEipAnnouncement(final List<EIPAnnouncementHistory> eipAnnouncement) {
        this.eipAnnouncement = eipAnnouncement;
    }

    public void getAllNotices() {

        int highlightCount = 0;
        int schemeCount = 0;
        int usefulLinkCount = 0;
        publicNotices = iAdminPublicNoticesService.getGuestCitizenPublicNotices(UserSession.getCurrent().getOrganisation());

        String FileName = null;
        for (final PublicNoticesHistory publicNotice : publicNotices) {
            final String attachmentPath = publicNotice.getProfileImgPath();
            FileName = StringUtility.staticStringAfterChar(File.separator, attachmentPath);
            publicNotice.setAttachmentName(FileName);
        }

        final ListIterator<PublicNoticesHistory> listIterator = publicNotices.listIterator();
        PublicNoticesHistory publicNotices = null;
        String[] pathArr = null;
        String directoryTree = null;
        FileStorageCache cache = null;
        while (listIterator.hasNext()) {
            publicNotices = listIterator.next();

            directoryTree = publicNotices.getProfileImgPath();

            publicNotices.setImagePath(downloadNewsImages(publicNotices));

            if (directoryTree != null) {
                pathArr = directoryTree.split(MainetConstants.operator.COMA);

                directoryTree = (pathArr.length > 0) ? pathArr[0] : directoryTree;

                directoryTree = StringUtility.staticStringBeforeChar(MainetConstants.operator.FORWARD_SLACE, directoryTree);

                directoryTree = directoryTree.replace(MainetConstants.operator.FORWARD_SLACE, MainetConstants.operator.COMA);
            }

            cache = publicNotices.getFileStorageCache();

            try {
                if ((cache.getMultipleFiles() != null) && (cache.getMultipleFiles().getSize() > 0)) {
                    getFileNetClient().uploadFileList(cache.getFileList(), directoryTree);
                }
            } catch (final Exception ex) {
                throw new FrameworkException(ex.getLocalizedMessage());
            } finally {
                cache.flush();
            }

            if (publicNotices.getIsHighlighted() != null
                    && publicNotices.getIsHighlighted().equalsIgnoreCase(MainetConstants.YES)) {
                highlightCount++;
            } else if (publicNotices.getIsUsefullLink() != null
                    && publicNotices.getIsUsefullLink().equalsIgnoreCase(MainetConstants.YES)) {
                usefulLinkCount++;
            } else {
                schemeCount++;
            }
            UserSession.getCurrent().setKeywords(UserSession.getCurrent().getKeywords() + publicNotices.getNoticeTitle() + ", ");
        }

        if (highlightCount > 0) {
            setHighlighted(true);

        } else {

            setHighlighted(false);
        }
        if (schemeCount > 0) {
            setScheme(true);
        } else {
            setScheme(false);

        }
        if (usefulLinkCount > 0) {
            setUsefull(true);
        } else {
            setUsefull(false);
        }
    }

	public void getAnnouncement() {

        eipAnnouncement = iEipAnnouncementService.getGuestHomeEIPAnnouncement(UserSession.getCurrent().getOrganisation());
        int highlightCount = 0;
        final ListIterator<EIPAnnouncementHistory> listIterator = eipAnnouncement.listIterator();

        while (listIterator.hasNext()) {
            final EIPAnnouncementHistory eipAnnouncement = listIterator.next();
            //Compare highlighted date current date
            //if current date is less than highlighted date then set ishighlighted flag true else false
            Date highlightedDate=eipAnnouncement.getHighlightedDate();
    		Date currentDate=new Date();
    			if(eipAnnouncement.getHighlightedDate()!=null) {
    				int dateComp=highlightedDate.compareTo(currentDate);
    				if(dateComp<=0) {
    					eipAnnouncement.setIsHighlighted(true);
    					highlightCount++;
    				}
    				else {
    					eipAnnouncement.setIsHighlighted(false);
    				}
    			}
            final String directoryTree = eipAnnouncement.getAttach();
           File file = new File(getAppSession().getMessage("upload.physicalPath")+ MainetConstants.FILE_PATH_SEPARATOR+ directoryTree);
           eipAnnouncement.setFileSize((file.length() / 1024) + " KB");
            //eipAnnouncement.setAttachImage(downloadNewsImages(eipAnnouncement));
           eipAnnouncement.setAttachImage(downloadRecentAnno(eipAnnouncement));
            final FileStorageCache cache = eipAnnouncement.getFileStorageCache();

            try

            {
                if ((cache.getMultipleFiles() != null) && (cache.getMultipleFiles().getSize() > 0)) {
                    getFileNetClient().uploadFileList(cache.getFileList(), directoryTree);
                }
            } catch (final Exception ex) {
                throw new FrameworkException(ex.getLocalizedMessage());
            } finally {
                cache.flush();
            }
            if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
                UserSession.getCurrent()
                        .setKeywords(UserSession.getCurrent().getKeywords() + eipAnnouncement.getAnnounceDescEng() + ", ");
            } else {
                UserSession.getCurrent()
                        .setKeywords(UserSession.getCurrent().getKeywords() + eipAnnouncement.getAnnounceDescReg() + ", ");
            }
        }
		if (highlightCount > 0) {
        	setHighlightedAnnouncement(true);
        } else {
        	setHighlightedAnnouncement(false);
        }
    }

    public boolean isAgencyEmployee(final Employee agency) {

        final List<LookUp> lookUpList = super.getLookUpList(MainetConstants.NEC.PARENT);

        if ((agency.getEmplType() != null) && (agency.getEmplType() != 0l)) {
            for (final LookUp lookUp : lookUpList) {
                if ((lookUp.getLookUpId() == agency.getEmplType())

                        && (lookUp.getLookUpCode().equals(MainetConstants.NEC.ARCHITECT)
                                || lookUp.getLookUpCode().equals(MainetConstants.NEC.BUILDER)
                                || lookUp.getLookUpCode().equals(MainetConstants.NEC.CENTER)
                                || lookUp.getLookUpCode().equals(MainetConstants.NEC.CREMATORIA)
                                || lookUp.getLookUpCode().equals(MainetConstants.NEC.CYBER)
                                || lookUp.getLookUpCode().equals(MainetConstants.NEC.HOSPITAL)
                                || lookUp.getLookUpCode().equals(MainetConstants.NEC.ENGINEER)
                                || lookUp.getLookUpCode().equals(MainetConstants.NEC.ADVOCATE)
                                || lookUp.getLookUpCode().equals(MainetConstants.NEC.ADVERTISE)
                                || lookUp.getLookUpCode().equals(MainetConstants.NEC.TOWN_PLANNER)
                                || lookUp.getLookUpCode().equals(MainetConstants.NEC.STRUCTURAL_ENGINEER)
                                || lookUp.getLookUpCode().equals(MainetConstants.NEC.SUPERVISOR))) {
                    ((EIPMenuManager) UserSession.getCurrentMenuManager()).setAgencyType(true);
                    return true;
                }
            }
            return false;
        } else {
            ((EIPMenuManager) UserSession.getCurrentMenuManager()).setAgencyType(false);
            return false;
        }
    }

    /**
     * @return the currentEmpForEditProfile
     */
    public Employee getCurrentEmpForEditProfile() {
        return currentEmpForEditProfile;
    }

    /**
     * @param currentEmpForEditProfile the currentEmpForEditProfile to set
     */
    public void setCurrentEmpForEditProfile(final Employee currentEmpForEditProfile) {
        this.currentEmpForEditProfile = currentEmpForEditProfile;
    }

    /**
     * @return the empNameForEditProfile
     */
    public String getEmpNameForEditProfile() {
        return empNameForEditProfile;
    }

    /**
     * @param empNameForEditProfile the empNameForEditProfile to set
     */
    public void setEmpNameForEditProfile(final String empNameForEditProfile) {
        this.empNameForEditProfile = empNameForEditProfile;
    }

    public void saveEditedInformation(final Employee modifiedEmployee) {
        final List<LookUp> genderLookUp = getLevelData(MainetConstants.GENDER);        
       try {
    	String   citizenImagePath = Utility.getProfileImagePath();
    	   uploadedFile =	super.uploadAndGetFilePath(getFileNetClient(), citizenImagePath);
    	   if (!uploadedFile.isEmpty() && !uploadedFile.equalsIgnoreCase("")) {
    	          
               uploadedFile = citizenImagePath + File.separator + uploadedFile;
               getCurrentEmpForEditProfile().setEmpphotopath(uploadedFile);
           }else {
        	   setLogInUserImage(MainetConstants.BLANK);
           }
	} catch (Exception e) {
		// TODO Auto-generated catch block
		
	}
       
        for (final LookUp lookUp : genderLookUp) {
            if ((modifiedEmployee.getEmpGender() != null)
                    && modifiedEmployee.getEmpGender().equalsIgnoreCase(String.valueOf(lookUp.getLookUpId()))) {
                modifiedEmployee.setEmpGender(lookUp.getLookUpCode());
            }
        }
        iEmployeeService.saveEditProfileInfo(modifiedEmployee);
    }

    public boolean isUniqueEmailAddress(final String empEMail) {
        boolean isUnique = false;

        final List<Employee> employeeList = iEmployeeService.getEmployeeByEmpEMailAndType(empEMail,
                UserSession.getCurrent().getEmployee().getEmplType(), UserSession.getCurrent().getOrganisation(),
                MainetConstants.IsDeleted.ZERO, false);

        final LookUp lookUp = getCitizenLooUp();
        if ((employeeList == null) || (employeeList.size() == 0)) {
            return true;
        } else {
            for (final Employee employee : employeeList) {

                if (employee.getEmplType() != null) {
                    if ((employee.getEmplType() == lookUp.getLookUpId())) {
                        return false;
                    } else {
                        isUnique = true;
                    }
                } else {
                    isUnique = true;

                }

            }

            return isUnique;
        }

    }

    public LookUp getCitizenLooUp() {
        final List<LookUp> lookUpList = super.getCitizenLookUpList(MainetConstants.NEC.PARENT);

        for (final LookUp lookUp : lookUpList) {
            if (lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.NEC.CITIZEN)) {
                return lookUp;
            }
        }
        return null;
    }

    public String setEmailId(final String mailId) {
        final Employee updateEmployee = UserSession.getCurrent().getEmployee();
        if (updateEmployee.getEmpGender().length() > 1) {
            updateEmployee.setEmpGender(getNonHierarchicalLookUpObject(new Long(updateEmployee.getEmpGender())).getLookUpCode());
        }
        updateEmployee.setEmpemail(mailId);
        iEmployeeService.saveEmployee(updateEmployee);

        if (updateEmployee != null) {
            UserSession.getCurrent().setEmployee(updateEmployee);
        }
        return null;
    }

    public void setEmpGender() {
        final List<LookUp> genderLookUp = getLevelData(MainetConstants.GENDER);
        if (CollectionUtils.isNotEmpty(genderLookUp)) {
            for (final LookUp lookUp : genderLookUp) {
                if ((getCurrentEmpForEditProfile().getEmpGender() != null)
                        && getCurrentEmpForEditProfile().getEmpGender().equalsIgnoreCase(lookUp.getLookUpCode())) {
                    getCurrentEmpForEditProfile().setEmpGender(lookUp.getLookUpId() + MainetConstants.BLANK);
                    this.setEmpGenderCode(lookUp.getLookUpCode());
                }
            }
        } else {
            LOG.error("{} Prefix not Loaded" + MainetConstants.GENDER);
        }
    }

    public void loadProfileData(final HttpServletRequest request) {
        final HttpSession httpSession = request.getSession(false);
        final LookUp mayorlookUp = getProfileMasterSection(PrefixConstants.Prefix.PMS, MainetConstants.DEPT_SHORT_NAME.MAYOR);
        final LookUp deputyMayorlookUp = getProfileMasterSection(PrefixConstants.Prefix.PMS,
                MainetConstants.DEPT_SHORT_NAME.DEPUTY_MAYOR);
        final LookUp commissionerlookUp = getProfileMasterSection(PrefixConstants.Prefix.PMS,
                PrefixConstants.Prefix.COMMISSIONER);
        final LookUp deputyCommissionerlookUp = getProfileMasterSection(PrefixConstants.Prefix.PMS,
                PrefixConstants.Prefix.DEPUTY_COMMISSIONER);
        final IProfileMasterService iProfileMasterService = ApplicationContextProvider.getApplicationContext()
                .getBean(IProfileMasterService.class);
        final List<ProfileMasterHistory> profileMastersList = iProfileMasterService.getGuestAllProfileMaster(
                Arrays.asList(mayorlookUp, deputyMayorlookUp, commissionerlookUp, deputyCommissionerlookUp),
                UserSession.getCurrent().getOrganisation());

        if (CollectionUtils.isNotEmpty(profileMastersList)) {
            final Map<Long, ProfileMasterHistory> profMasterMap = new HashMap<>();
            ProfileMasterHistory mayor_profile = new ProfileMasterHistory();
            ProfileMasterHistory deputyMayor_profile = new ProfileMasterHistory();
            ProfileMasterHistory commissioner_profile = new ProfileMasterHistory();
            ProfileMasterHistory deputyCommissioner_profile = new ProfileMasterHistory();
            for (final ProfileMasterHistory eachProfileMaster : profileMastersList) {

                if (!profMasterMap.containsKey(eachProfileMaster.getCpdSection())) {
                    profMasterMap.put(eachProfileMaster.getCpdSection(), eachProfileMaster);
                }

            }
            for (final Long eachId : profMasterMap.keySet()) {

                if (mayorlookUp != null && eachId == mayorlookUp.getLookUpId()) {	
                    mayor_profile = profMasterMap.get(eachId);
                    continue;
                }

                if (deputyMayorlookUp != null && eachId == deputyMayorlookUp.getLookUpId()) {
                    deputyMayor_profile = profMasterMap.get(eachId);
                    continue;
                }

                if (commissionerlookUp != null && eachId == commissionerlookUp.getLookUpId()) {
                    commissioner_profile = profMasterMap.get(eachId);
                    continue;
                }
                if (deputyCommissionerlookUp != null && eachId == deputyCommissionerlookUp.getLookUpId()) {
                    deputyCommissioner_profile = profMasterMap.get(eachId);
                    continue;
                }
            }
            Boolean isDMS = false;
            if (MainetConstants.YES.equals(ApplicationSession.getInstance().getMessage(MainetConstants.DMS_CONFIGURE))) {
                isDMS = true;
            }

            // createProfileImage
            if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {

                httpSession.setAttribute("mayorName", mayor_profile.getpNameEn());
                httpSession.setAttribute("mayorProfile", mayor_profile.getProfileEn());
                httpSession.setAttribute("EmailId", mayor_profile.getEmailId());
                httpSession.setAttribute("SummaryEng", mayor_profile.getSummaryEng());
                httpSession.setAttribute("mayorprofileImage",
                        isDMS ? getDmsProfileImage(mayor_profile) : createProfileImage(mayor_profile));
                httpSession.setAttribute("deputyMayorName", deputyMayor_profile.getpNameEn());
                httpSession.setAttribute("deputyMayorProfile", deputyMayor_profile.getProfileEn());
                httpSession.setAttribute("deputyMayorProfileImage",
                        isDMS ? getDmsProfileImage(deputyMayor_profile) : createProfileImage(deputyMayor_profile));
                httpSession.setAttribute("deputyMayorEmailId", deputyMayor_profile.getEmailId());
                httpSession.setAttribute("deputyMayorSummaryEng", deputyMayor_profile.getSummaryEng());
                httpSession.setAttribute("commissionerName", commissioner_profile.getpNameEn());
                httpSession.setAttribute("commissionerProfile", commissioner_profile.getProfileEn());
                httpSession.setAttribute("commissionerProfileImage",
                        isDMS ? getDmsProfileImage(commissioner_profile) : createProfileImage(commissioner_profile));
                httpSession.setAttribute("commissionerEmailId", commissioner_profile.getEmailId());
                httpSession.setAttribute("commissionerSummaryEng", commissioner_profile.getSummaryEng());

                httpSession.setAttribute("deputyCommissionerName", deputyCommissioner_profile.getpNameEn());
                httpSession.setAttribute("deputycommissionerProfile", deputyCommissioner_profile.getProfileEn());
                httpSession.setAttribute("deputycommissionerProfileImage",
                        isDMS ? getDmsProfileImage(deputyCommissioner_profile) : createProfileImage(deputyCommissioner_profile));
                httpSession.setAttribute("deputycommissionerEmailId", deputyCommissioner_profile.getEmailId());
                httpSession.setAttribute("deputycommissionerSummaryEng", deputyCommissioner_profile.getSummaryEng());

                httpSession.setAttribute("quickPayFlag",
                        ApplicationSession.getInstance().getMessage("database.client").toString());
                
                httpSession.setAttribute("mayorDtOfJoin",mayor_profile.getDtOfJoin());
                httpSession.setAttribute("deputyMayorDtOfJoin",deputyMayor_profile.getDtOfJoin()); 
                httpSession.setAttribute("commissionerDtOfJoin",commissioner_profile.getDtOfJoin());
                httpSession.setAttribute("deputycommissionerDtOfJoin",deputyCommissioner_profile.getDtOfJoin()); 
                
            } else {
                httpSession.setAttribute("mayorName", mayor_profile.getpNameReg());
                httpSession.setAttribute("mayorProfile", mayor_profile.getProfileReg());
                httpSession.setAttribute("SummaryEng", mayor_profile.getSummaryEng());
                httpSession.setAttribute("mayorprofileImage",
                        isDMS ? getDmsProfileImage(mayor_profile) : createProfileImage(mayor_profile));
                httpSession.setAttribute("deputyMayorName", deputyMayor_profile.getpNameReg());
                httpSession.setAttribute("deputyMayorProfile", deputyMayor_profile.getProfileReg());
                httpSession.setAttribute("deputyMayorProfileImage",
                        isDMS ? getDmsProfileImage(deputyMayor_profile) : createProfileImage(deputyMayor_profile));
                httpSession.setAttribute("deputyMayorSummaryEng", deputyMayor_profile.getSummaryEng());
                httpSession.setAttribute("commissionerName", commissioner_profile.getpNameReg());
                httpSession.setAttribute("commissionerProfile", commissioner_profile.getProfileReg());
                httpSession.setAttribute("commissionerProfileImage",
                        isDMS ? getDmsProfileImage(commissioner_profile) : createProfileImage(commissioner_profile));
                httpSession.setAttribute("commissionerSummaryEng", commissioner_profile.getSummaryEng());

                httpSession.setAttribute("deputyCommissionerName", deputyCommissioner_profile.getpNameReg());
                httpSession.setAttribute("deputycommissionerProfile", deputyCommissioner_profile.getProfileReg());
                httpSession.setAttribute("deputycommissionerProfileImage",
                        isDMS ? getDmsProfileImage(deputyCommissioner_profile) : createProfileImage(deputyCommissioner_profile));
                httpSession.setAttribute("deputycommissionerSummaryEng", deputyCommissioner_profile.getSummaryEng());
                httpSession.setAttribute("quickPayFlag",
                        ApplicationSession.getInstance().getMessage("database.client").toString());
                
                httpSession.setAttribute("mayorDtOfJoin",mayor_profile.getDtOfJoin());
                httpSession.setAttribute("deputyMayorDtOfJoin",deputyMayor_profile.getDtOfJoin()); 
                httpSession.setAttribute("commissionerDtOfJoin",commissioner_profile.getDtOfJoin());
                httpSession.setAttribute("deputycommissionerDtOfJoin",deputyCommissioner_profile.getDtOfJoin()); 
            }
        } else {

            httpSession.setAttribute("mayorName", MainetConstants.BLANK);
            httpSession.setAttribute("mayorProfile", MainetConstants.BLANK);
            httpSession.setAttribute("mayorprofileImage", MainetConstants.BLANK);
            httpSession.setAttribute("deputyMayorName", MainetConstants.BLANK);
            httpSession.setAttribute("deputyMayorProfile", MainetConstants.BLANK);
            httpSession.setAttribute("deputyMayorProfileImage", MainetConstants.BLANK);
            httpSession.setAttribute("commissionerName", MainetConstants.BLANK);
            httpSession.setAttribute("commissionerProfile", MainetConstants.BLANK);
            httpSession.setAttribute("commissionerProfileImage", MainetConstants.BLANK);
            httpSession.setAttribute("deputyCommissionerName", MainetConstants.BLANK);
            httpSession.setAttribute("deputycommissionerProfile", MainetConstants.BLANK);
            httpSession.setAttribute("deputycommissionerProfileImage", MainetConstants.BLANK);
            httpSession.setAttribute("quickPayFlag", MainetConstants.BLANK);
            
            httpSession.setAttribute("mayorDtOfJoin",MainetConstants.BLANK);
            httpSession.setAttribute("deputyMayorDtOfJoin",MainetConstants.BLANK); 
            httpSession.setAttribute("commissionerDtOfJoin",MainetConstants.BLANK);
            httpSession.setAttribute("deputycommissionerDtOfJoin",MainetConstants.BLANK); 
        }

    }

    private String createProfileImage(ProfileMasterHistory profileMaster) {
        String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR +
                UserSession.getCurrent().getOrganisation().getOrgid() + MainetConstants.FILE_PATH_SEPARATOR
                + MainetConstants.HOME_IMAGES;
        if (profileMaster.getImagePath() != null) {
            try {
                String str = Utility.downloadedFileUrl(profileMaster.getImagePath(), outputPath, getFileNetClient());
                return str;
            } catch (Exception e) {

                return MainetConstants.BLANK;
            }
        } else {
            return MainetConstants.BLANK;
        }

    }

    private String getDmsProfileImage(final ProfileMasterHistory profileMaster) {

        if ((profileMaster.getDocId() != null) && !profileMaster.getDocId().equals(MainetConstants.BLANK)) {
            try {
                final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR
                        +
                        UserSession.getCurrent().getOrganisation().getOrgid() + MainetConstants.FILE_PATH_SEPARATOR
                        + "HOME_IMAGES" + MainetConstants.FILE_PATH_SEPARATOR;

                final byte[] byteArray = dmsService.getDocumentById(profileMaster.getDocId());
                if (byteArray == null) {
                    throw new RuntimeException("webservice error");
                }

                return Utility.getFileUrl(outputPath, profileMaster.getDocName(), byteArray);
            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
        }
        return MainetConstants.BLANK;

    }

    private LookUp getProfileMasterSection(final String prefix, final String value) {
        LookUp quickLink = null;

        final List<LookUp> lookUps = getLevelData(prefix);

        if (lookUps == null) {
            return null;
        }

        LookUp lookUp = null;
        for (final LookUp lookUp2 : lookUps) {
            lookUp = lookUp2;

            if (lookUp.getLookUpCode().equals(value)) {
                quickLink = lookUp;
                break;
            }

        }
        return quickLink;

    }

    /**
     * @return the serviceList
     */
    public List<PortalService> getServiceList() {
        return serviceList;
    }

    /**
     * @param serviceList the serviceList to set
     */
    public void setServiceList(final List<PortalService> serviceList) {
        this.serviceList = serviceList;
    }

    /**
     * @return the allDepartment
     */
    public List<PortalDepartmentDTO> getAllDepartmentAndServices() {

        final Long groupid = iEntitlementService.getGroupIdByName(MainetConstants.MENU.DEFAULT_CITIZEN,
                getUserSession().getOrganisation().getOrgid());
        if (groupid != null) {
            AllDeptAndService = iPortalServiceMasterService.getAllDeptAndService(getUserSession().getOrganisation().getOrgid(),
                    groupid, UserSession.getCurrent().getLanguageId());
        }
        return AllDeptAndService;

    }

    /**
     * @param allDepartment the allDepartment to set
     */

    public List<PortalPhotoDTO> getAllphotos(final String Photogallery) {
        return iSectionService.findhomepagephotos(Photogallery);
    }

    public List<PortalVideoDTO> getAllvideos(final String videogallery) {
        return iSectionService.findhomepagevideos(videogallery);
    }

    public List<String> getAllhtml(final String CK_Editer3) {
        return iSectionService.getAllhtml(CK_Editer3);
    }

    public List<String> getAllhtmlPhoto(final String photoGalleryLinkName) {
    	List<String> list = iSectionService.getPhotoGallery(photoGalleryLinkName);
        return list;
    }
    
    public List<String> getAllhtmlVideo(final String videoGalleryLinkName) {
        List<String> list = iSectionService.getVideoGallery(videoGalleryLinkName);
        return list;
    }

    private class KeyValue implements Serializable {

        private static final long serialVersionUID = -4413589667550488856L;
        private final int key;
        private final String value;
        private final String fieldLabel;
        private final long sectiontype;

        public long getSectiontype() {
            return sectiontype;
        }

        public KeyValue(final int key, final String value, final String fieldLabel, final long sectiontype) {
            this.key = key;
            this.value = value;
            this.fieldLabel = fieldLabel;
            this.sectiontype = sectiontype;
        }

        /**
         * @return the key
         */
        public int getKey() {
            return key;
        }

        /**
         * @return the value
         */
        public String getValue() {
            return value;
        }

        public String getFieldLabel() {
            return fieldLabel;
        }
    }

    public List<List<LookUp>> gethomepagelink(final String sublink) {

        SubLinkMaster master = new SubLinkMaster();

        master = iSectionService.findSublinksbyename(sublink);
        if (master != null) {
            try {

                final List<List<LookUp>> dataList = new ArrayList<>();

                String fieldLabel;

                final List<KeyValue> fields = new ArrayList<>(0);

                for (final SubLinkFieldMapping temp : master.getSubLinkFieldMappings()) {
                    if (MainetConstants.IsDeleted.DELETE.equals(temp.getIsUsed())
                            && MainetConstants.IsDeleted.NOT_DELETE.equals(temp.getIsDeleted())) {

                        if (getUserSession().getLanguageId() == MainetConstants.ENGLISH) {

                            fieldLabel = temp.getFieldNameEn();
                        } else {
                            fieldLabel = temp.getFieldNameRg();
                        }
                        fields.add(this.new KeyValue(temp.getFieldType(), temp.getFiledNameMap().toLowerCase(), fieldLabel,
                                temp.getSectionType()));
                    }
                }

                final boolean hasDocs = hasDownloadDocs(fields);
                int count = 0;
                List<LookUp> lookUps = null;
                for (final SubLinkFieldDetails details : master.getSubLinkFieldDetails()) {
                    if (MainetConstants.IsDeleted.NOT_DELETE.equals(details.getIsDeleted())) {
                        lookUps = new ArrayList<>();
                        LookUp lookUp = null;
                        // To add details of text_filed,text_area and date picker only.
                        for (final KeyValue keyValue : fields) {
                            if ((keyValue.getKey() == MainetConstants.TEXT_FIELD)
                                    || (keyValue.getKey() == MainetConstants.TEXT_AREA)) {
                                lookUp = new LookUp();
                                lookUp.setDescLangFirst(String.valueOf(getPropertyVal(keyValue.getValue() + "_en", details)));
                                lookUp.setDescLangSecond(String.valueOf(getPropertyVal(keyValue.getValue() + "_rg", details)));
                                lookUp.setLookUpType(String.valueOf(keyValue.getKey()));
                                lookUp.setLookUpCode(keyValue.getFieldLabel());
                                lookUp.setLookUpExtraLongOne(keyValue.getSectiontype());
                                lookUp.setLookUpExtraLongOne(keyValue.getSectiontype());
                                lookUp.setLookUpExtraLongTwo(count);
                                lookUps.add(lookUp);

                            }

                            count++;
                        }

                        if (hasDocs) {
                            // Add details of Download Documents.
                            for (final KeyValue keyValue : fields) {
                                if (keyValue.getKey() == MainetConstants.ATTACHMENT_FIELD) {
                                    lookUp = new LookUp(details.getId(),
                                            String.valueOf(getPropertyVal(keyValue.getValue(), details)));
                                    lookUp.setDescLangSecond(
                                            lookUp.getDescLangSecond().replace(MainetConstants.operator.FORWARD_SLACE,
                                                    MainetConstants.operator.DOUBLE_BACKWARD_SLACE));
                                    lookUp.setDescLangSecond(lookUp.getDescLangSecond()
                                            .replace(MainetConstants.operator.DOUBLE_BACKWARD_SLACE, "\\\\"));
                                    lookUp.setLookUpType(String.valueOf(keyValue.getKey()));
                                    lookUp.setLookUpExtraLongOne(keyValue.getSectiontype());
                                    lookUp.setLookUpCode(keyValue.getFieldLabel());
                                    lookUps.add(lookUp);
                                }
                            }
                        }

                        dataList.add(lookUps);
                    }

                }
                // below method is called for setting the label according to language Id.

                return dataList;
            } catch (final NullPointerException e) {
                LOG.error(MainetConstants.ERROR_OCCURED, e);
                return null;
            }
        } else {
            return null;
        }
    }

    private Object getPropertyVal(final String propertyName, final Object clazz) {
        final BeanWrapper wrapper = new BeanWrapperImpl(clazz);

        if (wrapper.isReadableProperty(propertyName)) {
            final Object val = wrapper.getPropertyValue(propertyName);

            if (val != null) {
                if (val instanceof Date) {
                    return Utility.dateToString(Utility.converObjectToDate(val));
                } else if (val instanceof byte[]) {
                    final byte[] bytes = (byte[]) val;

                    return bytes;
                }
                return val.toString();
            } else {
                return MainetConstants.BLANK;
            }
        }

        return null;
    }

    private boolean hasDownloadDocs(final List<KeyValue> fields) {
        for (final KeyValue keyValue : fields) {
            if (keyValue.getKey() == MainetConstants.ATTACHMENT_FIELD) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return the allServicewithUrl
     */
    /*
     * public List<PortalQuickServiceDTO> getAllServicewithUrl() { final List<PortalQuickServiceDTO> dplist2 = new ArrayList<>(0);
     * final Set<PortalQuickServiceDTO> set2 = new LinkedHashSet<>(); for (final PortalQuickServiceDTO service :
     * AllServicewithUrl) { set2.add(service); } final Iterator<PortalQuickServiceDTO> iterator = set2.iterator(); while
     * (iterator.hasNext()) { final PortalQuickServiceDTO element = iterator.next(); if (!element.getServiceurl().equals('#')) {
     * dplist2.add(element); } } return dplist2; }
     */

    public String getorganisation() {
        return UserSession.getCurrent().getOrganisation().getDefaultStatus();
    }

    /**
     * @param allServicewithUrl the allServicewithUrl to set
     */

    public List<PublicNoticesHistory> getPublicNotices() {
        return publicNotices;
    }

    public void setPublicNotices(final List<PublicNoticesHistory> publicNotices) {
        this.publicNotices = publicNotices;
    }

	public List<Organisation> getOrganisationsList() {
        return iOrganisationService.findAllActiveOrganizationForHomePage("A", UserSession.getCurrent().getLanguageId());
    }

    public void setOrganisationsList(final List<Organisation> organisationsList) {
        this.organisationsList = organisationsList;
    }

    public Boolean getIsDMS() {
        if (MainetConstants.YES.equals(ApplicationSession.getInstance().getMessage(MainetConstants.DMS_CONFIGURE))) {
            return true;
        }
        return false;
    }

    public void setIsDMS(Boolean isDMS) {
        this.isDMS = isDMS;
    }



    public void setThemeMap() {
    	AbstractModel.themeMap = themeMasterService.getThemeMasterMapByOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
    }

    private String downloadNewsImages(EIPAnnouncementHistory newsImages) {
        String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR +
                UserSession.getCurrent().getOrganisation().getOrgid() + MainetConstants.FILE_PATH_SEPARATOR
                + MainetConstants.NEWS_IMAGES;
        if (newsImages.getAttachImage() != null) {
            try {
                return Utility.downloadedFileUrl(newsImages.getAttachImage(), outputPath, getFileNetClient());
            } catch (Exception e) {

                return MainetConstants.WHITE_SPACE;
            }
        } else {
            return MainetConstants.WHITE_SPACE;
        }

    }

    private String downloadRecentAnno(EIPAnnouncementHistory newsImages) {
        String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR +
                UserSession.getCurrent().getOrganisation().getOrgid() + MainetConstants.FILE_PATH_SEPARATOR
                + newsImages.getAnnounceDescEng();
        String imgArr[];
        String attachImg=newsImages.getAttachImage();
        String img="";
        String cacheImg="";
        if(attachImg!=null && attachImg.contains(",")) {
        	imgArr=attachImg.split(",");
        	for(String arr:imgArr) {
            	img=Utility.downloadedFileUrl(arr, outputPath, getFileNetClient()); 
            	cacheImg+=img+",";
            	}
        	if(cacheImg.length()!= 0) {	
        		cacheImg=cacheImg.substring(0, cacheImg.length()-1);
        	}
        }
        else {
        if (newsImages.getAttachImage() != null) {
            try {
                return Utility.downloadedFileUrl(newsImages.getAttachImage(), outputPath, getFileNetClient());
            } catch (Exception e) {

                return MainetConstants.WHITE_SPACE;
            }
        } else {
            return MainetConstants.WHITE_SPACE;
        }
        }
        return cacheImg;
    }
    
    private String downloadNewsImages(PublicNoticesHistory newsImages) {
        String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR +
                UserSession.getCurrent().getOrganisation().getOrgid() + MainetConstants.FILE_PATH_SEPARATOR
                + MainetConstants.NEWS_IMAGES;
        String imgArr[];
        String attachImg=newsImages.getImagePath();
        String img="";
        String cacheImg="";
        if(attachImg!=null && attachImg.contains(",")) {
        	imgArr=attachImg.split(",");
        	for(String arr:imgArr) {
            	img=Utility.downloadedFileUrl(arr, outputPath, getFileNetClient()); 
            	cacheImg+=img+",";
        	}
        	if(cacheImg.length()!= 0) {	
        		cacheImg=cacheImg.substring(0, cacheImg.length()-1);
        	}

        }
        else {
        if (newsImages.getImagePath() != null) {
            try {
                return Utility.downloadedFileUrl(newsImages.getImagePath(), outputPath, getFileNetClient());
            } catch (Exception e) {

                return MainetConstants.WHITE_SPACE;
            }
        } else {
            return MainetConstants.WHITE_SPACE;
        }
        }
        return cacheImg;

    }

	/*
	 * private String downloadNewsImages(PublicNoticesHistory newsImages) { String
	 * outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER +
	 * MainetConstants.FILE_PATH_SEPARATOR +
	 * UserSession.getCurrent().getOrganisation().getOrgid() +
	 * MainetConstants.FILE_PATH_SEPARATOR + MainetConstants.NEWS_IMAGES; if
	 * (newsImages.getImagePath() != null) { try { return
	 * Utility.downloadedFileUrl(newsImages.getImagePath(), outputPath,
	 * getFileNetClient()); } catch (Exception e) {
	 * 
	 * return MainetConstants.WHITE_SPACE; } } else { return
	 * MainetConstants.WHITE_SPACE; }
	 * 
	 * }
	 */

    public List<EIPContactUsHistory> getOrganisationContactList() {
    	setContactList();
        return organisationContactList;
    }

    public void setOrganisationContactList(List<EIPContactUsHistory> organisationContactList) {
        this.organisationContactList = organisationContactList;
    }

    public void setContactList() {
        this.organisationContactList = contactUsService
                .getAllContactUsListByOrganisation(UserSession.getCurrent().getOrganisation());
    }

    public boolean isHighlighted() {
        return isHighlighted;
    }

    public void setHighlighted(boolean isHighlighted) {
        this.isHighlighted = isHighlighted;
    }

    public boolean isUsefull() {
        return isUsefull;
    }

    public void setUsefull(boolean isUsefull) {
        this.isUsefull = isUsefull;
    }

    public boolean isScheme() {
        return isScheme;
    }

    public void setScheme(boolean isScheme) {
        this.isScheme = isScheme;
    }

    public List<DocumentDetailsVO> getCheckList() {
        return checkList;
    }

    public void setCheckList(List<DocumentDetailsVO> checkList) {
        this.checkList = checkList;
    }

    public String getServiceURL() {
        return serviceURL;
    }

    public void setServiceURL(String serviceURL) {
        this.serviceURL = serviceURL;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public List<String> getAllNodes() {

        final Long groupid = UserSession.getCurrent().getEmployee().getGmid();
        if (groupid != null) {
            smfaction = iPortalServiceMasterService.getSmfaction(getUserSession().getOrganisation().getOrgid(),
                    groupid, UserSession.getCurrent().getLanguageId());
        }
        return smfaction;

        // TODO Auto-generated method stub

    }

    public List<String> getSmfaction() {
        return smfaction;
    }

    public void setSmfaction(List<String> smfaction) {
        this.smfaction = smfaction;
    }
    
    public String getSubscriberEmail() {
		return subscriberEmail;
	}

	public void setSubscriberEmail(String subscriberEmail) {
		this.subscriberEmail = subscriberEmail;
	}

	public NewsLetterSubscriptionDTO getSubscription() {
        return subscription;
    }

    public void setSubscription(NewsLetterSubscriptionDTO subscription) {
        this.subscription = subscription;
    }
    
	public boolean isHighlightedAnnouncement() {
		return isHighlightedAnnouncement;
	}

	public void setHighlightedAnnouncement(boolean isHighlightedAnnouncement) {
		this.isHighlightedAnnouncement = isHighlightedAnnouncement;
	}

	public Long getOrgId() {
		return OrgId;
	}
	
	public void setOrgId(Long orgId) {
		OrgId = orgId;
	}
	
	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	
	public void getGalleryMaps() {
		galleryMap = null;
		galleryMap = new HashMap<>();
		final List<SubLinkFieldDetailsHistory> photo = subLinkMasterDAO
                .getAllhtml(UserSession.getCurrent().getOrganisation().getOrgid(),ApplicationSession.getInstance().getMessage("front.section.media.gallery.photo"));
		if(photo != null && photo.size() > 0) {
			final String photoPath = downloadGalleryImages(photo.get(0).getProfile_img_path());
			galleryMap.put(MainetConstants.FRONMEDIASECTION.PHOTO, photoPath);
		}
		final List<SubLinkFieldDetailsHistory> vedio = subLinkMasterDAO
                .getAllhtml(UserSession.getCurrent().getOrganisation().getOrgid(), ApplicationSession.getInstance().getMessage("front.section.media.gallery.video"));
		if(vedio != null && vedio.size() > 0) {
			final String videoPath = downloadGalleryVideoes(vedio.get(0).getAtt_video_path());
			galleryMap.put(MainetConstants.FRONMEDIASECTION.VIDEO, videoPath);
		}
	}
	

	public Map<String, String> getGalleryMap() {
		return galleryMap;
	}

	public void setGalleryMap(Map<String, String> galleryMap) {
		this.galleryMap = galleryMap;
	}
	
	private String downloadGalleryImages(String newsImages) {
        String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR +
                UserSession.getCurrent().getOrganisation().getOrgid() + MainetConstants.FILE_PATH_SEPARATOR
                + MainetConstants.GALLERY_IMAGES;
        String imgArr[];
        String attachImg=newsImages;
        String img="";
        String cacheImg="";
        if(attachImg!=null && attachImg.contains(MainetConstants.operator.COMA)) {
        	imgArr=attachImg.split(MainetConstants.operator.COMA);
        	for(String arr:imgArr) {
            	img=Utility.downloadedFileUrl(arr, outputPath, getFileNetClient()); 
            	cacheImg+=img+MainetConstants.operator.COMA;
        	}
        	cacheImg=cacheImg.substring(0, cacheImg.length()-1);

        }
        else {
        if (newsImages != null) {
            try {
                return Utility.downloadedFileUrl(newsImages, outputPath, getFileNetClient());
            } catch (Exception e) {

                return MainetConstants.WHITE_SPACE;
            }
        } else {
            return MainetConstants.WHITE_SPACE;
        }
        }
        return cacheImg;

    }
    
    private String downloadGalleryVideoes(String newsImages) {
        String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR +
                UserSession.getCurrent().getOrganisation().getOrgid() + MainetConstants.FILE_PATH_SEPARATOR
                + MainetConstants.GALLERY_VIDEO;
        String imgArr[];
        String attachImg=newsImages;
        String img="";
        String cacheImg="";
        if(attachImg!=null && attachImg.contains(MainetConstants.operator.COMA)) {
        	imgArr=attachImg.split(MainetConstants.operator.COMA);
        	for(String arr:imgArr) {
            	img=Utility.downloadedFileUrl(arr, outputPath, getFileNetClient()); 
            	cacheImg+=img+MainetConstants.operator.COMA;
        	}
        	cacheImg=cacheImg.substring(0, cacheImg.length()-1);

        }
        else {
        if (newsImages != null) {
            try {
                return Utility.downloadedFileUrl(newsImages, outputPath, getFileNetClient());
            } catch (Exception e) {

                return MainetConstants.WHITE_SPACE;
            }
        } else {
            return MainetConstants.WHITE_SPACE;
        }
        }
        return cacheImg;

    }

 public Feedback getFeedback() {
		return feedback;
	}

	public void setFeedback(Feedback feedback) {
		this.feedback = feedback;
	}

	public String getCaptchaSessionLoginValue() {
		return captchaSessionLoginValue;
	}

	public void setCaptchaSessionLoginValue(String captchaSessionLoginValue) {
		this.captchaSessionLoginValue = captchaSessionLoginValue;
	}
  
	//D128606 code added for DSCL MDDA dashboard

	@SuppressWarnings("unchecked")
	public List<MDDAApiResponseDto> getMDDAApiResponse() {

	  MDDAAPIReqDto req=new MDDAAPIReqDto();
		
	  req.setUsername(ApplicationSession.getInstance().getMessage("mdda.username"));
	  req.setPassword(ApplicationSession.getInstance().getMessage("mdda.password"));
	  RestTemplate restTemplate=new RestTemplate();
	  HttpHeaders headers=new HttpHeaders();
	  HttpEntity<String> requestEntity = null;
	  List<Map<String,Object>> finalReponse=null;
	  
	  Object resp=null;
	  String lid=null;
	  
		
	  String urlForAutheticate=ApplicationSession.getInstance().getMessage("mdda.authenticate.url");
	  String urlForSummary=ApplicationSession.getInstance().getMessage("mdda.summary.url");
	  
	  LinkedHashMap< String , Object> responseMap=null;
	  if(req!=null && urlForAutheticate!=null) {
	   resp = JersyCall.callRestTemplateClient(req,urlForAutheticate);
	   LOG.info("The given URI is "+urlForAutheticate +"with given request entity is "+req);
	  }
	  responseMap=(LinkedHashMap<String , Object>)resp;
	
	  if(responseMap!=null) {
		  lid=String.valueOf(responseMap.get("lid"));
		  headers.set("lid", lid);
		  requestEntity = new HttpEntity<>(null, headers);
	  }
	  
	  ResponseEntity<Object> responseEntity=null;
	  
	  if(urlForSummary!=null && requestEntity!=null){
		  responseEntity = restTemplate.exchange(urlForSummary, HttpMethod.POST, requestEntity, Object.class);
		  LOG.info("The given URI is "+urlForSummary +"with request entity is "+requestEntity);
	  }else {
		  LOG.error("url is invalid please check "+urlForSummary);
	  }
		 
	   if(responseEntity!=null) {
	    	finalReponse=(List<Map<String,Object>>)responseEntity.getBody();	
	    }
	 
	  int Compounding=0; 
	  int SELF_COMPUNDING=0; 
	  int New_Submission=0; 
	  int One_Time_Settlement=0; 
	  int LAYOUT_APPROVAL=0; 
	  int OTHERS=0; 
	  int issued=0; 
	  int rejected=0; 
	  int approved=0; 
	  int inprogess=0; 
	  int first_total = 0;
	  int second_total =0;
	  int minYear=0;
	  int maxYear=0;
	  boolean yearFlag=true;
	 if(finalReponse!=null){
	 
	   for(Map<String,Object> MDDADto:finalReponse) {
		  if(MDDADto.get("subDesc").toString().trim().equalsIgnoreCase("Compounding")) {
				 map.put("COMPUNDING",++Compounding); 
				 first_total++;
			 }
		  else if(MDDADto.get("subDesc").toString().trim().equalsIgnoreCase("SELF Compounding")) {
			 map.put("SELF COMPUNDING",++SELF_COMPUNDING); 
			 first_total++;
		  } 
		  else if(MDDADto.get("subDesc").toString().trim().equalsIgnoreCase("New Submission")){
		      map.put("NEW SUBMISSION",++New_Submission); 
		      first_total++;
		  }
		  else if(MDDADto.get("subDesc").toString().trim().equalsIgnoreCase("One Time Settlement")){
		      map.put("ONE TIME SETTLEMENT",++One_Time_Settlement); 
		      first_total++;
		  }
		  else if(MDDADto.get("subDesc").toString().trim().equalsIgnoreCase("Layout Approval")){
		      map.put("LAYOUT APPROVAL",++LAYOUT_APPROVAL);
		      first_total++;
		  }
		  else if(MDDADto.get("subDesc").toString().trim().equalsIgnoreCase("others")){
		      map.put("OTHERS",++OTHERS); 
		      second_total++;
		  }
		
		  if(MDDADto.get("approvalStatus").toString().trim().equalsIgnoreCase("rejected")) {
			  mddastatus.put("REJECTED",++rejected);   
			  second_total++;
		  } 
		  else if(MDDADto.get("approvalStatus").toString().trim().equalsIgnoreCase("approved")){
			  mddastatus.put("APPROVED",++approved); 
			  second_total++;
			  
		  }else if(MDDADto.get("approvalStatus").toString().trim().equalsIgnoreCase("issued")) {
			  mddastatus.put("ISSUED",++issued); 
			  second_total++;
			 }
		  else if(MDDADto.get("approvalStatus").toString().trim().equalsIgnoreCase("")){
			  mddastatus.put("OTHERS",++inprogess); 
			  second_total++;
		  }
		  if(MDDADto.get("year").toString().trim() !=null) {
			 try {
				String year = MDDADto.get("year").toString().trim();
				int mYear = Integer.parseInt(year);
				if(yearFlag) {
					minYear = mYear;
					maxYear = mYear;
					yearFlag = false;
				}else{
					if(minYear >= mYear) {
						minYear = mYear;
					}
					if(maxYear <= mYear) {
						maxYear = mYear;
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		  }
      }
	   totalMDDA.put("first_total",first_total); 
	   totalMDDA.put("second_total",second_total); 
  }
	 totalMDDA.put("minYear",minYear);
	 totalMDDA.put("maxYear",maxYear);
	map.put("sizeOfMDDAService", map.size());
	mddastatus.put("sizeOfMDDAStatus", mddastatus.size());
	//mDDAApiResponseDto.addAll((Collection<? extends MDDAApiResponseDto>) finalReponse);
   return mDDAApiResponseDto;
    }

 public Map<String, Integer> getMddastatus() {
		return mddastatus;
	}

	public void setMddastatus(Map<String, Integer> mddastatus) {
		this.mddastatus = mddastatus;
	}

	public Map<String, Integer> getTotalMDDA() {
		return totalMDDA;
	}

	public void setTotalMDDA(Map<String, Integer> totalMDDA) {
		this.totalMDDA = totalMDDA;
	}
	
public Map<String, Integer> getMap() {
		return map;
	}

	public void setMap(Map<String, Integer> map) {
		this.map = map;
	}
	
	    public void getMayorAndCommissionerProfileList() {
	        final LookUp commissionerLookup =     getProfileMasterSection(PrefixConstants.Prefix.PMS,MainetConstants.DEPT_SHORT_NAME.DEPUTY_MAYOR);
	        final LookUp deputyLookUp =     getProfileMasterSection(PrefixConstants.Prefix.PMS,MainetConstants.FlagM);
	        commissinorProfile = iProfileMasterService.getAllProfileMasterCPDSection(commissionerLookup,
	                UserSession.getCurrent().getOrganisation(), MainetConstants.FlagY);
	        commissinorProfile.forEach(c->{
	        if(c.getImagePath()!=null && c.getImagePath()!=""){
	        	c.setImagePath(Utility.getImageDetails(c.getImagePath()));
	        }
	        });
	        mayorProfile = iProfileMasterService.getAllProfileMasterCPDSection(deputyLookUp,
	                UserSession.getCurrent().getOrganisation(), MainetConstants.FlagY);
	        mayorProfile.forEach(c->{
		        if(c.getImagePath()!=null && c.getImagePath()!=""){
		        	c.setImagePath(Utility.getImageDetails(c.getImagePath()));
		        }
		        });
	        
	    }
	    
	    public List<ProfileMaster> getCommissinorProfile() {
			return commissinorProfile;
		}

		public void setCommissinorProfile(List<ProfileMaster> commissinorProfile) {
			this.commissinorProfile = commissinorProfile;
		}
		
		public List<ProfileMaster> getMayorProfile() {
			return mayorProfile;
		}

		public void setMayorProfile(List<ProfileMaster> mayorProfile) {
			this.mayorProfile = mayorProfile;
		}
		
		  
		public String getMayorOrCommisnrFlag() {
			return mayorOrCommisnrFlag;
		}

		public void setMayorOrCommisnrFlag(String mayorOrCommisnrFlag) {
			this.mayorOrCommisnrFlag = mayorOrCommisnrFlag;
		}
		 public String getEmpGenderCode() {
				return empGenderCode;
			}

			public void setEmpGenderCode(String empGenderCode) {
				this.empGenderCode = empGenderCode;
			}

			@SuppressWarnings("unchecked")
			public List<TraficUpdateDTO> getTrafficUpdate() {
				RestTemplate restTemplate = new RestTemplate();
				restTemplate.getMessageConverters().add(0,
						new StringHttpMessageConverter(Charset.forName(MainetConstants.UTF8)));

				List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
				MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
				converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
				messageConverters.add(converter);
				restTemplate.setMessageConverters(messageConverters);

				HttpHeaders headers = new HttpHeaders();

				HttpEntity<String> requestEntity = null;
				List<TraficUpdateDTO> traficDtos = new ArrayList<TraficUpdateDTO>();
				 List<Map<String,Object>> mapResponse=null;
				Object resp = null;
				String traficUpateUrl = ApplicationSession.getInstance().getMessage("trafic.update.url");
				requestEntity = new HttpEntity<>(null, headers);
				ResponseEntity<Object> responseEntity = null;

				if (traficUpateUrl != null) {
					responseEntity = restTemplate.exchange(traficUpateUrl, HttpMethod.GET, requestEntity, Object.class);
					LOG.info("The given trafic update URI is " + traficUpateUrl);
				} else {
					LOG.error("url is invalid trafic please check " + traficUpateUrl);
				}

				if (responseEntity != null) {
					mapResponse = ( List<Map<String,Object>>) responseEntity.getBody();
				}
				
				 for(Map<String,Object> traifcMap:mapResponse) {
					 TraficUpdateDTO traficDto = new TraficUpdateDTO();
					 traficDto.setSignalSCN((String) traifcMap.get(MainetConstants.TraficUpdate.SignalSCN));
					 traficDto.setShortDescription((String) traifcMap.get(MainetConstants.TraficUpdate.ShortDescription));
					 try {
						 Double congection = Double.parseDouble((String) traifcMap.get(MainetConstants.TraficUpdate.Congestion)) ;
						 traficDto.setCongestion(congection);
					 }catch (Exception e) {
						LOG.error("Cannot cast to double value");
					}
						if( traficDto.getCongestion() <= 40 ) {
							traficDto.setStatus(MainetConstants.TraficUpdate.LOW);
						}else if(traficDto.getCongestion() >=41 && traficDto.getCongestion() <= 75) {
							traficDto.setStatus(MainetConstants.TraficUpdate.MEDIUM);
						}else if(traficDto.getCongestion() >=76 ){
							traficDto.setStatus(MainetConstants.TraficUpdate.HIGH);
						}
						traficDtos.add(traficDto);	
				 }
				if(traficDtos!=null && !traficDtos.isEmpty()) {
					traficDtos =traficDtos.stream().sorted(Comparator.comparingDouble(TraficUpdateDTO::getCongestion)).collect(Collectors.toList());
					this.setTraficUpdateAPIList(traficDtos);
					
				}
				return traficDtos;
			}

			
			public List<TraficUpdateDTO> getTraficUpdateAPIList() {
				return traficUpdateAPIList;
			}

			public void setTraficUpdateAPIList(List<TraficUpdateDTO> traficUpdateAPIList) {
				this.traficUpdateAPIList = traficUpdateAPIList;
			}
			
			
		
			public List<List<String>> getAllhtmlPhotoWithDownloadedLoc(final String photoGalleryLinkName) {
				List<List<String>> downList = new ArrayList<List<String>>();
				List<String> childList = new ArrayList<String>();
				String store = null;
				List<String> list = iSectionService.getPhotoGallery(photoGalleryLinkName);
				if (list != null && !list.isEmpty()) {
					for (String photoPath : list) {
						childList = new ArrayList<String>();
						if (photoPath != null && !photoPath.isEmpty()) {
							String[] album = photoPath.split(MainetConstants.operator.COMMA);
							for (String s : album) {
								store = Utility.getImageDetails(photoPath);
								if (store != null && !store.isEmpty()) {
									childList.add(store);
								}
							}
							if(childList!=null && !childList.isEmpty()) {
								downList.add(childList);
							}
							
						}
					}
				}
				return downList;
		    }

	public long getPasswordExpiryAlert() {
		long diff = 0;
		try {
			Date expDate = null;
			Date currDate = new Date();
			if (UserSession.getCurrent() != null && UserSession.getCurrent().getEmployee() != null
					&& UserSession.getCurrent().getEmployee().getEmpexpiredt() != null) {
				expDate = UserSession.getCurrent().getEmployee().getEmpexpiredt();
				long difference_In_Time = expDate.getTime() - currDate.getTime();
				diff = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return diff;

	}

	public String getHiddEmailId() {
		return hiddEmailId;
	}

	public void setHiddEmailId(String hiddEmailId) {
		this.hiddEmailId = hiddEmailId;
	}

	public String getHiddMobNo() {
		return hiddMobNo;
	}

	public void setHiddMobNo(String hiddMobNo) {
		this.hiddMobNo = hiddMobNo;
	}
}
