--liquibase formatted sql
--changeset Kanchan:V20210503115148__AL_tb_as_pro_detail_hist_03052021.sql
alter  table tb_as_pro_detail_hist
 add column PRO_CARPET_AREA decimal(12,2) DEFAULT NULL,
  add column PRO_AGE decimal(12,2) DEFAULT NULL,
  add column PRO_CONSTRUCT_PERMISSION_NUMBER varchar(100) DEFAULT NULL,
  add column PRO_PERMISSION_USE_NO varchar(100) DEFAULT NULL,
  add column PRO_ASSESSMENT_REMARK varchar(100) DEFAULT NULL,
  add column PRO_LEGAL varchar(100) DEFAULT NULL,
  add column PRO_OCCUPIER_NAME_REG varchar(100) DEFAULT NULL,
  add column PRO_ASSE_MOBILENO varchar(100) DEFAULT NULL,
  add column PRO_OCCUPIER_EMAIL varchar(100) DEFAULT NULL,
  add column PRO_ACTUAL_RENT decimal(12,2) DEFAULT NULL;
 
 


