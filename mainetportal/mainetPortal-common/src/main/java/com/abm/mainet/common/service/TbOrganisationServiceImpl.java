package com.abm.mainet.common.service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.dao.TbOrganisationJpaRepository;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.OrganisationHistory;
import com.abm.mainet.common.domain.RoleEntitlement;
import com.abm.mainet.common.domain.SystemModuleFunction;
import com.abm.mainet.common.domain.TbComparamDetEntity;
import com.abm.mainet.common.domain.TbComparamMasEntity;
import com.abm.mainet.common.domain.TbComparentMasEntity;
import com.abm.mainet.common.dto.PortalServiceDTO;
import com.abm.mainet.common.dto.TbComparamDet;
import com.abm.mainet.common.dto.TbComparentMas;
import com.abm.mainet.common.dto.TbOrganisationRest;
import com.abm.mainet.common.master.mapper.TbOrganisationServiceMapper;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.client.FileNetApplicationClient;
import com.abm.mainet.dms.utility.FileUploadUtility;

@Service
public class TbOrganisationServiceImpl implements TbOrganisationService {

    @Autowired
    private TbOrganisationJpaRepository OrganisationJpaRepository;

    @Autowired
    private IEntitlementService entitlementService;

    @Autowired
    private IGroupMasterService iGroupMasterService;

    @Autowired
    private IDepartmentService iDepartmentService;

    @Autowired
    private IDesignationService iDesignationService;

    @Autowired
    private IEmployeeService iEmployeeService;

    @Autowired
    private IServiceMasterService iServiceMasterService;

    @Autowired
    private TbComparamMasService tbComparamMasService;

    @Autowired
    private TbComparamDetService tbComparamDetService;

    @Autowired
    private TbComparentMasService tbComparentMasService;

    @Autowired
    private TbOrganisationServiceMapper tbOrganisationServiceMapper;

    @Autowired
    private AuditService auditService;

    private static final Logger LOGGER = Logger.getLogger(TbOrganisationServiceImpl.class);

    @Override
    @Transactional
    public TbOrganisationRest findById(final Long orgid) {

        final Organisation tbOrganisationEntity = OrganisationJpaRepository.findOne(orgid);
        return tbOrganisationServiceMapper.mapTbOrganisationEntityToTbOrganisation(tbOrganisationEntity);
    }

    @Override
    @Transactional
    public Set<TbOrganisationRest> findAll() {

        final Set<Organisation> entities = OrganisationJpaRepository.findActiveOrgList();

        final Set<TbOrganisationRest> beans = new HashSet<>();

        for (final Organisation tbOrganisationEntity : entities) {
            beans.add(tbOrganisationServiceMapper.mapTbOrganisationEntityToTbOrganisation(tbOrganisationEntity));
        }
        return beans;
    }

    @Override
    @Transactional
    public boolean defaultexist(final String defaultStatus) {
        final List<Organisation> list = OrganisationJpaRepository.defaultexist(defaultStatus);
        if ((list != null) && !list.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public TbOrganisationRest create(TbOrganisationRest organisationDTO, ApplicationSession appSession,
            UserSession userSession,
            String directory, FileNetApplicationClient fileNetApplicationClient) throws JsonGenerationException,
            JsonMappingException, URISyntaxException, IOException, IllegalAccessException, InvocationTargetException {

        Organisation OrganisationEntity = new Organisation();
        organisationDTO.setOrgStatus(MainetConstants.STATUS.ACTIVE);

        List<File> list = null;
        for (Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            list = new ArrayList<File>(entry.getValue());
            Iterator<File> setFilesItr = entry.getValue().iterator();
            String tempDirPath = MainetConstants.operator.EMPTY;
            while (setFilesItr.hasNext()) {
                File file = setFilesItr.next();
                tempDirPath = directory + MainetConstants.FILE_PATH_SEPARATOR + entry.getKey().toString();

                if (entry.getKey().longValue() == 0) {
                    organisationDTO.setOLogo(tempDirPath + MainetConstants.FILE_PATH_SEPARATOR + file.getName());
                }

                try {
                    fileNetApplicationClient.uploadFileList(list, tempDirPath);
                } catch (Exception e) {
                    LOGGER.error(MainetConstants.ERROR_OCCURED, e);
                    return organisationDTO;
                }
            }
        }

        tbOrganisationServiceMapper.mapTbOrganisationToTbOrganisationEntity(organisationDTO, OrganisationEntity);

        Organisation newOrganisationEntitySaved = OrganisationJpaRepository.save(OrganisationEntity);
        OrganisationHistory orgHist = new OrganisationHistory();
        orgHist.setStatus(MainetConstants.InsertMode.ADD.getStatus());
        auditService.createHistory(newOrganisationEntitySaved, orgHist);
        createDefaultPrefixes(newOrganisationEntitySaved, userSession);
        createDefaults(newOrganisationEntitySaved, userSession, appSession);
        // Re-Loading Application prefix
        ApplicationSession.getInstance().loadPrefixData();

        return tbOrganisationServiceMapper.mapTbOrganisationEntityToTbOrganisation(OrganisationEntity);

    }

    @Override
    public void createDefaults(Organisation orgMasterEntitySaved, UserSession userSession, ApplicationSession appSession)
            throws IllegalAccessException, InvocationTargetException {

        Organisation org = new Organisation();
        org.setOrgid(orgMasterEntitySaved.getOrgid());

        GroupMaster adminGrpMaster = new GroupMaster();
        GroupMaster adminGrpMasterSvd = null;
        adminGrpMaster.setGrCode(appSession.getMessage("groupmaster.GrCode"));
        adminGrpMaster.setGrDescEng(appSession.getMessage("groupmaster.GrDescEng"));
        adminGrpMaster.setGrDescReg(appSession.getMessage("groupmaster.GrDescReg"));
        adminGrpMasterSvd = createDefaultGroup(org, adminGrpMaster);

        GroupMaster citizenGrpMaster = new GroupMaster();
        citizenGrpMaster.setGrCode(appSession.getMessage("groupmaster.GrCode1"));
        citizenGrpMaster.setGrDescEng(appSession.getMessage("groupmaster.GrDescEng1"));
        citizenGrpMaster.setGrDescReg(appSession.getMessage("groupmaster.GrDescReg1"));
        createDefaultGroup(org, citizenGrpMaster);

        GroupMaster agencyGrpMaster = new GroupMaster();
        agencyGrpMaster.setGrCode(appSession.getMessage("groupmaster.GrCode2"));
        agencyGrpMaster.setGrDescEng(appSession.getMessage("groupmaster.GrDescEng2"));
        agencyGrpMaster.setGrDescReg(appSession.getMessage("groupmaster.GrDescReg2"));
        createDefaultGroup(org, agencyGrpMaster);

        GroupMaster adminChkGrpMaster = new GroupMaster();
        GroupMaster adminChkGrpMasterSvd = null;
        adminChkGrpMaster.setGrCode(appSession.getMessage("groupmaster.GrCode3"));
        adminChkGrpMaster.setGrDescEng(appSession.getMessage("groupmaster.GrDescEng3"));
        adminChkGrpMaster.setGrDescReg(appSession.getMessage("groupmaster.GrDescReg3"));
        adminChkGrpMasterSvd = createDefaultGroup(org, adminChkGrpMaster);

        Department department = iDepartmentService.getDepartment(MainetConstants.DEPARTMENT.ONLINE_SERVICES_CODE,
                MainetConstants.STATUS.ACTIVE);

        if (department == null) {
            department = new Department();
            department.setDpDeptcode(MainetConstants.DEPARTMENT.ONLINE_SERVICES_CODE);
            department.setStatus(MainetConstants.STATUS.ACTIVE);
            department.setDpDeptdesc(MainetConstants.DEPARTMENT.ONLINE_DEPT_DESC);
            department.setDpNameMar(MainetConstants.DEPARTMENT.ONLINE_DEPT_DESC);
            department.setUserId(org.getUserId().intValue());
            department.setLmoddate(new Date());
            iDepartmentService.create(department);
        }

        Designation adminDesignation = iDesignationService
                .getDesignationByName(MainetConstants.DESIGNATION.ADMIN_DESIGNATION_NAME);
        if (adminDesignation == null) {
            adminDesignation = new Designation();
            adminDesignation.setDsgname(appSession.getMessage("admin.designation.name"));
            adminDesignation.setDsgnameReg(appSession.getMessage("admin.designation.name"));
            adminDesignation.setDsgshortname(appSession.getMessage("admin.designation.shortname"));
            adminDesignation.setLgIpMac(Utility.getMacAddress());
            adminDesignation.setLmoddate(new Date());
            adminDesignation.setUserId(org.getUserId());
            adminDesignation = iDesignationService.create(adminDesignation);
        }

        Designation ctznDesignation = iDesignationService
                .getDesignationByName(MainetConstants.DESIGNATION.CITIZEN_DESIGNATION_NAME);
        if (ctznDesignation == null) {
            ctznDesignation = new Designation();
            ctznDesignation.setDsgname(appSession.getMessage("citizen.designation.name"));
            ctznDesignation.setDsgnameReg(appSession.getMessage("citizen.designation.name"));
            ctznDesignation.setDsgshortname(appSession.getMessage("citizen.designation.shortname"));
            ctznDesignation.setLgIpMac(Utility.getMacAddress());
            ctznDesignation.setLmoddate(new Date());
            ctznDesignation.setUserId(org.getUserId());
            ctznDesignation = iDesignationService.create(ctznDesignation);
        }

        Employee empSession = new Employee();

        TbOrganisationRest tbOrgRest = new TbOrganisationRest();

        empSession.setEmpId(tbOrgRest.getUserId());

        Department cfcDepartment = new Department();
        cfcDepartment = iDepartmentService.getDepartment(MainetConstants.DEPARTMENT.CFC_CODE,
                MainetConstants.STATUS.ACTIVE);
        Employee mbaEmp = new Employee();
        mbaEmp.setEmpname(appSession.getMessage("employee.mba.empname"));
        mbaEmp.setEmploginname(appSession.getMessage("employee.mba.emploginname"));
        mbaEmp.setUserAlias(appSession.getMessage("employee.mba.emploginname"));
        mbaEmp.setEmppassword(Utility.encryptPassword(appSession.getMessage("employee.mba.emploginname"),
                appSession.getMessage("employee.emppassword")));
        mbaEmp.setAutMob(MainetConstants.IsDeleted.DELETE);
        mbaEmp.setDesignation(adminDesignation);
        mbaEmp.setTbDepartment(cfcDepartment);
        mbaEmp.setUserId(empSession);
        mbaEmp.setLmodDate(new Date());
        mbaEmp.setOndate(new Date());
        mbaEmp.setLangId(tbOrgRest.getLangId());
        mbaEmp.setIsUploaded(MainetConstants.IsUploaded.NOT_UPLOADED);
        mbaEmp.setGmid(adminGrpMasterSvd.getGmId());
        mbaEmp.setOrganisation(org);
        mbaEmp.setIsdeleted(MainetConstants.IsDeleted.ZERO);
        iEmployeeService.create(mbaEmp);

        Employee guestEmp = new Employee();

        guestEmp.setEmpname(appSession.getMessage("employee.guest.empname"));
        guestEmp.setEmploginname(appSession.getMessage("employee.guest.emploginname"));
        guestEmp.setUserAlias(appSession.getMessage("employee.guest.emploginname"));
        guestEmp.setEmppassword(Utility.encryptPassword(appSession.getMessage("employee.guest.emploginname"),
                appSession.getMessage("employee.emppassword")));
        guestEmp.setAutMob(MainetConstants.IsDeleted.DELETE);
        guestEmp.setDesignation(ctznDesignation);
        guestEmp.setTbDepartment(cfcDepartment);
        guestEmp.setUserId(empSession);
        guestEmp.setLmodDate(new Date());
        guestEmp.setOndate(new Date());
        guestEmp.setLangId(tbOrgRest.getLangId());
        guestEmp.setIsUploaded(MainetConstants.IsUploaded.NOT_UPLOADED);
        guestEmp.setGmid(null);
        guestEmp.setOrganisation(org);
        guestEmp.setIsdeleted(MainetConstants.IsDeleted.ZERO);
        iEmployeeService.create(guestEmp);

        Employee mbaChkEmp = new Employee();

        mbaChkEmp.setEmpname(appSession.getMessage("employee.mbachk.empname"));
        mbaChkEmp.setEmploginname(appSession.getMessage("employee.mbachk.emploginname"));
        mbaChkEmp.setUserAlias(appSession.getMessage("employee.mbachk.emploginname"));
        mbaChkEmp.setEmppassword(Utility.encryptPassword(appSession.getMessage("employee.mbachk.emploginname"),
                appSession.getMessage("employee.emppassword")));
        mbaChkEmp.setAutMob(MainetConstants.IsDeleted.DELETE);
        mbaChkEmp.setDesignation(ctznDesignation);
        mbaChkEmp.setTbDepartment(cfcDepartment);
        mbaChkEmp.setUserId(empSession);
        mbaChkEmp.setLmodDate(new Date());
        mbaChkEmp.setOndate(new Date());
        mbaChkEmp.setLangId(tbOrgRest.getLangId());
        mbaChkEmp.setIsUploaded(MainetConstants.IsUploaded.NOT_UPLOADED);
        mbaChkEmp.setGmid(adminChkGrpMasterSvd.getGmId());
        mbaChkEmp.setOrganisation(org);
        mbaChkEmp.setIsdeleted(MainetConstants.IsDeleted.ZERO);
        iEmployeeService.create(mbaChkEmp);
        if (tbOrgRest.getServiceMstList() != null) {
            for (PortalServiceDTO serviceMst : tbOrgRest.getServiceMstList()) {
                iServiceMasterService.createPortalService(serviceMst);
            }
        }

    }

    @Transactional
    public GroupMaster createDefaultGroup(Organisation org, GroupMaster groupMst) {

        ApplicationSession appSession = ApplicationSession.getInstance();
        List<SystemModuleFunction> sysModFunctionList = entitlementService.findBySmfaction(
                appSession.getMessage("systemmodulefunction.smfaction"), appSession.getMessage("systemmodulefunction.smfname"));

        RoleEntitlement roleEntitlement = null;
        GroupMaster groupMaster = new GroupMaster();
        groupMaster.setOrgId(org);
        groupMaster.setGrCode(groupMst.getGrCode());
        groupMaster.setGrDescEng(groupMst.getGrDescEng());
        groupMaster.setGrDescReg(groupMst.getGrDescReg());
        groupMaster.setGrStatus(MainetConstants.Common_Constant.ACTIVE_FLAG);

        if (groupMst.getGrCode().equals(MainetConstants.GROUPMASTER.ADMIN_GROUP_CODE)) {
            Set<RoleEntitlement> entitlementSet = new HashSet<RoleEntitlement>();
            for (SystemModuleFunction sysmodFunction : sysModFunctionList) {
                roleEntitlement = new RoleEntitlement();
                if (sysmodFunction.getModuleFunction() == null) {
                    roleEntitlement.setParentId(Long.valueOf(0));
                } else {
                    roleEntitlement.setParentId(Long.valueOf(1));
                }
                roleEntitlement.setEntitle(sysmodFunction);
                roleEntitlement.setIsActive(MainetConstants.Common_Constant.ZERO_SEC);
                roleEntitlement.setOrganisation(org);
                roleEntitlement.setUpdatedDate(new Date());
                roleEntitlement.setGroupMaster(groupMaster);
                entitlementSet.add(roleEntitlement);
            }
            groupMaster.setEntitlements(entitlementSet);
        }
        return iGroupMasterService.create(groupMaster);
    }

    @Override
    @Transactional
    public void createDefaultPrefixes(final Organisation tbOrganisationEntitySaved, final UserSession userSession) {

        List<TbComparamMasEntity> tbComparamMasEntity = null;
        Long cpmId = null;
        List<TbComparamDetEntity> tbComparamDetlist = null;
        TbComparamDet tbComparamBean = null;
        tbComparamMasEntity = tbComparamMasService.findAllByCpmReplicateFlag(MainetConstants.Common_Constant.YES,
                MainetConstants.Common_Constant.NO);
        for (TbComparamMasEntity tbCmprm : tbComparamMasEntity) {
            cpmId = tbCmprm.getCpmId();
            tbComparamDetlist = tbComparamDetService.findCmprmDetDataByCpmId(cpmId);
            for (TbComparamDetEntity tbComparamDetEntityList : tbComparamDetlist) {
                tbComparamBean = new TbComparamDet();
                tbComparamBean.setOrgid(tbOrganisationEntitySaved.getOrgid());
                tbComparamBean.setCpdDefault(tbComparamDetEntityList.getCpdDefault());
                tbComparamBean.setCpdDesc(tbComparamDetEntityList.getCpdDesc());
                tbComparamBean.setCpdStatus(tbComparamDetEntityList.getCpdStatus());
                tbComparamBean.setCpdDescMar(tbComparamDetEntityList.getCpdDescMar());
                tbComparamBean.setCpdValue(tbComparamDetEntityList.getCpdValue());
                tbComparamBean.setUserId(userSession.getEmployee().getEmpId());
                tbComparamBean.setLmoddate(new Date());
                tbComparamBean.setLangId(Long.valueOf(userSession.getLanguageId()));
                tbComparamBean.setCpmId(cpmId);
                tbComparamBean.setUpdatedBy(userSession.getEmployee().getEmpId());
                tbComparamBean.setUpdatedDate(new Date());
                tbComparamDetService.create(tbComparamBean);
            }

        }

        tbComparamMasEntity = tbComparamMasService.findAllByCpmReplicateFlag(MainetConstants.Common_Constant.YES,
                PrefixConstants.LookUp.HIERARCHICAL);
        List<TbComparentMas> tbComparentMasList = null;
        TbComparentMasEntity tbComparentMasEntity = null;
        for (TbComparamMasEntity tbComparam : tbComparamMasEntity) {
            cpmId = tbComparam.getCpmId();
            tbComparentMasList = tbComparentMasService.findComparentMasDataByCpmId(cpmId);
            for (TbComparentMas tbComparentMas : tbComparentMasList) {
                tbComparentMasEntity = new TbComparentMasEntity();
                tbComparentMasEntity.setTbComparamMas(tbComparam);
                tbComparentMasEntity.setComDesc(tbComparentMas.getComDesc());
                tbComparentMasEntity.setComDescMar(tbComparentMas.getComDescMar());
                tbComparentMasEntity.setComValue(tbComparentMas.getComValue());
                tbComparentMasEntity.setComLevel(tbComparentMas.getComLevel());
                tbComparentMasEntity.setOrgid(tbOrganisationEntitySaved.getOrgid());
                tbComparentMasEntity.setUserId(userSession.getEmployee().getEmpId());
                tbComparentMasEntity.setLangId(Long.valueOf(userSession.getLanguageId()));
                tbComparentMasEntity.setLmoddate(new Date());
                tbComparentMasEntity.setUpdatedBy(userSession.getEmployee().getEmpId());
                tbComparentMasEntity.setUpdatedDate(new Date());
                tbComparentMasService.create(tbComparentMasEntity);
            }
        }

    }

    @Override
    public List<String> exist(String mode, Long orgId, Long ulbOrgId, String orgName, String orgNameMar,
            ApplicationSession appSession) {

        final String orgNameNew = orgName.trim();
        final String orgNameRegNew = orgNameMar.trim();
        final List<String> errorList = new ArrayList<>();

        final Organisation ulbOrgIdObj = OrganisationJpaRepository.findOrgById(ulbOrgId);
        final Organisation orgNameObj = OrganisationJpaRepository.findData(orgNameNew);
        final Organisation orgNameRegObj = OrganisationJpaRepository.findDataByNameReg(orgNameRegNew);

        if (mode.equalsIgnoreCase("create")) {
            if (ulbOrgIdObj != null) {
                errorList.add(appSession.getMessage("tbOrganisation.error.orgIdExist"));
            }
            if (orgNameObj != null) {
                errorList.add(appSession.getMessage("tbOrganisation.error.orgNameExist"));
            }
            if (orgNameRegObj != null) {
                errorList.add(appSession.getMessage("tbOrganisation.error.orgNameRegExist"));
            }
        } else {
            if (ulbOrgIdObj != null) {
                if (!orgId.equals(ulbOrgIdObj.getOrgid())) {
                    errorList.add(appSession.getMessage("tbOrganisation.error.orgIdExist"));
                }
            }
            if (orgNameObj != null) {
                if (!orgId.equals(orgNameObj.getOrgid())) {
                    errorList.add(appSession.getMessage("tbOrganisation.error.orgNameExist"));
                }
            }
            if (orgNameRegObj != null) {
                if (!orgId.equals(orgNameRegObj.getOrgid())) {
                    errorList.add(appSession.getMessage("tbOrganisation.error.orgNameRegExist"));
                }
            }
        }
        return errorList;
    }

    @Override
    @Transactional
    public Organisation findByShortCode(final String orgShortCode) {

        return OrganisationJpaRepository.findByShortCode(orgShortCode);
    }

    @Override
    @Transactional
    public void delete(final Long orgid) {
        final Organisation tbOrganisationEntity = OrganisationJpaRepository.findOne(orgid);
        tbOrganisationEntity.setOrgStatus(MainetConstants.IsLookUp.INACTIVE);
        OrganisationJpaRepository.save(tbOrganisationEntity);
    }

    @Override
    @Transactional
    public TbOrganisationRest update(final TbOrganisationRest tbOrganisation, final String directry,
            final FileNetApplicationClient filenetClient) {
        final Organisation tbOrganisationEntity = OrganisationJpaRepository.findOrgById(tbOrganisation.getUlbOrgID());
        if ((tbOrganisation.getOrgStatus() == null) || (tbOrganisation.getOrgStatus() == MainetConstants.BLANK)) {
            tbOrganisation.setOrgStatus(MainetConstants.STATUS.ACTIVE);
        }
        List<File> list = null;
        if ((FileUploadUtility.getCurrent().getFileMap().entrySet() != null)
                && !FileUploadUtility.getCurrent().getFileMap().entrySet()
                        .isEmpty()) {
            for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility
                    .getCurrent().getFileMap().entrySet()) {
                list = new ArrayList<>(entry.getValue());
                final Iterator<File> setFilesItr = entry.getValue().iterator();
                String tempDirPath = MainetConstants.operator.EMPTY;
                while (setFilesItr.hasNext()) {
                    final File file = setFilesItr.next();
                    tempDirPath = directry
                            + MainetConstants.FILE_PATH_SEPARATOR
                            + tbOrganisation.getOrgid().toString()
                            + MainetConstants.FILE_PATH_SEPARATOR
                            + entry.getKey().toString();

                    if ((entry.getKey().longValue() == 0) && (file != null)) {
                        tbOrganisation.setOLogo((tempDirPath
                                + MainetConstants.FILE_PATH_SEPARATOR
                                + file.getName()));
                    } else {
                        tbOrganisation.setOLogo(tbOrganisationEntity.getOLogo());
                    }

                    try {
                        filenetClient.uploadFileList(list, tempDirPath);
                    } catch (final Exception e) {
                        LOGGER.error(MainetConstants.ERROR_OCCURED, e);
                        return tbOrganisation;
                    }
                }
            }
        } else {

            tbOrganisation.setOLogo(null);
        }
        flushServerFolder();

        tbOrganisationServiceMapper.mapTbOrganisationToTbOrganisationEntity(tbOrganisation, tbOrganisationEntity);
        final Organisation tbOrganisationEntitySaved = OrganisationJpaRepository.save(tbOrganisationEntity);
        OrganisationHistory orgHist = new OrganisationHistory();
        orgHist.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
        auditService.createHistory(tbOrganisationEntitySaved, orgHist);
        if (tbOrganisationEntitySaved.getDefaultStatus().equals(MainetConstants.Organisation.SUPER_ORG_STATUS)) {
            ApplicationSession.getInstance().setSuperUserOrganization(tbOrganisationEntitySaved);
        }

        return tbOrganisationServiceMapper.mapTbOrganisationEntityToTbOrganisation(tbOrganisationEntitySaved);

    }

    public void flushServerFolder() {
        try {
            final String path = FileUploadUtility.getCurrent()
                    .getExistingFolderPath();
            if (path != null) {
                final File cacheFolderStructure = new File(FileUploadUtility
                        .getCurrent().getExistingFolderPath());
                FileUtils.deleteDirectory(cacheFolderStructure);
            }
        } catch (final Exception e) {
            LOGGER.error(MainetConstants.ERROR_OCCURED, e);

        }
    }
}
