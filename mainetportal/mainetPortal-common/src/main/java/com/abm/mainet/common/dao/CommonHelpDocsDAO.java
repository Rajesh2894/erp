package com.abm.mainet.common.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.CommonHelpDocs;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;

/**
 * @author vinay.jangir
 *
 */

@Repository
public class CommonHelpDocsDAO extends AbstractDAO<CommonHelpDocs> implements ICommonHelpDocsDAO {
    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.ICommonHelpDocsDAO#persistHelpDocInDB(com.abm.mainet.eip.domain.core.CommonHelpDocs)
     */
    @Override
    public boolean persistHelpDocInDB(final CommonHelpDocs chd,String mode) {
    	boolean status=false;
       if(mode!=null && mode.equalsIgnoreCase("A"))
       {
    	   this.entityManager.persist(chd);
    	   status=true;
       }
       else
       {
    	   this.entityManager.merge(chd);
    	   status=true;   
       }
	return status;
       
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.ICommonHelpDocsDAO#checkExistingModuleName(java.lang.String)
     */
    @Override
    public boolean checkExistingModuleName(final String moduleName, final Organisation organisation) {

        final Query query = createQuery(
                "select c from CommonHelpDocs c where c.orgId = ?1 and c.isDeleted = ?2 and UPPER(c.moduleName) like CONCAT('%',UPPER(?3),'%')");
        query.setParameter(1, organisation);
        query.setParameter(2, MainetConstants.IsDeleted.NOT_DELETE);
        query.setParameter(3, moduleName);
        @SuppressWarnings("unchecked")
        final List<CommonHelpDocs> list = query.getResultList();

        if (list.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.ICommonHelpDocsDAO#retrieveHelpDoc(java.lang.String)
     */
    @Override
    public CommonHelpDocs retrieveHelpDoc(final String moduleName, final Organisation organisation) {

        final Query query = createQuery(
                "select c from CommonHelpDocs c where c.orgId = ?1 and c.isDeleted = ?2 and c.moduleName like CONCAT('%',?3,'%')");
        query.setParameter(1, organisation);
        query.setParameter(2, MainetConstants.IsDeleted.NOT_DELETE);
        query.setParameter(3, moduleName);
        CommonHelpDocs uniqueRecord = null;

        @SuppressWarnings("unchecked")
        final List<CommonHelpDocs> helpDocsList = query.getResultList();

        if ((helpDocsList != null) && !helpDocsList.isEmpty()) {
            uniqueRecord = helpDocsList.get(0);
        } else {
            uniqueRecord = new CommonHelpDocs();
        }
        return uniqueRecord;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.ICommonHelpDocsDAO#retrieveHelpDoc()
     */
    @Override
    public List<CommonHelpDocs> retrieveHelpDoc(final Organisation organisation) {

        final Query query = createQuery("select c from CommonHelpDocs c where c.isDeleted = ?1");
        query.setParameter(1, organisation);
        @SuppressWarnings("unchecked")
        final List<CommonHelpDocs> helpDocsList = query.getResultList();

        return helpDocsList;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.ICommonHelpDocsDAO#getUploadedFileByDept(java.lang.String)
     */
    @Override
    public CommonHelpDocs getUploadedFileByDept(String moduleName, Organisation organisation) {

        CommonHelpDocs commonHelpDoc = null;
        final Query query = createQuery(
                "select c from CommonHelpDocs c where c.orgId = ?1 and c.moduleName = ?2 and c.isDeleted = ?3");
        query.setParameter(1, organisation);
        query.setParameter(2, moduleName);
       // query.setParameter(3, deptCode);
        query.setParameter(3, MainetConstants.IsDeleted.NOT_DELETE);
        @SuppressWarnings("unchecked")
        final List<CommonHelpDocs> docs = query.getResultList();
        if ((docs != null) && !docs.isEmpty()) {
            commonHelpDoc = docs.get(0);
        }
        return commonHelpDoc;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.ICommonHelpDocsDAO#getAllModuleName(com.abm.mainet.domain.core.Organisation)
     */
    @Override
    public List<Long> getAllModuleName(final Organisation organisation) {

        final Query query = createQuery("select c from CommonHelpDocs c where c.orgId = ?1 and c.isDeleted = ?2 ");
        query.setParameter(1, organisation);
        query.setParameter(2, MainetConstants.IsDeleted.NOT_DELETE);
        @SuppressWarnings("unchecked")
        final List<Long> helpDocsList = query.getResultList();
        return helpDocsList;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IHelpDocDAO#checkExistingModuleName(java.lang.String)
     */
    @Override
    public boolean checkExistingModuleName(final String moduleName, final String deptCode, final Organisation organisation) {

        final Query query = createQuery(
                "select c from CommonHelpDocs c where c.orgId = ?1 and c.isDeleted = ?2 and c.moduleName = ?3 and c.deptCode = ?4");
        query.setParameter(1, organisation);
        query.setParameter(2, MainetConstants.IsDeleted.NOT_DELETE);
        query.setParameter(3, moduleName);
        query.setParameter(4, deptCode);

        @SuppressWarnings("unchecked")
        final List<CommonHelpDocs> list = query.getResultList();

        if (list.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IHelpDocDAO#findModuleName(long, com.abm.mainet.domain.core.Organisation, java.lang.String)
     */
    @Override
    public CommonHelpDocs findModuleName(final long helpDocId, final Organisation organisation, final String deleteStatus) {

        final Query query = createQuery(
                "select c from CommonHelpDocs c where c.orgId = ?1 and c.helpDocId = ?2 and c.isDeleted = ?3 ");
        query.setParameter(1, organisation);
        query.setParameter(2, helpDocId);
        query.setParameter(3, deleteStatus);
        @SuppressWarnings("unchecked")
        final List<CommonHelpDocs> commonHelpDocs = query.getResultList();

        if ((commonHelpDocs == null) || commonHelpDocs.isEmpty()) {
            return null;
        } else {
            return commonHelpDocs.get(0);
        }

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IHelpDocDAO#getCommonHelpDocsById(long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public CommonHelpDocs getCommonHelpDocsById(final long helpDocId, final Organisation organisation) {
        final Query query = createQuery(
                "select c from CommonHelpDocs c where c.orgId = ?1 and c.helpDocId = ?2 and c.isDeleted = ?3 ");
        query.setParameter(1, organisation);
        query.setParameter(2, helpDocId);
        query.setParameter(3, MainetConstants.IsDeleted.NOT_DELETE);
        final List<CommonHelpDocs> commonHelpDocs = query.getResultList();
        if ((commonHelpDocs == null) || commonHelpDocs.isEmpty()) {
            return null;
        } else {
            return commonHelpDocs.get(0);
        }
    }

    @Override
    public Map<String, String> getDepartmentCodes(final int langId) {
        final Map<String, String> departmentFinalList = new LinkedHashMap<>();
        final Query query = createQuery(
                "select d.dpDeptid,d.dpDeptdesc,d.dpNameMar,d.dpDeptcode from Department d where d.status = ?1 ");
        query.setParameter(1, MainetConstants.IsLookUp.ACTIVE);
        @SuppressWarnings("unchecked")
        final List<Object[]> deptList = query.getResultList();
        for (final Object[] dept : deptList) {
            try {
                if (langId == MainetConstants.ENGLISH) {
                    departmentFinalList.put(dept[3].toString(),
                            dept[1].toString() + MainetConstants.operator.ARROW + dept[3].toString());
                }
                if (langId == MainetConstants.MARATHI) {
                    departmentFinalList.put(dept[3].toString(),
                            dept[2].toString() + MainetConstants.operator.ARROW + dept[3].toString());
                }
            } catch (final Exception e) {
                throw new FrameworkException(e);
            }
        }

        return departmentFinalList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<LookUp> getServiceList(final String deptId) {

        final Query query = createQuery(
                "SELECT SMF.smfid,SMF.smfaction,SMF.smfname,SMF.smfname_mar FROM SystemModuleFunction SMF WHERE SMF.smfid IN"
                        + " (SELECT PS.psmSmfid from PortalService PS  where  "
                        + "  PS.psmDpDeptid IN (SELECT D.dpDeptid FROM Department D WHERE D.dpDeptcode=?1))");

        query.setParameter(1, deptId);

        final List<Object[]> serviceList = query.getResultList();

        final List<LookUp> finalLookUps = new ArrayList<>();
        for (final Object[] service : serviceList) {
            final LookUp lookUp = new LookUp();

            lookUp.setLookUpId(new Long(service[0].toString()));
            if ((service[1] != null) && (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH)) {
                lookUp.setLookUpCode(service[1].toString());
            } else if ((service[1] != null) && (UserSession.getCurrent().getLanguageId() == MainetConstants.MARATHI)) {
                lookUp.setLookUpCode(service[1].toString());
            }
            finalLookUps.add(lookUp);

        }

        return finalLookUps;
    }

	@Override
	public Map<String, String> getAllNodes(int languageId) {
		 final Query query = createQuery(
	                "SELECT SMF.smfid,SMF.smfaction,SMF.smfname,SMF.smfname_mar FROM SystemModuleFunction SMF WHERE SMF.smfaction!=?1 and SMF.smfaction not like '%?%' order by SMF.smfname asc");

	        query.setParameter(1, "#");

	        final List<Object[]> nodeList = query.getResultList();

	        final Map<String, String> departmentFinalList = new LinkedHashMap<>();
	        for (final Object[] node : nodeList) {
	        	if ((node[1] != null) && (node[2] != null) && (languageId == MainetConstants.ENGLISH)) {
	        	departmentFinalList.put(node[1].toString(), node[2].toString());
	        }
	        	if ((node[1] != null) && (node[3] != null) && (languageId == MainetConstants.MARATHI)) {
		        	departmentFinalList.put(node[1].toString(), node[3].toString());
		        }
	        }
	        return departmentFinalList;
	}
}
