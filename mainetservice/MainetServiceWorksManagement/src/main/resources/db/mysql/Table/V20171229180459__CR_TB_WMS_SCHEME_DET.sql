--liquibase formatted sql
--changeset jinea:V20171229180459__CR_TB_WMS_SCHEME_DET.sql
CREATE TABLE TB_WMS_SCHEME_DET (
  SCHD_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  SCH_ID bigint(12) NOT NULL COMMENT 'Foregin  Key(TB_WMS_SCHEME_MAST)',
  SCHD_SPONSORED_BY varchar(250) NOT NULL COMMENT 'Scheme Sponsoror BY',
  SCHD_SHARING_PER decimal(6,2) DEFAULT NULL COMMENT 'Scheme Sharing Percentage',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  SCHD_ACTIVE char(1) DEFAULT NULL,
  PRIMARY KEY (SCHD_ID),
  KEY FK_SCHID_idx (SCH_ID),
  CONSTRAINT FK_SCHID FOREIGN KEY (SCH_ID) REFERENCES TB_WMS_SCHEME_MAST (SCH_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

