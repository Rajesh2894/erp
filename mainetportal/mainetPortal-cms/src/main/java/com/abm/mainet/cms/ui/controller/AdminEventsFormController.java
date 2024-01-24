package com.abm.mainet.cms.ui.controller;

import java.io.Serializable;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.abm.mainet.cms.ui.model.AdminEventsFormModel;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;

/**
 * @author swapnil.shirke
 * @see This Controller for add edit delete operation for Admin Events Section ({@link : News}
 */
@Controller
@RequestMapping("/AdminEventsForm.html")
public class AdminEventsFormController extends AbstractEntryFormController<AdminEventsFormModel> implements Serializable {
    private static final long serialVersionUID = 1783105307588892004L;
}
