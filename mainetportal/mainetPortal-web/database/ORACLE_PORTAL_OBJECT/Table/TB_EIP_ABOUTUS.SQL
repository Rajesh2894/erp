-- Create table
create table TB_EIP_ABOUTUS
(
  aboutus_id       NUMBER(12) not null,
  description_en   VARCHAR2(3000) not null,
  description_reg  NVARCHAR2(2000),
  isdeleted        VARCHAR2(1) default 'N' not null,
  orgid            NUMBER(4) not null,
  user_id          NUMBER(7) not null,
  lang_id          NUMBER(7) not null,
  created_date     DATE not null,
  updated_by       NUMBER(7),
  updated_date     DATE,
  lg_ip_mac        VARCHAR2(100),
  lg_ip_mac_upd    VARCHAR2(100),
  description_en1  VARCHAR2(3000) not null,
  description_reg1 NVARCHAR2(2000)
);
-- Add comments to the columns 
comment on column TB_EIP_ABOUTUS.aboutus_id
  is 'Primary Key';
comment on column TB_EIP_ABOUTUS.description_en
  is 'Description in english';
comment on column TB_EIP_ABOUTUS.description_reg
  is 'Description in regional language';
comment on column TB_EIP_ABOUTUS.isdeleted
  is 'Record Deletion flag - value N non-deleted record and Y- deleted record';
comment on column TB_EIP_ABOUTUS.orgid
  is 'Organization Id';
comment on column TB_EIP_ABOUTUS.user_id
  is 'User Id';
comment on column TB_EIP_ABOUTUS.lang_id
  is 'Language Id';
comment on column TB_EIP_ABOUTUS.created_date
  is 'Created Date';
comment on column TB_EIP_ABOUTUS.updated_by
  is 'Modified By';
comment on column TB_EIP_ABOUTUS.updated_date
  is 'Modification Date';
comment on column TB_EIP_ABOUTUS.lg_ip_mac
  is 'Client Machine''''''''s Login Name | IP Address | Physical Address';
comment on column TB_EIP_ABOUTUS.lg_ip_mac_upd
  is 'Updated Client Machine''''''''s Login Name | IP Address | Physical Address';
comment on column TB_EIP_ABOUTUS.description_en1
  is 'Description in english (para 2)';
comment on column TB_EIP_ABOUTUS.description_reg1
  is 'Description in regional (para 2)';
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_EIP_ABOUTUS
  add constraint PK_EIP_ABOUTUS_ID primary key (ABOUTUS_ID);
alter table TB_EIP_ABOUTUS
  add constraint FK_EIP_ABOUTUS_ORG_ID foreign key (ORGID)
  references TB_ORGANISATION (ORGID);
