--liquibase formatted sql
--changeset Anil:V20200303164619__CR_tb_res_configmaster_03032020.sql
drop table if exists tb_res_configmaster;
--liquibase formatted sql
--changeset Anil:V20200303164619__CR_tb_res_configmaster_030320201.sql
CREATE TABLE tb_res_configmaster (
  res_id bigint(12) NOT NULL COMMENT 'Primary Key',
  page_id bigint(12) NOT NULL COMMENT 'page id',
  field_id bigint(12) NOT NULL COMMENT 'field id',
  field_type varchar(20) NOT NULL COMMENT 'field type',
  ORGID bigint(19) NOT NULL,
  CREATED_BY bigint(10) NOT NULL,
  CREATED_DATE datetime NOT NULL COMMENT 'Date on which data is going to create',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'Client Machine?s Login Name | IP Address | Physical Address',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'Updated Client Machine?s Login Name | IP Address | Physical Address',
  UPDATED_BY bigint(12) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  PRIMARY KEY (res_id)
);
--liquibase formatted sql
--changeset Anil:V20200303164619__CR_tb_res_configmaster_030320202.sql
drop table if exists tb_res_configdet;
--liquibase formatted sql
--changeset Anil:V20200303164619__CR_tb_res_configmaster_030320203.sql
CREATE TABLE tb_res_configdet (
  res_det_id bigint(12) NOT NULL COMMENT 'Primary Key',
  res_id bigint(12) NOT NULL COMMENT 'Foreign Key (tb_res_configmaster)',
  is_mandatory varchar(1) DEFAULT NULL COMMENT 'mandatory key',
  is_visible varchar(1) DEFAULT NULL COMMENT 'visible key',
  ORGID bigint(19) NOT NULL,
  CREATED_BY bigint(10) NOT NULL,
  CREATED_DATE datetime NOT NULL COMMENT 'Date on which data is going to create',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'Client Machine?s Login Name | IP Address | Physical Address',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'Updated Client Machine?s Login Name | IP Address | Physical Address',
  UPDATED_BY bigint(12) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  PRIMARY KEY (res_det_id),
  KEY FK_res_id (res_id),
  CONSTRAINT FK_res_id FOREIGN KEY (res_id) REFERENCES tb_res_configmaster (res_id) ON DELETE NO ACTION ON UPDATE NO ACTION
) ;

