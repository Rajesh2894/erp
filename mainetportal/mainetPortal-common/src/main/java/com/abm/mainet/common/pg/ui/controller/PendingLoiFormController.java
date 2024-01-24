package com.abm.mainet.common.pg.ui.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.pg.dto.LoiPaymentSearchDTO;
import com.abm.mainet.common.pg.ui.model.PendingLoiFormModel;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.integration.ws.JersyCall;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/PendingLoiForm.html")
public class PendingLoiFormController extends AbstractFormController<PendingLoiFormModel> {
	private static final Logger LOGGER = Logger.getLogger(RePaymentFormController.class);

	@RequestMapping(params = "edit", method = { RequestMethod.POST })
	public ModelAndView editForm(@RequestParam final long rowId, @RequestParam final long taskId,
			final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		final UserSession userSession = UserSession.getCurrent();
		if (getModel().doAuthorization(rowId)) {
			getModel().getOfflineDTO().setFeeIds(new HashMap<>(0));
			LoiPaymentSearchDTO requestDTO = new LoiPaymentSearchDTO();
			requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			requestDTO.setApplicationId(rowId);
			requestDTO.setEmpId(userSession.getEmployee().getEmpId());
			requestDTO.setMobileNo(userSession.getEmployee().getEmpmobno());
			LinkedHashMap<Long, Object> responseObj = (LinkedHashMap<Long, Object>) JersyCall
					.callRestTemplateClient(requestDTO, ServiceEndpoints.JercyCallURL.DASHBOARD_LOI_PAYMENT);

			if (responseObj != null && !responseObj.isEmpty()) {
				String d = new JSONObject(responseObj).toString();
				try {
					LoiPaymentSearchDTO app = new ObjectMapper().readValue(d, LoiPaymentSearchDTO.class);
                  //Defect #134500 for orgid dependancy in DSCL
					if (app.getLoiMasData().getOrgid() != null && Utility
							.isEnvPrefixAvailable(userSession.getOrganisation(), MainetConstants.APP_NAME.DSCL)) {
						app.setOrgId(app.getLoiMasData().getOrgid());
					}
					getModel().setDto(app);
					getModel().setServiceId(app.getLoiMasData().getLoiServiceId());
					getModel().setTaskId(taskId);
				} catch (Exception ex) {
					LOGGER.error("Exception while casting CitizenDashBoardResDTO to rest response :" + ex);
				}
			}
		} else {
			getModel().addValidationError(getApplicationSession().getMessage("not.authorized.user"));
		}

		return this.defaultResult();

	}

	// Defect #117792
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.POST, params = "showDetails")
	public ModelAndView searchLOIRecords(final HttpServletRequest httpServletRequest,
			@RequestParam(value = "appId") final Long appId, @RequestParam(value = "serviceId") final Long serviceId,
			@RequestParam(value = "loiDate") final Date loiDate, @RequestParam(value = "loiNo") final String loiNo,
			@RequestParam(value = "loiAmount") final BigDecimal loiAmount) {
		bindModel(httpServletRequest);
		LoiPaymentSearchDTO requestDTO = new LoiPaymentSearchDTO();
		requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		requestDTO.setApplicationId(appId);
		requestDTO.setLoiNo(loiNo);
		requestDTO.setMobileNo(UserSession.getCurrent().getEmployee().getEmpmobno());
		requestDTO.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		requestDTO.setServiceId(serviceId);
		requestDTO.setLoiAmount(loiAmount);

		LinkedHashMap<Long, Object> responseObj = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(requestDTO, ServiceEndpoints.JercyCallURL.DASHBOARD_LOI_PAYMENT);

		if (responseObj != null && !responseObj.isEmpty()) {
			String d = new JSONObject(responseObj).toString();
			try {
				LoiPaymentSearchDTO app = new ObjectMapper().readValue(d, LoiPaymentSearchDTO.class);
				app.setLoiNo(app.getLoiMasData().getLoiNo());
				app.setApplicationDate(
						new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(app.getLoiMasData().getLoiDate()));
				getModel().setDto(app);
				getModel().setServiceId(app.getLoiMasData().getLoiServiceId());

			} catch (Exception ex) {
				throw new FrameworkException("Exception while casting LoiPaymentSearchDTO to rest response :" + ex);
			}
		} else {
			getModel().addValidationError(getApplicationSession().getMessage("not.authorized.user"));
		}
		return new ModelAndView("LoiPrintingDetail", MainetConstants.FORM_NAME, getModel());

	}

}
