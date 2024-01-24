---------------------------------------------------------
-- Export file for user SERVICE1                       --
-- Created by kailash.agarwal on 3/17/2017, 5:57:06 PM --
---------------------------------------------------------

set define off
spool function.log

prompt
prompt Creating function FN_GETCPDDESC
prompt ===============================
prompt
CREATE OR REPLACE FUNCTION "FN_GETCPDDESC" (n_id number, v_data varchar2, n_orgid number) return varchar2 is
   v_value varchar2(1000);
begin
-- v_data = 'E' --> English  --> English Description from tb_comparam_det
-- v_data = 'R' --> Regional --> Marathi Description from tb_comparam_det
-- v_data = 'V' --> Value    --> Getting Cpd_Value   from tb_comparam_det
-- v_data = 'O' --> Value    --> Getting Cpd_Others  from tb_comparam_det

  select trim(to_char(decode(v_data,'E',cpd_desc,'R',nvl(cpd_desc_mar,cpd_desc),'V',cpd_value,'O', cpd_others,null)))  --- added 'O' by pratibha on 06-02-2010
        --- trim, to_char is added by ankur on 19/10/2010 because all 3 fields are nvarchar2 so some times it create problems.
     into v_value
     from tb_comparam_det
    where cpd_status = 'A'
      and orgid      = n_orgid
      and cpd_id     = n_id;

   return v_value;
exception
  when others then
    return null;
end;
/

prompt
prompt Creating function FN_JAVA_SQ_GENERATION
prompt =======================================
prompt
CREATE OR REPLACE FUNCTION "FN_JAVA_SQ_GENERATION"
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
 v_nxtnum        number(20);    -- to Return next Sequence No.
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

prompt
prompt Creating function FN_SQ_GENERATION
prompt ==================================
prompt
CREATE OR REPLACE FUNCTION "FN_SQ_GENERATION"
(v_mod     varchar2,             -- Module Name
 v_tbl     varchar2,             -- Table Name
 v_fld     varchar2,             -- Field Name
 n_org     number,               -- Orgid
 v_rst     varchar2,             -- Reset Type when first time created
 v_ctr_id  varchar2 default null -- For Counterwise Resetting and generating sequnce no. from 1 for each counter in receipt.
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
      IF v_mod <> 'TP'  --Added By--Prashant--03/03/2012--I-2706_I-2707_I-2708--
      AND ( v_mod <> 'WT'                           --Nayan--29-03-2012--Added
            OR ( v_mod = 'WT'                       --Nayan--29-03-2012--Added
                 AND upper(v_tbl) <> 'TB_WT_BILL_MAS'      --Nayan--29-03-2012--Added
                 AND upper(v_fld) <> 'BM_BILLNO'           --Nayan--29-03-2012--Added
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
-- v_sqname_new      := 'SQ_'||n_org||UPPER(substr(v_fld,1,12))||v_ctr_id||'_'; -- commented by DC / YV on 01-04-2011
          v_sqname_new      := 'SQ_'||n_org||UPPER(substr(v_fld,1,6))||v_dp_deptcode||'_';
      ELSE --Added By--Prashant--03/03/2012--I-2706_I-2707_I-2708--
         v_sqname_new      := 'SQ_'||n_org||UPPER(substr(v_fld,1,6)); --Added By--Prashant--03/03/2012--I-2706_I-2707_I-2708--
      END IF;
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

       return v_nxtnum;
 end;
/

prompt
prompt Creating function NOS_WORDS
prompt ===========================
prompt
CREATE OR REPLACE FUNCTION "NOS_WORDS" (input NUMBER) RETURN VARCHAR2 IS
    v_words    VARCHAR2(32767); --To return the whole number.
    n_len      NUMBER(3); --For length of the number.
    v_hdd      VARCHAR2(3000); --For storing hundreds into words.
    v_thsd     VARCHAR2(3000); --For storing thousands into words.
    v_lacs     VARCHAR2(3000); --For storing lacs into words.
    v_crores   VARCHAR2(3000); --For storing crores into words.
    v_million  VARCHAR2(3000); --For storing million into words.
    v_billion  VARCHAR2(3000); --For storing billion into words.
    v_trillion VARCHAR2(3000); --For storing trillion into words.
    n_place    NUMBER(2); --For storing the place of the
    --decimal point.
    n_input        NUMBER; --For storing the precision of the                 --number.
    v_paise        VARCHAR2(3000); --For storing the paise into words.
    n_roundedinput NUMBER; --For storing the rounded input.
  BEGIN
    n_roundedinput := ROUND(input, 2); --Rounding it off to two decimals.
    n_len          := LENGTH(input); --Store the length of the passed
    --number.
    n_input := input; --Store the passed number.
    n_place := INSTR(input, '.'); --Find the units place of decimal                  --point.

    --Store the precision into n_input and its length into n_len,provided
    --the number is in decimals.
    IF (n_place > 0) THEN
      n_input := TO_NUMBER(SUBSTR(input, 1, n_place - 1));
      n_len   := LENGTH(n_input);
    END IF;

    --Provided the number is in hundreds, or more then convert it into words in the specified way.
    IF (n_len >= 3) THEN
      --Provided that the last three numbers are zeros, convert the three figures into words.
      IF (SUBSTR(n_input, -3) != 0) THEN
        SELECT TO_CHAR(TO_DATE(SUBSTR(n_input, -3), 'j'), 'Jsp')
          INTO v_hdd
          FROM DUAL;
      END IF;

      --Provided that the length of the number is more than 3 and both the last two numbers are not zeros,then
      --convert those two into words. This makes up a thousand figure.
      IF (n_len > 3) THEN
        IF (SUBSTR(SUBSTR(n_input, 1, (n_len - LENGTH(SUBSTR(n_input, -3)))),
                   -2) != 0) THEN
          SELECT TO_CHAR(TO_DATE(SUBSTR(SUBSTR(n_input,
                                               1,
                                               (n_len -
                                               LENGTH(SUBSTR(n_input, -3)))),
                                        -2),
                                 'j'),
                         'Jsp') || ' Thousand '
            INTO v_thsd
            FROM DUAL;
        END IF;

        --Provided that instead of two numbers for e.g. 2143, i.e. only 2 is left,
        --then convert it into words.
        IF (n_len = 4) THEN
          IF (SUBSTR(SUBSTR(n_input,
                            1,
                            (n_len - LENGTH(SUBSTR(n_input, -3)))),
                     1,
                     2) != 0) THEN
            SELECT TO_CHAR(TO_DATE(SUBSTR(SUBSTR(n_input,
                                                 1,
                                                 (n_len -
                                                 LENGTH(SUBSTR(n_input, -3)))),
                                          1,
                                          2),
                                   'j'),
                           'Jsp') || ' Thousand '
              INTO v_thsd
              FROM DUAL;
          END IF;
        END IF;

        --Provided that the length of the number is more than 5 and both the last two numbers are not zeros,then
        --convert those two into words. This makes up a figure of lac.
        IF (n_len > 5) THEN
          IF (SUBSTR(SUBSTR(n_input,
                            1,
                            (n_len - LENGTH(SUBSTR(n_input, -5)))),
                     -2) != 0) THEN
            SELECT TO_CHAR(TO_DATE(SUBSTR(SUBSTR(n_input,
                                                 1,
                                                 (n_len -
                                                 LENGTH(SUBSTR(n_input, -5)))),
                                          -2),
                                   'j'),
                           'Jsp') || ' Lacs '
              INTO v_lacs
              FROM DUAL;
          END IF;

          --Provided that instead of two numbers for e.g. 132143, i.e. only  1 is left,
          --then convert it into words.
          IF (n_len = 6) THEN
            IF (SUBSTR(SUBSTR(n_input,
                              1,
                              (n_len - LENGTH(SUBSTR(n_input, -5)))),
                       1,
                       2) != 0) THEN
              SELECT TO_CHAR(TO_DATE(SUBSTR(SUBSTR(n_input,
                                                   1,
                                                   (n_len -
                                                   LENGTH(SUBSTR(n_input,
                                                                  -5)))),
                                            1,
                                            2),
                                     'j'),
                             'Jsp') || ' Lacs '
                INTO v_lacs
                FROM DUAL;
            END IF;
          END IF;

          --Provided that the length of the number is more than 7 and both the last two numbers are not zeros,then
          --convert those two into words. This makes up a figure of crore.
          IF (n_len > 7) THEN
            IF (SUBSTR(SUBSTR(n_input,
                              1,
                              (n_len - LENGTH(SUBSTR(n_input, -7)))),
                       -2) != 0) THEN
              SELECT TO_CHAR(TO_DATE(SUBSTR(SUBSTR(n_input,
                                                   1,
                                                   (n_len -
                                                   LENGTH(SUBSTR(n_input,
                                                                  -7)))),
                                            -2),
                                     'j'),
                             'Jsp') || ' Crores '
                INTO v_crores
                FROM DUAL;
            END IF;

            --Provided that instead of two numbers for e.g. 32132143, i.e. only  2 is left,
            --then convert it into words.
            IF (n_len = 8) THEN
              IF (SUBSTR(SUBSTR(n_input,
                                1,
                                (n_len - LENGTH(SUBSTR(n_input, -7)))),
                         1,
                         2) != 0) THEN
                SELECT TO_CHAR(TO_DATE(SUBSTR(SUBSTR(n_input,
                                                     1,
                                                     (n_len -
                                                     LENGTH(SUBSTR(n_input,
                                                                    -7)))),
                                              1,
                                              2),
                                       'j'),
                               'Jsp') || ' Crores '
                  INTO v_crores
                  FROM DUAL;
              END IF;
            END IF;

            --Provided that the length of the number is more than 9 and both the last two numbers are not zeros,then
            --convert those two into words. This makes up a figure of million.
            IF (n_len > 9) THEN
              IF (SUBSTR(SUBSTR(n_input,
                                1,
                                (n_len - LENGTH(SUBSTR(n_input, -9)))),
                         -2) != 0) THEN
                SELECT TO_CHAR(TO_DATE(SUBSTR(SUBSTR(n_input,
                                                     1,
                                                     (n_len -
                                                     LENGTH(SUBSTR(n_input,
                                                                    -9)))),
                                              -2),
                                       'j'),
                               'Jsp') || ' Million '
                  INTO v_million
                  FROM DUAL;
              END IF;

              --Provided that instead of two numbers for e.g. 2902132143, i.e. only  2 is left,
              --then convert it into words.
              IF (n_len = 10) THEN
                IF (SUBSTR(SUBSTR(n_input,
                                  1,
                                  (n_len - LENGTH(SUBSTR(n_input, -9)))),
                           1,
                           2) != 0) THEN
                  SELECT TO_CHAR(TO_DATE(SUBSTR(SUBSTR(n_input,
                                                       1,
                                                       (n_len -
                                                       LENGTH(SUBSTR(n_input,
                                                                      -9)))),
                                                1,
                                                2),
                                         'j'),
                                 'Jsp') || ' Million '
                    INTO v_million
                    FROM DUAL;
                END IF;
              END IF;

              --Provided that the length of the number is more than 11 and both the last two numbers are not zeros,then
              --convert those two into words. This makes up a figure of billion.
              IF (n_len > 11) THEN
                IF (SUBSTR(SUBSTR(n_input,
                                  1,
                                  (n_len - LENGTH(SUBSTR(n_input, -11)))),
                           -2) != 0) THEN
                  SELECT TO_CHAR(TO_DATE(SUBSTR(SUBSTR(n_input,
                                                       1,
                                                       (n_len -
                                                       LENGTH(SUBSTR(n_input,
                                                                      -11)))),
                                                -2),
                                         'j'),
                                 'Jsp') || ' Billion '
                    INTO v_billion
                    FROM DUAL;
                END IF;

                --Provided that instead of two numbers for e.g. 342902132143, i.e. only  3 is left,
                --then convert it into words.
                IF (n_len = 12) THEN
                  IF (SUBSTR(SUBSTR(n_input,
                                    1,
                                    (n_len - LENGTH(SUBSTR(n_input, -11)))),
                             1,
                             2) != 0) THEN
                    SELECT TO_CHAR(TO_DATE(SUBSTR(SUBSTR(n_input,
                                                         1,
                                                         (n_len -
                                                         LENGTH(SUBSTR(n_input,
                                                                        -11)))),
                                                  1,
                                                  2),
                                           'j'),
                                   'Jsp') || ' Billion '
                      INTO v_billion
                      FROM DUAL;
                  END IF;
                END IF;

                --Provided that the length of the number is more than 13 and both the last two numbers are not zeros,then
                --convert those two into words. This makes up a figure of trillion.
                IF (n_len > 13) THEN
                  IF (SUBSTR(SUBSTR(n_input,
                                    1,
                                    (n_len - LENGTH(SUBSTR(n_input, -13)))),
                             -2) != 0) THEN
                    SELECT TO_CHAR(TO_DATE(SUBSTR(SUBSTR(n_input,
                                                         1,
                                                         (n_len -
                                                         LENGTH(SUBSTR(n_input,
                                                                        -13)))),
                                                  -2),
                                           'j'),
                                   'Jsp') || ' Trillion '
                      INTO v_trillion
                      FROM DUAL;
                  END IF;

                  --Provided that instead of two numbers for e.g. 76342902132143, i.e. only  7 is left,
                  --then convert it into words.
                  IF (n_len = 14) THEN
                    IF (SUBSTR(SUBSTR(n_input,
                                      1,
                                      (n_len - LENGTH(SUBSTR(n_input, -13)))),
                               1,
                               2) != 0) THEN
                      SELECT TO_CHAR(TO_DATE(SUBSTR(SUBSTR(n_input,
                                                           1,
                                                           (n_len -
                                                           LENGTH(SUBSTR(n_input,
                                                                          -13)))),
                                                    1,
                                                    2),
                                             'j'),
                                     'Jsp') || ' Trillion '
                        INTO v_trillion
                        FROM DUAL;
                    END IF;
                  END IF;
                END IF;
              END IF;
            END IF;
          END IF;
        END IF;
      END IF;

      --Provided the figure is in trillions, make sure that the converted trillion is stored first.
      IF (v_trillion IS NOT NULL) THEN
        v_words := v_trillion || ' ';
      END IF;

      --Provided the figure is in billions, make sure that the converted billion is stored first or after previous one.
      IF (v_billion IS NOT NULL) THEN
        v_words := v_words || v_billion || ' ';
      END IF;

      --Provided the figure is in millions, make sure that the converted million is stored first or after previous one.
      IF (v_million IS NOT NULL) THEN
        v_words := v_words || v_million || ' ';
      END IF;

      --Provided the figure is in crores, make sure that the converted crores is stored first or after previous one.
      IF (v_crores IS NOT NULL) THEN
        v_words := v_words || v_crores || ' ';
      END IF;

      --Provided the figure is in lacs, make sure that the converted lacs is stored first or after previous one.
      IF (v_lacs IS NOT NULL) THEN
        v_words := v_words || v_lacs || ' ';
      END IF;

      --Provided the figure is in thousands, make sure that the converted thousands is stored first or after previous one.
      IF (v_thsd IS NOT NULL) THEN
        v_words := v_words || v_thsd || ' ';
      END IF;

      --Provided the figure is in hundreds, make sure that the converted hundreds is stored first or after previous one.
      IF (v_hdd IS NOT NULL) THEN
        v_words := v_words || v_hdd;
      END IF;
      --Provided that the number is less than hundred but the number is non-zero, convert the number into words
    ELSIF (n_len < 3 AND n_input != 0) THEN
      SELECT TO_CHAR(TO_DATE(n_input, 'j'), 'Jsp') INTO v_words FROM DUAL;
    END IF;

    --Provided that the input is zero,then hardcoded value "Zero" is passed.
    IF n_input = 0 THEN
      v_words := 'Zero';
    END IF;

    --For Paise, the place of decimal point should be present and the input number is not an whole number, round the figure to 2 decimal points and convert it into words and
    --add it to the already existing value in v_words,variable.
    --For paise
    IF (n_place > 0 AND input != 0) THEN
      SELECT TO_CHAR(TO_DATE(RPAD(SUBSTR(n_roundedinput,
                                         n_place + 1,
                                         LENGTH(n_roundedinput) - n_place),
                                  2,
                                  0),
                             'j'),
                     'Jsp') || ' Paise '
        INTO v_paise
        FROM DUAL;

      v_words := v_words || ' And ' || v_paise;
    END IF;

    --Add a string 'Only' to the value in the variable,v_words, which is to be passed out of the function.
    RETURN v_words || ' Only';
  END;
/


spool off
