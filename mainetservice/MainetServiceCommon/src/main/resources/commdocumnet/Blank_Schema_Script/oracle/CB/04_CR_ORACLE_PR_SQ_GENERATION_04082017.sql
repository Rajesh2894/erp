CREATE OR REPLACE PROCEDURE PR_SQ_GENERATION
(v_mod     varchar2,             -- Module Name
 v_tbl     varchar2,             -- Table Name
 v_fld     varchar2,             -- Field Name
 n_org     number,               -- Orgid
 v_rst     varchar2,             -- Reset Type when first time created
 v_ctr_id  varchar2 default null, -- For Counterwise Resetting and generating sequnce no. from 1 for each counter in receipt.
 v_nextnum OUT  number
) IS

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
 pragma autonomous_transaction;

begin
   -- To check whether Table and Corresponding field are exist or not in Database
   begin
      select table_name into v_var
      from   user_tab_columns
      where  table_name  = UPPER(v_tbl)
      and    column_name = UPPER(v_fld);
   exception
      when no_data_found then
           raise_application_error(-20101,'Please Enter Valid Table/Field Name'||v_tbl||' '||v_fld) ;
   end;

   --added by DC / YV on 010411
   IF (v_ctr_id is not null) THEN
          v_sqname_new      := 'SQ_'||n_org||UPPER(substr(v_fld,1,6))||v_ctr_id||'_';
   else
       --  v_sqname_new      := 'SQ_'||n_org||UPPER(substr(v_fld,1,12));  -- commented by DC / YV on 01-04-2011
       v_sqname_new      := 'SQ_'||n_org||UPPER(substr(v_fld,1,6));
   end if;

   -- To get seq_name,start_date,next_reset_date and reset_type.
   select  sq_seq_name,nvl(sq_str_date,'01-MAR-1900'),nvl(sq_nxt_rst_dt,'01-MAR-1900'),sq_rst_typ
   into    v_sqname,d_stdt,d_nrd,v_rst_typ
   from    tb_seq_generation
   --where   --sq_mdl_name = UPPER(v_mod)   and     -- Commented by Deepak A. on 25/08/08
   where   sq_tbl_name = UPPER(v_tbl)
   and     sq_fld_name = UPPER(v_fld)
   and     sq_orgid    = UPPER(n_org)
   and     nvl(sq_ctr_id,' ') = nvl(v_ctr_id,' ')
   and     (sq_nxt_rst_dt is null OR sq_nxt_rst_dt > sysdate)
   and     sq_str_date = (select max(sq_str_date)
                          from   tb_seq_generation
                          --where  sq_mdl_name = UPPER(v_mod) and
                          where  sq_tbl_name = UPPER(v_tbl) -- Commented by Deepak A. on 25/08/08
                          and    sq_fld_name = UPPER(v_fld)
                          and    sq_orgid    = UPPER(n_org)
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
      insert into tb_seq_generation
                 (sq_id,sq_mdl_name,sq_tbl_name,sq_fld_name,sq_orgid,sq_seq_name,sq_str_with,sq_max_num,
                  sq_rst_typ,sq_str_date,sq_nxt_rst_dt,sq_lst_rst_dt,sq_ctr_id)
          values (n_sqno,UPPER(v_mod),UPPER(v_tbl),UPPER(v_fld),n_org,v_sqname,1,999999999999,
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

 --  return v_nxtnum;
       v_nextnum:=v_nxtnum; 
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

       -- Inserting Sequence record into table.
       insert into tb_seq_generation
                  (sq_id,sq_mdl_name,sq_tbl_name,sq_fld_name,sq_orgid,sq_seq_name,sq_str_with,sq_max_num,
                   sq_rst_typ,sq_str_date,sq_nxt_rst_dt,sq_lst_rst_dt,sq_ctr_id)
           values (n_sqno,UPPER(v_mod),UPPER(v_tbl),UPPER(v_fld),n_org,v_sqname,1,999999999999,
                   nvl(v_rst,'C'),sysdate,d_nrst_dt,sysdate,v_ctr_id);
       commit;

       -- Creating New Sequnce
       str := 'create sequence '||v_sqname;
       str := str||' increment by 1 start with 1 ' ;
       str := str||'  maxvalue  999999999999 ' ;
       str := str||'  minvalue 1 ';
       str := str||'  cycle ';
       str := str||'  nocache ';

       execute immediate str ;

       -- Next Sequence No. Generation
       str := 'select '||v_sqname||'.nextval from dual' ;
       execute immediate str into v_nxtnum  ;
       
       v_nextnum:=v_nxtnum; 
       --return v_nxtnum;
 end;
