--liquibase formatted sql
--changeset Anil:V20200313161141__CR_TB_RTS_DRN_CON_13032020.sql
drop table if exists TB_RTS_DRN_CON;
--liquibase formatted sql
--changeset Anil:V20200313161141__CR_TB_RTS_DRN_CON_130320201.sql
CREATE TABLE TB_RTS_DRN_CON(
  CNN_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  APM_APPLICATION_ID bigint(16) NOT NULL COMMENT 'Foreign Key (tb_cfc_application_mst)',
  DRAINAGE_ADDRESS varchar(1000) NOT NULL,
  APPLICANT_TYPE bigint(12) NOT NULL,
  SIZE_OF_CONNECTION bigint(13) NOT NULL,
  TYPE_OF_CONNECTION bigint(14) NOT NULL,
  WARD bigint(15) NOT NULL,
  PROPERTY_INDEX_NO varchar(100) NOT NULL,
  ORGID bigint(19) NOT NULL,
  CREATED_BY bigint(10) NOT NULL,
  CREATED_DATE datetime NOT NULL COMMENT 'Date on which data is going to create',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'Client Machine?s Login Name | IP Address | Physical Address',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'Updated Client Machine?s Login Name | IP Address | Physical Address',
  UPDATED_BY bigint(12) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  PRIMARY KEY (CNN_ID),
  KEY FK_APM_APPLICATION_ID_1 (APM_APPLICATION_ID),
  CONSTRAINT FK_APM_APPLICATION_ID_1 FOREIGN KEY (APM_APPLICATION_ID) REFERENCES tb_cfc_application_mst (APM_APPLICATION_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
);
--liquibase formatted sql
--changeset Anil:V20200313161141__CR_TB_RTS_DRN_CON_130320202.sql
drop table if exists TB_RTS_DRN_CON_HIST;
--liquibase formatted sql
--changeset Anil:V20200313161141__CR_TB_RTS_DRN_CON_130320203.sql
CREATE TABLE TB_RTS_DRN_CON_HIST (
  CNN_ID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  CNN_ID bigint(12) NOT NULL,
  APM_APPLICATION_ID bigint(16) NOT NULL COMMENT 'Foreign Key (TB_SEQ_CONFIGMASTER)',
  DRAINAGE_ADDRESS varchar(1000) NOT NULL,
  APPLICANT_TYPE bigint(12) NOT NULL,
  SIZE_OF_CONNECTION bigint(13) NOT NULL,
  TYPE_OF_CONNECTION bigint(14) NOT NULL,
  WARD bigint(15) NOT NULL,
  PROPERTY_INDEX_NO varchar(100) NOT NULL,
  ORGID bigint(19) NOT NULL,
  CREATED_BY bigint(10) NOT NULL,
  CREATED_DATE datetime NOT NULL COMMENT 'Date on which data is going to create',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'Client Machine?s Login Name | IP Address | Physical Address',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'Updated Client Machine?s Login Name | IP Address | Physical Address',
  UPDATED_BY bigint(12) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  H_STATUS varchar(2) DEFAULT NULL,
  PRIMARY KEY (CNN_ID_H)
);

