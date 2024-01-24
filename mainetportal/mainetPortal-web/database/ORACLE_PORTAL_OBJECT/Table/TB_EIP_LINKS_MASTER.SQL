-- Create table
create table TB_EIP_LINKS_MASTER
(
  link_id         NUMBER(12) not null,
  link_path       VARCHAR2(250),
  link_image_name VARCHAR2(100),
  link_order      NUMBER(10,2) not null,
  link_title_en   VARCHAR2(500),
  link_title_reg  NVARCHAR2(1000),
  cpd_section     NUMBER(12),
  isdeleted       VARCHAR2(1) default 'N' not null,
  orgid           NUMBER(4) not null,
  user_id         NUMBER(7) not null,
  lang_id         NUMBER(7) not null,
  created_date    DATE not null,
  updated_by      NUMBER(7),
  updated_date    DATE,
  lg_ip_mac       VARCHAR2(100),
  lg_ip_mac_upd   VARCHAR2(100),
  is_link_modify  NVARCHAR2(1) default 'F'
);
-- Add comments to the columns 
comment on column TB_EIP_LINKS_MASTER.link_id
  is 'Link Id  which is primary key of table';
comment on column TB_EIP_LINKS_MASTER.link_path
  is 'Actual Link';
comment on column TB_EIP_LINKS_MASTER.link_image_name
  is 'Image for a link';
comment on column TB_EIP_LINKS_MASTER.link_order
  is 'Order of Link';
comment on column TB_EIP_LINKS_MASTER.link_title_en
  is 'Link Title In English';
comment on column TB_EIP_LINKS_MASTER.link_title_reg
  is 'Link Title In Regional language';
comment on column TB_EIP_LINKS_MASTER.cpd_section
  is 'Look up for Section to which the Link is mapped. prefix HQS';
comment on column TB_EIP_LINKS_MASTER.isdeleted
  is 'Record Deletion flag - value N non-deleted record and Y- deleted record';
comment on column TB_EIP_LINKS_MASTER.orgid
  is 'Organization Id';
comment on column TB_EIP_LINKS_MASTER.user_id
  is 'User Id';
comment on column TB_EIP_LINKS_MASTER.lang_id
  is 'Language Id';
comment on column TB_EIP_LINKS_MASTER.created_date
  is 'Created Date';
comment on column TB_EIP_LINKS_MASTER.updated_by
  is 'Modified By';
comment on column TB_EIP_LINKS_MASTER.updated_date
  is 'Modification Date';
comment on column TB_EIP_LINKS_MASTER.lg_ip_mac
  is 'Client Machine''''''''s Login Name | IP Address | Physical Address';
comment on column TB_EIP_LINKS_MASTER.lg_ip_mac_upd
  is 'Updated Client Machine''''''''s Login Name | IP Address | Physical Address';
comment on column TB_EIP_LINKS_MASTER.is_link_modify
  is 'FLAG';
-- Create/Recreate indexes 
create index IX_LINK_ID_EIP_ORGID on TB_EIP_LINKS_MASTER (ORGID);
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_EIP_LINKS_MASTER
  add constraint PK_EIP_LINK_MAS_ID primary key (LINK_ID);
alter table TB_EIP_LINKS_MASTER
  add constraint FKF50FCAC418DADB9 foreign key (USER_ID)
  references EMPLOYEE (EMPID);
alter table TB_EIP_LINKS_MASTER
  add constraint FK_EIP_LM_ORG_ID foreign key (ORGID)
  references TB_ORGANISATION (ORGID);
