-- Create table
create table TB_EIP_ANNOUNCEMENT_LANDING
(
  announce_id       NUMBER(12) not null,
  announce_desc_eng NVARCHAR2(2000) not null,
  announce_desc_reg NVARCHAR2(2000) not null,
  attachment        NVARCHAR2(300),
  isdeleted         VARCHAR2(1) default 'N' not null,
  orgid             NUMBER(4) not null,
  user_id           NUMBER(7) not null,
  lang_id           NUMBER(7) not null,
  created_date      DATE not null,
  updated_by        NUMBER(7),
  updated_date      DATE,
  lg_ip_mac         VARCHAR2(100),
  lg_ip_mac_upd     VARCHAR2(100)
);
-- Add comments to the columns 
comment on column TB_EIP_ANNOUNCEMENT_LANDING.announce_id
  is 'PRIMARY KEY';
comment on column TB_EIP_ANNOUNCEMENT_LANDING.announce_desc_eng
  is 'ANNOUNCEMENT IN ENGLISH';
comment on column TB_EIP_ANNOUNCEMENT_LANDING.announce_desc_reg
  is 'ANNOUNCEMENT IN REGIONAL';
comment on column TB_EIP_ANNOUNCEMENT_LANDING.attachment
  is 'ANNOUNCEMENT ATTACHMENT';
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_EIP_ANNOUNCEMENT_LANDING
  add constraint PK_ANNOUNCEMENT_LANDING primary key (ANNOUNCE_ID);
