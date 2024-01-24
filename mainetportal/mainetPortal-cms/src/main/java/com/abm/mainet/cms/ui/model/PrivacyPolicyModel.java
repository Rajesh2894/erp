package com.abm.mainet.cms.ui.model;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
@Component
@Scope(value = "session")
public class PrivacyPolicyModel extends AbstractFormModel implements Serializable {
	private static final long serialVersionUID = -6153048961158163058L;
}
