-- Create table
create table TB_PG_BANK_DETAIL_HIST
(
  H_bmbankid   NUMBER(12),
  bm_bankid    NUMBER(12),
  cpd_id       NUMBER(12),
  pb_bankurl   VARCHAR2(200),
  pb_tablename VARCHAR2(50),
  user_id      NUMBER(7),
  lang_id      NUMBER(7),
  lmoddate     DATE,
  orgid        NUMBER(4),
  H_STATUS     NVARCHAR2(1)
);
