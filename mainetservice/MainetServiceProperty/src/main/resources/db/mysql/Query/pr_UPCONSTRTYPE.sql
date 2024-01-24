USE suda_service1;
DROP procedure IF EXISTS pr_UPCONSTRTYPE;

DELIMITER $$
USE suda_service1$$
CREATE PROCEDURE pr_UPCONSTRTYPE()
BEGIN
	declare n_orgid bigint(12);
    declare n_assid bigint(12);
    declare n_cnt int default 0;
    
	declare v_org INT default 0;
    declare row,col INT DEFAULT 1;
    declare i INTEGER DEFAULT 0;
	
    declare cu_org cursor for select orgid from tb_organisation where orgid=17;
	
	declare cu_assid cursor for select b.mn_ass_id from tb_as_assesment_detail b 
		                       where b.orgid=17 group by b.mn_ass_id having count(1) =1;
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_org = 1;
    
   BEGIN
      
    open cu_org;
	  get_org:loop
      	fetch cu_org into n_orgid;
      			if v_org=1 then
      	          LEAVE get_org;
      			end if;
                  

            block2: BEGIN
            DECLARE v_fa INT default 0;
            DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_fa = 1;
      		  
            open cu_assid;
      			get_fa : loop
      	        fetch cu_assid into n_assid;
      			if v_fa=1 then
                   close cu_assid;   
      	        LEAVE get_fa;
      			end if;
      		      
				   update tb_as_assesment_detail  a set a.mn_assd_constru_type=
				   (select r.cpd_id from tb_comparam_det r,tb_comparam_mas s 
                   where r.CPM_ID=s.CPM_ID and r.cpd_value='VCL' and s.CPM_PREFIX='CSC' and cpd_status='A'
				   and orgid=n_orgid)
				   where a.mn_ass_id=n_assid and a.mn_assd_constru_type in
                   (select cpd_id from tb_comparam_det where cpm_id=73 and orgid=n_orgid and cpd_desc='Land');
      					COMMIT;  
                   
                end loop;
                END block2;
      	end loop;	  
          close cu_org;
   END;

END$$

DELIMITER ;

