package com.abm.mainet.intranet.ui.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.ui.model.AbstractFormModel;

@Component
@Scope(value=WebApplicationContext.SCOPE_SESSION)
public class DownloadIntranetDocModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
