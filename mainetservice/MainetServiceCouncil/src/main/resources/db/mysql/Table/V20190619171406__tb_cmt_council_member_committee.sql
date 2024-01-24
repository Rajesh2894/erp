--liquibase formatted sql
--changeset Anil:V20190619171406__tb_cmt_council_member_committee.sql
DROP TABLE IF EXISTS `tb_cmt_council_member_committee`;

--liquibase formatted sql
--changeset Anil:V20190619171406__tb_cmt_council_member_committee1.sql
CREATE TABLE `tb_cmt_council_member_committee` (
  `MEMBER_COMMITTEE_ID` bigint(12) NOT NULL COMMENT 'Primary key',
  `COU_ID` bigint(12) NOT NULL COMMENT 'Member Id',
  `COMMITTEE_TYPE_ID` bigint(12) NOT NULL COMMENT 'Committee Type (Prefix)',
  `DISSOLVE_DATE` datetime NOT NULL COMMENT 'DISSOLVE_DATE',
  `FROM_DATE` datetime NOT NULL COMMENT 'FROM_DATE',
  `TO_DATE` datetime NOT NULL COMMENT 'TO_DATE',
  `ORGID` bigint(12) NOT NULL COMMENT 'organization id',
  `CREATED_BY` bigint(12) NOT NULL COMMENT 'user id who created the record',
  `CREATED_DATE` datetime NOT NULL COMMENT 'record creation date',
  `UPDATED_BY` bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  `UPDATED_DATE` datetime DEFAULT NULL COMMENT 'date on which updated the record',
  `lg_ip_mac` varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  `lg_ip_mac_upd` varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  `STATUS` char(20) DEFAULT NULL,
  PRIMARY KEY (`MEMBER_COMMITTEE_ID`),
  KEY `FK_MEETING_MEMBER_COMMITTEE_COU_ID` (`COU_ID`),
  CONSTRAINT `FK_MEETING_MEMBER_COMMITTEE_COU_ID` FOREIGN KEY (`COU_ID`) REFERENCES `tb_cmt_council_mem_mast` (`COU_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

