-- Create table
create table TB_EIP_HOME_IMAGES
(
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
  module_type   NVARCHAR2(1)
);
-- Add comments to the columns 
comment on column TB_EIP_HOME_IMAGES.hm_img_id
  is 'Primary Key';
comment on column TB_EIP_HOME_IMAGES.hm_img_order
  is 'order in which image should be displayed ';
comment on column TB_EIP_HOME_IMAGES.image_name
  is 'Image file Name';
comment on column TB_EIP_HOME_IMAGES.image_path
  is 'Image file path';
comment on column TB_EIP_HOME_IMAGES.isdeleted
  is 'Record Deletion flag - value N non-deleted record and Y- deleted record';
comment on column TB_EIP_HOME_IMAGES.orgid
  is 'Organization Id.';
comment on column TB_EIP_HOME_IMAGES.user_id
  is 'User Id';
comment on column TB_EIP_HOME_IMAGES.lang_id
  is 'Language Id';
comment on column TB_EIP_HOME_IMAGES.created_date
  is 'Created Date';
comment on column TB_EIP_HOME_IMAGES.updated_by
  is 'Modified By';
comment on column TB_EIP_HOME_IMAGES.updated_date
  is 'Modification Date';
comment on column TB_EIP_HOME_IMAGES.lg_ip_mac
  is 'Client Machine''''s Login Name | IP Address | Physical Address';
comment on column TB_EIP_HOME_IMAGES.lg_ip_mac_upd
  is 'Updated Client Machine''''s Login Name | IP Address | Physical Address';
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_EIP_HOME_IMAGES
  add constraint PK_EIP_HM_IMG_ID primary key (HM_IMG_ID);
alter table TB_EIP_HOME_IMAGES
  add constraint FK_EIP_HM_ID foreign key (ORGID)
  references TB_ORGANISATION (ORGID);
