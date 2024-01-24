/**
 *
 */
package com.abm.mainet.swm.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.ErrorResponse;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.utility.BarcodeGenerator;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.abm.mainet.swm.dao.ISolidWasteConsumerDAO;
import com.abm.mainet.swm.domain.SolidWasteBillMaster;
import com.abm.mainet.swm.domain.SolidWasteConsumerMaster;
import com.abm.mainet.swm.domain.SolidWasteConsumerMasterHistory;
import com.abm.mainet.swm.dto.SolidWasteBillMasterDTO;
import com.abm.mainet.swm.dto.SolidWasteConsumerMasterDTO;
import com.abm.mainet.swm.mapper.SolidWasteBillMasterMapper;
import com.abm.mainet.swm.mapper.SolidWasteConsumerMapper;
import com.abm.mainet.swm.repository.SolidWasteBillMasterRepository;
import com.abm.mainet.swm.repository.SolidWasteConsumerRepository;

import io.swagger.annotations.Api;

/**
 * The Class UserRegistrationServiceImpl.
 *
 * @author Lalit.Prusti
 *
 * Created Date : 09-Jun-2018
 */
@Service
@Produces(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@Consumes(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@WebService(endpointInterface = "com.abm.mainet.swm.service.ISolidWasteConsumerService")
@Path(value = "/userregnservice")
@Api(value = "/userregnservice")
public class SolidWasteConsumerService implements ISolidWasteConsumerService {

    private static final Logger LOG = LoggerFactory.getLogger(SolidWasteConsumerService.class);

    /** The trip sheet repository. */
    @Autowired
    private SolidWasteConsumerRepository solidWasteConsumerRepository;

    @Autowired
    private ISolidWasteConsumerDAO solidWasteConsumerDAO;

    @Autowired
    private SolidWasteBillMasterMapper billMasterMapper;

    /** The trip sheet mapper. */
    @Autowired
    private SolidWasteConsumerMapper solidWasteConsumerMapper;

    /** The audit service. */
    @Autowired
    private AuditService auditService;

    /**
     * The SeqGenFunction Utility
     */
    @Autowired
    private SeqGenFunctionUtility seqGenFunctionUtility;

    /**
     * The Solid Waste Bill Master Repository
     */
    @Autowired
    private SolidWasteBillMasterRepository solidWasteBillMasterRepository;

    /**
     * The IFile Upload Service
     */
    @Autowired
    private IFileUploadService fileUploadService;

    /**
     * The IOrganisation Service
     */
    @Autowired
    private IOrganisationService organisationService;

    /**
     * The ISMS And Email Service
     */
    @Autowired
    private ISMSAndEmailService smsAndEmailService;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.UserRegistrationService#delete(java.lang.Long)
     */
    @Override
    @Transactional
    @WebMethod(exclude = true)
    public boolean delete(Long userRegistrationId, Long empId, String ipMacAdd) {
        SolidWasteConsumerMaster master = solidWasteConsumerRepository.findOne(userRegistrationId);
        // TODO: set delete flag master.setDeActive(MainetConstants.IsDeleted.DELETE);
        master.setUpdatedBy(empId);
        master.setUpdatedDate(new Date());
        master.setLgIpMacUpd(ipMacAdd);
        solidWasteConsumerRepository.save(master);
        saveUserRegistrationHistory(master, MainetConstants.Transaction.Mode.DELETE);
        return true;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.UserRegistrationService#getById(java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    @GET
    @Path(value = "/get/{id}")
    public SolidWasteConsumerMasterDTO getById(@PathParam("id") Long userRegistration) {
        return solidWasteConsumerMapper
                .mapUserRegistrationToUserRegistrationDTO(
                        solidWasteConsumerRepository.findOne(userRegistration));
    }

    /**
     * Mapped.
     *
     * @param userRegistrationDetails the trip sheet details
     * @return the trip sheet
     */
    private SolidWasteConsumerMaster mapped(SolidWasteConsumerMasterDTO userRegistrationDetails) {
        SolidWasteConsumerMaster master = solidWasteConsumerMapper
                .mapUserRegistrationDTOToUserRegistration(userRegistrationDetails);
        return master;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.UserRegistrationService#save(com.abm.mainet.swm.dto.UserRegistrationDTO)
     */
    @Override
    @Transactional
    @POST
    @Path(value = "/save")
    public SolidWasteConsumerMasterDTO save(@RequestBody SolidWasteConsumerMasterDTO userRegistrationDetails) {
        SolidWasteConsumerMaster master = mapped(userRegistrationDetails);
        if (isValidUser(userRegistrationDetails)) {
            final Long sequence = seqGenFunctionUtility.generateSequenceNo(MainetConstants.SolidWasteManagement.SHORT_CODE,
                    "TB_SW_REGISTRATION", "SW_NEW_CUST_ID", userRegistrationDetails.getOrgid(),
                    MainetConstants.FlagC, userRegistrationDetails.getOrgid());
            Organisation org = organisationService.getOrganisationById(userRegistrationDetails.getOrgid());
            String customerId = org.getOrgShortNm() + String.format("%06d", sequence);

            master.setSwNewCustId(customerId);

            master = solidWasteConsumerRepository.save(master);
            saveUserRegistrationHistory(master, MainetConstants.Transaction.Mode.ADD);
            BarcodeGenerator barcodeGenerator = new BarcodeGenerator();
            StringBuilder builder = new StringBuilder();
            builder.append("ID : ").append(master.getRegistrationId()).append(MainetConstants.WHITE_SPACE);
            builder.append("NAME : ").append(master.getSwName()).append(MainetConstants.WHITE_SPACE);
            builder.append("CONSUMER ID : ").append(master.getSwNewCustId()).append(MainetConstants.WHITE_SPACE);
            builder.append("MOBILE : ").append(master.getSwMobile()).append(MainetConstants.WHITE_SPACE);
            builder.append("ADDRESS : ").append(master.getSwAddress());
            try {
                byte[] barcodeImage = barcodeGenerator.getBarcodeInByteArray(builder.toString());
                DocumentDetailsVO barcodeDoc = new DocumentDetailsVO();
                barcodeDoc.setDocumentName("BARCODE.png");
                barcodeDoc.setDocumentByteCode(new String(Base64.getEncoder().encode(barcodeImage)));
                if (userRegistrationDetails.getDocumentList() == null) {
                    userRegistrationDetails.setDocumentList(new ArrayList<>());
                }
                userRegistrationDetails.getDocumentList().add(barcodeDoc);
            } catch (Exception e) {
            	throw new FrameworkException("Exception while generate QRCODE", e);
            }
            if (CollectionUtils.isNotEmpty(userRegistrationDetails.getDocumentList())) {
                try {

                    RequestDTO fileUploadDTO = new RequestDTO();

                    fileUploadDTO.setOrgId(userRegistrationDetails.getOrgid());
                    fileUploadDTO.setIdfId("REGISTRATION/" + master.getSwNewCustId());
                    fileUploadDTO.setStatus(MainetConstants.FlagA);
                    fileUploadDTO.setUserId(userRegistrationDetails.getCreatedBy());
                    fileUploadDTO.setDepartmentName(MainetConstants.SolidWasteManagement.SHORT_CODE);
                    fileUploadService.doMasterFileUpload(userRegistrationDetails.getDocumentList(), fileUploadDTO);

                } catch (Exception e) {
                	throw new FrameworkException("File Uploading Error", e);
                }

                SMSAndEmailDTO dto = new SMSAndEmailDTO();
                dto.setOrganizationName(org.getONlsOrgname());
                dto.setAppNo(master.getSwNewCustId());
                // dto.setServName(servName);
                dto.setTenantNo(master.getSwOldCustId());
                dto.setPropertyNo(master.getSwNewPropNo());
                dto.setTntTenantNo(master.getSwOldPropNo());
                dto.setMobnumber(master.getSwMobile().toString());
                dto.setUserName(master.getSwName());

                smsAndEmailService.sendEmailSMS(MainetConstants.SolidWasteManagement.SHORT_CODE,
                        MainetConstants.SolidWasteManagement.CONSUMER_REG,
                        PrefixConstants.SMS_EMAIL_ALERT_TYPE.GENERAL_MSG, dto, org,
                        1);
            }
            return solidWasteConsumerMapper.mapUserRegistrationToUserRegistrationDTO(master);
        } else {
        	SolidWasteConsumerMasterDTO errorDto=new SolidWasteConsumerMasterDTO();
        	ErrorResponse errorResponse=new ErrorResponse();
        	errorResponse.setMessage("User already Register");
        	errorDto.setErrorResponse(errorResponse);
        	return errorDto;
            //throw new FrameworkException("User already Register");
        }

        // gerenerate sequence

    }

    /**
     * Save vendor history.
     *
     * @param master the master
     * @param status the status
     */
    private void saveUserRegistrationHistory(SolidWasteConsumerMaster master, String status) {
        SolidWasteConsumerMasterHistory masterHistory = new SolidWasteConsumerMasterHistory();
        masterHistory.sethStatus(status);
        auditService.createHistory(master, masterHistory);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.UserRegistrationService#update(com.abm.mainet.swm.dto.UserRegistrationDTO)
     */
    @Override
    @Transactional
    @WebMethod(exclude = true)
    public SolidWasteConsumerMasterDTO update(SolidWasteConsumerMasterDTO userRegistrationDetails) {
        SolidWasteConsumerMaster master = mapped(userRegistrationDetails);
        master = solidWasteConsumerRepository.save(master);
        saveUserRegistrationHistory(master, MainetConstants.Transaction.Mode.UPDATE);
        return solidWasteConsumerMapper.mapUserRegistrationToUserRegistrationDTO(master);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.ISolidWasteConsumerService#search(java.lang.Long, java.lang.String, java.lang.String,
     * java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    @GET
   @Path(value = "/search")
    public SolidWasteConsumerMasterDTO search(@QueryParam(value = "registrationId") Long registrationId,
            @QueryParam(value = "custNumber") String custNumber, @QueryParam(value = "propetyNo") String propetyNo,
            @QueryParam(value = "mobileNo") Long mobileNo, @QueryParam(value = "orgId") Long orgId) {

        SolidWasteConsumerMaster userInfo = solidWasteConsumerDAO.search(registrationId, custNumber, propetyNo, mobileNo, orgId);

        SolidWasteConsumerMasterDTO dto = solidWasteConsumerMapper
                .mapUserRegistrationToUserRegistrationDTO(
                        userInfo);
        if (null != userInfo) {
            SolidWasteBillMaster master = solidWasteBillMasterRepository.lastPaymentDetails(userInfo.getRegistrationId(),
                    userInfo.getOrgid());
            SolidWasteBillMasterDTO sdto = billMasterMapper.mapBillMasterToBillMasterDTO(
                    master);

            if (null != master) {
                dto.setLaspPaymentDate(master.getBillToDate());
                dto.setLastPayAmount(master.getBillAmount());
                dto.setOutstandingAmount(master.getOutStandingAmount());
                dto.setAdvanceAmount(master.getAdvanceAmount());
                dto.setTbSwNewBillMas(sdto);
            }

        }

        return dto;

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.ISolidWasteConsumerService#isValidUser(com.abm.mainet.swm.dto.SolidWasteConsumerMasterDTO)
     */
    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public boolean isValidUser(SolidWasteConsumerMasterDTO userRegistrationDetails) {
        SolidWasteConsumerMaster consumer = null;

        String propertyNO = userRegistrationDetails.getSwNewPropNo();
        if (StringUtils.isBlank(propertyNO)) {
            propertyNO = userRegistrationDetails.getSwOldPropNo();
        }
        String custNo = userRegistrationDetails.getSwNewCustId();

        if (StringUtils.isBlank(custNo)) {
            custNo = userRegistrationDetails.getSwOldCustId();
        }
        try {
            consumer = solidWasteConsumerDAO.search(userRegistrationDetails.getRegistrationId(),
                    custNo, propertyNO, userRegistrationDetails.getSwMobile(),
                    userRegistrationDetails.getOrgid());
        } catch (Exception e) {
        	throw new FrameworkException("Search exception", e);
        }
        return consumer == null;
    }

}
