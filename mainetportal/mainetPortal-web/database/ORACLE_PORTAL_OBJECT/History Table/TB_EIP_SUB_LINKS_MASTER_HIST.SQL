-- Create table
create table TB_EIP_SUB_LINKS_MASTER_HIST
(
  H_SUBLINKID       NUMBER,
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
  IS_STATUS         NVARCHAR2(1)
);

-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_EIP_SUB_LINKS_MASTER_HIST
  add constraint PK_H_SUBLINKID primary key (H_SUBLINKID);
