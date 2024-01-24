prompt "Creating Default Organisation"
insert into TB_ORGANISATION 
(orgid,o_nls_orgname,user_id,lang_id, 
lmoddate,org_short_nm,org_address, 
o_nls_orgname_mar,org_address_mar, 
org_status,default_status,ulb_org_id)
values 
(fn_java_sq_generation('AUT','TB_ORGANISATION','ORGID',NULL,NULL), 
'ABM Knowledgeware Ltd.,',1,1, SYSDATE, 'ABM',
'ABM House, Plot No. 268, Linking Road, Bandra (West), Mumbai', 
'ABM Knowledgeware Ltd.,',
'ABM House, Plot No. 268, Linking Road, Bandra (West), Mumbai', 
'A' ,
'Y',
01);

prompt "Creating Default Location Head Office"
insert into TB_LOCATION_MAS
(loc_id,loc_name_eng,loc_name_reg,loc_source,loc_type, 
loc_active,orgid,user_id,lang_id,lmoddate,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_LOCATION_MAS','LOC_ID',NULL,NULL),
'Head Office','Head Office','O','Y',
'Y',(select orgid from TB_ORGANISATION where default_status='Y') ,1,1,sysdate,'AMCTP | 192.168.0.150 | 44-87-FC-D2-66-D1');
commit;

prompt "Creating Default Designation SUPERAdmin ABM"
prompt "Creating Default Designation Sub_admin "
insert into DESIGNATION
  (dsgid,
   dsgname,dsgname_reg,dsgshortname,locid, 
   isdeleted,user_id,lang_id,lmoddate,lg_ip_mac)
values
  (fn_java_sq_generation('AUT','DESIGNATION','DSGID',NULL,NULL),
   'ADMIN','ADMIN','ADM',(select loc_id from TB_LOCATION_MAS),
   '0',1,1,SYSDATE,'AMCTP | 192.168.0.150 | 44-87-FC-D2-66-D1');

commit;

prompt "Creating Designation Mapping with Organisation"
insert into tb_org_designation
(map_id,
dsgid,map_status,orgid,
user_id,lang_id,lmoddate,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_ORG_DESIGNATION','MAP_ID',NULL,NULL),
 (select dsgid from designation),'A',(select orgid from TB_ORGANISATION where default_status='Y'),
 1,1,sysdate,'AMCTP | 192.168.0.150 | 44-87-FC-D2-66-D1');
 commit;
 
 prompt "Creating Default Department SUPERIT"
 
 insert into TB_DEPARTMENT
  (dp_deptid, 
  dp_deptcode,dp_deptdesc,dp_name_mar,dp_check, 
  user_id,lang_id,lmoddate,status,lg_ip_mac)
values
  (fn_java_sq_generation('AUT','TB_DEPARTMENT','DP_DEPTID',NULL,NULL),
   'CFC','CFC','CFC','Y',1,1,SYSDATE,'A','AMCTP | 192.168.0.150 | 44-87-FC-D2-66-D1');
COMMIT;

prompt "Creating Department Mapping with Organisation for CFC"

insert into TB_DEPORG_MAP
(map_id, 
dp_deptid,map_status,orgid, 
user_id,lang_id,lmoddate,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_DEPORG_MAP','MAP_ID',NULL,NULL),
 (select dp_deptid from tb_department where dp_deptcode='CFC'),'A',(select orgid from TB_ORGANISATION where default_status='Y'),
 1,1,sysdate,'AMCTP | 192.168.0.150 | 44-87-FC-D2-66-D1');

commit;

prompt "Creating Department Mapping with Location"

insert into TB_DEPT_LOCATION
(mapid, 
dp_deptid,loc_id,isdeleted, 
orgid,user_id,lang_id,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_DEPT_LOCATION','MAPID',NULL,NULL),
(select dp_deptid from tb_department where dp_deptcode='CFC'),(select loc_id from tb_location_mas),'0',
(select orgid from TB_ORGANISATION where default_status='Y'),1,1,'AMCTP | 192.168.0.150 | 44-87-FC-D2-66-D1') ;
commit;

 prompt "Creating Default Department COMMON"
 
 insert into TB_DEPARTMENT
  (dp_deptid, 
  dp_deptcode,dp_deptdesc,dp_name_mar,dp_check, 
  user_id,lang_id,lmoddate,status,lg_ip_mac)
values
  (fn_java_sq_generation('AUT','TB_DEPARTMENT','DP_DEPTID',NULL,NULL),
   'COM','COMMON','COMMON','Y',1,1,SYSDATE,'A','AMCTP | 192.168.0.150 | 44-87-FC-D2-66-D1');
COMMIT;

prompt "Creating Department Mapping with Organisation for COMMON"

insert into TB_DEPORG_MAP
(map_id, 
dp_deptid,map_status,orgid, 
user_id,lang_id,lmoddate,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_DEPORG_MAP','MAP_ID',NULL,NULL),
 (select dp_deptid from tb_department where dp_deptcode='COM'),'A',(select orgid from TB_ORGANISATION where default_status='Y'),
 1,1,sysdate,'AMCTP | 192.168.0.150 | 44-87-FC-D2-66-D1');

commit;

prompt "Creating Department Mapping with Location"

insert into TB_DEPT_LOCATION
(mapid, 
dp_deptid,loc_id,isdeleted, 
orgid,user_id,lang_id,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_DEPT_LOCATION','MAPID',NULL,NULL),
(select dp_deptid from tb_department where dp_deptcode='COM'),(select loc_id from tb_location_mas),'0',
(select orgid from TB_ORGANISATION where default_status='Y'),1,1,'AMCTP | 192.168.0.150 | 44-87-FC-D2-66-D1') 


prompt "Creating Menu Entitlement"

insert into TB_SYSMODFUNCTION 
(smfid, 
smfname,smfdescription,smfflag,smfaction,smfcategory, 
user_id,ondate,ontime,action,isdeleted,smfsystemid,smfcode, 
lang_id,smfname_mar,smfsrno,lg_ip_mac, 
sm_parent_id,depth,sm_param1,sm_param2)
values (fn_java_sq_generation('AUT','TB_SYSMODFUNCTION','SMFID',NULL,NULL), 
'Entitlement', 'Entitlement', 'S', null, 'U',
1,sysdate,to_char(sysdate,'hh:mm:ss'), 'A', '0', null, null,1, 'Entitlement', null, 'AMCTP | 192.168.0.150 | 44-87-FC-D2-66-D1', 
null, null, null, null);

insert into TB_SYSMODFUNCTION 
(smfid, 
smfname,smfdescription,smfflag,smfaction,smfcategory, 
user_id,ondate,ontime,action,isdeleted,smfsystemid,smfcode, 
lang_id,smfname_mar,smfsrno,lg_ip_mac, 
sm_parent_id,depth,sm_param1,sm_param2)
values (fn_java_sq_generation('AUT','TB_SYSMODFUNCTION','SMFID',NULL,NULL),
'Entitlement', 'Entitlement', 'M', 'entitlement.html', null, 
1,sysdate,to_char(sysdate,'hh:mm:ss'),'A','0', null, null,1, 'Entitlement',null,'AMCTP | 192.168.0.150 | 44-87-FC-D2-66-D1',
1,null, null, null);

commit;

prompt "Creating Default Group SUPER ADMIN"

insert into TB_GROUP_MAST
  (gm_id,gr_code,gr_desc_eng,gr_desc_reg,orgid,gr_status, 
   lang_id,user_id,entry_date,lg_ip_mac)
values
(fn_java_sq_generation('AUT','TB_GROUP_MAST','GM_ID',NULL,NULL),
 'SUPER_ADMIN','Menus for Super Admin','Menus for Super Admin',
  (select orgid from TB_ORGANISATION where default_status='Y'),'A',
  1,1,SYSDATE,'AMCTP | 192.168.0.150 | 44-87-FC-D2-66-D1');
COMMIT;





