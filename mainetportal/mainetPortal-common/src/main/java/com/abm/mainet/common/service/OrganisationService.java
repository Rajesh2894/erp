package com.abm.mainet.common.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dao.IOrganisationDAO;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.OrganisationEntity;
import com.abm.mainet.common.domain.RoleEntitlement;
import com.abm.mainet.common.domain.SystemModuleFunction;
import com.abm.mainet.common.domain.ViewOrgDetails;
import com.abm.mainet.common.dto.OrganisationDTO;
import com.abm.mainet.common.dto.PortalServiceDTO;
import com.abm.mainet.common.dto.TbOrganisationRest;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.integration.ws.JersyCall;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Swapnil.Pisat
 */
@Service
public class OrganisationService implements IOrganisationService {

	private static Logger log = Logger.getLogger(OrganisationService.class);
    /**
     * 
     */
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 396646840086668589L;
    @Autowired
    private IOrganisationDAO organisationDAO;

    @Autowired
    private IEntitlementService entitlementService;

    @Autowired
    private IEmployeeService iEmployeeService;

    @Autowired
    private IDepartmentService iDepartmentService;

    @Autowired
    private IDesignationService iDesignationService;

    @Autowired
    private IServiceMasterService iServiceMasterService;

    @Autowired
    private IGroupMasterService iGroupMasterService;

    /*
     * @Autowired AboutProjectRepository aboutProjectRepository;
     */

    @Override
    @Transactional
    public Organisation getOrganisationById(final long orgid) {

        return organisationDAO.getOrganisationById(orgid, MainetConstants.STATUS.ACTIVE);
    }

    /**
     * Get SuperUser {@link Organisation} means Organization.defaultStatus = 'Y'.
     * @return List<{@link Organisation}>
     */
    @Override
    @Transactional
    public Organisation getSuperUserOrganisation() {
        final List<Organisation> organisations = organisationDAO.getOrganisations(MainetConstants.Organisation.SUPER_ORG_STATUS,
                MainetConstants.STATUS.ACTIVE);
        return organisations.get(0);
    }

    @Override
    @Transactional
    public List<Organisation> getAllMunicipalOrganisation(final long districtCpdId) {
        final List<Organisation> organisations = organisationDAO.getOrganisations(MainetConstants.BLANK,
                MainetConstants.STATUS.ACTIVE,
                districtCpdId);
        return organisations;
    }

    @Override
    @Transactional(readOnly = true)
    public Long findCountOfActiveOrg() {

        return organisationDAO.findCountOfOrg();
    }

    @Override
    @Transactional
    public OrganisationEntity create(OrganisationEntity org) {
        return organisationDAO.create(org);
    }

    @Override
    @Transactional
    public void createDefault(OrganisationEntity org, ApplicationSession appSession, TbOrganisationRest tbOrgRest) {

        Organisation orgSaved = new Organisation();
        orgSaved.setOrgid(org.getOrgid());

        GroupMaster adminGrpMaster = new GroupMaster();
        GroupMaster adminGrpMasterSvd = null;
        adminGrpMaster.setGrCode(appSession.getMessage("groupmaster.GrCode"));
        adminGrpMaster.setGrDescEng(appSession.getMessage("groupmaster.GrDescEng"));
        adminGrpMaster.setGrDescReg(appSession.getMessage("groupmaster.GrDescReg"));
        adminGrpMasterSvd = createDefaultGroup(orgSaved, adminGrpMaster);

        GroupMaster citizenGrpMaster = new GroupMaster();
        citizenGrpMaster.setGrCode(appSession.getMessage("groupmaster.GrCode1"));
        citizenGrpMaster.setGrDescEng(appSession.getMessage("groupmaster.GrDescEng1"));
        citizenGrpMaster.setGrDescReg(appSession.getMessage("groupmaster.GrDescReg1"));
        createDefaultGroup(orgSaved, citizenGrpMaster);

        GroupMaster agencyGrpMaster = new GroupMaster();
        agencyGrpMaster.setGrCode(appSession.getMessage("groupmaster.GrCode2"));
        agencyGrpMaster.setGrDescEng(appSession.getMessage("groupmaster.GrDescEng2"));
        agencyGrpMaster.setGrDescReg(appSession.getMessage("groupmaster.GrDescReg2"));
        createDefaultGroup(orgSaved, agencyGrpMaster);

        GroupMaster adminChkGrpMaster = new GroupMaster();
        GroupMaster adminChkGrpMasterSvd = null;
        adminChkGrpMaster.setGrCode(appSession.getMessage("groupmaster.GrCode3"));
        adminChkGrpMaster.setGrDescEng(appSession.getMessage("groupmaster.GrDescEng3"));
        adminChkGrpMaster.setGrDescReg(appSession.getMessage("groupmaster.GrDescReg3"));
        adminChkGrpMasterSvd = createDefaultGroup(orgSaved, adminChkGrpMaster);

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
        empSession.setEmpId(tbOrgRest.getUserId());

        Department cfcDepartment = new Department();
        cfcDepartment = iDepartmentService.getDepartment(MainetConstants.DEPARTMENT.CFC_CODE, MainetConstants.STATUS.ACTIVE);

        Employee mbaEmp = new Employee();
        /*
         * mbaEmp.setEmpname(appSession.getMessage("employee.mba.empname"));
         * mbaEmp.setEmploginname(appSession.getMessage("employee.mba.emploginname"));
         * mbaEmp.setUserAlias(appSession.getMessage("employee.mba.emploginname"));
         * mbaEmp.setEmppassword(Utility.encryptPassword(appSession.getMessage("employee.mba.emploginname"),
         * appSession.getMessage("employee.emppassword")));
         */

        // default employee login details
        if (tbOrgRest.getPortalEmpDto() != null) {
            mbaEmp.setEmpname(tbOrgRest.getPortalEmpDto().getEmpname());
            mbaEmp.setEmpMName(tbOrgRest.getPortalEmpDto().getEmpmname());
            mbaEmp.setEmpLName(tbOrgRest.getPortalEmpDto().getEmplname());
            mbaEmp.setEmploginname(tbOrgRest.getPortalEmpDto().getEmploginname());
            mbaEmp.setUserAlias(tbOrgRest.getPortalEmpDto().getEmploginname());
            mbaEmp.setEmppassword(tbOrgRest.getPortalEmpDto().getEmppassword());
            mbaEmp.setEmpmobno(tbOrgRest.getPortalEmpDto().getEmpmobno());
            mbaEmp.setEmpemail(tbOrgRest.getPortalEmpDto().getEmpemail());
        }

        mbaEmp.setAutMob(MainetConstants.IsDeleted.NOT_DELETE);
        mbaEmp.setDesignation(adminDesignation);
        mbaEmp.setTbDepartment(cfcDepartment);
        mbaEmp.setUserId(empSession);
        mbaEmp.setLmodDate(new Date());
        mbaEmp.setOndate(new Date());
        mbaEmp.setLangId(tbOrgRest.getLangId());
        mbaEmp.setIsUploaded(MainetConstants.IsUploaded.NOT_UPLOADED);
        mbaEmp.setGmid(adminGrpMasterSvd.getGmId());
        mbaEmp.setOrganisation(orgSaved);
        mbaEmp.setIsdeleted(MainetConstants.IsDeleted.ZERO);
        mbaEmp.setAutMob(MainetConstants.AUTH);
        iEmployeeService.create(mbaEmp);

        Employee guestEmp = new Employee();
        guestEmp.setEmpname(appSession.getMessage("employee.guest.empname"));
        guestEmp.setEmploginname(appSession.getMessage("employee.guest.emploginname"));
        guestEmp.setUserAlias(appSession.getMessage("employee.guest.emploginname"));
        guestEmp.setEmppassword(Utility.encryptPassword(appSession.getMessage("employee.guest.emploginname"),
                appSession.getMessage("employee.emppassword")));
        guestEmp.setAutMob(MainetConstants.IsDeleted.NOT_DELETE);
        guestEmp.setDesignation(ctznDesignation);
        guestEmp.setTbDepartment(cfcDepartment);
        guestEmp.setUserId(empSession);
        guestEmp.setLmodDate(new Date());
        guestEmp.setOndate(new Date());
        guestEmp.setLangId(tbOrgRest.getLangId());
        guestEmp.setIsUploaded(MainetConstants.IsUploaded.NOT_UPLOADED);
        guestEmp.setGmid(null);
        guestEmp.setOrganisation(orgSaved);
        guestEmp.setIsdeleted(MainetConstants.IsDeleted.ZERO);
        guestEmp.setAutMob(MainetConstants.AUTH);
        iEmployeeService.create(guestEmp);

        /*
         * Employee mbaChkEmp = new Employee();
         * mbaChkEmp.setEmpname(appSession.getMessage("employee.mbachk.empname"));
         * mbaChkEmp.setEmploginname(appSession.getMessage("employee.mbachk.emploginname"));
         * mbaChkEmp.setUserAlias(appSession.getMessage("employee.mbachk.emploginname"));
         * mbaChkEmp.setEmppassword(Utility.encryptPassword(appSession.getMessage("employee.mbachk.emploginname"),
         * appSession.getMessage("employee.emppassword")));
         * mbaChkEmp.setAutMob(MainetConstants.IsDeleted.NOT_DELETE);
         * mbaChkEmp.setDesignation(ctznDesignation);
         * mbaChkEmp.setTbDepartment(cfcDepartment);
         * mbaChkEmp.setUserId(empSession);
         * mbaChkEmp.setLmodDate(new Date());
         * mbaChkEmp.setOndate(new Date());
         * mbaChkEmp.setLangId(tbOrgRest.getLangId());
         * mbaChkEmp.setIsUploaded(MainetConstants.IsUploaded.NOT_UPLOADED);
         * mbaChkEmp.setGmid(adminChkGrpMasterSvd.getGmId());
         * mbaChkEmp.setOrganisation(orgSaved);
         * mbaChkEmp.setIsdeleted(MainetConstants.IsDeleted.ZERO);
         * mbaChkEmp.setAutMob(MainetConstants.AUTH);
         * iEmployeeService.create(mbaChkEmp);
         */

        if (tbOrgRest.getServiceMstList() != null) {
            for (PortalServiceDTO serviceDto : tbOrgRest.getServiceMstList()) {
                iServiceMasterService.createPortalService(serviceDto);
            }
        }
        // Re-Loading Application prefix
        ApplicationSession.getInstance().loadPrefixData();
    }

    @Override
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
    public List<ViewOrgDetails> getAllMunicipalOrganisationNew(
            final long districtCpdId) {

        return organisationDAO.getOrganisationsNew(districtCpdId);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.common.service.IOrganisationService#findAllActiveOrganization(java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public List<Organisation> findAllActiveOrganization(final String orgStatus) {
        return organisationDAO.findAllActiveOrganization(orgStatus);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Set<Organisation>> findAllActiveOrganizationByULBOrgId(final String orgStatus, int langId) {

        List<Organisation> list = organisationDAO.findAllActiveOrganizationByOrgId(orgStatus);
        Map<String, Set<Organisation>> orgMap = new HashMap<>();
        Set<Organisation> organisations = null;
        String englishDesc = null;
        if (langId == MainetConstants.ENGLISH) {
            englishDesc = MainetConstants.D2KFUNCTION.ENGLISH_DESC;
        } else {
            englishDesc = MainetConstants.D2KFUNCTION.REG_DESC;
        }
        for (Organisation org : list) {
        	
            String key = CommonMasterUtility.getCPDDescription(org.getOrgCpdId(), englishDesc);

            if (orgMap.containsKey(key)) {

                orgMap.get(key).add(org);

            } else {
                organisations = new HashSet<>();
                organisations.add(org);
                orgMap.put(key, organisations);
            }
        }

        return orgMap;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Organisation> findAllActiveOrganizationForHomePage(final String orgStatus, int langId) {
        return organisationDAO.findAllActiveOrganizationForHomePage(orgStatus, langId);
    }
    

    

	@Override
	@Transactional
	public List<OrganisationDTO> getAllOrganisationActiveWorkflow(final String deptCode) {
		@SuppressWarnings("unchecked")
		List<LinkedHashMap<Long, Object>> responseVo = (List<LinkedHashMap<Long, Object>>) JersyCall
				.callRestTemplateClient(deptCode,ServiceEndpoints.GET_ORGANISATION_WITH_ACTIVE_WORKFLOW + deptCode);
		List<OrganisationDTO> dtos = new ArrayList<>();
		try {
			for (LinkedHashMap<Long, Object> obj : responseVo) {
				final String d = new JSONObject(obj).toString();
				OrganisationDTO dto = new ObjectMapper().readValue(d, OrganisationDTO.class);
				dtos.add(dto);
			}
		} catch (Exception e) {
			log.info("Error Occured in getAllOrganisationActiveWorkflow()" + e);
		}
		return dtos;

	}

	@Override
	public Organisation getActiveOrgByUlbShortCode(String ulbShortCode) {
		return organisationDAO.getActiveOrgByUlbShortCode(ulbShortCode, MainetConstants.FlagA);
	}
	
	@Override
	public Organisation getOrgByOrgShortCode(String ulbShortCode) {
		return organisationDAO.getOrgByOrgShortCode(ulbShortCode);
	}
}
