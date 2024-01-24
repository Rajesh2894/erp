--liquibase formatted sql
--changeset priya:V20180417134355__CR_TB_WMS_WORKESTIMATE_MAS_HIST.sql
drop table if exists TB_WMS_WORKESTIMATE_MAS_HIST;

--liquibase formatted sql
--changeset priya:V20180417134355__CR_TB_WMS_WORKESTIMATE_MAS_HIST1.sql
CREATE TABLE TB_WMS_WORKESTIMATE_MAS_HIST (
  WORKE_ID_H bigint(12) NOT NULL COMMENT 'Primary Jey',
  WORKE_ID bigint(12)  COMMENT 'Primary Jey',
  WORK_ID bigint(12)  COMMENT 'foregin key TB_WMS_WORKDEFINATION',
  WORKE_ESTIMATE_TYPE char(1)  COMMENT '(Estimate from SOR->''S'',From Previous Estimate->''P'',Upload Estimate->''U'')',
  WORKE_PID bigint(12)  COMMENT 'Parent id of primary key',
  WORKE_File_Name varchar(500)  COMMENT 'when measuremnet upoaded',
  WORKE_PRE_ESTID bigint(12)  COMMENT 'From Previous Estimate',
  SOR_ID bigint(12)  COMMENT 'foregin key TB_WMS_SOR_MAS(Estimate from SOR->''S'')',
  SORD_ID bigint(12)  COMMENT 'foregin key TB_WMS_SOR_DET(Estimate from SOR->''S'')',
  MA_ID bigint(12)  COMMENT 'foregin key TB_WMS_GEN_RATE_MAST(Estimate from SOR->''S'')',
  MA_PID bigint(12)  COMMENT 'Parent id of primary key of TB_WMS_GEN_RATE_MAST',
  SORD_CATEGORY bigint(12)  COMMENT 'Category',
  SORD_SUBCATEGORY varchar(2000)  COMMENT 'Sub Category',
  SORD_ITEMNO varchar(50)  COMMENT 'Item Number',
  SORD_DESCRIPTION varchar(4000)  COMMENT 'Item Description',
  SORD_ITEM_UNIT bigint(12)  COMMENT 'Item Unit from prefix',
  SORD_BASIC_RATE decimal(15,2)  COMMENT 'Basic Rate',
  SORD_LABOUR_RATE decimal(15,2)  COMMENT 'Labour Rate\n',
  WORKE_QUANTITY decimal(5,0)  COMMENT 'Quantity',
  WORKE_AMOUNT decimal(20,2)  COMMENT 'Total (Quantity*Rate)',
  WORKE_FLAG char(3)  COMMENT '(S->''SOR ITEM'',''M''->Material,''RO''->Royalty,''LO''->LOAD,''UN''->''Unload'',''LE''->''Lead'',''LF''->LIFT,''ST''->''Stacking'',''L''->Labour,''C''->Machinary,''N''->''NON-SOR'')\n',
  WORKE_ACTIVE char(1)  COMMENT 'Status (''Y''-> Active,''N''->Inactive)',
  ORGID bigint(12)  COMMENT 'organization id',
  CREATED_BY bigint(12)  COMMENT 'user id who created the record',
  CREATED_DATE datetime  COMMENT 'record creation date',
  UPDATED_BY bigint(12)  COMMENT 'user id who updated the record',
  UPDATED_DATE datetime  COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100)  COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100)  COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (WORKE_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Work Estimate';
