--liquibase formatted sql
--changeset Anil:V20191014164834__CR_tb_deposit_14102019.sql
drop table if exists tb_deposit;
--liquibase formatted sql
--changeset Anil:V20191014164834__CR_tb_deposit_141020191.sql
CREATE TABLE tb_deposit(
  DEP_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  DEP_NO bigint(12) NOT NULL COMMENT 'deposit number. which will be one-up generated',
  DEP_DATE date NOT NULL COMMENT 'Deposite Entry Date',
  DEP_TYPE bigint(12) NOT NULL COMMENT 'deposit type stored in common parameter dty',
  DEP_NARRATION varchar(50) NOT NULL COMMENT 'Deposite Narration',
  DP_DEPTID bigint(12) NOT NULL COMMENT 'department wherein its deposited',
  DEP_AMOUNT decimal(12,2) NOT NULL COMMENT 'amount deposited',
  VM_VENDORID bigint(12) NOT NULL COMMENT 'Vendor Id',
  RM_RCPTID bigint(12) DEFAULT NULL COMMENT 'Receipt id',
  VOU_ID bigint(12) DEFAULT NULL COMMENT 'Voucher Id',
  BM_ID bigint(12) DEFAULT NULL COMMENT 'Deposite Refund Bill No.',
  DEP_REFUND_BAL decimal(12,2) DEFAULT NULL COMMENT 'balance amount yet to be refunded through payment voucher',
  SAC_HEAD_ID bigint(12) DEFAULT NULL COMMENT 'Account Headcode Id',
  DEP_REF_ID bigint(12) DEFAULT NULL COMMENT 'Deposite Reference Id',
  DEP_RECEIPTNO bigint(12) DEFAULT NULL COMMENT 'amount deposited, its receipt no also backdated receipt numbers can be entered',
  PAYMENT_ID bigint(12) DEFAULT NULL COMMENT 'Payment Id',
  DEP_ENTRYFLAG char(1) DEFAULT NULL COMMENT 'Manual->M,System->S,Uploaded-> U',
  DEP_STATUS bigint(12) NOT NULL COMMENT 'Deposite Status',
  ORGID bigint(12) NOT NULL COMMENT 'organisation id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user identity',
 CREATED_DATE datetime NOT NULL COMMENT 'last modification date',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'client machine''s login name | ip address | physical address',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'updated client machines login name | ip address | physical address',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who update the data',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'Tax Id',
  PRIMARY KEY(DEP_ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
