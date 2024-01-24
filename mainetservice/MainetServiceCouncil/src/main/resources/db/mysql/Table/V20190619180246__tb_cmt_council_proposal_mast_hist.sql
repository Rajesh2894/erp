--liquibase formatted sql
--changeset Anil:V20190619180246__tb_cmt_council_proposal_mast_hist.sql
DROP TABLE IF EXISTS `tb_cmt_council_proposal_mast_hist`;

--liquibase formatted sql
--changeset Anil:V20190619180246__tb_cmt_council_proposal_mast_hist1.sql
CREATE TABLE `tb_cmt_council_proposal_mast_hist` (
  `PROPOSAL_H_ID` bigint(12) NOT NULL COMMENT 'Primary key',
  `PROPOSAL_ID` bigint(12) NOT NULL COMMENT 'Foreign key',
  `PROPOSAL_NUMBER` varchar(15) DEFAULT NULL COMMENT 'Proposal Number (1/12/2017-18 <SerialNo>/<Month>/<FinancialYear>)',
  `PROPOSAL_DEP_ID` bigint(12) DEFAULT NULL COMMENT 'Department ID',
  `PROPOSAL_DATE` datetime DEFAULT NULL COMMENT 'Proposal Date',
  `PROPOSAL_DETAILS` varchar(1000) DEFAULT NULL COMMENT 'Proposal Details',
  `PROPOSAL_AMOUNT` int(15) DEFAULT NULL COMMENT 'Proposal Amount',
  `AGENDA_ID` bigint(12) DEFAULT NULL COMMENT 'Agenada Number',
  `PROPOSAL_STATUS` char(1) DEFAULT 'D' COMMENT 'Proposal Status(d->Draft,P->Pending,,A->Approved,R->Reject)',
  `H_STATUS` char(1) DEFAULT NULL COMMENT 'History Status',
  `CREATED_BY` bigint(12) NOT NULL COMMENT 'user id who created the record',
  `CREATED_DATE` datetime NOT NULL COMMENT 'record creation date',
  `ORGID` bigint(12) NOT NULL COMMENT 'organization id',
  `UPDATED_DATE` datetime DEFAULT NULL COMMENT 'date on which updated the record',
  `lg_ip_mac` varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  `lg_ip_mac_upd` varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (`PROPOSAL_H_ID`),
  KEY `FK_PROPOSAL_ID1` (`PROPOSAL_ID`),
  CONSTRAINT `FK_PROPOSAL_ID1` FOREIGN KEY (`PROPOSAL_ID`) REFERENCES `tb_cmt_council_proposal_mast` (`PROPOSAL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

