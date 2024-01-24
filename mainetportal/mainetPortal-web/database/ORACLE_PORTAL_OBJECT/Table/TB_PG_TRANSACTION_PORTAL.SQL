-- Create table
create table TB_PG_TRANSACTION_PORTAL
(
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
-- Add comments to the columns 
comment on column TB_PG_TRANSACTION_PORTAL.pt_transid
  is 'Online transaction id';
comment on column TB_PG_TRANSACTION_PORTAL.po_service_id
  is 'Online Service Id';
comment on column TB_PG_TRANSACTION_PORTAL.pt_payment_status
  is 'Default P; P - Pending C - Complete, F - Fail';
comment on column TB_PG_TRANSACTION_PORTAL.pt_amount
  is 'Amount to be paid';
comment on column TB_PG_TRANSACTION_PORTAL.bm_bankid
  is 'Bank id';
comment on column TB_PG_TRANSACTION_PORTAL.pt_bank_ref_no
  is 'Bank reference no';
comment on column TB_PG_TRANSACTION_PORTAL.pt_transdt
  is 'Date & time of the transaction';
comment on column TB_PG_TRANSACTION_PORTAL.ctz_citizen_id
  is 'Citizen id';
comment on column TB_PG_TRANSACTION_PORTAL.sm_service_id
  is 'Sm_Service_Id';
comment on column TB_PG_TRANSACTION_PORTAL.po_identno1
  is 'Po_IdentNo1';
comment on column TB_PG_TRANSACTION_PORTAL.po_identno2
  is 'Po_IdentNo2';
comment on column TB_PG_TRANSACTION_PORTAL.receipt_no
  is 'Receipt Number form Mainet';
comment on column TB_PG_TRANSACTION_PORTAL.paid_by
  is 'To capture the payee name';
comment on column TB_PG_TRANSACTION_PORTAL.pt_remark
  is 'To capture the Manual receipt genaration remark';
comment on column TB_PG_TRANSACTION_PORTAL.user_id
  is 'User id';
comment on column TB_PG_TRANSACTION_PORTAL.lang_id
  is 'Lang id';
comment on column TB_PG_TRANSACTION_PORTAL.orgid
  is 'Organisation id';
comment on column TB_PG_TRANSACTION_PORTAL.lmoddate
  is 'Lmoddate';
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_PG_TRANSACTION_PORTAL
  add constraint PK_TB_PG_TRANSACTION_PORTAL primary key (PT_TRANSID);
-- Create/Recreate check constraints 
alter table TB_PG_TRANSACTION_PORTAL
  add constraint NN_PGTRAN_AMOUNT
  check ("PT_AMOUNT" IS NOT NULL);
alter table TB_PG_TRANSACTION_PORTAL
  add constraint NN_PGTRAN_BMBANKID
  check ("BM_BANKID" IS NOT NULL);
alter table TB_PG_TRANSACTION_PORTAL
  add constraint NN_PGTRAN_CITIZENID
  check ("CTZ_CITIZEN_ID" IS NOT NULL);
alter table TB_PG_TRANSACTION_PORTAL
  add constraint NN_PGTRAN_IDENTNO1
  check ("PO_IDENTNO1" IS NOT NULL);
alter table TB_PG_TRANSACTION_PORTAL
  add constraint NN_PGTRAN_LANGID
  check ("LANG_ID" IS NOT NULL);
alter table TB_PG_TRANSACTION_PORTAL
  add constraint NN_PGTRAN_LMODDATE
  check ("LMODDATE" IS NOT NULL);
alter table TB_PG_TRANSACTION_PORTAL
  add constraint NN_PGTRAN_ORGID
  check ("ORGID" IS NOT NULL);
alter table TB_PG_TRANSACTION_PORTAL
  add constraint NN_PGTRAN_PAYMENTSTATUS
  check ("PT_PAYMENT_STATUS" IS NOT NULL);
alter table TB_PG_TRANSACTION_PORTAL
  add constraint NN_PGTRAN_POSERVICEID
  check ("PO_SERVICE_ID" IS NOT NULL);
alter table TB_PG_TRANSACTION_PORTAL
  add constraint NN_PGTRAN_TRANSDT
  check ("PT_TRANSDT" IS NOT NULL);
alter table TB_PG_TRANSACTION_PORTAL
  add constraint NN_PGTRAN_TRANSID
  check ("PT_TRANSID" IS NOT NULL);
alter table TB_PG_TRANSACTION_PORTAL
  add constraint NN_PGTRAN_USER_ID
  check ("USER_ID" IS NOT NULL);
