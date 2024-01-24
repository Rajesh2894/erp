CREATE OR REPLACE PROCEDURE inprefix()
BEGIN
  DECLARE done INT DEFAULT FALSE;
  DECLARE b INT;
  DECLARE cur1 CURSOR FOR SELECT ORGID FROM TB_ORGANISATION WHERE ORG_STATUS='A';
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;


  OPEN cur1;
  read_loop: LOOP
    FETCH cur1 INTO b;
    IF done THEN
      LEAVE read_loop;
    END IF;
	
			Insert into TB_COMPARAM_DET
			(cpd_id,orgid, 
			cpd_desc,cpd_value,cpd_status, 
			cpm_id,user_id,lang_id,lmoddate,cpd_default, 
			cpd_desc_mar,cpd_others,lg_ip_mac)
			values
			(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),b,
			'Monthly','M','A',
			(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='BFR'),1,1,now(),'Y',
			'Monthly',NULL,NULL);
			commit;


			Insert into TB_COMPARAM_DET
			(cpd_id,orgid, 
			cpd_desc,cpd_value,cpd_status, 
			cpm_id,user_id,lang_id,lmoddate,cpd_default, 
			cpd_desc_mar,cpd_others,lg_ip_mac)
			values
			(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),b,
			'Yearly','Y','A',
			(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='BFR'),1,1,now(),NULL,
			'वार्षिक',NULL,NULL);
			commit;


			Insert into TB_COMPARAM_DET
			(cpd_id,orgid, 
			cpd_desc,cpd_value,cpd_status, 
			cpm_id,user_id,lang_id,lmoddate,cpd_default, 
			cpd_desc_mar,cpd_others,lg_ip_mac)
			values
			(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),b,
			'Half Yearly','HY','A',
			(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='BFR'),1,1,now(),NULL,
			'अर्धवार्षिक',NULL,NULL);
			commit;

			Insert into TB_COMPARAM_DET
			(cpd_id,orgid, 
			cpd_desc,cpd_value,cpd_status, 
			cpm_id,user_id,lang_id,lmoddate,cpd_default, 
			cpd_desc_mar,cpd_others,lg_ip_mac)
			values
			(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),b,
			'Daily','D','A',
			(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='BFR'),1,1,now(),NULL,
			'दैनिक',NULL,NULL);
			commit;

			Insert into TB_COMPARAM_DET
			(cpd_id,orgid, 
			cpd_desc,cpd_value,cpd_status, 
			cpm_id,user_id,lang_id,lmoddate,cpd_default, 
			cpd_desc_mar,cpd_others,lg_ip_mac)
			values
			(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),b,
			'Weekly','W','A',
			(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='BFR'),1,1,now(),NULL,
			'साप्ताहिक',NULL,NULL);
			commit;

			Insert into TB_COMPARAM_DET
			(cpd_id,orgid, 
			cpd_desc,cpd_value,cpd_status, 
			cpm_id,user_id,lang_id,lmoddate,cpd_default, 
			cpd_desc_mar,cpd_others,lg_ip_mac)
			values
			(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),b,
			'One Time','W','A',
			(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='BFR'),1,1,now(),NULL,
			'एक बार',NULL,NULL);
			commit;

			Insert into TB_COMPARAM_DET
			(cpd_id,orgid, 
			cpd_desc,cpd_value,cpd_status, 
			cpm_id,user_id,lang_id,lmoddate,cpd_default, 
			cpd_desc_mar,cpd_others,lg_ip_mac)
			values
			(fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),b,
			'Quarterly','O','A',
			(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='BFR'),1,1,now(),NULL,
			'त्रैमासिक',NULL,NULL);
			commit;
			
			Insert into TB_COMPARAM_MAS
			(CPM_ID,CPM_PREFIX,CPM_DESC,CPM_STATUS,
			 LANG_ID,LMODDATE,CPM_MODULE_NAME,CPM_CONFIG,
			 CPM_EDIT,LG_IP_MAC,CPM_REPLICATE_FLAG,CPM_TYPE,
			 CPM_EDIT_DESC,CPM_EDIT_VALUE,CPM_EDIT_OTH,LOAD_AT_STARTUP,
			 CPD_EDIT_DEFAULT,CPD_EDIT_STATUS,USER_ID) 
			 values (fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL),'BJO','Batch Job','A',
			 1,now(),'COM','Y','Y',NULL,'Y','N',NULL,NULL,NULL,'Y',NULL,NULL,1);
             commit;

			Insert into TB_COMPARAM_DET
			(cpd_id,orgid,cpd_desc,cpd_value,cpd_status, 
             cpm_id,user_id,lang_id,lmoddate,cpd_default,cpd_others, 
             cpd_desc_mar,cpd_others,lg_ip_mac)
	        values
            (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),b,
            'Monthly News Letter','1','A',(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='BJO'),1,1,now(),'Y','CFC',
            'Monthly News Letter',NULL,NULL);
           
		   commit;

END LOOP;

  CLOSE cur1;
END;
