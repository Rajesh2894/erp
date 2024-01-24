package com.abm.mainet.landEstate.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.loi.dto.LoiPaymentSearchDTO;
import com.abm.mainet.cfc.loi.dto.TbLoiDet;
import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.service.TbLoiDetService;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.dto.TbServicesMst;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.service.ICFCApplicationAddressService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.landEstate.dto.LandAcquisitionDto;
import com.abm.mainet.landEstate.service.ILandAcquisitionService;

@Component
@Scope("session")
public class LOIPayableModel extends AbstractFormModel {

    private static final long serialVersionUID = -7459098330229789128L;

    private TbLoiMas loiMaster = new TbLoiMas();

    private LoiPaymentSearchDTO searchDto = new LoiPaymentSearchDTO();

    private List<TbLoiDet> loiDetail = null;

    private String pageUrl;

    private Long serviceId;

    private String wokflowDecision;

    private LandAcquisitionDto acquisitionDto = null;

    private List<AttachDocs> attachDocsList = new ArrayList<>();

    private List<TbLocationMas> locationList = null;

    public TbLoiMas getLoiMaster() {
        return loiMaster;
    }

    public void setLoiMaster(TbLoiMas loiMaster) {
        this.loiMaster = loiMaster;
    }

    public LoiPaymentSearchDTO getSearchDto() {
        return searchDto;
    }

    public void setSearchDto(LoiPaymentSearchDTO searchDto) {
        this.searchDto = searchDto;
    }

    public List<TbLoiDet> getLoiDetail() {
        return loiDetail;
    }

    public void setLoiDetail(List<TbLoiDet> loiDetail) {
        this.loiDetail = loiDetail;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getWokflowDecision() {
        return wokflowDecision;
    }

    public void setWokflowDecision(String wokflowDecision) {
        this.wokflowDecision = wokflowDecision;
    }

    public LandAcquisitionDto getAcquisitionDto() {
        return acquisitionDto;
    }

    public void setAcquisitionDto(LandAcquisitionDto acquisitionDto) {
        this.acquisitionDto = acquisitionDto;
    }

    public List<AttachDocs> getAttachDocsList() {
        return attachDocsList;
    }

    public void setAttachDocsList(List<AttachDocs> attachDocsList) {
        this.attachDocsList = attachDocsList;
    }

    public List<TbLocationMas> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<TbLocationMas> locationList) {
        this.locationList = locationList;
    }

    @Autowired
    private TbLoiMasService itbLoiMasService;

    @Autowired
    private TbLoiDetService itbLoidetService;

    @Autowired
    private ICFCApplicationMasterService iCFCApplicationMasterService;

    @Autowired
    private TbServicesMstService iTbServicesMstService;

    @Autowired
    private ICFCApplicationAddressService iCFCApplicationAddressService;

    @Autowired
    private ILandAcquisitionService acquisitionService;

    public boolean getLoiData() {
        LoiPaymentSearchDTO dto = null;
        final LoiPaymentSearchDTO searchdata = getSearchDto();
        final ApplicationSession appSession = ApplicationSession.getInstance();

        if ((searchdata.getApplicationId() == null) && ((searchdata.getLoiNo() == null) || searchdata.getLoiNo().isEmpty())
                && (searchdata.getLoiDate() == null)) {
            setLoiMaster(new TbLoiMas());
            addValidationError(appSession.getMessage("loiPay.msg.appId"));
            return false;
        } else if ((searchdata.getLoiNo() != null) && !searchdata.getLoiNo().isEmpty() && (searchdata.getLoiDate() == null)) {
            setLoiMaster(new TbLoiMas());
            addValidationError(appSession.getMessage("loiPay.msg.appId"));
            return false;
        } else if (((searchdata.getLoiNo() == null) || searchdata.getLoiNo().isEmpty()) && (searchdata.getLoiDate() != null)) {
            setLoiMaster(new TbLoiMas());
            addValidationError(appSession.getMessage("loiPay.msg.appId"));
            return false;
        }
        getSearchDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());

        final TbLoiMas master = itbLoiMasService.findLoiMasBySearchCriteria(searchdata, MainetConstants.PAY_STATUS.NOT_PAID);

        if (master != null) {
            dto = itbLoidetService.findLoiDetailsByLoiMasAndOrgId(master, searchdata.getOrgId());
            final TbCfcApplicationMstEntity applicationMaster = iCFCApplicationMasterService
                    .getCFCApplicationByApplicationId(master.getLoiApplicationId(), searchdata.getOrgId());
            String userName = (applicationMaster.getApmFname() == null ? MainetConstants.BLANK
                    : applicationMaster.getApmFname() + MainetConstants.WHITE_SPACE);
            userName += applicationMaster.getApmMname() == null ? MainetConstants.BLANK
                    : applicationMaster.getApmMname() + MainetConstants.WHITE_SPACE;
            userName += applicationMaster.getApmLname() == null ? MainetConstants.BLANK : applicationMaster.getApmLname();
            getSearchDto().setApplicantName(userName);
            getSearchDto().setApplicationDate(Utility.dateToString(applicationMaster.getApmApplicationDate()));

            final TbServicesMst serviceMst = iTbServicesMstService.findById(master.getLoiServiceId());
            if (serviceMst != null) {
                getSearchDto().setServiceId(serviceMst.getSmServiceId());
                getSearchDto().setServiceName(serviceMst.getSmServiceName());
            }
            final CFCApplicationAddressEntity address = iCFCApplicationAddressService
                    .getApplicationAddressByAppId(master.getLoiApplicationId(), searchdata.getOrgId());
            if (address != null) {
                getSearchDto().setEmail(address.getApaEmail());
                getSearchDto().setMobileNo(address.getApaMobilno());
                getSearchDto().setAddress(address.getApaAreanm());
            }
        }
        getSearchDto().setLoiMasData(master);
        if (dto != null) {
            getSearchDto().setChargeDesc(dto.getChargeDesc());
            getSearchDto().setTotal(dto.getTotal());
            getSearchDto().setLoiCharges(dto.getLoiCharges());
            getOfflineDTO().setAmountToShow(dto.getTotal());
        }

        if ((searchdata != null) && (searchdata.getLoiMasData() != null)) {
            setLoiMaster(searchdata.getLoiMasData());
            getLoiMaster().setLoiRecordFound(MainetConstants.Common_Constant.YES);
            return true;
        }
        setLoiMaster(new TbLoiMas());
        getLoiMaster().setLoiRecordFound(MainetConstants.Common_Constant.NO);
        return false;
    }

    @Override
    public boolean saveForm() {

        acquisitionService.updateLoiPayableData(getAcquisitionDto(), getWokflowDecision(), getTaskId(),
                UserSession.getCurrent().getEmployee(), UserSession.getCurrent().getOrganisation(),
                UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation().getOrgid());

        if (getWokflowDecision().equalsIgnoreCase(MainetConstants.WorkFlow.Decision.REJECTED)) {
            setSuccessMessage(getAppSession().getMessage("land.acq.workflow.rejected"));
        } else {
            setSuccessMessage(getAppSession().getMessage("land.acq.workflow.approved"));
        }

        return true;
    }

}
