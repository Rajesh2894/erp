---PORTAL  & SERVICE

propmt "Inserting TTL Prefix"

Insert into TB_COMPARAM_MAS
(CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,
LANG_ID,LMODDATE,CPM_MODULE_NAME,CPM_CONFIG,
CPM_EDIT,LG_IP_MAC,CPM_REPLICATE_FLAG,CPM_TYPE,
CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,
CPD_EDIT_DEFAULT,CPD_EDIT_STATUS,USER_ID) 
values (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),'TTL','Title','A',
1,now(),'CFC','Y',
'Y',NULL,'N','N',
NULL,NULL,NULL,'Y',
NULL,NULL,1);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),
'Mr.','MR.','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='TTL'),1,1,now(),'Y',
'श्री',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc, cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),
'Mrs.','MRS.','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='TTL'),1,1,now(),NULL,
'श्रीमती',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),
'Miss.','MISS.','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='TTL'),1,1,now(),NULL,
'कुमारी',NULL,NULL);

commit;

propmt "Inserting LOG Prefix"

Insert into TB_COMPARAM_MAS
(CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,
LANG_ID,LMODDATE,CPM_MODULE_NAME,CPM_CONFIG,
CPM_EDIT,LG_IP_MAC,CPM_REPLICATE_FLAG,CPM_TYPE,
CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,
CPD_EDIT_DEFAULT,CPD_EDIT_STATUS,USER_ID) 
values (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),'LOG','Login Logo','A',
1,now(),'CFC','Y',
'N',NULL,'Y','N',
NULL,NULL,NULL,'Y',NULL,NULL,1);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid,
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),
'http://192.168.100.230:8080/MainetService/assets/img/nrda-logo.png','nrda-logo.png','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='LOG'),1,1,now(),'N',
'http://192.168.100.230:8080/MainetService/assets/img/nrda-logo.png',NULL,NULL);
commit;


propmt "Inserting LNG Prefix"

Insert into TB_COMPARAM_MAS
(CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,
LANG_ID,LMODDATE,CPM_MODULE_NAME,CPM_CONFIG,
CPM_EDIT,LG_IP_MAC,CPM_REPLICATE_FLAG,CPM_TYPE,
CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,
CPD_EDIT_DEFAULT,CPD_EDIT_STATUS,USER_ID) 
values (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),'LNG','Language','A',
1,now(),'CFC','Y',
'Y',NULL,'Y','N',
NULL,NULL,NULL,'N',NULL,NULL,1);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),
'English','1','A',(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='LNG'),1,1,now(),'Y','अंग्रेज़ी',NULL,NULL);
commit;

Insert into TB_COMPARAM_DET
(cpd_id, orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),
'Hindi','2','A',(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='LNG'),1,1,now(),NULL,'हिंदी',NULL,NULL);
commit;

---------PMS
INSERT INTO TB_COMPARAM_MAS (CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,USER_ID,LANG_ID,LMODDATE,CPM_LIMITED_YN,CPM_MODULE_NAME,UPDATED_BY,UPDATED_DATE,CPM_CONFIG,CPM_EDIT,LG_IP_MAC,LG_IP_MAC_UPD,CPM_REPLICATE_FLAG,CPM_TYPE,CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,CPD_EDIT_DEFAULT,CPD_EDIT_STATUS) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),'PMS','Profile Master Section','A',1,1,NOW(),NULL,'CFC',NULL,NULL,'Y','Y',NULL,NULL,'Y','N',NULL,NULL,NULL,'Y','N',NULL);

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD)
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),'Mayor','M','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='PMS'),1,1,now(),NULL,NULL,NULL,'Mayor',NULL,NULL,NULL);

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD)
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),'Deputy Mayor','DM','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='PMS'),1,1,now(),NULL,NULL,NULL,'Deputy Mayor',NULL,NULL,NULL);

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD)
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),'Corporators','C','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='PMS'),1,1,now(),NULL,NULL,NULL,'Corporators',NULL,NULL,NULL);

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD)
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),'Standing Committees','SC','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='PMS'),1,1,now(),NULL,NULL,NULL,'Standing Committees',NULL,NULL,NULL);

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD)
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),'Additional commissioner','AC','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='PMS'),1,1,now(),NULL,NULL,NULL,'Additional commissioner',NULL,NULL,NULL);

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD)
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),'Commissioner','CO','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='PMS'),1,1,now(),NULL,NULL,NULL,'Commissioner',NULL,NULL,NULL);

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD)
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),'List Of committees','LOC','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='PMS'),1,1,now(),NULL,NULL,NULL,'List Of committees',NULL,NULL,NULL);

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD)
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),'Deputy Commissioner','DC','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='PMS'),1,1,now(),NULL,NULL,NULL,'Deputy Commissioner',NULL,NULL,NULL);



INSERT INTO TB_COMPARAM_MAS (CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,USER_ID,LANG_ID,LMODDATE,CPM_LIMITED_YN,CPM_MODULE_NAME,UPDATED_BY,UPDATED_DATE,CPM_CONFIG,CPM_EDIT,LG_IP_MAC,LG_IP_MAC_UPD,CPM_REPLICATE_FLAG,CPM_TYPE,CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,CPD_EDIT_DEFAULT,CPD_EDIT_STATUS) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),'NEC','New Employee Category','A',
1,1,now(),NULL,'CFC',NULL,NULL,'N','N','Utkarsha.Dhamne | 192.168.100.160 | 74-D4-35-AB-53-B0',NULL,'Y','N',NULL,NULL,NULL,'N','N',NULL);

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD)
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),'Engineer','G','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='NEC'),1,1,now(),NULL,NULL,NULL,NULL,NULL,'Utkarsha.Dhamne | 192.168.100.160 | 74-D4-35-AB-53-B0',NULL);

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD)
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),'Structural Engineer','F','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='NEC'),1,1,now(),NULL,NULL,NULL,NULL,NULL,'Utkarsha.Dhamne | 192.168.100.160 | 74-D4-35-AB-53-B0',NULL);

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD)
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),'Citizen','C','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='NEC'),1,1,now(),NULL,NULL,NULL,NULL,NULL,'Utkarsha.Dhamne | 192.168.100.160 | 74-D4-35-AB-53-B0',NULL);

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD)
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),'Builder','B','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='NEC'),1,1,now(),NULL,NULL,NULL,NULL,NULL,'Utkarsha.Dhamne | 192.168.100.160 | 74-D4-35-AB-53-B0',NULL);

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD)
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),'Architect','A','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='NEC'),1,1,now(),NULL,NULL,NULL,NULL,NULL,'Utkarsha.Dhamne | 192.168.100.160 | 74-D4-35-AB-53-B0',NULL);

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD)
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),'Town Planner','D','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='NEC'),1,1,now(),NULL,NULL,NULL,NULL,NULL,'Utkarsha.Dhamne | 192.168.100.160 | 74-D4-35-AB-53-B0',NULL);

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD)
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),'Supervisor','E','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='NEC'),1,1,now(),NULL,NULL,NULL,NULL,NULL,'Utkarsha.Dhamne | 192.168.100.160 | 74-D4-35-AB-53-B0',NULL);

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD)
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),'Advocate','L','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='NEC'),1,1,now(),NULL,NULL,NULL,NULL,NULL,'Utkarsha.Dhamne | 192.168.100.160 | 74-D4-35-AB-53-B0',NULL);

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD)
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),'Advertising Agency','AGN','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='NEC'),1,1,now(),NULL,NULL,NULL,NULL,NULL,'Utkarsha.Dhamne | 192.168.100.160 | 74-D4-35-AB-53-B0',NULL);

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD)
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),'Citizen Facilitation Center','CFC','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='NEC'),1,1,now(),NULL,NULL,NULL,NULL,NULL,'Utkarsha.Dhamne | 192.168.100.160 | 74-D4-35-AB-53-B0',NULL);

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD)
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),'Cyber Cafe','CC','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='NEC'),1,1,now(),NULL,NULL,NULL,NULL,NULL,'Utkarsha.Dhamne | 192.168.100.160 | 74-D4-35-AB-53-B0',NULL);

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD)
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),'Hospital','HS','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='NEC'),1,1,now(),NULL,NULL,NULL,NULL,NULL,'Utkarsha.Dhamne | 192.168.100.160 | 74-D4-35-AB-53-B0',NULL);

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD)
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),'Crematoria','CRM','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='NEC'),1,1,now(),NULL,NULL,NULL,NULL,NULL,'Utkarsha.Dhamne | 192.168.100.160 | 74-D4-35-AB-53-B0',NULL);



---------HQS
INSERT INTO TB_COMPARAM_MAS (CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,USER_ID,LANG_ID,LMODDATE,CPM_LIMITED_YN,CPM_MODULE_NAME,UPDATED_BY,UPDATED_DATE,CPM_CONFIG,CPM_EDIT,LG_IP_MAC,LG_IP_MAC_UPD,CPM_REPLICATE_FLAG,CPM_TYPE,CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,CPD_EDIT_DEFAULT,CPD_EDIT_STATUS) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),
'HQS','EIP Homepage Quick link Sections','A',1,1,now(),NULL,'CFC',NULL,NULL,'Y','N',NULL,NULL,'Y','N','Y','N','N','Y','Y','N');

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD)
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),'Quick links','Q','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='HQS'),1,1,now(),'N',NULL,NULL,'Quick links',NULL,NULL,NULL);

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD)
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),'Home page articles','A','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='HQS'),1,1,now(),'N',NULL,NULL,'Home page articles',NULL,NULL,NULL);

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD)
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),'Home page Center Links','C','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='HQS'),1,1,now(),'N',NULL,NULL,'Home page Center Links',NULL,NULL,NULL);

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD)
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),'Bottom Links','B','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='HQS'),1,1,now(),'N',NULL,NULL,'Bottom Links',NULL,NULL,NULL);

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD)
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),'Top Bar','T','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='HQS'),1,1,now(),'N',NULL,NULL,'Top Bar',NULL,NULL,NULL);
---------EST

INSERT INTO TB_COMPARAM_MAS (CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,USER_ID,LANG_ID,LMODDATE,CPM_LIMITED_YN,CPM_MODULE_NAME,UPDATED_BY,UPDATED_DATE,CPM_CONFIG,CPM_EDIT,LG_IP_MAC,LG_IP_MAC_UPD,CPM_REPLICATE_FLAG,CPM_TYPE,CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,CPD_EDIT_DEFAULT,CPD_EDIT_STATUS) 
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),
'EST','EIP Section Type','A',1,1,now(),NULL,'CFC',NULL,NULL,'Y','N',NULL,NULL,'Y','N','Y','N','N','Y','Y','Y');

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD)
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),'VIDEO','VD','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='EST'),1,1,now(),'N',NULL,NULL,'VIDEO',NULL,NULL,NULL);

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD)
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),'Photo Gallery','PHOTO','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='EST'),1,1,now(),'N',NULL,NULL,'Photo Gallery',NULL,NULL,NULL);

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD)
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),'Section Grid','SEC','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='EST'),1,1,now(),'N',NULL,NULL,'Section Grid',NULL,NULL,NULL);

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD)
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),'Table Grid','TABLE','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='EST'),1,1,now(),'N',NULL,NULL,'Table Grid',NULL,NULL,NULL);

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD)
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),'MAP','MAP','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='EST'),1,1,now(),'N',NULL,NULL,'MAP',NULL,NULL,NULL);

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD)
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),'Label','LBL','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='EST'),1,1,now(),'N',NULL,NULL,'Label',NULL,NULL,NULL);

INSERT INTO TB_COMPARAM_DET (CPD_ID,ORGID,CPD_DESC,CPD_VALUE,CPD_STATUS,CPM_ID,USER_ID,LANG_ID,LMODDATE,CPD_DEFAULT,UPDATED_BY,UPDATED_DATE,CPD_DESC_MAR,CPD_OTHERS,LG_IP_MAC,LG_IP_MAC_UPD)
VALUES (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
(select orgid from TB_ORGANISATION where default_status='Y'),'Portrait','PORTRAIT','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='EST'),1,1,now(),'N',NULL,NULL,'Portrait','NULL',NULL,NULL);


---ABOVE PREFIX +BELOW PREFIX for portal

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
CPD_EDIT_DEFAULT,CPD_EDIT_STATUS,USER_ID)  values (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),'STT','STATE','A',1,now(),'COM','Y','N','Y','N','Y','N','Y','Y','Y','Y',1);
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
COMMIT;

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
CPD_EDIT_DEFAULT,CPD_EDIT_STATUS,USER_ID)  values (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),'OST','Organisation Sub-Type','A',1,now(),'COM','Y','N','N','N','Y','N','N','Y','Y','Y',1);
commit;

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'A','A','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='OST'),
1,1,now(),'N','A',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'B','B','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='OST'),
1,1,now(),'N','B',NULL,NULL);

Insert into TB_COMPARAM_DET
(cpd_id,orgid, 
cpd_desc,cpd_value,cpd_status, 
cpm_id,user_id,lang_id,lmoddate,cpd_default, 
cpd_desc_mar,cpd_others,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),(select orgid from TB_ORGANISATION where default_status='Y'),'C','C','A',
(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='OST'),
1,1,now(),'N','C',NULL,NULL);
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



