--liquibase formatted sql
--changeset nilima:V20190328171153__AL_tb_wms_measurementbook_det_hist_27032019.sql
drop table if exists tb_wms_measurementbook_det_hist;

--liquibase formatted sql
--changeset nilima:V20190328171153__AL_tb_wms_measurementbook_det_hist_270320191.sql
CREATE TABLE tb_wms_measurementbook_det_hist(
  MBD_ID_H bigint(12) NOT NULL COMMENT 'primary key',
  MBD_ID bigint(12) DEFAULT NULL COMMENT 'primary key',
  MB_ID bigint(12) DEFAULT NULL COMMENT 'Foregin Key TB_WMS_MEASUREMENTBOOK_MAST',
  MBD_PID bigint(12) DEFAULT NULL COMMENT 'Parent id',
  WORKE_ESTIMATE_TYPE char(1) DEFAULT NULL COMMENT '(Estimate from SOR->''S'',From Previous Estimate->''P'',Upload Estimate->''U'')',
  WORKE_ID bigint(12) DEFAULT NULL COMMENT 'Foregin Key TB_WMS_WORKESTIMATE_MAS',
  WORKE_ACTUAL_QTY decimal(15,2) DEFAULT NULL COMMENT 'Actual Quantity',
  WORKE_UTL_QTY decimal(15,2) DEFAULT NULL,
  WORKE_ACTUAL_AMOUNT decimal(20,2) DEFAULT NULL,
  WORKE_FLAG char(3) DEFAULT NULL COMMENT '(S->SOR ITEM,M->Material,RO->Royalty,LO->LOAD,UN->Unload,LE->Lead,LF->LIFT,ST->Stacking,L->Labour,C->Machinary,N->NON-SOR)',
  H_STATUS char(1) DEFAULT NULL COMMENT 'Status I->Insert,update',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL,
  CREATED_DATE datetime DEFAULT NULL,
  UPDATED_BY bigint(12) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) DEFAULT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  PRIMARY KEY (MBD_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Measurement Book Detail History';