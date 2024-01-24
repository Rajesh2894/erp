-- Create table
create table TB_PG_BANKPARAMETER_PORTAL
(
  bankparid  NUMBER(12) not null,
  bm_bank_id NUMBER(12),
  parname    VARCHAR2(1000),
  parvalue   VARCHAR2(1000),
  passvalue  VARCHAR2(1)
);
