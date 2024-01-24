package com.abm.mainet.common.pg.ui.controller;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.pg.dto.RePaymentDTO;
import com.abm.mainet.common.pg.ui.model.RePaymentFormModel;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.integration.ws.JersyCall;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/RePaymentForm.html")
public class RePaymentFormController extends AbstractFormController<RePaymentFormModel>

{

    private static final Logger LOGGER = Logger.getLogger(RePaymentFormController.class);

    @RequestMapping(params = "edit", method = { RequestMethod.POST })
    public ModelAndView editForm(@RequestParam final long rowId,
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        if (getModel().doAuthorization(rowId)) {
            final Long[] requestParam = { rowId };
            LinkedHashMap<Long, Object> responseObj = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
                    requestParam, ServiceEndpoints.JercyCallURL.DASHBOARD_REPAYMENT);

            if (responseObj != null && !responseObj.isEmpty()) {
                String d = new JSONObject(responseObj).toString();
                try {
                    RePaymentDTO app = new ObjectMapper().readValue(d, RePaymentDTO.class);
                    getModel().setDto(app);
                    getModel().setServiceId(app.getServiceId());
                    if(getModel().getOfflineDTO()!=null) {
                    getModel().getOfflineDTO().setChallanServiceType(app.getChallanServiceType());
                    getModel().getOfflineDTO().setFlatNo(app.getFlatNo());
                    }
                } catch (Exception ex) {
                    LOGGER.error("Exception while casting CitizenDashBoardResDTO to rest response :" + ex);
                }
            }
        } else {
            getModel().addValidationError(
                    getApplicationSession().getMessage("not.authorized.user"));
        }

        return this.defaultResult();

    }

}