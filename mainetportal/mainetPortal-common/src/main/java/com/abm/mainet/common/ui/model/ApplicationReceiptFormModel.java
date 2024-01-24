package com.abm.mainet.common.ui.model;

import java.io.Serializable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session")
public class ApplicationReceiptFormModel extends AbstractModel implements Serializable {
	private static final long serialVersionUID = -6153048961158163058L;
	
}
