package com.abm.mainet.label.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.PropertyFiles;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.label.model.LableUpdateSearchModel;

@Controller
@RequestMapping("LableUpdateSearch.html")
public class LableUpdateSearchController extends AbstractFormController<LableUpdateSearchModel> {

    String directoryPath = MainetConstants.BLANK;
    private static final Logger LOG = Logger.getLogger(LableUpdateSearchController.class);

    @RequestMapping(method = {RequestMethod.POST,RequestMethod.GET})
    public ModelAndView showLabelUpdate(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        directoryPath = getModel().getDirectoryPath();
        return index();
    }

    @RequestMapping(method = RequestMethod.GET, params = "openSelected")
    public ModelAndView openSelectedForm(final HttpServletRequest httpServletRequest) {
        directoryPath = getModel().getDirectoryPath();

        return index();
    }

    @RequestMapping(params = "displayEachProperty", method = RequestMethod.POST)
    public @ResponseBody boolean displayAll(@RequestParam("propertyFileName") final String fileName, final long value) {

        getModel().setSelectBoxValue(value);

        final Properties props = new Properties();
        final List<PropertyFiles> propList = new ArrayList<>();

        try {
            props.load(new FileInputStream(new File(directoryPath + fileName)));
        } catch (final IOException e) {
            LOG.error(MainetConstants.ERROR_OCCURED, e);
        }

        PropertyFiles propsFile = null;

        for (final Entry<Object, Object> eachEntry : props.entrySet()) {

            propsFile = new PropertyFiles((String) eachEntry.getKey(), (String) eachEntry.getValue());
            propsFile.setPropfileName(fileName);
            propList.add(propsFile);
        }
        getModel().setPropList(propList);
        return true;

    }

    @RequestMapping(method = RequestMethod.POST, produces = MainetConstants.URL_EVENT.JSON_APP, params = "LABLE_LIST")
    public @ResponseBody JQGridResponse<PropertyFiles> getGrid(final HttpServletRequest httpServletRequest,
            @RequestParam final String page, @RequestParam final String rows) {

        return getModel().paginate(httpServletRequest, page, rows, getModel().getPropList());
    }

    @RequestMapping(params = "getPopUp", method = RequestMethod.POST)
    public ModelAndView addForm(@RequestParam("key") final String key, @RequestParam("file") final String file,
            final HttpServletRequest httpServletRequest) {
        final LableUpdateSearchModel model = getModel();
        final Properties props = new Properties();
        try {
            props.load(new FileInputStream(new File(directoryPath + file)));
        } catch (final IOException e) {
            LOG.error(MainetConstants.ERROR_OCCURED, e);
        }
        final String value = (String) props.get(key);
        final PropertyFiles propFile = model.getPropFile();
        propFile.setValue(value);
        propFile.setName(key);
        propFile.setPropfileName(file);

        return new ModelAndView("LabelUpdateEdit", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "save", method = RequestMethod.POST)
    public ModelAndView saveForm(final HttpServletRequest httpServletRequest, @ModelAttribute final PropertyFiles propFile) {
        bindModel(httpServletRequest);

        final LableUpdateSearchModel model = getModel();
        final PropertyFiles propFiles = model.getPropFile();

        final File fileName = new File(directoryPath + propFiles.getPropfileName());

        try {

            final PropertiesConfiguration pc = new PropertiesConfiguration(fileName.getAbsolutePath());
            pc.setReloadingStrategy(new FileChangedReloadingStrategy());

            pc.setAutoSave(true);

            pc.setProperty(propFiles.getName().trim(), propFiles.getValue().trim());
            pc.save();
            return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));

        } catch (final ConfigurationException e) {
            LOG.error(MainetConstants.ERROR_OCCURED, e);
        }

        return defaultResult();

    }

}
