package com.abm.mainet.common.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.util.LookUp;

@SuppressWarnings("unchecked")
@Repository
public class DesignationDAO extends AbstractDAO<Designation> implements Serializable, IDesignationDAO {

    private static final long serialVersionUID = 8399989172682533131L;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IDesignationDAO#saveOrUpdate(com.abm.mainet.domain.core.Designation)
     */
    @Override
    public void saveOrUpdate(final Designation designation) {

        entityManager.persist(designation);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IDesignationDAO#getDesignation(long)
     */
    @Override
    public List<Designation> getDesignation(final long deptId) {
        final List<Designation> designations = new ArrayList<>(0);

        final String hql = "from DeskDesgTree t,Department d, Designation s where d.dpDeptid=?1 and t.dsgid=s.dsgid  and t.dpDeptid=d.dpDeptid and t.dpDeptid is not null ";

        /* JPA Query Start */
        final Query query = createQuery(hql);
        query.setParameter(1, deptId);
        /* JPA Query End */

        final List<Object[]> objects = query.getResultList();

        if (objects.size() > 0) {
            for (final Object[] object : objects) {

                designations.add((Designation) object[2]);
            }
        }

        return designations;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IDesignationDAO#getDesgName(long)
     */
    @Override
    public Designation getDesgName(final long desgId) {

        final Query query = createQuery("Select dg from Designation dg where dg.dsgid = ?1 ");
        query.setParameter(1, desgId);

        final List<Designation> designationList = query.getResultList();
        if ((designationList == null) || designationList.isEmpty()) {
            return null;
        } else {
            return designationList.get(0);
        }

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IDesignationDAO#getDesignationbyDeptIdAndDesgId(long, long)
     */
    @Override
    public List<Designation> getDesignationbyDeptIdAndDesgId(final long deptId, final long desgId) {

        final Query query = createQuery("Select dg from Designation dg where  dg.dsgid = ?1 ");
        query.setParameter(1, desgId);

        final List<Designation> designationList = query.getResultList();
        return designationList;

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IDesignationDAO#getDesignationName(long)
     */
    @Override
    public String getDesignationName(final long desgId) {

        /* JPA Query Start */
        final Query query = createQuery("Select dg.dsgname from Designation dg where dg.dsgid = ?2 ");
        query.setParameter(2, desgId);

        final List<String> designationNameList = query.getResultList();
        if ((designationNameList == null) || designationNameList.isEmpty()) {
            return null;
        } else {
            return designationNameList.get(0);
        }
    }

    @Override
    public List<Designation> getAllDesignationByDesgName(final String desgName) {

        final Query query = createQuery("Select dg from Designation dg where UPPER(dg.dsgname) = UPPER( ?1 ) ");
        query.setParameter(1, desgName);

        final List<Designation> designationList = query.getResultList();
        return designationList;

    }

    @Override
    public List<LookUp> getDesignationByOrg(final Organisation organisation) {

        final List<LookUp> lookUps = new ArrayList<>(0);
        final Query query = createQuery("Select dg from Designation dg ");

        final List<Designation> list = query.getResultList();
        for (final Designation depLoc : list) {
            final LookUp lookUp = new LookUp();
            lookUp.setLookUpId(depLoc.getDsgid());
            lookUp.setDescLangFirst(depLoc.getDsgname());
            lookUp.setDescLangSecond(depLoc.getDsgname());
            lookUp.setLookUpDesc(depLoc.getDsgname());
            lookUp.setLookUpCode(depLoc.getDsgshortname());

            lookUps.add(lookUp);
        }
        final List<LookUp> lookUpList = lookUps;
        return lookUpList;
    }
    
    @Override
	public Designation findByShortName(final String dsgshortname) {
		Query query = this.createQuery("select d from Designation d where d.dsgshortname= ?1 ");
		query.setParameter(1,dsgshortname);
		@SuppressWarnings("unchecked")
		List<Designation> designationList = query.getResultList();
		if(designationList == null || designationList.isEmpty()){
			return null;
		}else
		return designationList.get(0);
	}




	@Override
	public Designation getDesgByName(String dsgName) {
		Query query=this.createQuery("Select dg from Designation dg where dg.dsgname = ?1 ");
		query.setParameter(1, dsgName);
		
		@SuppressWarnings("unchecked")
		List<Designation> designationList = query.getResultList();
		if(designationList == null || designationList.isEmpty()){
			return null;
		}else
		return designationList.get(0);
	}
	
	@Override
	public Designation create(Designation designation){
		this.entityManager.persist(designation);
		return designation;
	}
}
