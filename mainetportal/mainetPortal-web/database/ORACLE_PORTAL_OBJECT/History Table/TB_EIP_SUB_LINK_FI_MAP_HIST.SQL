-- Create table
create table TB_EIP_SUB_LINK_FIE_MAP_HIST
(
  H_SUBLINK_FIELD   NUMBER not null,
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
  H_STATUS          NVARCHAR2(1)
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_EIP_SUB_LINK_FIE_MAP_HIST
  add constraint PK_H_SUBLINK_FIELD primary key (H_SUBLINK_FIELD);
 
