--liquibase formatted sql
--changeset nilima:V20170412120521__CR_TB_CONTRACT_DETAIL_DDL.sql
CREATE TABLE `TB_CONTRACT_DETAIL` (
  `CONTD_ID` decimal(12,0) NOT NULL COMMENT 'Storing contract details for Contract Creation,Contract renewal,contract revision',
  `CONT_ID` decimal(12,0) NOT NULL COMMENT 'fk key (TB_CONTRACT_MAST)',
  `CONT_FROM_DATE` datetime NOT NULL COMMENT 'Contract From Date',
  `CONT_TO_DATE` datetime NOT NULL COMMENT 'Contract To Date',
  `CONT_AMOUNT` decimal(20,2) DEFAULT NULL COMMENT 'Contracted Amount applicable for Commerciale contract',
  `CONT_SEC_AMOUNT` decimal(20,2) DEFAULT NULL COMMENT 'Security Deposite Amount applicable for Commerciale contract',
  `CONT_SEC_REC_NO` varchar(40) DEFAULT NULL COMMENT 'Security Deposite Receipt No. applicable for Commerciale contract',
  `CONT_SEC_REC_DATE` datetime DEFAULT NULL COMMENT 'Security Deposite Receipt Date applicable for Commerciale contract',
  `CONT_PAY_PERIOD` decimal(12,0) DEFAULT NULL COMMENT 'Contract Payment Terms (Non Hirarchey Prefix ''''PTR'''') applicable for Commerciale contract',
  `CONT_INSTALLMENT_PERIOD` decimal(12,0) DEFAULT NULL COMMENT 'No. of Installment (Non Hirarchey Prefix ''''NOI'''') applicable for Commerciale contract',
  `CONT_ENTRY_TYPE` char(2) NOT NULL COMMENT '(O->Original R->Renew V->Revise S->Sub lease)',
  `CONTD_ACTIVE` char(1) NOT NULL COMMENT 'flag to identify whether the record is deleted or not. ''''y''''  for not deleted (active) record and ''''n'''' for deleted (inactive) .',
  `ORGID` bigint(12) NOT NULL COMMENT 'orgnisation id',
  `CREATED_BY` bigint(12) NOT NULL COMMENT 'user id who created the record',
  `CREATED_DATE` datetime NOT NULL COMMENT 'record creation date',
  `UPDATED_BY` bigint(12) DEFAULT NULL COMMENT 'user id who update the data',
  `UPDATED_DATE` datetime DEFAULT NULL COMMENT 'date on which data is going to update',
  `LG_IP_MAC` varchar(100) NOT NULL COMMENT 'client machine?s login name | ip address | physical address',
  `LG_IP_MAC_UPD` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`CONTD_ID`),
  KEY `FK_CONT_ID` (`CONT_ID`)
); 

