package com.abm.mainet.cfc.loi.ui.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.loi.dto.LoiPaymentSearchDTO;
import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.service.TbLoiDetService;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.cfc.loi.ui.model.LoiDeletionModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.master.dto.TbServicesMst;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.service.ICFCApplicationAddressService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author Rahul.Yadav
 *
 */
@Controller
@RequestMapping(value = "/LoiDeletion.html")
public class LoiDeletionController extends AbstractFormController<LoiDeletionModel> {

    @Resource
    private TbLoiMasService tbLoiMasService;
    @Autowired
    private ICFCApplicationMasterService iCFCApplicationMasterService;

    @Autowired
    private TbServicesMstService iTbServicesMstService;

    @Autowired
    private ICFCApplicationAddressService iCFCApplicationAddressService;

    @Autowired
    private TbLoiDetService itbLoidetService;

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        this.getModel().setCommonHelpDocs("LoiDeletion.html");
        return defaultResult();
    }

    @RequestMapping(method = RequestMethod.POST, params = "searchLOIRecordsDelete")
    public ModelAndView searchLOIRecordsDelete(final HttpServletRequest httpServletRequest,
            @RequestParam("applicationId") final Long applicationId) {
        sessionCleanup(httpServletRequest);
        bindModel(httpServletRequest);
        LoiPaymentSearchDTO dto = null;
        final LoiDeletionModel model = getModel();
        if (applicationId == null) {
            model.addValidationError(ApplicationSession.getInstance().getMessage("loidet.msg.loiCharges"));
            return defaultMyResult();
        }
        String addressData = null;
        String userName = null;
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final TbLoiMas loidata = tbLoiMasService.getloiByApplicationIdForDeletion(applicationId, orgId);
        if (loidata != null) {
            dto = itbLoidetService.findLoiDetailsByLoiMasAndOrgId(loidata, orgId);
            final TbCfcApplicationMstEntity applicationMaster = iCFCApplicationMasterService
                    .getCFCApplicationByApplicationId(applicationId, orgId);
            userName = (applicationMaster.getApmFname() == null ? MainetConstants.BLANK
                    : applicationMaster.getApmFname() + MainetConstants.WHITE_SPACE);
            userName += applicationMaster.getApmMname() == null ? MainetConstants.BLANK
                    : applicationMaster.getApmMname() + MainetConstants.WHITE_SPACE;
            userName += applicationMaster.getApmLname() == null ? MainetConstants.BLANK : applicationMaster.getApmLname();
            dto.setApplicantName(userName);
            dto.setServiceId(loidata.getLoiServiceId());
            final TbServicesMst serviceMst = iTbServicesMstService.findById(loidata.getLoiServiceId());
            if (serviceMst != null) {
                dto.setServiceName(serviceMst.getSmServiceName());
            }
            final CFCApplicationAddressEntity address = iCFCApplicationAddressService.getApplicationAddressByAppId(applicationId,
                    orgId);
            if (address != null) {
                addressData = address.getApaFlatBuildingNo() == null ? MainetConstants.BLANK
                        : address.getApaFlatBuildingNo() + MainetConstants.WHITE_SPACE;
                addressData += address.getApaBldgnm() == null ? MainetConstants.BLANK
                        : address.getApaBldgnm() + MainetConstants.WHITE_SPACE;
                addressData += address.getApaBlockName() == null ? MainetConstants.BLANK
                        : address.getApaBlockName() + MainetConstants.WHITE_SPACE;
                addressData += address.getApaAreanm() == null ? MainetConstants.BLANK
                        : address.getApaAreanm() + MainetConstants.WHITE_SPACE;
                addressData += address.getApaRoadnm() == null ? MainetConstants.BLANK
                        : address.getApaRoadnm() + MainetConstants.WHITE_SPACE;
                addressData += address.getApaPincode() == null ? MainetConstants.BLANK
                        : address.getApaPincode() + MainetConstants.WHITE_SPACE;
                model.setAddress(addressData);
            }
            dto.setLoiMasData(loidata);
            dto.setApplicationId(applicationId);
        } else {
            model.addValidationError(ApplicationSession.getInstance().getMessage("no.record.found"));
        }
        model.setSearchData(dto);
        return defaultMyResult();
    }

}
