--liquibase formatted sql
--changeset nilima:V20180609165608__CR_TB_SW_TRIPSHEET_GDET_08062018.sql
CREATE TABLE TB_SW_TRIPSHEET_GDET(
  TRIPD_ID BIGINT(12) NOT NULL COMMENT 'Primary Key',
  TRIP_ID BIGINT(12) NOT NULL COMMENT 'FK TB_SW_TRIPSHEET',
  WAST_TYPE BIGINT(12) NOT NULL COMMENT 'Waste Type',
  TRIP_VOLUME BIGINT(20) NOT NULL COMMENT 'Volume',
  ORGID BIGINT(12) NOT NULL COMMENT 'organization id',
  CREATED_BY BIGINT(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE DATETIME NOT NULL COMMENT 'record creation date',
  UPDATED_BY BIGINT(12) NULL COMMENT 'user id who updated the record',
  UPDATED_DATE DATETIME NULL COMMENT 'date on which updated the record',
  LG_IP_MAC VARCHAR(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD VARCHAR(100) NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (TRIPD_ID),
  INDEX FK_TRIPID_idx(TRIP_ID ASC),
  CONSTRAINT FK_TRIPID
    FOREIGN KEY (TRIP_ID)
    REFERENCES tb_sw_tripsheet(TRIP_ID)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);