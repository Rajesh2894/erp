--liquibase formatted sql
--changeset Anil:V20190619180312__tb_cmt_council_proposal_ward.sql
DROP TABLE IF EXISTS `tb_cmt_council_proposal_ward`;

--liquibase formatted sql
--changeset Anil:V20190619180312__tb_cmt_council_proposal_ward1.sql
CREATE TABLE `tb_cmt_council_proposal_ward` (
  `PROPOSAL_WARD_ID` bigint(12) NOT NULL COMMENT 'Primary key',
  `PROPOSAL_ID` bigint(12) NOT NULL COMMENT 'PROPOSAL ID',
  `WARD_ID` bigint(12) NOT NULL COMMENT 'WARD ID',
  `ORGID` bigint(12) NOT NULL COMMENT 'organization id',
  `CREATED_BY` bigint(12) NOT NULL COMMENT 'user id who created the record',
  `CREATED_DATE` datetime NOT NULL COMMENT 'record creation date',
  `UPDATED_BY` bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  `UPDATED_DATE` datetime DEFAULT NULL COMMENT 'date on which updated the record',
  `lg_ip_mac` varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  `lg_ip_mac_upd` varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (`PROPOSAL_WARD_ID`),
  KEY `FK_PROPOSAL_ID` (`PROPOSAL_ID`),
  CONSTRAINT `FK_PROPOSAL_ID` FOREIGN KEY (`PROPOSAL_ID`) REFERENCES `tb_cmt_council_proposal_mast` (`PROPOSAL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

