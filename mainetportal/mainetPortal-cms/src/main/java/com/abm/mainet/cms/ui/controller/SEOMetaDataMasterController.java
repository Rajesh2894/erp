package com.abm.mainet.cms.ui.controller;

import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.ui.model.SEOMetaDataMasterModel;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.SEOKeyWordMaster;
import com.abm.mainet.common.service.ISEOMetaDataMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.UserSession;

@Controller
@RequestMapping("/SEOMaster.html")
public class SEOMetaDataMasterController extends AbstractFormController<SEOMetaDataMasterModel> {

    @Autowired
    private ISEOMetaDataMasterService service;

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        this.getModel().setCommonHelpDocs("SEOMaster.html");
        Optional<SEOKeyWordMaster> master = service.getSEOMaster(UserSession.getCurrent().getOrganisation().getOrgid());
        if (master.isPresent()) {
            this.getModel().setMaster(master.get());
        }
        return index();
    }

    @Override
    @RequestMapping(params = "saveform", method = RequestMethod.POST)
    public ModelAndView saveform(final HttpServletRequest httpServletRequest) {

        bindModel(httpServletRequest);
        SEOKeyWordMaster master = this.getModel().getMaster();
        String keyWord = master.getKeyWord();
        String description = master.getDescription();
        if (StringUtils.isEmpty(keyWord) || StringUtils.isEmpty(description)) {
            // validation
            this.getModel().addValidationError(getApplicationSession().getMessage("seo.key.word.validate"));
            return defaultMyResult();
        } else {
            Employee employee = UserSession.getCurrent().getEmployee();
            Date date = new Date();
            master.setKeyWord(keyWord);
            if (master.getSeoId() == 0L) {
                master.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
                master.setCreatedBy(employee.getEmpId());
                master.setCreatedDate(date);
                master.setLgipmac(employee.getEmppiservername());
            } else {
                master.setUpdatedBy(employee.getEmpId());
                master.setUpdatedDate(date);
                master.setLgipmacupd(employee.getEmppiservername());
            }

            service.saveOrUpdateSEOMaster(master);
            return jsonResult(JsonViewObject
                    .successResult());
        }

    }

}
