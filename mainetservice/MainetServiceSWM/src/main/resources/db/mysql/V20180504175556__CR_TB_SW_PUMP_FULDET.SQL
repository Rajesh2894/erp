--liquibase formatted sql
--changeset nilima:V20180504175556__CR_TB_SW_PUMP_FULDET.SQL
CREATE TABLE TB_SW_PUMP_FULDET (
  pfu_id bigint(12) NOT NULL COMMENT 'Primary key',
  pu_id bigint(12) NOT NULL COMMENT 'FK TB_SW_PUMP_MST',
  pu_fuid bigint(12) NOT NULL COMMENT ' Fuel Type->Value from prefix',
  pu_fuunit bigint(12) NOT NULL COMMENT ' Fuel Unit->Value from prefix',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (pfu_id),
  KEY FK_PU_ID_idx (pu_id),
  CONSTRAINT FK_PU_ID FOREIGN KEY (pu_id) REFERENCES tb_sw_pump_mast (PU_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Refillin pump station detail';
