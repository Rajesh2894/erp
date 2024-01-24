-- Create table
create table TB_EIP_LINKS_MASTER_HIST
(
  H_LINKID         NUMBER(12) not null,
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
  is_link_modify  NVARCHAR2(1) default 'F',
  H_STATUS        NVARCHAR2(1)
);

-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_EIP_LINKS_MASTER_HIST
  add constraint PK_H_LINKID primary key (H_LINKID);
 
