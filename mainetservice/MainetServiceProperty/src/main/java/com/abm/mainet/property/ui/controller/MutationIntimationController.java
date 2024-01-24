package com.abm.mainet.property.ui.controller;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.property.dto.MutationIntimationDto;
import com.abm.mainet.property.dto.PropertyTransferMasterDto;
import com.abm.mainet.property.service.MutationIntimationService;
import com.abm.mainet.property.service.MutationService;
import com.abm.mainet.property.ui.model.MutationModel;

@Controller
@RequestMapping("/MutationIntimation.html")
public class MutationIntimationController extends AbstractFormController<MutationModel> {

    @Autowired
    private MutationIntimationService mutationIntimationService;

    @Autowired
    private ServiceMasterService serviceMaster;

    @Autowired
    private MutationService mutationService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest request) {
        this.sessionCleanup(request);

        return defaultResult();
    }

    @RequestMapping(params = "SEARCH_GRID_RESULTS", produces = "application/json", method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<? extends Serializable> getSearchResults(
            HttpServletRequest httpServletRequest) {
        List<MutationIntimationDto> result = null;
        int count = 0;
        MutationModel model = this.getModel();
        MutationIntimationDto dto = model.getMutationIntimationDto();

        if ((dto.getPropertyno() == null || dto.getPropertyno().isEmpty())
                && (dto.getExcuClaimMobileNo() == null || dto.getExcuClaimMobileNo().isEmpty())
                && (dto.getExcuClaimName() == null || dto.getExcuClaimName().isEmpty())
                && (dto.getRegistrationNo() == null || dto.getRegistrationNo().isEmpty())
                && (dto.getMutationOrderNo() == null || dto.getMutationOrderNo().isEmpty())) {
            model.addValidationError("In order to search it is mandatory to enter any one of the below search detail");
        }
        final String page = httpServletRequest.getParameter(MainetConstants.CommonConstants.PAGE);
        final String rows = httpServletRequest.getParameter(MainetConstants.CommonConstants.ROWS);
        if (!model.hasValidationErrors()) {
            result = mutationIntimationService.getMutationIntimationGridData(dto, getModel().createPagingDTO(httpServletRequest),
                    getModel().createGridSearchDTO(httpServletRequest));

            if (result != null && !result.isEmpty()) {
                count = mutationIntimationService.getcountOfSearchMutationIntimation(dto,
                        getModel().createPagingDTO(httpServletRequest), getModel().createGridSearchDTO(httpServletRequest));
            }

        }
        return this.getModel().paginate(httpServletRequest, page, rows, count,
                result);
    }

    @RequestMapping(params = "searchData", method = RequestMethod.POST)
    public ModelAndView search(HttpServletRequest request) {
        MutationModel model = this.getModel();
        model.bind(request);
        // model.setSearchDtoResult(new ArrayList<>(0));
        MutationIntimationDto dto = model.getMutationIntimationDto();
        model.getMutationIntimationDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        if ((dto.getPropertyno() == null || dto.getPropertyno().isEmpty())
                && (dto.getExcuClaimMobileNo() == null || dto.getExcuClaimMobileNo().isEmpty())
                && (dto.getExcuClaimName() == null || dto.getExcuClaimName().isEmpty())
                && (dto.getRegistrationNo() == null || dto.getRegistrationNo().isEmpty())
                && (dto.getMutationOrderNo() == null || dto.getMutationOrderNo().isEmpty())) {
            model.addValidationError("In order to search it is mandatory to enter any one of the below search detail");
        }
        return defaultMyResult();
    }

    @RequestMapping(params = "edit", method = RequestMethod.POST)
    public ModelAndView editForm(@RequestParam final long rowId,
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        MutationModel model = this.getModel();
        model.setMutIntimationFlag(MainetConstants.FlagY);
        ServiceMaster service = serviceMaster.getServiceByShortName(MainetConstants.Property.MUT,
                UserSession.getCurrent().getOrganisation().getOrgid());
        MutationIntimationDto dto = mutationIntimationService.getMutationIntimationView(rowId,
                UserSession.getCurrent().getOrganisation().getOrgid());
        
        List<TbBillMas> billMasList = mutationIntimationService.getPropertyDuesByPropNo(dto.getPropertyno(),
         UserSession.getCurrent().getOrganisation().getOrgid());         
        if (billMasList.isEmpty()) {        
        model.setMutationIntimationViewDto(dto);
        PropertyTransferMasterDto transferdto = mutationIntimationService.getTansferDtoByMutIntimation(dto);
        if (dto.getClaimantList().size() > 1) {
            model.setOwnershipPrefix("JO");
            transferdto.setOwnerType(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("JO", "OWT",
                    UserSession.getCurrent().getOrganisation().getOrgid()));
        } else {
            model.setOwnershipPrefix("SO");
            transferdto.setOwnerType(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("JO", "OWT",
                    UserSession.getCurrent().getOrganisation().getOrgid()));
        }
        transferdto.setSmServiceId(service.getSmServiceId());
        transferdto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        if (service.getSmFeesSchedule().equals(1l)) {
            model.setAppliChargeFlag(service.getSmAppliChargeFlag());
        } else {
            model.setAppliChargeFlag(MainetConstants.N_FLAG);
        }
        if (model.getAppliChargeFlag().equals(MainetConstants.Y_FLAG)) {
            mutationService.fetchChargesForMuatation(transferdto,null);
        }
        model.setDeptId(service.getTbDepartment().getDpDeptid());
        model.setPropTransferDto(transferdto);
        return new ModelAndView("MutationIntimationView", "command", model);        
        } 
        return null;         
    }

    @RequestMapping(params = "downloadRegDoc", method = RequestMethod.POST)
    public @ResponseBody String downloadRegDoc(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        MutationModel model = this.getModel();
        return mutationIntimationService.downloadMutationDocument(model.getMutationIntimationViewDto(),
                model.getMutationIntimationViewDto().getRegistrationDocument());
    }

    @RequestMapping(params = "downloadMutDoc", method = RequestMethod.POST)
    public @ResponseBody String downloadMutDoc(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        MutationModel model = this.getModel();
        return mutationIntimationService.downloadMutationDocument(model.getMutationIntimationViewDto(),
                model.getMutationIntimationViewDto().getMutationDocument());
    }

    void setDataToTransferDto() {

    }
    
    @RequestMapping(params = "backToMainPage", method = RequestMethod.POST)
    public ModelAndView back(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        this.sessionCleanup(httpServletRequest);
    	getModel().bind(httpServletRequest);
        return defaultMyResult();      
    }
}
