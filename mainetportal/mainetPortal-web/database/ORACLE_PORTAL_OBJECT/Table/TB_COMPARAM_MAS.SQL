-- Create table
create table TB_COMPARAM_MAS
(
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
  load_at_startup    CHAR(1) default 'N',
  cpm_edit_desc      VARCHAR2(1),
  cpm_edit_value     VARCHAR2(1),
  cpm_edit_oth       VARCHAR2(1),
  cpd_edit_default   VARCHAR2(1) default 'N',
  cpd_edit_status    VARCHAR2(1)
);
-- Add comments to the table 
comment on table TB_COMPARAM_MAS
  is 'Common parameter Master';
-- Add comments to the columns 
comment on column TB_COMPARAM_MAS.cpm_id
  is 'Common Parameter Identity';
comment on column TB_COMPARAM_MAS.cpm_prefix
  is 'Prefix of the type';
comment on column TB_COMPARAM_MAS.cpm_desc
  is 'Description of the parameter';
comment on column TB_COMPARAM_MAS.cpm_status
  is 'Status of the parameter';
comment on column TB_COMPARAM_MAS.user_id
  is 'User Identity';
comment on column TB_COMPARAM_MAS.lang_id
  is 'Language Identity';
comment on column TB_COMPARAM_MAS.lmoddate
  is 'Last Modification Date';
comment on column TB_COMPARAM_MAS.updated_by
  is 'Last Updated By';
comment on column TB_COMPARAM_MAS.updated_date
  is 'Last Updated Date';
comment on column TB_COMPARAM_MAS.cpm_config
  is 'Allow Add in Detail';
comment on column TB_COMPARAM_MAS.cpm_edit
  is 'Allow Edit in Detail';
comment on column TB_COMPARAM_MAS.load_at_startup
  is 'Should load at server startup?';
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_COMPARAM_MAS
  add constraint PK_CPM_ID primary key (CPM_ID);
