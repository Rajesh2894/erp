--liquibase formatted sql
--changeset Anil:V20190619171857__tb_cmt_council_proposal_mast.sql
DROP TABLE IF EXISTS `tb_cmt_council_proposal_mast`;

--liquibase formatted sql
--changeset Anil:V20190619171857__tb_cmt_council_proposal_mast1.sql
CREATE TABLE `tb_cmt_council_proposal_mast` (
  `PROPOSAL_ID` bigint(12) NOT NULL COMMENT 'Primary key',
  `PROPOSAL_NUMBER` varchar(15) NOT NULL COMMENT 'Proposal Number (1/12/2017-18 <SerialNo>/<Month>/<FinancialYear>)',
  `PROPOSAL_DEP_ID` bigint(12) NOT NULL COMMENT 'Department ID',
  `PROPOSAL_DATE` datetime NOT NULL COMMENT 'Proposal Date',
  `PROPOSAL_DETAILS` varchar(1000) NOT NULL COMMENT 'Proposal Details',
  `PROPOSAL_AMOUNT` int(15) NOT NULL COMMENT 'Proposal Amount',
  `AGENDA_ID` bigint(12) DEFAULT NULL COMMENT 'Agenada Number',
  `PROPOSAL_STATUS` char(1) NOT NULL DEFAULT 'D' COMMENT 'Proposal Status(d->Draft,P->Pending,,A->Approved,R->Reject)',
  `ORGID` bigint(12) NOT NULL COMMENT 'organization id',
  `CREATED_BY` bigint(12) NOT NULL COMMENT 'user id who created the record',
  `CREATED_DATE` datetime NOT NULL COMMENT 'record creation date',
  `UPDATED_BY` bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  `UPDATED_DATE` datetime DEFAULT NULL COMMENT 'date on which updated the record',
  `lg_ip_mac` varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  `lg_ip_mac_upd` varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  `YEAR_ID` bigint(12) DEFAULT NULL,
  `SAC_HEAD_ID` bigint(12) DEFAULT NULL,
  PRIMARY KEY (`PROPOSAL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

