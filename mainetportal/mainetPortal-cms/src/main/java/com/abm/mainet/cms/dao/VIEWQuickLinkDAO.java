package com.abm.mainet.cms.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.cms.domain.VIEWQuickLink;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.Organisation;

/**
 * @author pabitra.raulo
 *
 */
@Repository
public class VIEWQuickLinkDAO extends AbstractDAO<VIEWQuickLink> implements IVIEWQuickLinkDAO {

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IVIEWQuickLinkDAO#getallQuicklink()
     */
	@Override
    public List<VIEWQuickLink> getallQuicklink(final Organisation org) {

        final Query query = createQuery(
                "select v from VIEWQuickLink v where v.orgid = ?1 and v.checker = ?2 order by linkOrder,sort_order asc  ");
        query.setParameter(1, org.getOrgid());
        query.setParameter(2, "Y");
        @SuppressWarnings("unchecked")
        final List<VIEWQuickLink> links = query.getResultList();

        if (links != null) {
            links.size();
        }

        return links;
    }
	
    @Override
    public List<Object[]> getallQuicklinkSuda(final Organisation org) {

        final Query query = createNativeQuery("select * from (\r\n" + 
        		"select\r\n" + 
        		"FN_GET_ROWNUM() as ROW_NUM1_84_, s.CHEKER_FLAG  as Cheker2_84_,\r\n" + 
        		"NULL as HAS_SUB_3_84_, s.IS_LINK_MODIFY as IS_LINK_4_84_,\r\n" + 
        		"s.LINK_ORDER as LINK_ORD5_84_, concat('M',cast(s.LINK_ID as char charset utf8)) as LINK_ID6_84_, 'M' as LINK_TYP7_84_,\r\n" + 
        		"s.LINK_ID as LINKID8_84_, s.LINK_TITLE_EN as MENU_NM_9_84_, s.LINK_TITLE_REG as MENU_NM10_84_,\r\n" + 
        		"s.ORGID as ORGID11_84_, s.LINK_PATH as PAGE_UR12_84_, 'M' as PARENTI13_84_,\r\n" + 
        		"s.LINK_ID  as SORT_OR14_84_\r\n" + 
        		"from (tb_eip_links_master_hist s\r\n" + 
        		"join tb_eip_links_master d on(((s.LINK_ID = d.LINK_ID) and (d.ISDELETED = 'N'))))\r\n" + 
        		"where ((s.ISDELETED = 'N') and s.LINK_ID_H in (select max(eh.LINK_ID_H) from tb_eip_links_master_hist eh\r\n" + 
        		" where ((eh.LINK_ID = s.LINK_ID) and (eh.ORGID = s.ORGID) and (coalesce(eh.CHEKER_FLAG,'Y') = 'Y'))))\r\n" + 
        		" and s.ORGID=:orgId\r\n" + 
        		"union\r\n" + 
        		"select\r\n" + 
        		"FN_GET_ROWNUM() as ROW_NUM1_84_, s.CHEKER_FLAG as Cheker2_84_,\r\n" + 
        		"d.HAS_SUB_LINK as HAS_SUB_3_84_, d.IS_LINK_MODIFY as IS_LINK_4_84_,\r\n" + 
        		"s.LINK_ORDER as LINK_ORD5_84_, concat('F',cast(d.SUB_LINK_MAS_ID as char charset utf8)) as LINK_ID6_84_,\r\n" + 
        		"'F' as LINK_TYP7_84_,\r\n" + 
        		"d.SUB_LINK_MAS_ID as LINKID8_84_, d.SUB_LINK_NAME_EN as MENU_NM_9_84_, d.SUB_LINK_NAME_RG as MENU_NM10_84_,\r\n" + 
        		"s.ORGID as ORGID11_84_, d.PAGE_URL as PAGE_UR12_84_, (case when isnull(d.SUB_LINK_PAR_ID) then concat('M',cast(d.LINK_ID as char charset utf8)) else concat('F',cast(d.SUB_LINK_PAR_ID as char charset utf8)) end) as PARENTI13_84_,\r\n" + 
        		"(s.LINK_ID or d.SUB_LINK_ORDER)  as SORT_OR14_84_\r\n" + 
        		"from (tb_eip_links_master s join tb_eip_sub_links_master_hist d)\r\n" + 
        		"where ((s.LINK_ID = d.LINK_ID) and (s.ISDELETED = 'N') and (d.ISDELETED = 'N') and (s.ORGID = d.ORGID) and (s.CHEKER_FLAG = 'Y') and (d.CHEKER_FLAG = 'Y') and d.SUB_LINK_MAS_ID_H in (select max(sh.SUB_LINK_MAS_ID_H) from tb_eip_sub_links_master_hist sh where ((sh.ORGID = d.ORGID) and (sh.SUB_LINK_MAS_ID = d.SUB_LINK_MAS_ID) and (coalesce(sh.CHEKER_FLAG,'Y') = 'Y'))))\r\n" + 
        		"and s.ORGID=:orgId) a\r\n" + 
        		"order by LINK_ORD5_84_,SORT_OR14_84_");
        query.setParameter("orgId", org.getOrgid());
        @SuppressWarnings("unchecked")
        final List<Object[]> links = query.getResultList();

        if (links != null) {
            links.size();
        }

        return links;
    }
}
