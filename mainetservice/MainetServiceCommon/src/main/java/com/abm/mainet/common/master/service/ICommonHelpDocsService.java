/**
 *
 */
package com.abm.mainet.common.master.service;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.domain.CommonHelpDocs;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.utility.LookUp;


/**
 * @author vinay.jangir
 *
 */
@Transactional(readOnly = true)
public interface ICommonHelpDocsService extends Serializable {
    public boolean saveHelpDoc(CommonHelpDocs helpDoc, String mode);

    public List<CommonHelpDocs> getHelpDocServicesNames(Organisation organisation);

    public CommonHelpDocs getHelpDoc(String moduleName, Organisation organisation);

    public CommonHelpDocs getUploadedFileByDept(String moduleName, Organisation organisation);

    public boolean checkExistingModuleName(String moduleName, Organisation organisation);

    public List<Long> getAllModuleName(Organisation organisation);

    public boolean checkExistingModuleName(String moduleName, String DeptCode, Organisation organisation);

    public boolean delete(long helpDocId, Organisation organisation);

    public CommonHelpDocs findModuleName(long rowId, Organisation organisation);

    public CommonHelpDocs getCommonHelpDocs(long helpDocId, Organisation organisation);

    public abstract Map<String, String> getDepartmentCodes(int langId);

    List<LookUp> getServiceList(String deptcode);

	public Map<String, String> getAllNodes(int languageId);


}
