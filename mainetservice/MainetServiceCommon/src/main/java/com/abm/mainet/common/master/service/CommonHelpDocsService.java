/**
 *
 */
package com.abm.mainet.common.master.service;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;

import com.abm.mainet.common.domain.CommonHelpDocs;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.master.dao.ICommonHelpDocsDAO;
import com.abm.mainet.common.utility.LookUp;


/**
 * @author vinay.jangir
 *
 */
@Service
public class CommonHelpDocsService implements ICommonHelpDocsService {

    private static final long serialVersionUID = -134434334109249851L;

    @Autowired
    private ICommonHelpDocsDAO helpDocDAO;

    @Override
    @Transactional
    public boolean saveHelpDoc(final CommonHelpDocs helpDoc, String mode) {
        return helpDocDAO.persistHelpDocInDB(helpDoc,mode);
    }

    @Override
    public boolean checkExistingModuleName(final String moduleName, final Organisation organisation) {

        return helpDocDAO.checkExistingModuleName(moduleName, organisation);
    }

    @Override
    public List<CommonHelpDocs> getHelpDocServicesNames(final Organisation organisation) {

        return helpDocDAO.retrieveHelpDoc(organisation);
    }

    @Override
    @Transactional
    public CommonHelpDocs getUploadedFileByDept(String moduleName, Organisation organisation) {

        return helpDocDAO.getUploadedFileByDept(moduleName, organisation);
    }

    @Override
    public CommonHelpDocs getHelpDoc(final String moduleName, final Organisation organisation) {
        return helpDocDAO.retrieveHelpDoc(moduleName, organisation);
    }

    @Override
    public List<Long> getAllModuleName(final Organisation organisation) {

        return helpDocDAO.getAllModuleName(organisation);
    }

    @Override
    public boolean checkExistingModuleName(final String moduleName, final String deptCode, final Organisation organisation) {

        return helpDocDAO.checkExistingModuleName(moduleName, deptCode, organisation);
    }

    @Transactional
    @Override
    public boolean delete(final long rowId, final Organisation organisation) {
        final CommonHelpDocs doc = helpDocDAO.getCommonHelpDocsById(rowId, organisation);
        if (doc != null) {
            doc.setIsDeleted(MainetConstants.IsDeleted.DELETE);
            doc.updateAuditFields();
            return helpDocDAO.persistHelpDocInDB(doc,null);
        } else {
            return false;
        }
    }

    @Override
    public CommonHelpDocs findModuleName(final long helpDocId, final Organisation organisation) {

        return helpDocDAO.findModuleName(helpDocId, organisation, MainetConstants.IsDeleted.NOT_DELETE);
    }

    @Override
    public CommonHelpDocs getCommonHelpDocs(final long helpDocId, final Organisation organisation) {
        return helpDocDAO.getCommonHelpDocsById(helpDocId, organisation);
    }

    @Override
    public Map<String, String> getDepartmentCodes(final int langId) {

        return helpDocDAO.getDepartmentCodes(langId);
    }

    @Override
    public List<LookUp> getServiceList(final String deptcode) {

        return helpDocDAO.getServiceList(deptcode);
    }

	@Override
	@Transactional
	public Map<String, String> getAllNodes(int languageId) {
		return helpDocDAO.getAllNodes(languageId);
	}

}
