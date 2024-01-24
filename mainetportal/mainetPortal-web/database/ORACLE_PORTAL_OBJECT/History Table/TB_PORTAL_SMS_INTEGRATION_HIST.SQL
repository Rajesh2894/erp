-- Create table
create table TB_PORTAL_SMS_INTEGRATION_HIST
(
  H_SEID          NUMBER(12) not null,
  se_id           NUMBER(12) not null,
  dp_deptid       NUMBER(12) not null,
  smfid           NUMBER(12) not null,
  alert_type      VARCHAR2(3),
  istransactional VARCHAR2(3),
  isdeleted       VARCHAR2(1) not null,
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
ALTER TABLE TB_PORTAL_SMS_INTEGRATION_HIST
ADD CONSTRAINTS PK_HSEID PRIMARY KEY (H_SEID);


