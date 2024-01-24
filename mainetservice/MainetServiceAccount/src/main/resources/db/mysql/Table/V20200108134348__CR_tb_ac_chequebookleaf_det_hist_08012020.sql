--liquibase formatted sql
--changeset Anil:V20200108134348__CR_tb_ac_chequebookleaf_det_hist_08012020.sql
drop table if exists tb_ac_chequebookleaf_det_hist;
--liquibase formatted sql
--changeset Anil:V20200108134348__CR_tb_ac_chequebookleaf_det_hist_080120201.sql
CREATE TABLE tb_ac_chequebookleaf_det_hist (
CHEQUE_ID bigint(12) NOT NULL,
CHEQUEBOOK_DETID bigint(12) COMMENT 'Generated checkbook id (primary key)',
CHEQUEBOOK_ID bigint(12) COMMENT 'Checkbook Id(Fk)',
CHEQUE_NO varchar(24) COMMENT 'Cheque No ',
CPD_IDSTATUS bigint(12) COMMENT 'Cpd_idstatus (Fk -TB_COMPARAM_DET )',
PAYMENT_ID bigint(12) COMMENT 'Payment voucher Id (Fk -TB_AC_PAYMENT_MAS) ',
REMARK varchar(500) COMMENT 'Remark',
STOP_PAY_ORDER_NO bigint(12) COMMENT 'Stop Payment Order No',
STOP_PAY_ORDER_DATE datetime COMMENT 'Stop Payment Order Date',
STOP_PAY_FLAG char(1) COMMENT 'Stop Payment Order as per MNAM',
STOP_PAY_REMARK varchar(500) COMMENT 'Stop Payment Remark',
STOPPAY_DATE datetime COMMENT 'Stop Payment Date',
PAYMENT_TYPE char(1) COMMENT 'Payments Cheque Issuance Flag',
ISSUANCE_DATE datetime COMMENT 'Cheque issuance date',
CANCELLATION_DATE datetime COMMENT 'Cancellation Date',
CANCELLATION_REASON varchar(1000) COMMENT 'Reason for cancellation',
NEW_ISSUE_CHEQUEBOOK_DETID bigint(12) COMMENT 'New Cheque issue id --Ref. TB_AC_CHEQUEBOOKLEAF_DET.CHEQUEBOOK_DETID ',
ORGID bigint(12) COMMENT 'Organization Id',
CREATED_BY bigint(12) COMMENT 'User Identity',
CREATED_DATE datetime COMMENT 'Last Modification Date',
UPDATED_BY bigint(10) ,
UPDATED_DATE datetime COMMENT 'Last Modification Date',
LG_IP_MAC varchar(100) COMMENT 'client machine''s login name | ip address | physical address',
LG_IP_MAC_UPD varchar(100) COMMENT 'Updated Client Machine¿s Login Name | IP Address | Physical Address',
FI04_N1 decimal(15,0) COMMENT 'Additional number FI04_N1 to be used in future',
FI04_V1 varchar(200) COMMENT 'Additional nvarchar2 FI04_V1 to be used in future',
FI04_D1 datetime COMMENT 'Additional Date FI04_D1 to be used in future',
FI04_LO1 char(1) COMMENT 'Additional Logical field FI04_LO1 to be used in future',
H_STATUS char(1) NOT NULL ,
PRIMARY KEY (CHEQUE_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='This table stores history Details of the Checkbook';
