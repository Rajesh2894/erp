-- Create table
create table TB_COMPARAM_DET_HIST
(
  H_CPDID       NUMBER(12) not null,
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
  lg_ip_mac_upd VARCHAR2(100),
  H_STATUS      NVARCHAR2(1)
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_COMPARAM_DET_HIST
  add constraint PK_H_CPDID primary key (H_CPDID);
 
