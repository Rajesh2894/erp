-- Create table
create table TB_EIP_PUBLIC_NOTICES_HIST
(
  H_PNID           NUMBER(12) not null,
  pn_id            NUMBER(12) not null,
  dept_id          NUMBER(12),
  notice_sub_en    VARCHAR2(250),
  notice_sub_reg   VARCHAR2(500),
  detail_en        VARCHAR2(500),
  detail_reg       VARCHAR2(1000),
  issue_date       DATE,
  validity_date    DATE,
  publish_flag     VARCHAR2(1),
  profile_img_path VARCHAR2(1000),
  isdeleted        VARCHAR2(1) default 'N' not null,
  orgid            NUMBER(4) not null,
  user_id          NUMBER(7) not null,
  lang_id          NUMBER(7) not null,
  created_date     DATE not null,
  updated_by       NUMBER(7),
  updated_date     DATE,
  lg_ip_mac        VARCHAR2(100),
  lg_ip_mac_upd    VARCHAR2(100),
  H_STATUS         VARCHAR2(1)
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_EIP_PUBLIC_NOTICES_HIST
  add primary key (H_PNID);
 
