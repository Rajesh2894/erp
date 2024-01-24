-- Create table
create table TB_PG_TRANSACTION_PORTAL_HIST
(
  H_PTTRANSID       NUMBER(12),
  pt_transid        NUMBER(12),
  po_service_id     NUMBER(12),
  pt_payment_status CHAR(1),
  pt_amount         NUMBER(10,2),
  bm_bankid         NUMBER(12),
  pt_bank_ref_no    VARCHAR2(25),
  pt_transdt        DATE default SYSDATE,
  ctz_citizen_id    VARCHAR2(16),
  sm_service_id     NUMBER(12),
  po_identno1       VARCHAR2(20),
  po_identno2       VARCHAR2(20),
  receipt_no        NUMBER(12),
  paid_by           VARCHAR2(50),
  pt_remark         VARCHAR2(250),
  user_id           NUMBER(7),
  lang_id           NUMBER(7) default 1,
  orgid             NUMBER(4) default 1,
  lmoddate          DATE default SYSDATE
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_PG_TRANSACTION_PORTAL_HIST
  add constraint PK_H_PTTRANSID primary key (H_PTTRANSID);
  
