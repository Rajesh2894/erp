--liquibase formatted sql
--changeset Kanchan:V20220323202353__CR_tb_wms_projectBudet_Det_23032022.sql
CREATE TABLE `tb_wms_projectBudet_Det` (
  `PB_ID` bigint(12) NOT NULL COMMENT 'Primary Key',
  `PROJ_ID` bigint(12) NOT NULL COMMENT 'foregin key TB_WMS_PROJECT_MAST',
  `SAC_HEAD_ID` bigint(12) DEFAULT NULL COMMENT 'BugetCode',
  `FA_YEARID` bigint(12) DEFAULT NULL COMMENT 'Financiale Year Id',
  `YE_FINANCE_CODE_DESC` varchar(500) DEFAULT NULL,
  `YE_BUGEDED_AMOUNT` decimal(15,2) DEFAULT NULL COMMENT 'Bugeted Amount',
  `ORGID` bigint(12) DEFAULT NULL COMMENT 'organization id',
  `CREATED_BY` bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  `CREATED_DATE` datetime DEFAULT NULL COMMENT 'record creation date',
  `LG_IP_MAC` varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  `UPDATED_BY` bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  `UPDATED_DATE` datetime DEFAULT NULL COMMENT 'date on which updated the record',
  `LG_IP_MAC_UPD` varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (`PB_ID`),
  KEY `FK_PROJ_ID_idx` (`PROJ_ID`),
  KEY `FK_WORK_FAYEARID_idx` (`FA_YEARID`),
   CONSTRAINT `FK_WORK_FAYEARID1` FOREIGN KEY (`FA_YEARID`) REFERENCES `tb_financialyear` (`FA_YEARID`) ON DELETE NO ACTION ON UPDATE NO ACTION  ,
  CONSTRAINT `FK_PROJ_ID1` FOREIGN KEY (`PROJ_ID`) REFERENCES `TB_WMS_PROJECT_MAST` (`PROJ_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Project budet detail';
