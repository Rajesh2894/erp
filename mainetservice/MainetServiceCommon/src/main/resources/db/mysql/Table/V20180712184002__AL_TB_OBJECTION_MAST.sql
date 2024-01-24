--liquibase formatted sql
--changeset nilima:V20180712184001__AL_TB_OBJECTION_MAST1.sql
CREATE TABLE tb_objection_mast (
  OBJ_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  OBJ_NO varchar(50) NOT NULL,
  OBJ_DATE date NOT NULL COMMENT 'Object Date',
  DP_DEPTID bigint(12) NOT NULL,
  COD_ID_OPER_LEVEL1 bigint(12) DEFAULT NULL COMMENT 'Operational Word Level',
  COD_ID_OPER_LEVEL2 bigint(12) DEFAULT NULL COMMENT 'Operational Word Level',
  COD_ID_OPER_LEVEL3 bigint(12) DEFAULT NULL COMMENT 'Operational Word Level',
  COD_ID_OPER_LEVEL4 bigint(12) DEFAULT NULL COMMENT 'Operational Word Level',
  COD_ID_OPER_LEVEL5 bigint(12) DEFAULT NULL COMMENT 'Operational Word Level',
  SM_SERVICE_ID bigint(12) NOT NULL COMMENT 'Service Id',
  APM_APPLICATION_ID bigint(16) NOT NULL COMMENT 'Application Id',
  OBJ_REFID varchar(50) NOT NULL COMMENT 'Reference Id',
  OBJ_ADDREFID varchar(50) DEFAULT NULL COMMENT 'Additional Reference Id',
  OBJ_DETAIL varchar(45) NOT NULL COMMENT 'Objection Details',
  OBJ_STATUS varchar(100) NOT NULL COMMENT 'Objection Status',
  BILL_NO varchar(50) DEFAULT NULL COMMENT '	X	',
  NOTTICE_NO varchar(50) DEFAULT NULL COMMENT '	X	',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (OBJ_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Objection Mast';