package com.abm.mainet.common.master.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.dao.TbAcVendormasterDao;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.TbAcVendormasterEntity;
import com.abm.mainet.common.domain.TbAcVendormasterHistEntity;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.dto.VendorMasterUploadDto;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.dto.SecondaryheadMaster;
import com.abm.mainet.common.integration.acccount.dto.VendorBillApprovalDTO;
import com.abm.mainet.common.integration.acccount.dto.VendorDTO;
import com.abm.mainet.common.integration.acccount.hrms.vendormaster.soap.jaxws.client.VendorMaster;
import com.abm.mainet.common.integration.acccount.repository.SecondaryheadMasterJpaRepository;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.acccount.service.VendorMasterSoapWSProvisionService;
import com.abm.mainet.common.integration.dms.dao.IAttachDocsDao;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.mapper.TbAcVendormasterServiceMapper;
import com.abm.mainet.common.repository.TbAcVendormasterJpaRepository;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;

/**
 * Implementation of TbAcVendormasterService
 */
@Component
@Transactional
public class TbAcVendormasterServiceImpl implements TbAcVendormasterService {

    @Resource
    private TbAcVendormasterJpaRepository tbAcVendormasterJpaRepository;

    @Resource
    private TbAcVendormasterServiceMapper tbAcVendormasterServiceMapper;

    @Autowired
    private SecondaryheadMasterJpaRepository tbAcSecondaryheadMasterJpaRepository;

    @Autowired
    private SecondaryheadMasterService secondaryheadMasterService;

    @Autowired
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Resource
    private TbAcVendormasterDao tbAcVendormasterDao;

    @Resource
    private VendorMasterSoapWSProvisionService vendorMasterSoapWSProvisionService;

    @Resource
    private AuditService auditService;
    
    @Autowired
    private IAttachDocsDao attachDocsDao;
    
    @Autowired
    private IFileUploadService fileUploadservice;

    @Resource
    private TbOrganisationService tbOrganisationService;

    private static final Logger LOGGER = Logger.getLogger(TbAcVendormasterServiceImpl.class);

    private static final String SEQUENCE_NO = "V0000";

    private static final String DATE_COVERT_EXCEPTION = "Exception while converting date to XMLGregorianCalendar :";

    @Override
    public TbAcVendormaster findById(final Long vmVendorid, final Long orgid) {
        final TbAcVendormasterEntity tbAcVendormasterEntity = tbAcVendormasterJpaRepository.findOne(vmVendorid);
        return tbAcVendormasterServiceMapper.mapTbAcVendormasterEntityToTbAcVendormaster(tbAcVendormasterEntity);
    }

    @Override
    public List<TbAcVendormaster> findAll(final Long orgid) {
        final Iterable<TbAcVendormasterEntity> entities = tbAcVendormasterJpaRepository.getVendorList(orgid);
        final List<TbAcVendormaster> beans = new ArrayList<>();
        for (final TbAcVendormasterEntity tbAcVendormasterEntity : entities) {
            beans.add(
                    tbAcVendormasterServiceMapper.mapTbAcVendormasterEntityToTbAcVendormaster(tbAcVendormasterEntity));
        }
        return beans;
    }

    @Override
    public List<TbAcVendormaster> findAllSechead(final Long orgid) {
        final Iterable<TbAcVendormasterEntity> entities = tbAcVendormasterJpaRepository.getVendorListsechead(orgid);
        final List<TbAcVendormaster> beans = new ArrayList<>();
        for (final TbAcVendormasterEntity tbAcVendormasterEntity : entities) {
            beans.add(
                    tbAcVendormasterServiceMapper.mapTbAcVendormasterEntityToTbAcVendormaster(tbAcVendormasterEntity));
        }
        return beans;
    }

    @Override
    public TbAcVendormaster create(final TbAcVendormaster tbAcVendormaster, final List<LookUp> venderType,
            final Long secndVendorType, final Organisation defaultOrg, final Organisation org, final int langId) {

        final TbAcVendormasterEntity tbAcVendormasterEntity = new TbAcVendormasterEntity();
        final Date curDate = new Date();
        long vendcodeNumber = 0;
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        vendcodeNumber = seqGenFunctionUtility.generateSequenceNo(
                com.abm.mainet.common.constant.MainetConstants.TbAcVendormaster.FI04,
                com.abm.mainet.common.constant.MainetConstants.TbAcVendormaster.TB_VENDORMASTER,
                com.abm.mainet.common.constant.MainetConstants.TbAcVendormaster.VM_VENDORCODE, orgId,
                MainetConstants.MODE_CREATE, null);
        final StringBuilder vendorCodeNo = new StringBuilder();
        vendorCodeNo.append(SEQUENCE_NO).append(vendcodeNumber).toString();
        tbAcVendormaster.setVmVendorcode(vendorCodeNo.toString());
        if(tbAcVendormaster.getAddMobileNo() != null) {
        	 tbAcVendormasterEntity.setAddMobileNo(tbAcVendormaster.getAddMobileNo());
        }
        tbAcVendormaster.setLmoddate(curDate);
        if(StringUtils.isNotEmpty(tbAcVendormaster.getVmVendornameReg())) {
        	tbAcVendormasterEntity.setVmVendornameReg(tbAcVendormaster.getVmVendornameReg());
        }
        final LookUp statusLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.ACCOUNT_MASTERS.SECONDARY_MASTER.SECONDARY_SEQ_DEPARTMENT_TYPE, PrefixConstants.VSS,
                langId, org);
        if (tbAcVendormaster.getVmCpdStatus() == null) {
            tbAcVendormaster.setVmCpdStatus(statusLookup.getLookUpId());
        } else {
            tbAcVendormaster.setVmCpdStatus(tbAcVendormaster.getVmCpdStatus());
        }
        tbAcVendormasterEntity.setMsmeNo(tbAcVendormaster.getMsmeNo());
        tbAcVendormasterServiceMapper.mapTbAcVendormasterToTbAcVendormasterEntity(tbAcVendormaster, tbAcVendormasterEntity);
        TbAcVendormasterEntity tbAcVendormasterEntitySaved = tbAcVendormasterJpaRepository.save(tbAcVendormasterEntity);
        try {
            TbAcVendormasterHistEntity tbAcVendormasterHistEntity = new TbAcVendormasterHistEntity();
            tbAcVendormasterHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_C);
            auditService.createHistory(tbAcVendormasterEntity, tbAcVendormasterHistEntity);
        } catch (Exception ex) {
            LOGGER.error("Could not make audit entry for " + tbAcVendormasterEntity, ex);
        }
        insertVendorMasterDataIntoPropertyTaxTableByUsingSoapWS(tbAcVendormasterEntitySaved);

        SecondaryheadMaster tbAcSecondaryheadMaster = null;
        if (tbAcVendormaster.getSliMode().equals("L")) {
            if (tbAcVendormaster.getPacHeadId() != null) {
                tbAcSecondaryheadMaster = new SecondaryheadMaster();
                final Long sacHeadId = tbAcSecondaryheadMasterJpaRepository
                        .findSacHeadId(tbAcVendormaster.getVmVendorid(), orgId);
              
                final List<LookUp> accountTypeLevel = CommonMasterUtility.getListLookup(PrefixConstants.CMD_PREFIX,
                        org);
                for (final LookUp lookUp : accountTypeLevel) {
                    if (lookUp != null) {
                        if (PrefixConstants.SECONDARY_CPD_VALUE.equalsIgnoreCase(lookUp.getLookUpCode())) {
                            if ((lookUp.getOtherField() != null) && !lookUp.getOtherField().isEmpty()) {
                                if (lookUp.getOtherField().equals(MainetConstants.MASTER.Y)) {
                                    tbAcSecondaryheadMaster.setSecondaryStatus(MainetConstants.MASTER.Y);
                                }
                            }
                        }
                    }
                }
                tbAcSecondaryheadMaster.setSacHeadId(sacHeadId);
                tbAcSecondaryheadMaster.setSacLeddgerTypeCpdId(secndVendorType);
                if (tbAcVendormaster.getFunctionId() != null) {
                    tbAcSecondaryheadMaster.setFunctionId(tbAcVendormaster.getFunctionId());
                }
                tbAcSecondaryheadMaster.setPacHeadId(tbAcVendormaster.getPacHeadId());
                tbAcSecondaryheadMaster.setUserId(tbAcVendormaster.getUserId());
                tbAcSecondaryheadMaster.setOrgid(tbAcVendormaster.getOrgid());
                tbAcSecondaryheadMaster.setLgIpMac(tbAcVendormaster.getLgIpMac());
                tbAcSecondaryheadMaster.setLangId(tbAcVendormaster.getLangId());
                tbAcSecondaryheadMaster.setVmVendorid(tbAcVendormasterEntity.getVmVendorid());
                tbAcSecondaryheadMaster
                        .setSacHeadDesc(tbAcVendormaster.getVmVendorname());
                tbAcSecondaryheadMaster.setOldSacHeadCode(tbAcVendormaster.getAccOldHeadCode());
                secondaryheadMasterService.saveSecondaryHeadData(tbAcSecondaryheadMaster, defaultOrg, langId);
            }
        }
        return tbAcVendormasterServiceMapper.mapTbAcVendormasterEntityToTbAcVendormaster(tbAcVendormasterEntitySaved);

    }

    private void insertVendorMasterDataIntoPropertyTaxTableByUsingSoapWS(
            TbAcVendormasterEntity tbAcVendormasterEntitySaved) {

        try {
            VendorMaster vendorMaster = new VendorMaster();
            vendorMaster.setORGID(tbAcVendormasterEntitySaved.getOrgid().toString());
            // vendorMaster.setStatus("Status-962742536");
            // vendorMaster.setModifiedIn("ModifiedIn42301816");
            vendorMaster.setCreatedBy(tbAcVendormasterEntitySaved.getUserId().toString());

            if (tbAcVendormasterEntitySaved.getPacHeadId() != null) {
                vendorMaster.setPACHEADID(tbAcVendormasterEntitySaved.getPacHeadId());
            }
            vendorMaster.setVMVENDORADD(tbAcVendormasterEntitySaved.getVmVendoradd());
            // vendorMaster.setSheetId("SheetId1976750720");
            if (tbAcVendormasterEntitySaved.getLmoddate() != null) {
                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(tbAcVendormasterEntitySaved.getLmoddate());
                vendorMaster.setCreatedAt(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
            }
            // vendorMaster.setMetadata("Metadata-2015365170");
            vendorMaster.setSheetName(tbAcVendormasterEntitySaved.getVmVendorid().toString());
            vendorMaster.setVMVENDORID(tbAcVendormasterEntitySaved.getVmVendorid());
            if (tbAcVendormasterEntitySaved.getUpdatedBy() != null) {
                vendorMaster.setModifiedBy(tbAcVendormasterEntitySaved.getUpdatedBy().toString());
            }
            // vendorMaster.setCaption("Caption-1914567761");
            // vendorMaster.setAssignedTo("AssignedTo-848354094");
            if (tbAcVendormasterEntitySaved.getUpdatedDate() != null) {
                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(tbAcVendormasterEntitySaved.getUpdatedDate());
                vendorMaster.setModifiedAt(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
            }
            vendorMaster.setVMVENDORNAME(tbAcVendormasterEntitySaved.getVmVendorname());
            // vendorMaster.setTenant("Tenant1552762844");
            vendorMaster.setVMVENDORCODE(tbAcVendormasterEntitySaved.getVmVendorcode());
            vendorMaster.setVendorType(tbAcVendormasterEntitySaved.getCpdVendortype().toString());
            String vendorLookUpCode = "";
            if (tbAcVendormasterEntitySaved.getVmCpdStatus() != null) {
                vendorLookUpCode = CommonMasterUtility.findLookUpCode(PrefixConstants.VSS, tbAcVendormasterEntitySaved.getOrgid(),
                        tbAcVendormasterEntitySaved.getVmCpdStatus());
                vendorMaster.setVendorStatus(vendorLookUpCode);
            }
            // vendorMaster.setVendorStatus(tbAcVendormasterEntitySaved.getVmCpdStatus().toString());
            if (tbAcVendormasterEntitySaved.getMobileNo() != null) {
                vendorMaster.setVendorMobileNo(tbAcVendormasterEntitySaved.getMobileNo());
            }
            if (tbAcVendormasterEntitySaved.getEmailId() != null) {
                vendorMaster.setVendorMailId(tbAcVendormasterEntitySaved.getEmailId());
            }
            vendorMaster.setCPDVENDORSUBTYPE(tbAcVendormasterEntitySaved.getCpdVendorSubType());
            if (tbAcVendormasterEntitySaved.getVendorClass() != null) {
                vendorMaster.setVmclass(CommonMasterUtility
                        .getNonHierarchicalLookUpObjectByPrefix(tbAcVendormasterEntitySaved.getVendorClass(),
                                tbAcVendormasterEntitySaved.getOrgid(), PrefixConstants.TbAcVendormaster.VEC)
                        .getDescLangFirst());
            }
            // vendorMaster.setProcessInstance("ProcessInstance1496774539");
            // vendorMaster.setSheetMetadataName("SheetMetadataName-1981061581");
            //vendorMasterSoapWSProvisionService.createVendorMaster(vendorMaster);

        } catch (DatatypeConfigurationException ex) {
            throw new FrameworkException(DATE_COVERT_EXCEPTION + ex);
        }

    }

    @Override
    public TbAcVendormaster update(final TbAcVendormaster tbAcVendormaster, final Organisation orgId,
            final int langId) {
        final TbAcVendormasterEntity tbAcVendormasterEntity = tbAcVendormasterJpaRepository
                .findOne(tbAcVendormaster.getVmVendorid());
        final Date curDate = new Date();
        tbAcVendormaster.setLmoddate(curDate);
        tbAcVendormasterServiceMapper.mapTbAcVendormasterToTbAcVendormasterEntity(tbAcVendormaster, tbAcVendormasterEntity);
        TbAcVendormasterEntity tbAcVendormasterEntitySaved = tbAcVendormasterJpaRepository.save(tbAcVendormasterEntity);
        try {
            TbAcVendormasterHistEntity tbAcVendormasterHistEntity = new TbAcVendormasterHistEntity();
            tbAcVendormasterHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_U);
            auditService.createHistory(tbAcVendormasterEntity, tbAcVendormasterHistEntity);
        } catch (Exception ex) {
            LOGGER.error("Could not make audit entry for " + tbAcVendormasterEntity, ex);
        }
        //updateVendorMasterDataIntoPropertyTaxTableByUsingSoapWS(tbAcVendormasterEntitySaved);

        SecondaryheadMaster tbAcSecondaryheadMaster = null;
        if (tbAcVendormaster.getSliMode().equals("L")) {
            if (tbAcVendormaster.getPacHeadId() != null) {
            	final Long sacHeadId = tbAcSecondaryheadMasterJpaRepository
                        .findSacHeadId(tbAcVendormaster.getVmVendorid(), orgId.getOrgid());
                	final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
                    Organisation defaultOrg = null;
                    if (isDafaultOrgExist) {
                        defaultOrg = ApplicationSession.getInstance().getSuperUserOrganization();
                    } else {
                        defaultOrg = UserSession.getCurrent().getOrganisation();
                    }
                	
                    tbAcSecondaryheadMaster = new SecondaryheadMaster();
                    
                    final List<LookUp> accountTypeLevel = CommonMasterUtility.getListLookup(PrefixConstants.CMD_PREFIX,
                    		defaultOrg);
                    for (final LookUp lookUp : accountTypeLevel) {
                        if (lookUp != null) {
                            if (PrefixConstants.SECONDARY_CPD_VALUE.equalsIgnoreCase(lookUp.getLookUpCode())) {
                                if ((lookUp.getOtherField() != null) && !lookUp.getOtherField().isEmpty()) {
                                    if (lookUp.getOtherField().equals(MainetConstants.MASTER.Y)) {
                                        tbAcSecondaryheadMaster.setSecondaryStatus(MainetConstants.MASTER.Y);
                                    }
                                }
                            }
                        }
                    }
                    tbAcSecondaryheadMaster.setSacHeadId(sacHeadId);
                    tbAcSecondaryheadMaster.setSacLeddgerTypeCpdId(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                            PrefixConstants.TbAcVendormaster.VD, PrefixConstants.FTY, orgId.getOrgid()));
                    if (tbAcVendormaster.getFunctionId() != null) {
                        tbAcSecondaryheadMaster.setFunctionId(tbAcVendormaster.getFunctionId());
                    }
                    tbAcSecondaryheadMaster.setPacHeadId(tbAcVendormaster.getPacHeadId());
                    tbAcSecondaryheadMaster.setUserId(tbAcVendormaster.getUserId());
                    tbAcSecondaryheadMaster.setOrgid(tbAcVendormaster.getOrgid());
                    tbAcSecondaryheadMaster.setLgIpMac(tbAcVendormaster.getLgIpMac());
                    tbAcSecondaryheadMaster.setLangId(tbAcVendormaster.getLangId());
                    tbAcSecondaryheadMaster.setVmVendorid(tbAcVendormasterEntity.getVmVendorid());
                    tbAcSecondaryheadMaster.setSacHeadDesc(
                            tbAcVendormaster.getVmVendorname());
                    /* #91157- Passing defaultOrg same as like in create vendor master - by Samadhan Sir*/
                    secondaryheadMasterService.saveSecondaryHeadData(tbAcSecondaryheadMaster, defaultOrg, langId);
                }
            }
        

        return tbAcVendormasterServiceMapper.mapTbAcVendormasterEntityToTbAcVendormaster(tbAcVendormasterEntitySaved);
    }

    private void updateVendorMasterDataIntoPropertyTaxTableByUsingSoapWS(
            TbAcVendormasterEntity tbAcVendormasterEntitySaved) {
        try {
            VendorMaster vendorMaster = new VendorMaster();
            vendorMaster.setORGID(tbAcVendormasterEntitySaved.getOrgid().toString());
            // vendorMaster.setStatus("Status-962742536");
            // vendorMaster.setModifiedIn("ModifiedIn42301816");
            vendorMaster.setCreatedBy(tbAcVendormasterEntitySaved.getUserId().toString());

            if (tbAcVendormasterEntitySaved.getPacHeadId() != null) {
                vendorMaster.setPACHEADID(tbAcVendormasterEntitySaved.getPacHeadId());
            }
            vendorMaster.setVMVENDORADD(tbAcVendormasterEntitySaved.getVmVendoradd());
            // vendorMaster.setSheetId("SheetId1976750720");
            if (tbAcVendormasterEntitySaved.getLmoddate() != null) {
                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(tbAcVendormasterEntitySaved.getLmoddate());
                vendorMaster.setCreatedAt(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
            }
            // vendorMaster.setMetadata("Metadata-2015365170");
            vendorMaster.setSheetName(tbAcVendormasterEntitySaved.getVmVendorid().toString());
            vendorMaster.setVMVENDORID(tbAcVendormasterEntitySaved.getVmVendorid());
            if (tbAcVendormasterEntitySaved.getUpdatedBy() != null) {
                vendorMaster.setModifiedBy(tbAcVendormasterEntitySaved.getUpdatedBy().toString());
            }
            // vendorMaster.setCaption("Caption-1914567761");
            // vendorMaster.setAssignedTo("AssignedTo-848354094");
            if (tbAcVendormasterEntitySaved.getUpdatedDate() != null) {
                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(tbAcVendormasterEntitySaved.getUpdatedDate());
                vendorMaster.setModifiedAt(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
            }
            vendorMaster.setVMVENDORNAME(tbAcVendormasterEntitySaved.getVmVendorname());
            // vendorMaster.setTenant("Tenant1552762844");
            vendorMaster.setVMVENDORCODE(tbAcVendormasterEntitySaved.getVmVendorcode());
            vendorMaster.setVendorType(tbAcVendormasterEntitySaved.getCpdVendortype().toString());
            String vendorLookUpCode = "";
            if (tbAcVendormasterEntitySaved.getVmCpdStatus() != null) {
                vendorLookUpCode = CommonMasterUtility.findLookUpCode(PrefixConstants.VSS, tbAcVendormasterEntitySaved.getOrgid(),
                        tbAcVendormasterEntitySaved.getVmCpdStatus());
                vendorMaster.setVendorStatus(vendorLookUpCode);
            }
            // vendorMaster.setVendorStatus(tbAcVendormasterEntitySaved.getVmCpdStatus().toString());
            if (tbAcVendormasterEntitySaved.getMobileNo() != null) {
                vendorMaster.setVendorMobileNo(tbAcVendormasterEntitySaved.getMobileNo());
            }
            if (tbAcVendormasterEntitySaved.getEmailId() != null) {
                vendorMaster.setVendorMailId(tbAcVendormasterEntitySaved.getEmailId());
            }
            vendorMaster.setCPDVENDORSUBTYPE(tbAcVendormasterEntitySaved.getCpdVendorSubType());
            if (tbAcVendormasterEntitySaved.getVendorClass() != null) {
                vendorMaster.setVmclass(CommonMasterUtility
                        .getNonHierarchicalLookUpObjectByPrefix(tbAcVendormasterEntitySaved.getVendorClass(),
                                tbAcVendormasterEntitySaved.getOrgid(), PrefixConstants.TbAcVendormaster.VEC)
                        .getDescLangFirst());
            }
            // vendorMaster.setProcessInstance("ProcessInstance1496774539");
            // vendorMaster.setSheetMetadataName("SheetMetadataName-1981061581");
            vendorMasterSoapWSProvisionService.updateVendormaster(vendorMaster);

        } catch (DatatypeConfigurationException ex) {
            throw new FrameworkException(DATE_COVERT_EXCEPTION + ex);
        }

    }

    @Override
    public void delete(final Long vmVendorid, final Long orgid) {
        final TbAcVendormasterEntity id = new TbAcVendormasterEntity();
        tbAcVendormasterJpaRepository.delete(id.getVmVendorid());
    }

    @Override
    public List<TbAcVendormaster> getVendorvmPanNumber(final String vmpanNumber, final Long orgId) {
        List<TbAcVendormasterEntity> tbAcVendormaster = null;
        tbAcVendormaster = tbAcVendormasterJpaRepository.getVendorvmPanNumber(vmpanNumber, orgId);
        final List<TbAcVendormaster> beans = new ArrayList<>();
        for (final TbAcVendormasterEntity venpanEntity : tbAcVendormaster) {
            beans.add(tbAcVendormasterServiceMapper.mapTbAcVendormasterEntityToTbAcVendormaster(venpanEntity));
        }
        return beans;
    }

    public TbAcVendormasterJpaRepository getTbAcVendormasterJpaRepository() {
        return tbAcVendormasterJpaRepository;
    }

    public void setTbAcVendormasterJpaRepository(final TbAcVendormasterJpaRepository tbAcVendormasterJpaRepository) {
        this.tbAcVendormasterJpaRepository = tbAcVendormasterJpaRepository;
    }

    public TbAcVendormasterServiceMapper getTbAcVendormasterServiceMapper() {
        return tbAcVendormasterServiceMapper;
    }

    public void setTbAcVendormasterServiceMapper(final TbAcVendormasterServiceMapper tbAcVendormasterServiceMapper) {
        this.tbAcVendormasterServiceMapper = tbAcVendormasterServiceMapper;
    }

    @Override
    public List<TbAcVendormaster> getVendorData(final Long cpdVendortype, final Long cpdVendorSubType,
            final String vendor_vmvendorcode, final Long vmCpdStatus, final Long orgid) {

        final List<TbAcVendormaster> beans = new ArrayList<>(0);
        TbAcVendormaster account = null;

        final List<TbAcVendormasterEntity> entity = tbAcVendormasterDao.getVendorData(cpdVendortype, cpdVendorSubType,
                vendor_vmvendorcode, vmCpdStatus, orgid);

        if ((entity != null) && !entity.isEmpty()) {
            for (final TbAcVendormasterEntity data : entity) {
                account = new TbAcVendormaster();
                account.setVmVendorid(data.getVmVendorid());
                account.setVmVendorcode(data.getVmVendorcode());
                account.setVmVendorname(data.getVmVendorname());
                account.setVmVendoradd(data.getVmVendoradd());
                account.setVmCpdStatus(data.getVmCpdStatus());
                beans.add(account);
            }
        }
        return beans;
    }

    @Override
    public List<TbAcVendormaster> getActiveVendors(final Long orgid, final Long vendorStatus) {
        final Iterable<TbAcVendormasterEntity> entities = tbAcVendormasterJpaRepository.getActiveVendors(orgid,
                vendorStatus);
        final List<TbAcVendormaster> beans = new ArrayList<>();
        for (final TbAcVendormasterEntity tbAcVendormasterEntity : entities) {
            beans.add(
                    tbAcVendormasterServiceMapper.mapTbAcVendormasterEntityToTbAcVendormaster(tbAcVendormasterEntity));
        }
        return beans;
    }

    /**
     * @param orgId
     * @param vendorId
     * @return
     */
    @Override
    public List<Object[]> getVendorDetails(final Long orgId, final Long vendorId) {

        return tbAcVendormasterJpaRepository.getVendorDetailsByVendorId(orgId, vendorId);

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.service.TbAcVendormasterService#getVendorNameById(java .lang.Long, java.lang.Long)
     */
    @Override
    public String getVendorNameById(final Long vmVendorid, final Long orgId) {

        return tbAcVendormasterJpaRepository.getVendorNameById(vmVendorid, orgId);
    }

    @Override
    public String getCpdMode() {
        final LookUp lookup = CommonMasterUtility
                .getDefaultValue(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE);
        if (lookup != null) {
            return lookup.getLookUpCode();
        } else {
            return null;
        }
    }

    @Override
    public List<TbAcVendormasterEntity> getVendorByPanNo(final String vmPanNumber, final Long orgId) {
        return tbAcVendormasterJpaRepository.getVendorByPanNo(vmPanNumber, orgId);
    }

    @Override
    public List<TbAcVendormasterEntity> getVendorByMobileNo(final String vmMobileNumber, final Long orgId) {
        return tbAcVendormasterJpaRepository.getVendorByMobileNo(vmMobileNumber, orgId);
    }

    @Override
    public List<TbAcVendormasterEntity> getVendorByUidNo(final Long uidNo, final Long orgId) {
        return tbAcVendormasterJpaRepository.getVendorByUidNo(uidNo, orgId);
    }

    @Override
    public List<TbAcVendormasterEntity> getVendorByVat(final String tinNumber, final Long orgId) {
        return tbAcVendormasterJpaRepository.getVendorByVat(tinNumber, orgId);
    }

    @Override
    public List<TbAcVendormasterEntity> getVendorByGst(final String gstNo, final Long orgId) {
        return tbAcVendormasterJpaRepository.getVendorByGst(gstNo, orgId);
    }

    @Override
    public List<TbAcVendormaster> getVendorMasterData(final Long orgId) {
        List<TbAcVendormasterEntity> entities = tbAcVendormasterJpaRepository.getVendorMasterData(orgId);
        final List<TbAcVendormaster> beans = new ArrayList<>();
        entities.forEach(entity -> {
            beans.add(tbAcVendormasterServiceMapper.mapTbAcVendormasterEntityToTbAcVendormaster(entity));
        });
        return beans;
    }

    @Override
    public List<TbAcVendormaster> getActiveStatusVendorsAndSacAcHead(Long orgid, Long vendorStatus, Long sacStatus) {
        // TODO Auto-generated method stub
        final Iterable<TbAcVendormasterEntity> entities = tbAcVendormasterJpaRepository
                .getActiveStatusVendorsAndSacAcHead(orgid, vendorStatus, sacStatus);
        final List<TbAcVendormaster> beans = new ArrayList<>();
        for (final TbAcVendormasterEntity tbAcVendormasterEntity : entities) {
            beans.add(
                    tbAcVendormasterServiceMapper.mapTbAcVendormasterEntityToTbAcVendormaster(tbAcVendormasterEntity));
        }
        return beans;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.service.AccountBillEntryService# getVendorCodeByVendorId(java.lang.Long, java.lang.Long)
     */
    @Override
    public List<TbAcVendormasterEntity> getVendorCodeByVendorId(final Long orgId, final Long vendorId) {

        return tbAcVendormasterJpaRepository.getVendorCodeByVendorId(orgId, vendorId);

    }

    @Override
    public VendorBillApprovalDTO getVendorNames(final Long orgId, final Long languageId) {

        final Organisation organisation = new Organisation();
        organisation.setOrgid(orgId);
        final LookUp lookUpVendorStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                AccountConstants.AC.getValue(), PrefixConstants.VSS, languageId.intValue(), organisation);
        final Long vendorStatus = lookUpVendorStatus.getLookUpId();
        final List<TbAcVendormaster> vendorList = getActiveVendors(orgId, vendorStatus);
        final VendorBillApprovalDTO billApprovalDto = new VendorBillApprovalDTO();
        LookUp lookUp = null;
        final List<LookUp> vendorLookUpList = new ArrayList<>();
        if ((vendorList != null) && !vendorList.isEmpty()) {
            for (final TbAcVendormaster vendor : vendorList) {
                lookUp = new LookUp();
                lookUp.setLookUpId(vendor.getVmVendorid());
                lookUp.setDescLangFirst(vendor.getVmVendorname());
                vendorLookUpList.add(lookUp);
            }
            billApprovalDto.setLookUpList(vendorLookUpList);
        }
        return billApprovalDto;

    }

    @Override
    public List<VendorDTO> addVendorListData(final List<TbAcVendormaster> list) {
        final List<VendorDTO> vendorList = new ArrayList<>();
        final VendorDTO vendor = new VendorDTO();
        for (final TbAcVendormaster tbAcVendormaster : list) {
            if ((tbAcVendormaster.getVmVendorid() != null) && ((tbAcVendormaster.getVmVendorname() != null)
                    && !tbAcVendormaster.getVmVendorname().isEmpty())) {
                vendor.setVmVendorid(tbAcVendormaster.getVmVendorid());
                vendor.setVmVendorname(tbAcVendormaster.getVmVendorname());
                vendor.setMobileNo(tbAcVendormaster.getMobileNo());
                vendor.setEmailId(tbAcVendormaster.getEmailId());
                vendor.setOrgid(tbAcVendormaster.getOrgid());
            }
        }
        vendorList.add(vendor);
        return vendorList;
    }

    @Override
    public List<Object[]> getVendorPhoneNoAndEmailId(final Long vendorId, final Long orgId) {
        final List<Object[]> vendorList = tbAcVendormasterJpaRepository.getVendorPhoneNoAndEmailId(vendorId, orgId);

        return vendorList;
    }

    /**
     * get All Active vendors which are mapped and non mapped with secondary head code by Organization Id and active status id of
     * vender status prefix 'VSS'
     * @param orgid
     * @param vendorStatus
     * @return List<TbAcVendormaster>
     */
    @Override
    public List<TbAcVendormaster> getAllActiveVendors(Long orgid, Long vendorStatus) {
        final Iterable<TbAcVendormasterEntity> entities = tbAcVendormasterJpaRepository.getAllActiveVendors(orgid,
                vendorStatus);
        final List<TbAcVendormaster> beans = new ArrayList<>();
        for (final TbAcVendormasterEntity tbAcVendormasterEntity : entities) {
            beans.add(
                    tbAcVendormasterServiceMapper.mapTbAcVendormasterEntityToTbAcVendormaster(tbAcVendormasterEntity));
        }
        return beans;
    }

    @Override
    public void saveVendorMasterExcelData(VendorMasterUploadDto vendorMasterUploadDto, Organisation defaultOrg, int langId,
            List<LookUp> venderType, Long subTypeId) {

        /*
         * TbAcVendormasterEntity vendormasterEntity = new TbAcVendormasterEntity();
         * vendormasterEntity.setUserId(vendorMasterUploadDto.getUserId());
         * vendormasterEntity.setLangId(vendorMasterUploadDto.getLangId());
         * vendormasterEntity.setOrgid(vendorMasterUploadDto.getOrgid());
         * vendormasterEntity.setLmoddate(vendorMasterUploadDto.getLmoddate());
         * vendormasterEntity.setLgIpMac(vendorMasterUploadDto.getLgIpMac());
         * vendormasterEntity.setCpdVendorSubType(Long.valueOf(vendorMasterUploadDto.getType()));
         * vendormasterEntity.setCpdVendorSubType(Long.valueOf(vendorMasterUploadDto.getSubType()));
         * vendormasterEntity.setVmVendorname(vendorMasterUploadDto.getName());
         * vendormasterEntity.setVmVendoradd(vendorMasterUploadDto.getAddress());
         * vendormasterEntity.setEmailId(vendorMasterUploadDto.getEmailId());
         * vendormasterEntity.setMobileNo(vendorMasterUploadDto.getMobileNum().toString());
         * vendormasterEntity.setVmPanNumber(vendorMasterUploadDto.getPanNum());
         * vendormasterEntity.setVmUidNo(vendorMasterUploadDto.getUidNum());
         * vendormasterEntity.setVmGstNo(vendorMasterUploadDto.getGstNum());
         * vendormasterEntity.setBankId(Long.valueOf(vendorMasterUploadDto.getBankBranchIfsc()));
         * vendormasterEntity.setBankaccountnumber(vendorMasterUploadDto.getBankAcNum());
         * vendormasterEntity.setVmVendornamePayto(vendorMasterUploadDto.getPayTo());
         * vendormasterEntity.setPacHeadId(Long.valueOf(vendorMasterUploadDto.getPrimaryAccountHead()));
         */
        // tbAcVendormasterJpaRepository.save(vendormasterEntity);

        Organisation org = new Organisation();
        org.setOrgid(vendorMasterUploadDto.getOrgid());

        TbAcVendormaster tbAcVendormaster = new TbAcVendormaster();
        tbAcVendormaster.setUserId(vendorMasterUploadDto.getUserId());
        tbAcVendormaster.setLangId(vendorMasterUploadDto.getLangId());
        tbAcVendormaster.setOrgid(vendorMasterUploadDto.getOrgid());
        tbAcVendormaster.setLmoddate(vendorMasterUploadDto.getLmoddate());
        tbAcVendormaster.setLgIpMac(vendorMasterUploadDto.getLgIpMac());
        tbAcVendormaster.setCpdVendortype(Long.valueOf(vendorMasterUploadDto.getType()));
        tbAcVendormaster.setCpdVendorSubType(Long.valueOf(vendorMasterUploadDto.getSubType()));
        tbAcVendormaster.setVmVendorname(vendorMasterUploadDto.getName());
        tbAcVendormaster.setVmVendoradd(vendorMasterUploadDto.getAddress());
        tbAcVendormaster.setEmailId(vendorMasterUploadDto.getEmailId());
        if (vendorMasterUploadDto.getMobileNum() != null) {
            tbAcVendormaster.setMobileNo(vendorMasterUploadDto.getMobileNum().toString());
        }
        tbAcVendormaster.setVmPanNumber(vendorMasterUploadDto.getPanNum());
        tbAcVendormaster.setVmUidNo(vendorMasterUploadDto.getUidNum());
        tbAcVendormaster.setVmGstNo(vendorMasterUploadDto.getGstNum());
        if (vendorMasterUploadDto.getBankBranchIfsc() != null) {
            tbAcVendormaster.setBankId(Long.valueOf(vendorMasterUploadDto.getBankId()));
            tbAcVendormaster.setIfscCode(vendorMasterUploadDto.getBankBranchIfsc());
        }
        tbAcVendormaster.setBankaccountnumber(vendorMasterUploadDto.getBankAcNum());
        tbAcVendormaster.setVmVendornamePayto(vendorMasterUploadDto.getPayTo());
        if (vendorMasterUploadDto.getPrimaryAccountHead() != null) {
            tbAcVendormaster.setPacHeadId(Long.valueOf(vendorMasterUploadDto.getPrimaryAccountHead()));
        }
        if (vendorMasterUploadDto.getFunction() != null) {
            tbAcVendormaster.setFunctionId(Long.valueOf(vendorMasterUploadDto.getFunction()));
        }
        if (vendorMasterUploadDto.getSliMode() != null) {
            tbAcVendormaster.setSliMode(vendorMasterUploadDto.getSliMode());
        }
        if (vendorMasterUploadDto.getVendorClassName() != null) {
            tbAcVendormaster.setVendorClassName(CommonMasterUtility
                    .getNonHierarchicalLookUpObjectByPrefix(Long.valueOf(vendorMasterUploadDto.getVendorClassName()),
                            vendorMasterUploadDto.getOrgid(), PrefixConstants.TbAcVendormaster.VEC)
                    .getDescLangFirst());
            tbAcVendormaster.setVendorClass(Long.valueOf(vendorMasterUploadDto.getVendorClassName()));
        }
        tbAcVendormaster.setAccOldHeadCode(vendorMasterUploadDto.getAccOldHeadCode());
        create(tbAcVendormaster, venderType, subTypeId, defaultOrg, org, langId);

    }

	@Override
	public void updateUploadedFileDeleteRecords(List<Long> removeFileById, Long updatedBy) {
		attachDocsDao.updateRecord(removeFileById, updatedBy, MainetConstants.RnLCommon.Flag_D);
	}

	@Override
	public void documentUpload(List<DocumentDetailsVO> attach, FileUploadDTO uploadDTO,long empId, long orgid ) {	
		uploadDTO.setOrgId(orgid);
		uploadDTO.setStatus(MainetConstants.FlagA);
		uploadDTO.setDepartmentName(MainetConstants.RECEIPT_MASTER.Module);
		uploadDTO.setUserId(empId);						
		fileUploadservice.doMasterFileUpload(attach, uploadDTO);		
	}

}