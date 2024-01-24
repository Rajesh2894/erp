package com.abm.mainet.common.master.dao;

import java.util.List;
import java.util.Map;

import com.abm.mainet.common.domain.CommonHelpDocs;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.utility.LookUp;


public interface ICommonHelpDocsDAO {

    public abstract boolean persistHelpDocInDB(CommonHelpDocs chd, String mode);

    public abstract boolean checkExistingModuleName(String moduleName, Organisation organisation);

    public abstract CommonHelpDocs retrieveHelpDoc(String moduleName, Organisation organisation);

    public abstract List<CommonHelpDocs> retrieveHelpDoc(Organisation organisation);

    public abstract CommonHelpDocs getUploadedFileByDept(String moduleName, Organisation organisation);

    public abstract List<Long> getAllModuleName(Organisation organisation);

    public abstract boolean checkExistingModuleName(String moduleName, String deptCode, Organisation organisation);

    public abstract CommonHelpDocs findModuleName(long helpDocId, Organisation organisation,
            String deleteStatus);

    public abstract CommonHelpDocs getCommonHelpDocsById(long helpDocId, Organisation organisation);

    public abstract Map<String, String> getDepartmentCodes(int langId);

    List<LookUp> getServiceList(String deptcode);

	public abstract Map<String, String> getAllNodes(int languageId);

}