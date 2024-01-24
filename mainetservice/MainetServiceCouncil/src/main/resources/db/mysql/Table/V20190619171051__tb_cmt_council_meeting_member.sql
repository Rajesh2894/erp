--liquibase formatted sql
--changeset Anil:V20190619171051__tb_cmt_council_meeting_member.sql
DROP TABLE IF EXISTS `tb_cmt_council_meeting_member`;

--liquibase formatted sql
--changeset Anil:V20190619171051__tb_cmt_council_meeting_member1.sql
CREATE TABLE `tb_cmt_council_meeting_member` (
  `MEETING_MEMBER_ID` bigint(12) NOT NULL COMMENT 'Primary key',
  `MEETING_ID` bigint(12) NOT NULL COMMENT 'MEETING_ID',
  `COU_ID` bigint(12) NOT NULL COMMENT 'COU_ID',
  `ATTENDANCE_STATUS` char(1) NOT NULL DEFAULT '0' COMMENT '0->Absent,1->Present',
  `ORGID` bigint(12) NOT NULL COMMENT 'organization id',
  `CREATED_BY` bigint(12) NOT NULL COMMENT 'user id who created the record',
  `CREATED_DATE` datetime NOT NULL COMMENT 'record creation date',
  `UPDATED_BY` bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  `UPDATED_DATE` datetime DEFAULT NULL COMMENT 'date on which updated the record',
  `lg_ip_mac` varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  `lg_ip_mac_upd` varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (`MEETING_MEMBER_ID`),
  KEY `FK_MEETING_MEMBER_MEETING_ID` (`MEETING_ID`),
  KEY `FK_MEETING_MEMBER_COU_ID` (`COU_ID`),
  CONSTRAINT `FK_MEETING_MEMBER_COU_ID` FOREIGN KEY (`COU_ID`) REFERENCES `tb_cmt_council_mem_mast` (`COU_ID`),
  CONSTRAINT `FK_MEETING_MEMBER_MEETING_ID` FOREIGN KEY (`MEETING_ID`) REFERENCES `tb_cmt_council_meeting_mast` (`MEETING_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

