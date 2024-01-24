--liquibase formatted sql
--changeset nilima:V20171031182153__CR_TB_WORKFLOW_MAS_28092017.sql
DROP TABLE workflow_mapping;
--liquibase formatted sql
--changeset nilima:V20171031182153__CR_TB_WORKFLOW_MAS_280920171.sql
DROP TABLE workflow_type;

--liquibase formatted sql
--changeset nilima:V20171031182153__CR_TB_WORKFLOW_MAS_280920172.sql
CREATE TABLE tb_workflow_mas (
  WF_ID BIGINT(12) NOT NULL COMMENT 'primary key',
  DP_DEPTID BIGINT(12) NOT NULL COMMENT 'Department Id',
  SM_SERVICE_ID BIGINT(12) NULL COMMENT 'Service Id',
  WF_MODE BIGINT(12) NOT NULL COMMENT 'Workflow Mode->Auto Escalation,Manual Escalation',
  COMP_ID BIGINT(12) NULL COMMENT 'Complaint Type',
  WF_LOC_TYPE VARCHAR(1) NULL COMMENT 'Location Type',
  COD_ID_OPER_LEVEL1 BIGINT(12) NULL COMMENT 'OPER_LEVEL1',
  COD_ID_OPER_LEVEL2 BIGINT(12) NULL COMMENT 'OPER_LEVEL2',
  COD_ID_OPER_LEVEL3 BIGINT(12) NULL COMMENT 'OPER_LEVEL3',
  COD_ID_OPER_LEVEL4 BIGINT(12) NULL COMMENT 'OPER_LEVEL4',
  COD_ID_OPER_LEVEL5 BIGINT(12) NULL COMMENT 'OPER_LEVEL5',
  WF_STATUS VARCHAR(45) NOT NULL COMMENT 'Workflow Status',
  ORGID BIGINT(12) NOT NULL COMMENT 'organization id',
  CREATED_BY BIGINT(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE DATETIME NOT NULL COMMENT 'record creation date',
  UPDATED_BY BIGINT(12) NULL COMMENT 'user id who updated the record',
  UPDATED_DATE DATETIME NULL COMMENT 'user date who updated the record',
  LG_IP_MAC VARCHAR(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD VARCHAR(100) NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (WF_ID)  COMMENT '')
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci
COMMENT = 'Workflow type Master';
