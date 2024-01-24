package com.abm.mainet.common.dao;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.ApplicationMasterConstant;
import com.abm.mainet.common.constant.MainetConstants.Common_Constant;
import com.abm.mainet.common.domain.ApplicationPortalMaster;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.domain.SystemModuleFunction;
import com.abm.mainet.common.dto.ApplicationDetail;
import com.abm.mainet.common.dto.ApplicationStatusResponseVO;
import com.abm.mainet.common.dto.PortalDepartmentDTO;
import com.abm.mainet.common.dto.PortalQuickServiceDTO;
import com.abm.mainet.common.util.UserSession;

@SuppressWarnings("unchecked")
@Repository
public class PortalServiceMasterDao extends AbstractDAO<Object> implements IPortalServiceMasterDao {
    protected final Logger LOGGER = Logger.getLogger(this.getClass());

    @Override
    public PortalService getService(final Long serviceId, final Long orgId) {
        final Query query = createQuery(
                "select am from PortalService am  WHERE am.serviceId = :serviceId AND am.serviceOrgId = :orgId");
        query.setParameter("serviceId", serviceId);
        query.setParameter("orgId", orgId);
        final PortalService appealMaster = (PortalService) query.getSingleResult();
        return appealMaster;
    }

    @Override
    public Long getSeviceId(final String shortCode, final Long orgId) {
        final Query query = createQuery(
                "select am from PortalService am  WHERE am.shortName = :shortName AND am.serviceOrgId = :orgId");
        query.setParameter("shortName", shortCode);
        query.setParameter("orgId", orgId);
        final PortalService appealMaster = (PortalService) query.getSingleResult();
        return appealMaster.getServiceId();
    }

    @Override
    public void saveApplicationMaster(final ApplicationPortalMaster applicationMaster) {
        entityManager.persist(applicationMaster);
    }

    @Override
    public String findServiceNameForServiceId(final Long serviceId) {
        final Query query = createQuery("select am from PortalService am  WHERE am.serviceId = :serviceId");
        query.setParameter("serviceId", serviceId);
        final PortalService portalService = (PortalService) query.getSingleResult();
        return portalService.getServiceName();
    }

    @Override
    public PortalService getPortalService(final Long serviceId) {
        final Query query = createQuery("select am from PortalService am  WHERE am.serviceId = :smServiceId");
        query.setParameter("smServiceId", serviceId);
        final PortalService portalService = (PortalService) query.getSingleResult();
        return portalService;
    }

    @Override
    public String getServiceURL(final Long psmSmfid) {
        final Query query = createQuery("select am from SystemModuleFunction am  WHERE am.smfid = :psmSmfid");
        query.setParameter("psmSmfid", psmSmfid);
        final SystemModuleFunction systemModuleFunction = (SystemModuleFunction) query.getSingleResult();
        return systemModuleFunction.getSmfaction();
    }

    @Override
    public boolean saveCfcCitizenId(final String cfcCitizenId, final Long userId) {
        final Query query = createQuery("update Employee emp set emp.userAlias = :cfcCitizenId where emp.empId = :empId");
        query.setParameter("cfcCitizenId", cfcCitizenId);
        query.setParameter("empId", userId);
        final int numberOfColumnsUpdated = query.executeUpdate();
        if (numberOfColumnsUpdated > 0) {
            return true;
        }
        return false;

    }

    @Override
    public String getServiceShortName(final Long psmDpDeptid) {
        final Query query = createQuery("select am from Department am  WHERE am.dpDeptid = :psmDpDeptid");
        query.setParameter("psmDpDeptid", psmDpDeptid);
        final Department department = (Department) query.getSingleResult();
        return department.getDpDeptcode();
    }

    @Override
    public Long getServiceDeptIdId(final Long serviceId) {
        final Query query = createQuery("select am from PortalService am  WHERE am.serviceId = :serviceId");
        query.setParameter("serviceId", serviceId);
        final PortalService portalService = (PortalService) query.getSingleResult();
        return portalService.getPsmDpDeptid();
    }

    @Override
    public String checkListAvailable(final long applicationId) {
        final Query query = createQuery("select am from ApplicationPortalMaster am  WHERE am.pamApplicationId = :applicationId");
        query.setParameter("applicationId", applicationId);
        final ApplicationPortalMaster applicationPortalMaster = (ApplicationPortalMaster) query.getSingleResult();
        return applicationPortalMaster.getPamChecklistApp();
    }

    @Override
    public List<ApplicationPortalMaster> getApplicationStatusOpen(final Organisation orgId) {

        List<ApplicationPortalMaster> appOpenIdList = null;
        final List<String> stsCodeList = new ArrayList<>();
        stsCodeList.add("Y");
        stsCodeList.add("F");
        // JOIN FETCH apm.orgId
        final String queryString = "select apm from ApplicationPortalMaster apm JOIN FETCH apm.orgId WHERE apm.pamApplicationStatus = :pamApplicationStatus "
                + " and apm.orgId.orgid =:orgId "
                + "and apm.pamPaymentStatus IN :stsCodeList";
        final Query query = createQuery(queryString);
        query.setParameter("pamApplicationStatus", ApplicationMasterConstant.APPLICATION_STATUS_OPEN);
        query.setParameter("orgId", orgId.getOrgid());
        query.setParameter("stsCodeList", stsCodeList);

        appOpenIdList = query.getResultList();
        if (appOpenIdList != null) {
            if (!appOpenIdList.isEmpty()) {
                for (final ApplicationPortalMaster applicationPortalMaster : appOpenIdList) {
                    Hibernate.initialize(applicationPortalMaster);
                }
            }

            if (appOpenIdList.size() > 0) {
            }
        }
        return appOpenIdList;
    }

    @Override
    public List<ApplicationPortalMaster> getApplicationStatusListOpenForUser(final Organisation organisation) {

        new ArrayList<String>();
        final List<String> payStatusList = new ArrayList<>();
        payStatusList.add("Y");
        payStatusList.add("O");
        payStatusList.add("F");

        final List<String> applicationStatusList = new ArrayList<>();
        applicationStatusList.add(ApplicationMasterConstant.APPLICATION_STATUS_OPEN);
        applicationStatusList.add(ApplicationMasterConstant.APPLICATION_STATUS_PENDING);

        // JOIN FETCH apm.orgId
        final String queryString = "select apm from ApplicationPortalMaster apm JOIN FETCH apm.orgId WHERE "
                + "apm.pamPaymentStatus IN :payStatusList  and "
                + "apm.pamApplicationStatus IN :applicationStatusList  and "
                + "apm.orgId =:orgId ";

        final Query query = createQuery(queryString);
        query.setParameter("applicationStatusList", applicationStatusList);
        query.setParameter("orgId", organisation);
        query.setParameter("payStatusList", payStatusList);

        final List<ApplicationPortalMaster> appOpenIdPortalMasterList = query.getResultList();
        for (final ApplicationPortalMaster applicationPortalMaster : appOpenIdPortalMasterList) {
            Hibernate.initialize(applicationPortalMaster);
        }

        return appOpenIdPortalMasterList;
    }

    @Override
    public void updateApplicationStatus(final ApplicationStatusResponseVO appStsResponseVO) {
        PortalService portalService = null;
        final List<ApplicationDetail> appDetailList = appStsResponseVO.getAppDetailDTO();
        if (appDetailList.size() > 0) {
            final String portalServiceQuery = "select ps from PortalService ps";
            final Query queryPS = createQuery(portalServiceQuery);
            final List<PortalService> portalServiceList = queryPS.getResultList();
            final HashMap<Long, PortalService> portalServiceMap = new HashMap<>();
            for (int i = 0; i < portalServiceList.size(); i++) {
                portalServiceMap.put(portalServiceList.get(i).getServiceId(), portalServiceList.get(i));
            }
            String queryStr = null;
            String selectQuery = null;
            final SimpleDateFormat sdf = new SimpleDateFormat(MainetConstants.COMMON_DATE_FORMAT);
            final Calendar calendar = Calendar.getInstance();

            for (int i = 0; i < appDetailList.size(); i++) {
                final ApplicationDetail appDetail = appDetailList.get(i);
                queryStr = "UPDATE ApplicationPortalMaster apm SET apm.pamApplicationStatus = :status";
                portalService = portalServiceMap.get(appDetail.getServiceId());
                boolean docVerificationFlag = false;
                try {
                    Date docVerificationDate = null;
                    Date slaDate = null;
                    if ("Application Accepted".equals(appDetail.getApmStatus())
                            || "Application Printed through Printer Grid".equals(appDetail.getApmStatus())) {
                        selectQuery = "select apm  from ApplicationPortalMaster apm where apm.pamApplicationId =:pamApplicationId";

                        final Query query = createQuery(selectQuery);
                        query.setParameter("pamApplicationId", appDetail.getApmApplicationId());
                        final ApplicationPortalMaster portaloAppMaster = (ApplicationPortalMaster) query.getSingleResult();

                        if (portaloAppMaster != null) {
                            if ("O".equals(portaloAppMaster.getPamPaymentStatus())) {
                                queryStr = queryStr + ",apm.pamPaymentStatus = 'Y'";
                            }
                            if (portaloAppMaster.getPamDocVerificationDate() == null) {
                                calendar.setTime(new Date());
                                final String docVerificationDateStr = sdf.format(calendar.getTime());
                                docVerificationDate = sdf.parse(docVerificationDateStr);

                                calendar.setTime(sdf.parse(docVerificationDateStr));
                                calendar.add(Calendar.DATE, Integer.parseInt(portalService.getSlaDays().toString()));
                                final String slaDateStr = sdf.format(calendar.getTime());
                                slaDate = sdf.parse(slaDateStr);
                                docVerificationFlag = true;
                                queryStr = queryStr + ",apm.pamDocVerificationDate =:docVerificationDate";
                                queryStr = queryStr + ",apm.pamSlaDate =:slaDate";
                            }
                        }
                    }
                    queryStr = queryStr + ",apm.updatedDate = sysdate where apm.pamApplicationId =:applicationId";
                    final Query query = createQuery(queryStr);
                    query.setParameter("status", appDetail.getApmStatus());
                    query.setParameter("applicationId", appDetail.getApmApplicationId());
                    if (docVerificationFlag) {
                        query.setParameter("docVerificationDate", docVerificationDate);
                        query.setParameter("slaDate", slaDate);
                    }
                    query.executeUpdate();
                } catch (final Exception e) {
                    LOGGER.error("Error at PortalServiceMasterDao.updateApplicationStatus   for  Application Id:-->"
                            + appDetail.getApmApplicationId(), e);
                }
            }
        }

    }

    public ApplicationPortalMaster calculateDate(final PortalService portalService,
            final ApplicationPortalMaster applicationMaster) throws Exception {
        final SimpleDateFormat sdf = new SimpleDateFormat(MainetConstants.COMMON_DATE_FORMAT);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        applicationMaster.setPamChecklistApp(Common_Constant.NO);        // N

        calendar.add(Calendar.DATE, 0);
        final String output1 = sdf.format(calendar.getTime());
        applicationMaster.setPamDocVerificationDate(sdf.parse(output1));

        calendar.setTime(sdf.parse(output1));
        calendar.add(Calendar.DATE, Integer.parseInt(portalService.getSlaDays().toString()));
        final String output2 = sdf.format(calendar.getTime());
        applicationMaster.setPamSlaDate(sdf.parse(output2));

        calendar.setTime(sdf.parse(output2));
        calendar.add(Calendar.DATE, Integer.parseInt(portalService.getFirstAppealDuration().toString()));
        final String output3 = sdf.format(calendar.getTime());
        applicationMaster.setPamFirstAppealDate(sdf.parse(output3));

        calendar.setTime(sdf.parse(output3));
        calendar.add(Calendar.DATE, Integer.parseInt(portalService.getSecondAppealDuration().toString()));
        final String output4 = sdf.format(calendar.getTime());
        applicationMaster.setPamSecondAppealDate(sdf.parse(output4));

        return applicationMaster;

    }

    @Override
    public List<Object[]> findApplicationInfo(final long applicationId, final long orgId) {

        final Query query = createQuery(
                "select apm.pamSlaDate, ps.slaDays, apm.pamApplicationStatus from ApplicationPortalMaster apm, PortalService ps "
                        + " WHERE apm.smServiceId =ps.serviceId AND apm.orgId.orgid=ps.serviceOrgId AND apm.pamApplicationId=:applicationId");

        query.setParameter("applicationId", applicationId);

        return query.getResultList();
    }

    public List<Long> getServiceIdList(final List<String> serviceShortCodeList, final Organisation organisation) {
        final Query query = createQuery("select ps.serviceId from PortalService ps WHERE "
                + "ps.serviceOrgId =:orgId and "
                + "ps.shortName IN :serviceShortCodeList "); // TB_PORTALSERVICE_MASTER

        query.setParameter("orgId", organisation.getOrgid());
        query.setParameter("serviceShortCodeList", serviceShortCodeList);
        final List<Long> serviceIdList = query.getResultList();
        return serviceIdList;
    }

    @Override
    public List<ApplicationPortalMaster> getCertificateList(final List<String> serviceShortCodeList,
            final Organisation organisation) {

        List<Long> serviceIdList = null;
        final List<String> pamApplicationStatusList = new ArrayList<>();

        pamApplicationStatusList.add(MainetConstants.ApplicationMasterConstant.CERTIFICATE_APPLICATION_STATUS_COMPLETE);
        pamApplicationStatusList.add(MainetConstants.ApplicationMasterConstant.CLOSED);

        serviceIdList = getServiceIdList(serviceShortCodeList, organisation);

        final Query query = createQuery("select apm from ApplicationPortalMaster apm JOIN FETCH apm.userId where "
                + "apm.orgId = :organisation and "
                + "apm.pamApplicationStatus IN :pamApplicationStatusList and "
                + "apm.smServiceId IN :smServiceIdList ");
        query.setParameter("organisation", organisation);
        query.setParameter("pamApplicationStatusList", pamApplicationStatusList);
        query.setParameter("smServiceIdList", serviceIdList);

        final List<ApplicationPortalMaster> appPortalMasterList = query.getResultList();

        for (final ApplicationPortalMaster applicationPortalMaster : appPortalMasterList) {
            Hibernate.initialize(applicationPortalMaster);
        }
        return appPortalMasterList;
    }

    @Override
    public void updateDigitalSignFlag(final Long applicationId, final Long serviceId, final Organisation organisation) {

        final String queryString = "UPDATE ApplicationPortalMaster apm SET apm.pamDigitalSign =:pamDigitalSign " +
                " WHERE apm.pamApplicationId =:pamApplicationId and " +
                "apm.smServiceId = :smServiceId ";

        System.out.println("----queryString-----" + queryString);
        final Query query = createQuery(queryString);
        query.setParameter("pamDigitalSign", MainetConstants.MENU.Y);
        query.setParameter("pamApplicationId", applicationId);
        query.setParameter("smServiceId", serviceId);
        query.executeUpdate();
    }

    @Override
    public List<Department> getAllDepartment(final Long orgId) {

        final Query query = createQuery("select am from Department am ");

        return query.getResultList();
    }

    @Override
    public List<Object[]> getAllServicewithUrl(final Long orgId, final long gmid) {
        final Query query = createNativeQuery(
                "select s.psm_dp_deptid,s.psm_service_id,s.psm_short_name, tb.smfname,tb.smfaction ,tb.smfname_mar from  TB_SYSMODFUNCTION tb ,TB_PORTAL_SERVICE_MASTER s where  tb.smfid IN ( select  RR.SMFID  from  ROLE_ENTITLEMENT RR where RR.Role_Id =:gmid  and RR.ORG_ID=:orgid and s.psm_smfid = tb.smfid ) ");
        query.setParameter("orgid", orgId);
        query.setParameter("gmid", gmid);

        return query.getResultList();
    }

    @Override
    public ApplicationPortalMaster getServiceApplicationStatus(final Long appId) {
        ApplicationPortalMaster appMaster = null;
        final Query query = createQuery("select t from ApplicationPortalMaster t where t.pamApplicationId=?1 and t.orgId=?2");
        query.setParameter(1, appId);
        query.setParameter(2, UserSession.getCurrent().getOrganisation());

        final List<ApplicationPortalMaster> applicationList = query.getResultList();
        if ((applicationList != null) && !applicationList.isEmpty()) {
            appMaster = applicationList.get(0);
            Hibernate.initialize(appMaster.getUserId());
        }
        return appMaster;
    }

    @Override
    public Date getLastUpdated() {
        final Query query = createNativeQuery(
                "select MAX(a.UPDATED_DATE) as UPDATED_DATE from (" +
                        "            select MAX(UPDATED_DATE) as UPDATED_DATE from tb_eip_links_master" +
                        "            union" +
                        "            select MAX(UPDATED_DATE) from tb_eip_profile_master" +
                        "            union" +
                        "            select MAX(UPDATED_DATE) from tb_eip_public_notices" +
                        "            union" +
                        "            select MAX(UPDATED_DATE) from tb_eip_sub_link_fields_dtl" +
                        "            union" +
                        "            select MAX(UPDATED_DATE) from tb_eip_announcement" +
                        "            union" +
                        "            select MAX(UPDATED_DATE) from tb_eip_home_images" +
                        "            union" +
                        "            select MAX(UPDATED_DATE) from tb_eip_aboutus) a");

        return (Date) query.getSingleResult();
    }

    @Override
    public List<PortalDepartmentDTO> getAllDeptAndService(Long orgid, Long groupid, int langId) {

        List<PortalDepartmentDTO> dtoList = new ArrayList<>();
        Map<Long, PortalDepartmentDTO> map = new LinkedHashMap<Long, PortalDepartmentDTO>(0);
        PortalQuickServiceDTO childDTO = null;
        final Query query = createNativeQuery(
                "select s.psm_dp_deptid,s.PSM_DP_DEPTCODE,s.PSM_DP_DEPTDESC,s.PSM_DP_NAME_MAR,s.psm_service_id,s.psm_short_name,s.psm_service_name,s.psm_service_name_reg,tb.smfaction  from  TB_SYSMODFUNCTION tb ,TB_PORTAL_SERVICE_MASTER s where"
                        + " tb.SM_SHORTDESC=s.psm_short_name and s.ORGID=:orgid and" +
                        "  tb.smfid IN ( select  RR.SMFID  from  ROLE_ENTITLEMENT RR where RR.Role_Id =:gmid  and RR.ORG_ID=:orgid and RR.IS_ACTIVE=:active) and s.IS_DELETED=:deleted order by tb.smfsrno");
        query.setParameter("orgid", orgid);
        query.setParameter("gmid", groupid);
        query.setParameter("active", 0);
        query.setParameter("deleted", 0);

        List<Object[]> deptCountList = query.getResultList();

        PortalDepartmentDTO dto = null;
        for (Object[] deptCount : deptCountList) {

            BigInteger deptId = (BigInteger) deptCount[0];
            dto = map.get(deptId.longValue());
            if (null == dto) {
                dto = new PortalDepartmentDTO();
                dto.setDepartmentCode((String) deptCount[1]);
                if (langId == 1) {
                    dto.setDepartmentName((String) deptCount[2]);
                } else {
                    dto.setDepartmentName((String) deptCount[3]);
                }
                map.put(deptId.longValue(), dto);
            }

            childDTO = new PortalQuickServiceDTO();
            BigInteger serviceId = (BigInteger) deptCount[4];
            childDTO.setServiceId(serviceId.longValue());
            childDTO.setServiceCode((String) deptCount[5]);
            if (langId == 1) {
                childDTO.setServiceName((String) deptCount[6]);
            } else {
                childDTO.setServiceName((String) deptCount[7]);
            }
            childDTO.setServiceurl((String) deptCount[8]);

            dto.addChildDTO(childDTO);
        }
        dtoList.addAll(map.values());

        return dtoList;

    }

	@Override
	public List<String> getSmfaction(Long orgid, Long groupid, int languageId) {
		 final Query query = createNativeQuery(
	                "select tb.smfaction from  TB_SYSMODFUNCTION tb ,ROLE_ENTITLEMENT RR where"
	                        + "  tb.smfid=RR.SMFID and RR.Role_Id =:gmid  and RR.ORG_ID=:orgid and tb.smfflag=:flag and RR.IS_ACTIVE=0 and tb.ISDELETED=0");
	        query.setParameter("orgid", orgid);
	        query.setParameter("gmid", groupid);
	        query.setParameter("flag", "M");

	        List<String> smfaction = query.getResultList();
			return smfaction;
	}

}
