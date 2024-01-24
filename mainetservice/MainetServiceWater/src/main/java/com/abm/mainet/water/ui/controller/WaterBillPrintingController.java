package com.abm.mainet.water.ui.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.report.utility.ReportUtility;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.WaterBillPrintSkdclDTO;
import com.abm.mainet.water.ui.model.WaterBillPrintingModel;
/**
 * @author Rahul.Yadav
 *
 */
@Controller
@RequestMapping("/WaterBillPrinting.html")
public class WaterBillPrintingController extends AbstractFormController<WaterBillPrintingModel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WaterBillPrintingController.class);

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest request) {
        sessionCleanup(request);
        final WaterBillPrintingModel model = getModel();
        model.setConType(MainetConstants.PAYMODE.MOBILE);
        model.setEntity(new TbCsmrInfoDTO());
        model.getEntity().setCsFromdt(new Date());
        model.getEntity().setCsTodt(new Date());
        return defaultResult();
    }

    @RequestMapping(method = RequestMethod.POST, params = "serachWaterBillPrintData")
    public ModelAndView searchWaterBillRecords(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        final WaterBillPrintingModel model = getModel();
        model.setEntityList(null);
        model.searchWaterBillPrintingRecords();
        return index();
    }

    @RequestMapping(method = RequestMethod.POST, params = "generateBillAndPrint")
    public ModelAndView generateBillAndPrint(final HttpServletRequest httpServletRequest, 
    		final HttpServletResponse httpServletResponse, @RequestParam("idarray") final String ids) {

        bindModel(httpServletRequest);
        final WaterBillPrintingModel model = getModel();
        if ((ids != null) && !ids.isEmpty()) {
            final List<String> billIds = Arrays.asList(ids.split(MainetConstants.operator.COMMA));
//			model.generateBillandPrint(billIds);
            if ((Utility.isEnvPrefixAvailable( UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL))) {
                LOGGER.info("In SKDCL and metered bill condition ");

            	Map<Long, WaterBillPrintSkdclDTO> printBillForSkdcl = model.printBillForSkdcl(billIds);
            	final Map<String, Object> map = new HashMap<>();
                final String jrxmlName = "WaterMeterBillPrintingReport.jrxml";
                final String jrxmlFileLocation = Filepaths.getfilepath() + "jasperReport" + MainetConstants.FILE_PATH_SEPARATOR
                        + jrxmlName;
                List<WaterBillPrintSkdclDTO> billDtoValues = printBillForSkdcl.values().stream().collect(Collectors.toList());
                LOGGER.info("Bill DTO "+ billDtoValues.get(0));
                LOGGER.info("Searching for jasper file in location: " + jrxmlFileLocation);
                String fileName = ReportUtility.generateReportFromCollectionUtility(httpServletRequest, httpServletResponse,
                        jrxmlFileLocation, map,billDtoValues, UserSession.getCurrent().getEmployee().getEmpId());
                LOGGER.info("FileName is" + fileName);

                if (!fileName.equals(MainetConstants.SERVER_ERROR)) {
                    getModel().setFilePath(fileName);
                    if (fileName.contains(MainetConstants.DOUBLE_BACK_SLACE)) {
        				fileName = fileName.replace(MainetConstants.DOUBLE_BACK_SLACE, MainetConstants.QUAD_BACK_SLACE);
        			} else if (fileName.contains(MainetConstants.DOUBLE_FORWARD_SLACE)) {
        				fileName = fileName.replace(MainetConstants.DOUBLE_FORWARD_SLACE, MainetConstants.QUAD_FORWARD_SLACE);
        			}
        			getModel().setFilePath(fileName);
                }
                else {
                	model.addValidationError("Jasper report not found in specified location: " + fileName);
                	LOGGER.error("Error while calling generateReportFromCollectionUtility(): " + fileName);
                }
                getModel().setRedirectURL("WaterBillPrinting.html");
            	return new ModelAndView(MainetConstants.URL_EVENT.OPEN_NEXT_TAB, MainetConstants.FORM_NAME, model);
            	
			}else {
                LOGGER.info("non-skdcl");
				model.generateBillandPrint(billIds);
				if (MainetConstants.NewWaterServiceConstants.METER.equals(model.getMeterType())) {
					return new ModelAndView("WaterMeterBillPrintData", MainetConstants.CommonConstants.COMMAND, model);
				} else {
					return new ModelAndView("WaterNonMeterBillPrintData", MainetConstants.CommonConstants.COMMAND,
							model);
				}
			}
        } else {
            model.addValidationError(ApplicationSession.getInstance().getMessage("water.billPrint.selectOne"));
            return defaultMyResult();
        }
    
    }
}
