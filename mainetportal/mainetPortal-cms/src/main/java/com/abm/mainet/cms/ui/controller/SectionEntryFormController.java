package com.abm.mainet.cms.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.domain.SubLinkFieldDetails;
import com.abm.mainet.cms.domain.SubLinkFieldMapping;
import com.abm.mainet.cms.domain.SubLinkMaster;
import com.abm.mainet.cms.ui.model.SectionEntryFormModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.LookUp;

/**
 * @author Pranit.Mhatre
 * @since 18 February, 2014
 */
@Controller
@RequestMapping("/SectionEntryForm.html")
public class SectionEntryFormController extends AbstractEntryFormController<SectionEntryFormModel> {
    @RequestMapping(method = RequestMethod.POST, params = "saveAndHoldData")
    public ModelAndView tempSaveProperty(final HttpServletRequest httpServletRequest) {

        bindModel(httpServletRequest);

        final SectionEntryFormModel model = getModel();
        /*final List<SubLinkFieldMapping> SubLinkFieldMapping2 = model.getEntity().getSubLinkFieldMappings();*/
       /* if (SubLinkFieldMapping2 != null)
        {
            for (final SubLinkFieldMapping subLinkFieldMapping : SubLinkFieldMapping2) {
            	subLinkFieldMapping.setCpdSectionType(subLinkFieldMapping.getSectionType());
            }
        }
        /*int count = SubLinkFieldMapping2.size();
        int count2 = 0;
        if (model.getSubLinkFieldMapping() != null) {
            final List<SubLinkFieldDetails> getSubLinkFieldlist2 = model.getSubLinkFieldMapping().getSubLinkFieldlist();
            count2 = getSubLinkFieldlist2.size();
        }*/

        /*if ((count != 0) || (count2 != 0)) {
            if ((count != 0) && (count2 != 0)) {
                if (model.getSubLinkFieldMapping().getSubLinkFieldlist().get(0).getFieldNameEn() != null) {
                    SubLinkFieldMapping2.clear();
                    count = 0;
                }
            }*/
           /* if (model.getSubLinkFieldMapping() != null)

            {
                for (final SubLinkFieldDetails subLinkFieldDetails3 : model.getSubLinkFieldMapping().getSubLinkFieldlist()) {
                    if (subLinkFieldDetails3.getFieldNameEn() != null) {
                        SubLinkFieldMapping2.add(new SubLinkFieldMapping());
                      
                        SubLinkFieldMapping2.get(count).setFieldNameEn(subLinkFieldDetails3.getFieldNameEn());
                        SubLinkFieldMapping2.get(count).setFieldNameRg(subLinkFieldDetails3.getFieldNameRg());
                        SubLinkFieldMapping2.get(count).setFieldType(subLinkFieldDetails3.getFieldType());

                        count++;
                    }

                }
            }*/
        //}

        if (model.saveSection()) {
            return new ModelAndView("SectionEntryFormElement", MainetConstants.FORM_NAME, model);
        }

        if (model.getSectionTypeList().isEmpty()) {
            model.setSectionTypeList(new ArrayList<SubLinkMaster>(0));
        }
        return super.defaultResult();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(method = RequestMethod.POST, params = "AddElement")
    public ModelAndView openElementForm(final HttpServletRequest httpServletRequest, final Model model) {

        getModel().setSubLinkFieldMapping(new SubLinkFieldMapping());
        getModel().setSectionType2(new ArrayList(0));
        if (getModel().getEntity().getSectionType0() != 0) {
            getModel().getSectionType2().add(getModel().getEntity().getSectionType0());
        }

        if ((getModel().getEntity().getSectionType1() != null) && (getModel().getEntity().getSectionType0() != 0)) {
            getModel().getSectionType2().add(getModel().getEntity().getSectionType1());
        }
        if ((getModel().getEntity().getSectionType2() != null) && (getModel().getEntity().getSectionType2() != 0)) {
            getModel().getSectionType2().add(getModel().getEntity().getSectionType2());
        }
        if ((getModel().getEntity().getSectionType3() != null) && (getModel().getEntity().getSectionType3() != 0)) {
            getModel().getSectionType2().add(getModel().getEntity().getSectionType3());
        }
        if ((getModel().getEntity().getSectionType4() != null) && (getModel().getEntity().getSectionType4() != 0)) {
            getModel().getSectionType2().add(getModel().getEntity().getSectionType4());
        }

        model.addAttribute("sectionType2", getModel().getSectionType2());
        getModel().setListMode(false);
        return new ModelAndView("SectionEntryFormElement", MainetConstants.FORM_NAME, getModel());
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(method = RequestMethod.POST, params = "EditElement")
    public ModelAndView editElement(final long rowId, final HttpServletRequest httpServletRequest, final Model model) {
        model.addAttribute("sectionType3", "EEE");
        getModel().setEditData(rowId);        
        if(Math.signum(rowId) == MainetConstants.SIGNUM_NEGATIVE) {
        	
            getModel().setSectionType2(new ArrayList(0));
            if (getModel().getEntity().getSectionType0() != 0) {
                getModel().getSectionType2().add(getModel().getEntity().getSectionType0());
            }

            if ((getModel().getEntity().getSectionType1() != null) && (getModel().getEntity().getSectionType0() != 0)) {
                getModel().getSectionType2().add(getModel().getEntity().getSectionType1());
            }
            if ((getModel().getEntity().getSectionType2() != null) && (getModel().getEntity().getSectionType2() != 0)) {
                getModel().getSectionType2().add(getModel().getEntity().getSectionType2());
            }
            if ((getModel().getEntity().getSectionType3() != null) && (getModel().getEntity().getSectionType3() != 0)) {
                getModel().getSectionType2().add(getModel().getEntity().getSectionType3());
            }
            if ((getModel().getEntity().getSectionType4() != null) && (getModel().getEntity().getSectionType4() != 0)) {
                getModel().getSectionType2().add(getModel().getEntity().getSectionType4());
            }			
            model.addAttribute("fieldType2", getModel().getSectionType2());
        }
        return new ModelAndView("SectionEntryFormElement", MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(method = RequestMethod.POST, params = "DeleteElement")
    public @ResponseBody JsonViewObject deleteElement(final long rowId, final HttpServletRequest httpServletRequest) {

        final SectionEntryFormModel model = getModel();

        model.deleteElement(rowId);

        final JsonViewObject jsonViewObject = JsonViewObject.successResult();

        jsonViewObject.setMessage(getMessageText("Delete.SUCCESS"));

        return jsonViewObject;

    }

    @RequestMapping(method = RequestMethod.POST, params = "saveElement")
    public ModelAndView saveElement(final HttpServletRequest httpServletRequest, final Model model) {
        bindModel(httpServletRequest);

        getModel().setListMode(getModel().saveElement());
        model.addAttribute("sectionType2", getModel().getSectionType2());
        final BindingResult bindingResult = getModel().getBindingResult();
        final ModelAndView mv = new ModelAndView("SectionEntryFormElement", MainetConstants.FORM_NAME, getModel());
        if (bindingResult != null) {
            mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
        }

        return mv;
    }

    @RequestMapping(method = RequestMethod.POST, params = "Cancel")
    public ModelAndView cancelElement(final HttpServletRequest httpServletRequest) {
        getModel().setListMode(true);
        return new ModelAndView("SectionEntryFormElement", MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(method = RequestMethod.POST, params = "function")
    public @ResponseBody List<LookUp> getFunction(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);

        return getModel().getFunctions();
    }

    @RequestMapping(method = RequestMethod.POST, params = "functionCount")
    public @ResponseBody Double getFunctionCount(@RequestParam("linkid") final long linkid,
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);

        return getModel().getFunctionCount(linkid);
    }
}
