--liquibase formatted sql
--changeset nilima:V20171012122502__cr_tb_ac_bank_tds_details_hist_03102017.sql
CREATE TABLE tb_ac_bank_tds_details_hist (
  HPTB_ID bigint(12) NOT NULL,
  PTB_ID bigint(12) NOT NULL,
  ORGID bigint(12) NOT NULL,
  BANKID bigint(12) NOT NULL,
  CREATED_BY bigint(12) NOT NULL,
  CREATED_DATE datetime NOT NULL,
  UPDATED_BY int(11) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  PTB_BSRCODE varchar(15) NOT NULL,
  LG_IP_MAC varchar(100) NOT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  VM_VENDORID bigint(12) NOT NULL,
  PTB_TDS_TYPE bigint(12) NOT NULL,
  PTB_STATUS char(1) NOT NULL,
  SAC_HEAD_ID bigint(12) NOT NULL,
  PTB_BAK_ACCOUNTNO varchar(50) DEFAULT NULL,
   HPTB_STATUS varchar(1) DEFAULT NULL,
  PRIMARY KEY (HPTB_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;