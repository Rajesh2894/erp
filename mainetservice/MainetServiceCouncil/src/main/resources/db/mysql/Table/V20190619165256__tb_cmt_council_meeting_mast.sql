--liquibase formatted sql
--changeset Anil:V20190619165256__tb_cmt_council_meeting_mast.sql
DROP TABLE IF EXISTS `tb_cmt_council_meeting_mast`;

--liquibase formatted sql
--changeset Anil:V20190619165256__tb_cmt_council_meeting_mast1.sql
CREATE TABLE `tb_cmt_council_meeting_mast` (
  `MEETING_ID` bigint(12) NOT NULL COMMENT 'Primary key',
  `MEETING_NUMBER` varchar(15) NOT NULL COMMENT 'MEETING Number (1/12/2017-18 <SerialNo>/<Month>/<FinancialYear>)',
  `AGENDA_ID` bigint(12) NOT NULL COMMENT 'AGENDA_ID',
  `MEETING_TYPE_ID` bigint(12) NOT NULL COMMENT 'MEETING_TYPE_ID',
  `MEETING_DATE_TIME` datetime NOT NULL COMMENT 'MEETING_DATE_TIME',
  `MEETING_PLACE` varchar(250) DEFAULT NULL,
  `MEETING_INVITATION_MSG` varchar(250) DEFAULT NULL,
  `MEETING_STATUS` varchar(250) DEFAULT NULL,
  `ORGID` bigint(12) NOT NULL COMMENT 'organization id',
  `CREATED_BY` bigint(12) NOT NULL COMMENT 'user id who created the record',
  `CREATED_DATE` datetime NOT NULL COMMENT 'record creation date',
  `UPDATED_BY` bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  `UPDATED_DATE` datetime DEFAULT NULL COMMENT 'date on which updated the record',
  `lg_ip_mac` varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  `lg_ip_mac_upd` varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (`MEETING_ID`),
  KEY `FK_MEETING_MST_AGENDA_ID` (`AGENDA_ID`),
  CONSTRAINT `FK_MEETING_MST_AGENDA_ID` FOREIGN KEY (`AGENDA_ID`) REFERENCES `tb_cmt_council_agenda_mast` (`AGENDA_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

