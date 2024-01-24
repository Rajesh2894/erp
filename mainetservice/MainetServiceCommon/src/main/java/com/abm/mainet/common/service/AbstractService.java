package com.abm.mainet.common.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.abm.mainet.cfc.scrutiny.dto.ScrutinyLableValueDTO;
import com.abm.mainet.cfc.scrutiny.service.ScrutinyService;
import com.abm.mainet.common.exception.FrameworkException;

@Service
public abstract class AbstractService {

    @Resource
    private ScrutinyService scrutinyService;

    public boolean saveScrutinyValue(final ScrutinyLableValueDTO lableValueDTO) throws FrameworkException {

        return scrutinyService.saveScrutinyValueBylabelId(lableValueDTO);
    }

}
