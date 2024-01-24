package com.abm.mainet.common.model.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

/**
 *
 * @author Vivek.Kumar
 * @since 05-Feb-2016
 */
@Component
public class CommonModelMapper extends AbstractModelMapper {

    private ModelMapper modelMapper;

    @Override
    protected ModelMapper getModelMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

}
