-- Create table
create table TB_CUSTBANKS
(
  cb_bankid     NUMBER(12) not null,
  cb_bankcode   NUMBER(12) not null,
  cb_bankname   NVARCHAR2(500) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(7) not null,
  lmoddate      DATE not null,
  updated_by    NUMBER(7),
  updated_date  DATE,
  cb_branchname NVARCHAR2(500),
  cb_city       NVARCHAR2(100),
  cb_address    NVARCHAR2(250),
  cm_bankid     NUMBER(12),
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  pg_flag       CHAR(2) default 'N',
  orgid         NUMBER(4)
);
-- Add comments to the columns 
comment on column TB_CUSTBANKS.cb_bankid
  is 'Primary Key';
comment on column TB_CUSTBANKS.cb_bankcode
  is 'Bank Code';
comment on column TB_CUSTBANKS.cb_bankname
  is 'Name of the Bank';
comment on column TB_CUSTBANKS.user_id
  is 'User Identity';
comment on column TB_CUSTBANKS.lang_id
  is 'Language Identity';
comment on column TB_CUSTBANKS.lmoddate
  is 'Last Modification Date';
comment on column TB_CUSTBANKS.updated_by
  is 'Updated by';
comment on column TB_CUSTBANKS.updated_date
  is 'Updated Date';
comment on column TB_CUSTBANKS.cb_branchname
  is 'Branch name';
comment on column TB_CUSTBANKS.cb_city
  is 'City name';
comment on column TB_CUSTBANKS.cb_address
  is 'Address';
comment on column TB_CUSTBANKS.pg_flag
  is 'Payment Gateway Flag';
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_CUSTBANKS
  add constraint PK_CUSTBANKS_BNKID primary key (CB_BANKID);
alter table TB_CUSTBANKS
  add constraint FK_CUSTBANKS_CB_BANKID foreign key (CM_BANKID)
  references TB_CUSTBANKS_MAS (CM_BANKID);
