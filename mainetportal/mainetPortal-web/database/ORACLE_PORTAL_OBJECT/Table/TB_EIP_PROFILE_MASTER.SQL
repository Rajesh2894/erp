-- Create table
create table TB_EIP_PROFILE_MASTER
(
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
  lg_ip_mac_upd   VARCHAR2(100)
);
-- Add comments to the columns 
comment on column TB_EIP_PROFILE_MASTER.profile_id
  is 'Primary Key';
comment on column TB_EIP_PROFILE_MASTER.address
  is 'Address ';
comment on column TB_EIP_PROFILE_MASTER.image_name
  is 'Image file Name';
comment on column TB_EIP_PROFILE_MASTER.image_size
  is 'Image Size';
comment on column TB_EIP_PROFILE_MASTER.email_id
  is 'Email Id';
comment on column TB_EIP_PROFILE_MASTER.p_name_en
  is 'Person Name in English Language ';
comment on column TB_EIP_PROFILE_MASTER.p_name_reg
  is 'Person Name in Regional Language ';
comment on column TB_EIP_PROFILE_MASTER.prabhag_en
  is 'Prabhag Name in English Language ';
comment on column TB_EIP_PROFILE_MASTER.prabhag_reg
  is 'Prabhag Name in Regional  Language ';
comment on column TB_EIP_PROFILE_MASTER.cpd_section
  is 'Look up for Section to which the Link is mapped. prefix PMS';
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_EIP_PROFILE_MASTER
  add constraint PK_EIP_PROF_MAS_ID primary key (PROFILE_ID);
alter table TB_EIP_PROFILE_MASTER
  add constraint FK_EIP_PM_ORG_ID foreign key (ORGID)
  references TB_ORGANISATION (ORGID);
