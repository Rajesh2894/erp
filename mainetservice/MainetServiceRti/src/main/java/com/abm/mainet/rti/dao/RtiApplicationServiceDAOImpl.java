/**
 * @author    : Harshit kumar
 * @since     : 20 Feb 2018
 * @comment   : Implementation file for data transaction
 * @Interface : IRtiApplicationServiceDAO.java
 * @methods   : saveRtiApplicationForm - for saving RTI application.
 *              updateRtiApplicationForm - for updating RTI application.
 *              getRtiApplicationDetails - fetch RTI application.
 */
package com.abm.mainet.rti.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.rti.domain.TbRtiApplicationDetails;
import com.abm.mainet.rti.domain.TbRtiMediaDetails;

@Repository
public class RtiApplicationServiceDAOImpl extends AbstractDAO<Long> implements IRtiApplicationServiceDAO {

    @Override
    @Transactional
    public void saveRtiApplicationForm(TbRtiApplicationDetails tbRtiApplicationDetails) {
        this.entityManager.merge(tbRtiApplicationDetails);

    }

    @Override
    public TbRtiApplicationDetails getRtiApplicationDetails(Long appId, Long orgId) {
        final Query query = entityManager
                .createQuery("SELECT c FROM TbRtiApplicationDetails c WHERE c.apmApplicationId=? and c.orgId=?");
        query.setParameter(1, appId);
        query.setParameter(2, orgId);
        return (TbRtiApplicationDetails) query.getSingleResult();

    }

    @Override
    public void saveRtiMediaDetails(TbRtiMediaDetails tbRtiMediaDetails) {
        this.entityManager.merge(tbRtiMediaDetails);

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TbRtiMediaDetails> getMediaList(Long rtiId, Long orgId) {
        final Query query = entityManager
                .createQuery("SELECT c FROM TbRtiMediaDetails c WHERE c.rtiMedId=? and c.orgId=? and media_status='A'");
        query.setParameter(1, rtiId);
        query.setParameter(2, orgId);
        return (List<TbRtiMediaDetails>) query.getResultList();

    }

    @Override
    public String getdepartmentNameById(Long deptId) {

        final Query query = entityManager
                .createQuery("SELECT c.dpDeptdesc FROM Department c WHERE c.dpDeptid=?");
        query.setParameter(1, deptId);
        return (String) query.getSingleResult();

    }

    @Override
    @SuppressWarnings("unchecked")
    public Boolean getRtiNumber(String rtiNumber, Long orgId) {
        final Query query = entityManager
                .createQuery("SELECT c.rtiNo FROM TbRtiApplicationDetails c WHERE c.rtiNo=? and c.orgId=?");
        query.setParameter(1, rtiNumber);
        query.setParameter(2, orgId);

        String rtiNo = (String) query.getSingleResult();
        if (rtiNo != null && !MainetConstants.BLANK.equals(rtiNo)) {
            return true;
        } else
            return false;

    }

    @Override
    public Long getApplicationNumberByRefNo(String rtiNumber, Long serviceId, Long orgId, Long empId) {
        final Query query = createQuery("SELECT c.apmApplicationId FROM TbRtiApplicationDetails c WHERE c.rtiNo=?");
        query.setParameter(1, rtiNumber);
//changes for US34043
       // query.setParameter(2, orgId);

        Long apmApplicationId = (Long) query.getSingleResult();
        return apmApplicationId;

    }

    @Override
    public String getRtiApplicationNumberForObj(Long apmApplicationId, Long orgId) {
        final Query query = entityManager
                .createQuery("SELECT c.rtiNo FROM TbRtiApplicationDetails c WHERE c.apmApplicationId=? and c.orgId=?");
        query.setParameter(1, apmApplicationId);
        query.setParameter(2, orgId);

        String rtiNo = (String) query.getSingleResult();
        return rtiNo;
    }

    @Override
    public TbRtiApplicationDetails getRTIApplicationDetail(long apmApplicationId, long orgId) {
        final Query query = entityManager
                .createQuery("SELECT c FROM TbRtiApplicationDetails c WHERE c.apmApplicationId=? ");
        query.setParameter(1, apmApplicationId);
        //query.setParameter(2, orgId);

        TbRtiApplicationDetails applicationDetails = (TbRtiApplicationDetails) query.getSingleResult();
        return applicationDetails;
    }

    @Override
    @Transactional
    public TbServiceReceiptMasEntity getReceiptDetails(Long apmApplicationId, Long orgId) {
        final Query query = createQuery("SELECT r FROM TbServiceReceiptMasEntity r WHERE r.apmApplicationId=?1");
        query.setParameter(1, apmApplicationId);
       // query.setParameter(2, orgId);
        TbServiceReceiptMasEntity receiptDetail = (TbServiceReceiptMasEntity) query.getSingleResult();
        return receiptDetail;
    }

    @Override
    @Transactional
    public String getBplType(String rtiNo, Long orgId) {
        final Query query = createQuery("SELECT b.rtiBplFlag FROM TbRtiApplicationDetails b WHERE b.rtiNo=?1 ");
        query.setParameter(1, rtiNo);
        //query.setParameter(2, orgId);
        String bplFlag = (String) query.getSingleResult();
        return bplFlag;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<TbRtiApplicationDetails> getDetails(Long apmApplicationId, Long orgId) {
        final Query query = entityManager
                .createQuery("SELECT c FROM TbRtiApplicationDetails c WHERE c.apmApplicationId=? and c.orgId=?");
        query.setParameter(1, apmApplicationId);
        query.setParameter(2, orgId);
        return (List<TbRtiApplicationDetails>) query.getResultList();

    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<TbRtiApplicationDetails> getRtiAction(Long forwardDeptId, Long orgId) {
        final Query query = entityManager
                .createQuery("SELECT c FROM TbRtiApplicationDetails c WHERE c.rtiAction=? and c.orgId=?");
        query.setParameter(1, forwardDeptId.intValue());
        query.setParameter(2, orgId);
        return (List<TbRtiApplicationDetails>) query.getResultList();

    }
    
    @Override
    public TbRtiApplicationDetails getRtiApplicationDetailsForDSCL(Long appId, Long orgId) {
        final Query query = entityManager
                .createQuery("SELECT c FROM TbRtiApplicationDetails c WHERE c.apmApplicationId=?");
        query.setParameter(1, appId);
        return (TbRtiApplicationDetails) query.getSingleResult();

    }
}
