package com.abm.mainet.smsemail.ui.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.service.IServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.smsemail.domain.SMSAndEmailInterface;
import com.abm.mainet.smsemail.domain.SMSAndEmailInterfaceDTO;
import com.abm.mainet.smsemail.domain.SmsAndMailRespose;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.abm.mainet.smsemail.ui.model.SMSAndEmailModel;
import com.abm.mainet.smsemail.ui.validator.SMSAndEmailTempalteValidator;

/**
 *
 * @author Kavali.Kiran
 *
 */
@Controller
@RequestMapping("/SMSAndEmail.html")
public class SMSAndEmailController extends AbstractEntryFormController<SMSAndEmailModel> {

    @Autowired
    ISMSAndEmailService ismsAndEmailService;

    @Autowired
    IServiceMasterService serviceMasterService;

    List<SMSAndEmailInterfaceDTO> list = new ArrayList<>();;

    @RequestMapping(method = {RequestMethod.GET ,RequestMethod.POST})
    public ModelAndView showStatusForm(final HttpServletRequest httpServletRequest) {
        list.clear();
        sessionCleanup(httpServletRequest);
        this.getModel().setCommonHelpDocs("SMSAndEmail.html");
        return index();
    }

    @RequestMapping(params = "serviceList")
    public @ResponseBody List<LookUp> getServiceList(@RequestParam("deptId") final Long deptId) {
        final List<Object[]> list = serviceMasterService
                .findAllActiveServicesByDepartment(UserSession.getCurrent().getOrganisation().getOrgid(), deptId,
                        MainetConstants.IsDeleted.ZERO);
        final List<LookUp> lookupList = new ArrayList<>();
        LookUp lookup = null;
        for (final Object[] obj : list) {
            lookup = new LookUp();
            lookup.setLookUpId(Long.valueOf(obj[0].toString()));
            lookup.setLookUpCode(obj[1].toString());
            lookupList.add(lookup);
        }
        return lookupList;
    }

    @RequestMapping(params = "getGridData")
    public @ResponseBody SmsAndMailRespose gridData(final HttpServletRequest request, final Model model) {
        final int page = Integer.parseInt(request.getParameter(MainetConstants.Common_Constant.PAGE));
        final SmsAndMailRespose response = new SmsAndMailRespose();
        response.setRows(list);
        response.setTotal(list.size());
        response.setRecords(list.size());
        response.setPage(page);
        model.addAttribute("list", list);
        return response;
    }

    @RequestMapping(params = "searchTemplate", method = RequestMethod.POST)
    public @ResponseBody List<SMSAndEmailInterfaceDTO> getTemplateList(@RequestParam("deptId") final Long deptId,
            @RequestParam("serviceId") final Long serviceId, @RequestParam("eventId") final Long eventId,
            @RequestParam("messageType") final String messageType, @RequestParam("alertType") final String alertType,
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        list.clear();

        final SMSAndEmailModel model = getModel();

        final SMSAndEmailInterface entity = model.getEntity();
        entity.getDpDeptid().setDpDeptid(deptId);
        entity.getServiceId().setServiceId(serviceId);
        entity.getSmfid().setSmfid(eventId);
        entity.getSmsAndmailTemplate().setMessageType(messageType);
        entity.setAlertType(alertType);
        final List<SMSAndEmailInterfaceDTO> master = ismsAndEmailService.searchTemplateFromDB(entity);
        if (master != null) {
            list.addAll(master);
        }

        return list;

    }

    @RequestMapping(params = "formForUpdate", method = RequestMethod.POST)
    public ModelAndView formForUpdate(@RequestParam("smsMailId") final Long smsEmailId, @RequestParam("mode") final String mode,
            final HttpServletRequest httpServletRequest, final Model model) {
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final SMSAndEmailInterface smsEmailTemplate = ismsAndEmailService.getTemplate(smsEmailId, orgId);
        if (smsEmailTemplate.getServiceId() == null) {
        	
        	smsEmailTemplate.setServiceId(new PortalService());
            smsEmailTemplate.getServiceId().setServiceId(null);
        }

        getModel().setEntity(smsEmailTemplate);

        final List<String> attributeList = getAttributeList();
        getModel().setAttributeList(attributeList);
        final List<LookUp> messageType = CommonMasterUtility.getListLookup(PrefixConstants.Prefix.SMT,
                UserSession.getCurrent().getOrganisation());
        getModel().setMessageLookUp(messageType);
        model.addAttribute(MainetConstants.MESSAGE_TYPE, messageType);
        return new ModelAndView(MainetConstants.SMS_EMAIL_EDIT, MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(params = "complete", method = RequestMethod.GET)
    public ModelAndView Resubmission(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        list.clear();
        return new ModelAndView(MainetConstants.SMSEMAIL, MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(params = "form", method = RequestMethod.GET)
    public ModelAndView createTemplate(final HttpServletRequest httpServletRequest, final Model model) {
        sessionCleanup(httpServletRequest);

        final List<String> attributeList = getAttributeList();
        getModel().setAttributeList(attributeList);
        final List<LookUp> messageType = CommonMasterUtility.getListLookup(PrefixConstants.Prefix.SMT,
                UserSession.getCurrent().getOrganisation());
        getModel().setMessageLookUp(messageType);
        return new ModelAndView(MainetConstants.SMS_EMAIL_ADD, MainetConstants.FORM_NAME, getModel());
    }

    @SuppressWarnings("rawtypes")
	private List<String> getAttributeList() {
        final List<String> attributeList = new ArrayList<>();
        final SMSAndEmailDTO attributes = new SMSAndEmailDTO();
        final Class cls = attributes.getClass();
        final Field[] fields = cls.getDeclaredFields();
        for (final Field field : fields) {
            attributeList.add(field.getName().toString());
        }
        return attributeList;
    }

    @RequestMapping(params = "deleteData", method = RequestMethod.POST)
    public @ResponseBody String deleteTemplate(@RequestParam("seId") final Long seId,
            final HttpServletRequest httpServletRequest) {
        String msg = MainetConstants.COMMON_STATUS.FAIL;
        ismsAndEmailService.deleteTemplate(seId);
        msg = MainetConstants.COMMON_STATUS.SUCCESS;
        return msg;

    }

    @RequestMapping(method = RequestMethod.POST, params = "search")
    public String searchApplicationForms(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);

        final SMSAndEmailModel model = getModel();

        if ((model.getModeType() != null) && !model.getModeType().isEmpty()) {
            model.setEditModeType(model.getModeType());
        }

        final SMSAndEmailInterface entity = model.getEntity();
        if ((model.getSerid() != null) && !model.getSerid().isEmpty()) {
            model.setServiceID(new Long(model.getSerid().replace("'", MainetConstants.BLANK)));
        }
        if ((model.getDeptid() != null) && !model.getDeptid().isEmpty()) {
            model.setDeptID(new Long(model.getDeptid().replace("'", MainetConstants.BLANK)));
        }

        try {
            if (model.getEntity().getDpDeptid() != null) {
                entity.getDpDeptid().setDpDeptid(model.getDeptID());
            }
            if (model.getEntity().getServiceId() != null) {
                entity.getServiceId().setServiceId(model.getServiceID());
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        if ((model.getEntity().getServiceId().getServiceId() == 0) || (model.getEntity().getDpDeptid().getDpDeptid() == 0)
                || model.getEntity().getSmsAndmailTemplate().getMessageType().equals("0")) {
            model.addValidationError(ApplicationSession.getInstance().getMessage("sms.pleaseselect"));
        } else {
            final SMSAndEmailInterface master = ismsAndEmailService.getTemplateFromDB(entity);

            if (master != null) {
                model.setEntity(master);
            } else {
                model.addValidationError(ApplicationSession.getInstance().getMessage("sms.notfound"));
                model.setEntity(new SMSAndEmailInterface());
                model.setDeptid(MainetConstants.BLANK);
            }

        }
        return getViewName();
    }

    @Override
    @RequestMapping(params = "saveform", method = RequestMethod.POST)
    public ModelAndView saveform(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        ModelAndView mv = null;
        final SMSAndEmailModel model = getModel();
        model.getEntity().setOrgId(UserSession.getCurrent().getOrganisation());
        final List<String> attributeList = getAttributeList();
        model.setAttributeList(attributeList);
        if ((model.getSelectedMode() != null) && model.getSelectedMode().equalsIgnoreCase(MainetConstants.MENU.A)) {
            model.getEntity().setSelectedMode(model.getSelectedMode());
            model.validateBean(model.getEntity(), SMSAndEmailTempalteValidator.class);
            mv = new ModelAndView(MainetConstants.SMS_EMAIL_ADD, MainetConstants.FORM_NAME, model);
            final BindingResult bindingResult = getModel().getBindingResult();

            if (bindingResult != null) {
                mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
            }

            if (model.hasValidationErrors()) {
                return mv;
            } else if (!model.checkExistance()) {
            	boolean saveOrUpdateFlag = model.saveOrUpdateForm();
				if (saveOrUpdateFlag == false && model.hasValidationErrors()) {
					return mv;
				}

                if (saveOrUpdateFlag) {
                    return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));
                } else {
                    mv = new ModelAndView(MainetConstants.SMS_EMAIL_ADD, MainetConstants.FORM_NAME, getModel());
                }
            }
        } else {
            final SMSAndEmailInterface smsEmailTemplate = ismsAndEmailService.getTemplate(getModel().getEntity().getSeId(),
                    UserSession.getCurrent().getOrganisation().getOrgid());

            smsEmailTemplate.setAlertType(getModel().getEntity().getAlertType());
            smsEmailTemplate.setSmsAndmailTemplate(getModel().getEntity().getSmsAndmailTemplate());
            model.validateBean(smsEmailTemplate, SMSAndEmailTempalteValidator.class);
            mv = new ModelAndView(MainetConstants.SMS_EMAIL_EDIT, MainetConstants.FORM_NAME, model);
            final BindingResult bindingResult = getModel().getBindingResult();

            if (bindingResult != null) {
                mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
            }
            if (model.hasValidationErrors()) {
                return mv;

            } else {
                if (model.saveOrUpdateForm()) {
                    return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));
                } else {
                    mv = new ModelAndView(MainetConstants.SMS_EMAIL_EDIT, MainetConstants.FORM_NAME, model);
                }
            }
        }
        return mv;
    }

}
