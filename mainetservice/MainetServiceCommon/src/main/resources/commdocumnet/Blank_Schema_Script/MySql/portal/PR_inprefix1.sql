USE `bihar_portal`;
DROP procedure IF EXISTS `inprefix1`;

DELIMITER $$
USE `bihar_portal`$$
CREATE PROCEDURE `inprefix1`()
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
			(cpd_id,orgid,cpd_desc,cpd_value,cpd_status, 
             cpm_id,user_id,lang_id,lmoddate,cpd_default,cpd_others, 
             cpd_desc_mar,cpd_others,lg_ip_mac)
	        values
            (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),b,
            'SMS And Email','1','A',(select CPM_ID from TB_COMPARAM_MAS where CPM_PREFIX='BJO'),1,1,now(),'Y','CFC','एसएमएस और ईमेल',NULL,NULL);
           
		   commit;

END LOOP;

CLOSE cur1;  
END$$
DELIMITER ;

