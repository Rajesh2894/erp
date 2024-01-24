--liquibase formatted sql
--changeset Anil:V20190619171317__tb_cmt_council_mem_mast.sql
DROP TABLE IF EXISTS `tb_cmt_council_mem_mast`;

--liquibase formatted sql
--changeset Anil:V20190619171317__tb_cmt_council_mem_mast1.sql
CREATE TABLE `tb_cmt_council_mem_mast` (
  `COU_ID` bigint(12) NOT NULL,
  `COU_COD_ID1` bigint(12) DEFAULT NULL,
  `COU_COD_ID2` bigint(12) DEFAULT NULL,
  `COU_COD_ID3` bigint(12) DEFAULT NULL,
  `COU_COD_ID4` bigint(12) DEFAULT NULL,
  `COU_COD_ID5` bigint(12) DEFAULT NULL,
  `COU_DESG_ID` bigint(12) NOT NULL,
  `COU_EDU_ID` bigint(12) NOT NULL,
  `COU_CAST_ID` bigint(12) NOT NULL,
  `COU_MEM_NAME` varchar(250) NOT NULL,
  `COU_GEN` bigint(12) NOT NULL,
  `COU_MOBNO` bigint(10) NOT NULL,
  `COU_DOB` datetime NOT NULL,
  `COU_EMAIL` varchar(250) NOT NULL,
  `COU_ADDRESS` varchar(500) NOT NULL,
  `COU_ELECDATE` datetime NOT NULL,
  `COU_OATHDATE` datetime NOT NULL,
  `COU_PARTYAFFILATION` bigint(12) NOT NULL,
  `ORGID` bigint(12) NOT NULL,
  `CREATED_BY` bigint(12) NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `UPDATED_BY` bigint(12) DEFAULT NULL,
  `UPDATED_DATE` varchar(45) DEFAULT NULL,
  `lg_ip_mac` varchar(100) NOT NULL,
  `lg_ip_mac_upd` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`COU_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

