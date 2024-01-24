package com.abm.mainet.water.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.water.constant.QueryConstants;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.domain.WaterExceptionGapEntity;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.WaterExceptionGapDTO;

/**
 * @author Rahul.Yadav
 *
 */
@Repository
public class WaterExceptionalGapDAOImpl extends AbstractDAO<Long> implements WaterExceptionalGapDAO {

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.dao.WaterExceptionalGapDAO#getWaterDataForExceptionGap(com.abm.mainet.water.dto.TbCsmrInfoDTO,
     * long)
     */
    @Override
    public List<TbKCsmrInfoMH> getWaterDataForExceptionGap(final TbCsmrInfoDTO waterDTO,
            final long billingFrequency, final Long finyearId, final String meterType) {

        final StringBuilder queryString = new StringBuilder(QueryConstants.Exceptional_Gap_Tool.Fetch_Add_Data);
        if ((waterDTO.getTrmGroup1() != null) && (waterDTO.getTrmGroup1() > 0)) {
            queryString.append(" and a.trmGroup1=:trmGroup1 ");
        }
        if ((waterDTO.getTrmGroup2() != null) && (waterDTO.getTrmGroup2() > 0)) {
            queryString.append(" and a.trmGroup2=:trmGroup2 ");
        }
        if ((waterDTO.getTrmGroup3() != null) && (waterDTO.getTrmGroup3() > 0)) {
            queryString.append(" and a.trmGroup3=:trmGroup3 ");
        }
        if ((waterDTO.getTrmGroup4() != null) && (waterDTO.getTrmGroup4() > 0)) {
            queryString.append(" and a.trmGroup4=:trmGroup4 ");
        }
        if ((waterDTO.getTrmGroup5() != null) && (waterDTO.getTrmGroup5() > 0)) {
            queryString.append(" and a.trmGroup5=:trmGroup5 ");
        }
        if ((waterDTO.getCsCcnsize() != null) && (waterDTO.getCsCcnsize() > 0)) {
            queryString.append(" and a.csCcnsize=:csCcnsize ");
        }
        if ((waterDTO.getCsMeteredccn() != null)
                && (waterDTO.getCsMeteredccn() > 0)) {
            queryString.append(" and a.csMeteredccn=:csMeteredccn ");
        }
        if (billingFrequency > 0d) {
            queryString.append(" and b.tbComparamDet.cpdId=:frequency ");
        }
        final Query query = entityManager.createQuery(queryString.toString());
        query.setParameter("orgId", waterDTO.getOrgId());
        query.setParameter("codDwzid1", (waterDTO.getCodDwzid1() != null ? waterDTO.getCodDwzid1() : 0l));
        query.setParameter("codDwzid2", (waterDTO.getCodDwzid2() != null ? waterDTO.getCodDwzid2() : 0l));
        query.setParameter("codDwzid3", (waterDTO.getCodDwzid3() != null ? waterDTO.getCodDwzid3() : 0l));
        query.setParameter("codDwzid4", (waterDTO.getCodDwzid4() != null ? waterDTO.getCodDwzid4() : 0l));
        query.setParameter("codDwzid5", (waterDTO.getCodDwzid5() != null ? waterDTO.getCodDwzid5() : 0l));
        if ((waterDTO.getTrmGroup1() != null) && (waterDTO.getTrmGroup1() > 0)) {
            query.setParameter("trmGroup1", waterDTO.getTrmGroup1());
        }
        if ((waterDTO.getTrmGroup2() != null) && (waterDTO.getTrmGroup2() > 0)) {
            query.setParameter("trmGroup2", waterDTO.getTrmGroup2());
        }
        if ((waterDTO.getTrmGroup3() != null) && (waterDTO.getTrmGroup3() > 0)) {
            query.setParameter("trmGroup3", waterDTO.getTrmGroup3());
        }
        if ((waterDTO.getTrmGroup4() != null) && (waterDTO.getTrmGroup4() > 0)) {
            query.setParameter("trmGroup4", waterDTO.getTrmGroup4());
        }
        if ((waterDTO.getTrmGroup5() != null) && (waterDTO.getTrmGroup5() > 0)) {
            query.setParameter("trmGroup5", waterDTO.getTrmGroup5());
        }
        if ((waterDTO.getCsCcnsize() != null) && (waterDTO.getCsCcnsize() > 0)) {
            query.setParameter("csCcnsize", waterDTO.getCsCcnsize());
        }
        if ((waterDTO.getCsMeteredccn() != null)
                && (waterDTO.getCsMeteredccn() > 0)) {
            query.setParameter("csMeteredccn", waterDTO.getCsMeteredccn());
        }
        if (billingFrequency > 0d) {
            query.setParameter("frequency", billingFrequency);
        }
        query.setParameter("finyearId", finyearId);
        query.setParameter("meterType", meterType);
        @SuppressWarnings("unchecked")
        final List<TbKCsmrInfoMH> result = query.getResultList();
        return result;
    }

    @Override
    public void saveAndUpdateExceptionalData(final WaterExceptionGapEntity entity, final String macAddress, final Long empId) {
        if ((entity.getMgapExgid() != null) && (entity.getMgapExgid() > 0d)) {
            entity.setLgIpMacUpd(macAddress);
            entity.setUpdatedBy(empId);
            entity.setUpdatedDate(new Date());
            entityManager.merge(entity);
        } else {
            entity.setLgIpMac(macAddress);
            entity.setCreatedBy(empId);
            entity.setLmodDate(new Date());
            entityManager.persist(entity);
        }

    }

    @Override
    public List<Object[]> fetchEditExceptionGapEntry(final WaterExceptionGapDTO editGap, final long meterType) {

        final StringBuilder queryString = new StringBuilder(
                "select w.csCcn,w.csName,w.csMname,w.csLname,e from TbKCsmrInfoMH w ,WaterExceptionGapEntity e where w.orgId=e.orgId and w.csIdn=e.csIdn "
                        + " and e.orgId=:orgId   "
                        + "  and e.mgapActive='Y' and w.csMeteredccn=:csMeteredccn ");

        if ((editGap.getCpdMtrstatus() != null) && (editGap.getCpdMtrstatus() > 0d)) {
            queryString.append(" and e.cpdMtrstatus=:cpdMtrstatus ");
        }

        if ((editGap.getCpdGap() != null) && (editGap.getCpdGap() > 0d)) {
            queryString.append(" and e.cpdGap=:cpdGap ");
        }

        if (editGap.getMgapFrom() != null) {
            queryString.append(" and e.mgapFrom >= :mgapFrom  ");
        }
        if (editGap.getMgapTo() != null) {
            queryString.append(" and e.mgapTo <= :mgapTo ");
        }

        final Query query = entityManager.createQuery(queryString.toString());
        query.setParameter("orgId", editGap.getOrgId());
        if ((editGap.getCpdMtrstatus() != null) && (editGap.getCpdMtrstatus() > 0d)) {
            query.setParameter("cpdMtrstatus", editGap.getCpdMtrstatus());
        }
        if ((editGap.getCpdGap() != null) && (editGap.getCpdGap() > 0d)) {
            query.setParameter("cpdGap", editGap.getCpdGap());
        }
        query.setParameter("csMeteredccn", meterType);
        if (editGap.getMgapFrom() != null) {
            query.setParameter("mgapFrom", editGap.getMgapFrom());
        }
        if (editGap.getMgapTo() != null) {
            query.setParameter("mgapTo", editGap.getMgapTo());
        }
        final List<Object[]> result = query.getResultList();
        return result;
    }

    @Override
    public List<WaterExceptionGapEntity> fetchForExceptionGap(final List<Long> csIdn, final long orgid, final String status) {
        final String queryString = "select e from WaterExceptionGapEntity e where e.csIdn in (:csIdn) and e.orgId=:orgId and e.mgapActive=:mgapActive";
        final Query query = entityManager.createQuery(queryString);
        query.setParameter("orgId", orgid);
        query.setParameter("csIdn", csIdn);
        query.setParameter("mgapActive", status);
        final List<WaterExceptionGapEntity> result = query.getResultList();
        return result;
    }

}
