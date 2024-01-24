--liquibase formatted sql
--changeset Anil:V20190619171259__tb_cmt_council_meeting_mom.sql
DROP TABLE IF EXISTS `tb_cmt_council_meeting_mom`;

--liquibase formatted sql
--changeset Anil:V20190619171259__tb_cmt_council_meeting_mom1.sql
CREATE TABLE `tb_cmt_council_meeting_mom` (
  `MEETING_MOM_ID` bigint(12) NOT NULL COMMENT 'Primary key',
  `MEETING_ID` bigint(12) NOT NULL COMMENT 'MEETING_ID',
  `PROPOSAL_ID` bigint(12) NOT NULL COMMENT 'PROPOSAL_ID',
  `MOM_RESOLUTION_COMMENTS` varchar(250) NOT NULL COMMENT 'RESOLUTION_REMARKS',
  `MOM_STATUS` varchar(20) NOT NULL,
  `ORGID` bigint(12) NOT NULL COMMENT 'organization id',
  `CREATED_BY` bigint(12) NOT NULL COMMENT 'user id who created the record',
  `CREATED_DATE` datetime NOT NULL COMMENT 'record creation date',
  `UPDATED_BY` bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  `UPDATED_DATE` datetime DEFAULT NULL COMMENT 'date on which updated the record',
  `LG_IP_MAC` varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  `LG_IP_MAC_UPD` varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (`MEETING_MOM_ID`),
  KEY `FK_MEETING_MOM_MEETING_ID` (`MEETING_ID`),
  KEY `FK_MEETING_MOM_PROPOSAL_ID` (`PROPOSAL_ID`),
  CONSTRAINT `FK_MEETING_MOM_MEETING_ID` FOREIGN KEY (`MEETING_ID`) REFERENCES `tb_cmt_council_meeting_mast` (`MEETING_ID`),
  CONSTRAINT `FK_MEETING_MOM_PROPOSAL_ID` FOREIGN KEY (`PROPOSAL_ID`) REFERENCES `tb_cmt_council_proposal_mast` (`PROPOSAL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

