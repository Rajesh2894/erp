--liquibase formatted sql
--changeset nilima:V20180518114918__CR_TB_WMS_WORKEORDER_TERMS_14052018.sql
CREATE TABLE TB_WMS_WORKEORDER_TERMS (
  WORKOR_TEID BIGINT(12) NOT NULL COMMENT 'Primary key',
  WORKOR_ID BIGINT(12) NOT NULL COMMENT 'FK  TB_WMS_WORKEORDER',
  WORKOR_TERMDESC VARCHAR(1000) NOT NULL COMMENT 'Terms Description',
  WORKOR_TERMACTIVE CHAR(1) NOT NULL COMMENT 'Active->\'Y\',Inactive->\'N\'',
  ORGID BIGINT(12) NOT NULL COMMENT 'organization id',
  CREATED_BY BIGINT(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE DATETIME NOT NULL COMMENT 'record creation date',
  UPDATED_BY BIGINT(12) NULL COMMENT 'user id who updated the record',
  UPDATED_DATE DATETIME NULL,
  LG_IP_MAC VARCHAR(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD VARCHAR(100) NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (WORKOR_TEID),
  INDEX FK_WORKORDER_ID_idx (WORKOR_ID ASC),
  CONSTRAINT FK_WORKORDER_ID
    FOREIGN KEY (WORKOR_ID)
    REFERENCES tb_wms_workeorder (WORKOR_ID)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
COMMENT = 'Work Order Terms and condition';

