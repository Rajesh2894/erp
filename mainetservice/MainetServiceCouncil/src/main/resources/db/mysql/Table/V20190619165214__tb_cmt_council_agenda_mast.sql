--liquibase formatted sql
--changeset Anil:V20190619165214__tb_cmt_council_agenda_mast.sql
DROP TABLE IF EXISTS `tb_cmt_council_agenda_mast`;

--liquibase formatted sql
--changeset Anil:V20190619165214__tb_cmt_council_agenda_mast1.sql
CREATE TABLE `tb_cmt_council_agenda_mast` (
  `AGENDA_ID` bigint(12) NOT NULL COMMENT 'Primary key',
  `AGENDA_DATE` date NOT NULL COMMENT 'Agenda Date',
  `AGENDA_NO` bigint(12) NOT NULL COMMENT 'Agenda No.',
  `COMMITTEE_TYPE_ID` bigint(12) NOT NULL COMMENT 'Committee Type (Prefix)',
  `AGENDA_STATUS` varchar(100) NOT NULL COMMENT 'AGENDA_STATUS',
  `ORGID` bigint(12) NOT NULL COMMENT 'organization id',
  `CREATED_BY` bigint(12) NOT NULL COMMENT 'user id who created the record',
  `CREATED_DATE` datetime NOT NULL COMMENT 'record creation date',
  `UPDATED_BY` bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  `UPDATED_DATE` datetime DEFAULT NULL COMMENT 'date on which updated the record',
  `lg_ip_mac` varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  `lg_ip_mac_upd` varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (`AGENDA_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

