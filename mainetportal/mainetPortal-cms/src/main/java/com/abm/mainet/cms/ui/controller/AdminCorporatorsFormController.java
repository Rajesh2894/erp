package com.abm.mainet.cms.ui.controller;

import java.io.Serializable;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.abm.mainet.cms.ui.model.AdminCorporatorsFormModel;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;

/**
 * @author swapnil.shirke
 */
@Controller
@RequestMapping("/AdminCorporatorsForm.html")
public class AdminCorporatorsFormController extends AbstractEntryFormController<AdminCorporatorsFormModel>
        implements Serializable {
    private static final long serialVersionUID = -2412035862638057592L;
}
