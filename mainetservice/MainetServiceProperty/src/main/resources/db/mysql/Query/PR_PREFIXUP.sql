USE `service45`;
DROP procedure IF EXISTS `Pr_prefixup`;

DELIMITER $$
USE `service45`$$
CREATE PROCEDURE Pr_prefixup()
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

          Insert into TB_COMPARAM_DET (cpd_id,orgid,cpd_desc,cpd_value,cpd_status,cpm_id,user_id,lang_id,lmoddate,cpd_default, 
                                       cpd_desc_mar,cpd_others,lg_ip_mac)
          values (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),n_orgid,'Vaccant Land','VCL','A',(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='CSC'),1,1,now(),'N','Vaccant Land',NULL,NULL);
          COMMIT;
            	
    end loop get_og;
    close cu_og;
   END; 
END$$

DELIMITER ;

