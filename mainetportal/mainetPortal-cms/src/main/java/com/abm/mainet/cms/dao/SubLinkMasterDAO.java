package com.abm.mainet.cms.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.abm.mainet.cms.domain.LinksMaster;
import com.abm.mainet.cms.domain.SubLinkFieldDetails;
import com.abm.mainet.cms.domain.SubLinkFieldDetailsHistory;
import com.abm.mainet.cms.domain.SubLinkFieldMapping;
import com.abm.mainet.cms.domain.SubLinkFieldMappingHistory;
import com.abm.mainet.cms.domain.SubLinkMaster;
import com.abm.mainet.cms.domain.SubLinkMasterHistory;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;

/**
 * @author Pranit.Mhatre
 * @since 17 February, 2014
 */
@Repository
public class SubLinkMasterDAO extends AbstractDAO<SubLinkMaster> implements ISubLinkMasterDAO {
    private static final Logger LOG = Logger.getLogger(SubLinkMasterDAO.class);

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.ISubLinkMasterDAO#getAllSublinkList(java.lang.String)
     */
    @Autowired
    private AuditService auditService;

    @Override
    @SuppressWarnings("unchecked")
    public List<SubLinkMaster> getAllSublinkList(final String activeStatus, String flag) {

        final StringBuilder queryAppender = new StringBuilder(
                "select subLinkMaster from SubLinkMaster subLinkMaster join subLinkMaster.linksMaster l where  subLinkMaster.orgId= :orgId and subLinkMaster.isDeleted= :active and l.isDeleted= :active ");
        if (flag != null) {
            queryAppender
                    .append("and subLinkMaster.chekkerflag =:authFlag ");
        } else {
            queryAppender
                    .append("and subLinkMaster.chekkerflag is null ");
        }
        queryAppender
               // .append("order by l.updatedDate desc");
       //df#120242 sorting based on the module name and function name --> starts
      .append("order by l.linkTitleEg ");
      //df#120242 sorting based on the module name and function name --> ends
        final Query query = createQuery(queryAppender.toString());
        query.setParameter("orgId", UserSession.getCurrent().getOrganisation());
        query.setParameter("active", activeStatus);
        if (flag != null) {
            query.setParameter("authFlag", flag);
        }
        final List<SubLinkMaster> list = query.getResultList();

        return list;

    }
    
    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.ISubLinkMasterDAO#getAllSublinkList(long, long, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<SubLinkMaster> getAllSublinkList(final long moduleId, final long functionId, final String activeStatus,
            String flag) {

        final StringBuilder builder = new StringBuilder("select subLinkMaster from SubLinkMaster subLinkMaster" +
                " join subLinkMaster.linksMaster l " +
                " where subLinkMaster.orgId= :orgId and subLinkMaster.isDeleted= :active and "
                + " l.linkId = :moduleId and l.isDeleted= :lisDeleted and l.orgId = :lorgId ");

        if (functionId != 0L) {
            builder.append(" and subLinkMaster.id=:functionId ");
        }

        builder.append(" order by l.linkTitleEg asc");

        final Query query = createQuery(builder.toString());

        query.setParameter("orgId", UserSession.getCurrent().getOrganisation());
        query.setParameter("active", activeStatus);
        query.setParameter("moduleId", moduleId);
        query.setParameter("lisDeleted", "N");
        query.setParameter("lorgId", UserSession.getCurrent().getOrganisation());
        if (functionId != 0L) {
            query.setParameter("functionId", functionId);
        }
        final List<SubLinkMaster> list = query.getResultList();

        return list;

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.ISubLinkMasterDAO#getAllSublinkList(com.abm.mainet.eip.domain.core.LinksMaster,
     * java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<SubLinkMaster> getAllSublinkList(final LinksMaster linksMaster, final String activeStatus) {

        final Query query = createQuery("select subLinkMaster from SubLinkMaster subLinkMaster" +
                " where  subLinkMaster.orgId= :orgId and subLinkMaster.isDeleted= :active and subLinkMaster.linksMaster=:linksMaster order by subLinkMaster.id asc");
        query.setParameter("orgId", UserSession.getCurrent().getOrganisation());
        query.setParameter("active", activeStatus);
        query.setParameter("linksMaster", linksMaster);
        final List<SubLinkMaster> list = query.getResultList();

        return list;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.ISubLinkMasterDAO#findById(long, java.lang.String)
     */
    @Override
    public SubLinkMaster findById(final long subLinkId, final String activeStatus, long lookupId) {
        String isArchive = null;
        try {

            final Query query = createQuery("select subLinkMaster from SubLinkMaster subLinkMaster" +
                    " where  subLinkMaster.isDeleted= :active and subLinkMaster.id=:id order by subLinkMaster.id asc");
            /* subLinkMaster.orgId= :orgId and query.setParameter("orgId", UserSession.getCurrent().getOrganisation()); */
            query.setParameter("active", activeStatus);
            query.setParameter("id", subLinkId);
            final SubLinkMaster subLinkMaster = (SubLinkMaster) query.getSingleResult();
            
            isArchive = subLinkMaster.getIsArchive();

            final ListIterator<SubLinkFieldMapping> listIterator = subLinkMaster.getSubLinkFieldMappings().listIterator();
            while (listIterator.hasNext()) {
                listIterator.next();
            }

            List<Long> subLinkDetailId = new ArrayList();
            for (SubLinkFieldDetails subLinkFieldDetails : subLinkMaster.getSubLinkFieldDetails()) {
                subLinkDetailId.add(subLinkFieldDetails.getId());
            }
            if (subLinkDetailId != null && !subLinkDetailId.isEmpty()) {

                final StringBuilder queryAppender = new StringBuilder(
                        "select h from SubLinkFieldDetailsHistory h where h.chekkerflag='Y' ");

                if (isArchive != null && isArchive.equalsIgnoreCase("Y")) {
                    queryAppender
                            .append("and h.validityDate >= ?2 ");
                }

                queryAppender
                        .append("and (h.hId) in (select max(hId) from SubLinkFieldDetailsHistory eh where eh.chekkerflag='Y' and eh.id in ?1 ");
                if(subLinkMaster.getSectionType0() == lookupId) {
                queryAppender.append("group by eh.id) order by h.date_01 desc ");
                }
                else {
                queryAppender.append("group by eh.id) order by h.subLinkFieldDtlOrd desc ");
                }
                final Query query2 = createQuery(queryAppender.toString());
                query2.setParameter(1, subLinkDetailId);
                if (isArchive != null && isArchive.equalsIgnoreCase("Y")) {
                    query2.setParameter(2, new Date());
                }
                subLinkMaster.setDetailsHistories(query2.getResultList());
            }
            return subLinkMaster;
        } catch (final Exception e) {
        	 LOG.error("Error occured during findbyid for id: " + subLinkId, e);
            return null;
        }

    }

    @Override
    public SubLinkFieldDetails getSubLinkFieldDtlsObj(Long subLinkId) {
        try {
            final Query query = createQuery("select s from SubLinkFieldDetails s where  s.id=:id ");
            query.setParameter("id", subLinkId);
            final SubLinkFieldDetails fieldDtl = (SubLinkFieldDetails)query.getSingleResult();
           
            return fieldDtl;
        } catch (final Exception e) {
            LOG.error(MainetConstants.FINDBYID_ERROR, e);
            return null;
        }
    }
    
    
    @Override
    public List<SubLinkFieldDetails> getSubLinkFieldDetails(Long subLinkMasId) {
        try {
            final Query query = createQuery("select s from SubLinkFieldDetails s" +
                    " where  s.subLinkMaster.id=:id and s.isDeleted in('N','S') order by s.isDeleted asc,s.chekkerflag asc,s.subLinkFieldDtlOrd desc");
            query.setParameter("id", subLinkMasId);
            final List<SubLinkFieldDetails> fieldDtlList = query.getResultList();

            if (null == fieldDtlList || fieldDtlList.isEmpty()) {
                return null;
            }
            return fieldDtlList;
        } catch (final Exception e) {
            LOG.error(MainetConstants.FINDBYID_ERROR, e);
            return null;
        }
    }
    

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.ISubLinkMasterDAO#getCommitteInfo(java.lang.String)
     */
    @Override
    public Long getCommitteInfo(final String prefix) {
        Long id = null;

        final String jpaQuery = "select subLinkMaster from SubLinkMaster subLinkMaster where  subLinkMaster.orgId= :orgId and subLinkMaster.sectionType IS NOT NULL and subLinkMaster.imageLinkType= :prifixValue order by subLinkMaster.id desc ";
        final Query query = createQuery(jpaQuery);
        final LookUp prifixValue1 = CommonMasterUtility.getValueFromPrefixLookUp(prefix, MainetConstants.LookUp.EIP_IMAGE_TYPE);
        query.setParameter("orgId", UserSession.getCurrent().getOrganisation());
        query.setParameter("prifixValue", prifixValue1.getLookUpId());
        @SuppressWarnings("unchecked")
        final List<SubLinkMaster> list = query.getResultList();
        if (!list.isEmpty()) {

            id = list.get(0).getId();
        } else {
            throw new RuntimeException(getAppSession().getMessage("record.notfound.sublinkmaster"));
        }
        return id;

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.ISubLinkMasterDAO#isSubLinkIsExist(com.abm.mainet.eip.domain.core.SubLinkMaster)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<SubLinkMaster> isSubLinkIsExist(final SubLinkMaster sublinksMaster) {
      List<SubLinkMaster> sublinkMas = new ArrayList<SubLinkMaster>();
        final StringBuilder builder = new StringBuilder(
                "select s from SubLinkMaster s where s.linksMaster = ?1 and s.subLinkOrder = ?2 and s.orgId = ?3 and s.isDeleted =?5");
       if (sublinksMaster.getId() > 0) {
            builder.append(" and s.id <> ?4 ");
        }
          if (sublinksMaster.getSubLinkMaster()!= null && sublinksMaster.getSubLinkMaster().getId() > 0) {
            builder.append(" and s.subLinkMaster = ?6 ");
        }
        final Query query = createQuery(builder.toString());
        query.setParameter(1, sublinksMaster.getLinksMaster());
        query.setParameter(2, sublinksMaster.getSubLinkOrder());
        query.setParameter(3, UserSession.getCurrent().getOrganisation());
        query.setParameter(5, MainetConstants.FLAGN);

         	if (sublinksMaster.getId() > 0) {
			query.setParameter(4, sublinksMaster.getId());
			}
         	 if (sublinksMaster.getSubLinkMaster()!= null && sublinksMaster.getSubLinkMaster().getId() > 0) {
			query.setParameter(6, sublinksMaster.getSubLinkMaster());
		}            
        sublinkMas =   query.getResultList();
        if (!(sublinksMaster.getSubLinkMaster()!= null && sublinksMaster.getSubLinkMaster().getId() > 0)) {
        	sublinkMas =  sublinkMas.stream().filter(i -> i.getSubLinkMaster() ==null  || ( i.getSubLinkMaster() != null && i.getSubLinkMaster().getId() <0) ).collect(Collectors.toList());
        }        
        return sublinkMas;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.ISubLinkMasterDAO#getMaxLinkOrderCount(long)
     */
    @Override
    public Double getMaxLinkOrderCount(final long linkId) {

        final Query query = createQuery("select max(s.subLinkOrder) from SubLinkMaster s where s.linksMaster.linkId = ?1 ");
        query.setParameter(1, linkId);
        return (Double) query.getSingleResult();

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SubLinkMaster> getSerchContentList(final String searchText) {

        final StringBuilder builder = new StringBuilder(
                "select s.id, s.subLinkNameEn, s.subLinkNameRg from SubLinkMaster s where s.isDeleted = ?1  "
                        + "and s.orgId = ?2  and ");
        if (UserSession.getCurrent().getLanguageId() == 1) {
            builder.append(" UPPER(s.subLinkNameEn) like concat('%',UPPER( ?3 ),'%')");
        } else {
            builder.append(" UPPER(s.subLinkNameRg) like concat('%',UPPER( ?3 ),'%')");
        }

        builder.append(
                " and s.id not in (select sm.subLinkMaster.id from SubLinkMaster sm where sm.isDeleted = ?4 and sm.orgId = ?5 and sm.subLinkMaster.id is not null) and ");
        if (UserSession.getCurrent().getLanguageId() == 1) {
            builder.append(
                    " s.subLinkNameEn not in (select lm.linkTitleEg from LinksMaster lm where lm.isDeleted = ?4 and lm.orgId = ?5 and ");
            builder.append(" UPPER(lm.linkTitleEg) like concat('%',UPPER( ?3 ),'%'))");
        } else {
            builder.append(
                    " s.subLinkNameRg not in (select lm.linkTitleReg from LinksMaster lm where lm.isDeleted = ?4 and lm.orgId = ?5 and ");
            builder.append(" UPPER(lm.linkTitleReg) like concat('%',UPPER( ?3 ),'%'))");
        }

        final Query query = createQuery(builder.toString());
        query.setParameter(1, MainetConstants.IsDeleted.NOT_DELETE);
        query.setParameter(2, UserSession.getCurrent().getOrganisation());
        query.setParameter(3, searchText);
        query.setParameter(4, MainetConstants.IsDeleted.NOT_DELETE);
        query.setParameter(5, UserSession.getCurrent().getOrganisation());

        final List<Object[]> lisObject = query.getResultList();
        final List<SubLinkMaster> subList = new ArrayList<>();
        SubLinkMaster linkMaster = null;

        for (final Object object[] : lisObject) {

            linkMaster = new SubLinkMaster();
            linkMaster.setId((Long) object[0]);
            linkMaster.setSubLinkNameEn(object[1] + MainetConstants.BLANK);
            linkMaster.setSubLinkNameRg(object[2] + MainetConstants.BLANK);
            subList.add(linkMaster);
        }
        return subList;

    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<SubLinkMaster> getSerchContentCKeditorList(final String searchText) {

        final StringBuilder builder = new StringBuilder(
                "select s.id, s.subLinkNameEn, s.subLinkNameRg from SubLinkMaster s where s.isDeleted = ?1  "
                        + "and s.orgId = ?2 ") ;
   
        if (UserSession.getCurrent().getLanguageId() == 1) {
            builder.append(" and s.id in (select sm.subLinkMaster.id from SubLinkFieldDetails sm where (UPPER(sm.txta_03_ren_blob) like concat('%',UPPER( ?3 ),'%') or UPPER(sm.txt_01_en) like concat('%',UPPER( ?3 ),'%') or UPPER(sm.txt_02_en) like concat('%',UPPER( ?3 ),'%') or UPPER(sm.txt_03_en) like concat('%',UPPER( ?3 ),'%') or UPPER(sm.txt_04_en) like concat('%',UPPER( ?3 ),'%') or UPPER(sm.txt_05_en) like concat('%',UPPER( ?3 ),'%') or UPPER(sm.txt_06_en) like concat('%',UPPER( ?3 ),'%') or UPPER(sm.txt_07_en) like concat('%',UPPER( ?3 ),'%') or UPPER(sm.txt_08_en) like concat('%',UPPER( ?3 ),'%') or UPPER(sm.txt_09_en) like concat('%',UPPER( ?3 ),'%') or UPPER(sm.txt_10_en) like concat('%',UPPER( ?3 ),'%') or UPPER(sm.txt_11_en) like concat('%',UPPER( ?3 ),'%') or UPPER(sm.txt_12_en) like concat('%',UPPER( ?3 ),'%') or UPPER(sm.txta_01_en) like concat('%',UPPER( ?3 ),'%') or UPPER(sm.txta_01_en) like concat('%',UPPER( ?3 ),'%')  ) and sm.isDeleted = ?4 and sm.orgId = ?5 and sm.id is not null) ");
        } else {
        
            builder.append(" and s.id in (select sm.subLinkMaster.id from SubLinkFieldDetails sm where (UPPER(sm.txta_03_en_nnclob) like concat('%',UPPER( ?3 ),'%') or UPPER(sm.txt_01_rg) like concat('%',UPPER( ?3 ),'%') or UPPER(sm.txt_02_rg) like concat('%',UPPER( ?3 ),'%') or UPPER(sm.txt_03_rg) like concat('%',UPPER( ?3 ),'%') or UPPER(sm.txt_04_rg) like concat('%',UPPER( ?3 ),'%') or UPPER(sm.txt_05_rg) like concat('%',UPPER( ?3 ),'%') or UPPER(sm.txt_06_rg) like concat('%',UPPER( ?3 ),'%') or UPPER(sm.txt_07_rg) like concat('%',UPPER( ?3 ),'%') or UPPER(sm.txt_08_rg) like concat('%',UPPER( ?3 ),'%') or UPPER(sm.txt_09_rg) like concat('%',UPPER( ?3 ),'%') or UPPER(sm.txt_10_rg) like concat('%',UPPER( ?3 ),'%') or UPPER(sm.txt_11_rg) like concat('%',UPPER( ?3 ),'%') or UPPER(sm.txt_12_rg) like concat('%',UPPER( ?3 ),'%') or UPPER(sm.txta_01_rg) like concat('%',UPPER( ?3 ),'%') or UPPER(sm.txta_01_rg) like concat('%',UPPER( ?3 ),'%')  ) and sm.isDeleted = ?4 and sm.orgId = ?5 and sm.id is not null) ");
        }
        
        final Query query = createQuery(builder.toString());
        query.setParameter(1, MainetConstants.IsDeleted.NOT_DELETE);
        query.setParameter(2, UserSession.getCurrent().getOrganisation());
        query.setParameter(3, searchText);
        query.setParameter(4, MainetConstants.IsDeleted.NOT_DELETE);
        query.setParameter(5, UserSession.getCurrent().getOrganisation());

        final List<Object[]> lisObject = query.getResultList();
        final List<SubLinkMaster> subList = new ArrayList<>();
        SubLinkMaster linkMaster = null;

        for (final Object object[] : lisObject) {

            linkMaster = new SubLinkMaster();
            linkMaster.setId((Long) object[0]);
            linkMaster.setSubLinkNameEn(object[1] + MainetConstants.BLANK);
            linkMaster.setSubLinkNameRg(object[2] + MainetConstants.BLANK);
            subList.add(linkMaster);
        }
        return subList;

    }

    @Override
    public SubLinkMaster findBymapId(final long subLinkId, final String idformap, final String activeStatus) {
        try {

            final Query query = createQuery("select subLinkMaster from SubLinkMaster subLinkMaster" +
                    " join subLinkMaster.subLinkFieldDetails  fieldDetails  " +
                    " where fieldDetails.txt_01_en=:idformap and subLinkMaster.orgId= :orgId and subLinkMaster.isDeleted= :active and subLinkMaster.id=:id order by subLinkMaster.id asc");
            query.setParameter("orgId", UserSession.getCurrent().getOrganisation());
            query.setParameter("active", activeStatus);
            query.setParameter("id", subLinkId);
            query.setParameter("idformap", idformap);
            final SubLinkMaster subLinkMaster = (SubLinkMaster) query.getSingleResult();

            final ListIterator<SubLinkFieldMapping> listIterator = subLinkMaster.getSubLinkFieldMappings().listIterator();
            while (listIterator.hasNext()) {
                listIterator.next();
            }

            final ListIterator<SubLinkFieldDetails> listIterator2 = subLinkMaster.getSubLinkFieldDetails().listIterator();
            while (listIterator2.hasNext()) {
                listIterator2.next();
            }
            return subLinkMaster;
        } catch (final Exception e) {
            LOG.error("Error occured during findBymapId", e);
            return null;
        }
    }

    @Override
    public boolean save(SubLinkMaster linkMaster) {
        try {
        	int editRowId = (int)linkMaster.getEditRowId();
            SubLinkMasterHistory subLinkMasterHistory = new SubLinkMasterHistory();
            if (linkMaster.getId() == 0L) {
                subLinkMasterHistory.setStatus(MainetConstants.InsertMode.ADD.getStatus());
                entityManager.merge(linkMaster);
                subLinkMasterHistory.setLinksMaster(linkMaster.getLinksMaster().getId());
                auditService.createHistory(linkMaster, subLinkMasterHistory);
                if(linkMaster.getSubLinkFieldDetails().size()>0) {
                    SubLinkFieldDetails fieldDetail=linkMaster.getSubLinkFieldDetails().get(linkMaster.getSubLinkFieldDetails().size()-1);
                    SubLinkFieldDetailsHistory fieldDetailsHistory = new SubLinkFieldDetailsHistory();
                    fieldDetailsHistory.setId(fieldDetail.getId());
                    fieldDetailsHistory.setSubLinkMaster(linkMaster.getId());
                    fieldDetailsHistory.setStatus(MainetConstants.InsertMode.ADD.getStatus());
                    auditService.createHistory(fieldDetail, fieldDetailsHistory);
                }
                for (SubLinkFieldMapping fieldMapping : linkMaster.getSubLinkFieldMappings()) {
                    SubLinkFieldMappingHistory fieldMappingHistory = new SubLinkFieldMappingHistory();
                    fieldMappingHistory.setId(fieldMapping.getId());
                    fieldMappingHistory.setSubLinkMaster(linkMaster.getId());
                    fieldMappingHistory.setStatus(MainetConstants.InsertMode.ADD.getStatus());
                    auditService.createHistory(fieldMapping, fieldMappingHistory);
                }
            } else {
                if (linkMaster.getIsDeleted().equalsIgnoreCase(MainetConstants.IsDeleted.DELETE)) {
                    subLinkMasterHistory.setStatus(MainetConstants.InsertMode.DELETE.getStatus());
                } else {
                    subLinkMasterHistory.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
                }

                if (linkMaster.getCheckerFlag1() != null && linkMaster.getCheckerFlag1().equalsIgnoreCase("Y")) {

                    linkMaster = entityManager.merge(linkMaster);
                } else {
                    if (linkMaster.getEditRowId() != 0l) {
                        if (linkMaster.getId() == linkMaster.getEditRowId()) {
                            linkMaster.setChekkerflag(null);
                        }
                    }
                    if (!linkMaster.getSubLinkFieldDetails().isEmpty()) {
                        for (SubLinkFieldDetails fieldDetails : linkMaster.getSubLinkFieldDetails()) {
                            if (linkMaster.getEditRowId() != 0l) {
                                if (fieldDetails.getId() == linkMaster.getEditRowId()) {
                                    fieldDetails.setChekkerflag(null);
                                }
                            }
                        }
                    }
                    linkMaster = entityManager.merge(linkMaster);
                }
                subLinkMasterHistory.setLinksMaster(linkMaster.getLinksMaster().getId());
                auditService.createHistory(linkMaster, subLinkMasterHistory);
                for (SubLinkFieldMapping fieldMapping : linkMaster.getSubLinkFieldMappings()) {
                    SubLinkFieldMappingHistory fieldMappingHistory = new SubLinkFieldMappingHistory();
                    fieldMappingHistory.setId(fieldMapping.getId());
                    fieldMappingHistory.setSubLinkMaster(linkMaster.getId());
                    if (linkMaster.getIsDeleted().equalsIgnoreCase(MainetConstants.IsDeleted.DELETE)) {
                        fieldMappingHistory.setStatus(MainetConstants.InsertMode.DELETE.getStatus());
                    } else {
                        fieldMappingHistory.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
                    }
                    auditService.createHistory(fieldMapping, fieldMappingHistory);
                }
                for (SubLinkFieldDetails fieldDetail : linkMaster.getSubLinkFieldDetails()) {
                	SubLinkFieldDetailsHistory fieldDetailsHistory = new SubLinkFieldDetailsHistory();
                	if(fieldDetail.getRowId() == editRowId) {                    
	                    fieldDetailsHistory.setId(fieldDetail.getId());
	                    fieldDetailsHistory.setSubLinkMaster(linkMaster.getId());
	                    if (linkMaster.getIsDeleted().equalsIgnoreCase(MainetConstants.IsDeleted.DELETE)) {
	                        fieldDetailsHistory.setStatus(MainetConstants.InsertMode.DELETE.getStatus());
	                    } else {
	                        fieldDetailsHistory.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
	                    }
	                    auditService.createHistory(fieldDetail, fieldDetailsHistory);
	                    break;
                	}
                	
                	/* uncomment for iprd*/
                	
					/*
					 * if((linkMaster.getLinksMaster().getLinkTitleEg().equalsIgnoreCase(
					 * MainetConstants.OFFICE_DOCUMENTS)) ||
					 * (linkMaster.getLinksMaster().getLinkTitleEg().equalsIgnoreCase(
					 * MainetConstants.TENDERS))) { if(editRowId == 0) {
					 * if(linkMaster.getSubLinkFieldDetails().size()>0) {
					 * fieldDetail=linkMaster.getSubLinkFieldDetails().get(linkMaster.
					 * getSubLinkFieldDetails().size()-1); }
					 * fieldDetailsHistory.setId(fieldDetail.getId());
					 * fieldDetailsHistory.setSubLinkMaster(linkMaster.getId()); if
					 * (linkMaster.getIsDeleted().equalsIgnoreCase(MainetConstants.IsDeleted.DELETE)
					 * ) {
					 * fieldDetailsHistory.setStatus(MainetConstants.InsertMode.DELETE.getStatus());
					 * } else {
					 * fieldDetailsHistory.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
					 * } auditService.createHistory(fieldDetail, fieldDetailsHistory); break; } }
					 */
                }
                
            }

            return true;
        } catch (final Exception e) {
            LOG.error(MainetConstants.ERROR_OCCURED_SAVEORUPDATE, e);
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> findhomepagephotos(final long orgId, final String homepagephotos) {
        List<Object[]> photoList = new ArrayList<>();
        final Query query = createQuery(
                "select b.id from  SubLinkMaster a, SubLinkFieldDetails b,LinksMaster c where a.subLinkNameEn like :homepagephotos and  a.id =b.subLinkMaster.id and c.linkId=a.linksMaster.linkId and a.isDeleted='N' and b.isDeleted='N' and b.orgId.orgid=?1 and c.chekkerflag='Y' and c.isDeleted='N'");
        query.setParameter(1, orgId);
        query.setParameter("homepagephotos", homepagephotos);

        final List<Long> photoId = query.getResultList();

        if (photoId != null && !photoId.isEmpty()) {
            final Query query2 = createQuery(
                    "select h.profile_img_path, h.txt_01_en , h.txt_01_rg from SubLinkFieldDetailsHistory h where h.chekkerflag='Y' and h.id in ?1 and (h.hId) in (select max(eh.hId) from SubLinkFieldDetailsHistory eh where eh.chekkerflag='Y' and eh.id in ?1 group by eh.id) order by h.updatedDate desc");
            query2.setParameter(1, photoId);
            photoList = query2.getResultList();
        }
        return photoList;

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> findhomepagevideos(final long orgId, final String videogallery) {
        List<Object[]> videoList = new ArrayList<>();
        final Query query = createQuery(
                "select b.id from  SubLinkMaster a, SubLinkFieldDetails b where a.subLinkNameEn like :videogallery and  a.id =b.subLinkMaster.id and a.isDeleted='N' and b.isDeleted='N' and b.orgId.orgid=?1 ");
        query.setParameter(1, orgId);
        query.setParameter("videogallery", videogallery);

        final List<Long> videoId = query.getResultList();
        if (videoId != null && !videoId.isEmpty()) {
            final Query query2 = createQuery(
                    "select h.att_video_path, h.txt_01_en , h.txt_01_rg , h.att_01 from SubLinkFieldDetailsHistory h where h.chekkerflag='Y' and h.id in ?1 and (h.hId) in (select max(eh.hId) from SubLinkFieldDetailsHistory eh where eh.chekkerflag='Y' and eh.id in ?1 group by eh.id) order by h.date_01 desc");
            query2.setParameter(1, videoId);
            videoList = query2.getResultList();
        }
        return videoList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SubLinkFieldDetailsHistory> getAllhtml(final long orgId, final String CK_Editer3) {
        List<SubLinkFieldDetailsHistory> detailHistory = null;
        try {
            final Query query = createQuery("select subLinkMaster from SubLinkMaster subLinkMaster" +
                    " where  subLinkMaster.orgId= :orgId and subLinkMaster.isDeleted= :active and subLinkMaster.subLinkNameEn like :subLinkname order by subLinkMaster.id asc");
            query.setParameter("orgId", UserSession.getCurrent().getOrganisation());
            query.setParameter("active", "N");
            query.setParameter("subLinkname", CK_Editer3);
            final List<SubLinkMaster> subLinkMaster = query.getResultList();
            List<Long> detailId = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(subLinkMaster)) {
                for (SubLinkFieldDetails obj : subLinkMaster.get(0).getSubLinkFieldDetails()) {
                    detailId.add(obj.getId());
                }
            }
            if (detailId != null && !detailId.isEmpty()) {
                final Query query2 = createQuery(
                        "select h from SubLinkFieldDetailsHistory h where h.chekkerflag='Y' and h.id in ?1 and (h.hId) in (select max(eh.hId) from SubLinkFieldDetailsHistory eh where eh.chekkerflag='Y' and eh.id in ?1 group by eh.id) order by h.updatedDate desc");
                query2.setParameter(1, detailId);
                detailHistory = query2.getResultList();
            }

        } catch (final Exception e) {
            LOG.error("Error occured during getAllhtml", e);
            return null;
        }
        return detailHistory;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<SubLinkFieldDetailsHistory> getAllhtmlByOrgId(final Organisation organisation, final String CK_Editer3) {
        List<SubLinkFieldDetailsHistory> detailHistory = null;
        try {
            final Query query = createQuery("select subLinkMaster from SubLinkMaster subLinkMaster" +
                    " where  subLinkMaster.orgId= :orgId and subLinkMaster.isDeleted= :active and subLinkMaster.subLinkNameEn like :subLinkname order by subLinkMaster.id asc");
            query.setParameter("orgId", organisation);
            query.setParameter("active", "N");
            query.setParameter("subLinkname", CK_Editer3);
            final List<SubLinkMaster> subLinkMaster = query.getResultList();
            List<Long> detailId = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(subLinkMaster)) {
                for (SubLinkFieldDetails obj : subLinkMaster.get(0).getSubLinkFieldDetails()) {
                    detailId.add(obj.getId());
                }
            }
            if (detailId != null && !detailId.isEmpty()) {
                final Query query2 = createQuery(
                        "select h from SubLinkFieldDetailsHistory h where h.chekkerflag='Y' and h.id in ?1 and (h.hId) in (select max(eh.hId) from SubLinkFieldDetailsHistory eh where eh.chekkerflag='Y' and eh.id in ?1 group by eh.id) order by h.updatedDate desc");
                query2.setParameter(1, detailId);
                detailHistory = query2.getResultList();
            }

        } catch (final Exception e) {
            LOG.error("Error occured during getAllhtml", e);
            return null;
        }
        return detailHistory;
    }

    @Override
    public Long getNewsLetterId(Organisation orgId, final String CK_Editer3) {
        try {
            final Query query = createQuery(
                    " select max(d.id) from SubLinkFieldDetails d where d.orgId=:orgId and d.isDeleted=:isDeleted and d.subLinkMaster.id=( select m.id from SubLinkMaster m where m.orgId=:orgId and m.subLinkNameEn like :section and m.isDeleted=:isDeleted ) ");
            query.setParameter("orgId", orgId);
            query.setParameter("isDeleted", "N");
            query.setParameter("section", CK_Editer3);
            final List<Long> subLinkMaster = query.getResultList();

            if (null == subLinkMaster || subLinkMaster.isEmpty()) {
                return null;
            }
            return subLinkMaster.get(0);
        } catch (final Exception e) {
            LOG.error("Error occured during getNewsLetterId", e);
            return null;
        }
    }

    @Override
    public List<SubLinkFieldDetails> getNewsLetter(Long newsletterId) {
        try {
            final Query query = createQuery(
                    " select d from SubLinkFieldDetails d where d.isDeleted=:isDeleted and d.id = :newsletterId ");
            query.setParameter("newsletterId", newsletterId);
            query.setParameter("isDeleted", "N");
            final List<SubLinkFieldDetails> subLinkMaster = query.getResultList();

            if (null == subLinkMaster || subLinkMaster.isEmpty()) {
                return null;
            }
            return subLinkMaster;
        } catch (final Exception e) {
            LOG.error("Error occured during getNewsLetter", e);
            return null;
        }
    }

    @Override
    public SubLinkMaster findSublinksbyename(final String subLinkname,
            final String activeStatus) {
        try {

            final Query query = createQuery("select subLinkMaster from SubLinkMaster subLinkMaster" +
                    " where  subLinkMaster.orgId= :orgId and subLinkMaster.isDeleted= :active and subLinkMaster.subLinkNameEn like :subLinkname order by subLinkMaster.id asc");
            query.setParameter("orgId", UserSession.getCurrent().getOrganisation());
            query.setParameter("active", activeStatus);
            query.setParameter("subLinkname", subLinkname);
            final SubLinkMaster subLinkMaster = (SubLinkMaster) query.getSingleResult();

            final ListIterator<SubLinkFieldMapping> listIterator = subLinkMaster.getSubLinkFieldMappings().listIterator();
            while (listIterator.hasNext()) {
                listIterator.next();
            }

            final ListIterator<SubLinkFieldDetails> listIterator2 = subLinkMaster.getSubLinkFieldDetails().listIterator();
            while (listIterator2.hasNext()) {
                listIterator2.next();
            }
            return subLinkMaster;
        } catch (final Exception e) {
            LOG.error("Error occured during findSublinksbyename for sublink: " + subLinkname, e);
            return null;
        }

    }

	@Override
	public Double getSubLinkFieldMaxOrder(Long id, Long orgid, String zero) {
		try {
            final Query query = createQuery(
                    " select MAX(d.subLinkFieldDtlOrd) from SubLinkFieldDetails d where d.isDeleted= ?1 and d.subLinkMaster.id = ?2 and d.orgId.orgid = ?3 ");
            query.setParameter(1, zero);
            query.setParameter(2, id);
            query.setParameter(3, orgid);
            return  (Double) query.getSingleResult();
        } catch (final Exception e) {
            LOG.error("Error occured during getSubLinkFielsDetaildById", e);
            return null;
        }
	}

	@Override
	public SubLinkMaster findById(long subLinkId, String activeStatus) {		

        String isArchive = null;
        try {

            final Query query = createQuery("select subLinkMaster from SubLinkMaster subLinkMaster" +
                    " where  subLinkMaster.isDeleted= :active and subLinkMaster.id=:id order by subLinkMaster.id asc");
            /* subLinkMaster.orgId= :orgId and query.setParameter("orgId", UserSession.getCurrent().getOrganisation()); */
            query.setParameter("active", activeStatus);
            query.setParameter("id", subLinkId);
            final SubLinkMaster subLinkMaster = (SubLinkMaster) query.getSingleResult();
            
            isArchive = subLinkMaster.getIsArchive();

            final ListIterator<SubLinkFieldMapping> listIterator = subLinkMaster.getSubLinkFieldMappings().listIterator();
            while (listIterator.hasNext()) {
                listIterator.next();
            }

            List<Long> subLinkDetailId = new ArrayList();
            for (SubLinkFieldDetails subLinkFieldDetails : subLinkMaster.getSubLinkFieldDetails()) {
                subLinkDetailId.add(subLinkFieldDetails.getId());
            }
            if (subLinkDetailId != null && !subLinkDetailId.isEmpty()) {

                final StringBuilder queryAppender = new StringBuilder(
                        "select h from SubLinkFieldDetailsHistory h where h.chekkerflag='Y' ");

                if (isArchive != null && isArchive.equalsIgnoreCase("Y")) {
                    
                    queryAppender
                    .append("and h.issueDate <= ?2 ");
                    queryAppender
                    .append("and h.validityDate >= ?2 ");
                }

                queryAppender
                        .append("and (h.hId) in (select max(hId) from SubLinkFieldDetailsHistory eh where eh.chekkerflag='Y' and eh.id in ?1 ");                
                
                queryAppender.append("group by eh.id) order by h.subLinkFieldDtlOrd desc ");
                
                final Query query2 = createQuery(queryAppender.toString());
                query2.setParameter(1, subLinkDetailId);
                if (isArchive != null && isArchive.equalsIgnoreCase("Y")) {
                    query2.setParameter(2, new Date());
                }
                          
                subLinkMaster.setDetailsHistories(query2.getResultList());
            }
            return subLinkMaster;
        } catch (final Exception e) {
            LOG.error(MainetConstants.FINDBYID_ERROR, e);
            return null;
        }
	}

	@Override
	public List<SubLinkFieldDetailsHistory> getAllDetailHistorysByDetailId(Long detailId, String orderByColumn,
			Organisation organisation) {
		List<SubLinkFieldDetailsHistory> historys = new ArrayList<SubLinkFieldDetailsHistory>(); 
		 try {
			 StringBuilder queryString = new StringBuilder();
			 queryString.append(" select h from SubLinkFieldDetailsHistory h where h.isDeleted=:isDeleted and h.id = :detailId  ");//and h.orgId= :orgId
	            if(orderByColumn == null) {
	            	queryString.append(" order by h.hId desc ");
	            }
	            final Query query = createQuery(queryString.toString());
	            query.setParameter("detailId", detailId);
	            query.setParameter("isDeleted",MainetConstants.FlagN);
	           // query.setParameter("orgId",organisation);
	            
	            historys = query.getResultList();
	            for (SubLinkFieldDetailsHistory subLinkFieldDetails : historys) {
	            	Hibernate.initialize(subLinkFieldDetails.getUserId());
	        		Hibernate.initialize(subLinkFieldDetails.getUpdatedBy());
	            }
	            return historys;
	        } catch (final Exception e) {
	            LOG.error("Error occured during getAllDetailHistorysByDetailId", e);
	            return historys;
	        }
	}

	@Override
	public SubLinkFieldDetails getSubLinkFieldDetail(Long detailId) {
        try {
            final Query query = createQuery("select s from SubLinkFieldDetails s where  s.id=:id ");
            query.setParameter("id", detailId);
            final SubLinkFieldDetails subLinkFieldDetail = (SubLinkFieldDetails)query.getSingleResult();
            Hibernate.initialize(subLinkFieldDetail.getUserId());
            return subLinkFieldDetail;
        } catch (final Exception e) {
            LOG.error("Error occured during getSubLinkFieldDetail ", e);
            return null;
        }
    }

	

}
