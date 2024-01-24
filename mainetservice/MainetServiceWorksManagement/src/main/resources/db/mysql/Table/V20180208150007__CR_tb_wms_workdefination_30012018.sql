--liquibase formatted sql
--changeset priya:V20180208150007__CR_tb_wms_workdefination_30012018.sql
CREATE TABLE tb_wms_workdefination (
  WORK_ID BIGINT(12) NOT NULL COMMENT 'Primary Key',
  WORK_NAME VARCHAR(500) NOT NULL COMMENT 'Work Name',
  PROJ_ID BIGINT(12) NOT NULL COMMENT 'Project Code (Foregin Key TB_WMS_PROJECT_MAST)',
  WORK_START_DATE DATE NOT NULL COMMENT 'Work Start date ',
  WORK_END_DATE DATE NOT NULL COMMENT 'Work end date ',
  WORK_TYPE BIGINT(12) NOT NULL COMMENT 'Work Type(Capital/Maintenance)(prefix)',
  DP_DEPTID BIGINT(12) NOT NULL COMMENT 'Execution Department',
  WORK_PROJECT_PHASE BIGINT(12) NOT NULL COMMENT 'Project Phase (preifix)',
  LOC_ID_ST BIGINT(12) NOT NULL COMMENT 'Start Location Id',
  LOC_ID_EN BIGINT(12) NOT NULL COMMENT 'End Location Id',
  ASSET_CODE VARCHAR(50) NULL COMMENT 'Asset Code',
  WORK_CODE VARCHAR(500) NOT NULL,
  WORK_STATUS CHAR(3) NOT NULL COMMENT '(D->Draft,P->Pending,A->Approved,AA->Administrator Approval,TA->Technical Approval)',
  ORGID BIGINT(12) NOT NULL COMMENT 'organization id',
  CREATED_BY BIGINT(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE DATETIME NOT NULL COMMENT 'record creation date',
  UPDATED_BY BIGINT(12) NULL COMMENT 'user id who updated the record',
  UPDATED_DATE DATETIME NULL COMMENT 'date on which updated the record',
  LG_IP_MAC VARCHAR(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD VARCHAR(100) NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (WORK_ID),
  INDEX FK_WR_PROJID_idx (PROJ_ID ASC),
  INDEX FK_WR_DPDEPTID_idx (DP_DEPTID ASC),
  CONSTRAINT FK_WR_PROJID
    FOREIGN KEY (PROJ_ID)
    REFERENCES tb_wms_project_mast (PROJ_ID)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT FK_WR_DPDEPTID
    FOREIGN KEY (DP_DEPTID)
    REFERENCES tb_department (DP_DEPTID)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
COMMENT = 'Work Defination';
