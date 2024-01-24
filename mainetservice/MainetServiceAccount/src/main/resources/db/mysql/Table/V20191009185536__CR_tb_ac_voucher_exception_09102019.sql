--liquibase formatted sql
--changeset Anil:V20191009185536__CR_tb_ac_voucher_exception_09102019.sql
create table tb_ac_voucher_exception(
VOU_ID bigint(12) NOT NULL DEFAULT '0' COMMENT 'primary key',
VOU_DATE datetime NOT NULL COMMENT 'Voucher entry Date',
VOU_TYPE_CPD_ID bigint(12) NOT NULL,
VOU_SUBTYPE_CPD_ID bigint(12) NOT NULL,
DP_DEPTID bigint(12) NOT NULL COMMENT 'Department Id',
VOU_REFERENCE_NO varchar(40) DEFAULT NULL COMMENT 'Voucher ref number receipt/bill/contra etc number ',
VOU_REFERENCE_NO_DATE datetime DEFAULT NULL COMMENT 'Voucher ref number receipt/bill/contra etc number ',
NARRATION varchar(2000) NOT NULL COMMENT 'Voucher NARRATION ',
PAYER_PAYEE varchar(2000) DEFAULT NULL COMMENT 'Payer and Payee from receipt master and payment voucher ',
FIELD_ID bigint(12) DEFAULT NULL COMMENT 'FK TB_AC_FIELD_MASTER',
ORGID bigint(12) NOT NULL COMMENT 'Organisation Id',
CREATED_BY bigint(12) NOT NULL COMMENT 'Created User Identity',
CREATED_DATE datetime NOT NULL COMMENT 'Created Date',
LANG_ID bigint(2) NOT NULL COMMENT 'Language Identity',
LG_IP_MAC varchar(100) NOT NULL COMMENT 'Client Machines Login Name | IP Address | Physical Address',
ENTRY_TYPE bigint(12) NOT NULL COMMENT 'Voucher entry type: System :- 1 Manual :- 2',
BILL_VOUCHER_POSTING_DATE datetime DEFAULT NULL COMMENT 'bill voucher posting date',
PAY_ENTRY_MAKER_FLAG char(1) DEFAULT NULL COMMENT 'paymnet entry maker flag',
AUTHO_FLG char(1) DEFAULT NULL COMMENT 'Flag',
TEMPLATE_TYPE bigint(12) DEFAULT NULL,
FA_YEARID bigint(12) DEFAULT NULL,
PAY_MODEID_ID bigint(12) DEFAULT NULL,
ENTRY_FLAG char(1) DEFAULT NULL COMMENT 'Flag',
EXCEPTION_DETAILS VARCHAR(100) DEFAULT NULL COMMENT 'Exception details',
PRIMARY KEY (VOU_ID),
KEY FK_DP_DEPTID_VOUCHER_EXCEPTION(DP_DEPTID),
KEY FK_VOU_SUBTYPE_CPDID_VOUCSUBID_EXCEPTION(VOU_SUBTYPE_CPD_ID),
KEY FK_VOU_TYPE_CPD_ID_VOUCHER_EXCEPTION(VOU_TYPE_CPD_ID),
KEY FK_VOUCHERHD_FIELD_ID_EXCEPTION(FIELD_ID),
CONSTRAINT FK_VOU_DEP_ID_EXCEPTION FOREIGN KEY (DP_DEPTID) REFERENCES tb_department(DP_DEPTID) ON DELETE NO ACTION ON UPDATE NO ACTION
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
--liquibase formatted sql
--changeset Anil:V20191009185536__CR_tb_ac_voucher_exception_091020191.sql
create table tb_ac_voucher_exception_det(
VOUDET_ID bigint(12) NOT NULL DEFAULT '0' COMMENT 'Primary Key',
VOUDET_AMT decimal(15,2) NOT NULL COMMENT 'Voucher Detail Amount',
DRCR_CPD_ID bigint(15) NOT NULL COMMENT 'prefix ''DCR''',
SAC_HEAD_ID bigint(12) DEFAULT NULL,
PAY_MODEID_ID bigint(12) DEFAULT NULL,
DEMAND_TYPE_ID bigint(12) DEFAULT NULL,
YEARID bigint(12) DEFAULT NULL,
ACCOUNT_HEAD_FLAG char(1) DEFAULT NULL COMMENT 'Flag',
PRIMARY KEY(VOUDET_ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
