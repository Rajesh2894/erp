--liquibase formatted sql
--changeset nilima:V20170804144355__CR_PR_SQ_GENERATION_04082017.sql
DROP PROCEDURE IF EXISTS PR_SQ_GENERATION;
CREATE PROCEDURE PR_SQ_GENERATION(v_mod  varchar(10),v_tbl  varchar(100),v_fld  varchar(100),n_org  int(4),v_rst  varchar(1),v_ctr_id  varchar(100),OUT v_nxtnum  int)
begin
 declare n_sqno          int;           
 declare d_nrst_dt       datetime;      
 declare d_stdt          datetime;      
 declare v_sqname_new    varchar(200);  
 declare v_sqname        varchar(100);  
 declare d_nrd           datetime;      
 declare v_rst_typ       varchar(1);    
 declare v_var           varchar(100);  
 declare v_dp_deptcode   varchar(10);   
 
 DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;



   if (v_ctr_id is not null) then
           set v_sqname_new  = CAST(CONCAT('SQ_',CAST(n_org AS CHAR),COALESCE(UPPER(substring(v_fld,1,6)), ''),COALESCE(v_ctr_id, ''),'_') AS CHAR);	  
   else 
           set v_sqname_new  = CAST(CONCAT('SQ_',CAST(n_org AS CHAR),COALESCE(UPPER(substring(v_fld,1,6)), ''),'_') AS CHAR);
   end if;
    
    
   select  sq_seq_name,COALESCE(sq_str_date,STR_TO_DATE('01-MAR-1900',"%d-%b-%Y")),COALESCE(sq_nxt_rst_dt,STR_TO_DATE('01-MAR-1900',"%d-%b-%Y")),sq_rst_typ
     into  v_sqname,d_stdt,d_nrd,v_rst_typ
     from  TB_SEQ_GENERATION   
    where  sq_tbl_name = UPPER(v_tbl)
      and  sq_fld_name = UPPER(v_fld)
      and  sq_orgid    = UPPER(n_org)
      and  COALESCE(sq_ctr_id,' ') = COALESCE(v_ctr_id,' ')
      and  (sq_nxt_rst_dt is null OR sq_nxt_rst_dt > CURDATE())
      and  sq_str_date = (select max(sq_str_date)
                            from TB_SEQ_GENERATION
                           where sq_tbl_name = UPPER(v_tbl) 
                             and sq_fld_name = UPPER(v_fld)
                             and sq_orgid    = UPPER(n_org)
                             and COALESCE(sq_ctr_id,' ') = COALESCE(v_ctr_id,' ')
                          );

  if v_sqname is null and v_rst_typ is null then
   set v_rst_typ = v_rst;
  end if;


START TRANSACTION ;
          
  if (d_nrd <> STR_TO_DATE('01-MAR-1900',"%d-%b-%Y") and CURDATE() >= d_nrd)  or v_sqname is null then
      
      if (v_rst_typ = 'D' or v_rst_typ = 'd')   then  
         select DATE_ADD(CURDATE(),INTERVAL 1 DAY) into d_nrst_dt;
      elseif(v_rst_typ = 'M' or v_rst_typ ='m') then   
         select DATE_ADD(CURDATE(),INTERVAL 1 MONTH) into d_nrst_dt;
      elseif(v_rst_typ = 'F' or v_rst_typ ='f') then  
           
           if  CURDATE()  <= STR_TO_DATE(CONCAT(CAST('31-MAR-' AS CHAR) , CAST(YEAR(CURDATE()) AS CHAR)),"%d-%b-%Y")
           then
               select STR_TO_DATE(CONCAT(CAST('01-APR-' AS CHAR) , CAST(YEAR(CURDATE()) AS CHAR)),"%d-%b-%Y") into d_nrst_dt;
           else 
              select DATE_ADD(STR_TO_DATE(CONCAT(CAST('01-APR-' AS CHAR) , CAST(YEAR(CURDATE()) AS CHAR)),"%d-%b-%Y"),INTERVAL 1 YEAR) into d_nrst_dt;
           end if;

      elseif (v_rst_typ = 'Y' or v_rst_typ ='y') then 
         select DATE_ADD(STR_TO_DATE(CONCAT(CAST('01-JAN-' AS CHAR) , CAST(YEAR(CURDATE()) AS CHAR)),"%d-%b-%Y"),INTERVAL 1 YEAR) into d_nrst_dt;
      else                                            
        set d_nrst_dt = NULL;
      end if;
      
      set v_sqname = v_sqname_new;
      set n_sqno = NextVal('SQ_SEQ_GENID');
      set v_sqname = concat(v_sqname,n_sqno);
      
      
      insert into TB_SEQ_GENERATION
                 (sq_id,sq_mdl_name,sq_tbl_name,sq_fld_name,sq_orgid,sq_seq_name,sq_str_with,sq_max_num,
                  sq_rst_typ,sq_str_date,sq_nxt_rst_dt,sq_lst_rst_dt,sq_ctr_id)
          values (n_sqno,UPPER(v_mod),UPPER(v_tbl),UPPER(v_fld),n_org,v_sqname,1,999999999999,
                  COALESCE(v_rst_typ,'C'),CURDATE(),d_nrst_dt,CURDATE(),v_ctr_id);
    
      insert into _sequences(name,next,inc) values (v_sqname, 1, 1);
    
      
   end if;
     
    set v_nxtnum = NextVal(v_sqname);
    
    commit;
 
 end;