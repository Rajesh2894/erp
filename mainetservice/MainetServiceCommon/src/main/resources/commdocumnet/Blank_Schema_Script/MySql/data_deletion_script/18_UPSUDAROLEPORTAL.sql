drop procedure UPSUDAROLEPORTAL;
DELIMITER $$
CREATE PROCEDURE UPSUDAROLEPORTAL()
BEGIN
	declare n_orgid bigint(12);
    declare n_empid bigint(12);
	
    declare v_grseq INTEGER DEFAULT 0;
	DECLARE v_finished INTEGER DEFAULT 0;
    
	
    declare cu_employee cursor for select empid,orgid from suda_portal1.employee where orgid>=14 and empname<>'GUEST';
    declare continue handler for not found set v_finished=1;
    DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;

/*MBA */   
   open cu_employee;
  	get_mba : loop
	fetch cu_employee into n_empid,n_orgid;
		if v_finished=1 then
	        Leave get_mba;
		end if;	
		
		insert into role_entitlement
		(role_et_id,role_id,smfid,et_parent_id,is_active,org_id)
		values
		(fn_java_sq_generation('AUT','ROLE_ENTITLEMENT','ROLE_ET_ID',NULL,NULL),
		(select gm_id from TB_GROUP_MAST where GR_CODE='SUPER_ADMIN'and orgid=n_orgid),
		(select smfid from TB_SYSMODFUNCTION where smfflag='S' and smfname='Entitlement'),
		0,
		0,
		n_orgid);
		commit;

		insert into role_entitlement
		(role_et_id,role_id,smfid,et_parent_id,is_active,org_id)
		values
		(fn_java_sq_generation('AUT','ROLE_ENTITLEMENT','ROLE_ET_ID',NULL,NULL),
		(select gm_id from TB_GROUP_MAST where GR_CODE='SUPER_ADMIN' and orgid=n_orgid),
		(select smfid from TB_SYSMODFUNCTION where smfaction='entitlement.html'),
		(select smfid from TB_SYSMODFUNCTION where smfflag='S' and smfname='Entitlement'),
		0,
		n_orgid);
		
		commit;

end loop get_mba;
    close cu_employee;
END$$
DELIMITER ;
