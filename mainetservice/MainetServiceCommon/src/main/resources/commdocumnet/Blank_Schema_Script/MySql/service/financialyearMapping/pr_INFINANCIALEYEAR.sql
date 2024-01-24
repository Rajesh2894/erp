USE suda_service1;
DROP procedure IF EXISTS INFINANCIALEYEAR;

DELIMITER $$
USE suda_service1$$
CREATE PROCEDURE INFINANCIALEYEAR()
BEGIN
	declare n_orgid bigint(12);
    declare n_fayearid bigint(12);
    declare n_yearstatus bigint(12);
    declare v_fafromdate,v_fatodate date;
	declare n_cnt int default 0;
    
	declare v_org INT default 0;
    declare row,col INT DEFAULT 1;
    declare i INTEGER DEFAULT 0;
	
    declare cu_org cursor for select orgid from tb_organisation;
	
	declare cu_fayearid cursor for select fa_yearid,fa_fromdate,fa_todate 
    from tb_financialyear order by fa_fromdate asc;
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_org = 1;
    
   BEGIN
   
   SELECT CPD_ID into n_yearstatus
   FROM tb_comparam_det a,tb_comparam_mas b where cpd_value='OPN' 
      								  and CPM_PREFIX='YOC' and orgid=1;
   
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
      	        fetch cu_fayearid into n_fayearid,v_fafromdate,v_fatodate;
      			if v_fa=1 then
                   close cu_fayearid;   
      	        LEAVE get_fa;
      			end if;
      		      
                  set n_cnt=0;
      			  select count(1) into n_cnt from tb_fincialyearorg_map where FA_YEARID=n_fayearid and orgid=n_orgid;
                    
                   if n_cnt = 0 then 
      					  INSERT INTO TB_FINCIALYEARORG_MAP
      					  (MAP_ID,FA_YEARID,FA_FROMYEAR,FA_TOYEAR,ORGID,CREATED_BY,CREATED_DATE,FA_YEARSTATUS)
      					  VALUES
      					  ( fn_java_sq_generation('COM','TB_FINCIALYEARORG_MAP','MAP_ID',NULL,NULL),
      					   n_fayearid,
                           year(v_fafromdate),
                           year(v_fatodate),
      					   n_orgid,1,now(),n_yearstatus);
      						commit;
                    end if;          

                end loop;
                END block2;
      	end loop;	  
          close cu_org;
   END;

END$$

DELIMITER ;

