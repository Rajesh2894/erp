package com.abm.mainet.common.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.ApplicationMasterConstant;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dao.IPortalPrefixMappingDAO;
import com.abm.mainet.common.dao.IPortalServiceMasterDao;
import com.abm.mainet.common.domain.ApplicationPortalMaster;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.PortalPrifixMappingMaster;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.ApplicationDetail;
import com.abm.mainet.common.dto.ApplicationStatusRequestVO;
import com.abm.mainet.common.dto.ApplicationStatusResponseVO;
import com.abm.mainet.common.dto.PortalDepartmentDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.integration.ws.JersyCall;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PortalServiceMasterService implements IPortalServiceMasterService {

    @Autowired
    private IPortalServiceMasterDao iServiceMasterDao;

    @Autowired
    private IPortalPrefixMappingDAO iPrefixMappingDAO;

    protected final Logger LOGGER = Logger.getLogger(this.getClass());

    @Override
    @Transactional(readOnly = true)
    public Long getServiceId(final String shortCode, final Long orgId) {
        return iServiceMasterDao.getSeviceId(shortCode, orgId);
    }

    @Override
    @Transactional
    public void saveApplicationMaster(final ApplicationPortalMaster applicationMaster, final Double paymentAmount,
            final int docListSize) {
        try {
            // free service
            if ((paymentAmount != null) && paymentAmount.equals(0D)) {
                applicationMaster.setPamPaymentStatus(ApplicationMasterConstant.PAYMENT_STATUS_FREE_SERVICE);
                if (docListSize == 0) {
                    // no check list - pending
                    final SimpleDateFormat sdf = new SimpleDateFormat(MainetConstants.COMMON_DATE_FORMAT);
                    final Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date());
                    final String docVerificationDate = sdf.format(calendar.getTime());
                    applicationMaster.setPamDocVerificationDate(sdf.parse(docVerificationDate));
                    applicationMaster.setPamChecklistApp(MainetConstants.Common_Constant.NO);
                    applicationMaster.setPamApplicationStatus(ApplicationMasterConstant.APPLICATION_STATUS_PENDING);
                    final PortalService portalServiceMaster = getService(applicationMaster.getSmServiceId(),
                            UserSession.getCurrent().getOrganisation().getOrgid());
                    if (portalServiceMaster.getSlaDays() != null)
                        calendar.add(Calendar.DATE, Integer.parseInt(portalServiceMaster.getSlaDays().toString()));
                    final String slaDate = sdf.format(calendar.getTime());
                    applicationMaster.setPamSlaDate(sdf.parse(slaDate));
                } else {
                    // check list available - open - before payment
                    applicationMaster.setPamChecklistApp(MainetConstants.Common_Constant.YES);
                    applicationMaster.setPamApplicationStatus(ApplicationMasterConstant.APPLICATION_STATUS_OPEN);
                }
            } else {
                // paid service - update payment status before payment - empty
                applicationMaster.setPamApplicationStatus(ApplicationMasterConstant.APPLICATION_STATUS_OPEN);
                applicationMaster.setPamPaymentStatus(ApplicationMasterConstant.PAYMENT_STATUS_BEFORE_PAYMENT_EMPTY);
                if (docListSize == 0) {
                    // no check list - open - before payment
                    applicationMaster.setPamChecklistApp(MainetConstants.Common_Constant.NO);
                } else {
                    // check list available - open - before payment
                    applicationMaster.setPamChecklistApp(MainetConstants.Common_Constant.YES);
                }
            }
            iServiceMasterDao.saveApplicationMaster(applicationMaster);
        } catch (final NumberFormatException e) {
            throw new FrameworkException(e);
        } catch (final ParseException e) {
            throw new FrameworkException(MainetConstants.EXCEPTION_SAVEAPPLICATION_MASTER, e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public String findServiceNameForServiceId(final Long serviceId) {
        return iServiceMasterDao.findServiceNameForServiceId(serviceId);
    }

    @Override
    @Transactional(readOnly = true)
    public PortalPrifixMappingMaster getMappedPrefix(final String prefixType, final Long portalPrefix) {
        return iPrefixMappingDAO.getMappedPrefix(prefixType, portalPrefix);
    }

    @Override
    @Transactional(readOnly = true)
    public PortalService getPortalService(final Long serviceId) {
        return iServiceMasterDao.getPortalService(serviceId);
    }

    @Override
    @Transactional(readOnly = true)
    public String getServiceURL(final Long psmSmfid) {
        return iServiceMasterDao.getServiceURL(psmSmfid);
    }

    @Override
    @Transactional
    public boolean saveCFCCitizenId(final String cfcCitizenId, final Long userId) {
        iServiceMasterDao.saveCfcCitizenId(cfcCitizenId, userId);
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public String getServiceShortName(final Long psmDpDeptid) {
        return iServiceMasterDao.getServiceShortName(psmDpDeptid);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getServiceDeptIdId(final Long serviceId) {
        return iServiceMasterDao.getServiceDeptIdId(serviceId);
    }

    @Override
    @Transactional(readOnly = true)
    public String checkListAvailable(final long applicationId) {
        return iServiceMasterDao.checkListAvailable(applicationId);
    }

    @Override
    @Transactional
    public void getApplicationStatusOpen(final Organisation organization) {

        final List<ApplicationPortalMaster> appStatusList = iServiceMasterDao.getApplicationStatusListOpenForUser(organization);

        final ApplicationStatusRequestVO vo = setRequestVOObject(appStatusList, organization.getOrgid());

        @SuppressWarnings("unchecked")
        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(vo,
                ServiceEndpoints.CommonPrefixUri.GET_APPLICATION_STATUS_LISTOPEN_FOR_USER);
        final String d = new JSONObject(responseVo).toString();

        ApplicationStatusResponseVO outPutObject = null;

        try {
            outPutObject = new ObjectMapper().readValue(d, ApplicationStatusResponseVO.class);

            if (outPutObject != null) {
                iServiceMasterDao.updateApplicationStatus(outPutObject);
            }
        } catch (final IOException e) {
            throw new FrameworkException(e);
        }

    }

    public ApplicationStatusRequestVO setRequestVOObject(final List<ApplicationPortalMaster> appStatusList, final Long orgId) {
        final ApplicationStatusRequestVO appStsRequestDTO = new ApplicationStatusRequestVO();
        appStsRequestDTO.setOrgId(orgId);

        ApplicationPortalMaster appPortalMaster = null;
        ApplicationDetail appDetail = null;
        for (int i = 0; i < appStatusList.size(); i++) {
            appPortalMaster = appStatusList.get(i);
            if ((appPortalMaster.getOrgId() != null) && (appPortalMaster.getOrgId().getOrgid() == orgId)) {
                if ((appPortalMaster.getPamApplicationId() != null) && (appPortalMaster.getPamApplicationId() != 0l)) {
                    if ((appPortalMaster.getSmServiceId() != null) && (appPortalMaster.getPamChecklistApp() != null)
                            && (appPortalMaster.getPamPaymentStatus() != null)
                            && (appPortalMaster.getPamApplicationStatus() != null)) {
                        appDetail = new ApplicationDetail();
                        appDetail.setApmApplicationId(appPortalMaster.getPamApplicationId());
                        appDetail.setServiceId(appPortalMaster.getSmServiceId());
                        appDetail.setOrgId(appPortalMaster.getOrgId().getOrgid());
                        appDetail.setApmStatus(appPortalMaster.getPamChecklistApp());
                        appDetail.setApmStatus(appPortalMaster.getPamPaymentStatus());
                        appDetail.setApmStatus(appPortalMaster.getPamApplicationStatus());
                        appStsRequestDTO.getAppDetailList().add(appDetail);
                    }
                }
            }
        }
        return appStsRequestDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> findApplicationInfo(final long applicationId, final long orgId) {

        return iServiceMasterDao.findApplicationInfo(applicationId, orgId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationPortalMaster> getCertificateList(final List<String> serviceShortCodeList,
            final Organisation organisation) {

        return iServiceMasterDao.getCertificateList(serviceShortCodeList, organisation);
    }

    @Override
    @Transactional
    public void updateDigitalSignFlag(final Long applicationId, final Long serviceId, final Organisation organisation) {

        iServiceMasterDao.updateDigitalSignFlag(applicationId, serviceId, organisation);

    }

    @Override
    @Transactional(readOnly = true)
    public PortalService getService(final Long serviceId, final Long orgId) {
        return iServiceMasterDao.getService(serviceId, orgId);
    }

    @Override
    @Transactional(readOnly = true)
    public Date getLastUpdated() {
        return iServiceMasterDao.getLastUpdated();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PortalDepartmentDTO> getAllDeptAndService(Long orgid, Long groupid, int langId) {

        List<PortalDepartmentDTO> PortalQuickService = new ArrayList<>();

        PortalQuickService = iServiceMasterDao.getAllDeptAndService(orgid, groupid, langId);

        return PortalQuickService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getSmfaction(Long orgid, Long groupid, int languageId) {
        return iServiceMasterDao.getSmfaction(orgid, groupid, languageId);
    }

}
