-- Create table
create table TB_CUSTBANKS_MAS
(
  cm_bankid     NUMBER(12) not null,
  cm_bankname   NVARCHAR2(500) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(7) not null,
  lmoddate      DATE not null,
  updated_by    NUMBER(7),
  updated_date  DATE,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100)
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_CUSTBANKS_MAS
  add constraint PK_CUSTBANKS_MAS_BNKID primary key (CM_BANKID);
