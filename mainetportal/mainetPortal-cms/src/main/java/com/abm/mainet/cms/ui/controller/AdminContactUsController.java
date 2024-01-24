package com.abm.mainet.cms.ui.controller;

import java.io.Serializable;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.abm.mainet.cms.ui.model.AdminContactUsModel;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;

@Controller
@RequestMapping("/AdminContactUs.html")
public class AdminContactUsController extends AbstractEntryFormController<AdminContactUsModel> implements Serializable {

    private static final long serialVersionUID = -2412035862638057592L;

}
