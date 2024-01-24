package com.abm.mainet.cms.ui.controller;

import java.io.Serializable;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.abm.mainet.cms.ui.model.AdminQuickLinkFormModel;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;

/**
 * @author swapnil.shirke
 * @see This Controller for add edit delete operation for Admin Quick Links Section
 */
@Controller
@RequestMapping("/AdminQuickLinkForm.html")
public class AdminQuickLinkFormController extends AbstractEntryFormController<AdminQuickLinkFormModel> implements Serializable {
    private static final long serialVersionUID = -6568582176124628315L;

}
