/**
 *
 */
package com.abm.mainet.common.master.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.common.utility.UserSession;

@Repository
public class DesignationDAO extends AbstractDAO<Designation> implements Serializable, IDesignationDAO {
    private static final long serialVersionUID = 8399989172682533131L;

    @Override
    public void saveOrUpdate(final Designation designation) {

        entityManager.persist(designation);
    }

    @Override
    public String getDesignationName(final long desgId) {

        final Query query = createQuery("Select dg.dsgname from Designation dg where dg.organisation = ?1 and dg.dsgid = ?2 ");
        query.setParameter(1, UserSession.getCurrent().getOrganisation());
        query.setParameter(2, desgId);

        @SuppressWarnings("unchecked")
        final List<String> designationNameList = query.getResultList();
        if ((designationNameList == null) || designationNameList.isEmpty()) {
            return null;
        } else {
            return designationNameList.get(0);
        }
    }

    @Override
    public List<Designation> getAllDesignationByDesgName(final String desgName) {

        final Query query = createQuery(
                "Select dg from Designation dg where UPPER(dg.dsgname) = UPPER( ?1 ) and dg.organisation = ?2 ");
        query.setParameter(1, desgName);
        query.setParameter(2, UserSession.getCurrent().getOrganisation());

        @SuppressWarnings("unchecked")
        final List<Designation> designationList = query.getResultList();
        return designationList;

    }

}
