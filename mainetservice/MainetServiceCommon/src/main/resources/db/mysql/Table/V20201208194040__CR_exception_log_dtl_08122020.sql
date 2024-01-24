--liquibase formatted sql
--changeset Kanchan:V20201208194040__CR_exception_log_dtl_08122020.sql
CREATE TABLE exception_log_dtl (
  exception_log_id bigint(15) NOT NULL,
  exception_class varchar(100) DEFAULT NULL,
  URL varchar(100) DEFAULT NULL,
  exception_detail varchar(1000) DEFAULT NULL,
  file_name varchar(50) DEFAULT NULL,
  method_name varchar(50) DEFAULT NULL,
  ORGID bigint(12) NOT NULL COMMENT 'Org ID',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'User id who update the data',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'Date on which data is going to update',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  endpoint varchar(100) DEFAULT NULL,
  PRIMARY KEY (exception_log_id)
) ;
