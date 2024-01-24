package com.abm.mainet.water.service;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.dto.WaterNoDueDto;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.integration.dto.WebServiceResponseDTO;
import com.abm.mainet.common.integration.property.dto.PropertyDetailDto;
import com.abm.mainet.common.integration.property.dto.PropertyInputDto;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.water.dao.NewWaterRepository;
import com.abm.mainet.water.dao.WaterNoDuesCertificateDao;
import com.abm.mainet.water.datamodel.WaterRateMaster;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.domain.TbWtBillMasEntity;
import com.abm.mainet.water.domain.WaterNoDuesEntity;
import com.abm.mainet.water.dto.NewWaterConnectionReqDTO;
import com.abm.mainet.water.dto.NoDueCerticateDTO;
import com.abm.mainet.water.dto.NoDuesCertificateReqDTO;
import com.abm.mainet.water.dto.NoDuesCertificateRespDTO;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.repository.TbCsmrInfoRepository;
import com.abm.mainet.water.repository.TbWtBillMasJpaRepository;
import com.abm.mainet.water.ui.model.NoDuesCertificateModel;

import io.swagger.annotations.Api;


@WebService(endpointInterface = "com.abm.mainet.water.service.WaterNoDuesCertificateService")
@Api(value = "/waternoduescertificate")
@Path("/waternoduescertificate")
@Service
public class WaterNoDuesCertificateServiceImpl implements WaterNoDuesCertificateService {

    private static final Logger LOGGER = Logger.getLogger(WaterNoDuesCertificateServiceImpl.class);

    @Resource
    private NewWaterRepository waterRepository;
    
    @Autowired
    private BRMSWaterService brmsWaterService;

    @Resource
    private WaterNoDuesCertificateDao waterDao;

    @Resource
    IFileUploadService fileUploadService;

    @Resource
    ApplicationService applicationService;

    @Resource
    IFinancialYearService financialYearService;

    @Autowired
    private ServiceMasterService serviceMasterService;

    @Autowired
    private IChallanService iChallanService;

    @Resource
    private CommonService commonService;

    @Resource
    private WaterCommonService waterCommonService;

    @Autowired
    private BillMasterService billMasterService;
    @Autowired
    private TbCsmrInfoRepository csmrInfoRepository;
    
    @Autowired
    private WaterDisconnectionService waterDisconnectService;
    
    @Autowired
    private NewWaterConnectionService newWaterConnectionService;
    

	@Autowired
	private TbWtBillMasJpaRepository tbWtBillMasJpaRepository;
	
	@Autowired
	private TbWtBillMasService billMasService;


    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.service.WaterNoDuesCertificateService# getApplicationData(long, long)
     */
    @Override
    @Transactional(readOnly = true)
    public NoDueCerticateDTO getNoDuesApplicationData(final long apmApplicationId, final long orgid) {

        LOGGER.info("start the  getApplicationData");
        final NoDueCerticateDTO certificateRespDTO = new NoDueCerticateDTO();
        try {
            final WaterNoDuesEntity dto = waterDao.getApplicantInformationById(apmApplicationId, orgid);
            if (dto != null) {
                final TbKCsmrInfoMH csmrInfoMH = waterCommonService.getWaterConnectionDetailsById(dto.getCsIdn(),
                        orgid);
                final ServiceMaster serMst = serviceMasterService
                        .getServiceMasterByShortCode(MainetConstants.NoDuesCertificate.NO_DUE_CERTIFICATE, orgid);
                if (serMst != null) {
                    certificateRespDTO.setServiceName(serMst.getSmServiceName());
                    certificateRespDTO.setServiceNameMar(serMst.getSmServiceNameMar());
                }

                certificateRespDTO.setNoDuesCertiDate(UtilityService.convertDateToDDMMYYYY(new Date()));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(MainetConstants.DATE_FORMAT_UPLOAD);
                certificateRespDTO.setApplicantDate(simpleDateFormat.format(dto.getLmodDate()));
                certificateRespDTO.setApproveDate(new Date());
                certificateRespDTO.setApplicantName(csmrInfoMH.getCsName());
                certificateRespDTO.setConnectionNo(csmrInfoMH.getCsCcn());
                certificateRespDTO.setApplicantAdd(csmrInfoMH.getCsBadd());
                certificateRespDTO.setFromDate(dto.getFromYr());
                certificateRespDTO.setToDate(dto.getToYr());
                certificateRespDTO.setApplicationId(apmApplicationId);
                certificateRespDTO.setCertificateNo(String.valueOf(apmApplicationId));
                // data set for ward zone
                certificateRespDTO.setZone(csmrInfoMH.getCodDwzid1() != null
                        ? CommonMasterUtility.getHierarchicalLookUp(csmrInfoMH.getCodDwzid1(), orgid).getLookUpDesc()
                        : "NA");
                certificateRespDTO.setWard(csmrInfoMH.getCodDwzid2() != null
                        ? CommonMasterUtility.getHierarchicalLookUp(csmrInfoMH.getCodDwzid2(), orgid).getLookUpDesc()
                        : "NA");
                certificateRespDTO.setBlock(csmrInfoMH.getCodDwzid3() != null
                        ? CommonMasterUtility.getHierarchicalLookUp(csmrInfoMH.getCodDwzid3(), orgid).getLookUpDesc()
                        : "NA");
                certificateRespDTO.setStatus(MainetConstants.WebServiceStatus.SUCCESS);
            }
            certificateRespDTO.setStatus(MainetConstants.WebServiceStatus.FAIL);
            certificateRespDTO.setNoOfCopies(dto.getCaCopies().intValue());
        } catch (final Exception exception) {
            LOGGER.error("Start the getApplicationData", exception);
            certificateRespDTO.setStatus(MainetConstants.WebServiceStatus.FAIL);
        }
        return certificateRespDTO;
    }
    
    @Override
    @Transactional(readOnly = true)
    public WaterNoDueDto getNoDuesApplicationDataForScrutiny(final Long apmApplicationId, Long orgid) {

        LOGGER.info("start the  getApplicationData");
        WaterNoDueDto waterNoDueDto=new WaterNoDueDto();
        try {
            final WaterNoDuesEntity dto = waterDao.getApplicantInformationById(apmApplicationId, orgid);
            if (dto != null) {
                final TbKCsmrInfoMH csmrInfoMH = waterCommonService.getWaterConnectionDetailsById(dto.getCsIdn(),
                        orgid);
                if(csmrInfoMH !=null) {
                	 final TbWtBillMasEntity billMaster = waterDao.getWaterDues(csmrInfoMH.getCsIdn(),
                     		orgid);
                	 if(billMaster != null)
                		 waterNoDueDto.setWaterDueAmt(billMaster.getBmTotalAmount());
                	 else
                		 waterNoDueDto.setWaterDueAmt(0d);
                	 
                	 PropertyInputDto detailDto=new PropertyInputDto();
                     detailDto.setPropertyNo(csmrInfoMH.getPropertyNo());
                     detailDto.setOrgId(csmrInfoMH.getOrgId());
                     Double propertyDUeAmt = getPropertyDueAmt(detailDto);
                     waterNoDueDto.setPropDueAmt(propertyDUeAmt);
                }
               
                
                waterNoDueDto.setPropNo(csmrInfoMH.getPropertyNo());
                
                waterNoDueDto.setWaterConNo(csmrInfoMH.getCsCcn());
               
                waterNoDueDto.setAppNo(apmApplicationId);
               
            }
           
        } catch (final Exception exception) {
            LOGGER.error("Start the getApplicationData", exception);
        }
        return waterNoDueDto;
    }
    
    
    private Double getPropertyDueAmt(PropertyInputDto propertyInputDto) {
    	ResponseEntity<?> responseEntity = null;
    	Double dueAmount=null;
    	Object obj=new Object();
    	LookUp lookUp=null;
    	try {
    		lookUp =CommonMasterUtility.getValueFromPrefixLookUp("WDC", MainetConstants.ENV, UserSession.getCurrent().getOrganisation());
	    } catch (Exception e) {
	        LOGGER.error("No prefix found for ENV - CWF ", e);
	    }
    	if(lookUp != null && StringUtils.equals(MainetConstants.FlagY, lookUp.getOtherField())) {
	    	try {
	            responseEntity = RestClient.callRestTemplateClient(propertyInputDto, ServiceEndpoints.PROP_BY_PROP_NO_AND_FLATNO);
	            String d = new JSONObject(obj).toString();
	            try {
	            	PropertyDetailDto app = new ObjectMapper().readValue(d, PropertyDetailDto.class);
	            	dueAmount=app.getTotalOutsatandingAmt();
	            } catch (Exception ex) {
	                  throw new FrameworkException("Exception occured while fetching Asset details : ", ex);
	
	            }
	        } catch (Exception exception) {
	            throw new FrameworkException("Error occured while fetching Property detail ", exception);
	        }
    	}else {
    		dueAmount=0d;
    	}
    	return dueAmount;
    }
    
    
    

    @Override
    @Transactional(readOnly = true)
    public NoDuesCertificateReqDTO getConnectionDetailForNoDuesCer(final UserSession usersession,
            final String consumerNo, final NoDuesCertificateReqDTO noDuesCertificateReqDTO,
            final NoDuesCertificateModel noDuesCertificateModel) {

        LOGGER.info("start getConnectionDetail () in service Class");
        try {

            final NoDuesCertificateReqDTO requestDTO = new NoDuesCertificateReqDTO();
            requestDTO.setApplicantDTO(noDuesCertificateModel.getApplicantDetailDto());
            requestDTO.setConsumerNo(consumerNo);
            requestDTO.setOrgId(noDuesCertificateModel.getReqDTO().getOrgId());
            requestDTO.setServiceId(noDuesCertificateModel.getServiceId());
            requestDTO.setUserId(usersession.getEmployee().getEmpId());
            requestDTO.setEmpId(usersession.getEmployee().getEmpId());
            NoDuesCertificateReqDTO noDuesCertificateReqDTO1 = null;
            NoDuesCertificateRespDTO resDTO = null;

            if(Utility.isEnvPrefixAvailable(usersession.getOrganisation(), MainetConstants.ENV_SKDCL)){
            	resDTO = getWaterDuesByPropNoNConnNo(requestDTO);
            	
            	
            }else {
                resDTO = populateNoDuesCertificateResp(requestDTO);
            }
            if (resDTO != null) {
                noDuesCertificateReqDTO1 = new NoDuesCertificateReqDTO();
                noDuesCertificateReqDTO.setConsumerName(resDTO.getConsumerName());
               //D#146950
                 noDuesCertificateReqDTO.setDuesAmount(resDTO.getAmount());
                if (resDTO.getConsumerAddress() != null) {
                    noDuesCertificateReqDTO.setConsumerAddress(resDTO.getConsumerAddress());
                } else {
                    noDuesCertificateReqDTO.setConsumerAddress(resDTO.getCsAdd());
                }
                for (final Map.Entry<String, Double> map : resDTO.getDuesList().entrySet()) {
                	if(MainetConstants.NoDuesCertificate.PROPERTYDUES.equals(map.getKey())) {
                		noDuesCertificateReqDTO.setPropDueAmt(map.getValue());
                	}
                    noDuesCertificateReqDTO.setWaterDues(map.getKey());
                    noDuesCertificateReqDTO.setDuesAmount(map.getValue());
                }
                noDuesCertificateReqDTO.setDues(resDTO.isDues());
                noDuesCertificateModel.setResponseDTO(resDTO);
                noDuesCertificateReqDTO.setApplicantDTO(noDuesCertificateModel.getApplicantDetailDto());
                noDuesCertificateReqDTO.setConsumerNo(consumerNo);
                noDuesCertificateReqDTO.setOrgId(noDuesCertificateModel.getOrgId());
                noDuesCertificateReqDTO.setServiceId(noDuesCertificateModel.getServiceId());
                noDuesCertificateReqDTO.setUserId(usersession.getEmployee().getEmpId());
                noDuesCertificateReqDTO.setEmpId(usersession.getEmployee().getEmpId());
                noDuesCertificateReqDTO.setMobileNo(resDTO.getCsContactno());
                noDuesCertificateReqDTO.setEmail(resDTO.getEmail());
                noDuesCertificateModel.setCheckListApplFlag(resDTO.getCheckListApplFlag());
                noDuesCertificateModel.setConnectionSize(resDTO.getCsCcnsize());
                noDuesCertificateReqDTO.setCcnDuesList(resDTO.getCcnDuesList());
                //D#146950
                noDuesCertificateReqDTO.setErrorMsg(resDTO.getErrorMsg());
                if (resDTO.isBillGenerated()) {
                    noDuesCertificateReqDTO.setBillGenerated(true);
                } else {
                    noDuesCertificateReqDTO.setBillGenerated(false);
                }

            } else {
                noDuesCertificateModel.setResponseDTO(resDTO);
            }
          

        } catch (final Exception exception) {
            LOGGER.info("Exception occur in getConnectionDetail()", exception);

        }
        return noDuesCertificateReqDTO;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rest.water.service.WaterNoDuesCertificateService #getWaterConnectionDetail
     * (com.abm.mainetservice.rest.water.bean.NoDuesCertificateReqDTO)
     */

    @SuppressWarnings("unused")
	@Override
    @Transactional(readOnly = true)
    public NoDuesCertificateRespDTO populateNoDuesCertificateResp(final NoDuesCertificateReqDTO requestDTO) {

        LOGGER.info("Start the getWaterConnectionDetail");
        NoDuesCertificateRespDTO certificateRespDTO = null;
        final Map<String, Double> map = new HashMap<>();
        List<String> errorList=new ArrayList<>();
        try {
            final Organisation organisation = new Organisation();
            organisation.setOrgid(requestDTO.getOrgId());
            final TbKCsmrInfoMH csmrInfoMH = waterCommonService
                    .fetchConnectionDetailsByConnNo(requestDTO.getConsumerNo(), requestDTO.getOrgId());

            if (csmrInfoMH != null) {
                certificateRespDTO = new NoDuesCertificateRespDTO();
                certificateRespDTO.setConsumerNo(csmrInfoMH.getCsCcn());
                certificateRespDTO.setConsumerAddress((csmrInfoMH.getCsAdd() != null ? csmrInfoMH.getCsAdd()
                        : MainetConstants.BLANK) + MainetConstants.WHITE_SPACE
                        + (csmrInfoMH.getCsLanear() != null ? csmrInfoMH.getCsLanear() : MainetConstants.BLANK)
                        + MainetConstants.WHITE_SPACE
                        + (csmrInfoMH.getCsRdcross() != null ? csmrInfoMH.getCsRdcross() : MainetConstants.WHITE_SPACE)
                        + MainetConstants.WHITE_SPACE
                        + (csmrInfoMH.getCsBldplt() != null ? csmrInfoMH.getCsBldplt() : MainetConstants.BLANK));
                certificateRespDTO.setConsumerName(csmrInfoMH.getCsName());
                certificateRespDTO.setCsTitle(csmrInfoMH.getCsTitle());
                certificateRespDTO.setCsMname(csmrInfoMH.getCsMname());
                certificateRespDTO.setCsLname(csmrInfoMH.getCsLname());
                certificateRespDTO.setCsName(csmrInfoMH.getCsName());
                certificateRespDTO.setConnectionId(csmrInfoMH.getCsIdn());
                certificateRespDTO.setCsAdd(csmrInfoMH.getCsAdd());
                certificateRespDTO.setCsBldplt(csmrInfoMH.getCsBldplt());
                certificateRespDTO.setCsLanear(csmrInfoMH.getCsLanear());
                certificateRespDTO.setCsRdcross(csmrInfoMH.getCsRdcross());
                certificateRespDTO.setCsContactno(csmrInfoMH.getCsContactno());
                certificateRespDTO.setCsNoofusers(csmrInfoMH.getCsNoofusers());
                certificateRespDTO.setCsMeteredccn(csmrInfoMH.getCsMeteredccn());
                certificateRespDTO.setCsCcnsize(csmrInfoMH.getCsCcnsize());
                certificateRespDTO.setCsCcnstatus(CommonMasterUtility
                        .getNonHierarchicalLookUpObject(csmrInfoMH.getCsCcnstatus(), organisation).getDescLangFirst());
                final Organisation org = new Organisation();
                org.setOrgid(requestDTO.getOrgId());
                if (csmrInfoMH.getTrmGroup1() != null) {
                    certificateRespDTO.setUsageSubtype1(CommonMasterUtility
                            .getHierarchicalLookUp(csmrInfoMH.getTrmGroup1(), org).getDescLangFirst());
                }
                if (csmrInfoMH.getTrmGroup2() != null) {
                    certificateRespDTO.setUsageSubtype2(CommonMasterUtility
                            .getHierarchicalLookUp(csmrInfoMH.getTrmGroup2(), org).getDescLangFirst());
                }
                if (csmrInfoMH.getTrmGroup3() != null) {
                    certificateRespDTO.setUsageSubtype3(CommonMasterUtility
                            .getHierarchicalLookUp(csmrInfoMH.getTrmGroup3(), org).getDescLangFirst());
                }
                if (csmrInfoMH.getTrmGroup4() != null) {
                    certificateRespDTO.setUsageSubtype4(CommonMasterUtility
                            .getHierarchicalLookUp(csmrInfoMH.getTrmGroup4(), org).getDescLangFirst());
                }
                if (csmrInfoMH.getTrmGroup5() != null) {
                    certificateRespDTO.setUsageSubtype5(CommonMasterUtility
                            .getHierarchicalLookUp(csmrInfoMH.getTrmGroup5(), org).getDescLangFirst());
                }
                if (csmrInfoMH.getApplicantType() != null) {
                    certificateRespDTO.setApplicantType(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(csmrInfoMH.getApplicantType(), org).getDescLangFirst());
                }
                String propBillingMethod="";
                
				final TbWtBillMasEntity billMaster = waterDao.getWaterDues(csmrInfoMH.getCsIdn(),
						requestDTO.getOrgId());
				certificateRespDTO.setDues(false);             
				if (billMaster != null) {
					// This will check if current date is after or before bill to date
					boolean checkDateAfterOrBefore = Utility.compareDate(new Date(), billMaster.getBmTodt());
					if (!checkDateAfterOrBefore) {
						certificateRespDTO.setBillGenerated(true);
					} else {
						/// 127521 - To check no dues present till current date
						TbWtBillMasEntity waterBillEntity = waterDisconnectService.getWaterBillDues(
								csmrInfoMH.getCsIdn(), requestDTO.getOrgId());
						if (waterBillEntity != null && waterBillEntity.getBmTotalOutstanding() > 0) {
							certificateRespDTO.setDues(true);
						}
						map.put(MainetConstants.NoDuesCertificate.WATERDUES, billMaster.getBmTotalBalAmount());
					}
					
				} else {
                    map.put(MainetConstants.NoDuesCertificate.WATERDUES, 0d);
                    certificateRespDTO.setBillGenerated(true);
                }
                }
                certificateRespDTO.setDuesList(map);
                final ServiceMaster serviceMaster = serviceMasterService
                        .getServiceByShortName(MainetConstants.NoDuesCertificate.NO_DUE_CERTIFICATE, requestDTO.getOrgId());
                Long SmChklstVerifyId = serviceMaster.getSmChklstVerify();
                List<LookUp> lookUps = CommonMasterUtility.getLookUps("APL", organisation);
                for (LookUp lookUp : lookUps) {

                    if (SmChklstVerifyId == lookUp.getLookUpId()) {
                        certificateRespDTO.setCheckListApplFlag(lookUp.getLookUpCode());
                    }
                }
            /*
             * if (csmrInfoMH != null) { List<TbBillMas> tbBillMas =
             * billMasterService.getBillMasterListByUniqueIdentifier(csmrInfoMH.getCsIdn(), requestDTO.getOrgId()); if
             * (tbBillMas.isEmpty()) { certificateRespDTO.setBillGenerated(true); } }
             */

        } catch (final Exception exception) {
            LOGGER.error("Start the getWaterConnectionDetail", exception);
        }
        return certificateRespDTO;
    }
    
    private String getPropertyBillingMethod(final String propNo) throws ClassNotFoundException, LinkageError {
		Class<?> clazz = null;
		Object dynamicServiceInstance = null;
		String serviceClassName = null;
		serviceClassName = "com.abm.mainet.property.service.PropertyNoDuesCertificateServiceImpl";
		Long orgId= UserSession.getCurrent().getOrganisation().getOrgid();
		clazz = ClassUtils.forName(serviceClassName,
				ApplicationContextProvider.getApplicationContext().getClassLoader());
		dynamicServiceInstance = ApplicationContextProvider.getApplicationContext().getAutowireCapableBeanFactory()
				.autowire(clazz, 2, false);
		final Method method = ReflectionUtils.findMethod(clazz,MainetConstants.Property.GET_PROPERTY_BILLING_METHOD,
				new Class[] { String.class, Long.class });
		Object obj = (Object) ReflectionUtils.invokeMethod(method, dynamicServiceInstance,
				new Object[] { propNo, orgId });

		if (obj != null) {
			return  (String) new JSONObject(obj.toString()).get("billingMethod");
		}
		return null;
	}

    @Override
    @Transactional(readOnly = true)
    public WardZoneBlockDTO getWordZoneBlockByApplicationId(final Long applicationId, final Long serviceId,
            final Long orgId) {
        final WaterNoDuesEntity master = waterDao.getApplicantInformationById(applicationId, orgId);

        final WardZoneBlockDTO wardZoneDTO = new WardZoneBlockDTO();

        if (master != null) {

            if (master.getWtN1() != null) {
                wardZoneDTO.setAreaDivision1(master.getWtN1());
            }
            if (master.getWtN2() != null) {
                wardZoneDTO.setAreaDivision2(master.getWtN2());
            }
            if (master.getWtN3() != null) {
                wardZoneDTO.setAreaDivision3(master.getWtN3());
            }
            if (master.getWtN4() != null) {
                wardZoneDTO.setAreaDivision4(master.getWtN4());
            }
            if (master.getWtN5() != null) {
                wardZoneDTO.setAreaDivision5(master.getWtN5());
            }
        } else {
            wardZoneDTO.setAvailable(false);
        }

        return wardZoneDTO;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.service.WaterNoDuesCertificateService#saveForm (com.abm.mainet.water.dto.NoDuesCertificateReqDTO)
     */
    @Override
    @Transactional
    public NoDuesCertificateRespDTO saveForm(final NoDuesCertificateReqDTO requestDTO) {
        LOGGER.info("start the  saveForm");
        NoDuesCertificateRespDTO response = null;
        try {
            WaterNoDuesEntity entity = new WaterNoDuesEntity();
            response = new NoDuesCertificateRespDTO();

            final ApplicantDetailDTO applicantDetailDto = requestDTO.getApplicantDTO();
            applicantDetailDto.setOrgId(requestDTO.getOrgId());
            requestDTO.setTitleId(applicantDetailDto.getApplicantTitle());
            requestDTO.setfName(applicantDetailDto.getApplicantFirstName());
            requestDTO.setmName(applicantDetailDto.getApplicantMiddleName());
            requestDTO.setlName(applicantDetailDto.getApplicantLastName());
            requestDTO.setReferenceId(requestDTO.getConsumerNo());
            requestDTO.setRoadName(applicantDetailDto.getRoadName());
            requestDTO.setBlockName(applicantDetailDto.getVillageTownSub());
            requestDTO.setAreaName(applicantDetailDto.getAreaName());
            requestDTO.setPincodeNo(Long.valueOf(applicantDetailDto.getPinCode()));
            requestDTO.setMobileNo(applicantDetailDto.getMobileNo());
            requestDTO.setEmail(applicantDetailDto.getEmailId());
            requestDTO.setBplNo(applicantDetailDto.getBplNo());
            final Double proDues = 0D;
            final Double waterDues = requestDTO.getDuesAmount();
            final TbKCsmrInfoMH csmrInfoMH = waterDao.getWaterConnByConsNo(requestDTO);

            if (csmrInfoMH != null) {
                if (requestDTO.isFree()) { // free service
                    requestDTO.setPayStatus("F");
                }
                final Long applicationId = applicationService.createApplication(requestDTO);

                if ((applicationId != null) && (applicationId != 0)) {
                    requestDTO.setApplicationId(applicationId);
                    entity.setCsIdn(csmrInfoMH.getCsIdn());
                    entity.setCaCopies(requestDTO.getNoOfCopies());
                    entity.setCaNoticeno(null);
                    entity.setCaPropdues(proDues);
                    entity.setCaWaterDue(waterDues);
                    /*
                     * final List<Long> finList = Arrays.asList(requestDTO.getFinYear()); if (finList.size() > 1) { final
                     * FinancialYear dto2 = financialYearService.getFinincialYearsById(finList.get(0), requestDTO.getOrgId());
                     * final FinancialYear dto1 = financialYearService.getFinincialYearsById( finList.get(finList.size() - 1),
                     * requestDTO.getOrgId()); entity.setFromYr(dto1.getFaFromDate().toString().substring(0, 4));
                     * entity.setToYr(dto2.getFaToDate().toString().substring(0, 4)); } else { final FinancialYear dto1 =
                     * financialYearService.getFinincialYearsById(finList.get(0), requestDTO.getOrgId());
                     * entity.setFromYr(dto1.getFaFromDate().toString().substring(0, 4));
                     * entity.setToYr(dto1.getFaToDate().toString().substring(0, 4)); }
                     */
                    entity.setLangId(requestDTO.getLangId().intValue());
                    entity.setLmodDate(new Date());
                    entity.setLgIpMac(requestDTO.getLgIpMac());
                    entity.setOrgId(requestDTO.getOrgId());
                    entity.setPrFlag(MainetConstants.MENU.N);
                    entity.setRmAmount(requestDTO.getCharges());
                    entity.setWtN1(requestDTO.getApplicantDTO().getDwzid1());
                    entity.setWtN2(requestDTO.getApplicantDTO().getDwzid2());
                    entity.setWtV1(String.valueOf(applicationId));
                    final Employee employee = new Employee();
                    employee.setEmpId(requestDTO.getUserId());
                    entity.setUserId(employee);
                    entity = waterDao.saveFormData(entity);
                    response.setApplicationNo(applicationId);
                    response.setApplicationDate(new Date());
                    if ((requestDTO.getDocumentList() != null) && !requestDTO.getDocumentList().isEmpty()) {
                        fileUploadService.doFileUpload(requestDTO.getDocumentList(), requestDTO);
                    }

                    response.setStatus(MainetConstants.WebServiceStatus.SUCCESS);
                } else {
                    response.getErrorList().add("ApplicationId are not generated");
                    response.setErrorMsg("ApplicationId are not generated");
                    response.setStatus(MainetConstants.WebServiceStatus.FAIL);
                }
            }

        } catch (final Exception exception) {
            LOGGER.error("Start the saveForm", exception);
            response.getErrorList().add(exception.toString());
            response.setStatus(MainetConstants.WebServiceStatus.FAIL);
            response.setApplicationNo(0l);
        }
        return response;
    }

    @Override
    public List<WebServiceResponseDTO> validation(final NoDuesCertificateReqDTO collectionModuleReportReqVO,
            final String report) {

        WebServiceResponseDTO wsResponseDTO = null;
        final List<WebServiceResponseDTO> wsResponseList = new ArrayList<>();

        if ((collectionModuleReportReqVO.getOrgId() == null)
                || (collectionModuleReportReqVO.getOrgId() == MainetConstants.CommonConstant.ZERO_LONG)) {
            wsResponseDTO = new WebServiceResponseDTO();
            wsResponseDTO.setStatus(MainetConstants.WebServiceStatus.FAIL);
            wsResponseDTO.setErrorCode(MainetConstants.InputError.ERROR_CODE);
            wsResponseDTO.setErrorMsg(MainetConstants.InputError.ORGID_NOT_FOUND);
            wsResponseList.add(wsResponseDTO);
        }

        if ((collectionModuleReportReqVO.getUserId() == null)
                || (collectionModuleReportReqVO.getUserId() == MainetConstants.CommonConstant.ZERO_LONG)) {
            wsResponseDTO = new WebServiceResponseDTO();
            wsResponseDTO.setStatus(MainetConstants.WebServiceStatus.FAIL);
            wsResponseDTO.setErrorCode(MainetConstants.InputError.ERROR_CODE);
            wsResponseDTO.setErrorMsg(MainetConstants.InputError.UserName_Not_Found);
            wsResponseList.add(wsResponseDTO);
        }
        if (collectionModuleReportReqVO.getConsumerNo() == null) {
            wsResponseDTO = new WebServiceResponseDTO();
            wsResponseDTO.setStatus(MainetConstants.WebServiceStatus.FAIL);
            wsResponseDTO.setErrorCode(MainetConstants.InputError.ERROR_CODE);
            wsResponseDTO.setErrorMsg(MainetConstants.InputError.WaterConn_NO_Not_Found);
            wsResponseList.add(wsResponseDTO);
        }

        return wsResponseList;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.service.WaterNoDuesCertificateService# validationFromData
     * (com.abm.mainet.water.dto.NoDuesCertificateReqDTO, java.lang.String)
     */
    @Override
    public List<WebServiceResponseDTO> validationFromData(final NoDuesCertificateReqDTO requestDTO,
            final String formData) {
        WebServiceResponseDTO wsResponseDTO = null;
        final List<WebServiceResponseDTO> wsResponseList = new ArrayList<>();

        if ((requestDTO.getOrgId() == null) || (requestDTO.getOrgId() == MainetConstants.CommonConstant.ZERO_LONG)) {
            wsResponseDTO = new WebServiceResponseDTO();
            wsResponseDTO.setStatus(MainetConstants.WebServiceStatus.FAIL);
            wsResponseDTO.setErrorCode(MainetConstants.InputError.ERROR_CODE);
            wsResponseDTO.setErrorMsg(MainetConstants.InputError.ORGID_NOT_FOUND);
            wsResponseList.add(wsResponseDTO);
        }
        if ((requestDTO.getLangId() == null) || (requestDTO.getLangId() == MainetConstants.CommonConstant.ZERO_LONG)) {
            wsResponseDTO = new WebServiceResponseDTO();
            wsResponseDTO.setStatus(MainetConstants.WebServiceStatus.FAIL);
            wsResponseDTO.setErrorCode(MainetConstants.InputError.ERROR_CODE);
            wsResponseDTO.setErrorMsg(MainetConstants.InputError.LANG_ID_NOT_FOUND);
            wsResponseList.add(wsResponseDTO);
        }
        if ((requestDTO.getUserId() == null) || (requestDTO.getUserId() == MainetConstants.CommonConstant.ZERO_LONG)) {
            wsResponseDTO = new WebServiceResponseDTO();
            wsResponseDTO.setStatus(MainetConstants.WebServiceStatus.FAIL);
            wsResponseDTO.setErrorCode(MainetConstants.InputError.ERROR_CODE);
            wsResponseDTO.setErrorMsg(MainetConstants.InputError.UserName_Not_Found);
            wsResponseList.add(wsResponseDTO);
        }
        if ((requestDTO.getServiceId() == null)
                || (requestDTO.getServiceId() == MainetConstants.CommonConstant.ZERO_LONG)) {
            wsResponseDTO = new WebServiceResponseDTO();
            wsResponseDTO.setStatus(MainetConstants.WebServiceStatus.FAIL);
            wsResponseDTO.setErrorCode(MainetConstants.InputError.ERROR_CODE);
            wsResponseDTO.setErrorMsg(MainetConstants.InputError.SERVICE_ID_NOT_FOUND);
            wsResponseList.add(wsResponseDTO);
        }
        if ((requestDTO.getConsumerNo() == null) || requestDTO.getConsumerNo().isEmpty()) {
            wsResponseDTO = new WebServiceResponseDTO();
            wsResponseDTO.setStatus(MainetConstants.WebServiceStatus.FAIL);
            wsResponseDTO.setErrorCode(MainetConstants.InputError.ERROR_CODE);
            wsResponseDTO.setErrorMsg(MainetConstants.InputError.WaterConn_NO_Not_Found);
            wsResponseList.add(wsResponseDTO);
        }
        return wsResponseList;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.service.WaterNoDuesCertificateService#saveExeFormData(
     * com.abm.mainet.water.dto.NoDueCerticateDTO)
     */
    @Override
    @Transactional
    public Boolean saveExeFormData(final NoDueCerticateDTO nodueCertiDTO) {

        LOGGER.info("start the  saveExeFormData");
        Boolean flag = false;
        try {
            final WaterNoDuesEntity entity = waterDao.getApplicantInformationById(nodueCertiDTO.getApplicationId(),
                    nodueCertiDTO.getOrgId());
            entity.setWtD1(nodueCertiDTO.getApproveDate());
            entity.setWtD2(UtilityService.convertStringToDDMMMYYYYDate(nodueCertiDTO.getNoDuesCertiDate()));
            entity.setWtV2(nodueCertiDTO.getApproveBy());
            flag = waterDao.update(entity);
        }

        catch (final Exception exception) {
            LOGGER.error("Start the saveExeFormData", exception);
        }
        return flag;
    }

    @Override
    public boolean populateNoDuesPaymentDetails(final CommonChallanDTO offline,
            final NoDuesCertificateModel noDuesCertificateModel, final UserSession userSession) {
        LOGGER.info("start setPaymentDetail () in service Class");
        boolean setFalg = false;
        try {

            if (((offline.getOnlineOfflineCheck() != null)
                    && offline.getOnlineOfflineCheck().equals(PrefixConstants.NewWaterServiceConstants.NO))
                    || (noDuesCertificateModel.getCharges() > 0d)) {
                offline.setApplNo(noDuesCertificateModel.getResponseDTO().getApplicationNo());
                offline.setAmountToPay(noDuesCertificateModel.getCharges().toString());
                offline.setUserId(userSession.getEmployee().getEmpId());
                offline.setOrgId(userSession.getOrganisation().getOrgid());
                offline.setLangId(userSession.getLanguageId());
                offline.setLgIpMac(userSession.getEmployee().getEmppiservername());
                offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
                offline.setFaYearId(userSession.getFinYearId());
                offline.setFinYearStartDate(userSession.getFinStartDate());
                offline.setFinYearEndDate(userSession.getFinEndDate());
                offline.setServiceId(noDuesCertificateModel.getServiceId());
                offline.setApplicantName(noDuesCertificateModel.getApplicantDetailDto().getApplicantFirstName());
                offline.setMobileNumber(noDuesCertificateModel.getApplicantDetailDto().getMobileNo());
                offline.setEmailId(noDuesCertificateModel.getApplicantDetailDto().getEmailId());
                offline.setEmpType(userSession.getEmployee().getEmplType());
                for (final Map.Entry<Long, Double> entry : noDuesCertificateModel.getChargesMap().entrySet()) {
                    offline.getFeeIds().put(entry.getKey(), entry.getValue());
                }
                final ServiceMaster serviceMaster = serviceMasterService.getServiceByShortName(
                        MainetConstants.WaterServiceShortCode.WATER_NO_DUES, userSession.getOrganisation().getOrgid());
                if (serviceMaster != null) {
                    final Long deptId = serviceMaster.getTbDepartment().getDpDeptid();
                    offline.setDeptId(deptId);
                    offline.setOfflinePaymentText(CommonMasterUtility.getNonHierarchicalLookUpObject(
                            offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation()).getLookUpCode());
                    if ((offline.getOnlineOfflineCheck() != null)
                            && offline.getOnlineOfflineCheck().equals(PrefixConstants.NewWaterServiceConstants.NO)) {

                        final ChallanMaster challanMaster = iChallanService.InvokeGenerateChallan(offline);
                        if (challanMaster != null) {
                            offline.setChallanNo(challanMaster.getChallanNo());
                            offline.setChallanValidDate(challanMaster.getChallanValiDate());
                            noDuesCertificateModel.setOfflineDTO(offline);
                        }
                    }

                    setFalg = true;
                }
            }
        } catch (final Exception exception) {
            throw new FrameworkException("Exception occur in setPaymentDetail()", exception);

        }
        return setFalg;
    }

    @Override
    public void setCommonField(final Organisation organisation, final NoDuesCertificateModel noDuesCertificateModel) {

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        noDuesCertificateModel.getReqDTO().setOrgId(orgId);
        noDuesCertificateModel.setOrgId(orgId);
        final ServiceMaster serviceMaster = serviceMasterService.getServiceByShortName(
                MainetConstants.NoDuesCertificate.NO_DUE_CERTIFICATE,
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (serviceMaster != null) {
            final Long deptId = serviceMaster.getTbDepartment().getDpDeptid();
            noDuesCertificateModel.setServiceId(serviceMaster.getSmServiceId());
            noDuesCertificateModel.setServiceId(serviceMaster.getSmServiceId());
            noDuesCertificateModel.setDeptId(deptId);
            noDuesCertificateModel.getReqDTO().setDeptId(deptId);
            final Long langId = (long) UserSession.getCurrent().getLanguageId();
            noDuesCertificateModel.setLangId(langId);
            /*
             * Long SmChklstVerifyId = serviceMaster.getSmChklstVerify(); List<LookUp> lookUps =
             * CommonMasterUtility.getLookUps("APL", organisation); for (LookUp lookUp : lookUps) { if (SmChklstVerifyId ==
             * lookUp.getLookUpId()) { noDuesCertificateModel.setCheckListApplFlag(lookUp.getLookUpCode()); } }
             */

        } else {
            throw new FrameworkException("Service Matser Not fetch based on the service Short Name");
        }

    }

    @Override
    @Transactional
    public void initiateWorkflowForFreeService(NoDuesCertificateReqDTO requestDTO) {

        if (requestDTO.isFree()) { // free service
            boolean checklist = false;
            if ((requestDTO.getDocumentList() != null) && !requestDTO.getDocumentList().isEmpty()) {
                checklist = true;
            }
            final ApplicantDetailDTO applicantDetailDto = requestDTO.getApplicantDTO();
            ApplicationMetadata applicationData = new ApplicationMetadata();
            applicationData.setApplicationId(requestDTO.getApplicationId());
            applicationData.setIsCheckListApplicable(checklist);
            applicationData.setOrgId(requestDTO.getOrgId());
            applicantDetailDto.setServiceId(requestDTO.getServiceId());
            applicantDetailDto.setDepartmentId(requestDTO.getDeptId());
            commonService.initiateWorkflowfreeService(applicationData, applicantDetailDto);
        }
    }

    // Defect #126420 OTP should come to register mobile number against connection number.
    @Override
    @Transactional
    public int updateOTPServiceForWater(String mobileNo, String connectionNo, String otp, Date updatedDate) {
        LOGGER.info("start WaterNoDuesCertificateServiceImpl.updateOTPServiceForWater()");
        int count = 0;
        try {
            count = csmrInfoRepository.updateOTPServiceForWater(mobileNo, connectionNo, otp, updatedDate);
        } catch (final Exception exception) {
            LOGGER.error("Exception while updateOTPServiceForWater", exception);
        }
        return count;
    }

    // Defect #126420 OTP should come to register mobile number against connection number.
    @Override
    public RequestDTO doOTPVerificationServiceForWater(String mobileNo, String connectionNo) {
        RequestDTO requestDTO = new RequestDTO();
        TbKCsmrInfoMH csmrInfoMH = csmrInfoRepository.getOTPServiceForWater(mobileNo, connectionNo);
        if (csmrInfoMH != null) {
            if (csmrInfoMH.getMobileNoOTP() != null) {
                requestDTO.setOtpPass(csmrInfoMH.getMobileNoOTP());
            }
            if (csmrInfoMH.getUpdatedDate() != null) {
                requestDTO.setApplicationDate(csmrInfoMH.getUpdatedDate());
            }
            if (csmrInfoMH.getCsEmail() != null) {
                requestDTO.setEmail(csmrInfoMH.getCsEmail());
            }
            if (csmrInfoMH.getCsName() != null) {
                requestDTO.setfName(csmrInfoMH.getCsName());
            }
        }
        return requestDTO;
    }

    @Override
    @Transactional
    public WaterNoDuesEntity getNoDuesDetailsByApplId(Long applicationId, Long orgId) {
        return waterDao.getApplicantInformationById(applicationId, orgId);
    }
	
	@Override
	@Transactional
	@WebMethod(exclude = true)
	public Map<Long, Double> getLoiCharges(final Long applicationId, final Long serviceId, final Long orgId)
			throws CloneNotSupportedException {
		Map<Long, Double> chargeMap = new HashMap<>();
		NoDueCerticateDTO noDueCerticateDTO = getNoDuesApplicationData(applicationId,
				orgId);
		ServiceMaster serviceMaster = serviceMasterService.getServiceMaster(serviceId, orgId);
		//ApplicantDetailDTO applicantDetailDto = noDueCerticateDTO.getApplicantDTO();
		
		 final NoDuesCertificateReqDTO requestDTO = new NoDuesCertificateReqDTO();
       //  requestDTO.setApplicantDTO(noDuesCertificateModel.getApplicantDetailDto());
         requestDTO.setConsumerNo(noDueCerticateDTO.getConnectionNo());
         requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
         requestDTO.setServiceId(serviceId);
         requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
         requestDTO.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
         //NoDuesCertificateReqDTO noDuesCertificateReqDTO1 = null;
         final NoDuesCertificateRespDTO resDTO = populateNoDuesCertificateResp(requestDTO);
         
		
		final WSRequestDTO dto = new WSRequestDTO();
		dto.setModelName(MainetConstants.NewWaterServiceConstants.CHECKLIST_WATERRATEMASTER_MODEL);
        WSResponseDTO response = RestClient.callBRMS(dto, ServiceEndpoints.BRMSMappingURL.INITIALIZE_MODEL_URL);
		
        if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) { {
        	final List<Object> checklistModel = RestClient.castResponse(response, CheckListModel.class, 0);
        	final List<Object> waterRateMasterList = RestClient.castResponse(response, WaterRateMaster.class, 1);
        	List<Object> rateMaster = RestClient.castResponse(response, WaterRateMaster.class, 0);
			final WaterRateMaster WaterRateMaster = (WaterRateMaster) waterRateMasterList.get(0);
			final CheckListModel checkListModel2 = (CheckListModel) checklistModel.get(0);
			
			
			WaterRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            WaterRateMaster.setServiceCode("WND");
            WaterRateMaster.setDeptCode("WT");
           // WaterRateMaster.setIsBPL(model.getApplicantDetailDto().getIsBPL());
            //WaterRateMaster.setChargeApplicableAt(Long.toString(48));
				WaterRateMaster.setChargeApplicableAt(Long.toString(
						CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.ChargeApplicableAt.SCRUTINY,
								MainetConstants.NewWaterServiceConstants.CAA).getLookUpId()));
            WaterRateMaster.setUsageSubtype1(resDTO.getUsageSubtype1());
            WaterRateMaster.setMeterType(CommonMasterUtility.getCPDDescription(Long.valueOf(resDTO.getCsMeteredccn()),
                    PrefixConstants.D2KFUNCTION.ENGLISH_DESC));
            dto.setDataModel(WaterRateMaster);
			
			final WSResponseDTO res = brmsWaterService.getApplicableTaxes(dto);
			
			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(res.getWsStatus())) {
			
					final List<Object> rates = (List<Object>) res.getResponseObj();
					 final List<WaterRateMaster> requiredCHarges = new ArrayList<>();
					for (final Object rate : rates) {
						WaterRateMaster master1 = (WaterRateMaster) rate;
                        master1 = populateChargeModel(noDueCerticateDTO, master1);
							/*
							 * master1.setOrgId(87l); master1.setTaxCode("WT59"); master1.setTaxId(2242);
							 */
                        requiredCHarges.add(master1);
                        break;
					}
					
					dto.setDataModel(requiredCHarges);
					final WSResponseDTO output = brmsWaterService.getApplicableCharges(dto);
                  //  List<ChargeDetailDTO> detailDTOs = (List<ChargeDetailDTO>) output.getResponseObj();
					final List<?> waterRateList = RestClient.castResponse(output, WaterRateMaster.class);
					
					 List<ChargeDetailDTO> detailDTOs = (List<ChargeDetailDTO>) output.getResponseObj();
					 detailDTOs.forEach(detail->{
						 chargeMap.put(requiredCHarges.get(0).getTaxId(),detail.getChargeAmount());
					 });
				
			}
		}
		
	}
    return chargeMap;
}
	private WaterRateMaster populateChargeModel(final NoDueCerticateDTO noDueCerticateDTO, final WaterRateMaster chargeModel) {

        chargeModel.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        chargeModel.setServiceCode(PrefixConstants.NewWaterServiceConstants.WND);
       
        chargeModel.setFactor1("NA");
        chargeModel.setNoOfCopies(noDueCerticateDTO.getNoOfCopies());
        return chargeModel;

    }
	
	@POST
    @Path("/getWaterDuesByPropNoNConnNo")
    @Transactional(readOnly = true)
    @Consumes("application/json")
    @Override
    public NoDuesCertificateRespDTO getWaterDuesByPropNoNConnNo(@RequestBody NoDuesCertificateReqDTO requestDTO) {

        LOGGER.info("Start the getWaterConnectionDetail");
        NoDuesCertificateRespDTO certificateRespDTO = null;
        final Map<String, Double> map = new HashMap<>();
        try {
            final Organisation organisation = new Organisation();
            organisation.setOrgid(requestDTO.getOrgId());
            final TbKCsmrInfoMH csmrInfoMH = waterCommonService
                    .fetchConnectionDetailsByConnNo(requestDTO.getConsumerNo(), requestDTO.getOrgId());

            if (csmrInfoMH != null) {
                certificateRespDTO = new NoDuesCertificateRespDTO();
                certificateRespDTO.setConsumerNo(csmrInfoMH.getCsCcn());
                certificateRespDTO.setConsumerAddress((csmrInfoMH.getCsAdd() != null ? csmrInfoMH.getCsAdd()
                        : MainetConstants.BLANK) + MainetConstants.WHITE_SPACE
                        + (csmrInfoMH.getCsLanear() != null ? csmrInfoMH.getCsLanear() : MainetConstants.BLANK)
                        + MainetConstants.WHITE_SPACE
                        + (csmrInfoMH.getCsRdcross() != null ? csmrInfoMH.getCsRdcross() : MainetConstants.WHITE_SPACE)
                        + MainetConstants.WHITE_SPACE
                        + (csmrInfoMH.getCsBldplt() != null ? csmrInfoMH.getCsBldplt() : MainetConstants.BLANK));
                certificateRespDTO.setConsumerName(csmrInfoMH.getCsName());
                certificateRespDTO.setCsTitle(csmrInfoMH.getCsTitle());
                certificateRespDTO.setCsMname(csmrInfoMH.getCsMname());
                certificateRespDTO.setCsLname(csmrInfoMH.getCsLname());
                certificateRespDTO.setCsName(csmrInfoMH.getCsName());
                certificateRespDTO.setConnectionId(csmrInfoMH.getCsIdn());
                certificateRespDTO.setCsAdd(csmrInfoMH.getCsAdd());
                certificateRespDTO.setCsBldplt(csmrInfoMH.getCsBldplt());
                certificateRespDTO.setCsLanear(csmrInfoMH.getCsLanear());
                certificateRespDTO.setCsRdcross(csmrInfoMH.getCsRdcross());
                certificateRespDTO.setCsContactno(csmrInfoMH.getCsContactno());
                certificateRespDTO.setCsNoofusers(csmrInfoMH.getCsNoofusers());
                certificateRespDTO.setCsMeteredccn(csmrInfoMH.getCsMeteredccn());
                certificateRespDTO.setCsCcnsize(csmrInfoMH.getCsCcnsize());
                if(csmrInfoMH.getCsCcnstatus() != null) {
                	certificateRespDTO.setCsCcnstatus(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(csmrInfoMH.getCsCcnstatus(), organisation).getDescLangFirst());
                }
                
                final Organisation org = new Organisation();
                org.setOrgid(requestDTO.getOrgId());
                if (csmrInfoMH.getTrmGroup1() != null) {
                    certificateRespDTO.setUsageSubtype1(CommonMasterUtility
                            .getHierarchicalLookUp(csmrInfoMH.getTrmGroup1(), org).getDescLangFirst());
                }
                if (csmrInfoMH.getTrmGroup2() != null) {
                    certificateRespDTO.setUsageSubtype2(CommonMasterUtility
                            .getHierarchicalLookUp(csmrInfoMH.getTrmGroup2(), org).getDescLangFirst());
                }
                if (csmrInfoMH.getTrmGroup3() != null) {
                    certificateRespDTO.setUsageSubtype3(CommonMasterUtility
                            .getHierarchicalLookUp(csmrInfoMH.getTrmGroup3(), org).getDescLangFirst());
                }
                if (csmrInfoMH.getTrmGroup4() != null) {
                    certificateRespDTO.setUsageSubtype4(CommonMasterUtility
                            .getHierarchicalLookUp(csmrInfoMH.getTrmGroup4(), org).getDescLangFirst());
                }
                if (csmrInfoMH.getTrmGroup5() != null) {
                    certificateRespDTO.setUsageSubtype5(CommonMasterUtility
                            .getHierarchicalLookUp(csmrInfoMH.getTrmGroup5(), org).getDescLangFirst());
                }
                if (csmrInfoMH.getApplicantType() != null) {
                    certificateRespDTO.setApplicantType(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(csmrInfoMH.getApplicantType(), org).getDescLangFirst());
                }
                String propBillingMethod="";
                
                if (!StringUtils.isEmpty(csmrInfoMH.getPropertyNo())) {
                	NewWaterConnectionReqDTO dto = new NewWaterConnectionReqDTO();
                	dto.setOrgId(organisation.getOrgid());
                	dto.setPropertyNo(csmrInfoMH.getPropertyNo());
                	propBillingMethod = getPropertyBillingMethod(csmrInfoMH.getPropertyNo());                	
                	if(!StringUtils.isEmpty(propBillingMethod) && MainetConstants.FlagI.equals(propBillingMethod)) {
                		if(StringUtils.isEmpty(csmrInfoMH.getFlatNo())) {
                			throw new FrameworkException("Flat No is not present for property No whose billing method is Individual in water connection table:" + csmrInfoMH.getPropertyNo());
                		}else {
                			dto.setFlatNo(csmrInfoMH.getFlatNo());
                		}
                	}
                	certificateRespDTO.setDues(false); 
                	TbCsmrInfoDTO csmrDto = newWaterConnectionService.getPropertyDetailsByPropertyNumberNFlatNo(dto);
                	map.put(MainetConstants.NoDuesCertificate.PROPERTYDUES, csmrDto.getTotalOutsatandingAmt());   
                	if(csmrDto.getTotalOutsatandingAmt()>0) {
                    	certificateRespDTO.setDues(true); 
                	}
                	final Map<String, Double> amountMap = new HashMap<>();
                	List<TbKCsmrInfoMH> csmrList = waterDao.getWaterConnByPropNoNFlatNo(csmrInfoMH.getPropertyNo(), csmrInfoMH.getFlatNo(), organisation.getOrgid());
    				   
                	if(!csmrList.isEmpty()) {
						for (TbKCsmrInfoMH csmr : csmrList) {
							if(csmr.getCsCcn() != null) {
								TbWtBillMasEntity billMaster = waterDisconnectService
										.getWaterBillDues(csmrInfoMH.getCsIdn(), requestDTO.getOrgId());
								if (billMaster != null) {
									// This will check if current date is after or before bill to date
									/*boolean checkDateAfterOrBefore = Utility.compareDate(new Date(),
											billMaster.getBmTodt());
									if (!checkDateAfterOrBefore) {
										certificateRespDTO.setBillGenerated(true);
									} else {*/
										/// 127521 - To check no dues present till current date
										if (billMaster != null && billMaster.getBmTotalOutstanding() > 0) {
											certificateRespDTO.setDues(true);
										}
										amountMap.put(csmr.getCsCcn(), billMaster.getBmTotalBalAmount());
										if (map.get(MainetConstants.NoDuesCertificate.WATERDUES) != null) {
											double amnt = billMaster.getBmTotalBalAmount()
													+ map.get(MainetConstants.NoDuesCertificate.WATERDUES);
											map.put(MainetConstants.NoDuesCertificate.WATERDUES, amnt);
										} else {
											map.put(MainetConstants.NoDuesCertificate.WATERDUES,
													billMaster.getBmTotalBalAmount());
										}
									/*}*/
								} else {
									if (map.get(MainetConstants.NoDuesCertificate.WATERDUES) != null) {
										double amnt = 0d + map.get(MainetConstants.NoDuesCertificate.WATERDUES);
										map.put(MainetConstants.NoDuesCertificate.WATERDUES, amnt);
									} else {
										map.put(MainetConstants.NoDuesCertificate.WATERDUES, 0d);
									}
									map.put(MainetConstants.NoDuesCertificate.WATERDUES, 0d);
									//certificateRespDTO.setBillGenerated(true);
								}
							}
							
						}
						certificateRespDTO.setCcnDuesList(amountMap);
                	}
                	
                }
                certificateRespDTO.setDuesList(map);
                final ServiceMaster serviceMaster = serviceMasterService
                        .getServiceByShortName(MainetConstants.NoDuesCertificate.NO_DUE_CERTIFICATE, requestDTO.getOrgId());
                Long SmChklstVerifyId = serviceMaster.getSmChklstVerify();
                List<LookUp> lookUps = CommonMasterUtility.getLookUps("APL", organisation);
                for (LookUp lookUp : lookUps) {

                    if (SmChklstVerifyId == lookUp.getLookUpId()) {
                        certificateRespDTO.setCheckListApplFlag(lookUp.getLookUpCode());
                    }
                }
            }
            
          //D#146950
			List<TbWtBillMasEntity> billList = tbWtBillMasJpaRepository
 		            .getBillMasByConnectionId(csmrInfoMH.getCsIdn());
		    if(CollectionUtils.isNotEmpty(billList)) {
		    	Long finYearId = financialYearService.getFinanceYearId(new Date());
	            TbWtBillMasEntity billmas = null;
                String respMsg=null;
                Boolean billUpToDateForGivenFinYear = billMasService.isBillUpToDateForGivenFinYear(billList,finYearId, csmrInfoMH.getCsMeteredccn());
			    if(!billUpToDateForGivenFinYear) {
			    	 respMsg = ApplicationSession.getInstance().getMessage(
		                        "Bill generation is not up to date for connection no. " +csmrInfoMH.getCsCcn()+
		                        " Please generate the bills and pay any dues to proceed with change of ownership ");
			    	 certificateRespDTO.setDues(true);
			    	 certificateRespDTO.setErrorMsg(respMsg);
			    	 List<Long> finYearListFromLastBillGenDate = billMasService.getFinYearListFromLastBillGenDate(
					    		billList, billmas,finYearId);
			    	 certificateRespDTO.setAmount(finYearListFromLastBillGenDate.get(0).doubleValue());
		             
			    }
			    
		    }
            /*
             * if (csmrInfoMH != null) { List<TbBillMas> tbBillMas =
             * billMasterService.getBillMasterListByUniqueIdentifier(csmrInfoMH.getCsIdn(), requestDTO.getOrgId()); if
             * (tbBillMas.isEmpty()) { certificateRespDTO.setBillGenerated(true); } }
             */

        } catch (final Exception exception) {
            LOGGER.error("Start the getWaterConnectionDetail", exception);
        }
        return certificateRespDTO;
    
	}

	
}
