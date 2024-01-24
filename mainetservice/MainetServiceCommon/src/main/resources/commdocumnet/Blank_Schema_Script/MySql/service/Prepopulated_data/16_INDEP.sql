DELIMITER $$
CREATE PROCEDURE `INDEP`()
BEGIN
	declare n_orgid bigint(12);
    declare n_deptid bigint(12);
	declare n_cnt int default 0;
    
	declare v_org INT default 0;
    declare row,col INT DEFAULT 1;
    declare i INTEGER DEFAULT 0;
	
    declare cu_org cursor for select orgid from tb_organisation;
	
	declare cu_fayearid cursor for select dp_deptid from tb_department;
    
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
      		  
            open cu_fayearid;
      			get_fa : loop
      	        fetch cu_fayearid into n_deptid;
      			if v_fa=1 then
                   close cu_fayearid;   
      	        LEAVE get_fa;
      			end if;
      		      
				insert into TB_DEPORG_MAP
				(map_id, 
				dp_deptid,map_status,orgid, 
				user_id,lang_id,lmoddate,lg_ip_mac)
				values
				(fn_java_sq_generation('AUT','TB_DEPORG_MAP','MAP_ID',NULL,NULL),
				n_deptid,'A',n_orgid,
				1,1,now(),'AMCTP | 192.168.0.150 | 44-87-FC-D2-66-D1');

				commit;
                

                end loop;
                END block2;
      	end loop;	  
          close cu_org;
   END;

END$$
DELIMITER ;
