--liquibase formatted sql
--changeset nilima:V20180721162932__CR_TB_NOTICE_MAS_21072018.sq
CREATE TABLE TB_NOTICE_MAS (
  NOT_ID bigint(12) NOT NULL COMMENT 'Primary Key	',
  DP_DEPTID bigint(12) NOT NULL COMMENT 'department Id',
  SM_SERVICE_ID bigint(12) DEFAULT NULL COMMENT 'Service id',
  APM_APPLICATION_ID bigint(12) DEFAULT NULL COMMENT 'Application Id',
  NOT_REF_NO varchar(20) DEFAULT NULL COMMENT 'Reference no',
  NOT_TYP bigint(12) DEFAULT NULL COMMENT 'Notice Type',
  NOT_NO varchar(20) DEFAULT NULL COMMENT 'Notice No.	',
  NOT_DATE date DEFAULT NULL COMMENT 'Notice Date	',
  NOT_DUEDT date DEFAULT NULL COMMENT 'Notice DueDate	',
  NOT_ACCEPTDT date DEFAULT NULL COMMENT 'Notice Acceptdt',
  NOT_REMARKS varchar(100) DEFAULT NULL COMMENT 'Remarks	',
  NOT_SIGNEDBY bigint(12) DEFAULT NULL COMMENT 'Sigine By',
  NOT_SIGNEDDT datetime DEFAULT NULL COMMENT 'Signe date	',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id	',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record	',
  CREATION_DATE datetime DEFAULT NULL COMMENT '	X	',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (NOT_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;