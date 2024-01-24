--liquibase formatted sql
--changeset Kanchan:V20210129110755__CR_tb_opinion_poll_master_29012021.sql
CREATE TABLE `tb_opinion_poll_master` (
  `POLL_ID` bigint(12) NOT NULL,
  `POLL_SUB_EN` longtext,
  `POLL_SUB_REG` longtext,
  `Attachment1` varchar(2000) DEFAULT NULL,
  `ISSUE_DATE` datetime DEFAULT NULL,
  `VALIDITY_DATE` datetime DEFAULT NULL,
  `Attachment2` varchar(2000) DEFAULT NULL,
  `CHEKER_FLAG` char(1) DEFAULT NULL COMMENT 'Authorisation flag (Y->Authorised,N -> not Authorised)',
  `ISDELETED` varchar(1) NOT NULL,
  `ORGID` bigint(12) NOT NULL COMMENT 'Organisation Id',
  `CREATED_BY` bigint(12) NOT NULL COMMENT 'User Id',
  `CREATED_DATE` datetime NOT NULL,
  `UPDATED_BY` bigint(10) DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `LG_IP_MAC` varchar(100) DEFAULT NULL,
  `LG_IP_MAC_UPD` varchar(100) DEFAULT NULL,
  `ISARCHIVE` char(1) DEFAULT 'N' COMMENT 'For Highligted notice',
  PRIMARY KEY (`POLL_ID`),
  KEY `FK886D7E8938B65205` (`UPDATED_BY`),
  KEY `FK886D7E89418DADB9` (`CREATED_BY`),
  KEY `FK886D7E896078ED5` (`ORGID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--liquibase formatted sql
--changeset Kanchan:V20210129110755__CR_tb_opinion_poll_master_290120211.sql
CREATE TABLE `tb_opinion_poll_master_hist` (
  `POLL_HIST_ID` bigint(12) NOT NULL,
  `POLL_ID` bigint(12) NOT NULL,
  `POLL_SUB_EN` longtext,
  `POLL_SUB_REG` longtext,
  `Attachment1` varchar(2000) DEFAULT NULL,
  `ISSUE_DATE` datetime DEFAULT NULL,
  `VALIDITY_DATE` datetime DEFAULT NULL,
  `Attachment2` varchar(2000) DEFAULT NULL,
  `CHEKER_FLAG` char(1) DEFAULT NULL COMMENT 'Authorisation flag (Y->Authorised,N -> not Authorised)',
  `ISDELETED` varchar(1) NOT NULL,
  `ORGID` bigint(12) NOT NULL COMMENT 'Organisation Id',
  `CREATED_BY` bigint(12) NOT NULL COMMENT 'User Id',
  `CREATED_DATE` datetime NOT NULL,
  `UPDATED_BY` bigint(10) DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `LG_IP_MAC` varchar(100) DEFAULT NULL,
  `LG_IP_MAC_UPD` varchar(100) DEFAULT NULL,
  `ISARCHIVE` char(1) DEFAULT 'N' ,
  PRIMARY KEY (`POLL_HIST_ID`),
  KEY `FK886D7E8938B65205` (`UPDATED_BY`),
  KEY `FK886D7E89418DADB9` (`CREATED_BY`),
  KEY `FK886D7E896078ED5` (`ORGID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--liquibase formatted sql
--changeset Kanchan:V20210129110755__CR_tb_opinion_poll_master_290120212.sql
CREATE TABLE `tb_opinion_poll_option` (
  `POLL_OPTION_ID` bigint(12) NOT NULL,
  `POLL_ID` bigint(12) NOT NULL,
  `OPTION_EN` varchar(500) DEFAULT NULL,
  `OPTION_REG` varchar(500) DEFAULT NULL,
  `IS_SELETED` varchar(1) NOT NULL,
  `CREATED_BY` bigint(12) NOT NULL COMMENT 'User Id',
  `CREATED_DATE` datetime NOT NULL,
  `UPDATED_BY` bigint(12) DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `LG_IP_MAC` varchar(100) DEFAULT NULL,
  `LG_IP_MAC_UPD` varchar(100) DEFAULT NULL,
  `ANSWERED_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`POLL_OPTION_ID`),
  KEY `FK886D7E8938B65205` (`UPDATED_BY`),
  KEY `FK886D7E89418DADB9` (`CREATED_BY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--liquibase formatted sql
--changeset Kanchan:V20210129110755__CR_tb_opinion_poll_master_290120213.sql
CREATE TABLE `tb_opinion_poll_option_hist` (
  `POLL_OPTION_HIST_ID` bigint(12) NOT NULL,
  `POLL_OPTION_ID` bigint(12) NOT NULL,
  `POLL_ID` bigint(12) NOT NULL,
  `OPTION_EN` varchar(500) DEFAULT NULL,
  `OPTION_REG` varchar(500) DEFAULT NULL,
  `IS_SELETED` varchar(1) NOT NULL,
  `CREATED_BY` bigint(12) NOT NULL COMMENT 'User Id',
  `CREATED_DATE` datetime NOT NULL,
  `UPDATED_BY` bigint(12) DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `LG_IP_MAC` varchar(100) DEFAULT NULL,
  `LG_IP_MAC_UPD` varchar(100) DEFAULT NULL,
  `ANSWERED_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`POLL_OPTION_HIST_ID`),
  KEY `FK886D7E8938B65205` (`UPDATED_BY`),
  KEY `FK886D7E89418DADB9` (`CREATED_BY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--liquibase formatted sql
--changeset Kanchan:V20210129110755__CR_tb_opinion_poll_master_290120214.sql
CREATE TABLE `tb_poll_response` (
`RESPONSE_ID` bigint(12) NOT NULL,
`POLL_OPTION_ID` bigint(12) NOT NULL,
`POLL_ID` bigint(12) NOT NULL,
`ORG_ID` bigint(12) NOT NULL,
`CITIZEN_NAME` varchar(100) DEFAULT NULL,
  `CITIZEN_EMAIL` varchar(25) DEFAULT NULL,
  `CITIZEN_MOBILE` varchar(100) DEFAULT NULL,
`IS_DELETED` varchar(1) NOT NULL,
`CREATED_BY` bigint(12) NOT NULL COMMENT 'User Id',
`CREATED_DATE` datetime NOT NULL,
`UPDATED_BY` bigint(12) DEFAULT NULL,
`UPDATED_DATE` datetime DEFAULT NULL,
`LG_IP_MAC` varchar(100) DEFAULT NULL,
`LG_IP_MAC_UPD` varchar(100) DEFAULT NULL,
`RESPONSED_DATE` datetime DEFAULT NULL,
PRIMARY KEY (`RESPONSE_ID`),
KEY `FK886D7E8938B65205` (`UPDATED_BY`),
KEY `FK886D7E89418DADB9` (`CREATED_BY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

