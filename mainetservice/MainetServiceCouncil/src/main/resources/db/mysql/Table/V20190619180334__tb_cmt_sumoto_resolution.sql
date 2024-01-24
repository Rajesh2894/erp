--liquibase formatted sql
--changeset Anil:V20190619180334__tb_cmt_sumoto_resolution.sql
DROP TABLE IF EXISTS `tb_cmt_sumoto_resolution`;

--liquibase formatted sql
--changeset Anil:V20190619180334__tb_cmt_sumoto_resolution1.sql
CREATE TABLE `tb_cmt_sumoto_resolution` (
  `SUMOTO_RESO_ID` bigint(12) NOT NULL COMMENT 'Primary key',
  `MEETING_MOM_ID` bigint(12) NOT NULL COMMENT 'MOM_ID',
  `SUMOTO_DEP_ID` bigint(12) NOT NULL COMMENT 'Department ID',
  `DETAILS_OF_RESO` varchar(250) DEFAULT NULL,
  `RESOLUTION_NO` varchar(15) NOT NULL COMMENT 'RESOLUTION_NO (25/12/2017-110 <Meeting Date>/<Serial no>)',
  `RESOLUTION_COMMENT` varchar(250) NOT NULL COMMENT 'SUMOTO RESOLUTION COMMENT ',
  `AMOUNT` int(15) NOT NULL COMMENT 'Sumoto Amount',
  `STATUS` varchar(20) NOT NULL,
  `ORGID` bigint(12) NOT NULL COMMENT 'organization id',
  `CREATED_BY` bigint(12) NOT NULL COMMENT 'user id who created the record',
  `CREATED_DATE` datetime NOT NULL COMMENT 'record creation date',
  `UPDATED_BY` bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  `UPDATED_DATE` datetime DEFAULT NULL COMMENT 'date on which updated the record',
  `LG_IP_MAC` varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  `LG_IP_MAC_UPD` varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (`SUMOTO_RESO_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

