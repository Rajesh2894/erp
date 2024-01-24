/**
 *
 */
package com.abm.mainet.cfc.scrutiny.service;

import java.util.List;
import java.util.Map;

import com.abm.mainet.cfc.scrutiny.dto.ScrutinyLabelDTO;
import com.abm.mainet.cfc.scrutiny.dto.ScrutinyLableValueDTO;
import com.abm.mainet.cfc.scrutiny.dto.ViewCFCScrutinyLabelValue;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.utility.UserSession;

/**
 *
 * @author umashanker.kanaujiya
 *
 */
public interface ScrutinyService {

    ScrutinyLabelDTO populateScrutinyLabelData(long applicationId, long empId, long gmId, long orgId, long serviceId, int langId, String refNo,Long currentLevel);

    boolean saveScrutinyValueBylabelId(ScrutinyLableValueDTO lableValueDTO);

    boolean saveCompleteScrutinyLabel(List<ViewCFCScrutinyLabelValue> list, UserSession userSession,
            ScrutinyLabelDTO scrutinyLabelDTO, boolean updateFlag, Long taskId, List<Long> attachmentId, String wokflowDecision, String remark);

    boolean saveAllScrutinyDoc(List<CFCAttachment> scrutinyDocs);

    String getValueByLabelQuery(String query);

	Map<String, Long> getApplicationDetails(Long applicatioId);
	Boolean checkLoiGeneratedOrNot(ScrutinyLabelDTO dto,Long desgId,List<ViewCFCScrutinyLabelValue> labellIST);
	
    String getAppDetailByAppId(Long applicationId);
    boolean updateStatusFlagByRefId(String refId, Long orgId, String deptCode,Long empId);

	Boolean checkMeteredOrNot(ScrutinyLabelDTO dto, Long desgId, List<ViewCFCScrutinyLabelValue> labelList);

	Map<String, Long> getWaterApplicationDetails(Long applicatioId);

	List<CFCAttachment> getDocumentUploadedListByGroupId(String groupValue, Long applicationId);

	String getGroupValue(Long levels, Long slLabelId, Long smServiceId, Long orgId);
    
}
