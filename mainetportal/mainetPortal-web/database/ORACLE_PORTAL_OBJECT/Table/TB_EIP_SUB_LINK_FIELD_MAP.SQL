-- Create table
create table TB_EIP_SUB_LINK_FIELD_MAP
(
  sub_link_field_id NUMBER not null,
  sub_link_mas_id   NUMBER,
  field_name_en     VARCHAR2(100) not null,
  field_type_id     NUMBER not null,
  field_seq         NUMBER not null,
  field_name_map    VARCHAR2(20) not null,
  is_used           CHAR(1) default 'Y' not null,
  is_mandatory      CHAR(1) default 'N' not null,
  field_name_rg     VARCHAR2(150) not null,
  isdeleted         CHAR(1) default 'N',
  orgid             NUMBER(12),
  user_id           NUMBER(12),
  lang_id           NUMBER(4),
  created_date      DATE,
  updated_by        NUMBER(12),
  updated_date      DATE,
  lg_ip_mac         VARCHAR2(100),
  lg_ip_mac_upd     VARCHAR2(100),
  sub_section_type  NUMBER(12)
);
-- Add comments to the columns 
comment on column TB_EIP_SUB_LINK_FIELD_MAP.field_name_rg
  is 'Regional label name for field';
comment on column TB_EIP_SUB_LINK_FIELD_MAP.isdeleted
  is 'Record is deleted flag status';
comment on column TB_EIP_SUB_LINK_FIELD_MAP.orgid
  is 'Organisation id';
comment on column TB_EIP_SUB_LINK_FIELD_MAP.user_id
  is 'User Id who insert new record';
comment on column TB_EIP_SUB_LINK_FIELD_MAP.lang_id
  is 'Language Id';
comment on column TB_EIP_SUB_LINK_FIELD_MAP.created_date
  is 'Record creation date';
comment on column TB_EIP_SUB_LINK_FIELD_MAP.updated_by
  is 'User Id who update the record';
comment on column TB_EIP_SUB_LINK_FIELD_MAP.updated_date
  is 'Date on which record get updated';
comment on column TB_EIP_SUB_LINK_FIELD_MAP.lg_ip_mac
  is 'Client machine IP address from which record get inserted';
comment on column TB_EIP_SUB_LINK_FIELD_MAP.lg_ip_mac_upd
  is 'Client machine IP address from which record get updated';
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_EIP_SUB_LINK_FIELD_MAP
  add constraint PK_SUB_LINK_FIELD_ID primary key (SUB_LINK_FIELD_ID);
alter table TB_EIP_SUB_LINK_FIELD_MAP
  add constraint FK1404AA3538B65205 foreign key (UPDATED_BY)
  references EMPLOYEE (EMPID);
alter table TB_EIP_SUB_LINK_FIELD_MAP
  add constraint FK1404AA35418DADB9 foreign key (USER_ID)
  references EMPLOYEE (EMPID);
alter table TB_EIP_SUB_LINK_FIELD_MAP
  add constraint FK1404AA356078ED5 foreign key (ORGID)
  references TB_ORGANISATION (ORGID);
alter table TB_EIP_SUB_LINK_FIELD_MAP
  add constraint FK_SUB_LINK_MAS_ID foreign key (SUB_LINK_MAS_ID)
  references TB_EIP_SUB_LINKS_MASTER (SUB_LINK_MAS_ID) on delete cascade;
