package com.abm.mainet.common.integration.dms.service;

import java.util.List;
import java.util.Map;

import com.abm.mainet.common.integration.dms.dto.DmsDocsMetadataDto;

public interface DepartmentalMetadetaService {
   
List<DmsDocsMetadataDto> getDepartmentalMetadeta(String colname,String Colvalue,Long orgId,Long deptId, Map<String, Object> argumentsMap);
}
