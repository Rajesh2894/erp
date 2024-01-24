-- Create table
create table TB_EIP_PROFILE_MASTER_HIST
(
  H_PROFILEID     NUMBER(12) not null,
  profile_id      NUMBER(12) not null,
  address         VARCHAR2(200),
  image_name      VARCHAR2(1000),
  image_size      VARCHAR2(150),
  designation_en  VARCHAR2(100),
  designation_reg NVARCHAR2(1000),
  email_id        VARCHAR2(150),
  link_title_en   VARCHAR2(150),
  link_title_reg  NVARCHAR2(1000),
  p_name_en       VARCHAR2(100),
  p_name_reg      NVARCHAR2(1000),
  prabhag_en      VARCHAR2(100),
  prabhag_reg     NVARCHAR2(1000),
  profile_en      VARCHAR2(4000),
  profile_reg     NVARCHAR2(2000),
  cpd_section     NUMBER(12),
  summary_en      VARCHAR2(2000),
  summary_reg     VARCHAR2(2000),
  isdeleted       VARCHAR2(1) default 'N' not null,
  orgid           NUMBER(4) not null,
  user_id         NUMBER(7) not null,
  lang_id         NUMBER(7) not null,
  created_date    DATE not null,
  updated_by      NUMBER(7),
  updated_date    DATE,
  lg_ip_mac       VARCHAR2(100),
  lg_ip_mac_upd   VARCHAR2(100),
  H_STATUS        NVARCHAR2(1)
);

-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_EIP_PROFILE_MASTER_HIST
  add constraint PK_H_PROFILEID primary key (H_PROFILEID);
