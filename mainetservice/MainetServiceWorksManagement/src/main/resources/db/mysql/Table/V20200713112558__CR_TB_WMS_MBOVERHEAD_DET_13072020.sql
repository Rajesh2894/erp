--liquibase formatted sql
--changeset Anil:V20200713112558__CR_TB_WMS_MBOVERHEAD_DET_13072020.sql
drop table if exists TB_WMS_MBOVERHEAD_DET;
--liquibase formatted sql
--changeset Anil:V20200713112558__CR_TB_WMS_MBOVERHEAD_DET_130720201.sql
CREATE TABLE TB_WMS_MBOVERHEAD_DET (
  MB_OVHID bigint(12) NOT NULL COMMENT 'primary key',
  OV_ID bigint(12) NOT NULL COMMENT 'Foreign key',
  WORK_ID bigint(12) NOT NULL,
  OV_VALUE decimal(20,2) DEFAULT NULL,
  ACT_AMT decimal(20,2) DEFAULT NULL,
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE date NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE date DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (MB_OVHID),
  KEY FK_OV_ID_IDX (OV_ID),
  CONSTRAINT FK_OV_ID FOREIGN KEY (OV_ID) REFERENCES tb_wms_overhead_detail(OV_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
);

