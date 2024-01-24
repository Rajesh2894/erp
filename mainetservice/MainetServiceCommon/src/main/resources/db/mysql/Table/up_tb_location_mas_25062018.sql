UPDATE tb_location_mas a set a.LOC_CAT=
(select cpd_id from tb_comparam_det a,
 tb_comparam_mas b
 where a.cpm_id=b.CPM_ID and
 b.CPM_PREFIX='LCT' and a.cpd_desc='General');
 commit;