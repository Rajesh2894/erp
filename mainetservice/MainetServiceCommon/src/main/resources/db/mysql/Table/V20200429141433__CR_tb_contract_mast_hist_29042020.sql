--liquibase formatted sql
--changeset Anil:V20200429141433__CR_tb_contract_mast_hist_29042020.sql
drop table if exists tb_contract_mast_hist;
--liquibase formatted sql
--changeset Anil:V20200429141433__CR_tb_contract_mast_hist_290420201.sql
CREATE TABLE tb_contract_mast_hist(
  CONT_ID_H bigint(12) NOT NULL COMMENT 'primary key',
  CONT_ID bigint(12) NOT NULL,
  CONT_DEPT bigint(12) NOT NULL COMMENT 'Contract Department(department selected in Party1 details)',
  CONT_NO varchar(40) DEFAULT NULL COMMENT 'Contract No. (FINANCIAL YEAR+9999)',
  CONT_DATE datetime NOT NULL COMMENT 'Contract Date/Renewal Date/Revision Date',
  CONT_TND_NO varchar(40) NOT NULL COMMENT 'Tender No.',
  CONT_TND_DATE datetime NOT NULL COMMENT 'Tender Date',
  CONT_RSO_NO varchar(40) NOT NULL COMMENT 'Resolution No.',
  CONT_RSO_DATE datetime NOT NULL COMMENT 'Resolution Date',
  CONT_TYPE bigint(12) NOT NULL COMMENT 'Contract Type value from Non Hirarchey Prefix(CNT)',
  CONT_MODE char(1) DEFAULT NULL COMMENT 'Contract Mode(Commercial ''C'',Non Commercial ''N'')',
  CONT_PAY_TYPE char(1) DEFAULT NULL COMMENT 'Contract Payment Type (Payable ''P'',Receivable ''R'')',
  CONT_RENEWAL char(1) DEFAULT NULL COMMENT 'Renewal of Contract (Y,N)',
  cont_map_flag char(1) DEFAULT NULL COMMENT 'Flag',
  LOA_NO varchar(50) DEFAULT NULL,
  CONT_ACTIVE char(1) NOT NULL COMMENT 'flag to identify whether the record is deleted or not. ''y'' for deleted (inactive) and ''n'' for not deleted (active) record.',
  CONT_CLOSE_FLAG char(1) NOT NULL COMMENT 'Contract Close Flag (N,Y)',
  CONT_AUT_STATUS char(1) DEFAULT NULL COMMENT 'authorisation status',
  CONT_AUT_BY bigint(12) DEFAULT NULL COMMENT 'authorisation by (empid)',
  CONT_AUT_DATE datetime DEFAULT NULL COMMENT 'authorisation date',
  CONT_TERMINAT_BY char(1) DEFAULT NULL COMMENT 'Contract Terminated By',
  CONT_TERMINATION_DATE datetime DEFAULT NULL COMMENT 'Contract Termination Date',
  ORGID bigint(19) NOT NULL,
  CREATED_BY bigint(19) NOT NULL,
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(19) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which data is updated',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'client machine?s login name | ip address | physical address',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'updated client machine?s login name | ip address | physical address',
  H_STATUS varchar(2) DEFAULT NULL,
  PRIMARY KEY (CONT_ID_H)
);

