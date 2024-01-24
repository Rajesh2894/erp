--liquibase formatted sql
--changeset nilima:V20171031170713__AL_tb_fincialyearorg_map_13102017.sql
CREATE TABLE TB_WORKLFOW_DOCUMENTS (
  WORKFLOW_DOC_ID BIGINT(12) NOT NULL COMMENT 'Primary Key',
  WORKFLOW_ACT_ID BIGINT(19) NULL COMMENT 'fk with TB_WORKFLOW_ACTION',
  DMS_DOC_ID VARCHAR(100) NULL COMMENT 'DMS document ID',
  DMS_DOC_NAME VARCHAR(100) NULL COMMENT 'DMS document Name',
  DMS_DOC_VERSION VARCHAR(100) NULL COMMENT 'DMS document Version',
  DMS_FOLDER_PATH VARCHAR(100) NULL COMMENT 'DMS floder path',
  ORGID BIGINT(12) NOT NULL COMMENT '',
  CREATED_BY BIGINT(12) NOT NULL COMMENT '',
  CREATED_DATE DATETIME NOT NULL COMMENT '',
  UPDATED_BY BIGINT(12) NULL COMMENT '',
  UPDATED_DATE DATETIME NULL COMMENT '',
  PRIMARY KEY (WORKFLOW_DOC_ID),
  INDEX FK_ACT_ID_idx (WORKFLOW_ACT_ID ASC),
  CONSTRAINT FK_ACT_ID
    FOREIGN KEY (WORKFLOW_ACT_ID)
    REFERENCES service.TB_WORKFLOW_ACTION (WORKFLOW_ACT_ID)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Stores Data related document attached during workflow';