package com.abm.mainet.common.entitlement.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.domain.SystemModuleFunction;
import com.abm.mainet.common.entitlement.domain.RoleEntitlement;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

@Repository
public class EntitlementDao extends AbstractDAO<GroupMaster>
        implements IEntitlementDao {

    protected final Logger LOGGER = Logger
            .getLogger(this.getClass());

    @SuppressWarnings("unchecked")
    @Override
    public Set<SystemModuleFunction> getMenuMasterList(
            final String orderByClause) throws RuntimeException {

        final String strQuery = "Select s from SystemModuleFunction s where s.moduleFunction is null and s.isdeleted= ?1 "
                + "order by s." + orderByClause
                + " asc";
        final Query query = createQuery(strQuery);
        query.setParameter(1, MainetConstants.MENU._0);
        final Set<SystemModuleFunction> masters = new LinkedHashSet<SystemModuleFunction>(
                query.getResultList());
        return masters;

    }

    @Override
    public SystemModuleFunction findByTmId(final Long tmId)
            throws RuntimeException {
        return entityManager
                .find(SystemModuleFunction.class, tmId);
    }

    @Override
    public boolean save(final GroupMaster groupMaster)
            throws RuntimeException {
        entityManager.persist(groupMaster);
        return true;
    }

    // Recursive method for fething child
    public void fetchChild(
            final Set<SystemModuleFunction> hierarchicals) {

        Set<SystemModuleFunction> childSet = null;
        if ((hierarchicals != null)
                && !hierarchicals.isEmpty()) {
            for (final SystemModuleFunction mas : hierarchicals) {
                childSet = mas.getMenuHierarchicalList();
                fetchChild(childSet);
            }

        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<RoleEntitlement> getExistTemplateParent(
            final Long roleId, final List<String> flagList, final Long orgId,
            final String orderByClause) throws RuntimeException {

        final String StrQuery = "Select r from RoleEntitlement r where r.groupMaster.gmId = ?1 and r.parentId = ?2 "
                + "and r.entitle.smfflag in (?3)  "
                + "and r.organisation.orgid = ?4 and  r.isActive=?5  order by r.entitle." + orderByClause + " asc";

        final Query query = createQuery(StrQuery);
        query.setParameter(1, roleId);
        query.setParameter(2, 0L);
        query.setParameter(3, flagList);
        query.setParameter(4, orgId);
        query.setParameter(5, MainetConstants.MENU._0);

        final Set<RoleEntitlement> masters = new LinkedHashSet<RoleEntitlement>(
                query.getResultList());

        return masters;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<RoleEntitlement> getExistTemplateChild(
            final Long roleId, final List<String> flagList, final Long orgId,
            final String orderByClause) throws RuntimeException {

        final String sqlquery = "Select r from RoleEntitlement r where r.groupMaster.gmId = ?1 and "
                + "r.parentId <> ?2 and r.entitle.smfflag in (?3)  and r.organisation.orgid = ?4 and r.isActive= ?5 "
                + "order by r.entitle." + orderByClause
                + "  asc";

        final Query query = createQuery(sqlquery);

        query.setParameter(1, roleId);
        query.setParameter(2, 0L);
        query.setParameter(3, flagList);
        query.setParameter(4, orgId);
        query.setParameter(5, MainetConstants.MENU._0);
        final Set<RoleEntitlement> masters = new LinkedHashSet<RoleEntitlement>(
                query.getResultList());

        return masters;
    }
    
	//119534  New method added that will return entitelment base on smParam1 flag is Y
    @SuppressWarnings("unchecked")
    @Override
    public Set<RoleEntitlement> getExistTemplateChildByFlag(
            final Long roleId, final List<String> flagList, final Long orgId,
            final String orderByClause) throws RuntimeException {

        final String sqlquery = "Select r from RoleEntitlement r where r.groupMaster.gmId = ?1 and "
                + "r.parentId <> ?2 and r.entitle.smfflag in (?3)  and r.organisation.orgid = ?4 and r.isActive= ?5 "
                + "and r.entitle.smParam1='Y' order by r.entitle." + orderByClause
                + "  asc";

        final Query query = createQuery(sqlquery);

        query.setParameter(1, roleId);
        query.setParameter(2, 0L);
        query.setParameter(3, flagList);
        query.setParameter(4, orgId);
        query.setParameter(5, MainetConstants.MENU._0);
        Set<RoleEntitlement> masters = new LinkedHashSet<RoleEntitlement>(
                query.getResultList());

        Set<RoleEntitlement> newHashSet = new LinkedHashSet<RoleEntitlement>();
        
        masters.forEach(dto->{
        	dto.getEntitle().getMenuHierarchicalList().forEach(dtos->{
        		int count=0;
        		for(RoleEntitlement entitlement: dtos.getEntitlements()) {
        			if(entitlement.getIsActive().equals("1") && count == 0){
        				newHashSet.add(entitlement);
        				count=1;
        			}
        		}
        	});
        });
        masters.addAll(newHashSet);
        
        return masters;
    }

    @Override
    public boolean saveNode(
            final SystemModuleFunction entitlement)
            throws RuntimeException {

        entityManager.persist(entitlement);
        return true;

    }

    @Override
    public boolean saveRoleEntitlement(
            RoleEntitlement roleEntitlement)
            throws RuntimeException {

        roleEntitlement = entityManager
                .merge(roleEntitlement);
        entityManager.persist(roleEntitlement);
        return true;

    }

    @Override
    public RoleEntitlement findByRoleAndEntitleId(
            final Long roleId, final Long entitleId)
            throws RuntimeException {

        final Query query = createQuery(
                "select re from RoleEntitlement re where re.groupMaster.gmId= ?1 and re.entitle.smfid= ?2");
        query.setParameter(1, roleId);
        query.setParameter(2, entitleId);

        @SuppressWarnings("unchecked")
        final List<RoleEntitlement> entitlements = query
                .getResultList();

        if ((entitlements == null)
                || entitlements.isEmpty()) {
            return null;
        } else {
            return entitlements.get(0);
        }

    }

    @Override
    public List<LookUp> getLinks(final Long roleId,
            final List<String> flagList, final Long orgId,
            final String orderByClause) throws RuntimeException {

        final String strQuery = "Select r.entitle.smfname,r.entitle.smfname_mar,r.entitle.smfaction from RoleEntitlement r where r.groupMaster.gmId = ?1 and r.parentId <> ?2 "
                + "and r.entitle.smfflag in (?3)  "
                + "and r.organisation.orgid = ?4 and  r.isActive= ?5  order by r.entitle.smfid asc";

        final Query query = createQuery(strQuery);

        query.setParameter(1, roleId);
        query.setParameter(2, 0L);
        query.setParameter(3, flagList);
        query.setParameter(4, orgId);
        query.setParameter(5, MainetConstants.MENU._0);

        LookUp lookUp = null;
        final List<LookUp> lookUps = new ArrayList<>();

        @SuppressWarnings("unchecked")
        final List<Object[]> list = query.getResultList();
        for (final Object[] objects : list) {
            lookUp = new LookUp();
            lookUp.setDescLangFirst(objects[0].toString());
            lookUp.setDescLangSecond(objects[1].toString());
            lookUp.setOtherField(objects[2].toString());
            lookUps.add(lookUp);
        }
        return lookUps;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<Long, String> getGroupList(final Long orgId) {

        final Map<Long, String> list = new LinkedHashMap<>();
        final Query query = createQuery(
                "select rl from GroupMaster rl where rl.orgId.orgid= ?1 and rl.grStatus= ?2");

        query.setParameter(1, orgId);
        query.setParameter(2, MainetConstants.MENU.A);

        final List<GroupMaster> master = query.getResultList();

        if ((master != null) && !master.isEmpty()) {
            for (final GroupMaster mas : master) {
                list.put(mas.getGmId(), mas.getGrCode());
            }

        }
        return list;
    }

    @Override
    public List<SystemModuleFunction> getSearchdata(
            final Long gmId, final String text, final Long orgId) {

        final StringBuilder queryBuilder = new StringBuilder(
                "Select e.smfaction,e.smfname,e.smfname_mar from RoleEntitlement r join r.entitle e  join r.groupMaster gm where "
                        + "r.organisation.orgid=?1 and  gm.gmId = ?2 and e.smfaction not in (?3) and ");
        if (UserSession.getCurrent().getLanguageId() == 1) {
            queryBuilder.append(
                    " UPPER(e.smfname) like concat('%',UPPER( ?4 ),'%')");
        } else {
            queryBuilder.append(
                    " UPPER(e.smfname_mar) like concat('%',UPPER( ?4 ),'%')");
        }

        final Query query = createQuery(queryBuilder.toString());
        query.setParameter(1, orgId);
        query.setParameter(2, gmId);
        query.setParameter(3,
                Arrays.asList(new String[] {
                        "CitizenRegistration.html",
                        "PasswordReset.html" }));
        query.setParameter(4, text);
        @SuppressWarnings("unchecked")
        final List<Object[]> accessList = query.getResultList();
        SystemModuleFunction entitlement = null;
        final List<SystemModuleFunction> entitlements = new ArrayList<>();
        for (final Object[] object : accessList) {

            entitlement = new SystemModuleFunction();
            entitlement.setSmfaction(object[0].toString());
            if (UserSession.getCurrent()
                    .getLanguageId() == 1) {
                entitlement
                        .setSmfname(object[1].toString());
            } else {
                entitlement.setSmfname_mar(
                        object[2].toString());
            }

            entitlements.add(entitlement);
        }

        return entitlements;
    }

    @Override
    public Long getGroupId(final String groupCode, final Long orgId) {

        final Query query = createQuery(
                "select gm from GroupMaster gm where gm.grStatus= ?1 and gm.orgId.orgid = ?2 and UPPER(gm.grCode) = UPPER( ?3 )");

        query.setParameter(1, MainetConstants.MENU.A);
        query.setParameter(2, orgId);
        query.setParameter(3, groupCode);

        final List list = query.getResultList();

        if ((list == null) || list.isEmpty()) {
            return null;
        } else {
            final GroupMaster groupMaster = (GroupMaster) list
                    .get(0);
            return groupMaster.getGmId();
        }

    }

    @Override
    public SystemModuleFunction updateNode(
            SystemModuleFunction data) {
        data = entityManager.merge(data);
        return data;
    }

    @Override
    public boolean getRoleCodeFromGrpMst(final long orgid,
            final String roleCode) {
        final Query query = createQuery(
                "select gm from GroupMaster gm where gm.orgId.orgid = ?1 and UPPER(gm.grCode) = UPPER( ?2 )");

        query.setParameter(1, orgid);
        query.setParameter(2, roleCode);

        @SuppressWarnings("unchecked")
        final List<GroupMaster> list = query.getResultList();

        if ((list == null) || list.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public List<GroupMaster> getGroupListForNewActiveNode(
            final long orgid) {
        final Query query = createQuery(
                "select rl from GroupMaster rl where rl.orgId.orgid= ?1 and rl.grStatus= ?2");

        query.setParameter(1, orgid);
        query.setParameter(2, MainetConstants.MENU.A);

        @SuppressWarnings("unchecked")
        final List<GroupMaster> master = query.getResultList();
        return master;
    }

    @Override
    public Map<Long, String> getRoleEntitleIds(final Long roleId,
            final long orgid) {

        final Query query = createQuery(
                "select re from RoleEntitlement re where re.groupMaster.gmId= ?1 and re.organisation.orgid = ?2 and re.isActive= ?3");
        query.setParameter(1, roleId);
        query.setParameter(2, orgid);
        query.setParameter(3, MainetConstants.MENU._0);

        @SuppressWarnings("unchecked")
        final List<RoleEntitlement> entitlements = query
                .getResultList();

        final Map<Long, String> list = new LinkedHashMap<>();
        if ((entitlements != null)
                && !entitlements.isEmpty()) {
            final List<Long> parentId = new ArrayList<>(0);

            for (final RoleEntitlement finalre : entitlements) {
                parentId.add(finalre.getParentId());
            }

            for (final RoleEntitlement rle : entitlements) {
                if (!parentId.contains(
                        rle.getEntitle().getSmfid())) {
                    list.put(rle.getEntitle().getSmfid(),
                            rle.getEntitle().getSmfflag());
                }
            }
        }

        return list;
    }

    @Override
    public List<RoleEntitlement> getExistTemplateChild(
            final Long editedRoleId, final long orgid) {
        final Query query = createQuery(
                "select re from RoleEntitlement re where re.groupMaster.gmId= ?1 and re.organisation.orgid = ?2 and re.isActive= ?3");
        query.setParameter(1, editedRoleId);
        query.setParameter(2, orgid);
        query.setParameter(3, MainetConstants.MENU._0);

        @SuppressWarnings("unchecked")
        final List<RoleEntitlement> entitlements = query
                .getResultList();
        return entitlements;
    }

    @Override
    public void getExistTemplateChild(final List<Long> parentids,
            final long orgid, long roleId) {

        final Query query = createQuery(
                "UPDATE RoleEntitlement re SET re.isActive ='1' WHERE re.entitle.smfid in (?1) and re.organisation.orgid= ?2 and "
                        + "re.groupMaster.gmId=?3");
        query.setParameter(1, parentids);
        query.setParameter(2, orgid);
        query.setParameter(3, roleId);
        query.executeUpdate();
    }

    @Override
    public void getExistTemplateChild(
            final Set<RoleEntitlement> roleEntitlements) {
        for (final RoleEntitlement re : roleEntitlements) {
            entityManager.persist(re);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.authentication.role.dao.IEntitlementDao# updateRecord(java.lang.String, java.lang.Long)
     */
    @Override
    public boolean updateRecord(final List<String> addElement,
            final List<String> deleteElement) {

        String type = null;
        if ((addElement != null) && !addElement.isEmpty()) {

            for (final String addItem : addElement) {
                final StringBuilder queryAppender = new StringBuilder(
                        "UPDATE RoleEntitlement re SET ");
                final String[] subIds = addItem.split("/");
                // type=null;
                switch (subIds[0]) {
                case MainetConstants.MENU.A:
                    type = "re.add";
                    break;
                case MainetConstants.MENU.E:
                    type = "re.update";
                    break;
                case MainetConstants.MENU.D:
                    type = "re.delete";
                    break;
                }
                queryAppender.append(type);
                queryAppender.append(
                        " = 'Y' where  re.roleEtId =?1 ");
                final Query query = createQuery(
                        queryAppender.toString());
                query.setParameter(1,
                        Long.valueOf(subIds[1]));
                query.executeUpdate();
            }

        }

        if ((deleteElement != null)
                && !deleteElement.isEmpty()) {

            for (final String removeItem : deleteElement) {
                final StringBuilder queryAppender = new StringBuilder(
                        "UPDATE RoleEntitlement re SET ");
                final String[] subIds = removeItem.split("/");

                switch (subIds[0]) {
                case MainetConstants.MENU.A:
                    type = "re.add";
                    break;
                case MainetConstants.MENU.E:
                    type = "re.update";
                    break;
                case MainetConstants.MENU.D:
                    type = "re.delete";
                    break;
                }
                queryAppender.append(type);
                queryAppender.append(
                        " = 'N' where  re.roleEtId =?1 ");
                final Query query = createQuery(
                        queryAppender.toString());
                query.setParameter(1,
                        Long.valueOf(subIds[1]));
                query.executeUpdate();
            }
        }

        return true;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.authentication.role.dao.IEntitlementDao#findByRoleId(java.lang.Long)
     */
    @Override
    public GroupMaster findByRoleId(final Long gmId, final long orgid) {
        final Query query = createQuery(
                "select g from GroupMaster g where g.gmId = ?1 and g.orgId.orgid =?2");
        query.setParameter(1, gmId);
        query.setParameter(2, orgid);
        return (GroupMaster) query.getSingleResult();
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.authentication.role.dao.IEntitlementDao#findAllRolesByDept(java.lang.Long, java.lang.Long)
     */
    @Override
    public List<Object[]> findAllRolesByOrg(final Long orgId) {
        final String StrQuery = "Select distinct g.gmId,g.grCode,g.grDescReg from RoleEntitlement r join r.groupMaster g where"
                + " g.gmId IN (select distinct e.gmid from Employee e where e.organisation.orgid =?1) and r.organisation.orgid =?1  "
                + " and  r.isActive=?2  order by g.gmId asc";

        final Query query = createQuery(StrQuery);
        query.setParameter(1, orgId);
        // query.setParameter(2, deptId);
        query.setParameter(2, MainetConstants.MENU._0);

        @SuppressWarnings("unchecked")
        final List<Object[]> list = query.getResultList();
        return list;
    }
}
