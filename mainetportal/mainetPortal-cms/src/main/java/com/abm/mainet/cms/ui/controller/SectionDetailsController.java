package com.abm.mainet.cms.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.domain.SubLinkFieldDetails;
import com.abm.mainet.cms.domain.SubLinkMaster;
import com.abm.mainet.cms.ui.model.SectionDetailsModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.service.IEntitlementService;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.dms.service.FileUploadServiceValidator;

/**
 * @author Pranit.Mhatre
 * @since 25 February, 2014
 */
@Controller
@RequestMapping("/SectionDetails.html")
public class SectionDetailsController extends AbstractEntryFormController<SectionDetailsModel> {
	@Autowired
    private IEntitlementService iEntitlementService;
	
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        this.sessionCleanUp(httpServletRequest);
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        getModel().setCommonHelpDocs("SectionDetails.html");
        getModel().setListMode(true);
        return super.index();
    }

    @RequestMapping(method = RequestMethod.POST, produces = MainetConstants.URL_EVENT.JSON_APP, params = "SEARCH_RESULT_PEN")
    public @ResponseBody JQGridResponse<SubLinkMaster> getSectionListPen(@RequestParam final String page,
            @RequestParam final String rows, final HttpServletRequest httpServletRequest) {
        String flag = null;
        return getModel().paginate(httpServletRequest, page, rows, getModel().getSections(flag));
    }
 
    @RequestMapping(method = RequestMethod.POST, produces = MainetConstants.URL_EVENT.JSON_APP, params = "SEARCH_RESULT_APP")
    public @ResponseBody JQGridResponse<SubLinkMaster> getSectionListApp(@RequestParam final String page,
            @RequestParam final String rows, final HttpServletRequest httpServletRequest) {
        String flag = "Y";
        return getModel().paginate(httpServletRequest, page, rows, getModel().getSections(flag));
    }

    @RequestMapping(method = RequestMethod.POST, produces = MainetConstants.URL_EVENT.JSON_APP, params = "SEARCH_RESULT_REJ")
    public @ResponseBody JQGridResponse<SubLinkMaster> getSectionListRej(@RequestParam final String page,
            @RequestParam final String rows, final HttpServletRequest httpServletRequest) {
        String flag = "N";
        return getModel().paginate(httpServletRequest, page, rows, getModel().getSections(flag));
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.ui.controller.AbstractEntryFormController#editForm(long, javax.servlet.http.HttpServletRequest)
     */
    @Override
    public ModelAndView editForm(final long rowId, final HttpServletRequest httpServletRequest) {
        getModel().editForm(rowId);
        getModel().setListMode(true);
        return new ModelAndView("SectionDetailsForm", MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(method = RequestMethod.POST, params = "editForm")
    public ModelAndView editForm(final HttpServletRequest httpServletRequest) {
    	String rowId=httpServletRequest.getParameter("rowId");
        getModel().editForm(Long.parseLong(rowId));
        return new ModelAndView("SectionDetailsForm", MainetConstants.FORM_NAME, getModel());
    }
    
    @RequestMapping(method = RequestMethod.POST, params = "showFormLayOut")
    public ModelAndView doIt(@RequestParam("rowId") final String rowId) {
        getModel().setListMode(true);
        return new ModelAndView("SectionDetailsValidn", MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(method = RequestMethod.POST, params = "AddDetails")
    public ModelAndView addDetails(final HttpServletRequest httpServletRequest) {
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        getModel().setListMode(false);
		if(httpServletRequest.getParameter("rowId")!=null) { 
		String rowId=httpServletRequest.getParameter("rowId");
		getModel().editForm(Long.parseLong(rowId)); 
		}
		
		
        SubLinkFieldDetails subLinkFieldDetails = new SubLinkFieldDetails();
        //subLinkFieldDetails.setValidityDate(date);
        subLinkFieldDetails.setIsHighlighted(MainetConstants.FlagN);
        getModel().setEntity(subLinkFieldDetails);
        getModel().getMaxOrderDetail();
        getModel().getDept();
        getModel().getInvestmentTypeList();
        getModel().getFinancialYear();
        getModel().getImgName().clear();
        getModel().setListMode(false);
        getModel().setMode("Add");
        return new ModelAndView("SectionDetailsForm", MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(method = RequestMethod.POST, params = "EditDetails")
    public ModelAndView editDetails(final long rowId, final HttpServletRequest httpServletRequest) {
        getModel().editDetailObject(rowId);
        getModel().getDept();
        getModel().getFinancialYear();
        getModel().getInvestmentTypeList();
        getModel().setListMode(false);
        validateAndSetClobBlobData(httpServletRequest);
        return new ModelAndView("SectionDetailsForm", MainetConstants.FORM_NAME, getModel());
    }

    
    @RequestMapping(method = RequestMethod.POST, params = "DeleteDetail")
    public @ResponseBody JsonViewObject deleteDetails(final long rowId, final HttpServletRequest httpServletRequest) {
        final SectionDetailsModel model = getModel();

        model.deleteDetailObject(rowId);

        final JsonViewObject jsonViewObject = JsonViewObject.successResult();

        jsonViewObject.setMessage(getMessageText("Delete.SUCCESS"));

        return jsonViewObject;

    }

    @RequestMapping(method = RequestMethod.POST, params = "HideDetail")
    public @ResponseBody JsonViewObject hideDetails(@RequestParam("rowId") long rowId, @RequestParam("hideflag") String hideflag, final HttpServletRequest httpServletRequest) {
        final SectionDetailsModel model = getModel();
        boolean flag=model.hideDetailObject(rowId,hideflag);

        final JsonViewObject jsonViewObject = JsonViewObject.successResult();

        if(flag==true) {
    	if(hideflag.equals(MainetConstants.IsDeleted.SUSPEND))
    		jsonViewObject.setMessage(getMessageText("deactivated.SUCCESS"));
    	else
    		jsonViewObject.setMessage(getMessageText("restored.SUCCESS"));
        }
        else
        	jsonViewObject.setMessage(getMessageText("restored.failure"));

        return jsonViewObject;

    }

    @RequestMapping(params = "DeleteImage", method = RequestMethod.POST)
    public @ResponseBody void DeleteFile(@RequestParam("fileName") final String fileName, @RequestParam("del") final String del,
    		@RequestParam("elementName") final String elementName, final HttpServletRequest httpServletRequest) {
        getModel().deleteFiles(fileName, del,elementName);
    }

    @RequestMapping(method = RequestMethod.POST, params = "CancelForm")
    public ModelAndView cancelElement(final HttpServletRequest httpServletRequest) {
        getModel().setListMode(true);
        return new ModelAndView("SectionDetailsForm", MainetConstants.FORM_NAME, getModel());
    }

    @SuppressWarnings("unused")
    @RequestMapping(method = RequestMethod.POST, params = "SaveDetail")
    public ModelAndView saveElement(final HttpServletRequest httpServletRequest) {
        final BindingResult bindingResult = bindModel(httpServletRequest);

        final SectionDetailsModel model = getModel();
        model.getEntity().setTxt_01_en(model.getEntity().getTxt_01_en());
        model.getEntity().setTxt_01_rg(model.getEntity().getTxt_01_rg());
        model.getSubLinkMaster().getSubLinkFieldMappings().get(0).getFieldType();
        getModel().getFileStorageCache();
        ModelAndView mv = null;
        final String parameter = httpServletRequest.getParameter("fieldType");
        final Integer fieldType = Integer
                .valueOf(StringUtils.isNumeric(parameter) ? parameter : MainetConstants.Common_Constant.NUMBER.ZERO);
        String englishData = httpServletRequest.getParameter("String1");
        String hindiData = httpServletRequest.getParameter("String2");
        final String realpath = "cache" + MainetConstants.FILE_PATH_SEPARATOR +  UserSession.getCurrent().getOrganisation().getOrgid();
        if (StringUtils.isNotEmpty(englishData) && fieldType.equals(MainetConstants.TEXT_AREA_HTML)) {
            model.getEntity().setTxta_03_ren_blob(englishData);
        } else {
            model.getEntity().setTxta_03_ren_blob(null);
        }
       
        if (StringUtils.isNotEmpty(hindiData) && fieldType.equals(MainetConstants.TEXT_AREA_HTML)) {
            // hindiData = replaceImagePath(hindiData, realpath);
            model.getEntity().setTxta_03_en_nnclob(hindiData);
        } else {
            model.getEntity().setTxta_03_en_nnclob(null);
        }
        if(model.getEntity().getIsHighlighted()==null)
        {
        	  model.getEntity().setIsHighlighted(MainetConstants.FlagN);
        }
        mv = new ModelAndView("SectionDetailsForm", MainetConstants.FORM_NAME, model);

        model.setListMode(getModel().saveOrUpdateForm());

        if ((bindingResult != null) && !bindingResult.equals(0)) {
            validateAndSetClobBlobData(httpServletRequest);
            mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, model.getBindingResult());
        }

        return mv;
    }

    /*
     * private String replaceImagePath(String data, final String realpath) { data = data.replace("\\", MainetConstants.BLANK);
     * data = data.replace("images", realpath); data = data.replace("jpg\\", "jpg"); return data; }
     */

    private void validateAndSetClobBlobData(final HttpServletRequest httpServletRequest) {
        if ((null != getModel().getEntity().getTxta_03_ren_blob())
                && (!getModel().getEntity().getTxta_03_ren_blob().isEmpty())) {
            final String text1 = new String(getModel().getEntity().getTxta_03_ren_blob());
            httpServletRequest.setAttribute("String1", text1);
        }
        if ((null != getModel().getEntity().getTxta_03_en_nnclob())
                && !getModel().getEntity().getTxta_03_en_nnclob().isEmpty()) {
            httpServletRequest.setAttribute("String2", getModel().getEntity().getTxta_03_en_nnclob());
        }
    }

    @RequestMapping(params = "AdminFaqCheker", method = { RequestMethod.POST })
    public ModelAndView chekkerSearch(final HttpServletRequest request) {
        bindModel(request);
        final String isSuperAdmin = iEntitlementService.getGroupCodeById(UserSession.getCurrent().getEmployee().getGmid(),UserSession.getCurrent().getOrganisation().getOrgid());
        if(isSuperAdmin.equalsIgnoreCase(MainetConstants.GROUPMASTER.ADMIN_GROUP_CODE) || isSuperAdmin.equalsIgnoreCase(MainetConstants.GROUPMASTER.ADMIN_GR_BOTH)){ 
        	getModel().setMakkerchekkerflag(MainetConstants.NEC.CITIZEN);
        	return new ModelAndView(getViewName(), MainetConstants.FORM_NAME, getModel());
        }else {
        long gmid = iEntitlementService.getGroupIdByName(MainetConstants.MENU.APPROVER, UserSession.getCurrent().getOrganisation().getOrgid());
        if (gmid == UserSession.getCurrent().getEmployee().getGmid()) {
        	getModel().setMakkerchekkerflag(MainetConstants.NEC.CITIZEN);
        	return new ModelAndView(getViewName(), MainetConstants.FORM_NAME, getModel());
        } else {
        	return new ModelAndView("redirect:/LogOut.html");
        }}
    }
}
