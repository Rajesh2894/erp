package com.abm.mainet.cms.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import com.abm.mainet.cms.domain.VIEWQuickLink;
import com.abm.mainet.cms.dto.VIEWQuickLinkDTO;
import com.abm.mainet.common.utility.AbstractServiceMapper;

@Component
public class ViewQuickLinkMapper extends AbstractServiceMapper{
	
	private ModelMapper modelMapper;

	public ModelMapper getModelMapper() {
		return modelMapper;
	}

	public ViewQuickLinkMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }
	
	public VIEWQuickLinkDTO mapEntityToDTO(VIEWQuickLink quicklinkList){
    	if (quicklinkList == null) {
            return null;
        }
    	return map(quicklinkList, VIEWQuickLinkDTO.class);
    }
}
