--liquibase formatted sql
--changeset Anil:V20190621155850__CR_tb_rl_propty_shift_21062019.sql
DROP TABLE IF EXISTS `tb_rl_propty_shift`;
--liquibase formatted sql
--changeset Anil:V20190621155850__CR_tb_rl_propty_shift_210620191.sql
CREATE TABLE `tb_rl_propty_shift` (
  `PROP_SHFID` bigint(12) NOT NULL COMMENT ' Primary Key',
  `PROP_ID` bigint(12) NOT NULL COMMENT ' FK',
  `PROP_SHFT` bigint(12) NOT NULL COMMENT ' Prefix->SHF',
  `PROP_FROMTIME` datetime NOT NULL COMMENT ' From Date',
  `PROP_TOTIME` datetime NOT NULL COMMENT ' To Date',
  `PROP_SHIF_STATUS` char(2) DEFAULT NULL,
  `ORGID` bigint(12) NOT NULL COMMENT ' organization id',
  `LANGID` bigint(8) NOT NULL COMMENT ' language id 1- english,2-regional',
  `CREATED_BY` bigint(12) NOT NULL COMMENT ' user id who created the record',
  `CREATED_DATE` date NOT NULL COMMENT ' record creation date',
  `UPDATED_BY` bigint(12) DEFAULT NULL COMMENT ' user id who updated the record',
  `UPDATED_DATE` date DEFAULT NULL COMMENT ' user id who updated the record',
  `LG_IP_MAC` varchar(100) NOT NULL COMMENT ' machine ip address from where user has created the record',
  `LG_IP_MAC_UPD` varchar(100) DEFAULT NULL COMMENT ' machine ip address from where user has updated the record',
  PRIMARY KEY (`PROP_SHFID`),
  KEY `FK_PROP_ID_SH` (`PROP_ID`),
  CONSTRAINT `FK_PROP_ID_SH` FOREIGN KEY (`PROP_ID`) REFERENCES `tb_rl_property_mas` (`PROP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
