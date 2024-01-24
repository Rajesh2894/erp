CREATE OR REPLACE PROCEDURE INPORTSERV()
BEGIN
	declare n_orgid bigint(12);
    declare n_pgid bigint(12);
    
	DECLARE v_finished INTEGER DEFAULT 0;
    DECLARE i INTEGER DEFAULT 0;
	
    declare cu_og cursor for select orgid from tb_organisation;
    declare continue handler for not found set v_finished=1;
    DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;

   BEGIN



    open cu_og;
	get_og : loop
	fetch cu_og into n_orgid;

		if v_finished=1 then
	        Leave get_og;
		end if;	

          
            update service45.tb_services_mst set SM_SERV_ACTIVE=
            (select a.cpd_id from 
             service45.tb_comparam_det a,
			 service45.tb_comparam_mas b
			 where a.cpm_id=b.cpm_id and b.cpm_prefix='ACN'
			 and a.cpd_value='A')
             where orgid=n_orgid;
             commit;


			insert into portal45.tb_portal_service_master
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
			from service45.tb_services_mst a,
			service45.tb_department b
			where b.dp_deptid=a.cdm_dept_id and
			a.sm_actual='Y' and
            SM_SHORTDESC not in
            (select PSM_SHORT_NAME from portal45.tb_portal_service_master where orgid=6) and
			orgid=n_orgid);
			commit;

				
    end loop get_og;
    close cu_og;
   END; 
END



