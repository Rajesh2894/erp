-- Create table
create table TB_EIP_SUB_LINKS_MASTER
(
  sub_link_par_id   NUMBER,
  link_id           NUMBER(10),
  has_sub_link      CHAR(1) default 'N' not null,
  sub_link_name_en  VARCHAR2(100) not null,
  sub_link_mas_id   NUMBER not null,
  is_custom         CHAR(1) default 'N',
  isdeleted         CHAR(1) default 'N' not null,
  sub_link_name_rg  NVARCHAR2(1000) not null,
  orgid             NUMBER(12) not null,
  user_id           NUMBER(12) not null,
  lang_id           NUMBER(4) not null,
  created_date      DATE not null,
  updated_by        NUMBER(12),
  updated_date      DATE,
  lg_ip_mac         VARCHAR2(100) not null,
  lg_ip_mac_upd     VARCHAR2(100),
  page_url          VARCHAR2(200),
  cpd_secion_type   NUMBER(12),
  cpd_img_link_type NUMBER(12),
  sub_link_order    NUMBER(10,2) not null,
  is_link_modify    NVARCHAR2(1) default 'F',
  cpd_secion_type1  NUMBER(12),
  cpd_secion_type2  NUMBER(12),
  cpd_secion_type3  NUMBER(12),
  cpd_secion_type4  NUMBER(12)
);
-- Add comments to the columns 
comment on column TB_EIP_SUB_LINKS_MASTER.link_id
  is 'Foreign key column of TB_EIP_LINK_MASTER';
comment on column TB_EIP_SUB_LINKS_MASTER.isdeleted
  is 'Record is deleted or not.';
comment on column TB_EIP_SUB_LINKS_MASTER.orgid
  is 'Organisation id';
comment on column TB_EIP_SUB_LINKS_MASTER.user_id
  is 'User Id who insert new sub link information';
comment on column TB_EIP_SUB_LINKS_MASTER.lang_id
  is 'Language Id';
comment on column TB_EIP_SUB_LINKS_MASTER.created_date
  is 'Record creation date';
comment on column TB_EIP_SUB_LINKS_MASTER.updated_by
  is 'User Id who update the record';
comment on column TB_EIP_SUB_LINKS_MASTER.updated_date
  is 'Date on which record get updated';
comment on column TB_EIP_SUB_LINKS_MASTER.lg_ip_mac
  is 'Client machine IP address from which record get inserted';
comment on column TB_EIP_SUB_LINKS_MASTER.lg_ip_mac_upd
  is 'Client machine IP address from which record get updated';
comment on column TB_EIP_SUB_LINKS_MASTER.cpd_secion_type
  is 'Foreign reference from EST prefix';
comment on column TB_EIP_SUB_LINKS_MASTER.cpd_img_link_type
  is 'Foreign reference from EIL prefix';
comment on column TB_EIP_SUB_LINKS_MASTER.is_link_modify
  is 'FLAG ';
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_EIP_SUB_LINKS_MASTER
  add constraint PK_ID primary key (SUB_LINK_MAS_ID);
alter table TB_EIP_SUB_LINKS_MASTER
  add constraint FK_SUB_LINK_PAR_ID foreign key (SUB_LINK_PAR_ID)
  references TB_EIP_SUB_LINKS_MASTER (SUB_LINK_MAS_ID) on delete cascade;
