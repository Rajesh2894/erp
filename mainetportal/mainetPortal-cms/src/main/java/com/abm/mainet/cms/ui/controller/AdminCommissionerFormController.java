package com.abm.mainet.cms.ui.controller;

import java.io.Serializable;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.abm.mainet.cms.ui.model.AdminCommissionerFormModel;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;

/**
 * @author swapnil.shirke
 */
@Controller
@RequestMapping("/AdminCommissionerForm.html")
public class AdminCommissionerFormController extends AbstractEntryFormController<AdminCommissionerFormModel>
        implements Serializable {
    private static final long serialVersionUID = -2412035862638057592L;
}
