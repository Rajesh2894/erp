--liquibase formatted sql
--changeset nilima:V20180511154721__CR_TB_SW_DISPOSAL_MAST_11052018.sql
CREATE TABLE TB_SW_DISPOSAL_DET (
  DED_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  DE_ID bigint(12) NOT NULL COMMENT 'foregin key TB_SW_DEPOSAL_MAST',
  DE_WEST_TYPE bigint(12) NOT NULL COMMENT 'Desposal Waste Details',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (DED_ID),
  KEY FK_DE_ID_idx (DE_ID),
  CONSTRAINT FK_DE_ID FOREIGN KEY (DE_ID) REFERENCES tb_sw_disposal_mast (DE_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Desposal Details';