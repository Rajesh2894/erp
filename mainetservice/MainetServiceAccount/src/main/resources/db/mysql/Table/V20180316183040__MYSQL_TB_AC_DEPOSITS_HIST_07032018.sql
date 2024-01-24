--liquibase formatted sql
--changeset shamik:V20180316183040__MYSQL_TB_AC_DEPOSITS_HIST_070320181.sql
DROP TABLE IF EXISTS TB_AC_DEPOSITS_HIST;
--liquibase formatted sql
--changeset shamik:V20180316183040__MYSQL_TB_AC_DEPOSITS_HIST_070320182.sql
CREATE TABLE TB_AC_DEPOSITS_HIST (
  DEP_ID_H BIGINT(12)  COMMENT 'Primary Key',
  DEP_ID BIGINT(12)  COMMENT '',
  DEP_RECEIPTDT DATETIME  COMMENT 'Receipt Id',
  DEP_NO BIGINT(12)  COMMENT 'deposit number. which will be one-up generated',
  CPD_DEPOSIT_TYPE BIGINT(12)  COMMENT 'deposit type stored in common parameter dty',
  DP_DEPTID BIGINT(12)  COMMENT 'department wherein its deposited',
  DEP_RECEIPTNO BIGINT(12)  COMMENT 'amount deposited, its receipt no also backdated receipt numbers can be entered',
  CPD_STATUS BIGINT(12)  COMMENT 'Cpd Status',
  DEP_AMOUNT DECIMAL(12,2)  COMMENT 'amount deposited',
  DEP_REFUND_BAL DECIMAL(12,2)  COMMENT 'balance amount yet to be refunded through payment voucher',
  VM_VENDORID BIGINT(12)  COMMENT 'vendor id from vendor master',
  DEP_RECEIVEDFROM VARCHAR(1000)  COMMENT 'deposit received from whom',
  DEP_NARRATION VARCHAR(2000) ,
  SAC_HEAD_ID BIGINT(12)  COMMENT 'Account Headcode Id',
  ORGID BIGINT(12)  COMMENT 'organisation id',
  CREATED_BY BIGINT(12)  COMMENT 'user identity',
  CREATED_DATE DATETIME  COMMENT 'last modification date',
  UPDATED_BY BIGINT(12)  COMMENT 'user id who update the data',
  UPDATED_DATE DATETIME  COMMENT 'date on which data is going to update',
  LG_IP_MAC VARCHAR(100)  COMMENT 'client machine''s login name | ip address | physical address',
  LG_IP_MAC_UPD VARCHAR(100)  COMMENT 'updated client machine’s login name | ip address | physical address',
  DEP_DEL_FLAG CHAR(1)  COMMENT 'Deposit Deletion flag',
  DEP_DEL_DATE DATETIME  COMMENT 'Deposit Deletion Date',
  DEP_DEL_ORDER_NO CHAR(1) CHARACTER SET LATIN1  COMMENT 'Deposit Deletion Order No.',
  DEP_DEL_AUTH_BY BIGINT(12)  COMMENT 'Deposit Deletion Authorised by',
  DEP_DEL_REMARK VARCHAR(500)  COMMENT 'Deposit Deletion Remark',
  RM_RCPTID BIGINT(12)  COMMENT 'Receipt id ',
  TR_TENDER_ID BIGINT(12)  COMMENT 'Tender Id',
  BM_ID BIGINT(12)  COMMENT 'fk- tb_ac_bill_mas',
  VOU_ID BIGINT(12)  COMMENT 'Voucher Id',
  PAYMENT_ID BIGINT(12)  COMMENT 'Payment Id',
  H_STATUS CHAR(1) ,
  PRIMARY KEY (DEP_ID_H) 
) ;