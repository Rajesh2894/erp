select 
(select mn_ass_no from tb_as_assesment_mast where mn_ass_id=b.mn_ass_id) PropertyNumber,
(select k.O_NLS_ORGNAME  from tb_organisation k where k.orgid=b.orgid ) OrganisationName
from 
(select count(1),mn_ass_id from
(select count(distinct MN_assd_floor_no) ,mn_ass_id,MN_assd_floor_no
from tb_as_assesment_detail 
group by mn_ass_id,MN_assd_floor_no) x
group by x.mn_ass_id
having count(1) > 1) a,
tb_as_assesment_detail b
where a.mn_ass_id=b.MN_ass_id and
b.MN_assd_constru_type=
(select cpd_id 
from tb_comparam_det c,
tb_comparam_mas d
where c.CPD_VALUE='OTH'
and c.cpm_id=d.cpm_id
and d.cpm_prefix='CSC' and c.orgid=b.orgid)
order by PropertyNumber,OrganisationName
