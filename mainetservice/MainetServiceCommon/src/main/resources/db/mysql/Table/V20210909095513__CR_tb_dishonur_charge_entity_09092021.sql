--liquibase formatted sql
--changeset Kanchan:V20210909095513__CR_tb_dishonur_charge_entity_09092021.sql
CREATE TABLE `tb_dishonur_charge_entity` (
  `DISHNOUR_CHG_ID` bigint(16) NOT NULL,
  `APM_APPLICATION_ID` bigint(16) NOT NULL,
  `REF_ID` varchar(100) DEFAULT NULL,
  `TAX_ID` bigint(16) DEFAULT NULL,
  `DIS_HN_AMT` decimal(20,2) DEFAULT NULL,
  `IS_DIS_HNR_CHR_PAID` varchar(10) DEFAULT NULL,
  `DIS_HN_REMARKS` varchar(1000) DEFAULT NULL,
  `ORGID` bigint(12) DEFAULT NULL,
  `CREATED_BY` bigint(12) NOT NULL,
  `UPDATED_BY` bigint(12) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `LG_IP_MAC` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`DISHNOUR_CHG_ID`)
) ;
