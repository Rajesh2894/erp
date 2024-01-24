--liquibase formatted sql
--changeset nilima:V20180609170502__CR_TB_SW_CONTVEND_MAPPING_09062018.sql
CREATE TABLE TB_SW_CONTVEND_MAPPING (
  MAP_ID bigint(12) NOT NULL,
  CONT_ID bigint(12) NOT NULL COMMENT 'FK tb_contract_mast',
  MAP_TASK_ID varchar(50) DEFAULT NULL COMMENT 'Task Mapping',
  COD_WARD1 bigint(12) DEFAULT NULL,
  COD_WARD2 bigint(12) DEFAULT NULL,
  COD_WARD3 bigint(12) DEFAULT NULL,
  COD_WARD4 bigint(12) DEFAULT NULL,
  COD_WARD5 bigint(12) DEFAULT NULL,
  MAP_WASTETYPE bigint(12) DEFAULT NULL,
  MAP_GARBAGE bigint(12) DEFAULT NULL,
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE bigint(12) NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  PRIMARY KEY (MAP_ID),
  KEY FK_SWCONTID_idx (CONT_ID),
  CONSTRAINT FK_SWCONTID FOREIGN KEY (CONT_ID) REFERENCES tb_contract_mast (CONT_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;