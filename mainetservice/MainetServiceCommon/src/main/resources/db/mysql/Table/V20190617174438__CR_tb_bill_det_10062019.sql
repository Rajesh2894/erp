--liquibase formatted sql
--changeset Anil:V20190617174438__CR_tb_bill_det_10062019.sql
drop table if exists tb_bill_det;
--liquibase formatted sql
--changeset Anil:V20190617174438__CR_tb_bill_det_100620191.sql
CREATE TABLE `tb_bill_det` (
  `TBD_ID` bigint(12) NOT NULL,
  `BM_ID` bigint(12) NOT NULL,
  `TAX_ID` bigint(12) NOT NULL,
  `TBD_AMOUNT` decimal(10,0) DEFAULT NULL,
  `SAC_HEAD_ID` bigint(12) DEFAULT NULL,
  `ACC_NO` bigint(12) DEFAULT NULL,
  `ORGID` bigint(12) NOT NULL,
  `CREATEDBY` int(11) NOT NULL,
  `CREATEDDATETIME` datetime NOT NULL,
  `LASTUPDATEDBY` int(11) DEFAULT NULL,
  `LASTUPDATEDDATETIME` datetime DEFAULT NULL,
  `ISDELETED` varchar(1) NOT NULL,
  `SBD_REMARKS` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`TBD_ID`),
  KEY `FK_tb_bill_det` (`BM_ID`),
  CONSTRAINT `FK_tb_bill_det` FOREIGN KEY (`BM_ID`) REFERENCES `tb_bill_mas` (`bm_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8