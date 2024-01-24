DROP FUNCTION IF EXISTS FN_SQ_GENERATION;
CREATE FUNCTION service.`FN_SQ_GENERATION`(v_mod     varchar(10),
 v_tbl     varchar(100),
 v_fld     varchar(100),
 n_org     int(4),        
 v_rst     varchar(1),
 v_ctr_id  varchar(100)
) RETURNS int(11)
begin

/*
  Function     : fn_sq_generation:
  Purpose      : Generate Sequence with considering organisation id
  Supported DB : MYSQL
  
*/


 declare n_sqno          int;           -- to store next sq_number;
 declare d_nrst_dt       datetime;      -- to store next reset date
 declare d_stdt          datetime;      -- to store start sequence date
 declare v_sqname_new    varchar(200);  -- to create Sequence name
 declare v_sqname        varchar(100);  -- to create and store Sequence name
 declare v_nxtnum        int;           -- to Return next Sequence No.
 declare d_nrd           datetime;      -- to find next reset date
 declare v_rst_typ       varchar(1);    -- to store reset type when alredy created
 declare v_var           varchar(100);  -- Variable
 declare v_dp_deptcode   varchar(10);   /* Use -meta option tb_department.dp_deptcode%type */
 /* declare pragma autonomous_transaction;*/

   if (v_ctr_id is not null) then
  
      if v_mod <> 'TP'  
      and ( v_mod <> 'WT'                           
            or ( v_mod = 'WT'                       
                 and upper(v_tbl) <> 'TB_WT_BILL_MAS'     
                 and upper(v_fld) <> 'BM_BILLNO'           
               )
          ) then
                
           select A.DP_DEPTCODE
            into   v_dp_deptcode
            from   TB_DEPARTMENT A
            where  DP_DEPTID = v_ctr_id;
     
           set v_sqname_new  = CAST(CONCAT('SQ_',CAST(n_org AS CHAR),COALESCE(UPPER(substring(v_fld,1,6)), ''),COALESCE(v_dp_deptcode, ''),'_') AS CHAR);
      else
           set v_sqname_new  = CAST(CONCAT('SQ_',CAST(n_org AS CHAR),COALESCE(UPPER(substring(v_fld,1,6)), ''),'_') AS CHAR);     
      end if;
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
              -- Inserting Sequence record into table.
      insert into TB_SEQ_GENERATION
                 (sq_id,sq_mdl_name,sq_tbl_name,sq_fld_name,sq_orgid,sq_seq_name,sq_str_with,sq_max_num,
                  sq_rst_typ,sq_str_date,sq_nxt_rst_dt,sq_lst_rst_dt,sq_ctr_id)
          values (n_sqno,UPPER(v_mod),UPPER(v_tbl),UPPER(v_fld),n_org,v_sqname,1,999999999999,
                  COALESCE(v_rst_typ,'C'),CURDATE(),d_nrst_dt,CURDATE(),v_ctr_id);
    
      insert into _sequences(name,next,inc) values (v_sqname, 1, 1);
    
      
   end if;
     
    set v_nxtnum = NextVal(v_sqname);
    

    return v_nxtnum        ;
 
 end;
