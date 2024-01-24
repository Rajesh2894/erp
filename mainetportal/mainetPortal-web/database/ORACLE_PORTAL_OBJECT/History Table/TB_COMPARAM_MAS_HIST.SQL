-- Create table
create table TB_COMPARAM_MAS_HIST
(
  H_CPMID            NUMBER(12) not null,
  cpm_id             NUMBER(12) not null,
  cpm_prefix         NVARCHAR2(3) not null,
  cpm_desc           NVARCHAR2(200) not null,
  cpm_status         NVARCHAR2(1) not null,
  user_id            NUMBER(7) not null,
  lang_id            NUMBER(4) not null,
  lmoddate           DATE not null,
  cpm_limited_yn     NVARCHAR2(1),
  cpm_module_name    NVARCHAR2(10),
  updated_by         NUMBER(7),
  updated_date       DATE,
  cpm_config         CHAR(1),
  cpm_edit           VARCHAR2(50),
  lg_ip_mac          VARCHAR2(100),
  lg_ip_mac_upd      VARCHAR2(100),
  cpm_replicate_flag CHAR(1) default 'Y',
  cpm_type           CHAR(1),
  cpm_edit_desc      VARCHAR2(1),
  cpm_edit_value     VARCHAR2(1),
  cpm_edit_oth       VARCHAR2(1),
  load_at_startup    CHAR(1) default 'N',
  cpd_edit_default   VARCHAR2(1) default 'N',
  cpd_edit_status    VARCHAR2(1),
  H_STATUS           VARCHAR2(1)
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_COMPARAM_MAS_HIST
  add constraint PK_H_CPMID primary key (H_CPMID);
