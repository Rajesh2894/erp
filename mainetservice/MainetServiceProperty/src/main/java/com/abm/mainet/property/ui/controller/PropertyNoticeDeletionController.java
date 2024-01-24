
package com.abm.mainet.property.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.property.dto.NoticeGenSearchDto;
import com.abm.mainet.property.service.PropertyNoticeDeletionService;
import com.abm.mainet.property.ui.model.PropertyDemandNoticeGenerationModel;

@Controller
@RequestMapping({ "/PropertyNoticeDeletion.html" })
public class PropertyNoticeDeletionController extends AbstractFormController<PropertyDemandNoticeGenerationModel>

{

    @Resource
    private ILocationMasService iLocationMasService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private PropertyNoticeDeletionService propertyNoticeDeletionService;

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(HttpServletRequest request) {
        this.sessionCleanup(request);

        this.resetForm(request);

        getModel().setCommonHelpDocs("PropertyDemandNoticeGeneration.html");
        PropertyDemandNoticeGenerationModel model = this.getModel();
        // model.getSpecialNotGenSearchDto().setSpecNotSearchType("SM");

        model.bind(request);
        List<LookUp> locList = getModel().getLocation();
        Organisation org = UserSession.getCurrent().getOrganisation();
        Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Property.PROP_DEPT_SHORT_CODE);
        List<LocationMasEntity> location = iLocationMasService.findWZMappedLocationByOrgIdAndDeptId(org.getOrgid(),
                deptId);
        if (location != null && !location.isEmpty()) {
            location.forEach(loc -> {
                LookUp lookUp = new LookUp();
                lookUp.setLookUpId(loc.getLocId());
                lookUp.setDescLangFirst(loc.getLocNameEng());
                lookUp.setDescLangSecond(loc.getLocNameReg());
                locList.add(lookUp);
            });
        }
        List<String> notices = new ArrayList<>();
        notices.add("DN");
        notices.add("WN");
        notices.add("SP");
        // notices.add("WEN");
        List<LookUp> demandType = model.getLevelData("NTY");
        demandType.stream()
                .filter(notice -> notices.contains(notice.getLookUpCode()))
                .forEach(type -> {
                    model.getDemandType().add(type);
                });

        return defaultResult();

    }

    @RequestMapping(method = RequestMethod.POST, params = { "SearchNotice" })
    public ModelAndView search(HttpServletRequest request) {
        getModel().bind(request);
        PropertyDemandNoticeGenerationModel model = this.getModel();

        boolean validate = false;
        model.setNotGenSearchDtoList(null);
        if (model.getSpecialNotGenSearchDto().getNoticeType() == null || model.getSpecialNotGenSearchDto().getNoticeType() <= 0) {
            model.addValidationError("Please select notice type.");
        } else {
            // if(model.getSpecialNotGenSearchDto().getSpecNotSearchType().equals("SM")) {
            if ((model.getSpecialNotGenSearchDto().getNoticeNo() == null)) {

                model.addValidationError("Please enter valid notice number");
            } else {

                long noticeNo = model.getSpecialNotGenSearchDto().getNoticeNo();
                Long noticeType = model.getSpecialNotGenSearchDto().getNoticeType();

                LookUp typelookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(noticeType,
                        UserSession.getCurrent().getOrganisation());

                boolean validateNoticeForExistOrNot = propertyNoticeDeletionService.validateNoticeForExistOrNot(noticeNo,
                        typelookUp, noticeType,
                        UserSession.getCurrent().getOrganisation().getOrgid());
                if (validateNoticeForExistOrNot) {

                    if (typelookUp.getLookUpCode().equals("WN")) {
                        validate = true;

                    } else {

                        validate = propertyNoticeDeletionService.validateNoticeDeletion(noticeNo, typelookUp, noticeType,
                                UserSession.getCurrent().getOrganisation().getOrgid());
                    }

                    if (validate) {

                        model.getSpecialNotGenSearchDto().setAssdUsagetype1(null);

                        fetchPropertyforNoticeDeletion(model, noticeType);

                    }

                    else {
                        model.addValidationError("notice can not be  deleted");

                    }

                } else {
                    model.addValidationError("Entered Notice No Not Found");
                }

            }
        }
        return defaultResult();
    }

    private void fetchPropertyforNoticeDeletion(PropertyDemandNoticeGenerationModel model, long noticeType) {

        List<NoticeGenSearchDto> notGenShowList = propertyNoticeDeletionService.fetchPropertyForNoticeDeletion(model,
                UserSession.getCurrent().getOrganisation().getOrgid(), noticeType);

        model.setNotGenSearchDtoList(notGenShowList);
        if (notGenShowList == null || notGenShowList.isEmpty()) {
            model.addValidationError("No record found");
        }

    }

    @RequestMapping(params = { "DeleteNotice" }, method = RequestMethod.POST)
    public ModelAndView deleteBill(HttpServletRequest httpServletRequest) {
        getModel().bind(httpServletRequest);
        PropertyDemandNoticeGenerationModel model = this.getModel();
        List<NoticeGenSearchDto> notGenSearchDtoList = model.getNotGenSearchDtoList();

        NoticeGenSearchDto noticeGenSearchDto = notGenSearchDtoList.get(0);

        long noticeId = noticeGenSearchDto.getNoticeId();
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        propertyNoticeDeletionService.deleteNoticByNoticeNO(noticeId, orgId);

        return jsonResult(JsonViewObject.successResult(
                ApplicationSession.getInstance().getMessage("Notice Deleted Successfully")));
    }
}