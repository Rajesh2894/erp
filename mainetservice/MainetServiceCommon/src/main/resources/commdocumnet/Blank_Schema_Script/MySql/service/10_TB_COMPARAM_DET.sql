Insert into TB_COMPARAM_MAS
(CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,
LANG_ID,LMODDATE,CPM_MODULE_NAME,CPM_CONFIG,
CPM_EDIT,CPM_REPLICATE_FLAG,CPM_TYPE,
CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,
CPD_EDIT_DEFAULT,CPD_EDIT_STATUS,USER_ID)  
values (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),
'ACN','ACTIVENESS','A',1,now(),'COM','Y','N','N','N','Y','N','Y','N','Y','Y',1);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),
'Active','A','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='ACN'),
1,1,now(),'Y','सक्रिय',NULL,NULL);

commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),
'Inactive','I','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='ACN'),
1,1,now(),'N','निष्क्रिय',NULL,NULL);
commit;


Insert into TB_COMPARAM_MAS
(CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,
LANG_ID,LMODDATE,CPM_MODULE_NAME,CPM_CONFIG,
CPM_EDIT,CPM_REPLICATE_FLAG,CPM_TYPE,
CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,
CPD_EDIT_DEFAULT,CPD_EDIT_STATUS,USER_ID)  
values (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),'APL','Applicability','A',
1,now(),'COM','Y',
'N','N','N',
'Y','N','Y','Y',
'N','Y',1);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Applicable','A','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='APL'),
1,1,now(),'A','उपयुक्त',NULL,NULL);
commit;


Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),
'Not Applicable','NA','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='APL'),
1,1,now(),'N','लागू नहीं',NULL,NULL);
commit;

Insert into TB_COMPARAM_MAS
(CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,
LANG_ID,LMODDATE,CPM_MODULE_NAME,CPM_CONFIG,
CPM_EDIT,CPM_REPLICATE_FLAG,CPM_TYPE,
CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,
CPD_EDIT_DEFAULT,CPD_EDIT_STATUS,USER_ID)  
values (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),'APT','Applicant Type','A',
1,now(),'COM','Y',
'N','N','N',
'Y','N','Y','Y',
'N','Y',1);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Group','G','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='APT'),
1,1,now(),'N','समूह',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Individual','I','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='APT'),1,1,now(),'N','व्यक्ति',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Organisation','OTM','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='APT'),
1,1,now(),'N','संगठन',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Tenant','B','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='APT'),
1,1,now(),'N','किरायेदार',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Trust','TR','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='APT'),
1,1,now(),'N','ट्रस्ट',NULL,NULL);
commit;


Insert into TB_COMPARAM_MAS
(CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,
LANG_ID,LMODDATE,CPM_MODULE_NAME,CPM_CONFIG,
CPM_EDIT,CPM_REPLICATE_FLAG,CPM_TYPE,
CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,
CPD_EDIT_DEFAULT,CPD_EDIT_STATUS,USER_ID)  
values (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),'CAA','Charge Applicable At','A',1,now(),'COM','Y','N','N','N','Y','N','Y','Y','Y','Y',1);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Application','APL','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='CAA'),
1,1,now(),'N','आवेदन',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Scrutiny','SCL','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='CAA'),
1,1,now(),'N','मतपत्रों की जाँच',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Bill','BILL','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='CAA'),
1,1,now(),'N','बिल',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Receipt','RCPT','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='CAA'),
1,1,now(),'N','रसीद',NULL,NULL);
commit;

Insert into TB_COMPARAM_MAS
(CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,
LANG_ID,LMODDATE,CPM_MODULE_NAME,CPM_CONFIG,
CPM_EDIT,CPM_REPLICATE_FLAG,CPM_TYPE,
CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,
CPD_EDIT_DEFAULT,CPD_EDIT_STATUS,USER_ID)  
values (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),'CAN','Cancellation of Method
','A',1,now(),'COM','Y','N','Y','N','Y','N','Y','Y','Y','Y',1);
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

Insert into TB_COMPARAM_MAS
(CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,
LANG_ID,LMODDATE,CPM_MODULE_NAME,CPM_CONFIG,
CPM_EDIT,CPM_REPLICATE_FLAG,CPM_TYPE,
CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,
CPD_EDIT_DEFAULT,CPD_EDIT_STATUS,USER_ID)  
values (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),'CLG','Checklist Group','A',
1,now(),'COM','Y',
'N','N','N',
'Y','N','Y','Y',
'Y','Y',1);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'D1','D1','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='CLG'),
1,1,now(),'N','D1',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),
'D2','D2','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='CLG'),
1,1,now(),'N','D2',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'D3','D3','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='CLG'),
1,1,now(),'N','D3',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'D4','D4','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='CLG'),
1,1,now(),'N','D4',NULL,NULL);
commit;


Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'D5','D5','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='CLG'),
1,1,now(),'N','D5',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'D6','D6','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='CLG'),
1,1,now(),'N','D6',NULL,NULL);
commit;


Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'D7','D7','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='CLG'),
1,1,now(),'N','D7',NULL,NULL);
commit;


Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'D8','D8','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='CLG'),
1,1,now(),'N','D8',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'D9','D9','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='CLG'),
1,1,now(),'N','D9',NULL,NULL);
commit;


Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'D10','D10','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='CLG'),
1,1,now(),'N','D10',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'D11','D11','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='CLG'),
1,1,now(),'N','D11',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),
'D12','D12','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='CLG'),
1,1,now(),'N','D12',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'D13','D13','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='CLG'),
1,1,now(),'N','D13',NULL,NULL);
commit;


Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'D14','D14','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='CLG'),
1,1,now(),'N','D14',NULL,NULL);
commit;


Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'D15','D15','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='CLG'),
1,1,now(),'N','D15',NULL,NULL);

commit;

Insert into TB_COMPARAM_MAS
(CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,
LANG_ID,LMODDATE,CPM_MODULE_NAME,CPM_CONFIG,
CPM_EDIT,CPM_REPLICATE_FLAG,CPM_TYPE,
CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,
CPD_EDIT_DEFAULT,CPD_EDIT_STATUS,USER_ID)  
values (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),'CNT','Contract Type','A',1,now(),'COM','Y','N','Y','N','Y','N','Y','Y','Y','Y',1);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Contract Against Lease','CL','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='CNT'),
1,1,now(),'N','पट्टा के खिलाफ अनुबंध',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Contract Against Supply','CS','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='CNT'),
1,1,now(),'N','आपूर्ति के खिलाफ अनुबंध',NULL,NULL);
commit;


Insert into TB_COMPARAM_MAS
(CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,
LANG_ID,LMODDATE,CPM_MODULE_NAME,CPM_CONFIG,
CPM_EDIT,CPM_REPLICATE_FLAG,CPM_TYPE,
CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,
CPD_EDIT_DEFAULT,CPD_EDIT_STATUS,USER_ID)  
values (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),'DIS','District','A',1,now(),'COM','Y','N','N','N','Y','N','N','Y','Y','Y',1);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Mumbai City district‎','MUM','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='DIS'),
1,1,now(),'N','मुंबई शहर जिला',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Mumbai Suburban district','NK','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='DIS'),
1,1,now(),'N','मुंबई उपनगर जिला',NULL,NULL);
commit;

Insert into TB_COMPARAM_MAS
(CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,
LANG_ID,LMODDATE,CPM_MODULE_NAME,CPM_CONFIG,
CPM_EDIT,CPM_REPLICATE_FLAG,CPM_TYPE,
CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,
CPD_EDIT_DEFAULT,CPD_EDIT_STATUS,USER_ID)  
values (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),'DTT','Datatype','A',1,now(),'COM','Y','N','N','N','Y','N','Y','Y','N','Y',1);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'String','VARCHAR2','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='DTT'),
1,1,now(),'N','स्ट्रिंग',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'NUMBER','NUMBER','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='DTT'),
1,1,now(),'N','संख्या',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'DATE','DATE','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='DTT'),
1,1,now(),'N','तारीख',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'LIST','LIST','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='DTT'),
1,1,now(),'N','सूची',NULL,NULL);
commit;

Insert into TB_COMPARAM_MAS
(CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,
LANG_ID,LMODDATE,CPM_MODULE_NAME,CPM_CONFIG,
CPM_EDIT,CPM_REPLICATE_FLAG,CPM_TYPE,
CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,
CPD_EDIT_DEFAULT,CPD_EDIT_STATUS,USER_ID)  
values (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),'DVN','Division','A',1,now(),'COM','Y','N','N','N','Y','N','Y','Y','Y','Y',1);
commit;


Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Amravati division','MUM','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='DVN'),
1,1,now(),'N','अमरावती डिवीजन',NULL,NULL);
COMMIT;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Aurangabad division','NK','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='DVN'),
1,1,now(),'N','औरंगाबाद डिवीजन',NULL,NULL);

commit;

Insert into TB_COMPARAM_MAS
(CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,
LANG_ID,LMODDATE,CPM_MODULE_NAME,CPM_CONFIG,
CPM_EDIT,CPM_REPLICATE_FLAG,CPM_TYPE,
CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,
CPD_EDIT_DEFAULT,CPD_EDIT_STATUS,USER_ID)  
values (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),'GEN','GENDER','A',1,now(),'COM','Y','Y','N','N','N','N','Y','N','N','Y',1);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),
'MALE','M','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='GEN'),
1,2,now(),NULL,'पुरुष',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),
'FEMALE','F','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='GEN'),
1,2,now(),NULL,'महिला',NULL,NULL);
commit;


Insert into TB_COMPARAM_MAS
(CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,
LANG_ID,LMODDATE,CPM_MODULE_NAME,CPM_CONFIG,
CPM_EDIT,CPM_REPLICATE_FLAG,CPM_TYPE,
CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,
CPD_EDIT_DEFAULT,CPD_EDIT_STATUS,USER_ID)  values (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),'PON','Print On','A',1,now(),'COM','Y','N','N','N','Y','N','Y','Y','N','Y',1);
commit;


Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Bill','BLL','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='PON'),
1,1,now(),'N','बिल',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),'Notice','NOT','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='PON'),
1,1,now(),'N','सूचना',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),'Receipt','RCT','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='PON'),
1,1,now(),'N','रसीद',NULL,NULL);
commit;

Insert into TB_COMPARAM_MAS
(CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,
LANG_ID,LMODDATE,CPM_MODULE_NAME,CPM_CONFIG,
CPM_EDIT,CPM_REPLICATE_FLAG,CPM_TYPE,
CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,
CPD_EDIT_DEFAULT,CPD_EDIT_STATUS,USER_ID)  
values (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),
'PRN','Printing Responsibility','A',1,now(),'COM','Y','N','N','N','Y','N','Y','Y','N','Y',1);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Citizen Facilitation Center','C','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='PRN'),
1,1,now(),'N','नागरिक सुविधा केंद्र',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Department','D','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='PRN'),
1,1,now(),'N','विभाग',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Not Applicable','NA','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='PRN'),
1,1,now(),'N','लागू नहीं',NULL,NULL);
commit;

Insert into TB_COMPARAM_MAS
(CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,
LANG_ID,LMODDATE,CPM_MODULE_NAME,CPM_CONFIG,
CPM_EDIT,CPM_REPLICATE_FLAG,CPM_TYPE,
CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,
CPD_EDIT_DEFAULT,CPD_EDIT_STATUS,USER_ID)  values (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),'REM','Remark Type','A',1,now(),'COM','Y','N','N','N','Y','N','Y','Y','N','Y',1);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Approval Remarks','APP','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='REM'),
1,1,now(),'N','अनुमोदन टिप्पणियां',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Truti Patra Remarks','TRU','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='REM'),1,1,now(),'N','त्रुटि पत्र टिप्पणियां',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Rejection Remarks','REJ','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='REM'),1,1,now(),'N','अस्वीकृति टिप्पणी',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Work Order Remarks','WOR','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='REM'),1,1,now(),'N','कार्य आदेश टिप्पणी',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Plumber License Remarks','PLR','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='REM'),1,1,now(),'N','प्लंबर लाइसेंस टिप्पणियां',NULL,NULL);
commit;


Insert into TB_COMPARAM_MAS
(CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,
LANG_ID,LMODDATE,CPM_MODULE_NAME,CPM_CONFIG,
CPM_EDIT,CPM_REPLICATE_FLAG,CPM_TYPE,
CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,
CPD_EDIT_DEFAULT,CPD_EDIT_STATUS,USER_ID)  values (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),'TAG','Tax Group','A',1,now(),'COM','Y','N','N','N','Y','N','Y','Y','N','Y',1);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Government Tax','GVT','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='TAG'),
1,1,now(),'N','सरकार के टैक्स',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Non-Government Tax','NGT','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='TAG'),
1,1,now(),'N','गैर-सरकारी कर',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Service Tax','ST','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='TAG'),
1,1,now(),'N','सेवा कर',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Penalty Tax','PT','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='TAG'),
1,1,now(),'N','जुर्माना कर',NULL,NULL);
commit;

Insert into TB_COMPARAM_MAS
(CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,
LANG_ID,LMODDATE,CPM_MODULE_NAME,CPM_CONFIG,
CPM_EDIT,CPM_REPLICATE_FLAG,CPM_TYPE,
CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,
CPD_EDIT_DEFAULT,CPD_EDIT_STATUS,USER_ID)  
values (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),'LPT','LOI Payment Type','A',1,now(),'COM','Y','N','Y','N','Y','N','Y','Y','Y','Y',1);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Collection','C','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='LPT'),
1,1,now(),'N','संग्रह',NULL,NULL);
commit;


Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Refund','R','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='LPT'),
1,1,now(),'N','धन की वापसी',NULL,NULL);
commit;

Insert into TB_COMPARAM_MAS
(CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,
LANG_ID,LMODDATE,CPM_MODULE_NAME,CPM_CONFIG,
CPM_EDIT,CPM_REPLICATE_FLAG,CPM_TYPE,
CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,
CPD_EDIT_DEFAULT,CPD_EDIT_STATUS,USER_ID)  
values 
(fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),
'MON','Months','A',1,now(),'COM','Y','N','Y','N','Y','N','Y','Y','Y','Y',1);
commit;


Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'JAN','01','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='MON'),
1,1,now(),'N','जनवरी',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'FEB','02','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='MON'),
1,1,now(),'N','फ़रवरी',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'MAR','03','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='MON'),
1,1,now(),'N','मार्च',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'APR','04','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='MON'),1,1,now(),'N','अप्रैल',NULL,NULL);
commit;


Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'MAY','05','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='MON'),1,1,now(),'N','मई',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'JUN','06','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='MON'),1,1,now(),'N','जून',NULL,NULL);
commit;


Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'JUL','07','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='MON'),
1,1,now(),'N','जुलाई',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'AUG','08','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='MON'),
1,1,now(),'N','अगस्त',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'SEP','09','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='MON'),
1,1,now(),'N','सितम्बर',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'OCT','10','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='MON'),
1,1,now(),'N','अक्टूबर',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'NOV','11','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='MON'),
1,1,now(),'N','नवम्बर',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'DEC','12','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='MON'),
1,1,now(),'N','दिसम्बर',NULL,NULL);
commit;

Insert into TB_COMPARAM_MAS
(CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,
LANG_ID,LMODDATE,CPM_MODULE_NAME,CPM_CONFIG,
CPM_EDIT,CPM_REPLICATE_FLAG,CPM_TYPE,
CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,
CPD_EDIT_DEFAULT,CPD_EDIT_STATUS,USER_ID)  values (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),'OFL','OFFLINE PAYMENT MODE','A',1,now(),'COM','Y','N','Y','N','Y','N','Y','N','N','Y',1);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'PAY BY CHALLAN@BANK','PCB','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='OFL'),
1,1,now(),'N','बैंक द्वारा चलेन द्वारा भुगतान',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'PAY BY CHALLAN@ULB','PCU','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='OFL'),1,1,now(),'N','यूएएल द्वारा चलेन द्वारा भुगतान करें',NULL,NULL);
commit;


Insert into TB_COMPARAM_MAS
(CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,
LANG_ID,LMODDATE,CPM_MODULE_NAME,CPM_CONFIG,
CPM_EDIT,CPM_REPLICATE_FLAG,CPM_TYPE,
CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,
CPD_EDIT_DEFAULT,CPD_EDIT_STATUS,USER_ID)  
values 
(fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),'OST','Organisation Sub-Type','A',
1,now(),'COM','Y','N','N','N','Y','N','N','Y','Y','Y',1);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),
'A','A','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='OST'),
1,1,now(),'N','ए',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'B','B','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='OST'),
1,1,now(),'N','बी',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'C','C','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='OST'),
1,1,now(),'N','सी',NULL,NULL);


Insert into TB_COMPARAM_MAS
(CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,
LANG_ID,LMODDATE,CPM_MODULE_NAME,CPM_CONFIG,
CPM_EDIT,CPM_REPLICATE_FLAG,CPM_TYPE,
CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,
CPD_EDIT_DEFAULT,CPD_EDIT_STATUS,USER_ID)  
values (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),'OTY','ORGANISATION TYPE','A',1,now(),'COM','Y','N','N','N','Y','N','Y','N','Y','Y',1);

commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Council','CON','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='OTY'),
1,1,now(),'N','Council',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Corporation','COP','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='OTY'),1,1,now(),'N','Corporation',NULL,NULL);



Insert into TB_COMPARAM_MAS
(CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,
LANG_ID,LMODDATE,CPM_MODULE_NAME,CPM_CONFIG,
CPM_EDIT,CPM_REPLICATE_FLAG,CPM_TYPE,
CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,
CPD_EDIT_DEFAULT,CPD_EDIT_STATUS,USER_ID)  
values 
(fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),'PAY','PAYMENT MODE','A',
1,now(),'COM','Y','N','Y','N','Y','N','N','Y','Y','Y',1);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),
'Demand Draft','D','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='PAY'),
1,1,now(),'','मांग पत्र',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),
'Cash','C','A',(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='PAY'),
1,1,now(),'','कैश',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),
'Cheque','Q','A',(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='PAY'),
1,1,now(),'','चेक',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Pay Order','P','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='PAY'),
1,1,now(),'','भुगतान आदेश',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Transfer','T','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='PAY'),
1,1,now(),'','Transfer',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Bank','B','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='PAY'),
1,1,now(),'','Bank',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Adjustment','A','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='PAY'),
1,1,now(),'','Adjustment',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Rebate','R','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='PAY'),
1,1,now(),'','Rebate',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'User Adjustment','U','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='PAY'),
1,1,now(),'','User Adjustment',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'User Discount','UD','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='PAY'),
1,1,now(),'','User Discount',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'WEB','W','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='PAY'),
1,1,now(),'','WEB',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'RTGS','RT','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='PAY'),
1,1,now(),'','WEB',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'NEFT','N','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='PAY'),
1,1,now(),'','WEB',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'FDR','F','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='PAY'),
1,1,now(),'','FDR',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'POS','POS','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='PAY'),1,1,now(),'','POS',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Petty Cash','PCA','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='PAY'),1,1,now(),'','Petty Cash',NULL,NULL);

Insert into TB_COMPARAM_MAS
(CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,
LANG_ID,LMODDATE,CPM_MODULE_NAME,CPM_CONFIG,
CPM_EDIT,CPM_REPLICATE_FLAG,CPM_TYPE,
CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,
CPD_EDIT_DEFAULT,CPD_EDIT_STATUS,USER_ID)  values (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),'RBA','Receipt Collection Method','A',1,now(),'COM','Y','N','Y','N','Y','N','N','Y','N','Y',1);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Collection Order wise','C','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='RBA'),
1,1,now(),'N','Collection Order wise',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Bifurcation Method wise','B','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='RBA'),
1,1,now(),'N','Bifurcation Method wise',NULL,NULL);

Insert into TB_COMPARAM_MAS
(CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,
LANG_ID,LMODDATE,CPM_MODULE_NAME,CPM_CONFIG,
CPM_EDIT,CPM_REPLICATE_FLAG,CPM_TYPE,
CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,
CPD_EDIT_DEFAULT,CPD_EDIT_STATUS,USER_ID)  values (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),'SET','Set of the sets for checklist values','A',1,now(),'COM','Y','N','N','N','Y','N','Y','Y','N','Y',1);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Yes','Y','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='SET'),
1,1,now(),'N','Yes',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'No','N','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='SET'),
1,1,now(),'N','No',NULL,NULL);


Insert into TB_COMPARAM_MAS
(CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,
LANG_ID,LMODDATE,CPM_MODULE_NAME,CPM_CONFIG,
CPM_EDIT,CPM_REPLICATE_FLAG,CPM_TYPE,
CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,
CPD_EDIT_DEFAULT,CPD_EDIT_STATUS,USER_ID)  values (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),'SLI','Setting or Live Mode','A',1,now(),'COM','Y','N','Y','N','Y','N','Y','N','N','Y',1);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Setting Mode','S','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='SLI'),
1,1,now(),'N','सेटिंग मोड',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Live Mode','L','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='SLI'),
1,1,now(),'Y','लाइव मोड',NULL,NULL);

Insert into TB_COMPARAM_MAS
(CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,
LANG_ID,LMODDATE,CPM_MODULE_NAME,CPM_CONFIG,
CPM_EDIT,CPM_REPLICATE_FLAG,CPM_TYPE,
CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,
CPD_EDIT_DEFAULT,CPD_EDIT_STATUS,USER_ID)  
values (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),'STT','STATE','A',1,now(),'COM','Y','N','N','N','Y','N','Y','Y','Y','Y',1);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Himachal Pradesh ','HP','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='STT'),
1,1,now(),'N','हिमाचल प्रदेश',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Karnataka','KA','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='STT'),
1,1,now(),'Y','कर्नाटक',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Maharashtra','MAH','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='STT'),1,1,now(),'N','महाराष्ट्र',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Chhattisgarh','CTH','A',(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='STT'),1,1,now(),'N','Chhattisgarh',NULL,NULL);

Insert into TB_COMPARAM_MAS
(CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,
LANG_ID,LMODDATE,CPM_MODULE_NAME,CPM_CONFIG,
CPM_EDIT,CPM_REPLICATE_FLAG,CPM_TYPE,
CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,
CPD_EDIT_DEFAULT,CPD_EDIT_STATUS,USER_ID)  values (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),'TFM','Transfer Mode','A',1,now(),'COM','Y','N','N','N','Y','N','Y','Y','Y','Y',1);
commit;


Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Hereditary Mode','HRM','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='TFM'),
1,1,now(),'N','Hereditary Mode',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Other Mode','OTM','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='TFM'),
1,1,now(),'N','Other Mode',NULL,NULL);


Insert into TB_COMPARAM_MAS
(CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,
LANG_ID,LMODDATE,CPM_MODULE_NAME,CPM_CONFIG,
CPM_EDIT,CPM_REPLICATE_FLAG,CPM_TYPE,
CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,
CPD_EDIT_DEFAULT,CPD_EDIT_STATUS,USER_ID)  values 
(fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),
'TXN','Tax Description','A',
1,now(),'COM','Y',
'N','N','N','Y','N','Y','Y','Y','Y',1);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Application Charge','APC','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='TXN'),
1,1,now(),'N','Application Charge',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Road Digging Charge','RDG','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='TXN'),
1,1,now(),'N','Road Digging Charge',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Water Tax','WT','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='TXN'),1,1,now(),'N','Water Tax',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Penalty','PTL','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='TXN'),
1,1,now(),'N','Penalty',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Security Deposit','SCD','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='TXN'),
1,1,now(),'N','Security Deposit',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'License Fee','LCF','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='TXN'),
1,1,now(),'N','License Fee',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Advance Payment','ADP','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='TXN'),
1,1,now(),'N','Advance Payment',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Booking Fees','BKF','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='TXN'),1,1,now(),'N','Booking Fees',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Rebate','RBT','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='TXN'),1,1,now(),'N','Rebate',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'change of usage tax','COU','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='TXN'),1,1,now(),'N','change of usage tax',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Property Tax','PTR','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='TXN'),1,1,now(),'N','Property Tax',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Property Tax - Commercial','PTC','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='TXN'),
1,1,now(),'N','Property Tax - Commercial',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Property Tax - Industrial','PTI','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='TXN'),
1,1,now(),'N','Property Tax - Industrial',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Consolidate Tax','CST','A',(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='TXN'),1,1,now(),'N','Consolidate Tax',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Sanitation Tax','SNT','A',(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='TXN'),1,1,now(),'N','Sanitation Tax',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Fire Tax','FRT','A',(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='TXN'),1,1,now(),'N','Fire Tax',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Education Cess','EDU','A',(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='TXN'),1,1,now(),'N','Education Cess',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Income Tax','IT','A',(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='TXN'),1,1,now(),'N','Income Tax',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Work Contract Tax','WCT','A',(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='TXN'),1,1,now(),'N','Work Contract Tax',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Hereditary Mode','HRM','A',(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='TFM'),1,1,now(),'N','Hereditary Mode',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Other Mode','OTM','A',(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='TFM'),1,1,now(),'N','Other Mode',NULL,NULL);


Insert into TB_COMPARAM_MAS
(CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,
LANG_ID,LMODDATE,CPM_MODULE_NAME,CPM_CONFIG,
CPM_EDIT,CPM_REPLICATE_FLAG,CPM_TYPE,
CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,
CPD_EDIT_DEFAULT,CPD_EDIT_STATUS,USER_ID)  values (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),'UTS','Unit','A',1,now(),'COM','Y','N','Y','N','Y','N','Y','Y','Y','Y',1);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'DAY','D','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='UTS'),
1,1,now(),'N','DAY',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Hour','H','A',(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='UTS'),1,1,now(),'N','Hour',NULL,NULL);

Insert into TB_COMPARAM_MAS
(CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,
LANG_ID,LMODDATE,CPM_MODULE_NAME,CPM_CONFIG,
CPM_EDIT,CPM_REPLICATE_FLAG,CPM_TYPE,
CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,
CPD_EDIT_DEFAULT,CPD_EDIT_STATUS,USER_ID)  values (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),'VTY','Factor Value Type','A',1,now(),'COM','Y','N','Y','N','Y','N','Y','Y','N','Y',1);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Percentage','PER','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='VTY'),
1,1,now(),'N','Percentage',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Amount','AMT','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='VTY'),
1,1,now(),'Y','Amount',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Multiply','MUL','A',(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='VTY'),1,1,now(),'N','Multiply',NULL,NULL);

Insert into TB_COMPARAM_MAS
(CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,
LANG_ID,LMODDATE,CPM_MODULE_NAME,CPM_CONFIG,
CPM_EDIT,CPM_REPLICATE_FLAG,CPM_TYPE,
CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,
CPD_EDIT_DEFAULT,CPD_EDIT_STATUS,USER_ID)  values (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),'WER','Work flow event required','A',1,now(),'COM','Y','N','N','N','Y','N','Y','Y','Y','Y',1);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Mandatory','M','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='WER'),
1,1,now(),'N','Mandatory',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Conditional','C','A',(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='WER'),1,1,now(),'N','Conditional',NULL,NULL);

Insert into TB_COMPARAM_MAS
(CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,
LANG_ID,LMODDATE,CPM_MODULE_NAME,CPM_CONFIG,
CPM_EDIT,CPM_REPLICATE_FLAG,CPM_TYPE,
CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,
CPD_EDIT_DEFAULT,CPD_EDIT_STATUS,USER_ID)  
values (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),'YOC','Financial year Status','A',1,now(),'COM','Y','N','N','N','Y','N','Y','Y','Y','Y',1);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Open','OPN','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='YOC'),
1,1,now(),'N','Open',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Softclose','CLO','A',(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='YOC'),1,1,now(),'N','Softlcose',NULL,NULL);
Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'Hardclose','HCL','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='YOC'),1,1,now(),'N','Hardclose',NULL,NULL);




INSERT INTO TB_COMPARAM_MAS (CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,USER_ID,LANG_ID,LMODDATE,
CPM_LIMITED_YN,CPM_MODULE_NAME,UPDATED_BY,UPDATED_DATE,CPM_CONFIG,CPM_EDIT,LG_IP_MAC,LG_IP_MAC_UPD,CPM_REPLICATE_FLAG,CPM_TYPE,
CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,CPD_EDIT_DEFAULT,CPD_EDIT_STATUS) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),
'AUT','Authorization Required','A',1,1,'2016-12-14 17:36:46',NULL,'COM',NULL,NULL,'Y','N',NULL,NULL,'Y','N','N','N','N','N','N','N');
COMMIT;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,
CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y')
'Receipt Entry','RV','I',(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='AUT'),1,1,'2016-12-14 17:36:46','N',NULL,NULL,'Receipt Entry','',NULL,NULL);
COMMIT;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Contra Entry - TRF ','CV','A',select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='AUT',1,1,'2016-12-14 17:36:46','N',NULL,NULL,'Contra Entry - TRF ','',NULL,NULL);
COMMIT;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Voucher Transactions','VT','A',select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='AUT',1,1,'2016-12-14 17:36:46','N',NULL,NULL,'Journal Entry - TRN','',NULL,NULL);
COMMIT;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Payment Entry','PV','A',(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='AUT'),
1,1,'2016-12-14 17:36:46','N',NULL,NULL,'Payment Entry','',NULL,NULL);
COMMIT;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Bill Entry','BE','A',(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='AUT'),
1,1,'2016-12-14 17:36:46','N',NULL,NULL,'Bill Entry','',NULL,NULL);
COMMIT;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Vendor Entry','VE','A',(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='AUT'),
1,1,'2016-12-14 17:36:46','N',NULL,NULL,'Vendor Entry','',NULL,NULL);
COMMIT;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Re-appropriation','RPR','A',(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='AUT'),
1,1,'2016-12-14 17:36:46','N',NULL,NULL,'Re-appropriation','',NULL,NULL);
COMMIT;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Supplementary','SPL','A',(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='AUT'),
1,1,'2016-12-14 17:36:46','N',NULL,NULL,'Supplementary','',NULL,NULL);
COMMIT;


INSERT INTO TB_COMPARAM_MAS (CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,USER_ID,LANG_ID,LMODDATE,CPM_LIMITED_YN,CPM_MODULE_NAME,UPDATED_BY,UPDATED_DATE,CPM_CONFIG,CPM_EDIT,LG_IP_MAC,LG_IP_MAC_UPD,CPM_REPLICATE_FLAG,CPM_TYPE,CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,CPD_EDIT_DEFAULT,CPD_EDIT_STATUS) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),
'BAT','Bank Type','A',1,1,'2016-08-06 16:22:32',NULL,'COM',NULL,NULL,'Y','N',NULL,NULL,'N','N','N','N','N','N','N','N');


INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Nationalized Bank','NB','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='BAT'),
1,1,'2016-08-06 16:22:32','Y',NULL,NULL,'Nationalized Bank',NULL,NULL,NULL);
commit;


INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Other Schedule Bank','OS','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='BAT'),
1,1,'2016-08-06 16:22:32','N',NULL,NULL,'Other Schedule Bank',NULL,NULL,NULL);
commit;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Schedule Cooperative Bank','SCB','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='BAT'),
1,1,'2016-08-06 16:22:32','N',NULL,NULL,'Schedule Cooperative Bank',NULL,NULL,NULL);
COMMIT;

####CLR####

INSERT INTO TB_COMPARAM_MAS (CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,USER_ID,LANG_ID,LMODDATE,CPM_LIMITED_YN,CPM_MODULE_NAME,UPDATED_BY,UPDATED_DATE,CPM_CONFIG,CPM_EDIT,LG_IP_MAC,LG_IP_MAC_UPD,CPM_REPLICATE_FLAG,CPM_TYPE,CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,CPD_EDIT_DEFAULT,CPD_EDIT_STATUS) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),
'CLR','Cheque Status','A',1,1,'2016-08-09 11:28:59',NULL,'COM',NULL,NULL,'Y','N',NULL,NULL,'Y','N','Y','N','N','Y','N','N');
COMMIT;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Cleared','CLD','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='CLR'),
1,1,'2016-08-09 11:28:59','N',NULL,NULL,'Cleared','3',NULL,NULL);


INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Cancelled','CND','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='CLR'),
1,1,'2016-08-09 11:28:59','N',NULL,NULL,'Cancelled',NULL,NULL,NULL);

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Issued','ISD','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='CLR'),
1,1,'2016-08-09 11:28:59','N',NULL,NULL,'Issued',NULL,NULL,NULL);

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Not Issued','NSD','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='CLR'),
1,1,'2016-08-09 11:28:59','N',NULL,NULL,'Not Issued',NULL,NULL,NULL);

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Deposited But Not Cleared','DNC','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='CLR'),
1,1,'2016-08-09 11:28:59','N',NULL,NULL,'Deposited But Not Cleared',NULL,NULL,NULL);

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Received But Not Deposited','RND','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='CLR'),
1,1,'2016-08-09 11:28:59','N',NULL,NULL,'Received But Not Deposited',NULL,NULL,NULL);

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Dishonored (Bounced)','DSH','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='CLR'),
1,1,'2016-08-09 11:28:59','N',NULL,NULL,'Dishonored (Bounced)',NULL,NULL,NULL);

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Stop Payment','STP','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='CLR'),
1,1,'2016-08-09 11:28:59','N',NULL,NULL,'Stop Payment',NULL,NULL,NULL);

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Stale','STL','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='CLR'),
1,1,'2016-08-09 11:28:59','N',NULL,NULL,'Stale',NULL,NULL,NULL);

####DTY####

INSERT INTO TB_COMPARAM_MAS (CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,USER_ID,LANG_ID,LMODDATE,CPM_LIMITED_YN,CPM_MODULE_NAME,UPDATED_BY,UPDATED_DATE,CPM_CONFIG,CPM_EDIT,LG_IP_MAC,LG_IP_MAC_UPD,CPM_REPLICATE_FLAG,CPM_TYPE,CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,CPD_EDIT_DEFAULT,CPD_EDIT_STATUS) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),
'DTY','Deposit Types','A',1,1,now(),NULL,'COM',NULL,NULL,'Y','N',NULL,NULL,'Y','N','Y','N','N','Y','N','N');

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Security  Deposits','S','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='DTY'),
1,1,now(),'N',NULL,NULL,'Security  Deposits',NULL,NULL,NULL);
commit;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'EMD ','EM','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='DTY'),
1,1,now(),'N',NULL,NULL,'EMD ',NULL,NULL,NULL);
commit;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Water Deposit ','WD','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='DTY'),
1,1,now(),'N',NULL,NULL,'Water Deposit ',NULL,NULL,NULL);
commit;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Rental Deposit ','RD','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='DTY'),
1,1,now(),'N',NULL,NULL,'Rental Deposit ',NULL,NULL,NULL);
commit;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Staff  Deposit ','ST','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='DTY'),
1,1,now(),'N',NULL,NULL,'Staff  Deposit ',NULL,NULL,NULL);
commit;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Illegal Construction Deposit ','IC','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='DTY'),
1,1,now(),'N',NULL,NULL,'Illegal Construction Deposit',NULL,NULL,NULL);
commit;

****VNT**********
INSERT INTO TB_COMPARAM_MAS (CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,USER_ID,LANG_ID,LMODDATE,CPM_LIMITED_YN,CPM_MODULE_NAME,UPDATED_BY,UPDATED_DATE,CPM_CONFIG,CPM_EDIT,LG_IP_MAC,LG_IP_MAC_UPD,CPM_REPLICATE_FLAG,CPM_TYPE,CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,CPD_EDIT_DEFAULT,CPD_EDIT_STATUS) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),,
'VNT','Vendor Type','A',1,1,now(),NULL,'COM',NULL,NULL,'Y','N',NULL,NULL,'Y','N','Y','N','N','Y','N','N');
commit;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Contractor ','C','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='VNT'),
1,1,now(),'N',NULL,NULL,'Contractor',NULL,NULL,NULL);
commit;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Others ','O','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='VNT'),
1,1,now(),'N',NULL,NULL,'Others',NULL,NULL,NULL);
commit;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Supplier ','S','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='VNT'),
1,1,now(),'N',NULL,NULL,'Supplier',NULL,NULL,NULL);
commit;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Contractors','CNS','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='VNT'),
1,1,now(),'N',NULL,NULL,'Contractors',NULL,NULL,NULL);
commit;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Employee','E','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='VNT'),
1,1,now(),'N',NULL,NULL,'Employee',NULL,NULL,NULL);
commit;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Advertiser','A','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='VNT'),
1,1,now(),'N',NULL,NULL,'Advertiser',NULL,NULL,NULL);
commit;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Professional/Technical Services','P','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='VNT'),
1,1,now(),'N',NULL,NULL,'Professional/Technical Services',NULL,NULL,NULL);
commit;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Tenant','T','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='VNT'),
1,1,now(),'N',NULL,NULL,'Tenant',NULL,NULL,NULL);
commit;

******VST*******
INSERT INTO TB_COMPARAM_MAS (CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,USER_ID,LANG_ID,LMODDATE,CPM_LIMITED_YN,CPM_MODULE_NAME,UPDATED_BY,UPDATED_DATE,CPM_CONFIG,CPM_EDIT,LG_IP_MAC,LG_IP_MAC_UPD,CPM_REPLICATE_FLAG,CPM_TYPE,CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,CPD_EDIT_DEFAULT,CPD_EDIT_STATUS) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),
'VST','Vendor Sub-Types','A',1,1,now(),NULL,'COM',NULL,NULL,'Y','N',NULL,NULL,'Y','N','Y','N','N','Y','N','N');
commit;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Association of Persons','AP','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='VST'),
1,1,now(),'N',NULL,NULL,'Association of Persons',NULL,NULL,NULL);
commit;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Body of Individuals','BI','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='VST'),
1,1,now(),'N',NULL,NULL,'Body of Individuals',NULL,NULL,NULL);
commit;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'A Domestic Company (Public subst. interested)','DCPSI','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='VST'),
1,1,now(),'N',NULL,NULL,'A Domestic Company (Public subst. interested)',NULL,NULL,NULL);
commit;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Company other than Domestic Company','C','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='VST'),
1,1,now(),'N',NULL,NULL,'Company other than Domestic Company',NULL,NULL,NULL);
commit;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Individual','I','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='VST'),
1,1,now(),'N',NULL,NULL,'Individual',NULL,NULL,NULL);
commit;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Registered Partnership (Business)','R','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='VST'),
1,1,now(),'N',NULL,NULL,'Registered Partnership (Business)',NULL,NULL,NULL);
commit;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Registered Partnership (Business)','P','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='VST'),
1,1,now(),'N',NULL,NULL,'Registered Partnership (Business)',NULL,NULL,NULL);
commit;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Registered Partnership (Professional)','AJP','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='VST'),
1,1,now(),'N',NULL,NULL,'Registered Partnership (Professional)',NULL,NULL,NULL);
commit;


INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'A Domestic Company (Public non subst. interested)','DCPNSI','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='VST'),
1,1,now(),'N',NULL,NULL,'A Domestic Company (Public non subst. interested)',NULL,NULL,NULL);
commit;

	
******VSS*******
INSERT INTO TB_COMPARAM_MAS (CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,USER_ID,LANG_ID,LMODDATE,CPM_LIMITED_YN,
CPM_MODULE_NAME,UPDATED_BY,UPDATED_DATE,CPM_CONFIG,CPM_EDIT,LG_IP_MAC,LG_IP_MAC_UPD,CPM_REPLICATE_FLAG,
CPM_TYPE,CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,CPD_EDIT_DEFAULT,CPD_EDIT_STATUS) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),
'VSS','Vendor Status','A',1,1,now(),NULL,'COM',NULL,NULL,'Y','N',NULL,NULL,'N','N','Y','N','N','Y','N','N');
commit;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Active','AC','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='VSS'),
1,1,now(),'N',NULL,NULL,'Active',NULL,NULL,NULL);
commit;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Inactive','IN','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='VSS'),
1,1,now(),'N',NULL,NULL,'Inactive',NULL,NULL,NULL);
commit;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Blacklist','BL','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='VSS'),
1,1,now(),'N',NULL,NULL,'Blacklist',NULL,NULL,NULL);
commit;

******DOC*******
INSERT INTO TB_COMPARAM_MAS (CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,USER_ID,LANG_ID,LMODDATE,CPM_LIMITED_YN,CPM_MODULE_NAME,UPDATED_BY,UPDATED_DATE,CPM_CONFIG,CPM_EDIT,LG_IP_MAC,LG_IP_MAC_UPD,CPM_REPLICATE_FLAG,CPM_TYPE,CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,CPD_EDIT_DEFAULT,CPD_EDIT_STATUS) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL)
'DOC','Document Type','A',1,1,now(),NULL,'COM',NULL,NULL,'Y','N',NULL,NULL,'Y','N','Y','N','N','Y','N','N');
commit;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'.PDF','PDF','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='DOC'),
1,1,now(),'N',NULL,NULL,'.PDF',NULL,NULL,NULL);
commit;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'.DOC','DOC','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='DOC'),
1,1,now(),'N',NULL,NULL,'.DOC',NULL,NULL,NULL);
commit;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'.JPG','.JPG','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='DOC'),
1,1,now(),'N',NULL,NULL,'.JPG',NULL,NULL,NULL);
commit;

******TAC*********
Insert into TB_COMPARAM_MAS
(CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,
LANG_ID,LMODDATE,CPM_MODULE_NAME,CPM_CONFIG,
CPM_EDIT,LG_IP_MAC,CPM_REPLICATE_FLAG,CPM_TYPE,
CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,
CPD_EDIT_DEFAULT,CPD_EDIT_STATUS,USER_ID) 
values 
(fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),'TAC','Tax Category & Tax Sub Category','A',
1,now(),'COM','Y',
'N',NULL,'Y','H',
NULL,NULL,NULL,'Y',NULL,NULL,1);
commit;


Insert into TB_COMPARENT_MAS
(com_id,cpm_id, 
com_desc,com_value,com_level,com_status, 
orgid,user_id,lang_id,lmoddate, 
com_desc_mar,com_replicate_flag,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARENT_MAS','COM_ID',NULL,NULL),(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='TAC'),
'Tax Category','W',1,'Y',
(select orgid from TB_ORGANISATION where default_status='Y'),1,1,now(),
'कर श्रेणी',NULL,NULL);
commit;

Insert into TB_COMPARENT_MAS
(com_id,cpm_id,
com_desc,com_value,com_level,com_status, 
orgid,user_id,lang_id,lmoddate, 
com_desc_mar,com_replicate_flag,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARENT_MAS','COM_ID',NULL,NULL),(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='TAC'),
'Tax Sub Category','P',2,'Y',
(select orgid from TB_ORGANISATION where default_status='Y'),1,1,now(),
'कर उप श्रेणी',NULL,NULL);
commit;

********ACK*******
INSERT INTO TB_COMPARAM_MAS (CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,USER_ID,LANG_ID,LMODDATE,CPM_LIMITED_YN,CPM_MODULE_NAME,UPDATED_BY,UPDATED_DATE,CPM_CONFIG,CPM_EDIT,LG_IP_MAC,LG_IP_MAC_UPD,CPM_REPLICATE_FLAG,CPM_TYPE,CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,CPD_EDIT_DEFAULT,CPD_EDIT_STATUS) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),
'ACK','Acknowledgement Format','A',1,1,now(),NULL,'COM',NULL,NULL,'Y','N',NULL,NULL,'Y','N','Y','N','N','Y','N','N');
commit;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'General','G','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='ACK'),
1,1,now(),'N',NULL,NULL,'सामान्य',NULL,NULL,NULL);
commit;



INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Service Department/Specific','S','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='ACK'),
1,1,now(),'N',NULL,NULL,'सेवा विभाग / विशिष्ट',NULL,NULL,NULL);
commit;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,
CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Service Button Not Applicable','B','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='ACK'),
1,1,now(),'N',NULL,NULL,'सेवा बटन लागू नहीं है',NULL,NULL,NULL);
commit;


********ACK*******
INSERT INTO TB_COMPARAM_MAS (CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,USER_ID,LANG_ID,LMODDATE,CPM_LIMITED_YN,CPM_MODULE_NAME,UPDATED_BY,UPDATED_DATE,CPM_CONFIG,CPM_EDIT,LG_IP_MAC,LG_IP_MAC_UPD,CPM_REPLICATE_FLAG,CPM_TYPE,CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,CPD_EDIT_DEFAULT,CPD_EDIT_STATUS) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),
'ACK','Acknowledgement Format','A',1,1,now(),NULL,'COM',NULL,NULL,'Y','N',NULL,NULL,'Y','N','Y','N','N','Y','N','N');
commit;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'General','G','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='ACK'),
1,1,now(),'N',NULL,NULL,'सामान्य',NULL,NULL,NULL);
commit;



INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Service Department/Specific','S','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='ACK'),
1,1,now(),'N',NULL,NULL,'सेवा विभाग / विशिष्ट',NULL,NULL,NULL);
commit;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,
CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Service Button Not Applicable','B','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='ACK'),
1,1,now(),'N',NULL,NULL,'सेवा बटन लागू नहीं है',NULL,NULL,NULL);
commit;


********RDC*******
INSERT INTO TB_COMPARAM_MAS (CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,USER_ID,LANG_ID,LMODDATE,CPM_LIMITED_YN,CPM_MODULE_NAME,UPDATED_BY,UPDATED_DATE,CPM_CONFIG,CPM_EDIT,LG_IP_MAC,LG_IP_MAC_UPD,CPM_REPLICATE_FLAG,CPM_TYPE,CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,CPD_EDIT_DEFAULT,CPD_EDIT_STATUS) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),
'RDC','Payable Deposit Status','A',1,1,now(),NULL,'COM',NULL,NULL,'Y','N',NULL,NULL,'N','N','Y','N','N','Y','N','N');
commit;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Deposit Refunded','DR','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='RDC'),
1,1,now(),'N',NULL,NULL,'जमा वापसी',NULL,NULL,NULL);
commit;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Receipt Deleted','DR','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='RDC'),
1,1,now(),'N',NULL,NULL,'जमा वापसी',NULL,NULL,NULL);
commit;





********ATY*******
INSERT INTO TB_COMPARAM_MAS (CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,USER_ID,LANG_ID,LMODDATE,CPM_LIMITED_YN,CPM_MODULE_NAME,UPDATED_BY,UPDATED_DATE,CPM_CONFIG,CPM_EDIT,LG_IP_MAC,LG_IP_MAC_UPD,CPM_REPLICATE_FLAG,CPM_TYPE,CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,CPD_EDIT_DEFAULT,CPD_EDIT_STATUS) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),
'ACK','Acknowledgement Format','A',1,1,now(),NULL,'COM',NULL,NULL,'Y','N',NULL,NULL,'Y','N','Y','N','N','Y','N','N');
commit;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'General','G','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='ACK'),
1,1,now(),'N',NULL,NULL,'सामान्य',NULL,NULL,NULL);
commit;



INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Service Department/Specific','S','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='ACK'),
1,1,now(),'N',NULL,NULL,'सेवा विभाग / विशिष्ट',NULL,NULL,NULL);
commit;

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,
CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),
'Service Button Not Applicable','B','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='ACK'),
1,1,now(),'N',NULL,NULL,'सेवा बटन लागू नहीं है',NULL,NULL,NULL);
commit;


INSERT INTO tb_comparam_mas (CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,USER_ID,LANG_ID,LMODDATE,CPM_LIMITED_YN,CPM_MODULE_NAME,UPDATED_BY,UPDATED_DATE,CPM_CONFIG,CPM_EDIT,LG_IP_MAC,LG_IP_MAC_UPD,CPM_REPLICATE_FLAG,CPM_TYPE,
CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,CPD_EDIT_DEFAULT,CPD_EDIT_STATUS) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),
'SMT','SMS and Email Template Type','A',1,1,'2018-09-10 23:29:24',NULL,'COM',NULL,NULL,'Y','N',NULL,NULL,'N','N','Y','Y','N','Y','Y','N');

INSERT INTO tb_comparam_det (CPD_ID,
ORGID,
CPD_DESC,
CPD_VALUE,
CPD_STATUS,
CPM_ID,USER_ID,
LANG_ID,
LMODDATE,
CPD_DEFAULT,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),1,
'Approval Message','AA','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='SMT'),
1,1,'2018-09-10 23:29:24','N','स्वीकृति संदेश','',NULL);


INSERT INTO tb_comparam_det (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,
CPD_DEFAULT,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),1,'Rejected Message','AR','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='SMT'),1,1,'2018-09-10 23:29:24','N','Rejected Message','',NULL);

INSERT INTO tb_comparam_det (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,
CPD_DEFAULT,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),1,'Submitted Message','AS','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='SMT'),1,1,'2018-09-10 23:29:24','N','Submitted Message','',NULL);

INSERT INTO tb_comparam_det (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,
CPD_DEFAULT,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),1,'Hold Message','AF','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='SMT'),
1,1,'2018-09-10 23:29:24','N','Hold Message','',NULL);

INSERT INTO tb_comparam_det (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,
CPD_DEFAULT,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),1,'General Message','GM','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='SMT')
,1,1,'2018-09-10 23:29:24','N','General Message','',NULL);

INSERT INTO tb_comparam_det (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,
CPD_DEFAULT,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),1,'Completed Message','CM','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='SMT'),
1,1,'2018-09-10 23:29:24','N','Completed Message','',NULL);

INSERT INTO tb_comparam_det (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,
CPD_DEFAULT,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),1,'OTP Message','OM','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='SMT'),1,1,'2018-09-10 23:29:24','N','OTP Message','',NULL);

INSERT INTO tb_comparam_det (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,
CPD_DEFAULT,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),1,'Task Notification','TN','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='SMT'),1,1,'2018-09-10 23:29:24','N','Task Notification','',
NULL);
COMMIT;



















