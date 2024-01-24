--liquibase formatted sql
--changeset nilima:V20181001145751__CR_TB_SW_MRF_EMPLOYEE_DET_25092018.sql
CREATE TABLE TB_SW_MRF_EMPLOYEE_DET (
  MRFE_ID bigint(12) NOT NULL COMMENT 'Primary key',
  MRF_ID bigint(12) NOT NULL COMMENT 'Foregin key',
  DSGID bigint(12) NOT NULL COMMENT 'Vechicle Type',
  MRFE_AVALCNT bigint(12) NOT NULL COMMENT 'Vechicle Avalable Count',
  MRFE_REQCNT bigint(12) NOT NULL,
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (MRFE_ID),
  KEY FK_EMRF_ID_idx (MRF_ID),
  CONSTRAINT FK_EMRF_ID FOREIGN KEY (MRF_ID) REFERENCES tb_sw_mrf_mast (MRF_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;