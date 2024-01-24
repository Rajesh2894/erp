package com.abm.mainet.cfc.objection.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.abm.mainet.cfc.objection.domain.TbObjectionEntity;
import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;

@Repository
public class ObjectionServiceDaoImpl extends AbstractDAO<Long> implements IObjectionServiceDAO {

    @Override
    public void saveObjectionDetails(TbObjectionEntity tbObjectionEntity) {
        this.entityManager.persist(tbObjectionEntity);
    }

    @SuppressWarnings("unchecked")
    @Override

    public List<TbObjectionEntity> getObjectionDetails(ObjectionDetailsDto objectionDetailsDto) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ob "
                + "FROM TbObjectionEntity ob "
                + "Where ob.orgId =:orgId");
        if (objectionDetailsDto.getObjectionDeptId() != null && objectionDetailsDto.getObjectionDeptId() > 0) {
            builder.append(" and ob.objectionDeptId =:objectionDeptId");
        }
        if (objectionDetailsDto.getServiceId() != null && objectionDetailsDto.getServiceId() > 0) {
            builder.append(" and ob.serviceId =:objectionType");
        }

        if (objectionDetailsDto.getObjectionReferenceNumber() != null &&
                objectionDetailsDto.getObjectionReferenceNumber().equals(MainetConstants.BLANK)) {
            builder.append(" and ob.s =:objectionReferenceNumber");
        }

        Query query = this.createQuery(builder.toString());
        query.setParameter("orgId", objectionDetailsDto.getOrgId());

        if (objectionDetailsDto.getObjectionDeptId() != null && objectionDetailsDto.getObjectionDeptId() > 0) {
            query.setParameter("objectionDeptId", objectionDetailsDto.getObjectionDeptId());
        }

        if (objectionDetailsDto.getServiceId() != null && objectionDetailsDto.getServiceId() > 0) {
            query.setParameter("objectionType", objectionDetailsDto.getServiceId());
        }

        if (objectionDetailsDto.getObjectionReferenceNumber() != null
                && objectionDetailsDto.getObjectionReferenceNumber().equals(MainetConstants.BLANK)) {
            query.setParameter("objectionReferenceNumber", objectionDetailsDto.getObjectionReferenceNumber());
        }

        return (List<TbObjectionEntity>) query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TbObjectionEntity> getObjectionDetails(final Long orgId, final Long objectionDeptId, final Long serviceId,
            String refNo, Long objectionOn) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ob "
                + "FROM TbObjectionEntity ob "
                + "Where ob.orgId =:orgId");
        if (objectionDeptId != null && objectionDeptId > 0) {
            builder.append(" and ob.objectionDeptId =:objectionDeptId");
        }

        if (serviceId != null && serviceId > 0) {
            builder.append(" and ob.serviceId =:serviceId");
        }
        if (objectionOn != null && objectionOn > 0) {
            builder.append(" and ob.objectionOn =:objectionOn");
        }
        if (!StringUtils.isEmpty(refNo)) {
            builder.append(" and ob.objectionNumber =:refNo");
        }

        Query query = this.createQuery(builder.toString());
        query.setParameter("orgId", orgId);

        if (objectionDeptId != null && objectionDeptId > 0) {
            query.setParameter("objectionDeptId", objectionDeptId);
        }

        if (serviceId != null && serviceId > 0) {
            query.setParameter("serviceId", serviceId);
        }
        if (objectionOn != null && objectionOn > 0) {
            query.setParameter("objectionOn", objectionOn);
        }
        if (!StringUtils.isEmpty(refNo)) {
            query.setParameter("refNo", refNo);
        }

        return (List<TbObjectionEntity>) query.getResultList();
    }

    @Override
    public String getdepartmentNameById(Long deptId) {
        final Query query = entityManager
                .createQuery("SELECT c.dpDeptcode FROM Department c WHERE c.dpDeptid=? and c.status='A'");
        query.setParameter(1, deptId);
        return (String) query.getSingleResult();
    }

}
