-- Create table
create table TB_PORTAL_APPLICATION_MST_HIST
(
  H_PAMID                        NUMBER(12) not null,
  pam_id                         NUMBER(12) not null,
  orgid                          NUMBER(4),
  pam_application_id             NUMBER(16),
  pam_application_date           DATE,
  sm_service_id                  NUMBER(12),
  pam_cfc_citizenid              VARCHAR2(16),
  user_id                        NUMBER(7),
  lang_id                        NUMBER(7),
  created_date                   DATE,
  created_by                     NVARCHAR2(100),
  updated_by                     NVARCHAR2(100),
  updated_date                   DATE,
  pam_sla_date                   DATE,
  pam_doc_verification_date      DATE,
  pam_first_appeal_date          DATE,
  pam_second_appeal_date         DATE,
  pam_application_status         NVARCHAR2(100),
  pam_application_rejection_date DATE,
  pam_payment_status             NVARCHAR2(20),
  pam_checklist_app              VARCHAR2(1),
  pam_app_accepted_date          DATE,
  pam_aaple_track_id             VARCHAR2(50),
  pam_aaple_status               VARCHAR2(50),
  pam_digital_sign               VARCHAR2(1) default 'N',
  pam_sign_type                  VARCHAR2(500),
  H_STATUS                       VARCHAR2(1)
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_PORTAL_APPLICATION_MST_HIST
  add primary key (PAM_ID);
