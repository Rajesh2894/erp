/**
 *
 */
package com.abm.mainet.common.formbuilder.dao;

import java.util.List;

import com.abm.mainet.common.domain.GroupMaster;

/**
 * @author Lalit.Prusti
 *
 */
public interface IFormBuilderDataDAO {

    GroupMaster getDesignationName(Long slDsgid);

    List<Object> getfindAllFormLabelValueList(Long orgId, String smShortDesc, Long scrutinyId, Long applicationId);

    List<Object> searchFormValue(Long orgId, String serviceCode);

}
