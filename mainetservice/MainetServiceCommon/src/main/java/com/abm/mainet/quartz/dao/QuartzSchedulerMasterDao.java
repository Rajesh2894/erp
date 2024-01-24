package com.abm.mainet.quartz.dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;

/**
 *
 * @author Vivek.Kumar
 * @since 05-May-2015
 */
@Repository
public class QuartzSchedulerMasterDao extends AbstractDAO<QuartzSchedulerMaster> implements IQuartzSchedulerMasterDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzSchedulerMasterDao.class);

    @Override
    public List<QuartzSchedulerMaster> queryForJobsInfoToBegin()
            throws RuntimeException {

        final Query query = createQuery("FROM QuartzSchedulerMaster qm ORDER BY qm.dpDeptid ASC");

        final List<QuartzSchedulerMaster> schedulerMasters = this.listWithSuppressedWarningsJPA(query);

        if (schedulerMasters != null) {
            for (final QuartzSchedulerMaster quartzSchedulerMaster : schedulerMasters) {
                Hibernate.initialize(quartzSchedulerMaster.getOrgId());
            }
        }

        return schedulerMasters;
    }

    @Override
    public QuartzSchedulerMaster saveQuartzMasterJobsInfo(final QuartzSchedulerMaster master)
            throws RuntimeException {

        QuartzSchedulerMaster qsm = null;

        QuartzSchedulerMaster entity = null;

        if (master.getJobId() != 0L) {
            qsm = findById(master.getJobId());
        }

        if (qsm != null) {
            entity = Update(master);
        } else {
            entity = create(master);
        }
        LOGGER.info("----qsm----" + qsm);
        if (entity != null) {
            Hibernate.initialize(entity.getOrgId());
        }

        return entity;
    }

    @Override
    public QuartzSchedulerMaster fetchQuartzMaster(final String funcOrProcName, final Organisation orgId)
            throws RuntimeException {

        QuartzSchedulerMaster master = null;
        final Query query = createQuery("FROM QuartzSchedulerMaster qm WHERE UPPER(qm.jobProcName)=UPPER(?1) AND qm.orgId=?2");
        query.setParameter(1, funcOrProcName);
        query.setParameter(2, orgId);
        final List<QuartzSchedulerMaster> masters = this.listWithSuppressedWarningsJPA(query);
        if (!masters.isEmpty()) {
            master = masters.get(0);
        }
        return master;
    }

    @Override
    public List<QuartzSchedulerMaster> queryListOfJobs(final long deptId, final Organisation orgId)
            throws RuntimeException {

        final Query query = createQuery(
                "FROM QuartzSchedulerMaster qm WHERE qm.dpDeptid=?1 AND qm.orgId=?2 ORDER BY qm.jobId DESC ");
        query.setParameter(1, deptId);
        query.setParameter(2, orgId);
        return listWithSuppressedWarningsJPA(query);
    }

    @Override
    public QuartzSchedulerMaster findQuartzMasterById(final long rowId, final Organisation orgId)
            throws RuntimeException {

        final Query query = createQuery("FROM QuartzSchedulerMaster qm WHERE qm.jobId=?1 AND qm.orgId=?2");
        query.setParameter(1, rowId);
        query.setParameter(2, orgId);
        final QuartzSchedulerMaster master = (QuartzSchedulerMaster) query.getSingleResult();

        return master;
    }
	

}
