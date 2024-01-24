package com.abm.mainet.cms.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.cms.dto.DepartmentUserLogReportDTO;
import com.abm.mainet.common.dao.AbstractDAO;

@Repository
public class DepartmentUserLogReportDao extends AbstractDAO<DepartmentUserLogReportDTO> implements IDepartmentUserLogReportDao{

	@Override
	public List<Object[]> getUserLogReport(String section, Date fromDate, Date toDate, Long orgid) {
		SimpleDateFormat format=new SimpleDateFormat("dd-MMM-yyyy");
		@SuppressWarnings("unused")
		final StringBuilder queryAppender = new StringBuilder(
                "select * from (select e.EMPID,e.EMPNAME,e.EMPLOGINNAME,e.ORGID,(select k.o_nls_orgname from tb_organisation k where k.orgid=e.orgid) org," + 
                " date_format(e.last_loggedin, \"%d-%b-%Y %T\") l_login from employee e" + 
                " where e.logged_in is not null");
				if (orgid!=0) {
					queryAppender
                    .append(" and e.ORGID =:orgId");
				}
				queryAppender
                .append(") emp" + 
                " left outer join " + 
                " (select * from ("); 
                if (section.equals("A")) {
                    queryAppender
                            .append(" select DISTINCT m.orgid, 'LINK' activity, m.Link_title_en activity_desc, date_format(ifnull(m.updated_date,m.created_date), \"%d-%b-%Y %T\") la_date , ifnull(m.updated_by,m.created_by) userid" + 
                " from tb_eip_links_master m" + 
                " union all" + 
                " select DISTINCT b.orgid, 'SUBLINK' activity,b.sub_link_name_en activity_desc, date_format(ifnull(b.updated_date,b.created_date), \"%d-%b-%Y %T\") la_date , ifnull(b.updated_by,b.created_by) userid" + 
                " from tb_eip_sub_links_master b " + 
                " union all " + 
                " select DISTINCT f.orgid, 'SUBLINKFLD' activity,(select o.sub_link_name_en from tb_eip_sub_links_master o where o.SUB_LINK_MAS_ID=f.sub_links_mas_id) activity_desc, date_format(ifnull(f.updated_date,f.created_date), \"%d-%b-%Y %T\") la_date, ifnull(f.updated_by,f.created_by) userid" + 
                " from TB_EIP_SUB_LINK_FIELDS_DTL f" + 
                " union all" + 
                " select DISTINCT p.orgid,'notice' activity,p.notice_title activity_desc, date_format(ifnull(p.updated_date,p.created_date), \"%d-%b-%Y %T\") la_date, ifnull(p.UPDATED_BY,p.created_by) userid" + 
                " from TB_EIP_PUBLIC_NOTICES p" + 
                " union all" + 
                " select DISTINCT a.orgid, 'news' activity,a.announce_desc_eng activity_desc, date_format(ifnull(a.updated_date,a.created_date), \"%d-%b-%Y %T\") la_date, ifnull(a.UPDATED_BY,a.created_by) userid" + 
                " from TB_EIP_announcement a" + 
                " union all" + 
                " select DISTINCT c.ORG_ID, 'Keycontact' activity,c.CNAME_EN activity_desc, date_format(ifnull(c.updated_date,c.CREATED_DATE), \"%d-%b-%Y %T\") la_date, ifnull(c.UPDATED_BY,c.USERID) userid" + 
                " from TB_EIP_CONTACT_US c"); 
                }
                else if (section.equals("QL")) {
                    queryAppender
                            .append(" select DISTINCT m.orgid, 'LINK' activity, m.Link_title_en activity_desc, date_format(ifnull(m.updated_date,m.created_date), \"%d-%b-%Y %T\") la_date , ifnull(m.updated_by,m.created_by) userid" + 
                " from tb_eip_links_master m"); }
                else if (section.equals("SL")) {
                        queryAppender
                                .append(" select DISTINCT b.orgid, 'SUBLINK' activity,b.sub_link_name_en activity_desc, date_format(ifnull(b.updated_date,b.created_date), \"%d-%b-%Y %T\") la_date , ifnull(b.updated_by,b.created_by) userid" + 
                " from tb_eip_sub_links_master b "); }
                else if (section.equals("SD")) {              
                    queryAppender
                            .append(" select DISTINCT f.orgid, 'SUBLINKFLD' activity,(select o.sub_link_name_en from tb_eip_sub_links_master o where o.SUB_LINK_MAS_ID=f.sub_links_mas_id) activity_desc, date_format(ifnull(f.updated_date,f.created_date), \"%d-%b-%Y %T\") la_date, ifnull(f.updated_by,f.created_by) userid" + 
                " from TB_EIP_SUB_LINK_FIELDS_DTL f" );} 
                else if (section.equals("PN")) {
                    queryAppender
                            .append(" select DISTINCT p.orgid,'notice' activity,p.notice_title activity_desc, date_format(ifnull(p.updated_date,p.created_date), \"%d-%b-%Y %T\") la_date, ifnull(p.UPDATED_BY,p.created_by) userid" + 
                " from TB_EIP_PUBLIC_NOTICES p" );} 
                else if (section.equals("RA")) {
                    queryAppender
                            .append(" select DISTINCT a.orgid, 'news' activity,a.announce_desc_eng activity_desc, date_format(ifnull(a.updated_date,a.created_date), \"%d-%b-%Y %T\") la_date, ifnull(a.UPDATED_BY,a.created_by) userid" + 
                " from TB_EIP_announcement a" );} 
                else if (section.equals("CU")) {
                    queryAppender
                            .append(" select DISTINCT c.ORG_ID as orgid, 'Keycontact' activity,c.CNAME_EN activity_desc, date_format(ifnull(c.updated_date,c.CREATED_DATE), \"%d-%b-%Y %T\") la_date, ifnull(c.UPDATED_BY,c.USERID) userid" + 
                " from TB_EIP_CONTACT_US c"); }
                queryAppender
                	.append(" ) x " + 
                " where str_to_date(x.la_date,\"%d-%b-%Y\") >= str_to_date(:fromDate,\"%d-%b-%Y\") and str_to_date(x.la_date,\"%d-%b-%Y\") <= str_to_date(:toDate,\"%d-%b-%Y\") "
                + ")sub" + 
                " on emp.orgid = sub.orgid" + 
                " and emp.empid = sub.userid" + 
                " order by emp.empid ");
		final Query query = createNativeQuery(queryAppender.toString());
        if (orgid != 0) {
            query.setParameter("orgId",orgid);
        }
			query.setParameter("fromDate", format.format(fromDate));
			query.setParameter("toDate", format.format(toDate));
        List<Object[]> objList=query.getResultList();
		return objList;
	}

}
