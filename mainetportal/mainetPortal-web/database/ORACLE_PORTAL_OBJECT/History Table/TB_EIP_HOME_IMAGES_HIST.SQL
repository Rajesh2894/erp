-- Create table
create table TB_EIP_HOME_IMAGES_HIST
(
  H_IMGID       NUMBER(12) not null,
  hm_img_id     NUMBER(12) not null,
  hm_img_order  NUMBER(2),
  image_name    VARCHAR2(200),
  image_path    VARCHAR2(1000),
  isdeleted     VARCHAR2(1) default 'N' not null,
  orgid         NUMBER(4) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(7) not null,
  created_date  DATE not null,
  updated_by    NUMBER(7),
  updated_date  DATE,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  module_type   NVARCHAR2(1),
  H_STATUS      NVARCHAR2(1)
);

-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_EIP_HOME_IMAGES_HIST
  add constraint PK_H_IMGID primary key (H_IMGID)
