Insert into TB_COMPARAM_MAS
(CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,
LANG_ID,LMODDATE,CPM_MODULE_NAME,CPM_CONFIG,
CPM_EDIT,CPM_REPLICATE_FLAG,CPM_TYPE,
CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,
CPD_EDIT_DEFAULT,CPD_EDIT_STATUS,USER_ID)  
values (fn_java_sq_generation('AUT','CPM_ID','TB_COMPARAM_MAS',NULL,NULL),'SLD','Flat or Slab Dependant','A',
1,now(),'WT','Y',
'N','Y','N',
'N','N','Y','N',
'N','Y',1);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,
user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','CPD_ID','TB_COMPARAM_DET',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'No.of Family','NOF','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='SLD'),
1,1,now(),'N','No of Family',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','CPD_ID','TB_COMPARAM_DET',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'No. of Users','NOU','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='SLD'),
1,1,now(),'N','No. of Users',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','CPD_ID','TB_COMPARAM_DET',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),'No. of Taps','NOT','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='SLD'),
1,1,now(),'N','No. of Taps',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','CPD_ID','TB_COMPARAM_DET',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),'Bill Due Date','BDD','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='SLD'),
1,1,now(),'N','Bill Due Date',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','CPD_ID','TB_COMPARAM_DET',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),'Branch Line','BRL','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='SLD'),
1,1,now(),'N','Branch Line',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','CPD_ID','TB_COMPARAM_DET',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),'Main Line','MNL','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='SLD'),
1,1,now(),'N','Main Line',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','CPD_ID','TB_COMPARAM_DET',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Road Type','RDT','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='SLD'),
1,1,now(),'N','Road Type',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','CPD_ID','TB_COMPARAM_DET',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'NA','NA','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='SLD'),
1,1,now(),'N','NA',NULL,NULL);
