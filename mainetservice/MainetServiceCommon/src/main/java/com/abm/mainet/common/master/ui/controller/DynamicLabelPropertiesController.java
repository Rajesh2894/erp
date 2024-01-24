package com.abm.mainet.common.master.ui.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.dto.PropertyFiles;
import com.abm.mainet.common.master.ui.model.DynamicLabelPropertiesModel;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/DynamicLabelProperties.html")
public class DynamicLabelPropertiesController extends AbstractFormController<DynamicLabelPropertiesModel> {

    private static final String PROPERTIES_EXTN = ".properties";
    private static final String LABLE_PATH = "lablpath";
    private static final Logger LOG = LoggerFactory.getLogger(DynamicLabelPropertiesController.class);
    static String filePath = MainetConstants.BLANK;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showStatusForm(final HttpServletRequest httpServletRequest) {

        sessionCleanup(httpServletRequest);
        return index();
    }

    @RequestMapping(params = "displayEachProperty", method = RequestMethod.GET)
    public @ResponseBody String displayAll(@RequestParam("propertyFileName") final String fileName) {

        final Properties props = new Properties();
        final List<PropertyFiles> propList = new ArrayList<>();

        try {
            props.load(new FileInputStream(new File(filePath + fileName)));
        } catch (final IOException e) {
            LOG.error(MainetConstants.ERROR_OCCURED, e);
        }

        PropertyFiles propsFile = null;

        for (final Entry<Object, Object> eachEntry : props.entrySet()) {

            propsFile = new PropertyFiles((String) eachEntry.getKey(), (String) eachEntry.getValue());
            propList.add(propsFile);
        }

        final ObjectMapper mapper = new ObjectMapper();

        String str = null;
        try {
            str = mapper.writeValueAsString(propList);
        } catch (final Exception e) {
            LOG.error(MainetConstants.ERROR_OCCURED, e);
        }

        return str;
    }

    @RequestMapping(params = "updateProperty", method = RequestMethod.POST)
    public @ResponseBody String savePropertyValue(@RequestParam("string") final String data) {
        final String[] array = data.split(MainetConstants.operator.TILDE);
        final File fileName = new File(filePath + array[2]);

        try {

            final PropertiesConfiguration pc = new PropertiesConfiguration(fileName.getAbsolutePath());
            pc.setReloadingStrategy(new FileChangedReloadingStrategy());

            pc.setAutoSave(true);

            pc.setProperty(array[0].trim(), array[1].trim());
            pc.save();

        } catch (final ConfigurationException e) {
            LOG.error(MainetConstants.ERROR_OCCURED, e);
        }

        return array[1].trim();

    }

    @ModelAttribute("propertiesFileList")
    public Map<Integer, String> getMasterReason() {

        final Map<Integer, String> fileList = new LinkedHashMap<>();
        File file = null;
        filePath = ApplicationSession.getInstance().getMessage(LABLE_PATH);
        final StringTokenizer tokenizer = new StringTokenizer(filePath, MainetConstants.operator.COMA);
        filePath = MainetConstants.BLANK;
        while (tokenizer.hasMoreElements()) {
            final String token = tokenizer.nextElement().toString();
            filePath += token + File.separator;
        }

        try {

            file = new File(filePath);
        } catch (final Exception e) {
            LOG.error(MainetConstants.ERROR_OCCURED, e);
        }
        int count = 1;
        for (final File eachFile : file.listFiles()) {

            if (eachFile.isFile() && eachFile.getName().endsWith(PROPERTIES_EXTN)) {

                fileList.put(count, eachFile.getName());
                count++;
            }
        }

        return fileList;

    }

}
