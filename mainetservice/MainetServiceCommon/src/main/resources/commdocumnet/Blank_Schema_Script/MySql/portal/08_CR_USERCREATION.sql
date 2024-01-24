prompt "Creating Default Employee MBA"

Insert into employee
(empid,
 empname,emploginname,emppassword,
 dsgid,
 isdeleted,orgid,created_by,created_date, 
 empexpiredt,lg_ip_mac,
 dp_deptid,auth_status, 
 aut_mob,cpd_ttl_id,
 emplname,emp_gender, 
 isuploaded,gm_id)
 valueS
 (fn_java_sq_generation('AUT','EMPLOYEE','EMPID',NULL,NULL),
 'Administrator','SysAdmin','SvrhvE79;=',
 (select dsgid from designation where DSGSHORTNAME='ADM'),
 0,(select orgid from TB_ORGANISATION where default_status='Y'),1,now(),
 '2030-03-31 14:48:54','AMCTP | 192.168.0.150 | 44-87-FC-D2-66-D1',
 (select dp_deptid from TB_DEPARTMENT where dp_deptcode='CFC'),'A','Y',
 (select cpd_id from TB_COMPARAM_DET where cpd_value='MR.'),
 'SysAdmin',
 (select cpd_value from TB_COMPARAM_MAS A, TB_COMPARAM_DET B
 where A.CPM_ID=B.CPM_ID AND cpm_prefix='GEN' and cpd_value='M'),
 'Y',(select gm_id from TB_GROUP_MAST WHERE GR_CODE='SUPER_ADMIN'));
 commit;
 
prompt "Assiging Rights to Default Employee MBA" 
insert into
role_entitlement
(role_et_id, 
role_id, 
smfid, 
et_parent_id,
is_active, 
org_id)
values
(fn_java_sq_generation('AUT','ROLE_ENTITLEMENT','ROLE_ET_ID',NULL,NULL),
(select gm_id from TB_GROUP_MAST where GR_CODE='SUPER_ADMIN'),
(select smfid from TB_SYSMODFUNCTION where smfflag='S'),
0,
0,
(select orgid from TB_ORGANISATION where default_status='Y')
);
commit;

prompt "Creating Default Employee MBA"
insert into
role_entitlement
(role_et_id, 
role_id, 
smfid, 
et_parent_id,
is_active, 
org_id)
values
(fn_java_sq_generation('AUT','ROLE_ENTITLEMENT','ROLE_ET_ID',NULL,NULL),
(select gm_id from TB_GROUP_MAST where GR_CODE='SUPER_ADMIN'),
(select smfid from TB_SYSMODFUNCTION where smfaction='entitlement.html'),
(select smfid from TB_SYSMODFUNCTION where smfflag='S'),
0,
(select orgid from TB_ORGANISATION where default_status='Y')
);
commit;
 
 prompt "Creating Default Employee GUEST"
 
 Insert into employee
(empid,
 empname,emploginname,emppassword,
 dsgid,
 isdeleted,orgid,created_by,created_date, 
 empexpiredt,lg_ip_mac,
 dp_deptid,auth_status, 
 aut_mob,cpd_ttl_id,
 emplname,emp_gender, 
 isuploaded,gm_id)
 valueS
 (fn_java_sq_generation('AUT','EMPLOYEE','EMPID',NULL,NULL),
 'GUEST','NOUSER','SvrhvE79;=',
 (select dsgid from designation where DSGSHORTNAME='CIT'),
 0,(select orgid from TB_ORGANISATION where default_status='Y'),1,now(),
 '2030-03-31 14:48:54','AMCTP | 192.168.0.150 | 44-87-FC-D2-66-D1',
 (select dp_deptid from TB_DEPARTMENT where dp_deptcode='CFC'),'A','Y',
 (select cpd_id from TB_COMPARAM_DET where cpd_value='MR.'),
 'NOUSER',
 (select cpd_value from TB_COMPARAM_MAS A, TB_COMPARAM_DET B
 where A.CPM_ID=B.CPM_ID AND cpm_prefix='GEN' and cpd_value='M'),
 'Y',null);
 commit;

 
 