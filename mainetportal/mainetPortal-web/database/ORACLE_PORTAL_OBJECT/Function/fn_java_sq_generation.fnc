create or replace function fn_java_sq_generation
(v_mod     varchar2,             -- Module Name
 v_tbl     varchar2,             -- Table Name
 v_fld     varchar2,             -- Field Name
 v_rst     varchar2,             -- Reset Type when first time created
 v_ctr_id  varchar2 default null--, -- For Counterwise Resetting and generating sequnce no. from 1 for each counter in receipt.
-- v_error out varchar2
) return number as

 n_sqno          number;        -- to store next sq_number;
 d_nrst_dt       date ;         -- to store next reset date
 d_stdt          date ;         -- to store start sequence date
 str             varchar2(200); -- to store create sequence string
 v_sqname_new    varchar2(100); -- to create Sequence name
 v_sqname        varchar2(100); -- to create and store Sequence name
 v_nxtnum        number(12);    -- to Return next Sequence No.
 d_nrd           date;          -- to find next reset date
 v_rst_typ       varchar2(1);   -- to store reset type when alredy created
 v_var           varchar2(100); -- Variable
 v_dp_deptcode   tb_department.dp_deptcode%type;
 v_statement     varchar2(2000);
 n_initial_cnt   number :=0;
 pragma autonomous_transaction;
 v_table varchar2(100);
 v_field varchar2(100);
begin
  --v_error := null;
  v_table := UPPER(v_tbl);
  v_field := UPPER(v_fld);
  -- To check whether Table and Corresponding field are exist or not in Database
   begin
      select table_name into v_var
      from   user_tab_columns
      where  table_name  = UPPER(v_table)
      and    column_name = UPPER(v_field);
   exception
      when no_data_found then
           raise_application_error(-20101,'Please Enter Valid Table/Field Name') ;
   end;

   --added by DC / YV on 010411
   IF (v_ctr_id is not null) THEN
      IF v_mod <> 'TP'  --Added By--Prashant--03/03/2012--I-2706_I-2707_I-2708--
      AND ( v_mod <> 'WT'                           --Nayan--29-03-2012--Added
            OR ( v_mod = 'WT'                       --Nayan--29-03-2012--Added
                 AND upper(v_table) <> 'TB_WT_BILL_MAS'      --Nayan--29-03-2012--Added
                 AND upper(v_field) <> 'BM_BILLNO'           --Nayan--29-03-2012--Added
               )
          )
      THEN
         Begin
            select A.DP_DEPTCODE
            into   v_dp_deptcode
            from   tb_department A
            where  DP_DEPTID = v_ctr_id;
         exception
            when no_data_found then
                 null;
         End;
          v_sqname_new      := 'SQ_'||UPPER(substr(v_field,1,6))||v_dp_deptcode||'_';
      ELSE
         v_sqname_new      := 'SQ_'||UPPER(substr(v_field,1,6));
      END IF;
   else

       v_sqname_new      := 'SQ_'||UPPER(substr(v_field,1,6));
   end if;

   -- To get seq_name,start_date,next_reset_date and reset_type.
   select  sq_seq_name,nvl(sq_str_date,'01-MAR-1900'),nvl(sq_nxt_rst_dt,'01-MAR-1900'),sq_rst_typ
   into    v_sqname,d_stdt,d_nrd,v_rst_typ
   from    TB_JAVA_SEQ_GENERATION
   --where   --sq_mdl_name = UPPER(v_mod)   and     -- Commented by Deepak A. on 25/08/08
   where   sq_tbl_name = UPPER(v_table)
   and     sq_fld_name = UPPER(v_field)
   and     nvl(sq_ctr_id,' ') = nvl(v_ctr_id,' ')
   and     (sq_nxt_rst_dt is null OR sq_nxt_rst_dt > sysdate)
   and     sq_str_date = (select max(sq_str_date)
                          from   TB_JAVA_SEQ_GENERATION
                          --where  sq_mdl_name = UPPER(v_mod) and
                          where  sq_tbl_name = UPPER(v_table) -- Commented by Deepak A. on 25/08/08
                          and    sq_fld_name = UPPER(v_field)
                          and     nvl(sq_ctr_id,' ') = nvl(v_ctr_id,' ')
                          );

   -- If sequnce exist then check for Reset or Generate next seq. No.
   if (d_nrd <> '01-MAR-1900' and sysdate >= d_nrd) then

      select SQ_SEQ_GENID.nextval
      into   n_sqno
      from   dual;

      v_sqname := v_sqname_new||n_sqno; --To set unique Sequence Name

      -- Setting Next Reset Date for Sequence
      if (v_rst_typ = 'D' or v_rst_typ = 'd')   then   -- For Daily Reset
         select to_date(sysdate + 1) "Next Day"
         into   d_nrst_dt
         from   dual ;
      elsif(v_rst_typ = 'M' or v_rst_typ ='m') then   -- For Monthly Reset
         select to_date(to_char(add_months(sysdate,1),'YYYY'),'YYYY') "Next Month"
         into   d_nrst_dt
         from   dual ;
      elsif(v_rst_typ = 'F' or v_rst_typ ='f') then   -- For Yearly(Financilal from 01 Apr) Reset
           /* select to_date('01-APR-'||(to_number(to_char(sysdate,'YYYY')) + 1),'DD/MM/YYYY') "NEXT YEAR"
              into d_nrst_dt from dual ;  */--commented by snehal on 07-02-2012
           --- added by snehal on 07-02-2012
           if trunc(sysdate) <= to_date('31-MAR-' || (to_number(to_char(sysdate, 'YYYY'))))
           then
               select to_date('01-APR-'||(to_number(to_char(sysdate,'YYYY'))),'DD/MM/YYYY') "NEXT YEAR"
               into d_nrst_dt from dual ;
           else
               select to_date('01-APR-'||(to_number(to_char(sysdate,'YYYY')) + 1),'DD/MM/YYYY') "NEXT YEAR"
               into d_nrst_dt from dual ;
           end if;
           ---- till here on 07-02-2012
      elsif(v_rst_typ = 'Y' or v_rst_typ ='y') then   -- For Yearly(Normal from 01 Jan) Reset
        select to_date('01-JAN-'||(to_number(to_char(sysdate,'YYYY')) + 1),'DD/MM/YYYY') "NEXT YEAR"
        into   d_nrst_dt
        from   dual ;
      else                                            -- For continuing sequence
        d_nrst_dt := null;
      end if;

      -- Inserting Sequence record into table.
      insert into TB_JAVA_SEQ_GENERATION
                 (sq_id,sq_mdl_name,sq_tbl_name,sq_fld_name,sq_seq_name,sq_str_with,sq_max_num,
                  sq_rst_typ,sq_str_date,sq_nxt_rst_dt,sq_lst_rst_dt,sq_ctr_id)
          values (n_sqno,UPPER(v_mod),UPPER(v_table),UPPER(v_field),v_sqname,1,999999999999,
                  nvl(v_rst_typ,'C'),d_stdt,d_nrst_dt,sysdate,v_ctr_id);

      commit ;

      str := 'create sequence '||v_sqname;
      str := str||' increment by 1 start with 1' ;
      str := str||'  maxvalue  999999999999 ' ;
      str := str||'  minvalue 1' ;
      str := str||'  cycle ';
      str := str||'  nocache ';
      execute immediate str ;

   end if;

   -- Next Sequence No. Generation
   str := 'Select '||v_sqname||'.nextval from dual' ;
   execute immediate str into v_nxtnum  ;

   --v_error := null;
   return v_nxtnum;

exception
   when no_data_found then
       select sq_seq_genid.nextval
       into   n_sqno
       from   dual;

       v_sqname := v_sqname_new||n_sqno;

       -- Setting Next Reset Date for Sequence
       if (v_rst = 'D' or v_rst = 'd')   then   -- For Daily Reset
          select to_date(sysdate + 1) "Next Day"
          into d_nrst_dt
          from dual ;
       elsif (v_rst = 'M' or v_rst ='m') then   -- For Monthly Reset
          select to_date(to_char(add_months(sysdate,1),'YYYY'),'YYYY') "Next Month"
          into   d_nrst_dt
          from   dual ;
       elsif (v_rst = 'F' or v_rst ='f') then   -- For Yearly(Financilal from 01 Apr) Reset
            /* select to_date('01-APR-'||(to_number(to_char(sysdate,'YYYY')) + 1),'DD/MM/YYYY') "NEXT YEAR"
               into d_nrst_dt from dual ;  */--commented by snehal on 07-02-2012
            --- added by snehal on 07-02-2012
            if trunc(sysdate) <= to_date('31-MAR-' || (to_number(to_char(sysdate, 'YYYY'))))
            then
                select to_date('01-APR-'||(to_number(to_char(sysdate,'YYYY'))),'DD/MM/YYYY') "NEXT YEAR"
                into d_nrst_dt from dual ;
            else
                select to_date('01-APR-'||(to_number(to_char(sysdate,'YYYY')) + 1),'DD/MM/YYYY') "NEXT YEAR"
                into d_nrst_dt from dual ;
            end if;
            ---- till here on 07-02-2012
       elsif (v_rst = 'Y' or v_rst ='y') then   -- For Yearly(Normal from 01 Jan) Reset
          select to_date('01-JAN-'||(to_number(to_char(sysdate,'YYYY')) + 1),'DD/MM/YYYY') "NEXT YEAR"
          into   d_nrst_dt
          from   dual ;
       else                                    -- For continuing sequence
          d_nrst_dt := null;
       end if;

       v_statement := 'Select NVL(Max(' || v_field || '),0) + 1 from ' || v_table;
       execute immediate v_statement into n_initial_cnt;
       -- Inserting Sequence record into table.
       insert into TB_JAVA_SEQ_GENERATION
                  (sq_id,sq_mdl_name,sq_tbl_name,sq_fld_name,sq_seq_name,sq_str_with,sq_max_num,
                   sq_rst_typ,sq_str_date,sq_nxt_rst_dt,sq_lst_rst_dt,sq_ctr_id)
           values (n_sqno,UPPER(v_mod),UPPER(v_table),UPPER(v_field),v_sqname,n_initial_cnt,999999999999,
                   nvl(v_rst,'C'),sysdate,d_nrst_dt,sysdate,v_ctr_id);

       -- Creating New Sequnce
       str := 'create sequence '||v_sqname;
       str := str||' increment by 1 start with  ' || n_initial_cnt ;
       str := str||'  maxvalue  999999999999 ' ;
       str := str||'  minvalue  ' ||  n_initial_cnt;
       str := str||'  cycle ';
       str := str||'  nocache ';

       execute immediate str ;
       commit;

       -- Next Sequence No. Generation
       str := 'select '||v_sqname||'.nextval from dual' ;
       execute immediate str into v_nxtnum  ;
       --v_error := null;
       return v_nxtnum;
    when others then
    v_nxtnum :=-1;
    --v_error := V_error ||' ' || sqlerrm;
    return v_nxtnum;
 end;
/
