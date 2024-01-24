--liquibase formatted sql
--changeset priya:V20180123180402__cr_tb_wms_error.sql
drop table if exists TB_WMS_ERROR;
CREATE TABLE TB_WMS_ERROR (
  ERR_LABEL1 varchar(500) DEFAULT NULL COMMENT 'Identifier Label1',
  ERR_DATA varchar(2000) DEFAULT NULL COMMENT 'Error data',
  ERR_DESCRIPTION varchar(2000) DEFAULT NULL COMMENT 'Description',
  FILE_NAME varchar(500) DEFAULT NULL COMMENT 'Uploaded file Name',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  ERR_LABEL2 varchar(500) DEFAULT NULL COMMENT 'Identifier Label2',
  ERR_LABEL3 varchar(500) DEFAULT NULL COMMENT 'Identifier Label3',
  ERR_FLAG  char(3) DEFAULT NULL COMMENT 'Identifier Label3'  
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Error Table';

