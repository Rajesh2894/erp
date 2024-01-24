-- Create table
create table TB_EIP_ANNOUNCEMENT
(
  announce_id       NUMBER(12) not null,
  announce_desc_eng NVARCHAR2(2000) not null,
  announce_desc_reg NVARCHAR2(2000) not null,
  attach            NVARCHAR2(100),
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
comment on column TB_EIP_ANNOUNCEMENT.announce_id
  is 'EIP ANNOUNCEMENT Primary Key Id';
comment on column TB_EIP_ANNOUNCEMENT.announce_desc_eng
  is 'EIP DESC ENG';
comment on column TB_EIP_ANNOUNCEMENT.announce_desc_reg
  is 'EIP DESC REG';
comment on column TB_EIP_ANNOUNCEMENT.attach
  is 'ATTACHMENT';
comment on column TB_EIP_ANNOUNCEMENT.isdeleted
  is 'Record Deletion flag - value N non-deleted record and Y- deleted record';
comment on column TB_EIP_ANNOUNCEMENT.orgid
  is 'Organization Id.';
comment on column TB_EIP_ANNOUNCEMENT.user_id
  is 'User Id';
comment on column TB_EIP_ANNOUNCEMENT.lang_id
  is 'Language Id';
comment on column TB_EIP_ANNOUNCEMENT.created_date
  is 'Created Date';
comment on column TB_EIP_ANNOUNCEMENT.updated_by
  is 'Modified By';
comment on column TB_EIP_ANNOUNCEMENT.updated_date
  is 'Modification Date';
comment on column TB_EIP_ANNOUNCEMENT.lg_ip_mac
  is 'Client Machine''''s Login Name | IP Address | Physical Address';
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_EIP_ANNOUNCEMENT
  add constraint PK_ANNOUNCEMENT primary key (ANNOUNCE_ID);
alter table TB_EIP_ANNOUNCEMENT
  add constraint FK_EIP_ORGID4 foreign key (ORGID)
  references TB_ORGANISATION (ORGID);
