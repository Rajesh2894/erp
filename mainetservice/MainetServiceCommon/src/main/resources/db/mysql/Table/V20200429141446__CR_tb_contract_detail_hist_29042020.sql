--liquibase formatted sql
--changeset Anil:V20200429141446__CR_tb_contract_detail_hist_29042020.sql
drop table if exists tb_contract_detail_hist;
--liquibase formatted sql
--changeset Anil:V20200429141446__CR_tb_contract_detail_hist_290420201.sql
CREATE TABLE tb_contract_detail_hist(
  CONTD_ID_H bigint(12) NOT NULL COMMENT 'primary key',
  CONTD_ID bigint(12) NOT NULL COMMENT 'Storing contract details for Contract Creation,Contract renewal,contract revision',
  CONT_ID bigint(19) NOT NULL,
  CONT_FROM_DATE datetime NOT NULL COMMENT 'Contract From Date',
  CONT_TO_DATE datetime NOT NULL COMMENT 'Contract To Date',
  CONT_AMOUNT decimal(20,2) DEFAULT NULL COMMENT 'Contracted Amount applicable for Commerciale contract',
  CONT_SEC_AMOUNT decimal(20,2) DEFAULT NULL COMMENT 'Security Deposite Amount applicable for Commerciale contract',
  CONT_SEC_REC_NO varchar(40) DEFAULT NULL COMMENT 'Security Deposite Receipt No. applicable for Commerciale contract',
  CONT_SEC_REC_DATE datetime DEFAULT NULL COMMENT 'Security Deposite Receipt Date applicable for Commerciale contract',
  CONT_ADDPER_SECURITYDE decimal(10,2) DEFAULT NULL COMMENT 'Additional Performance Security Depoist',
  CONT_OTHER_DEPDET decimal(10,2) DEFAULT NULL COMMENT 'Other Deposit Detail',
  CONT_PAY_PERIOD bigint(12) DEFAULT NULL COMMENT 'Contract Payment Terms (Non Hirarchey Prefix ''''PTR'''') applicable for Commerciale contract',
  CONT_INSTALLMENT_PERIOD bigint(12) DEFAULT NULL COMMENT 'No. of Installment (Non Hirarchey Prefix ''''NOI'''') applicable for Commerciale contract',
  CONT_DEFECTLIABILITYPER bigint(12) DEFAULT NULL,
  CONT_DEFECTLIABILITYUNI bigint(12) DEFAULT NULL,
  CONT_ENTRY_TYPE char(2) NOT NULL COMMENT '(O->Original R->Renew V->Revise S->Sub lease)',
  CONTD_ACTIVE char(1) NOT NULL COMMENT 'flag to identify whether the record is deleted or not. ''''y''''  for not deleted (active) record and ''''n'''' for deleted (inactive) .',
  ORGID bigint(19) NOT NULL,
  CREATED_BY bigint(19) NOT NULL,
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(19) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which data is going to update',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'client machine?s login name | ip address | physical address',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  CONT_TIMEEXTPER bigint(3) DEFAULT NULL COMMENT 'Contract Time Exten',
  CONT_TIMEEXTUNIT bigint(12) DEFAULT NULL COMMENT 'Contract Time Extension Unit',
  CONT_TIMEEXTEMPID bigint(12) DEFAULT NULL,
  CONT_PERIOD bigint(12) NOT NULL COMMENT 'Contract Period',
  CONT_DEPTYP bigint(12) DEFAULT NULL,
  CONT_DEP_BANKID bigint(12) DEFAULT NULL,
  CONT_DEP_MODEID bigint(12) DEFAULT NULL,
  CONT_DEP_DUEDT date DEFAULT NULL,
  CONT_DEP_PRTCL date DEFAULT NULL,
  H_STATUS varchar(2) DEFAULT NULL,
  PRIMARY KEY (CONTD_ID_H)
);
