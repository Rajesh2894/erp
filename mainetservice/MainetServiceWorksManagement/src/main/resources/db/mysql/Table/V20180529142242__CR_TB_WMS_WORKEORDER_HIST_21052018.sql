--liquibase formatted sql
--changeset nilima:V20180529142242__CR_TB_WMS_WORKEORDER_HIST_21052018.sql
CREATE TABLE TB_WMS_WORKEORDER_HIST (
  WORKOR_ID_H bigint(12) NOT NULL COMMENT 'Primary KEy',
  WORKOR_ID bigint(12)  COMMENT 'Primary KEy',
  WORKOR_NO varchar(25) ,
  WORKOR_DATE date  COMMENT 'Work Order Date',
  TND_ID bigint(12)  COMMENT 'Tender No FK TB_WMS_TENDER_MAST',
  WORKOR_AGFROMDATE date  COMMENT 'Work Order Agreement From Date',
  WORKOR_AGTODATE date  COMMENT 'Work Order Agreement To  Date',
  WORKOR_STARTDATE date  COMMENT 'Date to start the work',
  WORKOR_DEFECTLIABILITYPER bigint(12)  COMMENT 'Defect Liabilitty Period',
  H_STATUS char(1)  COMMENT 'Status I->Insert,U->update',  
  ORGID bigint(12)  COMMENT 'organization id',
  CREATED_BY bigint(12)  COMMENT 'user id who created the record',
  CREATED_DATE date ,
  UPDATED_BY bigint(12) ,
  UPDATED_DATE datetime ,
  LG_IP_MAC varchar(100) ,
  LG_IP_MAC_UPD varchar(100) ,
  PRIMARY KEY (WORKOR_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Works Management Work Order History';
