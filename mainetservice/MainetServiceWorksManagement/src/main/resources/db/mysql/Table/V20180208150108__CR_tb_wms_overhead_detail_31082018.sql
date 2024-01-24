--liquibase formatted sql
--changeset priya:V20180208150108__CR_tb_wms_overhead_detail_31082018.sql
CREATE TABLE tb_wms_overhead_detail (
  OV_ID BIGINT(12) NOT NULL COMMENT 'Primary Jey',
  WORK_ID BIGINT(12) NULL COMMENT 'foregin key TB_WMS_WORKESTIMATE_MAS',
  OV_CODE VARCHAR(200) NULL COMMENT 'Overhead Code',
  OV_DESCRIPTION VARCHAR(1000) NULL COMMENT 'Overhead Description',
  OV_VALUE_TYPE BIGINT(12) NULL COMMENT 'Value Type (Amount,Percentage)',
  OV_RATE DECIMAL(20,2) NULL COMMENT 'Overhead Rate',
  WORK_EST_AMT DECIMAL(20,2) NULL COMMENT 'SOR ESTIMATE Amount',
  OV_VALUE DECIMAL(20,2) NULL COMMENT 'Overhead value',
  ORGID BIGINT(12) NULL,
  CREATED_BY BIGINT(12) NULL COMMENT 'user id who created the record',
  CREATED_DATE DATETIME NULL COMMENT 'record creation date',
  UPDATED_BY BIGINT(12) NULL COMMENT 'user id who updated the record',
  UPDATED_DATE DATETIME NULL COMMENT 'date on which updated the record',
  LG_IP_MAC VARCHAR(100) NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD VARCHAR(100) NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (OV_ID),
  INDEX FK_OV_WORK_ID_idx (WORK_ID ASC),
  CONSTRAINT FK_OV_WORK_ID
    FOREIGN KEY (WORK_ID)
    REFERENCES tb_wms_workdefination (WORK_ID)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
COMMENT = 'OVERHEAD DETAIL';
