package com.abm.mainet.socialsecurity.ui.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.socialsecurity.service.ConfigurationMasterService;
import com.abm.mainet.socialsecurity.ui.dto.ConfigurationMasterDto;
import com.abm.mainet.socialsecurity.ui.model.ConfigurationMasterModel;
/**
 * @author rahul.chaubey
 * @since 11 Jan 2020
 */
@Component
public class ConfigurationMasterValidator extends BaseEntityValidator<ConfigurationMasterModel> {

	@Autowired
	ConfigurationMasterService configurationMasterService;

	@Override
	protected void performValidations(ConfigurationMasterModel model,
			EntityValidationContext<ConfigurationMasterModel> entityValidationContext) {
		final ApplicationSession session = ApplicationSession.getInstance();
		
		if (model.getConfigurtionMasterDto().getSchemeMstId() != null) {
			/*
			 * ConfigurationMasterDto configurationMasterDto =
			 * configurationMasterService.findSchemeById(
			 * model.getConfigurtionMasterDto().getConfigurationId(),
			 * model.getConfigurtionMasterDto().getOrgId()); if (configurationMasterDto !=
			 * null) { entityValidationContext.addOptionConstraint(session.getMessage(
			 * "config.mst.err")); }
			 */
			
			
			List<ConfigurationMasterDto> configurationMasterDto	= configurationMasterService.getData(null, model.getConfigurtionMasterDto().getSchemeMstId(), model.getConfigurtionMasterDto().getOrgId());
			configurationMasterDto.size();
			if(configurationMasterDto.size() > 0 )
			{
				entityValidationContext.addOptionConstraint(session.getMessage("config.mst.err"));
			}
		}

	}

}
