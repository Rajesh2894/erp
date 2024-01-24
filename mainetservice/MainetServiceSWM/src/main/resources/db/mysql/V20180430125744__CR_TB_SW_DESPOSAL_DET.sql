--liquibase formatted sql
--changeset nilima:V20180430125744__CR_TB_SW_DESPOSAL_DET.sql
CREATE TABLE TB_SW_DESPOSAL_DET (
  DED_ID BIGINT(12) NOT NULL COMMENT 'Primary Key',
  DE_ID BIGINT(12) NOT NULL COMMENT 'foregin key TB_SW_DEPOSAL_MAST',
  DE_WEST_TYPE BIGINT(12) NOT NULL COMMENT 'Desposal Waste Details',
  ORGID BIGINT(12) NOT NULL COMMENT 'organization id',
  CREATED_BY BIGINT(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE DATETIME NOT NULL COMMENT 'record creation date',
  UPDATED_BY BIGINT(12) NULL COMMENT 'user id who updated the record',
  UPDATED_DATE DATETIME NULL COMMENT 'date on which updated the record',
  LG_IP_MAC VARCHAR(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD VARCHAR(100) NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (DED_ID),
  INDEX FK_DE_ID_idx (DE_ID ASC),
  CONSTRAINT FK_DE_ID
    FOREIGN KEY (DE_ID)
    REFERENCES tb_sw_desposal_mast (DE_ID)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
COMMENT = 'Desposal Details';