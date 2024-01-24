-- Create table
create table TB_PORTAL_SERVICE_MASTER_HIST
(
  H_PSMID                    NUMBER(12) not null,
  psm_id                     NUMBER(12) not null,
  psm_service_id             NUMBER(12),
  psm_doc_verify_period      NUMBER(12),
  psm_sla_days               NUMBER(12),
  psm_first_appeal_duration  NUMBER(12),
  psm_second_appeal_duration NUMBER(12),
  psm_service_name           VARCHAR2(100),
  psm_service_name_reg       NVARCHAR2(2000),
  psm_short_name             NVARCHAR2(10),
  orgid                      NUMBER(12),
  user_id                    NUMBER(12),
  lang_id                    NUMBER(12),
  lmoddate                   DATE,
  updated_by                 NUMBER(12),
  updated_date               DATE,
  is_deleted                 NVARCHAR2(1) default 'N',
  psm_dp_deptid              NUMBER(12),
  psm_smfid                  NUMBER(12),
  psm_first_apl_autho        VARCHAR2(50),
  psm_sec_apl_autho          VARCHAR2(50),
  H_STATUS                   NVARCHAR2(1)
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_PORTAL_SERVICE_MASTER_HIST
  add primary key (H_PSMID);
