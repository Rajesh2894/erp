package com.abm.mainet.cms.ui.controller;

import java.io.Serializable;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.abm.mainet.cms.ui.model.AdminStandingCommitteeFormModel;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;

/**
 * @author swapnil.shirke
 */
@Controller
@RequestMapping("/AdminStandingCommitteeForm.html")
public class AdminStandingCommitteeFormController extends AbstractEntryFormController<AdminStandingCommitteeFormModel>
        implements Serializable {

    private static final long serialVersionUID = -2736995190005978948L;

}
