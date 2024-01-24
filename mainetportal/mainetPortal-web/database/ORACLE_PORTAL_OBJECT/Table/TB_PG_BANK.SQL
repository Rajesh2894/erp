-- Create table
create table TB_PG_BANK
(
  bm_bankid     NUMBER(12),
  pb_merchantid VARCHAR2(40),
  user_id       NUMBER(7),
  lang_id       NUMBER(7),
  lmoddate      DATE default (sysdate),
  orgid         NUMBER(4),
  bm_active     VARCHAR2(1) default 'A' not null
);
-- Add comments to the columns 
comment on column TB_PG_BANK.bm_bankid
  is 'existing in kDNET bank master';
comment on column TB_PG_BANK.pb_merchantid
  is 'marchant id given by bank to client i.e KDMC';
comment on column TB_PG_BANK.user_id
  is 'user id';
comment on column TB_PG_BANK.lang_id
  is 'Longin id';
comment on column TB_PG_BANK.lmoddate
  is 'Last Modification date & time';
comment on column TB_PG_BANK.orgid
  is 'ORGANIZATION ID';
comment on column TB_PG_BANK.bm_active
  is 'Active Flag';
-- Create/Recreate indexes 
create index IX_TB_PG_BANK_MERCHANTID on TB_PG_BANK (PB_MERCHANTID);
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_PG_BANK
  add constraint PK_TB_PG_BANK primary key (BM_BANKID);
-- Create/Recreate check constraints 
alter table TB_PG_BANK
  add constraint NN_PGBANK_BANKID
  check ("BM_BANKID" IS NOT NULL);
alter table TB_PG_BANK
  add constraint NN_PGBANK_LANGID
  check ("LANG_ID" IS NOT NULL);
alter table TB_PG_BANK
  add constraint NN_PGBANK_LMODDATE
  check ("LMODDATE" IS NOT NULL);
alter table TB_PG_BANK
  add constraint NN_PGBANK_MERCHANTID
  check ("PB_MERCHANTID" IS NOT NULL);
alter table TB_PG_BANK
  add constraint NN_PGBANK_ORGID
  check ("ORGID" IS NOT NULL);
alter table TB_PG_BANK
  add constraint NN_PGBANK_USERID
  check ("USER_ID" IS NOT NULL);
