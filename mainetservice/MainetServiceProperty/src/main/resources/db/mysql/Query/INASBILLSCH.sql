USE suda_service1;
DROP procedure IF EXISTS INASBILLSCH;

DELIMITER $$
USE suda_service1$$
CREATE PROCEDURE INASBILLSCH()
BEGIN
	declare n_orgid bigint(12);
	declare n_seqbillschmas bigint(12);
	declare n_billfreq bigint(12);
	declare n_calfreq bigint(12);
    declare v_fafromdate,v_fatodate date;
    
	declare v_org INT default 0;
    declare row,col INT DEFAULT 1;
    declare i INTEGER DEFAULT 0;
	
    declare cu_org cursor for select orgid from tb_organisation where orgid not in (1) and org_status='A' and orgid not in 
	(select orgid from tb_as_bill_schedule_mast where fa_yearid=32)
	order by orgid;
	
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_org = 1;
    
   BEGIN
                select date(fa_fromdate),date(fa_todate) into v_fafromdate,v_fatodate
				        from tb_financialyear where FA_YEARID=32;
    open cu_org;
	  get_org:loop
      	fetch cu_org into n_orgid;
      			if v_org=1 then
      	          LEAVE get_org;
      			end if;
				
				block2: BEGIN  
                     declare hasError INT default 0;				
				     DECLARE Exit HANDLER FOR NOT FOUND SET hasError = 1;
	
	                 if hasError=1 then
					   set n_billfreq:=0;
					   set n_calfreq:=0;
					end if;
					
				
						select cpd_id into n_billfreq from tb_comparam_det where cpd_value=12 and cpm_id=124 and orgid=n_orgid;
						
						select cal_frm_dt into n_calfreq from tb_as_bill_schedule_det where sch_detid in
						(select max(sch_detid) from tb_as_bill_schedule_det where status='A' and orgid=n_orgid);
						
					END block2;	  
                   
                    set n_seqbillschmas:=fn_java_sq_generation('AS','TB_AS_BILL_SCHEDULE_MAST','AS_BILL_SCHEID',NULL,NULL);
					
					 INSERT INTO nilima (orgid,n_billfreq,n_calfreq,n_seqbillschmas)
					 values (n_orgid,n_billfreq,n_calfreq,n_seqbillschmas);	
    				

					
					INSERT INTO tb_as_bill_schedule_mast (as_bill_scheid,fa_yearid,as_bill_frequency,orgid,created_by,created_date,lg_ip_mac,as_bill_status) 
					VALUES (n_seqbillschmas,32,n_billfreq,n_orgid,1,now(),'10.40.80.2/10.30.80.2/10.20.80.2/10.80.80.4/','A');
					                     
					INSERT INTO tb_as_bill_schedule_det (SCH_detid,as_bill_scheid,BILL_FROM_DATE,BILL_TO_DATE,ORGID,CREATED_BY,CREATED_Date,STATUS,Cal_frm_dt,No_of_Days) 
					VALUES (fn_java_sq_generation('AS','TB_AS_BILL_SCHEDULE_DET','SCH_DETID',NULL,NULL),
					n_seqbillschmas,v_fafromdate,v_fatodate,n_orgid,1,now(),'A',n_calfreq,364);
                    
					commit;

           
      	end loop;	  
          close cu_org;
   END;

END$$

DELIMITER ;

	


