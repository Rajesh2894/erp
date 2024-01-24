--liquibase formatted sql
--changeset nilima:V20180529142308__CR_TB_WMS_VIGILANCE_DET_21052018.sql
CREATE TABLE TB_WMS_VIGILANCE_DET (
  VlD_ID BIGINT(12) NOT NULL COMMENT 'Primary Key',
  Vl_ID BIGINT(12) NOT NULL COMMENT 'FK TB_WMS_VIGILANCE',
  EMP_ID BIGINT(12) NULL COMMENT 'Employee Name',
  VID_DESC VARCHAR(500) NULL COMMENT 'Response Details',
  VID_STATUS CHAR(1) NOT NULL COMMENT 'R->Received  N->Not Received',
  ORGID BIGINT(12) NOT NULL COMMENT 'organization id',
  CREATED_BY BIGINT(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE DATETIME NOT NULL COMMENT 'record creation date',
  UPDATED_BY VARCHAR(45) NULL COMMENT 'user id who updated the record',
  UPDATED_DATE DATETIME NULL COMMENT 'date on which updated the record',
  LG_IP_MAC VARCHAR(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD VARCHAR(100) NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (VlD_ID),
  INDEX FK_VI_ID_idx (Vl_ID ASC),
  INDEX FK_EMPID_idx (EMP_ID ASC),
  CONSTRAINT FK_VI_ID
    FOREIGN KEY (Vl_ID)
    REFERENCES tb_wms_vigilance (VI_ID)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT FK_EMPID
    FOREIGN KEY (EMP_ID)
    REFERENCES employee (EMPID)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
COMMENT = 'Vigilance Detail';