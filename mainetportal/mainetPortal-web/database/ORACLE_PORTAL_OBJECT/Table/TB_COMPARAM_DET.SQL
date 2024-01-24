-- Create table
create table TB_COMPARAM_DET
(
  cpd_id        NUMBER(12) not null,
  orgid         NUMBER(4) not null,
  cpd_desc      NVARCHAR2(200) not null,
  cpd_value     NVARCHAR2(200),
  cpd_status    NVARCHAR2(1) not null,
  cpm_id        NUMBER(12) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(4) not null,
  lmoddate      DATE not null,
  cpd_default   CHAR(1),
  updated_by    NUMBER(7),
  updated_date  DATE,
  cpd_desc_mar  NVARCHAR2(270),
  cpd_others    VARCHAR2(60),
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100)
);
-- Add comments to the table 
comment on table TB_COMPARAM_DET
  is 'Common Parameter Detail';
-- Add comments to the columns 
comment on column TB_COMPARAM_DET.cpd_id
  is 'Common Parameter Identity';
comment on column TB_COMPARAM_DET.orgid
  is 'Organization Id';
comment on column TB_COMPARAM_DET.cpd_desc
  is 'Name of the parameter';
comment on column TB_COMPARAM_DET.cpd_value
  is 'Type of Parameter';
comment on column TB_COMPARAM_DET.cpd_status
  is 'Status';
comment on column TB_COMPARAM_DET.cpm_id
  is 'tb_comparam_mast table entry id';
comment on column TB_COMPARAM_DET.user_id
  is 'User Identity';
comment on column TB_COMPARAM_DET.lang_id
  is 'Language Identity';
comment on column TB_COMPARAM_DET.lmoddate
  is 'Last Modification Date';
comment on column TB_COMPARAM_DET.cpd_others
  is 'Extra field';
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_COMPARAM_DET
  add constraint PK_CPD_ID primary key (CPD_ID);
alter table TB_COMPARAM_DET
  add constraint FK_CPD_CPM_ID foreign key (CPM_ID)
  references TB_COMPARAM_MAS (CPM_ID);
