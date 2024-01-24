CREATE OR REPLACE FUNCTION FN_SQ_GENERATION(V_MOD    VARCHAR2, -- Module Name
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
