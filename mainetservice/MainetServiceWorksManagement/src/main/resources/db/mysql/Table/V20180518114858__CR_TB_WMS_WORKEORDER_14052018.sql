--liquibase formatted sql
--changeset nilima:V20180518114858__CR_TB_WMS_WORKEORDER_14052018.sql
DROP TABLE IF exists TB_WMS_WORKEORDER;
--changeset nilima:V20180518114858__CR_TB_WMS_WORKEORDER_140520181.sql
CREATE TABLE TB_WMS_WORKEORDER(
  WORKOR_ID BIGINT(12) NOT NULL COMMENT 'Primary KEy',
  WORKOR_NO VARCHAR(25) NOT NULL,
  WORKOR_DATE DATE NULL COMMENT 'Work Order Date',
  TND_ID BIGINT(12) NULL COMMENT 'Tender No FK TB_WMS_TENDER_MAST',
  WORKOR_AGFROMDATE DATE NOT NULL COMMENT 'Work Order Agreement From Date',
  WORKOR_AGTODATE DATE NOT NULL COMMENT 'Work Order Agreement To  Date',
  WORKOR_STARTDATE DATE NOT NULL COMMENT 'Date to start the work',
  WORKOR_DEFECTLIABILITYPER BIGINT(12) NOT NULL COMMENT 'Defect Liabilitty Period',
  ORGID BIGINT(12) NOT NULL COMMENT 'organization id',
  CREATED_BY BIGINT(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE DATE NOT NULL,
  UPDATED_BY BIGINT(12) NULL,
  UPDATED_DATE DATETIME NULL,
  LG_IP_MAC VARCHAR(100) NULL,
  LG_IP_MAC_UPD VARCHAR(100) NULL,
  PRIMARY KEY (WORKOR_ID),
  INDEX FK_WORKORDER_TND_idx(TND_ID ASC),
  CONSTRAINT FK_WORKORDER_TND
    FOREIGN KEY (TND_ID)
    REFERENCES tb_wms_tender_mast(TND_ID)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
COMMENT = 'Works Management Work Order';