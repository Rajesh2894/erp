package com.abm.mainet.water.ui.model;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.scrutiny.dto.ScrutinyLableValueDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.master.dto.TbCfcApplicationMst;
import com.abm.mainet.common.master.mapper.TbCfcApplicationMstServiceMapper;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.service.NewWaterConnectionService;
import com.abm.mainet.water.service.WaterCommonService;
import com.abm.mainet.water.service.WaterServiceMapper;

/**
 * @author deepika.pimpale
 *
 */
@Component
@Scope("session")
public class ApprovalOfConnectionNoModel extends AbstractFormModel {

    private static final long serialVersionUID = 5607065132258166430L;

    private TbCsmrInfoDTO csmrInfo = new TbCsmrInfoDTO();

    private TbCfcApplicationMstEntity cfcEntity = new TbCfcApplicationMstEntity();

    private CFCApplicationAddressEntity cfcAddressEntity = new CFCApplicationAddressEntity();

    private String applicantFullName;

    private String serviceName;

    @Autowired
    private NewWaterConnectionService waterService;

    @Autowired
    private ICFCApplicationMasterService cfcService;

    @Autowired
    private WaterServiceMapper waterMapper;

    @Autowired
    private CommonService commonservice;

    @Autowired
    private ApplicationService applicationService;

    private TbCfcApplicationMst cfcMasterDTO = new TbCfcApplicationMst();

    @Resource
    private TbCfcApplicationMstServiceMapper cfcServiceMapper;

    @Autowired
    private WaterCommonService waterCommonService;

    @Resource
    private ServiceMasterService serviceMasterService;

    private ScrutinyLableValueDTO lableValueDTO = new ScrutinyLableValueDTO();

    private Long serviceId;

    private String hasError = MainetConstants.BLANK;

    private String isScrutinyRequest = MainetConstants.N_FLAG;

    @Override
    protected final String findPropertyPathPrefix(final String parentCode) {
        switch (parentCode) {
        case PrefixConstants.NewWaterServiceConstants.WWZ:
            return "csmrInfo.codDwzid";
        default:
            return null;
        }

    }

    public void setConnectionDetailsInfo(final Long appId, final long serviceId, final long orgId) {

        cfcEntity = cfcService.getCFCApplicationByApplicationId(appId, orgId);
        cfcMasterDTO = cfcServiceMapper
                .mapTbCfcApplicationMstEntityToTbCfcApplicationMst(cfcEntity);
        setCfcAddressEntity(cfcService.getApplicantsDetails(appId));
        final ServiceMaster serviceMaster = serviceMasterService.getServiceMaster(
                serviceId, orgId);
        setServiceName(serviceMaster.getSmServiceName());
        setCsmrInfo(waterCommonService.getApplicantInformationById(appId, orgId));
        final TbCsmrInfoDTO csmrInfo = getCsmrInfo();
        if (csmrInfo.getCsMname() != null) {
            setApplicantFullName(csmrInfo.getCsName() + " "
                    + csmrInfo.getCsMname() + MainetConstants.WHITE_SPACE + csmrInfo.getCsLname());
        } else {
            setApplicantFullName(csmrInfo.getCsName() + " "
                    + csmrInfo.getCsLname());
        }
    }

    @Override
    public boolean saveForm() {
        final TbCsmrInfoDTO csmrInfo = getCsmrInfo();
        cfcServiceMapper.mapTbCfcApplicationMstToTbCfcApplicationMstEntity(
                getCfcMasterDTO(), cfcEntity);
        getCfcEntity().setAppAppRejBy(
                UserSession.getCurrent().getEmployee().getEmpId());
        getCfcEntity().setAppAppRejDate(new Date());
        final TbKCsmrInfoMH master = waterMapper
                .mapTbKCsmrInfoDTOToTbKCsmrInfoEntity(csmrInfo);
        setMasterUpdateFields(master);
        if (master.getCsMeteredccn() == null) {
            final Long nonMeterId = CommonMasterUtility.getValueFromPrefixLookUp(
                    PrefixConstants.NewWaterServiceConstants.NO,
                    PrefixConstants.NewWaterServiceConstants.WMN,
                    UserSession.getCurrent().getOrganisation()).getLookUpId();
            master.setCsMeteredccn(nonMeterId);
        }
        setCFCCmasterUpdateFields(cfcEntity);
        if ((cfcEntity.getApmAppRejFlag() != null)
                && cfcEntity.getApmAppRejFlag().equals(MainetConstants.MENU.A)) {
            final String connectionNo = waterService.generateWaterConnectionNumber(
                    master.getApplicationNo(), getServiceId(), UserSession
                            .getCurrent().getOrganisation().getOrgid(),master);
            master.setCsCcn(connectionNo);
        }
        applicationService.updateApplication(getCfcEntity());
        waterCommonService.updateCsmrInfo(master, getLableValueDTO());
        if (MainetConstants.MENU.Y.equals(getIsScrutinyRequest())) {
            /*
             * final TaskDefDto taskDefDto = new TaskDefDto(); taskDefDto.setApplicationId(master.getApplicationNo());
             * taskDefDto.setOrgId(master.getOrgId());
             * taskDefDto.setEmployeeId(UserSession.getCurrent().getEmployee().getEmpId());
             * taskDefDto.setServiceId(getServiceId()); if (!MainetConstants.MENU.A.equals(cfcEntity.getApmAppRejFlag())) {
             * taskDefDto.setApplicationRejected(true); } taskManagerService.updateUserTask(taskDefDto);
             */
        }
        return true;
    }

    /**
     * @param cfcEntity2
     */
    private void setCFCCmasterUpdateFields(final TbCfcApplicationMstEntity master) {
        master.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        master.setUpdatedDate(new Date());
        master.setLmoddate(new Date());
        master.setLgIpMacUpd(UserSession.getCurrent().getEmployee()
                .getLgIpMac());

    }

    private void setMasterUpdateFields(final TbKCsmrInfoMH master) {
        master.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        master.setUpdatedDate(new Date());
        master.setLmodDate(new Date());
        master.setLgIpMacUpd(UserSession.getCurrent().getEmployee()
                .getLgIpMac());

    }

    public CFCApplicationAddressEntity getCfcAddressEntity() {
        return cfcAddressEntity;
    }

    public void setCfcAddressEntity(final CFCApplicationAddressEntity cfcAddressEntity) {
        this.cfcAddressEntity = cfcAddressEntity;
    }

    public TbCsmrInfoDTO getCsmrInfo() {
        return csmrInfo;
    }

    public void setCsmrInfo(final TbCsmrInfoDTO csmrInfo) {
        this.csmrInfo = csmrInfo;
    }

    public TbCfcApplicationMstEntity getCfcEntity() {
        return cfcEntity;
    }

    public void setCfcEntity(final TbCfcApplicationMstEntity cfcEntity) {
        this.cfcEntity = cfcEntity;
    }

    public String getApplicantFullName() {
        return applicantFullName;
    }

    public void setApplicantFullName(final String applicantFullName) {
        this.applicantFullName = applicantFullName;
    }

    @Override
    public String getServiceName() {
        return serviceName;
    }

    @Override
    public void setServiceName(final String serviceName) {
        this.serviceName = serviceName;
    }

    public ICFCApplicationMasterService getCfcService() {
        return cfcService;
    }

    public void setCfcService(final ICFCApplicationMasterService cfcService) {
        this.cfcService = cfcService;
    }

    public ScrutinyLableValueDTO getLableValueDTO() {
        return lableValueDTO;
    }

    public void setLableValueDTO(final ScrutinyLableValueDTO lableValueDTO) {
        this.lableValueDTO = lableValueDTO;
    }

    @Override
    public Long getServiceId() {
        return serviceId;
    }

    @Override
    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    public TbCfcApplicationMst getCfcMasterDTO() {
        return cfcMasterDTO;
    }

    public void setCfcMasterDTO(final TbCfcApplicationMst cfcMasterDTO) {
        this.cfcMasterDTO = cfcMasterDTO;
    }

    /**
     * @return
     */
    public boolean validateInputs() {
        final TbCfcApplicationMst master = getCfcMasterDTO();
        if (master.getApmAppRejFlag() == null) {
            addValidationError(getAppSession().getMessage("sel.status"));
        }
        if ((master.getApmRemark() != null) && master.getApmRemark().equals(MainetConstants.BLANK)) {
            addValidationError(getAppSession().getMessage("sel.remark"));
        }
        if (hasValidationErrors()) {
            setHasError(MainetConstants.MENU.Y);
            return false;
        } else {
            setHasError(MainetConstants.Common_Constant.NO);
        }
        return true;
    }

    public String getHasError() {
        return hasError;
    }

    public void setHasError(final String hasError) {
        this.hasError = hasError;
    }

    public String getIsScrutinyRequest() {
        return isScrutinyRequest;
    }

    public void setIsScrutinyRequest(final String isScrutinyRequest) {
        this.isScrutinyRequest = isScrutinyRequest;
    }

}
