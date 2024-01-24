--liquibase formatted sql
--changeset Kanchan:V20210317105726__AL_tb_as_prop_det_17032021.sql
alter table  tb_as_prop_det add column
  PD_CARPET_AREA  decimal(15,2) DEFAULT NULL,
  add PM_AGE decimal(15,2) DEFAULT NULL,
  add PM_CONSTRUCT_PERMISSION_NUMBER  varchar(40) DEFAULT NULL,
  add PD_PERMISSION_USE_NO  varchar(500) DEFAULT NULL,
  add PD_ASSESSMENT_REMARK  varchar(2500) DEFAULT NULL,
  add PM_LEGAL varchar(50) DEFAULT NULL,
  add PD_OCCUPIER_NAME_REG  varchar(500) DEFAULT NULL,
  add PM_ASSE_MOBILENO varchar(100) DEFAULT NULL,
  add PM_OCCUPIER_EMAIL  varchar(100) DEFAULT NULL,
  add PD_ACTUAL_RENT  decimal(15,2) DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20210317105726__AL_tb_as_prop_det_170320211.sql
  alter table  tb_as_prop_factor add column
  PF_factor_date datetime DEFAULT NULL,
  add PF_factor_remark varchar(100) DEFAULT NULL;
