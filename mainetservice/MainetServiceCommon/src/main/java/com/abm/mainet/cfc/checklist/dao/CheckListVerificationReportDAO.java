package com.abm.mainet.cfc.checklist.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.abm.mainet.cfc.checklist.domain.CheckListReportEntity;
import com.abm.mainet.cfc.checklist.domain.ViewCheckList;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;

@Repository
public class CheckListVerificationReportDAO extends AbstractDAO<CheckListReportEntity>
        implements ICheckListVerificationReportDAO {

    @Autowired
    SeqGenFunctionUtility seqGenFunctionUtility;

    @SuppressWarnings("unchecked")
    @Override
    public List<CheckListReportEntity> getRejLetterList(final Long applicationId,
            final String statusVariable, final Organisation orgId) {
        List<CheckListReportEntity> rejAppList = null;

        final String HQL = "From CheckListReportEntity c where c.orgId = :orgId and c.apmApplicationId = :apmApplicationId ";
        final Query query = createQuery(HQL);
        query.setParameter("orgId", orgId);
        query.setParameter("apmApplicationId", applicationId);

        rejAppList = query.getResultList();

        CheckListReportEntity reportEntity = null;

        for (final ListIterator<CheckListReportEntity> j = rejAppList.listIterator(); j.hasNext();) {
            reportEntity = j.next();

            final List<LookUp> rejectedDoc = getRejectedDoc(reportEntity.getApmApplicationId());
            reportEntity.setRejectedDocs(rejectedDoc);

            j.set(reportEntity);
        }

        // DEMO CODE
        final List<Object> results1 = new ArrayList<>();
        results1.add(new String(""));
        results1.add(new String(""));
        results1.add(new String(""));
        rejAppList.get(0).setObject1(results1);

        if (statusVariable.equalsIgnoreCase("R")) {
        } else {
        }

        final List<Object> sign = new ArrayList<>();
        sign.add(new String("\\\\192.168.100.97\\mainet_prod\\lib\\aandc.gif"));
        sign.add(new String("DEMO DEGN"));
        sign.add(new String("DEMO RULE"));
        sign.add(new String("DEMO NOTE"));
        rejAppList.get(0).setObject(sign);
        return rejAppList;
    }

    public List<LookUp> getRejectedDoc(final long applicationId) {
        final List<LookUp> lookUps = new ArrayList<>(0);

        LookUp lookUp = null;
        final String HQL = "SELECT v FROM ViewCheckList v WHERE v.apmApplicationId = :apmApplicationId order by v.clmId asc";

        final Query query = createQuery(HQL);
        query.setParameter("apmApplicationId", applicationId);

        @SuppressWarnings("unchecked")
        final List<ViewCheckList> lists = query.getResultList();

        for (final ViewCheckList list : lists) {
            lookUp = new LookUp();

            lookUp.setLookUpId(list.getApmApplicationId());

            lookUp.setDescLangFirst(list.getClmDescEngl());

            lookUp.setDescLangSecond(list.getClmDesc());
            lookUp.setOtherField(list.getClmRemark());
            lookUps.add(lookUp);
        }

        return lookUps;
    }

    @Override
    public long updateApplicationMastrRejection(
            final long apmApplicationId, final Organisation org) {

        final String Hql = "UPDATE TbCfcApplicationMstEntity as v set v.rejctionNo = :rejctionNo, v.rejectionDt = :rejectionDt WHERE v.apmApplicationId = :apmApplicationId and v.tbOrganisation.orgid = :orgid ";
        final Query query = createQuery(Hql);
        final long orgId = org.getOrgid();
        final long rejctionNo = seqGenFunctionUtility.generateSequenceNo("CFC", "TB_CFC_APPLICATION_MST", "REJCTION_NO", orgId,
                null,
                null);
        query.setParameter("orgid", orgId);
        query.setParameter("apmApplicationId", apmApplicationId);
        query.setParameter("rejctionNo", rejctionNo);
        query.setParameter("rejectionDt", new Date());

        query.executeUpdate();
        return rejctionNo;
    }

}