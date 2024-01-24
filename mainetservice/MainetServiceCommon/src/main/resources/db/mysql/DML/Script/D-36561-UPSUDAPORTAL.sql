drop procedure UPSUDAPORTAL;
DELIMITER $$
CREATE PROCEDURE `UPSUDAPORTAL`()
BEGIN
	declare n_orgid bigint(12);
    
    declare v_grseq INTEGER DEFAULT 0;
	DECLARE v_finished INTEGER DEFAULT 0;
    
	
    declare cu_orgid cursor for select orgid from tb_organisation where orgid in (175,176,182)
	
	declare continue handler for not found set v_finished=1;
    DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;

/*MBA */   
   open cu_orgid;
  	get_mba : loop
	fetch cu_orgid into n_orgid;
		if v_finished=1 then
	        Leave get_mba;
		end if;	
   
   
insert into tb_group_mast
(GM_ID,GR_CODE,GR_NAME,GR_NAME_REG,GR_DESC_ENG, 
GR_DESC_REG,ORGID,ORG_SPECIFIC,GR_STATUS,
LANG_ID,USER_ID,ENTRY_DATE,LG_IP_MAC,UPDATED_BY, 
UPDATED_DATE,LG_IP_MAC_UPD,GR_DEFAULT) 
(select fn_java_sq_generation('AUT','TB_GROUP_MAST','GM_ID',NULL,NULL), 
GR_CODE,GR_NAME,GR_NAME_REG,GR_DESC_ENG,GR_DESC_REG,n_orgid, 
ORG_SPECIFIC,GR_STATUS,LANG_ID,USER_ID,ENTRY_DATE,LG_IP_MAC, 
UPDATED_BY,UPDATED_DATE,LG_IP_MAC_UPD,GR_DEFAULT from tb_group_mast where orgid=1); 

insert into employee
(EMPID,EMPNAME,EMPMNAME,EMPLNAME,EMPOSLOGINNAME,EMPLOGINNAME, 
EMPPASSWORD,EMPEMAIL,EMPMOBNO,GM_ID,ORGID,CREATED_BY,CREATED_DATE) 
(select fn_java_sq_generation('AUT','EMPLOYEE','EMPID',NULL,NULL),
EMPNAME,EMPMNAME,EMPLNAME,EMPOSLOGINNAME,  
EMPLOGINNAME,EMPPASSWORD,EMPEMAIL,EMPMOBNO,
(select GM_ID from tb_group_mast where GR_CODE='SUPER_ADMIN' and orgid=n_orgid),
n_orgid, 
CREATED_BY,CREATED_DATE from service1.employee where orgid=n_orgid and EMPLOGINNAME='SysAdmin'); 

insert into employee
(EMPID,EMPNAME,EMPMNAME,EMPLNAME,EMPOSLOGINNAME,EMPLOGINNAME, 
EMPPASSWORD,EMPEMAIL,EMPMOBNO,GM_ID,ORGID,CREATED_BY,CREATED_DATE) 
(select fn_java_sq_generation('AUT','EMPLOYEE','EMPID',NULL,NULL),
EMPNAME,EMPMNAME,EMPLNAME,EMPOSLOGINNAME,  
EMPLOGINNAME,EMPPASSWORD,EMPEMAIL,EMPMOBNO,GM_ID,n_orgid, 
CREATED_BY,CREATED_DATE from portal1.employee where orgid=1 and empname='GUEST');

insert into tb_portal_service_master
(PSM_ID,PSM_SERVICE_ID,PSM_SERVICE_NAME,PSM_SERVICE_NAME_REG,PSM_SHORT_NAME,ORGID,USER_ID,
LANG_ID,LMODDATE,IS_DELETED,PSM_DP_DEPTID,PSM_DP_DEPTCODE,PSM_DP_DEPTDESC,PSM_DP_NAME_MAR)
(select fn_java_sq_generation('AUT','TB_PORTAL_SERVICE_MASTER','PSM_ID',NULL,NULL), 
a.sm_service_id,
a.sm_service_name,
a.sm_service_name_mar,
a.sm_shortdesc,
n_orgid,
a.created_by,
a.lang_id,
a.created_date,
1,
b.DP_DEPTID,
b.DP_DEPTCODE,
b.DP_DEPTDESC,
b.DP_NAME_MAR
from service1.tb_services_mst a,
service1.tb_department b
where b.dp_deptid=a.cdm_dept_id and
orgid=n_orgid);

COMMIT;

   
end loop get_mba;
    close cu_orgid;
END$$
DELIMITER ;
