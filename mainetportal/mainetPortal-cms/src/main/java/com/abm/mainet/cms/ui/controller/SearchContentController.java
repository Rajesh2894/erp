package com.abm.mainet.cms.ui.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.ui.model.SearchContentModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.dto.PropertyFiles;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.UserSession;

/**
 * @author ritesh.patil
 *
 */
@Controller
@RequestMapping("/SearchContent.html")
public class SearchContentController extends AbstractFormController<SearchContentModel> {

    private static final Logger LOG = Logger.getLogger(SearchContentController.class);

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(@RequestParam(value = "searchWord", required = false) final String searchWord,
            final HttpServletRequest request) {
    	 sessionCleanup(request);
        final SearchContentModel model = getModel();
        final String dTree = model.getDirectoryPath();
        final Employee employee = UserSession.getCurrent().getEmployee();
        if ((searchWord != null) && !(searchWord.equals(MainetConstants.BLANK))) {

            model.setPropertFileName(employee.getGmid());
            model.setKeyword(searchWord);
            setSearchListFromProperty(dTree, searchWord);
            if (employee.getGmid() != null) {
                getModel().setSearchListFromDB(searchWord);
            } else {
                getModel().setLinkMasterListFromDB(searchWord);
                getModel().setSubLinkMasterListFromDB(searchWord);
                getModel().setSubLinkdetailCKeditorFromDB(searchWord);
                getModel().getFinalSubLinkMasterList();
                
            }
        }
        
        if (null != employee && null != employee.getEmploginname() && employee.getEmploginname()
                .equalsIgnoreCase(ApplicationSession.getInstance().getMessage("citizen.noUser.loginName"))) {
            return new ModelAndView(getViewName(), MainetConstants.FORM_NAME, getModel());
        } else if (null != employee && null != employee.getLoggedIn()
                && employee.getLoggedIn().equalsIgnoreCase(MainetConstants.UNAUTH)) {  // SITE MAP FOR UN-AUTHORIZED LOGGED-IN
                                                                                       // USER
            return new ModelAndView(getViewName(), MainetConstants.FORM_NAME, getModel());
        } else {
            return new ModelAndView("SearchContentLogin", MainetConstants.FORM_NAME, getModel());
        }
    }

    public void setSearchListFromProperty(final String dTree, final String searchText) {

        final Properties props = new Properties();
        final List<PropertyFiles> propList = new ArrayList<>();

        try {

            props.load(new FileInputStream(new File(dTree + getModel().getAccessPropertyFileName())));
        } catch (final IOException e) {
            LOG.error("Error occured during setSearchListFromProperty", e);

        }

        PropertyFiles propsFile = null;

        for (final Entry<Object, Object> eachEntry : props.entrySet()) {

            propsFile = new PropertyFiles((String) eachEntry.getKey(), (String) eachEntry.getValue());

            if (StringUtils.containsIgnoreCase(eachEntry.getValue().toString(), searchText.trim())) {
                if (propsFile.getName().contains(MainetConstants.SEARCH_PROPERTY_DETAILS.NAME.SEARCH_SPECIAL_CHAR_PRESENT)) {
                    propsFile.setName(
                            propsFile.getName().replace(MainetConstants.SEARCH_PROPERTY_DETAILS.NAME.SEARCH_SPECIAL_CHAR_PRESENT,
                                    MainetConstants.SEARCH_PROPERTY_DETAILS.NAME.SEARCH_SPECIAL_CHAR_REPLACE));
                }
                propList.add(propsFile);
            }

        }
        getModel().setPropList(propList);
    }

}
