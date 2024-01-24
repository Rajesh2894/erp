/**
 *
 */
package com.abm.mainet.swm.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.abm.mainet.swm.domain.FineChargeCollection;
import com.abm.mainet.swm.dto.FineChargeCollectionDTO;
import com.abm.mainet.swm.mapper.FineChargeCollectionMapper;
import com.abm.mainet.swm.repository.FineChargeCollectionRepository;

/**
 * The Class FineChargeCollectionServiceImpl.
 *
 * @author Lalit.Prusti
 *
 * Created Date : 09-Jun-2018
 */
@Service
@Produces(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@Consumes(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@WebService(endpointInterface = "com.abm.mainet.swm.service.IFineChargeCollectionService")
@Path(value = "/fineChargeCollectionService")
public class FineChargeCollectionService implements IFineChargeCollectionService {

    private static final Logger LOG = LoggerFactory.getLogger(FineChargeCollectionService.class);

    /**
     * Fine Charge Collection Repository
     */
    @Autowired
    private FineChargeCollectionRepository chargeCollectionRepository;

    /**
     * Fine Charge Collection Mapper
     */
    @Autowired
    private FineChargeCollectionMapper chargeCollectionMapper;

    /**
     * Department Service
     */
    @Autowired
    private DepartmentService departmentService;

    /**
     * Service Master Service
     */
    @Autowired
    private ServiceMasterService serviceMasterService;

    /**
     * TbTax Mas Service
     */
    @Autowired
    private TbTaxMasService taxMasService;

    /**
     * IReceipt Entry Service
     */
    @Autowired
    private IReceiptEntryService receiptEntryService;

    /**
     * The IReceiptEntry Service
     */
    @Autowired
    private IFileUploadService fileUploadService;

    /**
     * The ISMS And Email Service
     */
    @Autowired
    private ISMSAndEmailService smsAndEmailService;
    
 

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IFineChargeCollectionService#delete(java.lang.Long, java.lang.Long, java.lang.String)
     */
    @Override
    @Transactional
    @WebMethod(exclude = true)
    public void delete(Long chargeCollectionId, Long empId, String ipMacAdd) {
        FineChargeCollection master = chargeCollectionRepository.findOne(chargeCollectionId);
        // TODO: set delete flag master.setDeActive(MainetConstants.IsDeleted.DELETE);
        master.setUpdatedBy(empId);
        master.setUpdatedDate(new Date());
        master.setLgIpMacUpd(ipMacAdd);
        chargeCollectionRepository.save(master);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IFineChargeCollectionService#getById(java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    @GET
    @Path(value = "/get/{id}")
    public FineChargeCollectionDTO getById(@PathParam("id") Long chargeCollection) {
        return chargeCollectionMapper
                .mapFineChargeCollectionToFineChargeCollectionDTO(
                        chargeCollectionRepository.findOne(chargeCollection));
    }

    /**
     * @param chargeCollectionDetails
     * @return
     */
    private FineChargeCollection mapped(FineChargeCollectionDTO chargeCollectionDetails) {
        FineChargeCollection master = chargeCollectionMapper
                .mapFineChargeCollectionDTOToFineChargeCollection(chargeCollectionDetails);
        return master;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IFineChargeCollectionService#save(com.abm.mainet.swm.dto.FineChargeCollectionDTO)
     */
    @Override
    @Transactional
    @POST
    @Path(value = "/save")
    public FineChargeCollectionDTO save(@RequestBody FineChargeCollectionDTO chargeCollectionDetails) {
        FineChargeCollection master = mapped(chargeCollectionDetails);
        master = chargeCollectionRepository.save(master);

        Organisation org = new Organisation();
        org.setOrgid(master.getOrgid());      
        
        Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.SolidWasteManagement.SHORT_CODE);
        CommonChallanDTO receiptDTO = new CommonChallanDTO();
        receiptDTO.setDeptId(deptId);
        receiptDTO.setOrgId(master.getOrgid());
        if (master.getFchId() != null)
            receiptDTO.setUniquePrimaryId(master.getFchId().toString());
        receiptDTO.setUserId(master.getCreatedBy());
        ServiceMaster serviceMaster = serviceMasterService
                .getServiceMasterByShortCode(MainetConstants.SolidWasteManagement.SOLID_WASTE_FINE_CHARGED, master.getOrgid());
        receiptDTO.setServiceId(serviceMaster.getSmServiceId());

        receiptDTO.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
        receiptDTO.setApplicantName(master.getFchName());
        receiptDTO.setLgIpMac(master.getLgIpMac());
        
		
        // TODO : set tax and amount
        Map<Long, Double> feeId = new HashMap<>(0);
        List<TbTaxMas> taxList = null;

        final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
                PrefixConstants.LookUpPrefix.RCPT, PrefixConstants.NewWaterServiceConstants.CAA, org);
        
        //To get single record of tax details for specific service after setting tax group and service in tax master configuration    
		if (StringUtils.isNotEmpty(chargeCollectionDetails.getServiceShortCode())) {
			ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
					.getServiceMasterByShortCode(chargeCollectionDetails.getServiceShortCode(), master.getOrgid());
			if (sm.getSmServiceId() != null) {
				taxList = taxMasService.findAllTaxesForBillGenerationByServiceId(master.getOrgid(), deptId,
						chargeApplicableAt.getLookUpId(), sm.getSmServiceId());
			}
		} else
			taxList = taxMasService.findAllTaxesForBillGeneration(master.getOrgid(), deptId,
					chargeApplicableAt.getLookUpId(), null); // -1L for All

        feeId.put(taxList.get(0).getTaxId(), master.getFchAmount().doubleValue());
        receiptDTO.setFeeIds(feeId);

        final LookUp serviceReceipt = CommonMasterUtility.getValueFromPrefixLookUp("M",
                PrefixConstants.REV_SUB_CPD_VALUE, new Organisation(master.getOrgid()));
        receiptDTO.setPaymentCategory(serviceReceipt.getLookUpCode());
        receiptDTO.setAmountToPay(master.getFchAmount().toString());
        LookUp payLookUp = CommonMasterUtility
                .getValueFromPrefixLookUp(chargeCollectionDetails.getPayMode(), PrefixConstants.PAY_PREFIX.PREFIX_VALUE,
                        org);
        receiptDTO.setPayModeIn(
                payLookUp
                        .getLookUpId());

        receiptDTO.setDeptId(deptId);

        TbServiceReceiptMasEntity serviceReceiptMaster = receiptEntryService.insertInReceiptMaster(receiptDTO, null);

        FineChargeCollectionDTO dto = chargeCollectionMapper.mapFineChargeCollectionToFineChargeCollectionDTO(master);
        dto.setReiceptNo(serviceReceiptMaster.getRmRcptno());

        if (CollectionUtils.isNotEmpty(chargeCollectionDetails.getDocumentList())) {
            try {
                RequestDTO fileUploadDTO = new RequestDTO();

                fileUploadDTO.setDeptId(deptId);
                fileUploadDTO.setOrgId(chargeCollectionDetails.getOrgid());
                fileUploadDTO.setIdfId("FINECHARGE/" + master.getFchId());
                fileUploadDTO.setStatus(MainetConstants.FlagA);
                fileUploadDTO.setUserId(chargeCollectionDetails.getCreatedBy());
                fileUploadDTO.setDepartmentName(MainetConstants.SolidWasteManagement.SHORT_CODE);
                fileUploadService.doMasterFileUpload(chargeCollectionDetails.getDocumentList(), fileUploadDTO);
            } catch (Exception e) {
               throw new FrameworkException("File Uploading Error", e);
            }
        }

        SMSAndEmailDTO emailDto = new SMSAndEmailDTO();
        emailDto.setOrganizationName(org.getONlsOrgname());
        emailDto.setMobnumber(master.getFchMobno());
        emailDto.setUserName(master.getFchName());
        emailDto.setServName(serviceMaster.getSmServiceName());
        emailDto.setCurrentDate(Utility.dateToString(new Date()));
        emailDto.setChallanAmt(master.getFchAmount().toString());
        emailDto.setNoticeNo(serviceReceiptMaster.getRmRcptno().toString());
        emailDto.setNoticeDate(Utility.dateToString(serviceReceiptMaster.getRmDate()));
        emailDto.setDispMode(payLookUp.getDescLangFirst());
        if(master.getFchType()!=null)
        emailDto.setInward_type(CommonMasterUtility.getNonHierarchicalLookUpObject(master.getFchType(), org).getDescLangFirst());
        smsAndEmailService.sendEmailSMS(MainetConstants.SolidWasteManagement.SHORT_CODE,
                MainetConstants.SolidWasteManagement.FINE_CHARGE,
                PrefixConstants.SMS_EMAIL_ALERT_TYPE.GENERAL_MSG, emailDto, org,
                1);

        return dto;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IFineChargeCollectionService#update(com.abm.mainet.swm.dto.FineChargeCollectionDTO)
     */
    @Override
    @Transactional
    @WebMethod(exclude = true)
    public FineChargeCollectionDTO update(FineChargeCollectionDTO chargeCollectionDetails) {
        FineChargeCollection master = mapped(chargeCollectionDetails);
        master = chargeCollectionRepository.save(master);
        return chargeCollectionMapper.mapFineChargeCollectionToFineChargeCollectionDTO(master);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IFineChargeCollectionService#search(java.lang.String, java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    @GET
    @Path(value = "/search")
    public List<FineChargeCollectionDTO> search(@QueryParam(value = "mobileNo") String mobileNo,
            @QueryParam(value = "registrationId") Long registrationId, @QueryParam(value = "orgId") Long orgId) {

        return chargeCollectionMapper.mapFineChargeCollectionListToFineChargeCollectionDTOList(
                chargeCollectionRepository.findByFchMobnoOrRegistrationIdAndOrgid(mobileNo, registrationId, orgId));

    }

}
