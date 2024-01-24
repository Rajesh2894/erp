DELIMITER $$



DROP FUNCTION IF EXISTS fn_java_sq_generation $$

CREATE  FUNCTION `fn_java_sq_generation`(v_mod     VARCHAR(10),
 v_tbl     VARCHAR(100),
 v_fld     VARCHAR(100),   
 v_rst     VARCHAR(1),
 v_ctr_id  VARCHAR(100)
) RETURNS INT(11)
BEGIN
/*
  Function     : fn_java_sq_generation:
  Purpose      : Generate Sequence wihout considering organisation id
  Supported DB : MYSQL
  
*/
 DECLARE n_sqno          INT;           -- to store next sq_number;
 DECLARE d_nrst_dt       DATETIME;      -- to store next reset date
 DECLARE d_stdt          DATETIME;      -- to store start sequence date
 DECLARE v_sqname_new    VARCHAR(200);  -- to create Sequence name
 DECLARE v_sqname        VARCHAR(100);  -- to create and store Sequence name
 DECLARE v_nxtnum        INT;           -- to Return next Sequence No.
 DECLARE d_nrd           DATETIME;      -- to find next reset date
 DECLARE v_rst_typ       VARCHAR(1);    -- to store reset type when alredy created
 DECLARE v_var           VARCHAR(100);  -- Variable
 DECLARE v_dp_deptcode   VARCHAR(10);   /* Use -meta option tb_department.dp_deptcode%type */
 /* declare pragma autonomous_transaction;*/

   IF (v_ctr_id IS NOT NULL) THEN
  
      IF v_mod <> 'TP'  
      AND ( v_mod <> 'WT'                           
            OR ( v_mod = 'WT'                       
                 AND UPPER(v_tbl) <> 'TB_WT_BILL_MAS'     
                 AND UPPER(v_fld) <> 'BM_BILLNO'           
               )
          ) THEN
                
           SELECT A.DP_DEPTCODE
            INTO   v_dp_deptcode
            FROM   tb_department A
            WHERE  DP_DEPTID = v_ctr_id;
     
           SET v_sqname_new  = CAST(CONCAT('SQ_',COALESCE(UPPER(SUBSTRING(v_fld,1,6)), ''),COALESCE(v_dp_deptcode, ''),'_') AS CHAR);
      ELSE
           SET v_sqname_new  = CAST(CONCAT('SQ_',COALESCE(UPPER(SUBSTRING(v_fld,1,6)), ''),'_') AS CHAR);     
      END IF;
   ELSE 
           SET v_sqname_new  = CAST(CONCAT('SQ_',COALESCE(UPPER(SUBSTRING(v_fld,1,6)), ''),'_') AS CHAR);
   END IF;
    
    
   SELECT  sq_seq_name,COALESCE(sq_str_date,STR_TO_DATE('01-MAR-1900',"%d-%b-%Y")),COALESCE(sq_nxt_rst_dt,STR_TO_DATE('01-MAR-1900',"%d-%b-%Y")),sq_rst_typ
     INTO  v_sqname,d_stdt,d_nrd,v_rst_typ
     FROM  tb_java_seq_generation   
    WHERE  sq_tbl_name = UPPER(v_tbl)
      AND  sq_fld_name = UPPER(v_fld)
      AND  COALESCE(sq_ctr_id,' ') = COALESCE(v_ctr_id,' ')
      AND  (sq_nxt_rst_dt IS NULL OR sq_nxt_rst_dt > CURDATE())
      AND  sq_str_date = (SELECT MAX(sq_str_date)
                            FROM tb_java_seq_generation
                           WHERE sq_tbl_name = UPPER(v_tbl) 
                             AND sq_fld_name = UPPER(v_fld)
                             AND COALESCE(sq_ctr_id,' ') = COALESCE(v_ctr_id,' ')
                          );

  IF v_sqname IS NULL AND v_rst_typ IS NULL THEN
   SET v_rst_typ = v_rst;
  END IF;
    
  IF (d_nrd <> STR_TO_DATE('01-MAR-1900',"%d-%b-%Y") AND CURDATE() >= d_nrd)  OR v_sqname IS NULL THEN
      
      IF (v_rst_typ = 'D' OR v_rst_typ = 'd')   THEN  
         SELECT DATE_ADD(CURDATE(),INTERVAL 1 DAY) INTO d_nrst_dt;
      ELSEIF(v_rst_typ = 'M' OR v_rst_typ ='m') THEN   
         SELECT DATE_ADD(CURDATE(),INTERVAL 1 MONTH) INTO d_nrst_dt;
      ELSEIF(v_rst_typ = 'F' OR v_rst_typ ='f') THEN  
           
           IF  CURDATE()  <= STR_TO_DATE(CONCAT(CAST('31-MAR-' AS CHAR) , CAST(YEAR(CURDATE()) AS CHAR)),"%d-%b-%Y")
           THEN
               SELECT STR_TO_DATE(CONCAT(CAST('01-APR-' AS CHAR) , CAST(YEAR(CURDATE()) AS CHAR)),"%d-%b-%Y") INTO d_nrst_dt;
           ELSE 
              SELECT DATE_ADD(STR_TO_DATE(CONCAT(CAST('01-APR-' AS CHAR) , CAST(YEAR(CURDATE()) AS CHAR)),"%d-%b-%Y"),INTERVAL 1 YEAR) INTO d_nrst_dt;
           END IF;

      ELSEIF (v_rst_typ = 'Y' OR v_rst_typ ='y') THEN 
         SELECT DATE_ADD(STR_TO_DATE(CONCAT(CAST('01-JAN-' AS CHAR) , CAST(YEAR(CURDATE()) AS CHAR)),"%d-%b-%Y"),INTERVAL 1 YEAR) INTO d_nrst_dt;
      ELSE                                            
        SET d_nrst_dt = NULL;
      END IF;
      
      SET v_sqname = v_sqname_new;
      SET n_sqno = NextVal('SQ_SEQ_GENID');
      SET v_sqname = CONCAT(v_sqname,n_sqno);
              -- Inserting Sequence record into table.
      INSERT INTO tb_java_seq_generation
                 (sq_id,sq_mdl_name,sq_tbl_name,sq_fld_name,sq_seq_name,sq_str_with,sq_max_num,
                  sq_rst_typ,sq_str_date,sq_nxt_rst_dt,sq_lst_rst_dt,sq_ctr_id)
          VALUES (n_sqno,UPPER(v_mod),UPPER(v_tbl),UPPER(v_fld),v_sqname,1,999999999999,
                  COALESCE(v_rst_typ,'C'),CURDATE(),d_nrst_dt,CURDATE(),v_ctr_id);
    
      INSERT INTO _sequences(NAME,NEXT,inc) VALUES (v_sqname, 1, 1);
    
      
   END IF;
     
    SET v_nxtnum = NextVal(v_sqname);
    

    RETURN v_nxtnum        ;
 
 END$$

DELIMITER ;