--liquibase formatted sql
--changeset Kanchan:V20210317104523__AL_tb_as_assesment_detail_17032021.sql
alter table  tb_as_assesment_detail add column
  MN_CARPET_AREA decimal(12,2) DEFAULT NULL,
  add MN_AGE decimal(12,2) DEFAULT NULL,
  add MN_CONSTRUCT_PERMISSION_NUMBER varchar(100) DEFAULT NULL,
  add MN_PERMISSION_USE_NO varchar(100) DEFAULT NULL,
  add MN_ASSESSMENT_REMARK varchar(100) DEFAULT NULL,
  add MN_LEGAL varchar(100) DEFAULT NULL,
  add MN_OCCUPIER_NAME_REG varchar(100) DEFAULT NULL,
  add MN_ASSE_MOBILENO varchar(100) DEFAULT NULL,
  add MN_OCCUPIER_EMAIL varchar(100) DEFAULT NULL,
  add MN_ACTUAL_RENT decimal(12,2) DEFAULT NULL;
