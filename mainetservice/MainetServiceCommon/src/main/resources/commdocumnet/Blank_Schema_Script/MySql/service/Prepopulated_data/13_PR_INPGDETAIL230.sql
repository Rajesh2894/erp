DELIMITER $$
CREATE PROCEDURE INPGDETAIL230()
BEGIN
	declare n_orgid bigint(12);
    declare n_pgid bigint(12);
    
	DECLARE v_finished INTEGER DEFAULT 0;
    DECLARE i INTEGER DEFAULT 0;
	
    declare cu_pg cursor for select orgid from tb_organisation where orgid not in
	(select orgid from tb_pg_bank);
    declare continue handler for not found set v_finished=1;
    DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;

   BEGIN
   
   delete from tb_pg_bank;
   commit;
   delete from TB_PG_BANK_PARAMETER_DETAIL;
   commit;
   delete from tb_java_seq_generation where SQ_TBL_NAME='tb_pg_bank';
   delete from tb_java_seq_generation where SQ_TBL_NAME='TB_PG_BANK_PARAMETER_DETAIL';
   commit;
   
   open cu_pg;
	get_pg : loop
	fetch cu_pg into n_orgid;

		if v_finished=1 then
	        Leave get_pg;
		end if;	

		      set n_pgid = fn_java_sq_generation('COM','TB_PG_BANK','PG_ID',NULL,NULL);
			  
			INSERT INTO tb_pg_bank(PG_ID,BANKID,MERCHANT_ID,PG_NAME,PG_URL,PG_STATUS,BA_ACCOUNTID,ORGID,
			CREATED_BY,CREATED_DATE,LANG_ID) 
			VALUES (n_pgid,
			96650,'178871','CCAvenue','https://test.ccavenue.com/transaction/transaction.do?command=initiateTransaction','A',
			null,n_orgid,1,now(),1);
			commit;

				INSERT INTO TB_PG_BANK_PARAMETER_DETAIL
				(PG_PRAM_DET_ID,PG_ID,PAR_NAME,PAR_VALUE,PAR_STATUS,ORGID,CREATED_BY,CREATED_DATE,LANG_ID) 
				VALUES (fn_java_sq_generation('COM','TB_PG_BANK_PARAMETER_DETAIL','PG_PRAM_DET_ID',NULL,NULL),
				n_pgid,
				'production','N','A',n_orgid,1,now(),1);

				INSERT INTO tb_pg_bank_parameter_detail(PG_PRAM_DET_ID,PG_ID,PAR_NAME,
				PAR_VALUE,PAR_STATUS,ORGID,CREATED_BY,CREATED_DATE,LANG_ID) 
				VALUES (fn_java_sq_generation('COM','TB_PG_BANK_PARAMETER_DETAIL','PG_PRAM_DET_ID',NULL,NULL),
				n_pgid,
				'requestType','T','A',n_orgid,1,now(),1);

				INSERT INTO tb_pg_bank_parameter_detail(PG_PRAM_DET_ID,PG_ID,PAR_NAME,PAR_VALUE,PAR_STATUS,
				ORGID,CREATED_BY,CREATED_DATE,LANG_ID,UPDATED_BY,UPDATED_DATE,LG_IP_MAC,LG_IP_MAC_UPD,COMM_N1,
				COMM_V1,COMM_D1,COMM_LO1) 
				VALUES (fn_java_sq_generation('COM','TB_PG_BANK_PARAMETER_DETAIL','PG_PRAM_DET_ID',NULL,NULL),
				n_pgid,
				'schemeCode','AVBC02FI26AM73CBMA','A',n_orgid,1,now(),1,null,null,null,null,null,null,null,null);

				INSERT INTO tb_pg_bank_parameter_detail(PG_PRAM_DET_ID,PG_ID,PAR_NAME,
				PAR_VALUE,PAR_STATUS,ORGID,CREATED_BY,CREATED_DATE,LANG_ID) 
				VALUES (fn_java_sq_generation('COM','TB_PG_BANK_PARAMETER_DETAIL','PG_PRAM_DET_ID',NULL,NULL),
				n_pgid,
				'key','5BC884F5BF0C7757BFEF05725126ACBF','A',n_orgid,1,now(),1);

				INSERT INTO tb_pg_bank_parameter_detail(PG_PRAM_DET_ID,PG_ID,PAR_NAME,
				PAR_VALUE,PAR_STATUS,ORGID,CREATED_BY,CREATED_DATE,LANG_ID) 
				VALUES (fn_java_sq_generation('COM','TB_PG_BANK_PARAMETER_DETAIL','PG_PRAM_DET_ID',NULL,NULL),
				n_pgid,
				'salt','5BC884F5BF0C7757BFEF05725126ACBF','A',n_orgid,1,now(),1);

				INSERT INTO tb_pg_bank_parameter_detail(PG_PRAM_DET_ID,PG_ID,PAR_NAME,PAR_VALUE,PAR_STATUS,
				ORGID,CREATED_BY,CREATED_DATE,LANG_ID) 
				VALUES (fn_java_sq_generation('COM','TB_PG_BANK_PARAMETER_DETAIL','PG_PRAM_DET_ID',NULL,NULL),
				n_pgid,
				'pg','CCAvenue','A',n_orgid,1,now(),1);

				INSERT INTO tb_pg_bank_parameter_detail(PG_PRAM_DET_ID,PG_ID,PAR_NAME,
				PAR_VALUE,PAR_STATUS,ORGID,CREATED_BY,CREATED_DATE,LANG_ID) 
				VALUES (fn_java_sq_generation('COM','TB_PG_BANK_PARAMETER_DETAIL','PG_PRAM_DET_ID',NULL,NULL),
				n_pgid,
				'drop_category','CC,DC,CASH,EMI','A',n_orgid,1,now(),1);
				
								INSERT INTO tb_pg_bank_parameter_detail (PG_PRAM_DET_ID,PG_ID,PAR_NAME,PAR_VALUE,PAR_STATUS,
				ORGID,CREATED_BY,CREATED_DATE,LANG_ID) 
				VALUES (fn_java_sq_generation('COM','TB_PG_BANK_PARAMETER_DETAIL','PG_PRAM_DET_ID',NULL,NULL),
				n_pgid,
				'surl','http://192.168.100.230:8080/PaymentController.html?hdfcCCARedirect','A',n_orgid,1,now(),1);

				INSERT INTO tb_pg_bank_parameter_detail (PG_PRAM_DET_ID,PG_ID,PAR_NAME,PAR_VALUE,PAR_STATUS,
				ORGID,CREATED_BY,CREATED_DATE,LANG_ID) 
				VALUES (fn_java_sq_generation('COM','TB_PG_BANK_PARAMETER_DETAIL','PG_PRAM_DET_ID',NULL,NULL),
				n_pgid,'curl','http://192.168.100.230:8080/PaymentController.html?hdfcCCAcancel','A',n_orgid,1,now(),1);

				INSERT INTO tb_pg_bank_parameter_detail (PG_PRAM_DET_ID,PG_ID,PAR_NAME,PAR_VALUE,PAR_STATUS,
				ORGID,CREATED_BY,CREATED_DATE,LANG_ID) 
				VALUES (fn_java_sq_generation('COM','TB_PG_BANK_PARAMETER_DETAIL','PG_PRAM_DET_ID',NULL,NULL),
				n_pgid,'furl','http://192.168.100.230:8080/PaymentController.html?hdfcCCARedirect','A',n_orgid,1,now(),1);

				commit;
    end loop get_pg;
    close cu_pg;
   END; 
END$$
DELIMITER ;
