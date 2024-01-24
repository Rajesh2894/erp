--liquibase formatted sql
--changeset nilima:V20190801183006_CR_TB_AC_INVMST1.sql
drop table  IF EXISTS TB_AC_INVMST;

--liquibase formatted sql
--changeset nilima:V20190801183006_CR_TB_AC_INVMST2.sql
CREATE TABLE TB_AC_INVMST
(`IN_INVID`	BIGINT(12) NOT NULL COMMENT 'INVESTMENT ID',
`IN_INVNO`	VARCHAR(10) NOT NULL COMMENT 'INVESTMENT NO',
`IN_INVTYPE`	BIGINT(7) NOT NULL COMMENT 'INVESTMENT TYPE',
`IN_BANKID`	BIGINT(7) NOT NULL COMMENT 'BANK NAME',
`IN_FDRNO`	VARCHAR(20) NOT NULL COMMENT 'FDR NO',
`IN_INVDT`	DATE NOT NULL COMMENT 'INVESTMENT DATE',
`IN_INVAMT`	DECIMAL(12,2) NOT NULL COMMENT 'INVESTMENT AMOUNT',
`IN_DUEDATE`	DATE NOT NULL COMMENT 'INVESTMENT DUE DATE',
`IN_INTRATE`	DECIMAL(2,2) DEFAULT NULL COMMENT 'INTEREST RATE',
`IN_INTAMT`	DECIMAL(12,2) NOT NULL COMMENT 'NTEREST AMOUNT',
`IN_MATAMT`	DECIMAL(12,2) NOT NULL COMMENT 'INVESTMENT MATURITY AMOUNT',
`IN_RESNO`	DECIMAL(12) DEFAULT NULL COMMENT 'RESOLUTION NUMBER',
`IN_RESDT`	DATE DEFAULT NULL COMMENT 'RESOLUTION DATE',
`FUND_ID`	BIGINT(7) DEFAULT NULL COMMENT 'FUND ',
`REMARKS` VARCHAR(200) DEFAULT NULL COMMENT 'REMARK',
`IN_STATUS`	CHAR(1) DEFAULT NULL COMMENT 'O-OPEN AND C- CLOSED',
`CLOS_DT`	DATE DEFAULT NULL COMMENT 'WITHDRAWAL / RENEWAL DATE',
`LANG_ID`	BIGINT(2)	NOT NULL COMMENT 'LANGUAGE IDENTITY',
`ORGID`	BIGINT(20) DEFAULT NULL,
`CREATED_BY`	BIGINT(20) NOT NULL COMMENT 'USER ID',
`CREATED_DATE`	DATETIME NOT NULL COMMENT 'LAST MODIFICATION DATE',
`UPDATED_BY`	BIGINT(20) DEFAULT NULL COMMENT 'USER ID WHO UPDATE THE DATA',
`UPDATED_DATE`	DATETIME DEFAULT NULL COMMENT 'DATE ON WHICH DATA IS GOING TO UPDATE',
`LG_IP_MAC`	VARCHAR(100) NOT NULL COMMENT 'CLIENT MACHINE’S LOGIN NAME | IP ADDRESS | PHYSICAL ADDRESS',
`LG_IP_MAC_UPD`	VARCHAR(100) DEFAULT NULL,
PRIMARY KEY (`IN_INVID`)
);

--liquibase formatted sql
--changeset nilima:V20190801183006_CR_TB_AC_INVMST3.sql
drop table  IF EXISTS TB_AC_INVMST_HIST;

--liquibase formatted sql
--changeset nilima:V20190801183006_CR_TB_AC_INVMST4.sql
CREATE TABLE `TB_AC_INVMST_HIST`(
`H_IN_INVID` BIGINT(12) NOT NULL,
`IN_INVID` BIGINT(12) NOT NULL COMMENT 'INVESTMENT ID',
`IN_INVNO` VARCHAR(10) NOT NULL COMMENT 'INVESTMENT NO',
`IN_INVTYPE` BIGINT(7) NOT NULL COMMENT 'INVESTMENT TYPE',
`IN_BANKID` BIGINT(7) NOT NULL COMMENT 'BANK NAME',
`IN_FDRNO` VARCHAR(20) NOT NULL COMMENT 'FDR NO',
`IN_INVDT` DATE NOT NULL COMMENT 'INVESTMENT DATE',
`IN_INVAMT` DECIMAL(12,2) NOT NULL COMMENT 'INVESTMENT AMOUNT',
`IN_DUEDATE` DATE NOT NULL COMMENT 'INVESTMENT DUE DATE',
`IN_INTRATE` DECIMAL(2,2) DEFAULT NULL COMMENT 'INTEREST RATE',
`IN_INTAMT` DECIMAL(12,2) NOT NULL COMMENT 'NTEREST AMOUNT',
`IN_MATAMT` DECIMAL(12,2) NOT NULL COMMENT 'INVESTMENT MATURITY AMOUNT',
`IN_RESNO` DECIMAL(12,0) DEFAULT NULL COMMENT 'RESOLUTION NUMBER',
`IN_RESDT` DATE DEFAULT NULL COMMENT 'RESOLUTION DATE',
`FUND_ID` BIGINT(7) DEFAULT NULL COMMENT 'FUND ',
`REMARKS` VARCHAR(200) DEFAULT NULL COMMENT 'REMARK',
`IN_STATUS` CHAR(1) DEFAULT NULL COMMENT 'O-OPEN AND C- CLOSED',
`CLOS_DT` DATE DEFAULT NULL COMMENT 'WITHDRAWAL / RENEWAL DATE',
`LANG_ID` BIGINT(2) NOT NULL COMMENT 'LANGUAGE IDENTITY',
`ORGID` BIGINT(20) DEFAULT NULL,
`CREATED_BY` BIGINT(20) NOT NULL COMMENT 'USER ID',
`CREATED_DATE` DATETIME NOT NULL COMMENT 'LAST MODIFICATION DATE',
`UPDATED_BY` BIGINT(20) DEFAULT NULL COMMENT 'USER ID WHO UPDATE THE DATA',
`UPDATED_DATE` DATETIME DEFAULT NULL COMMENT 'DATE ON WHICH DATA IS GOING TO UPDATE',
`LG_IP_MAC` VARCHAR(100) NOT NULL COMMENT 'CLIENT MACHINE’S LOGIN NAME | IP ADDRESS | PHYSICAL ADDRESS',
`LG_IP_MAC_UPD` VARCHAR(100) DEFAULT NULL,
`H_STATUS` VARCHAR(2) DEFAULT NULL,
PRIMARY KEY (`H_IN_INVID`)
);