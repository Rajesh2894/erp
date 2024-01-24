-- Create table
create table TB_PORTAL_SERVICE_MASTER
(
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
  psm_sec_apl_autho          VARCHAR2(50)
);
-- Add comments to the table 
comment on table TB_PORTAL_SERVICE_MASTER
  is 'RTS Service Appeal Master';
-- Add comments to the columns 
comment on column TB_PORTAL_SERVICE_MASTER.psm_id
  is 'Primary Key of table';
comment on column TB_PORTAL_SERVICE_MASTER.psm_service_id
  is 'Service Id';
comment on column TB_PORTAL_SERVICE_MASTER.psm_first_apl_autho
  is 'First appeal Authority';
comment on column TB_PORTAL_SERVICE_MASTER.psm_sec_apl_autho
  is 'Second appeal Authority';
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_PORTAL_SERVICE_MASTER
  add primary key (PSM_ID);
