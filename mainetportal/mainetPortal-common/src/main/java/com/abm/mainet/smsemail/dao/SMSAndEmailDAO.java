package com.abm.mainet.smsemail.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.TemplateLookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.smsemail.domain.SMSAndEmailInterface;
import com.abm.mainet.smsemail.domain.SmsAndMailTemplate;

/**
 *
 * @author Kavali.Kiran
 *
 */
@Repository
public class SMSAndEmailDAO extends AbstractDAO<SMSAndEmailInterface> implements ISMSAndEmailDAO {

    public static final Logger LOG = Logger.getLogger(SMSAndEmailDAO.class);

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.ISMSAndEmailDAO#saveMessageTemplate(com.abm.mainet.domain.core.SMSAndEmailInterface)
     */
    @Override
    public boolean saveMessageTemplate(final SMSAndEmailInterface entity) {
        try {
            entityManager.merge(entity);
            return true;
        }

        catch (final Exception e) {
            LOG.error(MainetConstants.ERROR_OCCURED, e);

            return false;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public TemplateLookUp getTemplateFromDBForTran(final String deptCode, final String menuURL, final String type,
            final Organisation organisation, final int langId) {
        final TemplateLookUp lookup = new TemplateLookUp();

        final Query query = createQuery(
                "select master from  SMSAndEmailInterface master  join master.smfid smfId  join master.dpDeptid d  join master.smsAndmailTemplate s where "
                        + "UPPER(smfId.smfaction)= UPPER(?1) "
                        + "and UPPER(d.dpDeptcode)= UPPER(?2) and master.orgId=?3 "
                        + "and s.orgId=?4  and s.messageType=?5 and master.isDeleted='N' order by master.seId desc ");

        query.setParameter(1, menuURL);
        query.setParameter(2, deptCode);
        query.setParameter(3, organisation);
        query.setParameter(4, organisation);
        query.setParameter(5, type);

        final List<SMSAndEmailInterface> interfac = query.getResultList();

        for (final SMSAndEmailInterface fae : interfac) {
            fae.getSmsAndmailTemplate().getSeId();
        }

        if ((interfac != null) && !interfac.isEmpty()) {
            final SmsAndMailTemplate template = interfac.get(0).getSmsAndmailTemplate();
            if (langId == MainetConstants.ENGLISH) {
            	
            	if(template.getExtTemplate() != null) {
            		lookup.setTemplateId(template.getExtTemplate()); //Added as part of New Mobile Seva
            	}
                lookup.setMailBody(template.getMailBody());

                lookup.setMailSubject(template.getMailSub());
                lookup.setSmsBody(template.getSmsBody());
                lookup.setAlertType(interfac.get(0).getAlertType());
                return lookup;
            } else {
            	if(template.getExtTemplateReg() != null) {
            		lookup.setTemplateId(template.getExtTemplateReg()); //Added as part of New Mobile Seva
            	}
                if ((template.getMailBodyReg() != null) && !template.getMailBodyReg().isEmpty()) {
                    lookup.setMailBody(template.getMailBodyReg());
                } else {
                    lookup.setMailBody(template.getMailBody());
                }
                if ((template.getMailSubReg() != null) && !template.getMailSubReg().isEmpty()) {
                    lookup.setMailSubject(template.getMailSubReg());
                } else {
                    lookup.setMailSubject(template.getMailSub());
                }
                if ((template.getSmsBodyReg() != null) && !template.getSmsBodyReg().isEmpty()) {
                    lookup.setSmsBody(template.getSmsBodyReg());
                } else {
                    lookup.setSmsBody(template.getSmsBody());
                }

                lookup.setAlertType(interfac.get(0).getAlertType());
                return lookup;
            }
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean checkExistanceForEdit(final SMSAndEmailInterface entity) {

        final Query query = createQuery("select master from SMSAndEmailInterface master "
                + " where UPPER(master.smfid.smfid)=UPPER(?1) and UPPER(master.dpDeptid.dpDeptid)=UPPER(?2) and master.seId=master.smsAndmailTemplate.seId "
                + " and master.orgId=?3  and master.smsAndmailTemplate.messageType=?4 and master.isDeleted='N' "
                + " order by master.seId desc");

        query.setParameter(1, entity.getSmfid().getSmfid());
        query.setParameter(2, entity.getDpDeptid().getDpDeptid());
        query.setParameter(3, UserSession.getCurrent().getOrganisation());
        query.setParameter(4, entity.getSmsAndmailTemplate().getMessageType());

        final List<SMSAndEmailInterface> interfac = query.getResultList();

        if ((interfac != null) && !interfac.isEmpty()) {
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public SMSAndEmailInterface getTemplateFromDBForTran(final SMSAndEmailInterface interfaces, final Organisation organisation) {

        final Query query = createQuery("select master from SMSAndEmailInterface master "
                + " where UPPER(master.smfid.smfaction)=UPPER(?1) and UPPER(master.dpDeptid.dpDeptcode)=UPPER(?2) and master.seId=master.smsAndmailTemplate.seId "
                + " and master.orgId=?3  and master.smsAndmailTemplate.messageType=?4 "
                + " order by master.seId desc");

        query.setParameter(1, interfaces.getSmfid().getSmfid());
        query.setParameter(2, interfaces.getDpDeptid().getDpDeptid());
        query.setParameter(3, UserSession.getCurrent().getOrganisation());
        query.setParameter(4, interfaces.getSmsAndmailTemplate().getMessageType());

        final List<SMSAndEmailInterface> interfac = query.getResultList();

        for (final SMSAndEmailInterface fae : interfac) {
            fae.getSmsAndmailTemplate().getSeId();
        }
        if ((interfac != null) && !interfac.isEmpty()) {
            return interfac.get(0);
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SMSAndEmailInterface> searchTemplates(final SMSAndEmailInterface entity) {

        final Query query = createQuery(buildDynamicQuery(entity));

        query.setParameter(1, UserSession.getCurrent().getOrganisation().getOrgid());

        if (entity.getDpDeptid().getDpDeptid() != 0) {
            query.setParameter(2, entity.getDpDeptid().getDpDeptid());
        }
        if ((entity.getServiceId().getServiceId() != null) && (entity.getServiceId().getServiceId() > 0)) {
            query.setParameter(3, entity.getServiceId().getServiceId());
        }
        if (entity.getSmfid().getSmfid() != 0) {
            query.setParameter(4, entity.getSmfid().getSmfid());
        }
        if (!entity.getSmsAndmailTemplate().getMessageType().equals(MainetConstants.BLANK)) {
            query.setParameter(5, entity.getSmsAndmailTemplate().getMessageType());
        }
        if ((entity.getAlertType() != null) && !entity.getAlertType().equals(MainetConstants.BLANK)) {
            query.setParameter(6, entity.getAlertType());
        }
        final List<SMSAndEmailInterface> smsEmailTempList = query.getResultList();

        return smsEmailTempList;

    }

    private String buildDynamicQuery(final SMSAndEmailInterface entity) {

        final StringBuilder sb = new StringBuilder();
        sb.append(
                "select master from SMSAndEmailInterface master where master.isDeleted='N' and master.seId=master.smsAndmailTemplate.seId and master.orgId.orgid=?1 ");

        if (entity.getDpDeptid().getDpDeptid() != 0) {
            sb.append(" and master.dpDeptid.dpDeptid =?2 ");
        }
        if (entity.getServiceId().getServiceId() == null) {
            sb.append(" and master.serviceId.serviceId is null ");
        } else if (entity.getServiceId().getServiceId() > 0) {
            sb.append(" and master.serviceId.serviceId =?3 ");
        }

        if (entity.getSmfid().getSmfid() != 0) {
            sb.append(" and master.smfid.smfid =?4 ");
        }
        if (!entity.getSmsAndmailTemplate().getMessageType().equals(MainetConstants.BLANK)) {
            sb.append(" and master.smsAndmailTemplate.messageType=?5 ");
        }
        if ((entity.getAlertType() != null) && !entity.getAlertType().equals(MainetConstants.BLANK)) {
            sb.append("and master.alertType=?6 ");
        }
        return sb.toString();

    }

    @Override
    public void deleteTemplate(final Long seId) {
        final Query query = createQuery("update SMSAndEmailInterface set isDeleted='Y' where seId=?1");
        query.setParameter(1, seId);
        query.executeUpdate();
    }

    @Override
    public SMSAndEmailInterface getTemplateById(final Long templateId, final Long orgId) {
        final Query query = createQuery(
                "select master from SMSAndEmailInterface master where master.seId=?1 and master.orgId.orgid=?2");
        query.setParameter(1, templateId);
        query.setParameter(2, orgId);
        final SMSAndEmailInterface entity = (SMSAndEmailInterface) query.getSingleResult();
        return entity;

    }

    @Override
    public SMSAndEmailInterface getTemplateFromDBForTran(final SMSAndEmailInterface interfaces) {

        final Query query = createQuery("select master from SMSAndEmailInterface master "
                + " where master.smfid.smfid= ?1 and master.dpDeptid.dpDeptid= ?2 "
                + " and master.orgId=?3  and master.smsAndmailTemplate.messageType=?4 "
                + " order by master.seId desc");

        query.setParameter(1, interfaces.getSmfid().getSmfid());
        query.setParameter(2, interfaces.getDpDeptid().getDpDeptid());
        query.setParameter(3, UserSession.getCurrent().getOrganisation());
        query.setParameter(4, interfaces.getSmsAndmailTemplate().getMessageType());

        @SuppressWarnings("unchecked")
        final List<SMSAndEmailInterface> interfac = query.getResultList();
        for (final SMSAndEmailInterface fae : interfac) {
            fae.getSmsAndmailTemplate().getSeId();
        }
        if ((interfac != null) && !interfac.isEmpty()) {
            return interfac.get(0);
        }
        return null;

    }

    @Override
    public TemplateLookUp getTemplateFromDBForTran(final String deptCode, final String menuURL, final String type,
            final String transType,
            final int langId, final long org) {

        TemplateLookUp lookup = null;

        final Query query = createQuery(
                "select master from  SMSAndEmailInterface master  join master.smfid smfId  join master.dpDeptid d  join master.smsAndmailTemplate s where "
                        + "UPPER(smfId.smfaction)= UPPER(?1) "
                        + "and UPPER(d.dpDeptcode)= UPPER(?2) and master.orgId.orgid=?3 "
                        + "and s.orgId.orgid=?4  and s.messageType=?5 and master.tranOrNonTran=?6 order by master.seId desc ");

        query.setParameter(1, menuURL);
        query.setParameter(2, deptCode);
        query.setParameter(3, org);
        query.setParameter(4, org);
        query.setParameter(5, type);
        query.setParameter(6, transType);

        @SuppressWarnings("unchecked")
        final List<SMSAndEmailInterface> interfac = query.getResultList();

        if ((interfac != null) && !interfac.isEmpty()) {
            for (final SMSAndEmailInterface fae : interfac) {
                fae.getSmsAndmailTemplate().getSeId();
            }

            final SmsAndMailTemplate template = interfac.get(0).getSmsAndmailTemplate();
            if (langId == MainetConstants.ENGLISH) {
                lookup = new TemplateLookUp();
                lookup.setMailBody(template.getMailBody());

                lookup.setMailSubject(template.getMailSub());
                lookup.setSmsBody(template.getSmsBody());
                lookup.setAlertType(interfac.get(0).getAlertType());
                return lookup;
            } else {
                lookup = new TemplateLookUp();
                lookup.setMailBody(template.getMailBodyReg() != null ? template.getMailBodyReg() : template.getMailBody());
                lookup.setMailSubject(template.getMailSubReg() != null ? template.getMailSubReg() : template.getMailSub());
                lookup.setSmsBody(template.getSmsBodyReg() != null ? template.getSmsBodyReg() : template.getSmsBody());
                lookup.setAlertType(interfac.get(0).getAlertType());

                return lookup;
            }

        }
        return lookup;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> findAllEvents(String menuURL, String activeStatus) {
        Query eventQuery = createQuery(
                " SELECT s.smfid, s.smfname,s.smfname_mar,s.smfdescription,s.smfaction FROM SystemModuleFunction s WHERE LOWER(s.smfaction) like concat('%',LOWER( ?1 )) and s.isdeleted = ?2 order by s.smfname ");
        eventQuery.setParameter(1, menuURL);
        eventQuery.setParameter(2, activeStatus);
        return eventQuery.getResultList();
    }
}
