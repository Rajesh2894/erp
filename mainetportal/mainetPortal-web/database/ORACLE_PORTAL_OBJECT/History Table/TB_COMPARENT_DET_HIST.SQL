-- Create table
create table TB_COMPARENT_DET_HIST
(
  h_codid       NUMBER(12) not null,
  cod_id        NUMBER(12) not null,
  com_id        NUMBER(12) not null,
  cod_desc      NVARCHAR2(200) not null,
  cod_value     NVARCHAR2(200) not null,
  parent_id     NUMBER(12),
  orgid         NUMBER(4) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(7) not null,
  lmoddate      DATE not null,
  updated_by    NUMBER(7),
  updated_date  DATE,
  cpd_default   CHAR(1) default 'N',
  cod_status    NVARCHAR2(1) default 'Y',
  cod_desc_mar  NVARCHAR2(400),
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  h_status      NVARCHAR2(1)
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_COMPARENT_DET_HIST
  add constraint PK_HCODID primary key (H_CODID);
