--liquibase formatted sql
--changeset Anil:V20200907171106__CR_tb_wms_milestone_07092020.sql
drop table if exists tb_wms_milestone;
--liquibase formatted sql
--changeset Anil:V20200907171106__CR_tb_wms_milestone_070920201.sql
CREATE TABLE tb_wms_milestone(
  MILE_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  PROJ_ID bigint(12) NOT NULL,
  WORK_ID bigint(12) NOT NULL,
  MILE_NM varchar(50) NOT NULL,
  MILE_PER decimal(12,2) NOT NULL,
  ORGID bigint(12) DEFAULT NULL,
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL,
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (MILE_ID)
);

