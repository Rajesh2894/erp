package com.abm.mainet.property.ui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.property.dto.AssessmentChargeCalDTO;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.LandTypeApiDetailRequestDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentFactorDtlDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.SelfAssessmentDeaultValueDTO;
import com.abm.mainet.property.service.SelfAssessmentService;
import com.abm.mainet.property.ui.model.SelfAssesmentNewModel;

@Controller
@RequestMapping("/ShowAssessmentDetails.html")
public class ShowAssessmentDetailsController extends AbstractFormController<SelfAssesmentNewModel> {

    @Autowired
    private SelfAssessmentService selfAssessmentService;

    @Autowired
    private IPortalServiceMasterService serviceMaster;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest request) throws Exception {
        this.sessionCleanup(request);
        return new ModelAndView("SelfAssessmentTermCondition", MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(params = "showDetails", method = RequestMethod.POST)
    public ModelAndView defaultLoad(@RequestParam("appId") final long appId, @RequestParam("appStatus") String appStatus,
            final HttpServletRequest httpServletRequest) throws Exception {
        this.sessionCleanup(httpServletRequest);
        getModel().bind(httpServletRequest);
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        SelfAssesmentNewModel model = this.getModel();
        model.setApmMode(appStatus);
        model.setAssType(MainetConstants.Property.View_ASESS);
        setCommonFields(model);
        SelfAssessmentDeaultValueDTO data = selfAssessmentService.setAllDefaultFields(orgId, getModel().getDeptId());
        model.setLeastFinYear(data.getLeastFinYear());
        model.setLocation(data.getLocation());
        model.setSchedule(data.getSchedule());
        SelfAssessmentDeaultValueDTO requestData = new SelfAssessmentDeaultValueDTO();
        ProvisionalAssesmentMstDto proMas = new ProvisionalAssesmentMstDto();
        requestData.setProvisionalMas(proMas);
        requestData.setOrgId(orgId);
        requestData.getProvisionalMas().setApmApplicationId(appId);
        requestData.setDeptId(model.getDeptId());
        SelfAssessmentDeaultValueDTO response = selfAssessmentService.fetchPropertyByApplicationId(requestData);
        model.setDefaultData(response);
        model.setProvisionalAssesmentMstDto(response.getProvisionalMas());
        model.setMobdisabled(false);
        ProvisionalAssesmentMstDto assMst = model.getProvisionalAssesmentMstDto();      
        model.setServiceId(response.getProvisionalMas().getSmServiceId());
        if (assMst.getAssCorrAddress() == null) {
            assMst.setProAsscheck(MainetConstants.YES);
        } else {
            assMst.setProAsscheck(MainetConstants.NO);
        }
        if (assMst.getAssLpYear() == null) {
            assMst.setProAssBillPayment(MainetConstants.Property.NOT_APP);
        } else {
            assMst.setProAssBillPayment(MainetConstants.Property.MANUAL);
        }
        model.setFinancialYearMap(model.getDefaultData().getFinancialYearMap());

        List<ProvisionalAssesmentFactorDtlDto> factorMap = factorMappingForView(assMst);
        List<LookUp> lookupList = CommonMasterUtility.getLookUps("FCT",
                UserSession.getCurrent().getOrganisation());
        lookupList.forEach(factQuest -> {
            boolean result = factorMap.stream()
                    .anyMatch(fact -> factQuest.getLookUpId() == fact.getAssfFactorId().longValue());
            if (result) {
                assMst.getProAssfactor().add(MainetConstants.FlagY);
            } else {
                assMst.getProAssfactor().add(MainetConstants.FlagN);
            }
        });
        model.setProvAsseFactDtlDto(factorMap);
        this.getModel().setProvisionalAssesmentMstDto(assMst);
        this.getModel().setFinYearList(assMst.getFinancialYearList());
        model.setOwnershipPrefix(CommonMasterUtility
                .getNonHierarchicalLookUpObject(model.getProvisionalAssesmentMstDto().getAssOwnerType()).getLookUpCode());
        if (assMst.getAssLandType() != null) {
            LookUp landTypePrefix = CommonMasterUtility.getNonHierarchicalLookUpObject(
                    assMst.getAssLandType(), UserSession.getCurrent().getOrganisation());
            getModel().setLandTypePrefix(landTypePrefix.getLookUpCode());
        }
        model.getDefaultData().setProvisionalMas(assMst);
        if (getModel().getLandTypePrefix() != null) {
            setlandTypeDetails(getModel());
        }
        model.setDropDownValues();
        model.getDefaultData().setDeptId(model.getDeptId());
        model.getDefaultData().setProvAsseFactDtlDto(factorMap);

        List<DocumentDetailsVO> responseDto = selfAssessmentService.fetchAppDocuments(appId, orgId);
        model.setDocList(responseDto);

        AssessmentChargeCalDTO chargeCalDto = new AssessmentChargeCalDTO();
        chargeCalDto.setDeptId(model.getDeptId());
        chargeCalDto.setProvisionalMas(response.getProvisionalMas());
        if (assMst.getAssStatus() != null && !assMst.getAssStatus().equals("SD")) {
            AssessmentChargeCalDTO billdto = selfAssessmentService.fetchTaxListForDisplay(chargeCalDto);
            model.getProvisionalAssesmentMstDto().setBillTotalAmt(billdto.getProvisionalMas().getBillTotalAmt());
            Map<String, List<BillDisplayDto>> displayMap = selfAssessmentService.getTaxMapForDisplayCategoryWise(
                    billdto.getDisplayDto(),
                    UserSession.getCurrent().getOrganisation(), model.getDeptId(), billdto.getTaxCatList());
            model.setDisplayMap(displayMap);
        }
        if (model.getProvisionalAssesmentMstDto().getBillTotalAmt() >= 0) {
            model.setIsEdit(MainetConstants.MENU.Y);
        }
        return new ModelAndView("PropertyAssessmentView", MainetConstants.FORM_NAME, getModel());
    }

    private void setlandTypeDetails(SelfAssesmentNewModel model) {
        LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
        dto.setLandType(model.getLandTypePrefix());
        dto.setDistrictId(model.getProvisionalAssesmentMstDto().getAssDistrict());
        dto.setTehsilId(model.getProvisionalAssesmentMstDto().getAssTahasil());
        dto.setVillageId(model.getProvisionalAssesmentMstDto().getTppVillageMauja());
        dto.setMohallaId(model.getProvisionalAssesmentMstDto().getMohalla());
        dto.setStreetNo(model.getProvisionalAssesmentMstDto().getAssStreetNo());
        dto.setKhasraNo(model.getProvisionalAssesmentMstDto().getTppPlotNoCs());
        dto.setPlotNo(model.getProvisionalAssesmentMstDto().getTppPlotNo());
        model.setDistrict(selfAssessmentService.findDistrictByLandType(dto));
        model.setTehsil(selfAssessmentService.getTehsilListByDistrict(dto));
        model.setVillage(selfAssessmentService.getVillageListByTehsil(dto));              
        if (model.getLandTypePrefix().equals(MainetConstants.Property.KPK)) {
            model.setKhasraNo(selfAssessmentService.getKhasraNoList(dto));
        }
        if (model.getLandTypePrefix().equals(MainetConstants.Property.NZL)
                || model.getLandTypePrefix().equals(MainetConstants.Property.DIV)) {
            getModel().setMohalla(selfAssessmentService.getMohallaListByVillageId(dto));
            getModel().setBlockStreet(selfAssessmentService.getStreetListByMohallaId(dto));
        }
        if (model.getLandTypePrefix().equals(MainetConstants.Property.NZL)) {
            getModel().setPlotNo(selfAssessmentService.getNajoolPlotList(dto));
        }
        if (model.getLandTypePrefix().equals(MainetConstants.Property.DIV)) {
            getModel().setPlotNo(selfAssessmentService.getDiversionPlotList(dto));

        }
    }

    private List<ProvisionalAssesmentFactorDtlDto> factorMappingForView(final ProvisionalAssesmentMstDto assMst) {
        List<ProvisionalAssesmentFactorDtlDto> factorMap = new ArrayList<>();
        List<Long> unitNoList = new ArrayList<>();
        assMst.getProvisionalAssesmentDetailDtoList().forEach(propDet -> {
            if (!unitNoList.contains(propDet.getAssdUnitNo())) {
                propDet.getProvisionalAssesmentFactorDtlDtoList().forEach(fact -> {
                    ProvisionalAssesmentFactorDtlDto factDto = new ProvisionalAssesmentFactorDtlDto();
                    BeanUtils.copyProperties(fact, factDto);
                    factDto.setUnitNoFact(propDet.getAssdUnitNo().toString());
                    factDto.setProAssfFactorIdDesc(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(fact.getAssfFactorId(), UserSession.getCurrent().getOrganisation())
                            .getDescLangFirst());
                    factDto.setProAssfFactorValueDesc(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(fact.getAssfFactorValueId(),
                                    UserSession.getCurrent().getOrganisation())
                            .getDescLangFirst());
                    factDto.setFactorValueCode(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(fact.getAssfFactorId(), UserSession.getCurrent().getOrganisation())
                            .getLookUpCode());
                    factorMap.add(factDto);
                });
            }
            unitNoList.add(propDet.getAssdUnitNo());
        });
        return factorMap;
    }

    private void setCommonFields(SelfAssesmentNewModel model) {
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Long serviceId = serviceMaster.getServiceId(MainetConstants.Property.serviceShortCode.NCA, orgId);
        final Long deptId = serviceMaster.getServiceDeptIdId(serviceId);
        model.setOrgId(orgId);
        model.setDeptId(deptId);
        model.setIntgrtionWithBP(CommonMasterUtility
                .getDefaultValueByOrg(MainetConstants.Property.IBP, UserSession.getCurrent().getOrganisation()).getLookUpCode());
    }

    @RequestMapping(params = "editAssessmentDetails", method = RequestMethod.POST)
    public ModelAndView saveAuthorizationWithEdit(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        SelfAssesmentNewModel model = this.getModel();
        model.setAssType(MainetConstants.Property.View_ASESS);
        model.setDropDownValues();
        return new ModelAndView("SelfAssessmentFormEdit", MainetConstants.FORM_NAME, model);

    }
}
