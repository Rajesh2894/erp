/**
 *
 */
package com.abm.mainet.common.formbuilder.service;

import java.util.List;

import com.abm.mainet.cfc.scrutiny.dto.ScrutinyLabelDTO;
import com.abm.mainet.cfc.scrutiny.dto.ViewCFCScrutinyLabelValue;
import com.abm.mainet.common.formbuilder.domain.FormBuilderValueEntity;
import com.abm.mainet.common.formbuilder.dto.FormBuilderValueDTO;
import com.abm.mainet.common.utility.UserSession;

/**
 *
 * @author umashanker.kanaujiya
 *
 */
public interface IFormBuilderUtilityService {

    ScrutinyLabelDTO populateFormBuilderLabelData(Long applicationId, Long empId, Long gmId, Long orgId, String shortDesc,
            int langId);

    boolean saveCompleteFormBuilderLabel(List<ViewCFCScrutinyLabelValue> list, UserSession userSession,
            ScrutinyLabelDTO scrutinyLabelDTO, boolean updateFlag, Long taskId, List<Long> attachmentId);

    List<FormBuilderValueDTO> searchFormValue(Long orgId, String serviceCode);

	boolean saveFormBuilder(List<FormBuilderValueEntity> list);

}
