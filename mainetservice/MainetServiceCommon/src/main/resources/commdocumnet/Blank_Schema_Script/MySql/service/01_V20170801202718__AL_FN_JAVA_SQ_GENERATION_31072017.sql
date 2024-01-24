DROP FUNCTION IF EXISTS FN_JAVA_SQ_GENERATION;
CREATE FUNCTION FN_JAVA_SQ_GENERATION(v_mod     VARCHAR(10),
 v_tbl     VARCHAR(100),
 v_fld     VARCHAR(100),   
 v_rst     VARCHAR(1),
 v_ctr_id  VARCHAR(100)
) RETURNS int(11)
BEGIN

 DECLARE n_sqno          INT;           
 DECLARE d_nrst_dt       DATETIME;      
 DECLARE d_stdt          DATETIME;      
 DECLARE v_sqname_new    VARCHAR(200);  
 DECLARE v_sqname        VARCHAR(100);  
 DECLARE v_nxtnum        INT;           
 DECLARE d_nrd           DATETIME;      
 DECLARE v_rst_typ       VARCHAR(1);    
 DECLARE v_var           VARCHAR(100);  
 DECLARE v_dp_deptcode   VARCHAR(10);   
 

   IF (v_ctr_id IS NOT NULL) THEN
  
    /*  IF v_mod <> 'TP'  
      AND ( v_mod <> 'WT'                           
            OR ( v_mod = 'WT'                       
                 AND UPPER(v_tbl) <> 'TB_WT_BILL_MAS'     
                 AND UPPER(v_fld) <> 'BM_BILLNO'           
               )
          ) THEN
                
           SELECT A.DP_DEPTCODE
            INTO   v_dp_deptcode
            FROM   TB_DEPARTMENT A
            WHERE  DP_DEPTID = v_ctr_id;
     
           SET v_sqname_new  = CAST(CONCAT('SQ_',COALESCE(UPPER(SUBSTRING(v_fld,1,6)), ''),COALESCE(v_dp_deptcode, ''),'_') AS CHAR);
      ELSE
           SET v_sqname_new  = CAST(CONCAT('SQ_',COALESCE(UPPER(SUBSTRING(v_fld,1,6)), ''),'_') AS CHAR);     
      END IF;*/

	  SET v_sqname_new  = CAST(CONCAT('SQ_',COALESCE(UPPER(SUBSTRING(v_fld,1,6)), ''),COALESCE(v_ctr_id, ''),'_') AS CHAR);
   ELSE 
           SET v_sqname_new  = CAST(CONCAT('SQ_',COALESCE(UPPER(SUBSTRING(v_fld,1,6)), ''),'_') AS CHAR);
   END IF;
    
    
   SELECT  sq_seq_name,COALESCE(sq_str_date,STR_TO_DATE('01-MAR-1900',"%d-%b-%Y")),COALESCE(sq_nxt_rst_dt,STR_TO_DATE('01-MAR-1900',"%d-%b-%Y")),sq_rst_typ
     INTO  v_sqname,d_stdt,d_nrd,v_rst_typ
     FROM  TB_JAVA_SEQ_GENERATION   
    WHERE  sq_tbl_name = UPPER(v_tbl)
      AND  sq_fld_name = UPPER(v_fld)
      AND  COALESCE(sq_ctr_id,' ') = COALESCE(v_ctr_id,' ')
      AND  (sq_nxt_rst_dt IS NULL OR sq_nxt_rst_dt > CURDATE())
      AND  sq_str_date = (SELECT MAX(sq_str_date)
                            FROM TB_JAVA_SEQ_GENERATION
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
              
      INSERT INTO TB_JAVA_SEQ_GENERATION
                 (sq_id,sq_mdl_name,sq_tbl_name,sq_fld_name,sq_seq_name,sq_str_with,sq_max_num,
                  sq_rst_typ,sq_str_date,sq_nxt_rst_dt,sq_lst_rst_dt,sq_ctr_id)
          VALUES (n_sqno,UPPER(v_mod),UPPER(v_tbl),UPPER(v_fld),v_sqname,1,999999999999,
                  COALESCE(v_rst_typ,'C'),CURDATE(),d_nrst_dt,CURDATE(),v_ctr_id);
    
      INSERT INTO _sequences(NAME,NEXT,inc) VALUES (v_sqname, 1, 1);
    
      
   END IF;
     
    SET v_nxtnum = NextVal(v_sqname);
    

    RETURN v_nxtnum        ;
 
 END;
