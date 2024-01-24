--liquibase formatted sql
--changeset priya:V20180208150040__CR_tb_wms_measurement_detail_31082018.sql
CREATE TABLE tb_wms_measurement_detail (
  ME_ID BIGINT(12) NOT NULL COMMENT 'Primary Jey',
  WORKE_ID BIGINT(12) NOT NULL COMMENT 'Foregin Jey(TB_WMS_WORKESTIMATE_MAS)\n',
  ME_PARTICULARE VARCHAR(500) NOT NULL COMMENT 'Measurement Particulare',
  ME_NOS BIGINT(5) NOT NULL COMMENT 'Nos',
  ME_VALUE_TYPE CHAR(1) NOT NULL COMMENT 'Value Type (Calculated->C,Direct->D,Formula->F)',
  ME_LENGTH INT(7) NULL COMMENT 'Length',
  ME_BREADTH INT(7) NULL COMMENT 'Breadth',
  ME_HEIGHT INT(7) NULL COMMENT 'Height',
  ME_FORMULA CHAR(1) NULL COMMENT 'Formula/Direct Value',
  ME_TOTAL BIGINT(12) NOT NULL COMMENT 'Total Quantity',
  ME_TYPE CHAR(1) NOT NULL COMMENT 'Deviation Type (Calculated->C,Direct->D,Formula->F)',
  ME_ACTIVE CHAR(1) NOT NULL,
  ORGID BIGINT(12) NOT NULL COMMENT 'organization id',
  CREATED_BY BIGINT(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE DATETIME NOT NULL COMMENT 'record creation date',
  UPDATED_BY BIGINT(12) NULL COMMENT 'user id who updated the record',
  UPDATED_DATE DATETIME NULL COMMENT 'date on which updated the record',
  LG_IP_MAC VARCHAR(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD VARCHAR(100) NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (ME_ID),
  INDEX FK_ME_WORKD_ID_idx (WORKE_ID ASC),
  CONSTRAINT FK_ME_WORKD_ID
    FOREIGN KEY (WORKE_ID)
    REFERENCES tb_wms_workestimate_mas (WORKE_ID)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
COMMENT = 'Work estimation measurement details';
