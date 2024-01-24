----------------------------------------------------------
-- Export file for user PORTAL1                         --
-- Created by kailash.agarwal on 3/30/2017, 12:18:20 PM --
----------------------------------------------------------

set define off
spool function_p.log

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

prompt
prompt Creating function FN_SQ_GENERATION
prompt ==================================
prompt
CREATE OR REPLACE FUNCTION "FN_SQ_GENERATION" (V_MOD    VARCHAR2, -- Module Name
                                            V_TBL    VARCHAR2, -- Table Name
                                            V_FLD    VARCHAR2, -- Field Name
                                            N_ORG    NUMBER, -- Orgid
                                            V_RST    VARCHAR2, -- Reset Type when first time created
                                            V_CTR_ID VARCHAR2 DEFAULT NULL -- For Counterwise Resetting and generating sequnce no. from 1 for each counter in receipt.
                                            ) RETURN NUMBER AS

  N_SQNO        NUMBER; -- to store next sq_number;
  D_NRST_DT     DATE; -- to store next reset date
  D_STDT        DATE; -- to store start sequence date
  STR           VARCHAR2(200); -- to store create sequence string
  V_SQNAME_NEW  VARCHAR2(100); -- to create Sequence name
  V_SQNAME      VARCHAR2(100); -- to create and store Sequence name
  V_NXTNUM      NUMBER(12); -- to Return next Sequence No.
  D_NRD         DATE; -- to find next reset date
  V_RST_TYP     VARCHAR2(1); -- to store reset type when alredy created
  V_VAR         VARCHAR2(100); -- Variable
  V_DP_DEPTCODE TB_DEPARTMENT.DP_DEPTCODE%TYPE;
  PRAGMA AUTONOMOUS_TRANSACTION;

BEGIN
  -- To check whether Table and Corresponding field are exist or not in Database
  BEGIN
    SELECT TABLE_NAME
      INTO V_VAR
      FROM USER_TAB_COLUMNS
     WHERE TABLE_NAME = UPPER(V_TBL)
       AND COLUMN_NAME = UPPER(V_FLD);
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
      RAISE_APPLICATION_ERROR(-20101,
                              'Please Enter Valid Table/Field Name');
  END;

  --added by DC / YV on 010411
  IF (V_CTR_ID IS NOT NULL) THEN
    IF V_MOD <> 'TP' --Added By--Prashant--03/03/2012--I-2706_I-2707_I-2708--
       AND (V_MOD <> 'WT' --Nayan--29-03-2012--Added
       OR (V_MOD = 'WT' --Nayan--29-03-2012--Added
       AND UPPER(V_TBL) <> 'TB_WT_BILL_MAS' --Nayan--29-03-2012--Added
       AND UPPER(V_FLD) <> 'BM_BILLNO' --Nayan--29-03-2012--Added
       )) THEN
      BEGIN
        SELECT A.DP_DEPTCODE
          INTO V_DP_DEPTCODE
          FROM TB_DEPARTMENT A
         WHERE DP_DEPTID = V_CTR_ID;
      EXCEPTION
        WHEN NO_DATA_FOUND THEN
          NULL;
      END;
      -- v_sqname_new      := 'SQ_'||n_org||UPPER(substr(v_fld,1,12))||v_ctr_id||'_'; -- commented by DC / YV on 01-04-2011
      V_SQNAME_NEW := 'SQ_' || N_ORG || UPPER(SUBSTR(V_FLD, 1, 6)) ||
                      V_DP_DEPTCODE || '_';
    ELSE
      --Added By--Prashant--03/03/2012--I-2706_I-2707_I-2708--
      V_SQNAME_NEW := 'SQ_' || N_ORG || UPPER(SUBSTR(V_FLD, 1, 6)); --Added By--Prashant--03/03/2012--I-2706_I-2707_I-2708--
    END IF;
  ELSE
    --  v_sqname_new      := 'SQ_'||n_org||UPPER(substr(v_fld,1,12));  -- commented by DC / YV on 01-04-2011
    V_SQNAME_NEW := 'SQ_' || N_ORG || UPPER(SUBSTR(V_FLD, 1, 6));
  END IF;

  -- To get seq_name,start_date,next_reset_date and reset_type.
  SELECT SQ_SEQ_NAME,
         NVL(SQ_STR_DATE, '01-MAR-1900'),
         NVL(SQ_NXT_RST_DT, '01-MAR-1900'),
         SQ_RST_TYP
    INTO V_SQNAME, D_STDT, D_NRD, V_RST_TYP
    FROM TB_SEQ_GENERATION
  --where   --sq_mdl_name = UPPER(v_mod)   and     -- Commented by Deepak A. on 25/08/08
   WHERE SQ_TBL_NAME = UPPER(V_TBL)
     AND SQ_FLD_NAME = UPPER(V_FLD)
     AND SQ_ORGID = UPPER(N_ORG)
     AND NVL(SQ_CTR_ID, ' ') = NVL(V_CTR_ID, ' ')
     AND (SQ_NXT_RST_DT IS NULL OR SQ_NXT_RST_DT > SYSDATE)
     AND SQ_STR_DATE =
         (SELECT MAX(SQ_STR_DATE)
            FROM TB_SEQ_GENERATION
          --where  sq_mdl_name = UPPER(v_mod) and
           WHERE SQ_TBL_NAME = UPPER(V_TBL) -- Commented by Deepak A. on 25/08/08
             AND SQ_FLD_NAME = UPPER(V_FLD)
             AND SQ_ORGID = UPPER(N_ORG)
             AND NVL(SQ_CTR_ID, ' ') = NVL(V_CTR_ID, ' '));

  -- If sequnce exist then check for Reset or Generate next seq. No.
  IF (D_NRD <> '01-MAR-1900' AND SYSDATE >= D_NRD) THEN

    SELECT SQ_SEQ_GENID.NEXTVAL INTO N_SQNO FROM DUAL;

    V_SQNAME := V_SQNAME_NEW || N_SQNO; --To set unique Sequence Name

    -- Setting Next Reset Date for Sequence
    IF (V_RST_TYP = 'D' OR V_RST_TYP = 'd') THEN
      -- For Daily Reset
      SELECT TO_DATE(SYSDATE + 1) "Next Day" INTO D_NRST_DT FROM DUAL;
    ELSIF (V_RST_TYP = 'M' OR V_RST_TYP = 'm') THEN
      -- For Monthly Reset
      SELECT TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, 1), 'YYYY'), 'YYYY') "Next Month"
        INTO D_NRST_DT
        FROM DUAL;
    ELSIF (V_RST_TYP = 'F' OR V_RST_TYP = 'f') THEN
      -- For Yearly(Financilal from 01 Apr) Reset
      /* select to_date('01-APR-'||(to_number(to_char(sysdate,'YYYY')) + 1),'DD/MM/YYYY') "NEXT YEAR"
      into d_nrst_dt from dual ;  */ --commented by snehal on 07-02-2012
      --- added by snehal on 07-02-2012
      IF TRUNC(SYSDATE) <=
         TO_DATE('31-MAR-' || (TO_NUMBER(TO_CHAR(SYSDATE, 'YYYY')))) THEN
        SELECT TO_DATE('01-APR-' || (TO_NUMBER(TO_CHAR(SYSDATE, 'YYYY'))),
                       'DD/MM/YYYY') "NEXT YEAR"
          INTO D_NRST_DT
          FROM DUAL;
      ELSE
        SELECT TO_DATE('01-APR-' ||
                       (TO_NUMBER(TO_CHAR(SYSDATE, 'YYYY')) + 1),
                       'DD/MM/YYYY') "NEXT YEAR"
          INTO D_NRST_DT
          FROM DUAL;
      END IF;
      ---- till here on 07-02-2012
    ELSIF (V_RST_TYP = 'Y' OR V_RST_TYP = 'y') THEN
      -- For Yearly(Normal from 01 Jan) Reset
      SELECT TO_DATE('01-JAN-' || (TO_NUMBER(TO_CHAR(SYSDATE, 'YYYY')) + 1),
                     'DD/MM/YYYY') "NEXT YEAR"
        INTO D_NRST_DT
        FROM DUAL;
    ELSE
      -- For continuing sequence
      D_NRST_DT := NULL;
    END IF;

    -- Inserting Sequence record into table.
    INSERT INTO TB_SEQ_GENERATION
      (SQ_ID,
       SQ_MDL_NAME,
       SQ_TBL_NAME,
       SQ_FLD_NAME,
       SQ_ORGID,
       SQ_SEQ_NAME,
       SQ_STR_WITH,
       SQ_MAX_NUM,
       SQ_RST_TYP,
       SQ_STR_DATE,
       SQ_NXT_RST_DT,
       SQ_LST_RST_DT,
       SQ_CTR_ID)
    VALUES
      (N_SQNO,
       UPPER(V_MOD),
       UPPER(V_TBL),
       UPPER(V_FLD),
       N_ORG,
       V_SQNAME,
       1,
       999999999999,
       NVL(V_RST_TYP, 'C'),
       D_STDT,
       D_NRST_DT,
       SYSDATE,
       V_CTR_ID);

    COMMIT;

    STR := 'create sequence ' || V_SQNAME;
    STR := STR || ' increment by 1 start with 1';
    STR := STR || '  maxvalue  999999999999 ';
    STR := STR || '  minvalue 1';
    STR := STR || '  cycle ';
    STR := STR || '  nocache ';
    EXECUTE IMMEDIATE STR;

  END IF;

  -- Next Sequence No. Generation
  STR := 'Select ' || V_SQNAME || '.nextval from dual';
  EXECUTE IMMEDIATE STR
    INTO V_NXTNUM;

  RETURN V_NXTNUM;

EXCEPTION
  WHEN NO_DATA_FOUND THEN
    SELECT SQ_SEQ_GENID.NEXTVAL INTO N_SQNO FROM DUAL;

    V_SQNAME := V_SQNAME_NEW || N_SQNO;

    -- Setting Next Reset Date for Sequence
    IF (V_RST = 'D' OR V_RST = 'd') THEN
      -- For Daily Reset
      SELECT TO_DATE(SYSDATE + 1) "Next Day" INTO D_NRST_DT FROM DUAL;
    ELSIF (V_RST = 'M' OR V_RST = 'm') THEN
      -- For Monthly Reset
      SELECT TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, 1), 'YYYY'), 'YYYY') "Next Month"
        INTO D_NRST_DT
        FROM DUAL;
    ELSIF (V_RST = 'F' OR V_RST = 'f') THEN
      -- For Yearly(Financilal from 01 Apr) Reset
      /* select to_date('01-APR-'||(to_number(to_char(sysdate,'YYYY')) + 1),'DD/MM/YYYY') "NEXT YEAR"
      into d_nrst_dt from dual ;  */ --commented by snehal on 07-02-2012
      --- added by snehal on 07-02-2012
      IF TRUNC(SYSDATE) <=
         TO_DATE('31-MAR-' || (TO_NUMBER(TO_CHAR(SYSDATE, 'YYYY')))) THEN
        SELECT TO_DATE('01-APR-' || (TO_NUMBER(TO_CHAR(SYSDATE, 'YYYY'))),
                       'DD/MM/YYYY') "NEXT YEAR"
          INTO D_NRST_DT
          FROM DUAL;
      ELSE
        SELECT TO_DATE('01-APR-' ||
                       (TO_NUMBER(TO_CHAR(SYSDATE, 'YYYY')) + 1),
                       'DD/MM/YYYY') "NEXT YEAR"
          INTO D_NRST_DT
          FROM DUAL;
      END IF;
      ---- till here on 07-02-2012
    ELSIF (V_RST = 'Y' OR V_RST = 'y') THEN
      -- For Yearly(Normal from 01 Jan) Reset
      SELECT TO_DATE('01-JAN-' || (TO_NUMBER(TO_CHAR(SYSDATE, 'YYYY')) + 1),
                     'DD/MM/YYYY') "NEXT YEAR"
        INTO D_NRST_DT
        FROM DUAL;
    ELSE
      -- For continuing sequence
      D_NRST_DT := NULL;
    END IF;

    -- Inserting Sequence record into table.
    INSERT INTO TB_SEQ_GENERATION
      (SQ_ID,
       SQ_MDL_NAME,
       SQ_TBL_NAME,
       SQ_FLD_NAME,
       SQ_ORGID,
       SQ_SEQ_NAME,
       SQ_STR_WITH,
       SQ_MAX_NUM,
       SQ_RST_TYP,
       SQ_STR_DATE,
       SQ_NXT_RST_DT,
       SQ_LST_RST_DT,
       SQ_CTR_ID)
    VALUES
      (N_SQNO,
       UPPER(V_MOD),
       UPPER(V_TBL),
       UPPER(V_FLD),
       N_ORG,
       V_SQNAME,
       1,
       999999999999,
       NVL(V_RST, 'C'),
       SYSDATE,
       D_NRST_DT,
       SYSDATE,
       V_CTR_ID);
    COMMIT;

    -- Creating New Sequnce
    STR := 'create sequence ' || V_SQNAME;
    STR := STR || ' increment by 1 start with 1 ';
    STR := STR || '  maxvalue  999999999999 ';
    STR := STR || '  minvalue 1 ';
    STR := STR || '  cycle ';
    STR := STR || '  nocache ';

    EXECUTE IMMEDIATE STR;

    -- Next Sequence No. Generation
    STR := 'select ' || V_SQNAME || '.nextval from dual';
    EXECUTE IMMEDIATE STR
      INTO V_NXTNUM;

    RETURN V_NXTNUM;
END;
/


spool off
