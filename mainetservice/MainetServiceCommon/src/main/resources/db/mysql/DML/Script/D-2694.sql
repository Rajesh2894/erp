delete from Tb_comparam_det where cpd_id in (133,134);
delete from Tb_comparam_det where cpd_id in (41,42);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Force Cancellation','F','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='APT'),
1,1,now(),'N','बलपूर्वक रद्दीकरण',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Application Cancellation','APP','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='APT'),
1,1,now(),'N','आवेदन रद्दीकरण',NULL,NULL);
commit;

update TB_COMPARAM_DET set CPD_VALUE='A' where CPM_ID in
(select CPM_ID from TB_COMPARAM_MAS WHERE CPM_PREFIX='APL')
and cpd_desc='Applicable'; 
commit;

update TB_COMPARAM_DET set CPD_VALUE='NA' where CPM_ID in
(select CPM_ID from TB_COMPARAM_MAS WHERE CPM_PREFIX='APL')
and cpd_desc='Not Applicable'; 
commit;

update TB_COMPARAM_DET set cpd_others=NULL where CPM_ID in
(select CPM_ID from TB_COMPARAM_MAS WHERE CPM_PREFIX='CAA')
and cpd_desc='Application'; 
commit;

update TB_COMPARAM_DET set cpd_others=NULL where CPM_ID in
(select CPM_ID from TB_COMPARAM_MAS WHERE CPM_PREFIX='CAA')
and cpd_desc='Scrutiny'; 
commit;





