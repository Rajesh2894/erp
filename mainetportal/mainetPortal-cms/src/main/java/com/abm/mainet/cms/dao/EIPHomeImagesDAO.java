package com.abm.mainet.cms.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.abm.mainet.cms.domain.EIPHomeImages;
import com.abm.mainet.cms.domain.EIPHomeImagesHistory;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.Organisation;
//import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.util.ApplicationSession;

/**
 * @author vinay.jangir
 *
 */

@Repository
@SuppressWarnings("unchecked")
public class EIPHomeImagesDAO extends AbstractDAO<EIPHomeImages> implements IEIPHomeImagesDAO {

    @Autowired
    private AuditService auditService;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IEIPHomeImagesDAO#fetchImgPathsOnOrgId(com.abm.mainet.domain.core.Organisation)
     */
    @Override
    public List<String> fetchImgPathsOnOrgId(final Organisation orgid) {
        final Query query = createQuery("select e from EIPHomeImages e where e.orgId = ?1");
        query.setParameter(1, orgid);
        final List<EIPHomeImages> eipHomeImgObjList = query.getResultList();
        final List<String> imgPathsStringList = new ArrayList<>(0);
        for (final EIPHomeImages ee : eipHomeImgObjList) {
            imgPathsStringList.add(ee.getImagePath());
        }
        return imgPathsStringList;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IEIPHomeImagesDAO#persistEIPHomeImageObj(com.abm.mainet.eip.domain.core.EIPHomeImages)
     */
    @Override
    public boolean persistEIPHomeImageObj(EIPHomeImages eipHomeImages) {
        EIPHomeImagesHistory homeImagesHistory = new EIPHomeImagesHistory();

        if (eipHomeImages.getHmImgId() == 0L) {
            homeImagesHistory.setStatus(MainetConstants.InsertMode.ADD.getStatus());
            entityManager.persist(eipHomeImages);
        } else {
            homeImagesHistory.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
            if(eipHomeImages.getChekkerflag1()!=null && eipHomeImages.getChekkerflag1().equalsIgnoreCase("Y"))
            {
            	eipHomeImages = entityManager.merge(eipHomeImages);
            }
            else
            {
            	eipHomeImages.setMakkerchekerflage(null);
            	eipHomeImages = entityManager.merge(eipHomeImages);	
            }
        }
        auditService.createHistory(eipHomeImages, homeImagesHistory);

        if (eipHomeImages != null) {
            return true;
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IEIPHomeImagesDAO#getAllImagesList(com.abm.mainet.domain.core.Organisation, java.lang.String)
     */
    @Override
    public List<EIPHomeImages> getAllImagesList(final Organisation organisation, final String notDelete,
            final String moduleType,final String flag) {
    	
    	final StringBuilder queryAppender = new StringBuilder(
                "select e from EIPHomeImages e where e.orgId = ?1 and e.isDeleted = ?2  and e.moduleType = ?3 ");
        if (flag != null) {
            queryAppender
                    .append("and e.makkerchekerflage =?4 ");
        } else {
            queryAppender
                    .append("and e.makkerchekerflage is null ");
        }
        queryAppender
                .append(" order by e.hmImgOrder asc");
        final Query query = createQuery(queryAppender.toString());
        query.setParameter(1, organisation);
        query.setParameter(2, notDelete);
        query.setParameter(3, moduleType);
        if (flag != null) {
            query.setParameter(4, flag);
        }
        final List<EIPHomeImages> homeImages = query.getResultList();

        return homeImages;
    }

    @Override
    public List<EIPHomeImagesHistory> getAllImagesListhomepage(final Organisation organisation, final String notDelete,
            final String moduleType) {
    	List<EIPHomeImagesHistory> eipHomeImagesHistories=new ArrayList<>();
        final Query query = createQuery(
                "select e.hmImgId from EIPHomeImages e where e.orgId = ?1 and e.isDeleted = ?2 and e.moduleType = ?3 order by e.hmImgOrder asc");
        query.setParameter(1, organisation);
        query.setParameter(2, notDelete);
        query.setParameter(3, moduleType);
        final List<Long> imgIdList = query.getResultList();
        
        if(imgIdList!=null && !imgIdList.isEmpty()) {
        	final Query query2 = createQuery(
                    "select h from EIPHomeImagesHistory h where h.makkerchekerflage='Y' and h.hmImgId in ?1 and (h.hmImgId,h.updatedDate) in (select eh.hmImgId,max(eh.updatedDate) from EIPHomeImagesHistory eh where eh.makkerchekerflage='Y' and eh.hmImgId in ?1 group by eh.hmImgId) order by h.hmImgOrder asc, h.updatedDate desc");
        	query2.setParameter(1, imgIdList);
        	eipHomeImagesHistories = query2.getResultList();
        }
            return eipHomeImagesHistories;
    }


    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IEIPHomeImagesDAO#getImagesByID(long)
     */
    @Override
    public EIPHomeImages getImagesByID(final long rowId) {
        final Query query = createQuery("select e from EIPHomeImages e where e.hmImgId = ?1");
        query.setParameter(1, rowId);
        final List<EIPHomeImages> eipHomeImages = query.getResultList();
        if ((eipHomeImages == null) || eipHomeImages.isEmpty()) {
            return null;
        } else {
            return eipHomeImages.get(0);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IEIPHomeImagesDAO#isAccess(com.abm.mainet.domain.core.Organisation, java.lang.String)
     */
    @Override
    public boolean isAccess(final Organisation organisation, final String notDelete, final String moduleType) {

        final Query query = createQuery(
                "select count(e.hmImgId) from EIPHomeImages e where e.orgId = ?1 and e.isDeleted = ?2 and e.moduleType =?3");
        query.setParameter(1, organisation);
        query.setParameter(2, notDelete);
        query.setParameter(3, moduleType);
        final Integer totalResult = ((Number) (query.getSingleResult())).intValue();
        int maxImage;
        if (moduleType.equals(MainetConstants.FlagS)) {
            maxImage = Integer.parseInt(getFieldLabel("maxImages"));
        } else {
            maxImage = Integer.parseInt(getFieldLabel("maxlogoImages"));
        }

        if (totalResult >= maxImage) {
            return false;
        }

        return true;
    }

    private String getFieldLabel(final String field) {
        return ApplicationSession.getInstance().getMessage("landingPage" + MainetConstants.operator.DOT + field);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IEIPHomeImagesDAO#checkExistingSequence(java.lang.Long)
     */
    @Override
    public boolean checkExistingSequence(final Long hmImgOrder, final String moduleType, final Organisation organisation) {

        final Query query = createQuery(
                "select e from EIPHomeImages e where e.orgId = ?1 and e.isDeleted = ?2 and e.moduleType = ?3 and e.hmImgOrder= ?4 ");
        query.setParameter(1, organisation);
        query.setParameter(2, MainetConstants.IsDeleted.NOT_DELETE);
        query.setParameter(3, moduleType);
        query.setParameter(4, hmImgOrder);
        final List<EIPHomeImages> list = query.getResultList();
        if (list.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean checkAuthorisedkSequence(final Long hmImgOrder, final String moduleType, final Organisation organisation) {

        final Query query = createQuery(
                "select e from EIPHomeImages e where e.orgId = ?1 and e.isDeleted = ?2 and e.moduleType = ?3 and e.hmImgOrder= ?4 and makkerchekerflage= ?5 ");
        query.setParameter(1, organisation);
        query.setParameter(2, MainetConstants.IsDeleted.NOT_DELETE);
        query.setParameter(3, moduleType);
        query.setParameter(4, hmImgOrder);
        query.setParameter(5, "Y");
        final List<EIPHomeImages> list = query.getResultList();
        if (list.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IEIPHomeImagesDAO#gettEIPHomeImagesMaster(long, com.abm.mainet.domain.core.Organisation,
     * java.lang.String)
     */
    @Override
    public EIPHomeImages gettEIPHomeImagesMaster(final long rowId, final Organisation organisation, final String notDelete) {
        final Query query = createQuery("select e from EIPHomeImages e where e.id = ?1 and e.orgId = ?2 and e.isDeleted = ?3");
        query.setParameter(1, rowId);
        query.setParameter(2, organisation);
        query.setParameter(3, MainetConstants.IsDeleted.NOT_DELETE);
        final List<EIPHomeImages> eipHomeImages = query.getResultList();
        if ((eipHomeImages == null) || eipHomeImages.isEmpty()) {
            return null;
        } else {
            return eipHomeImages.get(0);
        }

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IEIPHomeImagesDAO#saveSchemeMaster(com.abm.mainet.eip.domain.core.EIPHomeImages)
     */
    @Override
    public void saveSchemeMaster(EIPHomeImages eipHomeImages) {
       // try {
            eipHomeImages.setIsDeleted(MainetConstants.IsDeleted.DELETE);
            eipHomeImages = entityManager.merge(eipHomeImages);
            entityManager.merge(eipHomeImages);

            EIPHomeImagesHistory homeImagesHistory = new EIPHomeImagesHistory();

            homeImagesHistory.setStatus(MainetConstants.InsertMode.DELETE.getStatus());
            auditService.createHistory(eipHomeImages, homeImagesHistory);

        /*}

        catch (final Exception e) {

            throw new FrameworkException(MainetConstants.ERROR_OCCURED_SAVE_SCHEMEMASTER, e);

        }*/

    }
    //#122435 
    @Override
   	public Set<Long> getAllImagesOrderSeqBasedOnModuleType(Organisation organisation, String notDelete, String moduleType) {
    	Set<Long> uniqueSeqs =new HashSet<Long>();
           final Query query = createQuery(
                   "select e.hmImgOrder from EIPHomeImages e where e.orgId = ?1 and e.isDeleted = ?2 and e.moduleType = ?3 order by e.hmImgOrder asc");
           query.setParameter(1, organisation);
           query.setParameter(2, notDelete);
           query.setParameter(3, moduleType);
           final List<Long> imgIdList = query.getResultList();
           if(imgIdList!=null && !imgIdList.isEmpty()) {
        	   uniqueSeqs = new HashSet<Long>(imgIdList);
           }
           return uniqueSeqs;
   	}
}
