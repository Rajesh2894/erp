package com.abm.mainet.cms.ui.controller;

import java.io.Serializable;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.abm.mainet.cms.ui.model.AdminCommitteeFormModel;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;

/**
 * @author swapnil.shirke
 */
@Controller
@RequestMapping("/AdminCommitteeForm.html")
public class AdminCommitteeFormController extends AbstractEntryFormController<AdminCommitteeFormModel> implements Serializable {

    private static final long serialVersionUID = 8071963491416379141L;

}
