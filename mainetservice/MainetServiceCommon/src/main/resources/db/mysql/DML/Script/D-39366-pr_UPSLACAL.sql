USE suda_service1;
DROP procedure IF EXISTS pr_UPSLACAL;

DELIMITER $$
USE suda_service1$$
CREATE PROCEDURE pr_UPSLACAL()
BEGIN
	declare n_wfdid bigint(12);
    declare n_sla bigint(15);
    declare n_cnt int default 0;
    
	declare v_org INT default 0;
    declare row,col INT DEFAULT 1;
    declare i INTEGER DEFAULT 0;
	
    declare cu_wsla cursor for select sla,wfd_id from 
       (select round((case when (select CPD_DESC from tb_comparam_det where cpd_id=wfd_unit) ='DAY' then (wfd_sla*86400000) when (select CPD_DESC from tb_comparam_det where cpd_id=wfd_unit) ='Hour' then (wfd_sla*3600000) end),0)  sla,wfd_id,SLA_CAL
							  from tb_workflow_det b where SERVICE_EVENT_ID in
                              (select SERVICE_EVENT_ID from tb_services_event where event_id=141) and b.wfd_sla<>0) cr
                              where sla<>sla_cal;
	
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_org = 1;
    
   BEGIN
      
    open cu_wsla;
	  get_org:loop
      	fetch cu_wsla into n_sla,n_wfdid;
      			if v_org=1 then
      	          LEAVE get_org;
      			end if;
           
		         update tb_workflow_det set SLA_CAL=n_sla where wfd_id=n_wfdid;
                 commit;
           
      	end loop;	  
          close cu_wsla;
   END;

END$$

DELIMITER ;

	


