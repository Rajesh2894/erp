DROP TABLE  TB_EIP_PROJECTINFO_HIST
-- Create table
create table TB_EIP_PROJECTINFO_HIST
(
  H_INFID      NUMBER(12) not null,  
  info_id       NUMBER(12) not null,
  ttl_desc_en   VARCHAR2(1000) not null,
  ttl_desc_reg  VARCHAR2(1000) not null,
  info_desc_en  VARCHAR2(1000) not null,
  info_desc_reg VARCHAR2(1000) not null,
  isdeleted     VARCHAR2(1) default 'N' not null,
  orgid         NUMBER(7) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(7) not null,
  created_date  DATE,
  updated_by    VARCHAR2(1000),
  updated_date  DATE,
  lg_ip_mac     VARCHAR2(1000),
  lg_ip_mac_upd VARCHAR2(1000),
  H_STATUS      NVARCHAR2(1)
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_EIP_PROJECTINFO_HIST
  add constraint PK_H_INFOID primary key (H_INFID);
