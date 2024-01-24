CREATE TABLE `TB_ADJ_BILL_DETAILS` (
  `ADJBD_ID` bigint(12) NOT NULL COMMENT 'Primary Key',
  `ADJD_ID` bigint(12) DEFAULT NULL COMMENT 'Adjustment Taxwise Detail iD(FK TB_ADJUSTMENT_DET)',
  `BD_BILLDETID` bigint(12) DEFAULT NULL COMMENT 'Bill Detail id from bill detail table against which adjustment is done',
  `ORGID` bigint(12) DEFAULT NULL COMMENT 'orgnisation id',
  `CREATED_BY` bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  `CREATED_DATE` datetime DEFAULT NULL COMMENT 'record creation date',
  `UPDATED_BY` bigint(12) DEFAULT NULL COMMENT 'user id who update the data',
  `UPDATED_DATE` datetime DEFAULT NULL COMMENT 'date on which data is going to update',
  `LG_IP_MAC` varchar(100) DEFAULT NULL COMMENT 'client machine?s login name | ip address | physical address',
  `LG_IP_MAC_UPD` varchar(100) DEFAULT NULL COMMENT 'updated client machine?s login name | ip address | physical address',
  `DP_DEPTID` bigint(12) DEFAULT NULL COMMENT 'Department Id',
  `ADJ_AMOUNT` decimal(15,2) DEFAULT NULL COMMENT 'Adjested Amount',
  PRIMARY KEY (`ADJBD_ID`),
  KEY `FK_ADJD_ID_idx` (`ADJD_ID`),
  CONSTRAINT `FK_ADJD_ID` FOREIGN KEY (`ADJD_ID`) REFERENCES `TB_ADJUSTMENT_DET` (`adjd_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
