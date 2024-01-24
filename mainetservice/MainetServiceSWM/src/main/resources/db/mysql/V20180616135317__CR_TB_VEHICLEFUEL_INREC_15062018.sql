--liquibase formatted sql
--changeset nilima:V20180616135317__CR_TB_VEHICLEFUEL_INREC_15062018.sql
CREATE TABLE TB_SW_VEHICLEFUEL_INREC (
  INREC_ID BIGINT(12) NOT NULL COMMENT 'Primary Key',
  PU_ID BIGINT(12) NULL COMMENT 'FK TB_SW_PUMP_MST',
  INREC_FROMDT DATE NULL COMMENT 'Invoice Reconsilation From Date',
  INREC_TODT DATE NULL COMMENT 'Invoice Reconsilation To Date',
  ORGID BIGINT(12) NULL COMMENT 'organization id',
  CREATED_BY BIGINT(12) NULL COMMENT 'user id who created the record',
  CREATED_DATE DATETIME NULL COMMENT 'record creation date',
  UPDATED_BY BIGINT(12) NULL COMMENT 'user id who updated the record',
  LG_IP_MAC VARCHAR(100) NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD VARCHAR(100) NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (INREC_ID),
  INDEX FK_INCRECPU_ID_idx (PU_ID ASC),
  CONSTRAINT FK_INCRECPU_ID
    FOREIGN KEY (PU_ID)
    REFERENCES tb_sw_pump_mast (PU_ID)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
COMMENT = 'Reconcilation of fuel Invoice';