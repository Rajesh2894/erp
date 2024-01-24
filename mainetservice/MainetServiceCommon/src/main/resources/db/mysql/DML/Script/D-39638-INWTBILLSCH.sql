USE service45;
DROP procedure IF EXISTS INWTBILLSCH;

DELIMITER $$
USE service45$$
CREATE PROCEDURE INWTBILLSCH()
BEGIN
	declare n_orgid bigint(12);
	declare n_seqbillschmas bigint(12);
	declare v_billfreqnm varchar(50);
	declare n_com1 bigint(2);
	declare n_com2 bigint(2);
	declare n_billfreq bigint(12);
	declare n_calfreq bigint(12);
    declare n_fafrommon,n_fatomon,n_fafrommon1 bigint(2);
    
	declare v_org INT default 0;
	declare row,col INT DEFAULT 1;
    declare i INTEGER DEFAULT 0;
	
    declare cu_org cursor for select * from wbillfreq where orgid<>1 and orgid not in
	(select orgid from TB_WT_BILL_SCHEDULE where cns_yearid=32) order by freqname;
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_org = 1;
    
   BEGIN
                select month(date(fa_fromdate)),month(date(fa_todate)) into n_fafrommon,n_fatomon from tb_financialyear where FA_YEARID=32;
				
    open cu_org;
	  get_org:loop
      	fetch cu_org into n_orgid,n_billfreq,v_billfreqnm;
		   if v_org=1 then
      	          LEAVE get_org;
      	   end if;
		        set n_fafrommon:=0;
				set n_fatomon:=0;
				set n_fafrommon1:=0;
	     			
	                if  v_billfreqnm='Monthly' then
						select month(date(fa_fromdate)),month(date(fa_fromdate)) into n_fafrommon,n_fatomon from tb_financialyear where FA_YEARID=32;
						
                    elseif v_billfreqnm='Yearly'	then

						select month(date(fa_fromdate)),month(date(fa_todate)) into n_fafrommon,n_fatomon from tb_financialyear where FA_YEARID=32;
					elseif v_billfreqnm='Half Yearly' then
						select month(date(fa_fromdate)),(month(date(fa_fromdate))+6) into n_fafrommon,n_fatomon from tb_financialyear where FA_YEARID=32;
                    end if;					

				insert into TEST (t2_ORGID,T2_billfreq,T2_billfreqnm,T2_nfafrommon,T2_nfatomon,T2_VORG) values (n_orgid,n_billfreq,v_billfreqnm,n_fafrommon,n_fatomon,v_org);
					commit;

				   block2: BEGIN  
                     declare hasError INT default 0;				
				     DECLARE Exit HANDLER FOR NOT FOUND SET hasError = 1;
	
	                 if hasError=1 then
					   set n_com1:=-1;
					   set n_com2:=-1;
					end if;
					
						select sum(com1) com1 ,sum(com2) com2
						 into n_com1,n_com2
						 from
						(select (case when com_level= 1 then -1 else 0 end) Com1,
						(case when com_level=2 then -1 else 0 end) Com2,com_level,
						orgid from tb_comparent_mas a,tb_comparam_mas b
						where  a.cpm_id=b.CPM_ID and com_status='Y' and CPM_PREFIX='WWZ' 
						and orgid=n_orgid) x
						group by orgid;
					END block2;
					
				  
                    set n_seqbillschmas:=fn_java_sq_generation('WT','TB_WT_BILL_SCHEDULE','CNS_ID',NULL,NULL);
					
					 					
					INSERT INTO TB_WT_BILL_SCHEDULE (CNS_ID,CNS_CPDID,CNS_FROM_DATE,CNS_TO_DATE,ORGID,USER_ID,LMODDATE,
					CNS_YEARID,CNS_MN,COD_ID1_WWZ,COD_ID2_WWZ,COD_ID3_WWZ,COD_ID4_WWZ, 
                    COD_ID5_WWZ,DEPENDS_ON_TYPE) 
					VALUES (n_seqbillschmas,n_billfreq,n_fafrommon,n_fatomon,n_orgid,1,now(),32,'NMR',n_com1,n_com2,null,null,null,1);
					commit;		

				     if	v_billfreqnm='Monthly' then
					      set n_fafrommon1:=n_fafrommon;
					 
						loop_label:  LOOP


							if  n_fafrommon1 > 12 THEN 
								set n_fafrommon1=1;
								/*LEAVE  loop_label;*/
							end if;	
								
							  
							INSERT INTO tb_wt_bill_schedule_detail 
							(DET_ID,CNS_ID,CNS_FROM_DATE,CNS_TO_DATE,ORGID,USER_ID,LANG_ID,LMODDATE,STATUS) VALUES  (fn_java_sq_generation('WT','tb_wt_bill_schedule_detail','DET_ID',NULL,NULL),n_seqbillschmas,n_fafrommon1,n_fafrommon1,n_orgid,1,1,now(),'A');
            
							  if n_fafrommon1=3 then
								LEAVE  loop_label;
							  END  IF;

							SET  n_fafrommon1 = n_fafrommon1 + 1;
							commit;

		
						END LOOP;
					else	

							 
							INSERT INTO tb_wt_bill_schedule_detail (DET_ID,CNS_ID,CNS_FROM_DATE,CNS_TO_DATE,ORGID,USER_ID,LANG_ID,LMODDATE,STATUS) VALUES  (fn_java_sq_generation('WT','tb_wt_bill_schedule_detail','DET_ID',NULL,NULL),n_seqbillschmas,n_fafrommon,n_fatomon,n_orgid,1,1,now(),'A');
					commit;
							
					end if;		
            
							
      	end loop;	  
          close cu_org;
   END;

END$$

DELIMITER ;

	


