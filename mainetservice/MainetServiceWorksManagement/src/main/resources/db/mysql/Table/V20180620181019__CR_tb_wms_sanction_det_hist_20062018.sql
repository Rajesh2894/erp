--liquibase formatted sql
--changeset nilima:V20180620181019__CR_tb_wms_sanction_det_hist_20062018.sql
CREATE TABLE tb_wms_sanction_det_hist (
  WORK_SACTID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  WORK_SACTID bigint(12) DEFAULT NULL COMMENT 'Primary Key',
  WORK_ID bigint(12) DEFAULT NULL COMMENT 'Work Id',
  SM_SERVICE_ID bigint(12) DEFAULT NULL,
  WORK_SACNO varchar(200) DEFAULT NULL COMMENT 'Saction Number',
  WORK_SACDATE datetime DEFAULT NULL COMMENT 'Saction Date',
  WORK_SACBY varchar(50) DEFAULT NULL COMMENT 'Saction BY',
  WORK_SACDSG varchar(50) DEFAULT NULL COMMENT 'Saction BY Designation',
  H_STATUS char(1) DEFAULT NULL COMMENT 'Status I->Insert,update',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (WORK_SACTID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Work Saction Detail';