package com.abm.mainet.mobile.controller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.cms.domain.EIPAboutUsHistory;
import com.abm.mainet.cms.domain.EIPContactUsHistory;
import com.abm.mainet.cms.domain.EIPHomeImagesHistory;
import com.abm.mainet.cms.domain.FrequentlyAskedQuestions;
import com.abm.mainet.cms.domain.ProfileMaster;
import com.abm.mainet.cms.domain.PublicNoticesHistory;
import com.abm.mainet.cms.domain.SubLinkFieldDetailsHistory;
import com.abm.mainet.cms.domain.SubLinkMaster;
import com.abm.mainet.cms.dto.VIEWQuickLinkDTO;
import com.abm.mainet.cms.service.IAdminPublicNoticesService;
import com.abm.mainet.cms.service.IEIPAboutUsService;
import com.abm.mainet.cms.service.IEIPContactUsService;
import com.abm.mainet.cms.service.IEIPHomePageImageService;
import com.abm.mainet.cms.service.IFrequentlyAskedQuestionsService;
import com.abm.mainet.cms.service.IProfileMasterService;
import com.abm.mainet.cms.service.ISectionService;
import com.abm.mainet.cms.service.IVIEWQuickLinkService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.Profile_image;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.dms.client.FileNetApplicationClient;

@ServletSecurity(httpMethodConstraints = {
		@HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/citizenAPI")
public class CitizenAPI {
	
	private static final Logger LOG = Logger.getLogger(CitizenAPI.class);

	@Autowired
	private IFrequentlyAskedQuestionsService iFrequentlyAskedQuestionsService;
	
	   
    @Autowired
    private IEIPAboutUsService iEIPAboutUsService;

    @Autowired
    private IAdminPublicNoticesService iAdminPublicNoticesService;
    
    @Autowired
    private IEIPContactUsService contactUsService;
    
    @Autowired
    private IEIPHomePageImageService iEIPHomePageImageService;
    
    @Autowired
    private IProfileMasterService iProfileMasterService;
    
    @Autowired
    private ISectionService iSectionService;
    
        
    @RequestMapping(value = "/getFrequentlyAskedQuestions/{orgid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public  List<FrequentlyAskedQuestions> getFaq(@PathVariable long orgid) {
    	List<FrequentlyAskedQuestions> faqList = new ArrayList<FrequentlyAskedQuestions>();
		
		try {
			Organisation org = new Organisation();
			org.setOrgid(orgid);
		 faqList =	iFrequentlyAskedQuestionsService.getAllAdminFAQList(org, MainetConstants.FlagY);
		 return faqList;
		} catch (final Exception exception) {
			LOG.error("Exception occure during fetching AdminFAQList List");
			return faqList;
		}

	}
    
    
    @RequestMapping(value = "/getCitizenAboutUs/{orgid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public  EIPAboutUsHistory getCitizenAboutUs(@PathVariable long orgid) {
    	EIPAboutUsHistory abtUs = new EIPAboutUsHistory();
		
		try {
			Organisation org = new Organisation();
			org.setOrgid(orgid);
			abtUs =	iEIPAboutUsService.getGuestAboutUs(org, MainetConstants.IsDeleted.NOT_DELETE);
		 return abtUs;
		} catch (final Exception exception) {
			LOG.error("Exception occure during fetching AboutUs List");
			
			return abtUs;
		}
  
    }
    
   
    @RequestMapping(value = "/getCitizenPublicNotices/orgid/{orgid}/noticeType/{noticeType}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  		@ResponseStatus(HttpStatus.OK)
  		@ResponseBody
  		public  List<PublicNoticesHistory> getGuestCitizenPublicNotices(@PathVariable long orgid,@PathVariable String noticeType) {
  	    	List<PublicNoticesHistory> publicNotices = new ArrayList<PublicNoticesHistory>();
  	    	List<PublicNoticesHistory> opList = new ArrayList<PublicNoticesHistory>();
  	    	boolean category;
  			
  			try {
  				Organisation org = new Organisation();
  				org.setOrgid(orgid);
  				publicNotices =	iAdminPublicNoticesService.getGuestCitizenPublicNotices(org);
  		    	try {
  		    		
  					final LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.ENVIRNMENT_VARIABLE.GENERAL_PUBLIC_INFORMATION, MainetConstants.ENVIRNMENT_VARIABLE.ENV,org);
  					if (lookup != null && StringUtils.isNotBlank(lookup.getOtherField()) && "Y".equalsIgnoreCase(lookup.getOtherField())) {
  						category = true;
  					}else {
  						category = false;
  					}

  				} catch (Exception e) {
  					category = false;
  					LOG.error("DGP value not configure in ENV Perfix");
  				}
  				if(publicNotices  !=null && !publicNotices.isEmpty()) {
  						List<String> uploadedDocsUrl = new ArrayList<String>();
  						List<String> downloadedDocsUrl  = new ArrayList<String>();
  						Date currentDate = new Date();
  					for(PublicNoticesHistory i : publicNotices) {
  						
  		  				//if( ((noticeType.equals(MainetConstants.IMP_LINK_CODE)? (!i.getIsUsefullLink().equals("Q") && !i.getIsUsefullLink().equals("T") && !i.getIsUsefullLink().equals("P")) : (i.getIsUsefullLink() !=null  && i.getIsUsefullLink().equals(noticeType)) ))
  						if(category ? 
  								(i.getIsUsefullLink() !=null  && i.getIsUsefullLink().equals(noticeType)
  						        && i.getIssueDate()!=null 
  		  						&& i.getValidityDate()!=null 
  		  						&& (i.getIssueDate().before(currentDate)) 
  		  						&& (currentDate.before(i.getValidityDate()))) 
  								:
  		  						( ( (noticeType!=null && noticeType.equalsIgnoreCase(MainetConstants.QUOTATIONS_CODE) && i.getIsUsefullLink() ==null  && (i.getIsHighlighted() == null || i.getIsHighlighted().equals(MainetConstants.FlagN))) // quick links
  		  					    ||  (noticeType!=null && noticeType.equalsIgnoreCase(MainetConstants.IMP_LINK_CODE)  && i.getIsUsefullLink() !=null && i.getIsUsefullLink().equals(MainetConstants.FlagY)   && (i.getIsHighlighted() == null || i.getIsHighlighted().equals(MainetConstants.FlagN) )) // imp links
  		  				        ||  (noticeType!=null && noticeType.equalsIgnoreCase(MainetConstants.ON_GOING_PROJECTS_CODE) && i.getIsHighlighted() !=null && i.getIsHighlighted().equals(MainetConstants.FlagY)  && ( i.getIsUsefullLink() ==null  ||  i.getIsUsefullLink().equals(MainetConstants.FlagN))) )// public notices						
  		  						&& i.getIssueDate()!=null 
  	  		  					&& i.getValidityDate()!=null 
  	  		  					&& (i.getIssueDate().before(currentDate)) 
  	  		  					&& (currentDate.before(i.getValidityDate())) ) ) {
  		  					
  		  					
  						if(i.getProfileImgPath() !=null && !i.getProfileImgPath().isEmpty()) {
  							uploadedDocsUrl = Arrays.asList(i.getProfileImgPath().split(MainetConstants.operator.COMA));
  							if(uploadedDocsUrl != null && !uploadedDocsUrl.isEmpty()) {
  							uploadedDocsUrl.forEach(u->{
  								if(u!= null && !u.isEmpty()) {
  									if(Utility.getImageDetails(u,orgid) != null && !Utility.getImageDetails(u,orgid).isEmpty() ){
  										downloadedDocsUrl.add( Utility.getImageDetails(u,orgid) );
  						         	}
  								}
  							});
  							}
  							if(downloadedDocsUrl !=null && !downloadedDocsUrl.isEmpty()) {
  							i.setProfileImgPath(String.join(MainetConstants.operator.COMA, downloadedDocsUrl));
  							}else {
  								i.setProfileImgPath(MainetConstants.BLANK);
  							}
  							downloadedDocsUrl.clear();
  						}
  						if(i.getImagePath() !=null && !i.getImagePath().isEmpty()) {
  							uploadedDocsUrl = Arrays.asList(i.getImagePath().split(MainetConstants.operator.COMA));
  							if(uploadedDocsUrl != null && !uploadedDocsUrl.isEmpty()) {
  							uploadedDocsUrl.forEach(u->{
  								if(u!= null && !u.isEmpty()) {
  									if(Utility.getImageDetails(u,orgid) != null && !Utility.getImageDetails(u,orgid).isEmpty() ){
  										downloadedDocsUrl.add( Utility.getImageDetails(u,orgid) );
  						         	}
  								
  								}
  							});
  							}
  							if(downloadedDocsUrl !=null && !downloadedDocsUrl.isEmpty()) {
  							i.setImagePath(String.join(MainetConstants.operator.COMA, downloadedDocsUrl));
  							}else {
  								i.setImagePath(MainetConstants.BLANK);
  							}
  							downloadedDocsUrl.clear();
  						}
  						opList.add(i);
  					}
  					}
  					}
  			 return opList;
  			} catch (final Exception exception) {
  				LOG.error("Exception occure during fetching Public Notices List");
  				
  				return publicNotices;
  			}
  	  
  	    }
    
    @RequestMapping(value = "/getCitizenContactUs/{orgid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public  List<EIPContactUsHistory> getCitizenContactUs(@PathVariable long orgid) {
    	List<EIPContactUsHistory> contactList = new ArrayList<EIPContactUsHistory>();
		
		try {
			Organisation org = new Organisation();
			org.setOrgid(orgid);
			contactList =	contactUsService
	                .getAllContactUsListByOrganisation(org);
		 return contactList;
		} catch (final Exception exception) {
			LOG.error("Exception occure during fetching AboutUs List");
			
			return contactList;
		}
  
    }
    
    
	@RequestMapping(value = "/getImageList/{orgid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Map<String, String> getImageList(@PathVariable long orgid) {
		Map<String, String> imageMap = new HashMap<String, String>();
		AtomicInteger count = new AtomicInteger(0);
		try {
			Organisation org = new Organisation();
			org.setOrgid(orgid);
			List<EIPHomeImagesHistory> eipHomeImageList = iEIPHomePageImageService.getImagesList(org,
					MainetConstants.FlagS);
			eipHomeImageList.forEach(c -> {
				if (c.getMobileEnable() != null && c.getMobileEnable().equalsIgnoreCase(MainetConstants.FlagY)
						&& c.getImagePath() != null && c.getImagePath() != "") {
					String base64Image = Utility.encodeBase64Image(c.getImagePath(),
							FileNetApplicationClient.getInstance());
					if (base64Image != null && base64Image != "") {
						imageMap.put(MainetConstants.IMAGE + count.incrementAndGet(), base64Image);

					}
				}
			});
			return imageMap;
		} catch (final Exception exception) {
			LOG.error("Exception occure during fetching Image List");
			return imageMap;
		}

	}

	@RequestMapping(value = "/getProfile/{profileType}/{orgid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Map<String, String>> getProfileList(@PathVariable String profileType, @PathVariable long orgid) {
		List<Map<String, String>> profileMapList = new ArrayList<>();
		LookUp lookup = null;
		try {
			if (profileType != null && profileType != "") {
				Organisation org = new Organisation();
				org.setOrgid(orgid);
				lookup = getProfileMasterSection(PrefixConstants.Prefix.PMS, profileType, org);

				List<ProfileMaster> profileList = iProfileMasterService.getAllProfileMasterCPDSection(lookup, org,
						MainetConstants.FlagY);
				profileList.forEach(c -> {
					Map<String, String> prifileImageMap = new HashMap<String, String>();
					prifileImageMap.put(Profile_image.Name, c.getpNameEn());
					prifileImageMap.put(Profile_image.NameReg, c.getpNameReg());
					prifileImageMap.put(Profile_image.Designation, c.getDesignationEn());
					prifileImageMap.put(Profile_image.DesignationReg, c.getDesignationReg());
					prifileImageMap.put(Profile_image.ContactNumber, c.getSummaryEng());// mobile num is stored in this
																						// field
					prifileImageMap.put(Profile_image.Email, c.getEmailId());
					prifileImageMap.put(Profile_image.Image, "");
					if (c.getImagePath() != null && c.getImagePath() != "") {
						String base64Image = Utility.encodeBase64Image(c.getImagePath(),
								FileNetApplicationClient.getInstance());
						if (base64Image != null && base64Image != "") {
							prifileImageMap.put(Profile_image.Image, base64Image);

						}
					}
					profileMapList.add(prifileImageMap);

				});
			}
			return profileMapList;
		} catch (Exception e) {
			LOG.error("Exception occure during fetching Profile List");
			return profileMapList;
		}

	}

	private LookUp getProfileMasterSection(final String prefix, final String value, Organisation organisation) {
		LookUp quickLink = null;

		final List<LookUp> lookUps = getLookUpList(prefix, organisation);

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

	public List<LookUp> getLookUpList(final String lookUpCode, final Organisation organisation) {
		final List<LookUp> lookupList = ApplicationSession.getInstance().getHierarchicalLookUp(organisation, lookUpCode)
				.get(lookUpCode);
		LOG.error(lookUpCode + " lookupList method ::  ");
		if ((lookupList == null) || lookupList.isEmpty()) {
			LOG.error("Prefix not found on CItizenAPis Controller" + lookUpCode
					+ MainetConstants.operator.LEFT_SQUARE_BRACKET);
		}
		return lookupList;
	} 
	
	@RequestMapping(value = "/getQuickLinkList/orgid/{orgid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public VIEWQuickLinkDTO getQuickLinkList(@PathVariable Long orgid) {
		Organisation org = new Organisation();
		org.setOrgid(orgid);
		final IVIEWQuickLinkService iviewQuickLinkService = ApplicationContextProvider.getApplicationContext()
                .getBean(IVIEWQuickLinkService.class);
		VIEWQuickLinkDTO quickLinkList = iviewQuickLinkService.getQuickLinkListData(org);
		return quickLinkList;		
	} 
	
	@RequestMapping(value = "/getDetailQuickLinkData/{linkId}/{orgId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public SubLinkMaster getDetailQuickLinkData(@PathVariable Long linkId, @PathVariable Long orgId) {
		SubLinkMaster findSublinks = iSectionService.findSublinks(linkId);
		if(findSublinks!= null){
			List<String> uploadedDocsUrl = new ArrayList<String>();
			List<String> downloadedDocsUrl  = new ArrayList<String>();
			for(SubLinkFieldDetailsHistory subLinkDetail :findSublinks.getDetailsHistories()){
				if(subLinkDetail.getAtt_01() !=null && !subLinkDetail.getAtt_01().isEmpty()) {
						uploadedDocsUrl = Arrays.asList(subLinkDetail.getAtt_01().split(MainetConstants.operator.COMA));
						if(uploadedDocsUrl != null && !uploadedDocsUrl.isEmpty()) {
						uploadedDocsUrl.forEach(u->{
							if(u!= null && !u.isEmpty()) {
								if(Utility.getImageDetails(u,orgId) != null && !Utility.getImageDetails(u,orgId).isEmpty() ){
									downloadedDocsUrl.add(Utility.getImageDetails(u,orgId));
					         	}
							}
						});
						}
						if(CollectionUtils.isNotEmpty(downloadedDocsUrl)) {
							subLinkDetail.setImg_path(String.join(MainetConstants.operator.COMA, downloadedDocsUrl));
						}else {
							subLinkDetail.setImg_path(MainetConstants.BLANK);
						}
						downloadedDocsUrl.clear();
					}
			}
		}		
		return findSublinks;		
	} 
}
